package com.tibco.rta.client.notify.impl;

import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.StringServiceResponse;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.util.ServiceConstants;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.Exchanger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 2/7/13
 * Time: 10:15 AM
 * Listener which gets notified upon session establishment.
 */
public class SessionEstablishmentNotifier implements MessageListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private Exchanger<Object> exchanger;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            //Used during session establish response.
            TextMessage textMessage = (TextMessage) message;
            try {
                String errorMessage = textMessage.getStringProperty(ServiceConstants.ERROR);
                ServiceResponse<String> serviceResponse = new StringServiceResponse();

                if (errorMessage != null) {
                    //Handle error case
                    serviceResponse.addProperty(ServiceConstants.ERROR, errorMessage);
                }
                exchanger.exchange(serviceResponse);
            } catch (JMSException e) {
                LOGGER.log(Level.ERROR, "", e);
            } catch (InterruptedException ie) {
                LOGGER.log(Level.ERROR, "", ie);
            } catch (Throwable e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }

    public void setExchanger(Exchanger<Object> exchanger) {
        this.exchanger = exchanger;
    }
}
