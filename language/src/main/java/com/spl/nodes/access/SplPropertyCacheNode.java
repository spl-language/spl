package com.spl.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.spl.nodes.SplTypes;
import com.spl.nodes.expression.SplEqualNode;
import com.spl.nodes.runtime.SplNull;
import com.spl.runtime.SplContext;
import com.spl.runtime.SplBigNumber;
import com.spl.runtime.SplFunction;

@TypeSystemReference(SplTypes.class)
public abstract class SplPropertyCacheNode extends Node {
    protected static final int CACHE_LIMIT = 3;

    protected static boolean shapeCheck(Shape shape, DynamicObject receiver) {
        return shape != null && shape.check(receiver);
    }

    protected static Shape lookupShape(DynamicObject receiver) {
        CompilerAsserts.neverPartOfCompilation();
        assert SplContext.isSplObject(receiver);
        return receiver.getShape();
    }

    /**
     * Property names can be arbitrary Tone objects. We could call {@link Object#equals}, but that is
     * generally a bad idea and therefore discouraged in Truffle.{@link Object#equals} is a virtual
     * call that can call possibly large methods that we do not want in compiled code. For example,
     * we do not want {@link SplBigNumber#equals} in compiled code but behind a
     * {@link CompilerDirectives.TruffleBoundary ). Therfore, we check types individually. The checks are semantically
     * the same as {@link SplEqualNode }.
     * <p>
     * Note that the {@code cachedName} is always a constant during compilation. Therefore, compiled
     * code is always reduced to a single {@code if} that only checks whether the {@code name} has
     * the same type.
     *
     */
    protected static boolean namesEqual(Object cachedName, Object name) {
        if (cachedName instanceof Long && name instanceof Long) {
            return (long) cachedName == (long) name;
        } else if (cachedName instanceof SplBigNumber && name instanceof SplBigNumber) {
            return ((SplBigNumber) cachedName).equals(name);
        } else if (cachedName instanceof Boolean && name instanceof Boolean) {
            return (boolean) cachedName == (boolean) name;
        } else if (cachedName instanceof String && name instanceof String) {
            return ((String) cachedName).equals(name);
        } else if (cachedName instanceof SplFunction && name instanceof SplFunction) {
            return cachedName == name;
        } else if (cachedName instanceof SplNull && name instanceof SplNull) {
            return cachedName == name;
        } else {
            assert !cachedName.equals(name);
            return false;
        }
    }

}
