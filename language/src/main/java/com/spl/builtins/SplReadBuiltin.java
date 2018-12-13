package com.spl.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.SplLanguage;
import com.spl.common.SplType;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SplExpressionNode;
import com.spl.runtime.SplContext;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Builtin function that reads a String from the {@link SplContext#getInput() standard input}.
 */
@NodeInfo(shortName = "read")
@NodeField(name = "splType", type = SplType.class)
public abstract class SplReadBuiltin extends SplExpressionNode {

    protected abstract SplType getSplType();

    @Specialization
    public Object readString() {
        Object result;
        String readLn = doRead(getContext().getInput());
        if (readLn == null) {
            /*
             * We do not have a sophisticated end of file handling, so returning an empty string is
             * a reasonable alternative. Note that the Java null value should never be used, since
             * it can interfere with the specialization logic in generated source code.
             */
            readLn = "";
        }
        switch (getSplType()) {
            case INT:
                result = Long.parseLong(readLn);
                break;
            case BOOL:
                result = Boolean.parseBoolean(readLn);
                break;
            default:
                result = readLn;
        }
        return result;
    }

    @TruffleBoundary
    private String doRead(BufferedReader in) {
        try {
            return in.readLine();
        } catch (IOException ex) {
            throw new SplTypeErrorException(ex.getMessage(), this);
        }
    }

    private final SplContext getContext() {
        return getRootNode().getLanguage(SplLanguage.class).getContextReference().get();
    }
}
