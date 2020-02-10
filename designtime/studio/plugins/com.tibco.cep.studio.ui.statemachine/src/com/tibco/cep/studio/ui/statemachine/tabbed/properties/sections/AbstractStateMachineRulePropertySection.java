package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils.openElement;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getGlobalVariableDefs;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setKeySupport;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setupSourceViewerDecorationSupport;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.updateDeclarations;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDropTarget;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setEditContextMenuSupport;

import java.util.List;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPartitionScanner;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractStateMachineRulePropertySection extends AbstractStateMachinePropertySection implements IResolutionContextProvider, IStudioRuleSourceCommon {

	protected Section declarations;
	protected Section conditions;
	protected Section actions;
	
	protected Compilable rule;
	
	protected SourceViewer condSourceViewer;
	protected SourceViewer actionsSourceViewer;
	protected Table declarationsTable;
	
	protected Document document; // For Action Source
	protected Document condDocument; // For Condition Source
    protected Composite ruleDeclClient;
	public static Font font = new Font(Display.getDefault(), "Courier New", 10, SWT.NULL);
	public static Font declfont = new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL);
 	
	protected boolean configured = false;
	protected Composite ruleSectionCompositeParent;
	
	protected StateMachineRulesFormSourceViewerConfiguration condConfig;
	protected StateMachineRulesFormSourceViewerConfiguration actionConfig;
	private boolean initialized = false;
	private StateMachineRuleDocumentListener doclistener;
	private FocusListener fSourceViewerFocusListener; 
	
	@Override
	public void dispose() {
		if (condSourceViewer != null) {
			condSourceViewer.unconfigure();
			condSourceViewer = null;
		}
		if (actionsSourceViewer != null) {
			actionsSourceViewer.unconfigure();
			actionsSourceViewer = null;
		}
		if (condConfig != null) {
			condConfig.unconfigure();
			condConfig = null;
		}
		if (actionConfig != null) {
			actionConfig.unconfigure();
			actionConfig = null;
		}

		if (declarationsTable != null) {
			declarationsTable.dispose();
		}
		if (document != null) {
			document.removeDocumentListener(doclistener);
			document = null;
		}
		if (condDocument != null) {
			condDocument.removeDocumentListener(doclistener);
			condDocument = null;
		}
		doclistener = null;
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachinePropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.ruleSectionCompositeParent = parent;
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		parent.setLayout(layout);
		
		//For fixed Declaration Section.. No Overlapping Section 
		createDeclarationsPart(parent);
		
		sashForm = new MDSashForm(parent, SWT.VERTICAL);
		sashForm.setData("form", parent);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		sashForm.setLayoutData(gd);
		parent.setLayoutData(gd);

		createSections(sashForm);
		
		hookResizeListener();
	}

	abstract protected void createSections(SashForm sashForm);
	
	
	//This is for Declaration Table Height
	protected int getDeclarationTableHeight(){
		return 30; //Override for Rule Property Section
	}

	/**
	 * @param parent
	 */
	protected void createDeclarationsPart(Composite parent) {

		declarations = getWidgetFactory().createSection(parent, Section.TITLE_BAR| Section.EXPANDED /*|Section.TWISTIE*/);
		declarations.setActiveToggleColor(getWidgetFactory().getHyperlinkGroup().getActiveForeground());
		declarations.setToggleColor(getWidgetFactory().getColors().getColor(IFormColors.SEPARATOR));
		declarations.setText(com.tibco.cep.studio.ui.editors.utils.Messages.getString("rule.declarations"));

		ruleDeclClient = getWidgetFactory().createComposite(declarations, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		ruleDeclClient.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		declarations.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
//        gd.heightHint = getDeclarationHeight();
        ruleDeclClient.setLayoutData(gd);
		getWidgetFactory().paintBordersFor(ruleDeclClient);
		declarations.setClient(ruleDeclClient);
		editDeclarationTable();
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		declarationsTable = new Table(ruleDeclClient, SWT.BORDER | SWT.FULL_SELECTION);
		declarationsTable.setLayout(new GridLayout());
		gd.heightHint = getDeclarationTableHeight();
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
		
		addControlListener();
	}
	
	protected void addControlListener(){
		//Override for Rule Property Section
	}
	
	protected void editDeclarationTable(){
		//Override for Rule Property Section
	}
	
	/**
	 * @param parent
	 */
	protected void createConditionsPart(Composite parent) {
		conditions = getWidgetFactory().createSection(parent, Section.TITLE_BAR| Section.EXPANDED /*| Section.TWISTIE*/);
		conditions.setActiveToggleColor(getWidgetFactory().getHyperlinkGroup().getActiveForeground());
		conditions.setToggleColor(getWidgetFactory().getColors().getColor(IFormColors.SEPARATOR));
		conditions.setText(com.tibco.cep.studio.ui.editors.utils.Messages.getString("rule.conditions"));
	
		Composite client = getWidgetFactory().createComposite(conditions, SWT.WRAP| SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		getWidgetFactory().paintBordersFor(client);
		conditions.setClient(client);
			
		DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
		EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
		OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);
		
		condSourceViewer = new ProjectionViewer(client, new VerticalRuler(12, access), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		condSourceViewer.getTextWidget().setFont(font);
		condSourceViewer.getTextWidget().addFocusListener(getFocusListener());
		
		condSourceViewer.setOverviewRulerAnnotationHover(new DefaultAnnotationHover());
		condSourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		setupSourceViewerDecorationSupport(condSourceViewer, ruler, access, sharedColors);
		
		if(editor != null && editor.isEnabled()){
			setDropTarget(condSourceViewer, null, null);
		}
	}
	
	private FocusListener getFocusListener() {
		if (fSourceViewerFocusListener == null) {
			fSourceViewerFocusListener = new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					editor.enableEdit(true);
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					editor.enableEdit(false);
				}
			};
		}
		return fSourceViewerFocusListener;
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
		actionsSourceViewer.getTextWidget().addFocusListener(getFocusListener());
		actionsSourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		setupSourceViewerDecorationSupport(actionsSourceViewer, ruler, access, sharedColors);
		
		if(editor != null && editor.isEnabled()){
			setDropTarget(actionsSourceViewer, null, null);
		}
	}
	
	protected void configureSourceViewers() {
		if (configured) {
			return;
		}
		if (condSourceViewer != null) {
			condConfig = new StateMachineRulesFormSourceViewerConfiguration(this, IRulesSourceTypes.CONDITION_SOURCE, getProject());
			condSourceViewer.configure(new StateMachineRulesFormSourceViewerConfiguration(this, IRulesSourceTypes.CONDITION_SOURCE, getProject()));
		}
		if (actionsSourceViewer != null) {
			actionConfig = new StateMachineRulesFormSourceViewerConfiguration(this, IRulesSourceTypes.ACTION_SOURCE, getProject());
			actionsSourceViewer.configure(actionConfig);
		}
		configured = true;
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		configureSourceViewers();
	}

	/**
	 * @param isConditionAndAction
	 */
	public void init(boolean isConditionAndAction) {
		updateDeclarations(declarationsTable, rule, getProject().getName());
		IDocumentPartitioner partitioner = null;
		doclistener = new StateMachineRuleDocumentListener();
		
		//For Condition Source
		if(isConditionAndAction){
			condDocument = new Document(rule.getConditionText()!= null ? rule.getConditionText():"");
			condDocument.addDocumentListener(doclistener);
			partitioner = new FastPartitioner(new RulesPartitionScanner(), 
					new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER, RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
			partitioner.connect(condDocument);
			condDocument.setDocumentPartitioner(partitioner);
			
			if (condSourceViewer != null) {
				condSourceViewer.setDocument(condDocument, new RulesAnnotationModel());
				
				TextViewerUndoManager conditionSourceUndoManager =  new TextViewerUndoManager(10);
				conditionSourceUndoManager.connect(condSourceViewer);
				if(editor.isEnabled()){
					if (!initialized) {
						setEditContextMenuSupport(condSourceViewer.getTextWidget(), conditionSourceUndoManager, true, this);
						setKeySupport(this, condSourceViewer.getTextWidget(), conditionSourceUndoManager, condConfig, condSourceViewer, false);
					}
				}
			}
		}
		
		//For Action Source
		document = new Document(rule.getActionText()!= null ? rule.getActionText():"");
		document.addDocumentListener(doclistener);
		partitioner = new FastPartitioner(new RulesPartitionScanner(), 
				new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER,  RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		
		if (actionsSourceViewer != null) {
			actionsSourceViewer.setDocument(document, new RulesAnnotationModel());
			
			TextViewerUndoManager actionSourceUndoManager =  new TextViewerUndoManager(10);
			actionSourceUndoManager.connect(actionsSourceViewer);
	
			setDeclarationTableEditableSupport(declarationsTable, 1, rule, -1, -1, -1, this, isFirstDeclarationEditable(), isDeclarationEditable(), null, true);
			if(editor.isEnabled()){
				if (!initialized) {
					setEditContextMenuSupport(actionsSourceViewer.getTextWidget(), actionSourceUndoManager, true, this);
					setKeySupport(this, actionsSourceViewer.getTextWidget(), actionSourceUndoManager, actionConfig, actionsSourceViewer, false);
				}
			}
		}
		
		//Checking No Condition Field for State Transition
		if(eObject instanceof StateTransition){
			StateTransition transition = (StateTransition)eObject;
			if(transition.isLambda()){
				ruleSectionCompositeParent.setEnabled(false);
//				sashForm.setEnabled(false);
			}else{
				ruleSectionCompositeParent.setEnabled(true);
//				sashForm.setEnabled(true);
			}
		}
		initialized = true;
 	}
	
	/**
	 * @return
	 */
	protected boolean isDeclarationEditable(){
		return false;
	}
	
	protected boolean isFirstDeclarationEditable(){
		return isDeclarationEditable();
	}
	
	/**
	 * 
	 * @author sasahoo
	 *
	 */
	protected class StateMachineRuleDocumentListener implements IDocumentListener{

		/* (non-Javadoc)
		 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			// TODO Auto-generated method stub
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		@Override
		public void documentChanged(DocumentEvent event) {
			if(condDocument!= null && event.getDocument() == condDocument){
				if(rule.getConditionText() != null && condDocument.get().equalsIgnoreCase("")){
					rule.setConditionText(null);
					editor.modified();
				}
				if(!condDocument.get().equalsIgnoreCase("")){
					rule.setConditionText(condDocument.get());
					editor.modified();
				}
			}

			if(document!=null && event.getDocument() == document){
				if(rule.getActionText() != null && document.get().equalsIgnoreCase("")){
					rule.setActionText(null);
					editor.modified();
				}
				if(!document.get().equalsIgnoreCase("")){
					rule.setActionText(document.get());
					editor.modified();
				}
			}
		}
	}
	
	@Override
	public IResolutionContext getResolutionContext(ElementReference elementReference) {
		ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
		SimpleResolutionContext context = new SimpleResolutionContext(scope);
		List<GlobalVariableDef> globalDefs = getGlobalVariableDefs(editor.getProject().getName(), declarationsTable);
		if (globalDefs == null) {
			return context;
		}
		for (GlobalVariableDef globalVariableDef : globalDefs) {
			context.addGlobalVariable(globalVariableDef);
		}
		return context;
	}

	@Override
	public IResolutionContext getResolutionContext(ElementReference elementReference, ScopeBlock scope) {
		SimpleResolutionContext context = new SimpleResolutionContext(scope);
		List<GlobalVariableDef> globalDefs = getGlobalVariableDefs(editor.getProject().getName(), declarationsTable);
		for (GlobalVariableDef globalVariableDef : globalDefs) {
			context.addGlobalVariable(globalVariableDef);
		}
		return context;
	}
	
	 /**
	 * @param textWidget
	 * @param undoManager
	 */
//	public void setKeySupport(final StyledText textWidget, final TextViewerUndoManager undoManager){
//
//		textWidget.addKeyListener(new KeyAdapter(){
//			/* (non-Javadoc)
//			 * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
//			 */
//			@Override
//			public void keyReleased(KeyEvent e) {
//				try {
//					if (e.stateMask == SWT.CTRL && e.keyCode == 'z') {
//						undoManager.undo();
//					}
//					if (e.stateMask == SWT.CTRL && e.keyCode == 'y') {
//						undoManager.redo();
//					}
//					if (e.stateMask == SWT.CTRL && e.keyCode == ' ') {
//						if(condConfig != null){
//							if(condSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
//								RulesEditorContentAssistant entityContentAssistant = 
//									(RulesEditorContentAssistant)condConfig.getContentAssistant(condSourceViewer);
//								entityContentAssistant.showPossibleCompletions();
//							}
//						}
//						if(actionConfig != null) {
//							if(actionsSourceViewer.getTextWidget().getCaretOffset() > 0){
//								RulesEditorContentAssistant entityContentAssistant = 
//									(RulesEditorContentAssistant)actionConfig.getContentAssistant(actionsSourceViewer);
//								entityContentAssistant.showPossibleCompletions();
//							}
//						}
//					}
//					if (e.keyCode == SWT.F3) {
//						openDeclaration();
//					}
//				}
//				catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}});
//	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#updateDeclarationStatements(java.lang.String, java.lang.String, java.lang.String, int, int, int, com.tibco.cep.designtime.core.model.rule.Compilable)
	 */
	@Override
	public boolean updateDeclarationStatements(String type, String id, String oldIdName,
			String newIdName, int ruleType, int blockType, int statementType,
			Compilable compilable) {
		boolean updated = StudioUIUtils.updateSymbol(type, oldIdName, newIdName, compilable);
		if(updated){
			refreshSourceViewerConfiguration();
			editor.modified();
			return true;
		}
		return false;
	}

	@Override
	public Compilable getCommonCompilable() {
		return rule;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#getRemoveDeclarationButton()
	 */
	public ToolItem getRemoveDeclarationButton(){
		return removeDeclButton;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#getDeclarationTable()
	 */
	public Table getDeclarationTable(){
		return declarationsTable;
	}
	
	@Override
	public ToolItem getDownDeclarationButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToolItem getUpDeclarationButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openDeclaration() {
		if(condSourceViewer != null && condSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
			openElement (condSourceViewer, 
					condConfig.getResolutionContextProvider(), document, IRulesSourceTypes.CONDITION_SOURCE, getProject().getName());
		}
		if(actionsSourceViewer != null && actionsSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
			openElement (actionsSourceViewer, 
					actionConfig.getResolutionContextProvider(), document, IRulesSourceTypes.ACTION_SOURCE, getProject().getName());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#refreshSourceViewerConfiguration()
	 */
	public void refreshSourceViewerConfiguration() {
		if(condConfig != null && condConfig.getReconciler() != null){
			condConfig.getReconciler().getReconcilingStrategy(IDocument.DEFAULT_CONTENT_TYPE).reconcile(null);
		}
		if(actionConfig != null && actionConfig.getReconciler() != null){
			actionConfig.getReconciler().getReconcilingStrategy(IDocument.DEFAULT_CONTENT_TYPE).reconcile(null);
		}
	}
}