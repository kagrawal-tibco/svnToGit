package com.tibco.cep.studio.ui.diagrams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.EntityLayoutManager;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.ui.UMLCurvedEdgeUI;
import com.tibco.cep.diagramming.ui.UMLEdgeUI;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.tools.EntityCreateEdgeTool;
import com.tibco.cep.studio.ui.diagrams.tools.EntityCreateNodeTool;
import com.tibco.cep.studio.ui.diagrams.tools.EntitySelectTool;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditorInput;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.xml.TSEEdgeXMLReader;
import com.tomsawyer.graphicaldrawing.xml.TSEEdgeXMLWriter;
import com.tomsawyer.graphicaldrawing.xml.TSENodeXMLReader;
import com.tomsawyer.graphicaldrawing.xml.TSENodeXMLWriter;
import com.tomsawyer.graphicaldrawing.xml.TSEVisualizationXMLReader;
import com.tomsawyer.graphicaldrawing.xml.TSEVisualizationXMLWriter;
import com.tomsawyer.interactive.command.TSCommandInterface;

/**
 * 
 * @author ggrigore 
 *
 */
public abstract class EntityDiagramManager<T extends Entity> extends DiagramManager implements IPropertyChangeListener {

	final private boolean PERSIST_DIAGRAM = false;
	protected IProject project;
	protected EditorPart editor;
	protected List<TSENode> entityNodes;
	protected List<TSEEdge> entityEdgeList;
	protected HashMap<String, TSENode> nodeMap;	
	protected HashMap<String,String>sharedentityMap;
	protected Composite swtAwtComponent;
	protected Entity selectedEntity;
	protected List<DesignerElement> selectedEntities;
	protected List<IFile> selectedProcessFiles;
	protected EntityNodeUI entityUI;
	protected EntityNodeUI metricEntityUI;
	protected UMLCurvedEdgeUI inhcurEdgeUI;
	protected UMLCurvedEdgeUI refcurEdgeUI;
	protected UMLCurvedEdgeUI concurEdgeUI;
	protected UMLEdgeUI inhEdgeUI;
	protected UMLEdgeUI refEdgeUI;
	protected UMLEdgeUI conEdgeUI;
	protected boolean isGeneratingModel = false;
	protected IProgressMonitor progressMonitor;
	protected TSEColor startColor;
	protected TSEColor endColor;
	protected TSENodeXMLReader entityReader;
	protected TSENodeXMLWriter entityWriter;
	protected TSEEdgeXMLReader entityLinkReader;
	protected TSEEdgeXMLWriter entityLinkWriter;
	protected EntitySelectTool selectTool;
	private EntityCreateEdgeTool entityCreateEdgeTool;
	protected EntityCreateNodeTool entityCreateNodeTool;
	
	abstract public TSEColor getStartColor();
	abstract public TSEColor getEndColor();	

	abstract protected TSENode createEntity(T entity);
	abstract protected TSENode createSharedEntity(DesignerElement e);
	protected TSENode createImplementation(Implementation implementation) {
		return null;
	}
	abstract public ELEMENT_TYPES getEntityType();
	abstract protected ELEMENT_TYPES[] getEntityTypes();	
	abstract protected TSEImage getEntityImage();

	abstract public String getExtension();
	abstract protected String getEntityTypeName();
	
	abstract public void populateTSNode(TSENode node, T entity);
	protected IPreferenceStore prefStore;
	int edgetype;
	
	public EntityDiagramManager() {
		this.initUI();
	}
	
	
	/**
	 * @param editor
	 */
	public EntityDiagramManager(EditorPart editor) {
		this.editor = editor;
		IEditorInput editorInput = editor.getEditorInput();
		if (editor != null && editorInput != null) {
			if (editorInput instanceof EntityDiagramEditorInput){
				this.project = ((EntityDiagramEditorInput) editorInput).getProject();
				this.selectedEntity = ((EntityDiagramEditorInput) editorInput).getSelectedEntity();	
				this.selectedEntities = ((EntityDiagramEditorInput) editorInput).getSelectedEntities();
				this.selectedProcessFiles = ((EntityDiagramEditorInput) editorInput).getSelectedProcessFiles();
			}
			else if (editorInput instanceof SequenceDiagramEditorInput) {
				this.project = ((SequenceDiagramEditorInput) editorInput).getProject();
				this.selectedEntity = ((SequenceDiagramEditorInput) editorInput).getSelectedEntity();	
			}
			else if (editorInput instanceof DependencyDiagramEditorInput) {
				this.project = ((DependencyDiagramEditorInput) editorInput).getProject();
				this.selectedEntity = ((DependencyDiagramEditorInput) editorInput).getSelectedEntity();	
			}
		}
	}

	/**
	 * @param project
	 * @param monitor
	 */
	public EntityDiagramManager(IProject project, IProgressMonitor monitor) {
		this.project = project;
		this.progressMonitor = monitor;
	}

	public void initialize() {
		super.initialize();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				initUI();
				EntityDiagramManager.this.graphManager = new TSEGraphManager();
				EntityDiagramManager.this.drawingCanvas = new DrawingCanvas(EntityDiagramManager.this.graphManager);	
				EntityDiagramManager.this.graphManager.assignSimpleUIs();		
				setGraphManager(EntityDiagramManager.this.graphManager);
			}
		});
	}
	
	public void initialize(TSEGraphManager graphManager, DrawingCanvas drawingCanvas) {
		super.initialize();
		initUI();
		this.graphManager = graphManager;
		this.drawingCanvas = drawingCanvas;	
		this.graphManager.assignSimpleUIs();		
		super.setGraphManager(graphManager);
	}
	

	@SuppressWarnings("unchecked")
	protected void initUI() {
		
		this.entityUI = new EntityNodeUI();
		this.entityUI.setImage(this.getEntityImage());
		this.entityUI.setGradient(this.getStartColor(), this.getEndColor());
		this.entityUI.setBorderColor(new TSEColor(255, 184, 20));
		
		this.metricEntityUI = new EntityNodeUI();
		this.metricEntityUI.setImage(EntityNodeUI.METRIC_IMAGE);
		this.metricEntityUI.setGradient(EntityNodeUI.METRIC_START_COLOR,EntityNodeUI.METRIC_END_COLOR);
		this.metricEntityUI.setBorderColor(EntityNodeUI.METRIC_START_COLOR);
	
		this.inhcurEdgeUI = new UMLCurvedEdgeUI(UMLCurvedEdgeUI.INHERITANCE);
		this.inhcurEdgeUI.setAntiAliasingEnabled(true);
		this.inhcurEdgeUI.setCurvature(100);
		this.refcurEdgeUI = new UMLCurvedEdgeUI(UMLCurvedEdgeUI.REFERENCE);
		this.refcurEdgeUI.setAntiAliasingEnabled(true);
		this.refcurEdgeUI.setCurvature(100);
		this.concurEdgeUI = new UMLCurvedEdgeUI(UMLCurvedEdgeUI.CONTAINMENT);
		this.concurEdgeUI.setAntiAliasingEnabled(true);
		this.concurEdgeUI.setCurvature(100);		
		
		
		this.inhEdgeUI = new UMLEdgeUI(UMLEdgeUI.INHERITANCE);
		this.inhEdgeUI.setAntiAliasingEnabled(true);
		this.refEdgeUI = new UMLEdgeUI(UMLEdgeUI.REFERENCE);
		this.refEdgeUI.setAntiAliasingEnabled(true);
		this.conEdgeUI = new UMLEdgeUI(UMLEdgeUI.CONTAINMENT);
		this.conEdgeUI.setAntiAliasingEnabled(true);
	
		// DIFF: this block not needed:
		

		this.entityNodes = new LinkedList();
		this.entityEdgeList=new LinkedList();
		this.nodeMap = new HashMap<String, TSENode>();
		this.sharedentityMap = new HashMap<String, String>();
		this.prefStore=EditorsUIPlugin.getDefault().getPreferenceStore();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#isValidModelFile()
	 */
	protected boolean isValidModelFile(){
		return true;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public SelectTool getSelectTool() {
		if (this.selectTool == null) {
			this.selectTool = new EntitySelectTool(this);
		}
		return this.selectTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getSelectTool()
	 */
	public CreateEdgeTool getEdgeTool() {
		if (this.entityCreateEdgeTool == null) {
			this.entityCreateEdgeTool = new EntityCreateEdgeTool(this);
		}
		return this.entityCreateEdgeTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getNodeTool()
	 */
	public CreateNodeTool getNodeTool(){
		if (this.entityCreateNodeTool == null) {
			this.entityCreateNodeTool = new EntityCreateNodeTool(this);
		}
		return this.entityCreateNodeTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramChangeListener()
	 */
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.changeListener == null) {
			this.changeListener = new EntityDiagramChangeListener(this);
		}
		return this.changeListener;
	}
	
	protected TSENodeXMLReader getEntityReader() {
		if (this.entityReader == null) {
			this.entityReader = new EntityXMLReader(this);
		}
		
		return this.entityReader;
	}
	
	protected TSEEdgeXMLReader getEntityLinkReader() {
		if (this.entityLinkReader == null) {
			this.entityLinkReader = new EntityLinkXMLReader(this);
		}
		
		return this.entityLinkReader;
	}
	
	public TSENodeXMLWriter getEntityWriter() {
		if (this.entityWriter == null) {
			this.entityWriter = new EntityXMLWriter();
		}
		
		return this.entityWriter;
	}
	
	public TSEEdgeXMLWriter getEntityLinkWriter() {
		if (this.entityLinkWriter == null) {
			this.entityLinkWriter = new EntityLinkXMLWriter();
		}
		
		return this.entityLinkWriter;
	}
	

	public void openModel() {
		this.isGeneratingModel = false;
		// Register the new UI for the purposes of XML reading.
		// TSEEnumerationTable.getTable().addUIName("conceptNode", ConceptNodeUI.class);
		File f = new File(this.getFileName());
		TSEVisualizationXMLReader reader = new TSEVisualizationXMLReader(f);
		// DIFF: next 2 lines
		reader.setNodeReader(this.getEntityReader());
		reader.setEdgeReader(this.getEntityLinkReader());

		// System.out.println("Opening " + f.getAbsoluteFile()); 

		this.entityNodes.clear();
		this.graphManager.emptyTopology();
		reader.setGraphManager(this.graphManager);
		reader.setServiceInputData(this.getLayoutManager().getInputData());

		// TODO: we temporarily disable storing diagrams till post 4.0 release
		if (PERSIST_DIAGRAM == false) {
			this.generateModel();
		}
		else {
			try {
				reader.read();
				EntityNodeData.calculateConceptNodeSizes(this.entityNodes, this.drawingCanvas);
				System.out.println("Opened diagram with " + this.graphManager.numberOfNodes() + " nodes and " + this.graphManager.numberOfEdges() + " edges.");	
			} catch (FileNotFoundException e) {
				System.out.println("Entity view diagram not found, generating one.");
				this.generateModel();
			} catch (IOException e) {
				System.err.println("Error opening entity view diagram!");
				e.printStackTrace();
				this.generateModel();
			}
		}
		
		if (this.selectedEntity != null) {
			final TSENode selectedNode = this.findTSNode(this.selectedEntity);
			if (selectedNode != null) {
				System.out.println("Found entity: " + this.selectedEntity.getName());
				this.drawingCanvas.centerPointInCanvas(selectedNode.getCenter(), true);
				selectedNode.setSelected(true);
				this.drawingCanvas.drawGraph();
				this.drawingCanvas.repaint();
                SwingUtilities.invokeLater(new Runnable()
                {
                    /* (non-Javadoc)
                     * @see java.lang.Runnable#run()
                     */
                    public void run()
                    {
                    	drawingCanvas.centerPointInCanvas(selectedNode.getCenter(), true);

//                    	drawingCanvas.scrollBy(30, 30, true);
//                    	drawingCanvas.drawGraph();
//                    	drawingCanvas.repaint();
//                    	drawingCanvas.setVisible(true);
//                    	drawingCanvas.centerPointInCanvas(selectedNode.getCenter(), true);
                    }
                });

			}
			else {
				System.out.println("Input entity not found in diagram!");
			}
		}
		else {
			// System.out.println("selectedEntity is null");
		}
	}

	public <E extends EObject> void generateModel() {
		if (this.progressMonitor == null) {
			this.progressMonitor = new NullProgressMonitor();
		}
		this.progressMonitor.beginTask("Generating " + 
			this.getEntityTypeName() +
			" model", 3);
		
		this.isGeneratingModel = true;
		this.graphManager.emptyTopology();
		this.entityNodes.clear();
		this.nodeMap.clear();
		// iterate over all entities
		// DIFF
		List<DesignerElement> list = IndexUtils.getAllElements(getProjectName(), this.getEntityTypes());
		Iterator<DesignerElement> iter = list.iterator();
		
		this.progressMonitor.subTask("Creating " + this.getEntityTypeName() + " diagram");
		while (iter.hasNext()) {
			DesignerElement e = iter.next();
			if(e instanceof SharedElement){
			/*This part has been commented as only those artifacts of project library 
			 *  should be shown in the diagrams
			 *  who have a relationship with the artifacts of the project in consideration. 
			 */
			//	this.createSharedEntity(e);
			}else if (e instanceof EntityElement) {
				Entity entity = ((EntityElement)e).getEntity();
				this.createEntity((T)entity);
			} else if (e instanceof DecisionTableElement) {
				if(AddonUtil.isDecisionManagerAddonInstalled()) {
					Implementation implementation = (Implementation) ((DecisionTableElement)e).getImplementation();
					createImplementation(implementation);
				}
			}
		}
		this.progressMonitor.worked(1);
		
		// it is imperative that this gets called as soon as the graph manager
		// is created so that the rule node sizes and connector positions are
		// calculated. This has to happen *after* all the graph objects in the
		// graph manager are created so that it can do the right thing. Don't call
		// layout or display a graph manager with concept nodes till this is called!
		EntityNodeData.calculateConceptNodeSizes(this.entityNodes, this.drawingCanvas);
		
		// TODO: setting the options should not be needed.
		this.getLayoutManager().setHierarchicalOptions();
		this.progressMonitor.subTask("Generating " + this.getEntityTypeName() + " diagram layout");
		this.progressMonitor.worked(1);
		// also save it the first time around:
		this.progressMonitor.subTask("Saving " + this.getEntityTypeName() + " diagram");
		this.save();
		this.progressMonitor.worked(1);
		this.isGeneratingModel = false;
		this.progressMonitor.done();
	}

	public void save() {
		File f = new File(getFileName());
		TSEVisualizationXMLWriter writer = null;
		try {
			// TODO: should we store in unicode? should we compress?
			writer = new TSEVisualizationXMLWriter(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Cannot create file to save " + this.getEntityTypeName() + " diagram!");
			e.printStackTrace();
			return;
		}

		if (PERSIST_DIAGRAM == false) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			writer.setNodeWriter(this.getEntityWriter());
			writer.setEdgeWriter(this.getEntityLinkWriter());
			writer.setGraphManager(this.graphManager);
			writer.setServiceInputData(this.getLayoutManager().getInputData());
			writer.setPreferenceData(this.drawingCanvas.getPreferenceData());
			writer.setIndenting(false);
			writer.write();
		}
	}

	public String getFileName() {
		return this.getProjectLocation() + "/" + this.getProjectName() + this.getExtension();
	}

	
	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new EntityLayoutManager(this);
		}
		return this.layoutManager;
	}

	public void resetConceptNodes() {
		this.entityNodes.clear();
	}	

	public void addConceptNode(TSENode node) {
		this.entityNodes.add(node);
	}

	public IProject getProject() {
		return this.project;
	}
	
	@Override
	public TSCommandInterface redo() {
		// TODO Auto-generated method stub
		return super.redo();
	}
	@Override
	public TSCommandInterface undo() {
		// TODO Auto-generated method stub
		return super.undo();
	}
	public String getProjectName() {
		return this.project.getName();
	}

	public String getProjectLocation() {
		return this.project.getLocation().toString();
	}
	
	public EditorPart getEditor() {
		return this.editor;
	}

	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}

	public TSEGraph getCompositeGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSEGraph getConcurrentGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	public TSENode findTSNode(Entity entity) {
		String entityAbsolutePath =  entity.getFolder()+ entity.getName();
		TSENode node = null;
		Iterator iter = this.graphManager.getMainDisplayGraph().nodes().iterator();
		while (iter.hasNext()) {
			node = (TSENode) iter.next();
			if (node.getUserObject() != null &&
					node.getUserObject() instanceof EntityNodeData) {
				Entity cp = (Entity)((EntityNodeData) node.getUserObject()).getEntity().getUserObject();
				String nodeAbsolutePath = cp.getFolder()+cp.getName();
				if (entityAbsolutePath.equalsIgnoreCase(nodeAbsolutePath)) {
					return node;
				}
			}
		}
		return null;
	}
	
	protected TSEEdge createRelationship(TSENode from, T entity, int type, String linkLabel) {

		
		TSENode to = null;
		String guid = entity.getGUID();
		guid = (guid == null) ? entity.getFolder() + entity.getName() : guid;
		if (this.nodeMap.containsKey(guid)) {
			to = this.nodeMap.get(guid);
		}
		else {
			IFile file = this.getProject().getFile(entity.getFullPath());
			SharedElement element = null;
			element = CommonIndexUtils.getSharedElement(entity.getOwnerProjectName(), entity.getFullPath());
			if (!file.exists() ) {
				if (element == null) {
					to = createEntity(entity);
				} else {
					to = createSharedEntity((DesignerElement)element);
				}
			}
		
		}	
		
		TSEEdge edge = (TSEEdge) this.graphManager.addEdge(from, to);
		edge.addLabel().setName(linkLabel);
		entityEdgeList.add(edge);
		edgetype=type;
		this.setEdgeUI(edge, type);
		
		return edge;
	}
	
	@Override
	protected void registerListeners() {
		// TODO Auto-generated method stub
		super.registerListeners();
	}

	@Override
	public void unregisterListeners() {
		super.unregisterListeners();
		removePropertyChangeListener(prefStore);
		prefStore = null;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		editor = null;
		entityUI = null;
		metricEntityUI = null;
		inhEdgeUI = null;
		metricEntityUI = null;
		refEdgeUI = null;
		conEdgeUI = null;
		startColor = null;
		endColor = null;
		if (nodeMap != null) {
			nodeMap.clear();
			nodeMap = null;
		}
		this.entityEdgeList = null;
		this.entityNodes = null;
		this.changeListener = null;
	}
	
	
	@Override
	protected void disposeTools() {
		super.disposeTools();
		this.selectTool = null;
		if (this.entityCreateEdgeTool != null) {
			this.entityCreateEdgeTool.dispose();
			this.entityCreateEdgeTool = null;
		}
		if (this.entityCreateNodeTool != null) {
			this.entityCreateNodeTool.dispose();
			this.entityCreateNodeTool = null;
		}
	}

	public void setEdgeUI(TSEEdge edge, int type) {
		/*if (type == DrawingCanvas.INHERITANCE_LINK_TYPE ){
			edge.setUI((TSEObjectUI) this.inhcurEdgeUI.clone());
			edge.setUserObject(new Integer(DrawingCanvas.INHERITANCE_LINK_TYPE));
		}else if (type == DrawingCanvas.REFERENCE_LINK_TYPE) {
			edge.setUI((TSEObjectUI) this.refcurEdgeUI.clone());
			edge.setUserObject(new Integer(DrawingCanvas.REFERENCE_LINK_TYPE));
		}else if (type == DrawingCanvas.CONTAINMENT_LINK_TYPE) {
			edge.setUI((TSEObjectUI) this.concurEdgeUI.clone());

			edge.setUserObject(new Integer(DrawingCanvas.CONTAINMENT_LINK_TYPE));
		}	else {
			System.err.println("WARNING, unknown edge UI type");
		}*/
		
		IPreferenceStore prefStore1 = DiagrammingPlugin.getDefault().getPreferenceStore();
		String link_type=prefStore1.getString(DiagramPreferenceConstants.LINK_TYPE);
		boolean curved=link_type.equals(DiagramPreferenceConstants.LINK_TYPE_CURVED);
		if (type == DrawingCanvas.INHERITANCE_LINK_TYPE ) {
			if(curved)
			{
			edge.setUI((TSEObjectUI) this.inhcurEdgeUI.clone());
			}else{
			edge.setUI((TSEObjectUI) this.inhEdgeUI.clone());
			}
			edge.setUserObject(new Integer(DrawingCanvas.INHERITANCE_LINK_TYPE));
		} else if (type == DrawingCanvas.REFERENCE_LINK_TYPE) {
			if(curved){
			edge.setUI((TSEObjectUI) this.refcurEdgeUI.clone());
			}else{
				edge.setUI((TSEObjectUI) this.refEdgeUI.clone());
			}
			edge.setUserObject(new Integer(DrawingCanvas.REFERENCE_LINK_TYPE));
		}else if (type == DrawingCanvas.CONTAINMENT_LINK_TYPE) {
			if(curved){
			edge.setUI((TSEObjectUI) this.concurEdgeUI.clone());
			}else{
				edge.setUI((TSEObjectUI) this.conEdgeUI.clone());	
			}
			edge.setUserObject(new Integer(DrawingCanvas.CONTAINMENT_LINK_TYPE));
		}
		else {
			System.err.println("WARNING, unknown edge UI type");
		}
	}
	
	public HashMap<String, TSENode> getNodeMap() {
		return this.nodeMap;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#copyGraph()
	 */
	
	public void copyGraph() {
//		super.copyGraph();
//		editGraph(EDIT_TYPES.COPY, getEditor().getEditorSite(), this);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#cutGraph()
	 */
	
	public void cutGraph() {
//		super.cutGraph();
//		editGraph(EDIT_TYPES.CUT, getEditor().getEditorSite(), this);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#pasteGraph()
	 */
	
	//TODO - NPE coming in EntityNodeUI
	public void pasteGraph() {
//		super.pasteGraph();
//		editGraph(EDIT_TYPES.PASTE, getEditor().getEditorSite(), this);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#delete()
	 */
	@Override
	public void delete() {
//		editGraph(EDIT_TYPES.DELETE, getEditor().getEditorSite(), this);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#resetPaletteSelection()
	 */
	protected void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
	public EntityNodeUI getEntityUI() {
		return entityUI;
	}
	
	public EntityNodeUI getMetricEntityUI() {
		return metricEntityUI;
	}
	
	public List<TSENode> getEntityNodes() {
		return entityNodes;
	}
	@Override
	public void propertyChange(
			PropertyChangeEvent event){
		if(!this.getRefreshAction()) {
			super.propertyChange(event);
			int i=0;
			if(entityEdgeList!=null){
				Iterator<TSEEdge> iterator = entityEdgeList.iterator();
				while(iterator.hasNext()){

					this.setEdgeUI(entityEdgeList.get(i++), edgetype);
					iterator.next();


				}
			}
		}
		
	}
	protected TSENode createImplementation(Implementation implementation,
			boolean sharedEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*public void actionPerformed(ActionEvent event) {

		System.out.println("got an event!");

		TSToolManager toolManager = this.drawingCanvas.getToolManager();
		String command = event.getActionCommand();

		if (command.equals(COMMAND_DELETE)) {
			TSEDeleteSelectedCommand deleteSelected = new TSEDeleteSelectedCommand(this.drawingCanvas);
			this.drawingCanvas.getCommandManager().transmit(deleteSelected);
		}		

		 TSToolManager toolManager = this.drawingCanvas.getToolManager();
		 String command = event.getActionCommand();
		
		 if (command.equals(COMMAND_ADD_NODE))
		 {
		 // TSERoundRectNodeUI ui = new TSERoundRectNodeUI();
		 // ui.setFillColor(TSEColor.blue);
		 // toolManager.setCurrentNodeUI(ui);
		 // toolManager.switchTool(
		 // TSEditingToolHelper.getCreateNodeTool(toolManager));
		
		 TSECreateNodeTool createTool =
		 TSEditingToolHelper.getCreateNodeTool(toolManager);
		 createTool.setNodeCreator(new ConceptStateNodeCreator());
		 toolManager.setCurrentNodeUI(new ConceptStateNodeUI());
		 toolManager.switchTool(createTool);
					
		 // this.graphManager.getMainDisplayGraph().
		 }
		 else if (command.equals(COMMAND_ADD_IMAGENODE))
		 {
		 TSECreateNodeTool createTool =
		 TSEditingToolHelper.getCreateNodeTool(toolManager);
		 createTool.setNodeCreator(new ConceptImageNodeCreator());
		 TSEImageNodeUI ui = new TSEImageNodeUI();
		 // ui.setImage(new TSEImage("/icons/designerStartBig.png"));
		 ui.setImage(new TSEImage("/icons/db_add.png"));
		 toolManager.setCurrentNodeUI(ui);
		 toolManager.switchTool(createTool);
		 }
		 else if (command.equals(COMMAND_CONNECT))
		 {
		 TSEDefaultEdgeUI edgeUI = new TSEDefaultEdgeUI();
		 edgeUI.setAntiAliasingEnabled(true);
		 TSECreateEdgeTool createEdgeTool =
		 TSEditingToolHelper.getCreateEdgeTool(toolManager);
		 createEdgeTool.setEdgeUI(edgeUI);
					
		 toolManager.switchTool(createEdgeTool);
		 }
		 else if (command.equals(COMMAND_SELECT))
		 {
		 toolManager.switchTool(
		 TSViewingToolHelper.getSelectTool(toolManager));
		 }
		 else if (command.equals(COMMAND_INTZOOM))
		 {
		 toolManager.switchTool(
		 TSViewingToolHelper.getInteractiveZoomTool(toolManager));
		 }
		 else if (command.equals(COMMAND_ZOOM))
		 {
		 toolManager.switchTool(
		 TSViewingToolHelper.getZoomTool(toolManager));
		 }
		 else if (command.equals(COMMAND_PAN))
		 {
		 toolManager.switchTool(
		 TSViewingToolHelper.getPanTool(toolManager));
		 }
		 else if (command.equals(COMMAND_DELETE))
		 {
		 TSEDeleteSelectedCommand deleteSelected =
		 new TSEDeleteSelectedCommand(this.drawingCanvas);
		 this.drawingCanvas.getCommandManager().transmit(deleteSelected);
		 }
		 else if (command.equals(COMMAND_LAYOUT))
		 {
		 this.callLayout();
		 }
		 else if (command.equals(COMMAND_FIT_IN_WINDOW))
		 {
		 this.drawingCanvas.fitInCanvas(true);
		 }
		 else if (command.equals(COMMAND_DELETE))
		 {
		 TSEDeleteSelectedCommand deleteSelected =
		 new TSEDeleteSelectedCommand(this.drawingCanvas);
		 this.drawingCanvas.getCommandManager().transmit(deleteSelected);
		 }
	}*/
}
