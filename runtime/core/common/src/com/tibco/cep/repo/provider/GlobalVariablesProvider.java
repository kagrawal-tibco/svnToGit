package com.tibco.cep.repo.provider;

import com.tibco.cep.repo.GlobalVariables;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 7, 2004
 * Time: 3:48:24 PM
 * To change this template use Options | File Templates.
 */
public interface GlobalVariablesProvider extends GlobalVariables {

	String GLOBAL_VARS_NAMESPACE = "http://www.tibco.com/be/DeployedVarsType";
	String NAME = "globalVariables";

    String getProjectName();


    String getProjectVersion();


    String getProjectOwner();


    String getPackagedComponentVersion(String componentName);


    void clear();


	void buildGlobalVariablesUsingRemoteRepository(String path, XiNode root,String projectSource) throws Exception;
	
	void validateGlobalVariables();


}
