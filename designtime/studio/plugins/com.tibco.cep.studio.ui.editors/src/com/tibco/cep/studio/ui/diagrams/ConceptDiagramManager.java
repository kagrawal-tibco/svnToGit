package com.tibco.cep.studio.ui.diagrams;


import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.CONCEPT_GRID;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.CONCEPT_LINES;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.CONCEPT_NONE;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.CONCEPT_POINTS;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.eclipse.ui.part.EditorPart;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.tools.ConceptCreateEdgeTool;
import com.tibco.cep.studio.ui.diagrams.tools.ConceptCreateNodeTool;
import com.tibco.cep.studio.ui.diagrams.tools.ConceptReconnectEdgeTool;
import com.tibco.cep.studio.ui.diagrams.tools.ConceptSelectTool;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.interactive.command.TSCommandInterface;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;
import com.tomsawyer.interactive.command.editing.TSEDeleteSelectedCommand;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.util.preference.TSPreferenceData;

/**
 * 
 * @author ggrigore
 *
 */
public class ConceptDiagramManager extends EntityDiagramManager<Concept> implements IPropertyChangeListener{

	public final static TSEColor START_COLOR = new TSEColor(255, 220, 81);
	public final static TSEColor END_COLOR = new TSEColor(255, 255, 255);
	private boolean showAllProperties = false;
	protected ConceptSelectTool conceptSelectTool;
	protected ConceptCreateNodeTool conceptCreateNodeTool;
	protected ConceptCreateEdgeTool conceptCreateEdgeTool;
	protected ConceptReconnectEdgeTool conceptReconnectEdgeTool;
	private ConceptDiagramSelectionChangeListener conceptSelectionListener;
	protected IProject project;
	protected DesignerProject studioProject;

	@Override
	protected void registerListeners() {

		super.registerListeners();
		addPropertyChangeListener(prefStore);
	}
	public ConceptDiagramManager(EditorPart editor, IProject project) {
		super(editor);
		this.project = project;
	}

	public ConceptDiagramManager(IProject project, IProgressMonitor monitor) {
		super(project, monitor);
		this.project = project;
	}

	private void localInit() {
		StudioModelManager mgr = StudioCorePlugin.getDesignerModelManager();
		this.studioProject = mgr.getIndex(this.getProject());
		
	}
	public SelectTool getSelectTool() {
		if (this.conceptSelectTool == null) {
			this.conceptSelectTool = new ConceptSelectTool(this);
		}
		return this.conceptSelectTool;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getNodeTool()
	 */
	public CreateNodeTool getNodeTool(){
		if (this.conceptCreateNodeTool == null) {
			this.conceptCreateNodeTool = new ConceptCreateNodeTool(getProjectName(), this);
		}
		return this.conceptCreateNodeTool;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#setPreferences()
	 */
	@Override
	protected void setPreferences() {
		super.setPreferences();
		String gridType = this.prefStore.getString(StudioPreferenceConstants.CONCEPT_GRID);
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);
		if(gridType.equalsIgnoreCase(StudioPreferenceConstants.CONCEPT_NONE)){
			gridType(Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
		}else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.CONCEPT_LINES)){
			gridType(Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
		} else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.CONCEPT_POINTS)){
			gridType(Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
		}
		swingTailor.setAutoHideScrollBars(false);
	}

	public TSEColor getStartColor() {
		//		if (this.startColor == null) {
		//			this.startColor = new TSEColor(255, 220, 81);
		//		}
		//		return this.startColor;
		return START_COLOR;
	}

	public TSCommandInterface undo() {

		TSCommandInterface command = super.undo();
		if(!(command instanceof TSEAddNodeCommand))
			resourceUndo();
		return command;
	}

	public TSCommandInterface redo() {
		TSCommandInterface command = super.redo();
		if(!(command instanceof TSEDeleteSelectedCommand))
			resourceRedo();
		return command;
	}
	public void resourceUndo()
	{
		IWorkbenchOperationSupport operationSupport = PlatformUI.getWorkbench().getOperationSupport();
		IOperationHistory operationHistory = operationSupport.getOperationHistory();
		//IStatus status = operationHistory.undo(undoableOperation, null, null);
		IUndoContext context=operationSupport.getUndoContext();
		IStatus status = null;
		try {
			status = operationHistory.undo(context,null, null);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!status.isOK()) {
			System.out.println("Error");
		}
	}
	public void resourceRedo()
	{
		IWorkbenchOperationSupport operationSupport = PlatformUI.getWorkbench().getOperationSupport();
		IOperationHistory operationHistory = operationSupport.getOperationHistory();
		//IStatus status = operationHistory.undo(undoableOperation, null, null);
		IUndoContext context=operationSupport.getUndoContext();
		IStatus status = null;
		try {
			status = operationHistory.redo(context,null, null);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!status.isOK()) {
			System.out.println("Error");
		}
	}
	public TSEColor getEndColor() {
		//		if (this.endColor == null) {
		//			this.endColor = new TSEColor(255, 255, 255);
		//		}
		//		return this.endColor;
		return END_COLOR;
	}

	public String getExtension() {
		return ".conceptview";
	}

	public TSEImage getEntityImage() {
		return EntityNodeUI.CONCEPT_IMAGE;
	}


	public ELEMENT_TYPES getEntityType() {
		return ELEMENT_TYPES.CONCEPT;
	}

	protected ELEMENT_TYPES[] getEntityTypes() {
		return new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT };
	}

	protected String getEntityTypeName() {
		return "concept";
	}

	protected TSENode createEntity(Concept concept) {

		if (this.nodeMap.containsKey(concept.getGUID())) {
			return (TSENode) this.nodeMap.get(concept.getGUID());
		}

		TSENode tsConcept = (TSENode) this.graphManager.getMainDisplayGraph().addNode();

		this.nodeMap.put(concept.getGUID(), tsConcept);

		String parentPath = concept.getSuperConceptPath();
		Concept parent = null;
		if (parentPath != null && parentPath.length() > 0) {
			parent = IndexUtils.getConcept(getProjectName(), parentPath);
		}

		if (parent != null && this.isGeneratingModel) {
			this.createRelationship(tsConcept, parent, DrawingCanvas.INHERITANCE_LINK_TYPE, "Inherits From");
		}

		this.populateTSNode(tsConcept, concept);

		return tsConcept;
	}

	public void save() {
		super.save();
	}	

	public static String createToolTip(Concept concept) {

		StringBuffer tooltip = new StringBuffer();
		tooltip.append("<p><b>Full path: </b>");
		tooltip.append(concept.getFullPath());
		tooltip.append("</p>");
		tooltip.append("<p><b>Name: </b>");
		tooltip.append(concept.getName());
		tooltip.append("</p>");
		if ((concept.getSuperConceptPath() != null) && (concept.getSuperConceptPath() != "")) {
			tooltip.append("<p><b>Parent: </b>");
			tooltip.append(concept.getSuperConceptPath());
			tooltip.append("</p>");
		}
		if ((concept.getDescription() != null) && (concept.getDescription() != "")) {
			tooltip.append("<p><b>Description: </b>");
			tooltip.append(concept.getDescription());
			tooltip.append("</p>");
		}
		tooltip.append("<p><b>Properties: </b>");
		List<PropertyDefinition> properties = concept.getProperties();
		tooltip.append(properties.size());
		tooltip.append("</p>");

		return tooltip.toString();
	}


	public void populateTSNode(TSENode tsConcept, Concept concept) {

		try{
			EntityNodeData cndata;
			{
				StringBuffer tooltip = new StringBuffer(createToolTip(concept));
				cndata = new EntityNodeData(tsConcept);

				Iterator<PropertyDefinition> propIter = concept.getProperties().iterator();
				PropertyDefinition property;
				String name;

				while (propIter.hasNext()) {
					property = propIter.next();
					name = property.getName();

					if (property.isArray()) {
						name += "[ ]";
					}

					if (property.getHistorySize() > 0) {
						name += " (" + property.getHistorySize() + ")";
					}
					switch (property.getType()) {
					case CONCEPT_REFERENCE:
						cndata.addAttribute(name,
								EntityNodeItem.CONCEPTREF_ATTRIBUTE,
								property);
						if (this.isGeneratingModel) {
							this.createRelationship(tsConcept,
									IndexUtils.getConcept(getProjectName(),
											property.getConceptTypePath()),
											DrawingCanvas.REFERENCE_LINK_TYPE,
											property.getName());
						}
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : reference</p>");
						break;
					case CONCEPT:
						cndata.addAttribute(name,
								EntityNodeItem.CONCEPTCON_ATTRIBUTE,
								property);
						if (this.isGeneratingModel) {
							this.createRelationship(tsConcept,
									IndexUtils.getConcept(getProjectName(),
											property.getConceptTypePath()),
											DrawingCanvas.CONTAINMENT_LINK_TYPE,
											property.getName());
						}
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : containment</p>");
						break;
					case DATE_TIME:
						cndata.addAttribute(name,
								EntityNodeItem.DATETIME_ATTRIBUTE,
								property);
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : DateTime</p>");
						break;				
					case BOOLEAN:
						cndata.addAttribute(name,
								EntityNodeItem.BOOLEAN_ATTRIBUTE,
								property);
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : Boolean</p>");
						break;
					case DOUBLE:
						cndata.addAttribute(name,
								EntityNodeItem.DOUBLE_ATTRIBUTE,
								property);
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : Double</p>");
						break;
					case LONG:
						cndata.addAttribute(name,
								EntityNodeItem.LONG_ATTRIBUTE,
								property);
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : Long</p>");
						break;
					case INTEGER:
						cndata.addAttribute(name,
								EntityNodeItem.INTEGER_ATTRIBUTE,
								property);
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : Integer</p>");
						break;
					case STRING:
						cndata.addAttribute(name,
								EntityNodeItem.STRING_ATTRIBUTE,
								property);
						tooltip.append("<p>"); 
						tooltip.append(name);
						tooltip.append(" : String</p>");
						break;
					default:
						System.err.println("WARNING: unknown property type!");
					}
				}

				tsConcept.setTooltipText(tooltip.toString());		

				prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
				showAllProperties = prefStore.getBoolean(StudioPreferenceConstants.CONCEPT_SHOW_ALL_PROPERTIES_IN_CONCEPT_NODE);
				//based on preference to show or not all properties of a node 
				if(showAllProperties){
					cndata.setExpanded(true);
				}else {
					cndata.setCollapsed(true);
				}
				cndata.setEntity(concept.getName(), concept);
				cndata.setLabel(concept.getName());
			}
			tsConcept.setName(concept.getName());
			tsConcept.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
			tsConcept.setUI((TSEObjectUI) this.entityUI.clone());

			this.entityNodes.add(tsConcept);		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void populateTSNode(TSENode node, SharedElement entity) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ELEMENT_TYPES type=	entity.getElementType();
		if(type == ELEMENT_TYPES.CONCEPT ){
			ui.setImage(EntityNodeUI.CONCEPT_IMAGE);
		} 
		ui.setBorderDrawn(true);
		node.setUI(ui);

		String name = null;
		@SuppressWarnings("unused")
		String guid = null;
		StringBuffer tooltip = null;
		name = entity.getName();
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);

		// TODO: TSLabelBuilder builder = this.graphManager.getLabelBuilder();

		
			TSENodeLabel label = (TSENodeLabel) node.addLabel();
			label.setName(name);
			if((getLayoutManager() != null) && (label != null)){
				getLayoutManager().setNodeLabelOptions(label);
			}
		node.setName(name);
		
		node.setUserObject(entity);

		// TODO: this is suspect!
		// this.nodeMap.put(guid, node);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.diagrams.EntityDiagramManager#openModel()
	 */
	@Override
	public void openModel() {
		this.localInit();
		super.openModel();
		this.getLayoutManager().callBatchGlobalLayout();
	}
	@Override
	public void propertyChange(
			PropertyChangeEvent event){
		super.propertyChange(event);
		try{
			String property=event.getProperty();
			property = prefStore.getString(CONCEPT_GRID);
			if(property.equals(CONCEPT_NONE)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
			}else if(property.equals(CONCEPT_LINES)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
			}else if(property.equals(CONCEPT_POINTS)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
			}
			DiagramUtils.refreshDiagram((ConceptDiagramManager)(((ConceptDiagramEditor)editor).getDiagramManager()));
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		}
		catch(Exception e)
		{
			EditorsUIPlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}

	}

	public void delete() {
		ConceptSelectTool conceptSelectTool = (ConceptSelectTool) getSelectTool();
		conceptSelectTool.deleteViaKeyBoard();
	}

	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.changeListener == null) {
			this.changeListener = new ConceptDiagramChangeListener(this);
		}
		return this.changeListener;
	}


	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.conceptSelectionListener == null) {
			this.conceptSelectionListener = new ConceptDiagramSelectionChangeListener(this);
		}
		return this.conceptSelectionListener;
	}


	public ConceptCreateEdgeTool getEdgeTool() {
		if(this.conceptCreateEdgeTool == null) {
			this.conceptCreateEdgeTool = new ConceptCreateEdgeTool(this, true);
		}
		return this.conceptCreateEdgeTool;
	}

	public ConceptReconnectEdgeTool getReconnectEdgeTool() {
		if(this.conceptReconnectEdgeTool == null) {
			this.conceptReconnectEdgeTool = new ConceptReconnectEdgeTool(this);
		}
		return this.conceptReconnectEdgeTool;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	@Override
	protected void disposeTools() {
		if (this.conceptCreateEdgeTool != null) {
			this.conceptCreateEdgeTool.dispose();
			this.conceptCreateEdgeTool = null;
		}
		if (this.conceptCreateNodeTool != null) {
			this.conceptCreateNodeTool.dispose();
			this.conceptCreateNodeTool = null;
		}

		if (this.conceptReconnectEdgeTool != null) {
			this.conceptReconnectEdgeTool.dispose();
			this.conceptReconnectEdgeTool = null;
		}
		if (this.conceptSelectTool != null) {
			this.conceptSelectTool.dispose();
			this.conceptSelectTool = null;
		}
		super.disposeTools();
	}
	@Override
	protected TSENode createSharedEntity(DesignerElement e) {
		String entityGuid = sharedentityMap.get(e.toString());
		if (nodeMap.containsKey(entityGuid)) {
			return nodeMap.get(entityGuid);
		}
		TSENode tsEvent = null;
		tsEvent = (TSENode) graphManager.getMainDisplayGraph().addNode();
		String newGUID = GUIDGenerator.getGUID();
		this.nodeMap.put(newGUID, tsEvent);
		this.sharedentityMap.put(e.toString(),newGUID);
		this.populateTSNode(tsEvent,(SharedElement)e);
		return tsEvent;
	}


}