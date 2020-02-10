package com.tibco.cep.studio.ui.editors.concepts;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.saveConceptEditor;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubConceptProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.ui.actions.EntityRenameElementAction;
import com.tibco.cep.studio.ui.diagrams.IFormEditorContributor;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.editors.utils.FormEditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractConceptFormViewer;
import com.tibco.cep.studio.ui.forms.components.ConceptSelector;
import com.tibco.cep.studio.ui.forms.extendedPropTreeViewer.ExtendedPropTreeViewer;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

/**
 * 
 * @author sasahoo
 *
 */
public class ConceptFormDesignViewer extends AbstractConceptFormViewer {

	private Text conDescText;
	private Text inheritsText;
	private Button button;

	private PropertyTypeCombo stateMachineCombo;
    private Button autoStartStateMachine;
    protected TableViewer smAssociationViewer;

	private Concept superConcept; 
	private Table smTable;
	Composite sectionClient;
	protected boolean isDbConceptsEnabled = false;
	protected org.eclipse.jface.action.Action dbconceptAction;
//	private PropertyDialogTableCellEditor tableCellEditor;
	ArrayList<String> columnnames; 
	private Hyperlink inheritsLink;
	public Composite  exSwtPanel;
	private ExtendedPropTreeViewer extndProp;
	/**
	 * @param editor
	 */
	public ConceptFormDesignViewer(ConceptFormEditor editor) {
		this.editor = editor;
		if (editor != null && editor.getEditorInput() instanceof  ConceptFormEditorInput) {
			concept = ((ConceptFormEditorInput) editor.getEditorInput()).getConcept();
		} else {
			concept = editor.getConcept();
		}
		isDbConceptsEnabled = getInitialSettingForDbConcept();
	}

	@Override
	public void dispose() {
	
//		if (tableCellEditor != null) {
//			tableCellEditor.dispose();
//			tableCellEditor = null;
//		}
//		if (exPanel != null) {
//			exPanel.dispose();
//			exPanel = null;
//		}
		removeSMAssociationButton = null;
		super.dispose();
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {

		/**
		 * @see com.tibco.cep.studio.ui.editors.AbstractFormViewer
		 */
		super.createPartControl(container, Messages.getString("concept.editor.title") + " " + concept.getName(),
				/*EntityImages.getImage(EntityImages.CONCEPT_PALETTE_CONCEPT)*/EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
		
		//if (enableMetadataConfigurationEnabled())
			sashForm.setWeights(getSectionWeights());// setting the default weights for the available sections.
		
		addDbConceptsEnablementAction();
			
		diagramAction = new org.eclipse.jface.action.Action("diagram", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
			public void run() {
//				if(isChecked()){
					IWorkbenchPage page =editor.getEditorSite().getWorkbenchWindow().getActivePage();
					IProject project = editor.getProject();
					IFile file = project.getFile(project.getName()+".conceptview");
					EntityDiagramEditorInput input = new EntityDiagramEditorInput(file,project);
					input.setSelectedEntity(((ConceptFormEditor)editor).getConcept());
					try {
						page.openEditor(input, ConceptDiagramEditor.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}		
					getForm().reflow(true);
//				}
			}
		};
//		diagramAction.setChecked(false);
		diagramAction.setToolTipText(Messages.getString("concept.Diagram.tooltip"));
		diagramAction.setImageDescriptor(EntityImages.getImageDescriptor(EntityImages.CONCEPT_DIAGRAM));
		getForm().getToolBarManager().add(diagramAction);
		
		getForm().updateToolBar();

		//Adding the diagram toolbar button
		dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
		getForm().getToolBarManager().add(dependencyDiagramAction);

		
	//	setDomainCellEditor(concept, 5);
		super.createToolBarActions(); // setting default toolbars
		
		if(concept.getDescription() != null){
			conDescText.setText(concept.getDescription());
		}
		if(concept.getSuperConceptPath() != null){
			inheritsText.setText(concept.getSuperConceptPath());
		}
		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			autoStartStateMachine.setSelection(concept.isAutoStartStateMachine());
		}
		
		
		//Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
	}
	
	private int[] getSectionWeights() {
		IFormEditorContributor[] formEditorContributors = FormEditorUtils.getFormEditorContributors();
		if (formEditorContributors.length == 0) {
			return defaultWeightPropertySections;
		}
		int[] defWeights = defaultWeightPropertySections;
		int[] weights = new int[defaultWeightPropertySections.length + formEditorContributors.length];
		System.arraycopy(defWeights, 0, weights, 0, defWeights.length);
		for (int i=0; i<formEditorContributors.length; i++) {
			weights[defaultWeightPropertySections.length+i] = formEditorContributors[i].getSectionWeight();
		}

		return weights;
	}

	private void addDbConceptsEnablementAction() {
		if (!AddonUtil.isDataModelingAddonInstalled())
			return;
		
		dbconceptAction = new org.eclipse.jface.action.Action("dbconcept", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
			public void run() {
				isDbConceptsEnabled = !isDbConceptsEnabled;
				extPropSection.setEnabled(isDbConceptsEnabled);
				if (!isDbConceptsEnabled) {
					extPropSection.setExpanded(false);
					sashForm.setWeights(getSectionWeights());
				}
				setDbConceptToolTipAndImage();
				getForm().reflow(true);
			}
		};
		setDbConceptToolTipAndImage();
		getForm().getToolBarManager().add(dbconceptAction);
	}

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
		conDescText = toolkit.createText(sectionClient,"",  SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		
		conDescText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				try{
					if(concept.getDescription() ==  null && !conDescText.getText().equalsIgnoreCase("")){
						Command command=new SetCommand(getEditingDomain(),concept,ModelPackage.eINSTANCE.getEntity_Description(),conDescText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						return;
					}
					if(!conDescText.getText().equalsIgnoreCase(concept.getDescription())){
						Object value = conDescText.getText().trim().equalsIgnoreCase("")? null: conDescText.getText().trim();
						Command command=new SetCommand(getEditingDomain(),concept,ModelPackage.eINSTANCE.getEntity_Description(),value) ;			
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
		conDescText.setLayoutData(condescgd);

//		toolkit.createLabel(sectionClient, Messages.getString("InheritsFrom"),  SWT.NONE);
		inheritsLink = createLinkField(toolkit, sectionClient, Messages.getString("InheritsFrom"));
		
		Composite inheritComposite = toolkit.createComposite(sectionClient);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		inheritComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inheritComposite.setLayoutData(gd);
		
		inheritsText =toolkit.createText(inheritComposite,"", SWT.SINGLE | SWT.BORDER);
		
		addHyperLinkFieldListener(inheritsLink, inheritsText, editor, editor.getProject().getName(), false, false);
		
		GridData intextgd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		intextgd.widthHint = 545;
		inheritsText.setLayoutData(intextgd);
		inheritsText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				try{
					inheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					inheritsText.setToolTipText("");

					if (concept.getSuperConceptPath() ==  null && !inheritsText.getText().equalsIgnoreCase("")) {
						
						if (IndexUtils.getEntity(concept.getOwnerProjectName(), inheritsText.getText().trim()) == null) {
							inheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", inheritsText.getText(), Messages.getString("InheritsFrom"));
							inheritsText.setToolTipText(problemMessage);
//							return;
						}
						
						Command command=new SetCommand(getEditingDomain(),concept,ElementPackage.eINSTANCE.getConcept_SuperConceptPath(),inheritsText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						command=new SetCommand(getEditingDomain(),concept,ElementPackage.eINSTANCE.getConcept_SuperConcept(),superConcept) ;			
						EditorUtils.executeCommand(editor, command);
						return;
					}
					//Checking valid Inherit Path
					if (!inheritsText.getText().trim().equals("") && 
							IndexUtils.getEntity(concept.getOwnerProjectName(), inheritsText.getText().trim()) == null) {
						inheritsText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", inheritsText.getText(), Messages.getString("InheritsFrom"));
						inheritsText.setToolTipText(problemMessage);
//						return;
					}
					if (concept.getSuperConceptPath() != null && (!concept.getSuperConceptPath().equalsIgnoreCase(inheritsText.getText()))) {	
						Object value = inheritsText.getText().trim().equalsIgnoreCase("")? null: inheritsText.getText().trim();
						Command command =new SetCommand(getEditingDomain(),concept,ElementPackage.eINSTANCE.getConcept_SuperConceptPath(),value) ;			
						EditorUtils.executeCommand(editor, command);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});

		button = new Button(inheritComposite, SWT.NONE);
		button.setText(Messages.getString("Browse"));
		button.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				try{
					if(saveConceptEditor(editor.getSite().getPage(), editor.getProject().getName(), true)){
						if(editor.isDirty() && editor.getSite().getPage().getDirtyEditors().length == 1){
							editor.getSite().getPage().saveEditor(editor, false);
							invlokeConceptSelector();
						}else{
							boolean status = MessageDialog.openQuestion(editor.getSite().getShell(),
									"Save Concept Editor", "Concept editors have been modified. Save changes?");
							if(status){
								saveConceptEditor(editor.getSite().getPage(), editor.getProject().getName(), false);
								invlokeConceptSelector();
							}
						}
					}else{
						invlokeConceptSelector();
					}
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
			}
		});
		
		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			toolkit.createLabel(sectionClient,Messages.getString("concept_associated_StateMachines")).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
			Composite composite = toolkit.createComposite(sectionClient);
			layout = new GridLayout(1,false);
			layout.verticalSpacing = 0;
			layout.marginWidth = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			createAssociationToolbar(composite);
			removeSMAssociationButton.setEnabled(false);

			smTable = toolkit.createTable(composite, SWT.BORDER);
			gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
			gd.widthHint = 610;
			gd.heightHint = 20;
			smTable.setLayoutData(gd);
			smAssociationViewer = new TableViewer(smTable);
			smAssociationViewer.setContentProvider(new StateMachineAssociationContentProvider());

			if(editor.getEditorInput() instanceof JarEntryEditorInput){
				try {
					JarEntryEditorInput storageInput = (JarEntryEditorInput)editor.getEditorInput();
					IStorage store = storageInput.getStorage();
					if(store instanceof JarEntryFile){
						JarEntryFile jarFile = (JarEntryFile)store;
						smAssociationViewer.setLabelProvider(new StateMachineAssociationLabelProvider(editor.getProject().getName(),
								jarFile.getFullPath() == null ? "" :jarFile.getFullPath().toString(), 
										jarFile.getJarFilePath()== null ? "": jarFile.getJarFilePath()));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			} else if(editor.getEditorInput() instanceof ConceptFormEditorInput){
				smAssociationViewer.setLabelProvider(new StateMachineAssociationLabelProvider(editor.getProject().getName(),
						IndexUtils.getFullPath(((ConceptFormEditorInput) editor.getEditorInput()).getFile())));
			}

			smAssociationViewer.setInput(concept);
			smAssociationViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				/* (non-Javadoc)
				 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
				 */
				public void selectionChanged(SelectionChangedEvent event) {
					if(smAssociationViewer.getSelection().isEmpty()){
						removeSMAssociationButton.setEnabled(false);
					}else{
						if(getEditor().isEnabled()){
							removeSMAssociationButton.setEnabled(true);
						}
					}
				}
			});

			toolkit.createLabel(sectionClient,Messages.getString("autostart_StateMachine"));
			autoStartStateMachine = new Button(sectionClient, SWT.CHECK);

			autoStartStateMachine.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					try{
						Command autostartSMCommand=new SetCommand(getEditingDomain(),(EObject)concept,ElementPackage.eINSTANCE.getConcept_AutoStartStateMachine(),autoStartStateMachine.getSelection()) ;			
						EditorUtils.executeCommand(editor, autostartSMCommand);
					}
					catch(Exception e1){
						e1.printStackTrace();
					}

				}});
		}
		
		toolkit.paintBordersFor(sectionClient);
	}

	
	@Override
	protected void createPropertiesPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		propertiesSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		propertiesSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		propertiesSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		propertiesSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				adjustPropertySections(e, true);
			}
		});

		propertiesSection.setText(Messages.getString("PROPERTIES_SECTION"));

		sectionClient = toolkit.createComposite(propertiesSection);
		propertiesSection.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout());
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = false;
		td.heightHint = 228;
		propertiesSection.setLayoutData(td);

		createPropertiesTable(sectionClient);
		
		toolkit.paintBordersFor(sectionClient);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractFormViewer#createExtendedPropertiesPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createExtendedPropertiesPart(final IManagedForm managedForm,	Composite parent) {
		/*
		if (!enableMetadataConfigurationEnabled())
			return;
			*/
		FormToolkit toolkit = managedForm.getToolkit();
		extPropSection = toolkit.createSection(parent, Section.TITLE_BAR  |Section.EXPANDED| Section.TWISTIE);
		extPropSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		extPropSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		extPropSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				adjustPropertySections(e, false);
			}
		});

		extPropSection.setText(Messages.getString("EXT_PROPERTIES_SECTION"));

		exSwtPanel=toolkit.createComposite(extPropSection, SWT.BORDER);
		exSwtPanel.setLayout(new GridLayout());
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = false;
		td.heightHint = 228;
		extPropSection.setLayoutData(td);
		
		extndProp= new ExtendedPropTreeViewer(editor);
		extndProp.createHierarchicalTree(exSwtPanel);

		extPropSection.setClient(exSwtPanel);
		extPropSection.setExpanded(false);
		extPropSection.setEnabled(enableMetadataConfigurationEnabled());

		toolkit.paintBordersFor(exSwtPanel);
	}
	
	public PropertyTypeCombo getStateMachineCombo() {
		return stateMachineCombo;
	}
	
	private void invlokeConceptSelector(){
		ConceptSelector picker = new ConceptSelector(editor.getSite().getShell(),
				editor.getProject().getName(),
				concept.getFullPath(),
				concept.getSuperConceptPath());
		if (picker.open() == Dialog.OK) {
			if(picker.getFirstResult() instanceof Concept){
				superConcept = (Concept) picker.getFirstResult();
				EList<PropertyDefinition> allList = concept.getAllProperties();
				List<PropertyDefinition> subList = getSubConceptProperties(concept.getFullPath(), concept.getOwnerProjectName());
				allList.addAll(subList);
				if(isPropertyPresent(allList, superConcept.getAllProperties())){
					MessageDialog.openError(editor.getSite().getShell(),
							"Duplicate Property", "Duplicate inherit property.");
					return;
				}
				inheritsText.setText(superConcept.getFullPath());
			}
		}
	}
	
	/**
	 * @param allList
	 * @param inhList
	 * @return
	 */
	private boolean isPropertyPresent(EList<PropertyDefinition> allList, EList<PropertyDefinition> inhList){
		Set<String> set = new HashSet<String>();
		int size = allList.size() + inhList.size();
		for(PropertyDefinition prop: allList){
			set.add(prop.getName());
		}
		for(PropertyDefinition prop: inhList){
			set.add(prop.getName());
		}
		if(set.size() < size){
			return true;
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public TableViewer getSmAssociationViewer() {
		return smAssociationViewer;
	}
	
	/**
	 * @param changedConcept
	 */
	public void doRefresh(Concept changedConcept){
		this.concept = changedConcept;
	
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				if(concept.getSuperConceptPath()!= null){
					inheritsText.setText(concept.getSuperConceptPath());
				}
				if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
					smAssociationViewer.setInput(concept);
					smAssociationViewer.refresh();
				}
				refreshTable();
				if (enableMetadataConfigurationEnabled()) {
					extPropSection.setExpanded(false);
				}
				if (editor.isDirty()) {
					editor.setModified(false);
					editor.firePropertyChange();
				}
			}});
	}
	
	public void refreshFieldOnFocus() {
		if(concept.getSuperConceptPath()!= null){
			inheritsText.setText(concept.getSuperConceptPath());
		}
	}
	
	private void readOnlyWidgets(){
		conDescText.setEditable(false);
		inheritsText.setEditable(false);
		button.setEnabled(false);
		
		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			autoStartStateMachine.setEnabled(false);
			removeSMAssociationButton.setEnabled(false);
		}

		toolBarProvider.getAddItem().setEnabled(false);
		toolBarProvider.getDeleteItem().setEnabled(false);
		
		diagramAction.setEnabled(false);
		dbconceptAction.setEnabled(true);
		dependencyDiagramAction.setEnabled(false);
		inheritsLink.setEnabled(false);
		
		if(AddonUtil.isDataModelingAddonInstalled()) {
			dbconceptAction.setEnabled(false);
		}
	}
	
	protected boolean enableMetadataConfigurationEnabled() {
		// Either DataMod add-on is installed and DBconcepts flag is set to true.
		// (or) Metadata section is enabled using the flag
		return ( (AddonUtil.isDataModelingAddonInstalled() && isDbConceptsEnabled) 
				|| super.enableMetadataConfigurationEnabled());
	}
	
	private boolean getInitialSettingForDbConcept() {
		return (ClusterConfigProjectUtils.isDbConcept(concept));
	}
	
	private void setDbConceptToolTipAndImage() {
		if (!isDbConceptsEnabled) {
			dbconceptAction.setToolTipText("Enable Metadata configuration");
			dbconceptAction.setImageDescriptor(EditorsUIPlugin.getDefault().getImageDescriptor("icons/property_entity.png"));
		} else {
			dbconceptAction.setToolTipText("Disable Metadata configuration");
			dbconceptAction.setImageDescriptor(EditorsUIPlugin.getDefault().getImageDescriptor("icons/property_entity_disabled.png"));
		}
	}

	@Override
	protected void removeAsociation() {
		int index = smAssociationViewer.getTable().getSelectionIndex();
		if (index == -1) {
			return;
		}
		String stateMachinePath = concept.getStateMachinePaths().get(index);

		((ConceptFormEditor)editor).getStateMachinePathSetFromConceptEditor().add(stateMachinePath);
		RemoveCommand removeCommand = new RemoveCommand(	editor.getEditingDomain(), concept.getStateMachinePaths(), stateMachinePath);
		EditorUtils.executeCommand(editor, removeCommand);
		smAssociationViewer.refresh();
		if(smAssociationViewer.getTable().getItemCount() > 0){
			if (index <=  smAssociationViewer.getTable().getItemCount() - 1) {
				Object item = (String)smAssociationViewer.getElementAt(index);
				smAssociationViewer.setSelection(new StructuredSelection(item));
				smAssociationViewer.getTable().setFocus();
			}
			else if(index - 1  <=  smAssociationViewer.getTable().getItemCount() - 1) {
				Object item = (String)smAssociationViewer.getElementAt(index - 1);
				smAssociationViewer.setSelection(new StructuredSelection(item));
				smAssociationViewer.getTable().setFocus();
			}
		}
		removeSMAssociationButton.setEnabled(concept.getStateMachinePaths().size() > 0);
	}
  
    /* (non-Javadoc)
     * @see com.tibco.cep.studio.ui.forms.AbstractEntityFormViewer#renameElement(com.tibco.cep.designtime.core.model.element.PropertyDefinition)
     */
    protected void renameElement(PropertyDefinition propertyDefinition) {
     	EList<PropertyDefinition> allList = concept.getAllProperties();
		List<PropertyDefinition> subList = getSubConceptProperties(concept.getFullPath(), concept.getOwnerProjectName());
		allList.addAll(subList);
    	
    	EntityRenameElementAction act = new EntityRenameElementAction(allList);
		act.selectionChanged(null, new StructuredSelection(propertyDefinition));
		act.run(null);
    }
    
    @Override
	protected void createExtensionContributedParts(IManagedForm managedForm, Composite sashForm) {
		IFormEditorContributor[] formEditorContributors = FormEditorUtils.getFormEditorContributors();
		for (IFormEditorContributor contributor : formEditorContributors) {
			contributor.addSection(managedForm, sashForm, editor);
		}
	}
	
}