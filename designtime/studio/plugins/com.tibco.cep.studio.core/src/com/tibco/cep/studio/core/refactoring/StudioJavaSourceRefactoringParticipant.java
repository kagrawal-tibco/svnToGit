package com.tibco.cep.studio.core.refactoring;

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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioJavaSourceRefactoringParticipant extends StudioRefactoringParticipant implements IStudioPasteParticipant {

	protected boolean changed = false;

	public StudioJavaSourceRefactoringParticipant() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
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
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();

		if (!(CommonIndexUtils.JAVA_EXTENSION.equalsIgnoreCase(file.getFileExtension()))) {
			return null;//new NullChange();
		}

		ICompilationUnit unit = JavaCore.createCompilationUnitFrom(file);
		
		if (StudioJavaUtil.isAnnotatedJavaTaskSource(file, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK)) {
			return null;
		}

		Object elementToRefactor = getElementToRefactor();
		String name = resource.getName().replace(CommonIndexUtils.DOT + CommonIndexUtils.JAVA_EXTENSION , "");
		CompositeChange change = new CompositeChange("Changes to "+ name);
		processJavaElement(fProjectName, change, file, unit, elementToRefactor);

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
				|| CommonIndexUtils.JAVA_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
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
		return processJavaElement(elementToRefactor, fProjectName, pm);
	}

	/**
	 * @param elementToRefactor
	 * @param projectName
	 * @param pm
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	protected Change processJavaElement(Object elementToRefactor,
			String projectName,
			IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		if (!shouldUpdateReferences()) {
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Java Resource changes:");
		List<IFile> javaFilesList = new ArrayList<IFile>();
		CommonUtil.getResourcesByExtension(getResource().getProject(), CommonIndexUtils.JAVA_EXTENSION, javaFilesList);
		for (IFile element: javaFilesList) {
			if (isFileProcessed(elementToRefactor, element)){
				// already processed in the pre-change
				continue;
			}
			if (isJavaFile(elementToRefactor))
				return null;

			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(element);
			if (checkForAnnotatedJavaTask(element)) {
				continue;
			}
			processJavaElement(fProjectName, compositeChange, element, cu, elementToRefactor);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}
	
	protected boolean checkForAnnotatedJavaTask(IFile element) {
		return StudioJavaUtil.isAnnotatedJavaTaskSource(element, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK);
	}

	
	/**
	 * @param projectName
	 * @param compositeChange
	 * @param javaFile
	 * @param workingCopy
	 * @param elementToRefactor
	 * @throws CoreException
	 */
	protected void processJavaElement(String projectName, 
			CompositeChange compositeChange, 
			IFile javaFile,
			ICompilationUnit workingCopy, 
			Object elementToRefactor) throws CoreException {

		workingCopy.becomeWorkingCopy(new NullProgressMonitor());
		
		String newFullPath = "";
		String oldFullPath = "";
		String oldPackageName = "";
		String newPackageName = "";
		
		if (elementToRefactor instanceof ProcessElement) {
			ProcessElement refactoredElement = (ProcessElement)elementToRefactor;
			oldFullPath = refactoredElement.getFolder() + refactoredElement.getName();
			if (isRenameRefactor()) {
				newFullPath = refactoredElement.getFolder() + getNewElementName();
			} else if (isMoveRefactor()){
				newFullPath = getNewElementPath() + refactoredElement.getName();
			}
		}		
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement)elementToRefactor).getEntity();
		}
		if (elementToRefactor instanceof JavaSource || isFolderRefactor()) {
			JavaSource refactoredElement = (JavaSource)elementToRefactor;
			oldFullPath = refactoredElement.getFolder() + refactoredElement.getName();
			oldPackageName = refactoredElement.getPackageName();
			if (isRenameRefactor()) {
				newFullPath = refactoredElement.getFolder() + getNewElementName();
			} else if (isMoveRefactor()) {
				newFullPath = getNewElementPath() + refactoredElement.getName();
				StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
				for (JavaSourceFolderEntry entry : pconfig.getJavaSourceFolderEntries()) {
					String path = entry.getPath().replace("/" + projectName, "");
					if (newFullPath.startsWith(path)) {
						newPackageName =  getNewElementPath().replace(path, "");
						newPackageName = newPackageName.substring(1).replace("/", CommonIndexUtils.DOT);
						newPackageName = newPackageName.substring(0, newPackageName.length() - 1);
						refactoredElement.setPackageName(newPackageName);
					}
				}
			}
		}
		if (elementToRefactor instanceof Concept || isFolderRefactor()) {
			if (elementToRefactor instanceof Concept) {
				Concept refactoredElement = (Concept) elementToRefactor;
				oldFullPath = refactoredElement.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + refactoredElement.getName();
				}
			}
		}
		
//		if (elementToRefactor instanceof Concept || isFolderRefactor()) {
//			IFolder folder = (IFolder) getResource();
//			if (IndexUtils.startsWithPath(concept.getParentConceptPath(), folder)) {
//				String newPath = getNewPath(concept.getParentConceptPath(), folder);
//				concept.setSuperConceptPath(newPath);
//				changed = true;
//			}
//		} else {
//			if (concept.getParentConceptPath() != null && concept.getParentConceptPath().equalsIgnoreCase(oldFullPath)) {
//				concept.setParentConceptPath(newFullPath);
//				changed = true;
//			}
//		}

		changeJavaSource(javaFile, workingCopy, compositeChange, oldFullPath, newFullPath, oldPackageName, newPackageName);

	}

	/**
	 * @param file
	 * @param workingCopy
	 * @param compositeChange
	 * @param oldFullPath
	 * @param newFullPath
	 */
	protected void changeJavaSource(IFile file, 
			ICompilationUnit workingCopy, 
			CompositeChange compositeChange, 
			final String oldFullPath, 
			final String newFullPath, 
			final String oldPackageName, 
			final String newPackageName) {

		InputStream fis = null;
		try {
			changed = false;
			String source = workingCopy.getSource();
			Document document= new Document(source);

			// creation of DOM/AST from a ICompilationUnit
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(workingCopy);
			CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);

			// start record of the modifications
			astRoot.recordModifications();

			
			//for (Object obj : astRoot.types()) {
			TypeDeclaration typeDeclaration = (TypeDeclaration)astRoot.types().get(0);
			
			SimpleName name = typeDeclaration.getName();
			String oldClassName = getOldElementName();
			if ( isRenameRefactor() && oldClassName != null && oldClassName.equals(name.toString())) {
				SimpleName newName = astRoot.getAST().newSimpleName(getNewElementName());
				typeDeclaration.setName(newName);
				changed = true;
			}
			
			if (isMoveRefactor() && !newPackageName.isEmpty() && oldClassName != null && oldClassName.equals(name.toString())) {
//				String p = astRoot.getAST().newPackageDeclaration().getName().toString();
				if(astRoot.getPackage() == null ){
					PackageDeclaration packageDeclaration = astRoot.getAST().newPackageDeclaration();
					astRoot.setPackage(packageDeclaration);
					try {
						astRoot.getPackage().setName(
								astRoot.getAST().newName(newPackageName));
					} catch (Exception e) {
						System.out.println("Cannot set name");
					}
					changed = true;
				}
				else if (astRoot.getPackage()!= null && astRoot.getPackage().getName().toString().equals(oldPackageName)) {
					
					astRoot.getPackage().setName(astRoot.getAST().newName(newPackageName));
					changed = true;
				}
			}
			
			refactorAnnotatedElement(typeDeclaration, oldFullPath, newFullPath);

			workingCopy.discardWorkingCopy();
			if (!changed) {
				return;
			}

			TextEdit edits = astRoot.rewrite(document, workingCopy.getJavaProject().getOptions(true));
			edits.apply(document);
			
			String newSource = document.get();
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, newSource);
			
			TextFileChange change = new TextFileChange("Change", file);
			change.setEdit(edit);
			compositeChange.add(change);

//			// update of the compilation unit
//			workingCopy.getBuffer().setContents(newSource);
//			workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
//			// Commit changes
//			workingCopy.commitWorkingCopy(false, null);
//			// Destroy working copy
//			workingCopy.discardWorkingCopy();

		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}  catch (CoreException e) {
			e.printStackTrace();
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

	}
	
	
	/**
	 * @param typeDeclaration
	 * @param oldFullPath
	 * @param newFullPath
	 */
	protected void refactorAnnotatedElement(final TypeDeclaration typeDeclaration, 
			final String oldFullPath, 
			final String newFullPath) {
		//Override this
	}
	
	/**
	 * @param elementToRefactor
	 * @return
	 */
	protected boolean isJavaFile(Object elementToRefactor) {
		if (elementToRefactor instanceof StudioRefactoringContext) {
			StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
			if (context.getElement() instanceof IFile) {
				IFile file = (IFile)context.getElement();
				if (file.getFileExtension().equalsIgnoreCase(CommonIndexUtils.JAVA_EXTENSION))
					return true;
			}
		}
		return false;
	}

	/**
	 * @param elementToRefactor
	 * @param element
	 * @return
	 */
	protected boolean isFileProcessed(Object elementToRefactor, IResource element){
		if (elementToRefactor instanceof StudioRefactoringContext) {
			StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
			if (context.getElement() instanceof IFile) {
				IFile file = (IFile)context.getElement();
				if(file.getFullPath().toString().equals(element.getFullPath().toString())) {
					return true;
				}
			}
		} else if (elementToRefactor instanceof JavaElement) {
			JavaElement el = (JavaElement) elementToRefactor;
			Entity entity = el.getEntity();
			IFile file = IndexUtils.getFile(entity.getOwnerProjectName(), entity);
			if (element.equals(file)) {
				return true;
			}
		}
		return false;
	}



	@Override
	public IStatus pasteElement(String newName, IResource elementToPaste,
			IContainer target, boolean overwrite, IProgressMonitor pm)
					throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IStatus validateName(IResource resource, IContainer target,
			String newName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProjectPaste(boolean projectPaste) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isProjectPaste() {
		return false;
	}

	@Override
	public String getName() {
		return "Java Refactoring participant";
	}

}