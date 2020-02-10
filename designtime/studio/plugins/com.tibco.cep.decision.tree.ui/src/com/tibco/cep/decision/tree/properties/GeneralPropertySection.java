package com.tibco.cep.decision.tree.properties;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditor;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreePropertySheetPage;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/*
@author ssailapp
@date Sep 22, 2011
 */

public class GeneralPropertySection extends AbstractPropertySection {

	@SuppressWarnings("unused")
	private DecisionTreePropertySheetPage propertySheetPage;
	private Composite composite;
	private Text tName;
	private DecisionTreeEditor editor;
	@SuppressWarnings("unused")
	private IProject project;
	private TSEGraph tseGraph;
	@SuppressWarnings({ "unused", "rawtypes" })
	private List nodeList;
	@SuppressWarnings("unused")
	private EObject eObject;
	private TSENode tseNode;
	private TSEdge tsedge;

	public GeneralPropertySection() {
		// TODO Auto-generated constructor stub
	}

	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.propertySheetPage = (DecisionTreePropertySheetPage) aTabbedPropertySheetPage;
		
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		getWidgetFactory().createLabel(composite, "Name:",  SWT.NONE);
		tName = getWidgetFactory().createText(composite,"",  SWT.BORDER);

		tName.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				//handleTextModified(tName.getText().trim(), ModelPackage.eINSTANCE.getEntity_Name());
			}});
		
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		tName.setLayoutData(gd);
	}
	
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		editor = (DecisionTreeEditor) getPart();
		project = editor.getProject();
		
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSEGraph) {
			tseGraph = (TSEGraph) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			eObject = (EObject) tseGraph.getUserObject();
			tseNode = null;
			tsedge = null;
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSEdge) {
			tsedge = (TSEdge) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			eObject = (EObject) tsedge.getUserObject();
			tseNode = null;
			tseGraph = null;
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSENode) {
			tseNode = (TSENode) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			eObject = (EObject) tseNode.getUserObject();
			tsedge = null;
			tseGraph = null;
		}
	}
	
	public void refresh() {
		//composite.redraw();
		if (tseNode !=null && tseNode.getUserObject()!=null ){
			tName.setText(((FlowElement) tseNode.getUserObject()).getName());
		} else {
			tName.setText("");
		}
	}
}
