package com.tibco.cep.studio.ui.editors.retegraph;

import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.CHANNEL;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.CONCEPT;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.DECISIONTABLE;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.DESTINATION;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.DOMAINMODEL;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.EDGE;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.EVENT;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.NODE;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.RULE;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.RULEFUNCTION;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.SCORECARD;
import static com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager.STATEMODEL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class ReteGraphDiagramGeneralPropertySection extends AbstractPropertySection /*implements PaintObjectListener, VerifyListener, DisposeListener*/ {
	
	protected ReteGraphDiagramPropertySheetPage propertySheetPage;
	protected TSEGraph tseGraph;
	protected TSENode tseNode;
	protected TSEdge tsedge;
	protected TSEObject tseObject;
	protected EObject eObject;
	protected List nodeList;
	
	protected IProject project;
	protected ReteGraphDiagramEditor editor;
	
	protected Text descriptionText;
    protected Composite composite;

    private StyledText summaryText;
    private String projectViewInfo;

    private static final Image nodes = EditorsUIPlugin.getDefault().getImage("icons/nodes.gif");
    private static final Image edges = EditorsUIPlugin.getDefault().getImage("icons/link.gif");
    
    private static final Image concepts = EditorsUIPlugin.getDefault().getImage("icons/concept.png");
    private static final Image events = EditorsUIPlugin.getDefault().getImage("icons/event.png");
    private static final Image rules = EditorsUIPlugin.getDefault().getImage("icons/rules.png");
    private static final Image rulefunctions = EditorsUIPlugin.getDefault().getImage("icons/rule-function.png");
    private static final Image statemodels = EditorsUIPlugin.getDefault().getImage("icons/state_machine.png");
//    private static final Image archives = EditorsUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
    private static final Image scoreCards = EditorsUIPlugin.getDefault().getImage("icons/scorecard.png");
    private static final Image channels = EditorsUIPlugin.getDefault().getImage("icons/channel_16x16.png");
    private static final Image destinations = EditorsUIPlugin.getDefault().getImage("icons/destination_16x16.png");
    private static final Image decisionTables =StudioUIPlugin.getDefault().getImage("icons/decisiontablerulefunctions_16x16.png");
    private static final Image domainModels =EditorsUIPlugin.getDefault().getImage("icons/domainModelView_16x15.png");
    
//    private  final Image[] images = new Image[] {concepts,events,rules,rulefunctions, statemodels,archives,scoreCards,channels,destinations,decisionTables};
    private HashMap< String, Image> imagesMap; 
    
    
    public Font declfont = new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL);
	
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
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		editor = (ReteGraphDiagramEditor) getPart();
		project = ((EntityDiagramEditorInput)editor.getEditorInput()).getProject();
		
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
    public Table summaryTable;
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.propertySheetPage = (ReteGraphDiagramPropertySheetPage) tabbedPropertySheetPage;
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(1, false));
//		composite.setLayout(new GridLayout(2, false));
//		getWidgetFactory().createLabel(composite, Messages.getString("project.diagram.general.property.section.suammry"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
//		summaryText = new StyledText(composite, SWT.READ_ONLY | SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
//		GridData gd = new GridData();
//		gd.widthHint = 800;
//		gd.heightHint = 100;
//		summaryText.setLayoutData(gd);
//		summaryText.addVerifyListener(this);
//		summaryText.addPaintObjectListener(this);
//		summaryText.addDisposeListener(this);
		
		summaryTable =getWidgetFactory().createTable(composite, SWT.BORDER | SWT.FULL_SELECTION);
		summaryTable.setLayout(new GridLayout());
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 400;
		
		summaryTable.setLayoutData(gd);
		summaryTable.setFont(declfont);
		
		TableColumn termColumn = new TableColumn(summaryTable, SWT.NULL);
		termColumn.setText(Messages.getString("project.view.summary.col.entity"));

		TableColumn aliasColumn = new TableColumn(summaryTable, SWT.NULL);
		aliasColumn.setText(Messages.getString("project.view.summary.col.definitions"));
		
		summaryTable.setLinesVisible(true);
		summaryTable.setHeaderVisible(true);
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(summaryTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		summaryTable.setLayout(autoTableLayout);
		
		imagesMap =  new HashMap<String, Image>();
		imagesMap.put(NODE,nodes);
		imagesMap.put(EDGE,  edges);
		imagesMap.put(CONCEPT,  concepts);
		imagesMap.put(EVENT,  events);
		imagesMap.put(RULE,  rules);
		imagesMap.put(RULEFUNCTION,  rulefunctions);
		imagesMap.put(STATEMODEL,  statemodels);
		imagesMap.put(SCORECARD,  scoreCards);
		imagesMap.put(CHANNEL, channels);
		imagesMap.put(DESTINATION,  destinations);
		imagesMap.put(DECISIONTABLE,  decisionTables);
		imagesMap.put(DOMAINMODEL, domainModels);
	}
 
	private void updateSummary() {
		HashMap<String, Integer> defMap = ((ProjectDiagramManager)editor.getDiagramManager()).getDefintionsMap();
		if(defMap != null){
			TableItem[] items = summaryTable.getItems();
			for (TableItem tableItem : items) {
				tableItem.dispose();
			}
			
			List<String> list = new ArrayList<String>();
			list.add(CONCEPT);
			list.add(STATEMODEL);
			list.add(EVENT);
			list.add(RULE);
			list.add(RULEFUNCTION);
			list.add(CHANNEL);
			list.add(DESTINATION);
			list.add(DECISIONTABLE);
			list.add(DOMAINMODEL);
			
			TableItem tableItem = null;
			for(String s: list){
				if(s.equals(NODE) || s.equals(EDGE))continue;
				tableItem = new TableItem(summaryTable, SWT.NONE);
				tableItem.setImage(imagesMap.get(s));
				tableItem.setText(0, s);
				tableItem.setText(1, new Integer(defMap.get(s)).toString());
			}		
			tableItem = new TableItem(summaryTable, SWT.NONE);
			tableItem.setImage(imagesMap.get(NODE));
			tableItem.setText(0, NODE);
			tableItem.setText(1, new Integer(defMap.get(NODE)).toString());
			
			tableItem = new TableItem(summaryTable, SWT.NONE);
			tableItem.setImage(imagesMap.get(EDGE));
			tableItem.setText(0, EDGE);
			tableItem.setText(1, new Integer(defMap.get(EDGE)).toString());	
		}
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(tseGraph != null){
//			projectViewInfo = ((ProjectDiagramManager)editor.getDiagramManager()).getProjectViewInfo();
//			summaryText.setText(projectViewInfo);
//			insert();
			updateSummary();
		}
	}


//	/* (non-Javadoc)
//	 * @see org.eclipse.swt.custom.PaintObjectListener#paintObject(org.eclipse.swt.custom.PaintObjectEvent)
//	 */
//	@Override
//	public void paintObject(PaintObjectEvent event) {
//		GC gc = event.gc;
//		if (event.style instanceof ImageStyleRange) {
//			ImageStyleRange imageStyleRange = (ImageStyleRange) event.style;
//			int x = event.x;
//			int y = event.y + event.ascent - imageStyleRange.metrics.ascent;
//			gc.drawImage(imageStyleRange.image, x, y);
//		}
//	}


//	@Override
//	public void verifyText(VerifyEvent e) {
//		StyleRange[] ranges = summaryText.getStyleRanges(e.start, (e.end - e.start), true);
//		for(StyleRange styleRange : ranges) {
//			if (styleRange instanceof ImageStyleRange) {
//				ImageStyleRange imageStyleRange = (ImageStyleRange) styleRange;
//				imageStyleRange.image.dispose();
//			}
//		}
//	}

//	private void insert() {
//		
//		int offset = projectViewInfo.indexOf("node");
//		addImage(nodes, offset);
//		summaryText.replaceTextRange(offset + 1, 3, "");
//		
//		offset = summaryText.getText().indexOf("edge");
//		addImage(edges, offset);
//		summaryText.replaceTextRange(offset + 1, 3, "");
//		
//		offset = summaryText.getText().indexOf("concept");
//		addImage(concepts, offset);
//		summaryText.replaceTextRange(offset + 1, 6, "");
//
//		offset = summaryText.getText().indexOf("event");
//		addImage(events, offset);
//		summaryText.replaceTextRange(offset + 1, 4, "");
//		
//		offset = summaryText.getText().indexOf("rule");
//		addImage(rules, offset);
//		summaryText.replaceTextRange(offset + 1, 3, "");
//		
//		offset = summaryText.getText().indexOf("rule function");
//		addImage(rulefunctions, offset);
//		summaryText.replaceTextRange(offset + 1, 12, "");
//		
//		offset = summaryText.getText().indexOf("state model");
//		addImage(statemodels, offset);
//		summaryText.replaceTextRange(offset + 1, 10, "");
//		
//		offset = summaryText.getText().indexOf("archive");
//		addImage(archives, offset);
//		summaryText.replaceTextRange(offset + 1, 6, "");
//		
//		offset = summaryText.getText().indexOf("scorecard");
//		addImage(scoreCards, offset);
//		summaryText.replaceTextRange(offset + 1, 8, "");
//		
//		offset = summaryText.getText().indexOf("channel");
//		addImage(channels, offset);
//		summaryText.replaceTextRange(offset + 1, 6, "");
//		
//		offset = summaryText.getText().indexOf("destination");
//		addImage(destinations, offset);
//		summaryText.replaceTextRange(offset + 1, 10, "");
//		
//		offset = summaryText.getText().indexOf("decision table");
//		addImage(decisionTables, offset);
//		summaryText.replaceTextRange(offset + 1, 13, "");
//	}
	
//	/**
//	 * @param image
//	 * @param offset
//	 */
//	private void addImage(Image image, int offset) {
//        StyleRange style = new ImageStyleRange(image);
//        style.start = offset;
//        style.length = 1;
//        Rectangle rect = image.getBounds();
//        style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
//        summaryText.setStyleRange(style);
//    }
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
//	 */
//	@Override
//	public void widgetDisposed(DisposeEvent e) {
//		for(StyleRange styleRange : summaryText.getStyleRanges()) {
//			if (styleRange instanceof ImageStyleRange) {
//				ImageStyleRange imageStyleRange = (ImageStyleRange) styleRange;
//				imageStyleRange.image.dispose();
//			}
//		}
//	}
//
//	
//	private static class ImageStyleRange extends StyleRange {
//		public Image image;
//		public ImageStyleRange(Image image) {
//			this.image = image;
//		}
//	}


}