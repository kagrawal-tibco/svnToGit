package com.tibco.cep.sharedresource.rvtransport;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 29, 2009 5:17:26 PM
 */

public class RvTransportModelMgr extends SharedResModelMgr {
	private RvTransportModel model;
	
	public RvTransportModelMgr(IProject project, RvTransportEditor editor) {
		super(project, editor);
	}

	public RvTransportModelMgr(IResource resource) {
		super(resource);
	}
	
	@Override
	public RvTransportModel getModel() {
		return model;
	}

	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put("description", "Description");
		propertyNames.put("showExpertSettings", "RV Type");
		return propertyNames;
	}
	
	public void initModel() {
		model = new RvTransportModel();
		model.values.put("description", "");		
		model.values.put("workerWeight", "1");
		model.values.put("workerTasks", "10");
		model.values.put("workerCompleteTime", "0");
		model.values.put("schedulerWeight", "1");
		model.values.put("scheduleHeartbeat", "1.0");
		model.values.put("scheduleActivation", "3.5");
		model.values.put("showExpertSettings", "reliable");		
		model.values.put("daemon", "");
		model.values.put("network", "");
		model.values.put("service", "");
		model.values.put("useSsl", "false");
		model.values.put("cmName", "");		
		model.values.put("ledgerFile", "");		
		model.values.put("syncLedger", "");		
		model.values.put("requireOld", "");		
		model.values.put("relayAgent", "");		
		model.values.put("operationTimeout", "");		
		model.values.put("cmqName", "");	
	}
	
	@Override
	public void parseModel() {
		initModel();
		RvTransportModelParser parser = new RvTransportModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);
		}
		processModelElementTypes();
	}
	
	public void processModelElementTypes() {
		String booleanKeys[] = new String[] {"useSsl", "syncLedger", "requireOld"};
		for (String key: booleanKeys) {
			String value = (String) model.values.get(key);
			if (!GvUtil.isGlobalVar(value)) {
				model.values.put(key, new Boolean(value));
			}
		}
	}
	
	@Override
	public String saveModel() {
		Document doc = RvTransportModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new RvTransportModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}
