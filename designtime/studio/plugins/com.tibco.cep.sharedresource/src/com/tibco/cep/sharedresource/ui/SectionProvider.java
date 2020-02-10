package com.tibco.cep.sharedresource.ui;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Nov 23, 2009 5:27:00 PM
 */
@SuppressWarnings("unused")
public class SectionProvider {
	
	private IManagedForm managedForm;
	private Composite containerParent;
	private String resourceName;
	private final EditorPart editor;
	
	public SectionProvider(IManagedForm managedForm, Composite containerParent, String resourceName, EditorPart editor) {
		this.managedForm = managedForm;
		this.containerParent = containerParent;
		this.resourceName = resourceName;
		this.editor = editor;
		
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		containerParent.setLayout(layout);
	}

	public Composite createSectionPart(final String sectionName, boolean collapseable) {
		return createSectionPart(containerParent, sectionName, collapseable);
	}

	public Composite createSectionPart(final Composite parent, final String sectionName, boolean collapseable) {
		FormToolkit toolkit = managedForm.getToolkit();
		int style = Section.TITLE_BAR | Section.EXPANDED;
		if (collapseable)
			style |= Section.TWISTIE;
		Section section = toolkit.createSection(parent, style);
		section.setLayout(PanelUiUtil.getCompactGridLayout(1, false));
		section.marginHeight = 0;
		section.marginWidth = 0;
		section.clientVerticalSpacing = 10;
		section.descriptionVerticalSpacing = 0;
		section.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setDragDetect(true);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(sectionName);
		
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		glayout.numColumns = 1;
		sectionClient.setLayout(glayout);
		sectionClient.setLayoutData(new GridData());
		
	    sectionClient.pack();
	    section.addExpansionListener(getExpansionListener(containerParent, managedForm.getForm()));
	    parent.pack();
	    containerParent.pack();
	    section.pack();
	    return sectionClient;
	}
	
	private IExpansionListener getExpansionListener(final Composite parent, final ScrolledForm form) {
		IExpansionListener listener = new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		};
		return listener;
	}
}
