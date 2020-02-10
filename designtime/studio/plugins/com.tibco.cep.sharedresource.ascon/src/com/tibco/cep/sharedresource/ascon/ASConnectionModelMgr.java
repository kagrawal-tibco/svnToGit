package com.tibco.cep.sharedresource.ascon;

import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_AUTH_CREDENTIAL_USERPWD;
import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_ROLE_REQUESTER;
import static com.tibco.cep.driver.as.ASConstants.AS_SECURITY_ROLE_REQUESTOR;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_CREDENTIAL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_DOMAIN;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_KEY_FILE;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_PASSWORD;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_PRIVATE_KEY;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_AUTH_USERNAME;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_DESCRIPTION;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_DISCOVERY_URL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_ENABLE_SECURITY;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_ID_PASSWORD;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_LISTEN_URL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_REMOTE_LISTEN_URL;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_MEMBER_NAME;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_METASPACE_NAME;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_POLICY_FILE;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_SECURITY_ROLE;
import static com.tibco.cep.sharedresource.ascon.ASConnectionModel.AS_CONFIG_TOKEN_FILE;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.jms.JmsConModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author George Wang (nwang@tibco-support.com)
@date Jan 21, 2014
*/

public class ASConnectionModelMgr extends SharedResModelMgr {

	private ASConnectionModel model;
	
	private String[] encodedKeys = { AS_CONFIG_ID_PASSWORD, AS_CONFIG_AUTH_PASSWORD };

	public ASConnectionModelMgr(IProject project, ASConnectionEditor editor) {
		super(project, editor);
	}

	public ASConnectionModelMgr(IResource resource) {
		super(resource);
	}

	@Override
	public void parseModel() {
		initModel();
	//	new ASConnectionModelParser().loadModel(getFilePath(), this);
		ASConnectionModelParser parser = new ASConnectionModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
		backwardCompatibility();
		decodeFields(encodedKeys);
	}

	private void backwardCompatibility() {
        Object securityRole = model.values.get(AS_CONFIG_SECURITY_ROLE);
        if (AS_SECURITY_ROLE_REQUESTOR.equals(securityRole)) {
            model.values.put(AS_CONFIG_SECURITY_ROLE, AS_SECURITY_ROLE_REQUESTER);
        }
    }

    private void initModel() {
		model = new ASConnectionModel();
		model.values.put(AS_CONFIG_DESCRIPTION, "");
		model.values.put(AS_CONFIG_METASPACE_NAME, "ms");
		model.values.put(AS_CONFIG_MEMBER_NAME, "");
		model.values.put(AS_CONFIG_DISCOVERY_URL, "");
		model.values.put(AS_CONFIG_LISTEN_URL, "");
		model.values.put(AS_CONFIG_REMOTE_LISTEN_URL, "");

		model.values.put(AS_CONFIG_ENABLE_SECURITY, "false");
		model.values.put(AS_CONFIG_SECURITY_ROLE, AS_SECURITY_ROLE_REQUESTER);
		model.values.put(AS_CONFIG_POLICY_FILE, "");
		model.values.put(AS_CONFIG_TOKEN_FILE, "");
        model.values.put(AS_CONFIG_ID_PASSWORD, "");

        // security requester
        model.values.put(AS_CONFIG_AUTH_CREDENTIAL, AS_SECURITY_AUTH_CREDENTIAL_USERPWD);

            // user-pwd
            model.values.put(AS_CONFIG_AUTH_DOMAIN, "");
            model.values.put(AS_CONFIG_AUTH_USERNAME, "");
            model.values.put(AS_CONFIG_AUTH_PASSWORD, "");

            // x509v3
            model.values.put(AS_CONFIG_AUTH_KEY_FILE, "");
            model.values.put(AS_CONFIG_AUTH_PRIVATE_KEY, "");
	}

	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = ASConnectionModelParser.getSaveDocument(getRootAttributes(), "BWSharedResource");
		new ASConnectionModelParser().saveModelParts(doc, this);
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}

	public ASConnectionModel getModel() {
		return model;
	}

	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put(AS_CONFIG_DESCRIPTION, AS_CONFIG_DESCRIPTION);
		propertyNames.put(AS_CONFIG_METASPACE_NAME, AS_CONFIG_METASPACE_NAME);
		propertyNames.put(AS_CONFIG_MEMBER_NAME, AS_CONFIG_MEMBER_NAME);
		propertyNames.put(AS_CONFIG_DISCOVERY_URL, AS_CONFIG_DISCOVERY_URL);
		propertyNames.put(AS_CONFIG_LISTEN_URL, AS_CONFIG_LISTEN_URL);
		propertyNames.put(AS_CONFIG_REMOTE_LISTEN_URL, AS_CONFIG_REMOTE_LISTEN_URL);
		
		propertyNames.put(AS_CONFIG_ENABLE_SECURITY, AS_CONFIG_ENABLE_SECURITY);
		propertyNames.put(AS_CONFIG_SECURITY_ROLE, AS_CONFIG_SECURITY_ROLE);
        propertyNames.put(AS_CONFIG_ID_PASSWORD, AS_CONFIG_ID_PASSWORD);
        // controller
		propertyNames.put(AS_CONFIG_POLICY_FILE, AS_CONFIG_POLICY_FILE);
		// requester
		propertyNames.put(AS_CONFIG_TOKEN_FILE, AS_CONFIG_TOKEN_FILE);

        // security requester
		propertyNames.put(AS_CONFIG_AUTH_CREDENTIAL, AS_CONFIG_AUTH_CREDENTIAL);
		    // user-pwd
    		propertyNames.put(AS_CONFIG_AUTH_DOMAIN, AS_CONFIG_AUTH_DOMAIN);
    		propertyNames.put(AS_CONFIG_AUTH_USERNAME, AS_CONFIG_AUTH_USERNAME);
    		propertyNames.put(AS_CONFIG_AUTH_PASSWORD, AS_CONFIG_AUTH_PASSWORD);
    		// x509v3
            propertyNames.put(AS_CONFIG_AUTH_KEY_FILE, AS_CONFIG_AUTH_KEY_FILE);
            propertyNames.put(AS_CONFIG_AUTH_PRIVATE_KEY, AS_CONFIG_AUTH_PRIVATE_KEY);

		return propertyNames;
	}

}
