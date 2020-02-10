package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleTemplate;



public class RuleTemplateAdapter<R extends com.tibco.cep.designtime.core.model.rule.RuleTemplate>
		extends RuleAdapter<R>
		implements RuleTemplate {

	public RuleTemplateAdapter(
			R adapted,
			Ontology emfOntology) {
		
		super(adapted, emfOntology);
	}

	@Override
	public String getBindingsText() { //TODO
		final StringBuffer sb = new StringBuffer();
		for (final Binding b : this.adapted.getBindings()) {
			sb.append(b.getType()).append(" ").append(b.getIdName());
			String s = b.getExpression();
			if (null != s) {
				sb.append(" = ").append(s);
			}
			s = b.getDomainModelPath();
			if (null != s) {
				sb.append(" (").append(s).append(")");
			}
			sb.append(";\n");
		}
		return sb.toString();		
	}

	@Override
	public String getViewsText() {
		final StringBuffer sb = new StringBuffer();
		for (final String view : this.adapted.getViews()) {
			sb.append(view).append(";\n");
		}
		return sb.toString();		
	}

	
}
