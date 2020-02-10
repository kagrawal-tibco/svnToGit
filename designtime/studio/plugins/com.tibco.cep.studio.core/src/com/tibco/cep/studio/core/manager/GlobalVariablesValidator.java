package com.tibco.cep.studio.core.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class GlobalVariablesValidator implements IGlobalVariablesChangeListener {

	public static final String GV_INTEGER = "Integer";
	public static final String GV_STRING = "String";
	public static final String GV_BOOLEAN = "Boolean";
	public static final String GV_PROBBLEM_MARKER = StudioCorePlugin.PLUGIN_ID + ".gvproblem";
	private List<GvProblem> gvProblmes;

	public GlobalVariablesValidator(GlobalVariablesMananger gvm) {
		gvm.addChangeListener(this);
		gvProblmes = new ArrayList<GvProblem>();
	}

	
	@Override
	public void globalVariablesChanged(GlobalVariablesProvider provider,
			String projectName) {

		if (provider instanceof GlobalVariablesProviderImpl) {

			gvProblmes.clear();

			GlobalVariablesProviderImpl providerInstance = (GlobalVariablesProviderImpl) provider;
			List<String> gvNames = providerInstance.getGvNames();
			if (gvNames.contains("")) {
				gvProblmes.add(new GvProblem(IMarker.SEVERITY_WARNING, "Global Variable(s) name cannot be empty."));
			}

			HashSet<String> gvSet = new HashSet<String>();
			//Duplicate GV check
			HashSet<String> duplicateGvSet = new HashSet<String>();
			for (String gvName : gvNames) {
				if (gvSet.contains(gvName.trim())) {
					duplicateGvSet.add(gvName);
				} else {
					gvSet.add(gvName);
				}
			}
			if (gvSet.size() < gvNames.size()) {				
				gvProblmes.add(new GvProblem(IMarker.SEVERITY_WARNING, "Duplicate Global Variable(s) "+String.join(",", duplicateGvSet)+" found. The highest order Global Variable will be used"));
			}
			Map<String, GlobalVariableDescriptor> namesMap = providerInstance.getNamesMap();

			Collection<GlobalVariableDescriptor> gvds = namesMap.values();
			for (GlobalVariableDescriptor gvd : gvds) {
				if(gvd.getName().contains("=")){
					gvProblmes.add(new GvProblem(IMarker.SEVERITY_ERROR, "Global Valriable(s) name can not contain = operator."));
				}
				if (gvd.getType().equals(GV_INTEGER)) {
					try {
						Integer.parseInt(gvd.getValueAsString());

					} catch (NumberFormatException e) {
						gvProblmes.add(new GvProblem(IMarker.SEVERITY_ERROR, "Integer variable "+gvd.getFullName()+" does not have an integer value"));
					}
					}
				else if(gvd.getType().equals(GV_BOOLEAN) &&
						!gvd.getValueAsString().equalsIgnoreCase("true") && 
						!gvd.getValueAsString().equalsIgnoreCase("false") &&
						!gvd.getValueAsString().equals("0") &&
						!gvd.getValueAsString().equals("1") 
						){
					gvProblmes.add(new GvProblem(IMarker.SEVERITY_ERROR, "Boolean variable "+gvd.getFullName()+" does not have a true/false value"));
				}
			}

			updateMarkers(projectName);

		}

	}

	private void addMarker(IResource resource) {

		try {
			for (GvProblem gvp : gvProblmes) {
				IMarker marker = resource.createMarker(GV_PROBBLEM_MARKER);
				marker.setAttribute(IMarker.SEVERITY, gvp.severity);
				marker.setAttribute(IMarker.MESSAGE, gvp.message);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clearGvProblemMarker(IResource resource) {
		IMarker[] markers;
		try {
			markers = resource.findMarkers(GV_PROBBLEM_MARKER, false, IResource.DEPTH_ZERO);
			for (IMarker marker : markers) {

				marker.delete();

			}
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private class GvProblem {
		int severity;
		String message;

		public GvProblem(int severity, String message) {
			this.severity = severity;
			this.message = message;
		}
	}

	
	private void updateMarkers(final String projectName) {

		Job j = new Job(projectName + "gvmarker") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IFile errorFile = ResourcesPlugin
							.getWorkspace()
							.getRoot()
							.getProject(projectName)
							.getFile(
									GlobalVariablesMananger.GLOBAL_VAR_FILE_NAME);
				if (errorFile.exists()) {
					clearGvProblemMarker(errorFile);
					addMarker(errorFile);
				}
				return Status.OK_STATUS;
			}
		};

		j.schedule();
	}
}
