/**
 * 
 */
package com.tibco.cep.studio.core.refactoring;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.DecisionTableSearchParticipant;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * @author aathalye
 * @author sasahoo
 * 
 */

public class DecisionTableRefactoringParticipant extends
		StudioRefactoringParticipant implements IStudioPasteParticipant {

	public static Map<Object , Object> deletedDtCache = new HashMap<Object,Object>();
	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.RULE_FN_IMPL_EXTENSION };

	private static final String CLASS = DecisionTableRefactoringParticipant.class
			.getName();

	public DecisionTableRefactoringParticipant() {
		super();
	}

	@Override
	protected ISearchParticipant getSearchParticipant() {
		return new DecisionTableSearchParticipant();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#createPreChange
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder) {
			Object elementToRefactor = getElementToRefactor();
			// folder refactorings need to be done pre-change, as elements could
			// have moved and therefore post-changes cannot be computed
			return processDecisionTable(elementToRefactor, fProjectName, pm);
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();
		if (IndexUtils.getFileType(file) == null) {
			return null; // object is not an entity
		}
		if (!(CommonIndexUtils.RULE_FN_IMPL_EXTENSION.equalsIgnoreCase(file.getFileExtension()))) {
			return null;// new NullChange();
		}
		Object elementToRefactor = getElementToRefactor();
		DesignerElement element = IndexUtils.getElement(file);
		if (!(element instanceof DecisionTableElement)) {
			return null;
		}
		CompositeChange change = new CompositeChange("Changes to " + element.getName());

		processDecisionTableElement(fProjectName, change,
				(DecisionTableElement) element, elementToRefactor);
		return change;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#checkConditions
	 * (org.eclipse.core.runtime.IProgressMonitor,
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
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
				|| CommonIndexUtils.RULE_FN_IMPL_EXTENSION
						.equalsIgnoreCase(file.getFileExtension())) {
			return status;
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#
	 * createChange(org.eclipse.core.runtime.IProgressMonitor)
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
			String projectName, IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		// look through all Decision Tables and make appropriate changes
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Table changes:");
		List<DesignerElement> allDecisionTables = CommonIndexUtils
				.getAllElements(projectName,
						new ELEMENT_TYPES[] { ELEMENT_TYPES.DECISION_TABLE });
		for (DesignerElement element : allDecisionTables) {
			if (element == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processDecisionTableElement(projectName, compositeChange,
					(DecisionTableElement) element, elementToRefactor);
		}
		if (compositeChange.getChildren() != null
				&& compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#
	 * getName()
	 */
	@Override
	public String getName() {
		return "Decision Table Rename participant";
	}



	
	public static Map<Object , Object> getDeletedDTCache(){
		return deletedDtCache ;
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
			DecisionTableElement decisionTableElement, Object elementToRefactor)
			throws CoreException {
		boolean changed = false;
		Table table = (Table) decisionTableElement.getImplementation();
		Table oldTable = EcoreUtil.copy(table);
		// Handling Folders
		if (processFolder(table)) {
			changed = true;
		}

		// Handling Concept/Scorecard/Event changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
			String newFullPath = "";
			String oldFullPath = "";
			if (elementToRefactor instanceof Concept
					|| elementToRefactor instanceof Event || isFolderRefactor()) {
				Entity refactoredElement = (Entity) elementToRefactor;
				oldFullPath = refactoredElement.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder()
							+ getNewElementName();
				} else if (isMoveRefactor()) {
					newFullPath = getNewElementPath()
							+ refactoredElement.getName();
				}
			}
			table = (Table) decisionTableElement.getImplementation();
			List<Argument> list = table.getArgument();
			for (Argument argument : list) {
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
					if (path.equals(oldFullPath)) {
						argumentProperty.setPath(newFullPath);
						changed = true;
					}
				}
			}	
			if(changed){
				if(table.getDecisionTable() != null & table.getDecisionTable().getColumns() != null){
					Columns dtcolumns = table.getDecisionTable().getColumns();
					for(Column dtColumn : dtcolumns.getColumn()){
						if (dtColumn.getPropertyPath() == null) {
							continue;
						}
						if(dtColumn.getPropertyPath().contains(oldFullPath+"/")){
							String newPropertyPath = dtColumn.getPropertyPath().replace(oldFullPath+"/", newFullPath+"/" );
							dtColumn.setPropertyPath(newPropertyPath);
						}
					}
				}
				
				if(table.getExceptionTable() != null & table.getExceptionTable().getColumns() != null){
					Columns etcolumns = table.getExceptionTable().getColumns();
					for(Column etColumn : etcolumns.getColumn()){
						if (etColumn.getPropertyPath() == null) {
							continue;
						}
						if(etColumn.getPropertyPath().contains(oldFullPath+"/")){
							String newPropertyPath = etColumn.getPropertyPath().replace(oldFullPath+"/", newFullPath+"/" );
							etColumn.setPropertyPath(newPropertyPath);
						}
					}
				}
				
			}
		}

		// Columns/Custom Condition/Custom Action Handling
		if (elementToRefactor instanceof PropertyDefinition) {
			if (table.getDecisionTable() != null && table.getDecisionTable().getColumns() != null) {				
				changed = refactorDecisionTableForProperties(projectName, table.getDecisionTable(), elementToRefactor);
			}

			if (table.getExceptionTable() != null && table.getExceptionTable().getColumns() != null) {
				changed = refactorDecisionTableForProperties(projectName, table.getExceptionTable(), elementToRefactor);
			}
		}
		
		// Handling Folder Refactoring
		if (isFolderRefactor()) {
			table = (Table) decisionTableElement.getImplementation();
			IFolder folder = (IFolder) getResource();

			// Handling RF Implementation Arguments
			List<Argument> list = table.getArgument();
			for (Argument argument : list) {
				ArgumentProperty argumentProperty = argument.getProperty();
				String path = argumentProperty.getPath();
				if (IndexUtils.startsWithPath(path, folder)) {
					String newPath = getNewPath(path, folder);
					argumentProperty.setPath(newPath);
					changed = true;
				}
			}

			// Handling VRFImplements Path
			String implementPath = table.getImplements();
			if (IndexUtils.startsWithPath(implementPath, folder)) {
				String newPath = getNewPath(implementPath, folder);
				table.setImplements(newPath);
				changed = true;
			}
			
			// Handling DT columns Property Paths
			
			Columns dtcolumns = table.getDecisionTable().getColumns();
			for (Column dtColumn : dtcolumns.getColumn()) {
				if (IndexUtils.startsWithPath(dtColumn.getPropertyPath(), folder)) {
					String newPath = getNewPath(dtColumn.getPropertyPath(), folder);
					dtColumn.setPropertyPath(newPath);
					changed = true;
				}
			}
			
		}

		//Handling Decision Table Rename/Move/Delete
		if (elementToRefactor instanceof DecisionTableElement) {
			DecisionTableElement tableElement = (DecisionTableElement) elementToRefactor;
			table = (Table) tableElement.getImplementation();
			if (isRenameRefactor()) {
				//Rename only if it is same table.
				if (decisionTableElement == tableElement) {
					table.setName(getNewElementName());
					changed = true;
				}
			} else if (isMoveRefactor()) {
				//Rename only if it is same table.
				if (decisionTableElement == tableElement) {
					table.setFolder(getNewElementPath());
					changed = true;
				}
			}
			else if (isDeleteRefactor()) {
				String vrfpath = table.getImplements();
				RuleElement ruleElement = IndexUtils.getRuleElement(fProjectName, vrfpath, ELEMENT_TYPES.RULE_FUNCTION);
				IFile ruleFile = IndexUtils.getFile(fProjectName, ruleElement);
				deletedDtCache.clear();
				deletedDtCache.put(table.getPath(), ruleFile);
			}
		}

		// Handling VRF refactoring
		if (elementToRefactor instanceof RuleElement) {
			table = (Table) decisionTableElement.getImplementation();
			RuleElement relement = (RuleElement) elementToRefactor;
			String folder = relement.getFolder();
			String tableImplements = table.getImplements();
			String ele = folder + relement.getName();
			if (tableImplements.equals(ele)) {
				if (isRenameRefactor()) {
					String rename = folder + getNewElementName();
					table.setImplements(rename);
				} else if (isMoveRefactor()) {
					table.setImplements(getNewElementPath()
							+ relement.getName());
				}
				Change change = createVRFRefactorChange(IndexUtils.getFile(
						fProjectName, decisionTableElement), table);
				compositeChange.add(change);
				return;
			}
		}

		if (changed) {
			Change change = createChange(IndexUtils.getFile(fProjectName,
					decisionTableElement), table, oldTable);
			compositeChange.add(change);
		}
	}
	
	
	private boolean refactorDecisionTableForProperties(String projectName, TableRuleSet table, Object elementToRefactor){
		boolean changed = false;
		Columns dtcolumns = table.getColumns();
		for (Column dtColumn : dtcolumns.getColumn()) {

			if (((PropertyDefinition) elementToRefactor).getFullPath().equals(dtColumn.getPropertyPath())) {
				dtColumn.setPropertyPath(
						dtColumn.getPropertyPath().replace(((PropertyDefinition) elementToRefactor).getName(), getNewElementName())
						);
				
				dtColumn.setName(
						dtColumn.getName().replace(((PropertyDefinition) elementToRefactor).getName(), getNewElementName())
						);
				changed = true;
			} else{
				if (dtColumn.getPropertyPath() == null) {
					continue;
				}
				int index = dtColumn.getPropertyPath().lastIndexOf("/");
				if (index < 0) {
					continue;
				}
				String concept = dtColumn.getPropertyPath().substring(0, index);
				Entity entity = null;
				
				DesignerElement designerElement = IndexUtils.getElement(projectName, concept);
				if (designerElement instanceof EntityElement) {
					EntityElement entityElement = (EntityElement)designerElement;
					entity = entityElement.getEntity();
				} 
				
				if (entity != null && entity instanceof Concept
						&& ((Concept)entity).getSuperConcept() != null 
						&& ((Concept)entity).getSuperConceptPath().trim().length() > 0) {
					List<PropertyDefinition> properties = ((Concept)entity).getAllProperties();
					for(PropertyDefinition property : properties){
						if(property.getOwnerPath().equals(((PropertyDefinition) elementToRefactor).getOwnerPath()) &&
								property.getName().equals(((PropertyDefinition) elementToRefactor).getName())){
							dtColumn.setPropertyPath(
									dtColumn.getPropertyPath().replace(((PropertyDefinition) elementToRefactor).getName(), getNewElementName())
									);
							
							dtColumn.setName(
									dtColumn.getName().replace(((PropertyDefinition) elementToRefactor).getName(), getNewElementName())
									);
							changed = true;
						}
					}					
				} else if (entity != null && entity instanceof Event
						&& ((Event)entity).getSuperEvent() != null 
						&& ((Event)entity).getSuperEventPath().trim().length() > 0) {
					List<PropertyDefinition> properties = ((Event)entity).getAllUserProperties();
					for(PropertyDefinition property : properties){
						if(property.getOwnerPath().equals(((PropertyDefinition) elementToRefactor).getOwnerPath()) &&
								property.getName().equals(((PropertyDefinition) elementToRefactor).getName())){
							dtColumn.setPropertyPath(
									dtColumn.getPropertyPath().replace(((PropertyDefinition) elementToRefactor).getName(), getNewElementName())
									);
							
							dtColumn.setName(
									dtColumn.getName().replace(((PropertyDefinition) elementToRefactor).getName(), getNewElementName())
									);
							changed = true;
						}
					}					
				}
			}
		}
		return changed;
	}

	/**
	 * Performs refactoring needed for decision table after the corresponding
	 * virtual rule function is changed Changes the implemented attribute
	 * related to the decision table
	 * 
	 * @param resource
	 *            {@link {@link IResource}
	 * @param newTable
	 *            {@link {@link Table}
	 * @return {@link Change}
	 */
	private Change createVRFRefactorChange(IResource resource, Table newTable) {
		// TODO Auto-generated method stub
		CompositeChange compositeChange = new CompositeChange("Changing Table. ");
		InputStream is = null;
		try {
			if (!(resource instanceof IFile)) {
				return compositeChange;
			}
			IFile file = (IFile) resource;
			String newName = newTable.getName();
			StudioCorePlugin.debug(CLASS, "New Name for Decision Table {0}", newName);
			is = file.getContents();
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder();
			int charCounter = 0;
			int c = -1;
			while ((c = inputStreamReader.read()) != -1) {
				// Since it is a char read cast it
				char character = (char) c;
				sb.append(character);
				if (isRenameRefactor()) {
					if (sb.indexOf(IMPLEMENTS_ATTR) != -1) {
						charCounter = charCounter + 1;
						Object ruleFunction_Old = getElementToRefactor();
						if (ruleFunction_Old instanceof RuleElement) {
							RuleElement oldRuleFunctionName = (RuleElement) ruleFunction_Old;
							performChange(oldRuleFunctionName.getFolder()
									+ getNewElementName(), oldRuleFunctionName
									.getFolder()
									+ oldRuleFunctionName.getName(), file,
									charCounter, compositeChange);
							break;
						}
					}
				}
				if (isMoveRefactor()) {
					if (sb.indexOf(IMPLEMENTS_ATTR) != -1) {
						charCounter = charCounter + 1;
						Object ruleFunction_Old = getElementToRefactor();
						if (ruleFunction_Old instanceof RuleElement) {
							RuleElement oldRuleFunctionName = (RuleElement) ruleFunction_Old;
							performChange(getNewElementPath()
									+ getNewElementName(), oldRuleFunctionName
									.getFolder()
									+ oldRuleFunctionName.getName(), file,
									charCounter, compositeChange);
							break;
						}

					}
				}
				charCounter++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				StudioCorePlugin.log(e);
			}
		}
		return compositeChange;
	}

	

	/**
	 * @param table
	 * @return
	 */
	private boolean processFolder(Table table) {
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
	private String getNewPath(String elementPath, IFolder folder) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName()
					+ elementPath.substring(oldPath.length() + offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath()
						.toString();
				return initChar + parentPath + "/" + getNewElementName()
						+ elementPath.substring(oldPath.length() + offset);
			}
			return initChar + getNewElementName()
					+ elementPath.substring(oldPath.length() + offset);
		}
		return elementPath;
	}

	/**
	 * Creates a <code>Change</code> object given the IFile and Table. Simply
	 * serializes the Table and creates a ReplaceEdit for the IFile. This is
	 * generally used after processing the table, to create the actual
	 * refactoring change to take place
	 * 
	 * @param file
	 * @param refactorParticipant
	 * @return
	 * @throws CoreException
	 */
	// This proves to be inefficient for large tables.
	// private Change createTextFileChange(IFile file, Table
	// refactorParticipant) throws CoreException {
	// refactorParticipant = (Table)
	// IndexUtils.getRootContainer(refactorParticipant);
	// byte[] objectContents;
	// TextFileChange change = null;
	// //MultiStateTextFileChange change = null;
	// InputStream fis = null;
	// try {
	// objectContents = IndexUtils.getEObjectContents(refactorParticipant);
	// String contents = new String(objectContents);
	//
	// change = new TextFileChange("New name change", file);
	// fis = file.getContents();
	// ReplaceEdit edit = new ReplaceEdit(0, fis.available(), contents);
	// change.setEdit(edit);
	// TextEditGroup editGroup = new TextEditGroup("Decision Table Changes");
	// int nameOffset = getAttributePosition(file, NAME_ATTR);
	// int folderOffset = getAttributePosition(file, FOLDER_ATTR);
	//			
	// TextEdit nameEdit =
	// new ReplaceEdit(nameOffset, NAME_ATTR.length(),
	// refactorParticipant.getName());
	// TextEdit folderEdit =
	// new ReplaceEdit(folderOffset, FOLDER_ATTR.length(),
	// refactorParticipant.getFolder());
	// editGroup.addTextEdit(nameEdit);
	// editGroup.addTextEdit(folderEdit);
	//			
	// change = new MultiStateTextFileChange("Changes", file);
	// change.addTextEditGroup(editGroup);
	// return change;
	//			
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (fis != null) {
	// fis.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return change;
	// }
	//	
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
		if (extensions == null
				&& (file.getFileExtension() == null || file.getFileExtension()
						.length() == 0)) {
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


	private static final String IMPLEMENTS_ATTR = "implements=\"";
	
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	/*private Change createChange(IResource resource, Table newTable) {
		CompositeChange compositeChange = new CompositeChange(
				"Changing Table. ");
		InputStream is = null;
		try {
			if (!(resource instanceof IFile)) {
				return compositeChange;
			}
			IFile file = (IFile) resource;
			IPath fullResourcePath = ((IFile) resource).getFullPath().removeFileExtension();
			String oldName = fullResourcePath.lastSegment();
			String oldFolderPath = "/" + fullResourcePath.removeFirstSegments(1).removeLastSegments(1).addTrailingSeparator().toString();		

			String newName = newTable.getName();
			//By this time the folder name would have been udpated.
			String newFolderPath = newTable.getFolder();
			
			StudioCorePlugin.debug(CLASS, "New Name for Decision Table {0}", newName);
			
			is = file.getContents();
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder();
			int charCounter = 0;
			int c = -1;
			int lastIndexOfChange = -1;
			int readWriteMismatchOffset = 0;
			while ((c = inputStreamReader.read()) != -1) {
				// Since it is a char read cast it
				char character = (char) c;
				sb.append(character);
				if (isRenameRefactor()) {
					if (sb.indexOf(NAME_ATTR) != -1) {
						// Note this position
						// Increment the position by 1
						charCounter = charCounter + 1;
						performChange(newName, oldName, file, charCounter, compositeChange);						
						break;
					}
				}
				//DT Moved to different folder.
				if (isMoveRefactor()) {
					if (sb.indexOf(FOLDER_ATTR) != -1) {
						// Note this position
						// Increment the position by 1
						charCounter = charCounter + 1;
						performChange(newFolderPath, oldFolderPath, file, charCounter, compositeChange);
						break;
					}
					Object elementToRefactor = getElementToRefactor();
					if (elementToRefactor instanceof EntityElement) {
						Entity entity = ((EntityElement) elementToRefactor).getEntity();
						if (entity instanceof Concept
								|| entity instanceof Event) {
							
							String oldElementPath = "";
							if (entity instanceof Concept) {
								Concept concept = (Concept)entity;
								oldElementPath = concept.getFullPath();
							} else if (entity instanceof Event) {
								Event event = (Event)entity;
								oldElementPath = event.getFullPath();
							} 
							int indexOfColumnChange = sb.lastIndexOf(COLUMN_PATH_ATTR);
							int indexOfArgChange = sb.lastIndexOf(ARG_PATH_ATTR);
							if (indexOfColumnChange > lastIndexOfChange || indexOfArgChange > lastIndexOfChange) {
								int indexToReplaceFrom = -1;
								lastIndexOfChange = Math.max(indexOfArgChange, indexOfColumnChange);
								if (indexOfColumnChange == lastIndexOfChange) {
									indexToReplaceFrom = lastIndexOfChange+COLUMN_PATH_ATTR.length();
								} else {
									indexToReplaceFrom = lastIndexOfChange+ARG_PATH_ATTR.length();
								}
								// get old string to replace
								StringBuffer strBuff = new StringBuffer();
								while((c = inputStreamReader.read()) != -1 && c != '"') {charCounter++;sb.append((char)c);strBuff.append((char)c);}
								charCounter++;
								sb.append((char)c);
								String prop = "";
								if (strBuff.indexOf(oldElementPath) == 0)	{
									prop = strBuff.substring(oldElementPath.length());
									String strToReplace = getNewElementPath()+entity.getName()+prop;
									String strToFind = strBuff.toString();
									performChange(strToReplace, strToFind, file, indexToReplaceFrom+readWriteMismatchOffset, compositeChange);
									readWriteMismatchOffset += strToReplace.length() - strToFind.length();
								}
							}
						}
					}
				}
				charCounter++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				StudioCorePlugin.log(e);
			}
		}
		return compositeChange;
	}
*/
	
	private Change createChange(final IFile file, final Table newTable, final Table oldTable){
		String newName = newTable.getName();			
		StudioCorePlugin.debug(CLASS, "New Name for Decision Table {0}", newName);		
		  return new CompositeChange("Decision table :"+ newName){

			@Override
			public Change perform(IProgressMonitor pm) throws CoreException {
				URI uri = URI.createFileURI(file.getLocation().toString());
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.createResource(uri);
				resource.getContents().add(newTable);
				
				Map<String, Object> options = new HashMap<String, Object>();
				options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
				options.put(XMIResource.OPTION_ENCODING, ModelUtils.DEFAULT_ENCODING); // G11N encoding changes

				try {
					resource.save(options);
					file.refreshLocal(IFile.DEPTH_INFINITE, null);
				} catch (IOException e) { 
					StudioCorePlugin.log(e);
				} catch (CoreException e) {
					StudioCorePlugin.log(e);
				}
				return undoChange(file, oldTable);
			}
			
		};
	}
	
	private Change undoChange(final IFile file, final Table oldTable){
		return new CompositeChange("Decision table :"+ oldTable.getName()){

			@Override
			public Change perform(IProgressMonitor pm) throws CoreException {
				URI uri = URI.createFileURI(file.getFullPath().toString());
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.createResource(uri);
				resource.getContents().add(oldTable);
				
				Map<String, Object> options = new HashMap<String, Object>();
				options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
				options.put(XMIResource.OPTION_ENCODING, ModelUtils.DEFAULT_ENCODING); // G11N encoding changes

				try {
					resource.save(options);
					file.refreshLocal(IFile.DEPTH_INFINITE, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		};
	}
	
	/**
	 * Perform actual change for updating Table attribute
	 * 
	 * @param newString
	 * @param oldString
	 * @param file
	 * @param offset
	 * @param compositeChange
	 */
	private void performChange(String newString, String oldString, IFile file,
			int offset, CompositeChange compositeChange) {

		int oldStringLength = oldString.length();
		int newStringLength = newString.length();
		if (newStringLength > oldStringLength) {
			NoPreviewTextFileChange insertChange = new NoPreviewTextFileChange("New name change",
					file);
			NoPreviewTextFileChange deleteChange = new NoPreviewTextFileChange(
					"Remove Old name change", file);
			newString = newString + "\"";

			InsertEdit insertEdit = new InsertEdit(offset, newString);
			insertChange.setEdit(insertEdit);
			compositeChange.add(insertChange);
			DeleteEdit deleteEdit = new DeleteEdit(offset + newString.length(),
					oldStringLength + 1);
			deleteChange.setEdit(deleteEdit);
			compositeChange.add(deleteChange);
		} else {
			NoPreviewTextFileChange deleteChange = new NoPreviewTextFileChange(
					"Remove Old name change", file);
			NoPreviewTextFileChange insertChange = new NoPreviewTextFileChange("New name change",
					file);
			DeleteEdit deleteEdit = new DeleteEdit(offset, oldStringLength);
			deleteChange.setEdit(deleteEdit);
			compositeChange.add(deleteChange);
			InsertEdit insertEdit = new InsertEdit(offset, newString);
			insertChange.setEdit(insertEdit);
			compositeChange.add(insertChange);
		}
	}

	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		if (resource instanceof IFile
				&& IndexUtils.isImplementationType((IFile) resource)) {
			return true;
		}
		return false;
	}

	@Override
	public IStatus pasteElement(String newName, IResource elementToPaste,
			IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (!(elementToPaste instanceof IFile)) {
			new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID,
					"Element is not a file");
		}
		
		IFile source = (IFile) elementToPaste;
		DesignerElement element = IndexUtils.getElement(source);
		if (!(element instanceof DecisionTableElement)) {
			new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID,
					"Element is not a Decision Table Element");
		}

		
		String oldName = source.getFullPath().removeFileExtension().lastSegment();
			
		IFile newFile = null;
		InputStream fis = null;
		InputStream contents = null;
		try {
			String newRuleName = "";
			contents = source.getContents();
			byte[] existingContents = new byte[contents.available()];
			contents.read(existingContents);
			String fileContents = new String(existingContents,
					ModelUtils.DEFAULT_ENCODING);

			if (newName == null) {
				return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID,
						"New element name cannot be null");
			}
			newRuleName = newName;
			int idx = newName.lastIndexOf('.');
			if (idx > 0) {
				newRuleName = newName.substring(0, idx);
			}
			newFile = target.getFile(new Path(newName));
			// if (source.getParent().equals(target)) {
			// newFile = target.getFile(new Path(source.getName()));
			// }

			fileContents = fileContents.replaceFirst(oldName, newRuleName);
			IDocument doc = new Document(fileContents);
			fis = new ByteArrayInputStream(doc.get().getBytes(
					source.getCharset()));
			if (newFile.exists()) {
				if (overwrite) {
					newFile.setContents(fis, IResource.FORCE,
							new NullProgressMonitor());
				}
			} else {
				newFile.create(fis, false, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		} catch (IOException e) {
			StudioCorePlugin.log(e);
		} catch (MalformedTreeException e) {
			StudioCorePlugin.log(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (contents != null) {
					contents.close();
				}
			} catch (IOException e) {
				StudioCorePlugin.log(e);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus validateName(IResource resource, IContainer target,
			String newName) {
		if (!EntityNameHelper.isValidBEEntityIdentifier(newName)) {
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID,
					newName + " is not a valid BE identifier");
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