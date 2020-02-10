package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.MergeExpressionXPathWizard;
import com.tibco.cep.bpmn.ui.XPathStringExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.viewers.RuleFunctionTreeViewerFilterForGatewayFork;
import com.tibco.cep.bpmn.ui.viewers.RuleFunctionTreeViewerFilterForGatewayJoin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.viewers.TreeViewerFilter;
import com.tibco.xml.datamodel.XiNode;
import com.tomsawyer.graph.TSGraph;



/**
 * 
 * @author majha
 *
 */
public class GeneralGatewayPropertySection extends GeneralNodePropertySection {

	private boolean refresh;
	private Button joinBrowseButton;
	private Text joinResourceText;
	private Text forkResourceText;
	private Button forkBrowseButton;
	private JoinForkRunleFunctionListener joinForkListener;
	private String joinResource;
	private String forkResource;
	private Text mergeResourceText;
	private Button mergeBrowseButton;
	private MergeListener mergeButtonListener;
	private Hyperlink forkRuleFunctionLabel;
	private Hyperlink joinRuleFunctionLabel;
	private Label mergeKeyLabel;
	private Composite mergeComposite;
	private Composite joinComposite;
	private Composite forkComposite;


	public GeneralGatewayPropertySection() {
		super();
		joinResource = "";
		forkResource = "";
		this.joinForkListener = new JoinForkRunleFunctionListener();
		this.mergeButtonListener = new MergeListener();
	}
	
	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		// TODO Auto-generated method stub
		super.createControls(parent, tabbedPropertySheetPage);
		insertChildSpecificComponents();
	}
	
	@Override
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
		if (!composite.isDisposed()) {
//			if (isMergeFuntionApplicable() && isMergeFuntionEnabled()) {
				// nodeTypeCombo.addModifyListener(this.widgetListener);
				if (mergeBrowseButton != null)
					mergeBrowseButton
							.removeSelectionListener(this.mergeButtonListener);
//			}
			
//			if (isJoinRulefuntionApplicable() && isJoinRuleFunctionEnabled()) {
				if (joinResourceText != null)
					joinResourceText
							.removeModifyListener(this.joinForkListener);
				// nodeTypeCombo.addModifyListener(this.widgetListener);
				if (joinBrowseButton != null)
					joinBrowseButton
							.removeSelectionListener(this.joinForkListener);
//			}

//			if (isForkRulefuntionApplicable() && isForkRuleFuntionEnabled()) {
				if (forkResourceText != null)
					forkResourceText
							.removeModifyListener(this.joinForkListener);
				// nodeTypeCombo.addModifyListener(this.widgetListener);
				if (forkBrowseButton != null)
					forkBrowseButton
							.removeSelectionListener(this.joinForkListener);
//			}
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!isListenerAttached) {
			// TODO Auto-generated method stub
			super.aboutToBeShown();
			if (!composite.isDisposed()) {
				// if (isMergeFuntionApplicable()&& isMergeFuntionEnabled()) {
				// nodeTypeCombo.addModifyListener(this.widgetListener);
				if (mergeBrowseButton != null)
					mergeBrowseButton
							.addSelectionListener(this.mergeButtonListener);
				// }

				// if (isJoinRulefuntionApplicable() &&
				// isJoinRuleFunctionEnabled()) {
				if (joinResourceText != null)
					joinResourceText.addModifyListener(this.joinForkListener);
				// nodeTypeCombo.addModifyListener(this.widgetListener);
				if (joinBrowseButton != null)
					joinBrowseButton
							.addSelectionListener(this.joinForkListener);
				// }

				// if (isForkRulefuntionApplicable() &&
				// isForkRuleFuntionEnabled()) {
				if (forkResourceText != null)
					forkResourceText.addModifyListener(this.joinForkListener);
				// nodeTypeCombo.addModifyListener(this.widgetListener);
				if (forkBrowseButton != null)
					forkBrowseButton
							.addSelectionListener(this.joinForkListener);
				// }
			}
		}
	}
	
	@Override
	protected boolean isResourcePropertyVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void insertChildSpecificComponents() {
		
		if(isMergeFuntionApplicable()){
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			// Add Event
			mergeKeyLabel = getWidgetFactory().createLabel(composite,
					BpmnMessages.getString("gatewayProp_mergeKeyLabel"), SWT.NONE);

			mergeComposite = getWidgetFactory().createComposite(
					composite);
			GridLayout layout = new GridLayout(2, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			mergeComposite.setLayout(layout);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			mergeComposite.setLayoutData(gd);

			mergeResourceText = getWidgetFactory().createText(mergeComposite, "",
					SWT.BORDER );
			gd = new GridData(/* GridData.FILL_HORIZONTAL */);
			gd.widthHint = 562;
			mergeResourceText.setLayoutData(gd);
			
			mergeBrowseButton = new Button(mergeComposite, SWT.NONE);
			mergeBrowseButton.setText(BpmnMessages.getString("edit_label"));	
			
			if(!isMergeFuntionEnabled()){
				mergeBrowseButton.setEnabled(false);
				mergeResourceText.setEnabled(false);
				mergeComposite.setEnabled(false);
				mergeKeyLabel.setEnabled(false);
			}
		}
		
		if(isJoinRulefuntionApplicable()){
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			// Add Event
			joinRuleFunctionLabel = getWidgetFactory().createHyperlink(composite,
					BpmnMessages.getString("gatewayProp_joinRuleFunctionLabel"), SWT.NONE);
			
			joinComposite = getWidgetFactory().createComposite(
					composite);
			GridLayout layout = new GridLayout(2, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			joinComposite.setLayout(layout);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			joinComposite.setLayoutData(gd);

			joinResourceText = getWidgetFactory().createText(joinComposite, "",
					SWT.BORDER );
			gd = new GridData(/* GridData.FILL_HORIZONTAL */);
			gd.widthHint = 562;
			joinResourceText.setLayoutData(gd);
			
			joinBrowseButton = new Button(joinComposite, SWT.NONE);
			joinBrowseButton.setText(BpmnMessages.getString("edit_label"));	
			
			if(!isJoinRuleFunctionEnabled()){
				joinBrowseButton.setEnabled(false);
				joinResourceText.setEnabled(false);
				joinComposite.setEnabled(false);
				joinRuleFunctionLabel.setEnabled(false);
			}
			
			addHyperLinkFieldListener(this, joinRuleFunctionLabel, joinResourceText, fEditor, fEditor
					.getProject().getName(), false, false,  new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION}, true);
		}
		
		if(isForkRulefuntionApplicable()){
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			// Add Event
			forkRuleFunctionLabel = getWidgetFactory().createHyperlink(composite,
					BpmnMessages.getString("gatewayProp_forkRuleFunctionLabel"), SWT.NONE);

			forkComposite = getWidgetFactory().createComposite(
					composite);
			GridLayout layout = new GridLayout(2, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			forkComposite.setLayout(layout);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			forkComposite.setLayoutData(gd);

			forkResourceText = getWidgetFactory().createText(forkComposite, "",
					SWT.BORDER );
			gd = new GridData(/* GridData.FILL_HORIZONTAL */);
			gd.widthHint = 562;
			forkResourceText.setLayoutData(gd);
			
			forkBrowseButton = new Button(forkComposite, SWT.NONE);
			forkBrowseButton.setText(BpmnMessages.getString("edit_label"));
			
			if(!isForkRuleFuntionEnabled()){
				forkBrowseButton.setEnabled(false);
				forkResourceText.setEnabled(false);
				forkComposite.setEnabled(false);
				forkRuleFunctionLabel.setEnabled(false);
			}
			addHyperLinkFieldListener(this, forkRuleFunctionLabel, forkResourceText, fEditor, fEditor
					.getProject().getName(), false, false,  new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION}, true);
		}
		

	}
	
	protected boolean isJoinRuleFunctionEnabled(){
		return false;
	}
	
	protected boolean isForkRuleFuntionEnabled(){
		return false;
	}
	
	protected boolean isJoinRulefuntionApplicable(){
		return false;
	}
	
	protected boolean isForkRulefuntionApplicable(){
		return false;
	}
	
	protected boolean isMergeFuntionApplicable(){
		return false;
	}
	
	protected boolean isMergeFuntionEnabled(){
		return false;
	}

	@Override
	public void refresh() {
		try{
			this.refresh = true;
			if (fTSENode != null) { 
				super.refresh();
				@SuppressWarnings("unused")
				String description = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION);
				@SuppressWarnings("unused")
				String nodeName = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
				@SuppressWarnings("unused")
				EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				@SuppressWarnings("unused")
				EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
				EObject userObject = (EObject) fTSENode.getUserObject();
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				
				if(isMergeFuntionApplicable()){
					if(mergeKeyLabel != null && !mergeKeyLabel.isDisposed())
						mergeKeyLabel.setVisible(true);
					if(mergeComposite != null && !mergeComposite.isDisposed())
						mergeComposite.setVisible(true);
					if (isMergeFuntionEnabled()) {
						mergeBrowseButton.setEnabled(true);
						mergeResourceText.setEnabled(true);
						mergeComposite.setEnabled(true);
						mergeKeyLabel.setEnabled(true);
						refreshMergeResouceWidget(userObjWrapper);
					} else {
						mergeBrowseButton.setEnabled(false);
						mergeResourceText.setEnabled(false);
						mergeComposite.setEnabled(false);
						mergeKeyLabel.setEnabled(false);
					}
				}
				else{
					if(mergeKeyLabel != null && !mergeKeyLabel.isDisposed())
						mergeKeyLabel.setVisible(false);
					if(mergeComposite != null && !mergeComposite.isDisposed())
						mergeComposite.setVisible(false);
				}
				
				if(isJoinRulefuntionApplicable()){
					if(joinRuleFunctionLabel != null && !joinRuleFunctionLabel.isDisposed())
						joinRuleFunctionLabel.setVisible(true);
					if(joinComposite != null && !joinComposite.isDisposed())
						joinComposite.setVisible(true);
					if(isJoinRuleFunctionEnabled()){
						joinResourceText.setEnabled(true);
						joinBrowseButton.setEnabled(true);
						joinComposite.setEnabled(true);
						joinRuleFunctionLabel.setEnabled(true);
						refreshJoinResouceWidget(userObjWrapper);
					}
					else{
						joinResourceText.setText("");
						joinResourceText.setEnabled(false);
						joinBrowseButton.setEnabled(false);
						joinComposite.setEnabled(false);
						joinRuleFunctionLabel.setEnabled(false);
					}
				}
				else{
					if(joinRuleFunctionLabel != null && !joinRuleFunctionLabel.isDisposed())
						joinRuleFunctionLabel.setVisible(false);
					if(joinComposite != null && !joinComposite.isDisposed())
						joinComposite.setVisible(false);
				}
				
				if(isForkRulefuntionApplicable()){
					if(forkRuleFunctionLabel != null && !forkRuleFunctionLabel.isDisposed())
						forkRuleFunctionLabel.setVisible(true);
					if(forkComposite != null && !forkComposite.isDisposed())
						forkComposite.setVisible(true);
					if(isForkRuleFuntionEnabled()){
						forkResourceText.setEnabled(true);
						forkBrowseButton.setEnabled(true);
						forkComposite.setEnabled(true);
						forkRuleFunctionLabel.setEnabled(true);
						refreshForkResouceWidget(userObjWrapper);
					}else{
						forkResourceText.setText("");
						forkResourceText.setEnabled(false);
						forkBrowseButton.setEnabled(false);
						forkComposite.setEnabled(false);
						forkRuleFunctionLabel.setEnabled(false);
					}
				}
				else{
					if(forkRuleFunctionLabel != null && !forkRuleFunctionLabel.isDisposed())
						forkRuleFunctionLabel.setVisible(false);
					if(forkComposite != null && !forkComposite.isDisposed())
						forkComposite.setVisible(false);
				}
				
			}
			if (fTSEEdge != null) {
			}
			if (fTSEGraph != null) {
			}
			
		}finally{
			this.refresh = false;
		}
	}
	
	protected void refreshMergeResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		if (mergeResourceText == null) {
			return;
		} else
			mergeResourceText.setText("");
		String taskName = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION;
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
			}
		}
		
		if (taskName != null && !taskName.isEmpty() ) {
			
			String keyFilter="";
			if (taskName != null && !taskName.trim().isEmpty()) {
	            XiNode xpathNode=null;
				try {
					xpathNode = XSTemplateSerializer
					        .deSerializeXPathString(taskName);
					keyFilter = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
	        }
	        else {
	            keyFilter = "";
	        }
			
			mergeResourceText.setText(keyFilter);
			
		}
	}
	
	protected void refreshJoinResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		if (joinResourceText == null || !joinResourceText.isEnabled()) {
			return;
		} else
			joinResourceText.setText("");
		String taskName = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION;
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
			}
		}
		
		if (taskName != null && !taskName.isEmpty() ) {
			DesignerElement element = IndexUtils.getElement(
					fProject.getName(), taskName);
			
			joinResourceText.setText(taskName);
			joinResource = taskName;
			if (element != null)
				joinResourceText.setForeground(COLOR_BLACK);
			else {
				joinResourceText.setForeground(COLOR_RED);
			}
			
		}
	}
	
	protected void refreshForkResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		if (forkResourceText == null || !forkResourceText.isEnabled() ) {
			return;
		} else
			forkResourceText.setText("");
		String taskName = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION;
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
			}
		}
		
		if (taskName != null && !taskName.isEmpty() ) {
			DesignerElement element = IndexUtils.getElement(
					fProject.getName(), taskName);
			
			forkResourceText.setText(taskName);
			forkResource = taskName;
			if (element != null)
				forkResourceText.setForeground(COLOR_BLACK);
			else {
				forkResourceText.setForeground(COLOR_RED);
			}
			
		}
	}
	
	
	protected PropertyNodeTypeGroup getNodeTypeData() {
		PropertyNodeTypeGroup gateways = new PropertyNodeTypeGroup("Gateways",
				getPaletteItems(),
				BpmnImages.GATEWAY_PALETTE_GRAPH);

		PropertyNodeTypeGroup roots = new PropertyNodeTypeGroup("roots",
				Arrays.asList(new PropertyNodeTypeGroup[] {gateways }),
				BpmnImages.GATEWAY_PALETTE_GRAPH);
		return roots;
	}
	
	protected List<BpmnPaletteGroupItem> getPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolBySubType(BpmnModelClass.GATEWAY, false));
		return items;
	}
	
	protected ViewerFilter getViewFilter(boolean join){
		ELEMENT_TYPES[] types = getElementsTypeSupportedForAction();
		EClass type = (EClass) fTSENode
				.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		if (type.equals(BpmnModelClass.PARALLEL_GATEWAY)
				|| type.equals(BpmnModelClass.INCLUSIVE_GATEWAY)) {
			if (join)
				return new RuleFunctionTreeViewerFilterForGatewayJoin(
						getProject(), types);
			else
				return new RuleFunctionTreeViewerFilterForGatewayFork(
						getProject(), types);
		}
		
		return new TreeViewerFilter(getProject(), types);
	}
	
	protected String openDialogBox() {
		EObject data = (EObject) fTSENode.getUserObject();
		final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
				.wrap(data);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);
		final String oldValue = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION);
		
		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					EObjectWrapper<EClass, EObject> modelRoot = getDiagramManager().getModelController().getModelRoot();
					if(fTSENode != null){
						TSGraph ownerGraph = fTSENode.getOwnerGraph();
						if(ownerGraph != null){
							Object userObject = ownerGraph.getUserObject();
							if(userObject != null && userObject instanceof EObject){
								EObject eObj = (EObject)userObject;
								if(eObj.eClass().equals(BpmnModelClass.SUB_PROCESS)){
									modelRoot = EObjectWrapper.wrap(eObj);
								}
							}
						}
						
					}
					MergeExpressionXPathWizard wizard = new MergeExpressionXPathWizard(
							getProject(), wrap, modelRoot, new XPathStringExpressionValidator());
					WizardDialog dialog = new WizardDialog(fEditor
							.getSite().getShell(), wizard) {
						@Override
						protected void createButtonsForButtonBar(
								Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.setMinimumPageSize(700, 500);
					try {
						dialog.create();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof InterruptedException) {
							return;
						}
					}
					int open = dialog.open();
					if(open == Window.OK){
						String xPath = wizard.getXPath();
						if(oldValue == null || !oldValue.equals(xPath)){
							HashMap<String, Object> updateList = new HashMap<String, Object>();
							updateList.put(
									BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION,xPath);
							updatePropertySection(updateList);
						}
					}
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
		});
		
		String newValue = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION);
		
		String keyFilter="";
		if (newValue != null && !newValue.trim().isEmpty()) {
            XiNode xpathNode=null;
			try {
				xpathNode = XSTemplateSerializer
				        .deSerializeXPathString(newValue);
				keyFilter = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        }
        else {
            keyFilter = "";
        }

		
		return keyFilter;
	}
	
	public void resourceBrowse(Text resText){
		
		StudioFilteredResourceSelectionDialog selectionDialog = getSeletionDialog(resText);
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Object element = selectionDialog.getFirstResult();
			String path = "";
			if(element instanceof IFile ) {
				IResource res = (IFile) element;
				path = IndexUtils.getFullPath(res);
				resText.setText(path);
			} else if (element instanceof SharedEntityElement) {
				path = ((SharedEntityElement) element).getSharedEntity().getFullPath();
				if(!path.startsWith("/"))
					path="/"+path;
				resText.setText(path);
			}
		}
	}
	
	protected StudioFilteredResourceSelectionDialog getSeletionDialog(Text resText){
		Object input = getProject();
		
		String project = ((IProject)input).getName();
		return new StudioFilteredResourceSelectionDialog(Display.getDefault().getActiveShell(), project, 
				resText.getText().trim(), 
                new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION }, 
                true, 
                false, 
                true);
	}
	
	private class JoinForkRunleFunctionListener implements SelectionListener, ModifyListener{

		@Override
		public void modifyText(ModifyEvent e) {
			if(refresh){
				return;
			}
			Object source = e.getSource();
			Map<String, Object> updateList = new HashMap<String, Object>();

			if (isJoinRulefuntionApplicable() && source == joinResourceText) {
				boolean empty = joinResourceText.getText().trim().isEmpty();
				String res = joinResourceText.getText();
				if ((empty || res != null) && res!= joinResource ) {
					String attrName =BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION;
					if(attrName != null) {
						updateList.put(attrName, res);	
						joinResource = res;
					}
				}
			}else if (isForkRulefuntionApplicable() && source == forkResourceText) {
				boolean empty = forkResourceText.getText().trim().isEmpty();
				String res = forkResourceText.getText();
				if ((empty || res != null) && res!= forkResource ) {
					String attrName =BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION;
					if(attrName != null) {
						updateList.put(attrName, res);	
						forkResource = res;
					}
				}
			}
			
			if(!updateList.isEmpty())
				updatePropertySection(updateList);
			
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if(isJoinRulefuntionApplicable() && source == joinBrowseButton) {
				resourceBrowse(joinResourceText);
//				ViewerFilter viewFilter= getViewFilter(true);
//				if(viewFilter == null) return;
//				
//				ViewerFilter[] filter = new ViewerFilter[]{viewFilter};
//				Object input = getProject();
//				ISelection selected = getPopupTreeSelection(joinResourceText, input, filter);
//				if(selected instanceof IStructuredSelection) {
//					Object element = ((IStructuredSelection)selected).getFirstElement();
//					String path = "";
//					if(element instanceof IFile ) {
//						IResource res = (IFile) element;
//						path = IndexUtils.getFullPath(res);
//						joinResourceText.setText(path);
//					} else if (element instanceof SharedEntityElement) {
//						path = ((SharedEntityElement) element).getSharedEntity().getFullPath();
//						joinResourceText.setText(path);
//					}
//				} 
			}else if(isForkRulefuntionApplicable() && source == forkBrowseButton) {
				resourceBrowse(forkResourceText);
//				ViewerFilter viewFilter= getViewFilter(false);
//				if(viewFilter == null) return;
//				
//				ViewerFilter[] filter = new ViewerFilter[]{viewFilter};
//				Object input = getProject();
//				ISelection selected = getPopupTreeSelection(forkResourceText, input, filter);
//				if(selected instanceof IStructuredSelection) {
//					Object element = ((IStructuredSelection)selected).getFirstElement();
//					String path = "";
//					if(element instanceof IFile ) {
//						IResource res = (IFile) element;
//						path = IndexUtils.getFullPath(res);
//						forkResourceText.setText(path);
//					} else if (element instanceof SharedEntityElement) {
//						path = ((SharedEntityElement) element).getSharedEntity().getFullPath();
//						forkResourceText.setText(path);
//					}
//				} 
			}
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class MergeListener implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			String value = openDialogBox();
			mergeResourceText.setText(value);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
		
	}
	
	
}