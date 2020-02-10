package com.tibco.cep.studio.ui.editors.concepts;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.element.Concept;

/**
 * @author ggrigore/sasahoo
 */
public class ConceptFormEditorInput extends FileEditorInput {

	@SuppressWarnings("unused")
	private IFile file;
	
	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	private Concept concept;
	
	
	public ConceptFormEditorInput(IFile file, Concept concept) {
		super(file);
		this.concept = concept;
		this.file = file;
	}
}
