package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;

/*
@author ssailapp
@date Feb 5, 2010 7:14:37 PM
 */

public class LogConfigBlock extends ClusterConfigBlock {
	private Tree trLogs;
	private Button bAdd, bRemove;
	
	public LogConfigBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		super(page, modelmgr);
	}

	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(LogConfig.class, new NodeLogConfigPage(modelmgr, viewer));
	}
	
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Log Configurations");
		section.setDescription("Define the Log configurations.");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trLogs = toolkit.createTree(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 80;
		trLogs.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAdd = toolkit.createButton(buttonsClient, "Add", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getLogConfigBlockAddListener(parent.getShell()));
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getLogConfigBlockRemoveListener());
		bRemove.setEnabled(false);

		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trLogs);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		addDoubleClickListener(viewer);
		
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		//registerContextMenu(); //TODO - To be enabled 
		registerSelectionListener();
		resetActionButtons();
	}

	private Listener getLogConfigBlockAddListener(Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					ArrayList<String> names = modelmgr.getLogConfigsName();
					String name = IdUtil.generateSequenceId("LogConfig", names);
					Object newObj = modelmgr.addLogConfig(name);
					BlockUtil.refreshViewer(viewer, selObj, newObj);
				}
			}
		};
		return listener;
	}

	private Listener getLogConfigBlockRemoveListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					boolean updated = modelmgr.removeLogConfig((LogConfig)selObj);
					if (updated) {
						BlockUtil.refreshViewer(viewer);
						resetActionButtons();
					}
				}
			}
		};
		return listener;
	}

	@Override
	protected void enableActionButtons(Object selObj) {
		bAdd.setEnabled(true);
		bRemove.setEnabled(false);
		if (selObj instanceof LogConfig) {
			bRemove.setEnabled(true);
		}
	}

	@Override
	protected void resetActionButtons() {
		bAdd.setEnabled(true);
		bRemove.setEnabled(false);
	}
	
	class MasterContentProvider extends ClusterConfigBlockMasterContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return modelmgr.getLogConfigs().toArray(new LogConfig[0]);
		}
	}

	class MasterLabelProvider extends ClusterConfigBlockMasterLabelProvider {
		@Override
		public Image getImage(Object element) {
			if (element instanceof LogConfig) {
				//TODO
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		
		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof LogConfig) {
				return ((LogConfig)element).id;
			}
			return element.toString();
		}
	}
}
