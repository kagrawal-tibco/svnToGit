package com.tibco.cep.studio.tester.ui.editor.data;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * 
 * @author sasahoo
 *
 */
public class TestDataEditorInput extends FileEditorInput {

	private IFile file;
	private Entity entity;
	private File testFile;

	/**
	 * @param file
	 * @param concept
	 */
	public TestDataEditorInput(IFile file) {
		super(file);
		this.file = file;
	}

	public Entity getEntity() {
		return entity;
	}

	public void getProject(){
		file.getProject();
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public File getTestFile() {
		return testFile;
	}

	public void setTestFile(File testFile) {
		this.testFile = testFile;
	}
}

