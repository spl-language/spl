package com.tone.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link ToneReturnNode return statement} and caught by the
 * {@link ToneFunctionBodyNode function body}. The exception transports the return value in its
 * {@link #result} field.
 */
public final class ToneReturnException extends ControlFlowException {

    private static final long serialVersionUID = 4073191346281369231L;

    private final Object result;

    public ToneReturnException(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
