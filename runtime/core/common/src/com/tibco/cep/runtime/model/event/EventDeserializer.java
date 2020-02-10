package com.tibco.cep.runtime.model.event;

import java.util.Calendar;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 19, 2006
 * Time: 8:08:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EventDeserializer {
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


    /**
     *
     * @param key
     * @param extKey
     */

    public boolean hasSchemaChanged();

    /**
     *
     * @return
     */
    public EventSerializer.EventMigrator getEventMigrator();
    /**
     *
     * @return
     */
    public long startEvent();

    /**
     *
     * @return
     */
    public long getId();

    /**
     *
     * @return
     */
    public String getExtId();

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
    public boolean startProperty(String propertyName, int index);



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
    public ConceptOrReference getEntityRefProperty();
    
    /**
     *
     * @return
     */
    public long getEntityRefPropertyAsLong();
       
    /**
     *
     * @return
     */
    public double getDoubleProperty();

    /**
     *
     * @return
     */
    public Calendar getDateTimeProperty();

    /**
     *
     * @return
     */
    public Object getPayload(ExpandedName eventType);
}


