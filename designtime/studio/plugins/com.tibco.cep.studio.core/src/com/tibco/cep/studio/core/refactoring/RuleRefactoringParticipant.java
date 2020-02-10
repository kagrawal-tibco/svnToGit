package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.common.util.EntityNameHelper.isValidBEEntityIdentifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.StringLiteralMatch;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.MultiSearchParticipant;
import com.tibco.cep.studio.core.search.RuleSearchParticipant;
import com.tibco.cep.studio.core.search.RuleTemplateSearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class RuleRefactoringParticipant extends StudioRefactoringParticipant implements IStudioPasteParticipant {

	protected RuleRefactoringHelper fHelper;

	public RuleRefactoringParticipant() {
	}

	@Override
	protected ISearchParticipant getSearchParticipant() {
		MultiSearchParticipant participant = new MultiSearchParticipant();
		participant.addParticipant(new RuleSearchParticipant());
		participant.addParticipant(new RuleTemplateSearchParticipant());
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
		} else if (data instanceof StringLiteralMatch) {
			StringLiteralMatch ref = (StringLiteralMatch) data;
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
		fHelper = null;
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
				|| CommonIndexUtils.RULE_EXTENSION.equalsIgnoreCase(file.getFileExtension())
				|| CommonIndexUtils.RULE_TEMPLATE_EXTENSION.equalsIgnoreCase(file.getFileExtension())
				|| CommonIndexUtils.RULEFUNCTION_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
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
			return processEntity(elementToRefactor, fProjectName, pm);
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();
		if (IndexUtils.getFileType(file) == null) {
			return null; // object is not an entity
		}
		if (!(CommonIndexUtils.RULE_EXTENSION.equalsIgnoreCase(file.getFileExtension())
				|| CommonIndexUtils.RULE_TEMPLATE_EXTENSION.equalsIgnoreCase(file.getFileExtension())
				|| CommonIndexUtils.RULEFUNCTION_EXTENSION.equalsIgnoreCase(file.getFileExtension()))) {
			return null;//new NullChange();
		}
		Object elementToRefactor = getElementToRefactor();
		DesignerElement element = IndexUtils.getElement(file);
		if (!(element instanceof RuleElement)) {
			return null;
		}
		CompositeChange change = new CompositeChange("Changes to "+element.getName());
		processRuleElement(fProjectName, change, (RuleElement) element, elementToRefactor);
		return change;
		
	}

	@Override
	public String getName() {
		return "Entity rename participant";
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
		return processEntity(elementToRefactor, fProjectName, pm);
	}

	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		// look through all rules and make appropriate changes
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Rule changes:");
		List<DesignerElement> allRules = IndexUtils.getAllElements(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE, ELEMENT_TYPES.RULE_FUNCTION, ELEMENT_TYPES.RULE_TEMPLATE });
		for (DesignerElement element : allRules) {
			if (element == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processRuleElement(projectName, compositeChange, (RuleElement) element, elementToRefactor);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}
	
	private void processRuleElement(String projectName,
			CompositeChange compositeChange, RuleElement ruleElement, Object elementToRefactor) {
		if (isContainedDelete(IndexUtils.getFile(fProjectName, ruleElement))) {
			return;
		}

		String nameToFind = getElementName(elementToRefactor);
		List<ElementReference> foundReferences = new ArrayList<ElementReference>();
		List<EObject> foundDefinitions = new ArrayList<EObject>();
		RuleSearchParticipant participant = new RuleSearchParticipant();
		participant.searchRuleElement(elementToRefactor, nameToFind, foundReferences, foundDefinitions, ruleElement);
		
		// filter results
		List<ElementReference> inexactReferences = new ArrayList<ElementReference>();
		List<ElementReference> filteredReferences = new ArrayList<ElementReference>();
		List<EObject> filteredDefinitions = new ArrayList<EObject>();
		for (EObject definition : foundDefinitions) {
			if (participant.isEqual(definition, elementToRefactor)) {
				filteredDefinitions.add(definition);
			}
		}
		SearchResult result = new SearchResult();
		List<IFile> processedFiles = new ArrayList<IFile>();
		for (ElementReference reference : foundReferences) {
			Object element = ElementReferenceResolver.resolveElement(reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference)));
			if (element != null && participant.isEqual(element, elementToRefactor)) {
				filteredReferences.add(reference);
			} else if (element instanceof JavaStaticFunctionWithXSLT) {
				// if we find any mapper functions, search all of them in one pass rather than individually
				participant.processFunctionsForFile(reference, elementToRefactor, projectName, result, processedFiles);
			} else if (element == null) {
				if (elementToRefactor instanceof RuleElement) {
					if (((RuleElement) elementToRefactor).getScope().getDefinitionRef() == reference) {
						filteredReferences.add(reference);
					} else {
						inexactReferences.add(reference);
					}
				} else {
					inexactReferences.add(reference);
				}
			}
		}
		
		MultiTextEdit edit = new MultiTextEdit();
		boolean changed = false;
		String newName = getNewElementName();
		String newFolder = getNewElementPath();
		if (getHelper().processDefinitions(filteredDefinitions, edit, ruleElement, newFolder, newName)) changed = true;
		if (getHelper().processReferences(filteredReferences, edit, ruleElement, newFolder, newName, elementToRefactor)) changed = true;
//		if (processReferences(inexactReferences, edit, ruleElement, newFolder, newName)) changed = true;
		if (result.getExactMatches().size() > 0) {
			if (getHelper().processSearchResult(result, edit, ruleElement, newFolder, newName, elementToRefactor)) changed = true;
		}

		if (changed) {
			IFile file = IndexUtils.getFile(fProjectName, ruleElement);
			if (file != null) {
				TextFileChange change = new TextFileChange(ruleElement.getName(), file);
				change.setEdit(edit);
				compositeChange.add(change);
			}
		}
	}

	private RuleRefactoringHelper getHelper() {
		if (fHelper == null) {
			this.fHelper = new RuleRefactoringHelper(isMoveRefactor(), getOldElementName(), getOldElementPath());
		}
		return fHelper;
	}

	private String getElementName(Object elementToRefactor) {
		if (elementToRefactor instanceof DesignerElement) {
			return ((DesignerElement) elementToRefactor).getName();
		}
		if (elementToRefactor instanceof Entity) {
			return ((Entity) elementToRefactor).getName();
		}
		return "";
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