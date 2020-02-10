package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.ActivationExprXPathWizard;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.XPathBooleanExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;

/**
 * 
 * @author majha
 * 
 */
public class GeneralComplexGatewayPropertySection extends
		GeneralExclusiveGatewayPropertySection {

	private Text activationText;
	private Button activationBrowseButton;
	private ActivationButtonListener activationButtonListener;
	private String activationExpr;

	public GeneralComplexGatewayPropertySection() {
		super();
		activationButtonListener = new ActivationButtonListener();
		activationExpr = "";
	}

	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
		if (!activationBrowseButton.isDisposed())
			activationBrowseButton
					.removeSelectionListener(activationButtonListener);
	}

	@Override
	public void aboutToBeShown() {
		// TODO Auto-generated method stub
		super.aboutToBeShown();
		if (!activationBrowseButton.isDisposed())
			activationBrowseButton
					.addSelectionListener(activationButtonListener);
	}

	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		
		EObject data = (EObject) fTSENode.getUserObject();
		final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(data);
		if(wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION)){
			
			EObject obj = wrap
					.getAttribute(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION);
			if(obj != null){
				EObjectWrapper<EClass, EObject> objwrap = EObjectWrapper.wrap(obj);
				
				activationExpr = objwrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				if (activationExpr == null)
					activationExpr = "";
				activationText.setText(activationExpr);
			}
			
		}
		
	}

	protected boolean isJoinRulefuntionApplicable() {
		return true;
	}

	protected boolean isForkRulefuntionApplicable() {
		return true;
	}

	protected boolean isOutgoingSequenceOrderApplicable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void insertChildSpecificComponents() {
		super.insertChildSpecificComponents();

		getWidgetFactory().createLabel(composite, BpmnMessages.getString("complexGatewayprop_Activationcondition_label"),
				SWT.NONE);

		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		activationText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER);
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 562;
		activationText.setLayoutData(gd);
		activationText.setEditable(false);

		activationBrowseButton = new Button(browseComposite, SWT.NONE);
		activationBrowseButton.setText(BpmnMessages.getString("edit_label"));

	}

	protected String openDialogBox() {
		EObject data = (EObject) fTSENode.getUserObject();
		final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(data);
		final String oldValue = wrap
				.getAttribute(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION);

		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					ActivationExprXPathWizard wizard = new ActivationExprXPathWizard(
							getProject(), wrap, new XPathBooleanExpressionValidator());
					WizardDialog dialog = new WizardDialog(fEditor.getSite()
							.getShell(), wizard) {
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
					dialog.open();
					String xPath = wizard.getXPath();
					if (oldValue == null || !oldValue.equals(xPath)) {
						HashMap<String, Object> updateList = new HashMap<String, Object>();
						updateList
								.put(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION,
										xPath);
						updatePropertySection(updateList);
					}
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
		});

		EObject obj = wrap
				.getAttribute(BpmnMetaModelConstants.E_ATTR_ACTIVATION_CONDITION);
		String newValue = "";
		if(obj != null){
			EObjectWrapper<EClass, EObject> objwrap = EObjectWrapper.wrap(obj);
			
		   newValue = objwrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
		}
		
		return newValue;
	}

	private class ActivationButtonListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			String value = openDialogBox();
			activationText.setText(value);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}
}