package com.tone.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.interop.ToneForeignToToneTypeNode;
import com.tone.nodes.runtime.ToneUndefinedNameException;
import com.tone.runtime.SplContext;

/**
 * The node for reading a property of an object. When executed, this node:
 * <ol>
 * <li>evaluates the object expression on the left hand side of the object access operator</li>
 * <li>evaluated the property name</li>
 * <li>reads the named property</li>
 * </ol>
 */
@NodeInfo(shortName = ".")
@NodeChild("receiverNode")
@NodeChild("nameNode")
@ImportStatic({SplContext.class, Message.class})
public abstract class ToneReadPropertyNode extends ToneExpressionNode {

    @Specialization(guards = "isToneObject(receiver)")
    protected Object read(DynamicObject receiver, Object name,
                          @Cached("create()") ToneReadPropertyCacheNode readNode) {
        /**
         * The polymorphic cache node that performs the actual read. This is a separate node so that
         * it can be re-used in cases where the receiver and name are not nodes but already
         * evaluated values.
         */
        return readNode.executeRead(receiver, name);
    }

    /**
     * Language interoperability: if the receiver object is a foreign value we use Truffle's interop
     * API to access the foreign data.
     */
    @Specialization(guards = "!isToneObject(receiver)")
    protected Object readForeign(TruffleObject receiver, Object name,
                                 // The child node to access the foreign object
                                 @Cached("READ.createNode()") Node foreignReadNode,
                                 // The child node to convert the result of the foreign read to a Tone value
                                 @Cached("create()") ToneForeignToToneTypeNode toToneTypeNode) {

        try {
            /* Perform the foreign object access. */
            Object result = ForeignAccess.sendRead(foreignReadNode, receiver, name);
            /* Convert the result to a Tone value. */
            return toToneTypeNode.executeConvert(result);

        } catch (UnknownIdentifierException | UnsupportedMessageException e) {
            /* Foreign access was not successful. */
            throw ToneUndefinedNameException.undefinedProperty(this, name);
        }
    }

    /**
     * When no specialization fits, the receiver is either not an object (which is a type error), or
     * the object has a shape that has been invalidated.
     */
    @Fallback
    protected Object typeError(@SuppressWarnings("unused") Object r, Object name) {
        /* Non-object types do not have properties. */
        throw ToneUndefinedNameException.undefinedProperty(this, name);
    }
}
