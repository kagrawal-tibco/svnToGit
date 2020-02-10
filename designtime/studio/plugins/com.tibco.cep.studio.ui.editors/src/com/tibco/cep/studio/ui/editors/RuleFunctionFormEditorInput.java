package com.tibco.cep.studio.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

public class RuleFunctionFormEditorInput extends FileEditorInput{

	@SuppressWarnings("unused")
	private IFile file;
		
	public RuleFunctionFormEditorInput(IFile file) {
		super(file);
	}
}
