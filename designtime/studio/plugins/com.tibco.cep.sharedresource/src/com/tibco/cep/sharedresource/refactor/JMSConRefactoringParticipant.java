package com.tibco.cep.sharedresource.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.sharedresource.jms.JmsConModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigJmsModel;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringContext;
import com.tibco.cep.studio.core.util.CommonUtil;

public class JMSConRefactoringParticipant extends AbstractSharedResourceRefactoring {
	
	public JMSConRefactoringParticipant() {
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
			return processJMSConnection(elementToRefactor, fProjectName, pm);
		}
		
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();
	
		if (!(getExtension().equalsIgnoreCase(file.getFileExtension()))) {
			return null;//new NullChange();
		}
		Object elementToRefactor = getElementToRefactor();
		
		String name = resource.getName().replace("." + getExtension(), "");
		JmsConModelMgr modelmgr = new JmsConModelMgr(resource);
//		if (modelmgr == null) {
//			return null;
//		}
		modelmgr.parseModel();
		
		CompositeChange change = new CompositeChange("Changes to "+ name);
		processJMSConnectionElement(fProjectName, change, file, modelmgr, elementToRefactor);
		return change;
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
		return processJMSConnection(elementToRefactor, fProjectName, pm);
	}

	/**
	 * @param elementToRefactor
	 * @param projectName
	 * @param pm
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	public Change processJMSConnection(Object elementToRefactor, 
			                                  String projectName, 
			                                  IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("JMS Connection changes:");
		List<IFile> fileList = new ArrayList<IFile>();
		CommonUtil.getResourcesByExtension(getResource().getProject(), getExtension(), fileList);
		for (IFile element : fileList) {
			if(isFileProcessed(elementToRefactor, element)){
				// already processed in the pre-change
				continue;
			}
			JmsConModelMgr modelmgr = new JmsConModelMgr(element);
			modelmgr.parseModel();
			processJMSConnectionElement(fProjectName, compositeChange, element, modelmgr, elementToRefactor);
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
		return "JMS Connection Refactoring participant";
	}

	
	/**
	 * @param projectName
	 * @param compositeChange
	 * @param sharedResFile
	 * @param modelmgr
	 * @param elementToRefactor
	 * @throws CoreException
	 */
	private void processJMSConnectionElement(String projectName,
			                                 CompositeChange compositeChange,
			                                 IFile sharedResFile,
			                                 JmsConModelMgr modelmgr, 
			                                 Object elementToRefactor) throws CoreException {
		boolean changed = false;
	    String fName = sharedResFile.getName().replace("." + getExtension(), "");
		//Handling JMS Connection rename 
	    if(elementToRefactor instanceof StudioRefactoringContext){
	    	StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
	    	if(context.getElement() instanceof IFile){
	    		IFile file = (IFile)context.getElement();
	    		if(file.getFileExtension().equals(getExtension())){
	    			if (isRenameRefactor()) {
	    				String oldName = modelmgr.getModel().getName();
	    				if(oldName.equalsIgnoreCase(fName)){
	    					modelmgr.getModel().setName(getNewElementName()); 
	    					changed  = true;
	    				}
	    			}
	    		}
	    		if(file.getFileExtension().equals("id")){
	    			boolean useSsl = modelmgr.getBooleanValue("useSsl");
	    			if (useSsl) {
	    				SslConfigJmsModel sslConfigModel = modelmgr.getModel().getSslConfigJmsModel();
	    				if (sslConfigModel != null) {
	    					String oldName = "/"+file.getProjectRelativePath().toString();
	    					String newName = "";
	    					String identity = sslConfigModel.getIdentity();
	    					if(isRenameRefactor()){
	    						 newName = "/"+file.getParent().getProjectRelativePath().toString()+"/"+ getNewElementName()+".id";
	    					}
	    					if(isMoveRefactor()){
	    						 newName = getNewElementPath() + file.getName();
	    					}
	    					if(oldName.equals(identity)){
	    						sslConfigModel.setIdentity(newName);
	    						changed = true;
	    					}
	    				}
	    			}
	    		}
	    		if(file.getFileExtension().equals("sharedjndi")){
	    			boolean useJNDI = modelmgr.getBooleanValue("UseJNDI");
	    			boolean useSharedJndiConfig = modelmgr.getBooleanValue("UseSharedJndiConfig");
	    			if(useJNDI){
	    				if(useSharedJndiConfig){
	    					String oldName = "/"+file.getProjectRelativePath().toString();
	    					String newName = "";
	    					String jndiSharedConfiguration = modelmgr.getStringValue("JndiSharedConfiguration");
	    					if(isRenameRefactor()){
	    						 newName = "/"+file.getParent().getProjectRelativePath().toString()+"/"+ getNewElementName()+".id";
	    					}
	    					if(isMoveRefactor()){
	    						 newName = getNewElementPath() + file.getName();
	    					}
	    					if(oldName.equals(jndiSharedConfiguration)){
	    						modelmgr.getModel().values.put("JndiSharedConfiguration", newName);
	    						changed = true;
	    					}
	    				}
	    			}
	    		}
	    	}
	    }
		if (changed) {
			String changedText = modelmgr.saveModel();
			Change change = createTextFileChange(sharedResFile, changedText);
			compositeChange.add(change);
		}
	}

	@Override
	public String getExtension() {
		return "sharedjmscon";
	}
}