package com.tibco.cep.runtime.model.event;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Mar 26, 2007
 * Time: 3:17:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AdvisoryEventDictionary {

    public static final String DEPLOYMENT_HotDeploySuccess         = "deployment.hotdeploy.success";
    public static final String DEPLOYMENT_HotDeployFail            = "deployment.hotdeploy.fail";

    public static final String DEPLOYMENT_EXTERNAL_CLASSES_SUCCESS = "deployment.externalclasses.success";
    public static final String DEPLOYMENT_EXTERNAL_CLASSES_FAILURE = "deployment.externalclasses.failure";

    public static final String ENGINE_PrimaryActivated             = "engine.primary.activated";
    public static final String ENGINE_LogLevelUpdated              = "engine.log.level.updated";
    
    public static final String BATCH_StatusDone                    = "batch.status.done";
    public static final String BATCH_StatusError                   = "batch.status.error";
}
