package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.ui.IEditorInput;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public interface IRulesWorkingCopyManager {

	public RulesASTNode getWorkingCopy(IEditorInput input);

	public void inputChanged(IEditorInput editorInput, Object input);
	
}
