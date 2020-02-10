package com.tibco.cep.runtime.model;


import java.lang.reflect.Constructor;
import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;


/**
 * Manages the runtime types defined in a project.
 *
 * @version 2.1.0
 * @.category public-api
 * @since 2.0.0
 */
public interface TypeManager {


    /**
     * Default namespace for the BE ontology - "www.tibco.com/be/ontology".
     *
     * @.category public-api
     * @since 2.0.0
     */
    final public static String DEFAULT_BE_NAMESPACE_URI = "www.tibco.com/be/ontology";

    /**
     * Matches simple events only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_SIMPLEEVENT = 0x0001;

    /**
     * Matches time events only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_TIMEEVENT = 0x0002;

    /**
     * Matches concepts only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_CONCEPT = 0x0004;

    /**
     * Matches scorecards only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_NAMEDINSTANCE = 0x0008;


    /**
     * Matches rules only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_RULE = 0x0010;

    /**
     * Matches rule functions only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_RULEFUNCTION = 0x0020;


    /**
     * Matches state machines only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_STATEMACHINE = 0x0040;

    /**
     * Matches state machines only.
     *
     * @.category public-api
     * @see #getTypeDescriptors(int)
     * @since 2.0.0
     */
    final public static int TYPE_POJO = 0x0080;
    
    final public static int TYPE_OBJECT_BEAN = 0x0100;

    final public static int METRIC_TYPE_FALSE = 0x0000;
    final public static int METRIC_TYPE_PRIMARY = 0x0001;
    final public static int METRIC_TYPE_DVM = 0x0002;
    
    /**
     * Gets all the <code>TypeDescriptor</code>'s for a given type.
     *
     * @param type the type of TypeDescriptor</code>.
     * @return All the <code>TypeDescriptor</code>'s of the given type.
     * @.category public-api
     * @see #TYPE_CONCEPT
     * @see #TYPE_NAMEDINSTANCE
     * @see #TYPE_RULE
     * @see #TYPE_RULEFUNCTION
     * @see #TYPE_SIMPLEEVENT
     * @see #TYPE_TIMEEVENT
     * @since 2.0.0
     */
    public Collection getTypeDescriptors(int type);


    /**
     * Returns the <code>TypeDescriptor</code> for the resource specified by the given <code>Class</code>.
     *
     * @param cls a <code>Class</code>.
     * @return a <code>TypeDescriptor</code> that represents the type.
     * @.category public-api
     * @since 2.1.0
     */
    public TypeDescriptor getTypeDescriptor(Class cls);


    /**
     * Returns the <code>TypeDescriptor</code> for the resource specified by the given <code>ExpandedName</code>.
     *
     * @param en an <code>ExpandedName</code>.
     * @return a <code>TypeDescriptor</code> that represents the type.
     * @.category public-api
     * @since 2.0.0
     */
    public TypeDescriptor getTypeDescriptor(ExpandedName en);


    /**
     * Returns a <code>TypeDescriptor</code> given the URI of the type resource in designer.
     *
     * @param uri the full path as in repository.
     * @return a <code>TypeDescriptor</code> that represents the type resource.
     * @.category public-api
     * @since 2.0.0
     */
    public TypeDescriptor getTypeDescriptor(String uri);


    /**
     * Creates an <code>Event</code> or a <code>Concept</code> given the <code>ExpandedName</code> of the type resource.
     *
     * @param en the <code>ExpandedName</code> of the entity to create.
     * @return en <code>Entity</code> created.
     * @throws Exception if not able to create the instance.
     * @.category public-api
     * @since 2.0.0
     */
    public Entity createEntity(ExpandedName en) throws Exception;


    /**
     * Creates an <code>Event</code> or a <code>Concept</code> instance given the URI of the type resource in Designer.
     *
     * @param uri the full path as in repository.
     * @return the <code>Entity</code> created.
     * @throws Exception if not able to create the instance.
     * @.category public-api
     */
    public Entity createEntity(String uri) throws Exception;


    /**
     * Gets the payload factory of this <code>TypeManager</code>.
     *
     * @return a <code>PayloadFactory</code>.
     */
    public PayloadFactory getPayloadFactory();

//    /* *
//     * Check if an Event or a Concept instance is instance of a given type specified by the full path.
//     * @param entity an Event or Concept instance to be checked
//     * @param fullPath the full path as in repository.
//     * @return true if the Event or Concept instance is a type specifiec by the fullPath.
//     *
//    public boolean instanceOf(Entity entity, String fullPath);
//
//    /* *
//     * Check if an Event or a Concept instance is instance of a given type specified by the ExpandedName.
//     * @param entity an Event or Concept instance to be checked
//     * @param en the ExpandedName.
//     * @return true if the Event or Concept instance is a type specified by the ExpandedName.
//     * 
//    public boolean instanceOf(Entity entity, ExpandedName en);


    /**
     * Describes the runtime corresponding to a type defined in a project.
     *
     * @version 2.0.0
     * @.category public-api
     * @since 2.0.0
     */
    public static class TypeDescriptor implements Comparable<TypeDescriptor> {


        private ExpandedName expandedName;
        private String uri;
        private Class implClass;
        private int type;
        private SmElement element;
        private Constructor ctor;
        private int metricType;
        private int typeId;


        /**
         * Gets the index representing the category of the type described.
         *
         * @return the index representing the category of the type described.
         * @.category public-api
         * @see #TYPE_CONCEPT
         * @see #TYPE_NAMEDINSTANCE
         * @see #TYPE_RULE
         * @see #TYPE_RULEFUNCTION
         * @see #TYPE_SIMPLEEVENT
         * @see #TYPE_TIMEEVENT
         */
        public int getType() {
            return type;
        }


        /**
         * Gets the URI of the type described.
         *
         * @return the URI of the type described.
         * @.category public-api
         */
        public String getURI() {
            return uri;
        }


        /**
         * Gets the implementing class of the type described.
         *
         * @return the implementing class of the type described.
         * @.category public-api
         */
        public Class getImplClass() {
            return implClass;
        }


        public SmElement getSmElement() {
            return element;
        }


        /**
         * Gets the <code>ExpandedName</code> of the type described.
         *
         * @return the <code>ExpandedName</code> of the type described.
         * @.category public-api
         */
        public ExpandedName getExpandedName() {
            return expandedName;
        }


        public Constructor getConstructor() {
            return ctor;
        }

        /**
         * Return the type of metric
         *
         * @return type of the metric
         * category not-public-api
         */
        public int getMetricType() {
        	return metricType;
        }
        
        public TypeDescriptor(int type, String uri, Class implClass, ExpandedName expandedName, SmElement smElement, int metricType) {
            this.type = type;
            this.uri = uri;
            this.implClass = implClass;
            this.element = smElement;
            this.expandedName = expandedName;
            this.metricType = metricType;
            
            try {
                this.ctor = implClass.getConstructor(new Class[]{long.class});
            }
            catch (java.lang.NoSuchMethodException e) {

            }
        }


        @Override
        public int compareTo(TypeDescriptor o) {
            if (null == o) {
                return 1;
            }
            return this.getURI().compareTo(o.getURI()); 
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }


}//class
