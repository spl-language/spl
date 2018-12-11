package com.tone.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.tone.nodes.ToneTypes;
import com.tone.nodes.expression.ToneEqualNode;
import com.tone.nodes.runtime.ToneNull;
import com.tone.runtime.ToneBigNumber;
import com.tone.runtime.ToneContext;
import com.tone.runtime.ToneFunction;

@TypeSystemReference(ToneTypes.class)
public abstract class TonePropertyCacheNode extends Node {
    protected static final int CACHE_LIMIT = 3;

    protected static boolean shapeCheck(Shape shape, DynamicObject receiver) {
        return shape != null && shape.check(receiver);
    }

    protected static Shape lookupShape(DynamicObject receiver) {
        CompilerAsserts.neverPartOfCompilation();
        assert ToneContext.isToneObject(receiver);
        return receiver.getShape();
    }

    /**
     * Property names can be arbitrary Tone objects. We could call {@link Object#equals}, but that is
     * generally a bad idea and therefore discouraged in Truffle.{@link Object#equals} is a virtual
     * call that can call possibly large methods that we do not want in compiled code. For example,
     * we do not want {@link ToneBigNumber#equals} in compiled code but behind a
     * {@link CompilerDirectives.TruffleBoundary ). Therfore, we check types individually. The checks are semantically
     * the same as {@link ToneEqualNode }.
     * <p>
     * Note that the {@code cachedName} is always a constant during compilation. Therefore, compiled
     * code is always reduced to a single {@code if} that only checks whether the {@code name} has
     * the same type.
     *
     */
    protected static boolean namesEqual(Object cachedName, Object name) {
        if (cachedName instanceof Long && name instanceof Long) {
            return (long) cachedName == (long) name;
        } else if (cachedName instanceof ToneBigNumber && name instanceof ToneBigNumber) {
            return ((ToneBigNumber) cachedName).equals(name);
        } else if (cachedName instanceof Boolean && name instanceof Boolean) {
            return (boolean) cachedName == (boolean) name;
        } else if (cachedName instanceof String && name instanceof String) {
            return ((String) cachedName).equals(name);
        } else if (cachedName instanceof ToneFunction && name instanceof ToneFunction) {
            return cachedName == name;
        } else if (cachedName instanceof ToneNull && name instanceof ToneNull) {
            return cachedName == name;
        } else {
            assert !cachedName.equals(name);
            return false;
        }
    }

}
