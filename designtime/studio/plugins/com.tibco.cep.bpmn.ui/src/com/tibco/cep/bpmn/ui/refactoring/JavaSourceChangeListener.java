package com.tibco.cep.bpmn.ui.refactoring;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportContainer;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.swt.widgets.Display;

public class JavaSourceChangeListener  implements IElementChangedListener {

	@Override
	public void elementChanged(ElementChangedEvent event) {
		 IJavaElementDelta delta= event.getDelta();
         if (delta != null) {
//            System.out.println("delta received: ");
//            System.out.print(delta);
         }
       
//         traverseAndPrint(delta);
	}
	
	public static void traverseAndPrint(IJavaElementDelta delta) {
		 
	        switch (delta.getKind()) {
	            case IJavaElementDelta.ADDED:
//	              processJavaElementAdded(delta.getElement());
	                break;
	            case IJavaElementDelta.REMOVED:
//	               processJavaElementAdded(delta.getElement());
	                break;
	            case IJavaElementDelta.CHANGED:
	            	processJavaElementAdded(delta.getElement());
	                /* Others flags can also be checked */
	                break;
	        }
	        IJavaElementDelta[] children = delta.getAffectedChildren();
	         for (int i = 0; i < children.length; i++) {
	             traverseAndPrint(children[i]);
	         }   
	        
	 }
	 
	 
	 @SuppressWarnings("unused")
		public static void processJavaElementAdded(IJavaElement element) {

			switch(element.getElementType()) {

			case IJavaElement.JAVA_MODEL:
				IJavaModel model = (IJavaModel)element;
				//TODO:
				break;

			case IJavaElement.JAVA_PROJECT:
				IJavaProject javaProject1 = (IJavaProject)element;
				//TODO:
				break;

			case IJavaElement.PACKAGE_FRAGMENT_ROOT:
				break;

			case IJavaElement.PACKAGE_FRAGMENT:

				IPackageFragment packageFolder = (IPackageFragment)element;
				//TODO:
				break;

			case IJavaElement.COMPILATION_UNIT:

				ICompilationUnit javaFile = (ICompilationUnit)element;
				//TODO:

				break;

			case IJavaElement.CLASS_FILE:
				break;

			case IJavaElement.TYPE:
				IType type = (IType)element;
				//TODO:
				break;

			case IJavaElement.FIELD:
				IField field = (IField)element;
				//TODO:
				break;

			case IJavaElement.METHOD:
				final IMethod method = (IMethod) element;
				final IJavaProject proj = method.getParent().getJavaProject();
	
				final String projName = proj.getElementName();
				// Validate for method changes in Bpmn Files .
				try {
					Display.getDefault().asyncExec(new Runnable() {
	
						@Override
						public void run() {
							try {
								proj.getProject().build(
										IncrementalProjectBuilder.FULL_BUILD,
										new NullProgressMonitor());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
	
				} catch (Exception e) {
					e.printStackTrace();
				}
				// TODO:
				break;

			case IJavaElement.INITIALIZER:
				break;

			case IJavaElement.PACKAGE_DECLARATION:
				IPackageDeclaration packageDeclaration = (IPackageDeclaration)element;
				//TODO:
				break;

			case IJavaElement.IMPORT_CONTAINER:
				IImportContainer importContainer = (IImportContainer)element;
				//TODO:
				break;

			case IJavaElement.IMPORT_DECLARATION:
				IImportDeclaration importDeclaration = (IImportDeclaration)element;
				//TODO:
				break;

			case IJavaElement.LOCAL_VARIABLE:
				ILocalVariable localVariable = (ILocalVariable)element;
				//TODO:
				break;

			case IJavaElement.TYPE_PARAMETER:
				ITypeParameter typeParameter = (ITypeParameter)element;
				//TODO:
				break;

			case IJavaElement.ANNOTATION:
				IAnnotation annotation = (IAnnotation)element;
				//TODO:
				break;
			}
		}


}
