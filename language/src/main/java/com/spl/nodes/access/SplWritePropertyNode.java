package com.spl.nodes.access;

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
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.runtime.SplUndefinedNameTypeErrorException;
import com.spl.runtime.SplContext;

/**
 * The node for writing a property of an object. When executed, this node:
 * <ol>
 * <li>evaluates the object expression on the left hand side of the object access operator</li>
 * <li>evaluates the property name</li>
 * <li>evaluates the value expression on the right hand side of the assignment operator</li>
 * <li>writes the named property</li>
 * <li>returns the written value</li>
 * </ol>
 */
@NodeInfo(shortName = ".=")
@NodeChild("receiverNode")
@NodeChild("nameNode")
@NodeChild("valueNode")
@ImportStatic({SplContext.class, Message.class})
public abstract class SplWritePropertyNode extends SplExpressionNode {

    @Specialization(guards = "isToneObject(receiver)")
    protected Object write(DynamicObject receiver, Object name, Object value,
                           @Cached("create()") SplWritePropertyCacheNode writeNode) {
        /**
         * The polymorphic cache node that performs the actual write. This is a separate node so
         * that it can be re-used in cases where the receiver, name, and value are not nodes but
         * already evaluated values.
         */
        writeNode.executeWrite(receiver, name, value);
        return value;
    }

    /**
     * Language interoperability: If the receiver object is a foreign value we use Truffle's interop
     * API to access the foreign data.
     */
    @Specialization(guards = "!isToneObject(receiver)")
    protected void writeForeign(TruffleObject receiver, Object name, Object value,
                                // The child node to access the foreign object
                                @Cached("WRITE.createNode()") Node foreignWriteNode) {

        try {
            /* Perform the foreign object access. */
            ForeignAccess.sendWrite(foreignWriteNode, receiver, name, value);

        } catch (UnknownIdentifierException | UnsupportedTypeException | UnsupportedMessageException e) {
            /* Foreign access was not successful. */
            throw SplUndefinedNameTypeErrorException.undefinedProperty(this, name);
        }
    }

    /**
     * When no specialization fits, the receiver is not an object (which is a type error).
     */
    @Fallback
    @SuppressWarnings("unused")
    protected void updateShape(Object r, Object name, Object value) {
        throw SplUndefinedNameTypeErrorException.undefinedProperty(this, name);
    }

}
