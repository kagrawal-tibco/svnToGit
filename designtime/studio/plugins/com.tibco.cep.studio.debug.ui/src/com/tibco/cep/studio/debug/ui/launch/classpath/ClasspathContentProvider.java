package com.tibco.cep.studio.debug.ui.launch.classpath;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;

public class ClasspathContentProvider implements ITreeContentProvider {
	
	private String fProjectName;
	private StudioProjectConfiguration fConfig;
	private DesignerProject fProject;
	private LIBRARY_PATH_TYPE[] typeFilter;
	private ClasspathTab tab;
	
	private ThirdPartyLibPath defaultThirdPartyLib;
	private CustomFunctionLibPath defaultCustomFnLib;

	public ClasspathContentProvider(LIBRARY_PATH_TYPE[] types, ClasspathTab tab){
		this.typeFilter = types;
		this.tab = tab;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement == null){
			return new Object[0];
		}
		List<Object> actualChildren = new LinkedList<Object>();
		if (parentElement instanceof DesignerProject) {
			return typeFilter;
		} else if(parentElement instanceof LIBRARY_PATH_TYPE) {
			LIBRARY_PATH_TYPE type = (LIBRARY_PATH_TYPE) parentElement;
			if(type == LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY) {
				EList<CoreJavaLibEntry> entries = fConfig.getCoreInternalLibEntries();
				for (CoreJavaLibEntry entry : entries) {
					actualChildren.add(entry);				
				}
				return actualChildren.toArray();
			}
			if(type == LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY) {
				return this.tab.getThirdPartyLib().toArray();
			} 
			if(type == LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY) {
				return this.tab.getCustomFunctionLib().toArray();
			}
		} else if(parentElement instanceof ThirdPartyLibPath 
				&& ((ThirdPartyLibPath) parentElement).isDefault()) {
			EList<ThirdPartyLibEntry> entries = fConfig.getThirdpartyLibEntries();
			for (ThirdPartyLibEntry entry : entries) {
				actualChildren.add(entry);				
			}
			return actualChildren.toArray();
		} else if(parentElement instanceof CustomFunctionLibPath 
				&& ((CustomFunctionLibPath)parentElement).isDefault()) {
			EList<CustomFunctionLibEntry> entries = fConfig.getCustomFunctionLibEntries();
			for (CustomFunctionLibEntry entry : entries) {
				actualChildren.add(entry);				
			}
			return actualChildren.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof DesignerProject) {
			return null;
		} else if(element instanceof LIBRARY_PATH_TYPE) {
			return this.fProject;
		} else if ( element instanceof ThirdPartyLibPath) {
			return LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY;
		} else if ( element instanceof CustomFunctionLibPath) {
			return LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY;
		} else if( element instanceof CoreJavaLibEntry) {
			return LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY;
		} else if(element instanceof ThirdPartyLibEntry) {
			return defaultThirdPartyLib;
		} else if(element instanceof CustomFunctionLibEntry) {
			return defaultCustomFnLib;
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof DesignerProject) {
			return true;
		} else if(element instanceof LIBRARY_PATH_TYPE) {
			return true;
		} else if ( element instanceof BuildPathEntry) {
			return false;
		} else if (element instanceof ThirdPartyLibPath) {
			if(((ThirdPartyLibPath)element).isDefault()){
				defaultThirdPartyLib = (ThirdPartyLibPath)element;
				return true;
			}
			return false;
		} else if(element instanceof CustomFunctionLibPath) {
			if(((CustomFunctionLibPath)element).isDefault()) {
				defaultCustomFnLib = (CustomFunctionLibPath) element;
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if(newInput instanceof String) {
			this.fProjectName = (String)newInput;
		} else 	if(newInput instanceof DesignerProject) {
			this.fProject = (DesignerProject) newInput;
		} else if ( newInput instanceof StudioProjectConfiguration ) {
			this.fConfig = (StudioProjectConfiguration) newInput;
		}
		initialize();
	}
	
	private void initialize() {
		if(this.fProjectName != null) {
			/*StudioCorePlugin.getDesignerModelManager().getIndex(fProjectName)*/
			this.fProject = StudioProjectCache.getInstance().getIndex(fProjectName);
		} 
		if(this.fProject != null) {
			this.fProjectName = this.fProject.getName();
		}
		if(this.fProjectName != null) {
			this.fConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(fProjectName);
		}
	}


}