package com.tibco.cep.studio.dashboard.ui.editors.metric;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class MetricEditorInput extends FileEditorInput {

	private LocalElement localElement;

	public MetricEditorInput(IFile file, LocalElement localElement) {
		super(file);
		this.localElement = localElement;
	}

	public LocalElement getLocalElement() {
		return localElement;
	}

	public void setLocalElement(LocalElement localElement) {
		this.localElement = localElement;
	}

}
