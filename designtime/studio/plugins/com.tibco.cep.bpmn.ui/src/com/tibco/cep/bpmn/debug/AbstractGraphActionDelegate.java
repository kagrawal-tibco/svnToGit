package com.tibco.cep.bpmn.debug;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.texteditor.IUpdate;

import com.tibco.cep.bpmn.ui.editor.IGraphEditor;
import com.tibco.cep.bpmn.ui.editor.IGraphInfo;
import com.tibco.cep.bpmn.ui.graph.tool.IGraphMenuListener;
import com.tibco.cep.bpmn.ui.graph.tool.IGraphMouseListener;
import com.tibco.cep.bpmn.ui.graph.tool.IGraphSelectTool;

public abstract class AbstractGraphActionDelegate  extends ActionDelegate implements IEditorActionDelegate,IGraphMenuListener,IGraphMouseListener {
	
	/** The editor. */
	private IGraphEditor fEditor;
	/** The action calling the action delegate. */
	private IAction fCallerAction;
	/** The underlying action. */
	private IAction fAction;

	/**
	 * The factory method creating the underlying action.
	 *
	 * @param editor the editor the action to be created will work on
	 * @param rulerInfo the vertical ruler the action to be created will work on
	 * @return the created action
	 */
	protected abstract IAction createAction(IGraphEditor editor,IGraphInfo info);


	/*
	 * @see IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction callerAction, IEditorPart targetEditor) {
		if (fEditor != null) {
			IGraphEditor graphEditor= (IGraphEditor) fEditor.getAdapter(IGraphEditor.class);
			if (graphEditor != null) {
				IGraphSelectTool tool= (IGraphSelectTool) graphEditor.getAdapter(IGraphSelectTool.class);
				Control control= tool.getControl();
				if (tool != null && !control.isDisposed())
					tool.removeContextMenuListener(this);
			}			
		}

		fEditor= (IGraphEditor)(targetEditor == null ? null : targetEditor.getAdapter(IGraphEditor.class));
		fCallerAction= callerAction;
		fAction= null;

		if (fEditor != null) {
			IGraphEditor graphEditor= (IGraphEditor) fEditor.getAdapter(IGraphEditor.class);
			if(graphEditor != null) {
				IGraphSelectTool tool= (IGraphSelectTool) graphEditor.getAdapter(IGraphSelectTool.class);
				if(tool != null){
					
					IGraphInfo info = (IGraphInfo) tool.getAdapter(IGraphInfo.class);
					if(info != null) {
						fAction= createAction(fEditor,info);
						update();				
					}
				}
			}
		}
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction callerAction) {
		if (fAction != null)
			fAction.run();
	}
	
	

	/*
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 * @since 3.2
	 */
	public void runWithEvent(IAction action, Event event) {
		if (fAction != null)
			fAction.runWithEvent(event);
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		/*
		 * This is a graph action - don't update on selection.
		 */
//		update();
	}

	/**
	 * Updates to the current state.
	 */
	private void update() {
		if (fAction instanceof IUpdate) {
			((IUpdate) fAction).update();
			if (fCallerAction != null) {
				fCallerAction.setText(fAction.getText());
				fCallerAction.setEnabled(fAction.isEnabled());
			}
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMousePressed(MouseEvent event) {
		update();
		
	}
	@Override
	public void onMouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMouseWheelMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void menuAboutToShow(IMenuManager manager) {
		update();
	}

	

}
