package com.tibco.cep.webstudio.client.widgets;

import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;

public interface IFilterBuilder {

	public Filter getFilter();

	public AbstractRelatedLink getLastLink();
	
	public AbstractRelatedLink getPreviousLink(RelatedLink link);
	public AbstractRelatedLink getPreviousValueLink(RelatedLink link);
	
	public String[] getOperators();

	
}
