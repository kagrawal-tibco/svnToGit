package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 6, 2010 3:48:41 PM
 */

public class BpmnAbstractDetailsPage implements IDetailsPage {
	
	protected IManagedForm mform;
	protected Section section;
	protected TreeViewer viewer;
	protected Composite client; 
	protected FormToolkit toolkit;

	public BpmnAbstractDetailsPage(TreeViewer viewer) { 
		this.viewer = viewer;
	}
	
	@Override
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		parent.setLayout(layout);

		toolkit = mform.getToolkit();
		section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.marginWidth = 5;
		section.setText(BpmnMessages.getString("bpmnAbstractDetailsPage_section_label"));
		//section.setDescription("");
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		section.setLayoutData(td);
		client = toolkit.createComposite(section);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		glayout.numColumns = 2;
		client.setLayout(glayout);
		
		PanelUiUtil.createSpacer(toolkit, client, 2);

		toolkit.paintBordersFor(section);
		section.setClient(client);
	}

	@Override
	public void commit(boolean onSave) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void initialize(IManagedForm mform) {
		this.mform = mform;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isStale() {
		return false;
	}

	@Override
	public void refresh() {
	}

	@Override
	public void setFocus() {
	}

	@Override
	public boolean setFormInput(Object input) {
		return false;
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		@SuppressWarnings("unused")
		IStructuredSelection ssel = (IStructuredSelection)selection;
	}
}
