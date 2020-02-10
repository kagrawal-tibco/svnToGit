package com.tibco.cep.studio.ui.compare.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.BufferedContent;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.util.CompareUtils;


public abstract class AbstractResourceNode extends BufferedContent implements ITypedElement,IStructureComparator, IEditableContent {

	private Object input;
	private String name;
	private boolean dirty;
	
	private boolean remote = false;
	private int revision = -1; // if this is a remote resource, this is the version of the remote content
	private boolean autoMerge;
	
	protected IFile localFile;
	
	public AbstractResourceNode(Object input, boolean autoMerge) {
		this.input = input;
		this.autoMerge = autoMerge;
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

	@Override
	protected InputStream createStream() throws CoreException {
		if (input instanceof InputStream) {
			try {
				((InputStream)input).reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return (InputStream) input;
		} else if (input instanceof IFile) {
			return ((IFile)input).getContents();
		} else if (input instanceof File) {
			try {
				return new FileInputStream((File)input);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (input instanceof EObject && !isRemote()) {
			String resPath = getModelAbsuluteFilePath((EObject) input);
			File file = new File(resPath);
			if (!file.exists()) {
				return null;
			}
			try {
				return new BufferedInputStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (input instanceof byte[]) {
			return new BufferedInputStream(new ByteArrayInputStream((byte[]) input));
		}
		return null;
	}

	public Image getImage() {
		return StudioUIPlugin.getDefault().getImage("icons/comp/detail.gif");
	}

	public String getShortName() {
		if (isRemote() && name != null) {
			return "[Remote] " + name;
		}
		if (input instanceof IFile) {
			return ((IFile)input).getName();
		} else if (input instanceof File) {
			return ((File)input).getName();
		} else if (input instanceof EObject) {
			return getModelName()+"."+ getExtension();
		}
		return "empty document";
	}
	
	public String getName() {
		if (isRemote() && name != null) {
			if (getRevision() == -1) {
				return "[Master copy] " + name;
			}
			return "[Remote revision " + getRevision() + "] " + name;
		}
		if (input instanceof EObject) {
			String name;
			if (isRemote()) {
				if (getRevision() == -1) {
					name =  "[Master copy] ";
				} else {
					name = "[Remote revision ";
					name +=  getRevision()+"] ";
				}
			} else {
				name = /*"[Local version "*/ "[Local Working Copy]";
			}
//			name +=  getRevision()+"] ";// uncomment this once we have local copy revision 
			name += "/" + getModelName()+"."+ getExtension();;
			return name;
		} 
		if (input != null) {
			String mName = getModelName()+"."+ getExtension();
			if (remote) {
				if (getRevision() == -1) {
					return "[Master copy] " + mName;
				}
				return "[Remote revision " + getRevision() + "] " + mName;
			} else if (!remote) {
				return /*"[Local version "*/ "[Local Working Copy]" + "/" +mName;	
			}
			return mName;
		}
		return getShortName();
	}

	public String getType() {
		return TEXT_TYPE;
	}

	public Object[] getChildren() {
		return new Object[] { new EMFCompareNode((EObject) input,CompareUtils.getTreeNode((EObject)input, getFeatureId()), autoMerge) };
	}

	/* (non-Javadoc)
	 * @see org.eclipse.compare.IEditableContent#isEditable()
	 */
	public boolean isEditable() {
		return !remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public boolean isRemote() {
		return remote;
	}
	
	public int getRevision() {
		return revision;
	}
	
	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	public ITypedElement replace(ITypedElement dest, ITypedElement src) {
		return dest;
	}

	public Object getInput() {
		return input;
	}

	@Override
	public byte[] getContent() {
		return super.getContent();
	}

	@Override
	public InputStream getContents() throws CoreException {
		return super.getContents();
	}

	/**
	 * Commit this resource to disk
	 * 
	 * @param pm
	 */
	public void commit(IProgressMonitor pm) {
		if (!(input instanceof EObject)) {
			return;
		}
		FileOutputStream fos = null;
		try {
			String path = getModelAbsuluteFilePath((EObject) input); 
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

	protected abstract String getModelAbsuluteFilePath(EObject object);
	protected abstract String getModelName();
	protected abstract String getExtension();
	protected abstract int getFeatureId();

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public IFile getLocalFile() {
		return localFile;
	}

	public void setLocalFile(IFile localFile) {
		this.localFile = localFile;
	}

}