package com.tibco.cep.decision.table.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.LegacyDecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.RuleFunction;

public class DefaultDecisionTableEditorInput extends AbstractDecisionTableEditorInput {

	private int modelSource = -1 ;
	
	/**
	 * Not used normally.
	 */
	protected DefaultDecisionTableEditorInput() {
		this(null);
	}
	/**
	 * @param file
	 */
	public DefaultDecisionTableEditorInput(IFile file) {
		super(file);
	}

	/**
	 * @param file
	 * @param template
	 */
	public DefaultDecisionTableEditorInput(IFile file, RuleFunction virtualRuleFunction) {
		this(file);
		this.virtualRuleFunction = virtualRuleFunction;
	}

	
	public int getModelSource() {
		return modelSource;
	}

	/**
	 * @param modelSource
	 */
	public void setModelSource(int modelSource) {
		this.modelSource = modelSource;
	}

	
	
	public String getFullPath() {
		return getFile().getLocation().toString();
	}
	
	public String getProjectName() {
		return getFile().getProject().getName();
	}
	
	public String getName() {
		String name = getFile().getName();
		name = name.substring(0, name.indexOf("." + getFile().getFileExtension()));
		return name;
	}
	
	public String getResourcePath() {
		IFile file = this.getFile();
		IPath relativePath = file.getProjectRelativePath();
		return "/" + relativePath.removeFileExtension().toString();
	}
	
	//TODO revisit exception handling
	@Override
	public void loadModel() {
		if (tableEModel == null) {
			//Load only if not already loaded
			try {
				LegacyDecisionTableUtil.loadModel(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public IStatus saveModel(RuleFunction virtualRuleFunction, Table tableEModel) {
		//Here we do not save the rule function because we do not need to
		//as it is handled in separate editor.
		try {
			DecisionTableUtil.saveIFileImpl(getFullPath(), 
	                                        tableEModel);
			return new Status(IStatus.OK, DecisionTableUIPlugin.PLUGIN_ID, "Table Saved successfully");
		} catch (Exception e) {
			return new Status(IStatus.ERROR, DecisionTableUIPlugin.PLUGIN_ID, "Failure in saving model", e);
		}
	}
}