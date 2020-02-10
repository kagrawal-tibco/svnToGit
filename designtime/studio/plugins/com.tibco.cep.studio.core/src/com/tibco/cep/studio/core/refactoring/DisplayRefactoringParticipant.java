package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.common.util.EntityNameHelper.isValidBEEntityIdentifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.FileStatusContext;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.resource.MoveResourceChange;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.MultiSearchParticipant;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.core.util.DisplayUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class DisplayRefactoringParticipant extends StudioRefactoringParticipant implements IStudioPasteParticipant {

	public DisplayRefactoringParticipant() {
	}

	@Override
	protected ISearchParticipant getSearchParticipant() {
		MultiSearchParticipant participant = new MultiSearchParticipant();
		return participant;
	}

	@Override
	protected RefactoringStatusContext createRefactoringStatusContext(
			EObject data) {
		IFile file = SearchUtils.getFile(data);
		IRegion region = null;
		if (data instanceof ElementMatch) {
			data = ((ElementMatch)data).getMatchedElement();
		}
		if (data instanceof ElementReference) {
			ElementReference ref = (ElementReference) data;
			region = new Region(ref.getOffset(), ref.getLength());
		} else if (data instanceof VariableDefinition) {
			VariableDefinition ref = (VariableDefinition) data;
			region = new Region(ref.getOffset(), ref.getLength());
		}
		if (file != null) {
			FileStatusContext context = new FileStatusContext(file, region);
			return context;
		}
		return null;
	}

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
				|| CommonIndexUtils.DISPLAY_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			return status;
		}

		return status;
	}

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
			return processDisplayModel(elementToRefactor, fProjectName, pm);
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		if (IndexUtils.getFileType(file) == null) {
			return null; // object is not an entity
		}
		if (!(CommonIndexUtils.DISPLAY_EXTENSION.equalsIgnoreCase(file.getFileExtension()))) {
			return null;
		}
		Object elementToRefactor = getElementToRefactor();
		DesignerElement element = IndexUtils.getElement(file);
		if (!(element instanceof EntityElement)) {
			return null;
		}
		CompositeChange change = new CompositeChange("Changes to "+element.getName());
//		processRuleElement(fProjectName, change, (RuleElement) element, elementToRefactor);
		return change;
		
	}

	@Override
	public String getName() {
		return "Display Model rename participant";
	}

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
		return processDisplayModel(elementToRefactor, fProjectName, pm);
	}

	public Change processDisplayModel(Object elementToRefactor, String projectName, IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		// look through all display models and make appropriate changes
		if (!shouldUpdateReferences()) {
			return null;
		}
		try{
		CompositeChange compositeChange = new CompositeChange("Display Model changes:");
		Object el = elementToRefactor;
		if (el instanceof EntityElement) {
			el = ((EntityElement) el).getEntity();
		}
		if (el instanceof Entity) {
			if (el instanceof PropertyDefinition) {
				el = ((PropertyDefinition) el).getOwner();
			}
			boolean checkAncestors = isRenameRefactor() && !(el instanceof Concept || el instanceof Event);
			IFile[] displayModelFiles = DisplayUtils.getDisplayModelFilesForEntity((Entity)el, checkAncestors);
			for (IFile dispModelFile : displayModelFiles) {
				processDisplayModelFile(dispModelFile, compositeChange, pm);
			}
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		}catch(Exception e){
		 System.out.println("DisplayModel refactoring exception");	
		}
		return null;
	}
	
	private void processDisplayModelFile(IFile dispModelFile,
			CompositeChange compositeChange, IProgressMonitor pm) {
		Object elementToRefactor = getElementToRefactor();
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!(elementToRefactor instanceof Entity)) {
			return;
		}
		Entity entity = (Entity) elementToRefactor;
		if ((entity instanceof Concept || entity instanceof Event)) {
			if (isMoveRefactor()) {
				IContainer folder = null;
				String newPath = getNewElementPath();
				if (newPath == null || newPath.length() == 0) {
					folder = dispModelFile.getProject();
				} else {
					folder = dispModelFile.getProject().getFolder(newPath);
				}
				MoveResourceChange c = new MoveResourceChange(dispModelFile, folder);
				compositeChange.add(c);
			} else if (isRenameRefactor()) {
				IPath fullPath = dispModelFile.getFullPath();
				String fileName = fullPath.removeFileExtension().lastSegment();
				int idx = fileName.indexOf('_');
				String suffix = "";
				if (idx >= 0) {
					suffix = fileName.substring(idx);
				}
				String newName = getNewElementName() + suffix;
				RenameResourceChange c = new RenameResourceChange(fullPath, newName+'.'+fullPath.getFileExtension());
				compositeChange.add(c);
			}
			return;
		}
		Properties props = new Properties();
		InputStream contents = null;
		try {
			contents = dispModelFile.getContents();
			int fileSize = contents.available();
			props.load(contents);
			Set<Object> keySet = props.keySet();
			Properties newProps = (Properties) props.clone();
			boolean changed = false;
			for (Object keyObj : keySet) {
				String key = (String) keyObj;
				String[] split = key.split("\\.");
				String propName = split[0];
				if (entity.getName().equals(propName)) {
					String newElementName = getNewElementName();
					String newProp = newElementName + '.' + split[1];
					Object remove = newProps.remove(key);
					newProps.put(newProp, remove);
					changed = true;
				}
			}
			if (changed) {
				StringWriter writer = new StringWriter();
				newProps.store(writer, DisplayUtils.DEFAULT_COMMENT);
				TextEdit edit = new ReplaceEdit(0, fileSize, writer.getBuffer().toString());
				TextFileChange c = new TextFileChange("Display Model change", dispModelFile);
				c.setEdit(edit);
				compositeChange.add(c);
			}
		} catch (IOException | CoreException | NullPointerException e) {
			StudioCorePlugin.log(e);
		} finally {
			if (contents != null) {
				try {
					contents.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		if (resource instanceof IFile && IndexUtils.isRuleType((IFile)resource)) {
			return true;
		}
		return false;
	}

	@Override
	public IStatus pasteElement(String newName, IResource elementToPaste,
			IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (!(elementToPaste instanceof IFile)) {
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, "Element is not a file, cannot paste");
		}
		pasteRule((IFile) elementToPaste, target, newName, overwrite);
		return Status.OK_STATUS;
	}

	private void pasteRule(IFile source, IContainer target, String newName, boolean overwrite) {
		DesignerElement element = IndexUtils.getElement(source);
		if (!(element instanceof RuleElement)) {
			return;
		}
		RuleElement ruleElement = (RuleElement) element;
		ElementReference definitionRef = ruleElement.getScope().getDefinitionRef();
//		String newName = source.getFullPath().removeFileExtension().lastSegment();
		String newPath = target.getFullPath().removeFirstSegments(1).toString();
		if (newName == null) {
			return;
		}
		String newRuleName = newName;
		int idx = newName.lastIndexOf('.');
		if (idx > 0) {
			newRuleName = newName.substring(0, idx);
		}
		String newPackage = ModelUtils.convertPathToPackage(newPath+"/"+newRuleName);
		IFile newFile = null;
		InputStream fis = null;
		InputStream contents = null;
		try {
			contents = source.getContents();
			byte[] existingContents = new byte[contents.available()];
			contents.read(existingContents);
			IDocument doc = new Document(new String(existingContents, ModelUtils.DEFAULT_ENCODING));
			ReplaceEdit edit = null;

			edit = new ReplaceEdit(definitionRef.getOffset(), definitionRef.getLength(), newRuleName);
			newFile = target.getFile(new Path(newName));
			if (!source.getParent().equals(target)) {
				ElementReference rootRef = definitionRef;
				while (rootRef.getQualifier() != null) {
					rootRef = rootRef.getQualifier();
				}
				int offset = rootRef.getOffset();
				edit = new ReplaceEdit(offset, definitionRef.getOffset()+definitionRef.getLength()-offset, newPackage);
				newFile = target.getFile(new Path(newName));
			}
			if (edit != null) {
				edit.apply(doc);
				fis = new ByteArrayInputStream(doc.get().getBytes(source.getCharset()));
				if (newFile.exists()) {
					// prompt for overwrite
					if (overwrite) {
						newFile.setContents(fis, IResource.FORCE, new NullProgressMonitor());
					}
				} else {
					newFile.create(fis, false, new NullProgressMonitor());
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (contents != null) {
					contents.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public IStatus validateName(IResource resource, IContainer target, String newName) {
		if (isValidBEEntityIdentifier(newName)) {
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
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