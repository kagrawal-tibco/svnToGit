package com.tibco.be.parser.codegen;

import com.tibco.cep.runtime.model.element.impl.GeneratedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.PropertyAtomContainedConcept_CalledFromCondition;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 7, 2004
 * Time: 6:31:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CGConstants {
	//generate concepts so that hotdeploy works with new properties
	public static final boolean HDPROPS = Boolean.parseBoolean(System.getProperty("com.tibco.be.hotdeploy.concept", "true"));
	public static final boolean HDPROPS_RUNTIME = Boolean.parseBoolean(System.getProperty("com.tibco.be.hotdeploy.concept", "true"));
	
    public static final String META_PROPS_ARRAY = "m_metaProps";
    public static final String CONCEPT_PROP_IDXS_ARRAY = "m_conceptPropIdxs";
    public static final String CONCEPT_PROP_IDXS_METHOD = "conceptPropIdxs";
    public static final String PARENT_PROPERTY_NAME = "parentPropertyName";

    public static final String BRK = String.format("%n");
    public static final String SET_PREFIX = "set";
    public static final String GET_PREFIX = "get";
    
    public static final String charset = "UTF-8";//$NON-NLS-1$
    
    public static final String defaultDateTime = com.tibco.cep.runtime.model.element.PropertyAtomDateTime.class.getName() + ".DEFAULT_VALUE";

    public static final String engineEntityInterface = com.tibco.cep.kernel.model.entity.Entity.class.getName();
    public static final String engineEventInterface = com.tibco.cep.kernel.model.entity.Event.class.getName();
    public static final String engineSimpleEventInterface = com.tibco.cep.runtime.model.event.SimpleEvent.class.getName();
    public static final String engineTimeEventInterface = com.tibco.cep.runtime.model.event.TimeEvent.class.getName();
    public static final String engineAdvisoryEventInterface = com.tibco.cep.runtime.model.event.AdvisoryEvent.class.getName();
    public static final String engineConceptInterface = com.tibco.cep.runtime.model.element.Concept.class.getName();
    public static final String engineConceptReferenceInterface = com.tibco.cep.runtime.model.element.Concept.class.getName();
    public static final String engineContainedConceptInterface = com.tibco.cep.runtime.model.element.ContainedConcept.class.getName();
    public static final String engineExceptionInterface = com.tibco.cep.runtime.model.exception.BEException.class.getName();
    public static final String engineProcessBaseClass = "com.tibco.cep.bpmn.runtime.model.JobContext";

    public static final String engineSimpleEventImpl = com.tibco.cep.runtime.model.event.impl.SimpleEventImpl.class.getName();
    public static final String engineTimeEventImpl = com.tibco.cep.runtime.model.event.impl.TimeEventImpl.class.getName();
    public static final String engineAdvisoryEventImpl = com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl.class.getName();
    public static final String engineConceptImpl = GeneratedConceptImpl.class.getName();

    //these can't go into arrays because BuiltinLookup needs to access each variable directly
    public static final String entityIdAttrName = "id";
    public static final String entityIdAttrGetter = "getId()";
    public static final String entityExtIdAttrName = "extId";
    public static final String entityExtIdAttrGetter = "getExtId()";
    public static final String containedConceptParentAttrName = "parent";
    public static final String containedConceptParentAttrGetter= "getParent()";
    public static final String eventTtlAttrName = "ttl";
    public static final String eventTtlAttrGetter = "getTTL()";
    public static final String simpleEventPayloadAttrName = "payload";
    public static final String simpleEventPayloadAttrGetter = "getPayloadAsString()";
    public static final String simpleEventSOAPActionAttrName = "soapAction";
    public static final String simpleEventSOAPActionAttrGetter = "getSoapAction()";
    public static final String timeEventClosureAttrName = "closure";
    public static final String timeEventClosureAttrGetter = "getClosure()";
    public static final String timeEventScheduledTimeAttrName = "scheduledTime";
    public static final String timeEventScheduledTimeAttrGetter = "getScheduledTime()";
    public static final String timeEventIntervalAttrName = "interval";
    public static final String timeEventIntervalAttrGetter = "getInterval()";
    public static final String advisoryEventCategoryAttrName = com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_CATEGORY;
    public static final String advisoryEventCategoryAttrGetter = "getCategory()";
    public static final String advisoryEventTypeAttrName= com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_TYPE;
    public static final String advisoryEventTypeAttrGetter = "getType()";
    public static final String advisoryEventMessageAttrName = com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_MESSAGE;
    public static final String advisoryEventMessageAttrGetter = "getMessage()";
    public static final String advisoryEventRuleUriAttrName = com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_RULEURI;
    public static final String advisoryEventRuleUriAttrGetter = AdvisoryEventImpl.class.getName() + ".getRuleUri";
    public static final String advisoryEventRuleScopeAttrName = com.tibco.cep.designtime.model.event.AdvisoryEvent.ATTRIBUTE_RULESCOPE;
    public static final String advisoryEventRuleScopeAttrGetter = AdvisoryEventImpl.class.getName() + ".getRuleScope";
    public static final String beExceptionMessageAttrName = com.tibco.cep.designtime.model.BEException.ATTRIBUTE_MESSAGE;
    public static final String beExceptionMessageAttrGetter = "getMessage()";
    public static final String beExceptionErrorTypeAttrName = com.tibco.cep.designtime.model.BEException.ATTRIBUTE_ERROR_TYPE;
    public static final String beExceptionErrorTypeAttrGetter = "getErrorType()";
    public static final String beExceptionCauseAttrName = com.tibco.cep.designtime.model.BEException.ATTRIBUTE_CAUSE;
    public static final String beExceptionCauseAttrGetter = "get_Cause()";
    public static final String beExceptionStackTraceAttrName = com.tibco.cep.designtime.model.BEException.ATTRIBUTE_STACK_TRACE;
    public static final String beExceptionStackTraceAttrGetter = "get_StackTrace()";

    public static final String propertySubjectAttrName = "subject";  //todo - parent???
    public static final String propertySubjectAttrGetter = "getParent()";
    public static final String propertyHistorySizeName = "historySize";
    public static final String propertyHistorySizeGetter = "getHistorySize()";
    public static final String propertyAtomIsSetAttrName = "isSet";
    public static final String propertyAtomIsSetAttrGetter = "isSet()";

    public static final String lengthAttrName = "length";
    public static final String propertyArrayLengthAttrGetter = "length()";
    public static final String primitiveArrayLengthAttrGetter = "length";
    public static final String propertyArrayElementGetter = "get";

    public static final String stringLengthAttrGetter = "length()";

    public static final String propertyGenericInterface = com.tibco.cep.runtime.model.element.Property.class.getName();
    public static final String propertyAtomGenericInterface = com.tibco.cep.runtime.model.element.PropertyAtom.class.getName();
    public static final String propertyArrayGenericInterface = com.tibco.cep.runtime.model.element.PropertyArray.class.getName();

    //the following arrays will be accessed with the type constants from
    //RDFTypes (as return values of getType()) and must be kept in sync with them
    public static final String[] propertyTypeInterfaceFSClassNames = {
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyString",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyInt",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyLong",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyDouble",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyBoolean",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyDateTime",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyContainedConcept",
        com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyConceptReference"
    };
    //since RDFTypes.Concept is mapped to ContainedConcept, there is no xyz_TYPEID that can be used for this
    public static final String propertyConceptFSClassName = com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyConcept";

    public static final String[] propertyArrayInterfaceFSClassNames = {
        com.tibco.cep.runtime.model.element.PropertyArrayString.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayInt.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayLong.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayDouble.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayBoolean.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayDateTime.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyArrayConceptReference.class.getName()
    };
    public static final String propertyArrayConceptFSClassName = com.tibco.cep.runtime.model.element.PropertyArrayConcept.class.getName();

    public static final String[] propertyArrayImplFSClassNames = {
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayStringImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayIntImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayLongImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayDoubleImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayBooleanImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayDateTimeImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayContainedConceptImpl.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayConceptReferenceImpl.class.getName()
    };

    public static final String[] propertyArrayImplSimpleClassNames = {
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayStringSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayIntSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayLongSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayDoubleSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayBooleanSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayDateTimeSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayContainedConceptSimple.class.getName(),
        com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple.class.getName()
    };

    public static final String[] propertyAtomInterfaceFSClassNames = {
        com.tibco.cep.runtime.model.element.PropertyAtomString.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomInt.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomLong.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomDouble.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomBoolean.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomDateTime.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept.class.getName(),
        com.tibco.cep.runtime.model.element.PropertyAtomConceptReference.class.getName()
    };

    public static final String propertyAtomConceptFSClassName = com.tibco.cep.runtime.model.element.PropertyAtomConcept.class.getName();

    public static final String[] propertyAtomImplFSClassNames = {
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomStringImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomIntImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomLongImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDoubleImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomBooleanImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDateTimeImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomContainedConceptImpl.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomConceptReferenceImpl.class.getName()
    };

    public static final String[] propertyAtomImplSimpleClassNames = {
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomLongSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDoubleSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomBooleanSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomContainedConceptSimple.class.getName(),
            com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomConceptReferenceSimple.class.getName()
    };

    public static final String[] setArgumentTypes = {
          "java.lang.String"
        , "int"
        , "long"
        , "double"
        , "boolean"
        , "java.util.Calendar"
        , engineContainedConceptInterface
        , engineConceptReferenceInterface
    };

    public static final String[] boxedPrimitivePropertyTypes = {
        "java.lang.String"
      , "java.lang.Integer"
      , "java.lang.Long"
      , "java.lang.Double"
      , "java.lang.Boolean"
      , "java.util.Calendar"
      , engineContainedConceptInterface
      , engineConceptReferenceInterface
  	};


    public static final String[] propAtomSetterNames = {
         "setString"
        , "setInt"
        , "setLong"
        , "setDouble"
        , "setBoolean"
        , "setDateTime"
        , "setContainedConcept"
        , "setConcept"
    };

    public static final String[] jdbcGetterNames = {
          "getString"
        , "getInt"
        , "getLong"
        , "getDouble"
        , "getBoolean"
        , "getTimestamp"
        , null
        , null
    };

    public static final String[] jdbcSetterNames = {
        "setString"
      , "setInt"
      , "setLong"
      , "setDouble"
      , "setBoolean"
      , "setTimestamp"
      , null
      , null
    };

    public static final String[] propertyAtomInterfaceXMLClassNames = {
        "com.tibco.xml.data.primitive.values.XsString",
        "com.tibco.xml.data.primitive.values.XsInt",
        "com.tibco.xml.data.primitive.values.XsLong",
        "com.tibco.xml.data.primitive.values.XsDouble",
        "com.tibco.xml.data.primitive.values.XsBoolean",
        "com.tibco.xml.data.primitive.values.XsDateTime",
        "com.tibco.xml.datamodel.XiNode",
        "com.tibco.xml.datamodel.XiNode"
    };

    public static final String[] propertyAtomInterfaceXMLMethodNames = {
        "new com.tibco.xml.data.primitive.values.XsString(%)",
        "new com.tibco.xml.data.primitive.values.XsInt(%)",
        "new com.tibco.xml.data.primitive.values.XsLong(%)",
        "new com.tibco.xml.data.primitive.values.XsDouble(%)",
        "%?com.tibco.xml.data.primitive.values.XsBoolean.TRUE:com.tibco.xml.data.primitive.values.XsBoolean.FALSE",
        "com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport.convertToXsDateTime(%)",
        "com.tibco.xml.datamodel.XiNode",
        "com.tibco.xml.datamodel.XiNode"
    };

    public static final String SET_XML_PREFIX = SET_PREFIX+"Xml";
    public static final String GET_XML_PREFIX = GET_PREFIX+"Xml";

    public static final String TAB = "\t";

    public static final String scorecardInstanceGetter = "getStaticInstance";
    // public static final String scorecardLookupMethod = com.tibco.cep.runtime.session.RuleSessionManager.class.getName() + ".getCurrentRuleSession().getObjectManager().getElement";

    public static final String scorecardLookupMethod = com.tibco.cep.runtime.session.RuleSessionManager.class.getName() + ".getCurrentRuleSession().getObjectManager().getNamedInstance";    
    public static final String actionImpl = com.tibco.cep.kernel.model.rule.impl.ActionImpl.class.getName();

    

    public static final String objectImpl = "java.lang.Object";

    public static final String oversizeStringConstantGetter = "be.gen.OversizeStringConstants.getConstant";

    public static final String getCurrentRuleSession = com.tibco.cep.runtime.session.RuleSessionManager.class.getName() + ".getCurrentRuleSession()";

    public static final String GENERATE_NULL_CONTAINED_CONCEPT = "tibco.be.generateNullContainedConcept";
    public static final String nullContainedConceptInnerClassName = "NullContainedConcept";
    public static final String nullContainedConceptInstanceName = "INSTANCE";
    public static final String calledFromCondition = "calledFromCondition";
    public static final String propertyAtomContainedConcept_CalledFromCondition = PropertyAtomContainedConcept_CalledFromCondition.class.getName();
}
