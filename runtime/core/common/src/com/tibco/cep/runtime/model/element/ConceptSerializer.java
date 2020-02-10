package com.tibco.cep.runtime.model.element;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 18, 2006
 * Time: 8:37:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConceptSerializer {
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
    public void startConcept(Class clz, long key, String extKey, int state, int version);

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
     * @param parent
     */
    public void startParentConcept(ConceptOrReference parent);

    /**
     *
     */
    public void endParentConcept();


    /**
     *
     * @param propertyIds
     * @param reverseRefs
     */
    public void startReverseReferences(ConceptImpl.RevRefItr itr, int size);

    /**
     *
     */
    public void endReverseReferences();

    /**
     *
     * @param propertyName
     */
    public void startProperty(String propertyName, int index, boolean isSet);

    /**
     *
     * @param propertyName
     * @param index
     * @param length
     */
    public void startPropertyArray(String propertyName, int index, int length);

    /**
     *
     * @param index
     */
    public void startPropertyArrayElement(int index, boolean isSet);

    /**
     *
     * @param index
     */
    public void startPropertyArrayElement(int index);
    /**
     *
     */
    public void endPropertyArrayElement();


    /**
     *
     * @param propertyName
     * @param index
     * @param isSet
     * @param historySize
     */
    public void startPropertyAtom(String propertyName, int index, boolean isSet, int historyIndex, int historySize);
    /**
     *
     */
    public void endProperty();

    /**
     *
     * @param s
     */
    public void writeStringProperty(String s);

    /**
     *
     * @param i
     */
    public void writeIntProperty(int i);

    /**
     *
     * @param b
     */
    public void writeBooleanProperty(boolean b);

    /**
     *
     * @param l
     */
    public void writeLongProperty(long l);

    /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d);
    /**
     *
     * @param date
     */
    public void writeDateTimeProperty(DateTimeTuple date);

    /**
     *
     * @param ref
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref);

    /**
     *
     * @param ref
     */
    public void writeContainedConceptProperty(ConceptOrReference ref);

    /**
     *
     * @param s
     */
    public void writeStringProperty(String s[], long time[]);

    /**
     *
     * @param i
     */
    public void writeIntProperty(int []i, long time[]);

    /**
     *
     * @param b
     */
    public void writeBooleanProperty(boolean b[], long time[]);

    /**
     *
     * @param l
     */
    public void writeLongProperty(long l[], long time[]);

    /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d[], long time[]);
    /**
     *
     * @param time
     */
    public void writeDateTimeProperty(DateTimeTuple date[], long time[]);


    /**
     *
     * @param date
     * @param tz
     * @param time
     */
    public void writeDateTimeProperty(long date[], String tz[], long time[]);

    /**
     *
     * @param ref
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref[], long time[]);

    /**
     *
     * @param ref
     */
    public void writeContainedConceptProperty(ConceptOrReference ref[], long time[]);


    /**
     *
     * @return
     */
    public boolean areNullPropsSerialized();

    public interface PropertyDescription {
        public int previousIndex();
        public int currentIndex();
        public Property getDeletedProperty();
    }

    public interface ConceptMigrator {
        public boolean wasContained();
        public PropertyDescription[] getPropertiesMap();
    }
}
