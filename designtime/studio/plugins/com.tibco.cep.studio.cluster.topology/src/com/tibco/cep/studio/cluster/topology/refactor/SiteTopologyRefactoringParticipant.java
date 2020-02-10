package com.tibco.cep.studio.cluster.topology.refactor;

import static com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils.CLUSTER_TOPOLOGY_JAXB_PACKAGE;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.studio.cluster.topology.model.Site;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringContext;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;
import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class SiteTopologyRefactoringParticipant  extends StudioRefactoringParticipant implements IStudioPasteParticipant {

	public SiteTopologyRefactoringParticipant() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
															  OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			return null;
		}
		IResource resource = getResource();
		
		if (resource instanceof IFolder) {
			Object elementToRefactor = getElementToRefactor();
			// folder refactorings need to be done pre-change, as elements could have moved and therefore post-changes cannot be computed
			return processSiteConfig(elementToRefactor, fProjectName, pm);
		}
		
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();
	
		if (!(getExtension().equalsIgnoreCase(file.getFileExtension()))) {
			return null;//new NullChange();
		}
		Object elementToRefactor = getElementToRefactor();
		
		String name = resource.getName().replace("."+ getExtension(), "");
		
		Site site = getSite(file);
		
		CompositeChange change = new CompositeChange("Changes to "+ name);
		processSiteConfigElement(fProjectName, change, file, site, elementToRefactor);
		return change;
	}

	/**
	 * @param file
	 * @return
	 */
	private Site getSite(IFile file) {
		try {
			File siteFile  = file.getLocation().toFile();
			JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Site site = (Site)unmarshaller.unmarshal(siteFile);
			return site;
		} catch (JAXBException je) {
			return null;
		}catch (Exception e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			                                 CheckConditionsContext context) throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return super.checkConditions(pm, context);
		}
		RefactoringStatus status = super.checkConditions(pm, context);
		IResource resource = getResource();
		if (!(resource instanceof IFile)) {
			return status;
		}
		IFile file = (IFile) resource;
		if (isDeleteRefactor()
				|| getExtension().equalsIgnoreCase(file.getFileExtension())) {
			return status;
		}

		return status;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		return processSiteConfig(elementToRefactor, fProjectName, pm);
	}

	/**
	 * @param elementToRefactor
	 * @param projectName
	 * @param pm
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	public Change processSiteConfig(Object elementToRefactor, 
			                                  String projectName, 
			                                  IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Site Topology Configuration changes:");
		List<IFile> fileList = new ArrayList<IFile>();
		CommonUtil.getResourcesByExtension(getResource().getProject(), getExtension(), fileList);
		for (IFile element: fileList) {
			if (isFileProcessed(elementToRefactor, (IFile)element)){
				// already processed in the pre-change
				continue;
			}
			Site site = getSite(element);
			processSiteConfigElement(fProjectName, compositeChange, element, site, elementToRefactor);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		return "Site Topology Configuration Refactoring participant";
	}

	
	/**
	 * @param projectName
	 * @param compositeChange
	 * @param sharedFile
	 * @param modelmgr
	 * @param elementToRefactor
	 * @throws CoreException
	 */
	private void processSiteConfigElement(String projectName,
			                                 CompositeChange compositeChange,
			                                 IFile sharedFile,
			                                 Site site, 
			                                 Object elementToRefactor) throws CoreException {
		boolean changed = false;
		String fName = sharedFile.getName().replace("." + getExtension(), "");
		//Handling Site Topology Config rename 
		if(elementToRefactor instanceof StudioRefactoringContext){
			StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
			if(context.getElement() instanceof IFile){
				IFile file = (IFile)context.getElement();
				if(file.getFileExtension().equals(getExtension())){
					if (isRenameRefactor()) {
						String oldName = site.getName();
						if(oldName.equalsIgnoreCase(fName)){
							site.setName(getNewElementName()); 
							changed  = true;
						}
					}
				}
			}
		}
		if (changed) {
			try {
				String changedText = getChangedText(site);
				Change change = createTextFileChange(sharedFile, changedText);
				compositeChange.add(change);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}

	public String getExtension() {
		return "st";
	}
	
	/**
	 * @param site
	 * @return
	 * @throws JAXBException
	 */
	protected String getChangedText(Site site) throws JAXBException {
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE);
		Marshaller marshaller=jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(site, stringWriter);
		return stringWriter.toString();
	}
	
	/**
	 * @param elementPath
	 * @param folder
	 * @return
	 */
	protected String getNewPath(String elementPath, IFolder folder) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toPortableString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toPortableString();
				return initChar + parentPath + "/" + getNewElementName() + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + getNewElementName() + elementPath.substring(oldPath.length()+offset);
		}
		return elementPath;
	}
	
	/**
	 * @param elementToRefactor
	 * @param element
	 * @return
	 */
	protected boolean isFileProcessed(Object elementToRefactor, IFile element){
		if(elementToRefactor instanceof StudioRefactoringContext){
	    	StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
	    	if(context.getElement() instanceof IFile){
	    		IFile file = (IFile)context.getElement();
	    		if(file.getFullPath().toPortableString().equals(element.getFullPath().toPortableString())){
	    			return true;
	    		}
	    	}
		}
		return false;
	}
	
	/**
	 * @param file
	 * @param modelmgr
	 * @return
	 * @throws CoreException
	 */
	protected Change createTextFileChange(IFile file, String changedText) throws CoreException {
		TextFileChange change = null;
		InputStream fis = null;
		try {
			String contents = new String(changedText.getBytes(file.getCharset()));
			change = new TextFileChange("New name change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return change;
	}
	
	
	/**
	 * @param newName
	 * @param resource
	 * @param target
	 * @param overwrite
	 * @throws CoreException
	 */
	protected void pasteSiteTopology(String newName, IResource resource, IContainer target, boolean overwrite) throws CoreException {
		if (newName == null) {
			return;
		}
		int idx = newName.lastIndexOf('.');
		Site site = getSite((IFile)resource);
		if (idx > 0) {
			String newEntName = newName.substring(0, idx);
			site.setName(newEntName); 
		} else {
			site.setName(newName);
		}
		try {
			String changedText = getChangedText(site);
			saveSiteTopology(newName, changedText, resource, target, overwrite);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param newName
	 * @param contents
	 * @param resource
	 * @param target
	 * @param overwrite
	 * @throws CoreException
	 */
	protected void saveSiteTopology(String newName, String contents, IResource resource, IContainer target, boolean overwrite) throws CoreException {
		try {
			ByteArrayInputStream bos = new ByteArrayInputStream(contents.getBytes());
			IFile file = target.getFile(new Path(newName));
			if (file.exists()) {
				if (overwrite) {
					file.setContents(bos, IResource.FORCE, new NullProgressMonitor());
				}
			} else {
				file.create(bos, false, new NullProgressMonitor());
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID, "could not save "+ resource.getFullPath().toString() ,e));
		} 
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant#pasteElement(java.lang.String, org.eclipse.core.resources.IResource, org.eclipse.core.resources.IContainer, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IStatus pasteElement(String newName, IResource resource, IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (resource instanceof IFile 
				&& ((IFile)resource).getFileExtension().equals(getExtension())) {
			pasteSiteTopology(newName, resource, target, overwrite);
    	}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant#isSupportedPaste(org.eclipse.core.resources.IResource, org.eclipse.core.resources.IContainer)
	 */
	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		if (resource.getFileExtension().equals(getExtension())){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant#validateName(org.eclipse.core.resources.IResource, org.eclipse.core.resources.IContainer, java.lang.String)
	 */
	@Override
	public IStatus validateName(IResource resource, IContainer target, String newName) {
		if (!EntityNameHelper.isValidBEEntityIdentifier(newName)) {
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, newName + " is not a valid BE identifier");
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setProjectPaste(boolean projectPaste) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isProjectPaste() {
		// TODO Auto-generated method stub
		return false;
	}
}