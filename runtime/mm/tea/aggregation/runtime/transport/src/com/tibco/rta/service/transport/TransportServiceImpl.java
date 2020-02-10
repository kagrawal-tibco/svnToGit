package com.tibco.rta.service.transport;

import java.util.Properties;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.MessageContext;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipListener;
//import com.tibco.rta.service.transport.http.tomcat.RESTTransportService;
import com.tibco.rta.service.transport.jms.JMSTransportService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/3/13
 * Time: 4:51 PM
 * <p>
 * Delegator impl for {@link TransportService} which delegates to
 * transport based implementation.
 * </p>
 * @see TransportTypes
 */
public class TransportServiceImpl extends AbstractStartStopServiceImpl implements TransportService, GroupMembershipListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    private TransportService delegatedTransportService;

    public TransportServiceImpl() {
        try {
            this.configuration = (Properties) ServiceProviderManager.getInstance().getConfiguration();

            ServiceProviderManager.getInstance().getGroupMembershipService().addMembershipListener(this);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public void init(Properties configuration) throws Exception {
        String transportType = configuration.getProperty(ConfigProperty.RTA_TRANSPORT_TYPE.getPropertyName(), ConfigProperty.RTA_TRANSPORT_TYPE.getDefaultValue());
        TransportTypes transportTypes = TransportTypes.valueOf(transportType);

        switch (transportTypes) {
            case HTTP :
               // delegatedTransportService = new DefaultHTTPTransportService();
                break;
            case JMS :
                delegatedTransportService = new JMSTransportService();
                break;
            case REST :
//                delegatedTransportService = new RESTTransportService();
                break;
        }
        if (delegatedTransportService != null) {
            delegatedTransportService.init(configuration);
        }
    }

    @Override
    public void start() throws Exception {
        delegatedTransportService.start();
    }

    @Override
    public void stop() throws Exception {
        if (delegatedTransportService != null) {
            delegatedTransportService.stop();
        }
    }

    public TransportService getDelegatedTransportService() {
        return delegatedTransportService;
    }

    @Override
    public <G extends GroupMember> void memberJoined(G member) {

    }

    @Override
    public <G extends GroupMember> void memberLeft(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onPrimary(G member) {
        try {
            //Initialize and start
            init(configuration);
            start();
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Transport service started..");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public <G extends GroupMember> void onSecondary(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void quorumComplete(GroupMember... groupMembers) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void networkFailed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void networkEstablished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onFenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onUnfenced(G member) {
        //Stop transport service on this machine if on
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Member unfenced but relinquishing earlier leader state if any");
        }
        try {
            stop();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public <G extends GroupMember> void onConflict(G member) {
        //Stop transport service on this machine if on
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Member conflicted and stopping transport service");
        }
        try {
            stop();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
	public void handleError(MessageContext context) {
		// TODO Auto-generated method stub
		
	}
}
