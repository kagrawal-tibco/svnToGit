package com.tibco.cep.studio.core.index.jobs;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.BaseBinaryStorageIndexCreator;

public class BinaryStorageIndexCreator extends BaseBinaryStorageIndexCreator {
	
	private IProgressMonitor fProgressMonitor;

	public BinaryStorageIndexCreator(DesignerProject index, JarFile file, IProgressMonitor monitor, String projectName) {
		super(projectName, index, file);
		this.fProgressMonitor = monitor;
	}

    protected void reportWorkBegin(JarEntry entry) {
    	getProgressMonitor().setTaskName("indexing binary storage -- "+entry.getName());
    }

    protected void reportWork(int work) {
    	getProgressMonitor().worked(work);
    }

    protected void finishWork() {
		getProgressMonitor().done();
	}

	private IProgressMonitor getProgressMonitor() {
		IProgressMonitor monitor = null;
    	if (fProgressMonitor != null) {
    		monitor = new SubProgressMonitor(fProgressMonitor, 1);
    	} else {
    		monitor = new NullProgressMonitor();
    	}
		return monitor;
    }

}

