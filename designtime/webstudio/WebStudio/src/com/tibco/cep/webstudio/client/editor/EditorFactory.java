/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditorFactory;
import com.tibco.cep.webstudio.client.domain.DomainEditorFactory;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.process.ProcessEditorFactory;

/**
 * Factory to return a specific editor factory based on the editor type
 * 
 * @author Vikram Patil
 */
public class EditorFactory {
	/**
	 * Retrieve a specific Editor Factory based on the editor Type
	 * 
	 * @param selectedRecord
	 * @return
	 */
	public static IEditorFactory getArtifactEditorFactory(NavigatorResource selectedRecord) {
		IEditorFactory selectedEditorFactory = null;

		switch (selectedRecord.getEditorType()) {
		case RULE:
			selectedEditorFactory = RuleEditorFactory.getInstance();
			break;
		case RULEFUNCTION:
			selectedEditorFactory = RuleFunctionEditorFactory.getInstance();
			break;
		case RULETEMPLATEINSTANCE:
			selectedEditorFactory = RuleTemplateInstanceEditorFactory.getInstance();
			break;
		case CONCEPT:
			selectedEditorFactory = ConceptEditorFactory.getInstance();
			break;
		case EVENT:
			selectedEditorFactory = EventEditorFactory.getInstance();
			break;
		case SCORECARD:
			selectedEditorFactory = ScorecardEditorFactory.getInstance();
			break;
		case RULEFUNCTIONIMPL:
			selectedEditorFactory = DecisionTableEditorFactory.getInstance();
			break;
		case PROCESS:
			selectedEditorFactory = ProcessEditorFactory.getInstance();
			break;
		case DOMAIN:
			selectedEditorFactory = DomainEditorFactory.getInstance();
			break;			
		}

		return selectedEditorFactory;
	}
}
