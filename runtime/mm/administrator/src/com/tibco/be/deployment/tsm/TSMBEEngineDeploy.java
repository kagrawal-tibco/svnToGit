package com.tibco.be.deployment.tsm;

import java.util.Iterator;
import java.util.Map;

import com.tibco.archive.helpers.NVPair;
import com.tibco.archive.helpers.NameValuePairs;
import com.tibco.pof.admindomain.ContainerConfiguration;
import com.tibco.pof.admindomain.OSProcess;
import com.tibco.pof.admindomain.ServiceConfiguration;
import com.tibco.tra.tsm.TSM;
import com.tibco.tra.tsm.TsmDeployment;
import com.tibco.tra.tsm.plugin.ActionsConstant;
import com.tibco.tra.tsm.plugin.TSMEngineDeploy;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Aug 30, 2004
 * Time: 12:02:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TSMBEEngineDeploy extends TSMEngineDeploy {

    protected static final String BE_HOTDEPLOY_PROPERTY_NAME = "be.engine.hotDeploy.enabled";

    protected boolean isHotDeployEnabled = false;


    public TSMBEEngineDeploy() {
        super();
    }


    protected void addBEProperties(OSProcess osProcess) throws Exception {

        try {
            final ServiceConfiguration serviceConfiguration = (ServiceConfiguration)
                    ((ContainerConfiguration)osProcess).getServiceConfigurations().iterator().next();
            final NameValuePairs beProps = (NameValuePairs) serviceConfiguration.getConfiguredServiceArchive()
                    .getDeploymentDescriptorByName(
                            NameValuePairs.DEPLOYMENT_DESCRIPTOR_TYPE, "BusinessEvents Properties");

            trace("Adding BusinessEvents properties.");

            int count = 0;
            if (null != beProps) {
                for (Iterator it = beProps.getNameValuePairs().iterator(); it.hasNext();) {
                    final NVPair nvPair = (NVPair) it.next();
                    trace("BusinessEvents property: " + nvPair.getName() + "=" + nvPair.getValue());

                    tra.put(nvPair.getName(), nvPair.getValue());
                    count++;

                    if (BE_HOTDEPLOY_PROPERTY_NAME.equals(nvPair.getName())) {
                        this.isHotDeployEnabled = Boolean.valueOf(nvPair.getValue()).booleanValue();
                    }
                }//for
            }//if
            trace("Added " + count + " BusinessEvents properties.");
        } catch (Exception e) {
            e.printStackTrace();
        }//catch

    }//addBEProperties


    public void beginAction(OSProcess osProcess, String action, Map map) throws Throwable {
        try {
            if (action.equals(ActionsConstant.DEPLOY_CLIENT_ENGINE)
                    || action.equals(ActionsConstant.DEPLOY_CLIENT_ADAPTER)) {

                super.beginAction(osProcess, ActionsConstant.DEPLOY_CLIENT_ENGINE, map);

                if (!deployAction.equals("delete")) {
                    createTra();
                    addBEProperties(osProcess);
                }//if

                this.deploy();

                if (this.isHotDeployEnabled) {
                    ((TsmDeployment)TSM.deploymentList.get(deployment))
                            .getComponentInstance(component).setDelayDeployment(false);
                    this.ci.setDelayDeployment(false);
                }

                trace("BusinessEvents hot deploy = " + this.isHotDeployEnabled
                        + ", delayed = " + this.ci.getDelayDeployment());

            } else {
                throw new IllegalArgumentException("Invalid action: " + action);
            }//else
        } catch (Throwable e) {
            trace(e.getMessage());
            throw e;
        }//catch
    }//beginAction


    public void endAction(OSProcess cc, String action, Map map) throws Throwable {
        if (action.equals(ActionsConstant.DEPLOY_CLIENT_ENGINE)
                || action.equals(ActionsConstant.DEPLOY_CLIENT_ADAPTER)) {
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }//else
    }//endAction


    private static void trace(String msg) {
        TSM.trc.log(TSM.trc.DEBUG, msg);
    }//trace


}//class
