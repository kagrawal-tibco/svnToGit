package com.tibco.cep.studio.ui.editors.concepts;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.studio.ui.actions.EntityRenameElementAction;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractScorecardFormViewer;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ScorecardFormViewer extends AbstractScorecardFormViewer {


	private Text scDescText;
	public Composite  exSwtPanel;

	
	public ScorecardFormViewer(ScorecardFormEditor editor) {
		this.editor = editor;
		if (editor != null && editor.getEditorInput() instanceof ScorecardFormEditorInput) {
			   scorecard = ((ScorecardFormEditorInput) editor.getEditorInput()).getScorecard();
		} else {
			scorecard = editor.getScorecard();
		}
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		/**
		 * @see com.tibco.cep.studio.ui.editors.AbstractFormViewer
		 */
		super.createPartControl(container, Messages.getString("scorecard.editor.title") + " " + scorecard.getName(),
				/*EntityImages.getImage(EntityImages.SCORECARD)*/EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
	/*    if(scorecard!=null){
	    	fetchChildren(scorecard);            //reference to old jtable here..commented for  now
	    }*/
		
//	    if (enableMetadataConfigurationEnabled())                 
//			sashForm.setWeights(defaultWeightPropertySections);// setting the default weights for the available sections.
		
	    dependencyDiagramAction = EditorUtils.createDependencyDiagramAction(editor, getForm(), editor.getProject());
		getForm().getToolBarManager().add(dependencyDiagramAction);
		getForm().updateToolBar();
		super.createToolBarActions(); // setting default toolbars 
		
	    scDescText.setText(scorecard!=null?scorecard.getDescription():"");
	
		 //Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
	}
	
	@Override
	protected void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit) {
		
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
		
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
	
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);

		toolkit.createLabel(sectionClient, Messages.getString("Description"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		scDescText = toolkit.createText(sectionClient,"",  SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		scDescText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					if(scorecard.getDescription() ==  null && !scDescText.getText().equalsIgnoreCase("")){
						Command command=new SetCommand(getEditingDomain(),scorecard,ModelPackage.eINSTANCE.getEntity_Description(),scDescText.getText()) ;			
						EditorUtils.executeCommand(editor, command);
						return;
					}
					if(!scDescText.getText().equalsIgnoreCase(scorecard.getDescription())){
						Object value = scDescText.getText().trim().equalsIgnoreCase("")? null: scDescText.getText().trim();
						Command command=new SetCommand(getEditingDomain(),scorecard,ModelPackage.eINSTANCE.getEntity_Description(),value) ;			
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
		condescgd.heightHint = 50;
		scDescText.setLayoutData(condescgd);

		toolkit.createLabel(sectionClient,"");

		toolkit.paintBordersFor(sectionClient);
	}

	
	@Override
	protected void createPropertiesPart(final IManagedForm managedForm,Composite parent) {
		
		FormToolkit toolkit = managedForm.getToolkit();
		propertiesSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED| Section.TWISTIE);
		propertiesSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		propertiesSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		propertiesSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				adjustPropertySections(e, true);
			}
		});
		
		propertiesSection.setText(Messages.getString("PROPERTIES_SECTION"));
		Composite sectionClient = toolkit.createComposite(propertiesSection);
		propertiesSection.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout());
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		propertiesSection.setLayoutData(td);
	
		createPropertiesTable(sectionClient);
		toolkit.paintBordersFor(sectionClient);
	}

	/**
	 * @param scorecard
	 */
	public void doRefresh(Scorecard changedScorecard){
		this.scorecard = changedScorecard;
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
//				propertyToolItem.setSelection(false);
				if (enableMetadataConfigurationEnabled()) {
					extPropSection.setExpanded(false);
				}
				refreshTable();
				if (editor.isDirty()) {
					editor.setModified(false);
					editor.firePropertyChange();
				}
			}});
	}

	private void readOnlyWidgets(){
		scDescText.setEditable(false);
		dependencyDiagramAction.setEnabled(false);
		
		toolBarProvider.getAddItem().setEnabled(false);
		toolBarProvider.getDeleteItem().setEnabled(false);
		
	}


	@Override
	protected void renameElement(PropertyDefinition propertyDefinition) {
		// TODO Auto-generated method stub
		EList<PropertyDefinition> allList = scorecard.getAllProperties();
		List<PropertyDefinition> subList = StudioUIUtils.getSubScorecardProperties(scorecard.getFullPath(), scorecard.getOwnerProjectName());
		allList.addAll(subList);
    	
    	EntityRenameElementAction act = new EntityRenameElementAction(allList);
		act.selectionChanged(null, new StructuredSelection(propertyDefinition));
		act.run(null);
	
		
		
	}

	
	

}