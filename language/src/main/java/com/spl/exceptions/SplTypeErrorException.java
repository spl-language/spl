package com.spl.exceptions;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.SourceSection;
import com.spl.nodes.runtime.SplNull;
import com.spl.runtime.ToneBigNumber;
import com.spl.runtime.SplContext;

/**
 * Tone does not need a sophisticated error checking and reporting mechanism, so all unexpected
 * conditions just abort execution. This exception class is used when we abort from within the Tone
 * implementation.
 */
public class SplTypeErrorException extends RuntimeException implements TruffleException {

    private static final long serialVersionUID = -6799734410727348507L;

    private final Node location;

    @TruffleBoundary
    public SplTypeErrorException(String message, Node location) {
        super(message);
        this.location = location;
    }

    @SuppressWarnings("sync-override")
    @Override
    public Throwable fillInStackTrace() {
        return null;
    }

    public Node getLocation() {
        return location;
    }

    /**
     * Provides a user-readable message for run-time type errors. Tone is strongly typed, i.e., there
     * are no automatic type conversions of values.
     */
    @TruffleBoundary
    public static SplTypeErrorException typeError(Node operation, Object... values) {
        StringBuilder result = new StringBuilder();
        result.append("Type error");

        if (operation != null) {
            SourceSection ss = operation.getEncapsulatingSourceSection();
            if (ss != null && ss.isAvailable()) {
                result.append(" at ").append(ss.getSource().getName()).append(" line ").append(ss.getStartLine()).append(" col ").append(ss.getStartColumn());
            }
        }

        result.append(": operation");
        if (operation != null) {
            NodeInfo nodeInfo = SplContext.lookupNodeInfo(operation.getClass());
            if (nodeInfo != null) {
                result.append(" \"").append(nodeInfo.shortName()).append("\"");
            }
        }

        result.append(" not defined for");

        String sep = " ";
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result.append(sep);
            sep = ", ";
            if (value instanceof Long || value instanceof ToneBigNumber) {
                result.append("Number ").append(value);
            } else if (value instanceof Boolean) {
                result.append("Boolean ").append(value);
            } else if (value instanceof String) {
                result.append("String \"").append(value).append("\"");
//            } else if (value instanceof ToneFunction) {
//                result.append("Function ").append(value);
            } else if (value == SplNull.SINGLETON) {
                result.append("NULL");
            } else if (value == null) {
                // value is not evaluated because of short circuit evaluation
                result.append("ANY");
            } else {
                result.append(value);
            }
        }
        return new SplTypeErrorException(result.toString(), operation);
    }

}
