package com.tibco.cep.bpmn.ui.graph.palette;

import java.net.MalformedURLException;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.palette.BPMNPaletteController;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.parts.SeparatorPaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.builder.TSObjectBuilder;


public class BpmnPaletteEntry extends PaletteEntryUI {
	
	private IGraphDrawing editor;
	@SuppressWarnings("unused")
	private LayoutManager layoutManager; 	
	
//	public enum Tool {
//	RULE_FUNCTION_GRAPH_TOOL, DECISION_TABLE_GRAPH_TOOL, DECISION_TREE_GRAPH_TOOL, SEQUENCE_FLOW, OR, XOR, COMPLEX, 
//	START_EVENT, END_EVENT, ERROR_EVENT, LANE, POOL, NOTE_TOOL
//	}
	
	/**
	 * @param page
	 * @param drawer
	 * @param id
	 * @param title
	 * @param tooltip
	 * @param image
	 * @param objectBuilder
	 * @param tool
	 * @param custom TODO
	 * @return
	 */
	public PaletteEntry createPaletteEntry(final IWorkbenchPage page,
											  PaletteDrawer drawer, 
											  String id, 
											  final String title, 
											  String tooltip,
											  String image, 
											  final TSObjectBuilder objectBuilder,
											  final Tool tool, boolean custom) {
		if(objectBuilder instanceof TSEdgeBuilder) {
			TSEdgeBuilder edgeBuilder = (TSEdgeBuilder) objectBuilder;
			return createPaletteEntry(page, 
								drawer, 
								id, 
								title, 
								tooltip, 
								image, 
								objectBuilder, 
								edgeBuilder.getEdgeUI(), 
								true, 
								tool, custom);
		} else if(objectBuilder instanceof TSNodeBuilder) {
			TSNodeBuilder nodeBuilder = (TSNodeBuilder) objectBuilder;
			return createPaletteEntry(page, 
					drawer, 
					id, 
					title, 
					tooltip, 
					image, 
					objectBuilder, 
					nodeBuilder.getNodeUI(), 
					false, 
					tool, custom);
		}
		return null;
	}
	
	@Override
    public void updatePaletteItem(Control control) {
		BpmnPaletteResourceUtil.setPaletteFont(control);
	}
	
	/**
	 * 
	 * @param window
	 * @param rootExpandBar
	 * @param drawer
	 * @param listener
	 */
	public void createDrawer(final IWorkbenchWindow window,
			ExpandBar rootExpandBar, PaletteDrawer drawer,
			PalettePresentationUpdater listener) {

		Composite parent = new Composite(rootExpandBar, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = layout.horizontalSpacing = layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);

		GridData data = new GridData(GridData.FILL_BOTH);
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		parent.setLayoutData(data);

		List<PaletteEntry> entries = drawer.getPaletteEntries();
		for (PaletteEntry paletteEntry : entries) {
			createPaletteEntry(window, drawer.isGlobal(), paletteEntry, parent,
					listener);
		}
		ExpandItem graphItem = new ExpandItem(rootExpandBar, SWT.NONE);
		graphItem.setText(drawer.getTitle());
		if (drawer.getImage() != null) {
//			if (drawer.isGlobal())
//				graphItem.setImage(StudioUIPlugin.getImageDescriptor(
//						drawer.getImage()).createImage());
//			else {
				Image image = BpmnPaletteResourceUtil.getPaletteImage(drawer.getImage());
				if(drawer.isCustom() && image == null) {
					IPath imageurl;
					try {
						imageurl = new Path(drawer.getImage());
						ImageDescriptor desc = ImageDescriptor.createFromURL(imageurl.toFile().toURI().toURL());
						graphItem.setImage(desc.createImage());
					} catch (MalformedURLException e) {
						BpmnUIPlugin.log(e);
					}
				} else {
					graphItem.setImage(image);
				}
//			}
		}
		graphItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		graphItem.setControl(parent);
		graphItem.setExpanded(true);
		graphItem.setData(drawer);
		
		BpmnPaletteResourceUtil.updatePaletteDrawerFont(rootExpandBar);

	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createGeneralGroup(final IWorkbenchPage page,
			Palette palette) {
		BpmnLayoutManager layoutManager = getLayoutManager(page);
		PaletteDrawer projectNodesDrawer = new PaletteDrawer(
				Messages.getString("palette.general"),
				Messages.getString("palette.general"),
				BpmnImages.GRAPH_PALETTE_NODES, palette, false);
		projectNodesDrawer.setGlobal(false);

		projectNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						projectNodesDrawer, 
						null,
						BpmnUIConstants.PALETTE_COMMON_TEXT_ANNOTATION,
						BpmnUIConstants.PALETTE_COMMON_TEXT_ANNOTATION_TOOLTIP,
						BpmnImages.PALETTE_NOTE,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_COMMON_TEXT_ANNOTATION, 
									"",
									"general.note",
									BpmnMetaModel.DOCUMENTATION),
//				new NoteNodeCreator(),
//						new NoteNodeUI(), 
//						false, 
						Tool.NONE, false));
		return projectNodesDrawer;
	}
	
	
	public PaletteDrawer createLinkGroup(IWorkbenchPage page, Palette palette) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public PaletteDrawer createNodeGroup(IWorkbenchPage page, Palette palette) {

		@SuppressWarnings("unused")
		TSEImage image = null; 
		BpmnLayoutManager layoutManager = getLayoutManager(page);

		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
				BpmnUIConstants.PALETTE_TASKS,
				BpmnUIConstants.PALETTE_TASKS_TOOLTIP,
				BpmnImages.ACTIVITIES_PALETTE_GRAPH,
				palette, false);
		graphDiagramNodesDrawer.setGlobal(false);

		image = new TSEImage("/icons/rule-function_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_RULE_FUNCTION,
						BpmnUIConstants.PALETTE_TASK_RULE_FUNCTION_TOOLTIP,
						BpmnImages.RULE_FUNCTION_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_RULE_FUNCTION_TASK,
									"",
									"activity.ruleFunction",
							BpmnMetaModel.RULE_FUNCTION_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_SCRIPT_TASK, ActivityTypes.ACTIVITY_RF), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));

		image = new TSEImage("/icons/table_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_DECISION_TABLE,
						BpmnUIConstants.PALETTE_TASK_DECISION_TABLE_TOOLTIP,
						BpmnImages.DECISION_TABLE_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_DECISION_TABLE_TASK, 
									"",
									"activity.businessrule",
									BpmnMetaModel.BUSINESS_RULE_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_DT_TASK, ActivityTypes.ACTIVITY_TABLE), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));

		image = new TSEImage("/icons/table_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_SEND_EVENT, 
						BpmnUIConstants.PALETTE_TASK_SEND_EVENT_TOOLTIP,
						BpmnImages.EVENT_SEND_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_SEND_EVENT_TASK, 
									"",
									"activity.sendEvent",
									BpmnMetaModel.SEND_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_SEND_EVENT_TASK, ActivityTypes.ACTIVITY_EVENT_SEND), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));

		image = new TSEImage("/icons/table_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_RECIEVE_EVENT, 
						BpmnUIConstants.PALETTE_TASK_RECIEVE_EVENT_TOOLTIP,
						BpmnImages.EVENT_RECEIVE_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_RECEIVE_EVENT_TASK,
									"",
									"activity.receiveEvent",
									BpmnMetaModel.RECEIVE_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_RECEIVE_EVENT_TASK, ActivityTypes.ACTIVITY_EVENT_RECEIVE), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));
		
		image = new TSEImage("/icons/table_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_MANUAL, 
						BpmnUIConstants.PALETTE_TASK_MANUAL_TOOLTIP,
						BpmnImages.MANUAL_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_MANUAL_TASK, 
									"",
									"activity.manual",
									BpmnMetaModel.MANUAL_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_MANUAL_TASK, ActivityTypes.ACTIVITY_MANUAL), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));

		image = new TSEImage("/icons/appicon48x48.gif");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_WEB_SERVICE,
						BpmnUIConstants.PALETTE_TASK_WEB_SERVICE_TOOLTIP,
						BpmnImages.WS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_WS_TASK, 
									"",
									"activity.webService",
									BpmnMetaModel.SERVICE_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_WS_TASK, ActivityTypes.ACTIVITY_WS), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));

		image = new TSEImage("/icons/table_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_TASK_INFERENCE,
						BpmnUIConstants.PALETTE_TASK_INFERENCE_TOOLTIP,
						BpmnImages.RULE_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_RULE_TASK, 
									"",
									"activity.inference",
									BpmnMetaModel.INFERENCE_TASK),
//						new TaskNodeCreator(BpmnUIConstants.NODE_RULE_TASK, ActivityTypes.ACTIVITY_RULE), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));

		image = new TSEImage("/icons/table_48x48.png");
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_ACTIVITY_SUB_PROCESS,
						BpmnUIConstants.PALETTE_ACTIVITY_SUB_PROCESS_TOOLTIP,
						BpmnImages.SUBPROCESS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_SUBPROCESS_ACTIVITY,
									"",
									"activity.subProcess",
									BpmnMetaModel.SUB_PROCESS),
//						new SubProcessNodeCreator(BpmnUIConstants.NODE_SUBPROCESS_ACTIVITY, ActivityTypes.ACTIVITY_SUBPROCESS), 
//						new ActivityBadgeNodeUI(image), 
//						false, 
						Tool.NONE, false));		
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_COMMON_SEQUENCE_FLOW_TOOLTIP,
						BpmnUIConstants.PALETTE_COMMON_SEQUENCE_FLOW_TOOLTIP,
						BpmnImages.SEQUENCE_FLOW_PALETTE_QUERY,
						BpmnGraphUIFactory.getInstance(layoutManager)
						.getEdgeUIFactory(
								BpmnUIConstants.NODE_SEQUENCE_FLOW,
								BpmnMetaModel.SEQUENCE_FLOW),
//						new ConnectionCreator(), 
//						new TSECurvedEdgeUI(), 
//						true, 
						Tool.NONE, false));

		return graphDiagramNodesDrawer;
	}

	public BpmnLayoutManager getLayoutManager(IWorkbenchPage page) {
		IEditorPart editorPart = page.getActiveEditor();

		return getLayoutManager(editorPart);
	}
	
	public BpmnLayoutManager getLayoutManager(IEditorSite site) {
		IEditorPart editorPart = (IEditorPart)site.getPart();
		return getLayoutManager(editorPart);
	}
	
	private BpmnLayoutManager getLayoutManager(IEditorPart editorPart){
		BpmnLayoutManager layoutManager = null;
		if(editorPart instanceof IGraphDrawing){
			editor = (IGraphDrawing)editorPart;
			Object diagramManager = editor.getDiagramManager();
			if((diagramManager != null) && (diagramManager instanceof BpmnDiagramManager)){
				layoutManager = (BpmnLayoutManager) ((BpmnDiagramManager) diagramManager).getLayoutManager();
			}
		}
		return layoutManager;
	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createGatewaysGroup(IWorkbenchPage page, Palette palette) {
		BpmnLayoutManager layoutManager = getLayoutManager(page);
		
		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
				BpmnUIConstants.PALETTE_GATEWAYS,
				BpmnUIConstants.PALETTE_GATEWAYS_TOOLTIP,
				BpmnImages.GATEWAY_PALETTE_GRAPH, palette, false);
		graphDiagramNodesDrawer.setGlobal(false);
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
							graphDiagramNodesDrawer, 
							null, 
							BpmnUIConstants.PALETTE_EXCLUSIVE_GATEWAY,
							BpmnUIConstants.PALETTE_EXCLUSIVE_GATEWAY_TOOLTIP,
							BpmnImages.XOR_PALETTE_GRAPH,
							BpmnGraphUIFactory.getInstance(layoutManager)
											  .getNodeUIFactory(
													  BpmnUIConstants.NODE_EXCLUSIVE_GATEWAY, 
													  "",
													  "gateway.exclusive",
													  BpmnMetaModel.EXCLUSIVE_GATEWAY),
//							new GatewayNodeCreator("OR", GatewayType.OR), 
//							new DecisionNodeUI(), 
//							false, 
							Tool.NONE, false));
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
							graphDiagramNodesDrawer, 
							null, 
							BpmnUIConstants.PALETTE_INCLUSIVE_GATEWAY,
							BpmnUIConstants.PALETTE_INCLUSIVE_GATEWAY_TOOLTIP,
							BpmnImages.OR_PALETTE_GRAPH,
							BpmnGraphUIFactory.getInstance(layoutManager)
											  .getNodeUIFactory(
													  BpmnUIConstants.NODE_INCLUSIVE_GATEWAY, 
													  "",
													  "gateway.inclusive",
													  BpmnMetaModel.INCLUSIVE_GATEWAY),
//							new GatewayNodeCreator("XOR", GatewayType.XOR), 
//							new DecisionNodeUI(), 
//							false, 
							Tool.NONE, false));
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
							graphDiagramNodesDrawer, 
							null, 
							BpmnUIConstants.PALETTE_PARALLEL_GATEWAY,
							BpmnUIConstants.PALETTE_PARALLEL_GATEWAY_TOOLTIP,
							BpmnImages.PARALLEL_PALETTE_GRAPH,
							BpmnGraphUIFactory.getInstance(layoutManager)
											  .getNodeUIFactory(
													  BpmnUIConstants.NODE_PARALLEL_GATEWAY, 
													  "",
													  "gateway.parallel",
													  BpmnMetaModel.PARALLEL_GATEWAY),
//							new GatewayNodeCreator("XOR", GatewayType.XOR), 
//							new DecisionNodeUI(), 
//							false, 
							Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
							graphDiagramNodesDrawer, 
							null, 
							BpmnUIConstants.PALETTE_COMPLEX_GATEWAY,
							BpmnUIConstants.PALETTE_COMPLEX_GATEWAY_TOOLTIP,
							BpmnImages.COMPLEX_PALETTE_GRAPH,
							BpmnGraphUIFactory.getInstance(layoutManager)
											  .getNodeUIFactory(
													  BpmnUIConstants.NODE_COMPLEX_GATEWAY, 
													  "",
													  "gateway.complex",
													  BpmnMetaModel.COMPLEX_GATEWAY),
//							new GatewayNodeCreator("XOR", GatewayType.XOR), 
//							new DecisionNodeUI(), 
//							false, 
							Tool.NONE, false));
		
		
		return graphDiagramNodesDrawer;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
//	public PaletteDrawer createEventsGroup(IWorkbenchPage page, Palette palette) {
//
//		BpmnLayoutManager layoutManager = getLayoutManager(page);
//		
//		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
//				BpmnUIConstants.PALETTE_EVENTS, BpmnUIConstants.PALETTE_EVENTS_TOOLTIP,
//				BpmnImages.EVENTS_PALETTE_GRAPH, palette);
//		graphDiagramNodesDrawer.setGlobal(false);
//
//		graphDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphDiagramNodesDrawer, null, BpmnUIConstants.PALETTE_START_EVENT, BpmnUIConstants.PALETTE_START_EVENT_TOOLTIP,
//				BpmnImages.START_EVENT_PALETTE_GRAPH,
//				BpmnGraphFactory.getInstance(layoutManager).getNodeFactory(BpmnMetaModel.START_EVENT),
////				new StartNodeCreator(BpmnUIConstants.NODE_START_EVENT,
////						BpmnMetaModel.getInstance().getEClass(BpmnMetaModel.START_EVENT), 
////						layoutManager), 
//				new InitialNodeUI(), false, Tool.NONE));
//
//		graphDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphDiagramNodesDrawer, null, BpmnUIConstants.PALETTE_END_EVENT,BpmnUIConstants.PALETTE_END_EVENT_TOOLTIP ,
//				BpmnImages.END_EVENT_PALETTE_GRAPH,
//				BpmnGraphFactory.getInstance(layoutManager).getNodeFactory(BpmnMetaModel.END_EVENT),
////				new EndNodeCreator(BpmnUIConstants.NODE_END_EVENT, 
////						BpmnMetaModel.getInstance().getEClass(BpmnMetaModel.END_EVENT),
////						layoutManager), 
//				new FinalStateNodeUI(), false, Tool.NONE));
//		
//
//		graphDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphDiagramNodesDrawer, null, BpmnUIConstants.PALETTE_TIMER_EVENT, BpmnUIConstants.PALETTE_TIMER_EVENT_TOOLTIP,
//				BpmnImages.EVENT_PALETTE_GRAPH,
//				new TaskNodeCreator(BpmnUIConstants.NODE_TIMER_EVENT, ActivityTypes.ACTIVITY_TIMER_EVENT), 
//				// new ActivityBadgeNodeCreator("Timer Event", null), 
//				new ErrorNodeUI(), false, Tool.NONE));
//
//		graphDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphDiagramNodesDrawer, null, BpmnUIConstants.PALETTE_ONMESSAGE_EVENT, BpmnUIConstants.PALETTE_ONMESSAGE_EVENT_TOOLTIP,
//				BpmnImages.EVENT_PALETTE_GRAPH,
//				new TaskNodeCreator(BpmnUIConstants.NODE_ONMESSAGE_EVENT, ActivityTypes.ACTIVITY_ONMESSAGE_EVENT), 
//				// new ActivityBadgeNodeCreator("On Message Event", null), 
//				new ErrorNodeUI(), false, Tool.NONE));
//
//		graphDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphDiagramNodesDrawer, null, "Error Event", "Add an Error Event",
//				BpmnImages.ERROR_EVENT_PALETTE_GRAPH, new ActivityBadgeNodeCreator("Error Event", null), 
//				new ErrorNodeUI(), false, Tool.NONE));
//
//		return graphDiagramNodesDrawer;
//
//	}
//	
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createStartEventsGroup(IWorkbenchPage page, Palette palette) {

		BpmnLayoutManager layoutManager = getLayoutManager(page);
		
		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
				BpmnUIConstants.PALETTE_START_EVENTS, 
				BpmnUIConstants.PALETTE_EVENTS_TOOLTIP,
				BpmnImages.START_EVENT_PALETTE_GRAPH, palette, false);
		graphDiagramNodesDrawer.setGlobal(false);
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_START_EVENT_NONE, 
						BpmnUIConstants.PALETTE_START_EVENT_NONE_TOOLTIP,
						BpmnImages.START_EVENT_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_START_EVENT_NONE, 
									"",
									"event.start.none",
									BpmnMetaModel.START_EVENT, 
									(Object[])null),
//									new InitialNodeUI(), 
//									false, 
						Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_START_EVENT_MESSAGE, 
						BpmnUIConstants.PALETTE_START_EVENT_MESSAGE_TOOLTIP,
						BpmnImages.START_EVENT_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_START_EVENT_MESSAGE,
									"",
									"event.start.message",
									BpmnMetaModel.START_EVENT, 
									BpmnMetaModel.MESSAGE_EVENT_DEFINITION),
//									new InitialNodeUI(), 
//									false, 
						Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_START_EVENT_TIMER,
						BpmnUIConstants.PALETTE_START_EVENT_TIMER_TOOLTIP ,
						BpmnImages.START_EVENT_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_START_EVENT_TIMER, 
									"",
									"event.start.timer",
									BpmnMetaModel.START_EVENT, 
									BpmnMetaModel.TIMER_EVENT_DEFINITION),
//						new InitialNodeUI(), 
//						false, 
						Tool.NONE, false));

		return graphDiagramNodesDrawer;

	}
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createEndEventsGroup(IWorkbenchPage page, Palette palette) {

		BpmnLayoutManager layoutManager = getLayoutManager(page);
		
		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
				BpmnUIConstants.PALETTE_END_EVENTS, 
				BpmnUIConstants.PALETTE_END_EVENTS_TOOLTIP,
				BpmnImages.END_EVENT_PALETTE_GRAPH, palette, false);
		graphDiagramNodesDrawer.setGlobal(false);
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_END_EVENT_NONE, 
						BpmnUIConstants.PALETTE_END_EVENT_NONE_TOOLTIP,
						BpmnImages.END_EVENT_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
						.getNodeUIFactory(
								BpmnUIConstants.NODE_END_EVENT_NONE,  
								"",
								"event.end.none",
								BpmnMetaModel.END_EVENT
								, (Object[])null),
//						new FinalStateNodeUI(), 
//						false, 
						Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_END_EVENT_MESSAGE, 
						BpmnUIConstants.PALETTE_END_EVENT_MESSAGE_TOOLTIP,
						BpmnImages.END_EVENT_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
						.getNodeUIFactory(
								BpmnUIConstants.NODE_END_EVENT_MESSAGE, 
								"",
								"event.end.message",
								BpmnMetaModel.END_EVENT
								, BpmnMetaModel.MESSAGE_EVENT_DEFINITION),
//						new FinalStateNodeUI(), 
//						false, 
						Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_END_EVENT_ERROR,
						BpmnUIConstants.PALETTE_END_EVENT_ERROR_TOOLTIP ,
						BpmnImages.END_EVENT_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
						.getNodeUIFactory(
								BpmnUIConstants.NODE_END_EVENT_ERROR, 
								"",
								"event.end.error"
								,BpmnMetaModel.END_EVENT
								,BpmnMetaModel.ERROR_EVENT_DEFINITION),
//						new FinalStateNodeUI(), 
//						false, 
						Tool.NONE, false));		

		return graphDiagramNodesDrawer;

	}
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createIntermediateEventsGroup(IWorkbenchPage page, Palette palette) {

		BpmnLayoutManager layoutManager = getLayoutManager(page);
		
		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
				BpmnUIConstants.PALETTE_INTERMEDIATE_EVENTS, 
				BpmnUIConstants.PALETTE_INTERMEDIATE_EVENTS_TOOLTIP,
				BpmnImages.EVENTS_PALETTE_GRAPH, 
				palette, false);
		graphDiagramNodesDrawer.setGlobal(false);
		// catch event
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_CATCH_INTERMEDIATE_EVENT_MESSAGE, 
						BpmnUIConstants.PALETTE_CATCH_INTERMEDIATE_EVENT_MESSAGE_TOOLTIP,
						BpmnImages.EVENTS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
								BpmnUIConstants.NODE_INTERMEDIATE_CATCH_MESSAGE, 
								"",
								"event.catch.message.intermediate",
								BpmnMetaModel.INTERMEDIATE_CATCH_EVENT, 
								BpmnMetaModel.MESSAGE_EVENT_DEFINITION),
//						new ErrorNodeUI(), 
//						false, 
						Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_CATCH_INTERMEDIATE_EVENT_TIMER,
						BpmnUIConstants.PALETTE_CATCH_INTERMEDIATE_EVENT_TIMER_TOOLTIP ,
						BpmnImages.EVENTS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
								BpmnUIConstants.NODE_INTERMEDIATE_CATCH_TIMER, 
								"",
								"event.catch.timer.intermediate",
								BpmnMetaModel.INTERMEDIATE_CATCH_EVENT, 
								BpmnMetaModel.TIMER_EVENT_DEFINITION),
//						new ErrorNodeUI(), 
//						false, 
						Tool.NONE, false));
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_CATCH_INTERMEDIATE_EVENT_ERROR,
						BpmnUIConstants.PALETTE_CATCH_INTERMEDIATE_EVENT_ERROR_TOOLTIP ,
						BpmnImages.EVENTS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
						.getNodeUIFactory(
								BpmnUIConstants.NODE_INTERMEDIATE_CATCH_ERROR, 
								"",
								"event.catch.error.intermediate",
								BpmnMetaModel.INTERMEDIATE_CATCH_EVENT,
								BpmnMetaModel.ERROR_EVENT_DEFINITION),
//						new ErrorNodeUI(), 
//						false, 
						Tool.NONE, false));
		
		// throw event
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_THROW_INTERMEDIATE_EVENT_NONE,
						BpmnUIConstants.PALETTE_THROW_INTERMEDIATE_EVENT_NONE_TOOLTIP,
						BpmnImages.EVENTS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
								BpmnUIConstants.NODE_INTERMEDIATE_THROW_MESSAGE, 
								"",
								"event.throw.intermediate",
								BpmnMetaModel.INTERMEDIATE_THROW_EVENT, 
								(Object[])null),
//						new ErrorNodeUI(), 
//						false, 
						Tool.NONE, false));	
		
		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_THROW_INTERMEDIATE_EVENT_MESSAGE,
						BpmnUIConstants.PALETTE_THROW_INTERMEDIATE_EVENT_MESSAGE_TOOLTIP,
						BpmnImages.EVENTS_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
								BpmnUIConstants.NODE_INTERMEDIATE_THROW_MESSAGE, 
								"",
								"event.throw.message.intermediate",
								BpmnMetaModel.INTERMEDIATE_THROW_EVENT, 
								BpmnMetaModel.MESSAGE_EVENT_DEFINITION),
//						new ErrorNodeUI(), 
//						false, 
						Tool.NONE, false));		

		return graphDiagramNodesDrawer;

	}
	
	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createSwimLanesGroup(IWorkbenchPage page, Palette palette) {

		BpmnLayoutManager layoutManager = getLayoutManager(page);
		
		PaletteDrawer graphDiagramNodesDrawer = new PaletteDrawer(
				BpmnUIConstants.PALETTE_SWIM_LANES, BpmnUIConstants.PALETTE_SWIMLANES_TOOLTIP,
				BpmnImages.SWIMLANE_PALETTE_GRAPH, palette, false);
		graphDiagramNodesDrawer.setGlobal(false);

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_LANE,
						BpmnUIConstants.PALETTE_LANE_TOOLTIP,
						BpmnImages.LANE_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
							.getNodeUIFactory(
									BpmnUIConstants.NODE_LANE,  
									"",
									"swimlane.lane",
									BpmnMetaModel.LANE),
//						new SubProcessNodeCreator(BpmnUIConstants.NODE_LANE,  ActivityTypes.ACTIVITY_LANE), 
//						new SubProcessNodeUI(), 
//						false, 
						Tool.NONE, false));

		graphDiagramNodesDrawer.addPaletteEntry(
				createPaletteEntry(page,
						graphDiagramNodesDrawer, 
						null, 
						BpmnUIConstants.PALETTE_POOL, 
						BpmnUIConstants.PALETTE_POOL_TOOLTIP,
						BpmnImages.POOL_PALETTE_GRAPH,
						BpmnGraphUIFactory.getInstance(layoutManager)
						.getNodeUIFactory(
								BpmnUIConstants.NODE_POOL, 
								"",
								"swimlane.pool",
								BpmnMetaModel.LANE, 
								BpmnMetaModel.PROCESS),
//						new PoolNodeCreator(BpmnUIConstants.NODE_POOL,  ActivityTypes.ACTIVITY_POOL, layoutManager), 
//						new SubProcessNodeUI(), 
//						false, 
						Tool.NONE, false));

		return graphDiagramNodesDrawer;
	}
	
	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param paletteEntry
	 * @param parent
	 * @param listener
	 */
	public void createPaletteEntry(final IWorkbenchWindow window,
			boolean isGlobalPalette, PaletteEntry paletteEntry,
			Composite parent, PalettePresentationUpdater listener) {
		if (paletteEntry == null) {
			System.err.println("Empty Palette Entry!");
			return;
		}
		else if (paletteEntry instanceof SeparatorPaletteEntry) {
			StudioPaletteUI.createSeparator(parent);
			return;
		}
		CLabel entry = createRectangleButton(window, isGlobalPalette, parent,
				paletteEntry.getTitle(), paletteEntry.getToolTip(),
				paletteEntry.getImage(), listener, paletteEntry.isCustom());
		entry.setData(paletteEntry);
		paletteEntry.setControl(entry);
	}

	
	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @return
	 */
	public CLabel createRectangleButton(final IWorkbenchWindow window,
			boolean isGlobalPalette, Composite buttonParent, String label,
			String toolTipText, String imageName,
			PalettePresentationUpdater listener, boolean custom) {
		Image image = null;
//		if (isGlobalPalette) {
//			if (imageName != null
//					&& StudioUIPlugin.getImageDescriptor(imageName) != null) {
//				image = (StudioUIPlugin.getImageDescriptor(imageName)
//						.createImage());
//			}
//		} else {
			image = BpmnPaletteResourceUtil.getPaletteImage(imageName);
			if(custom && image == null) {
				IPath imageurl;
				try {
					imageurl = new Path(imageName);
					ImageDescriptor desc = ImageDescriptor.createFromURL(imageurl.toFile().toURI().toURL());
					image = desc.createImage();
				} catch (MalformedURLException e) {
					BpmnUIPlugin.log(e);
				}
			}
//		}
		
		CLabel paletteClabel = createRectangleButton(window, isGlobalPalette,
				buttonParent, label, toolTipText, imageName, listener, image);
		BpmnPaletteResourceUtil.setPaletteFont(paletteClabel);
		return paletteClabel;
	}
	
	public static CLabel createRectangleButton(final IWorkbenchWindow window,
			boolean isGlobalPalette, Composite buttonParent, String label,
			String toolTipText, String imageName,
			PalettePresentationUpdater listener, Image image) {

		final CLabel button = new CLabel(buttonParent, SWT.NONE);
		button.setText(label);
		button.setFont(StudioPaletteUI.unselectedFont);
		button.setBackground(StudioPaletteUI.defaultColor);
		button.setForeground(StudioPaletteUI.textColor);
		if (isGlobalPalette) {
			if (imageName != null
					&& DiagrammingPlugin.getImageDescriptor(imageName) != null) {
				button.setImage(DiagrammingPlugin.getImageDescriptor(imageName)
						.createImage());
			}
		} else {
			button.setImage(image);
		}
		button.setToolTipText(toolTipText);
		button.addMouseListener(new BPMNPaletteController(window));
		button.addMouseTrackListener(listener);

		GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginRight = layout.marginWidth = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		button.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(data);
		return button;
	}

	
	/**
	 * 
	 * @param window
	 * @param paletteView
	 * @param rootExpandBar
	 * @param palette
	 * @param listener
	 */
	public void expandBar(final IWorkbenchWindow window,
			PaletteView paletteView, ExpandBar rootExpandBar, Palette palette,
			PalettePresentationUpdater listener) {
		for (PaletteDrawer drawer : palette.getPaletteDrawers()) {
			createDrawer(window, rootExpandBar, drawer, listener);
		}
	}

	/**
	 * 
	 * @param window
	 * @param paletteView
	 * @param palette
	 * @param rootExpandBar
	 * @param listener
	 */
	public void updateExpandBar(final IWorkbenchWindow window,
			PaletteView paletteView, Palette palette, ExpandBar rootExpandBar,
			PalettePresentationUpdater listener) {
		if (palette == null) {
			return;
		}
		ExpandItem[] items = rootExpandBar.getItems();
		for (ExpandItem item : items) {
			item.dispose();
		}
		Control[] children = rootExpandBar.getChildren();
		for (Control control : children) {
			control.dispose();
		}
		expandBar(window, paletteView, rootExpandBar, palette, listener);
	}
}
