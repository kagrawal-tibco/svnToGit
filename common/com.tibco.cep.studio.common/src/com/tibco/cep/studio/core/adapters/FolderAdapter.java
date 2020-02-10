package com.tibco.cep.studio.core.adapters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.Validatable;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.RulesetEntry;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * 
 * This class implements both {@link Folder}, and {@link RuleSet}
 * interfaces because since 4.0 we are representing rulesets
 * as folders. 
 * <p>
 * A ruleset now is nothing but a special folder, hence this class
 * can be used for the same
 * </p>
 *
 */
public class FolderAdapter implements Folder, RuleSet {

	protected Ontology emfOntology;
	
	//private com.tibco.cep.studio.core.index.model.Folder fAdapted;
	
	protected ElementContainer fContainer;
	
	protected DesignerProject designerProject;
	
	public FolderAdapter(Ontology emfOntology, ElementContainer container) {
		this.emfOntology = emfOntology;
		this.fContainer = container;
		if (fContainer instanceof DesignerProject) {
			designerProject = (DesignerProject)fContainer;
		}
	}

	public List<Entity> getEntities(boolean flag) {
		EList<DesignerElement> entries = fContainer.getEntries();
		List<Entity> children = new ArrayList<Entity>();
		try {
            for (DesignerElement element : entries) {
                if (element instanceof EntityElement) {
                    Entity adaptedEntity =
                            CoreAdapterFactory.INSTANCE.createAdapter(((EntityElement) element).getEntity(), emfOntology);
                    if (null != adaptedEntity) {
                        children.add(adaptedEntity);
                    }
                } else switch (element.getElementType()) {
                    case FOLDER: {
                        com.tibco.cep.studio.core.index.model.Folder folderElement =
                                (com.tibco.cep.studio.core.index.model.Folder) element;
                        children.add(new FolderAdapter(emfOntology, folderElement));
                    }
                    break;
                    case RULE: {
                        Rule adaptedEntity =
                                CoreAdapterFactory.INSTANCE.createAdapter(((RuleElement) element).getRule(), emfOntology);
                        children.add(adaptedEntity);
                    }
                    ;
                    break;
                    case RULE_FUNCTION: {
                        RuleFunction adaptedEntity =
                                CoreAdapterFactory.INSTANCE.createAdapter(((RuleElement) element).getRule(), emfOntology);
                        children.add(adaptedEntity);
                    }
                    ;
                    break;
                    default: {
                        int i = 0;
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return children;
	}
	
//	private List<Entity> getAllChildren(com.tibco.cep.studio.core.index.model.Folder root) {
//		Stack<FolderVisitor> stack = new Stack<FolderVisitor>();
//		//The final state of the stack will contain all children
//		List<Entity> allChildren = new ArrayList<Entity>();
//		//Add root to it
//		FolderVisitor rootVisitor = new FolderVisitor(root);
//		stack.push(rootVisitor);
//		
//		while (!(stack.isEmpty())) {
//            //Get the topmost element
//			FolderVisitor top = stack.peek();
//			//Check if it is visited
//            if (!(top.isVisited())) {
//                //Set its visited flag to true
//                top.setVisited(true);
//                //Check if it has children
//                if (top.hasUnvisitedChildren()) {
//                    Iterator<FolderVisitor> children = top.getUnvisitedChildren();
//                    //Push each child onto the stack
//                    if (children != null) {
//                        while (children.hasNext()) {
//                        	FolderVisitor child = children.next();
//                            if (!child.isVisited()) {
//                                //Add it to the stack
//                                stack.push(child);
//                            }
//                        }
//                    }
//                } else {
//                    top.setVisited(true);
//                }
//            }
//		}
//	}
//	
//		
//	private class FolderVisitor {
//		
//		private com.tibco.cep.studio.core.index.model.Folder wrapped;
//		
//		private List<FolderVisitor> wrappedChildren = new ArrayList<FolderVisitor>();
//		
//		private boolean visited;
//
//		/**
//		 * @param wrapped
//		 */
//		public FolderVisitor(com.tibco.cep.studio.core.index.model.Folder wrapped) {
//			this.wrapped = wrapped;
//			List<DesignerElement> entries = wrapped.getEntries();
//			for (DesignerElement element : entries) {
//				if (element instanceof com.tibco.cep.studio.core.index.model.Folder) {
//					wrappedChildren.add(new FolderVisitor((com.tibco.cep.studio.core.index.model.Folder)element));
//				}
//			}
//		}
//		
//		public boolean isVisited() {
//			return visited;
//		}
//		
//		public void setVisited(final boolean visited) {
//			this.visited = visited;
//		}
//		
//		Iterator<FolderVisitor> getUnvisitedChildren() {
//			Iterator<FolderVisitor> iterator;
//			List<FolderVisitor> unvisitedChildren = new ArrayList<FolderVisitor>();
//			if (!wrappedChildren.isEmpty()) {
//				for (FolderVisitor wrapper : wrappedChildren) {
//					if (!wrapper.isVisited()) {
//						unvisitedChildren.add(wrapper);
//					}
//				}
//				iterator = unvisitedChildren.iterator();
//			} else {
//				//Do not return null
//				iterator = wrappedChildren.iterator();
//			}
//			return iterator;
//		}
//		
//		public boolean hasUnvisitedChildren() {
//			if (!(wrapped instanceof com.tibco.cep.studio.core.index.model.Folder)) {
//				return false;
//			}
//			for (FolderVisitor visitor : wrappedChildren) {
//				//If a single child is also unvisited return true
//				if (!visitor.isVisited()) {
//					return true;
//				}
//			}
//			return false;
//		}
//	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.RuleSet#getRule(java.lang.String)
	 */
	
	public RulesetEntry getRule(String ruleName) {
		EList<DesignerElement> entries = fContainer.getEntries();
		
		try {
			for (DesignerElement element : entries) {
				if (element instanceof RuleElement) {
					if (element.getName().equals(ruleName)) {
						Rule adaptedEntity = 
							CoreAdapterFactory.INSTANCE.createAdapter(((RuleElement) element).getRule(), emfOntology);
						return adaptedEntity;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.RuleSet#getRules()
	 */
	
	public List<Rule> getRules() {
		List<Rule> children = new ArrayList<Rule>();
		EList<DesignerElement> entries = fContainer.getEntries();
		
		try {
			for (DesignerElement element : entries) {
				if (element instanceof RuleElement) {
					Rule adaptedEntity;
					adaptedEntity = 
						CoreAdapterFactory.INSTANCE.createAdapter(((RuleElement) element).getRule(), emfOntology);
					children.add(adaptedEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return children;
	}

	public com.tibco.cep.designtime.model.Entity getEntity(String s,
			boolean flag) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<?> getFolderList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getParent() {
		return getFolder();
	}

	public Folder getSubFolder(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Folder> getSubFolders() {
        final List<Folder> children = new ArrayList<Folder>();
        try {
            final String ontologyName = this.emfOntology.getName();
            String prefix = this.getFullPath();
            if (!prefix.endsWith("/")) {
                prefix += "/";
            }
            for (DesignerElement element : this.fContainer.getEntries()) {
                if (element.getElementType() == ELEMENT_TYPES.FOLDER) {
                    final ElementContainer folder = CommonIndexUtils.getFolderForFile(ontologyName,
                            prefix + element.getName());
		            final FolderAdapter adapter = new FolderAdapter(this.emfOntology, folder);
                    children.add(adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
	}

	public boolean hasChild(Folder folder) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasDescendant(Folder folder) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasPredecessor(Folder folder) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasSibling(Folder folder) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBindingString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<?, ?> getExtendedProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public Folder getFolder() {
		if (fContainer != null && fContainer.eContainer() instanceof ElementContainer) {
			return new FolderAdapter(emfOntology, (ElementContainer) fContainer.eContainer());
		}
		return null;
	}

	public String getFolderPath() {
		Folder parentFolder = getFolder();
		if (parentFolder != null) {
			return parentFolder.getFullPath();
		}
		return "/";
	}
	
	/**
	 * Aditya -> Added the logic below from codegen perspective
	 */
	public String getFullPath() {
		String containerName = "";
		if (fContainer != null) {
			containerName = fContainer.getName();
		}
		String ontName = (designerProject != null) ? designerProject.getName() : null;
		
		StringBuilder sBuilder = new StringBuilder(getFolderPath());
		if (!containerName.equals(ontName)) {
			sBuilder.append(containerName);
			sBuilder.append("/");
		}
		return sBuilder.toString();
	}

	public String getGUID() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<?, ?> getHiddenProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHiddenProperty(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIconPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return fContainer.getName();
	}

	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	public Ontology getOntology() {
		return emfOntology;
	}

	public Map<?, ?> getTransientProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTransientProperty(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public void serialize(OutputStream outputstream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public Validatable[] getInvalidObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> getModelErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	public void makeValid(boolean flag) {
		// TODO Auto-generated method stub
		
	}

}
