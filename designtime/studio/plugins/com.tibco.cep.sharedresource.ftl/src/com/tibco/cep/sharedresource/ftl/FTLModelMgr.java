package com.tibco.cep.sharedresource.ftl;

import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_REALMSERVER;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_SECONDARY;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USESSL;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

public class FTLModelMgr extends SharedResModelMgr {
	private FTLModel model;
	private String encodedKeys[] = new String[] { CHANNEL_PROPERTY_PASSWORD };

	public FTLModelMgr(IProject project, FTLEditor editor) {
		super(project, editor);
	}

	public FTLModelMgr(IResource resource) {
		super(resource);
	}

	public FTLModel getModel() {
		return this.model;
	}

	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put(CHANNEL_PROPERTY_DESCRIPTION, CHANNEL_PROPERTY_DESCRIPTION);
		propertyNames.put(CHANNEL_PROPERTY_REALMSERVER, CHANNEL_PROPERTY_REALMSERVER);
		propertyNames.put(CHANNEL_PROPERTY_USERNAME, CHANNEL_PROPERTY_USERNAME);
		//propertyNames.put(CHANNEL_PROPERTY_PASSWORD, CHANNEL_PROPERTY_PASSWORD);
		propertyNames.put(CHANNEL_PROPERTY_SECONDARY, CHANNEL_PROPERTY_SECONDARY);
		return propertyNames;
	}

	public void initModel() {
		this.model = new FTLModel();
		this.model.values.put(CHANNEL_PROPERTY_DESCRIPTION, "");
		this.model.values.put(CHANNEL_PROPERTY_REALMSERVER, "http://localhost:8080");
		this.model.values.put(CHANNEL_PROPERTY_USERNAME, "");
		this.model.values.put(CHANNEL_PROPERTY_PASSWORD, "");
		this.model.values.put(CHANNEL_PROPERTY_SECONDARY, "");
		this.model.values.put(CHANNEL_PROPERTY_USESSL, "false");
	}

	public void parseModel() {
		initModel();
	//	new FTLModelParser().loadModel(getFilePath(), this);
		FTLModelParser parser = new FTLModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
		decodeFields(encodedKeys);
	}

	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = FTLModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new FTLModelParser().saveModelParts(doc, this);
		decodeFields(encodedKeys);	// do this, so that the model always contains the decoded version of the values
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
}