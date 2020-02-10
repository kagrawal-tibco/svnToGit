package com.tibco.cep.studio.ui.views;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.MemberElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioElementDelta;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class IndexView extends ViewPart implements IStudioModelChangedListener {
	private TreeViewer fViewer;
	private TreeViewer fReferencesViewer;
	private DrillDownAdapter drillDownAdapter;
	private Action fToggleGroupLikeElementsAction;
	private Action fRefreshAction;
	private Action fDoubleClickAction;
	private Action fOpenReferenceAction;
	private Action fOpenDeclarationAction;
	private boolean fGroupLikeElements = true;
	private HashMap<String, Image> fImageCache = new HashMap<String, Image>();
	
	class TreeNode {
		protected String fLabel;
		protected Object fData;
		
		public TreeNode(String label) {
			this(label, null);
		}

		public TreeNode(String label, Object data) {
			this.fLabel = label;
			this.fData = data;
		}

		public String getLabel() {
			return fLabel;
		}

		public Object getData() {
			return fData;
		}

	}
	
	class TreeParent extends TreeNode {
		
		List<Object> fChildren;

		public TreeParent(String label, List children) {
			super(label);
			this.fChildren = children;
		}

		public List<Object> getChildren() {
			return fChildren;
		}

		public void setChildren(List<Object> children) {
			fChildren = children;
		}

	}
	
	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		
		public void dispose() {
		}
		
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				return StudioCorePlugin.getDesignerModelManager().getAllIndexes().toArray();
			}
			return getChildren(parent);
		}
		
		public Object getParent(Object child) {
			return null;
		}
		
		public Object [] getChildren(Object parent) {
			if (parent instanceof DesignerProject) {
				DesignerProject index = (DesignerProject) parent;
				List<Object> children = new ArrayList<Object>();
				children.add(new TreeParent("Entities", getEntityNodeChildren(index.getEntityElements())));
				children.add(new TreeParent("Archives", index.getArchiveElements()));
				children.add(new TreeParent("Rules", getRuleNodeChildren(index.getRuleElements())));
				children.add(new TreeParent("Implementations", index.getDecisionTableElements()));
				children.add(new TreeParent("Entries", index.getEntries()));
				//children.add(new TreeParent("Instances", index.getInstanceElements()));
				children.add(new TreeParent("Instances", getInstanceElements(index)));
				children.add(new TreeParent("Referenced Projects", index.getReferencedProjects()));
				return children.toArray();
			} else if (parent instanceof ElementContainer) {
				return ((ElementContainer) parent).getEntries().toArray();
			} else if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren().toArray();
			} 
			if (parent instanceof EntityElement) {
				return getEntityElementChildren((EntityElement)parent);
			}
			if (parent instanceof ArchiveElement) {
				return getArchiveElementChildren((ArchiveElement) parent);
			}
			if (parent instanceof DecisionTableElement) {
				return getDecisionTableElementChildren((DecisionTableElement) parent);
			}
			if (parent instanceof RuleElement) {
				return getRuleElementChildren((RuleElement) parent);
			}
			if (parent instanceof Rule) {
				return getRuleChildren((Rule)parent);
			}
			if (parent instanceof RuleFunction) {
				return getRuleFunctionChildren((RuleFunction)parent);
			}
			if (parent instanceof CompilableScopeEntry) {
				return getCompilableScopeChildren((CompilableScopeEntry)parent);
			}
			if (parent instanceof ScopeBlock) {
				return getScopeChildren((ScopeBlock)parent);
			}
			if (parent instanceof ElementReference) {
				return getElementReferenceChildren((ElementReference)parent);
			}
			if (parent instanceof InstanceElement) {
				return new Object[] { new TreeParent("Instances", ((InstanceElement) parent).getInstances()) };
			}
			if (parent instanceof List) {
				return ((List)parent).toArray();
			}
			if (parent instanceof TreeNode) {
				return getChildren(((TreeNode) parent).getData());
			}
			return new Object[0];
		}
		
		private Object[] getElementReferenceChildren(ElementReference parent) {
			List<TreeNode> children = new ArrayList<TreeNode>();
			if (parent.getQualifier() != null) {
				Object[] qChildren = getElementReferenceChildren(parent.getQualifier());
				List<Object> qChildrenList = new ArrayList<Object>();
				for (Object object : qChildren) {
					qChildrenList.add(object);
				}
				children.add(new TreeParent("Qualifier", qChildrenList));
			}
			children.add(new TreeNode("Name: "+parent.getName(), parent));
			return children.toArray();
		}

		private Object[] getCompilableScopeChildren(CompilableScopeEntry parent) {
			TreeNode ruleName = new TreeNode("Rule name: "+parent.getRuleName());
			if (parent.getScope() != null) {
				TreeParent vars = new TreeParent("Global vars:", parent.getScope().getGlobalVariables());
				TreeNode actionScope = new TreeNode("Action scope:", parent.getScope().getActionScope());
				TreeNode condScope = new TreeNode("Condition scope:", parent.getScope().getConditionScope());
				return new Object[] { ruleName, vars, actionScope, condScope };
			}
			return new Object[] { ruleName };
		}
		
		private Object[] getScopeChildren(ScopeBlock parent) {
			List<TreeNode> children = new ArrayList<TreeNode>();
			if (parent instanceof RootScopeBlock) {
				ElementReference definitionRef = ((RootScopeBlock) parent).getDefinitionRef();
				children.add(new TreeNode("root element ref", definitionRef));
			}
			TreeParent defs = new TreeParent("Definitions", parent.getDefs());
			children.add(defs);
			TreeParent refs = new TreeParent("References", parent.getRefs());
			children.add(refs);
			TreeParent childScopes = new TreeParent("Child scopes", parent.getChildScopeDefs());
			children.add(childScopes);
			return children.toArray();
		}

		private Object[] getRuleChildren(Rule parent) {
			List<TreeNode> children = new ArrayList<TreeNode>();
//			Collection<Symbol> symbols = parent.getSymbols().getSymbolMap().values();
			for (Symbol symbol : parent.getSymbols().getSymbolList()) {
				children.add(new TreeNode("name "+symbol.getIdName()+" type "+symbol.getType(), symbol));
			}
			TreeParent treeParent = new TreeParent("Rule Symbols", children);
			return new Object[] { treeParent };
		}

		private Object[] getRuleFunctionChildren(RuleFunction parent) {
			List<TreeNode> children = new ArrayList<TreeNode>();
//			Collection<Symbol> symbols = parent.getSymbols().getSymbolMap().values();
			for (Symbol symbol : parent.getSymbols().getSymbolList()) {
				children.add(new TreeNode("name "+symbol.getIdName()+" type "+symbol.getType(), symbol));
			}
			TreeParent treeParent = new TreeParent("Rule Function Symbols", children);
			return new Object[] { treeParent };
		}
		
		private List<?> getRuleNodeChildren(EList<RuleElement> ruleElements) {
			if (fGroupLikeElements) {
				List<TreeParent> parents = new ArrayList<TreeParent>();
				List<RuleElement> rules = new ArrayList<RuleElement>();
				List<RuleElement> ruleFunctions = new ArrayList<RuleElement>();
				for (RuleElement ruleElement : ruleElements) {
					if (ruleElement.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
						ruleFunctions.add(ruleElement);
					} else {
						rules.add(ruleElement);
					}
				}
				TreeParent ruleParent = new TreeParent("Rules", rules);
				TreeParent ruleFunctionParent = new TreeParent("Rule Functions", ruleFunctions);
				parents.add(ruleParent);
				parents.add(ruleFunctionParent);
				return parents;
			}
			return ruleElements;
		}

		private List<?> getEntityNodeChildren(EList<EntityElement> entityElements) {
			if (fGroupLikeElements) {
				List<TreeParent> parents = new ArrayList<TreeParent>();
				ELEMENT_TYPES[] values = ELEMENT_TYPES.values();
				// TODO : refactor to make more efficient
				for (ELEMENT_TYPES entityType : values) {
					if (entityType == ELEMENT_TYPES.ADAPTER_ARCHIVE || entityType == ELEMENT_TYPES.BE_ARCHIVE_RESOURCE
							|| entityType == ELEMENT_TYPES.ENTERPRISE_ARCHIVE || entityType == ELEMENT_TYPES.PRIMITIVE_TYPE
							|| entityType == ELEMENT_TYPES.RULE || entityType == ELEMENT_TYPES.RULE_FUNCTION
							|| entityType == ELEMENT_TYPES.PROCESS_ARCHIVE || entityType == ELEMENT_TYPES.SHARED_ARCHIVE) {
						continue;
					}
					List<EntityElement> children = new ArrayList<EntityElement>();
					for (EntityElement entityElement : entityElements) {
						if (entityElement.getElementType() == entityType) {
							children.add(entityElement);
						}
					}
					parents.add(new TreeParent(entityType.getLiteral()+"s", children));
				}
				return parents;
			}
			return entityElements;
		}
		
		private List<?> getInstanceElements(DesignerProject index) {
			if (fGroupLikeElements) {
				List<TreeParent> parents = new ArrayList<TreeParent>();
				EList<InstanceElement<?>> elements = index.getDomainInstanceElements();
//				List<InstanceElement<?>> domainInstances = new ArrayList<InstanceElement<?>>();
//				for (InstanceEntry instanceEntry : domainInstanceEntries) {
//					domainInstances.addAll(instanceEntry.getInstances());
//				}
//				List<InstanceElement<DomainInstance>> domainInstances = 
//							index.getDomainInstanceElements();
//				List<InstanceElement<SMInstance>> smInstances = 
//							index.getStateMachineInstanceElements();
				
				parents.add(new TreeParent("Domains", elements));
//				parents.add(new TreeParent("State Machines", smInstances));
				return parents;
			}
			return new ArrayList<Object>(0);
		}

		private Object[] getEntityElementChildren(EntityElement parent) {
			List<Object> children = new ArrayList<Object>();
			children.add(parent.getEntity());
			if (parent instanceof StateMachineElement) {
				children.add(new TreeParent("scopes", ((StateMachineElement) parent).getCompilableScopes()));
			}
			if (parent instanceof EventElement) {
				children.add(new TreeNode("scopes", ((EventElement) parent).getExpiryActionScopeEntry()));
			}
			return children.toArray();
		}

		private Object[] getRuleElementChildren(RuleElement parent) {
			List<Object> children = new ArrayList<Object>();
			children.add(parent.getRule());
			if (parent.isVirtual()) {
				children.add(new TreeNode("virtual"));
			}
			children.add(new TreeParent("global vars", parent.getGlobalVariables()));
			children.add(parent.getScope());
			return children.toArray();
		}
		
		private Object[] getArchiveElementChildren(ArchiveElement parent) {
			List<Object> children = new ArrayList<Object>();
			children.add(parent.getArchive());
			return children.toArray();
		}
		
		private Object[] getDecisionTableElementChildren(DecisionTableElement parent) {
			List<Object> children = new ArrayList<Object>();
			children.add(parent.getImplementation());
			return children.toArray();
		}
		
		public boolean hasChildren(Object parent) {
			if (parent instanceof DesignerProject) {
				return true;
			} else if (parent instanceof TreeParent) {
				return true;
			} else if (parent instanceof DesignerElement) {
				return true;
			} else if (parent instanceof Compilable) {
				return true;
			} else if (parent instanceof ScopeBlock) {
				return true;
			} else if (parent instanceof CompilableScopeEntry) {
				return true;
			} else if (parent instanceof CompilableScope) {
				return true;
			} else if (parent instanceof List) {
				return true;
			} else if (parent instanceof ElementReference) {
				return true;
			} else if (parent instanceof TreeNode) {
				return hasChildren(((TreeNode)parent).getData());
			}
			return false;
		}
		
	}
	
	class ViewLabelProvider extends LabelProvider {
		@SuppressWarnings("unchecked")
		public String getText(Object obj) {
			if (obj instanceof DesignerProject) {
				return ((DesignerProject)obj).getName() + " -- Ontology Index";
			}
			if (obj instanceof Entity) {
				return ((Entity)obj).getName() + "::" + obj.getClass().getSimpleName();
			}
			if (obj instanceof ArchiveResource) {
				return ((ArchiveResource)obj).getName() + "::" + obj.getClass().getSimpleName();
			}
			if (obj instanceof Implementation) {
				return ((Implementation)obj).getName() + "::" + obj.getClass().getSimpleName();
			}
			if (obj instanceof ScopeBlock) {
				return "Scope " + ((ScopeBlock)obj).getType();
			}
			if (obj instanceof CompilableScopeEntry) {
				return "Compilable Scope Entry for " + ((CompilableScopeEntry)obj).getRuleName();
			}
			if (obj instanceof VariableDefinition) {
				VariableDefinition def = (VariableDefinition) obj;
				String text = "Definition {Type: "+def.getType() + " Name: " + def.getName() + "[" + def.getOffset() + "," + def.getLength() + "]}";
				DesignerElement parentElement = IndexUtils.getVariableContext((EObject) obj);
				text += " from " + parentElement.getName();
				return text;
			}
			if (obj instanceof ElementReference) {
				ElementReference def = (ElementReference) obj;
				String text = "Reference {Name: " + def.getName() + "[" + def.getOffset() + "," + def.getLength() + "]}";
				DesignerElement parentElement = IndexUtils.getVariableContext((EObject) obj);
				text += " from " + parentElement.getName();
				return text;
			}
			if (obj instanceof TreeParent) {
				int childCount = 0;
				List<Object> children = ((TreeParent)obj).getChildren();
				for (Object child : children) {
					if (child instanceof TreeParent) {
						childCount += ((TreeParent)child).getChildren().size();
					} else {
						childCount++;
					}
				}
				return ((TreeParent)obj).getLabel() + " (size: " + childCount + ")";
			}
			if (obj instanceof TreeNode) {
				return ((TreeNode)obj).getLabel();
			}
			String label = "";
			
			if (obj instanceof InstanceElement) {
				InstanceElement<BaseInstance> instanceElement = 
					(InstanceElement<BaseInstance>)obj;
				EList<BaseInstance> instances = instanceElement.getInstances();
				StringBuilder stringBuilder = new StringBuilder(label);

				stringBuilder.append("(Entity -> ");
				stringBuilder.append(instanceElement.getEntityPath());
				stringBuilder.append(") ");
				stringBuilder.append(" has ");
				stringBuilder.append(instances.size());
				stringBuilder.append(" instance(s) ");
				label = stringBuilder.toString();
//				if (instance instanceof SMInstance) {
//					SMInstance stateMachineInstance = (SMInstance)instance;
//					EObject eobject = stateMachineInstance.eContainer();
//					if (eobject instanceof Concept) {
//						Concept concept = (Concept)eobject;
//						StringBuilder stringBuilder = new StringBuilder(label);
//						stringBuilder.append(" -- (State Machine path: ");
//						stringBuilder.append(stateMachineInstance.getResourcePath());
//						stringBuilder.append(") ");
//						stringBuilder.append("(Concept -> ");
//						stringBuilder.append(concept.getFullPath());
//						stringBuilder.append(") ");
//						label = stringBuilder.toString();
//					}
//				}
			}

			if (obj instanceof DomainInstance) {
				StringBuilder stringBuilder = new StringBuilder(label);
				DomainInstance domainInstance = (DomainInstance) obj;
				EObject eobject = domainInstance.getOwnerProperty();
				if (eobject instanceof PropertyDefinition) {
					PropertyDefinition propertyDefinition = (PropertyDefinition)eobject;
					EObject ownerEntity = propertyDefinition.eContainer();
					Entity entity = (Entity)ownerEntity;
					stringBuilder.append(" -- (Domain path: ");
					stringBuilder.append(domainInstance.getResourcePath());
					stringBuilder.append(") ");
					stringBuilder.append("(Entity -> ");
					stringBuilder.append(entity.getFullPath());
					stringBuilder.append(") ");
					stringBuilder.append("(Property -> ");
					stringBuilder.append(propertyDefinition.getName());
					stringBuilder.append(")");
				}
				label = stringBuilder.toString();
			}
			
			if (obj instanceof DesignerElement) {
				if (label.length() == 0) {
					label += ((DesignerElement)obj).getName();
				}
			}
			if (obj instanceof EntityElement) {
				label += " -- (path: " + ((EntityElement)obj).getFolder() + ") -- (type " + ((EntityElement)obj).getElementType()+")";
			}
			if (obj instanceof ArchiveElement) {
				label += " -- (path: " + ((ArchiveElement)obj).getFolder()+")";
			}
			if (obj instanceof DecisionTableElement) {
				label += " -- (path: " + ((DecisionTableElement)obj).getFolder()+")";
			}
			if (obj instanceof RuleElement) {
				label += " -- (path: " + ((RuleElement)obj).getFolder() + ") "+((RuleElement)obj).getElementType();
			}
			
			if (obj instanceof ElementContainer) {
				label += " (" + ((ElementContainer) obj).getEntries().size() + " entries)";
			}
			return label;
		}
		
		@SuppressWarnings("unchecked")
		public Image getImage(Object element) {
			if (element instanceof SharedElement) {
				String name = ((SharedElement) element).getName();
				String fileExtension = name;
				if (name.lastIndexOf('.') > 0) {
					fileExtension = name.substring(name.lastIndexOf('.')+1);
				}
				if (fImageCache.get(fileExtension) != null) {
					return fImageCache.get(fileExtension);
				}
				IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
				IEditorDescriptor ed = registry.getDefaultEditor(name);
				if (ed != null) {
					fImageCache.put(fileExtension, ed.getImageDescriptor().createImage());
					return fImageCache.get(fileExtension);
				}

			}
			if (element instanceof DesignerElement) {
				ELEMENT_TYPES elementType = ((DesignerElement) element).getElementType();
				
				String fileExtension = IndexUtils.getFileExtension(elementType);
				if (fImageCache.get(fileExtension) != null) {
					return fImageCache.get(fileExtension);
				}
				IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
				IEditorDescriptor ed = registry.getDefaultEditor(((DesignerElement) element).getName()+"."+fileExtension);
				if (ed != null) {
					fImageCache.put(fileExtension, ed.getImageDescriptor().createImage());
					return fImageCache.get(fileExtension);
				}
			}

	        if (element instanceof DesignerProject) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
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
			if (element instanceof ArchiveElement) {
				return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
			}
			
			if (element instanceof InstanceElement) {
				return StudioUIPlugin.getDefault().getImage("icons/domain_association.png");
//				InstanceElement<BaseInstance> instanceElement = 
//						(InstanceElement<BaseInstance>)element;
//				BaseInstance baseInstance = instanceElement.getInstance();
//				if (baseInstance instanceof DomainInstance) {
//					return StudioUIPlugin.getDefault().getImage("icons/domain_association.png");
//				}
//				if (baseInstance instanceof SMInstance) {
//					return StudioUIPlugin.getDefault().getImage("icons/state_machine.png");
//				}
			}
			
			
			
			// archives
			if (element instanceof EnterpriseArchive) {
				return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
			} else if (element instanceof BusinessEventsArchiveResource) {
				return StudioUIPlugin.getDefault().getImage("icons/beArchive16x16.gif");
			} else if (element instanceof SharedArchive) {
				return StudioUIPlugin.getDefault().getImage("icons/sharedArchive16x16.gif");
			} else if (element instanceof ProcessArchive) {
				return StudioUIPlugin.getDefault().getImage("icons/processArchive16x16.gif");
			} else if (element instanceof IFile) {
				return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
			}
			
			// rules
			if (element instanceof RuleFunction) {
				return StudioUIPlugin.getDefault().getImage("icons/rule-function.png");
			} else if (element instanceof Rule) {
				return StudioUIPlugin.getDefault().getImage("icons/rules.png");
			} 
			
			// entities
			if (element instanceof Scorecard) {
				return StudioUIPlugin.getDefault().getImage("icons/scorecard.png");
			} else if (element instanceof Concept) {
				return StudioUIPlugin.getDefault().getImage("icons/concept.png");
			} else if (element instanceof Event) {
				return StudioUIPlugin.getDefault().getImage("icons/event.png");
			} else if (element instanceof Channel) {
				return StudioUIPlugin.getDefault().getImage("icons/channel.png");
			} else if (element instanceof Destination) {
				return StudioUIPlugin.getDefault().getImage("icons/destination.png");
			} else if (element instanceof MemberElement) {
				return StudioUIPlugin.getDefault().getImage("icons/StartState.png");
			}
			if (element instanceof TreeParent) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
	}
	
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public IndexView() {
		StudioCorePlugin.getDefault().addDesignerModelChangedListener(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		StudioCorePlugin.getDefault().removeDesignerModelChangedListener(this);
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		fViewer = new TreeViewer(sash, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.RESIZE);
		drillDownAdapter = new DrillDownAdapter(fViewer);
		fViewer.setContentProvider(new ViewContentProvider());
		fViewer.setLabelProvider(new ViewLabelProvider());
		fViewer.setSorter(new NameSorter());
		fViewer.setInput(getViewSite());
		fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		
			public void selectionChanged(SelectionChangedEvent event) {
				findReferences();
			}
		});
		
		fReferencesViewer = new TreeViewer(sash, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.RESIZE | SWT.BORDER);
		drillDownAdapter = new DrillDownAdapter(fReferencesViewer);
		fReferencesViewer.setContentProvider(new ViewContentProvider());
		fReferencesViewer.setLabelProvider(new ViewLabelProvider());
		fReferencesViewer.setInput(null); // so that it is immediately displayed
		
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				IndexView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(fViewer.getControl());
		fViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, fViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(fToggleGroupLikeElementsAction);
		manager.add(new Separator());
		manager.add(fRefreshAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(fToggleGroupLikeElementsAction);
		manager.add(fRefreshAction);
		manager.add(fOpenDeclarationAction);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(fToggleGroupLikeElementsAction);
		manager.add(fRefreshAction);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		fToggleGroupLikeElementsAction = new Action() {
			public void run() {
				fGroupLikeElements = !fGroupLikeElements;
				fViewer.refresh();
			}
		};
		fToggleGroupLikeElementsAction.setText("Group similar elements");
		fToggleGroupLikeElementsAction.setToolTipText("Group similar elements together");
		fToggleGroupLikeElementsAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		fRefreshAction = new Action() {
			public void run() {
				fViewer.refresh();
			}
		};
		fRefreshAction.setText("Refresh");
		fRefreshAction.setToolTipText("Refresh all");
		fRefreshAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		fOpenDeclarationAction = new Action() {
			public void run() {
				ISelection selection = fViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				String projectName = getProjectName(fViewer);
				String nameToFind = "";
				Object resolvedElement = null;
				if (obj instanceof TreeNode && ((TreeNode)obj).getData() instanceof ElementReference) {
					ElementReference ref = null;
					ref = (ElementReference) ((TreeNode)obj).getData();
					resolvedElement = ElementReferenceResolver.resolveElement(ref, ElementReferenceResolver.createResolutionContext(getScope(ref)));
					nameToFind = ref.getName();
				} else if (obj instanceof TypeElement) {
					resolvedElement = obj;
					nameToFind = ((TypeElement) obj).getName();
				} else if (obj instanceof MemberElement) {
					resolvedElement = obj;
					nameToFind = ((MemberElement) obj).getName();
				} else if (obj instanceof ElementReference) {
					ElementReference ref = (ElementReference) obj;
					resolvedElement = ElementReferenceResolver.resolveElement(ref, ElementReferenceResolver.createResolutionContext(getScope(ref)));
					openSelection(projectName, resolvedElement);
					System.out.println("(Index View) resolved element as "+resolvedElement);
					nameToFind = ((ElementReference)obj).getName();
				}
				searchIndex(resolvedElement, projectName, nameToFind);
			}
		};
		fOpenDeclarationAction.setText("Open declaration of element");
		fOpenDeclarationAction.setToolTipText("Open the declaration of this element");
		fOpenDeclarationAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		fDoubleClickAction = new Action() {
			public void run() {
				ISelection selection = fViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				String projectName = getProjectName(fViewer);
				openSelection(projectName, obj);
			}
		};
		fOpenReferenceAction = new Action() {
			public void run() {
				ISelection selection = fReferencesViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				String projectName = getProjectName(fViewer);
				openSelection(projectName, obj);
			}
		};
	}
	
	protected ScopeBlock getScope(ElementReference ref) {
		while (ref.eContainer() != null) {
			if (ref.eContainer() instanceof ScopeBlock) {
				return (ScopeBlock) ref.eContainer();
			}
			ref = (ElementReference) ref.eContainer();
		}
		return null;
	}

	private Object resolveSymbol(String projectName, Symbol symbol) {
		String type = symbol.getType();
		DesignerElement resolvedElement = IndexUtils.getElement(projectName, type);
		
		if (resolvedElement != null) {
			showMessage("Double-click detected on "+resolvedElement.toString());
		}
		return resolvedElement;
	}
	
	protected void openSelection(String projectName, Object obj) {
		if (obj instanceof TreeNode) {
			obj = ((TreeNode)obj).getData();
		}
		if (obj instanceof TreeNode && ((TreeNode)obj).getData() instanceof Symbol) {
			obj = resolveSymbol(projectName, (Symbol) ((TreeNode)obj).getData());
		} 
		IEditorPart openElement = openElement(obj);
		if (openElement == null) {
			if (obj instanceof ScopeBlock) {
				ScopeBlock scope = (ScopeBlock) obj;
				DesignerElement variableContext = IndexUtils.getVariableContext((EObject) scope);
				openElement = openElement(variableContext);
				if (openElement != null) {
					Object adapter = openElement.getAdapter(ITextEditor.class);
					if (adapter instanceof ITextEditor) {
						((ITextEditor)adapter).selectAndReveal(scope.getOffset(), scope.getLength());
						return;
					}
				}
			}
			showMessage("Double-click detected on "+obj.toString());
		}
	}

	private void findReferences() {
		ISelection selection = fViewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		String projectName = getProjectName(fViewer);
		String nameToFind = "";
		Object resolvedElement = null;
		if (obj instanceof TreeNode && ((TreeNode)obj).getData() instanceof ElementReference) {
			ElementReference ref = null;
			ref = (ElementReference) ((TreeNode)obj).getData();
			resolvedElement = ElementReferenceResolver.resolveElement(ref, ElementReferenceResolver.createResolutionContext(getScope(ref)));
			nameToFind = ref.getName();
		} else if (obj instanceof TypeElement) {
			resolvedElement = obj;
			nameToFind = ((TypeElement) obj).getName();
		} else if (obj instanceof MemberElement) {
			resolvedElement = obj;
			nameToFind = ((MemberElement) obj).getName();
		} else if (obj instanceof ElementReference) {
			ElementReference ref = (ElementReference) obj;
			resolvedElement = ElementReferenceResolver.resolveElement(ref, ElementReferenceResolver.createResolutionContext(getScope(ref)));
			System.out.println("(Index View) resolved element as "+resolvedElement);
			nameToFind = ((ElementReference)obj).getName();
		}
		searchIndex(resolvedElement, projectName, nameToFind);
	}
	
	protected void searchIndex(Object resolvedElement, String projectName, String nameToFind) {
		if (projectName == null || projectName.length() == 0
				|| nameToFind == null || nameToFind.length() == 0) {
			return;
		}
		SearchResult searchResult = SearchUtils.searchIndex(resolvedElement, projectName, nameToFind);

		List<EObject> exactMatches = new ArrayList<EObject>();
		List<EObject> inexactMatches = new ArrayList<EObject>();
		List<TreeParent> children = new ArrayList<TreeParent>();
		for (EObject definition : searchResult.getExactMatches()) {
			exactMatches.add(definition);
		}
		for (EObject definition : searchResult.getInexactMatches()) {
			inexactMatches.add(definition);
		}
		children.add(new TreeParent("Exact matches to "+nameToFind, exactMatches));
		children.add(new TreeParent("Inexact matches to "+nameToFind, inexactMatches));
		fReferencesViewer.setInput(children);
	}

	private String getProjectName(TreeViewer viewer) {
		String projName = "";
		TreeItem[] selection = viewer.getTree().getSelection();
		if (selection.length > 0) {
			TreeItem treeItem = selection[0];
			TreeItem parent = treeItem.getParentItem();
			while (parent != null) {
				if (parent.getParentItem() == null) {
					Object data = parent.getData();
					if (data instanceof DesignerProject) {
						return ((DesignerProject)data).getName();
					}
				}
				parent = parent.getParentItem();
			}
		}
		return projName;
	}

	private void hookDoubleClickAction() {
		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				fDoubleClickAction.run();
			}
		});
		fReferencesViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				fOpenReferenceAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			fViewer.getControl().getShell(),
			"Ontology Index View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		fViewer.getControl().setFocus();
	}

	public void modelChanged(StudioModelChangedEvent event) {
		final StudioModelDelta delta = event.getDelta();
		Display.getDefault().asyncExec(new Runnable() {
		
			public void run() {
				List<StudioProjectDelta> changedProjects = delta.getChangedProjects();
				for (StudioProjectDelta designerProjectDelta : changedProjects) {
//					System.out.println(designerProjectDelta.toString());
					DesignerProject changedProject = designerProjectDelta.getChangedProject();
					if (designerProjectDelta.getType() == IStudioElementDelta.ADDED
							|| designerProjectDelta.getType() == IStudioElementDelta.REMOVED) {
						// refresh entire viewer, and break, as no more granular refreshes are required
						fViewer.refresh();
						break;
					}
					List<DesignerElement> elementsToRefresh = new ArrayList<DesignerElement>();
					collectElementsToRefresh(designerProjectDelta, elementsToRefresh);
					for (DesignerElement designerElement : elementsToRefresh) {
						fViewer.refresh(designerElement);
					}
					fViewer.refresh(changedProject);
				}
			}
		});
	}

	protected void collectElementsToRefresh(ElementContainer delta, List<DesignerElement> elementsToRefresh) {
		EList<DesignerElement> entries = delta.getEntries();
		for (DesignerElement designerElement : entries) {
			if (designerElement instanceof ElementContainer) {
				collectElementsToRefresh((ElementContainer) designerElement, elementsToRefresh);
			} else if (designerElement instanceof StudioElementDelta) {
				DesignerElement affectedChild = ((IStudioElementDelta) designerElement.eContainer()).getAffectedChild();
				if (!elementsToRefresh.contains(affectedChild)) {
					elementsToRefresh.add(affectedChild);
				}
			}
		}
	}
}