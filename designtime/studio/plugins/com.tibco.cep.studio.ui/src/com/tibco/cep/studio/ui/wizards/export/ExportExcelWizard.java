/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.ui.util.Messages;



/**
 * @author aathalye
 *
 */
public abstract class ExportExcelWizard<E extends ExportExcelWizard<E, L>, L extends ILabelDecorator> extends Wizard implements IExportWizard {
	
	protected IStructuredSelection selection;
	
	protected IWorkbenchWindow workbenchWindow;
	
	protected ExportExcelWizardPage<E, L> page;
	
	protected TestDataExportWizardPage pageForTestData;
	/**
	 * 
	 */
	public ExportExcelWizard() {
		setWindowTitle(Messages.getString("Export.title"));
	}

	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.workbenchWindow = workbench.getActiveWorkbenchWindow();
	}

}
