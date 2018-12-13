package com.spl.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;

public abstract class SplWritePropertyCacheNode extends SplPropertyCacheNode {

    public abstract void executeWrite(DynamicObject receiver, Object name, Object value);

    /**
     * Polymorphic inline cache for writing a property that already exists (no shape change is
     * necessary).
     */
    @Specialization(limit = "CACHE_LIMIT", //
                    guards = {
                                    "cachedName.equals(name)",
                                    "shapeCheck(shape, receiver)",
                                    "location != null",
                                    "canSet(location, value)"
                    }, //
                    assumptions = {
                                    "shape.getValidAssumption()"
                    })
    protected static void writeExistingPropertyCached(DynamicObject receiver, @SuppressWarnings("unused") Object name, Object value,
                                                      @SuppressWarnings("unused") @Cached("name") Object cachedName,
                                                      @Cached("lookupShape(receiver)") Shape shape,
                                                      @Cached("lookupLocation(shape, name, value)") Location location) {
        try {
            location.set(receiver, value, shape);

        } catch (IncompatibleLocationException | FinalLocationException ex) {
            /* Our guards ensure that the value can be stored, so this cannot happen. */
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Polymorphic inline cache for writing a property that does not exist yet (shape change is
     * necessary).
     */
    @Specialization(limit = "CACHE_LIMIT", //
                    guards = {
                                    "namesEqual(cachedName, name)",
                                    "shapeCheck(oldShape, receiver)",
                                    "oldLocation == null",
                                    "canStore(newLocation, value)"
                    }, //
                    assumptions = {
                                    "oldShape.getValidAssumption()",
                                    "newShape.getValidAssumption()"
                    })
    @SuppressWarnings("unused")
    protected static void writeNewPropertyCached(DynamicObject receiver, Object name, Object value,
                                                 @Cached("name") Object cachedName,
                                                 @Cached("lookupShape(receiver)") Shape oldShape,
                                                 @Cached("lookupLocation(oldShape, name, value)") Location oldLocation,
                                                 @Cached("defineProperty(oldShape, name, value)") Shape newShape,
                                                 @Cached("lookupLocation(newShape, name)") Location newLocation) {
        try {
            newLocation.set(receiver, value, oldShape, newShape);

        } catch (IncompatibleLocationException ex) {
            /* Our guards ensure that the value can be stored, so this cannot happen. */
            throw new IllegalStateException(ex);
        }
    }

    /** Try to find the given property in the shape. */
    protected static Location lookupLocation(Shape shape, Object name) {
        CompilerAsserts.neverPartOfCompilation();

        Property property = shape.getProperty(name);
        if (property == null) {
            /* Property does not exist yet, so a shape change is necessary. */
            return null;
        }

        return property.getLocation();
    }

    /**
     * Try to find the given property in the shape. Also returns null when the value cannot be store
     * into the location.
     */
    protected static Location lookupLocation(Shape shape, Object name, Object value) {
        Location location = lookupLocation(shape, name);
        if (location == null || !location.canSet(value)) {
            /* Existing property has an incompatible type, so a shape change is necessary. */
            return null;
        }

        return location;
    }

    protected static Shape defineProperty(Shape oldShape, Object name, Object value) {
        return oldShape.defineProperty(name, value, 0);
    }

    /**
     * There is a subtle difference between {@link Location#canSet} and {@link Location#canStore}.
     * We need {@link Location#canSet} for the guard of {@link #writeExistingPropertyCached} because
     * there we call {@link Location#set}. We use the more relaxed {@link Location#canStore} for the
     * guard of {@link SplWritePropertyCacheNode#writeNewPropertyCached} because there we perform a
     * shape transition, i.e., we are not actually setting the value of the new location - we only
     * transition to this location as part of the shape change.
     */
    protected static boolean canSet(Location location, Object value) {
        return location.canSet(value);
    }

    /** See {@link #canSet} for the difference between the two methods. */
    protected static boolean canStore(Location location, Object value) {
        return location.canStore(value);
    }

    /**
     * The generic case is used if the number of shapes accessed overflows the limit of the
     * polymorphic inline cache.
     */
    @TruffleBoundary
    @Specialization(replaces = {"writeExistingPropertyCached", "writeNewPropertyCached"}, guards = {"receiver.getShape().isValid()"})
    protected static void writeUncached(DynamicObject receiver, Object name, Object value) {
        receiver.define(name, value);
    }

    @TruffleBoundary
    @Specialization(guards = {"!receiver.getShape().isValid()"})
    protected void updateShape(DynamicObject receiver, Object name, Object value) {
        /*
         * Slow path that we do not handle in compiled code. But no need to invalidate compiled
         * code.
         */
        CompilerDirectives.transferToInterpreter();
        receiver.updateShape();
        writeUncached(receiver, name, value);
    }

    public static SplWritePropertyCacheNode create() {
        return SplWritePropertyCacheNodeGen.create();
    }

}
