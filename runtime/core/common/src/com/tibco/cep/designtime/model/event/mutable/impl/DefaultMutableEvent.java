/**
 * User: ishaan

  * Date: Apr 23, 2004
  * Time: 5:56:31 PM
  */
package com.tibco.cep.designtime.model.event.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.util.EntityNameHelper;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.event.AdvisoryEvent;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.event.mutable.MutableEvent;
import com.tibco.cep.designtime.model.event.mutable.MutableEventPropertyDefinition;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableRuleParticipant;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RulesetEntry;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRule;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDestination;
import com.tibco.cep.designtime.model.service.channel.mutable.impl.DefaultMutableChannel;
import com.tibco.xml.DefaultImportRegistry;
import com.tibco.xml.DefaultNamespaceMapper;
import com.tibco.xml.ImportRegistry;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.QNameLoadSaveUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.impl.DefaultComponentFactory;


public class DefaultMutableEvent extends AbstractMutableRuleParticipant implements MutableEvent, MutableRuleSet {


     public static final ExpandedName REFERRING_RULES_NAME = ExpandedName.makeName("referringRules");
     public static final ExpandedName REFERRING_RULE_NAME = ExpandedName.makeName("referringRule");
     public static final ExpandedName SUB_EVENTS_NAME = ExpandedName.makeName("subEvents");
     public static final ExpandedName SUB_EVENT_NAME = ExpandedName.makeName("subEvent");
     public static final ExpandedName SUPER_NAME = ExpandedName.makeName("super");
     public static final ExpandedName EXPIRY_ACION_NAME = ExpandedName.makeName(Event.EXPIRY_ACTION_RULE_NAME);
     public static final String INHERIT_PROPERTY_CONFLICT_KEY = "DefaultEvent.setSuperEventPath.inheritPropertyConflict";
     public static final String NON_ABSTRACT_INHERITANCE_KEY = "DefaultEvent.setSuperEventPath.nonAbstractInheritance";
     public static final String USER_PROPERTY_CLOBBER_KEY = "DefaultEvent.addUserProperty.clobber.msg";
     public static final String USER_PROPERTY_OVERRIDE_KEY = "DefaultEvent.addUserProperty.override.msg";
     public static final String SET_PAYLOAD_ON_ABSTRACT_KEY = "DefaultEvent.setPayload.isAbstract.msg";

     /**
      * Schedule Type Descriptions
      */
     public static final String[] SCHEDULE_DESCRIPTIONS = {
             "ruleBased", "runOnce", "repeat"
     };

     /**
      * TTL Units Descriptions
      */
     public static final String[] TTL_UNITS_DESCRIPTIONS = {
             "Milliseconds", "Seconds", "Minutes", "Hours", "Days"
     };

     static MutableComponentFactoryTNS cf = DefaultComponentFactory.getTnsAwareInstance();

     /**
      * Basic Event fields
      */
     protected int m_type;
     protected String m_super;
     protected LinkedHashSet m_subEvents;

     /**
      * Simple Event fields
      */
     protected Element payload;
     protected String m_ttl;
     protected int m_ttlUnits;
     protected ImportRegistry m_importRegistry;
     protected NamespaceImporter m_nsMapper;
     protected ArrayList m_userPropertiesList; //Hack to add additional data structure to maintain the order
     protected DefaultMutableRuleSet m_ruleSet;

     protected int m_serializationFormat = ModelConstants.SERIALIZATION_FORMAT_BE;

     /**
      * Time Event fields
      */
     protected long m_specifiedTime;
     protected String m_interval;
     protected int m_schedule;
     protected int m_intervalUnit;
     protected String m_timeEventCount = "1";
     protected boolean retryOnException = true;


     /**
      * MutableDestination to which the event is bound.
      */
     protected String m_destinationChannelURI;
     protected String m_destinationName;


     /**
      * Constructor for Simple Events
      */
     public DefaultMutableEvent(
             DefaultMutableOntology ontology,
             DefaultMutableFolder folder,
             String name,
             String ttl,
             int units,
             String destinationName,
             String destinationPath,
             boolean retryOnException) {
         this(ontology, folder, name, SIMPLE_EVENT, ttl, units, -1, "-1", RULE_BASED, destinationName, destinationPath,
                 retryOnException);
     }


     /**
      * Constructor for Time Events
      */
     public DefaultMutableEvent(
             DefaultMutableOntology ontology,
             DefaultMutableFolder folder,
             String name,
             long specifiedTime,
             String interval,
             int schedule,
             String destinationName,
             String destinationPath,
             boolean retryOnException) {
         this(ontology, folder, name, TIME_EVENT, "-1", Event.SECONDS_UNITS, specifiedTime, interval, schedule,
                 destinationName, destinationPath, retryOnException);
     }


     protected DefaultMutableEvent(
             DefaultMutableOntology ontology,
             DefaultMutableFolder folder,
             String name,
             int type,
             String ttl,
             int units,
             long specifiedTime,
             String interval,
             int schedule,
             String destinationName,
             String destinationPath,
             boolean retryOnException) {
         super(ontology, folder, name);
         m_type = type;
         m_ttl = ttl;
         m_ttlUnits = units;
         m_specifiedTime = specifiedTime;
         m_interval = interval;
         payload = null;
         m_userPropertiesList = new ArrayList();
         m_schedule = schedule;
         m_destinationName = destinationName;
         m_destinationChannelURI = destinationPath;
         m_subEvents = new LinkedHashSet();
         m_super = "";
         m_ruleSet = new MutableDelegateRuleSet(this, ontology, folder, name);
         this.retryOnException = retryOnException;
         initPayloadNamespaceHandlers();
     }


     public void setFolder(MutableFolder folder) throws ModelException {
         String oldPath = getFullPath();
         m_ontology.setEntityFolder(this, folder);
         String newPath = getFullPath();
         pathChanged(oldPath, newPath);
         notifyListeners();
     }


     public void delete() {
         super.delete();
     }//delete


     public void setName(String name, boolean renameOnConflict) throws ModelException {
         if (name == null || name.length() == 0) {
             BEModelBundle bundle = BEModelBundle.getBundle();
             String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
             throw new ModelException(msg);
         }
         if (name.equals(m_name)) {
             return;
         }

         String oldPath = getFullPath();
         super.setName(name, renameOnConflict);
         String newPath = getFullPath();

         pathChanged(oldPath, newPath);
         notifyListeners();
     }


     public void setOntology(MutableOntology ontology) {
         super.setOntology(ontology);
         m_ruleSet.setOntology(ontology);
     }


     public void pathChanged(String oldPath, String newPath) throws ModelException {
         if (m_ontology == null) {
             return;
         }
         if (ModelUtils.IsEmptyString(oldPath)) {
             return;
         }
         if (newPath == null) {
             newPath = "";
         }

//         /* Since we are also a RuleSet that may also point to ourself, re-key ourself in our MutableRuleSet list */
//         Object selfRules = m_referringRulesMap.remove(oldPath);
//         if (selfRules != null) {
//             m_referringRulesMap.put(newPath, selfRules);
//         }

         /** Tell sub Events and super Event */
         Iterator subEventsIt = m_subEvents.iterator();
         while (subEventsIt.hasNext()) {
             String path = (String) subEventsIt.next();
             DefaultMutableEvent de = (DefaultMutableEvent) m_ontology.getEvent(path);
             if (de != null) {
                 de.m_super = newPath;
             }
         }

         DefaultMutableEvent de = (DefaultMutableEvent) getSuperEvent();
         if (de != null) {
             de.modifySubEvent(oldPath, newPath);
         }

         /** Tell our destination MutableChannel */
         DefaultMutableChannel channel = (DefaultMutableChannel) m_ontology.getEntity(m_destinationChannelURI);
         if (channel != null) {
             DriverConfig dc = channel.getDriver();
             final MutableDestination d = (MutableDestination) dc.getDestination(m_destinationName);
             d.setEventURI(newPath);
         }

         /** Notify Rules that we have changed */
         super.pathChanged(oldPath, newPath);
         m_ruleSet.pathChanged(oldPath, newPath);
         m_ontology.registerEvent(this);
     }


     protected void initPayloadNamespaceHandlers() {
         m_importRegistry = new DefaultImportRegistry();
         m_nsMapper = new DefaultNamespaceMapper("xsd");
         ((DefaultNamespaceMapper) m_nsMapper).addXSDNamespace();
     }


     public int getType() {
         return m_type;
     }


     public boolean hasPayload() {
         boolean nullPayload = (payload == null);
         boolean noChildren = nullPayload || (payload.getFirstChild() == null);
         boolean noAttribs = nullPayload || (payload.getFirstAttribute() == null);

         return !nullPayload && (!noChildren || !noAttribs);
     }


     public boolean payloadIsAnyType() {
         if (!hasPayload()) {
             return false;
         }

         XiNode child = payload.getFirstChild();
         if (child == null) {
             return false;
         }

         ExpandedName name = child.getName();
         if (name == null) {
             return false;
         }

         String localName = name.getLocalName();
         return ("any".equalsIgnoreCase(localName));
     }


     public boolean isA(Event event) {
         if (event == null) {
             return false;
         }

         Event ptr = this;
         while (ptr != null) {
             /** Found a match */
             if (ptr.equals(event)) {
                 return true;
             }

             /** Advance the pointer */
             ptr = ptr.getSuperEvent();
         }
         return false;
     }


     public Event getSuperEvent() {
         if (m_ontology == null) {
             return null;
         }
         return m_ontology.getEvent(m_super);
     }


     public String getSuperEventPath() {
         return m_super;
     }


     public void setSuperEventPath(String parentPath) throws ModelException {
         if (m_ontology == null) {
             return;
         }
         
         if(RDFTypes.SOAP_EVENT.getName().equals(parentPath)){
        	 m_super = parentPath;
        	 return;
         }

         DefaultMutableEvent de = (DefaultMutableEvent) m_ontology.getEvent(parentPath);

         if (de == this) {
             return;
         }

         BEModelBundle bundle = BEModelBundle.getBundle();
         String childPath = getFullPath();

         if (de != null) {
             /** The proposed super Event cannot derive from the child */
             if (de.isA(this)) {
                 String msg = bundle.formatString(DefaultMutableConcept.INHERITANCE_LOOP_KEY, childPath, parentPath);
                 throw new ModelException(msg);
             }

             /** Events may only derive from Events without payloads, and Events that have an "any type" payload */
             //boolean payloadIsAnyType = de.payloadIsAnyType();//unused
             boolean hasPayload = de.hasPayload();
 //            if(hasPayload && !payloadIsAnyType) {
             if (hasPayload) {
                 String msg = bundle.formatString(NON_ABSTRACT_INHERITANCE_KEY, parentPath);
                 throw new ModelException(msg);
             }

             /** Events cannot share a non-inherited / non-derived property name */
             String conflictingName = hasUserPropertyConflict(de);
             if (conflictingName != null) {
                 String msg = bundle.formatString(INHERIT_PROPERTY_CONFLICT_KEY, childPath, parentPath, conflictingName);
                 throw new ModelException(msg);
             }
         }

         /** Unregister the child from its old parent */
         DefaultMutableEvent oldParent = (DefaultMutableEvent) getSuperEvent();
         if (oldParent != null) {
             oldParent.removeSubEvent(childPath);
         }

         /** Register the child with its new parent */
         m_super = (de != null) ? de.getFullPath() : "";
         if (de != null) {
             de.addSubEvent(childPath);
         }

         /** Notify old parent */
         if (oldParent != null) {
             oldParent.notifyListeners();
             oldParent.notifyOntologyOnChange();
         }

         /** Notify child */
         notifyListeners();
         notifyOntologyOnChange();
     }


     public Collection getSubEventPaths() {
         return m_subEvents;
     }


     public Element getPayloadSchema() {
         return payload;
     }


     public void setPayloadSchema(Element schema) throws ModelException {
         if (!m_subEvents.isEmpty()) {
             BEModelBundle bundle = BEModelBundle.getBundle();
             String msg = bundle.formatString(SET_PAYLOAD_ON_ABSTRACT_KEY, getFullPath());
             throw new ModelException(msg);
         }

         payload = schema;

         boolean hasPayload = hasPayload();
         //boolean payloadIsAnyType = payloadIsAnyType();//unused

         // cancel inheritance
 //        if(m_ontology != null && (hasPayload && !payloadIsAnyType)) {
         if (m_ontology != null && (hasPayload)) {
             ArrayList list = new ArrayList(m_subEvents);
             Iterator it = list.iterator();
             while (it.hasNext()) {
                 try {
                     String sub = (String) it.next();

                     final MutableEvent event = (MutableEvent) m_ontology.getEvent(sub);
                     if (event == null) {
                         continue;
                     }

                     event.setSuperEventPath(null);
                     it.remove();
                 } catch (ModelException e) {
                     e.printStackTrace();
                 }
             }
         }
         notifyListeners();
         notifyOntologyOnChange();
     }


     public String getPayloadSchemaAsString() {
         Element e = this.getPayloadSchema();
         if (null == e) {
             return null;
         }
         return XiSerializer.serialize(e);
     }


     public void setPayloadSchemaAsString(String schemaString) throws ModelException {
         try {
             final XiFactory factory = XiFactoryFactory.newInstance();
             final Element e = (Element) factory.createFragment(schemaString);
             this.setPayloadSchema(e);
         } catch (Exception ex) {
             throw new ModelException(ex);
         }
     }


     public ImportRegistry getPayloadImportRegistry() {
         if (!hasPayload()) {
             m_importRegistry = new DefaultImportRegistry();
         }

         return m_importRegistry;
     }


     public void setPayloadImportRegistry(ImportRegistry registry) {
         m_importRegistry = registry;
     }


     public NamespaceImporter getPayloadNamespaceImporter() {
         if (!hasPayload()) {
             m_nsMapper = new DefaultNamespaceMapper();
         }
         return m_nsMapper;
     }


     public void setPayloadNamespaceImporter(NamespaceImporter importer) {
         m_nsMapper = importer;
     }


     public Iterator getUserProperties() {
         return m_userPropertiesList.iterator();
     }


     public List getAllUserProperties() {
         ArrayList allProperties = new ArrayList();

         Event superEvent = getSuperEvent();
         if (superEvent != null) {
             final List superProps = superEvent.getAllUserProperties();
             allProperties.addAll(superProps);
         }
         allProperties.addAll(m_userPropertiesList);
         return allProperties;
     }


     public Map getAllExtendedProperties() {
         final Map props = new HashMap();
         final DefaultMutableEvent parent = (DefaultMutableEvent) this.getSuperEvent();
         if (null != parent) {
             props.putAll(parent.getAllExtendedProperties());
         }
         props.putAll(this.getExtendedProperties());
         return props;
     }


    public void setExtendedProperties(Map props) {
        super.setExtendedProperties(props);
//        if (null == props) {
//            this.m_extendedProperties = new LinkedHashMap();
//            final Map<String, Object> bsProps = new LinkedHashMap<String, Object>();
//            bsProps.put("Table Name", "");
//            this.m_extendedProperties.put("Backing Store", bsProps);
//        } else {
//            super.setExtendedProperties(props);
//        }
    }


     public Iterator getAncestorProperties() {
         ArrayList inheritProps = new ArrayList();

         Event ancestor = getSuperEvent();
         while (ancestor != null) {
             /** Add all the Ancestor's properties without clobbering */
             Iterator ancestorProps = ancestor.getUserProperties();

             while (ancestorProps.hasNext()) {
                 EventPropertyDefinition epd = (EventPropertyDefinition) ancestorProps.next();

                 String name = epd.getPropertyName();

                 if (inheritProps.contains(name)) {
                     continue;
                 }

                 boolean cont = false;
                 Object[] inheritArr = inheritProps.toArray();
                 for (int i = 0; i < inheritArr.length; i++) {
                     EventPropertyDefinition inheritProp = (EventPropertyDefinition) inheritArr[i];
                     if (name.equals(inheritProp.getPropertyName())) {
                         cont = true;
                         break;
                     }
                 }

                 if (cont) {
                     continue;
                 }
                 inheritProps.add(epd);
             }

             ancestor = ancestor.getSuperEvent();
         }

         return inheritProps.iterator();
     }


     public Collection getAttributeDefinitions() {
         ArrayList retAttributes = new ArrayList();
         fillStaticAttributes(retAttributes);
         return retAttributes;
     }

     public Collection getAttributeDefinitionsAsLocals() {
         ArrayList retAttributes = new ArrayList();
         fillStaticAttributes(this, retAttributes, getType(), getTimeEventCount());
         return retAttributes;
     }


     /**
      * @param attributeName
      * @return an EventPropertyDefinition
      */
     public EventPropertyDefinition getAttributeDefinition(String attributeName) {
         if (attributeName.equals(BASE_ATTRIBUTE_NAMES[0])) {
             return new DefaultMutableEventPropertyDefinition(null, BASE_ATTRIBUTE_NAMES[0], (RDFPrimitiveTerm) RDFTypes.LONG);
         } else if (attributeName.equals(BASE_ATTRIBUTE_NAMES[1])) {
             return new DefaultMutableEventPropertyDefinition(null, BASE_ATTRIBUTE_NAMES[1], (RDFPrimitiveTerm) RDFTypes.STRING);
         } else if (attributeName.equals(BASE_ATTRIBUTE_NAMES[2])) {
             return new DefaultMutableEventPropertyDefinition(null, BASE_ATTRIBUTE_NAMES[2], (RDFPrimitiveTerm) RDFTypes.LONG);
         } else if (attributeName.equals("closure")) {
             if (this.getType() == Event.TIME_EVENT) {
                 return new DefaultMutableEventPropertyDefinition(null, "closure", (RDFPrimitiveTerm) RDFTypes.STRING);
             }
         } else if (attributeName.equals("scheduledTime")) {
             if (this.getType() == Event.TIME_EVENT) {
                 return new DefaultMutableEventPropertyDefinition(null, "scheduledTime", (RDFPrimitiveTerm) RDFTypes.DATETIME);
             }
         } else if (attributeName.equals("interval")) {
             if (this.getType() == Event.TIME_EVENT) {
                 return new DefaultMutableEventPropertyDefinition(null, "interval", (RDFPrimitiveTerm) RDFTypes.LONG);
             }
         } else if (attributeName.equals("payload")) {
             if (this.getType() == Event.SIMPLE_EVENT) {
                 return new DefaultMutableEventPropertyDefinition(null, "payload", (RDFPrimitiveTerm) RDFTypes.STRING);
             }
         } else if (attributeName.equals("soapAction")) {
             if (this.getType() == Event.SIMPLE_EVENT && RDFTypes.SOAP_EVENT.getName().equals(getSuperEventPath())) {
                 return new DefaultMutableEventPropertyDefinition(null, "soapAction", (RDFPrimitiveTerm) RDFTypes.STRING);
             }
         }
         return null;
     }


     /**
      *
      */
     protected synchronized void fillStaticAttributes(ArrayList list) {
 //        list.add(new DefaultEventPropertyDefinition("id", (RDFPrimitiveTerm) RDFTypes.LONG));
 //        list.add(new DefaultEventPropertyDefinition("extId", (RDFPrimitiveTerm) RDFTypes.STRING));
 //        list.add(new DefaultEventPropertyDefinition("ttl", (RDFPrimitiveTerm) RDFTypes.INTEGER));
 //        if (this.getType() == Event.TIME_EVENT) {
 //            list.add(new DefaultEventPropertyDefinition("closure", (RDFPrimitiveTerm) RDFTypes.STRING));
 //            list.add(new DefaultEventPropertyDefinition("scheduledTime", (RDFPrimitiveTerm) RDFTypes.DATETIME));
 //        } else {
 //            list.add(new DefaultEventPropertyDefinition("payload", (RDFPrimitiveTerm) RDFTypes.STRING));
 //        }
 //        if(this.getTimeEventCount() == Event.SIMPLE_EVENT) {
 //            list.add(new DefaultEventPropertyDefinition("payload", (RDFPrimitiveTerm) RDFTypes.STRING));
 //        }

         fillStaticAttributes(list, getType(), getTimeEventCount());
         
         // Add attribute soapAction if this inherits SOAPEvent type
         if(getType() == Event.SIMPLE_EVENT && RDFTypes.SOAP_EVENT.getName().equals(getSuperEventPath())){
        	 list.add(new DefaultMutableEventPropertyDefinition(null, "soapAction", (RDFPrimitiveTerm) RDFTypes.STRING));
         }
     }


     public static synchronized void fillStaticAttributes(List list, int type, String timeEventCount) {
         fillStaticAttributes(null, list, type, timeEventCount);
     }
     private static synchronized void fillStaticAttributes(DefaultMutableEvent event, List list, int type, String timeEventCount) {
         list.add(new DefaultMutableEventPropertyDefinition(event, "id", (RDFPrimitiveTerm) RDFTypes.LONG));
         list.add(new DefaultMutableEventPropertyDefinition(event, "ttl", (RDFPrimitiveTerm) RDFTypes.LONG));
         if (type == Event.TIME_EVENT) {
             list.add(new DefaultMutableEventPropertyDefinition(event, "closure", (RDFPrimitiveTerm) RDFTypes.STRING));
             list.add(new DefaultMutableEventPropertyDefinition(event, "scheduledTime", (RDFPrimitiveTerm) RDFTypes.DATETIME));
             list.add(new DefaultMutableEventPropertyDefinition(event, "interval", (RDFPrimitiveTerm) RDFTypes.LONG));
         } else if (type == Event.SIMPLE_EVENT) {
             list.add(new DefaultMutableEventPropertyDefinition(event, "extId", (RDFPrimitiveTerm) RDFTypes.STRING));
             list.add(new DefaultMutableEventPropertyDefinition(event, "payload", (RDFPrimitiveTerm) RDFTypes.STRING));
         } else if (type == Event.ADVISORY_EVENT) {
             list.add(new DefaultMutableEventPropertyDefinition(event, "extId", (RDFPrimitiveTerm) RDFTypes.STRING));
             list.add(new DefaultMutableEventPropertyDefinition(event, AdvisoryEvent.ATTRIBUTE_CATEGORY, (RDFPrimitiveTerm) RDFTypes.STRING));
             list.add(new DefaultMutableEventPropertyDefinition(event, AdvisoryEvent.ATTRIBUTE_TYPE, (RDFPrimitiveTerm) RDFTypes.STRING));
             list.add(new DefaultMutableEventPropertyDefinition(event, AdvisoryEvent.ATTRIBUTE_MESSAGE, (RDFPrimitiveTerm) RDFTypes.STRING));
         }

         if (Long.parseLong(timeEventCount) == Event.SIMPLE_EVENT) {
             list.add(new DefaultMutableEventPropertyDefinition(null, "payload", (RDFPrimitiveTerm) RDFTypes.STRING));
         }
     }


     public void clearUserProperties() {
         m_userPropertiesList.clear();
//         setReferringRuleCompilationStatus(-1);
         notifyListeners();
         notifyOntologyOnChange();
     }


     /**
      * @return the URI of the MutableChannel of the Destination associated with this Event,
      *         or null if no Destiantion is associated with this event.
      */
     public String getChannelURI() {
         return m_destinationChannelURI;
     }


     /**
      * @param uri the URI of the MutableChannel of the Destination associated with this Event.
      */
     public void setChannelURI(String uri) {
         m_destinationChannelURI = uri;
         notifyListeners();
         notifyOntologyOnChange();
     }


     /**
      * @return the String name of the Destination associated with this Event,
      *         or null if no Destination is associated with this event.
      */
     public String getDestinationName() {
         return m_destinationName;
     }


     /**
      * @param name the String name of the Destination associated with this Event.
      */
     public void setDestinationName(String name) {
         m_destinationName = name;
         notifyListeners();
         notifyOntologyOnChange();
     }


     public long getSpecifiedTime() {
         return m_specifiedTime;
     }


     public void setSpecifiedTime(long time) {
         m_specifiedTime = time;
         notifyListeners();
         notifyOntologyOnChange();
     }


     public String getInterval() {
         return m_interval;
     }


     public void setInterval(String interval) {
         m_interval = interval;
         notifyListeners();
         notifyOntologyOnChange();
     }


     public int getIntervalUnit() {
         return m_intervalUnit;
     }


     public void setIntervalUnit(int intervalUnit) {
         m_intervalUnit = intervalUnit;
     }


     public String getTTL() {
         return m_ttl;
     }


     public void setTTL(String ttl) {
         m_ttl = ttl;
         notifyListeners();
         notifyOntologyOnChange();
     }


     public int getTTLUnits() {
         return m_ttlUnits;
     }


     public void setTTLUnits(int units) {
         m_ttlUnits = units;
         notifyListeners();
         notifyOntologyOnChange();
     }


     public int getSchedule() {
         return m_schedule;
     }


     public void setSchedule(int repeating) {
         m_schedule = repeating;
     }


     public static final ExpandedName TYPE_NAME = ExpandedName.makeName("type");
     public static final ExpandedName SPECIFIED_TIME_NAME = ExpandedName.makeName("specifiedTime");
     public static final ExpandedName TIME_INTERVAL_NAME = ExpandedName.makeName("timeInterval");
     public static final ExpandedName TTL_NAME = ExpandedName.makeName("ttl");
     public static final ExpandedName UNITS_NAME = ExpandedName.makeName("units");
     public static final ExpandedName SERIALIZATION_FORMAT_NAME = ExpandedName.makeName("serializationFormat");
     public static final ExpandedName PROPERTY_SCHEMA_NAME = ExpandedName.makeName("propertySchema");
     public static final ExpandedName USER_PROPERTIES_NAME = ExpandedName.makeName("userProperties");
     public static final ExpandedName USER_PROPERTY_NAME = ExpandedName.makeName("userProperty");
     public static final ExpandedName USER_PROPERTY_NAME_NAME = ExpandedName.makeName("name");
     public static final ExpandedName USER_PROPERTY_TYPE_NAME = ExpandedName.makeName("type");
     public static final ExpandedName DESTINATION_NAME = ExpandedName.makeName("destination");
     public static final ExpandedName DESTINATION_NAME_NAME = ExpandedName.makeName("name");
     public static final ExpandedName DESTINATION_PATH_NAME = ExpandedName.makeName("path");
     public static final ExpandedName SCHEDULE_NAME = ExpandedName.makeName("schedule");
     public static final ExpandedName INTERVAL_UNIT_NAME = ExpandedName.makeName("intervalUnit");
     public static final ExpandedName TIMEEVENT_COUNT_NAME = ExpandedName.makeName("timeEventCount");
     public static final ExpandedName RETRY_ON_EXCEPTION_NAME = ExpandedName.makeName("retryOnException");


     public static DefaultMutableEvent createDefaultEventFromNode(XiNode root) throws ModelException {
         DefaultMutableEvent de = null;

         /** Reload all props regardless of Event type */
         String folder = root.getAttributeStringValue(AbstractMutableEntity.FOLDER_NAME);
         String name = root.getAttributeStringValue(AbstractMutableEntity.NAME_NAME);
         String description = root.getAttributeStringValue(AbstractMutableEntity.DESCRIPTION_NAME);
         String typeName = root.getAttributeStringValue(TYPE_NAME);
         String specifiedTimeString = root.getAttributeStringValue(SPECIFIED_TIME_NAME);
         String intervalUnit = root.getAttributeStringValue(INTERVAL_UNIT_NAME);
         String timeIntervalString = root.getAttributeStringValue(TIME_INTERVAL_NAME);
         String ttlString = root.getAttributeStringValue(TTL_NAME);
         String unitsString = root.getAttributeStringValue(UNITS_NAME);
         String scheduleString = root.getAttributeStringValue(SCHEDULE_NAME);
         String superString = root.getAttributeStringValue(SUPER_NAME);
         String tecount = root.getAttributeStringValue(TIMEEVENT_COUNT_NAME);
         String serializationFormat = root.getAttributeStringValue(SERIALIZATION_FORMAT_NAME);
         String retryOnException = root.getAttributeStringValue(RETRY_ON_EXCEPTION_NAME);
         if (null == retryOnException) {
             retryOnException = Boolean.TRUE.toString();
         }


         XiNode subEventsNode = XiChild.getChild(root, SUB_EVENTS_NAME);
         XiNode schemaNode = XiChild.getChild(root, PROPERTY_SCHEMA_NAME);
         XiNode userPropsNode = XiChild.getChild(root, USER_PROPERTIES_NAME);
         XiNode destinationNode = XiChild.getChild(root, DESTINATION_NAME);
         String destinationName = null;
         String destinationPath = null;
         if (destinationNode != null) {
             destinationName = EntityNameHelper.convertToEntityIdentifier( // Old def allowed much more.
                     destinationNode.getAttributeStringValue(DESTINATION_NAME_NAME));
             destinationPath = destinationNode.getAttributeStringValue(DESTINATION_PATH_NAME);
         }

         int typeFlag = Event.SIMPLE_EVENT;
         if ("time".equalsIgnoreCase(typeName)) {
             typeFlag = Event.TIME_EVENT;
         }

         DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
         DefaultMutableFolder eventFolder = DefaultMutableOntology.createFolder(rootFolder, folder, false);

         int schedule = Event.RULE_BASED;
         for (int i = 0; i < SCHEDULE_DESCRIPTIONS.length; i++) {
             if (SCHEDULE_DESCRIPTIONS[i].equals(scheduleString)) {
                 schedule = i;
                 break;
             }
         }

         int ttlUnits = Event.MILLISECONDS_UNITS;
         if (unitsString != null) {
             for (int i = 0; i < TTL_UNITS_DESCRIPTIONS.length; i++) {
                 if (TTL_UNITS_DESCRIPTIONS[i].equals(unitsString)) {
                     ttlUnits = i;
                     break;
                 }
             }
         }

         if (typeFlag == Event.SIMPLE_EVENT) {
             de = new DefaultMutableEvent(null, eventFolder, name, ttlString, ttlUnits,
                     destinationName, destinationPath, Boolean.parseBoolean(retryOnException));
         } else if (typeFlag == Event.TIME_EVENT) {
             de = new DefaultMutableEvent(null, eventFolder, name, Long.parseLong(specifiedTimeString),
                     timeIntervalString, schedule, destinationName, destinationPath,
                     Boolean.parseBoolean(retryOnException));
             de.setSpecifiedTime(Long.parseLong(specifiedTimeString));
             de.setInterval(timeIntervalString);
             if ((intervalUnit != null) && (intervalUnit.trim().length() > 0)) {
                 de.setIntervalUnit(Integer.parseInt(intervalUnit));
             } else {
                 de.setIntervalUnit(0);
             }
             if ((tecount != null) && (tecount.trim().length() > 0)) {
                 de.m_timeEventCount = (tecount.trim());
             }
         }

         de.setDescription(description);
         de.setTTL(ttlString);

         if (serializationFormat != null) {
             de.setSerializationFormat(Integer.parseInt(serializationFormat));
         } else {
             de.setSerializationFormat(ModelConstants.SERIALIZATION_FORMAT_BE);
         }

         if (schemaNode != null) {
             Element schema = (Element) schemaNode.getFirstChild();
             de.setPayloadSchema(schema);
             de.m_nsMapper = QNameLoadSaveUtils.readNamespaces(root);
             de.m_importRegistry = QNameLoadSaveUtils.readImports(root, Event.XSD_IMPORT);
         }

         if (superString == null) {
             superString = "";
         }
         de.m_super = superString;

         // Extended properties
         XiNode extPropsNode = XiChild.getChild(root, EXTENDED_PROPERTIES_NAME);
         de.setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));

         Iterator it = userPropsNode.getChildren();
         while (it.hasNext()) {
             XiNode userPropNode = (XiNode) it.next();

             XiNode nameNode = XiChild.getChild(userPropNode, USER_PROPERTY_NAME_NAME);
             String propName = nameNode.getStringValue();

             XiNode typeNode = XiChild.getChild(userPropNode, USER_PROPERTY_TYPE_NAME);
             String typeString = typeNode.getStringValue();
             RDFPrimitiveTerm type = (RDFPrimitiveTerm) RDFTypes.getType(typeString);

             if (type == null) {
                 // TODO used for reverse compatibility..remove?
                 type = (RDFPrimitiveTerm) RDFTypes.getDriverType(typeString);
             }

             de.addUserProperty(propName, type);

             extPropsNode = XiChild.getChild(userPropNode, AbstractMutableEntity.EXTENDED_PROPERTIES_NAME);
             final MutableEventPropertyDefinition propDef = (MutableEventPropertyDefinition)
                     de.getPropertyDefinition(propName, false);
             propDef.setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));
         }

//         XiNode ruleSetsNode = XiChild.getChild(root, AbstractMutableRuleParticipant.REFERRING_RULESETS_NAME);
//         AbstractMutableRuleParticipant.rulesFromXiNode(de, ruleSetsNode);

         if (subEventsNode != null) {
             Iterator subEvents = subEventsNode.getChildren();
             while (subEvents.hasNext()) {
                 XiNode subEventNode = (XiNode) subEvents.next();
                 String path = subEventNode.getStringValue();
                 de.m_subEvents.add(path);
             }
         }

         // Get the RuleSet from the child
         // Get a special version of the RuleSet that is not a first class object (lives in Concept)
         de.m_ruleSet = new MutableDelegateRuleSet(de, null, eventFolder, name);
         XiNode ruleSetNode = XiChild.getChild(root, ExpandedName.makeName("ruleset"));

         if (ruleSetNode == null) {
             return de; //SS ADDED. For existing RepoTypes, they dont have
         }

         XiNode rulesNode = XiChild.getChild(ruleSetNode, DefaultMutableRuleSet.RULES_NAME);
         Iterator rulesIt = rulesNode.getChildren();
         while (rulesIt.hasNext()) {
             XiNode ruleNode = (XiNode) rulesIt.next();
             DefaultMutableRule.createDefaultRuleFromNode(ruleNode, de.m_ruleSet);
         }//endwhile


         return de;
     }


     public XiNode toXiNode(XiFactory factory) {
         XiNode root = super.toXiNode(factory, "event");
         root.removeAttribute(GUID_NAME);
         root.setAttributeStringValue(AbstractMutableEntity.FOLDER_NAME, m_folder);
         root.setAttributeStringValue(AbstractMutableEntity.NAME_NAME, getName());
         root.setAttributeStringValue(AbstractMutableEntity.DESCRIPTION_NAME, getDescription());

         if (m_type == Event.TIME_EVENT) {
             root.setAttributeStringValue(TYPE_NAME, "time");
         } else if (m_type == Event.SIMPLE_EVENT) {
             root.setAttributeStringValue(TYPE_NAME, "simple");
         }

         /** Save all props regardless of type */
         root.setAttributeStringValue(SPECIFIED_TIME_NAME, String.valueOf(getSpecifiedTime()));
         root.setAttributeStringValue(TIME_INTERVAL_NAME, String.valueOf(getInterval()));
         root.setAttributeStringValue(INTERVAL_UNIT_NAME, String.valueOf(getIntervalUnit()));
         root.setAttributeStringValue(TTL_NAME, String.valueOf(getTTL()));
         root.setAttributeStringValue(UNITS_NAME, TTL_UNITS_DESCRIPTIONS[getTTLUnits()]);
         root.setAttributeStringValue(SCHEDULE_NAME, SCHEDULE_DESCRIPTIONS[m_schedule]);
         root.setAttributeStringValue(SUPER_NAME, String.valueOf(m_super));
         root.setAttributeStringValue(TIMEEVENT_COUNT_NAME, String.valueOf(this.m_timeEventCount));
         root.setAttributeStringValue(SERIALIZATION_FORMAT_NAME, String.valueOf(this.m_serializationFormat));
         root.setAttributeStringValue(RETRY_ON_EXCEPTION_NAME, String.valueOf(this.retryOnException));

         XiNode subEventsNode = root.appendElement(SUB_EVENTS_NAME);
         Iterator subEvents = m_subEvents.iterator();
         while (subEvents.hasNext()) {
             String path = (String) subEvents.next();
             XiNode subEventNode = subEventsNode.appendElement(SUB_EVENT_NAME);
             subEventNode.setStringValue(path);
         }

         final XiNode destinationNode = root.appendElement(DESTINATION_NAME);
         final String destinationName = (m_destinationName == null) ? "" : m_destinationName;
         final String destinationPath = (m_destinationChannelURI == null) ? "" : m_destinationChannelURI;
         destinationNode.setAttributeStringValue(DESTINATION_NAME_NAME, destinationName);
         destinationNode.setAttributeStringValue(DESTINATION_PATH_NAME, destinationPath);

         if (payload != null) {
             XiNode schemaNode = factory.createElement(PROPERTY_SCHEMA_NAME);
             schemaNode.appendChild(payload);
             root.appendChild(schemaNode);
         }

         XiNode propsNode = root.appendElement(USER_PROPERTIES_NAME);

         Iterator it = getUserProperties();
         while (it.hasNext()) {
             EventPropertyDefinition propDefn = (EventPropertyDefinition) it.next();
             String name = (String) propDefn.getPropertyName();
             RDFPrimitiveTerm type = (RDFPrimitiveTerm) propDefn.getType();

             XiNode propNode = propsNode.appendElement(USER_PROPERTY_NAME);
             propNode.appendElement(USER_PROPERTY_NAME_NAME, name);
             propNode.appendElement(USER_PROPERTY_TYPE_NAME, type.getName());
             propNode.appendChild(createXiNodeFromExtendedProperties(propDefn.getExtendedProperties()));
         }

         QNameLoadSaveUtils.writeNamespaces(root, m_nsMapper);
         QNameLoadSaveUtils.writeImports(root, m_importRegistry, XSD_IMPORT, IMPORT_SCHEMA_LOCATION);

//         XiNode ruleSetsNode = rulesToXiNode();
//         root.appendChild(ruleSetsNode);

         if (m_ruleSet != null) {
             XiNode ruleSetNode = m_ruleSet.toXiNode(factory);
             root.appendChild(ruleSetNode);
         }

         return root;
     }


     /**
      * Provides an interface to generate an SmElement required for interaction with Client programs such as BW,...
      *
      * @return SmElement
      */
     public SmElement toSmElement() {
         return null;
     }


     public RDFPrimitiveTerm getPropertyType(String propertyName) {
         EventPropertyDefinition propDefn = getPropertyDefinition(propertyName, true);
         if (propDefn != null) {
             return propDefn.getType();
         }
         return null;
     }


     public void setPropertyType(String propertyName, RDFPrimitiveTerm type) {
         final MutableEventPropertyDefinition epd = (MutableEventPropertyDefinition) getPropertyDefinition(propertyName, false);

         if (epd != null) {
             epd.setType(type);
//             setReferringRuleCompilationStatus(-1);
         }
     }


     public RDFPrimitiveTerm getSuperPropertyType(String propertyName) {
         Event ancestor = getAncestorForPropertyName(propertyName);
         if (ancestor != null) {
             return ancestor.getPropertyType(propertyName);
         }

         return null;
     }


     public RDFPrimitiveTerm getSubPropertyType(String propertyName) {
         Event event = getDescendantForPropertyName(propertyName);
         if (event != null) {
             return event.getPropertyType(propertyName);
         }

         return null;
     }


     public Event getAncestorForPropertyName(String propertyName) {
         Event ancestor = getSuperEvent();
         while (ancestor != null) {
             RDFPrimitiveTerm type = ancestor.getPropertyType(propertyName);
             if (type != null) {
                 return ancestor;
             }

             ancestor = ancestor.getSuperEvent();
         }

         return null;
     }


     public Event getDescendantForPropertyName(String propertyName) {
         if (m_ontology == null) {
             return null;
         }

         Iterator it = m_subEvents.iterator();
         while (it.hasNext()) {
             String path = (String) it.next();

             Event event = m_ontology.getEvent(path);
             if (event == null) {
                 continue;
             }

             RDFPrimitiveTerm type = event.getPropertyType(propertyName);
             if (type == null) {
                 type = event.getSubPropertyType(propertyName);
             }

             if (type != null) {
                 return event;
             }
         }

         return null;

     }


     public Rule getExpiryAction(boolean create) {
         MutableRule r = (MutableRule) m_ruleSet.getRule(EXPIRY_ACTION_RULE_NAME);

         if (r == null) {
             try {
                 r = createRule(EXPIRY_ACTION_RULE_NAME, false, false);
             } catch (ModelException e) {
                 e.printStackTrace();
             }
         }

         return r;
     }


     protected String hasUserPropertyConflict(DefaultMutableEvent de) {
         if (de == null) {
             return null;
         }

         /** An Event can't be in conflict with an ancestor */
         if (isA(de)) {
             return null;
         }

         /**
          * Check if this Event, or any of its sub Events have a property name
          * in common with the other Event, or any of that MutableEvent's ancestor.
          */
         for (Iterator it = de.getAllUserProperties().iterator(); it.hasNext(); ) {
             EventPropertyDefinition epd = (EventPropertyDefinition) it.next();
             String name = epd.getPropertyName();

             EventPropertyDefinition myEPD = getPropertyDefinition(name, false);
             if (myEPD != null) {
                 return name;
             }
             if (getSubPropertyType(name) != null) {
                 return name;
             }
         }

         return null;
     }


     public void addUserProperty(String propertyName, RDFPrimitiveTerm type) {
         m_userPropertiesList.add(new DefaultMutableEventPropertyDefinition(this, propertyName, type));
//         setReferringRuleCompilationStatus(-1);
 //    public void addUserProperty(String propertyName, RDFPrimitiveTerm type) throws ModelException {
 //        String msgKey = null;
 //
 //        Event owner = getAncestorForPropertyName(propertyName);
 //        if(owner != null) msgKey = USER_PROPERTY_CLOBBER_KEY;
 //        else {
 //            owner = getDescendantForPropertyName(propertyName);
 //            if(owner != null) msgKey = USER_PROPERTY_OVERRIDE_KEY;
 //        }
 //
 //        if(msgKey != null) {
 //            BEModelBundle bundle = BEModelBundle.getBundle();
 //            String msg = bundle.formatString(msgKey, propertyName, owner.getFullPath());
 //            throw new ModelException(msg);
 //        }
 //
 //        m_userProps.put(propertyName, type);
 //        notifyListeners();
 //    }

         notifyListeners();
         notifyOntologyOnChange();
     }


     public void deleteUserProperty(String propertyName) {
         for (int i = 0; i < m_userPropertiesList.size(); i++) {
             EventPropertyDefinition propDefn = (EventPropertyDefinition) m_userPropertiesList.get(i);
             if (propDefn.getPropertyName().equals(propertyName)) {
                 m_userPropertiesList.remove(i);
             }
         }
//         setReferringRuleCompilationStatus(-1);
         notifyListeners();
         notifyOntologyOnChange();
     }


     /**
      * @param propertyName
      * @return an EventPropertyDefinition
      */
     public EventPropertyDefinition getPropertyDefinition(String propertyName, boolean all) {
         Iterator it = null;
         if (!all) {
             it = m_userPropertiesList.iterator();
         } else {
             it = getAllUserProperties().iterator();
         }
         for (; it.hasNext();) {
             MutableEventPropertyDefinition propDefn = (MutableEventPropertyDefinition) it.next();
             if (propDefn.getPropertyName().equals(propertyName)) {
                 return propDefn;
             }
         }
         return null;
     }


     /**
      * ********************** methods used by default implementation *******************
      */
     public void addSubEvent(String subEventPath) {
         addToSet(m_subEvents, subEventPath);
     }


     public void removeSubEvent(String subEventPath) {
         removeFromSet(m_subEvents, subEventPath);
     }


     public void modifySubEvent(String oldPath, String newPath) {
         modifySet(m_subEvents, oldPath, newPath);
     }


     protected void addToSet(Set set, String entry) {
         set.remove(entry);
         set.add(entry);
         notifyListeners();
         notifyOntologyOnChange();
     }


     protected boolean removeFromSet(Set set, String entry) {
         boolean exists = set.remove(entry);
         if (exists) {
             notifyListeners();
             notifyOntologyOnChange();
         }

         return exists;
     }


     protected void modifySet(Set set, String oldEntry, String newEntry) {
         boolean exists = set.remove(oldEntry);
         if (!exists) {
             return;
         }

         set.add(newEntry);
         notifyListeners();
         notifyOntologyOnChange();
     }


     /**
      * ********************** End methods used by default implementation ***************
      */

     public String getTimeEventCount() {
         return m_timeEventCount;
     }


     public void setTimeEventCount(String timeEventCount) {
         m_timeEventCount = timeEventCount;
     }


     /**
      * @param serializationFormat
      */
     public void setSerializationFormat(int serializationFormat) {
         m_serializationFormat = serializationFormat;
     }


     /**
      * @return an int
      */
     public int getSerializationFormat() {
         return m_serializationFormat;
     }


     public List getRules() {
         return m_ruleSet.getRules();
     }


     public RulesetEntry getRule(String name) {
         return m_ruleSet.getRule(name);
     }


     public MutableRule createRule(String name, boolean renameOnConflict, boolean isAFunction) throws ModelException {
         DefaultMutableRule dr = (DefaultMutableRule) m_ruleSet.createRule(name, renameOnConflict, isAFunction);
         dr.addDeclaration("event", getFullPath());
         return dr;
     }


     public void deleteRule(String name) {
         m_ruleSet.deleteRule(name);
     }


     public void clear() {
         m_ruleSet.clear();
     }


     public void clearSubEventPaths() {
         m_subEvents.clear();
     }


     static class MutableDelegateRuleSet extends DefaultMutableRuleSet {


         protected DefaultMutableEvent m_event;


         public MutableDelegateRuleSet(DefaultMutableEvent event, DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
             super(ontology, folder, name);
             m_event = event;
         }


         public Folder getFolder() {
             return m_event.getFolder();
         }


         public String getFullPath() {
             return m_event.getFullPath();
         }


         public String getName() {
             return m_event.getName();
         }


         public void setOntology(MutableOntology ontology) {
             Collection rules = getRules();
             Iterator it = rules.iterator();
             while (it.hasNext()) {
                 final MutableRule rule = (MutableRule) it.next();
                 rule.setOntology(ontology);
             }
             this.m_ontology = (DefaultMutableOntology) ontology;
         }
     }

     /**
      * Used by DBConcept Generator
      * @param c
      * @throws ModelException
      */
     public void setPayloadElement(Concept c) throws ModelException {
         com.tibco.xml.ImportRegistryEntry ire[] = {new com.tibco.xml.ImportRegistryEntry(c.getNamespace(), c.getFullPath())};
         com.tibco.xml.DefaultImportRegistry beRegistry = new com.tibco.xml.DefaultImportRegistry();
         beRegistry.setImports(ire);

         com.tibco.xml.NamespaceMapper beMapper =  new com.tibco.xml.DefaultNamespaceMapper();
         beMapper.addNamespaceURI("pfx", c.getNamespace());

         setPayloadImportRegistry(beRegistry);
         setPayloadNamespaceImporter(beMapper);
         setPayloadSchemaAsString("<payload ref=pfx:" + c.getName() + "/>");
     }


    public boolean getRetryOnException() {
        return this.retryOnException;
    }


    public void setRetryOnException(boolean retry) {
        this.retryOnException = retry;
    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getExpiryCodeBlock()
	 */
	@Override
	public CodeBlock getExpiryCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isSoapEvent() {
		return (getType() == SIMPLE_EVENT
				&& RDFTypes.SOAP_EVENT.getName().equals(getSuperEventPath()));
	}
}
