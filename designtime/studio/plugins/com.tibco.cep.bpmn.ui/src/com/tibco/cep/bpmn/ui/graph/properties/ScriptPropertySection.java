package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setupSourceViewerDecorationSupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDropTarget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * 
 * @author majha
 *
 */
public class ScriptPropertySection extends AbstractBpmnPropertySection {

	protected Composite composite;
	private MDSashForm sashForm;
	private Section declarations;
	private Composite scripClient;
	private Table declarationsTable;
	private Section actions;
	protected SourceViewer actionsSourceViewer;
	
	public Font font = new Font(Display.getDefault(), "Courier New", 10, SWT.NULL);
	public Font declfont = new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL);
	protected boolean refresh;
	protected Document condDocument;

	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		super.setInput(part, selection);
		updateDeclarations();
	}
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		composite = parent;
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		parent.setLayout(layout);

		//For fixed Declaration Section.. No Overlapping Section 
		createDeclarationsPart(parent);
		
		sashForm = new MDSashForm(parent, SWT.VERTICAL);
		sashForm.setData("form", composite);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		sashForm.setLayoutData(gd);
		parent.setLayoutData(gd);
		
		createSections(sashForm);
		
		hookResizeListener();
	}
	
	/**
	 * @param parent
	 */
	protected void createDeclarationsPart(Composite parent) {
		
		declarations = getWidgetFactory().createSection(parent, Section.TITLE_BAR| Section.EXPANDED /*|Section.TWISTIE*/);
		declarations.setActiveToggleColor(getWidgetFactory().getHyperlinkGroup().getActiveForeground());
		declarations.setToggleColor(getWidgetFactory().getColors().getColor(IFormColors.SEPARATOR));
		declarations.setText(com.tibco.cep.studio.ui.editors.utils.Messages.getString("rule.declarations"));
		
		scripClient = getWidgetFactory().createComposite(declarations, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		scripClient.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		declarations.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
//        gd.heightHint = getDeclarationHeight();
		scripClient.setLayoutData(gd);
		getWidgetFactory().paintBordersFor(scripClient);
		declarations.setClient(scripClient);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		declarationsTable = new Table(scripClient, SWT.BORDER | SWT.FULL_SELECTION);
		declarationsTable.setLayout(new GridLayout());
		gd.heightHint = 30;
		gd.widthHint = 100;
		declarationsTable.setLayoutData(gd);
//		declarationsTable.setFont(declfont);
		
		TableColumn termColumn = new TableColumn(declarationsTable, SWT.NULL);
		termColumn.setText(Messages.getString("rule.declaration.col.term"));
//		termColumn.setWidth(429);

		TableColumn aliasColumn = new TableColumn(declarationsTable, SWT.NULL);
		aliasColumn.setText(Messages.getString("rule.declaration.col.alias"));
//		aliasColumn.setWidth(430);
		
		declarationsTable.setLinesVisible(true);
		declarationsTable.setHeaderVisible(true);
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(declarationsTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		declarationsTable.setLayout(autoTableLayout);
		
	}
	
	protected void createSections(SashForm sashForm) {
		createActionsPart(sashForm);
		sashForm.setWeights(new int[] { 70 } );
	}
	
	/**
	 * @param parent
	 */
	protected void createActionsPart(Composite parent) {
		actions = getWidgetFactory().createSection(parent, Section.TITLE_BAR| Section.EXPANDED /*| Section.TWISTIE*/);
		actions.setActiveToggleColor(getWidgetFactory().getHyperlinkGroup().getActiveForeground());
		actions.setToggleColor(getWidgetFactory().getColors().getColor(IFormColors.SEPARATOR));
		actions.setText(com.tibco.cep.studio.ui.editors.utils.Messages.getString("rule.actions"));
		actions.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite client = getWidgetFactory().createComposite(actions, SWT.WRAP | SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing=0;
		layout.verticalSpacing=0;
		layout.numColumns =1;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		client.setLayout(layout);
		GridData gd1 = new GridData(GridData.FILL_BOTH);
		gd1.grabExcessVerticalSpace = true;
		client.setLayoutData(gd1);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		getWidgetFactory().paintBordersFor(client);
		actions.setClient(client);
		
		DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
		EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
		OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);
		
		actionsSourceViewer = new SourceViewer(client, new VerticalRuler(12, access), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		
		actionsSourceViewer.getControl().setLayoutData(gd);
		actionsSourceViewer.getTextWidget().setFont(font);
		actionsSourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		condDocument = new Document("");
		
		actionsSourceViewer.setDocument(condDocument, new RulesAnnotationModel());
		
		setupSourceViewerDecorationSupport(actionsSourceViewer, ruler, access, sharedColors);
		
		if(fEditor != null && fEditor.isEnabled()){
			setDropTarget(actionsSourceViewer, null, null);
		}
	}
	
	
	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		BpmnGraphUtils.fireUpdate(updateList,fTSENode ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
	}
	
	/**
	 * Sash form class
	 * @author sasahoo
	 *
	 */
	protected class MDSashForm extends SashForm {
		ArrayList<Sash> sashes = new ArrayList<Sash>();
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
    
	public void updateDeclarations() {
//		TableItem[] items = declarationsTable.getItems();
//		for (TableItem tableItem : items) {
//			tableItem.dispose();
//		}
//		TSGraph rootGraph = ((TSEGraph)fTSENode.getOwnerGraph()).getGreatestAncestor();
//		EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.useInstance((EObject)rootGraph.getUserObject());
		//add code retrive concept set in process and set in table	
	}
	
	protected boolean isRefresh() {
		return refresh;
	}
	
	@Override
	public void refresh() {
		try{
			this.refresh = true;
		}finally{
			this.refresh = false;
		}
	}
	
}