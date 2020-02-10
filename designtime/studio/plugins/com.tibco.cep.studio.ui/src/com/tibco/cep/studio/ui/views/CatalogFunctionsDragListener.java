package com.tibco.cep.studio.ui.views;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.functions.model.EMFModelFunctionCategory;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;

public class CatalogFunctionsDragListener extends DragSourceAdapter {

	private static final String XSLT_SUFFIX = "(\"xslt://\")";
	private static final String XPATH_SUFFIX = "(\"xpath://\")";
	private static final String XPATH = "xpath";
	private StructuredViewer viewer;
	private IWorkbenchPage page;

	/**
	 * @param viewer
	 * @param page
	 */
	public CatalogFunctionsDragListener(StructuredViewer viewer, IWorkbenchPage page) {
		this.viewer = viewer;
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragStart(DragSourceEvent event) {
		event.doit = !viewer.getSelection().isEmpty();
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (!(selection.getFirstElement() instanceof Predicate)) {
			event.doit = false;
		}
	
		IEditorPart editor = page.getActiveEditor();
		if(editor instanceof AbstractStudioResourceEditorPart){
			if ((((AbstractStudioResourceEditorPart)editor)).isCatalogFunctionDrag()) {
				event.doit = true;
                // Temporary Solution:
				// When Drag starts, the editor comes in focus... It is helpful for the 
				// state machine property view.. 
//				((AbstractStudioResourceEditorPart)editor).setFocus();
			}else{
				event.doit = false;
			}
		}else{
			event.doit = false;
		}
	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragFinished(DragSourceEvent event) {
		if (!event.doit)
			return;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@SuppressWarnings("unchecked")
	public void dragSetData(DragSourceEvent event) {
		try{
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			Object[] tobject = (Object[]) selection.toList().toArray(new Object[selection.size()]);
			Object obj1 = tobject[0];
			event.data = getObjData(obj1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Object getObjData(Object obj1) {
		if (obj1 instanceof JavaStaticFunctionWithXSLT) {
			JavaStaticFunctionWithXSLT jsf = (JavaStaticFunctionWithXSLT) obj1;
			if (XPATH.equals(jsf.getMapperType())) {
				return obj1.toString()+XPATH_SUFFIX;
			}
			return obj1.toString()+XSLT_SUFFIX;
		}
		if (obj1 instanceof JavaStaticFunction) {
			JavaStaticFunction jsf = (JavaStaticFunction) obj1;
			if (jsf.getArguments() == null || jsf.getArguments().length == 0) {
				return obj1.toString()+"()";
			}
		}
		if (obj1 instanceof EMFModelFunctionCategory) {
			EMFModelFunctionCategory emfmModelFunctionCategory = (EMFModelFunctionCategory)obj1;
			if (emfmModelFunctionCategory.getEntity()instanceof Entity 
					&& emfmModelFunctionCategory.getName().getNamespaceURI() == null ) {
				return ModelUtils.convertPathToPackage(emfmModelFunctionCategory.getEntity().getFullPath());
			}
		}
		if(obj1 instanceof EMFModelJavaFunction) {
			EMFModelJavaFunction jf = (EMFModelJavaFunction) obj1;
			StringBuilder sb = new StringBuilder();
			sb.append(jf.getCategoryInfo().getCategory())
				.append( ".")
				.append(jf.getFunctionInfo().getName())
				.append("(")
				.append(jf.argumentTemplate()).append(")");
			
			return sb.toString();
		}
		return obj1.toString();
	}
}