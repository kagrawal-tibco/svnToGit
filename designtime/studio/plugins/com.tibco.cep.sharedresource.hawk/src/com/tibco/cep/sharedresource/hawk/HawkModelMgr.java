package com.tibco.cep.sharedresource.hawk;

import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_HAWKDOAMIN;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVDAEMON;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVNETWORK;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_RVSERVICE;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_SERVER_URL;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_TRANSPORT;
import static com.tibco.cep.driver.hawk.HawkConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.hawk.HawkConstants.TRANSPORT_TYPE_RV;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

public class HawkModelMgr extends SharedResModelMgr {
	private HawkModel model;

	public HawkModelMgr(IProject project, HawkEditor editor) {
		super(project, editor);
	}

	public HawkModelMgr(IResource resource) {
		super(resource);
	}

	public HawkModel getModel() {
		return this.model;
	}

	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put(CHANNEL_PROPERTY_DESCRIPTION, CHANNEL_PROPERTY_DESCRIPTION);
		propertyNames.put(CHANNEL_PROPERTY_HAWKDOAMIN, CHANNEL_PROPERTY_HAWKDOAMIN);
		propertyNames.put(CHANNEL_PROPERTY_TRANSPORT, CHANNEL_PROPERTY_TRANSPORT);
		propertyNames.put(CHANNEL_PROPERTY_RVSERVICE, CHANNEL_PROPERTY_RVSERVICE);
		propertyNames.put(CHANNEL_PROPERTY_RVNETWORK, CHANNEL_PROPERTY_RVNETWORK);
		propertyNames.put(CHANNEL_PROPERTY_RVDAEMON, CHANNEL_PROPERTY_RVDAEMON);
		propertyNames.put(CHANNEL_PROPERTY_SERVER_URL, CHANNEL_PROPERTY_SERVER_URL);
		propertyNames.put(CHANNEL_PROPERTY_USERNAME, CHANNEL_PROPERTY_USERNAME);
		propertyNames.put(CHANNEL_PROPERTY_PASSWORD, CHANNEL_PROPERTY_PASSWORD);
		return propertyNames;
	}

	public void initModel() {
		this.model = new HawkModel();
		this.model.values.put(CHANNEL_PROPERTY_DESCRIPTION, "");
		this.model.values.put(CHANNEL_PROPERTY_HAWKDOAMIN, "default");
		this.model.values.put(CHANNEL_PROPERTY_TRANSPORT, TRANSPORT_TYPE_RV);
		// rv parameters
		this.model.values.put(CHANNEL_PROPERTY_RVSERVICE, "7474");
		this.model.values.put(CHANNEL_PROPERTY_RVNETWORK, "");
		this.model.values.put(CHANNEL_PROPERTY_RVDAEMON, "tcp:7474");
		// ems parameters
		this.model.values.put(CHANNEL_PROPERTY_SERVER_URL, "tcp://localhost:7222");
		this.model.values.put(CHANNEL_PROPERTY_USERNAME, "");
		this.model.values.put(CHANNEL_PROPERTY_PASSWORD, "");
	}

	public void parseModel() {
		initModel();
		//new HawkModelParser().loadModel(getFilePath(), this);
		HawkModelParser parser = new HawkModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
	}

	public String saveModel() {
		Document doc = HawkModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new HawkModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}