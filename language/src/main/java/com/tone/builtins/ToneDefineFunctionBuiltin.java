package com.tone.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.Source;
import com.tone.ToneLanguage;

/**
 * Builtin function to define (or redefine) functions. The provided source code is parsed the same
 * way as the initial source of the script, so the same syntax applies.
 */
@NodeInfo(shortName = "defineFunction")
public abstract class ToneDefineFunctionBuiltin extends ToneBuiltinNode {

    @TruffleBoundary
    @Specialization
    public String defineFunction(String code) {
        // @formatter:off
        Source source = Source.newBuilder(ToneLanguage.ID, code, "[defineFunction]").
            build();
        // @formatter:on
        /* The same parsing code as for parsing the initial source. */
        getContext().getFunctionRegistry().register(source);

        return code;
    }
}
