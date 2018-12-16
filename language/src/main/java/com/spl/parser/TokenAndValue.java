package com.spl.parser;

import com.spl.nodes.SplExpressionNode;
import org.antlr.v4.runtime.Token;

public class TokenAndValue {

    private Token token;
    private SplExpressionNode splExpressionNode;

    public TokenAndValue(Token token) {
        this.token = token;
    }

    public void setSplExpressionNode(SplExpressionNode splExpressionNode) {
        this.splExpressionNode = splExpressionNode;
    }

    public Token getToken() {
        return token;
    }

    public SplExpressionNode getSplExpressionNode() {
        return splExpressionNode;
    }
}
