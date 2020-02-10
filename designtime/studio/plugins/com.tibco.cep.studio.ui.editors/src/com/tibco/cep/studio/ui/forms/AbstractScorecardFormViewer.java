package com.tibco.cep.studio.ui.forms;

import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractScorecardFormViewer extends AbstractEntityFormViewer {
	
	protected Scorecard scorecard;
	
	protected void createPropertiesColumnList(){
		columnNames.add("Name");
		columnNames.add("Type");
		columnNames.add(Messages.getString("concept.property.Multiple"));
		columnNames.add(Messages.getString("concept.property.Policy"));
		columnNames.add(Messages.getString("concept.property.History"));
		columnNames.add(Messages.getString("concept.property.Domain"));
	}

}
