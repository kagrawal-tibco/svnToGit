package com.tibco.cep.decision.table.wizard;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.editors.DecisionTableDesignViewer;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.DefaultDecisionTableEditorInput;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTImages;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.RuleIdGenerator;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;

public class SaveAsDecisionTableWizard extends AbstractNewWizard {
//	private SaveAsDecisionTableWizardPage page;
	private IWorkbenchWindow window;
	private Implementation impl;
	private RuleFunction template;
	private RuleIdGenerator ruleIdGenerator;
	private DecisionTableColumnIdGenerator columnIdGenerator;
	private int modelSource;

	public SaveAsDecisionTableWizard(IWorkbenchWindow window,
			IEditorInput editorInput) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("SaveAs_wizard"));
		setDefaultPageImageDescriptor(DTImages
				.getImageDescriptor(DTImages.DT_IMAGES_SAVE_AS_WIZARD));
		this.window = window;
		if (editorInput instanceof IDecisionTableEditorInput) {
			IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)editorInput;
			this.impl = decisionTableEditorInput.getTableEModel();
			this.template = decisionTableEditorInput.getVirtualRuleFunction();
			this.ruleIdGenerator = decisionTableEditorInput.getRuleIdGenerator();
			this.columnIdGenerator = decisionTableEditorInput.getColumnIdGenerator();
//			this.modelSource = decisionTableEditorInput.getModelSource();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
	}

	/**
	 * Do the work after everything is specified. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return
	 * @generated
	 */
	public boolean performFinish() {
		IEditorInput dtEditorInput = null;
		Table newTableEModel = DtmodelFactory.eINSTANCE.createTable();
		newTableEModel.setDecisionTable(((Table) impl).getDecisionTable());
		newTableEModel.setExceptionTable(((Table) impl).getExceptionTable());
		newTableEModel.setMd(((Table) impl).getMd());
		for (Argument arg : ((Table) impl).getArgument()) {
			newTableEModel.getArgument().add(arg);
		}
		newTableEModel.setFolder(impl.getFolder());
		newTableEModel.setImplements(impl.getImplements());
		newTableEModel.setVersion(impl.getVersion());
		newTableEModel.setLastModifiedBy(impl.getLastModifiedBy());
		newTableEModel.setModified(impl.isModified());
		newTableEModel.setDescription(impl.getDescription());
		/**
		 * Commented for time-being. This attribute may not be needed
		 */
		//newTableEModel.setLocallyModified(((Table) impl).isLocallyModified());
		newTableEModel.setStyle(impl.getStyle());
		String newName = null;//page.getTxtName().getText();
		newTableEModel.setName(newName);
		//FIXME: NULL has to be replaced with IFile in 4.0
		dtEditorInput = new DefaultDecisionTableEditorInput(null, template);

		((DefaultDecisionTableEditorInput) dtEditorInput)
				.setTableEModel(newTableEModel);
		// set rule id generator as well as column id generator

		((DefaultDecisionTableEditorInput) dtEditorInput)
				.setRuleIdGenerator(ruleIdGenerator);
		((DefaultDecisionTableEditorInput) dtEditorInput)
				.setColumnIdGenerator(columnIdGenerator);
		// set model source (Excel ,new )
		((DefaultDecisionTableEditorInput) dtEditorInput).setModelSource(modelSource);
		try {
			DecisionTableEditor editor = (DecisionTableEditor) window
					.getActivePage().openEditor(dtEditorInput,
							DecisionTableEditor.ID);
			
			if (editor != null) {
				editor.doSave(new NullProgressMonitor());
				editor.explorerRefresh(template);
								
				DecisionTableDesignViewer viewer = editor
						.getDecisionTableDesignViewer();
				
				DecisionTablePane decisionTablePane = viewer.getDecisionTablePane();
				DecisionTablePane exceptionTablePane = viewer.getExceptionTablePane();
				
				if (decisionTablePane != null) {
					if (decisionTablePane.getDecisionDataModel().getRuleCount() > 0) {
						viewer.setToolBarEnabled();
					} else {
						viewer.setToolBarDisabled();
					}
				} else {
					viewer.setToolBarDisabled();
				}
				if (exceptionTablePane != null) {
					if (exceptionTablePane.getDecisionDataModel().getRuleCount() > 0) {
						viewer.setToolBarEnabled();
					} else {
						viewer.setToolBarDisabled();
					}
				} else {
					viewer.setToolBarDisabled();
				}
			}

		} catch (PartInitException e) {
			e.printStackTrace();
			DecisionTableUIPlugin.log(" Save As operation Failed>> ", e);
			
		} catch (Exception e){
			e.printStackTrace();
			DecisionTableUIPlugin.log(" Save As operation Failed>> ", e);
		}

		return true;
	}

	/**
	 * The framework calls this to create the contents of the wizard. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 */
	public void addPages() {
//		page = new SaveAsDecisionTableWizardPage(impl, template);
//		page.init(getSelection());
//		addPage(page);
	}

}
