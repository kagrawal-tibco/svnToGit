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

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfigs;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfigs;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;

public class LoadBalancerBlock extends ClusterConfigBlock {
	private Tree trLoadBalancer;
	private Button bAdd, bRemove;
	
	public LoadBalancerBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		super(page, modelmgr);
		EXPAND_LEVEL = 2;
	}

	protected void registerPages(DetailsPart detailsPart) {
		//detailsPart.registerPage(LoadBalancerPairConfigs.class, new NodeLoadBalancerPairConfigsPage(modelmgr, viewer));
		detailsPart.registerPage(LoadBalancerPairConfig.class, new NodeLoadBalancerPairConfigPage(modelmgr, viewer));
		//detailsPart.registerPage(LoadBalancerAdhocConfigs.class, new NodeLoadBalancerAdhocConfigsPage(modelmgr, viewer));
		detailsPart.registerPage(LoadBalancerAdhocConfig.class, new NodeLoadBalancerAdhocConfigPage(modelmgr, viewer));
	}
	
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Load Balancer");
		section.setDescription("Define the Load Balancer Configuration");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trLoadBalancer = toolkit.createTree(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 80;
		trLoadBalancer.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAdd = toolkit.createButton(buttonsClient, "Add", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getAddListener(parent.getShell()));
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getRemoveListener());
		bRemove.setEnabled(false);

		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trLoadBalancer);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		addDoubleClickListener(viewer);
		
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		registerSelectionListener();
		resetActionButtons();
		BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
	}

	private Listener getAddListener(Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					Object newObj = null;
					if (selObj instanceof LoadBalancerPairConfigs) {
						ArrayList<String> names = modelmgr.getLoadBalancerPairConfigNames();
						String name = IdUtil.generateSequenceId("Config", names);
						newObj = modelmgr.addLoadBalancerPairConfig(name);
					} else if (selObj instanceof LoadBalancerAdhocConfigs) {
						ArrayList<String> names = modelmgr.getLoadBalancerAdhocConfigNames();
						String name = IdUtil.generateSequenceId("Config", names);
						newObj = modelmgr.addLoadBalancerAdhocConfig(name);
					}
					if (newObj != null) {
						BlockUtil.refreshViewer(viewer, selObj, newObj);
					}
				}
			}
		};
		return listener;
	}

	private Listener getRemoveListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					boolean updated = false;
					updated = modelmgr.removeLoadBalancerConfig((LoadBalancerConfig)selObj);
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
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
		if (selObj instanceof LoadBalancerPairConfigs)
			bAdd.setEnabled(true);
		else if (selObj instanceof LoadBalancerAdhocConfigs)
			bAdd.setEnabled(true);
		else if (selObj instanceof LoadBalancerPairConfig || selObj instanceof LoadBalancerAdhocConfig)
			bRemove.setEnabled(true);
	}

	@Override
	protected void resetActionButtons() {
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
	}
	
	class MasterContentProvider extends ClusterConfigBlockMasterContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			ArrayList<Object> lbConfigs = new ArrayList<Object>();
			lbConfigs.add(modelmgr.getModel().loadBalancerPairConfigs);
			lbConfigs.add(modelmgr.getModel().loadBalancerAdhocConfigs);
			return lbConfigs.toArray(new Object[0]);
		}
		
		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof LoadBalancerPairConfigs) {
				return ((LoadBalancerPairConfigs)element).configs.toArray(new LoadBalancerPairConfig[0]);
			} else if (element instanceof LoadBalancerAdhocConfigs) {
				return ((LoadBalancerAdhocConfigs)element).configs.toArray(new LoadBalancerAdhocConfig[0]);
			}
			return new String[0];
		}
	}

	class MasterLabelProvider extends ClusterConfigBlockMasterLabelProvider {
		@Override
		public Image getImage(Object element) {
			if (element instanceof LoadBalancerPairConfigs) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof LoadBalancerAdhocConfigs) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		
		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof LoadBalancerPairConfigs) {
				return ("Pair Configurations");
			} else if (element instanceof LoadBalancerAdhocConfigs) {
				return ("Adhoc Configurations");
			} else if (element instanceof LoadBalancerConfig) {
				return ((LoadBalancerConfig)element).values.get(LoadBalancerConfig.ELEMENT_NAME);
			}
			return element.toString();
		}
	}
}
