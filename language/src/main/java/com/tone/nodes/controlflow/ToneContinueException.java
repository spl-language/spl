package com.tone.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

public final class ToneContinueException extends ControlFlowException {

    public static final ToneContinueException SINGLETON = new ToneContinueException();

    private static final long serialVersionUID = 5329687983726237188L;

    /* Prevent instantiation from outside. */
    private ToneContinueException() {
    }
}
