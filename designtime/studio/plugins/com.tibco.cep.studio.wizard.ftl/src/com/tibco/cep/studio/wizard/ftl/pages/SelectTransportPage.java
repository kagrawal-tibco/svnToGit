package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.model.FTLDataModel;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;

public class SelectTransportPage extends FTLWizardPage{

	private String[] tranItems;
	private String selectTran;
	
	private Map<String, List<String>> tranMap;
	
	private Properties props;
	
	private FTLDataModel newData;
	
	public String getSelectTran() {
		return selectTran;
	}

	public void setSelectTran(String selectTran) {
		this.selectTran = selectTran;
	}

	public Map<String, List<String>> getTranMap() {
		return tranMap;
	}

	public void setTranMap(Map<String, List<String>> tranMap) {
		this.tranMap = tranMap;
	}
	

	public SelectTransportPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.selectTransportWizard.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.selectTransportWizard.desc"));
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);

		Label endpointLabel = new Label(container, SWT.LEFT);
		endpointLabel.setText(FTLWizardConstants.PAGE_NAME_APP_TRANSPORT_LABEL);
		final org.eclipse.swt.widgets.List endpointList = new org.eclipse.swt.widgets.List(
				container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		endpointList.setItems(tranItems);
		GridData egd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		endpointList.setLayoutData(egd);
		endpointList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (endpointList.getSelectionIndex() > -1) {
					selectTran = tranItems[endpointList.getSelectionIndex()];
					newData.setTransport(tranItems[endpointList.getSelectionIndex()]);
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("ftl.wizard.page.selectTransportWizard.tip"));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (endpointList.getSelectionIndex() > -1) {
					selectTran = tranItems[endpointList.getSelectionIndex()];
					newData.setTransport(tranItems[endpointList.getSelectionIndex()]);
					updateStatus(null);
					getContainer().showPage(getNextPage());
				}
			}
		});
		
		setControl(container);
	}

	@Override
	protected void prepareData() {
		SelectAppPage appPage = (SelectAppPage)getWizard().getPage(FTLWizardConstants.PAGE_NAME_APP);
		SelectAppInforsPage appInforsPage = (SelectAppInforsPage)getWizard().getPage(FTLWizardConstants.PAGE_NAME_APP_INFORS);
		String jsonString = appInforsPage.getFtlJSONStr();
		newData = appPage.getNewModel();
		String appName = newData.getAppName();
		String endpointName =  newData.getEndpointName();
		String instanceName =  newData.getInstanceName();
		
		String tempConnName = null;
		
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONArray appsJSONArray = JSONArray.parseArray(JSONObject.toJSONString(jsonObject.get("applications")));
		boolean isFindListName = false;
		for (Object object : appsJSONArray) {
			JSONObject appJSONObj = JSONObject.parseObject(JSONObject.toJSONString(object));
			if(appName.equals(appJSONObj.getString("name"))) {
				 JSONArray instances = JSONArray.parseArray(JSONObject.toJSONString(appJSONObj.get("instances")));
				 for (Object instance : instances) {
					 String tempInstanceName = JSONObject.parseObject(JSONObject.toJSONString(instance)).get("name").toString();
					 if(tempInstanceName.equals(instanceName)) {
						 JSONArray endpointsJSONArray = JSONArray.parseArray(JSONObject.toJSONString(JSONObject.
								 parseObject(JSONObject.toJSONString(instance)).get("endpoints")));
						 for (Object endpoint : endpointsJSONArray) {
							 String endpoint_name = JSONObject.parseObject(JSONObject.toJSONString(endpoint)).get("endpoint_name").toString();
							 if(endpoint_name.equals(endpointName)) {
							       String list_name = JSONObject.parseObject(JSONObject.toJSONString(endpoint)).get("list_name").toString();
							       tempConnName = list_name;
							       isFindListName = true;
							       break;
							 }
							  
						 }
					 }
					 if(isFindListName) {
						 break;
					 }
				 }
			}
			if(isFindListName) {
				break;
			}
		}
		
		
		JSONArray connectors = JSONArray.parseArray(JSONObject.
				toJSONString(JSONObject.parseObject(jsonString).get("connector_lists")));
		List<String> tranList = new ArrayList<String>();
		tranMap = new HashMap<String, List<String>>();
		for (Object object : connectors) {
			 String connName = JSONObject.parseObject(JSONObject.toJSONString(object)).get("name").toString();
			 if(tempConnName != null && tempConnName.equals(connName)) {    
				   JSONArray transports = JSONArray.parseArray(JSONObject.
						   toJSONString(JSONObject.parseObject(JSONObject.toJSONString(object)).get("connectors")));
				   for (Object object2 : transports) {
		                String tranName = JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("transport");
		                tranList.add(tranName);
		                String receive = JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("receive");
		                String receive_inbox = JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("receive_inbox");
		                String send = JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("send");
		                String send_inbox = JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("send_inbox");
		                List<String> tranValue = new ArrayList<String>();
		                tranValue.add(receive);
		                tranValue.add(send);
		                tranValue.add(receive_inbox);  
		                tranValue.add(send_inbox);
		                tranMap.put(tranName, tranValue);
				   }
			 }
		}
		tranItems = new String[tranList.size()];
		tranItems = tranList.toArray(tranItems);
		
		String selectFormatName = newData.getFormatName();
		if (selectFormatName != null) {
			JSONArray formatsJsonArray = JSONArray.parseArray(JSONObject.toJSONString(JSONObject.parseObject(jsonString).get("formats")));
			props = new Properties();
			for (Object object : formatsJsonArray) {
				if (selectFormatName.equals(JSONObject.parseObject(JSONObject.toJSONString(object)).get("name"))) {
					JSONArray propArray = JSONArray.parseArray(JSONObject.
							toJSONString(JSONObject.parseObject(JSONObject.toJSONString(object)).get("fields")));
					for (Object object2 : propArray) {
						props.put(JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("name"), JSONObject.parseObject(JSONObject.toJSONString(object2)).getString("type"));
					}
				}
			}
		}
		newData.setFormatProp(props);
		
		setPrepared(true);
	}
	
	

}
