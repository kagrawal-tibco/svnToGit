package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;

/**
 * 
 * @author ggrigore
 *
 */
public class SequenceFlowGeneralPropertySection extends AbstractEdgeGeneralPropertySection {
	
	private Button unCntrldBtn;
	private Button defBtn;
	private Button condBtn;
	@SuppressWarnings("unused")
	private Hyperlink hideImplHypLink;
	private Composite condAreaComponent;
	private SashForm sashForm;
	private boolean refresh;
	@SuppressWarnings("unused")
	private WidgetListener widgetListener;	
	

	public SequenceFlowGeneralPropertySection() {
		super();
		this.widgetListener = new WidgetListener();
	}
	
	@Override
	public void aboutToBeHidden() {
		if(!composite.isDisposed()) {
			super.aboutToBeHidden();
//			hideImplHypLink.removeHyperlinkListener(this.widgetListener);
//			unCntrldBtn.removeSelectionListener(this.widgetListener);
//			defBtn.removeSelectionListener(this.widgetListener);
//			condBtn.removeSelectionListener(this.widgetListener);
		}
		
	}
	
	@Override
	public void aboutToBeShown() {
		if(!composite.isDisposed()) {
			super.aboutToBeShown();
//			hideImplHypLink.addHyperlinkListener(this.widgetListener);
//			unCntrldBtn.addSelectionListener(this.widgetListener);
//			defBtn.addSelectionListener(this.widgetListener);
//			condBtn.addSelectionListener(this.widgetListener);
		}

	}
	
	@SuppressWarnings("unused")
	private void refreshPropertySheetLabel() {
		// TODO
		// BpmnGraphPropertyLabelProvider.getText and getImage need to be called.
	}
	
	public boolean isRefresh() {
		return refresh;
	}
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
//		getWidgetFactory().createLabel(composite,
//				Messages.getString("BPMN_GRAPH_EDGE_TYPE")).setLayoutData(
//						new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
//		
//		sashForm = new SashForm(composite, SWT.HORIZONTAL); 
//		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
//		
//		Composite edgeTypePage = getWidgetFactory().createComposite(sashForm,SWT.NONE);
//		edgeTypePage.setLayout(new GridLayout(1, false));
//		edgeTypePage.setLayoutData(new GridData(GridData.FILL_BOTH));
//
//		unCntrldBtn = getWidgetFactory().createButton(
//			edgeTypePage, Messages.getString("BPMN_GRAPH_EDGE_TYPE_UNCNTRLD"), SWT.RADIO);
//		unCntrldBtn.setSelection(true);
//
//		condBtn = getWidgetFactory().createButton(edgeTypePage,
//				Messages.getString("BPMN_GRAPH_EDGE_COND"),
//				SWT.RADIO);
//		
//		defBtn = getWidgetFactory().createButton(edgeTypePage, 
//				Messages.getString("BPMN_GRAPH_EDGE_TYPE_DEFAULT"),
//				SWT.RADIO);
//		
//
//		hideImplHypLink = getWidgetFactory().createHyperlink(edgeTypePage,
//				Messages.getString("BPMN_GRAPH_EDGE_HIDE_IMPL_DET"),SWT.NONE);
//
//		condAreaComponent = getWidgetFactory().createComposite(sashForm,SWT.NONE);
//		condAreaComponent.setLayout(new GridLayout(1, false));
//		condAreaComponent.setLayoutData(new GridData(GridData.FILL_BOTH));
//		getWidgetFactory().createLabel(condAreaComponent,Messages.getString("BPMN_GRAPH_EDGE_SEQ_FLOW_COND"));
//		Text seqFlowCondText = getWidgetFactory().createText(condAreaComponent, "", SWT.MULTI | SWT.V_SCROLL);
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.heightHint = 100;
//		seqFlowCondText.setLayoutData(gd);
//		condAreaComponent.setVisible(false);
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		this.refresh = true;
		super.refresh();
		if (fTSENode != null){}
		if (fTSEEdge != null){ 
			
		}
		if (fTSEGraph != null){}
		this.refresh = false;
	}

	private class WidgetListener extends SelectionAdapter implements IHyperlinkListener {

		public void widgetSelected(SelectionEvent e) {
			if(isRefresh()){
				return;
			}
			Object source = e.getSource();
			if(source == unCntrldBtn ) {
				condAreaComponent.setVisible(false);
				sashForm.layout();
			}else if(source == condBtn ) {
				condAreaComponent.setVisible(true);
				sashForm.layout();
			}else if(source == defBtn ) {
				condAreaComponent.setVisible(false);
				sashForm.layout();
			}
		}

		@SuppressWarnings("unused")
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void linkActivated(HyperlinkEvent e) {
			if(isRefresh()){
				return;
			}
			@SuppressWarnings("unused")
			Object source = e.getSource();
			
		}


		@Override
		public void linkEntered(HyperlinkEvent e) {
			if(isRefresh()){
				return;
			}
			@SuppressWarnings("unused")
			Object source = e.getSource();
			
		}


		@Override
		public void linkExited(HyperlinkEvent e) {
			if(isRefresh()){
				return;
			}
			@SuppressWarnings("unused")
			Object source = e.getSource();
			
		}
	}

	@Override
	String getLabelAttribute() {
		// TODO Auto-generated method stub
		return BpmnMetaModelConstants.E_ATTR_NAME;
	}
}