package com.tibco.cep.studio.ui.editors.rules.assist;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;
import com.tibco.cep.studio.ui.wizards.NewConceptWizard;
import com.tibco.cep.studio.ui.wizards.NewEventWizard;
import com.tibco.cep.studio.ui.wizards.NewRuleFunctionWizard;
import com.tibco.cep.studio.ui.wizards.NewTimeEventWizard;

public class NewEntityCompletionProposal implements ICompletionProposal {

	private IProject fProject;
	private String fEntityName;
	private ELEMENT_TYPES fEntityType;
	private String fFolder;
	private boolean fCreateAutomatically = false;
	private int status;

	public NewEntityCompletionProposal(IProject project, String entityName, ELEMENT_TYPES type) {
		this.fProject= project;
		this.fEntityName = entityName;
		this.fEntityType = type;
	}

	public NewEntityCompletionProposal(IProject project, String entityName, ELEMENT_TYPES type, String folder, boolean createAutomatically) {
		this(project, entityName, type);
		this.fFolder = folder;
		this.fCreateAutomatically  = createAutomatically;
	}

	@Override
	public void apply(IDocument document) {
		AbstractNewEntityWizard wiz = null;
		switch (fEntityType) {
		case CONCEPT:
			if (fCreateAutomatically) {
				createConcept();
				refreshDocument(document);
				return;
			}
			wiz = new NewConceptWizard(null, fProject.getName());
			break;
			
		case SIMPLE_EVENT:
			if (fCreateAutomatically) {
				createSimpleEvent();
				refreshDocument(document);
				return;
			}
			wiz = new NewEventWizard(null, fProject.getName());
			break;
			
		case TIME_EVENT:
			if (fCreateAutomatically) {
				createTimeEvent();
				refreshDocument(document);
				return;
			}
			wiz = new NewTimeEventWizard(null, fProject.getName());
			break;
			
		case RULE_FUNCTION:
			if (fCreateAutomatically) {
				createRuleFunction();
				refreshDocument(document);
				return;
			}
			wiz = new NewRuleFunctionWizard(fProject.getName());
			break;
			
		default:
			break;
		}
		if (wiz != null) {
			wiz.setDefaultEntityName(fEntityName);
			runWizard(wiz);
			refreshDocument(document);
		}
		
	}

	private void refreshDocument(IDocument document) {
		try {
			// create a dummy replace so that the editor is re-reconciled to fix the displayed error
			document.replace(0, 0, "");
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void createRuleFunction() {
		RuleFunction rulefunction;
		try {
			rulefunction = MutableUtils.createRulefunction(fProject.getName(), fFolder, fFolder, fEntityName, true);
			IFile file = IndexUtils.getFile(fProject, rulefunction);
			CommonUtil.refresh(file, 0, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createTimeEvent() {
		Event event = MutableUtils.createTimeEvent(fProject.getName(), fFolder, fFolder, fEntityName, "0", TIMEOUT_UNITS.SECONDS, "0", TIMEOUT_UNITS.SECONDS, EVENT_SCHEDULE_TYPE.RULE_BASED, false, true);
		IFile file = IndexUtils.getFile(fProject, event);
		CommonUtil.refresh(file, 0, false);
	}

	private void createSimpleEvent() {
		Event event = MutableUtils.createEvent(fProject.getName(), fFolder, fFolder, fEntityName, "0", TIMEOUT_UNITS.SECONDS, false, true);
		IFile file = IndexUtils.getFile(fProject, event);
		CommonUtil.refresh(file, 0, false);
	}

	private void createConcept() {
		Concept concept = MutableUtils.createConcept(fProject.getName(), fFolder, fFolder, fEntityName, null, false);
		MutableUtils.persistEntityInProject(fProject, concept, new NullProgressMonitor());
		IFile file = IndexUtils.getFile(fProject, concept);
		CommonUtil.refresh(file, 0, false);
	}

	private boolean runWizard(final AbstractNewEntityWizard wiz) {
		status = -1;
		try{
			Display.getDefault().syncExec(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					wiz.setOpenEditor(false);
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wiz) {
						@Override
						protected void createButtonsForButtonBar(Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.create();
					status = dialog.open();
				}});
			if(status == WizardDialog.CANCEL){
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;		
	}

	@Override
	public String getAdditionalProposalInfo() {
		if (fCreateAutomatically) {
			return "This will automatically create a new "+fEntityType.getName()+" with the name " + fEntityName + " in the folder "+fFolder;
		}
		return "This will open the new "+fEntityType.getName()+" creation wizard";
	}

	@Override
	public IContextInformation getContextInformation() {
		return new ContextInformation("Create a new Element", "Of type: "+fEntityType.getName());
	}

	@Override
	public String getDisplayString() {
		if (fCreateAutomatically) {
			return "Create a new "+fEntityType.getName()+" in folder "+fFolder+" automatically";
		}
		return "Create a new "+fEntityType.getName()+"...";
	}

	@Override
	public Image getImage() {
		switch (fEntityType) {
		case CONCEPT:
			return EditorsUIPlugin.getDefault().getImage("icons/concept.png");

		case SIMPLE_EVENT:
			return EditorsUIPlugin.getDefault().getImage("icons/event.png");
			
		case TIME_EVENT:
			return EditorsUIPlugin.getDefault().getImage("icons/time-event.gif");
			
		case RULE_FUNCTION:
			return EditorsUIPlugin.getDefault().getImage("icons/rule-function.png");
			
		default:
			break;
		}
		return null;
	}

	@Override
	public Point getSelection(IDocument document) {
		return null;
	}

}
