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

import com.tibco.cep.sharedresource.ui.util.SharedResourceImages;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.HttpProperties;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;

/*
@author ssailapp
@date Feb 8, 2010 10:55:36 PM
 */

public class ProcUnitBlock extends ClusterConfigBlock {
	private Tree trProcUnits;
	private Button bAdd, bRemove;
	
	public ProcUnitBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		super(page, modelmgr);
		EXPAND_LEVEL = 2;
	}

	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(ProcessingUnit.class, new NodeProcUnitPage(modelmgr, viewer));
		detailsPart.registerPage(HttpProperties.class, new NodeProcUnitHttpPropertiesPage(modelmgr, viewer));
	}
	
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Processing Units");
		section.setDescription("Define the Processing Units in the cluster.");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trProcUnits = toolkit.createTree(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 80;
		trProcUnits.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAdd = toolkit.createButton(buttonsClient, "Add", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getProcUnitBlockAddListener(parent.getShell()));
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getProcUnitBlockRemoveListener());
		bRemove.setEnabled(false);

		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trProcUnits);
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
		BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
	}

	private Listener getProcUnitBlockAddListener(Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					ArrayList<String> names = modelmgr.getProcessingUnitsName();
					String name = IdUtil.generateSequenceId("ProcUnit", names);
					Object newObj = modelmgr.addProcessingUnit(name);
					BlockUtil.refreshViewer(viewer, selObj, newObj);
				}
			}
		};
		return listener;
	}

	private Listener getProcUnitBlockRemoveListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					boolean updated = modelmgr.removeProcessingUnit((ProcessingUnit)selObj);
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
		if (selObj instanceof ProcessingUnit) {
			bRemove.setEnabled(true);
		} else if (selObj instanceof HttpProperties) {
			bAdd.setEnabled(false);
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
			return modelmgr.getProcessingUnits().toArray(new ProcessingUnit[0]);
		}
		
		@Override
		public Object[] getChildren(Object element) {
			/*
			if (element instanceof ProcessingUnit) {
				ArrayList<Object> nodes = new ArrayList<Object>();
				nodes.add(((ProcessingUnit)element).httpProperties);
				return nodes.toArray(new Object[0]);	
			}
			*/
			return new String[0];
		}
	}

	class MasterLabelProvider extends ClusterConfigBlockMasterLabelProvider {
		@Override
		public Image getImage(Object element) {
			if (element instanceof ProcessingUnit) {
				//TODO
			} else if (element instanceof HttpProperties) {
				return (SharedResourceImages.getImage(SharedResourceImages.IMG_SHAREDRES_HTTP));
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		
		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof ProcessingUnit) {
				return ((ProcessingUnit)element).name;
			} else if (element instanceof HttpProperties) {
				return ("Http Properties");
			}
			return element.toString();
		}
	}
}
