package com.spl.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.spl.SplLanguage;
import com.spl.nodes.runtime.SplUndefinedNameTypeErrorException;
import com.spl.runtime.SplFunction;

/**
 * The initial {@link RootNode} of {@link SplFunction functions} when they are created, i.e., when
 * they are still undefined. Executing it throws an
 * {@link SplUndefinedNameTypeErrorException#undefinedFunction exception}.
 */
public class SplUndefinedFunctionRootNode extends SplRootNode {
    public SplUndefinedFunctionRootNode(SplLanguage language, String name) {
        super(language, null, null, null, name);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        throw SplUndefinedNameTypeErrorException.undefinedFunction(null, getName());
    }
}
