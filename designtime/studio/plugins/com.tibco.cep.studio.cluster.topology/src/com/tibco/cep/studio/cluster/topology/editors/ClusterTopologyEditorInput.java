package com.tibco.cep.studio.cluster.topology.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyEditorInput extends FileEditorInput {

	/**
	 * @param file
	 */
	public ClusterTopologyEditorInput(IFile file) {
		super(file);
	}
}


