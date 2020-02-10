package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;

/**
 * 
 * @author majha
 * 
 */
public class GeneralTimerEventPropertySection extends
		GeneralEventPropertySection {

	@SuppressWarnings("unused")
	private Label timeDateLabel;
//	private Text timeDateText;
//	private Button timeDateBrowseButton;
//	@SuppressWarnings("unused")
//	private Label timeCycleLabel;
//	private Text timeCycleText;
//	private Button timeCycleBrowseButton;
//	private TimerWidgetListener timerWidgetListener;
//	private EObjectWrapper<EClass, EObject> eventWrapper;
	
//	private String timeCycle;
//	private String timeDate;
//	private boolean timerRefresh;
	

	public GeneralTimerEventPropertySection() {
		super();
//		this.timerWidgetListener = new TimerWidgetListener();
//		timeCycle = "";
//		timeDate = "";
	}
	
	protected ELEMENT_TYPES[] getElementsTypeSupportedForAction() {
		// TODO Auto-generated method stub
		return new ELEMENT_TYPES[] { ELEMENT_TYPES.TIME_EVENT };
	}
	
	protected boolean isPriorityPropertyVisible() {
		return false;
	}
	
//	@Override
//	public void aboutToBeHidden() {
//		super.aboutToBeHidden();
//		if (timeDateBrowseButton != null && !composite.isDisposed()) {// test if timer specific widget is initialized or not
//			timeDateBrowseButton.removeSelectionListener(this.timerWidgetListener);
//			timeDateText.removeModifyListener(this.timerWidgetListener);
//			timeCycleBrowseButton.removeSelectionListener(this.timerWidgetListener);
//			timeCycleText.removeModifyListener(this.timerWidgetListener);
//		}
//	}
//	
//	@Override
//	public void aboutToBeShown() {
//		super.aboutToBeShown();
//		if (timeDateBrowseButton != null && !composite.isDisposed()) {// test if timer specific widget is initialized or not
//			timeDateBrowseButton.addSelectionListener(this.timerWidgetListener);
//			timeDateText.addModifyListener(this.timerWidgetListener);
//			timeCycleBrowseButton.addSelectionListener(this.timerWidgetListener);
//			timeCycleText.addModifyListener(this.timerWidgetListener);
//		}
//	}
	
	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		// TODO Auto-generated method stub
		super.createControls(parent, tabbedPropertySheetPage);
//		createTimeDateWidget();
//		createTimeCycleWidget();
	}

	@Override
	protected boolean isResourcePropertyVisible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected boolean isDestinationPropertyVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
//	private void createTimeDateWidget(){
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		// Add Event
//		timeDateLabel = getWidgetFactory().createLabel(composite,
//				"Time Date", SWT.NONE);
//
//		Composite browseComposite = getWidgetFactory().createComposite(
//				composite);
//		GridLayout layout = new GridLayout(2, false);
//		layout.marginWidth = 0;
//		layout.marginHeight = 0;
//		browseComposite.setLayout(layout);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		browseComposite.setLayoutData(gd);
//
//		timeDateText = getWidgetFactory().createText(browseComposite, "",
//				SWT.BORDER );
//		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
//		gd.widthHint = 562;
//		timeDateText.setLayoutData(gd);
//		timeDateText.setEditable(false);
//		
//		timeDateBrowseButton = new Button(browseComposite, SWT.NONE);
//		timeDateBrowseButton.setText("Browse");	
//	}
//	
//	private void createTimeCycleWidget(){
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		// Add Event
//		timeCycleLabel = getWidgetFactory().createLabel(composite,
//				"Time Cycle", SWT.NONE);
//
//		Composite browseComposite = getWidgetFactory().createComposite(
//				composite);
//		GridLayout layout = new GridLayout(2, false);
//		layout.marginWidth = 0;
//		layout.marginHeight = 0;
//		browseComposite.setLayout(layout);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		browseComposite.setLayoutData(gd);
//
//		timeCycleText = getWidgetFactory().createText(browseComposite, "",
//				SWT.BORDER );
//		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
//		gd.widthHint = 562;
//		timeCycleText.setLayoutData(gd);
//		timeCycleText.setEditable(false);
//		
//		timeCycleBrowseButton = new Button(browseComposite, SWT.NONE);
//		timeCycleBrowseButton.setText("Browse");	
//	}
//	
//	
//	@Override
//	public void refresh() {
//		// TODO Auto-generated method stub
//		super.refresh();
//		this.timerRefresh= true;
//		EObject userObject = null;
//		if(fTSENode != null)
//			userObject =(EObject) fTSENode.getUserObject();
//		else if(fTSEConnector != null)
//			userObject =(EObject) fTSEConnector.getUserObject();
//		
//		if(userObject != null){
//			eventWrapper = EObjectWrapper.wrap(userObject);
//			EList<EObject> listAttribute = eventWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
//			if(listAttribute.size() > 0 && listAttribute.get(0).eClass().equals(BpmnModelClass.TIMER_EVENT_DEFINITION)){
//				EObject eObject = listAttribute.get(0);
//				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
//				EObject timeCycleObj = (EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TIME_CYCLE);
//				if(timeCycleObj != null){
//					EObjectWrapper<EClass, EObject> timeCycleWrap = EObjectWrapper.wrap(timeCycleObj);
//					String attribute = timeCycleWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
//					if(attribute == null)
//						attribute = "";
//					
//					this.timeCycle = attribute;
//					timeCycleText.setText(this.timeCycle);
//				}
//				
//				EObject timeDateObj = (EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TIME_DATE);
//				if(timeDateObj != null){
//					EObjectWrapper<EClass, EObject> timeDateWrap = EObjectWrapper.wrap(timeDateObj);
//					String attribute = timeDateWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
//					if(attribute == null)
//						attribute = "";
//					
//					this.timeDate = attribute;
//					timeDateText.setText(this.timeDate);
//				}
//			}
//		}
//
//		this.timerRefresh= false;
//	}
//	
//
//	
//	class TimerWidgetListener implements SelectionListener, ModifyListener{
//		@Override
//		public void modifyText(ModifyEvent e) {
//			if(timerRefresh)
//				return;
//			Map<String, Object> updateList = new HashMap<String, Object>();
//			if( e.getSource() == timeDateText) {
//				String text = timeDateText.getText();
//				if(!text.equals(timeDate)){
//					updateList.put(BpmnMetaModelConstants.E_ATTR_TIME_DATE, text);
//					timeDate= text;
//				}
//			}else if( e.getSource() == timeCycleText) {
//				String text = timeCycleText.getText();
//				if(!text.equals(timeCycle) ){
//					updateList.put(BpmnMetaModelConstants.E_ATTR_TIME_CYCLE, text);
//					timeCycle = text;
//				}
//			}
//			
//			updatePropertySection(updateList);
//			
//		}
//
//		@Override
//		public void widgetSelected(SelectionEvent e) {
//			if (e.getSource() == timeDateBrowseButton) {
//				invokeProcessXPathDialog(timeDateText, eventWrapper, new XPathDateTimeExpressionValidator());
//			}
//			if (e.getSource() == timeCycleBrowseButton) {
//				invokeProcessXPathDialog(timeCycleText, eventWrapper, new XPathLongExpressionValidator());
//			}
//			
//		}
//
//		@Override
//		public void widgetDefaultSelected(SelectionEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}

}