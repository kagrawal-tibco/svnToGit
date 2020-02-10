package com.tibco.cep.studio.ui.editors;


import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getUniqueTag;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.isSymbolPresent;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.dialog.EntityQualifiedNameSelector;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;


public class RuleTemplateDisplayFormViewer extends AbstractRuleEntitiesFormViewer {

	private RuleTemplateFormEditor editor;
	private Section displayProperties;

	private TextViewer ruleDescTextViewer;
	private Rule rule;
	
	private Button browseButton;
	
	public RuleTemplateDisplayFormViewer(RuleTemplateFormEditor editor) {
		this.editor = editor;
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

	protected void createDeclarationsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		displayProperties = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED |Section.TWISTIE);
		displayProperties.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		displayProperties.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		displayProperties.setText(Messages.getString("rule.template.displayProperties"));
	
		Composite client = toolkit.createComposite(displayProperties, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		client.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		
		displayProperties.setClient(client);
//		displayProperties.addExpansionListener(new ExpansionAdapter() {
//			public void expansionStateChanged(ExpansionEvent e) {
//				if(e.getState() == true ){
//					sashForm.setWeights(new int[] { 50, 50 });
//				} else {
//					sashForm.setWeights(new int[] { 10, 90 });
//				}
//			}
//		});
		createButtons(toolkit, client, false);
		
		declarationsTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION);
		declarationsTable.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		declarationsTable.setLayoutData(gd);
//		declarationsTable.setFont(declfont);

		TableColumn propertyColumn = new TableColumn(declarationsTable, SWT.NULL);
		propertyColumn.setText(Messages.getString("Property"));
		
		TableColumn displayTextColumn = new TableColumn(declarationsTable, SWT.NULL);
		displayTextColumn.setText(Messages.getString("DisplayText"));
//		termColumn.setWidth(429);

//		TableColumn hiddenColumn = new TableColumn(declarationsTable, SWT.NULL);
//		hiddenColumn.setText(Messages.getString("display.Hidden"));
//		aliasColumn.setWidth(430);
		
		declarationsTable.setLinesVisible(true);
		declarationsTable.setHeaderVisible(true);
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(declarationsTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(2));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		
		declarationsTable.setLayout(autoTableLayout);

		StudioUIUtils.addRemoveControlListener(this, editor.isEnabled());
        
		if(editor.isEnabled()){
//			setDeclarationTableEditableSupport(declarationsTable, 
//					0, null, 
//					RulesParser.RULE_TEMPLATE_DECL, 
//					RulesParser.DISPLAY_BLOCK, 
//					RulesParser.NAME, 
//					this, 
//					true,
//					false);

			setDeclarationTableEditableSupport(declarationsTable, 
					1, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.DISPLAY_BLOCK, 
					RulesParser.LITERAL, 
					this, 
					true,
					false);
			
			setDeclarationTableEditableSupport(declarationsTable, 
					2, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.DISPLAY_BLOCK, 
					RulesParser.DOMAIN_MODEL, 
					this, 
					true,
					false);
			
		}
	
	}

	@Override
	protected void updateDeclarationsSection() {
		// this is the display properties section
		declarationsTable.deselectAll();
		if (getRemoveDeclarationButton() != null) {
			getRemoveDeclarationButton().setEnabled(false);	
		}
		if (getUpDeclarationButton() != null) {
			getUpDeclarationButton().setEnabled(false);
		}
		if (getDownDeclarationButton() != null) {
			getDownDeclarationButton().setEnabled(false);
		}
		TableItem[] items = declarationsTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		RuleElement ruleElement = IndexUtils.getRuleElement(getProjectName(), rule.getFolder(), rule.getName(), ELEMENT_TYPES.RULE_TEMPLATE);
		IResolutionContext resContext = ElementReferenceResolver.createResolutionContext(ruleElement.getScope());
		for (SimpleProperty prop: ((RuleTemplate)getCompilable()).getDisplayProperties()) {
			TableItem tableItem = new TableItem(declarationsTable, SWT.NONE);
			String name = prop.getName();
			Path path = new Path(ModelUtils.convertPackageToPath(name));
			ElementReference ref = RuleGrammarUtils.createElementReference(path);
			Object resolvedElement = ElementReferenceResolver.resolveElement(ref, resContext);
			if (resolvedElement instanceof GlobalVariableDef) {
				resolvedElement = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) resolvedElement);
			}
			if (resolvedElement instanceof DesignerElement) {
				DesignerElement element = (DesignerElement) resolvedElement;
				if (element.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				} else if (element.getElementType() == ELEMENT_TYPES.TIME_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
				} else if (element.getElementType() == ELEMENT_TYPES.CONCEPT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
				} else if (element.getElementType() == ELEMENT_TYPES.SCORECARD) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
				}
			} else if (resolvedElement instanceof PropertyDefinition) {
				PropertyDefinition propDef = (PropertyDefinition) resolvedElement;
				Image image = EditorUtils.getPropertyImage(propDef.getType());
				if(image!=null) {
					tableItem.setImage(image);
				}
			}
			tableItem.setText(0, prop.getName());
			tableItem.setText(1, prop.getValue());
			tableItem.setText(2, "Hidden");
		}		
	}

	@Override
	protected void removeDeclareStatement(String type, String idName,
			int ruleType, int blockType, int statementType, boolean isArray) {
		super.removeDeclareStatement(type, idName, ruleType, blockType, statementType,
				isArray);
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
		if (upDeclButton != null)
			upDeclButton.setEnabled(false);
		if (downDeclButton != null)
			downDeclButton.setEnabled(false);
		if (browseButton != null)
			browseButton.setEnabled(false);
	}

	protected void removeDeclaratorFromModel(int index) {
		((RuleTemplate)getCompilable()).getDisplayProperties().remove(index);
	}

	@Override
	public void updateDeclarationStatements(int index, int ruleType,
			int blockType, int statementType, boolean up) {
		if (statementType == RulesParser.DISPLAY_PROPERTY) {
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
		
		RulesASTNode valNode = currentDecASTNode.getChildByFeatureId(RulesParser.LITERAL);
//		RulesASTNode valNode = currentDecASTNode.getChildByFeatureId(RulesParser.LITERAL);
		RulesASTNode nameNode = currentDecASTNode.getChildByFeatureId(RulesParser.NAME);
		
		String valString = valNode.getText();
//		String typeString = RuleGrammarUtils.getNameAsString(valNode, RuleGrammarUtils.NAME_FORMAT);/*RuleGrammarUtils.rewriteNode(typeNode);*/
		String nameString = RuleGrammarUtils.rewriteNode(nameNode);

		RulesASTNode swapValTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.LITERAL);
//		RulesASTNode swapTypeNode = swapDecASTNode.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode swapNameNode = swapDecASTNode.getChildByFeatureId(RulesParser.NAME);
		
		String swapValTypeString = swapValTypeNode.getText();
//		String swapTypeString =RuleGrammarUtils.getNameAsString(swapTypeNode, RuleGrammarUtils.NAME_FORMAT);/* RuleGrammarUtils.rewriteNode(swapTypeNode);*/
		String swapNameString = RuleGrammarUtils.rewriteNode(swapNameNode);

		if(currentDecASTNode != null && swapDecASTNode != null){
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			if(up){
				analyzer.analyzeASTNodeReplace(valNode, swapValTypeString);
				analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
//				analyzer.analyzeASTNodeReplace(valNode, swapTypeString);
				analyzer.analyzeASTNodeReplace(swapValTypeNode, valString);
				analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
//				analyzer.analyzeASTNodeReplace(swapTypeNode, typeString);
				
				applyTextEdit(analyzer.getCurrentEdit());
			}
			else{
				analyzer.analyzeASTNodeReplace(swapValTypeNode, valString);
				analyzer.analyzeASTNodeReplace(swapNameNode, nameString);
				analyzer.analyzeASTNodeReplace(valNode, swapValTypeString);
				analyzer.analyzeASTNodeReplace(nameNode, swapNameString);
				
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
		if (statementType == RulesParser.NAME || statementType == RulesParser.LITERAL) {
			updateExpression(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
		} else {
			super.updateDeclarationStatements(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
		}
		return true;
	}

	private void updateExpression(String name, String value, String oldText,
			String newText, int ruleType, int blockType, int statementType,
			Compilable compilable) {
		
		name = replace(name, "/", ".");
		name = replace(name, "[]", "");
		if (name.charAt(0) == '.') {
			name = name.substring(1);
		}
		
		RulesASTNode astNode = getCurrentASTNode();
		RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);
		RulesASTNode node = declBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		for (RulesASTNode child : children) {
			RulesASTNode nameNode = child.getChildByFeatureId(RulesParser.NAME);
			RulesASTNode valueNode = child.getChildByFeatureId(RulesParser.LITERAL);
			
//			String typeString = RuleGrammarUtils.rewriteNode(typeNode);
			String valueString = valueNode.getText();//RuleGrammarUtils.getNameAsString(valueNode, RuleGrammarUtils.NAME_FORMAT);
			if (valueString.length() > 0 && valueString.charAt(0) == '"') {
				valueString = valueString.substring(1,valueString.length()-1);
			}
			String nameString = RuleGrammarUtils.rewriteNode(nameNode);
			
			if (valueString.equals(value) && nameString.equals(name)) {
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				String newExpr = newText;
				if (statementType == RulesParser.LITERAL) {
					newExpr = "\""+newExpr+"\"";
					TextEdit repEdit = analyzer.analyzeASTNodeReplace(valueNode, newExpr);
					applyTextEdit(repEdit);
				} else if (statementType == RulesParser.NAME) {
					TextEdit repEdit = analyzer.analyzeASTNodeReplace(nameNode, newExpr);
					applyTextEdit(repEdit);
				}
				break;
			}
		}
		refreshRulesFormSourceViewerConfiguration();
	}

	protected void createButtons(final FormToolkit toolkit, 
			final Composite composite,boolean updown) {
		Composite base = toolkit.createComposite(composite,SWT.NULL);
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
		EList<Symbol> symbolList = rule.getSymbols() != null ? rule.getSymbols().getSymbolList() : null;
		
		String title = "Qualified Name Selector";
		String message = "Select qualified name to translate";
		EntityQualifiedNameSelector selector = new EntityQualifiedNameSelector(Display.getDefault().getActiveShell(), 
				title, message, getFormEditor().getProject().getName(), symbolList);
		int ret = selector.open();
		if (ret == IStatus.OK) {
			String qName = selector.getQualifiedName();
			String qNamePackage = ModelUtils.convertPathToPackage(qName);
			EList<SimpleProperty> dispProps = ((RuleTemplate)getCompilable()).getDisplayProperties();
			SimpleProperty dispProp = ModelFactory.eINSTANCE.createSimpleProperty();
			dispProp.setName(qNamePackage);
			dispProp.setValue("");
			dispProps.add(dispProp);
			addDisplayStatement(qNamePackage, "");
			updateDeclarationsSection();
		}
	}

	private void addDisplayStatement(String qName, String displayText) {

		try {
			RulesASTNode astNode = getCurrentASTNode();
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode dispBlocksNode = blocksNode.getFirstChildWithType(RulesParser.DISPLAY_BLOCK);
			
			RulesASTNode dispNode = new RulesASTNode(new CommonToken(RulesParser.DISPLAY_PROPERTY));
			
			RulesASTNode typeNameNode = RuleGrammarUtils.createName(qName);
			RulesASTNode litNode = new RulesASTNode(new CommonToken(RulesParser.StringLiteral, "\""+displayText+"\""));
			
			dispNode.addChildByFeatureId(typeNameNode, RulesParser.NAME);
			dispNode.addChildByFeatureId(litNode, RulesParser.LITERAL);
			
			boolean addBlock = false;
			if (dispBlocksNode == null) {
				addBlock = true;
				dispBlocksNode = new RulesASTNode(new CommonToken(RulesParser.DISPLAY_BLOCK));
//				RulesASTNode blockNode = new RulesASTNode(new CommonToken(RulesParser.BLOCK));
//				dispBlocksNode.addChildByFeatureId(blockNode, RulesParser.BLOCK);
				RulesASTNode statementsNode = new RulesASTNode(new CommonToken(RulesParser.STATEMENTS));
				dispBlocksNode.addChildByFeatureId(statementsNode, RulesParser.BLOCK);
				statementsNode.addChild(dispNode);
			}

			RulesASTNode node = dispBlocksNode.getFirstChildWithType(RulesParser.BLOCK);
			List<RulesASTNode> children = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
			if (!addBlock && children != null && children.size() > 0) {
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				TextEdit textEdit = analyzer.analyzeASTNodeAdd(dispNode, node, RulesParser.STATEMENTS);
				applyTextEdit(textEdit);
			} else {
				// there are no display props yet, insert it
				String text = null;
				int offset = 0;
				if (addBlock) {
					// insert new display block before declare block
					text = RuleGrammarUtils.rewriteNode(dispBlocksNode);
					RulesASTNode declBlock = blocksNode.getChildByFeatureId(RulesParser.DECLARE_BLOCK);
					int lineOfOffset = getDocument().getLineOfOffset(declBlock.getOffset());
					offset = getDocument().getLineOffset(lineOfOffset);
				} else {
					text = RuleGrammarUtils.rewriteNode(dispNode);
					int lineOfOffset = getDocument().getLineOfOffset(dispBlocksNode.getOffset()+dispBlocksNode.getLength()-1);
					offset = getDocument().getLineOffset(lineOfOffset);
				}
				InsertEdit textEdit = new InsertEdit(offset, text);
				applyTextEdit(textEdit);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		removeDeclarator(RulesParser.RULE_TEMPLATE_DECL, RulesParser.DISPLAY_BLOCK, RulesParser.DISPLAY_PROPERTY, 1, 0);
	}

	@Override
	protected RulesASTNode findNode(List<RulesASTNode> children, String type,
			String idName, boolean isArray) {
    	for (RulesASTNode child : children) {
    		RulesASTNode nameNode = child.getChildByFeatureId(RulesParser.NAME);
    		if (nameNode == null) {
    			continue; // could be an expression in an action context
    		}
    		String nameString = RuleGrammarUtils.rewriteNode(nameNode);
    		if (nameString.equals(idName)) {
    			return child;
    		}
    	}
		return null;
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