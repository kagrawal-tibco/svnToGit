/*
 * Copyright(c) 2004-2013.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.jdbcstore.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.backingstore.EntityDescription;

public class SimpleEventDescription implements EntityDescription {

    protected final static Logger logger = LogManagerFactory.getLogManager().getLogger(SimpleEventDescription.class);
    protected ArrayList _properties = new ArrayList();
    protected SimpleEventImpl _TEMPLATE;
    protected Class _eventClass;

    public SimpleEventDescription(String eventClassName, Class eventClass) throws Exception {
        this._eventClass = eventClass;
        initialize();
    }

    protected void initialize() throws Exception{
        try {
            _TEMPLATE = (SimpleEventImpl) _eventClass.newInstance();
            String[] propertyNames= _TEMPLATE.getPropertyNames();
            int[] propertyTypes= _TEMPLATE.getPropertyTypes();

            for (int i=0; i < propertyNames.length;i++) {
                String propertyName= propertyNames[i];
                int propertyType= propertyTypes[i];

                EventProperty p = new EventProperty ();
                p.propertyName = propertyName;
                p.type= propertyType;
                p.index=i;
                _properties.add(p);
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public String getImplClass() {
        return this._eventClass.getName();
    }

    public List getProperties() {
        return _properties;
    }

    public class EventProperty {
        public int index;
        public String propertyName;
        public int type;

        /**
         *
         * @param other
         * @return
         */
        public boolean sameAs(EventProperty other) {
            return (propertyName.equalsIgnoreCase(other.propertyName)) &&
                   (type == other.type);
        }
    }

    class PropertyDescriptionImpl implements EventSerializer.PropertyDescription {
        int prev_index;
        int cur_index;
        EventSerializer.PropertyDrainer deletedProperty;

        protected PropertyDescriptionImpl(int prev_index, int cur_index) {
            this.prev_index=prev_index;
            this.cur_index=cur_index;
        }

        protected PropertyDescriptionImpl(int prev_index, EventSerializer.PropertyDrainer deletedProperty) {
            this.prev_index=prev_index;
            this.cur_index=-1;
            this.deletedProperty=deletedProperty;
        }

        public int previousIndex() {
            return prev_index;
        }

        public int currentIndex() {
            return cur_index;
        }
        public EventSerializer.PropertyDrainer getDeletedProperty() {
            return deletedProperty;
        }
    }

    class IntegerPropertyDrainer implements EventSerializer.PropertyDrainer {
        EventProperty deletedProperty;
        public IntegerPropertyDrainer(EventProperty property) {
            this.deletedProperty=property;
        }
        public void drain(EventDeserializer deserializer) {
        	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
	            deserializer.getIntProperty();
        	}
            deserializer.endProperty();
        }
    }

    class StringPropertyDrainer implements EventSerializer.PropertyDrainer {
        EventProperty deletedProperty;
        public StringPropertyDrainer(EventProperty property) {
            this.deletedProperty=property;
        }
        public void drain(EventDeserializer deserializer) {
            if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
	            deserializer.getStringProperty();
            }
            deserializer.endProperty();
        }
    }

    class DateTimePropertyDrainer implements EventSerializer.PropertyDrainer {
        EventProperty deletedProperty;
        public DateTimePropertyDrainer(EventProperty property) {
            this.deletedProperty=property;
        }
        public void drain(EventDeserializer deserializer) {
            if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
	            deserializer.getDateTimeProperty();
            }
            deserializer.endProperty();
        }
    }

    class DoublePropertyDrainer implements EventSerializer.PropertyDrainer {
        EventProperty deletedProperty;
        public DoublePropertyDrainer(EventProperty property) {
            this.deletedProperty=property;
        }
        public void drain(EventDeserializer deserializer) {
        	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
        		deserializer.getDoubleProperty();
        	}
            deserializer.endProperty();
        }
    }

    class LongPropertyDrainer implements EventSerializer.PropertyDrainer {
        EventProperty deletedProperty;
        public LongPropertyDrainer(EventProperty property) {
            this.deletedProperty=property;
        }
        public void drain(EventDeserializer deserializer) {
        	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
        		deserializer.getLongProperty();
        	}
            deserializer.endProperty();
        }
    }

    class BooleanPropertyDrainer implements EventSerializer.PropertyDrainer {
        EventProperty deletedProperty;
        public BooleanPropertyDrainer(EventProperty property) {
            this.deletedProperty=property;
        }
        public void drain(EventDeserializer deserializer) {
        	if (deserializer.startProperty(deletedProperty.propertyName, deletedProperty.index)) {
        		deserializer.getBooleanProperty();
        	}
            deserializer.endProperty();
        }
    }
}
