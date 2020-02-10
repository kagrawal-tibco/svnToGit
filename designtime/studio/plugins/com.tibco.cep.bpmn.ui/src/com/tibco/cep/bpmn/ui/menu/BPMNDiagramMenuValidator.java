package com.tibco.cep.bpmn.ui.menu;

import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_PASTE;
import static com.tibco.cep.diagramming.utils.DiagramUtils.isClipBoardContentsAvailable;

import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.menu.Pattern;
import com.tibco.cep.diagramming.menu.popup.AbstractMenuStateValidator;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNDiagramMenuValidator extends AbstractMenuStateValidator {

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.menu.popup.AbstractMenuStateValidator#validate(java.util.Map)
	 */
	@Override
	public void validate(Map<AbstractButton, EList<Pattern>> componentPatterns) {
		for (AbstractButton element : componentPatterns.keySet()) {
			if (element instanceof JMenuItem) {
				if (!isEditorEnabled()) {
					element.setEnabled(false);
					continue;
				}
				if (element.getActionCommand().equals(PROCESS_PASTE)) {
					element.setEnabled(isClipBoardContentsAvailable(manager));
				}
			}
		}
		
	}


	@Override
	public DiagramManager getManager() {
		return manager;
	}

	@Override
	public void setManager(DiagramManager manager) {
		this.manager = manager;
	}

	@Override
	public boolean isEditorEnabled() {
		if (!((BpmnEditor)manager.getEditor()).isEnabled()) {
			return false;
		}
		return true;
	}

}
