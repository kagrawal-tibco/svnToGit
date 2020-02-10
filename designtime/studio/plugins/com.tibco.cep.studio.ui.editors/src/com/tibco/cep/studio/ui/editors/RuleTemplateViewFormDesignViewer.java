package com.tibco.cep.studio.ui.editors;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextDropTargetEffect;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.text.RuleTemplatePresentationConverter;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractEntityFormViewer;
import com.tibco.cep.studio.ui.widgets.RuleTemplateSelector;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleTemplateViewFormDesignViewer extends AbstractEntityFormViewer {

	private Text descText;
	private Text ruleTemplateText;
	private Button ruleTemplateBrowseButton;
	private RuleTemplateView ruleTemplateView;
	private List bindingList;
	private StyledText htmlText;
	private Text htmlFileText;
	private Button htmlFileButton;
	
	/**
	 * @param editor
	 */
	public RuleTemplateViewFormDesignViewer(RuleTemplateViewFormEditor editor) {
		this.editor = editor;
		if (editor != null && editor.getEditorInput() instanceof  RuleTemplateViewFormEditorInput) {
			ruleTemplateView = ((RuleTemplateViewFormEditorInput) editor.getEditorInput()).getTemplateView();
		} else {
			ruleTemplateView = editor.getRuleTemplateView();
		}
	}

	@Override
	public void dispose() {
//		if (exPanel != null) {
//			exPanel.dispose();
//			exPanel = null;
//		}
		super.dispose();
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {

		/**
		 * @see com.tibco.cep.studio.ui.editors.AbstractFormViewer
		 */
		super.createPartControl(container, Messages.getString("ruletemplateview.editor.title") + " " + ruleTemplateView.getName(),
				EditorsUIPlugin.getDefault().getImage("icons/ruletemplate.png"));
		
//		sashForm.setWeights(new int[] { 50, 50 });// setting the default weights for the available sections.
		createToolBarActions(); // setting default toolbars 
		
		if(ruleTemplateView.getDescription() != null){
			descText.setText(ruleTemplateView.getDescription());
		}
		if(ruleTemplateView.getRuleTemplatePath() != null){
			ruleTemplateText.setText(ruleTemplateView.getRuleTemplatePath());
		} else {
			ruleTemplateText.setText("");
		}

		//Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
		container.addFocusListener(new FocusListener() {
		
			@Override
			public void focusLost(FocusEvent e) {
			}
		
			@Override
			public void focusGained(FocusEvent e) {
				updateBindings(getRuleTemplate());
			}
		});
	}
	
	@SuppressWarnings("serial")
	@Override
	protected void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit) {

		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);

		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		toolkit.createLabel(sectionClient, Messages.getString("Description"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		descText = toolkit.createText(sectionClient,"",  SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		
		descText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				try{
					if(ruleTemplateView.getDescription() ==  null && !descText.getText().equalsIgnoreCase("")){
						Command command=new SetCommand(getEditingDomain(),ruleTemplateView,ModelPackage.eINSTANCE.getEntity_Description(),descText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						return;
					}
					if(!descText.getText().equalsIgnoreCase(ruleTemplateView.getDescription())){
						Object value = descText.getText().trim().equalsIgnoreCase("")? null: descText.getText().trim();
						Command command=new SetCommand(getEditingDomain(),ruleTemplateView,ModelPackage.eINSTANCE.getEntity_Description(),value) ;			
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});

		GridData condescgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		condescgd.widthHint = 600;
		condescgd.heightHint = 30;
		descText.setLayoutData(condescgd);

		Hyperlink link = createLinkField(toolkit, sectionClient, Messages.getString("RuleTemplate"));
		
		Composite templateComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		templateComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		templateComposite.setLayoutData(gd);
		
		ruleTemplateText =toolkit.createText(templateComposite,"", SWT.SINGLE | SWT.BORDER);
		
		addHyperLinkFieldListener(link, ruleTemplateText, editor, editor.getProject().getName(), false, false);
		
		GridData intextgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		intextgd.widthHint = 545;
		ruleTemplateText.setLayoutData(intextgd);
		ruleTemplateText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				try{
					ruleTemplateText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					ruleTemplateText.setToolTipText("");

					if (ruleTemplateView.getRuleTemplatePath() ==  null && !ruleTemplateText.getText().equalsIgnoreCase("")) {
						
						if (IndexUtils.getRule(ruleTemplateView.getOwnerProjectName(), ruleTemplateText.getText().trim(), ELEMENT_TYPES.RULE_TEMPLATE) == null) {
							ruleTemplateText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", ruleTemplateText.getText(), Messages.getString("InheritsFrom"));
							ruleTemplateText.setToolTipText(problemMessage);
//							return;
						}
						
						Command command=new SetCommand(getEditingDomain(),ruleTemplateView,RulePackage.eINSTANCE.getRuleTemplateView_RuleTemplatePath(),ruleTemplateText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						return;
					}
					//Checking valid Path
					if (!ruleTemplateText.getText().trim().equals("") && 
							IndexUtils.getRule(ruleTemplateView.getOwnerProjectName(), ruleTemplateText.getText().trim(), ELEMENT_TYPES.RULE_TEMPLATE) == null) {
						ruleTemplateText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", ruleTemplateText.getText(), Messages.getString("RuleTemplate"));
						ruleTemplateText.setToolTipText(problemMessage);
						return;
					}
					if (ruleTemplateView.getRuleTemplatePath() != null && (!ruleTemplateView.getRuleTemplatePath().equalsIgnoreCase(ruleTemplateText.getText()))) {	
						Object value = ruleTemplateText.getText().trim().equalsIgnoreCase("")? null: ruleTemplateText.getText().trim();
						Command command =new SetCommand(getEditingDomain(),ruleTemplateView,RulePackage.eINSTANCE.getRuleTemplateView_RuleTemplatePath(),value) ;			
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});

		ruleTemplateBrowseButton = new Button(templateComposite, SWT.NONE);
		ruleTemplateBrowseButton.setText(Messages.getString("Browse"));
		ruleTemplateBrowseButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				try{
					if(EditorUtils.saveRuleTemplateEditor(editor.getSite().getPage(), editor.getProject().getName(), true)){
						if(editor.isDirty() && editor.getSite().getPage().getDirtyEditors().length == 1){
							editor.getSite().getPage().saveEditor(editor, false);
							invokeRuleTemplateSelector();
						}else{
							boolean status = MessageDialog.openQuestion(editor.getSite().getShell(),
									"Save Rule Editor", "Rule editors have been modified. Save changes?");
							if(status){
								EditorUtils.saveRuleTemplateEditor(editor.getSite().getPage(), editor.getProject().getName(), false);
								invokeRuleTemplateSelector();
							}
						}
					}else{
						invokeRuleTemplateSelector();
					}
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
			}
		});
		
		toolkit.paintBordersFor(sectionClient);
		createPresentationSection(form, toolkit);
	}

	private void invokeRuleTemplateSelector(){
		RuleTemplateSelector picker = new RuleTemplateSelector(editor.getSite().getShell(),
				editor.getProject().getName());
		if (picker.open() == Dialog.OK) {
			if(picker.getFirstResult() instanceof RuleTemplate){
				RuleTemplate template = (RuleTemplate) picker.getFirstResult();
				ruleTemplateText.setText(template.getFullPath());
				updateBindings(template);
			}
		}
	}
	private void createPresentationSection(final ScrolledForm form,final FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		section.setText(Messages.getString("PRESENTATION_SECTION_TITLE"));

		GridData gd = new GridData(GridData.FILL_BOTH);
		section.setLayoutData(gd);
		section.setLayout(new GridLayout());

		Composite sectionClient = toolkit.createComposite(section, SWT.NULL);
		section.setClient(sectionClient);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
//		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		toolkit.paintBordersFor(sectionClient);
		
		Composite bindingComp = new Composite(sectionClient, SWT.NULL);
		GridData data = new GridData(GridData.FILL_BOTH);
		bindingComp.setLayoutData(data);
		bindingComp.setLayout(new FillLayout());
		final SashForm sashForm = new SashForm(bindingComp, SWT.HORIZONTAL | SWT.NULL);
		sashForm.setLayout(new FillLayout());
		Group bindingGroup = new Group(sashForm, SWT.NULL);
		bindingGroup.setLayout(new FillLayout());
		bindingGroup.setText("Bindings");
		bindingList = new List(bindingGroup, SWT.NULL);
		
		RuleTemplate template = getRuleTemplate();
		if (template != null) {
			updateBindings(template);
		}
		
		Composite previewComp = new Composite(sashForm, SWT.NULL);
		previewComp.setLayout(new GridLayout());
		TabFolder folder = new TabFolder(previewComp, SWT.NULL);
		GridLayout tabLayout = new GridLayout();
		folder.setLayout(tabLayout);
		GridData tabData = new GridData(GridData.FILL_BOTH);
		tabData.widthHint = 150;
		folder.setLayoutData(tabData);

		TabItem sourceItem = new TabItem(folder, SWT.NULL);
		sourceItem.setText("Presentation");
		String initialText = ruleTemplateView.getPresentationText();
		htmlText = new StyledText(folder, SWT.NULL | SWT.WRAP | SWT.V_SCROLL);
		htmlText.setText(initialText);
		sourceItem.setControl(htmlText);
		
		if(editor.isEnabled()) {
			addDNDSupport(bindingList, htmlText);
		}
		
		TabItem htmlFileItem = new TabItem(folder, SWT.NULL);
		htmlFileItem.setText("HTML File");
		Composite htmlFileComposite = new Composite(folder, SWT.NULL);
		GridLayout htmlLayout = new GridLayout();
		htmlLayout.numColumns = 3;
		htmlFileComposite.setLayout(htmlLayout);
		htmlFileComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label htmlFileLabel = new Label(htmlFileComposite, SWT.NULL);
		htmlFileLabel.setText("HTML File");
		htmlFileText = new Text(htmlFileComposite, SWT.BORDER);
		if (ruleTemplateView.getHtmlFile() != null) {
			htmlFileText.setText(ruleTemplateView.getHtmlFile());
		}
		htmlFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		htmlFileButton = new Button(htmlFileComposite, SWT.NONE);
		htmlFileButton.setText(Messages.getString("Browse"));
		htmlFileItem.setControl(htmlFileComposite);
		
		TabItem previewItem = new TabItem(folder, SWT.NULL);
		previewItem.setText("Preview");
		final Browser browser = new Browser(folder, SWT.NULL);
		browser.setText(new RuleTemplatePresentationConverter(getRuleTemplate(), initialText).getConvertedHtml());
		previewItem.setControl(browser);
		htmlText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String text = htmlText.getText();
				browser.setText(text);
				if (editor.isEnabled()) {
					Command command = new SetCommand(getEditingDomain(), ruleTemplateView,
							RulePackage.eINSTANCE.getRuleTemplateView_PresentationText(), text);
					EditorUtils.executeCommand(editor, command);
					RuleTemplatePresentationConverter con = new RuleTemplatePresentationConverter(getRuleTemplate(),
							text);
					browser.setText(con.getConvertedHtml());
				}
			}
		});
		htmlFileButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell());
				dialog.setFilterExtensions(new String[] {"*.html"});
				IEditorInput editorInput = RuleTemplateViewFormDesignViewer.this.editor.getEditorInput();
				if (editorInput instanceof FileEditorInput) {
					IProject proj = ((FileEditorInput) editorInput).getFile().getProject();
					dialog.setFilterPath(proj.getLocation().toOSString());
					String filePath = dialog.open();
					if (filePath != null) {
						filePath = new Path(filePath).makeRelativeTo(proj.getLocation()).toOSString();
						htmlFileText.setText(filePath);
						Command command=new SetCommand(getEditingDomain(),ruleTemplateView,RulePackage.eINSTANCE.getRuleTemplateView_HtmlFile(),filePath) ;			
						EditorUtils.executeCommand(editor, command);
						RuleTemplatePresentationConverter con = new RuleTemplatePresentationConverter(getRuleTemplate(), getHtmlFileText(filePath));
						browser.setText(con.getConvertedHtml());
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	protected String getHtmlFileText(String filePath) {
		return ""; // TODO
	}

	private void updateBindings(RuleTemplate template) {
		if (template == null) {
			bindingList.setItems(new String[0]);
			return;
		}
		EList<Binding> bindings = template.getBindings();
		String[] items = new String[bindings.size()];
		for (int i=0; i<bindings.size(); i++) {
			items[i] = bindings.get(i).getIdName();
		}
		
		bindingList.setItems(items);
	}

	private void addDNDSupport(final List bindingList, final StyledText htmlText) {
		Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		
		final DragSource source = new DragSource (bindingList, operations);
		source.setTransfer(types);
		final String[] dragSourceItem = new String[1];
		source.addDragListener (new DragSourceListener () {
			public void dragStart(DragSourceEvent event) {
				String[] selection = bindingList.getSelection();
				if (selection.length > 0) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			};
			public void dragSetData (DragSourceEvent event) {
				event.data = dragSourceItem[0];
			}
			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0] = null;
			}
		});
		
		DropTarget target = new DropTarget(htmlText, DND.DROP_MOVE | DND.DROP_COPY);
		target.setTransfer(types);
		target.addDropListener(new StyledTextDropTargetEffect(htmlText));
		target.addDropListener (new DropTargetAdapter(){
			
			@Override
			public void drop(DropTargetEvent event) {
				htmlText.getSelection();
				htmlText.insert(buildBindingText(event.data));
			}

			private String buildBindingText(Object data) {
				return "<binding id=\""+data+"\"></binding>";
			}
			
		});
	}
	
	private RuleTemplate getRuleTemplate() {
		String templatePath = ruleTemplateView.getRuleTemplatePath();
		RuleElement ruleElement = IndexUtils.getRuleElement(ruleTemplateView.getOwnerProjectName(), templatePath, ELEMENT_TYPES.RULE_TEMPLATE);
		if (ruleElement != null) {
			return (RuleTemplate) ruleElement.getRule();
		}
		return null;
	}

	/**
	 * @param changedConcept
	 */
	public void doRefresh(RuleTemplateView changedRuleTemplateView){
		this.ruleTemplateView = changedRuleTemplateView;
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				if(ruleTemplateView.getRuleTemplatePath()!= null){
					ruleTemplateText.setText(ruleTemplateView.getRuleTemplatePath());
				} else {
					ruleTemplateText.setText("");
				}
				updateBindings(getRuleTemplate());
				if (editor.isDirty()) {
					editor.setModified(false);
					editor.firePropertyChange();
				}
			}});
	}
	
	public void refreshFieldOnFocus() {
		if(ruleTemplateView.getRuleTemplatePath()!= null){
			ruleTemplateText.setText(ruleTemplateView.getRuleTemplatePath());
		} else {
			ruleTemplateText.setText("");
		}
	}
	
	private void readOnlyWidgets() {
		descText.setEditable(false);
		ruleTemplateText.setEditable(false);
		ruleTemplateBrowseButton.setEnabled(false);
		htmlText.setEnabled(false);
		htmlFileText.setEnabled(false);
		htmlFileButton.setEnabled(false);
	}


	public void update() {
		updateBindings(getRuleTemplate());
	}

	@Override
	protected void createToolBarActions() {
		// no default toolbar actions
	}

	@Override
	protected void createPropertiesColumnList() {
		// TODO Auto-generated method stub
		
	}
	
	
}