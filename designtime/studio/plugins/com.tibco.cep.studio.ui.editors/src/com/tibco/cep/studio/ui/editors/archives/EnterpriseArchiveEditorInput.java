package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;

/**
 * 
 * @author sasahoo
 *
 */
public class EnterpriseArchiveEditorInput extends FileEditorInput {

	@SuppressWarnings("unused")
	private IFile file;
	
	private EnterpriseArchive archive;
	
	public EnterpriseArchive getEnterpriseArchive() {
		return archive;
	}

	public void setEnterpriseArchive(EnterpriseArchive archive) {
		this.archive = archive;
	}
	
	public EnterpriseArchiveEditorInput(IFile file, EnterpriseArchive archive) {
		super(file);
		this.file = file;
		this.archive = archive;
	}
}
