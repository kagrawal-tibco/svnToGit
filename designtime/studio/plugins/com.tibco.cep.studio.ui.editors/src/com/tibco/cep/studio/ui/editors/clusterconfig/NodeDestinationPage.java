package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;


/*
@author ssailapp
@date Dec 16, 2009 3:55:37 PM
 */

public class NodeDestinationPage extends ClusterNodeDetailsPage {

	private Label lName, lUri, lPreprocessor, lThreadModel, lThreadAffinityRulefunction, lThreadCount, lQueueSize;
	private Text tName, tUri, tPreprocessor, tThreadAffinityRulefunction, tThreadCount, tQueueSize;
	private Button bPreprocessorBrowse, bThreadAffinityRulefunction;
	private Destination destination;
    private Combo cThreadModel;
    private LinkedHashMap<String, String> threadModelMap;
	
    private static final String DISPLAY_THREAD_MODEL_SHARED_QUEUE = "Shared Queue";
    private static final String DISPLAY_THREAD_MODEL_CALLER = "Caller's Thread";
    private static final String DISPLAY_THREAD_MODEL_WORKERS = "Destination Queue";
    
	public NodeDestinationPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
		initThreadModelMap();
	}

	private void initThreadModelMap() {
		threadModelMap = new LinkedHashMap<String, String>();
		threadModelMap.put(Destination.THREAD_MODEL_SHARED_QUEUE, DISPLAY_THREAD_MODEL_SHARED_QUEUE);
		threadModelMap.put(Destination.THREAD_MODEL_CALLER, DISPLAY_THREAD_MODEL_CALLER);
		threadModelMap.put(Destination.THREAD_MODEL_WORKERS, DISPLAY_THREAD_MODEL_WORKERS);
		threadModelMap.put(DISPLAY_THREAD_MODEL_SHARED_QUEUE, Destination.THREAD_MODEL_SHARED_QUEUE);
		threadModelMap.put(DISPLAY_THREAD_MODEL_CALLER, Destination.THREAD_MODEL_CALLER);
		threadModelMap.put(DISPLAY_THREAD_MODEL_WORKERS, Destination.THREAD_MODEL_WORKERS);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		lName = PanelUiUtil.createLabel(client, "Input Destination ID: ");
		tName = PanelUiUtil.createText(client);
		tName.addListener(SWT.Modify, getNameModifyListener());
		
		lUri = PanelUiUtil.createLabel(client, "URI: ");
		tUri = PanelUiUtil.createText(client);
		tUri.addListener(SWT.Modify, getDestinationFieldListener(Elements.URI.localName, tUri));
		//TODO: Add Destinations selection
		
		lPreprocessor = PanelUiUtil.createLabel(client, "Preprocessor: ");
		final Composite preProcComp = new Composite(client, SWT.NONE);
		preProcComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		preProcComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tPreprocessor = PanelUiUtil.createText(preProcComp);
		bPreprocessorBrowse = PanelUiUtil.createBrowsePushButton(preProcComp, tPreprocessor);
		bPreprocessorBrowse.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				PreprocessorSelectionDialog dialog = new PreprocessorSelectionDialog(preProcComp.getShell());
				ArrayList<String> curList = new ArrayList<String>();
				curList.add(tPreprocessor.getText());
				dialog.open(ClusterConfigProjectUtils.getProjectRuleFunctionNames(modelmgr.project, ClusterConfigProjectUtils.RF_ARGS_TYPE_PREPROCESSOR), curList);
				String selFunction = dialog.getSelectedFunction();
				if (selFunction.trim().equals(""))
					return;
				tPreprocessor.setText(selFunction);
			}
		});
		
		tPreprocessor.addListener(SWT.Modify, getDestinationFieldListener(Elements.PRE_PROCESSOR.localName, tPreprocessor));
	
		lThreadModel = PanelUiUtil.createLabel(client, "Threading Model: ");
		String threadModels[] = new String[]{DISPLAY_THREAD_MODEL_SHARED_QUEUE, DISPLAY_THREAD_MODEL_CALLER, DISPLAY_THREAD_MODEL_WORKERS};
		cThreadModel = PanelUiUtil.createComboBox(client, threadModels);
		cThreadModel.addListener(SWT.Selection, getDestinationFieldListener(Elements.THREADING_MODEL.localName, cThreadModel));

		lThreadAffinityRulefunction = PanelUiUtil.createLabel(client, "Thread affinity Rule Function: ");
		final Composite threadAffinityRulefunctionComp = new Composite(client, SWT.NONE);
		threadAffinityRulefunctionComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		threadAffinityRulefunctionComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tThreadAffinityRulefunction = PanelUiUtil.createText(threadAffinityRulefunctionComp);
		bThreadAffinityRulefunction = PanelUiUtil.createBrowsePushButton(threadAffinityRulefunctionComp, tThreadAffinityRulefunction);
		bThreadAffinityRulefunction.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				PreprocessorSelectionDialog dialog = new PreprocessorSelectionDialog(threadAffinityRulefunctionComp.getShell()) {
					
					@Override
					public void openDialog() {
						this.dialog.setText("Rule Function Selector");
						super.openDialog();
						
					}
				};
				
				Map<String, com.tibco.cep.designtime.core.model.service.channel.Destination> destinationElements = CommonIndexUtils.getAllDestinationsURIMaps(modelmgr.project.getName());
				com.tibco.cep.designtime.core.model.service.channel.Destination currDestination = destinationElements.get(tUri.getText());
				EntityElement eventElement = null;
				if (currDestination != null) {
					DesignerElement designerElement = CommonIndexUtils.getElement(modelmgr.project.getName(), currDestination.getEventURI());
					if (designerElement instanceof EntityElement) {
						eventElement = (EntityElement) designerElement;
					}
				}
				
				ArrayList<String> curList = new ArrayList<String>();
				curList.add(tThreadAffinityRulefunction.getText());
				dialog.open(ClusterConfigProjectUtils.getProjectRuleFunctionNames(modelmgr.project, ClusterConfigProjectUtils.RF_ARGS_TYPE_THREAD_AFFINITY_RULEFUNCTION, eventElement), curList);				
				String selFunction = dialog.getSelectedFunction();
				if (!selFunction.trim().isEmpty())
					tThreadAffinityRulefunction.setText(selFunction);
			}
		});
		
		tThreadAffinityRulefunction.addListener(SWT.Modify, getDestinationFieldListener(Elements.THREAD_AFFINITY_RULE_FUNCTION.localName, tThreadAffinityRulefunction));
		
		lQueueSize = PanelUiUtil.createLabel(client, "Queue Size: ");
		tQueueSize = PanelUiUtil.createText(client);
		tQueueSize.addListener(SWT.Modify, getDestinationFieldListener(Elements.QUEUE_SIZE.localName, tQueueSize));

		lThreadCount = PanelUiUtil.createLabel(client, "Thread Count: ");
		tThreadCount = PanelUiUtil.createText(client);
		tThreadCount.addListener(SWT.Modify, getDestinationFieldListener(Elements.THREAD_COUNT.localName, tThreadCount));
		
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Listener getNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = tName.getText();
				boolean updated = modelmgr.updateDestinationName(destination, newName);
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}

	private Listener getDestinationFieldListener(final String key, final Control tField) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = null; 
				if (tField instanceof Text) {
					newName = ((Text) tField).getText();
				} else if (tField instanceof Combo) {
					newName = ((Combo) tField).getText();
					if (key.equals(Elements.THREADING_MODEL.localName)) {
						newName = threadModelMap.get(newName);
						if (newName.equals(Destination.THREAD_MODEL_CALLER)) {
							enableThreadAffinity(false);
							enableThreadCountQueueSize(false, false);
						} else if (newName.equals(Destination.THREAD_MODEL_SHARED_QUEUE)) {
							enableThreadAffinity(true);
							enableThreadCountQueueSize(false, false);
						} else {
							enableThreadAffinity(true);
							enableThreadCountQueueSize(true, true);
						}
					}
				}
				if (newName != null) {	
					modelmgr.updateDestinationVal(destination, key, newName);
				}
			}
		};
		return listener;
	}
	
	private void enableThreadCountQueueSize(boolean enThreadCount, boolean enQueueSize) {
		lThreadCount.setEnabled(enThreadCount);
		tThreadCount.setEnabled(enThreadCount);
		lQueueSize.setEnabled(enQueueSize);
		tQueueSize.setEnabled(enQueueSize);
	}

	private void enableThreadAffinity(boolean enThreadAffinity) {
		lThreadAffinityRulefunction.setEnabled(enThreadAffinity);
		tThreadAffinityRulefunction.setEnabled(enThreadAffinity);
		bThreadAffinityRulefunction.setEnabled(enThreadAffinity);
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			destination = ((Destination) ssel.getFirstElement());
		else
			destination = null;
		update();
	}
	
	private void update() {
		if (destination != null) {
			lName.setText(destination.isRef? "Ref:                ": "Input Destination ID: ");
			tName.setText(destination.id);
			setVisible(!destination.isRef);
			final String pathUri = destination.destinationVal.get(Elements.URI.localName);
			tUri.setText(pathUri);
			tPreprocessor.setText(destination.destinationVal.get(Elements.PRE_PROCESSOR.localName));
			setThreadModelText(destination, pathUri); //fix for BE-7515
			String threadAffinityRuleFunction = destination.destinationVal.get(Elements.THREAD_AFFINITY_RULE_FUNCTION.localName);
			if (threadAffinityRuleFunction != null)
				tThreadAffinityRulefunction.setText(threadAffinityRuleFunction);
			tThreadCount.setText(destination.destinationVal.get(Elements.THREAD_COUNT.localName));
			tQueueSize.setText(destination.destinationVal.get(Elements.QUEUE_SIZE.localName));
			cThreadModel.notifyListeners(SWT.Selection, new Event());
		}
	}

	//fix for BE-7515
	private void setThreadModelText(final Destination destination, final String pathUri) {
		cThreadModel.setEnabled(true);
		lThreadModel.setEnabled(true);
		/**
		 * The commented code is not required anymore
		 */
//		final List<Entity> allEntities = CommonIndexUtils.getAllEntities(
//				this.modelmgr.project.getName(), ELEMENT_TYPES.CHANNEL);
//		for (final Entity entity : allEntities) {
//			final Channel channel = (Channel) entity;
//			final DriverConfig driver = channel.getDriver();
//			final DRIVER_TYPE driverType = driver.getDriverType();
//			if (driverType.getName().equals(DRIVER_TYPE_HTTP)
//					&& pathUri.startsWith(channel.getFullPath())) {
//				final ExtendedConfiguration extendedConfiguration = driver.getExtendedConfiguration();
//				final EList<SimpleProperty> properties = extendedConfiguration.getProperties();
//				for (final SimpleProperty property : properties) {
//					if (property.getName().equals(SERVER_TYPE_PROPERTY)
//							&& property.getValue().equals(SERVER_TYPE_TOMCAT)) {
//						cThreadModel.setText(DISPLAY_THREAD_MODEL_CALLER);
//						cThreadModel.setEnabled(false);
//						lThreadModel.setEnabled(false);
//						destination.destinationVal.put(
//								Elements.THREADING_MODEL.localName,
//								Destination.THREAD_MODEL_CALLER);
//						break;
//					}
//				}
//				break;
//			}
//		}

		final String threadModel = destination.destinationVal.get(Elements.THREADING_MODEL.localName);
		if (threadModel != null)
			cThreadModel.setText(threadModelMap.get(threadModel));
		else
			cThreadModel.setText(DISPLAY_THREAD_MODEL_SHARED_QUEUE);
		cThreadModel.notifyListeners(SWT.Selection, new Event());
	}

	private void setVisible(boolean en) {
		tName.setEnabled(en);
		
		lUri.setVisible(en);
		tUri.setVisible(en);
		lPreprocessor.setVisible(en);
		tPreprocessor.setVisible(en);
		bPreprocessorBrowse.setVisible(en);
		lThreadAffinityRulefunction.setVisible(en);
		tThreadAffinityRulefunction.setVisible(en);
		bThreadAffinityRulefunction.setVisible(en);
		lThreadModel.setVisible(en);
		cThreadModel.setVisible(en);
		lThreadCount.setVisible(en);
		tThreadCount.setVisible(en);
		lQueueSize.setVisible(en);
		tQueueSize.setVisible(en);
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
