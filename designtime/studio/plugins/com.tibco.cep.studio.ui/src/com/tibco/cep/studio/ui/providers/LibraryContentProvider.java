package com.tibco.cep.studio.ui.providers;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;

/**
 * Content Provider for custom function jars which are then on the build path
 * @author rhollom
 *
 */
public class LibraryContentProvider implements ITreeContentProvider {
	
	private String fProjectName;
	private StudioProjectConfiguration fConfig;
	private DesignerProject fProject;
	private LIBRARY_PATH_TYPE[] typeFilter;

	public LibraryContentProvider(LIBRARY_PATH_TYPE[] types){
		this.typeFilter = types;

	}

	@Override
	public Object[] getElements(Object inputElement) {			
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
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
			this.fProject = StudioCorePlugin.getDesignerModelManager().getIndex(fProjectName);
		} 
		if(this.fProject != null) {
			this.fProjectName = this.fProject.getName();
		}
		if(this.fProjectName != null) {
			this.fConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(fProjectName);
		}
		
	}

	LIBRARY_PATH_TYPE[]getFilteredTypes() {
		return typeFilter;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement == null){
			return new Object[0];
		}
		if (parentElement instanceof DesignerProject) {
			return getFilteredTypes();
		} else if( parentElement instanceof LIBRARY_PATH_TYPE) {
			LIBRARY_PATH_TYPE type = (LIBRARY_PATH_TYPE) parentElement;
			Set<BuildPathEntry> actualChildren = new LinkedHashSet<BuildPathEntry>();
			if(type == LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY) {
				EList<CoreJavaLibEntry> entries = fConfig.getCoreInternalLibEntries();
				for (CoreJavaLibEntry entry : entries) {
					actualChildren.add(entry);				
				}
			}
			if(type == LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY) {
				EList<ThirdPartyLibEntry> entries = fConfig.getThirdpartyLibEntries();
				for (ThirdPartyLibEntry entry : entries) {
					actualChildren.add(entry);				
				}
			} 
			if(type == LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY) {
				EList<CustomFunctionLibEntry> entries = fConfig.getCustomFunctionLibEntries();
				for (CustomFunctionLibEntry entry : entries) {
					actualChildren.add(entry);				
				}
			} 
			if(type == LIBRARY_PATH_TYPE.PROJECT_LIBRARY) {
				EList<ProjectLibraryEntry> entries = fConfig.getProjectLibEntries();
				for (ProjectLibraryEntry entry : entries) {
					actualChildren.add(entry);				
				}
			}
			return actualChildren.toArray();
		} else if( parentElement instanceof JavaLibEntry && !(parentElement instanceof CoreJavaLibEntry)) {
			NativeLibraryPath libPath = ((JavaLibEntry)parentElement).getNativeLibraryLocation();
			if(libPath == null) {
				// create an empty one
				libPath = ConfigurationFactory.eINSTANCE.createNativeLibraryPath();
				((JavaLibEntry)parentElement).setNativeLibraryLocation(libPath);
			}
			return new Object[] {libPath};
		}

		return new Object[0];

	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof DesignerProject) {
			return null;
		} else if(element instanceof LIBRARY_PATH_TYPE) {
			return this.fProject;
		} else if ( element instanceof BuildPathEntry) {
			BuildPathEntry entry = (BuildPathEntry) element;
			return entry.getEntryType();
		} else if( element instanceof NativeLibraryPath) {
			return ((NativeLibraryPath)element).eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof DesignerProject) {
			return true;
		} else if(element instanceof LIBRARY_PATH_TYPE) {
			return true;
		} else if ( element instanceof JavaLibEntry) {
			return true;
		} 
		return false;
	}


}
