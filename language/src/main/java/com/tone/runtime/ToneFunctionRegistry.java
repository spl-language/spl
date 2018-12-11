package com.tone.runtime;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.source.Source;
import com.tone.SplLanguage;
import com.tone.nodes.runtime.FunctionsObject;
import com.tone.parser.SplParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Manages the mapping from function names to {@link ToneFunction function objects}.
 */
public final class ToneFunctionRegistry {

    private final SplLanguage language;
    private final FunctionsObject functionsObject = new FunctionsObject();

    public ToneFunctionRegistry(SplLanguage language) {
        this.language = language;
    }

    /**
     * Returns the canonical {@link ToneFunction} object for the given name. If it does not exist yet,
     * it is created.
     */
    public ToneFunction lookup(String name, boolean createIfNotPresent) {
        ToneFunction result = functionsObject.functions.get(name);
        if (result == null && createIfNotPresent) {
            result = new ToneFunction(language, name);
            functionsObject.functions.put(name, result);
        }
        return result;
    }

    /**
     * Associates the {@link ToneFunction} with the given name with the given implementation root
     * node. If the function did not exist before, it defines the function. If the function existed
     * before, it redefines the function and the old implementation is discarded.
     */
    public ToneFunction register(String name, RootCallTarget callTarget) {
        ToneFunction function = lookup(name, true);
        function.setCallTarget(callTarget);
        return function;
    }

    public void register(Map<String, RootCallTarget> newFunctions) {
        for (Map.Entry<String, RootCallTarget> entry : newFunctions.entrySet()) {
            register(entry.getKey(), entry.getValue());
        }
    }

    public void register(Source newFunctions) {
        register(SplParser.parseSpl(language, newFunctions));
    }

    public ToneFunction getFunction(String name) {
        return functionsObject.functions.get(name);
    }

    /**
     * Returns the sorted list of all functions, for printing purposes only.
     */
    public List<ToneFunction> getFunctions() {
        List<ToneFunction> result = new ArrayList<>(functionsObject.functions.values());
        Collections.sort(result, new Comparator<ToneFunction>() {
            public int compare(ToneFunction f1, ToneFunction f2) {
                return f1.toString().compareTo(f2.toString());
            }
        });
        return result;
    }

    public TruffleObject getFunctionsObject() {
        return functionsObject;
    }

}
