package com.spl.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.SplLanguage;
import com.spl.nodes.SplExpressionNode;
import com.spl.runtime.SplContext;

import java.io.PrintWriter;

/**
 * Builtin function to write a value to the {@link SplContext#getOutput() standard output}. The
 * different specialization leverage the typed {@code println} methods available in Java, i.e.,
 * primitive values are printed without converting them to a {@link String} first.
 * <p>
 * Printing involves a lot of Java code, so we need to tell the optimizing system that it should not
 * unconditionally inline everything reachable from the println() method. This is done via the
 * {@link TruffleBoundary} annotations.
 */
@NodeInfo(shortName = "print")
@NodeChild("expressionToPrint")
public abstract class SplPrintBuiltin extends SplExpressionNode {

    @Specialization
    public long println(long value) {
        doPrint(getContext().getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, long value) {
        out.println(value);
    }

    @Specialization
    public boolean println(boolean value) {
        doPrint(getContext().getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, boolean value) {
        out.println(value);
    }

    @Specialization
    public String println(String value) {
        doPrint(getContext().getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, String value) {
        out.println(value);
    }

    @Specialization
    public Object println(Object value) {
        doPrint(getContext().getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, Object value) {
        out.println(value);
    }

    private final SplContext getContext() {
        return getRootNode().getLanguage(SplLanguage.class).getContextReference().get();
    }
}
