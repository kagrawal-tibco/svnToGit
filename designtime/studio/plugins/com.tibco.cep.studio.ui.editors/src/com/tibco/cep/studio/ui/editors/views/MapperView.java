package com.tibco.cep.studio.ui.editors.views;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.mapper.core.BEMapperControlArgs;
import com.tibco.cep.studio.mapper.ui.BEMapperInputOutputAdapter;
import com.tibco.cep.studio.mapper.ui.util.SWTMapperUtils;
import com.tibco.cep.studio.ui.editors.AbstractRuleFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.events.EventFormEditor;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class MapperView extends ViewPart {

	private RulesASTNode fNode;
	private String fProjectName;
	private IDocument fDocument; // needed to insert code into from XMLMapper
	private IResolutionContextProvider fProvider;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.tibco.cep.studio.ui.editors.views.MapperView";

	private TableViewer viewer;
	private Action refreshAction;
	private Action action2;
	private Action doubleClickAction;

	private BEMapperControlArgs mEArgs;
	private IEMapperControl mapperControl;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public MapperView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		mEArgs = new BEMapperControlArgs();
//		IEMapperControl mapperControl = EMapperFactory.createMapperControl(parent, mEArgs);
		mapperControl = SWTMapperUtils.createMapperControl(parent, mEArgs).getMapperControl();
		parent.layout();
		
		makeActions();
		contributeToActionBars();
		
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				MapperView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(refreshAction);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(refreshAction);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(action2);
	}

	private RuleElement getRuleElement(RulesASTNode node) {
		if (node == null) {
			return null;
		}
		while (node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		return (RuleElement) node.getData("element");
	}

	private List<VariableDefinition> getVariableDefinitions(
			RuleElement ruleElement, int offset) {
		EObject elementReference = RuleGrammarUtils.getElementReference(getMethodCallNode());
		List<VariableDefinition> vars = new ArrayList<VariableDefinition>();
		if (elementReference instanceof ElementReference) {
			IResolutionContext resolutionContext = fProvider.getResolutionContext((ElementReference) elementReference);
			for (GlobalVariableDef globalVariableDef : resolutionContext.getGlobalVariables()) {
				if (globalVariableDef.getOffset() < offset) {
					vars.add(globalVariableDef);
				}
			}
		} else {
			if (ruleElement == null) {
				return vars;
			}
			for (GlobalVariableDef globalVariableDef : ruleElement.getGlobalVariables()) {
				if (globalVariableDef.getOffset() < offset) {
					vars.add(globalVariableDef);
				}
			}
		}

		if (elementReference.eContainer() instanceof ScopeBlock) {
			ScopeBlock scope = (ScopeBlock) elementReference.eContainer();
			processScope(scope, vars, offset);
		} else {
			// does this ever happen?  Leaving here for now
			ScopeBlock scope = ruleElement.getScope();
			processAllScopes(scope, vars, offset);
		}
		Collections.sort(vars, new Comparator<VariableDefinition>() {

			@Override
			public int compare(VariableDefinition o1, VariableDefinition o2) {
				return o1.getOffset() > o2.getOffset() ? 1 : -1;
			}
		});
		return vars;
	}

	private List<VariableDefinition> processScope(ScopeBlock scope, List<VariableDefinition> vars,
			int offset) {

		if (scope == null || scope.getOffset() > offset) {
			return vars;
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getOffset() < offset) {
				vars.add(localVariableDef);
			}
		}
		ScopeBlock parent = scope.getParentScopeDef();
		processScope(parent, vars, offset);
		return vars;

	}

	private RulesASTNode getMethodCallNode() {
		RulesASTNode methodNode = fNode;
		while (methodNode.getParent() != null) {
			methodNode = (RulesASTNode) methodNode.getParent();
			if (methodNode.getType() == RulesParser.METHOD_CALL) {
				return methodNode.getChildByFeatureId(RulesParser.NAME);
			}
		}
		return methodNode;
	}

	private List<VariableDefinition> processAllScopes(ScopeBlock scope,
			List<VariableDefinition> vars, int offset) {
		if (scope == null || scope.getOffset() > offset) {
			return vars;
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getOffset() < offset) {
				vars.add(localVariableDef);
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processAllScopes(scopeBlock, vars, offset);
		}
		return vars;
	}
	private void makeActions() {
		refreshAction = new Action() {
			public void run() {
				try {
					IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					Entity entity = null;
					mEArgs = new BEMapperControlArgs();
					List<EObject> sourceElements = new ArrayList<EObject>();
					if (activeEditor instanceof ConceptFormEditor) {
						entity = ((ConceptFormEditor) activeEditor).getConcept();
						sourceElements.add(entity);
					} else if (activeEditor instanceof EventFormEditor) {
						entity = ((EventFormEditor) activeEditor).getEntity();
						sourceElements.add(entity);
					} else if (activeEditor instanceof AbstractRuleFormEditor) {
						RulesEditor rulesEditor = ((AbstractRuleFormEditor) activeEditor).getRulesEditor();
						try {
							fNode = rulesEditor.getCurrentNode();
							fProvider = rulesEditor;
							if (fNode == null || fNode.getType() != RulesParser.StringLiteral) {
								return;
							}
							String xsltText = fNode.getText();
							RuleElement element = (RuleElement) getRuleElement(fNode);
							List<VariableDefinition> variableDefinitions = getVariableDefinitions(element, fNode.getOffset());
							sourceElements.addAll(variableDefinitions);
							Predicate function = getFunction(fNode);
							MapperInvocationContext ctx = new MapperInvocationContext(rulesEditor.getProjectName(), variableDefinitions, xsltText, function, (IDocument) rulesEditor.getStorage().getAdapter(IDocument.class), fNode);
							mEArgs.setInvocationContext(ctx);
							
							List receivingParams = XSTemplateSerializer.getReceivingParms(ctx.getXslt());
							if (receivingParams.size() == 1) {
								String entityPath = (String) receivingParams.get(0);
								entity = IndexUtils.getEntity(rulesEditor.getProjectName(), entityPath);
							} else {
								// just create something
								DesignerElement designerElement = IndexUtils.getAllElements(rulesEditor.getProjectName(), ELEMENT_TYPES.CONCEPT).get(0);
								if (designerElement instanceof EntityElement) {
									entity = ((EntityElement) designerElement).getEntity();
								}
							}
//							ctx.setMapperEditable(fMapperEditable);
//							if (function.getName().namespaceURI.equals(XPATH_PREFIX)) {
//								MapperUtils.invokeXPathBuilder(ctx, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//							} else {
//								MapperUtils.invokeMapper(ctx, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					mEArgs.setSourceElements(sourceElements);
					mEArgs.setTargetElement(entity);
					BEMapperInputOutputAdapter input = new BEMapperInputOutputAdapter(mapperControl, mEArgs);
					mapperControl.setInput(input);
				} catch (NullPointerException e) {
				}
			}
		};
		refreshAction.setText("Refresh from editor");
		refreshAction.setToolTipText("Refresh from editor");
		refreshAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			Display.getDefault().getActiveShell(),
			"Mapper Test View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
	
	private Predicate getFunction(RulesASTNode xmlStringNode) {
		RulesASTNode node = getMethodNameNode(xmlStringNode);
		EObject reference = RuleGrammarUtils.getElementReference(node);
		if (reference == null) {
			return null;
		}
		if (reference instanceof ElementReference) {
			Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope((ElementReference) reference)));
			if (element instanceof Predicate) {
				return (Predicate) element;
			}
		}
		return null;
	}

	private RulesASTNode getMethodNameNode(RulesASTNode xmlStringNode) {
		RulesASTNode node = (RulesASTNode) xmlStringNode.getParent();
		while (node != null) {
			if (node.getType() == RulesParser.METHOD_CALL) {
				RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
				if (nameNode.getType() == RulesParser.QUALIFIED_NAME) {
					return nameNode.getFirstChildWithType(RulesParser.SIMPLE_NAME);
				}
				return nameNode;
			}
			node = (RulesASTNode) node.getParent();
		}
		return null;
	}

}