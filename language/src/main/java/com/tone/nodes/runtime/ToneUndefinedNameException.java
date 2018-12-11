package com.tone.nodes.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node;
import com.tone.ToneException;

public final class ToneUndefinedNameException extends ToneException {

    private static final long serialVersionUID = 1L;

    @TruffleBoundary
    public static ToneUndefinedNameException undefinedFunction(Node location, Object name) {
        throw new ToneUndefinedNameException("Undefined function: " + name, location);
    }

    @TruffleBoundary
    public static ToneUndefinedNameException undefinedProperty(Node location, Object name) {
        throw new ToneUndefinedNameException("Undefined property: " + name, location);
    }

    private ToneUndefinedNameException(String message, Node node) {
        super(message, node);
    }
}
