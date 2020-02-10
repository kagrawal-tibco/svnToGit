package com.tibco.cep.studio.ui.search;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.providers.GeneralIndexLabelProvider;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class StudioElementSearchResultPage extends AbstractTextSearchViewPage {

	private HashMap<EObject, SharedElementMatch> eobjToAdapterMap = new HashMap<EObject, SharedElementMatch>();
	
	private class SharedElementMatch implements IWorkbenchAdapter {

		private EObject wrappedObj;

		public SharedElementMatch(EObject wrappedObj) {
			this.wrappedObj = wrappedObj;
		}

		@Override
		public Object[] getChildren(Object o) {
			return null;
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object element) {
			element = wrappedObj;
	        if (element instanceof DesignerProject) {
	        	return StudioUIPlugin.getImageDescriptor("icons/archive_obj.gif");
			} else if (element instanceof EntityElement) {
				element = ((EntityElement)element).getEntity();
			}
	        // designer elements
			if (element instanceof RuleElement) {
				return StudioUIPlugin.getImageDescriptor("icons/rules_folder.png");
			}
			if (element instanceof ElementContainer) {
				return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
			}
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}

		@Override
		public String getLabel(Object anElement) {
			anElement = wrappedObj;
			if (anElement instanceof SharedElementRootNode) {
				return "Project Dependencies";
			}
			if (anElement instanceof DesignerProject) {
				String archivePath = ((DesignerProject) anElement).getName();
				if (archivePath != null && archivePath.endsWith(".projlib")) {
					File archiveFile = new File(archivePath);
					if (!archiveFile.exists()) {
						return ((DesignerProject)anElement).getName() + " -- Project Library (does not exist)";
					}
				}

				return ((DesignerProject)anElement).getName() + " -- Project Library";
			}
			String label = "";
			
			if (anElement instanceof Entity) {
				return ((Entity) anElement).getName();
			}

			if (anElement instanceof SharedElement) {
				if (label.length() == 0) {
					String entryPath = ((SharedElement)anElement).getEntryPath();
					label += ((SharedElement)anElement).getFileName();
				}
			} else if (anElement instanceof DesignerElement) {
				if (label.length() == 0) {
					label += ((DesignerElement)anElement).getName();
				}
			}
			
			return label;
		}

		@Override
		public Object getParent(Object o) {
			EObject container = wrappedObj.eContainer();
			if (container == null) {
				return null;
			}
			if (container instanceof DesignerProject && container.eContainer() == null) {
				return ResourcesPlugin.getWorkspace().getRoot().getProject(((DesignerProject) container).getName());
			}
			if (eobjToAdapterMap.containsKey(container)) {
				return eobjToAdapterMap.get(container);
			}
			SharedElementMatch parent = new SharedElementMatch(container);
			eobjToAdapterMap.put(container, parent);
			return parent;
		}
		
	}
	
	private class SearchContentProvider implements ITreeContentProvider {

		private Map<Object, Set<Object>> fChildrenMap;
		
		public Object[] getChildren(Object parentElement) {
			if (fChildrenMap == null) {
				return EMPTY_CHILDREN;
			}
			Set<Object> children= fChildrenMap.get(parentElement);
			if (children == null) {
				return getMatchesForElement(parentElement);
			}
			return children.toArray();
		}

		public Object getParent(Object element) {
			if (element instanceof IProject) {
				return null;
			}
			if (element instanceof SharedElementMatch) {
				return ((SharedElementMatch) element).getParent(element);
			}
			if (element instanceof IResource) {
				return ((IResource)element).getParent();
			}
			if (element instanceof SharedElement) {
				EObject container = ((SharedElement) element).eContainer();
				if (eobjToAdapterMap.containsKey(container)) {
					return eobjToAdapterMap.get(container);
				}
				SharedElementMatch parent = new SharedElementMatch(container);
				eobjToAdapterMap.put(container, parent);
				return parent;
			}
			if (element instanceof DesignerProject) {
				if (((DesignerProject) element).eContainer() instanceof DesignerProject) {
					DesignerProject parentProj = (DesignerProject) ((DesignerProject) element).eContainer();
					return ResourcesPlugin.getWorkspace().getRoot().getProject(parentProj.getName());
				}
				return null;
			}
			if (element instanceof ElementContainer) {
				ElementContainer container = (ElementContainer) element;
				if (eobjToAdapterMap.containsKey(container)) {
					return eobjToAdapterMap.get(container);
				}
				SharedElementMatch parent = new SharedElementMatch(container);
				eobjToAdapterMap.put(container, parent);
				return parent;
			}
			if (element instanceof EObject) {
				return ((EObject)element).eContainer();
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			initialize();
		}

		private void initialize() {
			fChildrenMap = new HashMap<Object, Set<Object>>();
			Object[] elements = getInput().getElements();
			for (Object object : elements) {
				insert(object, false);
			}
		}

		private void insert(Object child, boolean refresh) {
			TreeViewer viewer = (TreeViewer) getViewer();
			Object parent = getParent(child);
			while (parent != null) {
				if (insertChild(parent, child)) {
					refresh(refresh, viewer);
				} else {
					refresh(refresh, viewer);
					return;
				}
				child = parent;
				parent = getParent(child);
			}
			if (insertChild(getInput(), child)) {
				refresh(refresh, viewer);
			}
		}

		private boolean insertChild(Object parent, Object child) {
			Set<Object> children = fChildrenMap.get(parent);
			if (children == null) {
				children = new HashSet<Object>();
				fChildrenMap.put(parent, children);
			}
			if (child != null) {
				return children.add(child);
			}
			return false;
		}

		protected void remove(Object element, boolean refresh) {
			TreeViewer viewer = (TreeViewer) getViewer();

			if (hasChildren(element)) {
				refresh(refresh, viewer);
			} else {
				if (getInput().getMatchCount(element) == 0) {
					fChildrenMap.remove(element);
					Object parent = getParent(element);
					if (parent != null) {
						removeChild(element, parent);
						remove(parent, refresh);
					} else {
						removeChild(element, getInput());
						refresh(refresh, viewer);
					}
				} else {
					refresh(refresh, viewer);
				}
			}
		}

		private void refresh(boolean refresh, TreeViewer viewer) {
			if (refresh) {
				viewer.refresh();
			}
		}

		private void removeChild(Object element, Object parent) {
			Set<Object> children = fChildrenMap.get(parent);
			if (children != null) {
				children.remove(element);
			}
		}

		public synchronized void elementsChanged(Object[] objects) {
			for (Object object : objects) {
				if (getInput().getMatchCount(object) > 0) {
					insert(object, true);
				} else {
					remove(object, true);
				}
			}
			getViewer().update(fContentProvider.getElements(getInput()), new String[] { LABEL_PROPERTY });
		}

		public void clear() {
			initialize();
			getViewer().refresh();
		}
		
	}
	
	private class SearchLabelProvider extends LabelProvider implements ILabelDecorator, IColorProvider, IFontProvider {

		private WorkbenchLabelProvider provider = new WorkbenchLabelProvider();
		private GeneralIndexLabelProvider elementProvider = new GeneralIndexLabelProvider();
		
		@Override
		public Image getImage(Object element) {
			if (element instanceof DesignerElement) {
				return elementProvider.getImage(element);
			}
			return provider.getImage(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof SharedElement) {
				return ((SharedElement) element).getName();
			}
			if (element instanceof SharedElementMatch) {
				return ((SharedElementMatch) element).getLabel(element);
			}
			return provider.getText(element);
		}

		@Override
		public Font getFont(Object element) {
			return provider.getFont(element);
		}

		@Override
		public Color getBackground(Object element) {
			return provider.getBackground(element);
		}

		@Override
		public Color getForeground(Object element) {
			if (element instanceof NestedStudioElementMatch) {
				NestedStudioElementMatch match = (NestedStudioElementMatch) element;
				if (!match.isExact()) {
					return ColorConstants.gray;
				}
				if (!(match.getMatchedElement() instanceof ElementReference)) {
					// this is a definition
					return ColorConstants.blue;
				}
			}
			return provider.getForeground(element);
		}

		public Image decorateImage(Image image, Object element) {
			return StudioUIPlugin.getDefault().getImage("icons/cube.gif");
		}
		
        public String decorateText(String text, Object element) {
        	String decoratedText = text;
        	if (element instanceof IFile) {
            	decoratedText += " (" + getMatchesForElement(element).length + " matches found)";
            }
        	if (element instanceof IContainer) {
        		decoratedText += " (" + getMatchesForElementRecursive(element).length + " matches found)";
        	}
        	if (element instanceof SharedElementMatch) {
        		decoratedText += " [Project library resource]";
        	}
            return decoratedText;
        }

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return LABEL_PROPERTY.equals(property);
		}

	}
	
    private static Object[] EMPTY_CHILDREN = new Object[0];
    public static final String LABEL_PROPERTY = "label_property";

	private SearchContentProvider fContentProvider;
	
    public Object[] getMatchesForElement(Object parentElement) {
		List<Object> allMatches = new ArrayList<Object>();
		
    	Match[] matches = getInput().getMatches(parentElement);
    	if (matches != null) {
    		for (Match match : matches) {
    			allMatches.add(((IStudioMatch)match).getNestedMatch());
    		}
    		return allMatches.toArray();
    	}

    	return EMPTY_CHILDREN;
	}

    public Object[] getMatchesForElementRecursive(Object parentElement) {
    	List<Object> allMatches = new ArrayList<Object>();
    	
    	Match[] matches = getInput().getMatches(parentElement);
    	if (matches != null) {
    		for (Match match : matches) {
    			allMatches.add(((IStudioMatch)match).getNestedMatch());
    		}
    	}
    	if (parentElement instanceof IContainer) {
    		try {
				IResource[] members = ((IContainer) parentElement).members();
				for (IResource resource : members) {
					Object[] objects = getMatchesForElementRecursive(resource);
					for (Object object : objects) {
						allMatches.add(object);
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
    	}
		return allMatches.toArray();
//    	return EMPTY_CHILDREN;
    }
    
	@Override
	protected void clear() {
		fContentProvider.clear();
	}

	@Override
	protected void configureTableViewer(TableViewer viewer) {
		fContentProvider = new SearchContentProvider();
		viewer.setContentProvider(fContentProvider);
		viewer.setLabelProvider(new SearchLabelProvider());
	}

	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		fContentProvider = new SearchContentProvider();
		viewer.setContentProvider(fContentProvider);
		viewer.setLabelProvider(new SearchLabelProvider());
	}

	@Override
	protected void elementsChanged(Object[] objects) {
		if (objects.length > 0) {
			fContentProvider.elementsChanged(objects);
		}
	}

	@Override
	protected void handleOpen(OpenEvent event) {
        ISelection sel= event.getSelection();
        if (getViewer() instanceof TreeViewer && sel instanceof IStructuredSelection) {
            IStructuredSelection selection= (IStructuredSelection) sel;
            Object element = selection.getFirstElement();
            Object elementToOpen = element;
            if (element instanceof Match) {
            	Match entry = (Match) element;
            	elementToOpen = entry.getElement();
            } else if (element instanceof NestedStudioElementMatch) {
            	NestedStudioElementMatch entry = (NestedStudioElementMatch) element;
            	elementToOpen = entry.getMatchedElement();
            }
            StudioUIUtils.openElement(elementToOpen);
        }
	}



}
