package com.tibco.cep.driver.soap;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.xml.data.primitive.ExpandedName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventNameRegistry {
	private static final EventNameRegistry INSTANCE = new EventNameRegistry();
	
	private EventNameRegistry() {}
	
	public static EventNameRegistry getInstance() {
		return INSTANCE;
	}

    private final Map<String, Map<String, Map<String, Registration>>>
            destinationPathToServiceNameToSoapActionToRegistration
            = new ConcurrentHashMap<String, Map<String, Map<String, Registration>>>();

    public ExpandedName getEventName(
            String destinationPath,
            String serviceName,
            String soapAction) {
        final Registration r = this.getRegistration(destinationPath, serviceName, soapAction);
        ExpandedName eventName = (null == r) ? null : r.getEventName();

        if (null == eventName) {
            if ((null == serviceName) || serviceName.isEmpty()) {
                return destinationPath.isEmpty()
                        ? null
                        : this.getEventName("", serviceName, soapAction);
            } else {
                eventName = this.getEventName(destinationPath, "", soapAction);
                if (null == eventName) {
                    return destinationPath.isEmpty()
                            ? null
                            : this.getEventName("", serviceName, soapAction);
                }
            }
        }

        return eventName;
    }

    public RuleFunction getPreprocessor(
            String destinationPath,
            String serviceName,
            String soapAction) {
        final Registration r = this.getRegistration(destinationPath, serviceName, soapAction);
        RuleFunction pp = (null == r) ? null : r.getPreprocessor();

        if (null == pp) {
            if ((null == serviceName) || serviceName.isEmpty()) {
                return destinationPath.isEmpty()
                        ? null
                        : this.getPreprocessor("", serviceName, soapAction);
            } else {
                pp = this.getPreprocessor(destinationPath, "", soapAction);
                if (null == pp) {
                    return destinationPath.isEmpty()
                            ? null
                            : this.getPreprocessor("", serviceName, soapAction);
                }
            }
        }

        return pp;
    }

    protected Registration getRegistration(
            String destinationPath,
            String serviceName,
            String soapAction) {
        destinationPath = (null == destinationPath) ? "" : destinationPath;
    	serviceName = (null == serviceName) ? "" : serviceName;
    	soapAction = (null == soapAction) ? "" : soapAction;

        final Map<String, Map<String, Registration>> serviceNameToSoapActionToRegistration =
                this.destinationPathToServiceNameToSoapActionToRegistration.get(destinationPath);
        if (null == serviceNameToSoapActionToRegistration) {
            return destinationPath.isEmpty()
                    ? null
                    : this.getRegistration("", serviceName, soapAction);
        }

    	final Map<String, Registration> soapActionToRegistration =
                serviceNameToSoapActionToRegistration.get(serviceName);
        if (null == soapActionToRegistration) {
            if (serviceName.isEmpty()) {
                return destinationPath.isEmpty()
                        ? null
                        : this.getRegistration("", serviceName, soapAction);
            } else {
                return this.getRegistration(destinationPath, "", soapAction);
            }
        }

        Registration registration = soapActionToRegistration.get(soapAction);
        if (null == registration) {
            if (serviceName.isEmpty()) {
                return destinationPath.isEmpty()
                        ? null
                        : this.getRegistration("", serviceName, soapAction);
            } else {
                registration = this.getRegistration(destinationPath, "", soapAction);
                if (null == registration) {
                    return destinationPath.isEmpty()
                        ? null
                        : this.getRegistration("", serviceName, soapAction);
                }
            }
        }

        return registration;
    }
    
    public void registerEventName(
            ExpandedName eventName,
            String destinationPath,
            String serviceName,
            String soapAction,
            RuleFunction ruleFunction) {
        destinationPath = (null == destinationPath) ? "" : destinationPath;
        serviceName = (null == serviceName) ? "" : serviceName;
    	soapAction = (null == soapAction) ? "" : soapAction;
    	
        synchronized (this) {
            Map<String, Map<String, Registration>> serviceNameToSoapActionToRegistration =
                    this.destinationPathToServiceNameToSoapActionToRegistration.get(destinationPath);
            if (null == serviceNameToSoapActionToRegistration) {
                serviceNameToSoapActionToRegistration = new ConcurrentHashMap<String, Map<String, Registration>>();
                this.destinationPathToServiceNameToSoapActionToRegistration.put(
                        destinationPath,
                        serviceNameToSoapActionToRegistration);
            }

            Map<String, Registration> soapActionToRegistration =
                    serviceNameToSoapActionToRegistration.get(serviceName);
            if (null == soapActionToRegistration) {
                soapActionToRegistration = new ConcurrentHashMap<String, Registration>();
                serviceNameToSoapActionToRegistration.put(serviceName, soapActionToRegistration);
            }

            Registration r = soapActionToRegistration.get(soapAction);
            if (null == r) {
                r = new Registration(eventName, destinationPath, serviceName, soapAction, ruleFunction);
                soapActionToRegistration.put(soapAction, r);
            } else {
                r.eventName = eventName;
                r.preprocessor = ruleFunction;
            }
        }
    }


    private static class Registration {

        final String destinationPath;
        ExpandedName eventName;
        RuleFunction preprocessor;
        final String serviceName;
        final String soapAction;

        private Registration(
                ExpandedName eventName,
                String destinationPath,
                String serviceName,
                String soapAction,
                RuleFunction preprocessor) {
            this.destinationPath = destinationPath;
            this.eventName = eventName;
            this.preprocessor = preprocessor;
            this.serviceName = serviceName;
            this.soapAction = soapAction;
        }


        @SuppressWarnings("UnusedDeclaration")
        public String getDestinationPath() {
            return this.destinationPath;
        }


        public ExpandedName getEventName() {
            return this.eventName;
        }


        public RuleFunction getPreprocessor() {
            return this.preprocessor;
        }


        @SuppressWarnings("UnusedDeclaration")
        public String getServiceName() {
            return this.serviceName;
        }


        @SuppressWarnings("UnusedDeclaration")
        public String getSoapAction() {
            return this.soapAction;
        }
    }

}
