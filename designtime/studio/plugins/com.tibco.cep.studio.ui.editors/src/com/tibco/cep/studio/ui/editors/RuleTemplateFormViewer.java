package com.tibco.cep.studio.ui.editors;


import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setKeySupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addRuleHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getUniqueTag;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.isSymbolPresent;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDropTarget;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setEditContextMenuSupport;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
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
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;

import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.GlobalVariableExtension;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IGlobalVariablesProvider;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPartitionScanner;
import com.tibco.cep.studio.ui.editors.utils.DeclarationSelector;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;
import com.tibco.cep.studio.ui.wizards.RuleFunctionSelector;


public class RuleTemplateFormViewer extends AbstractRuleEntitiesFormViewer implements IGlobalVariablesProvider{

	private static final int ID_COLUMN = 1; // reuse
	private static final int ACTION_TYPE_COLUMN = 2; // reuse
	
	private RuleTemplateFormEditor editor;
	private Section configuration;
	private Section declarations;
	private Section actionContext;
	private Section conditions;
	private Section actions;
	
	private TextViewer ruleDescTextViewer;
	private Text rankRuleFunctionText;
	private Combo priority;	
	private Rule rule;
	
	private Button ruleforwardChain;
	private Button browseButton;
	
	private ModifyListener fPriorityModifyListener;
	private ModifyListener fRankModifyListener;
	
	protected ToolItem removeActionContextButton;
	protected ToolItem addActionContextButton;
	protected ToolItem upActionContextButton;
	protected ToolItem downActionContextButton;

	private static final int Ws1 = 70;//weight for decl section
	private static final int Ws2 = 60;//weight for cond section
	private static final int Ws3 = 70;//weight for action section
	private static final int Ws4 = 60; //weight for variables section
	private static final int Wsm = 15;//min weight
	private static final int Wst = Ws1 + Ws2 + Ws3;
	
	public RuleTemplateFormViewer(RuleTemplateFormEditor editor) {
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
			
//			dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
//			getForm().getToolBarManager().add(dependencyDiagramAction);
//			sequenceDiagramAction = EditorUtils.createSequenceDiagramAction(editor, getForm(), editor.getProject());
//			getForm().getToolBarManager().add(sequenceDiagramAction);
			
			getForm().updateToolBar();
			super.createToolBarActions();
		}
		sashForm.setWeights(new int[] {Ws1, Ws2, Ws3, Ws4}); 
	}

	protected void createConfigurationPart(final ScrolledForm form,final FormToolkit toolkit) {
		
		configuration = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
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
				setDescription();
			}
		};
		
		ruleDescTextViewer.getTextWidget().addModifyListener(fDescModifyListener);

		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 600;
		gd.heightHint = 35;
		ruleDescTextViewer.getTextWidget().setLayoutData(gd);

		toolkit.createLabel(sectionClient, Messages.getString("rule.priority"),  SWT.NONE);
		
		priority = new Combo(sectionClient, SWT.BORDER | SWT.READ_ONLY);
		priority.setItems(new String[]{"1 (Highest)","2", "3", "4", "5", "6", "7", "8", "9", "10 (Lowest)"});
		fPriorityModifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					if (priority.getSelectionIndex() >= 0) {
						setPriority(priority.getSelectionIndex());
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
              }
			};
		priority.addModifyListener(fPriorityModifyListener);
		gd = new GridData();
		gd.widthHint=100;
		priority.setLayoutData(gd);

//		toolkit.createLabel(sectionClient, Messages.getString("RuleFunction_rank"),  SWT.NONE);
		Hyperlink link = createLinkField(toolkit, sectionClient, Messages.getString("RuleFunction_rank"));
		
		Composite browseComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);
		
		rankRuleFunctionText = toolkit.createText(browseComposite,"",  SWT.BORDER);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		rankRuleFunctionText.setLayoutData(gd);
		fRankModifyListener = new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				try{
					rankRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					rankRuleFunctionText.setToolTipText("");
					String newRank = rankRuleFunctionText.getText().trim();
					if(newRank.equals(getCompilable().getRank())){
						return;
					}
					if (!newRank.equals("")) {
						RuleElement element = IndexUtils.getRuleElement(editor.getProject().getName(), newRank, ELEMENT_TYPES.RULE_FUNCTION);

						if (element == null) {
							if (StudioUIUtils.isSharedRankRuleElement(getProjectName(), newRank)) {
								setRank( newRank.equals("") ? newRank : replace(newRank, "/", ".").substring(1));
								return;
							}
							rankRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry",newRank, "rank");
							rankRuleFunctionText.setToolTipText(problemMessage);
							return;
						}
					}
					setRank( newRank.equals("") ? newRank : replace(newRank, "/", ".").substring(1));
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		};
		
		addRuleHyperLinkFieldListener(link, rankRuleFunctionText, editor, getProjectName(), false);
		
		rankRuleFunctionText.addModifyListener(fRankModifyListener);
		
		browseButton = new Button(browseComposite, SWT.NONE);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					RuleFunctionSelector picker = new RuleFunctionSelector(Display.getDefault().getActiveShell(),
																			editor.getProject().getName(),
																			rankRuleFunctionText.getText(), true);
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() != null){
							rankRuleFunctionText.setText(picker.getFirstResult().toString());
						}
					}
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
			}});
		
		toolkit.createLabel(sectionClient, Messages.getString("rule.fowardChain"));
		ruleforwardChain = toolkit.createButton(sectionClient, "", SWT.CHECK);
		ruleforwardChain.addSelectionListener(new SelectionAdapter(){

			/**
			 * @param e
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				setForwardChain(ruleforwardChain.getSelection());
			}});
		
		toolkit.paintBordersFor(sectionClient);
	}	
	
	@SuppressWarnings("serial")
	protected void createDeclarationsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		declarations = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED |Section.TWISTIE);
		declarations.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		declarations.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		declarations.setText(Messages.getString("rule.variables"));
		declarations.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
					if(conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3,Ws4 });
					}
					if(conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3,Ws4 });
					}
					if(!conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] {Ws1,Wsm,Ws3+Ws2-Wsm,Ws4});
					}
					if(!conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] {Ws1,Wsm,Ws3+Ws2-Wsm,Ws4});
					}
				}
				else{
					if(conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Ws3,Ws4 });
					}
					if(!conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm),Ws4});
					}
					if(conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Wsm,Ws4 });
					}
					if(!conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm),Ws4 });
					}
				}
				managedForm.getForm().reflow(true);
			}
		});
	
		Composite client = toolkit.createComposite(declarations, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		client.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		
		declarations.setClient(client);

		createToolbar(client, true);
		
		declarationsTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION);
		declarationsTable.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		declarationsTable.setLayoutData(gd);
//		declarationsTable.setFont(declfont);

		TableColumn typeColumn = new TableColumn(declarationsTable, SWT.NULL);
		typeColumn.setText(Messages.getString("ruletemplate.variable.col.type"));
		
		TableColumn nameColumn = new TableColumn(declarationsTable, SWT.NULL);
		nameColumn.setText(Messages.getString("ruletemplate.variable.col.name"));
//		termColumn.setWidth(429);

		TableColumn exprColumn = new TableColumn(declarationsTable, SWT.NULL);
		exprColumn.setText(Messages.getString("ruletemplate.variable.col.expr"));
//		aliasColumn.setWidth(430);
		
		declarationsTable.setLinesVisible(true);
		declarationsTable.setHeaderVisible(true);
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(declarationsTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		
		declarationsTable.setLayout(autoTableLayout);

		StudioUIUtils.addRemoveControlListener(this, editor.isEnabled());
        
		if(editor.isEnabled()){
			setDeclarationTableEditableSupport(declarationsTable, 
					1, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.DECLARE_BLOCK, 
					RulesParser.DECLARATOR, 
					this, 
					true);

			setDeclarationTableEditableSupport(declarationsTable, 
					2, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.DECLARE_BLOCK, 
					RulesParser.EXPRESSION, 
					this, 
					true);
			
			setDeclarationScopeDropTarget(declarationsTable, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.DECLARE_BLOCK, 
					RulesParser.DECLARATOR);
		}
	
	}

	protected void updateActionContextSection() {
		// action context section
		removeActionContextButton.setEnabled(false);
		upActionContextButton.setEnabled(false);
		downActionContextButton.setEnabled(false);
		TableItem[] items = actionContextTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		RuleTemplate template = (RuleTemplate) getCompilable();
		ActionContext context = template.getActionContext();
		if (context == null) {
			return;
		}
		updateActionContextTable(context.getActionContextSymbols());
	}

	private void updateActionContextTable(Symbols symbols) {
		if (symbols == null) {
			return;
		}
		EList<Symbol> symbolList = symbols.getSymbolList();
		for (Symbol symbol2 : symbolList) {
			ActionContextSymbol symbol = (ActionContextSymbol) symbol2;
			String type = symbol.getType();
			TableItem tableItem = new TableItem(actionContextTable, SWT.NONE);
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
				}
			}
//			tableItem.setText(0, symbol.isArray()? symbol.getType() + "[]": symbol.getType());
			tableItem.setText(ID_COLUMN, symbol.getIdName());
			tableItem.setText(ACTION_TYPE_COLUMN, symbol.getActionType().getLiteral());
		}
	}

	@Override
	protected void createConditionsPart(final IManagedForm managedForm,
			Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		conditions = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		conditions.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		conditions.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		conditions.setText(Messages.getString("rule.preconditions"));
		conditions.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {

				if(e.getState() == true ){
					if(declarations.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3,Ws4 });
					}
					if(!declarations.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Ws3,Ws4 });
					}
					if(declarations.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3,Ws4});
					}
					if(!declarations.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Ws3,Ws4 });
					}
				}
				else{
					if(declarations.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Wsm,Ws3+Ws2-Wsm,Ws4 });
					}
					if(!declarations.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm),Ws4});
					}
					if(declarations.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Wsm,Ws3+Ws2-Wsm,Ws4 });
					}
					if(!declarations.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Wsm,Wst-(2*Wsm),Ws4 });
					}
				}
				managedForm.getForm().reflow(true);
			}
		});
	
		Composite client = toolkit.createComposite(conditions, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		client.setLayout(layout);
		
		toolkit.paintBordersFor(client);
		conditions.setClient(client);
		
		DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
		EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
		OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);
		
		condSourceViewer = new ProjectionViewer(client, new VerticalRuler(12, access), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		condSourceViewer.getTextWidget().setFont(font);
		
		condSourceViewer.setOverviewRulerAnnotationHover(new DefaultAnnotationHover());
		condSourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		if(editor.isEnabled()){
			setDropTarget(condSourceViewer, editor.getSite().getPage(), editor);
		}
		
		setupSourceViewerDecorationSupport(condSourceViewer, ruler, access, sharedColors);
	}
	
	protected void createActionContextPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		actionContext = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED |Section.TWISTIE);
		actionContext.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		actionContext.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		// use source based editor
		actionContext.setVisible(false);
		
		actionContext.setText(Messages.getString("rule.actionContext"));
		actionContext.addExpansionListener(new ExpansionAdapter() {
//			public void expansionStateChanged(ExpansionEvent e) {
//				if(e.getState() == true ){
//					if(conditions.isExpanded() && actions.isExpanded()){
//						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3,Ws4 });
//					}
//					if(conditions.isExpanded() && !actions.isExpanded()){
//						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3,Ws4 });
//					}
//					if(!conditions.isExpanded() && actions.isExpanded()){
//						sashForm.setWeights(new int[] {Ws1,Wsm,Ws3+Ws2-Wsm,Ws4});
//					}
//					if(!conditions.isExpanded() && !actions.isExpanded()){
//						sashForm.setWeights(new int[] {Ws1,Wsm,Ws3+Ws2-Wsm,Ws4});
//					}
//				}
//				else{
//					if(conditions.isExpanded() && actions.isExpanded()){
//						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Wsm,Wsm });
//					}
//					if(!conditions.isExpanded() && actions.isExpanded()){
//						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm),Wsm});
//					}
//					if(conditions.isExpanded() && !actions.isExpanded()){
//						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Wsm,Wsm });
//					}
//					if(!conditions.isExpanded() && !actions.isExpanded()){
//						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm),Wsm });
//					}
//				}
//				managedForm.getForm().reflow(true);
//			}
		});
	
		Composite client = toolkit.createComposite(actionContext, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		client.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		toolkit.paintBordersFor(client);
		
		actionContext.setClient(client);
		
		createActionContextButtons(toolkit, client, true);
		
		actionContextTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION);
		actionContextTable.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		actionContextTable.setLayoutData(gd);
//		actionContextTable.setFont(declfont);
			
		TableColumn termColumn = new TableColumn(actionContextTable, SWT.NULL);
		termColumn.setText(Messages.getString("rule.declaration.col.term"));
//		termColumn.setWidth(429);

		TableColumn aliasColumn = new TableColumn(actionContextTable, SWT.NULL);
		aliasColumn.setText(Messages.getString("rule.declaration.col.alias"));
		
		TableColumn actionTypeColumn = new TableColumn(actionContextTable, SWT.NULL);
		actionTypeColumn.setText(Messages.getString("ruletemplate.variable.col.actionType"));
		
		actionContextTable.setLinesVisible(true);
		actionContextTable.setHeaderVisible(true);
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(actionContextTable);
		autoTableLayout.addColumnData(new ColumnWeightData(6));
		autoTableLayout.addColumnData(new ColumnWeightData(3));
		autoTableLayout.addColumnData(new ColumnWeightData(3));
		actionContextTable.setLayout(autoTableLayout);

		addRemoveControlListener(this, editor.isEnabled());
        
		if(editor.isEnabled()){
			setDeclarationTableEditableSupport(actionContextTable, 
					ID_COLUMN, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.ACTION_CONTEXT_BLOCK, 
					RulesParser.IDENTIFIER,
					this, 
					true);

			setDeclarationTableEditableSupport(actionContextTable, 
					ACTION_TYPE_COLUMN, null, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.ACTION_CONTEXT_BLOCK, 
					RulesParser.ACTION_TYPE,
					this, 
					true,
					new String[] { ActionType.CREATE.getLiteral(), ActionType.MODIFY.getLiteral(), ActionType.CALL.getLiteral() },
					true);
			
			setDeclarationScopeDropTarget(actionContextTable, 
					RulesParser.RULE_TEMPLATE_DECL, 
					RulesParser.ACTION_CONTEXT_BLOCK, 
					RulesParser.STATEMENTS); // FIXME : Should not be STATEMENTS
		}
	
	}
	
	protected void createActionContextButtons(final FormToolkit toolkit, final Composite composite,boolean updown) {
		Composite base = toolkit.createComposite(composite,SWT.NULL);
		base.setLayout(new GridLayout(1,false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		base.setLayoutData(gd);
		createActionContextPropertyToolbar(base, updown);
	}

	protected ToolBar createActionContextPropertyToolbar(Composite parent, boolean updown) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        
        addActionContextButton = new ToolItem(toolBar, SWT.PUSH);
        Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_ADD);
        addActionContextButton.setImage(addImg);
        addActionContextButton.setToolTipText("Add");
        addActionContextButton.setText("Add");
        
        addActionContextButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				selectAndAddActionContextStatement(RulesParser.RULE_TEMPLATE_DECL, RulesParser.ACTION_CONTEXT_BLOCK, RulesParser.ACTION_CONTEXT_STATEMENT);
			}
		});
        
        removeActionContextButton = new ToolItem(toolBar, SWT.PUSH);
        Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
        removeActionContextButton.setImage(delImg);
        removeActionContextButton.setToolTipText("Delete");
        removeActionContextButton.setText("Delete");
        removeActionContextButton.setEnabled(false);
        removeActionContextButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				removeActionContextStatement(RulesParser.RULE_TEMPLATE_DECL, RulesParser.ACTION_CONTEXT_BLOCK, RulesParser.ACTION_CONTEXT_STATEMENT);
			}
		});
        
        if (updown) {
        	upActionContextButton = new ToolItem(toolBar, SWT.PUSH);
        	Image upImg = EditorsUIPlugin.getDefault().getImage("icons/arrow_up.png");
        	upActionContextButton.setImage(upImg);
        	upActionContextButton.setToolTipText("Up");
        	upActionContextButton.setText("Up");
        	upActionContextButton.setEnabled(false);
        	upActionContextButton.addListener(SWT.Selection, new Listener() {

        		@Override
        		public void handleEvent(Event event) {
        			reorderActionContextStatements(RulesParser.RULE_TEMPLATE_DECL, RulesParser.ACTION_CONTEXT_BLOCK, RulesParser.ACTION_CONTEXT_STATEMENT, true);
        		}
        	});
        	downActionContextButton = new ToolItem(toolBar, SWT.PUSH);
        	Image downImg = EditorsUIPlugin.getDefault().getImage("icons/arrow_down.png");
        	downActionContextButton.setImage(downImg);
        	downActionContextButton.setEnabled(false);
        	downActionContextButton.setToolTipText("Down");
        	downActionContextButton.setText("Down");
        	downActionContextButton.addListener(SWT.Selection, new Listener() {

        		@Override
        		public void handleEvent(Event event) {
        			reorderActionContextStatements(RulesParser.RULE_TEMPLATE_DECL, RulesParser.ACTION_CONTEXT_BLOCK, RulesParser.ACTION_CONTEXT_STATEMENT, false);
        		}
        	});
        }
        toolBar.pack();
        return toolBar;
	}
	
	@Override
	protected void createActionsPart(final IManagedForm managedForm, Composite parent) {
	
		FormToolkit toolkit = managedForm.getToolkit();
		
		actions = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		actions.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		actions.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		// DISABLE FOR NOW
//		actions.setVisible(false);
		
		actions.setText(Messages.getString("rule.actionContext"));
		actions.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				managedForm.getForm().reflow(true);
			}
		});
		Composite client = toolkit.createComposite(actions, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		client.setLayout(layout);
		
		toolkit.paintBordersFor(client);
		actions.setClient(client);
		
		DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
		EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
		OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);
		
		actionsSourceViewer = new ProjectionViewer(client, new VerticalRuler(12, access), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		actionsSourceViewer.getTextWidget().setFont(font);
	
		actionsSourceViewer.setOverviewRulerAnnotationHover(new DefaultAnnotationHover());
		actionsSourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		actionsSourceViewer.getTextWidget().setDragDetect(true);
		
		if(editor.isEnabled()){
			setDropTarget(actionsSourceViewer, editor.getSite().getPage(), editor);
		}
		
		setupSourceViewerDecorationSupport(actionsSourceViewer, ruler, access, sharedColors);
	}
	
	@Override
	protected void setCompilable(Compilable compilable) {
		if(compilable instanceof Rule){
			rule = (Rule)compilable;
		}
	}

	protected void updateWidgets(boolean isDirtyBeforeRefresh,
			RulesASTNode newNode, boolean editorIsEnabled) {
		updateRegions(newNode, RulesParser.RULE_TEMPLATE_DECL, RulesParser.ACTION_CONTEXT_BLOCK);
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
		ruleDescTextViewer.getTextWidget().addModifyListener(fDescModifyListener);
		
		if (rule.getPriority() < 1) {
			// leave modify listener in to automatically set priority to 5
			priority.select(4);
		} else if (rule.getPriority() > priority.getItemCount()) {
			// leave modify listener in to automatically set priority to priority.getItemCount() - 1
			priority.select(priority.getItemCount()-1);
		} else {
			priority.removeModifyListener(fPriorityModifyListener);
			priority.select(rule.getPriority()-1);
			priority.addModifyListener(fPriorityModifyListener);
		}
		
		rankRuleFunctionText.removeModifyListener(fRankModifyListener);
		rankRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		rankRuleFunctionText.setToolTipText("");
		if (rule.getRank() != null) {
			rankRuleFunctionText.setText(rule.getRank());
			RuleElement element = IndexUtils.getRuleElement(editor.getProject().getName(), rule.getRank(), ELEMENT_TYPES.RULE_FUNCTION);
			if(element == null){
				rankRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry",rule.getRank(), "rank");
				rankRuleFunctionText.setToolTipText(problemMessage);
			}
		} else {
			rankRuleFunctionText.setText("");
		}
		rankRuleFunctionText.addModifyListener(fRankModifyListener);

		ruleforwardChain.setSelection(isForwardChain(newNode));
		updateDeclarationsSection();
		updateActionContextSection();
		
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
	protected void updateSourceViewers(final boolean editable) {
		condSourceViewer.getDocument().set(rule.getConditionText());
		condSourceViewer.setEditable(editable);
		actionsSourceViewer.getDocument().set(rule.getActionText());
		actionsSourceViewer.setEditable(editable);
	}

	@Override
	protected void updateRegions(RulesASTNode newNode, int rootType, int blockType) {
		super.updateRegions(newNode, rootType, blockType);
		RulesASTNode ruleNode = newNode.getFirstChildWithType(rootType);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode whenBlock = blocksNode.getChildByFeatureId(RulesParser.WHEN_BLOCK);
		if (whenBlock != null) {
			fCurrentConditionsRegion = new Region(whenBlock.getOffset(), whenBlock.getLength());
		}
	}

	/**
	 * @param newRank
	 */
	protected void setRank(String newRank) {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			if(astNode ==  null) return;
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode attrBlock = blocksNode.getFirstChildWithType(RulesParser.ATTRIBUTE_BLOCK);
			RulesASTNode blockNode = attrBlock.getFirstChildWithType(RulesParser.BLOCK);
			RulesASTNode statements = blockNode.getFirstChildWithType(RulesParser.STATEMENTS);

			RulesASTNode rankStatement = statements.getFirstChildWithType(RulesParser.RANK_STATEMENT);

			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
			TextEdit textEdit = null;

			if(rankStatement == null){
				// no alias statement yet, add it
				int lineOfOffset = getDocument().getLineOfOffset(statements.getOffset()+statements.getLength()-1);
				int offset = getDocument().getLineOffset(lineOfOffset);
				String text = "\t\trank = "+newRank+";\n";
				textEdit = new InsertEdit(offset, text);
			}else{
				RulesASTNode rank = rankStatement.getChildByFeatureId(RulesParser.NAME);
				String oldRank = rank.getText();
				if (oldRank.equals(newRank)) {
					return;
				}
				if (newRank == null || newRank.length() == 0) {
					textEdit = analyzer.analyzeASTNodeDelete(rankStatement);
				} else {
					String text = "rank = "+newRank+";";
					textEdit = analyzer.analyzeASTNodeReplace(rankStatement, text);
				}
			}
			applyTextEdit(textEdit);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param index
	 */
	protected void setPriority(int index) {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			if(astNode ==  null) return;
			// get the priority AST node and replace it with the new priority
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode attrBlock = blocksNode.getFirstChildWithType(RulesParser.ATTRIBUTE_BLOCK);
			RulesASTNode node = attrBlock.getFirstChildWithType(RulesParser.BLOCK);
			RulesASTNode attrStatements = node.getFirstChildWithType(RulesParser.STATEMENTS);
			RulesASTNode prioStatement = attrStatements.getFirstChildWithType(RulesParser.PRIORITY_STATEMENT);

			if(prioStatement != null){
				RulesASTNode prio = prioStatement.getChildByFeatureId(RulesParser.PRIORITY);
				String text = prio.getText();
				String newPrio = String.valueOf(index+1);
				if (newPrio.equals(text)) {
					return;
				}
				ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
				TextEdit textEdit = analyzer.analyzeASTNodeReplace(prioStatement, "priority = "+newPrio+";");
				applyTextEdit(textEdit);
				editor.modified();
			}else{
				String newPrio = "\t\tpriority = "+String.valueOf(index+1)+";\n";
				doSourceChange(attrBlock, RulesParser.PRIORITY_STATEMENT, RulesParser.STATEMENTS, newPrio);
				editor.modified();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param forwardChain
	 */
	protected void setForwardChain(boolean forwardChain) {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			if(astNode ==  null) return;
			// get the forwardChain AST node and replace it with the new forwardChain
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode attrBlock = blocksNode.getFirstChildWithType(RulesParser.ATTRIBUTE_BLOCK);
			RulesASTNode blockNode = attrBlock.getFirstChildWithType(RulesParser.BLOCK);
			RulesASTNode statements = blockNode.getFirstChildWithType(RulesParser.STATEMENTS);

			RulesASTNode fwChainStatement = statements.getFirstChildWithType(RulesParser.FORWARD_CHAIN_STATEMENT);
			//		RulesASTNode bwChainStatement = attrBlock.getFirstChildWithType(RulesParser.BACKWARD_CHAIN_STATEMENT);
			if(fwChainStatement != null){
				RulesASTNode fw = fwChainStatement.getChildByFeatureId(RulesParser.LITERAL);
				String text = fw.getText();
				boolean oldFw = Boolean.parseBoolean(text);
				if (oldFw == forwardChain) {
					return;
				}
				doSourceChange(getDocument(), fwChainStatement, "forwardChain = "+Boolean.toString(forwardChain) +";");
				editor.modified();
			}else{
				String newFwSt = "\t\tforwardChain = "+Boolean.toString(forwardChain) +";\n";
				doSourceChange(attrBlock, RulesParser.FORWARD_CHAIN_STATEMENT, RulesParser.STATEMENTS, newFwSt);
				editor.modified();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param document
	 * @param rulesASTNode
	 * @param statement
	 */
	private void doSourceChange(IDocument document, RulesASTNode rulesASTNode, String statement){
		ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
		TextEdit textEdit = analyzer.analyzeASTNodeReplace(rulesASTNode, statement);
		applyTextEdit(textEdit);
	}
	
	/**
	 * @param blockNode
	 * @param type
	 * @param featureId
	 * @param newText
	 * @throws Exception
	 */
	private void doSourceChange(RulesASTNode blockNode, int type, int featureId, String newText) throws Exception{
		RulesASTNode prioNode = new RulesASTNode(new CommonToken(type));
		RulesASTNode nameNode = RuleGrammarUtils.createName(newText);
		prioNode.addChildByFeatureId(nameNode, featureId);
		String text = RuleGrammarUtils.rewriteNode(prioNode);
		int lineOfOffset = getDocument().getLineOfOffset(blockNode.getOffset()+blockNode.getLength()-1);
		int offset = getDocument().getLineOffset(lineOfOffset);
		InsertEdit textEdit = new InsertEdit(offset, text);
		applyTextEdit(textEdit);
	}
	
	/**
	 * @return
	 */
	private boolean isForwardChain(RulesASTNode astNode){
		// get the forwardChain AST node
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode attrBlock = blocksNode.getChildByFeatureId(RulesParser.ATTRIBUTE_BLOCK);
		RulesASTNode statements = attrBlock.getFirstChildWithType(RulesParser.STATEMENTS);
		
		RulesASTNode fwChainStatement = statements.getFirstChildWithType(RulesParser.FORWARD_CHAIN_STATEMENT);
//		RulesASTNode bwChainStatement = attrBlock.getFirstChildWithType(RulesParser.BACKWARD_CHAIN_STATEMENT);
		if (fwChainStatement == null) {
			return false;
		}
		RulesASTNode fw = fwChainStatement.getChildByFeatureId(RulesParser.LITERAL);
		if (fw == null) {
			return false;
		}
		String text = fw.getText();
		return Boolean.parseBoolean(text);
	}
	
	/**
	 * @return
	 */
//	private String getRank(RulesASTNode astNode){
//		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
//		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
//		RulesASTNode attrBlock = blocksNode.getChildByFeatureId(RulesParser.ATTRIBUTE_BLOCK);
//		RulesASTNode statements = attrBlock.getFirstChildWithType(RulesParser.STATEMENTS);
//		RulesASTNode rankStatement = statements.getFirstChildWithType(RulesParser.RANK_STATEMENT);
//		if (rankStatement == null) {
//			return null;
//		}
//		RulesASTNode rank = rankStatement.getChildByFeatureId(RulesParser.NAME);
//		if (rank == null) {
//			return null;
//		}
//		return rank.getText();
//	}
	
	@Override
	protected void init() {
		RulesEditor rulesEditor = editor.getRulesEditor();
		conditionSourceViewerConfiguration = new RulesFormSourceViewerConfiguration(this, IRulesSourceTypes.PRE_CONDITION_SOURCE, rulesEditor);

		condSourceViewer.configure(conditionSourceViewerConfiguration);
		condSourceViewer.setEditable(getFormEditor().getRulesEditor().isEditable());
		
		Document document = new Document(rule.getConditionText());

		document.addDocumentListener(new IDocumentListener(){
			public void documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent event) {
			}
			public void documentChanged(org.eclipse.jface.text.DocumentEvent event) {
				editor.modified();
				setSourceSynchronized(true);
			}
		});
		IDocumentPartitioner partitioner = new FastPartitioner(new RulesPartitionScanner(), 
				new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER, RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		condSourceViewer.setDocument(document, new RulesAnnotationModel());
		
		TextViewerUndoManager conditionSourceUndoManager =  new TextViewerUndoManager(10);
		conditionSourceUndoManager.connect(condSourceViewer);
		
		actionSourceViewerConfiguration = new RulesFormSourceViewerConfiguration(this, IRulesSourceTypes.ACTION_CONTEXT_SOURCE, rulesEditor);
		actionsSourceViewer.configure(actionSourceViewerConfiguration);
		actionsSourceViewer.setEditable(getFormEditor().getRulesEditor().isEditable());

		document = new Document(rule.getActionText());

		document.addDocumentListener(new IDocumentListener(){
			public void documentAboutToBeChanged(
					org.eclipse.jface.text.DocumentEvent event) {
			}
			public void documentChanged(org.eclipse.jface.text.DocumentEvent event) {
				editor.modified();
				setSourceSynchronized(true);
			}});
		partitioner = new FastPartitioner(new RulesPartitionScanner(), 
				new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER, RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		actionsSourceViewer.setDocument(document, new RulesAnnotationModel());
		
		TextViewerUndoManager actionSourceUndoManager =  new TextViewerUndoManager(10);
		actionSourceUndoManager.connect(actionsSourceViewer);
		
		condSourceViewer.getTextWidget().addFocusListener(new FocusListener() {
		
			@Override
			public void focusLost(FocusEvent e) {
				if (!editor.isModified()) {
					return;
				}
				setConditionText();
			}
		
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		actionsSourceViewer.getTextWidget().addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (!editor.isModified()) {
					return;
				}
				setActionText(RulesParser.RULE_TEMPLATE_DECL, RulesParser.ACTION_CONTEXT_BLOCK);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		Document descriptionDocument = new Document(rule.getDescription() == null? "": rule.getDescription());
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
		
		if(editor.isEnabled()){
			setEditContextMenuSupport(condSourceViewer.getTextWidget(), conditionSourceUndoManager, true, this);
			setEditContextMenuSupport(actionsSourceViewer.getTextWidget(), actionSourceUndoManager, true, this);
			setEditContextMenuSupport(ruleDescTextViewer.getTextWidget(), descriptionUndoManager, false, this);

			setKeySupport(this, condSourceViewer.getTextWidget(), conditionSourceUndoManager, conditionSourceViewerConfiguration, condSourceViewer);
			setKeySupport(this, actionsSourceViewer.getTextWidget(), actionSourceUndoManager, actionSourceViewerConfiguration, actionsSourceViewer);
			StudioUIUtils.setKeySupport(ruleDescTextViewer.getTextWidget(), descriptionUndoManager);
		}
		initialized = true;
 	}
	
	@Override
	protected void addAdditionalSymbolInfo(Symbol symbol, TableItem tableItem) {
		if (symbol instanceof RuleTemplateSymbol) {
			tableItem.setText(2, ((RuleTemplateSymbol) symbol).getExpression());
		}
	}

	protected void setConditionText() {
		RulesASTNode astNode = getCurrentASTNode();
		// get the priority AST node and replace it with the new priority
		if(astNode ==  null) return;
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_TEMPLATE_DECL);
		RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
		RulesASTNode whenBlock = null;
		if (blocksNode != null) {
			whenBlock = blocksNode.getChildByFeatureId(RulesParser.WHEN_BLOCK);
		}
		ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(getDocument());
		String source = getViewerSource(condSourceViewer);
		if (whenBlock == null) {
			CommonToken token = new CommonToken(RulesParser.WHEN_BLOCK);
			token.setStartIndex(fCurrentConditionsRegion.getOffset());
			token.setStopIndex(fCurrentConditionsRegion.getOffset()+fCurrentConditionsRegion.getLength()-1);
			whenBlock = new RulesASTNode(token);
			whenBlock.setOffset(fCurrentConditionsRegion.getOffset());
			whenBlock.setLength(fCurrentConditionsRegion.getLength());
		}
		if (whenBlock.getOffset() == 0) {
			// there are no statements under this block yet, replace the entire 'when' block
			whenBlock = (RulesASTNode) whenBlock.getParent();
			TextEdit textEdit = analyzer.analyzeASTNodeReplace(whenBlock, convertBlock(source, RulesParser.WHEN_BLOCK));
			applyTextEdit(textEdit);
			fCurrentConditionsRegion = new Region(textEdit.getOffset(), source.length());
		} else {
			TextEdit textEdit = analyzer.analyzeASTNodeReplace(whenBlock, source);
			applyTextEdit(textEdit);
			fCurrentConditionsRegion = new Region(textEdit.getOffset(), textEdit.getLength());
		}
		setSourceSynchronized(false);
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
	
	public void refreshRank(){
		rankRuleFunctionText.removeModifyListener(fRankModifyListener);
		rankRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		rankRuleFunctionText.setToolTipText("");
		if (!rankRuleFunctionText.getText().trim().equals("")) {
			RuleElement element = IndexUtils.getRuleElement(editor.getProject().getName(),rankRuleFunctionText.getText().trim(), 
					ELEMENT_TYPES.RULE_FUNCTION);
			if(element == null){
				rankRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry",rule.getRank(), "rank");
				rankRuleFunctionText.setToolTipText(problemMessage);
			}
		} 
		rankRuleFunctionText.addModifyListener(fRankModifyListener);
	}
	
	public void updateRule(){
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
		ruleDescTextViewer.setEditable(false);
		rankRuleFunctionText.setEditable(false);
		priority.setEnabled(false);	
		if (dependencyDiagramAction != null){
			dependencyDiagramAction.setEnabled(false);
		}
		if(sequenceDiagramAction != null){
			sequenceDiagramAction.setEnabled(false);	
		}
		ruleforwardChain.setEnabled(false);
	    condSourceViewer.setEditable(false);
	    actionsSourceViewer.setEditable(false);
	    
		removeDeclButton.setEnabled(false);
		addDeclButton.setEnabled(false);
		upDeclButton.setEnabled(false);
		downDeclButton.setEnabled(false);
		browseButton.setEnabled(false);
	}

	@Override
	public void updateDeclarationStatements(int index, int ruleType,
			int blockType, int statementType, boolean up) {
		if (statementType == RulesParser.ACTION_CONTEXT_STATEMENT) {
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

	@Override
	public boolean updateDeclarationStatements(String type, String id, String oldText,
			String newText, int ruleType, int blockType, int statementType,
			Compilable compilable) {
		if (statementType == RulesParser.EXPRESSION || statementType == RulesParser.ACTION_TYPE) {
			updateExpression(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
		} else {
			return super.updateDeclarationStatements(type, id, oldText, newText, ruleType,
					blockType, statementType, compilable);
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
					if (newText.trim().length() == 0) {
						return;
					}
					String newExpr = newText;
					if (statementType == RulesParser.EXPRESSION) {
						newExpr = nameString + " = " + newText;
					}
					TextEdit repEdit = analyzer.analyzeASTNodeReplace(nameNode, newExpr);
					applyTextEdit(repEdit);
				} else {
					if (newText.trim().length() > 0) {
						TextEdit repEdit = analyzer.analyzeASTNodeReplace(exprNode, newText);
						applyTextEdit(repEdit);
					} else {
						// The expression was removed, delete it
						String newExpr = "";
						if (statementType == RulesParser.EXPRESSION) {
							newExpr = typeString + " " + nameString + ";";
							TextEdit repEdit = analyzer.analyzeASTNodeReplace(child, newExpr);
							applyTextEdit(repEdit);
						}
					}
				}
				break;
			}
		}
		refreshRulesFormSourceViewerConfiguration();
	}
	
	protected void selectAndAddActionContextStatement(int ruleType, int blockType, int statementType) {
		if(actionContextTable.getSelectionCount()>0){
			removeActionContextButton.setEnabled(false);
		}
		String title = "";
		ELEMENT_TYPES elType = ELEMENT_TYPES.RULE;
		switch (ruleType) {
		case RulesParser.RULE_DECL:
			title = Messages.getString("rule_declration_selector_title");
			break;

		case RulesParser.RULE_FUNC_DECL:
			title = Messages.getString("rule_function_scope_selector_title");
			elType = ELEMENT_TYPES.RULE_FUNCTION;
			break;
			
		case RulesParser.RULE_TEMPLATE_DECL:
			title = Messages.getString("rule_template_declaration_selector_title");
			elType = ELEMENT_TYPES.RULE_TEMPLATE;
			break;
			
		default:
			break;
		}
		DeclarationSelector picker = new DeclarationSelector(Display.getDefault().getActiveShell(),
				title, getFormEditor().getProject().getName(), elType);
		if (picker.open() == Dialog.OK) {
			try{
				String idName = picker.getIdName();
				String type = picker.getType();
				String actionType = picker.getActionType();
				boolean isArray = picker.isArray();
				RuleTemplate template = (RuleTemplate) getCompilable();
				if (template.getActionContext().getActionContextSymbols() != null) {
					if(isSymbolPresent(template.getActionContext().getActionContextSymbols().getSymbolList(), idName.toLowerCase())){
						idName = getUniqueTag(template.getActionContext().getActionContextSymbols().getSymbolList(), idName+"_");
					}
				} else {
					template.getActionContext().setActionContextSymbols(RuleFactory.eINSTANCE.createSymbols());
				}
				
				Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
				symbol.setType(type);
				symbol.setIdName(idName);
				
				if (ruleType == RulesParser.RULE_FUNC_DECL) {
					symbol.setArray(isArray);
				}
				
				template.getActionContext().getActionContextSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
				
				if(picker.isResource()){
					type = replace(type, "/", ".");
					type = type.substring(1);
				}

				addActionContextStatement(actionType, type, idName, ruleType, blockType, statementType, isArray);
				
				updateActionContextSection();
			}
			catch(Exception e ){
				e.printStackTrace();
			}
		}
	}

	protected void addActionContextStatement(String actionType, String type, String idName, int ruleType, int blockType, int statementType, boolean isArray) {
		try {
			RulesASTNode astNode = getCurrentASTNode();
			RulesASTNode ruleNode = astNode.getFirstChildWithType(ruleType);
			RulesASTNode blocksNode = ruleNode.getFirstChildWithType(RulesParser.BLOCKS);
			RulesASTNode declBlocksNode = blocksNode.getFirstChildWithType(blockType);

			RulesASTNode declNode = new RulesASTNode(new CommonToken(RulesParser.ACTION_CONTEXT_STATEMENT));
			
			RulesASTNode typeNameNode = null;
			if (ruleType == RulesParser.RULE_FUNC_DECL && isArray) {
				type = type + "[]";
				typeNameNode = new RulesASTNode(new CommonToken(RulesParser.SIMPLE_NAME, type));
				typeNameNode.addChild(new RulesASTNode(new CommonToken(RulesParser.Identifier, type)));
			} else {
				typeNameNode = RuleGrammarUtils.createName(type);
			}
			RulesASTNode actionTypeNode = new RulesASTNode(new CommonToken(RulesParser.ACTION_TYPE, actionType));
//			RulesASTNode typeNameNode = RuleGrammarUtils.createName(type);
			RulesASTNode declNameNode = RuleGrammarUtils.createName(idName);
			
			declNode.addChildByFeatureId(actionTypeNode, RulesParser.ACTION_TYPE);
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

	protected void reorderActionContextStatements(int ruleType, int blockType, int statementType, boolean up) {

		int index = actionContextTable.getSelectionIndex();
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
			if(swapIndex == actionContextTable.getItemCount()){
				return;
			}
		}
		Symbol symbol = ((RuleTemplate)getCompilable()).getActionContext().getActionContextSymbols().getSymbolList().get(index);
		Symbol prevSymbol = ((RuleTemplate)getCompilable()).getActionContext().getActionContextSymbols().getSymbolList().get(swapIndex);
		((RuleTemplate)getCompilable()).getActionContext().getActionContextSymbols().getSymbolList().set(swapIndex, symbol);
		((RuleTemplate)getCompilable()).getActionContext().getActionContextSymbols().getSymbolList().set(index, prevSymbol);
		updateDeclarationStatements(index, ruleType, blockType, statementType, up);
		updateActionContextSection();
		
		actionContextTable.select(swapIndex);
	}

	protected void removeActionContextStatement(int ruleType, int blockType, int statementType) {
		TableItem[] selection = actionContextTable.getSelection();
		for (TableItem tableItem : selection) {
			String type = tableItem.getText(0);
			String idName = tableItem.getText(1);
			boolean isArray = false; 
			type = ModelUtils.convertPathToPackage(type);
			
			//Check for Array Type
			if (type.endsWith("[]")) {
				isArray = true;
			}
			
			int index = actionContextTable.getSelectionIndex();
			if (index !=-1) {
				((RuleTemplate)getCompilable()).getActionContext().getActionContextSymbols().getSymbolList().remove(index);
			}
			removeDeclareStatement(type, idName, ruleType, blockType, statementType, isArray);
			tableItem.dispose();
		}
		refreshRulesFormSourceViewerConfiguration();
		
		if (actionContextTable.getItemCount() > 0) {
			actionContextTable.deselectAll();
		}
		removeActionContextButton.setEnabled(false);
	}
	
	private void addRemoveControlListener(IStudioRuleSourceCommon ruleFormEditor, final boolean isEditorEnabled) {
		final Table  declarationsTable = actionContextTable;
		final ToolItem removeDeclButton = removeActionContextButton;
		final ToolItem upDeclButton = upActionContextButton;
		final ToolItem downDeclButton = downActionContextButton;
		declarationsTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int selectionCount = declarationsTable.getSelectionCount();
				final int selectionIndex=declarationsTable.getSelectionIndex();
				final int itemcount=declarationsTable.getItemCount();
				if (!isEditorEnabled) {
					return;
				}

				if (selectionCount > 0 ) {

					removeDeclButton.setEnabled(true);
					if(itemcount>1){
						if(selectionIndex==0){
							if (upDeclButton != null) {
								upDeclButton.setEnabled(false);
							}
							if (downDeclButton != null) {
								downDeclButton.setEnabled(true);
							}
						}else if(selectionIndex==itemcount-1){
							if (upDeclButton != null) {
								upDeclButton.setEnabled(true);
							}
							if (downDeclButton != null) {
								downDeclButton.setEnabled(false);
							}
						}else{
							if (upDeclButton != null) {
								upDeclButton.setEnabled(true);
							}
							if (downDeclButton != null) {
								downDeclButton.setEnabled(true);
							}
						}}
				} else {
					removeDeclButton.setEnabled(false);
					if (upDeclButton != null) {
						upDeclButton.setEnabled(false);
					}
					if (downDeclButton != null) {
						downDeclButton.setEnabled(false);
					}
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}

	@Override
	public IResolutionContext getResolutionContext(
			ElementReference elementReference) {
		IResolutionContext resolutionContext = super.getResolutionContext(elementReference);
		if (resolutionContext instanceof SimpleResolutionContext) {
			List<GlobalVariableDef> globalDefs = getBindingDefs();
			for (GlobalVariableDef globalVariableDef : globalDefs) {
				((SimpleResolutionContext) resolutionContext).addGlobalVariable(globalVariableDef);
			}
		}
		return resolutionContext;
	}

	@Override
	public IResolutionContext getResolutionContext(ElementReference reference,
			ScopeBlock scope) {
		IResolutionContext resolutionContext = super.getResolutionContext(reference, scope);
		if (resolutionContext instanceof SimpleResolutionContext) {
			List<GlobalVariableDef> globalDefs = getBindingDefs();
			for (GlobalVariableDef globalVariableDef : globalDefs) {
				((SimpleResolutionContext) resolutionContext).addGlobalVariable(globalVariableDef);
			}
		}
		return resolutionContext;
	}

	@SuppressWarnings("unchecked")
	private List<GlobalVariableDef> getBindingDefs() {
		final Object[] ret = new Object[1];
		Display.getDefault().syncExec(new Runnable() {
		
			@Override
			public void run() {
				if (rule instanceof RuleTemplate) {
					List<GlobalVariableDef> defs = new ArrayList<GlobalVariableDef>();
					EList<Binding> bindings = ((RuleTemplate) rule).getBindings();
					for (int i = 0; i < bindings.size(); i++) {
						Binding binding = bindings.get(i);
						GlobalVariableExtension gv = new GlobalVariableExtension(getFormEditor().getProject().getName());
						gv.setName(binding.getIdName());
						String type = binding.getType();
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
			}
		});
		return (List<GlobalVariableDef>) ret[0];
	}

	@Override
	protected void add() {
		selectAndAddDeclarator(RulesParser.RULE_TEMPLATE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR);
	}

	@Override
	protected String getUniqueIdName(String idName) {
		// get unique declaration id
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
	public boolean isGlobalVariable(String variableName) {
		if(variableName != null) {
			List<GlobalVariableDef> globalVariables = getGlobalVariableDefs();
			for(GlobalVariableDef globalVariableDef: globalVariables) {
				if(variableName.equalsIgnoreCase(globalVariableDef.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void remove() {
		removeDeclarator(RulesParser.RULE_TEMPLATE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR);
	}

	@Override
	protected void up() {
		reorderDeclarators(RulesParser.RULE_TEMPLATE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR, true);
	}

	@Override
	protected void down() {
		reorderDeclarators(RulesParser.RULE_TEMPLATE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR, false);					
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