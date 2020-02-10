package com.tibco.cep.studio.dashboard.ui.editors.views.login;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;

/**
 *
 * @author rgupta
 */
public class LoginPage extends AbstractEntityEditorPage {

	public LoginPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

    @Override
    protected void handleOutsideElementChange(int change, LocalElement element) {
    	try {
    		//we will only worry about changes to header due image re-feactor
			if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
				//reset all the properties
				getLocalElement().refresh(element.getEObject());
				//update all the controls
				populateControl(getLocalElement());
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not refresh " + getEditorInput().getName(), e));
		}
    }


}