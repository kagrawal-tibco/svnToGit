package com.tibco.cep.bpmn.ui.graph.palette;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI.Tool;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.builder.TSObjectBuilder;
import com.tomsawyer.interactive.tool.TSToolManager;

/**
 * @author pdhar
 *
 */
public class PaletteReader {
//
//	private String filename;
//	private File file;
	private IEditorSite site;
	private Palette palette;
	private List<PaletteDrawer> paletteSections;
	private BpmnPaletteEntry paletteUI;
	
	public PaletteReader(BpmnPaletteEntry paletteUI,
		Palette palette,
		IEditorSite site) {
		this.site = site;
		this.palette = palette;
		this.paletteUI = paletteUI;
	}
	
	IProject getProject() {
		IEditorPart part = (IEditorPart)site.getPart();
		return (IProject) part.getAdapter(IProject.class);
	}
	
	public void processPalettes() throws Exception {
		if (this.paletteSections == null) {
			this.paletteSections = new LinkedList<PaletteDrawer>();
		}
		else {
			this.paletteSections.clear();
		}
		BpmnPaletteModel model = PaletteLoader.load(getProject());
		List<BpmnPaletteGroup> toolSets = model.getBpmnPaletteGroups(false);
		for(BpmnPaletteGroup toolSet:toolSets) {
			List<BpmnPaletteGroupItem> items = toolSet.getPaletteItems(false);
			PaletteDrawer paletteSection = 
				createPaletteSection(
						toolSet.getTitle(), 
						toolSet.getTooltip(), 
						toolSet.getIcon(), 
						items, 
						!toolSet.isInternal());
			paletteSections.add(paletteSection);
		}
		
		addSelectionPaletteEntry();
	}
	
	private void addSelectionPaletteEntry(){
		Iterator<PaletteDrawer> iterator = paletteSections.iterator();
		PaletteDrawer generalPaletteDrawer = null;
		while (iterator.hasNext()) {
			PaletteDrawer paletteDrawer = (PaletteDrawer) iterator.next();
			if(paletteDrawer.getTitle().equalsIgnoreCase(Messages.getString("palette.general"))){
				generalPaletteDrawer = paletteDrawer;
				break;
			}
			
		}
		
		if(generalPaletteDrawer == null)
			generalPaletteDrawer=addGeneralSectionIfMissing();
		
		PaletteEntry paletteEntry = new PaletteEntry("Select",
				Messages.getString("PALLETTE_ENTRY_TOOL_SELECT_TITLE"),
				Messages.getString("PALLETTE_ENTRY_TOOL_SELECT_TITLE"),
				"com.tibco.cep.bpmn.ui.utils.images.select", generalPaletteDrawer, false) {
			
			@Override
			protected void stateChanged() {
				IEditorPart activeEditorPart = site.getPage().getActiveEditor();
				IGraphDrawing editor = null;;
				if (activeEditorPart instanceof IGraphDrawing) {
					editor = (IGraphDrawing) activeEditorPart;
				}
				if (editor != null) {
					Object diagramManager = editor.getDiagramManager();
					if (getState() == STATE_SELECTED) {
						((DiagramManager) diagramManager).setSelectedPaletteEntry(Messages.getString("PALLETTE_ENTRY_TOOL_SELECT_TITLE"));
					}
					
					
					DrawingCanvas canvas = (DrawingCanvas)(((DiagramManager) diagramManager).getDrawingCanvas());
					final TSToolManager toolManager = (TSToolManager) canvas.getToolManager();
					final TSEGraphManager graphManager = ((DiagramManager) diagramManager).getGraphManager();
					
					if (getState() == STATE_NOT_SELECTED) {
						toolManager.setActiveTool(null);
						graphManager.setNodeBuilder(null);
						return;
					}
					
					DiagramUtils.selectAction(site.getPage());
				}

			}
		};

			generalPaletteDrawer.addPaletteEntry(paletteEntry);
		
	}
	
	private PaletteDrawer addGeneralSectionIfMissing(){
		 PaletteDrawer generalPaletteDrawer = null;

		 if(generalPaletteDrawer == null){

             List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();

             generalPaletteDrawer = 

                          createPaletteSection(

                                        Messages.getString("palette.general"), 

                                        Messages.getString("palette.general"), 

                                        "com.tibco.cep.bpmn.ui.utils.images.nodes", 

                                        items, 

                                        true);
                 paletteSections.add(0,generalPaletteDrawer);

      }
		 return generalPaletteDrawer;
		
	}
	
	private PaletteDrawer createPaletteSection(
			String title, String tooltip, String icon, List<BpmnPaletteGroupItem> items, boolean custom) {

		PaletteDrawer paletteSection = new PaletteDrawer(title, tooltip, icon, this.palette, custom);
		paletteSection.setGlobal(false);

		for (BpmnPaletteGroupItem item : items) {
			
			BpmnCommonPaletteGroupItemType itemType = item.getItemType();
			switch(itemType.getType()) {
			case BpmnCommonPaletteGroupItemType.EMF_TYPE:
				BpmnCommonPaletteGroupEmfItemType emfType = (BpmnCommonPaletteGroupEmfItemType)itemType;
				{
					ExpandedName classSpec = emfType.getEmfType();
					ExpandedName extClassSpec = emfType.getExtendedType();
					BpmnLayoutManager layoutManager = this.paletteUI.getLayoutManager(this.site.getPage());
					BpmnGraphUIFactory uiFactory = BpmnGraphUIFactory.getInstance(layoutManager);
					EClass modelType = BpmnMetaModel.getInstance().getEClass(classSpec);
					TSObjectBuilder graphObjFactory;
					if(modelType == null)
						break;
					if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(modelType)||
							BpmnModelClass.ASSOCIATION.isSuperTypeOf(modelType)) {
						 graphObjFactory = uiFactory.getEdgeUIFactory(
								item.getTitle(),
								classSpec,
								extClassSpec);

					} else {
						if (extClassSpec == null)
							graphObjFactory = uiFactory.getNodeUIFactory(
									item.getTitle(),
									item.getAttachedResource(),
									item.getId(), 
									classSpec);
						else
							graphObjFactory = uiFactory.getNodeUIFactory(
									item.getTitle(),
									item.getAttachedResource(),
									item.getId(), 
									classSpec, 
									extClassSpec);
					}
					PaletteEntry paletteEntry = this.paletteUI.createPaletteEntry(
							this.site.getPage(),
							paletteSection, 
							null,
							item.getTitle(),
							item.getTooltip(),
							item.getIcon(),
							graphObjFactory, 
							Tool.NONE, 
							!item.isInternal());
					paletteSection.addPaletteEntry(paletteEntry	);
				}
				break;
			case BpmnCommonPaletteGroupItemType.JAVA_TYPE:
				break;
			case BpmnCommonPaletteGroupItemType.MODEL_TYPE:
				break;
			}
		}

		return paletteSection;
	}

	

	public List<PaletteDrawer> getPaletteSections() {
		return this.paletteSections;
	}
	
	

}
