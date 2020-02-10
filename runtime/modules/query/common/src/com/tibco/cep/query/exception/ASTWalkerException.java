package com.tibco.cep.query.exception;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.util.ResourceManager;

public class ASTWalkerException extends Exception {
    private static String typekey="walker.exception";
    private CommonTree node;
    private String msg;
    private ModelContext context;

    public ASTWalkerException(CommonTree node,String msg) {
       this.node = node;
        this.msg = msg;
    }

    public ASTWalkerException(ModelContext context,String msg) {
       this.context = context;
        this.msg = msg;
    }

    public String getMessage() {
        String name;
        String message = "";
        if (null != node) {
            Integer line = Integer.valueOf(node.getLine());
            Integer pos = Integer.valueOf(node.getCharPositionInLine());
            name = node.toString();
            ResourceManager rm = ResourceManager.getInstance();
            message = rm.formatMessage(ASTWalkerException.typekey, new Object[]{name, line, pos, this.msg});
        }
        if (null != context) {
            int line =0;
            int pos =0;
            if(context instanceof QueryContext) {

                line = Integer.valueOf(((QueryContext)context).getLine());
                pos = Integer.valueOf(((QueryContext)context).getCharPosition());
            }
            name = node.toString();
            ResourceManager rm = ResourceManager.getInstance();
            message = rm.formatMessage(ASTWalkerException.typekey, new Object[]{name, line, pos, this.msg});

        }
        return message;
    }

}
