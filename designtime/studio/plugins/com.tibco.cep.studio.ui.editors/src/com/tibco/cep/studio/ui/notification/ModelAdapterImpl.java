package com.tibco.cep.studio.ui.notification;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class ModelAdapterImpl extends AdapterImpl{
	
	protected AbstractSaveableEntityEditorPart editor;
	
	/**
	 * @param editor
	 */
	public ModelAdapterImpl(AbstractSaveableEntityEditorPart editor){
		this.editor = editor;
	}

	
	protected void asyncModified(){
		Display.getDefault().asyncExec(new Runnable(){
			public void run() {
				editor.modified();
				
		}});
	}
}
