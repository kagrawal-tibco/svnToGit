package com.tibco.cep.studio.dbconcept.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBCeptGenerator;
import com.tibco.cep.studio.dbconcept.palettes.tools.DBCeptGenHelper;
import com.tibco.cep.studio.dbconcept.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author majha
 * @author ssailapp
 */

public class DBImportWizard extends Wizard implements IImportWizard {

	private SpecifyDBConnectionPage dbPage;
	private ResourceLocationPage rlPage;
	private SelectDBEntitiesPage sDbPage;
	private ConceptDBEntitiesPage cDbPage;
	private String projName;
	private DBCeptGenerator gen;
	private DBCeptGenHelper helper;
	private IProject project;
	
	private boolean initialize;
	
	public DBImportWizard(){
		super();
	}
	
 
	@Override
	public boolean performFinish() {
		try {
			cDbPage.performFinish(project);
		} catch (Exception e) {
			ModulePlugin.log(new Status(Status.ERROR, ModulePlugin.PLUGIN_ID, "Error while importing database concepts", e));
			MessageDialog.openError(getShell(), Messages
					.getString("DBConcept_error"),
					"error while importing database\n" + e.toString());
			return false;
		}
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("DB Import");
		try {
			project= StudioResourceUtils.getProjectForWizard(selection);
			if(project == null){
				MessageDialog.openError(getShell(), Messages.getString("Project_selection_Error"), Messages.getString("Project_selection_Error_Message"));
				initialize = false;
				return;
			}
			this.projName = project.getName();
			gen = new DBCeptGenerator(project);
			helper = new DBCeptGenHelper(projName);
			Map<String, DBEntity> selectedEntities = new HashMap<String, DBEntity>();
			helper.setGen(gen);
			helper.setGenWithRel(false);
			helper.setSelection(selectedEntities);

			setNeedsProgressMonitor(true);
			dbPage = new SpecifyDBConnectionPage(project, helper);
			rlPage = new ResourceLocationPage(projName, helper);
			sDbPage = new SelectDBEntitiesPage(projName, helper);
			cDbPage = new ConceptDBEntitiesPage(projName, helper);
		} catch (Exception e) {
			ModulePlugin.log(new Status(Status.ERROR, ModulePlugin.PLUGIN_ID, "Error while importing database concepts", e));
			MessageDialog.openError(getShell(), Messages
					.getString("DBConcept_error"),
					"error while importing database\n" + e.toString());
			initialize = false;
		}
		initialize = true;
	}
	
    public void addPages() {
		super.addPages();
		if (initialize) {
			addPage(dbPage);
			addPage(rlPage);
			addPage(sDbPage);
			addPage(cDbPage);
		}
	}
}