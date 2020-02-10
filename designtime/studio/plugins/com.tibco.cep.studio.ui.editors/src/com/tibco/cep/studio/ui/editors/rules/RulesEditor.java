package com.tibco.cep.studio.ui.editors.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IAnnotationAccessExtension2;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.core.rules.DefaultProblemHandler;
import com.tibco.cep.studio.core.rules.IResolutionContextProviderExtension2;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.AbstractRuleFormEditor;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.actions.GoToMatchingBracketAction;
import com.tibco.cep.studio.ui.editors.rules.actions.OpenDeclarationAction;
import com.tibco.cep.studio.ui.editors.rules.actions.RenameLocalVariableAction;
import com.tibco.cep.studio.ui.editors.rules.actions.SearchForReferencesAction;
import com.tibco.cep.studio.ui.editors.rules.actions.ToggleCommentAction;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorQuickFixAssistant;
import com.tibco.cep.studio.ui.editors.rules.text.ConditionalHighlightingReconciler;
import com.tibco.cep.studio.ui.editors.rules.text.IReconcilingListener;
import com.tibco.cep.studio.ui.editors.rules.text.RulesDocumentProvider;
import com.tibco.cep.studio.ui.editors.rules.text.RulesProjectionUpdater;
import com.tibco.cep.studio.ui.editors.rules.text.RulesReconciler;
import com.tibco.cep.studio.ui.editors.rules.text.RulesSourceViewerConfiguration;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

public class RulesEditor extends AbstractDecoratedTextEditor implements IReconcilingListener, IResolutionContextProvider, IResolutionContextProviderExtension2 {

	public class RulesSourceViewer extends ProjectionViewer implements IAdaptable {

		public RulesSourceViewer(Composite parent, IVerticalRuler ruler,
				IOverviewRuler overviewRuler, boolean showsAnnotationOverview,
				int styles) {
			super(parent, ruler, overviewRuler, showsAnnotationOverview, styles);
		}
		
		protected void setVisibleDocument(IDocument document) {
			super.setVisibleDocument(document);
		}

		@SuppressWarnings("rawtypes")
		public Object getAdapter(Class key) {
			if (ITextEditor.class.equals(key)) {
				return getEditor();
			}
			return getEditor().getAdapter(key);
		}
		
	}
	
	
	public static final String RESOURCE_BUNDLE_ID	= "com.tibco.cep.studio.ui.editors.rules.EditorMessages";
	
	private static final String GROUP_EDIT_ID 		= "group.rules.edit";
	private static final String GROUP_SEARCH_ID 	= "group.rules.search";
	private static final String GROUP_RULES_ID 		= "group.rules.id";
	private static final String GROUP_NAVIGATE_ID 	= "group.rules.navigate";

	public static final char[] CHARACTER_PAIRS = new char[] {'(', ')', '{', '}', '[', ']'};

	public static final String PREF_MATCHING_BRACKETS = "matchingBrackets";
	public static final String PREF_MATCHING_BRACKETS_COLOR = "matchingBracketsColor";
    
	private MenuManager   fSearchMenu;
	private MenuManager	  fEditMenu;

	private ProjectionSupport fProjectionSupport;
	private RulesEditorOutlinePage fOutlinePage;
	private RulesProjectionUpdater fProjectionUpdater;
	private AbstractRuleFormEditor fFormEditor;
	private ConditionalHighlightingReconciler fHighlightingReconciler;
	
	private List<IReconcilingListener> fReconcileListeners = new ArrayList<IReconcilingListener>();
    private Composite container;

	private boolean fEnabled = true;
	private int fPreviousOffset = 0;
	
	private RulesEditorQuickFixAssistant fQuickFixAssistant;
    
	public Composite getContainer() {
		return container;
	}

	public RulesEditor(AbstractRuleFormEditor formEditor) {
		setSourceViewerConfiguration(new RulesSourceViewerConfiguration(this, this));
		RulesDocumentProvider provider = EditorsUIPlugin.getDefault().getDocumentProvider();
		setDocumentProvider(provider);
		this.fFormEditor = formEditor;
		setEditorContextMenuId("#BERuleEditorContext"); 
		setRulerContextMenuId("#BERuleRulerContext"); 
	}
    
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		ProjectionViewer viewer = ((ProjectionViewer)getSourceViewer());
		
        viewer.doOperation(ProjectionViewer.TOGGLE);
        AnnotationModel model = viewer.getProjectionAnnotationModel();
        if (model == null) {
        	
        }
        this.container = parent;
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		getDocumentProvider().addElementStateListener(fFormEditor);
		if (input instanceof FileEditorInput) {
//			fBodyScanner = 
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#installTextDragAndDrop(org.eclipse.jface.text.source.ISourceViewer)
	 */
	protected void installTextDragAndDrop(final ISourceViewer viewer) {
		super.installTextDragAndDrop(viewer);
		
		//When DND text on the Source Editor, editor gets focus   
		DropTarget target = getCurrentDropTarget(viewer.getTextWidget());
		if (target != null) {
			DropTargetListener dropTargetListener= new DropTargetAdapter() {
				/**
				 * @param event
				 */
				public void drop(DropTargetEvent event) {
					try {
						if (fFormEditor != null) {
							fFormEditor.setFocus();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			target.addDropListener(dropTargetListener);
		}
	}
	
	/**
	 * @param control
	 * @return
	 */
	private DropTarget getCurrentDropTarget(Control control) {
		if (control == null) {
			return null;
		}
		Object currentDropTarget = control.getData(DND.DROP_TARGET_KEY);
		return (DropTarget)currentDropTarget;
	}
	
	/*
	 * @see WorkbenchPart#firePropertyChange(int)
	 */
	@Override
	protected void firePropertyChange(int property) {
		super.firePropertyChange(property);
		if(isDirty()){
			fFormEditor.modified();
		}
	}
	

	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		if (false) {
			return super.createSourceViewer(parent, ruler, styles);
		}
		fAnnotationAccess= getAnnotationAccess();
		fOverviewRuler= createOverviewRuler(getSharedColors());

		ProjectionViewer viewer = new RulesSourceViewer(parent, ruler, fOverviewRuler, true, styles);

        fProjectionSupport = new ProjectionSupport(viewer,
                getAnnotationAccess(), getSharedColors());
        fProjectionSupport
                .addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error"); //$NON-NLS-1$
        fProjectionSupport
                .addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning"); //$NON-NLS-1$
        // TODO : add a custom hover control creator to chromacode hover
        // information (see fProjectionSupport.setHoverControlCreator())
        fProjectionSupport.install();
        
        fProjectionUpdater = new RulesProjectionUpdater();
        fProjectionUpdater.install(this, viewer, getPreferenceStore());
        addReconcileListener(fProjectionUpdater);
        
//        ConditionalHighlightingPresenter presenter = new ConditionalHighlightingPresenter();
//        fHighlightingReconciler = new ConditionalHighlightingReconciler(presenter, viewer, this);
//        addReconcileListener(fHighlightingReconciler);
//        presenter.install(viewer, (RulesPresentationReconciler) new RulesSourceViewerConfiguration(this, this).getPresentationReconciler(viewer));
        
		getSourceViewerDecorationSupport(viewer);

        return viewer;
	}

	@Override
	protected IAnnotationAccess createAnnotationAccess() {
		IAnnotationAccess access = super.createAnnotationAccess();
		if (access instanceof IAnnotationAccessExtension2) {
			fQuickFixAssistant = new RulesEditorQuickFixAssistant();
			((IAnnotationAccessExtension2) access).setQuickAssistAssistant(fQuickFixAssistant);
		}
		return access;
	}

	@Override
	protected void configureSourceViewerDecorationSupport(
			SourceViewerDecorationSupport support) {
		super.configureSourceViewerDecorationSupport(support);
		
		DefaultCharacterPairMatcher matcher = new DefaultCharacterPairMatcher(CHARACTER_PAIRS, IDocument.DEFAULT_CONTENT_TYPE);
		support.setCharacterPairMatcher(matcher);
		support.setMatchingCharacterPainterPreferenceKeys(PREF_MATCHING_BRACKETS, PREF_MATCHING_BRACKETS_COLOR);
		
		// TODO : make these preferences/configurable
		getPreferenceStore().setValue(PREF_MATCHING_BRACKETS, true);
		getPreferenceStore().setValue(PREF_MATCHING_BRACKETS_COLOR, "192,192,192");

	}

	@Override
	public void dispose() {
		ISourceViewer sourceViewer = getSourceViewer();
		if (fProjectionUpdater != null) {
			((RulesSourceViewer)sourceViewer).removeProjectionListener(fProjectionUpdater);
			fProjectionUpdater.uninstall();
			removeReconcileListener(fProjectionUpdater);
			fProjectionUpdater = null;
		}
		if (fProjectionSupport != null) {
			fProjectionSupport.dispose();
			fProjectionSupport = null;
		}
		if (fHighlightingReconciler != null) {
			fHighlightingReconciler.uninstall();
			removeReconcileListener(fHighlightingReconciler);
			fHighlightingReconciler = null;
		}
		if (fOutlinePage != null) {
			removeReconcileListener(fOutlinePage);
			fOutlinePage = null;
		}
		if (getDocumentProvider() != null) {
			getDocumentProvider().removeElementStateListener(fFormEditor);
		}
		if (fQuickFixAssistant != null) {
			fQuickFixAssistant.uninstall();
			fQuickFixAssistant = null;
		}
		super.dispose();
		if (super.getSite() instanceof MultiPageEditorSite) {
			((MultiPageEditorSite) super.getSite()).deactivate();
			((MultiPageEditorSite) super.getSite()).dispose();
		}
		fFormEditor = null;
	}

	@Override
	protected void createActions() {
		super.createActions();
        IAction action;
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_ID);

        action = new ContentAssistAction(bundle, "ContentAssistProposal.", this); //$NON-NLS-1$
        action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
        setAction("ContentAssistProposal", action);

        action = new OpenDeclarationAction(bundle, "OpenDeclaration.", this, this, getSourceViewer(), IRulesSourceTypes.FULL_SOURCE);
        action.setActionDefinitionId("com.tibco.cep.studio.ui.editors.rules.actions.opendeclaration");
        setAction("OpenDeclaration", action);

        action = new SearchForReferencesAction(bundle, "SearchForReferences.", this);
        action.setActionDefinitionId("com.tibco.cep.studio.ui.editors.rules.actions.searchforreferences");
        setAction("SearchForReferences", action);
        
        action = new ToggleCommentAction(bundle, "ToggleCommentAction.", this);
        action.setActionDefinitionId("com.tibco.cep.studio.ui.editors.rules.actions.togglecommentaction");
        setAction("ToggleCommentAction", action);

        action = new RenameLocalVariableAction(bundle, "RenameLocalVariableAction.", this, this);
        action.setActionDefinitionId("com.tibco.cep.studio.ui.editors.rules.actions.renamevariableaction");
        setAction("RenameLocalVariableAction", action);
        
        action = new GoToMatchingBracketAction(bundle, "GoToMatchingBracketAction", this);
        action.setActionDefinitionId("com.tibco.cep.studio.ui.editors.rules.actions.gotomatchingbracketaction");
        setAction("GoToMatchingBracketAction", action);
        markAsSelectionDependentAction("GoToMatchingBracketAction", true);
        
	}

	@Override
	protected void handleCursorPositionChanged() {
		int currOffset = getCurrentOffset();
		if (fPreviousOffset != currOffset) {
			fPreviousOffset = currOffset;
			GoToMatchingBracketAction action = (GoToMatchingBracketAction) getAction("GoToMatchingBracketAction");
			if (action != null) {
				action.reset();
			}
		}
		super.handleCursorPositionChanged();
	}
	
	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		initializeMenus();
		super.editorContextMenuAboutToShow(menu);
		
		menu.add(new Separator(GROUP_RULES_ID));
		menu.add(fEditMenu);
		menu.add(fSearchMenu);
		
		addAction(menu, GROUP_EDIT_ID, "ContentAssistProposal");
		addAction(menu, GROUP_EDIT_ID, "ToggleCommentAction");
		addAction(menu, GROUP_EDIT_ID, "RenameLocalVariableAction");
		
		menu.add(new Separator(GROUP_NAVIGATE_ID));
		addAction(menu, GROUP_NAVIGATE_ID, "OpenDeclaration");
		
		menu.add(new Separator(GROUP_SEARCH_ID));
		addAction(menu, GROUP_SEARCH_ID, "SearchForReferences");
	}

	private void initializeMenus() {
		if (fEditMenu == null) {
			fEditMenu = new MenuManager("Edit", GROUP_EDIT_ID);
		}
		if (fSearchMenu == null) {
			fSearchMenu = new MenuManager("Search", GROUP_SEARCH_ID);
		}
		fEditMenu.removeAll();
		fSearchMenu.removeAll();
	}

	@Override
    protected void initializeKeyBindingScopes() {
        setKeyBindingScopes(new String[] { "com.tibco.cep.studio.ui.editors.ruleEditorScope" }); //$NON-NLS-1$
    }

	@Override
	public Object getAdapter(Class key) {
		if (fProjectionSupport != null) {
			Object adapter = fProjectionSupport.getAdapter(getSourceViewer(),
					key);
			if (adapter != null)
				return adapter;
		}
		if (RulesASTNode.class.equals(key)) {
			return getRulesAST();
		}
		if (key.equals(IContentOutlinePage.class)) {
			return getOutlinePage();
		}
		if (IProject.class.equals(key)) {
			return getProject();
		}
		return super.getAdapter(key);
	}

	private Object getOutlinePage() {
		if (fOutlinePage == null) {
			fOutlinePage = new RulesEditorOutlinePage(this);
			addReconcileListener(fOutlinePage);
			setOutlineInput();
		}
		return fOutlinePage;
	}

	private void setOutlineInput() {
		if (fOutlinePage != null) {
			fOutlinePage.setInput(getRulesAST());
		}
	}

    public int getCurrentOffset() {
        ISourceViewer sourceViewer = getSourceViewer();
        if (sourceViewer == null)
            return 0;
        return sourceViewer.getSelectedRange().x;
    }

	public RulesASTNode reparse() {
		IDocument document = getDocumentProvider().getDocument(getEditorInput());
		CommonTree tree = RulesParserManager.parseRuleString(getProjectName(), document.get(), new DefaultProblemHandler());
		return (RulesASTNode) tree;
	}

	public RulesASTNode getRulesAST() {
		return EditorsUIPlugin.getDefault().getWorkingCopyManager().getWorkingCopy(getEditorInput());
	}

	public String getProjectName() {
		IProject project = getProject();
		if (project != null) {
			return project.getName();
		}
		return null;
	}
	
	public IProject getProject() {
		IStorage storage = getStorage();
		if (storage instanceof IFile) {
			return ((IFile) storage).getProject();
		} else if (storage instanceof JarEntryFile) {
			String projectName = ((JarEntryFile) storage).getProjectName();
			return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		}
		return null;
	}

	public IStorage getStorage() {
		if (getEditorInput() instanceof IStorageEditorInput) {
			try {
				return ((IStorageEditorInput) getEditorInput()).getStorage();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public RulesASTNode getCurrentNode() {
		int currentOffset = getCurrentOffset();
		RulesASTNodeFinder finder = new RulesASTNodeFinder(currentOffset);
		RulesASTNode rulesAST = getRulesAST();
		if (rulesAST == null) {
			rulesAST = reparse();
		}
		rulesAST.accept(finder);
		RulesASTNode foundNode = finder.getFoundNode();
		return foundNode;
	}

	public void reconciled(Object result) {
		// first, notify the working copy manager
		EditorsUIPlugin.getDefault().getWorkingCopyManager().inputChanged(getEditorInput(), result);
		
		fireReconciled(result);
	}

	private void fireReconciled(Object result) {
		if (fReconcileListeners.size() == 0) {
			return;
		}
		for (IReconcilingListener listener : fReconcileListeners) {
			listener.reconciled(result);
		}
	}

	private ITextEditor getEditor() {
		return this;
	}
	
	public void addReconcileListener(IReconcilingListener listener) {
		if (listener != null && !fReconcileListeners.contains(listener)) {
			fReconcileListeners.add(listener);
		}
	}

	public void removeReconcileListener(IReconcilingListener listener) {
		if (listener != null && fReconcileListeners.contains(listener)) {
			fReconcileListeners.remove(listener);
		}
	}

	public boolean canUseFormEditor() {
		if (hasSyntaxErrors()) {
			return false;
		}
		if (!hasConditionsSection()) {
			return false;
		}
		if (!hasActionsSection()) {
			return false;
		}
		return true;
	}
	
	public boolean hasActionsSection() {
		RulesASTNode rulesAST = getCurrentAST();
		if (rulesAST == null) {
			return false;
		}
		try {
			List<RulesASTNode> children = rulesAST.getFirstChild().getChildrenByFeatureId(RulesParser.BLOCKS);
			for (int i = 0; i < children.size(); i++) {
				int type = children.get(i).getType();
				if (type == RulesParser.THEN_BLOCK
						|| type == RulesParser.BODY_BLOCK
						|| type == RulesParser.ACTION_CONTEXT_BLOCK) {
					return true;
				}
			}
		} catch (NullPointerException e) {
		}
		return false;
	}

	public boolean hasConditionsSection() {
		RulesASTNode rulesAST = getCurrentAST();
		if (rulesAST == null) {
			return false;
		}
		if (rulesAST.getType() == RulesParser.RULE_FUNCTION) {
			return true; // rule functions don't have a conditions section
		}
		try {
			List<RulesASTNode> children = rulesAST.getFirstChild().getChildrenByFeatureId(RulesParser.BLOCKS);
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getType() == RulesParser.WHEN_BLOCK) {
					return true;
				}
			}
		} catch (NullPointerException e) {
		}
		return false;
	}

	public boolean hasSyntaxErrors() {
		RulesASTNode rulesAST = getCurrentAST();
		if (rulesAST == null) {
			return true;
		}
//		if(validate(rulesAST)){
//			rulesAST.setMalformed(true);
//		}
		return rulesAST.isMalformed();
	}

	private RulesASTNode getCurrentAST() {
		RulesASTNode rulesAST = getRulesAST();
		if (rulesAST != null && rulesAST.isDirty()) {
			rulesAST = reparse();
		}
		if (rulesAST == null) {
			rulesAST = reparse();
		}
		return rulesAST;
	}
	
	
	@Override
	public IResolutionContext getResolutionContext(ElementReference elementReference) {
		ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
		return ElementReferenceResolver.createResolutionContext(scope);
	}

	@Override
	public IResolutionContext getResolutionContext(ElementReference reference,
			ScopeBlock scope) {
		return ElementReferenceResolver.createResolutionContext(scope);
	}

	/**
	 * @param rulesAST
	 * @param ruleElement
	 * @return
	 */
//	private boolean checkRuleSemantics(RulesASTNode rulesAST, RuleElement ruleElement) {
//		RulesSemanticASTVisitor visitor = new RulesSemanticASTVisitor(this, getProject().getName(), null);
//		rulesAST.accept(visitor);
//		List<IRulesProblem> semanticErrors = visitor.getSemanticErrors();
//		if(semanticErrors.size() >0 ){
//			return true;
//		}
//		return false;
//	}
	
	@Override
	public IWorkbenchPartSite getSite() {
		return fFormEditor.getSite();
	}

	@Override
	public IFile getRuleFile() {
		if (!(getEditorInput() instanceof FileEditorInput)) {
			return null;
		}
		FileEditorInput fei = (FileEditorInput) getEditorInput();
		return fei.getFile();
	}

	/**
	 * Returns whether the source editor is enabled (i.e. it is in view and the form
	 * viewer is hidden).
	 * @return
	 */
	public boolean isEnabled() {
		return fEnabled;
	}
	
	/**
	 * Set the enablement state of the editor.  The editor is considered enabled if
	 * it is in view; it is considered disabled when the form viewer is in view.
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.fEnabled = enabled;
		updateState(getEditorInput());
		validateState(getEditorInput());
	}
	
	@Override
	public boolean isEditable() {
		if (!fEnabled) {
			return false;
		}
		if (getEditorInput() instanceof JarEntryEditorInput) {
			return false;
		}
		if (IndexUtils.isProjectLibType(getRuleFile())) {
			return false;
		}
		return super.isEditable();
	}

	@Override
	public boolean isEditorInputModifiable() {
		if (!fEnabled) {
			return false;
		}
		if (getEditorInput() instanceof JarEntryEditorInput) {
			return false;
		}
		if (IndexUtils.isProjectLibType(getRuleFile())) {
			return false;
		}
		return super.isEditorInputModifiable();
	}

	@Override
	public boolean isEditorInputReadOnly() {
		if (!fEnabled) {
			return true;
		}
		return super.isEditorInputReadOnly();
	}

	@Override
	protected void updateState(IEditorInput input) {
		super.updateState(input);
		
		if (getSourceViewerConfiguration() instanceof RulesSourceViewerConfiguration) {
			IReconciler reconciler = ((RulesSourceViewerConfiguration)getSourceViewerConfiguration()).getReconciler();
			if (reconciler instanceof RulesReconciler) {
				((RulesReconciler) reconciler).setEnablement(isEnabled());
			}
		}
	}
	
}