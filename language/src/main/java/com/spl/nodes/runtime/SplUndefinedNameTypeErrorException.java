package com.spl.nodes.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node;
import com.spl.exceptions.SplTypeErrorException;

public final class SplUndefinedNameTypeErrorException extends SplTypeErrorException {

    private static final long serialVersionUID = 1L;

    @TruffleBoundary
    public static SplUndefinedNameTypeErrorException undefinedFunction(Node location, Object name) {
        throw new SplUndefinedNameTypeErrorException("Undefined function: " + name, location);
    }

    @TruffleBoundary
    public static SplUndefinedNameTypeErrorException undefinedProperty(Node location, Object name) {
        throw new SplUndefinedNameTypeErrorException("Undefined property: " + name, location);
    }

    private SplUndefinedNameTypeErrorException(String message, Node node) {
        super(message, node);
    }
}
