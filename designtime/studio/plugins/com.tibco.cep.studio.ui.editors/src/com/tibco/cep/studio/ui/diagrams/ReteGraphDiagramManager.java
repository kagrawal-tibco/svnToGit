package com.tibco.cep.studio.ui.diagrams;

import java.awt.Font;
import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEFont;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSETextLabelUI;
import com.tomsawyer.graphicaldrawing.xml.TSEVisualizationXMLReader;

/**
 * 
 * @author ggrigore
 *
 */
public class ReteGraphDiagramManager extends EntityDiagramManager {
	
	private TSEColor RULE_START_COLOR = new TSEColor(255,110,7);	// orangish
	private TSEColor RULE_END_COLOR = new TSEColor(255,213,7);
	private TSEColor RULE_BORDER_COLOR = new TSEColor(255,0,0);

	private TSEColor ROOT_START_COLOR = new TSEColor(137, 137, 137);	// grayish
	private TSEColor ROOT_END_COLOR = new TSEColor(224, 224, 224);
	
	private TSEColor JOIN_START_COLOR = new TSEColor(99, 255, 151);  // greenish
	private TSEColor JOIN_END_COLOR = new TSEColor(198, 255, 201);	
	private TSEColor JOIN_BORDER_COLOR = new TSEColor(0,255,0);	

	private TSEColor FILTER_START_COLOR = new TSEColor(255, 250, 124);	// yellowish
	private TSEColor FILTER_END_COLOR = new TSEColor(255, 235, 198);	
	private TSEColor FILTER_BORDER_COLOR = new TSEColor(255, 216, 0);
	
	private TSEColor CLASS_START_COLOR = new TSEColor(0,208,255);	// bluish
	private TSEColor CLASS_END_COLOR = new TSEColor(206,255,242);	
	private TSEColor CLASS_BORDER_COLOR = new TSEColor(0,0,255);

	private TSEColor OTHER_START_COLOR = new TSEColor(163, 164, 255);
	private TSEColor OTHER_END_COLOR = new TSEColor(224, 229, 255);
	private TSEColor OTHER_BORDER_COLOR = new TSEColor(61, 77, 255);
	
	public ReteGraphDiagramManager(EditorPart editor) {
		super();
		this.editor = editor;
		TSEImage.setLoaderClass(this.getClass());
	}	

	protected boolean isValidModelFile(){
		return true;
	}
	
	public void openModel() {
		
		IPath fullpath = ((EntityDiagramEditorInput) this.editor.getEditorInput()).getFile().getLocation();
		File file = new File(fullpath.toString());
		TSEVisualizationXMLReader reader = new TSEVisualizationXMLReader(file);
		this.graphManager.emptyTopology();
		reader.setGraphManager(this.graphManager);
		reader.setServiceInputData(this.getLayoutManager().getInputData());

		try {
			reader.read();
		}
		catch (Exception e) {
			System.err.println("Error opening rete graph: " + e.getMessage());
			// e.printStackTrace();
			return;
		}
		
		this.decorateNodesWithLabels();
		this.decorateEdges();
		this.layoutManager.setLeftToRightOrthHierarchicalOptions();
		this.layoutManager.callBatchGlobalLayout();
	}
	
	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new LayoutManager(this);
		}
		return this.layoutManager;
	}	
	
	// overwritten from base class to do nothing
	public void postPopulateDrawingCanvas() { }	
	
	// handle: root, object type node, alpha node, join node, eval node and terminal nodes...
	private void decorateNodesWithNames() {
		TSENodeUI nodeUI = null;
		for (Object node : this.graphManager.getMainDisplayGraph().nodes()) {
			String tag = ((TSENode) node).getTagString();
			nodeUI = new RoundRectNodeUI();
			if (tag != null) {
				if (tag.equalsIgnoreCase("root")) {
					((RoundRectNodeUI)nodeUI).setGradient(ROOT_START_COLOR, ROOT_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(TSEColor.black);
					((TSENode)node).setHeight(60);
				}
				else if (tag.equalsIgnoreCase("class")) {
					((RoundRectNodeUI)nodeUI).setGradient(CLASS_START_COLOR, CLASS_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(CLASS_BORDER_COLOR);					
					TSENodeLabel label =  (TSENodeLabel) ((TSENode) node).labels().get(0);
					((TSENode)node).setName(label.getName());
					((TSENode)node).discard(label);
				}
				else if (tag.equalsIgnoreCase("rule")) {
					((RoundRectNodeUI)nodeUI).setGradient(RULE_START_COLOR, RULE_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(RULE_BORDER_COLOR);					
					TSENodeLabel label =  (TSENodeLabel) ((TSENode) node).labels().get(0);
					((TSENode)node).setName(label.getName());
					((TSENode)node).discard(label);
				}
				else if (tag.equalsIgnoreCase("join")) {
					((RoundRectNodeUI)nodeUI).setGradient(JOIN_START_COLOR, JOIN_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(JOIN_BORDER_COLOR);					
				}
				else if (tag.equalsIgnoreCase("filter")) {
					((RoundRectNodeUI)nodeUI).setGradient(FILTER_START_COLOR, FILTER_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(FILTER_BORDER_COLOR);					
				}				
				else {
					System.out.println("Unknown node type:" + tag);
					((RoundRectNodeUI)nodeUI).setGradient(OTHER_START_COLOR, OTHER_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(OTHER_BORDER_COLOR);					
				}
				((TSENode)node).setUI(nodeUI);
				((TSENode)node).setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
			}
			else {
				((RoundRectNodeUI)nodeUI).setGradient(OTHER_START_COLOR, OTHER_END_COLOR);
				((RoundRectNodeUI)nodeUI).setBorderColor(OTHER_BORDER_COLOR);					
				((TSENode)node).setSize(40, 60);
			}
		}
	}
	
	private void decorateNodesWithLabels() {
		TSENodeUI nodeUI = null;
		boolean first = true;
		for (Object node : this.graphManager.getMainDisplayGraph().nodes()) {
			String tag = ((TSENode) node).getTagString();
			nodeUI = new RoundRectNodeUI();
			if (tag != null) {
				if (tag.equalsIgnoreCase("root")) {
					((RoundRectNodeUI)nodeUI).setGradient(ROOT_START_COLOR, ROOT_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(TSEColor.black);					
				}
				else if (tag.equalsIgnoreCase("class")) {
					((RoundRectNodeUI)nodeUI).setGradient(CLASS_START_COLOR, CLASS_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(CLASS_BORDER_COLOR);					
				}
				else if (tag.equalsIgnoreCase("rule")) {
					((RoundRectNodeUI)nodeUI).setGradient(RULE_START_COLOR, RULE_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(RULE_BORDER_COLOR);					
				}
				else if (tag.equalsIgnoreCase("join")) {
					((RoundRectNodeUI)nodeUI).setGradient(JOIN_START_COLOR, JOIN_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(JOIN_BORDER_COLOR);					
				}
				else if (tag.equalsIgnoreCase("filter")) {
					((RoundRectNodeUI)nodeUI).setGradient(FILTER_START_COLOR, FILTER_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(FILTER_BORDER_COLOR);					
				}				
				else {
					System.out.println("Unknown node type:" + tag);
					((RoundRectNodeUI)nodeUI).setGradient(OTHER_START_COLOR, OTHER_END_COLOR);
					((RoundRectNodeUI)nodeUI).setBorderColor(OTHER_BORDER_COLOR);					
				}
				((TSENode) node).setUI(nodeUI);
				((TSENode) node).setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
			}
			else {
				((RoundRectNodeUI)nodeUI).setGradient(OTHER_START_COLOR, OTHER_END_COLOR);
				((RoundRectNodeUI)nodeUI).setBorderColor(OTHER_BORDER_COLOR);					
				((TSENode) node).setSize(40, 60);
			}

			// iterate through all the labels and set their position to be below the node
			for (Object aLabel : ((TSENode)node).labels()) {
				this.layoutManager.setNodeLabelOptions((TSENodeLabel)aLabel);
			}
			
			// decrease font size a bit.
			if (first) {
				if (((TSENode)node).labels() != null && ((TSENode)node).labels().size() > 0) {
					TSENodeLabel aLabel = (TSENodeLabel) ((TSENode)node).labels().get(0);
					TSEFont tsFont = ((TSETextLabelUI) ((TSENodeLabel)aLabel).getUI()).getFont();
					Font realFont = tsFont.getFont();
					tsFont.setFont(realFont.deriveFont((float) realFont.getSize()-1));
					first = false;
				}
			}
		}
	}

	private void decorateEdges() {
		TSECurvedEdgeUI edgeUI = null;
		for (Object edge : this.graphManager.getMainDisplayGraph().edges()) {
			edgeUI = new TSECurvedEdgeUI();
			edgeUI.setAntiAliasingEnabled(true);
			edgeUI.setCurvature(100);
			((TSEEdge) edge).setUI(edgeUI);
		}
	}
	
	public void dispose() {
		if (managedForm != null && managedForm.getToolkit() != null) {
			if (managedForm.getToolkit() != null &&
					managedForm.getToolkit().getColors() != null) {
				managedForm.dispose();
			}
		}
        if (diagramManager != null) {
			diagramManager = null;
		}
		if (drawingCanvas != null) {
			drawingCanvas.doubleBuffer = null;
			drawingCanvas.removeAll();
			drawingCanvas = null;
		}
		if (graphManager != null) {
			graphManager.getEventManager().removeAllListeners(this);
			graphManager.getEventManager().removeAllListeners(graphManager);
			graphManager.setNodeBuilder(null);
			graphManager.setEdgeBuilder(null);
			graphManager.setLabelBuilder(null);
			graphManager.setConnectorBuilder(null);
			graphManager.setGraphBuilder(null);
			graphManager.dispose();
			graphManager = null;
		}
	}
	

	@Override
	protected TSENode createEntity(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TSEColor getEndColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TSEImage getEntityImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ELEMENT_TYPES getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEntityTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ELEMENT_TYPES[] getEntityTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TSEColor getStartColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateTSNode(TSENode node, Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected TSENode createSharedEntity(DesignerElement e) {
		// TODO Auto-generated method stub
		return null;
	}
}
