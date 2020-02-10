package com.tibco.rta.model.command.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandTreeAdaptor extends CommonTreeAdaptor {

    @Override
    public Object create(Token payload) {
        return new CommandASTNode(payload);
    }
}
