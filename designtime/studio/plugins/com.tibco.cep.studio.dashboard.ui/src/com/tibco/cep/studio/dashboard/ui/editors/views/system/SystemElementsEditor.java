package com.tibco.cep.studio.dashboard.ui.editors.views.system;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 *
 * @author rgupta
 */
public class SystemElementsEditor extends FormEditor {

	public static final String EDITOR_ID = "com.tibco.cep.studio.dashboard.ui.editors.views.system.SystemElementsEditor";

	protected List<Entity> systemEntities;

	private LocalECoreFactory coreFactory;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) input).getFile();
			// ntamhank Dec 8 2010: Fix for BE-9922: Linux-BE Views:- On Creating a new Dasboard System Element Page exception is thrown.
			// Cause: file.getFullPath().toString() fails for Linux and it cannot find the file location even if the file is actually exists
			// Fix: instead of file.getFullPath().toString() use file.getLocation().toPortableString()
			EList<EObject> systemElements = DashboardCoreResourceUtils.loadMultipleElements(file.getLocation().toPortableString());
			//EList<EObject> systemElements = DashboardResourceUtils.loadMultipleElements(file.getFullPath().toString());
			systemEntities = new ArrayList<Entity>(systemElements.size());
			for (EObject object : systemElements) {
				systemEntities.add((Entity) object);
			}
			IProject project = StudioResourceUtils.getProjectForInput(input);
			// Calling this will load all the system skin elements
			coreFactory = LocalECoreFactory.getInstance(project);
			setPartName("System Skin Viewer");
		}
		site.getPage().addPartListener(partListener);
		super.init(site, input);
	}

	protected void addPages() {
		try {
			addPage(new SystemElementsPage(this, coreFactory, systemEntities));
		} catch (PartInitException e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not open editor for "+getEditorInput(),e));
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// read only editor

	}

	@Override
	public void doSaveAs() {
		// read only editor

	}

	@Override
	public boolean isSaveAsAllowed() {
		// read only editor
		return false;
	}

	@Override
	public void dispose() {
		if (partListener != null) {
			getSite().getPage().removePartListener(partListener);
		}
		super.dispose();
	}

	/**
	 * Handles activation of the editor or it's associated views.
	 */
	protected void handleActivate() {
		StudioUIUtils.refreshPaletteAndOverview(getEditorSite());
	}

	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p instanceof SystemElementsEditor) {
				handleActivate();
			}
		}

		public void partBroughtToTop(IWorkbenchPart p) {
		}

		public void partClosed(IWorkbenchPart p) {
		}

		public void partDeactivated(IWorkbenchPart p) {
		}

		public void partOpened(IWorkbenchPart p) {
		}
	};

}