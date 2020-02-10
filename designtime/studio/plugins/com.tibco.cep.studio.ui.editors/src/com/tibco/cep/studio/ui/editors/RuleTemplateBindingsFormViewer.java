package com.tibco.cep.studio.ui.editors;


import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getUniqueTag;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.isSymbolPresent;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.RuleTemplateViewSelector;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;


public class RuleTemplateBindingsFormViewer extends AbstractRuleEntitiesFormViewer {

	private RuleTemplateFormEditor editor;
	private Section bindings;
	private Section views;
	private Table viewsTable;

	private TextViewer ruleDescTextViewer;
	private Rule rule;
	
	private Button browseButton;
	
	protected ToolItem removeViewButton;
	protected ToolItem addViewButton;
	protected ToolItem upViewButton;
	protected ToolItem downViewButton;
	protected List<String> columnNames = new ArrayList<String>();
	private RuleTemplateViewsTable ruleTemplateViewsTable;
	
	public RuleTemplateBindingsFormViewer(RuleTemplateFormEditor editor) {
		this.editor = editor;
		InitializeColumnNames();
 	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		IEditorInput editorInput = editor.getEditorInput();
		String name = editorInput.getName();
		if (name.contains(".ruletemplate")){
			String ruleName = name.substring(0, name.indexOf(".ruletemplate"));
			super.createPartControl(container, Messages.getString("ruletemplate.editor.title") + " " + ruleName, EditorsUIPlugin.getDefault().getImage("icons/rulesTemplate.png"));
			
			getForm().updateToolBar();
			super.createToolBarActions();
		}
	}

	private void InitializeColumnNames(){
		columnNames.add(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.type"));
		columnNames.add(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.alias"));
		columnNames.add(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.expression"));
		columnNames.add(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.domainmodel"));
	}
	
	protected void createDeclarationsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		bindings = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED |Section.TWISTIE);
		bindings.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		bindings.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		bindings.setText(Messages.getString("rule.template.bindings"));
	
		Composite client = toolkit.createComposite(bindings, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		client.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		
		bindings.setClient(client);
		bindings.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
					sashForm.setWeights(new int[] { 50, 50 });
				} else {
					sashForm.setWeights(new int[] { 10, 90 });
				}
			}
		});
		
		declarationsTable = CreateRuleTemplateDeclTable( toolkit , client , false , this);
		if (editor.isEnabled()) {
			ruleTemplateViewsTable.createCellEditor(columnNames);
		}
	}

	protected void doRefresh(){
		ruleTemplateViewsTable.getTableViewer().setInput(((RuleTemplate)getCompilable()).getBindings());
		
		// Dispose all existing views to avoid duplicate and then add the views again.
		
		TableItem[] items = viewsTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		
		RuleTemplate template = (RuleTemplate) getCompilable();
		EList<String> viewsList = template.getViews();
		if (viewsList != null && !viewsList.isEmpty()) {
			updateViewsTable(viewsList);
		}
		super.doRefresh();
	}
	
	protected Table CreateRuleTemplateDeclTable( FormToolkit toolkit, Composite client, boolean updown, RuleTemplateBindingsFormViewer formViewer){
		ruleTemplateViewsTable = new RuleTemplateViewsTable( toolkit , client, editor, updown,formViewer );
		return ruleTemplateViewsTable.createTable(columnNames);
	}
	
	protected void updateViewsSection() {
		// action context section
		removeViewButton.setEnabled(false);
		if (upViewButton != null) {
			upViewButton.setEnabled(false);
		}
		if (downViewButton != null) {
			downViewButton.setEnabled(false);
		}
		TableItem[] items = viewsTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		RuleTemplate template = (RuleTemplate) getCompilable();
		EList<String> viewsList = template.getViews();
		if (viewsList == null) {
			return;
		}
		updateViewsTable(viewsList);
	}

	private void updateViewsTable(EList<String> viewsList) {
		if (viewsList == null) {
			return;
		}
		for (String view : viewsList) {
			String type = view;
			TableItem tableItem = new TableItem(viewsTable, SWT.NONE);
			DesignerElement element = IndexUtils.getElement(getFormEditor().getProject().getName(), type);
			if (element == null) {
				element = IndexUtils.getRuleElement(getFormEditor().getProject().getName(), type, ELEMENT_TYPES.RULE_FUNCTION);
			}
			if (element != null) {
				if (element.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				} else if (element.getElementType() == ELEMENT_TYPES.TIME_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
				} else if (element.getElementType() == ELEMENT_TYPES.CONCEPT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
				}else if (element.getElementType() == ELEMENT_TYPES.SCORECARD) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
				}else if (element.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/rule-function.png"));
				}else if (element.getElementType() == ELEMENT_TYPES.RULE_TEMPLATE_VIEW) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/rulesTemplateView.png"));
				}
			}
			tableItem.setText(0, type);
		}
	}

	protected void createViewsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		views = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED |Section.TWISTIE);
		views.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		views.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		views.setText(Messages.getString("rule.template.views"));
	
		Composite client = toolkit.createComposite(views, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		client.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		
		views.setClient(client);
		
		createViewPropertyToolbar(client);
		
		viewsTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION);
		viewsTable.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		viewsTable.setLayoutData(gd);
			
		TableColumn termColumn = new TableColumn(viewsTable, SWT.NULL);
		termColumn.setText(Messages.getString("ruletemplate.variable.col.name"));
		termColumn.setWidth(300);

		viewsTable.setLinesVisible(true);
		viewsTable.setHeaderVisible(true);
		
		addRemoveControlListener(this, editor.isEnabled());

		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(viewsTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		viewsTable.setLayout(autoTableLayout);

		StudioUIUtils.addRemoveControlListener(this, editor.isEnabled());
		if(editor.isEnabled()){
			setDeclarationTableEditableSupport(viewsTable, 
					1, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.BINDINGS_VIEWS_BLOCK, 
					RulesParser.NAME, 
					this, 
					true);

			setDeclarationScopeDropTarget(viewsTable, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.BINDINGS_VIEWS_BLOCK, 
					RulesParser.NAME);
		}
	
	}
	
	protected void removeViewStatement(int ruleType, int blockType, int statementType) {
		TableItem[] selection = viewsTable.getSelection();
		for (TableItem tableItem : selection) {
			String type = tableItem.getText(0);
			boolean isArray = false; 
			type = ModelUtils.convertPathToPackage(type);
			
			//Check for Array Type
			if (type.endsWith("[]")) {
				isArray = true;
			}
			
			int index = viewsTable.getSelectionIndex();
			if (index !=-1) {
				((RuleTemplate)getCompilable()).getViews().remove(index);
			}
			removeViewStatement(type, ruleType, blockType, statementType, isArray);
			tableItem.dispose();
		}
//		refreshRulesFormSourceViewerConfiguration();
		
		if (viewsTable.getItemCount() > 0) {
			viewsTable.deselectAll();
		}
		removeViewButton.setEnabled(false);
	}

	@Override
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
	protected void removeViewStatement(String type, 
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
		for (RulesASTNode child : children) {
			RulesASTNode nameNode = child.getChildByFeatureId(RulesParser.NAME);
			String nameString = RuleGrammarUtils.rewriteNode(nameNode);
			if (nameString.equals(type)) {
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				TextEdit deleteEdit = analyzer.analyzeASTNodeDelete(child);
				applyTextEdit(deleteEdit);
				break;
			}
		}
	}

	protected void selectAndAddView(int ruleType, int blockType, int statementType) {
		if(declarationsTable.getSelectionCount()>0){
			removeViewButton.setEnabled(false);
		}
		RuleTemplateViewSelector picker = new RuleTemplateViewSelector(Display.getDefault().getActiveShell(),
				getFormEditor().getProject().getName(), null);
		if (picker.open() == Dialog.OK) {
			try{
				Object firstResult = picker.getFirstResult();
				if (!(firstResult instanceof String)) {
					return;
				}
				String addedView = (String) firstResult;
				RuleTemplate compilable = (RuleTemplate) getCompilable();
				EList<String> currentViews = compilable.getViews();
				for (String currView : currentViews) {
					if (addedView.equals(currView)) {
						System.out.println("View already added");
						return;
					}
				}
				
				currentViews.add(addedView);
				addViewStatement(ModelUtils.convertPathToPackage(addedView));
				updateViewsSection();
			}
			catch(Exception e ){
				e.printStackTrace();
			}
		}
	}
	
	protected void addViewStatement(String view) {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(RulesParser.BINDINGS_VIEWS_BLOCK);

			RulesASTNode declNode = new RulesASTNode(new CommonToken(RulesParser.BINDING_VIEW_STATEMENT));
			RulesASTNode nameNode = RuleGrammarUtils.createName(view);
			declNode.addChildByFeatureId(nameNode, RulesParser.NAME);
			
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
	
	@Override
	protected void updateDeclarationsSection() {
		// this is the bindings section
		declarationsTable.deselectAll();
		ArrayList<Binding> bindList = new ArrayList<Binding>();
		
	/*	if (getRemoveDeclarationButton() != null) {
			getRemoveDeclarationButton().setEnabled(false);	
		}*/
		if (getUpDeclarationButton() != null) {
			getUpDeclarationButton().setEnabled(false);
		}
		if (getDownDeclarationButton() != null) {
			getDownDeclarationButton().setEnabled(false);
		}
		for (Binding symbol: ((RuleTemplate)getCompilable()).getBindings()) {
					
				Binding bind  = RuleFactory.eINSTANCE.createBinding();
				bind.setType(symbol.getType());
				bind.setIdName(symbol.getIdName());
				bind.setExpression(symbol.getExpression());
				bind.setDomainModelPath(symbol.getDomainModelPath());
				bindList.add(bind);
		}
		ruleTemplateViewsTable.getTableViewer().setInput(bindList);
		ruleTemplateViewsTable.getTableViewer().refresh();
	}

	@Override
	protected void addAdditionalSymbolInfo(Symbol symbol, TableItem tableItem) {
		if (symbol instanceof RuleTemplateSymbol) {
			tableItem.setText(2, ((RuleTemplateSymbol) symbol).getExpression());
		}
	}

	@Override
	protected AbstractRuleFormEditor getFormEditor() {
		return editor;
	}

	@Override
	protected IDocument getDescriptionText() {
		return ruleDescTextViewer.getDocument();
	}

	@Override
	protected SourceViewer getActionSourceViewer() {
		return actionsSourceViewer;
	}

	@Override
	protected Compilable getCompilable() {
		updateRule();
		return rule;
	}

	@Override
	public Compilable getCommonCompilable() {
		updateRule();
		return rule;
	}
	
	public void updateRule() {
		RulesASTNode newNode = getCurrentASTNode();
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(true, getFormEditor().getProject().getName());
		newNode.accept(visitor);

		Compilable compile = visitor.getRule();
		if(compile instanceof Rule){
			rule = (Rule)compile;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.AbstractRuleEntitiesFormViewer#getProjectName()
	 */
	protected String getProjectName(){
		return editor.getProject().getName();
	}
	
	private void readOnlyWidgets(){
		removeDeclButton.setEnabled(false);
		addDeclButton.setEnabled(false);
		if (upDeclButton != null) {
			upDeclButton.setEnabled(false);
		}
		if (downDeclButton != null) {
			downDeclButton.setEnabled(false);
		}
		if (browseButton != null) {
			browseButton.setEnabled(false);
		}
		removeViewButton.setEnabled(false);
		addViewButton.setEnabled(false);
		if (upViewButton != null) {
			upViewButton.setEnabled(false);
		}
		if (downViewButton != null) {
			downViewButton.setEnabled(false);
		}
	}

	protected void removeDeclaratorFromModel(int index) {
		((RuleTemplate)getCompilable()).getBindings().remove(index);
	}

	@Override
	public void updateDeclarationStatements(int index, int ruleType,
			int blockType, int statementType, boolean up) {
		if (statementType == RulesParser.BINDING_STATEMENT) {
			updateExpression(index, ruleType, blockType, statementType,
					up);
		} else if (statementType == RulesParser.DOMAIN_MODEL) {
			updateExpression(index, ruleType, blockType, statementType,
					up);
		} else {
			super.updateDeclarationStatements(index, ruleType, blockType, statementType,
					up);
		}
	}

	private void updateExpression(int index, int ruleType, int blockType,
			int statementType, boolean up) {
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		
		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
		
		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		
		RulesASTNode currentDecASTNode = children.get(index);
		RulesASTNode swapDecASTNode = children.get(up? index - 1: index + 1);
		
		RulesASTNode actionTypeNode = currentDecASTNode.getChildByFeatureId(RulesParser.ACTION_TYPE);
		RulesASTNode typeNode = currentDecASTNode.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode nameNode = currentDecASTNode.getChildByFeatureId(RulesParser.NAME);
		
		String actionTypeString = actionTypeNode.getText();
		String typeString = RuleGrammarUtils.getNameAsString(typeNode, RuleGrammarUtils.NAME_FORMAT);/*RuleGrammarUtils.rewriteNode(typeNode);*/
		String nameString = RuleGrammarUtils.rewriteNode(nameNode);

		RulesASTNode swapActionTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.ACTION_TYPE);
		RulesASTNode swapTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode swapNameNode = swapDecASTNode.getChildByFeatureId(RulesParser.NAME);
		
		String swapActionTypeString = swapActionTypeNode.getText();
		String swapTypeString =RuleGrammarUtils.getNameAsString(swapTypeNode, RuleGrammarUtils.NAME_FORMAT);/* RuleGrammarUtils.rewriteNode(swapTypeNode);*/
		String swapNameString = RuleGrammarUtils.rewriteNode(swapNameNode);

		if(currentDecASTNode != null && swapDecASTNode != null){
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			if(up){
				analyzer.analyzeASTNodeReplace(actionTypeNode, swapActionTypeString);
				analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
				analyzer.analyzeASTNodeReplace(typeNode, swapTypeString);
				analyzer.analyzeASTNodeReplace(swapActionTypeNode, actionTypeString);
				analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
				analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
				
				applyTextEdit(analyzer.getCurrentEdit());
			}
			else{
				analyzer.analyzeASTNodeReplace(swapActionTypeNode, actionTypeString);
				analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
				analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
				analyzer.analyzeASTNodeReplace(actionTypeNode, swapActionTypeString);
				analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
				analyzer.analyzeASTNodeReplace(typeNode, swapTypeString);
				
				applyTextEdit(analyzer.getCurrentEdit());
			}
		}
	}

	@SuppressWarnings("unused")
	private void updateConstraint(int index, int ruleType, int blockType,
			int statementType, boolean up) {
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		
		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
		
		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		
		RulesASTNode currentDecASTNode = children.get(index);
		RulesASTNode swapDecASTNode = children.get(up? index - 1: index + 1);
		
		RulesASTNode actionTypeNode = currentDecASTNode.getChildByFeatureId(RulesParser.ACTION_TYPE);
		RulesASTNode typeNode = currentDecASTNode.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode nameNode = currentDecASTNode.getChildByFeatureId(RulesParser.NAME);
		
		String actionTypeString = actionTypeNode.getText();
		String typeString = RuleGrammarUtils.getNameAsString(typeNode, RuleGrammarUtils.NAME_FORMAT);/*RuleGrammarUtils.rewriteNode(typeNode);*/
		String nameString = RuleGrammarUtils.rewriteNode(nameNode);
		
		RulesASTNode swapActionTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.ACTION_TYPE);
		RulesASTNode swapTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode swapNameNode = swapDecASTNode.getChildByFeatureId(RulesParser.NAME);
		
		String swapActionTypeString = swapActionTypeNode.getText();
		String swapTypeString =RuleGrammarUtils.getNameAsString(swapTypeNode, RuleGrammarUtils.NAME_FORMAT);/* RuleGrammarUtils.rewriteNode(swapTypeNode);*/
		String swapNameString = RuleGrammarUtils.rewriteNode(swapNameNode);
		
		if(currentDecASTNode != null && swapDecASTNode != null){
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			if(up){
				analyzer.analyzeASTNodeReplace(actionTypeNode, swapActionTypeString);
				analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
				analyzer.analyzeASTNodeReplace(typeNode, swapTypeString);
				analyzer.analyzeASTNodeReplace(swapActionTypeNode, actionTypeString);
				analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
				analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
				
				applyTextEdit(analyzer.getCurrentEdit());
			}
			else{
				analyzer.analyzeASTNodeReplace(swapActionTypeNode, actionTypeString);
				analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
				analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
				analyzer.analyzeASTNodeReplace(actionTypeNode, swapActionTypeString);
				analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
				analyzer.analyzeASTNodeReplace(typeNode, swapTypeString);
				
				applyTextEdit(analyzer.getCurrentEdit());
			}
		}
	}
	
	@Override
	public boolean updateDeclarationStatements(String type, String id, String oldText,
			String newText, int ruleType, int blockType, int statementType,
			Compilable compilable) {
		if (oldText == newText) {
			return true; // nothing to do
		}
		if (statementType == RulesParser.EXPRESSION || statementType == RulesParser.ACTION_TYPE) {
			updateExpression(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
		} else if (statementType == RulesParser.DOMAIN_MODEL) {
			if (!validateDomainModel(newText)) {
				return false;
			}
			updateExpression(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
		} else {
			super.updateDeclarationStatements(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
		}
		return true;
	}

	private boolean validateDomainModel(String type) {
		if (type == null || type.trim().length() == 0) {
			return true;
		}
		type = replace(type, "/", ".");
		if (type.charAt(0) == '.') {
			type = type.substring(1);
		}
		if (RulesParserManager.parseName(type) == null) {
			return false;
		}
		return true;
	}

	private boolean validateExpression(String newText) {
		if (RulesParserManager.parseName(newText) == null) {
			return false;
		}
		return true;
	}
	
	private void updateExpression(String type, String id, String oldText,
			String newText, int ruleType, int blockType, int statementType,
			Compilable compilable) {
		
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
		if (statementType == RulesParser.DOMAIN_MODEL) {
			newText = ModelUtils.convertPathToPackage(newText);
		}
		for (RulesASTNode child : children) {
			RulesASTNode typeNode = child.getChildByFeatureId(RulesParser.TYPE);
			RulesASTNode nameNode = child.getChildByFeatureId(RulesParser.NAME);
			
//			String typeString = RuleGrammarUtils.rewriteNode(typeNode);
			String typeString = RuleGrammarUtils.getNameAsString(typeNode, RuleGrammarUtils.NAME_FORMAT);
			String nameString = RuleGrammarUtils.rewriteNode(nameNode);
			
			if (typeString.equals(type) && nameString.equals(id)) {
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				RulesASTNode exprNode = child.getChildByFeatureId(statementType);
				if (exprNode == null) {
					String newExpr = newText;
					if (statementType == RulesParser.EXPRESSION) {
						newExpr = nameString + " = " + newText;
						TextEdit repEdit = analyzer.analyzeASTNodeReplace(nameNode, newExpr);
						applyTextEdit(repEdit);
					} else if (statementType == RulesParser.DOMAIN_MODEL) {
						exprNode = child.getChildByFeatureId(RulesParser.EXPRESSION);
						if (exprNode == null) {
							newExpr = nameString + " (" + newExpr + ")";
							TextEdit repEdit = analyzer.analyzeASTNodeReplace(nameNode, newExpr);
							applyTextEdit(repEdit);
						} else {
							CommonTokenStream tokens = RuleGrammarUtils.getTokens(exprNode);
							String expr = RuleGrammarUtils.getNodeText(tokens, exprNode);
							newExpr = expr + " (" + newExpr + ")";
							TextEdit repEdit = analyzer.analyzeASTNodeReplace(exprNode, newExpr);
							applyTextEdit(repEdit);
						}
					}
				} else {
					if (newText.length() > 0) {
						if (statementType == RulesParser.DOMAIN_MODEL) {
							newText = "(" + newText + ")";
						}
						TextEdit repEdit = analyzer.analyzeASTNodeReplace(exprNode, newText);
						applyTextEdit(repEdit);
					} else {
						// The expression was removed, delete it
						String newExpr = "";
						if (statementType == RulesParser.EXPRESSION) {
							exprNode = child.getChildByFeatureId(RulesParser.DOMAIN_MODEL);
							if (exprNode == null) {
								newExpr = typeString + " " + nameString + ";";
							} else {
								CommonTokenStream tokens = RuleGrammarUtils.getTokens(exprNode);
								String expr = RuleGrammarUtils.getNodeText(tokens, exprNode);
								newExpr = typeString + " " + nameString + " " + expr + ";";
							}
							TextEdit repEdit = analyzer.analyzeASTNodeReplace(child, newExpr);
							applyTextEdit(repEdit);
						} else if (statementType == RulesParser.DOMAIN_MODEL) {
							newExpr = "(" + newText + ")";
							TextEdit repEdit = analyzer.analyzeASTNodeDelete(exprNode);
							applyTextEdit(repEdit);
						}
					}
				}
				break;
			}
		}
		refreshRulesFormSourceViewerConfiguration();
	}

	private void addRemoveControlListener(IStudioRuleSourceCommon ruleFormEditor, final boolean isEditorEnabled) {
		viewsTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int selectionCount = viewsTable.getSelectionCount();
				
				final int selectionIndex=viewsTable.getSelectionIndex();
				final int itemcount=viewsTable.getItemCount();
				if (!isEditorEnabled) {
					return;
				}

				if (selectionCount > 0 ) {

					removeViewButton.setEnabled(true);
					if(itemcount>1){
						if(selectionIndex==0){
							if (upViewButton != null) {
								upViewButton.setEnabled(false);
							}
							if (downViewButton != null) {
								downViewButton.setEnabled(true);
							}
						}else if(selectionIndex==itemcount-1){
							if (upViewButton != null) {
								upViewButton.setEnabled(true);
							}
							if (downViewButton != null) {
								downViewButton.setEnabled(false);
							}
						}else{
							if (upViewButton != null) {
								upViewButton.setEnabled(true);
							}
							if (downViewButton != null) {
								downViewButton.setEnabled(true);
							}
						}}
				} else {
					removeViewButton.setEnabled(false);
					if (upViewButton != null) {
						upViewButton.setEnabled(false);
					}
					if (downViewButton != null) {
						downViewButton.setEnabled(false);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}
	
	protected void createButtons(final FormToolkit toolkit, 
			final Composite composite,boolean updown) {
		Composite base = toolkit.createComposite(composite,SWT.WRAP);
		GridLayout layout = new GridLayout(1, false);
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;

		base.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		base.setLayoutData(gd);
		createToolbar(base, updown);
	}

	protected ToolBar createViewPropertyToolbar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
		toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		addViewButton = new ToolItem(toolBar, SWT.PUSH);
		Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_ADD);
		addViewButton.setImage(addImg);
		addViewButton.setToolTipText("Add");
		addViewButton.setText("Add");

		addViewButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				selectAndAddView(RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_VIEWS_BLOCK, RulesParser.DECLARATOR);
			}
		});

		removeViewButton = new ToolItem(toolBar, SWT.PUSH);
		Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
		removeViewButton.setImage(delImg);
		removeViewButton.setToolTipText("Delete");
		removeViewButton.setText("Delete");
		removeViewButton.setEnabled(false);
		removeViewButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				removeViewStatement(RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_VIEWS_BLOCK, RulesParser.BINDING_VIEW_STATEMENT);
			}
		});
		toolBar.pack();
		return toolBar;
	}

	@Override
	protected void createActionContextPart(final IManagedForm managedForm,Composite parent) {
	}
	
	@Override
	protected void createActionsPart(final IManagedForm managedForm, Composite parent) {
	}
	
	@Override
	protected void createConfigurationPart(final ScrolledForm form,final FormToolkit toolkit) {
	}	
	
	@Override
	protected void createConditionsPart(final IManagedForm managedForm, Composite parent) {
	}

	@Override
	protected void add() {
		selectAndAddDeclarator(RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_BLOCK, RulesParser.BINDING_STATEMENT);
	}

	@Override
	protected String getUniqueIdName(String idName) {
		// get unique binding id
		EList<Binding> bindings = ((RuleTemplate)getCompilable()).getBindings();
		List<Symbol> allSymbols = new ArrayList<Symbol>();
		allSymbols.addAll(bindings);
		allSymbols.addAll(getCompilable().getSymbols().getSymbolList());
		if(isSymbolPresent(allSymbols, idName.toLowerCase())){
			idName = getUniqueTag(allSymbols, idName+"_");
		}
		return idName;
	}

	@Override
	protected void remove() {
		removeDeclarator(RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_BLOCK, RulesParser.BINDING_STATEMENT);
	}

	@Override
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

		}
		refreshRulesFormSourceViewerConfiguration();
		
		
		updateDeclarationsSection();
		if( declarationsTable.getItemCount() == 0){
		getRemoveDeclarationButton().setEnabled(false);
		}
		
	}
	
	@Override
	protected void up() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void down() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean isEditorDirty() {
		return editor.isDirty();
	}

	@Override
	protected boolean isEditorEnabled() {
		return editor.isEnabled();
	}

	@Override
	protected void init() {
		initialized = true;
		// init will occur in updateWidgets call
	}

	@Override
	protected void setCompilable(Compilable compilable) {
		if(compilable instanceof Rule){
			rule = (Rule)compilable;
		}
	}

	@Override
	protected void updateSourceViewers(boolean editable) {
		// no source viewers here
	}

	@Override
	protected void updateWidgets(boolean isDirtyBeforeRefresh,
			RulesASTNode newNode, boolean editable) {
		updateDeclarationsSection();
		updateViewsSection();
		
		//Making readonly widgets
		if(!editor.isEnabled()){
			readOnlyWidgets();
		}
		
		//Editor dirty handle with Refresh Form Editors
		if(!isDirtyBeforeRefresh && editor.isDirty()){
			editor.setModified(false);
			editor.firePropertyChange();
		}
	}

}