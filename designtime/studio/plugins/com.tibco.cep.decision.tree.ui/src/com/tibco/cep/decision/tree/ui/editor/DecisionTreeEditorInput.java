package com.tibco.cep.decision.tree.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.tree.common.model.DecisionTree;

/**
 * 
 * @author ssailapp
 * 
 */

public class DecisionTreeEditorInput extends FileEditorInput {
	private DecisionTree decisionTree;
	private IProject project;
	private Table tableEModel;
	
	public DecisionTreeEditorInput(IFile file, DecisionTree decisionTree) {
		super(file); 
		this.decisionTree = decisionTree;
		this.project = file.getProject();
	}
	
	public IProject getProject() {
		return this.project;
	}

	public DecisionTree getDecisionTree() {
		return decisionTree;
	}

	public void setDecisionTree(DecisionTree decisionTree) {
		this.decisionTree = decisionTree;
	}
	
	public Table getTableEModel() {
		return tableEModel;
	}
	
	public void setTableEModel(Table tableEModel) {
		this.tableEModel = tableEModel;
	}

}
