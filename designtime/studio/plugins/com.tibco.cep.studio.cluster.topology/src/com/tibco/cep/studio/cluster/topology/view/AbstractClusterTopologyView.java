package com.tibco.cep.studio.cluster.topology.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;

import javax.swing.JRootPane;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.diagramming.utils.SyncXErrorHandler;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractClusterTopologyView extends ViewPart{

	public static final String ID = "com.tibco.cep.studio.cluster.topology.view.processview";

	protected ManagedForm managedForm;
	protected Composite container;
	protected ScrolledPageBook processViewPage;
	
	public void createPartControl(Composite container) {
		this.container = container;
		managedForm = new ManagedForm(container);
		getForm().getBody().setLayout(new FillLayout());
		ScrolledForm form = getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		createFormParts(form, toolkit);
		managedForm.initialize();
	}
	
	@SuppressWarnings("serial")
	protected Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		new SyncXErrorHandler().installHandler();
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}
	
	@Override
	public void setFocus() {
		this.container.setFocus();
	}

	public Control getControl() {
		return getForm();
	}

	public ScrolledForm getForm() {
		return managedForm.getForm();
	}
	public ManagedForm getManagedForm() {
		return managedForm;
	}
	public ScrolledPageBook getProcessViewPage() {
		return processViewPage;
	}
	
	protected abstract void createFormParts(final ScrolledForm form,final FormToolkit toolkit);
}
