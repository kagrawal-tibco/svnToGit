package com.tibco.cep.studio.ui.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainResourceSelector extends NavigatorResourcesSelector{

	protected PROPERTY_TYPES types;
	protected String projectName;
	
	/**
	 * @param parent
	 * @param currentProject
	 * @param list
	 * @param types
	 */
	public DomainResourceSelector(Shell parent, 
			                      String projectName,
			                      EList<DomainInstance> list, 
			                      PROPERTY_TYPES types) {
		super(parent);
		emptyListMessage = Messages.getString("MultipleStateMachinesSelector_empty_message");
		emptySelectionMessage = Messages.getString("Domain_Selector_error_message");
		this.types = types;
		this.projectName = projectName;
		setTitle(Messages.getString("DomainSelection_Wizard_Title"));
		setMessage(Messages.getString("DomainSelection_Wizard_Message"));
		addFilter(new StudioProjectsOnly(projectName));
		Set<String> extensions = new HashSet<String>();
		extensions.add("domain");
		addFilter(new DomainFileInclusionFilter(extensions, projectName, types));
		addFilter(new OnlyFileInclusionFilter(extensions));
		addFilter(new EObjectFilter());
		addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.DOMAIN));
		addFilter(new SharedDomainFilter(types));
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		if(list!=null){
		setCheckedList(list,projectName);
		}
	}
	
	public DomainResourceSelector(Shell parent, 
            String projectName,
            EList<Domain> list, 
            PROPERTY_TYPES types,List <IFile>existingResourcesList,boolean isRuleTemplate) {
		super(parent);
		emptyListMessage = Messages.getString("MultipleStateMachinesSelector_empty_message");
		emptySelectionMessage = Messages.getString("Domain_Selector_error_message");
		this.types = types;
		this.projectName = projectName;
		setTitle(Messages.getString("DomainSelection_Wizard_Title"));
		setMessage(Messages.getString("DomainSelection_Wizard_Message"));
		addFilter(new StudioProjectsOnly(projectName));
		Set<String> extensions = new HashSet<String>();
		extensions.add("domain");
		addFilter(new DomainFileInclusionFilter(extensions, projectName, types));
		addFilter(new EObjectFilter());
		addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.DOMAIN));
		addFilter(new SharedDomainFilter(types));
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		if(list!=null && isRuleTemplate ){
			setCheckedList(existingResourcesList,list,isRuleTemplate);
		}
}
	/**
	 * @param element
	 */
	public void addElements(Object element) {
		if (this.checkedElements == null) {
			checkedElements = new HashSet<Object>();
		}
		checkedElements.add(element);
	}
	
	/**
	 * @param element
	 */
	public void addGrayedElements(Object element) {
		if (this.grayededElements == null) {
			grayededElements = new HashSet<Object>();
		}
		grayededElements.add(element);
	}

	/**
	 * @param list
	 */
	protected void setCheckedList(EList<DomainInstance> list,String projectName){
		List<IFile> existingResourcesList= new ArrayList<IFile>();
			for(DomainInstance instance: list){
				Entity entity = IndexUtils.getEntity(projectName, 
						instance.getResourcePath(), ELEMENT_TYPES.DOMAIN);
				if(entity != null){
					Domain domain = (Domain)entity;
					IFile file = IndexUtils.getFile(projectName, domain);
	
					if (file != null && !file.exists()) {
						IFile fileLoc = IndexUtils.getLinkedResource(projectName,
								instance.getResourcePath());
						if (fileLoc != null && fileLoc.exists())
							file = fileLoc;
					}
	
					existingResourcesList.add(file);
				}
			}
		if(existingResourcesList.size()>0){
			existingInstances = new IFile[existingResourcesList.size()];
			existingResourcesList.toArray(existingInstances);
		}
	}
	
	protected void setCheckedList(List <IFile> existingResourcesList,EList<Domain> list,boolean isRuletemplate){

		if(existingResourcesList.size()>0){
			existingInstances = new IFile[existingResourcesList.size()];
			existingResourcesList.toArray(existingInstances);
		}
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.NavigatorResourcesSelector#addCheckedElements()
	 */
	protected void addCheckedElements(){
		if (this.checkedElements != null) {
			checkedElements.clear();
		}
		for(Object element: getTreeViewer().getCheckedElements()){
			if(element instanceof IProject){
				//to discard Project if grayed  
				if(this.grayededElements != null && !grayededElements.contains(element)){
					addChildren(((IProject)element));
					addSharedProjectElements();//Adding Shared Project Elements
		            break;			
				}
			}
			if(element instanceof IFolder){
				//to discard Folder if grayed
				if(this.grayededElements != null && !grayededElements.contains(element)){
					addChildren(((IFolder)element));
				}
			}
			if(element instanceof IFile){
				addElements(element);
			}
		
			//Processing Shared Elements
			if(element instanceof SharedElementRootNode){
				addSharedProjectElements();
			}else if(element instanceof DesignerProject){
				addSharedProjectElement((DesignerProject)element);
			}else if (element instanceof SharedElement){
				if(isValidSharedEntity((SharedElement)element)){
					addElements((SharedElement)element);
				}
			}
		}   	
	}

	/**
	 * @param container
	 */
	private void addChildren(IContainer container){
		for(Object element:CommonUtil.getResources(container)){
			if(element instanceof IFile){
				IFile file = (IFile)element;
				if(isValidResource(file)){
					addElements(element);
				}
			}
			if(element instanceof IContainer){
				addChildren((IContainer)element);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#isValidResource(java.lang.Object)
	 */
	protected boolean isValidResource(IFile file){
		try{
			if(CommonIndexUtils.DOMAIN_EXTENSION.equals(file.getFileExtension())) {
				Domain domain = IndexUtils.getDomain(file.getProject().getName(), IndexUtils.getFullPath(file));
				return isDomainTypeMatch(domain.getDataType().getLiteral());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param domainType
	 * @param type
	 * @return
	 */
	private boolean isDomainTypeMatch(String domainType){
		if(domainType.equalsIgnoreCase(types.getLiteral())){
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.NavigatorResourcesSelector#addGrayedElements()
	 */
	protected void addGrayedElements(){
		
		if (this.grayededElements == null) {
			grayededElements = new HashSet<Object>();
		}
		
		if (this.grayededElements != null) {
			grayededElements.clear();
		}
		for(Object element: getTreeViewer().getGrayedElements()){
			addGrayedElements(element);
		}   	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
	 */
	@Override
	public Object getFirstResult() {
		return checkedElements;
	}

	/**
	 * @return
	 */
	public Set<Object> getCheckedElements() {
		return checkedElements;
	}
	
	//*********************Share Elements Processing*****************
	
	/**
	 * 
	 */
	protected void addSharedProjectElements() {
		DesignerProject project = IndexUtils.getIndex(projectName);
		for(DesignerProject refProject:project.getReferencedProjects()){
			addSharedProjectElement(refProject);
		}
	}
	
	/**
	 * @param refProject
	 */
	protected void addSharedProjectElement(DesignerProject refProject) {
		for(DesignerElement element:refProject.getEntries()){
			if(element instanceof SharedElement){
				if(isValidSharedEntity((SharedElement)element)){
					addElements(element);
				}
			}
			if(element instanceof Folder){
				addSharedFolder((Folder)element);
			}
		}
	}
	
	/**
	 * @param folder
	 * @return
	 */
	protected void addSharedFolder(Folder folder) {
		for(DesignerElement element:folder.getEntries()){
			if(element instanceof SharedElement){
				if(isValidSharedEntity((SharedElement)element)){
					addElements(element);
				}
			}
			if(element instanceof Folder){
				addSharedFolder((Folder)element);
			}
		}
	}
	
	/**
	 * @param element
	 * @return
	 */
	protected boolean isValidSharedEntity(SharedElement element){
		if(element instanceof SharedEntityElement){
			SharedEntityElement sharedEntityElement = (SharedEntityElement)element;
			if(sharedEntityElement.getEntity() instanceof Domain){
				Domain domain = (Domain)sharedEntityElement.getEntity();
				return isDomainTypeMatch(domain.getDataType().getLiteral());
			}
		}
		return true;
	}
}