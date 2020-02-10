package com.tibco.cep.container.standalone.hawk.methods.engine;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.ShutdownWatcher;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class StopApplicationInstanceMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public StopApplicationInstanceMethod(HawkRuleAdministrator hma) {
        super("stopApplicationInstance", "Shuts down the engine. All checkpoint files will be preserved and the engine's operating system process will exit.", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }


    public AmiParameterList getArguments() {
        return null;
    }


    public AmiParameterList getReturns() {
        return null;
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {

            new Thread () {
                public void run() {
                    try {
                        ((RuleServiceProviderImpl) m_hma.getServiceProvider()).hawkShutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }//catch
                    finally {
                        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
                        ShutdownWatcher shutdownWatcher = registry.getShutdownWatcher();

                        if (shutdownWatcher == null) {
                            System.exit(0); // This triggers the shutdown hook in BEMain.
                        }
                        else {
                            shutdownWatcher.exitSystem(0);
                        }
                    }//finally
                }//run
            }.start();

            return null;

        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class




