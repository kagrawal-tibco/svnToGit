package com.tibco.cep.studio.ui.compare.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class RuleResourceNode extends EMFResourceNode {

	private String path;
	private String extension;
	private int featureId;
	private String name;

	/**
	 * @param input
	 * @param name
	 * @param path
	 * @param extension
	 * @param featureId
	 * @param revision
	 * @param isRemote
	 * @param autoMerge
	 */
	public RuleResourceNode(Object input, 
			                String name, 
			                String path, 
			                String extension, 
			                int featureId, 
			                String revision,
			                boolean isRemote,
			                boolean autoMerge) {
		super(input, revision, isRemote, autoMerge);
		this.name = name;
		this.path = path;
		this.extension = extension;
		this.featureId = featureId;
		setRemote(isRemote);
		if (isRemote) {
			setRevision(Integer.parseInt(revision));
		}
	}

	@Override
	protected String getExtension() {
		return extension;
	}

	@Override
	protected int getFeatureId() {
		return featureId;
	}

	@Override
	protected String getModelAbsuluteFilePath(EObject object) {
		return null;
	}

	@Override
	protected String getModelName() {
		return name;
	}
	
	/**
	 * Commit this resource to disk
	 * 
	 * @param pm
	 */
	public void commit(IProgressMonitor pm) {
		if (!isValidInput()) {
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(getContent());
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
		if (localFile != null) {
			try {
				localFile.refreshLocal(IFile.DEPTH_ONE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		setDirty(false);
	}
	
	private boolean isValidInput() {
		if (getExtension().equals(CommonIndexUtils.RULE_EXTENSION) || 
				getExtension().equals(CommonIndexUtils.RULEFUNCTION_EXTENSION)) {
			return true;
		}
		return false;
	}
}