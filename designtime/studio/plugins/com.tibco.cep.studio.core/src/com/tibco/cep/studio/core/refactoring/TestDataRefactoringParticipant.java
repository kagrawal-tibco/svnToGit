package com.tibco.cep.studio.core.refactoring;

import java.io.IOException;
import java.io.InputStream;
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
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class TestDataRefactoringParticipant extends StudioRefactoringParticipant {
	
	private static final String[] SUPPORTED_EXTENSIONS = new String[] { "concepttestdata",  "evntestdata", "scorecardtestdata"};
	private List<String> extns = new ArrayList<String>();

	public TestDataRefactoringParticipant() {
		super();
		extns.clear();
		for(String s: SUPPORTED_EXTENSIONS){
			extns.add(s);
		}
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
//		if (resource instanceof IFolder) {
//			Object elementToRefactor = getElementToRefactor();
//			// folder refactorings need to be done pre-change, as elements could have moved and therefore post-changes cannot be computed
//			return processDecisionTable(elementToRefactor, fProjectName, pm);
//		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();
	
		if (!(extns.contains(file.getFileExtension()))) {
			return null;//new NullChange();
		}
//		Object elementToRefactor = getElementToRefactor();
//		DesignerElement element = IndexUtils.getElement(file);
//		if (!(element instanceof DecisionTableElement)) {
//			return null;
//		}
		CompositeChange change = new CompositeChange("Changes to "+ getName());
		
//		processDecisionTableElement(fProjectName, change, (DecisionTableElement) element, elementToRefactor);
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
		if (isDeleteRefactor() || extns.contains(file.getFileExtension())) {
			return status;
		}

		return status;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		return processDecisionTable(elementToRefactor, fProjectName, pm);
	}

	/**
	 * @param elementToRefactor
	 * @param projectName
	 * @param pm
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	public Change processDecisionTable(Object elementToRefactor, 
			                           String projectName, 
			                           IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		// look through all Decision Tables and make appropriate changes
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
//		CompositeChange compositeChange = new CompositeChange("Table changes:");
//		List<DesignerElement> allDecisionTables = IndexUtils.getAllElements(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.DECISION_TABLE });
//		for (DesignerElement element : allDecisionTables) {
//			if (element == elementToRefactor) {
//				// already processed in the pre-change
//				continue;
//			}
//			processDecisionTableElement(projectName, compositeChange, (DecisionTableElement) element, elementToRefactor);
//		}
//		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
//			return compositeChange;
//		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		return "Test Data Refactoring participant";
	}

	/**
	 * @param projectName
	 * @param compositeChange
	 * @param decisionTableElement
	 * @param elementToRefactor
	 * @throws CoreException
	 */
	private void processDecisionTableElement(String projectName,
			                                 CompositeChange compositeChange, 
			                                 DecisionTableElement decisionTableElement, 
			                                 Object elementToRefactor) throws CoreException {
		boolean changed = false;
		Table table = (Table) decisionTableElement.getImplementation();
		
		//Handling Folders
		if (processFolder(table)){
			changed = true;
		}
		
		//Handling Concept/Scorecard/Event changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
			String newFullPath = "";
			String oldFullPath = "";
			if (elementToRefactor instanceof Concept || 
					elementToRefactor instanceof Event || isFolderRefactor()) {
				Entity refactoredElement = (Entity) elementToRefactor;
				oldFullPath = refactoredElement.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + refactoredElement.getName();
				}
			}
			table =(Table) decisionTableElement.getImplementation();
			List<Argument> list =  table.getArgument();
			for(Argument argument:list){
				ArgumentProperty argumentProperty = argument.getProperty();
				String path = argumentProperty.getPath();
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(path, folder)) {
						String newPath = getNewPath(path, folder);
						argumentProperty.setPath(newPath);
						changed = true;
					}
				} else {
					if(path.equals(oldFullPath)){
						argumentProperty.setPath(newFullPath);
						changed = true;
					}
				}
			}
			
			if (elementToRefactor instanceof PropertyDefinition) {
				//TODO: Columns/Custom Condition/Custom Action Handling
			}
		}
		
		//Handling Folder Refactoring
		if (isFolderRefactor()) {
			table = (Table) decisionTableElement.getImplementation();
			IFolder folder = (IFolder) getResource();
			
			//Handling RF Implementation Arguments
			List<Argument> list =  table.getArgument();
			for(Argument argument:list){
				ArgumentProperty argumentProperty = argument.getProperty();
				String path = argumentProperty.getPath();
				if (IndexUtils.startsWithPath(path, folder)) {
					String newPath = getNewPath(path, folder);
					argumentProperty.setPath(newPath);
					changed = true;
				}
			}
			
			//Handling VRFImplements Path
			String implementPath = table.getImplements();
			if (IndexUtils.startsWithPath(implementPath, folder)) {
				String newPath = getNewPath(implementPath, folder);
				table.setImplements(newPath);
				changed = true;
			}
		}
		
        //Handling Decision Table Rename/Move		
		if(elementToRefactor instanceof DecisionTableElement){
			DecisionTableElement tableElement = (DecisionTableElement)elementToRefactor;
			table =(Table) tableElement.getImplementation();
			if(isRenameRefactor()){
				table.setName(getNewElementName());
			}else if(isMoveRefactor()){
				table.setFolder(getNewElementPath());
			}
			changed = true;
		}
		
		//Handling VRF refactoring
		if(elementToRefactor instanceof RuleElement){
			table =(Table) decisionTableElement.getImplementation();
			RuleElement relement = (RuleElement) elementToRefactor;
			if(table.getImplements().equals(relement.getFolder()+relement.getName())){
				if(isRenameRefactor()){
					table.setImplements(relement.getFolder() + getNewElementName());
				}else if(isMoveRefactor()){
					table.setFolder(getNewElementPath());
					table.setImplements(getNewElementPath() + relement.getName());
				}
				changed = true;
			}
		}
		
		if (changed) {
			Change change = createTextFileChange(IndexUtils.getFile(fProjectName, decisionTableElement), table);
			compositeChange.add(change);
		}
	}
	
	/**
	 * @param table
	 * @return
	 */
	protected boolean processFolder(Table table) {
		if (isFolderRefactor()) {
			IFolder folder = (IFolder) getResource();
			if (IndexUtils.startsWithPath(table.getFolder(), folder, true)) {
				String newPath = getNewPath(table.getFolder(), folder);
				table.setFolder(newPath);
				return true;
			}
		}
		return false;
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
		String oldPath = folder.getProjectRelativePath().toString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toString();
				return initChar + parentPath + "/" + getNewElementName() + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + getNewElementName() + elementPath.substring(oldPath.length()+offset);
		}
		return elementPath;
	}
	
	/**
	 * Creates a <code>Change</code> object given the IFile and Table.
	 * Simply serializes the Table and creates a ReplaceEdit for the
	 * IFile.  This is generally used after processing the table, to
	 * create the actual refactoring change to take place
	 * @param file
	 * @param refactorParticipant
	 * @return
	 * @throws CoreException
	 */
	protected Change createTextFileChange(IFile file, Table refactorParticipant) throws CoreException {
		refactorParticipant = (Table) IndexUtils.getRootContainer(refactorParticipant);
		byte[] objectContents;
		TextFileChange change = null;
		InputStream fis = null;
		try {
			objectContents = IndexUtils.getEObjectContents(null,refactorParticipant);
			String contents = new String(objectContents, file.getCharset());

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
	 * @return
	 */
	protected String[] getSupportedExtensions() {
		return SUPPORTED_EXTENSIONS;
	}
	
	/**
	 * @param file
	 * @return
	 */
	protected boolean isSupportedResource(IFile file) {
		String[] extensions = getSupportedExtensions();
		if (extensions == null && (file.getFileExtension() == null || file.getFileExtension().length() == 0)) {
			return true;
		}
		if (extensions != null) {
			for (String ext : extensions) {
				if (ext.equalsIgnoreCase(file.getFileExtension())) {
					return true;
				}
			}
		}
		return false;
	}

//	private static final String NAME_ATTR = "name=\"";
//	protected Change createChange(IResource resource) {
//		CompositeChange compositeChange = new CompositeChange("Changing Table. ");
//		InputStream is = null;
//		try {
//			if (!(resource instanceof IFile)) {
//				return compositeChange;
//			}
//			IFile file = (IFile)resource;
//			String oldName = (resource instanceof IFile) ?
//				((IFile)resource).getFullPath().removeFileExtension().lastSegment()
//			     : getResource().getName();
//				
//			String newName = getNewElementName();	
//			int newLength = newName.length();
//			int oldNameLength = oldName.length();
//			
//			is = file.getContents();
//			InputStreamReader inputStreamReader = new InputStreamReader(is);
//			StringBuilder sb = new StringBuilder();
//			int charCounter = 0;
//			int c = -1;
//			while ((c = inputStreamReader.read()) != -1) {
//				//Since it is a char read cast it
//				char character = (char)c;
//				sb.append(character);
//				if (sb.indexOf(NAME_ATTR) != -1) {
//					//Note this position
//					//Increment the position by 1
//					charCounter = charCounter + 1;
//					if (newLength > oldNameLength) {
//						TextFileChange insertChange = 
//							new TextFileChange("New name change", file);
//						TextFileChange deleteChange = 
//							new TextFileChange("Remove Old name change", file);
//						newName = newName + "\"";
//						InsertEdit insertEdit = new InsertEdit(charCounter, newName);
//						insertChange.setEdit(insertEdit);
//						compositeChange.add(insertChange);
//						DeleteEdit deleteEdit = 
//							new DeleteEdit(charCounter + newName.length(), oldNameLength + 1);
//						deleteChange.setEdit(deleteEdit);
//						compositeChange.add(deleteChange);
//					} else {
//						TextFileChange deleteChange = 
//							new TextFileChange("Remove Old name change", file);
//						TextFileChange insertChange = 
//							new TextFileChange("New name change", file);
//						DeleteEdit deleteEdit = new DeleteEdit(charCounter, oldNameLength);
//						deleteChange.setEdit(deleteEdit);
//						compositeChange.add(deleteChange);
//						InsertEdit insertEdit = new InsertEdit(charCounter, newName);
//						insertChange.setEdit(insertEdit);
//						compositeChange.add(insertChange);
//					}
//					break;
//				}
//				charCounter++;
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			try {
//				if (is != null) {
//					is.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return compositeChange;
//	}
}