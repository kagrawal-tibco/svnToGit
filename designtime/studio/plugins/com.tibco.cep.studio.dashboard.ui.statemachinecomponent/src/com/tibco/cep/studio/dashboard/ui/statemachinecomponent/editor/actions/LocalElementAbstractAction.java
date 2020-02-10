package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor;

@SuppressWarnings("serial")
public abstract class LocalElementAbstractAction extends AbstractAction {
	
	public LocalElementAbstractAction() {
		super();
	}

	public LocalElementAbstractAction(String name, Icon icon) {
		super(name, icon);
	}

	public LocalElementAbstractAction(String name) {
		super(name);
	}

	public final LocalElement getTargetElement() {
		IEditorPart activeEditor = getActiveEditor();
		if (activeEditor instanceof AbstractDBFormEditor) {
			return computeTargetElement((AbstractDBFormEditor) activeEditor);
		}
		return null;
	}

	protected LocalElement computeTargetElement(AbstractDBFormEditor editor) {
		return editor.getLocalElement();
	}
	
	protected IEditorPart getActiveEditor(){
		if (Display.getCurrent() != null){
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (activePage != null) {
				return activePage.getActiveEditor();
			}
			return null;
		}
		final IEditorPart[] activeEditors = new IEditorPart[1];
		Display.getDefault().syncExec(new Runnable(){

			@Override
			public void run() {
				activeEditors[0] = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			}
			
		});
		return activeEditors[0];
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		Display.getDefault().asyncExec(new Runnable(){
	
			@Override
			public void run() {
				LocalElement currTargetElement = getTargetElement();
//				System.out.println("["+getValue(NAME)+"].actionPerformed("+e+") on "+currTargetElement);				
				LocalElementAbstractAction.this.run(currTargetElement, e);
			}
			
		});
	}

	protected abstract void run(LocalElement targetElement, ActionEvent e);
}
