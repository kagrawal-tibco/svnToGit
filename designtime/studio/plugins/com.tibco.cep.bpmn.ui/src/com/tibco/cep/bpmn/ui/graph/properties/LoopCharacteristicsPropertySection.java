package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.XPathBooleanExpressionValidator;
import com.tibco.cep.bpmn.ui.XPathIntegerExpressionValidator;
import com.tibco.cep.bpmn.ui.XPathObjectExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnXSLTutils;
import com.tibco.cep.bpmn.ui.utils.PaletteHelpTextGenerator;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/**
 * 
 * @author majha
 *
 */
public class LoopCharacteristicsPropertySection extends AbstractFormPropertySection implements SelectionListener{

	
	protected Composite composite;
	protected CCombo loopCombo;
	protected Label comboLabel;	
	private boolean refresh;
	private WidgetListener widgetListener;
	protected Composite loopCharacteristicsComp;
	private StackLayout loopCharacteristicsLayout;
	private Group standardLoopGroup;
	private Group multiInstanceGroup;
	private Button testBeforeCheck;
//	private Text textMaxLoop;
	private Text textLoopCondition;
	private Text textCompletionCondition;
	
//	private int maxLoop;

	private String loopCount;
	private String loopCondition;
	private String stndloopCondition;
	private String multiloopCount;
	private Text textLoopCount ;
	protected FormText browser;
	
	protected Section helpSection;
	protected Composite browserComposite;
	
	protected String help = "";
	public Composite loopCountContainer;
	public Composite forEachContainer ;
	public static String loopTypeNone = "NONE" ;
	public static String loopTypeCount = "LOOPCOUNT";
	public static String loopTypeForEach = "FOREACH";
	private Button loopCondBrowseButton;
//	private Button loopCardinalBrowseButton;
	private Button loopCompCondBrowseButton;
	private Text textLoopIterator ;
	private Button loopIteratorButton ;
	
	private Label collectionLbl ;
//	private Text collectionText ;
	private Button collectionBtn ;
	private Composite loopComp ;
	private Boolean testBeforeChecked = false;
	private Button multiInstanceCheckbox; 
	
	public LoopCharacteristicsPropertySection() {
		super();
		
		this.widgetListener = new WidgetListener();
//		maxLoop = 0;
		loopCondition = "";
//		loopCardinality = "";
//		completionCondition = "";
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			super.aboutToBeHidden();
			loopCombo.removeModifyListener(this.widgetListener);
			testBeforeCheck.removeSelectionListener(this.widgetListener);
			multiInstanceCheckbox.removeSelectionListener(this.widgetListener);
			textLoopCondition.removeFocusListener(this.widgetListener);
			textLoopCondition.removeSelectionListener(this.widgetListener);
			textCompletionCondition.removeFocusListener(this.widgetListener);
			textCompletionCondition.removeSelectionListener(this.widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			super.aboutToBeShown();
			loopCombo.addModifyListener(this.widgetListener);
			testBeforeCheck.addSelectionListener(this.widgetListener);
			multiInstanceCheckbox.addSelectionListener(this.widgetListener);
			textLoopCondition.addFocusListener(this.widgetListener);
			textLoopCondition.addSelectionListener(this.widgetListener);
			textCompletionCondition.addFocusListener(this.widgetListener);
			textCompletionCondition.addSelectionListener(this.widgetListener);
		}
	}
	
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createPropertyPartControl(IManagedForm form, Composite parent){
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		
		// Add loop type
		comboLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("loopCharProp_comboLabel_label"),  SWT.NONE);
		loopCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		loopCombo.setLayoutData(gd);
		loopCombo.add(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);
		loopCombo.add(BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP);
		loopCombo.add(BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE);
		loopCombo.setEnabled(true);
		loopCombo.setText(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);
		
		
		loopCharacteristicsComp = getWidgetFactory().createComposite(composite);

		loopCharacteristicsLayout = new StackLayout();
		loopCharacteristicsComp.setLayout(loopCharacteristicsLayout);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true ;
		gd.horizontalSpan = 2;
		loopCharacteristicsComp.setLayoutData(gd);
		

		
		standardLoopGroup= getWidgetFactory().createGroup(loopCharacteristicsComp, BpmnMessages.getString("loopCharProp_standardLoopGroup_label"));
		
		multiInstanceGroup = getWidgetFactory().createGroup(loopCharacteristicsComp,BpmnMessages.getString("loopCharProp_multiInstanceGroup_label") );
		loopCharacteristicsLayout.topControl = standardLoopGroup;
		
		loopCharacteristicsComp.setVisible(false);
		

		createStandardLoopWidget();
		createMultiInstanceLoopWidget();
	}
	

	
	private void  createStandardLoopWidget(){
		GridLayout gridLayout = new GridLayout();
		GridData gd = new GridData() ;
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 40 ;
		gd.grabExcessHorizontalSpace = false ;
		gd.widthHint = 400;
		gd.heightHint = 20;
		standardLoopGroup.setLayout(gridLayout);
		gd = new GridData();
		gd.widthHint = 400;
		gd.heightHint = 20;
		GridLayout layout = new GridLayout(2, false);
		
		//Enhancement

	
		
		loopComp = new Composite( standardLoopGroup , SWT.NONE) ;
		loopComp.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		gd.horizontalSpan = 2 ;
		gd.widthHint = 600;
		gd.heightHint = 200;
		gridLayout.marginLeft = -5 ;
		loopComp.setLayout(gridLayout);
		loopComp.setLayoutData(gd);
		
		
		collectionLbl = getWidgetFactory().createLabel(loopComp,BpmnMessages.getString("loopCharProp_Count"), SWT.NONE);
		collectionLbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
	
		Composite loopContentComp = new Composite(loopComp, SWT.NONE);
		loopContentComp.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		loopContentComp.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		loopContentComp.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		textLoopCount =new Text( loopContentComp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 20;
		gd.widthHint = 400;
		textLoopCount.setLayoutData(gd);
		textLoopCount.setEditable(false);
//		textLoopCount.addModifyListener(widgetListener);
		collectionBtn = new Button(loopContentComp, SWT.NONE);
		collectionBtn.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		collectionBtn.setText(BpmnMessages.getString("edit_label"));
		collectionBtn.addSelectionListener(this);
		
		// Add testbefore check box
		@SuppressWarnings("unused")
		Label testBeforeLabel = getWidgetFactory().createLabel(loopComp,
				BpmnMessages.getString("loopCharProp_testBeforeLabel_label"), SWT.NONE);
		testBeforeCheck = new Button(loopComp,SWT.CHECK);
		
		
		// add loop condition 
		Label loopConditionLabel = getWidgetFactory().createLabel(loopComp,BpmnMessages.getString("loopCharProp_condition"), SWT.NONE);
		loopConditionLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Composite childContainer = new Composite(loopComp, SWT.NONE);
		childContainer.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		childContainer.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		textLoopCondition =new Text( childContainer, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 20;
		gd.widthHint = 400;
		textLoopCondition.setLayoutData(gd);
		textLoopCondition.setEditable(false);
		loopCondBrowseButton = new Button(childContainer, SWT.NONE);
		loopCondBrowseButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		loopCondBrowseButton.setText(BpmnMessages.getString("edit_label"));
		loopCondBrowseButton.addSelectionListener(this);
		
	}

	
	private void  createMultiInstanceLoopWidget(){
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		multiInstanceGroup.setLayout(gridLayout);
		GridData gd = new GridData();
		// add loop cardinality 
		Label loopIteraotorLabel = getWidgetFactory().createLabel(multiInstanceGroup,BpmnMessages.getString("loopCharProp_Iterator"), SWT.NONE);
		loopIteraotorLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Composite childContainer = new Composite(multiInstanceGroup, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		childContainer.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		/*textLoopCardinality*/textLoopIterator =new Text( childContainer, SWT.MULTI | SWT.BORDER |SWT.WRAP );
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 20;
		gd.widthHint = 400;
		/*textLoopCardinality*/textLoopIterator.setLayoutData(gd);
		textLoopIterator.setEditable(false);
		textLoopIterator.setText("");
		/*loopCardinalBrowseButton*/ loopIteratorButton = new Button(childContainer, SWT.NONE);
		loopIteratorButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		loopIteratorButton.setText(BpmnMessages.getString("edit_label"));
		loopIteratorButton.addSelectionListener(this);
		
		Label testBeforeLabel = getWidgetFactory().createLabel(multiInstanceGroup,
				"Test Before", SWT.NONE);
		multiInstanceCheckbox = new Button(multiInstanceGroup,SWT.CHECK);
		
		// add completion condition
		Label completionConditionLabel = getWidgetFactory().createLabel(multiInstanceGroup,BpmnMessages.getString("loopCharProp_completionConditionLabel_label"), SWT.NONE);
		completionConditionLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Composite childContainer2 = new Composite(multiInstanceGroup, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		childContainer2.setLayout(layout);
		childContainer2.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		childContainer2.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		textCompletionCondition= new Text( childContainer2, SWT.MULTI | SWT.BORDER |SWT.WRAP );
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 20;
		gd.widthHint = 400;
		textCompletionCondition.setLayoutData(gd);
		textCompletionCondition.setEditable(false);	
		textCompletionCondition.setText("");
		loopCompCondBrowseButton = new Button(childContainer2, SWT.NONE);
		loopCompCondBrowseButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		loopCompCondBrowseButton.setText(BpmnMessages.getString("edit_label"));
		loopCompCondBrowseButton.addSelectionListener(this);
		
	}
	
	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		
		BpmnGraphUtils.fireUpdate(updateList,fTSENode ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
	}
	
	private List<EObjectWrapper<EClass, EObject>> getPropDefs() {
		ModelController controller  = getController() ;
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(userObject);
		EObjectWrapper<EClass, EObject> processWrapper = BpmnModelUtils.getProcess(taskWrapper);
		return (List<EObjectWrapper<EClass, EObject>>)controller.getPropertyDefinitions(processWrapper);
	}
	
	private ModelController getController(){
		return fPropertySheetPage.getEditor().getBpmnGraphDiagramManager().getController() ;
	}
	
	private void refreshTestBeforeBtn(Map<String, Object> updateList) {
		if (testBeforeChecked) {
			testBeforeCheck.setSelection(true);
			testBeforeCheck.setEnabled(false);
			updateList.put(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE, true);
		} else {
			testBeforeCheck.setSelection(false);
			testBeforeCheck.setEnabled(true);
		}

	}
	
	private void refreshTestBeforeBtnMultiInstance(Map<String, Object> updateList) {
		if (testBeforeChecked) {
			multiInstanceCheckbox.setSelection(true);
			multiInstanceCheckbox.setEnabled(false);
			updateList.put(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE, true);
		} else {
			multiInstanceCheckbox.setSelection(false);
			multiInstanceCheckbox.setEnabled(true);
		}

	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		this.refresh = true;
		testBeforeChecked = false;
		Map<String, Object> updateList = new HashMap<String, Object>();
		if (fTSENode != null) {
			super.refresh();
			EClass nodeType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			EClass nodeExtType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			@SuppressWarnings("unused")
			String nodeName = (String) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
			
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(userObject);
			if (taskWrapper != null
					&& (taskWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK)
							|| taskWrapper
									.isInstanceOf(BpmnModelClass.SUB_PROCESS) || taskWrapper
								.isInstanceOf(BpmnModelClass.CALL_ACTIVITY))) {
				testBeforeChecked = true ;
			}
			//Populate forEachCombo box 
			ModelController controller  = getController() ;
			List<EObjectWrapper<EClass, EObject>> propDefs = getPropDefs();
			List< String > forEachComboNames = new ArrayList< String >();
			for(EObjectWrapper<EClass, EObject> propDef:propDefs){
				EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
				if(itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID)!= null){
					if ( (Boolean)itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION) ) {
						forEachComboNames.add( controller.getPropertyName(propDef)); 
					}
				}
			}
			//Get Help for the Node from the Palette Model
			BpmnPaletteModel _toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
			if (_toolDefinition != null) {
				String id = (String) ExtensionHelper.getExtensionAttributeValue(taskWrapper,	BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
				BpmnPaletteGroupItem item = null;
				if (id != null && !id.isEmpty()) {
					item = _toolDefinition.getPaletteItemById(id);
				} 
				if(item == null){
					List<BpmnPaletteGroupItem> paletteToolByType = _toolDefinition
							.getPaletteToolItemByType(nodeType, nodeExtType);
					if (paletteToolByType.size() > 0)
						item = paletteToolByType.get(0);
				}
				if (item != null) {
					help = PaletteHelpTextGenerator.getHelpText(item,BpmnUIConstants.lOOP_CHAR_HELP_SECTION).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
					help = "<form>" + help + "</form>";
					browser.setText(help, true, false);
				}
			}
			
			// update loop type
			if (loopCombo.isVisible()) {
				Object mode = fTSENode
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_MODE);
				if (mode != null) {
					loopCombo.setText(mode.toString());
					loopCharacteristicsComp.setVisible(true);
					Object attribute = taskWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
					if (attribute != null) {
						EObjectWrapper<EClass, EObject> loopWrapper = EObjectWrapper
								.wrap((EObject) attribute);
						if (loopCombo.getText().equals(
								BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP)) {
							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
							if (attribute != null) {
								boolean testBefore = (Boolean) attribute;
								if (testBeforeChecked) {
									testBeforeCheck.setEnabled(false);
								} else {
									testBeforeCheck.setEnabled(true);
								}
								testBeforeCheck.setSelection(testBefore);
							}

							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM );
							if (attribute != null) {
								textLoopCount.setText( (String) attribute) ;
							} else {
								textLoopCount.setText("");
							}
							
							textLoopCondition.setText("");
							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION);
							if (attribute != null) {
								EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper
										.wrap((EObject) attribute);
								String body =(String) expressionWrapper
										.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
								
								if (body != null) {
									String loopxslt = textLoopCount.getText();
									if (validateLoopCondition(body)
											&& loopxslt != null
											&& loopxslt.isEmpty()) {
										textLoopCondition.setText("");
										updateList
												.put(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION,
														"");
									} else {
										textLoopCondition.setText(body);
									}
								}
							}
							
							loopCharacteristicsLayout.topControl = standardLoopGroup;
							loopCharacteristicsComp.layout(true);
						} else if (loopCombo.getText().equals(
								BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE)) {
							
							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
							if (attribute != null) {
								boolean testBefore = (Boolean) attribute;
								if (testBeforeChecked) {
									multiInstanceCheckbox.setEnabled(false);
								} else {
									multiInstanceCheckbox.setEnabled(true);
								}
								multiInstanceCheckbox.setSelection(testBefore);
							}
							
//							attribute = loopWrapper
//									.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_SEQUENTIAL);
//							if (attribute != null) {
//								boolean isSequential = (Boolean) attribute;
//								sequentialCheck.setSelection(isSequential);
//							}
							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT);
							if (attribute != null ){
								textLoopIterator.setText((String) attribute) ;
							} else {
								textLoopIterator.setText("");
							}
							String behaviour = null;
							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_BEHAVIOR);
							if (attribute != null) {
								behaviour = ((EEnumLiteral) attribute)
										.getName();
//								comboMultiInstanceBehaviour.setText(behaviour);
							}


							textCompletionCondition.setText("");
							attribute = loopWrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION);
							if (attribute != null) {
								EObjectWrapper<EClass, EObject> completionWrapper = EObjectWrapper
										.wrap((EObject) attribute);
								String body = (String)completionWrapper
										.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
//								if (body != null) {
//									textCompletionCondition.setText(body);
//								}
								if (body != null) {
									String loopxslt = textLoopIterator.getText() ;
									if ( validateLoopCondition(body) && loopxslt!= null && loopxslt.isEmpty() ) {
											textCompletionCondition.setText(""); 
											updateList.put(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION, "");
									}
									else {
										textCompletionCondition.setText(body); 
									}
								}
							}

							if (behaviour != null) {
								enableWidgetForMultiInstanceBehaviourSelection(behaviour);
								@SuppressWarnings("unused")
								EObjectWrapper<EClass, EObject> event = null;
								if (behaviour
										.equals(BpmnUIConstants.NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_ONE)) {
									attribute = loopWrapper
											.getAttribute(BpmnMetaModelConstants.E_ATTR_ONE_BEHAVIOR_EVENT_REF);
									if (attribute != null)
										event = EObjectWrapper
												.wrap((EObject) attribute);
								} else if (behaviour
										.equals(BpmnUIConstants.NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_NONE)) {
									attribute = loopWrapper
											.getAttribute(BpmnMetaModelConstants.E_ATTR_NONE_BEHAVIOR_EVENT_REF);
									if (attribute != null)
										event = EObjectWrapper
												.wrap((EObject) attribute);
								} else if (behaviour
										.equals(BpmnUIConstants.NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_COMPLEX)) {
									EList<?> complexObj = (EList<?>) loopWrapper
											.getAttribute(BpmnMetaModelConstants.E_ATTR_COMPLEX_BEHAVIOR_DEFINITION);
									if (complexObj.size() > 0) {
										EObjectWrapper<EClass, EObject> complexObjWrapper = EObjectWrapper
												.wrap((EObject) complexObj.get(0));
										
										attribute = complexObjWrapper
												.getAttribute(BpmnMetaModelConstants.E_ATTR_EVENT);
										if (attribute != null)
											event = EObjectWrapper
													.wrap((EObject) attribute);
										
									}
								}
							}

							loopCharacteristicsLayout.topControl = multiInstanceGroup;
							loopCharacteristicsComp.layout(true);

						}

					}else{
						loopCombo.setText(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);
						loopCharacteristicsComp.setVisible(false);
					}
				} else {
					loopCombo.setText(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);
					loopCharacteristicsComp.setVisible(false);
				}
				loopCharacteristicsComp.layout(true);
			}


		}
		if (fTSEEdge != null) {
			comboLabel.setVisible(false);
			loopCombo.setVisible(false);
		}
		if (fTSEGraph != null) {
		}
		if ( ! updateList.isEmpty())
			updatePropertySection(updateList);
		// System.out.println("Done with refresh.");
		this.refresh = false;
	}
	
	private Boolean validateLoopCondition (String xslt){
		XiNode xpath = null;
		try {
            xpath = XSTemplateSerializer.deSerializeXPathString(xslt);
            if( xpath== null ) {
            	return false ;
            }
            String loopVarname = xpath.getStringValue().trim();
            if (loopVarname.contains("\n")) {
            	loopVarname = loopVarname.substring(0 ,loopVarname.indexOf("\n") );
            }
			if (loopVarname != null
					&& (loopVarname
							.startsWith("$" + MapperConstants.LOOP_COUNTER) || loopVarname
							.startsWith("$" + MapperConstants.LOOP_MAX))
					|| loopVarname.startsWith("$" + MapperConstants.LOOP_VAR)
					|| loopVarname.endsWith("$" + MapperConstants.LOOP_COUNTER)
					|| loopVarname.endsWith("$" + MapperConstants.LOOP_MAX)
					|| loopVarname.endsWith("$" + MapperConstants.LOOP_VAR)) {
				return true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false ;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.GeneralNodePropertySection#isRefresh()
	 */
	public boolean isRefresh() {
		return refresh;
	}
	
	private void enableWidgetForMultiInstanceBehaviourSelection(String selection){
		if (selection
				.equals(BpmnUIConstants.NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_ONE)
				|| selection
						.equals(BpmnUIConstants.NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_NONE)) {
		}else if (selection
				.equals(BpmnUIConstants.NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_COMPLEX)) {
		}else{
		}
		multiInstanceGroup.layout(true);
	}
	
	protected List<BpmnPaletteGroupItem> getIntermediateCatchEventPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolItemByType(
				BpmnModelClass.INTERMEDIATE_CATCH_EVENT,null, true));

		return items;
	}


	
	@SuppressWarnings("unused")
	private EClass getBoundaryEventDefinitionType(BpmnPaletteGroupItem item) {
		BpmnCommonPaletteGroupItemType itemType = item.getItemType();
		EClass modelType = null;
		
		if (itemType.getType() ==  BpmnCommonPaletteGroupItemType.EMF_TYPE) {
			ExpandedName extClassSpec = ((BpmnCommonPaletteGroupEmfItemType)itemType).getExtendedType();
			modelType = BpmnMetaModel.getInstance().getEClass(extClassSpec);
		}
		return modelType;
	}

	private void refreshWidgetsMultiInstace() {
		try {
			textLoopIterator.setText("");
			textCompletionCondition.setText("");
		} catch (Exception e) {

		}
		
	}
	private void refreshWidgetsStandard() {
		try {
			textLoopCount.setText("");
			textLoopCondition.setText("");
		} catch (Exception e) {

		}
		
	}
	
private class WidgetListener extends FocusAdapter implements ModifyListener, SelectionListener {
		
		public void modifyText(ModifyEvent e) {
			if(isRefresh()){
				return;
			}
			Object source = e.getSource();
			Map<String, Object> updateList = new HashMap<String, Object>();
			@SuppressWarnings("unused")
			EObject task = (EObject) fTSENode.getUserObject();
			if(source == loopCombo ) {
				String loopText = loopCombo.getText();
				if (loopText != null && loopText.trim().length() > 0) {
					if (loopText.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE)) {
						loopCharacteristicsComp.setVisible(true);
						loopCharacteristicsLayout.topControl = multiInstanceGroup;
						loopCharacteristicsComp.layout(true);
						refreshTestBeforeBtnMultiInstance(updateList);
						refreshWidgetsMultiInstace();
						updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS, BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE);
						
					}
					else if (loopText.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP)) {
						loopCharacteristicsComp.setVisible(true);
						loopCharacteristicsLayout.topControl = standardLoopGroup;
						loopCharacteristicsComp.layout(true);
						refreshWidgetsStandard();
						updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS, BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP);
						refreshTestBeforeBtn(updateList);
						textLoopCondition.setText("");
					}
					if (loopText.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE)) {
						loopCharacteristicsComp.setVisible(false);
						loopCharacteristicsComp.layout(true);
						updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS, BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);
						textCompletionCondition.setText("");
					}
					getDiagramManager().refreshNode(fTSENode);
				}
				
			}
			else if ( source == textLoopCount ) {
				String text = textLoopCount.getText();
				
//				if( text!= null ) {
//					updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM ,text ) ;
//				}
//				else if ( text !=null && !text.matches("[0-9]+")&& !XSTemplateSerializer.isXSLTString(text) && !XSTemplateSerializer.isXPathString(text)) {
//					textLoopCount.setForeground(COLOR_RED);
//				}
//				else if (text!= null && !XSTemplateSerializer.isXSLTString(text) && !XSTemplateSerializer.isXPathString(text) ) {
//					String xstlTemplate = BpmnUIConstants.LOOP_CHAR_XPATH_TEMPLATE;
//					xstlTemplate = xstlTemplate.replace(BpmnUIConstants.LOOP_CHAR_XSLT_STRREPLACE, text);
////					xstlTemplate = xstlTemplate.replace("&lt;", "<");
////					xstlTemplate = xstlTemplate.replace("&quot;", "\"");
//					textLoopCount.setText(xstlTemplate);
//					updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM ,xstlTemplate ) ;
//				}
//				else {
					if ( text != null  ) {
						updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM ,text ) ;
					}
//				}
//				if ( text != null  ) {
//					updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM ,text ) ;
//				}
			}
			updatePropertySection(updateList);
//			refresh();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {
				if(e.getSource() instanceof Text)
					handleTextModification((Text)e.getSource());
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object object = e.getSource();
			
			Map<String, Object> updateList = new HashMap<String, Object>();
			if( object == testBeforeCheck) {
				boolean optionTestbefore = testBeforeCheck.getSelection();
				updateList.put(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE, optionTestbefore);
			}
			if( object == multiInstanceCheckbox) {
				boolean optionTestbefore = multiInstanceCheckbox.getSelection();
				updateList.put(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE, optionTestbefore);
			}
			updatePropertySection(updateList);
//			if(object == sequentialCheck)
//				getDiagramManager().refreshNode(fTSENode);
			
		}


		@Override
		public void focusLost(FocusEvent e) {
			if(e.getSource() instanceof Text)
				handleTextModification((Text)e.getSource());
		}
		
	}

	private void handleTextModification(Text source) {
		if (isRefresh()) {
			return;
		}
		Map<String, Object> updateList = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		EObject task = (EObject) fTSENode.getUserObject();
		if (source == textLoopCount) {
			String text = textLoopCount.getText();
			if ( text != null  && !text.equals(loopCount)) {
				updateList.put(
						BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM,
						text);
				loopCount = text;
			}
			
		}
		else if (source == textLoopCondition) {
			String text = textLoopCondition.getText();
			if (text != null  && !text.equals(stndloopCondition)) {
				updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION,
						text);
				stndloopCondition = text;
			}
		} 
		else if (source == textCompletionCondition) {
			String text = textCompletionCondition.getText();
			if (text != null  && !text.equals(loopCondition)) {
				updateList.put(
						BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION,
						text);
				loopCondition = text;
			}
		}
		else if ( source == textLoopIterator ) {
			String text = textLoopIterator.getText();
			if ( text != null  && !text.equals(multiloopCount)) {
				updateList.put(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT ,text ) ;
			}
		}
		if (!updateList.isEmpty()) {
			updatePropertySection(updateList);
		}
	}
	

	protected List<BpmnPaletteGroupItem> getPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolBySubType(BpmnModelClass.ACTIVITY, false, BpmnModelClass.SUB_PROCESS));

		return items;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		EObject userObject = (EObject) fTSENode.getUserObject();
		EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(userObject);
		Map<String, Object> updateList = new HashMap<String, Object>();
//		Object loopType = fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_MODE);
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put(BpmnXSLTutils.mapcontainsLoop,false);
		map.put(BpmnXSLTutils.maploopVartype, "");
		map.put(BpmnXSLTutils.maploopVarpath, "");
		map.put(BpmnXSLTutils.maploopVarname, "");
		map.put(BpmnXSLTutils.maplooptype, "");
		map.put(BpmnXSLTutils.maploopvarisArray, false);
		
		if (e.getSource() == loopCondBrowseButton) {
			
			BpmnXSLTutils.getloopMapperVar( fProject ,map, userObject) ;
			String args[] = null;
			if (!textLoopCount.getText().isEmpty()) {
				String argstemp[] = new String[3];
				argstemp[0] = (String) map
						.get(BpmnXSLTutils.maplooptype);
				argstemp[1] = (String) map
						.get(BpmnXSLTutils.maploopVartype);
				argstemp[2] = (String) map
						.get(BpmnXSLTutils.maploopVarpath);
				args=argstemp;
			}
			if(textLoopCondition != null) {
				stndloopCondition = textLoopCondition.getText();
			}
			invokeProcessXPathDialog(textLoopCondition, taskWrapper, new XPathBooleanExpressionValidator(), args);
			handleTextModification(textLoopCondition);
		}
		if (e.getSource() == loopCompCondBrowseButton) {
			BpmnXSLTutils.getloopMapperVar( fProject ,map, userObject) ;
			String args[] = null;
			if (!textLoopIterator.getText().isEmpty()) {
				String argstemp[] = new String[3];
				argstemp[0] = (String) map
						.get(BpmnXSLTutils.maplooptype);
				argstemp[1] = (String) map
						.get(BpmnXSLTutils.maploopVartype);
				argstemp[2] = (String) map
						.get(BpmnXSLTutils.maploopVarpath);
				args=argstemp;
			}
			if (textCompletionCondition != null) {
				loopCondition = textCompletionCondition.getText();
			}
			invokeProcessXPathDialog(textCompletionCondition, taskWrapper,new XPathBooleanExpressionValidator(), args);
			handleTextModification(textCompletionCondition);
		}
		if( e.getSource() == collectionBtn ) {
			XPathIntegerExpressionValidator xpathIntValidation = new XPathIntegerExpressionValidator();
			xpathIntValidation.isloopCharacteristics = true;
			if( textLoopCount != null ){
				loopCount = textLoopCount.getText();
			}
			invokeProcessXPathDialog(textLoopCount, taskWrapper,
					xpathIntValidation);
			EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
					.createInstance(BpmnMetaModelConstants.ITERARTOR_DATATYPE);
			 String loopVarname = null ;
			if ( textLoopCount != null ) {
				String xslt = textLoopCount.getText() ; 
				XiNode xpath = null;
				try {
		            xpath = XSTemplateSerializer.deSerializeXPathString(xslt);
		            if (xpath != null ) {
		             loopVarname = xpath.getStringValue().trim();
		             if(loopVarname.contains("\n")){
		              loopVarname = loopVarname.substring(0,loopVarname.indexOf("\n"));
		             }
		            }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (loopVarname != null) {
				createInstance.setAttribute(
						BpmnMetaModelConstants.E_ATTR_VARIABLE_NAME,
						loopVarname);
			}
			if (xpathIntValidation.type != null) {
				createInstance.setAttribute(
						BpmnMetaModelConstants.E_ATTR_VARIABLE_TYPE,
						xpathIntValidation.type);
				createInstance.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IS_MULTIPLE, false);
			}
			try {
				updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_DATA_TYPE,
						createInstance.getEInstance());
				updatePropertySection(updateList);
			} catch (Exception exc) {

			}
			
			handleTextModification(textLoopCount);
		}
		if( e.getSource() == loopIteratorButton ) {
			EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
			XPathObjectExpressionValidator xpathValidator = new XPathObjectExpressionValidator(process, fProject.getName()) ;
			if( textLoopIterator != null ){
				multiloopCount = textLoopIterator.getText();
			}
			invokeProcessXPathDialog(textLoopIterator, taskWrapper, xpathValidator);
			if (xpathValidator.isValid && !xpathValidator.isConsValue) {
				EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
						.createInstance(BpmnMetaModelConstants.ITERARTOR_DATATYPE);
				 String loopVarname = null ;
					if ( textLoopIterator != null ) {
						String xslt = textLoopIterator.getText() ; 
						XiNode xpath = null;
						try {
				            xpath = XSTemplateSerializer.deSerializeXPathString(xslt);
				            loopVarname = xpath.getStringValue().trim();
				            if ( loopVarname.contains("\n"))
				            	loopVarname = loopVarname.substring(0,loopVarname.indexOf("\n"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				if (loopVarname != null) {
					createInstance.setAttribute(
							BpmnMetaModelConstants.E_ATTR_VARIABLE_NAME,
							loopVarname);
				}
				if (xpathValidator.path != null
						&& !xpathValidator.path.isEmpty()) {
					String projPath = fProject.getProject().getLocation()
							.toPortableString();
					String filePath = xpathValidator.path;
					String temp = "file:///";
					filePath = filePath.replace(temp, "");
					if (filePath.startsWith(projPath))
						filePath = filePath.substring(projPath.length() + 1,
								filePath.length());
					createInstance.setAttribute(
							BpmnMetaModelConstants.E_ATTR_VARIABLE_PATH,
							filePath);
				}
				if (xpathValidator.type != null) {
					createInstance.setAttribute(
							BpmnMetaModelConstants.E_ATTR_VARIABLE_TYPE,
							xpathValidator.type);
					createInstance.setAttribute(
							BpmnMetaModelConstants.E_ATTR_IS_MULTIPLE,
							xpathValidator.isArray);
				}
				try {
					updateList.put(
							BpmnMetaModelConstants.E_ATTR_LOOP_DATA_TYPE,
							createInstance.getEInstance());
					updatePropertySection(updateList);
				} catch (Exception exc) {

				}
			}
			handleTextModification(textLoopIterator);
		}
	}


	@Override
	protected void createHelpPartControl(IManagedForm form, Composite parent) {
		FormToolkit toolkit = form.getToolkit();
		helpSection = getWidgetFactory().createSection(parent, Section.TITLE_BAR | Section.EXPANDED);
		helpSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		helpSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		helpSection.setText("Help");
		
//		configureHelpToolBar(helpSection); TODO: later
		
		browserComposite = getWidgetFactory().createComposite(helpSection);
		helpSection.setClient(browserComposite);
		
		browserComposite.setLayout(new GridLayout(1,false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 500;
    	browserComposite.setLayoutData(data);

    	createBrowserContentArea(browserComposite);
    	data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 500;
		browser.setLayoutData(data);
	}
	
	protected void createBrowserContentArea(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		browser = toolkit.createFormText(parent, true);
		browser.setForeground(new Color(Display.getDefault(), 63, 95, 191));
	}

}