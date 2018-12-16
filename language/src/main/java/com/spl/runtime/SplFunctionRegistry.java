package com.spl.runtime;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.source.Source;
import com.spl.SplLanguage;
import com.spl.nodes.runtime.FunctionsObject;
import com.spl.parser.SplParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Manages the mapping from function names to {@link SplFunction function objects}.
 */
public final class SplFunctionRegistry {

    private final SplLanguage language;
    private final FunctionsObject functionsObject = new FunctionsObject();

    public SplFunctionRegistry(SplLanguage language) {
        this.language = language;
    }

    /**
     * Returns the canonical {@link SplFunction} object for the given name. If it does not exist yet,
     * it is created.
     */
    public SplFunction lookup(String name, boolean createIfNotPresent) {
        SplFunction result = functionsObject.functions.get(name);
        if (result == null && createIfNotPresent) {
            result = new SplFunction(language, name);
            functionsObject.functions.put(name, result);
        }
        return result;
    }

    /**
     * Associates the {@link SplFunction} with the given name with the given implementation root
     * node. If the function did not exist before, it defines the function. If the function existed
     * before, it redefines the function and the old implementation is discarded.
     */
    public SplFunction register(String name, RootCallTarget callTarget) {
        SplFunction function = lookup(name, true);
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

    public SplFunction getFunction(String name) {
        return functionsObject.functions.get(name);
    }

    /**
     * Returns the sorted list of all functions, for printing purposes only.
     */
    public List<SplFunction> getFunctions() {
        List<SplFunction> result = new ArrayList<>(functionsObject.functions.values());
        Collections.sort(result, new Comparator<SplFunction>() {
            public int compare(SplFunction f1, SplFunction f2) {
                return f1.toString().compareTo(f2.toString());
            }
        });
        return result;
    }

    public TruffleObject getFunctionsObject() {
        return functionsObject;
    }

}
