package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.model.RuleNode;

public class RuleSetRulesLabelProvider extends EntityLabelProvider {

	public Image getImage(Object element) {
		if (element instanceof RuleNode) {
			return StudioUIPlugin.getDefault().getImage("icons/rules.png");
		}
		return super.getImage(element);
	}

}
