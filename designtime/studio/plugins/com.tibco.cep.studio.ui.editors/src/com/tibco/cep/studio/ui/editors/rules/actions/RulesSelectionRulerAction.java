package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class RulesSelectionRulerAction extends AbstractRulerActionDelegate {

	@Override
	protected IAction createAction(ITextEditor editor,
			IVerticalRulerInfo rulerInfo) {
		return new RuleSelectAnnotationRulerAction(ResourceBundle.getBundle(EditorsUIPlugin.PLUGIN_ID+".messages"), "RulesSelectAnnotationRulerAction.", editor, rulerInfo); //$NON-NLS-1$
	}

}
