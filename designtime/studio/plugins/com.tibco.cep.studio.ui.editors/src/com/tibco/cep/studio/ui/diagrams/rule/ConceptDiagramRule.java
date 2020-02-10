package com.tibco.cep.studio.ui.diagrams.rule;



/**
 * 
 * @author smarathe
 *
 */
abstract class ConceptDiagramRule {
	
	abstract protected boolean isAllowed();
	
	abstract protected ConceptDiagramRuleSet getRuleset();
	
	

}
