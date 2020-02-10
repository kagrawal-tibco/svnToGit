package com.tibco.cep.driver.http.server.impl.servlet.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.pojo.Pojo;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 29/8/11
 * Time: 10:46 AM
 * A special concept instance with extid = session id.
 */
public class HTTPSessionConceptImpl extends ConceptImpl implements Pojo {

    private HttpSession wrappedSession;

    /**
     * Make this special namespace to register this concept with the BE classloader.
     */
    public static ExpandedName concept_expandedName = ExpandedName.makeName("www.tibco.com/be/ontology/Concepts/_HTTPSession_","_HTTPSession_");

    private Map<String, Property> attributePropertiesMap = new LinkedHashMap<String, Property>();

    /**
     *
     * @param id
     * @param httpSession
     */
    public HTTPSessionConceptImpl(long id, HttpSession httpSession) {
        super(id);
        this.wrappedSession = httpSession;
        //Session ID
        this.extId = wrappedSession.getId();
    }


    @Override
    public Property getProperty(String name) {
        if (attributePropertiesMap.containsKey(name)) {
            Property attributeProperty = attributePropertiesMap.get(name);
            return attributeProperty;
        }
        return super.getProperty(name);
    }

    /**
     *
     */
    public void setAttributeProperties() {
        Enumeration<String> sessionAttributes = wrappedSession.getAttributeNames();
        while (sessionAttributes.hasMoreElements()) {
            String sessionAttribute = sessionAttributes.nextElement();
            setAttribute(sessionAttribute, wrappedSession.getAttribute(sessionAttribute));
        }
        //Set other info
        long creationTime = wrappedSession.getCreationTime();
        setAttribute("CREATION_TIME", creationTime);
        long lastAccessedTime = wrappedSession.getLastAccessedTime();
        setAttribute("LAST_ACCESS_TIME", lastAccessedTime);
    }

    /**
     *
     * @param sessionProperty
     * @param attributeValue
     */
    public void setAttribute(String sessionProperty, Object attributeValue) {
        if (!attributePropertiesMap.containsKey(sessionProperty)) {
            Property attributeProperty =
                    PropertyFactory.getProperty(this, attributeValue);
            attributePropertiesMap.put(sessionProperty, attributeProperty);
        }
    }

    static class PropertyFactory {
        static Property getProperty(Concept ownerConcept, Object attributeValue) {
            if (attributeValue instanceof String) {
                return new SimpleSessionStringAttribute(ownerConcept, (String)attributeValue);
            } else if (attributeValue instanceof Long) {
                return new SimpleSessionLongAttribute(ownerConcept, (Long)attributeValue);
            }
            return null;
        }
    }

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param in the stream to read data from in order to restore the object
     * @throws java.io.IOException    if I/O errors occur
     * @throws ClassNotFoundException If the class for an object being
     *                                restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //Read attributes map
        attributePropertiesMap = (Map)in.readObject();
    }

    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @param out the stream to write the object to
     * @throws java.io.IOException Includes any I/O exceptions that may occur
     * @serialData Overriding methods should use this tag to describe
     * the data layout of this Externalizable object.
     * List the sequence of element types and, if possible,
     * relate the element to a public/protected field and/or
     * method of this Externalizable class.
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //Write attributes map
        out.writeObject(attributePropertiesMap);
    }
}
