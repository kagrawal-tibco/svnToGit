package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;

/**
 * 
 * @author sasahoo
 *
 */
public class WMResultEditorInput  implements IEditorInput {

	private String runName;
	private String path;
	private String projectName;
	private String sessionName;
	private IRuleRunTarget runTarget;
	
	/**
	 * Root for content provider
	 */
	private TesterResultsType testerResultsObject;
    

	/**
	 * @param runTarget
	 * @param sessionName
	 * @param testerResultsType
	 */
	public WMResultEditorInput(IRuleRunTarget runTarget, 
			                   String sessionName, 
			                   TesterResultsType testerResultsType) {
		this.sessionName = sessionName;
		this.path = testerResultsType.getRunName();
		this.runName = testerResultsType.getRunName();;
		this.testerResultsObject = testerResultsType;
		this.projectName = testerResultsType.getProject();
		this.runTarget = runTarget;
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return path;
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return null;
	}

	public String getFullPath() {
		return path;
	}

	public String getFileName() {
		return runName;
	}

	public void setFullPath(String fullPath) {
		this.path = fullPath;
	}

	/**
	 * @return the testerResultsObject
	 */
	public final TesterResultsType getTesterResultsObject() {
		return testerResultsObject;
	}

	@Override
	public String getName() {
		return runName;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getSessionName() {
		return sessionName;
	}

	public IRuleRunTarget getRunTarget() {
		return runTarget;
	}
}