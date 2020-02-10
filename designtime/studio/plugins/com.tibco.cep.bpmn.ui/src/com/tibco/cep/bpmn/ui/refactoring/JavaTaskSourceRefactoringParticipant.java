package com.tibco.cep.bpmn.ui.refactoring;

import java.lang.reflect.Modifier;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.refactoring.StudioJavaSourceRefactoringParticipant;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class JavaTaskSourceRefactoringParticipant extends StudioJavaSourceRefactoringParticipant {

	public JavaTaskSourceRefactoringParticipant() {
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
		if (!StudioJavaUtil.isAnnotatedJavaTaskSource(file, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK)) {
			return null;
		}

		Object elementToRefactor = getElementToRefactor();
		String name = resource.getName().replace(CommonIndexUtils.DOT + CommonIndexUtils.JAVA_EXTENSION , "");
		CompositeChange change = new CompositeChange("Changes to "+ name);
		processJavaElement(fProjectName, change, file, unit, elementToRefactor);

		return change;
	}
	
	@Override
	protected boolean checkForAnnotatedJavaTask(IFile element) {
		return !StudioJavaUtil.isAnnotatedJavaTaskSource(element, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.StudioJavaSourceRefactoringParticipant#refactorAnnotatedElement(org.eclipse.jdt.core.dom.TypeDeclaration, java.lang.String, java.lang.String)
	 */
	protected void refactorAnnotatedElement(final TypeDeclaration typeDeclaration, 
											final String oldFullPath, 
											final String newFullPath) {
		for (final MethodDeclaration methodDecl:typeDeclaration.getMethods()) {
			if (methodDecl.getModifiers() ==  Modifier.PUBLIC) {
				
				methodDecl.accept(new ASTVisitor() {

					/* (non-Javadoc)
					 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MarkerAnnotation)
					 */
					public boolean visit(MarkerAnnotation annotation) {
						if (annotation.getTypeName().toString().equals(BpmnCommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_METHOD_TASK)) {
							
							methodDecl.accept(new ASTVisitor() {
								
								/* (non-Javadoc)
								 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SingleMemberAnnotation)
								 */
								public boolean 	visit(SingleMemberAnnotation annotation) {
									return true;
								}
							
								/* (non-Javadoc)
								 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.NormalAnnotation)
								 */
								public boolean visit(NormalAnnotation annotation) {
									if (annotation.getTypeName().toString().equals(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE_MAP)) {
										
										@SuppressWarnings("unchecked")
										List<MemberValuePair> list = annotation.values();
										for (MemberValuePair pair : list) {
											if (pair.getName().toString().equals("uri")) {
												String val = pair.getValue().toString().replace("\"", "").trim();
												if (val.equals(oldFullPath)) {
													changed =  true;
													pair.getValue().accept(new ASTVisitor() {
								
														/* (non-Javadoc)
														 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.StringLiteral)
														 */
														@Override
														public boolean visit(StringLiteral node) {
															node.setLiteralValue(newFullPath);
															return true;
														}
													});
												}
											}
										}
									}
									return true;
								}

							});
						}
						return true;
					}
				});
			}
		}
	}
	
	
	@Override
	public String getName() {
		return "Java Task Refactoring participant";
	}

}