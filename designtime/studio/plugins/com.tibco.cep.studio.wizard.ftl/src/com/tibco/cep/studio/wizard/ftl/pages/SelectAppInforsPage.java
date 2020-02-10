package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.model.FTLDataModel;
import com.tibco.cep.studio.wizard.ftl.utils.FileOp;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;

public class SelectAppInforsPage extends FTLWizardPage{

	private String[] endpointItems;
	
	private String[] instanceItems;
	
	private String[] formatItems;
		
	private String ftlJSONStr;
	
	private org.eclipse.swt.widgets.List formatList;
	private Label buildInLabel;
	private Label formatLabel;
	private Combo buildInCombo;
	
	private boolean isSelEt, isSelIe, isSelFt;
	
	private FTLDataModel newData;

	public String getFtlJSONStr() {
		return ftlJSONStr;
	}

	public void setFtlJSONStr(String ftlJSONStr) {
		this.ftlJSONStr = ftlJSONStr;
	}

	public SelectAppInforsPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.selectAppInforWizard.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.selectAppInforWizard.desc"));
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);

		GridData endpointLabelGridData = new GridData(GridData.FILL_HORIZONTAL);
		endpointLabelGridData.horizontalSpan = 2;
		Label endpointLabel = new Label(container, SWT.LEFT);
		endpointLabel.setText(FTLWizardConstants.PAGE_NAME_APP_ENDPOINT_LABEL);
		endpointLabel.setLayoutData(endpointLabelGridData);
		final org.eclipse.swt.widgets.List endpointList = new org.eclipse.swt.widgets.List(
				container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		endpointList.setItems(endpointItems);
		GridData egd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		egd.horizontalSpan = 2;
		endpointList.setLayoutData(egd);
		endpointList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (endpointList.getSelectionIndex() > -1) {
					isSelEt = true;
					newData.setEndpointName(endpointItems[endpointList.getSelectionIndex()]);
					
					if(isSetPageComplete(isSelEt, isSelIe, isSelFt)) {
					    updateStatus(null);
					} else {
						
						updateStatus(Messages.getString("ftl.wizard.page.selectAppInforWizard.tip"));
					
					}
				} else {
					updateStatus(Messages.getString("ftl.wizard.page.selectAppInforWizard.tip"));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		GridData instanceLabelGD = new GridData(GridData.FILL_HORIZONTAL);
		instanceLabelGD.horizontalSpan = 2;
		Label instancelabel = new Label(container, SWT.LEFT);
		instancelabel.setText(FTLWizardConstants.PAGE_NAME_APP_INSTANCE_LABEL);
		instancelabel.setLayoutData(instanceLabelGD);
		final org.eclipse.swt.widgets.List instanceList = new org.eclipse.swt.widgets.List(
				container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		instanceList.setItems(instanceItems);
		GridData igd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		igd.horizontalSpan = 2;
		instanceList.setLayoutData(igd);
		instanceList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (instanceList.getSelectionIndex() > -1) {
					isSelIe = true;
					newData.setInstanceName(instanceItems[instanceList.getSelectionIndex()]);
					if(isSetPageComplete(isSelEt, isSelIe, isSelFt)) {
					    updateStatus(null);
					} else {
						updateStatus(Messages.getString("ftl.wizard.page.selectAppInforWizard.tip"));
					}
				} else {
					updateStatus(Messages.getString("ftl.wizard.page.selectAppInforWizard.tip"));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		
		
		GridData formatLabelGD = new GridData(GridData.FILL_HORIZONTAL);
		formatLabelGD.horizontalSpan = 2;
		formatLabel = new Label(container, SWT.LEFT);
		formatLabel.setText(FTLWizardConstants.PAGE_NAME_APP_FORMAT_LABEL);
		formatLabel.setLayoutData(formatLabelGD);
		formatList = new org.eclipse.swt.widgets.List(
				container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		formatList.setItems(formatItems);
		GridData formatGd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		formatGd.horizontalSpan = 2;
		formatList.setLayoutData(formatGd);
		formatList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (formatList.getSelectionIndex() > -1) {
					isSelFt = true;
					newData.setFormatName(formatItems[formatList.getSelectionIndex()]);
					if(isSetPageComplete(isSelEt, isSelIe, isSelFt)) {
					    updateStatus(null);
					} else {
						updateStatus(Messages.getString("ftl.wizard.page.selectAppInforWizard.tip"));
					}
				} else {
					updateStatus(Messages.getString("ftl.wizard.page.selectAppInforWizard.tip"));
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});		
		setControl(container);
	}
	
	private boolean isSetPageComplete(boolean isSelEt, boolean isSelIe, boolean isSelFt) {
		if(isSelEt && isSelIe && isSelFt) {
			  return true;
		} else {
			  return false;
		}
	}

	@Override
	protected void prepareData() {
		SelectFilesWizardPage filesWizardPage = (SelectFilesWizardPage)getWizard().getPage(FTLWizardConstants.PAGE_NAME_FILE);
		SelectAppPage appPage = (SelectAppPage)getWizard().getPage(FTLWizardConstants.PAGE_NAME_APP);
		String filePath = filesWizardPage.getFilePath();
		
		newData = appPage.getNewModel();
		String appName = newData.getAppName();
	    ftlJSONStr = FileOp.getJSONStr(filePath);
		List<String> endpointList= new ArrayList<String>();
		List<String> instanceList = new ArrayList<String>();
		List<String> formatList = new ArrayList<String>();
		if(!"".equals(ftlJSONStr)) {
			JSONObject jsonObject = JSONObject.parseObject(ftlJSONStr);
			JSONArray appsJSONArray = JSONArray.parseArray(JSONObject.toJSONString(jsonObject.get("applications")));
			for (Object object : appsJSONArray) {
				JSONObject appJSONObj = JSONObject.parseObject(JSONObject.toJSONString(object));
				if(appName.equals(appJSONObj.getString("name"))) {
					 JSONArray array = JSONArray.parseArray(JSONObject.toJSONString(appJSONObj.get("endpoints")));
					 for (Object object2 : array) {
						 endpointList.add(JSONObject.parseObject(JSONObject.toJSONString(object2)).get("name").toString());	
					 }
					 
					 array = JSONArray.parseArray(JSONObject.toJSONString(appJSONObj.get("instances")));
					 for (Object object2 : array) {
						String instanceName = JSONObject.parseObject(JSONObject.toJSONString(object2)).get("name").toString();
						instanceList.add(instanceName);
					 }
					 
					 array = JSONArray.parseArray(JSONObject.toJSONString(appJSONObj.get("preload_format_names")));
					 for (Object object2 : array) {
						 formatList.add(object2.toString());
					 }
				}
			}
			
			endpointItems = new String[endpointList.size()];
			endpointItems = endpointList.toArray(endpointItems);
			
			instanceItems = new String[instanceList.size()];
			instanceItems = instanceList.toArray(instanceItems);
			
			formatItems = new String[formatList.size()];
			formatItems = formatList.toArray(formatItems);

			 isSelEt = false;
			 isSelIe = false;
			 isSelFt = false;
			 setPrepared(true);
		} else {
			MessageBox mBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK);
			mBox.setMessage(Messages.getString("ftl.wizard.error.file"));
			mBox.open();
			setPrepared(false);
		}
	}
	
	

}
