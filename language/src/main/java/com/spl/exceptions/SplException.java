package com.spl.exceptions;

import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.nodes.Node;

public class SplException extends RuntimeException implements TruffleException {

    private final Node location;

    public SplException(String message, Node location) {
        super(message);
        this.location = location;
    }

    public SplException(String message) {
        super(message);
        this.location = null;
    }

    public Node getLocation() {
        return location;
    }
}
