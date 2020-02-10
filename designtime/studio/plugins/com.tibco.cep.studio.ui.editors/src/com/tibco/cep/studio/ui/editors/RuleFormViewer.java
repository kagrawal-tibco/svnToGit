package com.tibco.cep.studio.ui.editors;


import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setKeySupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addRuleHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDropTarget;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setEditContextMenuSupport;

import org.antlr.runtime.CommonToken;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
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

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPartitionScanner;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.wizards.RuleFunctionSelector;


public class RuleFormViewer extends AbstractRuleEntitiesFormViewer {

	private RuleFormEditor editor;
	private Section configuration;
	private Section declarations;
	private Section conditions;
	private Section actions;
	
	private TextViewer ruleDescTextViewer;
	private Text rankRuleFunctionText;
	private Combo priority;	
	private Rule rule;
	
	private Button ruleforwardChain;
	private Button browseButton;
	private Hyperlink rfRankLink;
	
	private ModifyListener fPriorityModifyListener;
	private ModifyListener fRankModifyListener;
	
	private static final int Ws1 = 70;//weight for decl section
	private static final int Ws2 = 60;//weight for cond section
	private static final int Ws3 = 140;//weight for action section
	private static final int Wsm = 15;//min weight
	private static final int Wst = Ws1 + Ws2 + Ws3;
	public static int focusIndex=0;
	
	public RuleFormViewer(RuleFormEditor editor) {
		this.editor = editor;
 	}

	@Override
	public void dispose() {
		super.dispose();
		this.editor = null;
		this.configuration.dispose();
		this.declarations.dispose();
		this.conditions.dispose();
		this.actions.dispose();
		this.priority.dispose();
		this.ruleforwardChain.dispose();
		this.browseButton.dispose();
		
		this.ruleDescTextViewer = null;
		this.rule = null;
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		IEditorInput editorInput = editor.getEditorInput();
		String name = editorInput.getName();
		if (name.contains(".rule")){
			String ruleName = name.substring(0, name.indexOf(".rule"));
			super.createPartControl(container, Messages.getString("rule.editor.title") + " " + ruleName, EditorsUIPlugin.getDefault().getImage("icons/rules.png"));
			
			dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
			getForm().getToolBarManager().add(dependencyDiagramAction);
			sequenceDiagramAction = EditorUtils.createSequenceDiagramAction(editor, getForm(), editor.getProject());
			getForm().getToolBarManager().add(sequenceDiagramAction);
			
			getForm().updateToolBar();
			super.createToolBarActions();
		}
		sashForm.setWeights(new int[] {Ws1, Ws2, Ws3}); 
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
		rfRankLink = createLinkField(toolkit, sectionClient, Messages.getString("RuleFunction_rank"));
		
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
		
		addRuleHyperLinkFieldListener(rfRankLink, rankRuleFunctionText, editor, getProjectName(), false);
		
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
	
	protected void createDeclarationsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		declarations = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED |Section.TWISTIE);
		declarations.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		declarations.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		declarations.setText(Messages.getString("rule.declarations"));
		declarations.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
					if(conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3 });
					}
					if(conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Ws1,Ws2,Ws3 });
					}
					if(!conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] {Ws1,Wsm,Ws3+Ws2-Wsm});
					}
					if(!conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] {Ws1,Wsm,Ws3+Ws2-Wsm});
					}
				}
				else{
					if(conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Ws3 });
					}
					if(!conditions.isExpanded() && actions.isExpanded()){
						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm)});
					}
					if(conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Wsm });
					}
					if(!conditions.isExpanded() && !actions.isExpanded()){
						sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm) });
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
					RulesParser.RULE_DECL, 
					RulesParser.DECLARE_BLOCK, 
					RulesParser.DECLARATOR, 
					this, 
					true);

			setDeclarationScopeDropTarget(declarationsTable, 
					RulesParser.RULE_DECL, 
					RulesParser.DECLARE_BLOCK, 
					RulesParser.DECLARATOR);
		}
	
	}
	 
	public static int getFocusIndex()
	{
		return focusIndex;
	}
	@Override
	protected void createConditionsPart(final IManagedForm managedForm,
			Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		conditions = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		conditions.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		conditions.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		conditions.setText(Messages.getString("rule.conditions"));
		conditions.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {

					if(e.getState() == true ){
						if(declarations.isExpanded() && actions.isExpanded()){
							sashForm.setWeights(new int[] { Ws1,Ws2,Ws3 });
						}
						if(!declarations.isExpanded() && actions.isExpanded()){
							sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Ws3 });
						}
						if(declarations.isExpanded() && !actions.isExpanded()){
							sashForm.setWeights(new int[] { Ws1,Ws2,Ws3});
						}
						if(!declarations.isExpanded() && !actions.isExpanded()){
							sashForm.setWeights(new int[] { Wsm,Ws2+Ws1-Wsm,Ws3 });
						}
					}
					else{
						if(declarations.isExpanded() && actions.isExpanded()){
							sashForm.setWeights(new int[] { Ws1,Wsm,Ws3+Ws2-Wsm });
						}
						if(!declarations.isExpanded() && actions.isExpanded()){
							sashForm.setWeights(new int[] {Wsm,Wsm,Wst-(2*Wsm)});
						}
						if(declarations.isExpanded() && !actions.isExpanded()){
							sashForm.setWeights(new int[] { Ws1,Wsm,Ws3+Ws2-Wsm });
						}
						if(!declarations.isExpanded() && !actions.isExpanded()){
							sashForm.setWeights(new int[] { Wsm,Wsm,Wst-(2*Wsm) });
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

	@Override
	protected void createActionsPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		
		actions = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		actions.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		actions.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		actions.setText(Messages.getString("rule.actions"));
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
		updateRegions(newNode, RulesParser.RULE_DECL, RulesParser.THEN_BLOCK);
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
		
		//Making readonly widgets
		if(!editorIsEnabled){
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
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_DECL);
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
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_DECL);
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
			RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_DECL);
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
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_DECL);
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
//		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_DECL);
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
		conditionSourceViewerConfiguration = new RulesFormSourceViewerConfiguration(this, IRulesSourceTypes.CONDITION_SOURCE, rulesEditor);

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
		
		actionSourceViewerConfiguration = new RulesFormSourceViewerConfiguration(this, IRulesSourceTypes.ACTION_SOURCE, rulesEditor);
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
				setActionText(RulesParser.RULE_DECL, RulesParser.THEN_BLOCK);
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
	
	protected void setConditionText() {
		RulesASTNode astNode = getCurrentASTNode();
		// get the priority AST node and replace it with the new priority
		if(astNode ==  null) return;
		RulesASTNode ruleNode = astNode.getFirstChildWithType(RulesParser.RULE_DECL);
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
		setCompilable(compile);
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
		
		rfRankLink.setEnabled(false);
	}

	@Override
	protected void add() {
		selectAndAddDeclarator(RulesParser.RULE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR);
	}

	@Override
	protected void remove() {
		removeDeclarator(RulesParser.RULE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR);
	}

	@Override
	protected void up() {
		reorderDeclarators(RulesParser.RULE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR, true);
		focusIndex = focusIndex + 1;

	}

	@Override
	protected void down() {
		reorderDeclarators(RulesParser.RULE_DECL, RulesParser.DECLARE_BLOCK, RulesParser.DECLARATOR, false);					
		focusIndex = focusIndex - 1;
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