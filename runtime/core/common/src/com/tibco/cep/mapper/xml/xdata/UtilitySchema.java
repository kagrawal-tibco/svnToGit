// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmException;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmSchemaError;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableModelGroup;
import com.tibco.xml.schema.build.MutableParticle;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.tns.TargetNamespace;
import com.tibco.xml.tns.TargetNamespaceProvider;
import com.tibco.xml.tns.parse.TnsErrorSeverity;

public class UtilitySchema {

    public static final String UTILITY_SCHEMA_NS = "http://www.tibco.com/tnt/utilitySchema";

    public static final String UTILITY_CONTENT_ELEMENT_NAME = "content";

    public static final SmSchema INSTANCE;

    /**
     * Contains a single wildcard ('ANY')
     */
    public static final SmElement ANONYMOUS_ELEMENT;

    /**
     * A type with no content.
     */
    public static final SmType EMPTY_TYPE;

    /**
     * An element whose type is EMPTY_TYPE.
     */
    public static final SmElement EMPTY_ELEMENT;

    /**
     * An simple string 'item' element.
     */
    public static final SmElement ITEM_ELEMENT;

    /**
     * An element whose type is XSDL.STRING.
     */
    public static final SmElement CONTENT_ELEMENT;

    public static final SmElement DOCUMENT_ELEMENT;

    public static final SmElement NAMESPACE_ELEMENT;

    /**
     * A marker xsd String subtype for indicating a reference to a java native object.
     */
    public static final SmType JAVA_NATIVE_OBJECT_REFERENCE_TYPE;

    /**
     * Since XML Schema can't describe java objects, this marker type is used
     * to indicate where an XData contains a java native object.<BR>
     * This is useful for passing non-data objects such as connections, etc.
     */
    //public static final SmType JAVA_NATIVE_TYPE;

    static {
        SmFactory factory;
        try {
            factory = SmFactory.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create factory");
        }
        MutableSchema ms = factory.createMutableSchema();
        ms.setNamespace(UTILITY_SCHEMA_NS);
        MutableType anont = MutableSupport.createType(ms,null);
        MutableModelGroup mmg = (MutableModelGroup) ms.createComponent(SmComponent.MODEL_GROUP_TYPE);
        anont.setContentModel(mmg);
        MutableParticle mp = (MutableParticle) ms.createComponent(SmComponent.PARTICLE_TYPE);
        mp.setMinOccurrence(1);
        mp.setMaxOccurrence(1);
        /* NOT IN BUILD: MutableWildcard mw = (MutableWildcard) ms.createComponent(SmComponent.WILDCARD_TYPE);
        mp.setTerm(mw);
        mmg.addParticle(mp);
        */

        MutableElement anon = MutableSupport.createElement(ms,"Data",anont);
        EMPTY_TYPE = MutableSupport.createType(ms,"empty");
        EMPTY_ELEMENT = MutableSupport.createElement(ms,"empty",EMPTY_TYPE);

        INSTANCE = ms;
        ANONYMOUS_ELEMENT = anon;

        CONTENT_ELEMENT = MutableSupport.createElement(ms,
            UTILITY_CONTENT_ELEMENT_NAME,XSDL.STRING);

        DOCUMENT_ELEMENT = MutableSupport.createElement(ms,
            UTILITY_CONTENT_ELEMENT_NAME,XSDL.STRING);

        NAMESPACE_ELEMENT = MutableSupport.createElement(ms,
            UTILITY_CONTENT_ELEMENT_NAME,XSDL.STRING);

        //JAVA_NATIVE_TYPE = MutableSupport.createType(ms,"JavaNative",XSDL.ANY_TYPE);

        SmSchemaError.Handler handler = new SmSchemaError.Handler() {

            public void error(SmSchemaError error) throws SmException {
                throw new RuntimeException("Bad utility schema");
            }

            public void warning(SmSchemaError error) throws SmException {
                throw new RuntimeException("Bad utility schema");
            }

			/**
			 * @return true iff constraint-checking for this schema should be performed
			 */
			public boolean isConstraintChecking() {
				return true;
			}
		};
        ITEM_ELEMENT = MutableSupport.createElement(ms,"item",XSDL.STRING);

        JAVA_NATIVE_OBJECT_REFERENCE_TYPE = MutableSupport.createType(ms,"nativeJavaObjectKey", XSDL.STRING);

        try {
            ms.lock(handler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error starting");
        }
    }

    public static SmElement createErrorMarkerElement(ExpandedName elName, String errorMessage)
    {
        return createErrorMarkerElement(elName,XSDL.ANY_TYPE,errorMessage);
    }

    public static SmElement createErrorMarkerElement(ExpandedName elName, SmType type, String errorMessage)
    {
        if (errorMessage==null)
        {
            errorMessage = "(Internal issue, null error message)";
        }
        if (type==null)
        {
            type = XSDL.ANY_TYPE;
        }
        SmFactory factory;
        try
        {
            factory = SmFactory.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Couldn't create factory");
        }
        MutableSchema ms = factory.createMutableSchema();
        ms.setNamespace(elName.getNamespaceURI());
        MutableElement el = MutableSupport.createElement(ms,elName.getLocalName(),type);
        el.setNamespace(elName.getNamespaceURI());
        // NOTE: In a previous release, the 2nd argument (below) was "" & it worked, at some point it changed
        // so that the 2nd argument is needed & not the 3rd.  Just leave both for now, seems to work.
        // Really need to get Tibco on 1 error/localization strategy not 3.
        el.addError(TnsErrorSeverity.ERROR,"{0}",errorMessage,-1,-1); // no way around deprecation.
        try
        {
            ms.lock(new DefaultSchemaErrorHandler());
            ms.resolveAndCheck(new TargetNamespaceProvider()
            {
                public TargetNamespace getNamespace(String namespace, Class kind)
                {
                    // n/a.
                    return null;
                }
            });
        }
        catch (SmException se)
        {
            throw new RuntimeException("Internal error: " + se.getMessage());
        }
        return el;
    }

    /**
     * Creates an unnamespaced element whose type is {@link XSDL#ANY_TYPE} and records the error message using the TnsErrors on the element itself.<br>
     * This is useful for gracefully displaying when all or part of a schema or reference doesn't work.<br>
     * WCETODO probably a good candidate for MutableSupport or somewhere like that...
     * @param errorMessage The error message causing the message to embed inside the element, cannot be null.
     * @return The marker element.
     */
    public static SmElement createErrorMarkerElement(String errorMessage)
    {
        return createErrorMarkerElement(ExpandedName.makeName("error"),errorMessage);
    }

    /*
     * No longer relevant w/ new error container.
     * Checks if the elements type is {@link #ERROR_TYPE} -- , i.e. those created by
     * {@link #createErrorMarkerElement(java.lang.String)}.<br>
     * @param element The element to test for being an error marker, can be null.
     * @return True if it is a marker element, false if not or if it is null element.
     *
    public static boolean isErrorMarkerElement(SmElement element) {
        if (element==null) {
            return false;
        }
        return isErrorMarkerType(element.getType());
    }*/

    /*No longer relevant w/ new error container.
     * Checks if the type is {@link #ERROR_TYPE} -- , i.e. those created by
     * {@link #createErrorMarkerElement(java.lang.String)}.<br>
     * @param type The element to test for being an error marker, can be null.
     * @return True if it is a marker type, false if not or if it is a null type.
     *
    public static boolean isErrorMarkerType(SmType type)
    {
        return type==ERROR_TYPE;
    }*/

    /* No longer relevant w/ new error container.
     * Extracts the error message string from an error marker element, i.e. those created by
     * {@link #createErrorMarkerElement(java.lang.String)}.
     * @param element The element from which to extract the error message string.
     * @return The error message, or null if not an error marker element (or element is null).
     *
    public static String extractErrorStringFromMarkerElement(SmElement element)
    {
        if (!isErrorMarkerElement(element))
        {
            return null;
        }
        SmMetaForeignAttribute attr = SmSupport.getMetaForeignAttribute(element,MARKER_ELEMENT_FNA.getNamespaceURI(),MARKER_ELEMENT_FNA.getLocalName());
        if (attr==null) {
            return null;
        }
        return attr.getValue();
    }*/
}