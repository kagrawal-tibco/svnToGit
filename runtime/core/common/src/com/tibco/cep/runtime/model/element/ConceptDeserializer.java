package com.tibco.cep.runtime.model.element;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 18, 2006
 * Time: 10:28:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConceptDeserializer {
     public final static int STATE_NEW = 0;
     public final static int STATE_MODIFIED = 1;
     public final static int STATE_DELETED = 2;

     public final static int TYPE_STREAM = 0;
     public final static int TYPE_NAMEVALUE = 1;

    /**
     *
     * @param key
     * @param extKey
     */

    public boolean hasSchemaChanged();

    public boolean isDeleted();
    /**
     *
     * @return
     */
    public ConceptSerializer.ConceptMigrator getConceptMigrator();

    /**
     *
     * @return
     */
    public long startConcept();

    /**
     *
     * @return
     */
    public String getExtId();

    /**
     *
     * @return
     */
    public long getId();

    /**
     *
     * @return
     */
    public int getVersion();

    /**
     *
     */
    public void endConcept();

    /**
     *
     * @return
     */
    public int getType();

    /**
     *
     * @return parent
     */
    public ConceptOrReference startParentConcept();

    /**
     *
     */
    public void endParentConcept();


    /**
     *
     * @return
     */
    public int startReverseReferences();


    /**
     *
     * @param propertyIds
     * @param reverseRefs
     * @return
     */
    public void getReverseReferences(ConceptImpl.RevRefItr itr);

    /**
     *
     */
    public void endReverseReferences();

    /**
     *
     * @param propertyName
     */
    public boolean startProperty(String propertyName, int index);


    /**
     *
     * @param propertyName
     * @param index
     * @return
     */
    public int startPropertyArray(String propertyName, int index);


    /**
     *
     * @param index
     */
    public boolean startPropertyArrayElement(int index);

    /**
     *
     */
    public void endPropertyArrayElement();

    /**
     *
     * @param propertyName
     * @param index
     * @return
     */
    public int startPropertyAtom(String propertyName, int index);

    /**
     *
     */
    public void endProperty();

    /**
     *
     * @return
     */
    public String getStringProperty();

    /**
     *
     */
    public int getIntProperty();

    /**
     *
     * @return
     */
    public boolean getBooleanProperty();

    /**
     *
     * @return
     */
    public long getLongProperty();

    /**
     *
     * @return
     */
    public double getDoubleProperty();

    /**
     *
     * @return
     */
    public DateTimeTuple getDateTimeProperty();

    /**
     *
     * @return
     */
    public ConceptOrReference getReferenceConceptProperty();

    /**
     *
     * @return
     */
    public ConceptOrReference getContainedConceptProperty();


    public void getStringProperty(String[] s, long[]time);

    /**
     *
     */
    public void getIntProperty(int[]i, long[]time);

    /**
     *
     * @return
     */
    public void getBooleanProperty(boolean[]b, long[] time);

    /**
     *
     * @return
     */
    public void getLongProperty(long[] l, long[] time);

    /**
     *
     * @return
     */
    public void getDoubleProperty(double[] d, long[] time);

    /**
     *
     * @return
     */
    public void getDateTimeProperty(DateTimeTuple[] date, long[] time);

    /**
     *
     * @param date
     * @param tz
     * @param time
     */
    public void getDateTimeProperty(long[] date, String [] tz, long[] time);
    /**
     *
     * @return
     */
    public void getReferenceConceptProperty(ConceptOrReference[] ref, long[] time);

    /**
     *
     * @return
     */
    public void getContainedConceptProperty(ConceptOrReference[] ref, long[] time);


    public boolean areNullPropsSerialized();
}

