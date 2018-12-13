package com.spl.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.runtime.SplNull;

/**
 * Built-in function that goes through to import a symbol from the polyglot bindings.
 */
@NodeInfo(shortName = "import")
public abstract class SplImportBuiltin extends SplBuiltinNode {

    @Node.Child
    private Node readNode = Message.READ.createNode();

    @Specialization
    public Object importSymbol(String name) {
        try {
            return ForeignAccess.sendRead(readNode, getContext().getPolyglotBindings(), name);
        } catch (UnknownIdentifierException e) {
            return SplNull.SINGLETON;
        } catch (UnsupportedMessageException e) {
            // polyglot bindings should always support reading
            CompilerDirectives.transferToInterpreter();
            throw new AssertionError(e);
        }
    }
}
