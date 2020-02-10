package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.JobKeyXPathWizard;
import com.tibco.cep.bpmn.ui.XPathStringExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.xml.datamodel.XiNode;



/**
 * 
 * @author majha
 *
 */
public class GeneralReceiveTaskPropertySection extends
		GeneralTaskPropertySection {

	@SuppressWarnings("unused")
	private Label jobKeyLabel;
	private Text jobKeyText;
	private Button jobKeyBrowseButton;
	private JobKeyListener jobKeyBrowseButtonListener;
	private String jobKey;
	public GeneralReceiveTaskPropertySection() {
		super();
		jobKeyBrowseButtonListener = new JobKeyListener();
		jobKey = "";
	}
	
	@Override
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
		if(!jobKeyBrowseButton.isDisposed())
			jobKeyBrowseButton.removeSelectionListener(jobKeyBrowseButtonListener);
	}
	
	@Override
	public void aboutToBeShown() {
		if (!isListenerAttached) {
			super.aboutToBeShown();
			if (!jobKeyBrowseButton.isDisposed())
				jobKeyBrowseButton
						.addSelectionListener(jobKeyBrowseButtonListener);
		}
	}

	@Override
	protected void createProperties() {		
		super.createProperties();
		this.timeoutEnable.setEnabled(false);
		this.timeoutEnable.setSelection(true);
	}
	protected void insertChildSpecificComponents() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		// Add Event
		jobKeyLabel = getWidgetFactory().createLabel(composite,
				BpmnMessages.getString("keyExpression_label"), SWT.NONE);

		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		jobKeyText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER );
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 562;
		jobKeyText.setLayoutData(gd);
		jobKeyText.setEditable(false);
		
		jobKeyBrowseButton = new Button(browseComposite, SWT.NONE);
		jobKeyBrowseButton.setText(BpmnMessages.getString("edit_label"));
	}
	@Override
	protected boolean isDestinationPropertyVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected String openDialogBox() {
		EObject data = (EObject) fTSENode.getUserObject();
		final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
				.wrap(data);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);
		final String oldValue = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
		
		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					JobKeyXPathWizard wizard = new JobKeyXPathWizard(
							getProject(), wrap, new XPathStringExpressionValidator());
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
									BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY,xPath);
							updatePropertySection(updateList);
						}
					}
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
		});
		
		String newValue = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
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
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		this.timeoutEnable.setEnabled(false);
		this.timeoutEnable.setSelection(true);
		EObject data = (EObject) fTSENode.getUserObject();
		final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
				.wrap(data);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);
		if(valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY)){
			jobKey = valueWrapper
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
			if(jobKey == null)
				jobKey = "";
			
			String keyFilter="";
			if (jobKey != null && !jobKey.trim().isEmpty()) {
	            XiNode xpathNode=null;
				try {
					xpathNode = XSTemplateSerializer
					        .deSerializeXPathString(jobKey);
					keyFilter = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
	        }
	        else {
	            keyFilter = "";
	        }
			
			jobKeyText.setText(keyFilter);
		}
	}
	
	private class JobKeyListener implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			String value = openDialogBox();
			jobKeyText.setText(value);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
		
	}
}