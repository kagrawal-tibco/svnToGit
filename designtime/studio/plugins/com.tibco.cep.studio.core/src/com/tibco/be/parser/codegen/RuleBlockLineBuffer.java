package com.tibco.be.parser.codegen;

import java.util.Iterator;

import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.Symbol;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jan 12, 2009
 * Time: 4:34:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleBlockLineBuffer extends BlockLineBuffer {
    public static String DECLARE_BLOCK = "declare";
    public static String WHEN_BLOCK = "when";
    public static String THEN_BLOCK = "then";
    protected int whenOffset = -1;
    protected int thenOffset = -1;
    protected int declareOffset = -1;
    protected boolean adjustedDeclare = false;
    protected boolean adjustedWhen = false;
    protected boolean adjustedThen = false;
	private Rule rule;

    protected RuleBlockLineBuffer(String name, String ruleSetPath) {
        super(name, ruleSetPath);
    }

    public RuleBlockLineBuffer(String name, String path, Rule rul) {
    	super(name, path);
    	this.rule = rul;
	}

	public static RuleBlockLineBuffer fromRule(Rule rul) {
        String path;
        if(rul.getRuleSet() != null) {
            path = rul.getRuleSet().getFullPath();
        } else {
            path = "";
        }
        RuleBlockLineBuffer rb = new RuleBlockLineBuffer(rul.getName(), path , rul);
      //todo put the "priority" string constant somewhere else
        rb.addAttr("priority", String.valueOf(rul.getPriority()));
        rb.addAttr("$lastmod", '"' + rul.getLastModified() + '"');
        rb.addAttr("forwardChain", String.valueOf(rul.usesForwardChaining()));

        //have to cast to linked hash map because the state machine generation
        //won't work if the rule object doesn't return a LinkedHashMap
        for (Iterator it = rul.getDeclarations().values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            rb.addDecl(symbol.getType(), symbol.getName());
        }
        rb.setWhenBlock(rul.getConditionText());
        rb.setThenBlock(rul.getActionText());
        
        return rb;
    }
    
    public static RuleBlockLineBuffer fromRuleOld(Rule rul) {
        String path;
        if(rul.getRuleSet() != null) {
            path = rul.getRuleSet().getFullPath();
        } else {
            path = "";
        }

        RuleBlockLineBuffer rb = new RuleBlockLineBuffer(rul.getName(), path);
        //todo put the "priority" string constant somewhere else
        rb.addAttr("priority", String.valueOf(rul.getPriority()));
        rb.addAttr("$lastmod", '"' + rul.getLastModified() + '"');

        //have to cast to linked hash map because the state machine generation
        //won't work if the rule object doesn't return a LinkedHashMap
        for (Iterator it = rul.getDeclarations().values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            rb.addDecl(symbol.getType(), symbol.getName());
        }
        rb.setWhenBlock(rul.getConditionText());
        rb.setThenBlock(rul.getActionText());
        return rb;
    }
    
	  public String getName() {
	  return name;
	}
	
	public void setWhenBlock(String whenBlock) {
		this.whenBlock = whenBlock;
	}

	public void setThenBlock(String thenBlock) {
		this.thenBlock = thenBlock;
	}

	public String toIndentedString() {
		LineBuffer lineBuffer = new LineBuffer();
		return toIndentedString(0, lineBuffer, false);
	}

    public String toIndentedString(boolean adjust) {
        LineBuffer lineBuffer = new LineBuffer();
        return toIndentedString(0, lineBuffer, adjust);
    }

    
    public String toIndentedString(int tablevel,LineBuffer lineBuffer,boolean adjust) {
    	CodeBlock db = rule.getDeclCodeBlock();
    	this.declareOffset = db.getStart();
    	setBlock(DECLARE_BLOCK,db.getStart(),db.getEnd());
    	CodeBlock wb = rule.getWhenCodeBlock();
    	this.whenOffset = wb.getStart();
    	setBlock(WHEN_BLOCK,wb.getStart(),wb.getEnd());
    	CodeBlock tb = rule.getThenCodeBlock();
    	this.thenOffset = tb.getStart();
    	setBlock(THEN_BLOCK,tb.getStart(),tb.getEnd());
    	return rule.getSource();
    }
    
    public String toIndentedStringOld(int tablevel,LineBuffer lineBuffer,boolean adjust) {
        String idstr;
        int start = 0;
        int end = 0;
        lineBuffer.append("rule ");
        lineBuffer.append(ModelUtils.convertPathToPackage(ruleSetPath + Folder.FOLDER_SEPARATOR_CHAR + name));
        lineBuffer.append(" {" + BRK);
        this.declareOffset = lineBuffer.getJavaLine()+1;
        lineBuffer.append(indent("declare {", 1) + BRK);
        start = lineBuffer.getJavaLine();
        idstr = indent(decls.toString(), 2);
        lineBuffer.append(idstr);
        end = lineBuffer.getJavaLine();
        if(adjust == true && this.adjustedDeclare == false ) {
            setBlock(DECLARE_BLOCK,start,end);
            adjustedDeclare = true;
        }
        lineBuffer.append(indent("}", 1) + BRK);
        this.whenOffset = lineBuffer.getJavaLine()+1;
        lineBuffer.append(indent("when {", 1) + BRK);
        start = lineBuffer.getJavaLine();
        idstr = indent(whenBlock, 2);
        lineBuffer.append(idstr);
        end = lineBuffer.getJavaLine();
        if(adjust == true && this.adjustedWhen == false ) {
            setBlock(WHEN_BLOCK, start, end);
            adjustedWhen = true;
        }
        lineBuffer.append(BRK + indent("}", 1) + BRK);
        this.thenOffset = lineBuffer.getJavaLine()+1;
        lineBuffer.append(indent("then {", 1) + BRK);
        start = lineBuffer.getJavaLine();
        idstr = indent(thenBlock, 2);
        lineBuffer.append(idstr);
        end = lineBuffer.getJavaLine();
        if(adjust == true && this.adjustedThen == false ) {
            setBlock(THEN_BLOCK,start,end);
            adjustedThen = true;
        }
        lineBuffer.append(BRK + indent("}", 1) + BRK);
        lineBuffer.append("}");
        return lineBuffer.toString();
    }

    @Override
    public String toString() {
        return toIndentedString();
    }

    public StringBuffer toStringBuffer() {
        StringBuffer ret = new StringBuffer(toLineBuffer().toString());
        return ret;
    }


    public LineBuffer toLineBuffer() {

        LineBuffer lineBuffer = new LineBuffer();
        toIndentedString(0,lineBuffer,false);
        return lineBuffer;
    }

    public int getThenOffset() {
        toIndentedString();
        return thenOffset;
    }
    
    /**
     * @deprecated
     * @return
     */
    public int getThenOffsetOld() {
        toIndentedString();
        return thenOffset;
    }

    public int getWhenOffset() {
            toIndentedString();
        return whenOffset;
    }
    /**
     * @deprecated
     * @return
     */
    public int getWhenOffsetOld() {
        toIndentedString();
        return whenOffset;
    }

    public int getDeclareOffset() {
        toIndentedString();
        return declareOffset;
    }
    
    /**
     * @deprecated
     * @return
     */
    public int getDeclareOffsetOld() {
        toIndentedString();
        return declareOffset;
    }
}
