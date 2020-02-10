package com.tibco.cep.studio.wizard.as.internal.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class PluginUtils {

	public static IProgressMonitor monitorFor(IProgressMonitor monitor) {
		if (monitor == null) return new NullProgressMonitor();
		return monitor;
	}

	public static IProgressMonitor subMonitorFor(IProgressMonitor monitor, int ticks) {
		if (monitor == null)
			return new NullProgressMonitor();
		if (monitor instanceof NullProgressMonitor)
			return monitor;
		return new SubProgressMonitor(monitor, ticks, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);
	}

	public static IProject getProject(IStructuredSelection selection) throws Exception{
		return StudioResourceUtils.getProjectForWizard(selection);
	}

	public static EAttribute getEAttribute(EList<EAttribute> attributes, String name) {
		EAttribute eAttribute = null;
		for (EAttribute attribute : attributes) {
			if (name.equals(attribute.getName())) {
				eAttribute = attribute;
				break;
			}
		}
		return eAttribute;
	}

}
