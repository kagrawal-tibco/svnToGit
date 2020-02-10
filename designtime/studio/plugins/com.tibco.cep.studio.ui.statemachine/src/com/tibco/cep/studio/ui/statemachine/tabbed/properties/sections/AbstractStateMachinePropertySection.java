package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverviewAndDiagram;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants.STATE_TYPE;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tibco.cep.studio.ui.statemachine.tabbed.properties.StateMachinePropertySheetPage;
import com.tibco.cep.studio.ui.util.ToolBarProvider;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractStateMachinePropertySection extends AbstractPropertySection {

	protected StateMachinePropertySheetPage propertySheetPage;
	protected TSEGraph tseGraph;
	protected TSENode tseNode;
	protected TSEdge tsedge;
	protected TSEObject tseObject;
	protected EObject eObject;
	protected List<?> nodeList;
	
	protected IProject project;
	protected SashForm sashForm;
	
	protected StateMachineEditor editor;
	
	protected ToolItem removeDeclButton;
	protected ToolItem addDeclButton;
	private Container contentPane;
	protected static final java.awt.Font awtFont = new java.awt.Font("Tahoma", 0, 11); 
	
	@Override
	public void dispose() {
		if (sashForm != null && !sashForm.isDisposed()) {
			Listener listener = ((MDSashForm) sashForm).listener;
			Control[] children = sashForm.getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof Sash)
					continue;
				children[i].removeListener(SWT.Resize, listener);
			}
			sashForm.dispose();
		}
		if (nodeList != null) {
			nodeList = null;
		}
		sashForm = null;
		super.dispose();
		tseGraph = null;
		tseNode = null;
		tsedge = null;
		tseObject = null;
		eObject = null;
		editor = null;
		propertySheetPage = null;
		if (contentPane != null) {
			DiagramUtils.disposePanel(contentPane);
		}
		setInput(null, null);
	}

	/**
	 * @param parent
	 * @param labels
	 * @return
	 */
	protected int getStandardLabelWidth(Composite parent, String[] labels) {
		int standardLabelWidth = STANDARD_LABEL_WIDTH;
		GC gc = new GC(parent);
		int indent = gc.textExtent("XXX").x; //$NON-NLS-1$
		for (int i = 0; i < labels.length; i++) {
			int width = gc.textExtent(labels[i]).x;
			if (width + indent > standardLabelWidth) {
				standardLabelWidth = width + indent;
			}
		}
		gc.dispose();
		return standardLabelWidth;
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.propertySheetPage = (StateMachinePropertySheetPage) aTabbedPropertySheetPage;
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		editor = (StateMachineEditor) getPart();
		project = editor.getProject();
		
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSEGraph) {
			tseGraph = (TSEGraph) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			eObject = (EObject) tseGraph.getUserObject();
			tseNode = null;
			tsedge = null;
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSEdge) {
			tsedge = (TSEdge) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			eObject = (EObject) tsedge.getUserObject();
			tseNode = null;
			tseGraph = null;
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof TSENode) {
			tseNode = (TSENode) ((IStructuredSelection) selection).getFirstElement();
			nodeList = ((IStructuredSelection) selection).toList();
			eObject = (EObject) tseNode.getUserObject();
			tsedge = null;
			tseGraph = null;
		}
	}
	
	protected IProject getProject() {
		if (project == null) {
			editor = (StateMachineEditor) getPart();
			if (editor != null) {
				project = editor.getProject();
			}
		}
		return project;
	}
	
	/**
	 * @param newText
	 * @param feature
	 * @return
	 */
	protected boolean isEqual(String newText, EStructuralFeature feature) {
		return getFeatureAsText(feature).equals(newText);
	}
	
	/**
	 * @param feature
	 * @return
	 */
	protected String getFeatureAsText(EStructuralFeature feature) {
		if(eObject == null) return "";
		String string = (String)eObject.eGet(feature);
		if (string == null) {
			return "";
		}
		return string;
	}
	
	/**
	 * @param parent
	 * @return
	 */
//	@SuppressWarnings("serial")
//	protected Container getSwingContainer(Composite parent) {
//		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
//		new SyncXErrorHandler().installHandler();
//		Panel panel = new Panel(new BorderLayout()) {
//			public void update(java.awt.Graphics g) {
//				paint(g);
//			}
//		};
//		frame.add(panel);
//		JRootPane root = new JRootPane();
//		panel.add(root);
//		contentPane = root.getContentPane();
//		return contentPane;
//	}
	
	/**
	 * @param text
	 * @param feature
	 */
	protected void handleTextModified(String newText,EStructuralFeature feature) {
		boolean equals = isEqual(newText,feature);
		if (!equals) {
			EditingDomain editingDomain = ((StateMachineEditor) getPart()).getEditingDomain();
			if (nodeList.size() == 1) {
				State state = (State)eObject;
				if (state == null) {
					return;
				}
				processStateRuleNames(state, state.getName(), newText);
				/* apply the property change to single selected object */
				EditorUtils.executeCommand((StateMachineEditor) getPart(), SetCommand.create(editingDomain, eObject, feature,newText));
				if(tseNode !=null &&  feature!=null && feature.getName().equalsIgnoreCase("Name")){
					tseNode.setName(newText);
					
					if(tseNode.getAttributeValue(STATE_TYPE).equals(STATE.START)
							|| tseNode.getAttributeValue(STATE_TYPE).equals(STATE.END)
							|| tseNode.getAttributeValue(STATE_TYPE).equals(STATE.SIMPLE)
							|| tseNode.getAttributeValue(STATE_TYPE).equals(STATE.SUBSTATEMACHINE)){
						((TSENodeLabel)(tseNode.labels().get(0))).setName(newText);
					}
					doRefreshAll(tseNode);
				}
				if(tseNode != null && eObject != null){
					tseNode.setTooltipText(createTooltip(state));
				}
//				refreshDiagram(((StateMachineDiagramManager)((StateMachineEditor) getPart()).getStateMachineDiagramManager()));
//				propertySheetPage.doRefreshTitleBar(tseNode);// Refresh Title for Property Name Change
//				ActionUtils.refreshOverView(((StateMachineEditor) getPart()).getEditorSite(), true, true);
			} 
		}
	}
	
	/**
	 * @param object
	 */
	public void doRefreshAll(TSEObject object){
		propertySheetPage.doRefreshTitleBar(object);// Refresh Title for Property Name Change
		StateMachineEditor editor = (StateMachineEditor) getPart();
		StateMachineDiagramManager manager = (StateMachineDiagramManager)editor.getStateMachineDiagramManager();
		refreshOverviewAndDiagram(editor.getEditorSite(), true, true, manager, true);
	}
	
	 /**
     * Resize handler method
     */
    protected void hookResizeListener() {
		Listener listener = ((MDSashForm) sashForm).listener;
		Control[] children = sashForm.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Sash)
				continue;
			children[i].addListener(SWT.Resize, listener);
		}
	}
	
	/**
	 * Sash form class
	 * @author sasahoo
	 *
	 */
	protected class MDSashForm extends SashForm {
		@SuppressWarnings("rawtypes")
		ArrayList sashes = new ArrayList();
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.MouseEnter:
					e.widget.setData("hover", Boolean.TRUE); //$NON-NLS-1$
					((Control) e.widget).redraw();
					break;
				case SWT.MouseExit:
					e.widget.setData("hover", null); //$NON-NLS-1$
					((Control) e.widget).redraw();
					break;
				case SWT.Paint:
					onSashPaint(e);
					break;
				case SWT.Resize:
					hookSashListeners();
					break;
				}
			}
		};
		
        /**
         * 
         * @param parent
         * @param style
         */
		public MDSashForm(Composite parent, int style) {
			super(parent, style);
		}

		public void layout(boolean changed) {
			super.layout(changed);
			hookSashListeners();
		}

		public void layout(Control[] children) {
			super.layout(children);
			hookSashListeners();
		}

		@SuppressWarnings("unchecked")
		private void hookSashListeners() {
			purgeSashes();
			Control[] children = getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof Sash) {
					Sash sash = (Sash) children[i];
					if (sashes.contains(sash))
						continue;
					sash.addListener(SWT.Paint, listener);
					sash.addListener(SWT.MouseEnter, listener);
					sash.addListener(SWT.MouseExit, listener);
					sashes.add(sash);
				}
			}
		}

		private void purgeSashes() {
			for (Iterator<?> iter = sashes.iterator(); iter.hasNext();) {
				Sash sash = (Sash) iter.next();
				if (sash.isDisposed())
					iter.remove();
			}
		}
	}
	
	/**
	 * This method handles the resize action.
	 * @param e
	 */
	private void onSashPaint(Event e) {
		Sash sash = (Sash) e.widget;
		FormColors colors = getWidgetFactory().getColors();
		boolean vertical = (sash.getStyle() & SWT.VERTICAL) != 0;
		GC gc = e.gc;
		Boolean hover = (Boolean) sash.getData("hover"); //$NON-NLS-1$
		gc.setBackground(colors.getColor(IFormColors.TB_BG));
		gc.setForeground(colors.getColor(IFormColors.TB_BORDER));
		Point size = sash.getSize();
		if (vertical) {
			if (hover != null)
				gc.fillRectangle(0, 0, size.x, size.y);
			// else
			// gc.drawLine(1, 0, 1, size.y-1);
		} else {
			if (hover != null)
				gc.fillRectangle(0, 0, size.x, size.y);
			// else
			// gc.drawLine(0, 1, size.x-1, 1);
		}
	}
	
	protected ToolBar createToolbar(Composite parent) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        
        addDeclButton = new ToolItem(toolBar, SWT.PUSH);
        Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_ADD);
        addDeclButton.setImage(addImg);
        addDeclButton.setToolTipText("Add");
        addDeclButton.setText("Add");
        
        addDeclButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				add();
			}
		});
        
        removeDeclButton = new ToolItem(toolBar, SWT.PUSH);
        Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
        removeDeclButton.setImage(delImg);
        removeDeclButton.setToolTipText("Delete");
        removeDeclButton.setText("Delete");
        removeDeclButton.setEnabled(false);
        removeDeclButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				remove();
			}
		});
        toolBar.pack();
        return toolBar;
	}
	
	protected void add(){
		
	}
	protected void remove(){
		
	}
	
	/**
	 * @param name
	 * @param oldStateName
	 * @param newStateName
	 * @return
	 */
	protected void modiflyRuleName(Compilable rule, 
			                       String oldStateName, 
			                       String newStateName){
		if(rule != null){ 
			rule.setName(replace(rule.getName(), oldStateName, newStateName));
		}
	}
	
	/**
	 * @param state
	 * @param oldStateName
	 * @param newStateName
	 */
	protected void processStateRuleNames(State state, 
			                             String oldStateName, 
			                             String newStateName){
		if (state.isInternalTransitionEnabled()){
			modiflyRuleName(state.getInternalTransitionRule(), oldStateName, newStateName);
		}
		modiflyRuleName( state.getEntryAction(), oldStateName, newStateName);
		modiflyRuleName( state.getExitAction(), oldStateName, newStateName);
		modiflyRuleName( state.getTimeoutAction(), oldStateName, newStateName);
		modiflyRuleName( state.getTimeoutExpression(), oldStateName, newStateName);
	}
	
	/**
	 * @param stateTransition
	 * @param oldStateName
	 * @param newStateName
	 */
	protected void processStateTransitionRuleName(StateTransition stateTransition, 
			                                      String oldStateName, 
			                                      String newStateName){
		modiflyRuleName(stateTransition.getGuardRule(), oldStateName, newStateName);
	}
}