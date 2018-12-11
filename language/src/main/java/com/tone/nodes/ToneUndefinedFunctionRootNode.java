package com.tone.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.tone.SplLanguage;
import com.tone.nodes.runtime.ToneUndefinedNameException;
import com.tone.runtime.ToneFunction;

/**
 * The initial {@link RootNode} of {@link ToneFunction functions} when they are created, i.e., when
 * they are still undefined. Executing it throws an
 * {@link ToneUndefinedNameException#undefinedFunction exception}.
 */
public class ToneUndefinedFunctionRootNode extends ToneRootNode {
    public ToneUndefinedFunctionRootNode(SplLanguage language, String name) {
        super(language, null, null, null, name);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        throw ToneUndefinedNameException.undefinedFunction(null, getName());
    }
}
