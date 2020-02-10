package com.tibco.cep.designtime.model.event;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.RuleParticipant;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.xml.ImportRegistry;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.flavor.XSDL;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 8:23:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Event extends RuleParticipant {

	public static final int DEFAULT_TTL = 0;
	
    String EXPIRY_ACTION_RULE_NAME = "expiryAction";
    int MILLISECONDS_UNITS = 0;
    int SECONDS_UNITS = 1;
    int MINUTES_UNITS = 2;
    int HOURS_UNITS = 3;
    int DAYS_UNITS = 4;
    int[] UNITS = {MILLISECONDS_UNITS, SECONDS_UNITS, MINUTES_UNITS, HOURS_UNITS, DAYS_UNITS};
    /**
     * TTL Units Descriptions
     */
    String[] TTL_UNITS_DESCRIPTIONS = {
            "Milliseconds", "Seconds", "Minutes", "Hours", "Days"
    };
    String[] BASE_ATTRIBUTE_NAMES = {"id", "extId", "ttl"};
    /**
     * Basic Event Types
     */
    int SIMPLE_EVENT = 0;
    int TIME_EVENT = 1;
    int ADVISORY_EVENT = 3;
    int AGENTCHANGE_EVENT=4;
    /**
     * Schedule Types
     */
    int RULE_BASED = 0;
    int REPEAT = 1;
    ExpandedName XSD_IMPORT = ExpandedName.makeName(XSDL.NAMESPACE, "import");
    String IMPORT_SCHEMA_LOCATION = "schemaLocation";


    int getType();


    boolean hasPayload();


    boolean payloadIsAnyType();


    boolean isA(Event event);


    Event getSuperEvent();


    String getSuperEventPath();


    Collection getSubEventPaths();


    Element getPayloadSchema();


    void setPayloadSchema(Element schema) throws ModelException;


    String getPayloadSchemaAsString();


    ImportRegistry getPayloadImportRegistry();


    NamespaceImporter getPayloadNamespaceImporter();


    long getSpecifiedTime();


    String getInterval();


    int getIntervalUnit();


    String getTTL();


    int getSchedule();


    SmElement toSmElement();


    RDFPrimitiveTerm getPropertyType(String propertyName);


    RDFPrimitiveTerm getSuperPropertyType(String propertyName);


    RDFPrimitiveTerm getSubPropertyType(String propertyName);


    Event getAncestorForPropertyName(String propertyName);


    Event getDescendantForPropertyName(String propertyName);


    Rule getExpiryAction(boolean create);


    Iterator getUserProperties();


    List getAllUserProperties();


    Iterator getAncestorProperties();


    /**
     * @return the URI of the Channel of the Destination associated with this Event,
     *         or null if no Destiantion is associated with this event.
     */
    String getChannelURI();


    /**
     * @return the String name of the Destination associated with this Event,
     *         or null if no Destination is associated with this event.
     */
    String getDestinationName();


    /**
     * @return namespace of this event
     */
    String getNamespace();


    /**
     * @param propertyName
     * @param all
     * @return an EventPropertyDefinition
     */
    EventPropertyDefinition getPropertyDefinition(String propertyName, boolean all);


    /**
     * @return a long
     */
    String getTimeEventCount();


    /**
     * @return an int
     */
    int getSerializationFormat();


    Collection getAttributeDefinitions();


    EventPropertyDefinition getAttributeDefinition(String attributeName);


    int getTTLUnits();


    boolean getRetryOnException();
    
    /**
     * returns the expiry action code block offsets
     * @since 4.0
     * @return
     */
    CodeBlock getExpiryCodeBlock();
    
    public boolean isSoapEvent() ;
}
