package com.spl.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

import java.io.File;

/**
 * Батьківський клас для всіх вершин. Клас {@link VirtualFrame} надає доступ до
 * локальних змінних.
 */
@NodeInfo(language = "Spl", description = "The abstract base node for all Tone statements")
@GenerateWrapper
@ReportPolymorphism
public abstract class SplStatementNode extends Node implements InstrumentableNode {

    private static final int NO_SOURCE = -1;
    private static final int UNAVAILABLE_SOURCE = -2;

    private int sourceCharIndex = NO_SOURCE;
    private int sourceLength;

    private boolean hasStatementTag;
    private boolean hasRootTag;

    /**
     * Створення вихідного розділу можна здійснити ліниво (lazily), переглянувши джерело кореневого вузла
     * а потім створити об'єкт вихідного розділу, використовуючи індекси, що зберігаються в вузлі. Це уникне
     * небажане створення об'єктів вихідного розділу під час аналізу та створює їх лише тоді, коли вони реально
     * потрібні.
     *
     * Більше деталей {@link InstrumentableNode}.
     */
    @Override
    @TruffleBoundary
    public final SourceSection getSourceSection() {
        if (sourceCharIndex == NO_SOURCE) {
            // AST node without source
            return null;
        }
        RootNode rootNode = getRootNode();
        if (rootNode == null) {
            // not yet adopted yet
            return null;
        }
        SourceSection rootSourceSection = rootNode.getSourceSection();
        if (rootSourceSection == null) {
            return null;
        }
        Source source = rootSourceSection.getSource();
        if (sourceCharIndex == UNAVAILABLE_SOURCE) {
            return source.createUnavailableSection();
        } else {
            return source.createSection(sourceCharIndex, sourceLength);
        }
    }

    public final boolean hasSource() {
        return sourceCharIndex != NO_SOURCE;
    }

    public final boolean isInstrumentable() {
        return hasSource();
    }

    public final int getSourceCharIndex() {
        return sourceCharIndex;
    }

    public final int getSourceEndIndex() {
        return sourceCharIndex + sourceLength;
    }

    public final int getSourceLength() {
        return sourceLength;
    }

    // invoked by the parser to set the source
    public final void setSourceSection(int charIndex, int length) {
        assert sourceCharIndex == NO_SOURCE : "source must only be set once";
        if (charIndex < 0) {
            throw new IllegalArgumentException("charIndex < 0");
        } else if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        this.sourceCharIndex = charIndex;
        this.sourceLength = length;
    }

    public final void setUnavailableSourceSection() {
        assert sourceCharIndex == NO_SOURCE : "source must only be set once";
        this.sourceCharIndex = UNAVAILABLE_SOURCE;
    }

    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == StandardTags.StatementTag.class) {
            return hasStatementTag;
        } else if (tag == StandardTags.RootTag.class) {
            return hasRootTag;
        }
        return false;
    }

    public WrapperNode createWrapper(ProbeNode probe) {
        return new SplStatementNodeWrapper(this, probe);
    }

    /**
     * Виконати цю частину без повернення типу
     */
    public abstract void executeVoid(VirtualFrame frame);

    /**
     * Мітка для того щоб знати чи можна використоувавити цю вершу для того щоб зупинити налагоджування
     */
    public final void addStatementTag() {
        hasStatementTag = true;
    }

    /**
     * Мітка для того щоб описати що це коренева вершина
     */
    public final void addRootTag() {
        hasRootTag = true;
    }

    @Override
    public String toString() {
        return formatSourceSection(this);
    }

    /**
     * Форматування вихідного розділу вузла в читабельній формі.
     *
     * @param node the node to format.
     * @return a formatted source section string
     */
    public static String formatSourceSection(Node node) {
        if (node == null) {
            return "<unknown>";
        }
        SourceSection section = node.getSourceSection();
        boolean estimated = false;
        if (section == null) {
            section = node.getEncapsulatingSourceSection();
            estimated = true;
        }

        if (section == null || section.getSource() == null) {
            return "<unknown source>";
        } else {
            String sourceName = new File(section.getSource().getName()).getName();
            int startLine = section.getStartLine();
            return String.format("%s:%d%s", sourceName, startLine, estimated ? "~" : "");
        }
    }
}
