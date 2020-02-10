package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.model.FTLDataModel;
import com.tibco.cep.studio.wizard.ftl.utils.FileOp;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;

public class SelectAppPage extends FTLWizardPage {
	private String[] items;
	
	private List<FTLDataModel> dataModels = new ArrayList<FTLDataModel>();
	private FTLDataModel newModel;	
	
	public List<FTLDataModel> getDataModels() {
		return dataModels;
	}

	public void setDataModels(List<FTLDataModel> dataModels) {
		this.dataModels = dataModels;
	}

	public FTLDataModel getNewModel() {
		return newModel;
	}

	public void setNewModel(FTLDataModel newModel) {
		this.newModel = newModel;
	}

	public SelectAppPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.selectApplicationWizard.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.selectApplicationWizard.desc"));
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		
		container.setLayout(layout);
		Label label = new Label(container, SWT.LEFT);
		label.setText(FTLWizardConstants.PAGE_NAME_SELECT_APP_LABEL);
		final org.eclipse.swt.widgets.List list = new org.eclipse.swt.widgets.List(
				container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setItems(items);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		list.setLayoutData(gd);
		list.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (list.getSelectionIndex() > -1) {
					newModel = new FTLDataModel();
					newModel.setAppName(items[list.getSelectionIndex()]);
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("ftl.wizard.page.selectApplicationWizard.tip"));
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (list.getSelectionIndex() > -1) {
					newModel = new FTLDataModel();
					newModel.setAppName(items[list.getSelectionIndex()]);
					getContainer().showPage(getNextPage());
				}
			}
		});
		setControl(container);
	}

	@Override
	protected void prepareData() {
		if(this.isControlCreated()) {
			this.setControl(null);
		}
		
		try {
			SelectFilesWizardPage filesWizardPage = (SelectFilesWizardPage) getWizard().getPage(FTLWizardConstants.PAGE_NAME_FILE);
			String filePath = filesWizardPage.getFilePath();
			String ftlJSONStr = FileOp.getJSONStr(filePath);
			List<String> appNameList = new ArrayList<String>();
			if (!"".equals(ftlJSONStr)) {
				JSONObject jsonObject = JSONObject.parseObject(ftlJSONStr);
				JSONArray appsJSONArray = JSONArray.parseArray(JSONObject.
						toJSONString(jsonObject.get("applications")));
				for (Object object : appsJSONArray) {
					JSONObject appJSONObj = JSONObject.parseObject(JSONObject.toJSONString(object));
					appNameList.add(appJSONObj.getString("name"));
				}
				items = new String[appNameList.size()];
				items = appNameList.toArray(items);
				setPrepared(true);
			} else {
				MessageBox mBox = new MessageBox(getShell(), SWT.ICON_WARNING
						| SWT.OK);
				mBox.setMessage(Messages.getString("ftl.wizard.error.file"));
				mBox.open();
				setPrepared(false);
			}
		} catch (JSONException  e) {
			// TODO: handle exception
			MessageBox mBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK);
			mBox.setMessage(Messages.getString("ftl.wizard.error.nulldata"));
			mBox.open();
		}
	}
	
	
	
	
	
	
	
}
