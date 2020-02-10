package com.tibco.cep.decisionproject.persistence.impl;

/**
 * @author rmishra
 * this singeleton class loads DecisionProject and keeps it in memory 
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.decisionproject.persistence.DecisionProjectLoadedListener;
import com.tibco.cep.decisionprojectmodel.DecisionProject;

public class DecisionProjectLoader {
	
	private static DecisionProjectLoader dpLoader;
	private DecisionProject decisionProject;
	private String baseDirectory;
	private String decisionProjectName;
	private String decisionProjectFullPath;
	private String brmsURL;
	private String rmsSelectedProject;// The RMS project
	private ArrayList<DecisionProjectLoadedListener> projectLoadedListeners = null;
	//private BUISetting buiSetting;

	private List<String> resourceNames = null;

	private DecisionProjectLoader() {
	}

	synchronized public static DecisionProjectLoader getInstance() {
		if (dpLoader == null) {
			dpLoader = new DecisionProjectLoader();
		}
		return dpLoader;
	}

	public String getRMSSelectedProject() {
		// Also load the project settings file
		Properties projectSettingprops = new Properties();
		try {
			projectSettingprops.load(new FileInputStream(baseDirectory
					+ File.separatorChar + ".project"));
			rmsSelectedProject = projectSettingprops
					.getProperty("rms.project.name");
			if (rmsSelectedProject == null) {
				throw new IllegalArgumentException(
						"The project name cannot be blank");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rmsSelectedProject;
	}

	public void loadDecisionProject(String dpLocation, boolean loadSettings) throws Exception {
		if (dpLocation == null) {
//			LOGGER.logError(this.getClass().getName(),
//					"Decision project location path can not be null.");
//			throw new IllegalArgumentException(
//					"Decision project location path can not be null.");
		}
		
		if (! (new File(dpLocation).exists()))
		{
//			LOGGER.logError(this.getClass().getName(),
//				"Decision project location [" + dpLocation + "] is invalid.");
//			throw new IllegalArgumentException(
//				"Decision project location [" + dpLocation + "] is invalid.");
		}
		
		baseDirectory = dpLocation.substring(0, dpLocation
				.lastIndexOf(File.separatorChar));
		decisionProjectFullPath = dpLocation;
		
		//update recent Project Locations for Display 
		//@since 3.0.2
		if (loadSettings) {
			updateRecent(decisionProjectFullPath);
		}
	
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createFileURI(dpLocation);
		if (uri != null) {
			Resource resource = rs.getResource(uri, true);
			if (resource != null) {
				List<EObject> resourceList = resource.getContents();
				for (EObject eobject : resourceList) {
					if (eobject instanceof DecisionProject) {
						decisionProject = (DecisionProject) eobject;
//						LOGGER.logDebug(CLASS_NAME,
//								"Decision project loaded successfully ::");
						// load BUI setting
						//loadBUISetting();
						if (resourceNames == null) {
							resourceNames = new ArrayList<String>();
						} else {
							resourceNames.clear();
						}
						
						if (projectLoadedListeners != null) {
							for (DecisionProjectLoadedListener pll : projectLoadedListeners) {
								pll.projectLoaded(decisionProject);
							}
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * It loads BUI Setting with Decision Project
	 *//*
	private void loadBUISetting() {
		ResourceSet rs = new ResourceSetImpl();
		File buiSettingFile = new File(baseDirectory + File.separatorChar
				+ decisionProject.getName() + BUI_SETTING_EXTENSION);
		if (buiSettingFile.exists()) {
			URI uri = URI.createFileURI(baseDirectory + File.separatorChar
					+ decisionProject.getName() + BUI_SETTING_EXTENSION);
			if (uri != null) {
				Resource resource = rs.getResource(uri, true);
				if (resource != null) {
					List<EObject> resourceList = resource.getContents();
					for (EObject eobject : resourceList) {
						if (eobject instanceof BUISetting) {
							buiSetting = (BUISetting) eobject;
							LOGGER.logDebug(CLASS_NAME,
									"BUI Setting loaded successfully ::");
							break;
						}
					}
				}
			}
		}
	}*/
	
	/**
	 * @param decisionProjectFullPath
	 * @since 3.0.2
	 */
	public void updateRecent(String decisionProjectFullPath){
		/*DecisionManagerSettingsLoader.getInstance().updateRecent(DecisionManagerSettingsLoader.getInstance().getProjects(), 
				 decisionProjectFullPath, Integer.parseInt(BUIConfig.getProperty("bui.keep.last.active.projects.size", "4")));*/
	}
	
	/**
	 * @param pll
	 */
	public void addProjectLoadedListener(DecisionProjectLoadedListener pll) {
		if (projectLoadedListeners == null) {
			projectLoadedListeners = new ArrayList<DecisionProjectLoadedListener>(
					2);
		}
		projectLoadedListeners.add(pll);
	}

	public DecisionProject getDecisionProject() {
		return decisionProject;
	}

	public void setDecisionProject(DecisionProject decisionProject) {
		this.decisionProject = decisionProject;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public String getDecisionProjectName() {
		return decisionProjectName;
	}

	public void setDecisionProjectName(String decisionProjectName) {
		this.decisionProjectName = decisionProjectName;
	}

	public String getBrmsURL() {
		return brmsURL;
	}

	public void setBrmsURL(String brmsURL) {
		this.brmsURL = brmsURL;
	}

	public String getDecisionProjectFullPath() {
		return decisionProjectFullPath;
	}

	public void setDecisionProjectFullPath(String decisionProjectFullPath) {
		this.decisionProjectFullPath = decisionProjectFullPath;
	}

	/*public BUISetting getBuiSetting() {
		return buiSetting;
	}

	public void setBuiSetting(BUISetting buiSetting) {
		this.buiSetting = buiSetting;
	}*/

	public List<String> getResurceNames() {
		return resourceNames;
	}

	public void setResurceNames(List<String> resurceNames) {
		this.resourceNames = resurceNames;
	}
}