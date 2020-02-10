package com.tibco.cep.studio.ui.navigator.providers;

import java.io.File;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.IDescriptionProvider;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;
import com.tibco.cep.studio.ui.navigator.model.DomainInstanceNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;
import com.tibco.cep.studio.ui.navigator.model.StateMachineAssociationNode;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class ProjectLibraryLabelProvider extends LabelProvider implements IStyledLabelProvider, ILightweightLabelDecorator, IColorProvider,IDescriptionProvider {

	private HashMap<String, Image> fImageCache = new HashMap<String, Image>();

	public String getText(Object obj) {
		return getLabel(obj);
	}
	
	public Image getImage(Object element) {
		if (element instanceof SharedElement) {
//			String name = ((SharedElement) element).getName();
			String fileName = ((SharedElement) element).getFileName();
			String fileExtension = "";
			if (fileName.lastIndexOf('.') > 0) {
				fileExtension = fileName.substring(fileName.lastIndexOf('.')+1);
			}
			if (fImageCache.get(fileExtension) != null) {
				return fImageCache.get(fileExtension);
			}
			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor ed = registry.getDefaultEditor(fileName);
			if (ed != null) {
				fImageCache.put(fileExtension, ed.getImageDescriptor().createImage());
				return fImageCache.get(fileExtension);
			} else {
				ImageDescriptor desc = null;
				IContentType[] contentTypes = Platform.getContentTypeManager().findContentTypesFor(fileName);
				if (contentTypes != null) {
					for (IContentType contentType : contentTypes) {
						IEditorDescriptor editor = registry.getDefaultEditor(fileName, contentType);
						if (editor != null) {
							desc = editor.getImageDescriptor();
							break;
						}
					}
				}
				if (desc != null) {
					fImageCache.put(fileExtension, desc.createImage());
					return fImageCache.get(fileExtension);
				}
			}
		}
//		if (element instanceof DesignerElement) {
//			ELEMENT_TYPES elementType = ((DesignerElement) element).getElementType();
//			
//			String fileExtension = IndexUtils.getFileExtension(elementType);
//			if (fImageCache.get(fileExtension) != null) {
//				return fImageCache.get(fileExtension);
//			}
//			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
//			IEditorDescriptor ed = registry.getDefaultEditor(((DesignerElement) element).getName()+"."+fileExtension);
//			if (ed != null) {
//				fImageCache.put(fileExtension, ed.getImageDescriptor().createImage());
//				return fImageCache.get(fileExtension);
//			}
//		}
		if (element instanceof SharedElementRootNode) {
			return StudioUIPlugin.getDefault().getImage("icons/library_obj.gif");
		}
		if (element instanceof StateMachineAssociationNode) {
			return NavigatorPlugin.getDefault().getImage("icons/sm_association.png");
		}
		if (element instanceof DomainInstanceNode) {
			return NavigatorPlugin.getDefault().getImage("icons/associateDomainModel.png");
		}
		if (element instanceof StudioNavigatorNode) {
			element = ((StudioNavigatorNode) element).getEntity();
		}
        if (element instanceof DesignerProject) {
        	return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		} else if (element instanceof EntityElement) {
			element = ((EntityElement)element).getEntity();
		}
        // designer elements
		if (element instanceof RuleElement) {
			return StudioUIPlugin.getDefault().getImage("icons/rules_folder.png");
		}
		if (element instanceof ElementContainer) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		if (element instanceof Destination) {
			return StudioUIPlugin.getDefault().getImage("icons/destination.png");
		} else if (element instanceof PropertyDefinition) {
			return StudioUIUtils.getPropertyImage(((PropertyDefinition) element).getType());
		} 
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	}
	
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof DesignerProject) {
			String archivePath = ((DesignerProject) element).getArchivePath();
			if (archivePath != null && archivePath.endsWith(".projlib")) {
				File archiveFile = new File(archivePath);
				if (!archiveFile.exists()) {
					return ColorConstants.red;
				}
			}
		}
//		if (element instanceof ElementContainer) {
//			if (containsDuplicateResources((ElementContainer) element)) {
//				return ColorConstants.orange;
//			}
//		}
		if (element instanceof SharedRuleElement) {
			String fp = ((SharedRuleElement) element).getFolder() + ((SharedRuleElement) element).getFileName();
			if (isDuplicateResouce((EObject) element, fp)) {
				return ColorConstants.orange;
			}
		}
		Entity entity = null;
		if (element instanceof SharedEntityElement) {
			entity = ((SharedEntityElement) element).getSharedEntity();
		} else if (element instanceof EntityElement) {
			entity = ((EntityElement) element).getEntity();
		}
		if (entity != null) {
			String fullPath = entity.getFullPath()+"."+IndexUtils.getFileExtension(entity);
			if (isDuplicateResouce((EObject) element, fullPath)) {
				return ColorConstants.orange;
			}
		}

		if (element instanceof DesignerElement) {
			return ColorConstants.gray;
		}
		if (element instanceof StudioNavigatorNode) {
			return ColorConstants.gray;
		}
		return null;
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof ElementContainer) {
			if (containsDuplicateResources((ElementContainer) element)) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/warning_overlay.gif"), IDecoration.BOTTOM_LEFT);
			}
		}
		if (element instanceof SharedRuleElement) {
			String fp = ((SharedRuleElement) element).getFolder() + ((SharedRuleElement) element).getFileName();
			if (isDuplicateResouce((EObject) element, fp)) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/warning_overlay.gif"), IDecoration.BOTTOM_LEFT);
			}
		}
		if (element instanceof SharedEntityElement) {
			element = ((SharedEntityElement) element).getSharedEntity();
		}
		if (element instanceof EntityElement) {
			element = ((EntityElement) element).getEntity();
		}
		if (element instanceof Entity) {
			Entity entity = (Entity) element;
			String fullPath = entity.getFullPath()+"."+IndexUtils.getFileExtension(entity);
			if (isDuplicateResouce((Entity) element, fullPath)) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/warning_overlay.gif"), IDecoration.BOTTOM_LEFT);
			}
		}
//		decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/lock_overlay.gif"), IDecoration.BOTTOM_RIGHT);
		decoration.addSuffix(Messages.getString("studio.navigator.label.read.only"));
	}

	private boolean containsDuplicateResources(ElementContainer container) {
		EList<DesignerElement> entries = container.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			DesignerElement designerElement = entries.get(i);
			if (designerElement instanceof EntityElement) {
				Entity entity = ((EntityElement) designerElement).getEntity();
				String fullPath = entity.getFullPath()+"."+IndexUtils.getFileExtension(entity);
				if (isDuplicateResouce(container, fullPath)) {
					return true;
				}
			} else if (designerElement instanceof SharedRuleElement) {
				String fp = ((SharedRuleElement) designerElement).getFolder() + ((SharedRuleElement) designerElement).getFileName();
				if (isDuplicateResouce((EObject) designerElement, fp)) {
					return true;
				}
			}
			if (designerElement instanceof ElementContainer) {
				if (containsDuplicateResources((ElementContainer) designerElement)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isDuplicateResouce(EObject element, String fullPath) {
		try {
			EObject rootContainer = IndexUtils.getRootContainer(element);
			if (rootContainer instanceof DesignerProject) {
				String projName = ((DesignerProject) rootContainer).getName();
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
				IFile file = project.getFile(fullPath);
				if (file.exists()) {
					return true;
				}
			}
		} catch (IllegalArgumentException e) {
			NavigatorPlugin.debug(e.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public StyledString getStyledText(Object element) {
		return new StyledString(getText(element));
	}

	@Override
	public String getDescription(Object anElement) {
		return getLabel(anElement);
	}
	
	private String getLabel(Object anElement){
		if (anElement instanceof SharedElementRootNode) {
			return "Project Dependencies";
		}
		if (anElement instanceof DesignerProject) {
			String archivePath = ((DesignerProject) anElement).getArchivePath();
			if (archivePath != null && archivePath.endsWith(".projlib")) {
				File archiveFile = new File(archivePath);
				if (!archiveFile.exists()) {
					return ((DesignerProject)anElement).getName() + " -- Project Library (does not exist)";
				}
			}

			return ((DesignerProject)anElement).getName() + " -- Project Library";
		}
		String label = "";
		
		if (anElement instanceof SharedElement) {
			if (label.length() == 0) {
//				String entryPath = ((SharedElement)anElement).getEntryPath();
				label += ((SharedElement)anElement).getFileName();
			}
		} else if (anElement instanceof DesignerElement) {
			if (label.length() == 0) {
				label += ((DesignerElement)anElement).getName();
			}
		}
		
		if (anElement instanceof ElementContainer) {
			label += " (" + ((ElementContainer) anElement).getEntries().size() + " entries)";
		}
		
		if (anElement instanceof Entity) {
			return ((Entity) anElement).getName();
		}
		
		if (anElement instanceof StudioNavigatorNode) {
			if (anElement instanceof PropertyNode) {
				PropertyNode propNode = (PropertyNode) anElement;
				if(propNode.getType() == PROPERTY_TYPES.CONCEPT 
						|| propNode.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE){
					PropertyDefinition propertyDefinition = (PropertyDefinition)propNode.getEntity();
					return ((PropertyNode)anElement).getName()+ " ["+propertyDefinition.getConceptTypePath()+"]";
				}
			}
			return ((StudioNavigatorNode) anElement).getName();
		}
		
		if (anElement instanceof StateMachineAssociationNode) {
			return ((StateMachineAssociationNode) anElement).getStateMachine();
		}
		if (anElement instanceof DomainInstanceNode) {
			return ((DomainInstanceNode)anElement).getPath();
		}
		return label;
	}
}
