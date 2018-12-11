package com.tone.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.Scope;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.Env;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.Source;
import com.tone.ToneLanguage;
import com.tone.builtins.ToneBuiltinNode;
import com.tone.builtins.TonePrintLnBuiltin;
import com.tone.builtins.TonePrintLnBuiltinFactory;
import com.tone.builtins.ToneReadLnBuiltin;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneRootNode;
import com.tone.nodes.local.ToneReadArgumentNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * The run-time state of Tone during execution. The context is created by the {@link ToneLanguage}. It
 * is used, for example, by {@link ToneBuiltinNode#getContext() builtin functions}.
 * <p>
 * It would be an error to have two different context instances during the execution of one script.
 * However, if two separate scripts run in one Java VM at the same time, they have a different
 * context. Therefore, the context is not a singleton.
 */
public final class ToneContext {

    private static final Source BUILTIN_SOURCE = Source.newBuilder(ToneLanguage.ID, "", "Tone builtin").build();
    private static final Layout LAYOUT = Layout.createLayout();

    private final Env env;
    private final BufferedReader input;
    private final PrintWriter output;
    private final ToneFunctionRegistry functionRegistry;
    private final Shape emptyShape;
    private final ToneLanguage language;
    private final AllocationReporter allocationReporter;
    private final Iterable<Scope> topScopes; // Cache the top scopes

    public ToneContext(ToneLanguage language, TruffleLanguage.Env env, List<NodeFactory<? extends ToneBuiltinNode>> externalBuiltins) {
        this.env = env;
        this.input = new BufferedReader(new InputStreamReader(env.in()));
        this.output = new PrintWriter(env.out(), true);
        this.language = language;
        this.allocationReporter = env.lookup(AllocationReporter.class);
        this.functionRegistry = new ToneFunctionRegistry(language);
        this.topScopes = Collections.singleton(Scope.newBuilder("global", functionRegistry.getFunctionsObject()).build());
        installBuiltins();
        for (NodeFactory<? extends ToneBuiltinNode> builtin : externalBuiltins) {
            installBuiltin(builtin);
        }
        this.emptyShape = LAYOUT.createShape(ToneObjectType.SINGLETON);
    }

    /**
     * Return the current Truffle environment.
     */
    public Env getEnv() {
        return env;
    }

    /**
     * Returns the default input, i.e., the source for the {@link ToneReadLnBuiltin}. To allow unit
     * testing, we do not use {@link System#in} directly.
     */
    public BufferedReader getInput() {
        return input;
    }

    /**
     * The default default, i.e., the output for the {@link TonePrintLnBuiltin}. To allow unit
     * testing, we do not use {@link System#out} directly.
     */
    public PrintWriter getOutput() {
        return output;
    }

    /**
     * Returns the registry of all functions that are currently defined.
     */
    public ToneFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }

    public Iterable<Scope> getTopScopes() {
        return topScopes;
    }

    /**
     * Adds all builtin functions to the {@link ToneFunctionRegistry}. This method lists all
     * {@link ToneBuiltinNode builtin implementation classes}.
     */
    private void installBuiltins() {
//        installBuiltin(ToneReadlnBuiltinFactory.getInstance());
        installBuiltin(TonePrintLnBuiltinFactory.getInstance());
//        installBuiltin(ToneNanoTimeBuiltinFactory.getInstance());
//        installBuiltin(ToneDefineFunctionBuiltinFactory.getInstance());
//        installBuiltin(ToneStackTraceBuiltinFactory.getInstance());
//        installBuiltin(ToneHelloEqualsWorldBuiltinFactory.getInstance());
//        installBuiltin(ToneNewObjectBuiltinFactory.getInstance());
//        installBuiltin(ToneEvalBuiltinFactory.getInstance());
//        installBuiltin(ToneImportBuiltinFactory.getInstance());
//        installBuiltin(ToneGetSizeBuiltinFactory.getInstance());
//        installBuiltin(ToneHasSizeBuiltinFactory.getInstance());
//        installBuiltin(ToneIsExecutableBuiltinFactory.getInstance());
//        installBuiltin(ToneIsNullBuiltinFactory.getInstance());
    }

    public void installBuiltin(NodeFactory<? extends ToneBuiltinNode> factory) {
        /*
         * The builtin node factory is a class that is automatically generated by the Truffle DSL.
         * The signature returned by the factory reflects the signature of the @Specialization
         *
         * methods in the builtin classes.
         */
        int argumentCount = factory.getExecutionSignature().size();
        ToneExpressionNode[] argumentNodes = new ToneExpressionNode[argumentCount];
        /*
         * Builtin functions are like normal functions, i.e., the arguments are passed in as an
         * Object[] array encapsulated in ToneArguments. A ToneReadArgumentNode extracts a parameter
         * from this array.
         */
        for (int i = 0; i < argumentCount; i++) {
            argumentNodes[i] = new ToneReadArgumentNode(i);
        }
        /* Instantiate the builtin node. This node performs the actual functionality. */
        ToneBuiltinNode builtinBodyNode = factory.createNode((Object) argumentNodes);
        builtinBodyNode.addRootTag();
        /* The name of the builtin function is specified via an annotation on the node class. */
        String name = lookupNodeInfo(builtinBodyNode.getClass()).shortName();
        builtinBodyNode.setUnavailableSourceSection();

        /* Wrap the builtin in a RootNode. Truffle requires all AST to start with a RootNode. */
        ToneRootNode rootNode = new ToneRootNode(language, new FrameDescriptor(), builtinBodyNode, BUILTIN_SOURCE.createUnavailableSection(), name);

        /* Register the builtin function in our function registry. */
        getFunctionRegistry().register(name, Truffle.getRuntime().createCallTarget(rootNode));
    }

    public static NodeInfo lookupNodeInfo(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        NodeInfo info = clazz.getAnnotation(NodeInfo.class);
        if (info != null) {
            return info;
        } else {
            return lookupNodeInfo(clazz.getSuperclass());
        }
    }

    /*
     * Methods for object creation / object property access.
     */

    public AllocationReporter getAllocationReporter() {
        return allocationReporter;
    }

    /**
     * Allocate an empty object. All new objects initially have no properties. Properties are added
     * when they are first stored, i.e., the store triggers a shape change of the object.
     */
    public DynamicObject createObject() {
        DynamicObject object = null;
        allocationReporter.onEnter(null, 0, AllocationReporter.SIZE_UNKNOWN);
        object = emptyShape.newInstance();
        allocationReporter.onReturnValue(object, 0, AllocationReporter.SIZE_UNKNOWN);
        return object;
    }

    public static boolean isToneObject(TruffleObject value) {
        /*
         * LAYOUT.getType() returns a concrete implementation class, i.e., a class that is more
         * precise than the base class DynamicObject. This makes the type check faster.
         */
        return LAYOUT.getType().isInstance(value) && LAYOUT.getType().cast(value).getShape().getObjectType() == ToneObjectType.SINGLETON;
    }

    /*
     * Methods for language interoperability.
     */

    public static Object fromForeignValue(Object a) {
        if (a instanceof Long || a instanceof ToneBigNumber || a instanceof String || a instanceof Boolean) {
            return a;
        } else if (a instanceof Character) {
            return String.valueOf(a);
        } else if (a instanceof Number) {
            return fromForeignNumber(a);
        } else if (a instanceof TruffleObject) {
            return a;
        } else if (a instanceof ToneContext) {
            return a;
        }
        CompilerDirectives.transferToInterpreter();
        throw new IllegalStateException(a + " is not a Truffle value");
    }

    @TruffleBoundary
    private static long fromForeignNumber(Object a) {
        return ((Number) a).longValue();
    }

    public CallTarget parse(Source source) {
        return env.parse(source);
    }

    /**
     * Returns an object that contains bindings that were exported across all used languages. To
     * read or write from this object the {@link TruffleObject interop} API can be used.
     */
    public TruffleObject getPolyglotBindings() {
        return (TruffleObject) env.getPolyglotBindings();
    }

    public static ToneContext getCurrent() {
        return ToneLanguage.getCurrentContext();
    }
}
