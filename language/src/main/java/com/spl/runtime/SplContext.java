package com.spl.runtime;

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
import com.spl.SplLanguage;
import com.spl.builtins.SplBuiltinNode;
import com.spl.builtins.SplPrintBuiltin;
import com.spl.builtins.SplReadBuiltin;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplRootNode;
import com.spl.nodes.local.SplReadArgumentNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * Використовується під час виконання. Контекст створено {@link SplLanguage}.
 *  * Використовується, наприклад, за допомогою вбудованих функцій {@link SplBuiltinNode # getContext ().
 *   * <p>
 *   * Було б помилкою мати два різних контексту при виконанні одного сценарію.
 *   * Однак, якщо два окремих скрипти одночасно запускаються в одній версії Java, вони мають інший
 *   * контекст Тому контекст не є синглтон.
 */
public final class SplContext {

    private static final Source BUILTIN_SOURCE = Source.newBuilder(SplLanguage.ID, "", "SPl builtin").build();
    private static final Layout LAYOUT = Layout.createLayout();

    private final Env env;
    private final BufferedReader input;
    private final PrintWriter output;
    private final SplFunctionRegistry functionRegistry;
    private final Shape emptyShape;
    private final SplLanguage language;
    private final AllocationReporter allocationReporter;
    private final Iterable<Scope> topScopes; // Cache the top scopes

    public SplContext(SplLanguage language, TruffleLanguage.Env env, List<NodeFactory<? extends SplBuiltinNode>> externalBuiltins) {
        this.env = env;
        this.input = new BufferedReader(new InputStreamReader(env.in()));
        this.output = new PrintWriter(env.out(), true);
        this.language = language;
        this.allocationReporter = env.lookup(AllocationReporter.class);
        this.functionRegistry = new SplFunctionRegistry(language);
        this.topScopes = Collections.singleton(Scope.newBuilder("global", functionRegistry.getFunctionsObject()).build());
        installBuiltins();
        for (NodeFactory<? extends SplBuiltinNode> builtin : externalBuiltins) {
            installBuiltin(builtin);
        }
        this.emptyShape = LAYOUT.createShape(SplObjectType.SINGLETON);
    }

    /**
     * Повернути поточне середовище Трюфеля.
     */
    public Env getEnv() {
        return env;
    }

    /**
     * Повертає вхід за умовчанням (потік звідки буде зчитуватись дані користувача, по замовчуваню використовуєм косоль),
     * тобто джерело для {@link SplReadBuiltin}. Щоб дозволити блок
     * тестування, ми не використовуємо {@link System#in} безпосередньо, бо для тестів необхідно зчитувати дані з файла.
     */
    public BufferedReader getInput() {
        return input;
    }

    /**
     * Повертає вихід за умовчанням (потік куди записуватись дані, по замовчуваню використовуєм косоль),
     * тобто вихід для {@link SplPrintBuiltin}. Щоб дозволити блок
     * тестування, ми не використовуємо {@link System#out} безпосередньо, бо для тестів необхідно зчитувати дані з файла.
     */
    public PrintWriter getOutput() {
        return output;
    }

    /**
     * Повертає регістр усіх функцій
     */
    public SplFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }

    public Iterable<Scope> getTopScopes() {
        return topScopes;
    }

    /**
     * Додає всі вбудовані функції до {@link SplFunctionRegistry}. Цей метод перераховує всі
     * {@link SplBuiltinNode вбудовані класи реалізації}.
     * Зараз немає вбудованих функцій, могло би бути функції print, read якби не мали спеціального синтаксису для виклику (замість дужок використовється просто прбіл).
     */
    private void installBuiltins() {
    }

    public void installBuiltin(NodeFactory<? extends SplBuiltinNode> factory) {

        /*
         * Фабрика вбудованих вузлів - це клас, який автоматично генерується Трюфельним DSL.
         * Підпис, повернений фабрикою, відображає підпис @Specialization
         *
         * методи у вбудованих класах.
         */
        int argumentCount = factory.getExecutionSignature().size();
        SplExpressionNode[] argumentNodes = new SplExpressionNode[argumentCount];

        /*
         * Builtin функції, як звичайні функції, тобто аргументи передаються як
         * Object[] масив інкапсульовано в SplArguments. SplReadArgumentNode вилучає параметр
         * з цього масиву.
         */
        for (int i = 0; i < argumentCount; i++) {
            argumentNodes[i] = new SplReadArgumentNode(i);
        }

        /* Інстантируйте вбудований вузол. Цей вузол виконує фактичну функціональність. */
        SplBuiltinNode builtinBodyNode = factory.createNode((Object) argumentNodes);
        builtinBodyNode.addRootTag();

        /* Назва вбудованої функції вказується через анотацію в класі вузлів. */
        String name = lookupNodeInfo(builtinBodyNode.getClass()).shortName();
        builtinBodyNode.setUnavailableSourceSection();

        /* Обертає вбудований в RootNode. Трюфель вимагає, щоб всі AST почали з RootNode. Тобто деякий батьківський вузло для всіх*/
        SplRootNode rootNode = new SplRootNode(language, new FrameDescriptor(), builtinBodyNode, BUILTIN_SOURCE.createUnavailableSection(), name);

        /* Реєструється вбудована функцію в нашому реєстрі функцій. */
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
     * Методи створення об'єкта / доступ до ресурсу об'єкта.
     */
    public AllocationReporter getAllocationReporter() {
        return allocationReporter;
    }

    /**
     * Виділити порожній об'єкт. Всі нові об'єкти спочатку не мають властивостей. Властивості додані
     * коли вони вперше зберігаються, тобто магазин запускає зміну форми об'єкта.
     */
    public DynamicObject createObject() {
        DynamicObject object = null;
        allocationReporter.onEnter(null, 0, AllocationReporter.SIZE_UNKNOWN);
        object = emptyShape.newInstance();
        allocationReporter.onReturnValue(object, 0, AllocationReporter.SIZE_UNKNOWN);
        return object;
    }

    public static boolean isSplObject(TruffleObject value) {

        /** LAYOUT.getType () повертає конкретний клас реалізації, тобто клас, який більше
         * точніше, ніж базовий клас DynamicObject. Це робить перевірку типу швидше.
         */
        return LAYOUT.getType().isInstance(value) && LAYOUT.getType().cast(value).getShape().getObjectType() == SplObjectType.SINGLETON;
    }

    /*
     * Методи мовної сумісності. Містить ті типи які має відповідно мова Spl.
     */
    public static Object fromForeignValue(Object a) {
        if (a instanceof Long || a instanceof SplBigNumber || a instanceof String || a instanceof Boolean) {
            return a;
        } else if (a instanceof Character) {
            return String.valueOf(a);
        } else if (a instanceof Number) {
            return fromForeignNumber(a);
        } else if (a instanceof TruffleObject) {
            return a;
        } else if (a instanceof SplContext) {
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
     * Повертає об'єкт, який містить прив'язки, які були експортовані на всі використовувані мови. До
     * читати чи писати з цього об'єкта можна використовувати API {@link TruffleObject interop}.
     */
    public TruffleObject getPolyglotBindings() {
        return (TruffleObject) env.getPolyglotBindings();
    }

    public static SplContext getCurrent() {
        return SplLanguage.getCurrentContext();
    }
}
