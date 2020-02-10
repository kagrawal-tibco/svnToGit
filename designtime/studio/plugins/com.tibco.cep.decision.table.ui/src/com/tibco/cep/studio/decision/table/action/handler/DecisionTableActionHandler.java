package com.tibco.cep.studio.decision.table.action.handler;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.studio.decision.table.editor.DecisionTableEditorContributor;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.ZoomAction;

public class DecisionTableActionHandler extends Action{

	private IDecisionTableEditor decisionTableEditor;
	
	public DecisionTableActionHandler(String id, IDecisionTableEditor decisionTableEditor) {
		super();
		setId(id);
		setActionDefinitionId(id);
		this.decisionTableEditor = decisionTableEditor;
	}

	@Override
	public void run() {
		decisionTableEditor.getDecisionTableDesignViewer().getDecisionTable().commitAndCloseActiveCellEditor();
		if (ActionFactory.UNDO.getId().equals(getId())) {
			executeUndo();
		}
		if (DecisionTableEditorContributor.ZOOM_IN.equals(getId())) {
			new ZoomAction(true, decisionTableEditor.getDtDesignViewer()).run(decisionTableEditor.getDtDesignViewer().getDecisionTable(), null);
		}
		if (DecisionTableEditorContributor.ZOOM_OUT.equals(getId())) {
			new ZoomAction(false, decisionTableEditor.getDtDesignViewer()).run(decisionTableEditor.getDtDesignViewer().getDecisionTable(), null);
		}
	}

	private void executeUndo() {
		if(decisionTableEditor.getTable() != null){
			String project = decisionTableEditor.getTable().getOwnerProjectName();
			String tablePath = decisionTableEditor.getTable().getPath();
			CommandFacade.getInstance().undo(project, tablePath);
		}
	}

    public IDecisionTableEditor getDecisionTableEditor() {
        return decisionTableEditor;
    }

    public void setDecisionTableEditor(IDecisionTableEditor decisionTableEditor) {
        this.decisionTableEditor = decisionTableEditor;
    }
	
	
}
