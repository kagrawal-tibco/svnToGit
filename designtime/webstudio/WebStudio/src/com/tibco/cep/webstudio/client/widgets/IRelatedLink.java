package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * 
 * @author Vikram Patil
 */
public interface IRelatedLink {

	public IRelatedLink getPreviousLink();
	public IRelatedLink getNextLink();
	public void setPreviousLink(IRelatedLink link);
	public void setNextLink(IRelatedLink link);
	public List<SymbolInfo> getFieldList();
	
	public void update();
	public IBuilderOperator getOperator();
	public void setAccessSuffix(boolean showSuffix);
	
	public RelatedLink getModel();

}
