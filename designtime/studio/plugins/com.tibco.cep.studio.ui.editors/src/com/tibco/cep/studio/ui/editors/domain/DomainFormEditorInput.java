/**
 * 
 */
package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.domain.Domain;

/**
 * @author aathalye
 *
 */
public class DomainFormEditorInput extends FileEditorInput {
	
	@SuppressWarnings("unused")
	private IFile file;
	
	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	private Domain domain;
	
	
	public DomainFormEditorInput(IFile file, Domain domain) {
		super(file);
		this.domain = domain;
		this.file = file;
	}
}
