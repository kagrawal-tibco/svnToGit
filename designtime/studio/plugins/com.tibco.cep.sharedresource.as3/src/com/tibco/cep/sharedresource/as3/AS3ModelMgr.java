package com.tibco.cep.sharedresource.as3;

import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_GRIDNAME;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_REALMSERVER;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_SECONDARY_REALM;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.as3.AS3Properties.AS3_CHANNEL_PROPERTY_USESSL;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

public class AS3ModelMgr extends SharedResModelMgr {
	private AS3Model model;
	private String encodedKeys[] = new String[] { AS3_CHANNEL_PROPERTY_PASSWORD};

	public AS3ModelMgr(IProject project, AS3Editor editor) {
		super(project, editor);
	}

	public AS3ModelMgr(IResource resource) {
		super(resource);
	}

	public AS3Model getModel() {
		return this.model;
	}

	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put(AS3_CHANNEL_PROPERTY_DESCRIPTION, AS3_CHANNEL_PROPERTY_DESCRIPTION);
		propertyNames.put(AS3_CHANNEL_PROPERTY_REALMSERVER, AS3_CHANNEL_PROPERTY_REALMSERVER);
		propertyNames.put(AS3_CHANNEL_PROPERTY_GRIDNAME, AS3_CHANNEL_PROPERTY_GRIDNAME);
		propertyNames.put(AS3_CHANNEL_PROPERTY_USERNAME, AS3_CHANNEL_PROPERTY_USERNAME);
		propertyNames.put(AS3_CHANNEL_PROPERTY_SECONDARY_REALM, AS3_CHANNEL_PROPERTY_SECONDARY_REALM);
		return propertyNames;
	}

	public void initModel() {
		this.model = new AS3Model();
		this.model.values.put(AS3_CHANNEL_PROPERTY_DESCRIPTION, "");
		this.model.values.put(AS3_CHANNEL_PROPERTY_REALMSERVER, "http://localhost:8080");
		this.model.values.put(AS3_CHANNEL_PROPERTY_GRIDNAME, "");
		this.model.values.put(AS3_CHANNEL_PROPERTY_USERNAME, "");
		this.model.values.put(AS3_CHANNEL_PROPERTY_PASSWORD, "");
		this.model.values.put(AS3_CHANNEL_PROPERTY_SECONDARY_REALM, "");
		this.model.values.put(AS3_CHANNEL_PROPERTY_USESSL, "false");
	}

	public void parseModel() {
		initModel();
		AS3ModelParser parser = new AS3ModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
		decodeFields(encodedKeys);
	}

	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = AS3ModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new AS3ModelParser().saveModelParts(doc, this);
		decodeFields(encodedKeys);	// do this, so that the model always contains the decoded version of the values
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}