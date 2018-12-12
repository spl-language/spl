package com.tone.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.SplLanguage;
import com.tone.ToneException;
import com.tone.nodes.ToneExpressionNode;
import com.tone.runtime.SplContext;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Builtin function that reads a String from the {@link SplContext#getInput() standard input}.
 */
@NodeInfo(shortName = "read")
public abstract class ToneReadBuiltin extends ToneExpressionNode {

    @Specialization
    public String read() {
        SplContext context = getRootNode().getLanguage(SplLanguage.class).getContextReference().get();

        String result = doRead(context.getInput());
        if (result == null) {
            /*
             * We do not have a sophisticated end of file handling, so returning an empty string is
             * a reasonable alternative. Note that the Java null value should never be used, since
             * it can interfere with the specialization logic in generated source code.
             */
            result = "";
        }
        return result;
    }

    @TruffleBoundary
    private String doRead(BufferedReader in) {
        try {
            return in.readLine();
        } catch (IOException ex) {
            throw new ToneException(ex.getMessage(), this);
        }
    }
}
