package com.tibco.cep.studio.ui.editors.archives;

import java.awt.Color;
import java.awt.Container;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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

import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractArchiveFormViewer;


/**
 * 
 * @author sasahoo
 *
 */
public class BusinessEventsArchiveFormViewer extends AbstractArchiveFormViewer{

	private Text archiveNameText;
	private Text archiveDescText;
	private Text authorText;
	private Section inputsection;
	private Section objectManagementsection;
    private Section ruleSetssection;
	private Section startupShutdownsection;


	/**
	 * @param archiveEditor
	 * @param archive
	 */
	public BusinessEventsArchiveFormViewer(EnterpriseArchiveEditor archiveEditor, EnterpriseArchive archive){
		this.editor = archiveEditor;
		this.archive = archive;
	}
	
	private Section section;

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container, Messages.getString("bar.editor.page.title"),EditorsUIPlugin.getImageDescriptor("icons/beArchives16x16.png").createImage());
		super.createToolBarActions(); 
	}

	@Override
	protected void createConfigurationPart(ScrolledForm form, FormToolkit toolkit) {
		section = toolkit.createSection(form.getBody(), Section.TITLE_BAR |
			Section.EXPANDED | Section.DESCRIPTION);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		section.setDescription(Messages.getString("GENERAL_SECTION_DESCRIPTION"));

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
		
		toolkit.createLabel(sectionClient, Messages.getString("Name"));
		archiveNameText = toolkit.createText(sectionClient, "");
		archiveNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(sectionClient, Messages.getString("Description"));
		archiveDescText = toolkit.createText(sectionClient, "");
		archiveDescText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		toolkit.createLabel(sectionClient, Messages.getString("bar.editor.page.details.Author"));
		authorText = toolkit.createText(sectionClient, "");
		authorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	@Override
	protected void createRulesetsPart(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		ruleSetssection = toolkit.createSection(parent, Section.TITLE_BAR  |
			Section.DESCRIPTION | Section.EXPANDED | Section.TWISTIE);
		ruleSetssection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		ruleSetssection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		ruleSetssection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
					//TODO
				}
				else{
					//TODO
				}
				getForm().reflow(true);
			}
		});
		ruleSetssection.setText(Messages.getString("RuleSetsSection"));
		ruleSetssection.setDescription(Messages.getString("RuleSetsSection"));
		Composite sectionClient = toolkit.createComposite(ruleSetssection, SWT.EMBEDDED);
		ruleSetssection.setClient(sectionClient);
		ruleSetssection.setExpanded(false);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		ruleSetssection.setLayoutData(td);

		Container panel = getSwingContainer(sectionClient);
		panel.setBackground(Color.WHITE);
		
		//TODO add panel.add(component)

		toolkit.paintBordersFor(sectionClient);
	}
	
	@Override
	protected void createInputPart(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		inputsection = toolkit.createSection(parent, Section.TITLE_BAR |
			Section.EXPANDED |Section.DESCRIPTION|Section.TWISTIE);
		inputsection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		inputsection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		inputsection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
				}
				else{
				}
				getForm().reflow(true);
			}
		});
		inputsection.setText(Messages.getString("InputSection"));
		inputsection.setDescription(Messages.getString("InputSection"));

		Composite sectionClient = toolkit.createComposite(inputsection, SWT.EMBEDDED);
		inputsection.setClient(sectionClient);

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		inputsection.setLayoutData(td);

		Container panel = getSwingContainer(sectionClient);
		panel.setBackground(Color.WHITE);
		//TODO add panel.add(component)

		toolkit.paintBordersFor(sectionClient);
	}
    
	@Override
	protected void createObjectManagementPart(IManagedForm managedForm,	Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		objectManagementsection = toolkit.createSection(parent, Section.TITLE_BAR  |
			Section.DESCRIPTION | Section.EXPANDED | Section.TWISTIE);
		objectManagementsection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		objectManagementsection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		objectManagementsection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
					//TODO
				}
				else{
					//TODO
				}
				getForm().reflow(true);
			}
		});
		objectManagementsection.setText(Messages.getString("ObjectManagementSection"));
		objectManagementsection.setDescription(Messages.getString("ObjectManagementSection"));
		Composite sectionClient = toolkit.createComposite(objectManagementsection, SWT.EMBEDDED);
		objectManagementsection.setClient(sectionClient);
		objectManagementsection.setExpanded(false);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		objectManagementsection.setLayoutData(td);

		Container panel = getSwingContainer(sectionClient);
		panel.setBackground(Color.WHITE);
		
		//TODO add panel.add(component)

		toolkit.paintBordersFor(sectionClient);
	}
   
	@Override
	protected void createStartupShutDownFunctionsPart(IManagedForm managedForm,	Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		startupShutdownsection = toolkit.createSection(parent, Section.TITLE_BAR  | 
			Section.DESCRIPTION | Section.EXPANDED | Section.TWISTIE);
		startupShutdownsection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		startupShutdownsection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		startupShutdownsection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if(e.getState() == true ){
				}
				else{
				}
				getForm().reflow(true);
			}
		});
		startupShutdownsection.setText(Messages.getString("StartupShutDownFunctions"));
		startupShutdownsection.setDescription(Messages.getString("StartupShutDownFunctions"));
		Composite sectionClient = toolkit.createComposite(startupShutdownsection, SWT.EMBEDDED);
		startupShutdownsection.setClient(sectionClient);
		startupShutdownsection.setExpanded(false);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		startupShutdownsection.setLayoutData(td);
		
		Container panel = getSwingContainer(sectionClient);
		panel.setBackground(Color.WHITE);
		
		//TODO add panel.add(component)

		toolkit.paintBordersFor(sectionClient);
	}	
	
	protected void init() {
		if (businessEventsArchiveResource!=null) {
			archiveNameText.setText(businessEventsArchiveResource.getName()!=null?businessEventsArchiveResource.getName():"");
			archiveDescText.setText(businessEventsArchiveResource.getDescription()!=null?businessEventsArchiveResource.getDescription():"");
			authorText.setText(businessEventsArchiveResource.getAuthor()!=null?businessEventsArchiveResource.getAuthor():"");
		}
	}
	
}
