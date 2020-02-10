package com.tibco.cep.studio.ui.editors;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getPropertyFieldImage;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setKeySupport;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setPopertyField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDropTarget;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setEditContextMenuSupport;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IResolutionContextProviderExtension;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.RulesSemanticASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.dialog.ReturnTypeSelector;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPartitionScanner;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class RuleFunctionFormViewer extends AbstractRuleEntitiesFormViewer implements IResolutionContextProviderExtension {

	private RuleFunctionFormEditor editor;
	private Section configuration;
	private Section scopeSection;
	private Section bodysection;
	private TextViewer ruleDescTextViewer;
	private TextViewer ruleAliasTextViewer;
	private RuleFunction rulefunction;
    private Combo validityCombo;
    private Button browseButton;
    private Button cbutton;
    private PropertyTypeCombo returnTypeText;
//	private SourceViewer bodySourceViewer; // reuse actionsSourceViewer, so openDeclaration works
    private String returnType;
    private boolean arrayReturnType;
    private Hyperlink returnTypeLink;
    
	private static final int Ws1 = 50;//weight for scope section
	private static final int Ws2 = 105;//weight for body section
	private static final int Wsm = 10;
	private static final int Wst = Ws1+Ws2;
	private static final String[] VALIDITY_ITEMS = new String[3];
	private static final String[] VALIDITY_TEXT  = new String[3];
	
	private static final int INDEX_ACTION 	 = 0;
	private static final int INDEX_CONDITION = 1;
	private static final int INDEX_QUERY	 = 2;
	
	static 
	{
		VALIDITY_ITEMS[INDEX_ACTION] 	= "Action";
		VALIDITY_ITEMS[INDEX_CONDITION] = "Action,Condition";
		VALIDITY_ITEMS[INDEX_QUERY] 	= "Action,Condition,Query";
		
		VALIDITY_TEXT[INDEX_ACTION] 	= "ACTION";
		VALIDITY_TEXT[INDEX_CONDITION] 	= "CONDITION";
		VALIDITY_TEXT[INDEX_QUERY] 		= "QUERY";
	};
	
	/**
	 * @param editor
	 */
	public RuleFunctionFormViewer(RuleFunctionFormEditor editor) {
		this.editor = editor;
 	}

	@Override
	public void dispose() {
		super.dispose();
		this.editor = null;
		this.configuration.dispose();
		this.scopeSection.dispose();
		this.bodysection.dispose();
		this.validityCombo.dispose();
		this.cbutton.dispose();
		this.returnTypeText.dispose();
		this.browseButton.dispose();
		
		this.ruleDescTextViewer = null;
		this.ruleAliasTextViewer = null;
		this.rulefunction = null;
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		IEditorInput editorInput = editor.getEditorInput();
		String name = editorInput.getName();
		if (name.contains(".rulefunction")) {
			String ruleName = name.substring(0, name.indexOf(".rulefunction"));
			super.createPartControl(container, Messages.getString("rulefunction.editor.title") + ruleName,EditorsUIPlugin.getDefault().getImage("icons/rule-function.png"));
		}
		
		dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
		getForm().getToolBarManager().add(dependencyDiagramAction);
		sequenceDiagramAction = EditorUtils.createSequenceDiagramAction(editor, getForm(), editor.getProject());
		getForm().getToolBarManager().add(sequenceDiagramAction);
		
		getForm().updateToolBar();

		super.createToolBarActions();

		sashForm.setWeights(new int[] {Ws1, Ws2});// setting the default weights for the available sections.
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.AbstractRuleEntitiesFormViewer#createConfigurationPart(org.eclipse.ui.forms.widgets.ScrolledForm, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	protected void createConfigurationPart(final ScrolledForm form,final FormToolkit toolkit) {
		
		configuration = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED);
		configuration.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		configuration.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		configuration.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		configuration.setLayoutData(gd);
		
		Composite sectionClient = toolkit.createComposite(configuration);
		configuration.setClient(sectionClient);
	
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);

		toolkit.createLabel(sectionClient, Messages.getString("Description"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		ruleDescTextViewer =  new TextViewer(sectionClient, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		fDescModifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// don't change on modify, only focus lost
				setDescription();
			}
		};
		ruleDescTextViewer.getTextWidget().addModifyListener(fDescModifyListener);

		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 600;
		gd.heightHint = 35;
		ruleDescTextViewer.getTextWidget().setLayoutData(gd);
		
		toolkit.createLabel(sectionClient, Messages.getString("alias"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		ruleAliasTextViewer =  new TextViewer(sectionClient, SWT.BORDER | SWT.WRAP);
		fAliasModifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
//				don't change on modify, only focus lost
				setAlias();
			}
		};
		ruleAliasTextViewer.getTextWidget().addModifyListener(fAliasModifyListener);

		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 270;
		gd.heightHint = 20;
		ruleAliasTextViewer.getTextWidget().setLayoutData(gd);
		

		toolkit.createLabel(sectionClient,  Messages.getString("rulefunction.isVirtual"),SWT.NONE);
		cbutton = toolkit.createButton(sectionClient,"",  SWT.CHECK);
		
		cbutton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				editor.setTitleAndImage(cbutton.getSelection(), rulefunction.getName());
				if(cbutton.getSelection()){
					returnTypeText.setEnabled(false);
					validityCombo.setEnabled(false);
					browseButton.setEnabled(false);
					returnTypeText.setText("void");
					returnTypeText.setImage(getPropertyFieldImage("void"));
					actionsSourceViewer.getTextWidget().setText("");
					actionsSourceViewer.getTextWidget().setEnabled(false);
				}
				else{
					returnTypeText.setEnabled(true);
					validityCombo.setEnabled(true);
					browseButton.setEnabled(true);
					actionsSourceViewer.getTextWidget().setText(rulefunction.getActionText());
					actionsSourceViewer.getTextWidget().setEnabled(true);
				}
				setVirtual(cbutton.getSelection());
			}});
		
		toolkit.createLabel(sectionClient, Messages.getString("rulefunction.Validity"));
		validityCombo =  new Combo(sectionClient, SWT.BORDER | SWT.READ_ONLY);
		validityCombo.setItems(VALIDITY_ITEMS);
		gd = new GridData();
		gd.widthHint=250;
		validityCombo.setLayoutData(gd);
		validityCombo.setText("Action");
		validityCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				setValidity(validityCombo.getSelectionIndex());
			}});
		
//		toolkit.createLabel(sectionClient, Messages.getString("rulefunction.returnType"),  SWT.NONE);
		returnTypeLink = createLinkField(toolkit, sectionClient, Messages.getString("rulefunction.returnType"));
		
		Composite retTypeComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		retTypeComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		retTypeComposite.setLayoutData(gd);
		
		returnTypeText = new PropertyTypeCombo(retTypeComposite, SWT.BORDER | SWT.NONE, false, true);
		returnTypeText.setImage(EditorsUIPlugin.getDefault().getImage("icons/iconString16.gif"));

		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 250;
		returnTypeText.setLayoutData(gd);
		returnTypeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String oldType = rulefunction.getReturnType() ==  null ? "void" : rulefunction.getReturnType();
				if(!oldType.equalsIgnoreCase(returnTypeText.getText())){
					setPropertyReturnType(true);
					setReturnType();
					rulefunction.setReturnType(returnTypeText.getText());
				}
			}
		});
		
		addHyperLinkFieldListener(returnTypeLink, returnTypeText, editor, getProjectName(), false, false);
		
		browseButton = new Button(retTypeComposite, SWT.NONE);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try{
					ReturnTypeSelector returnTypeSelector = new ReturnTypeSelector(getFormEditor().getSite().getShell(),
																		getFormEditor().getProject().getName(),
																		returnTypeText.getText(),
																		getPropertyReturnPropertyType(),
																		isArrayReturnType());
					if (returnTypeSelector.open() == Dialog.OK) {
						 setPopertyField(returnTypeText, returnTypeSelector.getPropertyType(), returnTypeSelector.getValue(), returnTypeSelector.isArray());
					}
					}catch(Exception e1){
						e1.printStackTrace();
					}
				 }
				});
		
		toolkit.paintBordersFor(sectionClient);
	}	

	protected void setAlias() {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			// get the validity AST node and replace it with the new validity
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_FUNC_DECL);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode attrBlock = blocksNode.getChildByFeatureId(RulesParser.ATTRIBUTE_BLOCK);
			RulesASTNode attrStatements = attrBlock.getFirstChildWithType(RulesParser.STATEMENTS);
			RulesASTNode aliasStatement = attrStatements.getFirstChildWithType(RulesParser.ALIAS_STATEMENT);
			String newAlias = "";
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			TextEdit textEdit = null;
			if (aliasStatement == null) {
				// no alias statement yet, add it
				int lineOfOffset = getDocument().getLineOfOffset(attrStatements.getOffset()+attrStatements.getLength()-1);
				int offset = getDocument().getLineOffset(lineOfOffset);
				newAlias = getAliasText().get();
				newAlias = newAlias.replaceAll("\r\n", " ");
				newAlias = newAlias.replaceAll("\n", " ");
				String text = "\t\talias = \""+newAlias+"\";\n";
				textEdit = new InsertEdit(offset, text);
			} else {
				RulesASTNode prio = aliasStatement.getChildByFeatureId(RulesParser.LITERAL);
				newAlias = getAliasText().get();
				newAlias = newAlias.replaceAll("\r\n", " ");
				newAlias = newAlias.replaceAll("\n", " ");
				String oldAlias = prio.getText();
				if (oldAlias != null && oldAlias.length() > 0) {
					if (oldAlias.charAt(0) == '\"') {
						oldAlias = oldAlias.substring(1, oldAlias.length()-1);
					}
				}

				if (newAlias.equals(oldAlias)) {
					return;
				}
				if (newAlias == null || newAlias.length() == 0) {
					textEdit = analyzer.analyzeASTNodeDelete(aliasStatement);
				} else {
					String text = "alias = \""+newAlias+"\";";
					textEdit = analyzer.analyzeASTNodeReplace(aliasStatement, text);
				}
			}
			applyTextEdit(textEdit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param virtual
	 */
	protected void setVirtual(boolean virtual) {
		RulesASTNode astNode = getCurrentASTNode();
		// get the validity AST node and replace it with the new validity
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_FUNC_DECL);
		RulesASTNode modifiersNode = ruleNode.getFirstChildWithType(RulesParser.MODIFIERS);
		RulesASTNode virtualNode = modifiersNode.getFirstChildWithType(RulesParser.VIRTUAL);
		ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
		if (virtual) {
			if (virtualNode == null) {
				// insert new 'virtual' node here
//				virtualNode = new RulesASTNode(new CommonToken(RulesParser.VIRTUAL));
//				TextEdit textEdit = analyzer.analyzeASTNodeAdd(virtualNode, modifiersNode, RulesParser.VIRTUAL);
				InsertEdit edit = new InsertEdit(ruleNode.getOffset(), "virtual ");
				applyTextEdit(edit);
			} else {
				// already done
				return;
			}
		} else {
			if (virtualNode == null) {
				// already done
				return;
			} else {
				TextEdit textEdit = analyzer.analyzeASTNodeDelete(virtualNode);
				applyTextEdit(textEdit);
			}
		}
	}

	/**
	 * @param index
	 */
	protected void setValidity(int index) {
		RulesASTNode astNode = getCurrentASTNode();
		// get the validity AST node and replace it with the new validity
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_FUNC_DECL);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode attrBlock = blocksNode.getChildByFeatureId(RulesParser.ATTRIBUTE_BLOCK);
		RulesASTNode attrStatements = attrBlock.getFirstChildWithType(RulesParser.STATEMENTS);
		RulesASTNode validityStatement = attrStatements.getFirstChildWithType(RulesParser.VALIDITY_STATEMENT);
		RulesASTNode prio = validityStatement.getChildByFeatureId(RulesParser.VALIDITY);
		String text = prio.getText();
		String newPrio = VALIDITY_TEXT[index];
		if (newPrio.equals(text)) {
			return;
		}
		ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
		TextEdit textEdit = analyzer.analyzeASTNodeReplace(validityStatement, "validity = "+newPrio+";");
		applyTextEdit(textEdit);
	}

	protected void setReturnType() {
		RulesASTNode astNode = getCurrentASTNode();
		// get the return AST node and replace it with the new return type
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_FUNC_DECL);
		RulesASTNode retTypeNode = ruleNode.getFirstChildWithType(RulesParser.RETURN_TYPE);
		String returnType = getReturnTypeAsString();
		if (retTypeNode == null || retTypeNode.getFirstChild() == null) {
			// insert this node after the MODIFIERS, if they exist
			RulesASTNode modifiersNode = ruleNode.getFirstChildWithType(RulesParser.MODIFIERS);
			RulesASTNode virtualNode = modifiersNode.getFirstChildWithType(RulesParser.VIRTUAL);
			int insertionOffset = ruleNode.getOffset();
			if (virtualNode != null) {
				// this should not happen, can not specify return type for virtual RFs, but put the logic in anyway
				insertionOffset = virtualNode.getOffset() + virtualNode.getLength();
				InsertEdit edit = new InsertEdit(insertionOffset, " " + returnType + " ");
				applyTextEdit(edit);
			} else {
				InsertEdit edit = new InsertEdit(insertionOffset, returnType + " ");
				applyTextEdit(edit);
			}
		} else {
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			if (retTypeNode.getFirstChild() != null && retTypeNode.getFirstChild().isArray()) {
				retTypeNode.getFirstChild().setLength(retTypeNode.getFirstChild().getLength() + 2 /* for the '[]' */);
			}
			TextEdit textEdit = analyzer.analyzeASTNodeReplace(retTypeNode, returnType);
			applyTextEdit(textEdit);
		}
	}
	
	private String getReturnTypeAsString() {
		
		arrayReturnType = false;
		String text = returnTypeText.getText();
		
		if (text.endsWith("[]")) {
			arrayReturnType = true;
			text = text.substring(0, text.indexOf("[]"));
		}
		//setting the image for return type field
		Entity entity = IndexUtils.getEntity(editor.getProject().getName(), text);
		if (entity != null) {
			if (entity instanceof Concept) {
				setPropertyReturnType("concept");
			} else if (entity instanceof Event) {
				setPropertyReturnType("event");
			}
		} else {
			IPath cpath = Path.fromOSString(text + ".concept");
			IPath epath = Path.fromOSString(text + ".event");
			if (editor.getProject().getFile(cpath).exists()) {
				setPropertyReturnType("concept");
			} else if(editor.getProject().getFile(epath).exists()) {
				setPropertyReturnType("event");
			} else {
				setPropertyReturnType(text);
			}
		}

		text =  ModelUtils.convertPathToPackage(text);
		
		if (isArrayReturnType()) {
			text = text + "[]";
		}
		return text;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.AbstractRuleEntitiesFormViewer#createScopePart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings("serial")
	@Override
	protected void createScopePart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		scopeSection = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		scopeSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		scopeSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		scopeSection.setText(Messages.getString("rulefunction.scope"));
		scopeSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if (e.getState() == true) {
					sashForm.setWeights(new int[] { Ws1,Ws2 });//When both expanded
				} else {
					sashForm.setWeights(new int[] { Wsm,Wst-Wsm });
				}
				managedForm.getForm().reflow(true);
			}
		});
	
		Composite client = toolkit.createComposite(scopeSection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		
		client.setLayout(layout);
		
		createToolbar(client, true);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		scopeSection.setClient(client);
		
		declarationsTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION);
		declarationsTable.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
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
		
		StudioUIUtils.addRemoveControlListener(this, editor.isEnabled());
		
		if(editor.isEnabled()){
			setDeclarationTableEditableSupport(declarationsTable, 
					1, null,
					RulesParser.RULE_FUNC_DECL, 
					RulesParser.SCOPE_BLOCK,
					RulesParser.SCOPE_DECLARATOR, 
					this, 
					true);

			setDeclarationScopeDropTarget(declarationsTable, 
					RulesParser.RULE_FUNC_DECL, 
					RulesParser.SCOPE_BLOCK,
					RulesParser.SCOPE_DECLARATOR);
		}
	}

	@Override
	protected void createBodyPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		bodysection = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		bodysection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		bodysection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		bodysection.setText(Messages.getString("rulefunction.body"));
		bodysection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				managedForm.getForm().reflow(true);
			}
		});
	
		Composite client = toolkit.createComposite(bodysection, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		client.setLayout(layout);
		
		toolkit.paintBordersFor(client);
		bodysection.setClient(client);
		
		DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
		EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
		OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);
		
		actionsSourceViewer = new SourceViewer(client, new VerticalRuler(12, access), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		actionsSourceViewer.getTextWidget().setFont(font);
		actionsSourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		if(editor.isEnabled()){
			setDropTarget(actionsSourceViewer, editor.getSite().getPage(), editor);
		}
		setupSourceViewerDecorationSupport(actionsSourceViewer, ruler, access, sharedColors);
	}
	
	@Override
	protected void init() {
		RulesEditor rulesEditor = editor.getRulesEditor();
		actionSourceViewerConfiguration = new RulesFormSourceViewerConfiguration(this, IRulesSourceTypes.ACTION_SOURCE, rulesEditor);
		actionsSourceViewer.configure(actionSourceViewerConfiguration);
		actionsSourceViewer.setEditable(getFormEditor().getRulesEditor().isEditable());
		Document document = new Document(rulefunction.getActionText());

		document.addDocumentListener(new IDocumentListener(){
			public void documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent event) {
			}
			public void documentChanged(org.eclipse.jface.text.DocumentEvent event) {
				editor.modified();
				setSourceSynchronized(true);
			}
		});
		IDocumentPartitioner partitioner = new FastPartitioner(new RulesPartitionScanner(), 
				new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER, 
			RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		actionsSourceViewer.setDocument(document, new RulesAnnotationModel());
		
		actionsSourceViewer.getTextWidget().addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (!editor.isModified()) {
					return;
				}
				setActionText(RulesParser.RULE_FUNC_DECL, RulesParser.BODY_BLOCK);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		TextViewerUndoManager bodySourceUndoManager =  new TextViewerUndoManager(10);
		bodySourceUndoManager.connect(actionsSourceViewer);
		
		Document descriptionDocument = new Document(rulefunction.getDescription() == null? "": rulefunction.getDescription());
		ruleDescTextViewer.setDocument(descriptionDocument);
		
		ruleDescTextViewer.getTextWidget().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!editor.isModified()) {
					return;
				}
				setDescription();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		TextViewerUndoManager descriptionUndoManager =  new TextViewerUndoManager(10);
		descriptionUndoManager.connect(ruleDescTextViewer);
		
		//TODO: After Alias model implemented
		Document aliasDocument = new Document(rulefunction.getDescription() == null? "" : ""/*rulefunction.getAlias()*/);
		ruleAliasTextViewer.setDocument(aliasDocument);
		
		ruleAliasTextViewer.getTextWidget().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!editor.isModified()) {
					return;
				}
				setAlias();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		TextViewerUndoManager aliasUndoManager =  new TextViewerUndoManager(10);
		aliasUndoManager.connect(ruleAliasTextViewer);
		
		if(editor.isEnabled()){
			setEditContextMenuSupport(actionsSourceViewer.getTextWidget(), bodySourceUndoManager, true, this);
			setKeySupport(this, actionsSourceViewer.getTextWidget(), bodySourceUndoManager, actionSourceViewerConfiguration, actionsSourceViewer);

			setEditContextMenuSupport(ruleDescTextViewer.getTextWidget(), descriptionUndoManager, false, this);
			StudioUIUtils.setKeySupport(ruleDescTextViewer.getTextWidget(), descriptionUndoManager);

			setEditContextMenuSupport(ruleAliasTextViewer.getTextWidget(), aliasUndoManager, false, this);
			StudioUIUtils.setKeySupport(ruleAliasTextViewer.getTextWidget(), aliasUndoManager);
		}
		
		initialized = true;
 	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.AbstractRuleEntitiesFormViewer#getDescriptionText()
	 */
	@Override
	protected IDocument getDescriptionText() {
		return ruleDescTextViewer.getDocument();
	}

	/**
	 * @return
	 */
	protected IDocument getAliasText() {
		return ruleAliasTextViewer.getDocument();
	}
	
	@Override
	protected AbstractRuleFormEditor getFormEditor() {
		return editor;
	}

	
	@Override
	protected void updateWidgets(boolean isDirtyBeforeRefresh,
			RulesASTNode newNode, boolean editable) {
		updateRegions(newNode, RulesParser.RULE_FUNC_DECL, RulesParser.BODY_BLOCK);
		//		ruleNameText.setText(rule.getName());
		RulesASTNode headerNode = (RulesASTNode) newNode.getData("HEADER");

		// remove modify listener first, or setting the text here will cause source editor to be modified
		ruleDescTextViewer.getTextWidget().removeModifyListener(fDescModifyListener);
		if (headerNode != null) {
			String desc = getDescription(headerNode);
			ruleDescTextViewer.getDocument().set(desc);
		} else {
			ruleDescTextViewer.getDocument().set("");
		}

		// add the listener back
		ruleAliasTextViewer.getTextWidget().addModifyListener(fAliasModifyListener);

		// remove modify listener first, or setting the text here will cause source editor to be modified
		ruleAliasTextViewer.getTextWidget().removeModifyListener(fAliasModifyListener);
		if (rulefunction.getAlias() != null) {
			ruleAliasTextViewer.getDocument().set(rulefunction.getAlias());
		} else {
			ruleAliasTextViewer.getDocument().set("");
		}

		// add the listener back
		ruleDescTextViewer.getTextWidget().addModifyListener(fDescModifyListener);

		if(rulefunction.isVirtual()){
			managedForm.getForm().setText(Messages.getString("rulefunction.virtual.editor.title") + " " +rulefunction.getName());
			managedForm.getForm().setImage(editor.getRfImage());
			returnTypeText.setEnabled(false);
			validityCombo.setEnabled(false);
			browseButton.setEnabled(false);

			//Clearing the invalid body source if there
			if(rulefunction.getActionText()!=null && !rulefunction.getActionText().trim().equalsIgnoreCase("")){
				isDirtyBeforeRefresh = true;
				actionsSourceViewer.getDocument().set("");
				setActionText(RulesParser.RULE_FUNC_DECL, RulesParser.BODY_BLOCK);
			}

			actionsSourceViewer.getTextWidget().setEnabled(false);
		}else{
			managedForm.getForm().setText(Messages.getString("rulefunction.editor.title") + " " +rulefunction.getName());
			managedForm.getForm().setImage(editor.getRfImage());
			returnTypeText.setEnabled(true);
			validityCombo.setEnabled(true);
			browseButton.setEnabled(true);
			actionsSourceViewer.getTextWidget().setEnabled(true);
		}

		Validity validity = rulefunction.getValidity();
		switch (validity) {
		case ACTION:
			validityCombo.select(0);
			break;

		case CONDITION:
			validityCombo.select(1);
			break;

		case QUERY:
			validityCombo.select(2);
			break;

		default:
			break;
		}
		returnTypeText.setText(rulefunction.getReturnType()== null? "void": rulefunction.getReturnType());
		setPropertyReturnType(true);
		cbutton.setSelection(rulefunction.isVirtual());
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

	@Override
	protected void setCompilable(Compilable compilable) {
		if(compilable instanceof RuleFunction){
			rulefunction = (RuleFunction)compilable;
		}
	}

	@Override
	protected void updateSourceViewers(boolean editable) {
		actionsSourceViewer.getDocument().set(rulefunction.getActionText());
		actionsSourceViewer.setEditable(editable);
	}

	@Override
	protected void updateRegions(RulesASTNode newNode, int rootType, int blockType) {
		super.updateRegions(newNode, rootType, blockType);
	}

	/**
	 * @param isSetImage
	 */
	private void setPropertyReturnType(boolean isSetImage) {
		try {
			String retType = getReturnTypeAsString();
			if (getPropertyReturnPropertyType().equalsIgnoreCase("Concept") || getPropertyReturnPropertyType().equalsIgnoreCase("Event") 
					                      || getPropertyReturnPropertyType().equalsIgnoreCase("DateTime")
					                      || getPropertyReturnPropertyType().equalsIgnoreCase("String")
					                      || getPropertyReturnPropertyType().equalsIgnoreCase("Object")) {
				//DO NOTHING
			}else{
				if (getReturnType().isUnknown()) {
					if(isSetImage)returnTypeText.setImage(EditorsUIPlugin.getDefault().getImage("icons/error_mark.png"));
					setPropertyReturnType("Unknown");
					return;
				}
			}
			if (getPropertyReturnPropertyType().equalsIgnoreCase("Concept")) {
				if (isSetImage) {
					returnTypeText.setImage(getPropertyFieldImage("concept"));
				}
			} else if (getPropertyReturnPropertyType().equalsIgnoreCase("Event")) {
				if (isSetImage) {
					returnTypeText.setImage(getPropertyFieldImage("event"));
				}
			} else {
				if (isSetImage) {
					if (retType.endsWith("]")) {
						retType = retType.substring(0, retType.indexOf("["));
					}
					returnTypeText.setImage(getPropertyFieldImage(retType));
				}
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isArrayReturnType() {
		return arrayReturnType;
	}
	
	public void setPropertyReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getPropertyReturnPropertyType(){
		return returnType;
	}
	
	@Override
	protected SourceViewer getActionSourceViewer() {
		return actionsSourceViewer;
	}
	
	@Override
	protected Compilable getCompilable() {
		updateRuleFunction();
		return rulefunction;
	}

	@Override
	public Compilable getCommonCompilable() {
		updateRuleFunction();
		return rulefunction;
	}

	public void updateRuleFunction(){
		RulesASTNode newNode = getCurrentASTNode();
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(true, getFormEditor().getProject().getName());
		newNode.accept(visitor);

		Compilable compile = visitor.getRule();
		setCompilable(compile);
	}
	
	@Override
	public NodeType getReturnType() {
		final NodeType[] arr = new NodeType[1];
		Display.getDefault().syncExec(new Runnable() {
		
			@Override
			public void run() {
				String retType = getReturnTypeAsString();
				CommonTree node = RulesParserManager.parseReturnTypeString(editor.getProject().getName(), retType, null, false);
				NodeType nodeType = new RulesSemanticASTVisitor(RuleFunctionFormViewer.this, editor.getProject().getName(), null).getNodeType((RulesASTNode) node, ((RulesASTNode) node).isArray(), true);
				if (nodeType == null) {
					arr[0] = NodeType.VOID;
				} else {
					arr[0] = nodeType;
				}
			}
		});
		
		return arr[0];
	}
	
	protected String getProjectName(){
		return editor.getProject().getName();
	}
	
	private void readOnlyWidgets(){
		ruleDescTextViewer.setEditable(false);
		if (dependencyDiagramAction != null){
			dependencyDiagramAction.setEnabled(false);
		}
		if(sequenceDiagramAction != null){
			sequenceDiagramAction.setEnabled(false);	
		}
	    
		removeDeclButton.setEnabled(false);
		addDeclButton.setEnabled(false);
		upDeclButton.setEnabled(false);
		downDeclButton.setEnabled(false);
		
		ruleDescTextViewer.setEditable(false);
		ruleAliasTextViewer.setEditable(false);
		
		validityCombo.setEnabled(false);
	    browseButton.setEnabled(false);
	  
	    cbutton.setEnabled(false);
	    
	    returnTypeText.setEnabled(false);
		actionsSourceViewer.setEditable(false);
		
		returnTypeLink.setEnabled(false);
	}

	@Override
	protected void add() {
		selectAndAddDeclarator(RulesParser.RULE_FUNC_DECL, RulesParser.SCOPE_BLOCK, RulesParser.SCOPE_DECLARATOR);
	}

	@Override
	protected void remove() {
		removeDeclarator(RulesParser.RULE_FUNC_DECL, RulesParser.SCOPE_BLOCK, RulesParser.SCOPE_DECLARATOR);
	}

	@Override
	protected void up() {
		reorderDeclarators(RulesParser.RULE_FUNC_DECL, RulesParser.SCOPE_BLOCK, RulesParser.SCOPE_DECLARATOR, true);
	}

	@Override
	protected void down() {
		reorderDeclarators(RulesParser.RULE_FUNC_DECL, RulesParser.SCOPE_BLOCK, RulesParser.SCOPE_DECLARATOR, false);					
	}

	@Override
	protected boolean isEditorDirty() {
		return editor.isDirty();
	}

	@Override
	protected boolean isEditorEnabled() {
		return editor.isEnabled();
	}
}
	
