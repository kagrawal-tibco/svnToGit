package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.RuleFunction;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jan 14, 2009
 * Time: 3:20:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleFunctionLineBuffer extends RuleBlockLineBuffer {

    private RuleFunction ruleFunction;

	protected RuleFunctionLineBuffer(String name, String ruleSetPath) {
        super(name, ruleSetPath);
    }

    public RuleFunctionLineBuffer(String name, String fullPath, RuleFunction fn) {
    	super(name, fullPath);
    	this.ruleFunction = fn;
	}

	public static RuleFunctionLineBuffer fromRuleFunction(RuleFunction fn) {
        RuleFunctionLineBuffer rb = new RuleFunctionLineBuffer(fn.getName(), fn.getFullPath(),fn);
        rb.setThenBlock(fn.getBody());
        return rb;
    }
	
	/**
	 * @deprecated
	 * @param fn
	 * @return
	 */
    public static RuleFunctionLineBuffer fromRuleFunctionOld(RuleFunction fn) {
        RuleFunctionLineBuffer rb = new RuleFunctionLineBuffer(fn.getName(), fn.getFullPath());
        rb.setThenBlock(fn.getBody());
        rb.addAttr("validity", fn.getValidity().getName());
        return rb;
    }


    public String toIndentedString() {
        LineBuffer lineBuffer = new LineBuffer();
        return toIndentedString(0, lineBuffer, false);
    }

    public String toIndentedString(boolean adjust) {
        LineBuffer lineBuffer = new LineBuffer();
        return toIndentedString(0, lineBuffer, adjust);
    }


    public String toIndentedString(int tablevel, LineBuffer lineBuffer, boolean adjust) {
    	CodeBlock scopeBlock = ruleFunction.getScopeCodeBlock();
    	setBlock(DECLARE_BLOCK,scopeBlock.getStart(),scopeBlock.getEnd());
    	setBlock(WHEN_BLOCK,-1,-1);
    	CodeBlock bodyBlock = ruleFunction.getBodyCodeBlock();
    	this.thenOffset = bodyBlock.getStart();
    	setBlock(THEN_BLOCK,bodyBlock.getStart(),bodyBlock.getEnd());
    	return ruleFunction.getSource();
    }
    
    /**
     * @deprecated
     */
    public String toIndentedStringOld(int tablevel, LineBuffer lineBuffer, boolean adjust) {
//      lineBuffer.append("rulefunction ");
//      lineBuffer.append( name);
//      lineBuffer.append(" {" + BRK);
      int start = lineBuffer.getJavaLine();
      thenOffset = lineBuffer.getJavaLine();
      lineBuffer.append(indent(thenBlock, 1));
      int end = lineBuffer.getJavaLine();
      if (adjust == true && this.adjustedThen == false) {
          setBlock(THEN_BLOCK, start, end);
          adjustedThen = true;
      }
//      lineBuffer.append(BRK + "}");
      return lineBuffer.toString();
  }

    @Override
    public LineBuffer toLineBuffer() {
        LineBuffer lineBuffer = new LineBuffer();
        toIndentedString(0,lineBuffer,false);
        return lineBuffer;
    }

    @Override
    public String toString() {
        return toIndentedString();
    }
}
