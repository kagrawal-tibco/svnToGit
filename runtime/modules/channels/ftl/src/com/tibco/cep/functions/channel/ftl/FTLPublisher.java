package com.tibco.cep.functions.channel.ftl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;






import com.tibco.be.model.rdf.RDFTypes;
//import com.tibco.be.functions.event.EventHelper;
import com.tibco.be.model.rdf.primitives.RDFBooleanTerm;
import com.tibco.be.model.rdf.primitives.RDFBooleanWrapTerm;
import com.tibco.be.model.rdf.primitives.RDFDateTimeTerm;
import com.tibco.be.model.rdf.primitives.RDFLongTerm;
import com.tibco.be.model.rdf.primitives.RDFLongWrapTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFStringTerm;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.ftl.EventQueue;
import com.tibco.ftl.Message;
import com.tibco.ftl.MessageFieldRef;
import com.tibco.ftl.Publisher;
import com.tibco.ftl.Realm;
import com.tibco.ftl.TibDateTime;
import com.tibco.ftl.TibProperties;

public class FTLPublisher{
	
	public static class MessagePropertyBinding {
		MessageFieldRef ref;
		RDFPrimitiveTerm rdfType;
		public MessagePropertyBinding(MessageFieldRef ref,
				RDFPrimitiveTerm rdfType) {
			super();
			this.ref = ref;
			this.rdfType = rdfType;
		}
		public MessageFieldRef getRef() {
			return ref;
		}
		public void setRef(MessageFieldRef ref) {
			this.ref = ref;
		}
		public RDFPrimitiveTerm getRdfType() {
			return rdfType;
		}
		public void setRdfType(RDFPrimitiveTerm rdfType) {
			this.rdfType = rdfType;
		}
		
	}
	
	Realm realm;
    String endPointName;  
    String eventUri;
    String messageFormatType;
    Map<String,MessagePropertyBinding> fieldRef = new HashMap<>();


	Publisher nativePublisher;
    EventQueue eventQueue;
    ExecutorService executorService;
    String contentMatcher;
    TibProperties publisherProps;
  

    
    public FTLPublisher(Object realm, String endPointName, Object publisherProps) {
        this.realm = (Realm) realm;
        this.endPointName = endPointName;
        this.publisherProps = (TibProperties) publisherProps;
        executorService = Executors.newSingleThreadExecutor();
    }
    
    
   

	public void bind(String eventUri, String messageFormat) throws Exception{
		RuleSession rs = RuleSessionManager.getCurrentRuleSession();
		final Logger logger = rs.getRuleServiceProvider().getLogger(FTLPublisher.class);
        logger.log(Level.INFO, String.format("Binding event : %s, messageFormat : %s", eventUri, messageFormat));
    	this.eventUri = eventUri;
    	this.messageFormatType = messageFormat;
    	DeployedProject project = rs.getRuleServiceProvider().getProject();
    	Ontology ontology = project.getOntology();
    	Event eventType = ontology.getEvent(eventUri);
    	for(Object prop:eventType.getAllUserProperties()) {
    		EventPropertyDefinition evp = (EventPropertyDefinition) prop;
    		RDFPrimitiveTerm rdfType = evp.getType();
    		MessageFieldRef mrf = realm.createMessageFieldRef(evp.getPropertyName());
    		fieldRef.put(evp.getPropertyName(), new MessagePropertyBinding(mrf, rdfType));	
    	}
    	MessageFieldRef payloadRef = realm.createMessageFieldRef(EventPayload.PAYLOAD_PROPERTY);
    	fieldRef.put(EventPayload.PAYLOAD_PROPERTY, new MessagePropertyBinding(payloadRef,(RDFPrimitiveTerm) RDFTypes.STRING));
    }
    
   
    
    
    

    
    public void publishMessage(SimpleEvent ev){
    	try{
    		RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();

            if (ruleSession == null) throw new RuntimeException("No Rulesession configured...");
            
    		this.nativePublisher = this.realm.createPublisher(this.endPointName);
    		
    		Message message = transform2FTLMessage(ev);
    		if(this.nativePublisher != null)
    			this.nativePublisher.send(message);
    		else
    			throw new IllegalStateException("Publisher used to send message to the FTL channel is null");
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

    protected Message transform2FTLMessage(SimpleEvent se) throws Exception{
    	String eventType = se.getType();
    	Message message = this.realm.createMessage(this.messageFormatType);
    	RuleSession rs = RuleSessionManager.getCurrentRuleSession();
    	final Logger logger = rs.getRuleServiceProvider().getLogger(FTLPublisher.class);
    	for(String propName : se.getPropertyNames()){
    		MessagePropertyBinding msgBinding = fieldRef.get(propName);
    		if(msgBinding.getRdfType() instanceof RDFBooleanTerm || msgBinding.getRdfType() instanceof RDFBooleanWrapTerm ) {
    			message.setLong(msgBinding.getRef(), (long) se.getPropertyValue(propName));
    			logger.log(Level.TRACE, String.format("%s : %s",propName,((long)se.getPropertyValue(propName))));
    		}
    		if(msgBinding.getRdfType() instanceof RDFDateTimeTerm){
    			GregorianCalendar cal = GregorianCalendar.class.cast(se.getPropertyValue(propName));
    			long milisec = cal.getTimeInMillis();
    			TibDateTime tdt = new TibDateTime();
    			tdt.setFromMillis(milisec);
    			message.setDateTime(msgBinding.getRef(), tdt);
    			logger.log(Level.TRACE, String.format("%s : %s", propName, cal));
    		}
    		if(msgBinding.getRdfType() instanceof RDFLongTerm || msgBinding.getRdfType() instanceof RDFLongWrapTerm){
    			message.setLong(msgBinding.getRef(), (long) se.getPropertyValue(propName));
    			logger.log(Level.TRACE,String.format("%s : %s",propName,((long)se.getPropertyValue(propName))));
    		}
    		if(msgBinding.getRdfType() instanceof RDFStringTerm){
    			message.setString(msgBinding.getRef(), (String) se.getPropertyValue(propName));
    			logger.log(Level.TRACE,String.format("%s : %s",propName,((String)se.getPropertyValue(propName))));
    		}
    		
//    		if(propName.equalsIgnoreCase("ts")){
//    			MessageHelper.setLong(message, propName, (Long)se.getProperty(propName));
//    		}else if(propName.equalsIgnoreCase("type")){
//    			MessageHelper.setLong(message, propName, (Long)se.getProperty(propName));
//    		}else if(propName.equalsIgnoreCase("callerId")){
//    			MessageHelper.setString(message, propName, (String)se.getProperty(propName));
//    		}else if(propName.equalsIgnoreCase("fromBaseStation")){
//    			MessageHelper.setString(message, propName, (String)se.getProperty(propName));
//    		}else if(propName.equalsIgnoreCase("toBaseStation")){
//    			MessageHelper.setString(message, propName, (String)se.getProperty(propName));
//    		}
    	}
    	//TO-DO
    	String payloadString = se.getPayloadAsString();
    	MessagePropertyBinding msgRef = fieldRef.get(EventPayload.PAYLOAD_PROPERTY);
    	if(payloadString != null && !payloadString.isEmpty() && msgRef != null) {
    		//RuleSession rs = RuleSessionManager.getCurrentRuleSession();
    		//final Logger logger = rs.getRuleServiceProvider().getLogger(FTLPublisher.class);
            logger.log(Level.TRACE, String.format("Payload : %s", payloadString));
    		message.setString(msgRef.getRef(), payloadString);
    	}
    	return message;
    }
    
    public String toString(){
		return "{" + "\n\t End Point Name : " + this.endPointName + "\n\t Event Uri : " + this.eventUri + "\n\t Message Format Type Name : " + this.messageFormatType + "\n}";
    	
    }
}
