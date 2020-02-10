package com.tibco.cep.studio.ui.diagrams;


import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.EVENT_GRID;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.EVENT_LINES;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.EVENT_NONE;
import static com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants.EVENT_POINTS;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.part.EditorPart;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
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
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.diagrams.tools.EventSelectTool;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.events.EventCreateNodeTool;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.util.preference.TSPreferenceData;


public class EventDiagramManager extends EntityDiagramManager<Event> implements IPropertyChangeListener{

	private EntityNodeUI timeEventUI;
	private EntityNodeUI simpleEventUI;
	protected EventSelectTool eventSelectTool;
	private boolean showAllProperties = false;
	private EventCreateNodeTool eventCreateNodeTool;
	private EventDiagramSelectionChangeListener eventSelectionListener;
	private DesignerProject studioProject;
	
	public final static TSEColor START_COLOR = new TSEColor(85, 141, 106);
	public final static TSEColor END_COLOR = new TSEColor(255, 255, 255);
	
	@Override
	protected void registerListeners() {

		super.registerListeners();
		addPropertyChangeListener(prefStore);
	}
	@Override
	protected void initUI() {
		super.initUI();
		this.timeEventUI = new EntityNodeUI();
		this.timeEventUI.setImage(this.getEntityTimeEventImage());
		this.timeEventUI.setGradient(this.getStartColor(), this.getEndColor());
		this.timeEventUI.setBorderColor(this.getStartColor());
		
		this.simpleEventUI = new EntityNodeUI();
		this.simpleEventUI.setImage(this.getEntitySimpleEventImage());
		this.simpleEventUI.setGradient(this.getStartColor(), this.getEndColor());
		this.simpleEventUI.setBorderColor(this.getStartColor());		
	}

	public EventDiagramManager(EditorPart editor) {
		super(editor);
	}
	
	private void localInit() {
		StudioModelManager mgr = StudioCorePlugin.getDesignerModelManager();
		this.studioProject = mgr.getIndex(this.getProject());
		
	}

	public SelectTool getSelectTool() {
		if (this.eventSelectTool == null) {
			this.eventSelectTool = new EventSelectTool(this);
		}
		return this.eventSelectTool;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getNodeTool()
	 */
	public CreateNodeTool getNodeTool(){
		if (this.eventCreateNodeTool == null) {
			this.eventCreateNodeTool = new EventCreateNodeTool(getProjectName(), this);
		}
		return this.eventCreateNodeTool;
	}
	
	
	
	
	public void delete() {
		EventSelectTool eventSelectTool = (EventSelectTool) getSelectTool();
		eventSelectTool.deleteViaKeyBoard();
	}
	
	
	
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.changeListener == null) {
			this.changeListener = new EventDiagramChangeListener(this);
		}
		return this.changeListener;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#setPreferences()
	 */
	@Override
	protected void setPreferences() {
		super.setPreferences();
		String gridType = this.prefStore.getString(StudioPreferenceConstants.EVENT_GRID);
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);
		if(gridType.equalsIgnoreCase(StudioPreferenceConstants.EVENT_NONE)){
			gridType(Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
		}else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.EVENT_LINES)){
			gridType(Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
		} else if(gridType.equalsIgnoreCase(StudioPreferenceConstants.EVENT_POINTS)){
			gridType(Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
		}
		swingTailor.setAutoHideScrollBars(false);
	}
	
	public EventDiagramManager(IProject project, IProgressMonitor monitor) {
		super(project, monitor);
	}

	public TSEColor getStartColor() {
//		if (this.startColor == null) {
//		this.startColor = new TSEColor(85, 141, 106);
//	}
//	
//	return this.startColor;
		return START_COLOR;
	}
	
	public TSEColor getEndColor() {
//		if (this.endColor == null) {
//			this.endColor = new TSEColor(255, 255, 255);
//		}
//		
//		return this.endColor;
		return END_COLOR;
	}
	
	public String getExtension() {
		return ".eventview";
	}
	
	protected TSEImage getEntityTimeEventImage() {
		return EntityNodeUI.TIME_EVENT_IMAGE;
	}
	
	protected TSEImage getEntitySimpleEventImage() {
		return EntityNodeUI.SIMPLE_EVENT_IMAGE;
	}
	
	public ELEMENT_TYPES getEntityType() {
		return ELEMENT_TYPES.SIMPLE_EVENT;
	}
	
	protected ELEMENT_TYPES[] getEntityTypes() {
		return new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT };
	}	
	
	protected String getEntityTypeName() {
		return "event";
	}

//	protected TSENode createEntity(Entity entity) {
//		return EventDiagramManager.createEntity(entity,
//			this.nodeMap, this.graphManager, this.isGeneratingModel);
//	}
	
//	public static TSENode createEntity(Entity entity,
//			HashMap nodeMap,
//			TSEGraphManager graphManager,
//			boolean isGeneratingModel) {
//	}
	
	// DIFF (Concept -> Event)
	protected TSENode createEntity(Event event) {
		
		if (nodeMap.containsKey(event.getGUID())) {
			return (TSENode) nodeMap.get(event.getGUID());
		}
		
		TSENode tsEvent = (TSENode) graphManager.getMainDisplayGraph().addNode();
		
		nodeMap.put(event.getGUID(), tsEvent);

		// DIFF
		String parentPath = event.getSuperEventPath();
		Event parent = null;
		if (parentPath != null && parentPath.length() > 0) {
			// DIFF
			parent = IndexUtils.getSimpleEvent(getProjectName(), parentPath);
		}

		if (parent != null && isGeneratingModel) {
			this.createRelationship(tsEvent,
					parent,
					DrawingCanvas.INHERITANCE_LINK_TYPE,
					"Inherits From");
		}

		this.populateTSNode(tsEvent, event);
		
		return tsEvent;
	}
	
	static public String createToolTip(String projectName, Event event) {
		StringBuilder tooltip = new StringBuilder();
		
		//"<p><b>Full path: </b>" + event.getFolder() + event.getName() + "</p>"
		tooltip.append("<p><b>Full path: </b>").
				append(event.getFullPath()).
				append("</p>");

		tooltip.append("<p><b>Name: </b>").
				       append(event.getName()).
				       append("</p>");
		// DIFF
		ELEMENT_TYPES eventType = IndexUtils.getElementType(event);
		if (event.getSuperEventPath() != null && eventType == ELEMENT_TYPES.SIMPLE_EVENT) {
			tooltip.append("<p><b>Parent: </b>").
			append(event.getSuperEventPath()).append("</p>");
		}

		if(event.getDescription() != null && !(event.getDescription().isEmpty())) {
			tooltip.append("<p><b>Description: </b>").
			append(event.getDescription()).
			append("</p>");
		}
		
		if (eventType == ELEMENT_TYPES.SIMPLE_EVENT) {
			tooltip.append("<p><b>Properties: </b>").
				append(event.getProperties().size()).
				append("</p>");
		}

		if (eventType == ELEMENT_TYPES.TIME_EVENT) {
			
			TimeEvent timeEvent = (TimeEvent)event;
			
			String intervalUnit = getUnitString(timeEvent.getIntervalUnit());
			
			int interval = 0;
			try {
				interval = GvUtil.getPossibleGVAsInt(projectName, timeEvent.getInterval());
			} catch (NumberFormatException nfe){}
			if (interval != 1) {
				intervalUnit += "s";
			}
				
			if(timeEvent.getScheduleType() == EVENT_SCHEDULE_TYPE.REPEAT ){
				tooltip.append("<p><b>Repeat every: </b>").
		        append(timeEvent.getInterval()).
		        append(" ").
		        append(intervalUnit).
		        append("</p>");
				
				tooltip.append("<p><b>Events/Interval: </b>").
				append(timeEvent.getTimeEventCount()).
				append("</p>");
				
			}	
		}
		// for simple events:
		else {
			String unit = getUnitString(event.getTtlUnits());
			
			int ttl = 0;
			try {
				ttl = GvUtil.getPossibleGVAsInt(projectName, event.getTtl());
			} catch (NumberFormatException nfe){}
			if (ttl != 1) {
				unit += "s";
			}
			
			tooltip.append("<p><b>Time To Live: </b>").
					append(event.getTtl()).
					append(" ").
					append(unit).
					append("</p>");
				 
			if (event instanceof SimpleEvent) {
				SimpleEvent simpleEvent = (SimpleEvent)event;
				String destinationName = simpleEvent.getDestinationName();
				if (destinationName != null) {
					tooltip.append("<p><b>Destination: </b>");
					tooltip.append(simpleEvent.getChannelURI());
					tooltip.append(".channel");
					tooltip.append("/");
					tooltip.append(destinationName);
					tooltip.append("</p>");
				} else {
					tooltip.append("<p><b>Destination: No default destination specified</b></p>");
				}
			}
			
			if (event.getPayload() != null) {
				tooltip.append("<p><b>Payload: </b>").
					append(event.getPayloadString()).
					append("</p>");
			}
		}
		
		return tooltip.toString();
	}
	
	
	static public String getUnitString(TIMEOUT_UNITS input) {
		String unit = "UNKNOWN";
		
		if (input == TIMEOUT_UNITS.DAYS) {
			unit = "day";
		}
		else if (input == TIMEOUT_UNITS.HOURS) {
			unit = "hour";
		}
		else if (input == TIMEOUT_UNITS.MILLISECONDS) {
			unit = "millisecond";
		}
		else if (input == TIMEOUT_UNITS.MINUTES) {
			unit = "minute";
		}
		else if (input == TIMEOUT_UNITS.SECONDS) {
			unit = "second";
		}
		//Commented out by Anand to fix BE-10395 - START - 01/20/2011		
//		else if (input == TIMEOUT_UNITS.WEEK_DAYS) {
//			unit = "weekday";
//		}
//		
//		else if (input == TIMEOUT_UNITS.WEEKS) {
//			unit = "week";
//		}
//		
//		else if (input == TIMEOUT_UNITS.MONTHS) {
//			unit = "month";
//		}
//		
//		else if (input == TIMEOUT_UNITS.YEARS) {
//			unit = "year";
//		}
		//Commented out by Anand to fix BE-10395 - END - 01/20/2011
		return unit;
	}
	

	public void populateTSNode(TSENode tsEvent, Event event) {
				
		StringBuffer tooltip = new StringBuffer(createToolTip(getProjectName(), event));
		EntityNodeData cndata = new EntityNodeData(tsEvent);
		
		// DIFF:
		Iterator<PropertyDefinition> propIter = event.getProperties().iterator();
		PropertyDefinition property;
		String name;
		
		while (propIter.hasNext()) {
			property = propIter.next();
			name = property.getName();
			
			switch (property.getType()) {
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
			
			if (event.getPayload() != null) {
				cndata.addAttribute("Payload",
					EntityNodeItem.ATTRIBUTE,
					event.getPayload());
			}
		}
		
		tsEvent.setTooltipText(tooltip.toString());		
		
		prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();
		showAllProperties = prefStore.getBoolean(StudioPreferenceConstants.EVENT_SHOW_ALL_PROPERTIES_IN_EVENT_NODE);
		//based on preference to show or not all properties of a node 
		if(showAllProperties){
			cndata.setExpanded(true);
		}else {
			cndata.setCollapsed(true);
		}
	
		cndata.setEntity(event.getName(), event);
		cndata.setLabel(event.getName());
		
		
		tsEvent.setName(event.getName());
		tsEvent.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		
		EntityNodeUI nodeUI = new EntityNodeUI();
		nodeUI.setGradient(this.getStartColor(), this.getEndColor());
		
		if (IndexUtils.getElementType(event) == ELEMENT_TYPES.TIME_EVENT) {
			// TODO: this is slow, should instantiate once
			nodeUI.setImage(EntityNodeUI.TIME_EVENT_IMAGE);
			tsEvent.setUI(((TSEObjectUI) this.timeEventUI.clone()));
		}
		else if (IndexUtils.getElementType(event) == ELEMENT_TYPES.SIMPLE_EVENT) {
			nodeUI.setImage(EntityNodeUI.SIMPLE_EVENT_IMAGE);
			tsEvent.setUI(((TSEObjectUI) this.simpleEventUI.clone()));
		}
		
		this.entityNodes.add(tsEvent);		
	}

	@Override
	protected TSEImage getEntityImage() {
		// TODO Auto-generated method stub
		return null;
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
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.eventSelectionListener == null) {
			this.eventSelectionListener = new EventDiagramSelectionChangeListener(this);
		}
		return this.eventSelectionListener;
	}
	@Override
	public void propertyChange(
			PropertyChangeEvent event){
		super.propertyChange(event);
		try{
			String property=event.getProperty();
			property = prefStore.getString(EVENT_GRID);
			if(property.equals(EVENT_NONE)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
			}else if(property.equals(EVENT_LINES)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
			}else if(property.equals(EVENT_POINTS)){
				gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
			}
			DiagramUtils.refreshDiagram((EventDiagramManager)(((EventDiagramEditor)editor).getDiagramManager()));
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		}
		catch(Exception e)
		{
			EditorsUIPlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}
		
	}
	public void populateTSNode(TSENode node, SharedElement entity) {
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ELEMENT_TYPES type=	entity.getElementType();
		if(type == ELEMENT_TYPES.SIMPLE_EVENT ){
			ui.setImage(EntityNodeUI.SIMPLE_EVENT_IMAGE);
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