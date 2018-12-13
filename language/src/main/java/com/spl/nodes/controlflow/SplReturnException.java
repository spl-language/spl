package com.spl.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link SplReturnNode return statement} and caught by the
 * {@link SplFunctionBodyNode function body}. The exception transports the return value in its
 * {@link #result} field.
 */
public final class SplReturnException extends ControlFlowException {

    private static final long serialVersionUID = 4073191346281369231L;

    private final Object result;

    public SplReturnException(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
