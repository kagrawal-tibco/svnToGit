package com.tibco.cep.studio.ui.editors;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils.openElement;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getPropertyImage;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getUniqueTag;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.isSymbolPresent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.GlobalVariableExtension;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.HeaderASTNode;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.HeaderParser;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.utils.DeclarationSelector;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractSashForm;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon;
import com.tibco.cep.studio.ui.util.StudioImages;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

@SuppressWarnings("restriction")
public abstract class AbstractRuleEntitiesFormViewer extends AbstractSashForm implements IResolutionContextProvider, IStudioRuleSourceCommon {
    
	protected boolean initialized = false;
	protected ModifyListener fDescModifyListener;
	protected ModifyListener fAliasModifyListener;
	protected Table declarationsTable;
	protected Table actionContextTable;
	protected Table bindingsTable;
	
    protected boolean sourceSyncronized;

    protected RulesFormSourceViewerConfiguration conditionSourceViewerConfiguration;
    protected RulesFormSourceViewerConfiguration actionSourceViewerConfiguration;
    
    protected SourceViewer condSourceViewer;
    protected SourceViewer actionsSourceViewer;
    protected IRegion fCurrentConditionsRegion;
    protected IRegion fCurrentActionsRegion;
    
	public static Font font = new Font(Display.getDefault(), "Courier New", 10, SWT.NULL);
	public static Font declfont = new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL);
	
	protected ToolItem removeDeclButton;
	protected ToolItem addDeclButton;
	protected ToolItem upDeclButton;
	protected ToolItem downDeclButton;
	
	protected org.eclipse.jface.action.Action dependencyDiagramAction;
	protected org.eclipse.jface.action.Action sequenceDiagramAction;
	
	protected final java.awt.Font awtFont = new java.awt.Font("Tahoma", 0, 11);

	/**
	 * 
	 * @param managedForm
	 * @param toolkit
	 */
	protected abstract void createConfigurationPart(final ScrolledForm form,final FormToolkit toolkit);
    
	/**
	 * 
	 * @param managedForm
	 * @param toolkit
	 */
	protected  void createDeclarationsPart(final IManagedForm managedForm, Composite parent){
		//Override this
	}

	/**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createConditionsPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }

	/**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createActionContextPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createActionsPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
 
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createViewsPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createScopePart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected void createBodyPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    
    protected abstract Compilable getCompilable();
    protected abstract AbstractRuleFormEditor getFormEditor();
    protected abstract IDocument getDescriptionText();
    protected abstract SourceViewer getActionSourceViewer();
    protected abstract void init();
    protected abstract boolean isEditorDirty();
    protected abstract boolean isEditorEnabled();
    protected abstract void setCompilable(Compilable compilable);
    protected abstract void updateSourceViewers(boolean editable);
	protected abstract void updateWidgets(boolean isDirtyBeforeRefresh,
			RulesASTNode newNode, boolean editable);

    protected void doRefresh() {
    	final boolean isDirtyBeforeRefresh = isEditorDirty();
    	final RulesASTNode newNode = getCurrentASTNode();
    	RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(true, getFormEditor().getProject().getName());
    	newNode.accept(visitor);
    	Compilable compile = visitor.getRule();
    	setCompilable(compile);
    	final boolean editable = isEditorEnabled();
    	if (!initialized) {
    		init();
    		updateWidgets(isDirtyBeforeRefresh, newNode, editable);
    	} else {
    		Display.getDefault().asyncExec(new Runnable() {

    			@Override
    			public void run() {
    				updateSourceViewers(editable);
    				updateWidgets(isDirtyBeforeRefresh, newNode, editable);
    			}
    		});
    	}
    }
    
	protected void refresh() {
    	doRefresh();
    }
    
	@Override
	public void dispose() {
		if (this.actionsSourceViewer != null) {
			this.actionsSourceViewer.unconfigure();
			this.actionsSourceViewer = null;
		}
		if (this.condSourceViewer != null) {
			this.condSourceViewer.unconfigure();
			this.condSourceViewer = null;
		}
		super.dispose();
		this.declarationsTable.dispose();
		if (this.conditionSourceViewerConfiguration != null) {
			this.conditionSourceViewerConfiguration.unconfigure();
			this.conditionSourceViewerConfiguration = null;
		}
		if (this.actionSourceViewerConfiguration != null) {
			this.actionSourceViewerConfiguration.unconfigure();
			this.actionSourceViewerConfiguration = null;
		}
		this.dependencyDiagramAction = null;
		this.sequenceDiagramAction = null;
	}

	protected String getDescription(RulesASTNode headerNode) {
		RulesASTNode descNode = getDescriptionNode(headerNode);
		if (descNode == null) {
			return "";
		}
		List<RulesASTNode> literals = descNode.getChildrenByFeatureId(HeaderParser.LITERALS);
		StringBuffer desc = new StringBuffer();
		if (literals != null) {
			for (RulesASTNode literalNode : literals) {
				if (literalNode.getType() == HeaderParser.NEWLINE) {
					desc.append('\r');
					desc.append('\n');
				} else {
					desc.append(literalNode.getText());
				}
			}
		}
		
		return desc.toString();
	}

	protected void setupSourceViewerDecorationSupport(ISourceViewer viewer, OverviewRuler ruler, DefaultMarkerAnnotationAccess access, EntitySharedTextColors sharedColors) {
		SourceViewerDecorationSupport fSourceViewerDecorationSupport= new SourceViewerDecorationSupport(viewer, ruler, access, sharedColors);
		configureSourceViewerDecorationSupport(fSourceViewerDecorationSupport);
	}
	
	@SuppressWarnings("unchecked")
	private void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		Iterator e= EditorsPlugin.getDefault().getMarkerAnnotationPreferences().getAnnotationPreferences().iterator();
		while (e.hasNext()) {
			support.setAnnotationPreference((AnnotationPreference) e.next());
		}

		support.setCursorLinePainterPreferenceKeys(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR);
		support.setMarginPainterPreferenceKeys(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLOR, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLUMN);
//		support.setSymbolicFontName(getFontPropertyPreferenceKey());
		
		DefaultCharacterPairMatcher matcher = new DefaultCharacterPairMatcher(RulesEditor.CHARACTER_PAIRS, IDocument.DEFAULT_CONTENT_TYPE);
		support.setCharacterPairMatcher(matcher);

		// TODO : make these preferences/configurable
		EditorsUI.getPreferenceStore().setValue(RulesEditor.PREF_MATCHING_BRACKETS, true);
		EditorsUI.getPreferenceStore().setValue(RulesEditor.PREF_MATCHING_BRACKETS_COLOR, "192,192,192");
		support.setMatchingCharacterPainterPreferenceKeys(RulesEditor.PREF_MATCHING_BRACKETS, RulesEditor.PREF_MATCHING_BRACKETS_COLOR);

		support.install(EditorsUI.getPreferenceStore());
	}

	/**
	 * @param titleImage
	 */
	public void setTitleAndImage(Image titleImage, String title){
		managedForm.getForm().setText(title);
		managedForm.getForm().setImage(titleImage);
	}
	
    /**
     * 
     * @param container
     */
    protected void createPartControl(Composite container,String title,Image titleImage){
    	
    	managedForm = new ManagedForm(container);
		final ScrolledForm form = managedForm.getForm();
		
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(title);
		form.setImage(titleImage);
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		
		createConfigurationPart(form, toolkit);
		
		sashForm = new MDSashForm(form.getBody(), SWT.VERTICAL);
		
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createDeclarationsPart(managedForm, sashForm);
		createViewsPart(managedForm, sashForm);
		createConditionsPart(managedForm, sashForm);
		createActionContextPart(managedForm, sashForm);
		createActionsPart(managedForm, sashForm);
		createScopePart(managedForm, sashForm);
		createBodyPart(managedForm, sashForm);
		
		hookResizeListener();
		
		managedForm.initialize();
    }
   
	public Control getControl() {
		return getForm();
	}
	
	protected void setDescription() {
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode headerNode = (RulesASTNode) astNode.getData("HEADER");
		if (headerNode == null) {
			System.out.println("header node not found");
			return;
		}
		RulesASTNode descNode = getDescriptionNode(headerNode);
		ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
		String description = getDescriptionText().get();
		String docDesc = convertDescription(description);
		if (descNode == null) {
			// need to add a new description node
			descNode = new HeaderASTNode(new CommonToken(HeaderParser.DESCRIPTION_STATEMENT));
			HeaderASTNode idNode = new HeaderASTNode(new CommonToken(HeaderParser.ID, docDesc));
			descNode.addChildByFeatureId(idNode, HeaderParser.LITERALS);
			TextEdit textEdit = analyzer.analyzeASTNodeAdd(descNode, headerNode, HeaderParser.STATEMENTS);
			applyTextEdit(textEdit);
			getFormEditor().modified();
		} else {
			TextEdit textEdit = analyzer.analyzeASTNodeReplace(descNode, "@description "+docDesc);
			applyTextEdit(textEdit);
			getFormEditor().modified();
		}
	}

	protected RulesASTNode getDescriptionNode(RulesASTNode headerNode) {
		List<RulesASTNode> childrenByFeatureId = headerNode.getChildrenByFeatureId(HeaderParser.STATEMENTS);
		RulesASTNode descNode = null;
		if (childrenByFeatureId != null) {
			for (RulesASTNode rulesASTNode : childrenByFeatureId) {
				if (rulesASTNode.getType() == HeaderParser.DESCRIPTION_STATEMENT) {
					descNode = rulesASTNode;
					break;
				}
			}
		}
		return descNode;
	}

	protected RulesASTNode getCurrentASTNode() {
		// TODO : potentially figure out a cleaner way to do this, perhaps wait/notify
		RulesEditor rulesEditor = getFormEditor().getRulesEditor();
		RulesASTNode astNode = rulesEditor.getRulesAST();
		if (astNode == null || astNode.isDirty()) {
			// can't perform modifications on this ast, as the offsets might not be correct
			astNode = rulesEditor.reparse();
		}
		return astNode;
	}

	protected void applyTextEdit(TextEdit textEdit) {
		IDocument document = getDocument();
		try {
			adjustOffsets(textEdit);
			textEdit.apply(document);
			// if the line at the start of the edit is now empty, delete that as well
			int offset = textEdit.getOffset();
			int lineNum = document.getLineOfOffset(offset);
			int length = document.getLineLength(lineNum);
			IRegion lineInfo = document.getLineInformationOfOffset(offset);
			String line = document.get(lineInfo.getOffset(), length);
			if (line.trim().length() == 0) {
				DeleteEdit delEdit = new DeleteEdit(lineInfo.getOffset(), length);
				adjustOffsets(delEdit);
				delEdit.apply(document);
			}
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void adjustOffsets(TextEdit textEdit) {
		if (textEdit instanceof InsertEdit) {
			adjustOffsets(textEdit.getOffset(), ((InsertEdit)textEdit).getText().length());
		} else if (textEdit instanceof DeleteEdit) {
			adjustOffsets(textEdit.getOffset(), -textEdit.getLength());
		} else if (textEdit instanceof ReplaceEdit) {
			int delta = ((ReplaceEdit) textEdit).getText().length() - textEdit.getLength();
			adjustOffsets(textEdit.getOffset(), delta);
		}
	}

	private void adjustOffsets(int offset, int offsetDelta) {
		if (fCurrentConditionsRegion != null && offset < fCurrentConditionsRegion.getOffset()) {
			fCurrentConditionsRegion = new Region(fCurrentConditionsRegion.getOffset()+offsetDelta, fCurrentConditionsRegion.getLength());
		}
		if (fCurrentActionsRegion != null && offset < fCurrentActionsRegion.getOffset()) {
			fCurrentActionsRegion = new Region(fCurrentActionsRegion.getOffset()+offsetDelta, fCurrentActionsRegion.getLength());
		}
	}

	protected IDocument getDocument() {
		return getFormEditor().getRulesEditor().getDocumentProvider().getDocument(getFormEditor().getRulesEditor().getEditorInput());
	}

	protected String convertDescription(String description) {
		return description.replaceAll("\r\n", "\r\n * ");
	}

	protected void updateRegions(RulesASTNode newNode, int rootType, int blockType) {
		RulesASTNode ruleNode = newNode.getFirstChildWithType(rootType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode thenBlock = blocksNode.getChildByFeatureId(blockType);
		if (thenBlock != null) {
			fCurrentActionsRegion = new Region(thenBlock.getOffset(), thenBlock.getLength());
		}
	}

	protected void setActionText(int rootType, int blockType) {
		RulesASTNode astNode = getCurrentASTNode();
		if(astNode ==  null) return;
		RulesASTNode ruleNode = astNode.getFirstChildWithType(rootType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode thenBlock = null;
		if (blocksNode != null) {
			thenBlock = blocksNode.getChildByFeatureId(blockType);
		}
		ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
		String source = getViewerSource(getActionSourceViewer());
		if (thenBlock == null) {
			CommonToken token = new CommonToken(blockType);
			token.setStartIndex(fCurrentActionsRegion.getOffset());
			token.setStopIndex(fCurrentActionsRegion.getOffset()+fCurrentActionsRegion.getLength()-1);
			thenBlock = new RulesASTNode(token);
			thenBlock.setOffset(fCurrentActionsRegion.getOffset());
			thenBlock.setLength(fCurrentActionsRegion.getLength());
		}
		if (thenBlock.getOffset() == 0) {
			// there are no statements under this block yet, replace the entire 'then' block
			thenBlock = (RulesASTNode) thenBlock.getParent();
			TextEdit textEdit = analyzer.analyzeASTNodeReplace(thenBlock, convertBlock(source, blockType));
			applyTextEdit(textEdit);
			fCurrentActionsRegion = new Region(textEdit.getOffset(), source.length());
		} else {
			TextEdit textEdit = analyzer.analyzeASTNodeReplace(thenBlock, source);
			applyTextEdit(textEdit);
			fCurrentActionsRegion = new Region(textEdit.getOffset(), textEdit.getLength());
		}
		setSourceSynchronized(false);
//		checkForErrors(rootType, blockType);
	}

	protected void checkForErrors(int rootType, int blockType) {
		Boolean isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioPreferenceConstants.RULE_FORM_INTIAL_PAGE);
		boolean formEditorUsable = true;
		String message = "";
		if (getFormEditor().rulesEditor.hasSyntaxErrors()) {
			formEditorUsable = false;
			message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.ERROR");
		} else if (!getFormEditor().rulesEditor.hasConditionsSection()) {
			formEditorUsable = false;
			message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.NoConditionsSection.ERROR");
		} else if (!getFormEditor().rulesEditor.hasActionsSection()) {
			formEditorUsable = false;
			message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.NoActionsSection.ERROR");
		}
		if (!formEditorUsable) {
			// force a page switch
			MessageDialog.openError(getFormEditor().getSite().getShell(), Messages.getString("RuleFormEditor.ERROR"), message);
			if (isFormFirstPage) {
				getFormEditor().activePage(1);
			} else {
				getFormEditor().activePage(0);
			}
		}
	}

	protected String getViewerSource(SourceViewer sourceViewer) {
		String source = sourceViewer.getDocument().get();
		int idx = source.length() - 1;
		boolean foundNewline = false;
		while (idx >= 0) {
			char c = source.charAt(idx);
			if (c == '\n') {
				foundNewline = true;
				break;
			}
			if (!Character.isWhitespace(c)) {
				break;
			}
			idx--;
		}
		if (!foundNewline) {
			source += "\r\n\t"; // add newline to avoid potential issues with comments on the last line
		}
		return "{\r\n" + source + "}";
	}

	protected String convertBlock(String source, int blockType) {
		StringBuffer buffer = new StringBuffer();
		switch (blockType) {
		case RulesParser.WHEN_BLOCK:
			buffer.append("when {\r\n\t\t");
			break;

		case RulesParser.THEN_BLOCK:
			buffer.append("then {\r\n\t\t");
			break;
			
		case RulesParser.BODY_BLOCK:
			buffer.append("body {\r\n\t\t");
			break;
			
		default:
			break;
		}
		buffer.append(source);
		buffer.append("\r\n\t}");
		return buffer.toString();
	}

	protected void updateDeclarationsSection() {
		// declarations section
		declarationsTable.deselectAll();
		getRemoveDeclarationButton().setEnabled(false);	
		getUpDeclarationButton().setEnabled(false);
		getDownDeclarationButton().setEnabled(false);
		TableItem[] items = declarationsTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		// calling .values() does not retrieve Symbols in proper order
//		Collection<Symbol> symbols = getCompilable().getSymbols().getSymbolMap().values();
//		for(Symbol symbol:symbols){
//		EMap<String,Symbol> symbolMap = getCompilable().getSymbols().getSymbolMap();
//		int size = symbolMap.size();
		for (Symbol symbol: getCompilable().getSymbols().getSymbolList()) {
//			Symbol symbol = symbolMap.get(i).getValue();
			TableItem tableItem = new TableItem(declarationsTable, SWT.NONE);
			DesignerElement element = IndexUtils.getElement(getFormEditor().getProject().getName(), symbol.getType());
			if (element != null) {
				if (element.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				} else if (element.getElementType() == ELEMENT_TYPES.TIME_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
				} else if (element.getElementType() == ELEMENT_TYPES.CONCEPT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
				}else if (element.getElementType() == ELEMENT_TYPES.SCORECARD) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
				}
			}else{
					for(PROPERTY_TYPES property_types:PROPERTY_TYPES.values()){
						if(property_types.getName().equalsIgnoreCase(symbol.getType())){
							Image image = getPropertyImage(property_types);
							if(image!=null){
								tableItem.setImage(image);
								break;
							}
						}
					}
					if(symbol.getType().equalsIgnoreCase("Concept")){
						tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
					}
					if(symbol.getType().equalsIgnoreCase("object")){
						tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/no_type.png"));
					}
					if(symbol.getType().equalsIgnoreCase("Event") || 
							symbol.getType().equalsIgnoreCase("SimpleEvent") || 
							      symbol.getType().equalsIgnoreCase("AdvisoryEvent")){
						tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
					}
					if(symbol.getType().equalsIgnoreCase("TimeEvent")){
						tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
					}
							
			}
			tableItem.setText(0, symbol.isArray()? symbol.getType() + "[]": symbol.getType());
			tableItem.setText(1, symbol.getIdName());
			addAdditionalSymbolInfo(symbol, tableItem);
		}		
	}

	protected void addAdditionalSymbolInfo(Symbol symbol, TableItem tableItem) {
	}

	@SuppressWarnings("unchecked")
	public List<GlobalVariableDef> getGlobalVariableDefs() {
		final Object[] ret = new Object[1];
		Display.getDefault().syncExec(new Runnable() {
		
			@Override
			public void run() {
				List<GlobalVariableDef> defs = new ArrayList<GlobalVariableDef>();
				TableItem[] items = declarationsTable.getItems();
				for (TableItem tableItem : items) {
					String type = tableItem.getText(0);
					String name = tableItem.getText(1);
					GlobalVariableExtension gv = new GlobalVariableExtension(getFormEditor().getProject().getName());
					gv.setName(name);
					int idx = type.indexOf("[]");
					if (idx > 0) {
						type = type.substring(0, idx);
						gv.setArray(true);
					}
					gv.setType(ModelUtils.convertPathToPackage(type));
					defs.add(gv);
				}
				ret[0] = defs;
			}
		});
		return (List<GlobalVariableDef>) ret[0];
	}
	

	@Override
	public IResolutionContext getResolutionContext(ElementReference elementReference) {
		ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
		SimpleResolutionContext context = new SimpleResolutionContext(scope);
		List<GlobalVariableDef> globalDefs = getGlobalVariableDefs();
		for (GlobalVariableDef globalVariableDef : globalDefs) {
			context.addGlobalVariable(globalVariableDef);
		}
		return context;
	}

	@Override
	public IResolutionContext getResolutionContext(ElementReference reference,
			ScopeBlock scope) {
		SimpleResolutionContext context = new SimpleResolutionContext(scope);
		List<GlobalVariableDef> globalDefs = getGlobalVariableDefs();
		for (GlobalVariableDef globalVariableDef : globalDefs) {
			context.addGlobalVariable(globalVariableDef);
		}
		return context;
	}

	/**
	 * @param parent
	 * @param updown
	 * @return
	 */
	protected ToolBar createToolbar(Composite parent, boolean updown) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        
        addDeclButton = new ToolItem(toolBar, SWT.PUSH);
        Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_ADD);
        addDeclButton.setImage(addImg);
        addDeclButton.setToolTipText("Add");
        addDeclButton.setText("Add");
        
        addDeclButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				add();
			}
		});
        
        removeDeclButton = new ToolItem(toolBar, SWT.PUSH);
        Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
        removeDeclButton.setImage(delImg);
        removeDeclButton.setToolTipText("Delete");
        removeDeclButton.setText("Delete");
        removeDeclButton.setEnabled(false);
        removeDeclButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				stopEditing();
				remove();
			}
		});
        
        if (updown) {
        	upDeclButton = new ToolItem(toolBar, SWT.PUSH);
        	Image upImg = EditorsUIPlugin.getDefault().getImage("icons/arrow_up.png");
        	upDeclButton.setImage(upImg);
        	upDeclButton.setToolTipText("Up");
        	upDeclButton.setText("Up");
        	upDeclButton.setEnabled(false);
        	upDeclButton.addListener(SWT.Selection, new Listener() {

        		@Override
        		public void handleEvent(Event event) {
        			stopEditing();
        			up();
        		}
        	});
        	downDeclButton = new ToolItem(toolBar, SWT.PUSH);
        	Image downImg = EditorsUIPlugin.getDefault().getImage("icons/arrow_down.png");
        	downDeclButton.setImage(downImg);
        	downDeclButton.setEnabled(false);
        	downDeclButton.setToolTipText("Down");
        	downDeclButton.setText("Down");
        	downDeclButton.addListener(SWT.Selection, new Listener() {

        		@Override
        		public void handleEvent(Event event) {
        			stopEditing();
        			down();
        		}
        	});
        }
        toolBar.pack();
        return toolBar;
	}
	
	protected void stopEditing() {
		if (declarationsTable != null) {
			declarationsTable.forceFocus();
		}
	}

	protected abstract void add();
	protected abstract void remove();
	protected abstract void up();
	protected abstract void down();
	
	protected void removeDeclarator(int ruleType, int blockType, int statementType) {
		removeDeclarator(ruleType, blockType, statementType, 0, 1);
	}
	
	protected void removeDeclarator(int ruleType, int blockType, int statementType, int typeCol, int idCol) {
		TableItem[] selection = declarationsTable.getSelection();
		for (TableItem tableItem : selection) {
			String type = tableItem.getText(typeCol);
			String idName = tableItem.getText(idCol);
			boolean isArray = false; 
			type = ModelUtils.convertPathToPackage(type);
			
			//Check for Array Type
			if (type.endsWith("[]")) {
				isArray = true;
			}
			
			int index = declarationsTable.getSelectionIndex();
			if (index !=-1) {
				removeDeclaratorFromModel(index);
			}
			removeDeclareStatement(type, idName, ruleType, blockType, statementType, isArray);
			tableItem.dispose();
		}
		refreshRulesFormSourceViewerConfiguration();
		
		if (declarationsTable.getItemCount() > 0) {
			declarationsTable.deselectAll();
		}
		getRemoveDeclarationButton().setEnabled(false);
	}
	
	protected void removeDeclaratorFromModel(int index) {
		getCompilable().getSymbols().getSymbolList().remove(index);
	}

	/**
	 * @param ruleType
	 * @param blockType
	 * @param statementType
	 * @param up
	 */
	protected void reorderDeclarators(int ruleType, int blockType, int statementType, boolean up) {

		int index = declarationsTable.getSelectionIndex();
		if(index == -1){
			return;
		}
		int swapIndex = -1 ;
		if(up){
			swapIndex = index - 1;
			if(swapIndex == -1){
				return;
			}
		}else{
			swapIndex = index + 1;
			if(swapIndex == declarationsTable.getItemCount()){
				return;
			}
		}
		Symbol symbol = getCompilable().getSymbols().getSymbolList().get(index);
		Symbol prevSymbol = getCompilable().getSymbols().getSymbolList().get(swapIndex);
		getCompilable().getSymbols().getSymbolList().set(swapIndex, symbol);
		getCompilable().getSymbols().getSymbolList().set(index, prevSymbol);
		updateDeclarationStatements(index, ruleType, blockType, statementType, up);
		updateDeclarationsSection();
		
		declarationsTable.setSelection(swapIndex);
		// need to manually notify listeners
		declarationsTable.notifyListeners(SWT.Selection, null);
	}


	/**
	 * @param index
	 * @param ruleType
	 * @param blockType
	 * @param statementType
	 * @param up
	 */
	public void updateDeclarationStatements(int index,
			int ruleType, 
			int blockType, 
			int statementType, 
			boolean up) {
		
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		
		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
		
		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		
		RulesASTNode currentDecASTNode = children.get(index);
		RulesASTNode swapDecASTNode = children.get(up? index - 1: index + 1);
		
		CommonTokenStream tokens = RuleGrammarUtils.getTokens(currentDecASTNode);
		String nodeString = RuleGrammarUtils.getNodeText(tokens, currentDecASTNode);
		String swapNodeString = RuleGrammarUtils.getNodeText(tokens, swapDecASTNode);
		
		if(currentDecASTNode != null && swapDecASTNode != null){
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			if(up){
				TextEdit replaceEdit = analyzer.analyzeASTNodeReplace(currentDecASTNode, swapNodeString);
				applyTextEdit(replaceEdit);
				
				replaceEdit = analyzer.analyzeASTNodeReplace(swapDecASTNode, nodeString);
				applyTextEdit(replaceEdit);
			}
			else{
				TextEdit replaceEdit = analyzer.analyzeASTNodeReplace(swapDecASTNode, nodeString);
				applyTextEdit(replaceEdit);
				
				replaceEdit = analyzer.analyzeASTNodeReplace(currentDecASTNode, swapNodeString);
				applyTextEdit(replaceEdit);
			}
		}
	}
	
//	/**
//	 * @param index
//	 * @param ruleType
//	 * @param blockType
//	 * @param statementType
//	 * @param up
//	 */
//	public void updateDeclarationStatements_old(int index,
//										    int ruleType, 
//											int blockType, 
//											int statementType, 
//											boolean up) {
//
//		RulesASTNode astNode = getCurrentASTNode();
//		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
//		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
//		
//		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
//		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
//		
//		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
//		
//		RulesASTNode currentDecASTNode = children.get(index);
//		RulesASTNode swapDecASTNode = children.get(up? index - 1: index + 1);
//		
//		RulesASTNode typeNode = currentDecASTNode.getChildByFeatureId(RulesParser.TYPE);
//		RulesASTNode nameNode = currentDecASTNode.getChildByFeatureId(RulesParser.NAME);
//		
//		String typeString = RuleGrammarUtils.getNameAsString(typeNode, RuleGrammarUtils.NAME_FORMAT);/*RuleGrammarUtils.rewriteNode(typeNode);*/
//		String nameString = RuleGrammarUtils.rewriteNode(nameNode);
//
//		RulesASTNode swapTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.TYPE);
//		RulesASTNode swapNameNode = swapDecASTNode.getChildByFeatureId(RulesParser.NAME);
//		
//		String swapTypeString =RuleGrammarUtils.getNameAsString(swapTypeNode, RuleGrammarUtils.NAME_FORMAT);/* RuleGrammarUtils.rewriteNode(swapTypeNode);*/
//		String swapNameString = RuleGrammarUtils.rewriteNode(swapNameNode);
//		
//		if(currentDecASTNode != null && swapDecASTNode != null){
//			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
//			if(up){
//				TextEdit replaceEdit = analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
//				applyTextEdit(replaceEdit);
//
//				replaceEdit = analyzer.analyzeASTNodeReplace(typeNode, swapTypeString);
//				applyTextEdit(replaceEdit);
//
//				replaceEdit = analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
//				applyTextEdit(replaceEdit);
//
//				replaceEdit = analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
//				applyTextEdit(replaceEdit);
//			}
//			else{
//				TextEdit replaceEdit = analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
//				applyTextEdit(replaceEdit);
//
//				replaceEdit = analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
//				applyTextEdit(replaceEdit);
//
//				replaceEdit = analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
//				applyTextEdit(replaceEdit);
//
//				replaceEdit = analyzer.analyzeASTNodeReplace(typeNode, swapTypeString);
//				applyTextEdit(replaceEdit);
//			}
//		}
//	}
	
	protected void selectAndAddDeclarator(int ruleType, int blockType, int statementType) {
		if(declarationsTable.getSelectionCount()>0){
			removeDeclButton.setEnabled(false);
			
		}
		String title = "";
		ELEMENT_TYPES eltype = null;
		boolean allowEntityType = true;
		boolean allowArray = true;
		switch (ruleType) {
		case RulesParser.RULE_DECL:
			title = Messages.getString("rule_declration_selector_title");
			eltype = ELEMENT_TYPES.RULE;
			break;

		case RulesParser.RULE_FUNC_DECL:
			title = Messages.getString("rule_function_scope_selector_title");
			eltype = ELEMENT_TYPES.RULE_FUNCTION;
			break;
			
		case RulesParser.RULE_TEMPLATE_DECL:
			if (blockType == RulesParser.DECLARE_BLOCK) {
				title = Messages.getString("rule_declration_selector_title");
				eltype = ELEMENT_TYPES.RULE_FUNCTION;
				allowArray = false;
			} else {
				title = Messages.getString("rule_template_binding_selector_title");
				eltype = ELEMENT_TYPES.RULE_FUNCTION;
				allowEntityType = false;
				allowArray = false;
			}
			break;
			
		default:
			break;
		}
		DeclarationSelector picker = new DeclarationSelector(Display.getDefault().getActiveShell(),
				title, getFormEditor().getProject().getName(), eltype, allowEntityType, allowArray);
		if (picker.open() == Dialog.OK) {
			try{
				String idName = picker.getIdName();
				String type = picker.getType();
				boolean isArray = picker.isArray();
				
				idName = getUniqueIdName(idName);
				
				Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
				symbol.setType(type);
				symbol.setIdName(idName);
				
				if (ruleType == RulesParser.RULE_FUNC_DECL) {
					symbol.setArray(isArray);
				}
				
				getCompilable().getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
				
				if(picker.isResource()){
					type = replace(type, "/", ".");
					type = type.substring(1);
				}

				addDeclareStatement(type, idName, ruleType, blockType, statementType, isArray);
				
				updateDeclarationsSection();
			}
			catch(Exception e ){
				e.printStackTrace();
			}
		}
	}
	
	protected String getUniqueIdName(String idName) {
		// by default, check the compilable's symbols.  Subclasses should override for other use cases,
		// for instance rule template bindings
		if(isSymbolPresent(getCompilable().getSymbols().getSymbolList(), idName.toLowerCase())){
			idName = getUniqueTag(getCompilable().getSymbols().getSymbolList(), idName+"_");
		}
		return idName;
	}

	/**
	 * @param type
	 * @param idName
	 */
	protected void addDeclareStatement(String type, String idName, int ruleType, int blockType, int statementType, boolean isArray) {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);

			RulesASTNode declNode = new RulesASTNode(new CommonToken(RulesParser.DECLARATOR));
			
			RulesASTNode typeNameNode = null;
			if (ruleType == RulesParser.RULE_FUNC_DECL && isArray) {
				type = type + "[]";
				typeNameNode = new RulesASTNode(new CommonToken(RulesParser.SIMPLE_NAME, type));
				typeNameNode.addChild(new RulesASTNode(new CommonToken(RulesParser.Identifier, type)));
			} else {
				typeNameNode = RuleGrammarUtils.createName(type);
			}
			
//			RulesASTNode typeNameNode = RuleGrammarUtils.createName(type);
			RulesASTNode declNameNode = RuleGrammarUtils.createName(idName);
			
			declNode.addChildByFeatureId(typeNameNode, RulesParser.TYPE);
			declNode.addChildByFeatureId(declNameNode, RulesParser.NAME);
			
			RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
			List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
			if (children != null && children.size() > 0) {
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				TextEdit textEdit = analyzer.analyzeASTNodeAdd(declNode, node, RulesParser.STATEMENTS);
				applyTextEdit(textEdit);
			} else {
				// there are no declarations yet, insert it
				String text = RuleGrammarUtils.rewriteNode(declNode);
				int lineOfOffset = getDocument().getLineOfOffset(declBlocksNode.getOffset()+declBlocksNode.getLength()-1);
				int offset = getDocument().getLineOffset(lineOfOffset);
				InsertEdit textEdit = new InsertEdit(offset, text);
				applyTextEdit(textEdit);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @param type
	 * @param idName
	 * @param ruleType
	 * @param blockType
	 * @param statementType
	 * @param isArray
	 */
	protected void removeDeclareStatement(String type, 
			                              String idName, 
			                              int ruleType, 
			                              int blockType, 
			                              int statementType, 
			                              boolean isArray) {
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		RulesASTNode toBeRemoved = findNode(children, type, idName, isArray);
		if (toBeRemoved != null) {
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			TextEdit deleteEdit = analyzer.analyzeASTNodeDelete(toBeRemoved);
			applyTextEdit(deleteEdit);
		}
	}

    protected RulesASTNode findNode(List<RulesASTNode> children, String type,
			String idName, boolean isArray) {
    	for (RulesASTNode child : children) {
    		RulesASTNode typeNode = child.getChildByFeatureId(RulesParser.TYPE);
    		RulesASTNode nameNode = child.getChildByFeatureId(RulesParser.NAME);
    		if (typeNode == null || nameNode == null) {
    			continue; // could be an expression in an action context
    		}
//			String typeString = RuleGrammarUtils.rewriteNode(typeNode);
    		String typeString = RuleGrammarUtils.getNameAsString(typeNode, RuleGrammarUtils.NAME_FORMAT);
    		if (isArray) { //Check for Array Type
    			typeString = typeString + "[]";
    		}
    		String nameString = RuleGrammarUtils.rewriteNode(nameNode);
    		if (typeString.equals(type) && nameString.equals(idName)) {
    			return child;
    		}
    	}
		return null;
	}

	/**
     * @param control
     * @param projectName
     * @param ruleType
     * @param blockType
     * @param statementType
     */
    protected void setDeclarationScopeDropTarget(final Control control, 
    		                     				 final int ruleType, 
    		                     				 final int blockType, 
    		                     				 final int statementType) {
    	
    	Transfer[] types = new Transfer[] { TextTransfer.getInstance(), 
    			                            FileTransfer.getInstance(), 
    			                            ResourceTransfer.getInstance(), 
    			                            LocalSelectionTransfer.getTransfer() };
    	DropTarget dropTarget = new DropTarget(control, DND.DROP_COPY | DND.DROP_MOVE);
    	dropTarget.setTransfer(types);
    	dropTarget.addDropListener(new DropTargetAdapter() {
    		/**
    		 * @param event
    		 */
    		public void drop(DropTargetEvent event) {
    			Object data  = event.data;
    			if(data instanceof TreeSelection){
    				TreeSelection selection = (TreeSelection) data;
    				for(Object obj:selection.toArray()){
    					if(obj instanceof IFile){
    						IFile file = (IFile)obj;
    						if(!validDrop(ruleType, IndexUtils.getElement(file).getElementType())){
    							return;
    						}
    						String type = IndexUtils.getFullPath(file);
    						updateDeclarataionSymbols(ruleType, blockType, statementType, type);
    					}
    					if (obj instanceof SharedEntityElement) {
    						SharedEntityElement sharedElement = (SharedEntityElement)obj;
    						if(!validDrop(ruleType, sharedElement.getElementType())){
    							return;
    						}
    						String type = sharedElement.getSharedEntity().getFullPath();
    						updateDeclarataionSymbols(ruleType, blockType, statementType, type);
    					}
    				}
    			}
    		}
    	});
    }
    
    /**
     * @param ruleType
     * @param blockType
     * @param statementType
     * @param type
     */
    private void updateDeclarataionSymbols( int ruleType, 
								    		int blockType, 
								    		int statementType, 
								    		String type){
    	String idName = type.substring( type.lastIndexOf("/") + 1).toLowerCase();

    	if( isSymbolPresent( getCompilable().getSymbols().getSymbolList(), idName.toLowerCase())){
    		idName = getUniqueTag( getCompilable().getSymbols().getSymbolList(), idName + "_");
    	}

    	Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
    	symbol.setType(type);
    	symbol.setIdName(idName);
    	getCompilable().getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);

    	type = replace(type, "/", ".").substring(1);

    	addDeclareStatement(type, idName, ruleType, blockType, statementType, false);
    	updateDeclarationsSection();
    }
    
    /**
     * @param ruleType
     * @param type
     * @return
     */
    protected boolean validDrop( final int ruleType, ELEMENT_TYPES type){
    	if(ruleType == RulesParser.RULE_DECL){
    		if(type == ELEMENT_TYPES.CONCEPT
    				|| type == ELEMENT_TYPES.SCORECARD
    				|| type == ELEMENT_TYPES.SIMPLE_EVENT
    				|| type == ELEMENT_TYPES.TIME_EVENT){
    			return true;
    		}
		}
		if(ruleType == RulesParser.RULE_FUNC_DECL){
			if(type == ELEMENT_TYPES.CONCEPT
					|| type == ELEMENT_TYPES.SCORECARD
					|| type == ELEMENT_TYPES.SIMPLE_EVENT){
				return true;
			}
		}
    	return false;
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#updateDeclarationStatements(java.lang.String, java.lang.String, java.lang.String, int, int, int, com.tibco.cep.designtime.core.model.rule.Compilable)
     */
    @Override
	public boolean updateDeclarationStatements(String type, 
										   String id,
				                           String oldText, 
				                           String newText, 
				                           int ruleType, 
				                           int blockType, 
				                           int statementType,
				                           Compilable compilable) {
    	
    	if (oldText == newText) {
    		return true;
    	}
		
		type = replace(type, "/", ".");
		type = replace(type, "[]", "");
		if (type.charAt(0) == '.') {
			type = type.substring(1);
		}
		
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		for (RulesASTNode child : children) {
			RulesASTNode typeNode = child.getChildByFeatureId(RulesParser.TYPE);
			RulesASTNode nameNode = child.getChildByFeatureId(RulesParser.NAME);
			
//			String typeString = RuleGrammarUtils.rewriteNode(typeNode);
			String typeString = RuleGrammarUtils.getNameAsString(typeNode, RuleGrammarUtils.NAME_FORMAT);
			String nameString = RuleGrammarUtils.rewriteNode(nameNode);
			
			if (typeString.equals(type) && nameString.equals(id)) {
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				TextEdit deleteEdit = analyzer.analyzeASTNodeReplace(nameNode, newText);
				applyTextEdit(deleteEdit);
				break;
			}
		}
		refreshRulesFormSourceViewerConfiguration();
		return true;
	}
    
    /**
     * @param textWidget
     */
//    protected void setKeySupport(final StyledText textWidget, final TextViewerUndoManager undoManager){
//    	
//    	textWidget.addKeyListener(new KeyAdapter(){
//			/* (non-Javadoc)
//			 * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
//			 */
//			@Override
//			public void keyReleased(KeyEvent e) {
//				if (e.stateMask == SWT.CTRL && e.keyCode == 'z') {
//					undoManager.undo();
//				} else if (e.stateMask == SWT.CTRL && e.keyCode == 'y') {
//					undoManager.redo();
//				} else if (e.stateMask == SWT.CTRL && e.keyCode == 'x') {
//					textWidget.cut();
//				} else if (e.stateMask == SWT.CTRL && e.keyCode == 'c') {
//					textWidget.copy();
//				} else if (e.stateMask == SWT.CTRL && e.keyCode == 'v') {
//					textWidget.paste();
//				} else if (e.stateMask == SWT.CTRL && e.keyCode == ' ') {
//					try {
//						if(conditionSourceViewerConfiguration != null){
//							if(condSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
//							RulesEditorContentAssistant entityContentAssistant = 
//								(RulesEditorContentAssistant)conditionSourceViewerConfiguration.getContentAssistant(condSourceViewer);
//							entityContentAssistant.showPossibleCompletions();
//							}
//						}
//						if(actionSourceViewerConfiguration != null){
//							if(actionsSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
//								RulesEditorContentAssistant entityContentAssistant = 
//									(RulesEditorContentAssistant)actionSourceViewerConfiguration.getContentAssistant(actionsSourceViewer);
//								entityContentAssistant.showPossibleCompletions();
//							}
//						}
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//				} else if (e.keyCode == SWT.F3) {
//					openDeclaration();
//				} else if (e.keyCode == SWT.HOME) {
//					StyledText text = (StyledText) e.getSource();
//					int offset = text.getSelection().y;
//					int line = text.getLineAtOffset(offset);
//					int start = text.getOffsetAtLine(line);
//					if (e.stateMask == SWT.SHIFT) {
//						text.setSelection(offset, start);
//					} else {
//						text.setSelection(start);
//					}
//				} else if (e.keyCode == SWT.END) {
//					StyledText text = (StyledText) e.getSource();
//					int offset = text.getSelection().y;
//					int line = text.getLineAtOffset(offset);
//					int start = text.getOffsetAtLine(line);
//					int delta = text.getLine(line).length();
//					if (e.stateMask == SWT.SHIFT) {
//						text.setSelection(offset, start+delta);
//					} else {
//						text.setSelection(start+delta);
//					}
//				}
//			}});
//    }
    
    @Override
    public void openDeclaration(){
    	if(condSourceViewer != null && condSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
			openElement (condSourceViewer, 
					conditionSourceViewerConfiguration.getResolutionContextProvider(), 
					condSourceViewer.getDocument(), IRulesSourceTypes.CONDITION_SOURCE, getProjectName());
		}
		if(actionsSourceViewer != null && actionsSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
			openElement (actionsSourceViewer, 
					actionSourceViewerConfiguration.getResolutionContextProvider(),
					actionsSourceViewer.getDocument(), IRulesSourceTypes.ACTION_SOURCE, getProjectName());
		}
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

	public boolean isSourceSynchronized() {
		return sourceSyncronized;
	}

	protected void setSourceSynchronized(boolean sourceModified) {
		this.sourceSyncronized = sourceModified;
	}


	public void refreshRulesFormSourceViewerConfiguration() {
		if(conditionSourceViewerConfiguration != null){
			conditionSourceViewerConfiguration.getReconciler().getReconcilingStrategy(IDocument.DEFAULT_CONTENT_TYPE).reconcile(null);
		}
		if(actionSourceViewerConfiguration != null){
			actionSourceViewerConfiguration.getReconciler().getReconcilingStrategy(IDocument.DEFAULT_CONTENT_TYPE).reconcile(null);
		}
	}

	public ToolItem getUpDeclarationButton() {
		return upDeclButton;
	}

	public ToolItem getDownDeclarationButton() {
		return downDeclButton;
	}
	
	protected abstract String getProjectName();
}