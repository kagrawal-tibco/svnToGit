package com.tibco.be.parser.codegen;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 15, 2004
 * Time: 1:28:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleBlock {
//    protected static final String BRK = CGConstants.BRK;
//    protected String name;
//    protected String ruleSetPath;
//    protected StringBuffer decls = new StringBuffer();
//    protected StringBuffer attrs = new StringBuffer();
//    protected String whenBlock = null;
//    protected String thenBlock = null;


//    public RuleBlock(String name, String ruleSetPath) {
//        this.name = name;
//        this.ruleSetPath = ruleSetPath;
//    }

    public String getRuleSetPath();
//    public String getRuleSetPath() {
//        return ruleSetPath;
//    }

//    public static RuleBlock fromRule(Rule rul) {
//        RuleBlock rb = new RuleBlock(rul.getName(), rul.getRuleSet().getName());
//        //todo put the "priority" string constant somewhere else
//        rb.addAttr("priority", String.valueOf(rul.getPriority()));
//        rb.addAttr("$lastmod", '"' + rul.getLastModified() + '"');
//
//        //have to cast to linked hash map because the state machine generation
//        //won't work if the rule object doesn't return a LinkedHashMap
//        for(Iterator it = rul.getDeclarations().values().iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            rb.addDecl(symbol.getType(), symbol.getName());
//        }
//        rb.setWhenBlock(rul.getConditionText());
//        rb.setThenBlock(rul.getActionText());
//        return rb;
//    }


    public String getName();
//    public String getName() {
//        return name;
//    }

//    public void addDecl(String type, String name) {
//        decls.append(ModelUtils.convertPathToPackage(type));
//        decls.append(" ");
//        decls.append(name);
//        decls.append(";");
//        decls.append(BRK);
//    }

//    public void addAttr(String name, String value) {
//        attrs.append(name);
//        attrs.append(" = ");
//        attrs.append(value);
//        attrs.append(";");
//        attrs.append(BRK);
//    }

//    public void setWhenBlock(String whenBlock) {
//        this.whenBlock = whenBlock;
//    }
//
//    public void setThenBlock(String thenBlock) {
//        this.thenBlock = thenBlock;
//    }

    public StringBuffer toStringBuffer();
//    public StringBuffer toStringBuffer() {
//        StringBuffer ret = new StringBuffer();
//
//        ret.append("rule ");
//        ret.append(ModelUtils.convertPathToPackage(ruleSetPath + Folder.FOLDER_SEPARATOR_CHAR + name));
//        ret.append(" {" + BRK);
//        ret.append("attribute {" + BRK);
//        ret.append(attrs);
//        ret.append("}" + BRK);
//        ret.append("declare {" + BRK);
//        ret.append(decls);
//        ret.append("}" + BRK);
//        ret.append("when {" + BRK);
//        ret.append(whenBlock);
//        ret.append(BRK + "}" + BRK);
//        ret.append("then {" + BRK);
//        ret.append(thenBlock);
//        ret.append(BRK + "}" + BRK);
//        ret.append("}");
//
//        return ret;
//    }

//    public String toString() {
//        return toStringBuffer().toString();
//    }

//    public String toIndentedString() {
//        StringBuffer ret = new StringBuffer();
//        String idstr;
//        ret.append("rule ");
//        ret.append(ModelUtils.convertPathToPackage(ruleSetPath + Folder.FOLDER_SEPARATOR_CHAR + name));
//        ret.append(" {" + BRK);
//
//        ret.append(indent("declare {",1) + BRK);
//        idstr = indent(decls.toString(), 2);
//        ret.append(idstr);
//        ret.append(indent("}",1) + BRK);
//
//        ret.append(indent("when {",1) + BRK);
//        idstr = indent(whenBlock, 2);
//        ret.append(idstr);
//        ret.append(BRK + indent("}",1) + BRK);
//
//        ret.append(indent("then {",1) + BRK);
//        idstr = indent(thenBlock, 2);
//        ret.append(idstr);
//        ret.append(BRK + indent("}",1) + BRK);
//        ret.append("}");
//
//        return ret.toString();
//    }

//    protected String indent(String src, int tabspace) {
//
//        StringBuffer buf = new StringBuffer();
//        appendSpace(buf, tabspace);
//        for (int i=0; i<src.length(); i++) {
//            char c = src.charAt(i);
//            buf.append(c);
//            if ((c == '\n') && (i+1 != src.length())){
//                appendSpace(buf,tabspace);
//            }
//        }
//        return buf.toString();
//
//    }
//
//    protected void appendSpace(StringBuffer buf, int tabspace) {
//        for (int i=0; i<tabspace; i++) {
//            buf.append("   ");
//        }
//    }


}