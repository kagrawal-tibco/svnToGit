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
public class SharedArchiveFormViewer extends AbstractArchiveFormViewer{
	
		
	private Text archiveNameText;
	private Text archiveDescText;
	
	public SharedArchiveFormViewer(EnterpriseArchiveEditor archiveEditor, EnterpriseArchive archive){
		this.editor = archiveEditor;
		this.archive = archive;
	}
	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		/**
		 * @see com.tibco.cep.studio.ui.editors.AbstractFormViewer
		 */
		super.createPartControl(container,Messages.getString("sar.editor.page.title"),EditorsUIPlugin.getDefault().getImage("icons/sharedArchives16x16.png"));
		super.createToolBarActions();
	}

	@Override
	protected void createConfigurationPart(ScrolledForm form, FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR | 
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
		
	}
	@Override
	protected void createResourcesPart(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section resourcesSection = toolkit.createSection(parent, Section.TITLE_BAR  | 
			Section.DESCRIPTION | Section.EXPANDED | Section.TWISTIE);
		resourcesSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		resourcesSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		resourcesSection.addExpansionListener(new ExpansionAdapter() {
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
		resourcesSection.setText(Messages.getString("sar.editor.page.details.Resources"));
		resourcesSection.setDescription(Messages.getString("sar.editor.page.details.Resources"));
		Composite sectionClient = toolkit.createComposite(resourcesSection, SWT.EMBEDDED);
		resourcesSection.setClient(sectionClient);
		resourcesSection.setExpanded(false);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 228;
		resourcesSection.setLayoutData(td);

		Container panel = getSwingContainer(sectionClient);
		panel.setBackground(Color.WHITE);
		
		//TODO add panel.add(component)

		toolkit.paintBordersFor(sectionClient);
	}
}
