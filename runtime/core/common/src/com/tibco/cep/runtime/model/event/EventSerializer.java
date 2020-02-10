package com.tibco.cep.runtime.model.event;


import java.util.Calendar;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 19, 2006
 * Time: 8:06:00 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EventSerializer {
     public final static int STATE_NEW = 0;
     public final static int STATE_ACKED = 1;
     public final static int STATE_DELETED = 2;

     public final static int TYPE_STREAM = 0;
     public final static int TYPE_NAMEVALUE = 1;

    /**
     *
     * @param key
     * @param extKey
     */
    public void startEvent(Class clz, long key, String extKey, int state);

    /**
     *
     */
    public void endEvent();

    /**
     *
     * @return
     */
    public int getType();

    /**
     *
     * @param propertyName
     */
    public void startProperty(String propertyName, int index, boolean isSet);

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
    * @param l
    */
   public void writeEntityRefProperty(long l);

   /**
   *
   * @param ref
   */
   public void writeEntityRefProperty(ConceptOrReference ref);

   /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d);
    /**
     *
     * @param time
     */
    public void writeDateTimePropertyDate(long time);

    /**
     *
     * @param tz
     */
    public void writeDateTimePropertyTimeZone(String tz);

    /**
     *
     * @param cal
     */
    public void writeDateTimePropertyCalendar(Calendar cal);
    /**
     *
     * @param payload
     */
    public void writePayload(Object payload);

    public interface PropertyDescription {
        public int previousIndex();
        public int currentIndex();
        public PropertyDrainer getDeletedProperty();
    }

    public interface EventMigrator {
        public PropertyDescription[] getPropertiesMap();
    }

    public interface PropertyDrainer {
        void drain(EventDeserializer deserializer);
    }
}

