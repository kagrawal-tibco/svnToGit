package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import java.awt.BorderLayout;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

class ReadOnlyStateMachineDiagramManager extends StateMachineDiagramManager {

	private Composite parentContainer;

	private StateMachineSelectionTranslator selectionChangeListener;

	private ReadOnlyStateMachineDiagramChangeListener diagramChangeListener;

	private StateMachineComponentSelectTool selectTool;

	ReadOnlyStateMachineDiagramManager(StateMachineEditor stateMachineEditor) {
		super(stateMachineEditor);
	}

	void setManagedForm(ManagedForm managedForm){
		this.managedForm = managedForm;
	}

	@Override
	public void createPartControl(Composite container) {
		this.parentContainer = container;
		TSEImage.setLoaderClass(this.getClass());
		this.graphManager = new TSEGraphManager();
		this.drawingCanvas = new DrawingCanvas(this.graphManager);
		this.graphManager.assignSimpleUIs();
		this.setGraphManager(this.graphManager);
		createFormParts(managedForm.getForm(), managedForm.getToolkit());
		this.initOverviewComponent();
		this.postPopulateDrawingCanvas();
	}

	@Override
	protected void createFormParts(ScrolledForm form, FormToolkit toolkit) {
		diagramEditorControl = toolkit.createComposite(parentContainer,SWT.EMBEDDED);
		GridLayout elayout = new GridLayout();
		elayout.numColumns = 1;
		elayout.makeColumnsEqualWidth = false;
		diagramEditorControl.setLayout(elayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 110;
		gd.widthHint = 200;
		diagramEditorControl.setLayoutData(gd);

		getSwingContainer(diagramEditorControl).add(createDrawingCanvas(), BorderLayout.CENTER);
		toolkit.paintBordersFor(diagramEditorControl);

//        addKeyListener(diagramEditorControl);
	}

	@Override
	public void setStateMachine(StateMachine stateMachine){
		StateMachine oldStateMachine = getStateMachine();
		super.setStateMachine(stateMachine);
		if (oldStateMachine != stateMachine){
			fGUIDStateCache.clear();
			startNodes.clear();
			endNodeLists.clear();
			final TSEGraph graph = (TSEGraph)graphManager.getMainDisplayGraph();
			graph.emptyTopology();
			graph.setUserObject(stateMachine);
			generateModel();
			getLayoutManager().callBatchGlobalLayout();
			SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
//					System.out.println(Thread.currentThread());
					getDrawingCanvas().setGraph(graph, true);
				}

			});
		}
	}

	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (selectionChangeListener == null){
			selectionChangeListener = new StateMachineSelectionTranslator(this);
		}
		return selectionChangeListener;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SelectTool getSelectTool() {
		if (selectTool == null){
			selectTool = new StateMachineComponentSelectTool(getEditor());
		}
		return selectTool;
	}

	@Override
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (diagramChangeListener == null){
			diagramChangeListener = new ReadOnlyStateMachineDiagramChangeListener();
		}
		return diagramChangeListener;
	}
//
//	@Override
//	protected TSEMoveSelectedTool getMoveSelectedTool() {
//		return super.getMoveSelectedTool();
//	}
//
//	@Override
//	protected CreateNodeTool getNodeTool() {
//		if (this.stateMachineCreateNodeTool == null) {
//			this.stateMachineCreateNodeTool = new StateMachineCreateNodeTool(this, false);
//		}
//		return this.stateMachineCreateNodeTool;
//
//	}
//
//	@Override
//	protected CreateEdgeTool getEdgeTool() {
//		if (this.stateMachineCreateEdgeTool == null) {
//			this.stateMachineCreateEdgeTool = new StateMachineCreateEdgeTool(this,false);
//		}
//		return this.stateMachineCreateEdgeTool;
//	}
//
//	@Override
//	protected ReconnectEdgeTool getReconnectEdgeTool() {
//		return super.getReconnectEdgeTool();
//	}

	class ReadOnlyStateMachineDiagramChangeListener extends DiagramChangeListener<StateMachineDiagramManager>{

		private static final long serialVersionUID = 8580430749057210778L;

		ReadOnlyStateMachineDiagramChangeListener(){
			super(ReadOnlyStateMachineDiagramManager.this);
		}

	}

}