package com.tibco.cep.bpmn.core.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class BPMNElementResolutionProvider implements
		IElementResolutionProvider {

	private  ThreadLocal<com.tibco.cep.designtime.core.model.element.Concept> conceptLocal = new ThreadLocal<com.tibco.cep.designtime.core.model.element.Concept>();
	
	private EObject index;
	IProject project;

	@Override
	public Object searchContainer(ElementReference reference,
			IResolutionContext resolutionContext, String projectName) {
		if (projectName.endsWith(".projlib")) {
			return null; // TODO : will the search on the parent index also find elements from the projlib?
		}
		this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		
//		if (!BpmnProjectNatureManager.getInstance().isBpmnProject(project)) 
//			return null;// if project is not bpmn type, no point of going ahead
//		
		// TODO : Get the root bpmn index and search for the element reference
		Object element = null;
		this.index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
		if(index == null)
			return null;
		
		DefaultBpmnIndex di = new DefaultBpmnIndex(index);
		String elementName = reference.getName();
		if(resolutionContext.getMode() == IResolutionContext.ELEMENT_MODE) {
			IFolder f = getFolder(this.project,elementName);
			if(f != null && isProcessFolder(f)) {
				element = f;
			}			
		}
		if(element == null) {
			EObject proc = di.getProcess(elementName);
			if(proc != null) {
				ProcessAdapter pa = new ProcessAdapter(proc,new ProcessOntologyAdapter(index),(Object[]) null);
				IPath p = new Path(pa.getFullPath());
				IFile file = project.getFile(p);
				if(file != null) {
					if(file.getParent().equals(this.project)) {
						// the process file is in the root folder of the project
						element = pa;
					}
				}
			} 
		}
		if (element instanceof IFolder) {
			IFolder folder = (IFolder)element;
			String path = folder.getFullPath().makeAbsolute().toPortableString();
			path = path.replace("/" + folder.getProject().getName(), "");
			element = CommonIndexUtils.getElement(folder.getProject().getName(), path);
		}
		return element;
	}
	
	private IFolder getFolder(IProject p,String name) {
		try {
			for(IResource r:p.members()) {
				if(r instanceof IFolder && r.getName().equals(name)){
					return (IFolder) r;
				}
			}
		} catch (CoreException e) {
			BpmnCorePlugin.log(e);
		}
		return null;
	}
	
	private IResource getMember(IContainer p,String name) {
		try {
			for(IResource r:p.members()) {
				if(r instanceof IFolder){
					if(r.getName().equals(name)){
						return r;
					}else {
						IResource member = getMember((IFolder)r, name);
						if(member != null)
							return member;
						
					}
				}else if(r instanceof IFile){
					IFile file = (IFile)r;
					if(file.getName().equals(name+"."+ file.getFileExtension())){
						return r;
					}
				}
				
			}
		} catch (CoreException e) {
			BpmnCorePlugin.log(e);
		}
		return null;
	}
	
	boolean isProcessFolder(IFolder f) {
		boolean hasProcesses = false;
		try {
			for(IResource r: f.members()) {
				if(r instanceof IFile && r.getFullPath().getFileExtension().equals(CommonIndexUtils.PROCESS_EXTENSION)) {
					hasProcesses = true;
					break;					
				}
			}
		} catch (CoreException e) {
			BpmnCorePlugin.log(e);
		}
		return hasProcesses;
	}
	
	@SuppressWarnings("unused")
	private List<IResource> getFolders(String projectName,final String name) {
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		final List<IResource> rl = new ArrayList<IResource>();
		try {
			p.accept(new IResourceVisitor() {
				
				@Override
				public boolean visit(IResource resource) throws CoreException {
					if(resource instanceof IFolder && name.equals(resource.getName()))  {
						rl.add(resource);
						return false;
					}
					else
						return true;
				}
			});
		} catch (CoreException e) {
			BpmnCorePlugin.log(e);
		}
		return rl;
	}

	@Override
	public Object[] getChildren(Object element) {
		if(element instanceof ProcessAdapter) {
			ProcessAdapter adaptor = (ProcessAdapter)element;
			Concept concept = conceptLocal.get();
			if(concept == null){
				concept = adaptor.cast(com.tibco.cep.designtime.core.model.element.Concept.class);
				conceptLocal.set(concept);
			}
			if (concept != null) {
				EList<PropertyDefinition> allProperties = concept.getAllProperties();
				return (PropertyDefinition[]) allProperties
						.toArray(new PropertyDefinition[allProperties.size()]);
			}
		}
		return null;
	}

	@Override
	public Object resolveAttributeReference(String attributeName, Object qualifier,
			ElementReference reference, String projectName) {
		if (qualifier instanceof VariableDefinition) {
			qualifier = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) qualifier);
		}
		if ("Process".equals(qualifier)) {
			if ("extId".equals(attributeName)) {
				return attributeName;
			}
			if ("id".equals(attributeName)) {
				return attributeName;
			}
			if ("taskId".equals(attributeName)) {
				return attributeName;
			}
			if ("templateVersion".equals(attributeName)) {
				return attributeName;
			}
			if ("parent".equals(attributeName)) {
				return attributeName;
			}
			return attributeName;
		}
		if (!(qualifier instanceof ProcessAdapter)) {
			return null;
		}
		ProcessAdapter pa = (ProcessAdapter) qualifier;
		Collection<com.tibco.cep.designtime.model.element.PropertyDefinition> attributeDefinitions = pa.getAttributeDefinitions();
		for (com.tibco.cep.designtime.model.element.PropertyDefinition propertyDefinition : attributeDefinitions) {
			if (propertyDefinition.getName().equals(attributeName)) {
				if ("parent".equals(attributeName)) {
					// TODO : resolve to whatever parent refers to
					return attributeName;
				}

				return attributeName;
			}
		}
		return null;
	}

	@Override
	public NodeType getAttributeType(String attributeName, boolean array) {
		if ("extId".equals(attributeName)
				|| "state".equals(attributeName)
				|| "taskId".equals(attributeName)) {
			return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("templateVersion".equals(attributeName)) {
			return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		if ("parent".equals(attributeName)) {
			return new NodeType(NodeTypeFlag.CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, array);
		}
		return null;
	}

	@Override
	public Object processQualifier(ElementReference reference,
			Object qualifier, String projectName, boolean contentAssistMode,
			boolean isEntityRef) {
		// Look at the 'qualifier' object and attempt to find the element reference 
		// (i.e. a bpmn property)
		Object element = null;
		this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//		if (!BpmnProjectNatureManager.getInstance().isBpmnProject(project)) 
//			return null;// if project is not bpmn type, no point of going ahead
//		
		this.index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
		if(index == null)
			return null;
		
		DefaultBpmnIndex di = new DefaultBpmnIndex(index);
		String elementName = reference.getName();
		if(qualifier instanceof Folder) {
//			Folder folder = (Folder) qualifier;
			IResource member = getMember(this.project,elementName);
			// first check if the element is a folder or not
			if(member instanceof IFolder && isProcessFolder((IFolder)member)) {
				element = member;
			} else if (member instanceof IFile) { // not a folder , could be a process
				IFile file = (IFile) member;
				if (file != null) {
					EObject proc = di.getProcess(elementName);
					if (proc != null) {
						ProcessAdapter pa = new ProcessAdapter(proc,
								new ProcessOntologyAdapter(index),
								(Object[]) null);
						if (pa != null) {
							if (pa.getFullPath().equals(file.getFullPath().makeRelativeTo(
									this.project.getFullPath()).removeFileExtension().makeAbsolute().toPortableString())) {
								element = pa;
								Concept cast = pa
										.cast(com.tibco.cep.designtime.core.model.element.Concept.class);
								conceptLocal.set(cast);
							}
						}
					}
				}
			}
		} else if(qualifier instanceof ProcessAdapter) {
			ProcessAdapter adaptor = (ProcessAdapter)qualifier;
			Concept concept = conceptLocal.get();
				if(concept == null){
					concept = adaptor.cast(com.tibco.cep.designtime.core.model.element.Concept.class);
					conceptLocal.set(concept);
				}
			element = findProperty(concept, elementName);
//			EList<PropertyDefinition> properties = concept.getProperties();
		} else if(qualifier instanceof PropertyDefinition){
			PropertyDefinition property =(PropertyDefinition)qualifier;
			if (property.getConceptTypePath() != null) {
				Concept concept = (Concept)property.eContainer();
				return searchEntity(reference, qualifier, concept, false);
			}
		}
		
		return element;
	}
	
	
	
	private Object searchEntity(ElementReference reference,
			Object qualifier, Entity entity, boolean entityRef) {
		// first, check if this is a property on the qualifier, only if the qualifier is NOT a direct entityRef
		PropertyDefinition property = findProperty(entity, reference.getName());
		if (property != null) {
			if (!entityRef || entity instanceof Scorecard) {
				return property;
			}
		}
		// next, check if this is a constructor for the qualifier
		if (qualifier instanceof EntityElement && reference.getName().equals(((EntityElement) qualifier).getName())) {
			// TODO : also check whether there are additional segments after this
			// if so, this is not a constructor.
			// this is a constructor
			if (reference.isMethod()) {
				return entity; // only a constructor if it is a method call
			}
		}
		return null;
	}
	
	private static PropertyDefinition findProperty(Entity entity, String name) {
		if (entity instanceof Concept) {
			Concept concept = (Concept) entity;
			EList<PropertyDefinition> allProperties = concept.getAllProperties();
			for (PropertyDefinition propertyDefinition : allProperties) {
				if (name.equals(propertyDefinition.getName())) {
					return propertyDefinition;
				}
			}
		}

		return null;
	}
	
	@Override
	public Object resolveType(String projectName, String type) {
//		Object element = null;

        if (null == type) {
            return null;
        }

        this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//		if (!BpmnProjectNatureManager.getInstance().isBpmnProject(project)) 
//			return null;// if project is not bpmn type, no point of going ahead
		
		this.index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
		if(index == null)
			return null;
		
		DefaultBpmnIndex di = new DefaultBpmnIndex(index);
		String elementName = type;
		String elements[] = elementName.split("[.]");
		IResource mr = null;
		for(String e:elements) {
			IResource r = getMember(project, e);
			if(r != null) {
				if(r instanceof IFolder) {
					mr = r;
				} else if(r instanceof IFile) {
					mr = r;
				}
			} 
			
			
		}
		if(mr != null && mr instanceof IFile) {
			EObject proc = di.getProcess(mr.getFullPath().removeFileExtension().lastSegment());
			if(proc != null) {
				ProcessAdapter pa = new ProcessAdapter(proc,new ProcessOntologyAdapter(index));
				String fullPath = ModelUtils.convertPackageToPath(type);
				if (fullPath.equals(pa.getFullPath())) {
					return pa;
				}
			}
		}
		
		return null;
	}

	public static final Object[] EMPTY_CHILDREN = new Object[0];
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider#getElementContainerChildren(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object[] getElementContainerChildren(Object element, String projectName) {
		try {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//			if (!BpmnProjectNatureManager.getInstance().isBpmnProject(project)) 
//				return EMPTY_CHILDREN;// if project is not bpmn type, no point of going ahead
			this.index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
			if(index == null)
				return null;
			DefaultBpmnIndex di = new DefaultBpmnIndex(index);
			if (element instanceof Folder) {
				IFolder folder = getFolder(((Folder)element), projectName);
				if(folder != null){
					List<Object> objs = new ArrayList<Object>();
					for(IResource r: folder.members()) {
						if(r instanceof IFile){
							IFile file = (IFile)r;
							if(file.getFileExtension().equals(CommonIndexUtils.PROCESS_EXTENSION)){
								if(file != null) {
									EObject proc = di.getProcess(file.getName().replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, ""));
									if(proc != null) {
										ProcessAdapter pa = new ProcessAdapter(proc,new ProcessOntologyAdapter(index),(Object[]) null);
										objs.add(pa);
									}
								}
							}
						}
					}
					if (objs.size() >0) {
						Object[] objarr = new Object[objs.size()];
						return objs.toArray(objarr);
					}
				}
			}
			if (element instanceof ProcessAdapter) {
				ProcessAdapter adapter = (ProcessAdapter)element;
				Concept concept = conceptLocal.get();
				if(concept == null){
					concept = adapter.cast(com.tibco.cep.designtime.core.model.element.Concept.class);
					conceptLocal.set(concept);
				}
				EList<PropertyDefinition> allProperties = concept.getAllProperties();
				Object[] objarr = new Object[allProperties.size()];
				return allProperties.toArray(objarr);
			}
				
			
		} catch (CoreException e) {
			BpmnCorePlugin.log(e);
		}
		return EMPTY_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider#getElementText(java.lang.Object)
	 */
	@Override
	public String getElementText(Object element) {
		if (element instanceof ProcessAdapter) {
			return ((ProcessAdapter)element).getName();
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider#getElementImageType(java.lang.Object)
	 */
	@Override
	public String getElementImageType(Object element) {
		if (element instanceof ProcessAdapter) {
			return CommonIndexUtils.PROCESS_EXTENSION;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider#getElementInfo(java.lang.Object)
	 */
	@Override
	public String getElementInfo(Object element, String projectName) {
		if (element instanceof Folder) {
			Folder folder = (Folder)element;
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("Folder: ");
			buffer.append(folder.getName());
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			IFolder folder_ = project.getFolder(folder.getName());
			try {
				List<IFile> flist = new ArrayList<IFile>();
				if(folder_ != null){
					for(IResource r: folder_.members()) {
						if(r instanceof IFile){
							IFile file = (IFile)r;
							if(file.getFileExtension().equals(CommonIndexUtils.PROCESS_EXTENSION)){
								flist.add(file);
							}
						}
					}
				}
				
				EList<DesignerElement> entries = folder.getEntries();
				buffer.append("\n contains ");
				buffer.append(entries.size() + flist.size());
				buffer.append(" elements:\n");
				for (DesignerElement designerElement : entries) {
					buffer.append(" - ");
					buffer.append(designerElement.getName());
					buffer.append("\n");
				}
				for (IFile f :  flist) {
					buffer.append(" - ");
					buffer.append(f.getName().replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, ""));
					buffer.append("\n");
				}
				return buffer.toString();
				
			} catch (CoreException e) {
				BpmnCorePlugin.log(e);
			}
		} else if (element instanceof ProcessAdapter) {
			ProcessAdapter adapter = (ProcessAdapter)element;
			StringBuffer buffer = new StringBuffer();
			buffer.append("\n");
			buffer.append("Process: ");
			buffer.append(adapter.getName());
			buffer.append("\n");
			buffer.append("Folder: ");
			buffer.append(adapter.getFolderPath());
			return buffer.toString();
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider#getElementAttributes(java.lang.Object)
	 */
	@Override
	public List<Map<String, String>> getElementAttributes(Object element) {
		List<Map<String, String>> list =  new ArrayList<Map<String,String>>();
		
		if (element instanceof ProcessAdapter) {
			ProcessAdapter pa = (ProcessAdapter) element;
			for(com.tibco.cep.designtime.model.element.PropertyDefinition pd:pa.getAttributeDefinitions()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Name", pd.getName());
				map.put("Info", String.format("Attribute '%s'",pd.getName()));
				map.put("Type", PROPERTY_TYPES.get(pd.getType()).getLiteral());
				list.add(map);
			}
		} else if ("Process".equals(element)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Name", "id");
			map.put("Info", "Attribute 'id'");
			map.put("Type", "Long");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("Name", "extId");
			map.put("Info", "Attribute 'extId'");
			map.put("Type", "String");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("Name", "taskId");
			map.put("Info", "Attribute 'taskId'");
			map.put("Type", "String");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("Name", "state");
			map.put("Info", "Attribute 'state'");
			map.put("Type", "String");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("Name", "templateVersion");
			map.put("Info", "Attribute 'templateVersion'");
			map.put("Type", "int");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("Name", "parent");
			map.put("Info", "Attribute 'parent'");
			map.put("Type", "Concept");
			list.add(map);
		}
		return list;
	}
	
	private IFolder getFolder(Folder folder, String projName){
		IFolder result = null;
		Stack<Folder> folderStack = new Stack<Folder>();
		folderStack.push(folder);
		while (folder.eContainer() != null && !(folder.eContainer() instanceof DesignerProject) && (folder.eContainer() instanceof Folder)) {
			EObject eContainer = folder.eContainer();
			if(eContainer instanceof Folder){
				folderStack.push((Folder)eContainer);
				folder = (Folder)eContainer;
			}
		}
		
		Folder pop = folderStack.pop();
		result = project.getFolder(pop.getName());
		while(!folderStack.isEmpty()){
			pop = folderStack.pop();
			result = result.getFolder(pop.getName());
		}
		
		if(!result.exists())
			result = null;
		
		return result;
	}

}
