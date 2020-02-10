package com.tibco.cep.studio.cluster.topology.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;

public class ClusterDiagramProcessingUnitPropertySection extends AbstractClusterTopologyPropertySection {

	protected Text puNameText;
	protected Button useAsEngineName;
	protected CCombo unitIDReferenceCombo;
	protected Text nrAgentsText;
	protected Text jmxPortText;
	protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));

	/*	getWidgetFactory().createLabel(composite, Messages.getString("unit.id"),  SWT.NONE);
		puNameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		puNameText.addModifyListener(new ModifyListener(){
			@Override
		
			public void modifyText(ModifyEvent e) {
				puNameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				puNameText.setToolTipText("");
				String oldId = processingUnit.getId();
				String newId = puNameText.getText();
				if (!newId.equalsIgnoreCase(oldId == null ? "" : oldId)) {
					if(!((ClusterTopologyDiagramManager)getManager()).checkDuplicateProcessingUnit(processingUnit.getDeploymentUnitImpl().getDeploymentUnit(), newId)) {
						processingUnit.setId(newId);
						
					}
					else {
						puNameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.duplicate.global.var.entry", puNameText.getText(), "Processing Unit");
						puNameText.setToolTipText(problemMessage);
					}
				}

			}});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 615;
		puNameText.setLayoutData(gd);*/

		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);

		getWidgetFactory().createLabel(composite, Messages.getString("unit.idref"),  SWT.NONE);
		unitIDReferenceCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		gd = new GridData();
		gd.widthHint = 300;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		unitIDReferenceCombo.setLayoutData(gd);
		unitIDReferenceCombo.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				String puIdText = unitIDReferenceCombo.getText();
				if(puIdText != null && puIdText.trim().length() > 0 && !puIdText.equalsIgnoreCase(processingUnit.getPuid())){
					processingUnit.setPuid(puIdText);
					// update nrAgentsText
					if (getProcessingUnits().length > 0 ){
						String nrAgents = String.valueOf(getNoOfAgents((puIdText)));
						if(nrAgentsText.getText() == null || !nrAgentsText.getText().equalsIgnoreCase(nrAgents)) {
							nrAgentsText.setText(nrAgents);
						}
					}
				}
			}});

		getWidgetFactory().createLabel(composite, Messages.getString("unit.nrAgents"),  SWT.NONE);
		nrAgentsText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.READ_ONLY | SWT.BACKGROUND);
		nrAgentsText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				String puId = processingUnit.getId();
				if (puId != null && puId.trim().length() > 0) {
					if (getNoOfAgents(puId) > 0){
						String nrAgents = String.valueOf(getNoOfAgents(puId));
						if(!nrAgentsText.getText().equalsIgnoreCase(nrAgents)) {
							nrAgentsText.setText(nrAgents);
						}
					}
				}

			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nrAgentsText.setLayoutData(gd);
		nrAgentsText.setEnabled(false);

		/*Label horizontalSeperator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		horizontalSeperator.setBounds(10000, 10000, 10000,10000);
		gd = new GridData();
		gd.horizontalSpan = 10;
		horizontalSeperator.setLayoutData(gd);*/
		
		getWidgetFactory().createLabel(composite, Messages.getString("unit.id"),  SWT.NONE);
		puNameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		puNameText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				puNameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				puNameText.setToolTipText("");
				String oldId = processingUnit.getId();
				String newId = puNameText.getText();
				if (!newId.equalsIgnoreCase(oldId == null ? "" : oldId)) {
					if(!((ClusterTopologyDiagramManager)getManager()).checkDuplicateProcessingUnit(processingUnit.getDeploymentUnitImpl().getDeploymentUnit(), newId)) {
						processingUnit.setId(newId);
						TSENode node = getSelectedObjectName();
						node.setName(newId);
						TSENodeLabel puLabel = getSelectedObjectLabel();
						if (puLabel != null) {
							//puLabel.setName(newId);
							getManager().refreshLabel(puLabel);
						}	
					} else {
						puNameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.duplicate.global.var.entry", puNameText.getText(), "Processing Unit");
						puNameText.setToolTipText(problemMessage);
					}
				}
			}});
	    gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
	    //gd.verticalIndent = 15;
		gd.widthHint = 615;
		puNameText.setLayoutData(gd);

			
		getWidgetFactory().createLabel(composite, Messages.getString("unit.useAsEngineName"),  SWT.NONE);
		useAsEngineName = new Button(composite, SWT.CHECK);
		useAsEngineName.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selection = useAsEngineName.getSelection();
				if(processingUnit != null && selection != processingUnit.isUseAsEngineName()){
					processingUnit.setUseAsEngineName(selection);
					// TODO add: once is selected/deselected for one PUC, the same button in all PUC are selected/deselected
					// and all model instances are updated.
				}
			}});

		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);

		
		getWidgetFactory().createLabel(composite, Messages.getString("unit.jmxPort"),  SWT.NONE);
		jmxPortText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		jmxPortText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				jmxPortText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				jmxPortText.setToolTipText("");
				if (!isNumeric(jmxPortText.getText())) {
					jmxPortText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.global.var.entry", jmxPortText.getText(), "JMX Port");
					jmxPortText.setToolTipText(problemMessage);
					return;
				}
				if (!jmxPortText.getText().equalsIgnoreCase(processingUnit.getJmxport())) {
					processingUnit.setJmxport(jmxPortText.getText());
				}
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		jmxPortText.setLayoutData(gd);
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseNode != null) {
			if(processingUnit != null){

				// Update the Name field
				String currentId = processingUnit.getId();
				if (currentId != null && currentId.trim().length() > 0 && !puNameText.getText().equalsIgnoreCase(currentId)) {
					puNameText.setText(currentId);
				}
				// Update the UseAsEngineName check box
				boolean isEngineName = processingUnit.isUseAsEngineName();
				if (isEngineName != useAsEngineName.getSelection()) {
					useAsEngineName.setSelection(isEngineName);
				}
				if (getProcessingUnits().length > 0 ) {
					// Update the puid dropdown
					unitIDReferenceCombo.setItems(getProcessingUnits());
					// Update the Number Of Agents field
					String selectedPuId = processingUnit.getPuid();
					if (selectedPuId != null && selectedPuId.trim().length() > 0) {
						unitIDReferenceCombo.setText(selectedPuId);
						if (getNoOfAgents(selectedPuId) != 0) {
							nrAgentsText.setText(String.valueOf(getNoOfAgents(selectedPuId)));
						}
					}
				}
				if(processingUnit.getJmxport() != null){
					jmxPortText.setText(processingUnit.getJmxport());
				} else {
					jmxPortText.setText("");
				}
			}
		}

		if (tseEdge != null) { }
		if (tseGraph != null) { }
	}

	private String[] getProcessingUnits() {
		if(topologyCluster != null){
			String projectCdd = topologyCluster.getProjectCdd();
			if (projectCdd != null && projectCdd.trim().length() > 0) {
				try {
					if(new File(projectCdd).exists()){
						ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(getEditor().getProject(), projectCdd);
						cddModelMgr.parseModel();
						ArrayList<ProcessingUnit> procUnits = cddModelMgr.getProcessingUnits();
						List<String> list = new ArrayList<String>();
						for (ProcessingUnit processingUnit : procUnits) {
							list.add(processingUnit.getName());
						}
						String[] puIdsArray = new String[list.size()];
						return list.toArray(puIdsArray);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new String[0];
				}
			}
		}
		return new String[0];
	}
	
	/**
	 * @param PUId
	 * @param projectCdd
	 * @param cddModelMgr
	 * @return
	 */
	private ProcessingUnit getProcessingUnit(String PUId, String projectCdd, ClusterConfigModelMgr cddModelMgr) {
		cddModelMgr.parseModel();
		ArrayList<ProcessingUnit> procUnits = cddModelMgr.getProcessingUnits();
		for (ProcessingUnit processingUnit : procUnits) {
			if (processingUnit.getName().equals(PUId)) {
				return processingUnit;
			}
		}
		return null;
	}
	
	/**
	 * @param control
	 * @param cddFilePath
	 * @return
	 */
	private int getNoOfAgents(String PUId) {
		if(topologyCluster != null){
			String projectCdd = topologyCluster.getProjectCdd();
			if (projectCdd != null && projectCdd.trim().length() > 0) {
				try {
					if(new File(projectCdd).exists()){
						ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(getEditor().getProject(), projectCdd);
						cddModelMgr.parseModel();
						ProcessingUnit pUnit = getProcessingUnit(PUId, projectCdd, cddModelMgr);
						String[] agentsArray = cddModelMgr.getProcUnitAgentNames(pUnit);
						return agentsArray.length;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * @param puConfigs
	 * @param tag
	 * @return
	 */
	public static String getUniqueId(String id){
		List<Integer> noList = new ArrayList<Integer>();
		String puId;
		String numStr;
		Iterator<String> PUIDs = ClusterTopologyUtils.getPUIDSet().iterator();
		while (PUIDs.hasNext()) {
			puId = PUIDs.next();
			if (puId.startsWith(id)) {
				numStr = puId.substring(puId.indexOf("_") + 1);
				noList.add(ClusterTopologyUtils.getValidNo(numStr));
			}
		}

		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer)noArray[noArray.length - 1]).intValue();
			num++;
			return id + "_" + num;
		}
		return id + "_" + 0;
	}
}