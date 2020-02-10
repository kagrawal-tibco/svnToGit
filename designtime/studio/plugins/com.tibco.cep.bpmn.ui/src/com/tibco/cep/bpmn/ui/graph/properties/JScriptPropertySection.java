package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.Map;

import org.eclipse.jface.text.Document;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;


public class JScriptPropertySection extends AbstractBpmnPropertySection {
	
	protected boolean refresh;
	protected Document condDocument;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.AbstractBpmnPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		parent.setLayout(layout);
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite().getPage().getActiveEditor();
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createSourceViewer(parent);
	}
	
	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		BpmnGraphUtils.fireUpdate(updateList,fTSENode ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
	}
	
	@Override
	public void refresh() {
	}
	

	private void createSourceViewer(Composite parent) {
		
		Text text = getWidgetFactory().createText(parent, "", SWT.V_SCROLL);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
//		JSView jsview = new JSView(parent);
//		jsview.getDocument().addDocumentListener(new IDocumentListener() {
//			
//			@Override
//			public void documentChanged(DocumentEvent event) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void documentAboutToBeChanged(DocumentEvent event) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	

}