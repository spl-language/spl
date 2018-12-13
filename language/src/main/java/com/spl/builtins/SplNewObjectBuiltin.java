package com.spl.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.runtime.SplContext;

/**
 * Built-in function to create a new object. Objects in Tone are simply made up of name/value pairs.
 */
@NodeInfo(shortName = "new")
public abstract class SplNewObjectBuiltin extends SplBuiltinNode {

    @CompilationFinal
    SplContext context;

    @Specialization
    public Object newObject() {
        if (context != getContext()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            context = getContext();
        }
        return context.createObject();
    }
}
