package com.tone.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link ToneBreakNode break statement} and caught by the {@link ToneWhileNode
 * loop statement}. Since the exception is stateless, i.e., has no instance fields, we can use a
 * {@link #SINGLETON} to avoid memory allocation during interpretation.
 */
public final class ToneBreakException extends ControlFlowException {

    public static final ToneBreakException SINGLETON = new ToneBreakException();

    private static final long serialVersionUID = -91013036379258890L;

    /* Prevent instantiation from outside. */
    private ToneBreakException() {
    }
}
