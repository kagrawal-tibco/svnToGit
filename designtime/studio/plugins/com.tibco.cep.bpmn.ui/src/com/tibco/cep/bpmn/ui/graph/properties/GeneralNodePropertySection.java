package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.editor.GraphSelection;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.graph.rule.ChangeNodeTypeRule;
import com.tibco.cep.bpmn.ui.graph.rule.DiagramRuleSet;
import com.tibco.cep.bpmn.ui.utils.JavaTaskResourceFilter;
import com.tibco.cep.bpmn.ui.utils.PaletteHelpTextGenerator;
import com.tibco.cep.bpmn.ui.viewers.DestinationTreeViewerFilter;
import com.tibco.cep.bpmn.ui.viewers.RuleFunctionTreeViewerFilter;
import com.tibco.cep.bpmn.ui.viewers.RuleFunctionTreeViewerFilterForGatewayFork;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.forms.components.StudioFilteredDestinationSelector;
import com.tibco.cep.studio.ui.viewers.TreeViewerFilter;
import com.tibco.cep.studio.ui.viewers.WsdlTreeViewerFilter;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;


/**
 * 
 * @author ggrigore
 *
 */
public abstract class GeneralNodePropertySection extends AbstractFormPropertySection implements IDiagramEntitySelection {

	private String HIGHEST_LABEL="(Highest)";
	private String LOWEST_LABEL="(Lowest)";
	
	protected Text nameText;
	protected Label nameLabel;
	protected Text labelText;
	protected Label labelLabel;
	protected boolean showName = true;
	protected boolean showLabel = true;
	
	protected Composite composite;
	protected Label inlineLabel;
	protected Button inlineCheck;
	protected Button timeoutEnable;
	protected Button asyncCheck;
	protected Button compensationCheck;
	protected Text resourceText;
	protected Hyperlink resourceLabel;
	protected Button browseButton;
	//	protected CCombo nodeTypeCombo;
	protected Label nodeTypeLabel;
	protected Text nodeTypeText;
	protected Button nodeTypeBrowseButton;

	private boolean refresh;
	private WidgetListener widgetListener;
	protected IHyperlinkListener resourceHLinklistener;

	private String name;
	private String label;
	protected Object resource;
	protected CCombo priorityTypeCombo;
	protected Object destination;

	protected Text destinationText;
	protected Hyperlink destinationLabel;
	protected Button destinationBrowseButton;
	
	protected Label javaMethodsLabel;
	protected CCombo javaMethodsCombo;
	
	protected IHyperlinkListener destHLinklistener;

	protected boolean isListenerAttached;
	@SuppressWarnings("unused")
	private Label priorityLabel;
	private Label errorLabel;
	protected Button cancelActivityCheck;

	protected FormText browser;
	
	protected Section helpSection;
	protected Composite browserComposite;
	
	protected String help = "";
	private DestinationPropertiesComposite destinationPropertiesComposite;
	
	private IFile newFile;
	private String packageName=null;
	protected JavaSource js;	
	
	public GeneralNodePropertySection() {
		super();
		this.widgetListener = new WidgetListener();
		name = "";
	}

	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			isListenerAttached = false;
			nameText.removeSelectionListener(this.widgetListener);
			nameText.removeFocusListener(this.widgetListener);
			labelText.removeSelectionListener(this.widgetListener);
			labelText.removeFocusListener(this.widgetListener);
			labelText.removeModifyListener(this.widgetListener);
			if(resourceLabel != null && resourceHLinklistener != null)
				resourceLabel.removeHyperlinkListener(resourceHLinklistener);
			if(destinationLabel != null && resourceHLinklistener != null)
				destinationLabel.removeHyperlinkListener(resourceHLinklistener);

			if(inlineCheck != null)
				inlineCheck.removeSelectionListener(this.widgetListener);
			if(timeoutEnable != null)
				timeoutEnable.removeSelectionListener(this.widgetListener);
			if(asyncCheck != null)
				asyncCheck.removeSelectionListener(this.widgetListener);
			if(compensationCheck != null)
				compensationCheck.removeSelectionListener(this.widgetListener);
			removeListenerFromResourceWidget();
			removeListenerFromDestinationWidget();
			if (isNodeTypePropertyVisible() && nodeTypeBrowseButton!= null) {
				nodeTypeBrowseButton.removeSelectionListener(this.widgetListener);
				nodeTypeText.removeModifyListener(this.widgetListener);
			}

			if(isPriorityPropertyVisible() && priorityTypeCombo != null){
				priorityTypeCombo.removeSelectionListener(this.widgetListener);
			}
			if(isCancelActivityCheckVisible() && cancelActivityCheck!= null){
				cancelActivityCheck.removeSelectionListener(this.widgetListener);
			}
			
			if (javaMethodsCombo != null) {
				javaMethodsCombo.removeModifyListener(this.widgetListener);
			}
			
		}
		
//		if (browser != null && !browser.isDisposed()) {
//			browser.dispose();
//			browser = null;
//		}
	}

	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed() && !isListenerAttached) {
			isListenerAttached = true;
			
			if (showName) {
				nameText.addSelectionListener(this.widgetListener);
				nameText.addFocusListener(this.widgetListener);
			}
			
			if (showLabel) {
				labelText.addSelectionListener(this.widgetListener);
				labelText.addFocusListener(this.widgetListener);
				labelText.addModifyListener(this.widgetListener);
			}
			
			if(resourceLabel != null && resourceHLinklistener != null)
				resourceLabel.addHyperlinkListener(resourceHLinklistener);
			if(inlineCheck != null)
				inlineCheck.addSelectionListener(this.widgetListener);
			if(timeoutEnable != null)
				timeoutEnable.addSelectionListener(this.widgetListener);
			if(asyncCheck != null)
				asyncCheck.addSelectionListener(this.widgetListener);
			if(compensationCheck != null)
				compensationCheck.addSelectionListener(this.widgetListener);			
			addListenerToResourceWidget();
			addListenerToDestinationWidget();
			if (isNodeTypePropertyVisible() && nodeTypeBrowseButton!= null) {
				nodeTypeBrowseButton.addSelectionListener(this.widgetListener);
				nodeTypeText.addModifyListener(this.widgetListener);
			}
			if(isPriorityPropertyVisible() && priorityTypeCombo != null) {
				priorityTypeCombo.addSelectionListener(this.widgetListener);
			}
			if(isCancelActivityCheckVisible() && cancelActivityCheck != null){
				cancelActivityCheck.addSelectionListener(this.widgetListener);
			}
			if(destinationLabel != null && destHLinklistener != null)
				destinationLabel.addHyperlinkListener(destHLinklistener);
			if (javaMethodsCombo != null) {
				javaMethodsCombo.addModifyListener(this.widgetListener);
			}
		}
		
		if (browser == null ) {
			createBrowserContentArea(browserComposite);
			GridData data = new GridData(GridData.FILL_BOTH);
			data.widthHint = 100;
			data.heightHint = 500;
			browser.setLayoutData(data);
		}
	}
	
	protected void createBrowserContentArea(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		browser = toolkit.createFormText(parent, true);
		browser.setForeground(new Color(Display.getDefault(), 63, 95, 191));
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.AbstractFormPropertySection#createPropertyPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createPropertyPartControl(IManagedForm form, Composite parent) {
		composite =  getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
			
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		if (showName) {
			nameLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("name_Label"),  SWT.NONE);
			nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
	//		gd.widthHint = 615;
			nameText.setLayoutData(gd);
			nameText.setEditable(false);
			nameText.setBackground(COLOR_GRAY);
		}
		
		Composite labelComp = null;
		
		if (showLabel) {
			labelLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("label_Label"),  SWT.NONE);
			labelComp = getWidgetFactory().createComposite(composite);
			GridLayout layout = new GridLayout(2, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			labelComp.setLayout(layout);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			labelComp.setLayoutData(gd);
			labelText = getWidgetFactory().createText(labelComp,"",  SWT.BORDER);
			gd = new GridData(GridData.FILL_HORIZONTAL);
	//		gd.widthHint = 615;
			labelText.setLayoutData(gd);
		}

		if (labelComp != null) {
			errorLabel = getWidgetFactory().createLabel(labelComp, "",  SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			errorLabel.setLayoutData(gd);
			errorLabel.setForeground(COLOR_RED);
		}

		// Add inline check box
		createInlineProperty();
		//add node specific special properties
		createProperties();

		if (isNodeTypePropertyVisible()) {
			// Add Node type tree
			nodeTypeLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("nodeProp_nodeType_label"), SWT.NONE);
			Composite nodeTypeTreeComposite = getWidgetFactory().createComposite(composite);
			GridLayout nodeTypelayout = new GridLayout(2, false);
			nodeTypelayout.marginWidth = 0;
			nodeTypelayout.marginHeight = 0;
			nodeTypeTreeComposite.setLayout(nodeTypelayout);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			nodeTypeTreeComposite.setLayoutData(gd);

			nodeTypeText = getWidgetFactory().createText(nodeTypeTreeComposite,
					"", SWT.BORDER | SWT.READ_ONLY);
			gd = new GridData(GridData.FILL_HORIZONTAL);
//			gd.widthHint = 562;
			nodeTypeText.setLayoutData(gd);
			nodeTypeBrowseButton = new Button(nodeTypeTreeComposite, SWT.NONE);
			nodeTypeBrowseButton.setText(BpmnMessages.getString("browse_label"));
		}
		if(isPriorityPropertyVisible()) {
			createPriorityProperty();
		}
		if (isResourcePropertyVisible()) {

			createResourceProperty();

		}
		if(isDestinationPropertyVisible())
			createDestinationProperty();
		
		if(isCancelActivityCheckVisible()){
			Label cancelActivityLabel = getWidgetFactory().createLabel(composite,BpmnMessages.getString("nodeProp_cancelActivity_label"),  SWT.NONE);
			gd = new GridData();
			gd.widthHint = 150;
			cancelActivityLabel.setLayoutData(gd);
			cancelActivityCheck = new Button(composite,SWT.CHECK);
			gd = new GridData();
			cancelActivityCheck.setLayoutData(gd);
			handleForBoundaryEventChange(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.AbstractFormPropertySection#createHelpPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createHelpPartControl(IManagedForm form, Composite parent) {
		FormToolkit toolkit = form.getToolkit();
		helpSection = getWidgetFactory().createSection(parent, Section.TITLE_BAR | Section.EXPANDED);
		helpSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		helpSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		helpSection.setText(BpmnMessages.getString("helpSection_Label"));
		
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
	
	protected void handleForBoundaryEventChange(BpmnPaletteGroupItem item){
		
	}

	private void createPriorityProperty() {
		this.priorityLabel = getWidgetFactory().createLabel(composite,/*BpmnUIConstants.NODE_START_PRIORITY*/BpmnMessages.getString("jmsSection_priority_label"), SWT.NONE);
		this.priorityTypeCombo = getWidgetFactory().createCCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		priorityTypeCombo.setLayoutData(gd);
		priorityTypeCombo.add("1"+HIGHEST_LABEL);
		for(int i=2;i<=9;i++) {
			priorityTypeCombo.add(String.valueOf(i));
		}
		priorityTypeCombo.add("10"+LOWEST_LABEL);
		priorityTypeCombo.setEnabled(true);
		priorityTypeCombo.setText("5");	
	}

	protected void createInlineProperty(){
	}


	protected void createProperties() {
	}

	//	protected void createCheckPointAsyncProperties(){
	//	
	//	}

	protected void createResourceProperty() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		
		// Add Event
		resourceLabel = getWidgetFactory().createHyperlink(composite,
				BpmnMessages.getString("generalNodePropertySection_Resource_label"), SWT.NONE);
		
		if(isServiceTask())
		{
			resourceLabel.setUnderlined(false);
			resourceLabel.setForeground(resourceLabel.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			final Cursor c=new Cursor(composite.getDisplay(),SWT.CURSOR_ARROW);
			
			resourceLabel.addMouseTrackListener(new MouseTrackListener()
			{	
				@Override
				public void mouseHover(MouseEvent e)
				{	
					resourceLabel.setCursor(c);
					resourceLabel.setForeground(resourceLabel.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}

				@Override
				public void mouseEnter(MouseEvent paramMouseEvent) {
					resourceLabel.setCursor(c);
					resourceLabel.setForeground(resourceLabel.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}

				@Override
				public void mouseExit(MouseEvent paramMouseEvent) {
					resourceLabel.setCursor(c);
					resourceLabel.setForeground(resourceLabel.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}
			}
					);
		}

		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		resourceText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER );
		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.widthHint = 562;
		resourceText.setLayoutData(gd);

		//resourceHLinklistener = addHyperLinkFieldListener(this, resourceLabel, resourceText, 
		//		fEditor, fEditor.getProject().getName(), false, false, getElementsTypeSupportedForAction());
//		resourceHLinklistener = onResourceHyperlinkClick(this, resourceLabel, resourceText, 
//				fEditor, fEditor.getProject().getName(), false, false, getElementsTypeSupportedForAction());
		browseButton = new Button(browseComposite, SWT.NONE);
		browseButton.setText(BpmnMessages.getString("browse_label"));	
	}
	
	protected IHyperlinkListener onResourceHyperlinkClick(final IDiagramEntitySelection select, 
			Hyperlink link,
			final Control control,
			final IEditorPart editor,
			final String projectName,
			final boolean isDestination,
			final boolean isNonEntity, 
			final ELEMENT_TYPES[] types, 
			boolean customFunction){
		resourceHLinklistener = addHyperLinkFieldListener(select, link, control, 
				editor, projectName, isDestination, isNonEntity, getElementsTypeSupportedForAction(), customFunction);
		return resourceHLinklistener;
		
	}
	protected void createDestinationProperty() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		// Add Event
		destinationLabel = getWidgetFactory().createHyperlink(composite,
				BpmnMessages.getString("destination_label"), SWT.NONE);

		Composite browseComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		destinationText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER );
		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.widthHint = 562;
		destinationText.setLayoutData(gd);
		
//		destHLinklistener = addHyperLinkFieldListener(destinationLabel, destinationText, fEditor, fEditor.getProject().getName(), true, false);

		destinationBrowseButton = new Button(browseComposite, SWT.NONE);
		destinationBrowseButton.setText(BpmnMessages.getString("browse_label"));	
		
		//destinationPropertiesComposite = new DestinationPropertiesComposite(composite, getWidgetFactory(),this);
	}


	protected boolean isCancelActivityCheckVisible(){
		return false;
	}

	protected boolean isResourcePropertyVisible(){
		return true;
	}

	protected boolean isDestinationPropertyVisible(){
		return false;
	}


	protected boolean isNodeTypePropertyVisible(){
		return false;
	}

	/**
	 * priority is for Start Msg Event so it can be set for the start rule
	 * @return
	 */
	protected boolean isPriorityPropertyVisible() {
		return false;
	}

	@SuppressWarnings("unused")
	private void refreshPropertySheetLabel(String label) {
		if(fTSENode != null)
			doRefresh(fTSENode, label);
		else if(fTSEConnector != null)
			doRefresh(fTSEConnector, label);
		
		refreshLabel();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	public void refresh() {
		try{
			this.refresh = true;
			help = "";
			TSGraphObject selectedObj = null;
			if(fTSENode != null)
				selectedObj  = fTSENode;
			else if(fTSEConnector != null){
				selectedObj = fTSEConnector;
			}
			if(destinationLabel != null && destHLinklistener == null)
			destHLinklistener = addHyperLinkFieldListener(destinationLabel,
					destinationText, fEditor, fEditor.getProject().getName(),
					true, false);
			if(resourceLabel != null && resourceHLinklistener == null)
			resourceHLinklistener = onResourceHyperlinkClick(this,
					resourceLabel, resourceText, fEditor, fEditor.getProject()
							.getName(), false, false,
					getElementsTypeSupportedForAction(), false);
			if (selectedObj != null) { 
				//				String nodeName = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
				//				String nodeLabel = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
				EClass nodeType = (EClass) selectedObj.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				EClass nodeExtType = (EClass) selectedObj.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
				EObject userObject = (EObject) selectedObj.getUserObject();
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);

//				resourceHLinklistener = onResourceHyperlinkClick(this, resourceLabel, resourceText, 
//						fEditor, fEditor.getProject().getName(), false, false, getElementsTypeSupportedForAction());
				
				if (userObject != null && showName) {
					name = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					nameText.setText(name);
				}

				if (showLabel) {
					label = "";
					labelText.setText("");
					String lbl = null;
					if (userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
						lbl =userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
	
					}else if (ExtensionHelper.isValidDataExtensionAttribute(
							userObjWrapper,
							BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
						EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(userObjWrapper);
						if (valWrapper != null)
							lbl = valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
					}
					if (lbl != null && !lbl.trim().isEmpty()) {
						label = lbl ;
						labelText.setText(label);
						boolean validIdentifier = isValidIdentifier(label);
						if(!validIdentifier){
							errorLabel.setText("\'"+labelText.getText()+"\' "+BpmnMessages.getString("generalNodePropertySection_error_message") );
						}else 
							errorLabel.setText("" );
					}
				}


				refreshResouceWidget(userObjWrapper);
				refreshDestinationWidget(userObjWrapper);

				if (inlineCheck != null && inlineCheck.isVisible()) {
					if (ExtensionHelper.isValidDataExtensionAttribute(
							userObjWrapper,
							BpmnMetaModelExtensionConstants.E_ATTR_INLINE)) {
						EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(userObjWrapper);
						boolean isInline = false;
						if (valWrapper != null) {
							isInline = (Boolean) valWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_INLINE);

						}
						inlineCheck.setSelection(isInline);
					}
				}

				if (timeoutEnable != null && timeoutEnable.isVisible()) {
					if (ExtensionHelper.isValidDataExtensionAttribute(
							userObjWrapper,
							BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT)) {
						EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(userObjWrapper);
						boolean isCheckPoint = false; 
						if (isAutoChekpointedTask(userObject)){
							isCheckPoint = true;
						}
						else if (valWrapper != null) {
							isCheckPoint = (Boolean) valWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT);

						}
						timeoutEnable.setSelection(isCheckPoint);
					}
				}

				if (asyncCheck != null && asyncCheck.isVisible()) {
					if (ExtensionHelper.isValidDataExtensionAttribute(
							userObjWrapper,
							BpmnMetaModelExtensionConstants.E_ATTR_ASYNC)) {
						EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(userObjWrapper);
						boolean isAsync = false;
						if (valWrapper != null) {
							isAsync = (Boolean) valWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ASYNC);

						}
						asyncCheck.setSelection(isAsync);
					}
				}
				
				if (compensationCheck != null && compensationCheck.isVisible()) {
					if (userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION)) {
						
						boolean isCompensation = false;
						if (userObjWrapper != null) {
							isCompensation = (Boolean) userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION);
						}
						compensationCheck.setSelection(isCompensation);
					}
				}				
				
				//Get Help for the Node from the Palette Model
				BpmnPaletteModel _toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
				if (_toolDefinition != null) {
					String id = (String) ExtensionHelper.getExtensionAttributeValue(userObjWrapper,	BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
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
						
						help = /*item.getHelp(BpmnUIConstants.GENERAL_HELP_SECTION)*/PaletteHelpTextGenerator.getHelpText(item,BpmnUIConstants.GENERAL_HELP_SECTION).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
						help = "<form>" + help + "</form>";
						browser.setText(help, true, false);
					}
				}

				if (isNodeTypePropertyVisible()) {
					nodeTypeText.setText("");
					BpmnPaletteModel toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
					if (toolDefinition != null) {
						String id = (String) ExtensionHelper.getExtensionAttributeValue(
								userObjWrapper,
								BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
						BpmnPaletteGroupItem item = null;
						if (id != null && !id.isEmpty()) {
							item = toolDefinition.getPaletteItemById(id);
						} else {
							List<BpmnPaletteGroupItem> paletteToolByType = toolDefinition
									.getPaletteToolItemByType(nodeType, nodeExtType);
							if (paletteToolByType.size() > 0)
								item = paletteToolByType.get(0);
						}
						if (item != null) {
							nodeTypeText.setText(item.getTitle());
							String help = /*item.getHelp("General")*/PaletteHelpTextGenerator.getHelpText(item,BpmnUIConstants.GENERAL_HELP_SECTION).replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
							browser.setText(help, true, false);
						}
					}
				}

				if(isPriorityPropertyVisible() && priorityTypeCombo != null) {
					int priority = (Integer) ExtensionHelper.getExtensionAttributeValue(
							userObjWrapper,
							BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY);
					if(priority == 10)
						priorityTypeCombo.setText("10"+LOWEST_LABEL);
					else if(priority == 1)
						priorityTypeCombo.setText("1"+HIGHEST_LABEL);
					else
						priorityTypeCombo.setText(String.valueOf(priority));
				}
				if(isCancelActivityCheckVisible()){
					BpmnPaletteModel toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
					if (toolDefinition != null) {
						String id = (String) ExtensionHelper.getExtensionAttributeValue(
								userObjWrapper,
										BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
						BpmnPaletteGroupItem item = null;
						if (id != null && !id.isEmpty()) {
							item = toolDefinition.getPaletteItemById(id);
						} else {
							EList<EObject> listAttribute = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
							if(listAttribute.size()>0){
								EClass extType = listAttribute.get(0).eClass();
								List<BpmnPaletteGroupItem> paletteToolByType = toolDefinition
								.getPaletteToolItemByType(BpmnModelClass.INTERMEDIATE_CATCH_EVENT, extType);
								if (paletteToolByType.size() > 0)
									item = paletteToolByType.get(0);
							}

						}
						if (item != null){
							handleForBoundaryEventChange(item);
						}
						
					}
					boolean cancelActivity = (Boolean)userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CANCEL_ACTIVITY);
					cancelActivityCheck.setSelection(cancelActivity);
				}
				
				if(isAutoChekpointedTask(userObject)) {
					updatePropertySection(new HashMap<String, Object>());
				}
			}
			if (fTSEEdge != null) {
//				nodeTypeLabel.setVisible(false);
				//				nodeTypeCombo.setVisible(false);
			}
			if (fTSEGraph != null) {
//				nodeTypeLabel.setVisible(false);
				//				nodeTypeCombo.setVisible(false);
			}
			
		}finally{
			this.refresh = false;
		}

	}

	
	protected String getAttributeValue(EObjectWrapper<EClass, EObject> userObjWrapper, String attrName){
		if(userObjWrapper.containsAttribute(attrName)){
			Object attr = userObjWrapper.getAttribute(attrName);
			if (attr != null)
				return attr.toString();
		}

		return "";
	}

	private boolean checkForNodeTypeChange(TSEObject node) {
		boolean isAllowed = true;
		ChangeNodeTypeRule changeNodeTypeRule = new ChangeNodeTypeRule(
				new DiagramRuleSet());
		if (!changeNodeTypeRule.isAllowed(new Object[] { node })) {
			Shell shell = fEditor.getEditorSite()
					.getShell();
			MessageDialog.openWarning(shell, com.tibco.cep.bpmn.ui.Messages
					.getString("node_type_change_title"), changeNodeTypeRule
					.getMessage());
			isAllowed = false;
		}

		return isAllowed;
	}

	public boolean isRefresh() {
		return refresh;
	}
	private Boolean isAutoChekpointedTask(EObject eobj){
		if ((BpmnModelClass.CALL_ACTIVITY.isInstance(eobj)) || (BpmnModelClass.SUB_PROCESS.isInstance(eobj)) || (BpmnModelClass.RECEIVE_TASK.isInstance(eobj))){
			return true;
		} else {
			return false;
		}
	}
	protected void updatePropertySection(Map<String, Object> updateList) {
		
		TSGraphObject selectedObj = null;
		if(fTSENode != null)
			selectedObj  = fTSENode;
		else if(fTSEConnector != null){
			selectedObj = fTSEConnector;
		}
			
		if (selectedObj != null) { 
			//				String nodeName = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
			//				String nodeLabel = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
			EObject userObject = (EObject) selectedObj.getUserObject();

			if (isAutoChekpointedTask(userObject)){
				boolean optionInline = true;
				EObject task = (EObject)fTSENode.getUserObject();
				EObject valueObj = ExtensionHelper.getAddDataExtensionValue(task);
				if (valueObj != null) {
					EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
							.wrap(valueObj);
					boolean isInline = (Boolean) valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT);
					if (optionInline != isInline) {
						updateList.put(
								BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT,
								optionInline);
					}
				}
			}
		}
		if(updateList.size() == 0)
			return;
		TSEObject object = null;
		if(fTSENode != null){
			BpmnGraphUtils.fireUpdate(updateList,fTSENode ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
			object = fTSENode;
		}
		else if(fTSEConnector != null){
			BpmnGraphUtils.fireUpdate(updateList,fTSEConnector ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
			object = fTSEConnector;
		}
		
		if(object != null){
			final TSEObject obj = object;
			if (updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID)
					|| updateList.containsKey(BpmnUIConstants.NODE_TYPE_CHANGE)
					|| updateList.containsKey(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT)) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						GraphSelection graphSelection = new GraphSelection(new ArrayList<TSEObject>());
						fPropertySheetPage.selectionChanged(
								fPropertySheetPage.getEditor(), graphSelection);
						
						 graphSelection = new GraphSelection(obj);
						fPropertySheetPage.selectionChanged(
								fPropertySheetPage.getEditor(), graphSelection);
					}

				});
			}
		}
		
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS) ||
				updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID) ||
				updateList.containsKey(BpmnUIConstants.NODE_TYPE_CHANGE) ||
				updateList.containsKey(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT)) {
			refreshOverviewView();
		}
	}

	protected ViewerFilter getViewFilter(){
		ELEMENT_TYPES[] types = getElementsTypeSupportedForAction();
		if(fTSENode != null){
			EClass type = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			if(types.length == 0) {
				if (type.equals(BpmnModelClass.SERVICE_TASK))
					return new WsdlTreeViewerFilter(getProject());
				return null;
			}else if (type.equals(BpmnModelClass.RULE_FUNCTION_TASK))
				return new RuleFunctionTreeViewerFilter(getProject(), types, false);
			else if (type.equals(BpmnModelClass.BUSINESS_RULE_TASK))
				return new RuleFunctionTreeViewerFilter(getProject(), types, true);
			else if (type.equals(BpmnModelClass.PARALLEL_GATEWAY) || type.equals(BpmnModelClass.INCLUSIVE_GATEWAY))
				return new RuleFunctionTreeViewerFilterForGatewayFork(getProject(), types);
		}
	
		return new TreeViewerFilter(getProject(), types);
	}

	protected Object getResource(){
		String resourcePath = resourceText.getText();
		if(!resourcePath.trim().isEmpty()){
			resourcePath = resourcePath.replace("\\", "/");
			DesignerElement element = IndexUtils.getElement(
					fProject.getName(), resourcePath);
			boolean valid = false;
			if(element != null){
				ELEMENT_TYPES elementType = element.getElementType();
				List<ELEMENT_TYPES> elementTypes = Arrays.asList(getElementsTypeSupportedForAction());
				if(elementTypes.contains(elementType)) {
					valid = true;
				}
			} if(fTSENode != null) {
				try {
					IFile file = null;
					String extn = "";
					EClass type = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
					if (type.equals(BpmnModelClass.JAVA_TASK)){
						extn = ".java";
					}else if(type.equals(BpmnModelClass.SERVICE_TASK)){
						extn = ".wsdl";
					}
					file = getProject().getFile(resourcePath+extn);
					IndexUtils.getFullPath(file);
					if(!file.exists()){
						IFile fileLoc=IndexUtils.getLinkedResource(getProject().getProject().getName(), resourcePath);
						if(fileLoc!=null)
							file=fileLoc;
					}
					try{
						if(type.equals(BpmnModelClass.JAVA_TASK)){
							  valid = IndexUtils.isJavaResource(file);
						}
						else if(type.equals(BpmnModelClass.BUSINESS_RULE_TASK))
						{	
							Object input = getProject();
							if(input!=null)
							{	String project = ((IProject)input).getName();
							
								DesignerElement businessRuleElement = 
										IndexUtils.getElement(project, resourcePath);
								if((businessRuleElement instanceof RuleElement))
								{
									RuleElement ruleElement = (RuleElement)businessRuleElement;
									RuleFunction ruleFunction = (RuleFunction) ruleElement.getRule();
									if (!ruleFunction.isVirtual()) 
									{
										valid=false;
									}
									else
									{
										valid=true;
									}
								}
							}
						}
						else if (file.exists()) {
							valid = true;
						}
					}catch(Exception e){
						valid=false;
					}
//					if (type.equals(BpmnModelClass.SERVICE_TASK)) {
//						try {
//							String pathWsdl=null;
//							 pathWsdl = file.getLocation().toPortableString()+".wsdl"; 
//							File file2 = new File(pathWsdl);
//							valid = file.exists();
//						} catch (Exception e) {
//							valid = false;
//						}
//						
//					  } else if(type.equals(BpmnModelClass.JAVA_TASK)){
//						  valid = IndexUtils.isJavaResource(file);
//					  }
//					
//					
//					else {
//						if (file.exists()) {
//							valid = true;
//						}
//					}
//					if(type.equals(BpmnModelClass.SERVICE_TASK) && file.exists()){
//						valid=true;	
//					  } 
				} catch (Exception e) {
					valid = false;
				}
				
			}
			if (valid) {
				resourceText.setForeground(COLOR_BLACK);
			} else{
				resourceText.setForeground(COLOR_RED);
				resourcePath = null;
			}	
		}else
			resourceText.setForeground(COLOR_BLACK);

		return resourcePath;
	}

	protected String getDestination(){
		Destination dest = null;
		String resourcePath = destinationText.getText();
		if(!resourcePath.trim().isEmpty()){
			resourcePath = resourcePath.replace("\\", "/");
			int lastIndexOf = resourcePath.lastIndexOf("/");
			boolean valid = false;
			if (lastIndexOf == -1) {
				valid = false;
			} else {
				String channel = resourcePath.substring(0, lastIndexOf)
						+ ".channel";
				String destination = resourcePath.substring(lastIndexOf);
				dest = IndexUtils.getDestination(
						fProject.getName(), channel + destination);

				if (dest != null) {
					valid = true;
				}
			}
			

			if (valid)
				destinationText.setForeground(COLOR_BLACK);
			else{
				destinationText.setForeground(COLOR_RED);
				resourcePath = null;
			}	
		}else
			destinationText.setForeground(COLOR_BLACK);
		
		EObject userObject = (EObject)fTSENode.getUserObject();
		if(destinationPropertiesComposite != null && userObject != null){
			destinationPropertiesComposite.refresh(dest, EObjectWrapper.createInstance(BpmnModelClass.EXTN_DESTINATION_CONFIG_DATA).getEInstance());
		}
		
		return resourcePath;
	}
	
	protected void updateJavaTaskFunction(Map<String, Object> updateList) {
	}

	protected void refreshResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		if (resourceText != null)
			resourceText.setText("");
		String taskName = null;
		String attrName = getAttrNameForTaskSelection();
		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					Object attribute = valueWrapper.getAttribute(attrName);
					if (attribute instanceof String)
						taskName = (String) attribute;
					if (taskName == null)
						taskName = "";
				}
			}else if(userObjWrapper.containsAttribute(attrName)) {
				taskName = getAttributeValue(userObjWrapper,  attrName);
			}
		}

		if (taskName != null && !taskName.isEmpty() ) {
			DesignerElement element = IndexUtils.getElement(
					fProject.getName(), taskName);

			resourceText.setText(taskName);
			resource = taskName;
			if (element != null)
				resourceText.setForeground(COLOR_BLACK);
			else if(fTSENode != null){
				EClass type = (EClass) fTSENode
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				if(type.equals(BpmnModelClass.SERVICE_TASK)){
					String wsdlPath = taskName+".wsdl";
					IFile file = fProject.getFile(wsdlPath);
					if(!file.exists()){
						IFile fileLoc=IndexUtils.getLinkedResource(getProject().getProject().getName(), taskName);
						if(fileLoc!=null)
							file=fileLoc;
					}
					if(file != null && file.exists()){
						resourceText.setForeground(COLOR_BLACK);
						return;
					}
				}

				resourceText.setForeground(COLOR_RED);
			}

		}
	}


	protected void updateForVrfResourceChange(){

	}

	protected void refreshDestinationWidget(
			EObjectWrapper<EClass, EObject> userObjWrapper) {
		if(!isDestinationPropertyVisible()){
			if(destinationLabel != null)
				destinationLabel.setVisible(false);
			if(destinationText != null)
				destinationText.setVisible(false);
			if(destinationBrowseButton != null)
				destinationBrowseButton.setVisible(false);
			return;
		}else{
			if(destinationLabel != null)
				destinationLabel.setVisible(true);
			if(destinationText != null)
				destinationText.setVisible(true);
			if(destinationBrowseButton != null)
				destinationBrowseButton.setVisible(true);
		}

		if (destinationText != null)
			destinationText.setText("");
		String taskName = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION;
		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper,
					attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					Object attribute = valueWrapper.getAttribute(attrName);
					if (attribute instanceof String)
						taskName = (String) attribute;
					if (taskName == null)
						taskName = "";
				}
			}

			if (taskName != null && !taskName.isEmpty()) {
				destinationText.setText(taskName);
				destination = taskName;
				int lastIndexOf = taskName.lastIndexOf("/");
				String channel = taskName.substring(0, lastIndexOf)+".channel";
				String destination = taskName.substring(lastIndexOf);
				Destination dest = IndexUtils.getDestination(
						fProject.getName(), channel+destination);
				if (dest != null)
					destinationText.setForeground(COLOR_BLACK);
				else {
					destinationText.setForeground(COLOR_RED);
				}
				if(destinationPropertiesComposite != null){
					EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObjWrapper);
					EObject attribute = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_CONFIG);
					destinationPropertiesComposite.refresh(dest, attribute);
				}

			}else{
				if(destinationPropertiesComposite != null)
					destinationPropertiesComposite.refresh(null, EObjectWrapper.createInstance(BpmnModelClass.EXTN_DESTINATION_CONFIG_DATA).getEInstance());

			}
		}
	}


	protected void addListenerToResourceWidget(){
		if (isResourcePropertyVisible()) {
			if(resourceText != null)
				resourceText.addModifyListener(this.widgetListener);
			// nodeTypeCombo.addModifyListener(this.widgetListener);
			if(browseButton != null)
				browseButton.addSelectionListener(this.widgetListener);
		}
	}
	
	protected boolean isServiceTask() {
		return false;
	}

	protected void removeListenerFromResourceWidget(){
		if (isResourcePropertyVisible()) {
			if(resourceText != null)
				resourceText.removeModifyListener(this.widgetListener);
			// nodeTypeCombo.addModifyListener(this.widgetListener);
			if(browseButton != null)
				browseButton.removeSelectionListener(this.widgetListener);
		}
	}

	protected void addListenerToDestinationWidget(){
		if (isDestinationPropertyVisible()) {
			if(destinationText != null)
				destinationText.addModifyListener(this.widgetListener);
			// nodeTypeCombo.addModifyListener(this.widgetListener);
			if(destinationBrowseButton != null)
				destinationBrowseButton.addSelectionListener(this.widgetListener);
			
			if(destinationPropertiesComposite != null)
				destinationPropertiesComposite.addlistener();
		}
	}

	protected void removeListenerFromDestinationWidget(){
		if (isDestinationPropertyVisible()) {
			if(destinationText != null)
				destinationText.removeModifyListener(this.widgetListener);
			// nodeTypeCombo.addModifyListener(this.widgetListener);
			if(destinationBrowseButton != null)
				destinationBrowseButton.removeSelectionListener(this.widgetListener);
			
			if(destinationPropertiesComposite != null)
				destinationPropertiesComposite.removelistener();
		}
	}


	private void refreshLabel(){
		if(fTSENode == null)
			return;
		
		EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		String toolId = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String resUrl = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		AbstractNodeUIFactory nodeUIFactory ;
		if (nodeExtType != null) {
			ExpandedName classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(nodeExtType);
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
					.getNodeUIFactory(name, resUrl, toolId, nodeType, classSpec);
		}
		else
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(name, resUrl,toolId, nodeType);
		// ((TSENodeLabel)(((TSENode)fTSENode).labels().get(0))).setDefaultOffset();
		nodeUIFactory.addNodeLabel(fTSENode, labelText.getText());
		getDiagramManager().refreshNode(fTSENode);
	}
	

	@SuppressWarnings("unused")
	private String getVirtualRuleFunctionForDecisionTableResource(){
		String virtualRuleFunc = null;
		String resourcePath = resourceText.getText();
		DesignerElement element = IndexUtils.getElement(
				fProject.getName(), resourcePath);

		if(element != null){
			if(element instanceof DecisionTableElement){
				DecisionTableElement table =(DecisionTableElement) element;
				Table implementation =(Table) table.getImplementation();
				String implementsURI= implementation.getImplements();
				virtualRuleFunc = implementsURI;
			}
		}

		return virtualRuleFunc;
	}
	
    protected boolean isValidIdentifier(String identifier) {
        return true;//EntityNameHelper.isValidBEEntityIdentifier(identifier);
    }

	private String getJavaPackage(JavaSource js,String path){
		String javaPackage = "";
		if( js!= null){
			javaPackage = js.getPackageName();
			if(javaPackage != null && !javaPackage.isEmpty()){
				return javaPackage;
			}
			javaPackage = IndexUtils.getJavaPackageName(path,
					fProject.getName());
		}
		return javaPackage;
	}
	private class WidgetListener extends SelectionAdapter implements FocusListener,ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
			if(isRefresh()){
				return;
			}
			Object source = e.getSource();
			Map<String, Object> updateList = new HashMap<String, Object>();

			if (isResourcePropertyVisible()&& !isServiceTask() && source == resourceText) {
				boolean empty = resourceText.getText().trim().isEmpty();
				Object res = getResource();
				String attrName = getAttrNameForTaskSelection();
				//Temp fix 
				// Need to handle this better
				if(empty && res == null && isCallActivity){
					updateList.put(attrName, null);	
				}
				if(res== null)
					res =  resourceText.getText();
				
				if (empty || res != null) {
					
					if (res instanceof String) {
						if(isJavaTask){
							try{
								js = BpmnIndexUtils.getJavaResource((String)res,fProject);	
							}catch(Exception ex){
								System.out.println("cannot build Java Source-->" +ex);
							}
						}
						if(resource==null){
							resource = "";
						}
						if(resource instanceof String && !((String) res).equalsIgnoreCase((String)resource)){
							if(attrName != null) {
								updateList.put(attrName, res);	
								if (isJavaTask) {
									if (js != null) {
										updateList
												.put(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE,
														getJavaPackage(js,(String)res));
									} else {
										updateList
												.put(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE,
														IndexUtils
																.getJavaPackageName(
																		(String) res,
																		fProject.getName()));

									}
								}
								resource = res;
							}
						}
					}else if(res!= resource){
						if(attrName != null) {
							updateList.put(attrName, res);	
							if (isJavaTask) {
								if (js != null) {
									updateList
											.put(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE,
													getJavaPackage(js,(String)res));
								} else {
									updateList
											.put(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE,
													IndexUtils
															.getJavaPackageName(
																	(String) res,
																	fProject.getName()));
								}
							}
							resource = res;
						}
					} 

				}
				if (isJavaTask) {
					if (!empty && js == null) {
						// do nothing
					} else if (empty) {
						javaMethodsCombo.removeAll();
					} else if (js != null) {
						((GeneralJavaTaskPropertySection) (GeneralNodePropertySection.this))
								.populateMethods();
					}
				}
			}
			
			else if (isResourcePropertyVisible() && !isServiceTask() && source == javaMethodsCombo) {
				updateJavaTaskFunction(updateList);
			}
			
			else if (isDestinationPropertyVisible()&& source == destinationText) {
				boolean empty = destinationText.getText().trim().isEmpty();
				String res = getDestination();
				if ((empty || res != null) && res!= destination ) {
					String attrName = BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION;
					if(attrName != null) {
						updateList.put(attrName, res);	
						EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper.createInstance(BpmnModelClass.EXTN_DESTINATION_CONFIG_DATA);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_CONFIG, createInstance.getEInstance());
						destination = res;
					}
					
				}
			}

			if (source == labelText) {
//				boolean validIdentifier = isValidIdentifier(labelText.getText());
//				if (!validIdentifier) {
//					errorLabel.setText("\'" + labelText.getText()
//							+ "\' is not valid BE identifier");
//					return;
//				} else {
//					errorLabel.setText("");
//					refreshPropertySheetLabel(labelText.getText());
//				}

			}

			updatePropertySection(updateList);
			if(updateList.size() >0 ){
				modifyNodeToolTip();
				if(fTSENode != null)
					fTSENode.setAttribute(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL, resourceText.getText());
				else if(fTSEConnector != null){
					fTSEConnector.setAttribute(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL, resourceText.getText());
				}
				if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION))
					updateForVrfResourceChange();
			}
		}

		private void modifyLabel() {
			refreshLabel();
		}
		
		private void modifyNodeToolTip(){
			if(fTSENode != null){
				EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
				String toolId = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
				String resUrl = (String)fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
				AbstractNodeUIFactory nodeUIFactory ;
				if (nodeExtType != null) {
					ExpandedName classSpec = BpmnMetaModel.INSTANCE
							.getExpandedName(nodeExtType);
					nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
							.getNodeUIFactory(name,resUrl, toolId, nodeType, classSpec);
				}
				else
					nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(name, resUrl, toolId, nodeType);
				nodeUIFactory.updateNodeToolTip(fTSENode);
			}else if (fTSEConnector != null){
				EClass nodeType = (EClass) fTSEConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				EClass nodeExtType = (EClass) fTSEConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
				String toolId = (String)fTSEConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
				String resUrl = (String)fTSEConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
				AbstractConnectorUIFactory connectorUIFactory ;
				if (nodeExtType != null) {
					ExpandedName classSpec = BpmnMetaModel.INSTANCE
							.getExpandedName(nodeExtType);
					connectorUIFactory = BpmnGraphUIFactory.getInstance(null)
							.getConnectorUIFactory((TSENode)fTSEConnector.getOwner(),name,resUrl, toolId, nodeType, classSpec);
					connectorUIFactory.updateNodeToolTip(fTSEConnector);
				}
				
			}
			

		}
		
		// this is called when we tab out or lose focus, so we can persist property sheet changes
		private void handleTextModification(Text source) {
			if(isRefresh()){
				return;
			}
			Map<String, Object> updateList = new HashMap<String, Object>();
			if(source == nameText ) {
//				if (fTSENode != null) {
					//					if (!name.equalsIgnoreCase(nameText.getText().trim())) {
					//						name = nameText.getText();
					//						updateList.put(BpmnMetaModelConstants.E_ATTR_ID, nameText.getText());
					//					}
//				}
			}
			else if (source == labelText) {
//				if (fTSENode != null) {
					boolean validIdentifier = isValidIdentifier(labelText.getText());
					
					if (validIdentifier && !label.equalsIgnoreCase(labelText.getText().trim())) {
						label = labelText.getText();
						modifyLabel();
						refreshOverviewView();
						updateList.put(BpmnMetaModelConstants.E_ATTR_NAME, labelText.getText());
					}
//				}
			}
			updatePropertySection(updateList);
			if(updateList.size() >0 )
				modifyNodeToolTip();
		}
		

		private boolean isSignalEvent(){
			boolean isSignalEvent = false;
			EObject userObject = null;
			if(fTSENode != null)
				userObject = (EObject) fTSENode.getUserObject();
			else if (fTSEConnector != null)
				userObject = (EObject)fTSEConnector.getUserObject();
			
			if (userObject != null) {
				if (BpmnModelClass.EVENT.isSuperTypeOf(userObject.eClass())) {
					EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
							.wrap(userObject);
					EList<EObject> eventDefinitions = userObjWrapper
							.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
					if (eventDefinitions.size() == 0)
						eventDefinitions = userObjWrapper
								.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS);
					EObject eDef = eventDefinitions.size() > 0 ? eventDefinitions
							.get(0) : null;
					if (eDef != null) {
						EClass extType = eDef.eClass();
						isSignalEvent = BpmnModelClass.SIGNAL_EVENT_DEFINITION
								.equals(extType);
					}

				}
			}
			return isSignalEvent;
		}
		
		private boolean isMessageEvent(){
			boolean isMessageEvent = false;
			EObject userObject = null;
			if(fTSENode != null)
				userObject = (EObject) fTSENode.getUserObject();
			else if (fTSEConnector != null)
				userObject = (EObject)fTSEConnector.getUserObject();
			
			if(userObject != null){
				if (BpmnModelClass.EVENT.isSuperTypeOf(userObject.eClass())) {

					EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
							.wrap(userObject);
					EList<EObject> eventDefinitions = userObjWrapper
							.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
					if (eventDefinitions.size() == 0)
						eventDefinitions = userObjWrapper
								.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS);
					EObject eDef = eventDefinitions.size() > 0 ? eventDefinitions
							.get(0) : null;
					if (eDef != null) {
						EClass extType = eDef.eClass();
						isMessageEvent = BpmnModelClass.MESSAGE_EVENT_DEFINITION
								.equals(extType);
					}

				}
			}
			return isMessageEvent;
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {
			if(e.getSource() instanceof Text)
				handleTextModification((Text)e.getSource());
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			Map<String, Object> updateList = new HashMap<String, Object>();
			if(isPriorityPropertyVisible() && source == priorityTypeCombo ){
				String attrName = BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY;
				String val = priorityTypeCombo.getText();
				val = val.replace(HIGHEST_LABEL, "");
				val=val.replace(LOWEST_LABEL, "");
				if(attrName != null) {
					updateList.put(attrName, Integer.valueOf(val).intValue());
				}

			} else if(isResourcePropertyVisible() && source == browseButton) {
				resourceBrowse();
				
			}else if(isDestinationPropertyVisible() && source == destinationBrowseButton) {
				boolean isSignalEvent = isSignalEvent();
				boolean isMessageEvent = isMessageEvent();
				ViewerFilter viewFilter= new DestinationTreeViewerFilter(getProject(), new ELEMENT_TYPES[]{ELEMENT_TYPES.DESTINATION}, isSignalEvent, isMessageEvent);
				ViewerFilter[] filter = new ViewerFilter[]{viewFilter};
				String text = destinationText.getText();
				Destination baseDestination = CommonIndexUtils.getAllDestinationsURIMaps(getProject().getName()).get(text.isEmpty() ? null : text);
				StudioFilteredDestinationSelector selector = new StudioFilteredDestinationSelector(fEditor.getSite().getShell(), getProject().getName(), Arrays.asList(filter), baseDestination);
				int status = selector.open();
				if (status == StudioResourceSelectionDialog.OK) {
					Object element = selector.getFirstResult();
					if(element instanceof Destination ) {
						Destination dest = (Destination)element;
						destinationText.setText(dest.getFullPath());
					} 
				} 
			}	else if(source instanceof Text) {
				handleTextModification((Text)source);
			}  else if( source == inlineCheck) {
				boolean optionInline = inlineCheck.getSelection();
				EObject task = (EObject)fTSENode.getUserObject();
				EObject valueObj = ExtensionHelper.getAddDataExtensionValue(task);
				if (valueObj != null) {
					EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
							.wrap(valueObj);
					boolean isInline = (Boolean) valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_INLINE);
					if (optionInline != isInline) {
						updateList.put(
								BpmnMetaModelExtensionConstants.E_ATTR_INLINE,
								optionInline);
					}
				}
			} else if( source == asyncCheck) {
				boolean optionInline = asyncCheck.getSelection();
				EObject task = (EObject)fTSENode.getUserObject();
				EObject valueObj = ExtensionHelper.getAddDataExtensionValue(task);
				if (valueObj != null) {
					EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
							.wrap(valueObj);
					boolean isInline = (Boolean) valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ASYNC);
					if (optionInline != isInline) {
						updateList.put(
								BpmnMetaModelExtensionConstants.E_ATTR_ASYNC,
								optionInline);
					}
				}
			} else if( source == timeoutEnable) {
				boolean optionInline = timeoutEnable.getSelection();
				EObject task = (EObject)fTSENode.getUserObject();
				EObject valueObj = ExtensionHelper.getAddDataExtensionValue(task);
				if (valueObj != null) {
					EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
							.wrap(valueObj);
					boolean isInline = (Boolean) valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT);
					if (optionInline != isInline) {
						updateList.put(
								BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT,
								optionInline);
					}
				}
			}
			else if (source == compensationCheck) {
				boolean optionCompensation = compensationCheck.getSelection();
				EObject task = (EObject)fTSENode.getUserObject();
				if (task != null) {
					EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(task);
					boolean isCompensation =
						(Boolean) taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION);
					if (optionCompensation != isCompensation) {
						updateList.put(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION,
							optionCompensation);
						
					}
				}
			}else if(source == cancelActivityCheck) {
				updateList.put(BpmnMetaModelConstants.E_ATTR_CANCEL_ACTIVITY, cancelActivityCheck.getSelection());
			}
			else if(isNodeTypePropertyVisible() && source == nodeTypeBrowseButton) {
				TSEObject obj = null;
				if(fTSENode != null)
					obj = fTSENode;
				else if(fTSEConnector != null){
					obj = fTSEConnector;
				}
				if(obj != null){
					if(checkForNodeTypeChange(getNode())){
						PropertyNodeTypeGroup input = getNodeTypeData();				
						ITreeContentProvider contentProvider = new PropertyNodeTypeTreeContentProvider(input);
						IBaseLabelProvider labelProvider = new PropertyNodeTypeLabelProvider();
						ISelection selected = getPopupTreeSelection(nodeTypeText, input,labelProvider,contentProvider,null);
						if(selected instanceof IStructuredSelection) {
							Object element = ((IStructuredSelection)selected).getFirstElement();
							if(element instanceof BpmnPaletteGroupItem) {
								BpmnPaletteGroupItem pn = (BpmnPaletteGroupItem) element;
								nodeTypeText.setData(pn);
								nodeTypeText.setText(pn.getTitle());
								updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID, pn.getId());
							} 
						} 
					}
				}
			}
			updatePropertySection(updateList);
			if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID)){
				refresh();
				refreshLabel();
			}
			if(updateList.containsKey(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION)){
				getDiagramManager().refreshNode(fTSENode);
			}

		}


		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}

		public void focusLost(FocusEvent e) {
			Object object = e.getSource();
			if(object instanceof Text)
				handleTextModification((Text)object);

		}
	}

	abstract protected PropertyNodeTypeGroup getNodeTypeData();

	public void resourceBrowse() {
		
		StudioFilteredResourceSelectionDialog selectionDialog = getSeletionDialog();
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Object element = selectionDialog.getFirstResult();
			String path = "";
			if(element instanceof IFile ) {
				IResource res = (IFile) element;
				path = IndexUtils.getFullPath(res);
				resourceText.setText(path);
			} else if (element instanceof SharedEntityElement) {
				path = ((SharedEntityElement) element).getSharedEntity().getFullPath();
				if(!path.startsWith("/"))
					path="/"+path;
				resourceText.setText(path);
			}
		}
	}
	
	protected StudioFilteredResourceSelectionDialog getSeletionDialog(){
		Object input = getProject();
		
		String project = ((IProject)input).getName();
		
		boolean onlyRF = false;
		if (fTSENode != null) {
			EClass type = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			if (type.equals(BpmnModelClass.RULE_FUNCTION_TASK)) {
				onlyRF = true;
			}
			if (type.equals(BpmnModelClass.SERVICE_TASK)) {
				
				Set<String> list = new HashSet<String>();
				list.add(CommonIndexUtils.WSDL_EXTENSION);
				
				return new StudioFilteredResourceSelectionDialog(Display.getDefault().getActiveShell(), project, 
		                resourceText.getText().trim() + CommonIndexUtils.DOT + CommonIndexUtils.WSDL_EXTENSION, 
		                list, new ViewerFilter[]{});
			}
			if(type.equals(BpmnModelClass.JAVA_TASK)){
				Set<String> list = new HashSet<String>();
				list.add(CommonIndexUtils.JAVA_EXTENSION);
				return new StudioFilteredResourceSelectionDialog(Display.getDefault().getActiveShell(), project, 
		                resourceText.getText().trim() + CommonIndexUtils.DOT + CommonIndexUtils.JAVA_EXTENSION, 
		                list, new ViewerFilter[]{new JavaTaskResourceFilter(list)});
			}
		}
		return new StudioFilteredResourceSelectionDialog(Display.getDefault().getActiveShell(), project, 
                resourceText.getText().trim(), 
                getElementsTypeSupportedForAction(), 
                true, 
                false, 
                onlyRF);
	}

	public class PropertyNodeTypeGroup {
		String groupName;
		@SuppressWarnings("rawtypes")
		Collection children = new ArrayList();
		String image;		


		@SuppressWarnings("rawtypes")
		public PropertyNodeTypeGroup(String groupName,
				Collection children, String image) {
			this.groupName = groupName;
			this.image = image;
			if(children != null) {
				this.children = children;

			}

		}

		@SuppressWarnings("unchecked")
		public Collection <Object> getChildren() {
			return children;
		}


		public String getGroupName() {
			return groupName;
		}

		public String getImage() {
			return image;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[ ");
			if(children != null) {
				for(Object i: children){
					sb.append(i.toString()).append(",");
				}
			}
			sb.append(" ]");
			return sb.toString();
		}
	}

	public class PropertyNodeTypeTreeContentProvider implements ITreeContentProvider {
		@SuppressWarnings("unused")
		private PropertyNodeTypeGroup contentRoot;

		PropertyNodeTypeTreeContentProvider(PropertyNodeTypeGroup root) {
			this.contentRoot = root;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Collection) {
				Collection col = (Collection) parentElement;
				return col.toArray(new Object[col.size()]);
			}else if(parentElement instanceof PropertyNodeTypeGroup){
				PropertyNodeTypeGroup pelement = (PropertyNodeTypeGroup)parentElement;
				return pelement.getChildren().toArray(new Object[pelement.getChildren().size()]);
			}
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {			
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean hasChildren(Object element) {
			if(element instanceof Collection) {
				return !((Collection)element).isEmpty();
			}else if(element instanceof PropertyNodeTypeGroup){
				PropertyNodeTypeGroup pelement = (PropertyNodeTypeGroup)element;
				return !pelement.getChildren().isEmpty();
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);

		}

		@Override
		public void dispose() {
			contentRoot = null;			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput instanceof PropertyNodeTypeGroup) {
				contentRoot = (PropertyNodeTypeGroup) newInput;
			}
		}
	}


	public class PropertyNodeTypeLabelProvider extends LabelProvider	{
		@Override
		public Image getImage(Object element) {
			if (element instanceof PropertyNodeTypeGroup) {
				PropertyNodeTypeGroup pg = (PropertyNodeTypeGroup) element;
				return BpmnImages.getInstance().getImage(pg.getImage());
			} else if (element instanceof BpmnPaletteGroupItem) {
				BpmnPaletteGroupItem pn = (BpmnPaletteGroupItem) element;
				return getImage(pn, pn.getIcon());				
			}
			return null;
		}

		private Image getImage(BpmnPaletteGroupItem element, String path) {
			Image image = BpmnImages.getInstance().getImage(path);

			return image;
		}

		@Override
		public String getText(Object element) {
			if(element instanceof Collection) {
				return "Root";
			} else if (element instanceof PropertyNodeTypeGroup) {
				PropertyNodeTypeGroup pg = (PropertyNodeTypeGroup) element;
				return pg.getGroupName();

			} else if (element instanceof BpmnPaletteGroupItem) {
				BpmnPaletteGroupItem pn = (BpmnPaletteGroupItem) element;
				return pn.getTitle();
			}else if (element instanceof String) {
				return (String)element;
			}
			return null;
		}

	}
	boolean isJavaTask = false;
	boolean isCallActivity = false;
	protected String getAttrNameForTaskSelection() {
		EClass type = null;
		if(fTSENode != null)
			type=(EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		else if(fTSEConnector != null)
			type=(EClass) fTSEConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		String name = null;
		if(type != null){
			if (type.equals(BpmnModelClass.JAVA_TASK)){
				isJavaTask = true;
				name= BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH;
				packageName=BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE;
				}			
			else if (type.equals(BpmnModelClass.RULE_FUNCTION_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION;
			else if (type.equals(BpmnModelClass.BUSINESS_RULE_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION;
			else if (type.equals(BpmnModelClass.INFERENCE_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_RULES;
			else if (type.equals(BpmnModelClass.SERVICE_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_WSDL;
			else if (type.equals(BpmnModelClass.RECEIVE_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
			else if (type.equals(BpmnModelClass.SERVICE_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_WSDL;
			else if (type.equals(BpmnModelClass.SEND_TASK))
				name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
			else if (BpmnModelClass.EVENT.isSuperTypeOf(type))
				name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
			else if (BpmnModelClass.INCLUSIVE_GATEWAY.isSuperTypeOf(type))
				name= BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION;
			else if (BpmnModelClass.PARALLEL_GATEWAY.isSuperTypeOf(type))
				name= BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION;
		}
		
		return name;
	}

	/**
	 * @return
	 */
	protected ELEMENT_TYPES[] getElementsTypeSupportedForAction() {
		ELEMENT_TYPES[] types = new ELEMENT_TYPES[0];
		EObject userObject = null;
		if (fTSENode != null) {
			userObject = (EObject)fTSENode.getUserObject();
		}

		if (fTSEGraph != null) {
			userObject = (EObject)fTSEGraph.getUserObject();
		} 
		if(userObject != null){
			EClass type = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			
			if (userObject.eClass().equals(BpmnModelClass.JAVA_TASK))
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.JAVA_SOURCE };
			else if (userObject.eClass().equals(BpmnModelClass.RULE_FUNCTION_TASK))
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION };
			else if (userObject.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK))
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION };
			else if (userObject.eClass().equals(BpmnModelClass.INFERENCE_TASK))
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE };
			else if (userObject.eClass().equals(BpmnModelClass.SEND_TASK)
					|| userObject.eClass().equals(BpmnModelClass.RECEIVE_TASK))
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT };
			else if (userObject.eClass().equals(BpmnModelClass.PARALLEL_GATEWAY)
					|| userObject.eClass().equals(BpmnModelClass.INCLUSIVE_GATEWAY))
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION };
		}
		
		return types;
	}
	

	public class PropertyNodeTypeInfo {
		String text;
		String label;
		String image;
		String tooltip;

		public PropertyNodeTypeInfo(String text, String label, String image, String tooltip) {
			super();
			this.text = text;
			this.label = label;
			this.image = image;
			this.tooltip = tooltip;
		}
		public String getText() {
			return text;
		}
		public String getLabel() {
			return label;
		}
		public String getImage() {
			return image;
		}
		public String getTooltip() {
			return tooltip;
		}

	}

	protected void configureHelpToolBar(Section section) {
		ToolBar tbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem titem = new ToolItem(tbar, SWT.NULL);
		Image refrImg = StudioUIPlugin.getDefault().getImage("icons/refresh.gif");
		titem.setImage(refrImg);
		titem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setText(help, true, false);
			}
		});
		section.setTextClient(tbar);
	}
	
	@Override
	public IFile getEntityFile() {
		return newFile;
	}

	@Override
	public void setEntityFile(IFile file) {
	      this.newFile = file; 
	}
}