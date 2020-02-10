package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.tester.core.model.TesterResultsType;

/**
 * 
 * @author sasahoo
 *
 */
public class TestResultEditorInput extends FileEditorInput {

	private String runName;
	private String path;
	private String projectName;
	private String[] ruleSessions;
	

	/**
	 * Root for content provider
	 */
	private TesterResultsType testerResultsObject;
    
	/**
	 * @param ruleSessions 
	 * @param uri
	 * @param testerRun
	 */
	public TestResultEditorInput(IFile file, TesterResultsType testerResultsObject/*, String[] ruleSessions*/) {
		super(file);
		this.path = testerResultsObject.getRunName();
		this.runName = testerResultsObject.getRunName();;
		this.testerResultsObject = testerResultsObject;
		this.projectName = testerResultsObject.getProject();
//		this.ruleSessions = ruleSessions;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.FileEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return path;
	}

	/**
	 * @return
	 */
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

	public String getProjectName() {
		return projectName;
	}
	
	public String[] getRuleSessions() {
		return ruleSessions;
	}
}