package com.tibco.cep.dashboard.psvr.mal;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;
import com.tibco.cep.dashboard.config.PropertyKey.DATA_TYPE;

public interface MALProperties extends PropertyKeys {

	public static final PropertyKey GLOBAL_STORAGE_TYPE = new PropertyKey("mal.global.storage.type","Defines the types of storage to use for global runtime objects",DATA_TYPE.String,"sar");

	public static final PropertyKey USER_STORAGE_TYPE = new PropertyKey("mal.user.storage.type","Defines the types of storage to use for user runtime objects",DATA_TYPE.String,"file");

	public static final PropertyKey FILE_STORAGE_ROOT = new PropertyKey("mal.storage.file.root","Defines the root directory for file storage",DATA_TYPE.String,null);

	public static final PropertyKey FILE_STORAGE_DB_NAME = new PropertyKey("mal.storage.file.dbname","Defines the database name for file storage",DATA_TYPE.String,"beviews");

	public static final PropertyKey ORIG_ELEMENT_LOADING_POLICY = new PropertyKey("mal.missing.original.element.loading.policy","Defines the policy for personalized element loading",DATA_TYPE.String,MISSING_ORIGINAL_ELEMENT_LOAD_POLICY.EXCEPTION.toString().toLowerCase());

//	public static final PropertyKey XML_PARSERS_FACTORY = new PropertyKey("javax.xml.parsers.DocumentBuilderFactory","Defines the xml parser factory",DATA_TYPE.String,"org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

}
