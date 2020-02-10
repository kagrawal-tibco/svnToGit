package com.tibco.cep.studio.ui.forms;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;


public abstract class AbstractEventFormViewer extends AbstractEntityFormViewer{
	
	protected SimpleEvent event;
	public static Font declfont = new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL);

	/**
     * @param container
     */
    public void createPartControl(Composite container) {
       
    	super.createPartControl(container,Messages.getString("event.editor.title")+ " " +event.getName() ,
    			/*EntityImages.getImage(EntityImages.EVENT)*/EditorsUIPlugin.getDefault().getImage("icons/event.png"));
        
        diagramAction = new org.eclipse.jface.action.Action("diagram", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
            public void run() {
//            	if(isChecked()){
            		IWorkbenchPage page =editor.getEditorSite().getWorkbenchWindow().getActivePage();
            		IProject project = editor.getProject();
            		IFile file = project.getFile(project.getName()+".eventview");
            		EntityDiagramEditorInput input = new EntityDiagramEditorInput(file,project);
            		input.setSelectedEntity(event);
            		try {
            			page.openEditor(input, EventDiagramEditor.ID);
            		} catch (PartInitException e) {
            			e.printStackTrace();
            		}
            		getForm().reflow(true);
//            	}
            }
        };
        diagramAction.setChecked(false);
        diagramAction.setToolTipText(Messages.getString("event.diagram.tooltip"));
        diagramAction.setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/eventview.png"));
        getForm().getToolBarManager().add(diagramAction);
        getForm().updateToolBar();

        dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
        getForm().getToolBarManager().add(dependencyDiagramAction);
        sequenceDiagramAction =  EditorUtils.createSequenceDiagramAction(editor, getForm(), editor.getProject());
        getForm().getToolBarManager().add(sequenceDiagramAction);
        
        super.createToolBarActions();
    }

    protected void createPropertiesColumnList(){
    	columnNames.add("Name");
    	columnNames.add("Type");
    	columnNames.add("Domain");
   }
    
	public SimpleEvent getEvent() {
		return event;
	}

	public void setEvent(SimpleEvent event) {
		this.event = event;
	}

}