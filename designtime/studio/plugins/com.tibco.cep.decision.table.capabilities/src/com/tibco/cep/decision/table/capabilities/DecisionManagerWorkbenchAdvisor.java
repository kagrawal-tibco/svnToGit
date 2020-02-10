package com.tibco.cep.decision.table.capabilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.eclipse.ui.internal.wizards.NewWizardRegistry;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.tibco.cep.studio.util.StudioConfig;

/*
@author ssailapp
@date Sep 27, 2009 3:26:32 PM
 */
@SuppressWarnings("restriction")
public class DecisionManagerWorkbenchAdvisor extends WorkbenchAdvisor {
	
	public static final String PERSPECTIVE_ID = "com.tibco.cep.decision.table.perspective";
	
	//TODO: This class needs to be merged with the WorkbenchAdvisor subclass in decision.table.application plugin
	
	@Override
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		
		//All Decision Manager Application configurations moved here 
		configurer.setSaveAndRestore(true);
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS,
				false);
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, "topRight");
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP, true);
		String genClass = StudioConfig.getInstance().getProperty("bui.gen.class.option", "false");
		System.setProperty("bui.gen.class.option", genClass);
		
		disableActionSets();
		removeWizards();
	}
	
	public static void disableActionSets() {
		
		ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();

		IActionSetDescriptor[] actionSets = reg.getActionSets();
		
		String[] removeActionSets = new String[] {
		//"org.eclipse.mylyn.doc.actionSet",
		"org.eclipse.ui.cheatsheets.actionSet",
		"org.eclipse.team.ui.actionSet",
		"org.eclipse.ui.externaltools.ExternalToolsSet",
		"org.eclipse.jdt.debug.ui.JDTDebugActionSet",
		//"org.eclipse.mylyn.java.actionSet",
		//"org.eclipse.mylyn.java.actionSet.browsing",
		"org.eclipse.ui.edit.text.actionSet.presentation",
		"org.eclipse.jdt.ui.text.java.actionSet.presentation",
		"org.eclipse.jdt.ui.JavaElementCreationActionSet",
		"org.eclipse.jdt.ui.JavaActionSet",
		"org.eclipse.jdt.ui.A_OpenActionSet",
		"org.eclipse.jdt.ui.CodingActionSet",
		"org.eclipse.jdt.ui.SearchActionSet",
		"org.eclipse.pde.ui.SearchActionSet",
		"org.eclipse.ui.edit.text.actionSet.annotationNavigation",
		"org.eclipse.ui.edit.text.actionSet.navigation",
		"org.eclipse.ui.edit.text.actionSet.convertLineDelimitersTo",
		//"org.eclipse.mylyn.tasks.ui.navigation",
		//"org.eclipse.mylyn.tasks.ui.navigation.additions",
		"org.eclipse.search.searchActionSet",
		"org.eclipse.ecf.example.collab.ui.actionSet",
		"org.eclipse.jdt.junit.JUnitActionSet",
		"org.eclipse.team.cvs.ui.CVSActionSet",
		"org.tigris.subversion.subclipse.actionSet",
		"org.eclipse.debug.ui.breakpointActionSet",
		"org.eclipse.debug.ui.debugActionSet",
		"org.eclipse.debug.ui.launchActionSet",
		"org.eclipse.debug.ui.profileActionSet",
		"com.tibco.luna.eclipseui.actionSet",
		//org.eclipse.ui.NavigateActionSet,
		//org.eclipse.ui.actionSet.keyBindings,
		"org.eclipse.ui.WorkingSetModificationActionSet",
		"org.eclipse.ui.WorkingSetActionSet",
		//org.eclipse.ui.actionSet.openFiles,
		//"org.eclipse.mylyn.context.ui.actionSet",
		"org.eclipse.ant.ui.actionSet.presentation",
		"com.tibco.cep.studio.ui.actions",
		"com.tibco.cep.studio.ui.editors.projectactions"};
		
		for (int i = 0; i<actionSets.length; i++) {
			System.out.println(actionSets[i].getId());
			boolean found = false;
			for (int j = 0; j<removeActionSets.length; j++) {
				if (removeActionSets[j].equals(actionSets[i].getId())) {
					found = true;
					break;
				}
			}

			if (!found)
				continue;
			IExtension ext = actionSets[i].getConfigurationElement().getDeclaringExtension();
			reg.removeExtension(ext, new Object[] { actionSets });
		}
	}

	/**
	 * DM might not need all the wizards available. removing the ones not needed(BE-16218)
	 */
	public static void removeWizards(){
		NewWizardRegistry newWizardRegistry = (NewWizardRegistry)WorkbenchPlugin.getDefault().getNewWizardRegistry();
		Map<IExtension, List<IWizardDescriptor>>  extnsMap = new HashMap<IExtension, List<IWizardDescriptor>>();
		
		IExtension extension = Platform.getExtensionRegistry().getExtension("com.tibco.cep.studio.ui.editors.newWizards");
		extnsMap.put(extension, getWizardDescriptors("com.tibco.cep.studio.ui.category", 
													 Arrays.asList("com.tibco.cep.studio.ui.newDomainModelWizard","com.tibco.cep.studio.decision.table.ui.wizard.NewDecisionTableWizard")
													));
				
		extension = Platform.getExtensionRegistry().getExtension("com.tibco.cep.studio.ui.newWizards");
		extnsMap.put(extension, getWizardDescriptors("com.tibco.cep.sharedresource.ui.category", null));
		
		for (IExtension ex : extnsMap.keySet()) {
			List<IWizardDescriptor> removeWizardDescriptors = extnsMap.get(ex);
			Object[] objects = new Object[removeWizardDescriptors.size()];
			removeWizardDescriptors.toArray(objects);
			newWizardRegistry.removeExtension(ex, objects);
		}
		
	}
	
	/**
	 * 
	 * @param category
	 * @param excludeIds
	 * @return
	 */
	private static List<IWizardDescriptor> getWizardDescriptors(String category, List<String> excludeIds) {	
		if(excludeIds == null || excludeIds.isEmpty()){
			return Arrays.asList(PlatformUI.getWorkbench().getNewWizardRegistry().findCategory(category).getWizards());
		} else{
			List<IWizardDescriptor> toBeRemoved = new ArrayList<IWizardDescriptor>();
			List<IWizardDescriptor> wizards = new ArrayList<IWizardDescriptor>(Arrays.asList(PlatformUI.getWorkbench().getNewWizardRegistry().findCategory(category).getWizards()));
			for(IWizardDescriptor wizard : wizards){
				if(excludeIds.contains(wizard.getId())){
					toBeRemoved.add(wizard);
				}
			}
			wizards.removeAll(toBeRemoved);
			return wizards;			
		}
	}	
}