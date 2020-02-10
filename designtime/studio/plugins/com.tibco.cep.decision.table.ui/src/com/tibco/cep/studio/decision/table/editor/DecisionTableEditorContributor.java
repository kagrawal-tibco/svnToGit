package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

public class DecisionTableEditorContributor extends MultiPageEditorActionBarContributor {

	public static final String ZOOM_IN = "com.tibco.cep.decision.table.ui.zoomin";
	public static final String ZOOM_OUT = "com.tibco.cep.decision.table.ui.zoomout";
	
	private IEditorPart activeEditorPart;
	
	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		if (activeEditorPart == part)
			return;

		activeEditorPart = part;
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					((IDecisionTableEditor) activeEditorPart)
							.getActionHandler(ActionFactory.UNDO.getId()));

			actionBars.setGlobalActionHandler(ZOOM_IN,
					((IDecisionTableEditor) activeEditorPart)
							.getActionHandler(ZOOM_IN));

			actionBars.setGlobalActionHandler(ZOOM_OUT,
					((IDecisionTableEditor) activeEditorPart)
					.getActionHandler(ZOOM_OUT));
			
			// all of these are already built in to NatTable and should not be overridden.
//			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), ((IDecisionTableEditor) activeEditorPart)
//					.getActionHandler(ActionFactory.COPY.getId()));
//
//			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), ((IDecisionTableEditor) activeEditorPart)
//					.getActionHandler(ActionFactory.PASTE.getId()));
//
//			actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), ((IDecisionTableEditor) activeEditorPart)
//					.getActionHandler(ActionFactory.SELECT_ALL.getId()));
//
//			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),
//					((IDecisionTableEditor) activeEditorPart)
//							.getActionHandler(ActionFactory.CUT.getId()));
//			
//			actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),
//					((IDecisionTableEditor) activeEditorPart)
//							.getActionHandler(ActionFactory.FIND.getId()));

			actionBars.updateActionBars();
		}
	}

	@Override
	public void setActivePage(IEditorPart activeEditor) {
		// TODO Auto-generated method stub
		
	}

}
