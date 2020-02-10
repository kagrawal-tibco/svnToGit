/**
 * 
 */
package com.tibco.cep.studio.core.rules;

import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;

/**
 * @author pdhar
 *
 */
public class CodeBlockImpl implements CodeBlock {
	int codeStart;
	int codeEnd;

	/**
	 * @param codeStart
	 * @param codeEnd
	 */
	public CodeBlockImpl(int codeStart, int codeEnd) {
		super();
		this.codeStart = codeStart;
		this.codeEnd = codeEnd;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Compilable.CodeBlock#getCodeEnd()
	 */
	@Override
	public int getEnd() {
		return codeEnd;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Compilable.CodeBlock#getCodeStart()
	 */
	@Override
	public int getStart() {
		return codeStart;
	}

	/**
	 * @param codeStart the codeStart to set
	 */
	public void setStart(int codeStart) {
		this.codeStart = codeStart;
	}

	/**
	 * @param codeEnd the codeEnd to set
	 */
	public void setEnd(int codeEnd) {
		this.codeEnd = codeEnd;
	}
	
	
	public String toString() {
		return codeStart+":"+codeEnd;
	}


}
