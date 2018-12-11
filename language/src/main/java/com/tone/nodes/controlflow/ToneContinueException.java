package com.tone.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link ToneContinueNode continue statement} and caught by the
 * {@link ToneWhileNode loop statement} or {@link ToneForNode loop statement}. Since the exception is stateless, i.e., has no instance
 * fields, we can use a {@link #SINGLETON} to avoid memory allocation during interpretation.
 */
public final class ToneContinueException extends ControlFlowException {

    public static final ToneContinueException SINGLETON = new ToneContinueException();

    private static final long serialVersionUID = 5329687983726237188L;

    /* Prevent instantiation from outside. */
    private ToneContinueException() {
    }
}
