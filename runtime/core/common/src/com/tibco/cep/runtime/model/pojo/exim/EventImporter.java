package com.tibco.cep.runtime.model.pojo.exim;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.runtime.model.serializers.PortablePojoEventDeserializer;
import com.tibco.cep.runtime.session.impl.HotDeployListener;

/*
* Author: Ashwin Jayaprakash / Date: 12/6/11 / Time: 3:23 PM
*/
public class EventImporter extends EximParent implements HotDeployListener {
    protected final TypeManager typeManager;

    protected final HashMap<Object, Constructor<? extends SimpleEventImpl>> typesAndSimpleEventCtors;
    protected final HashMap<Object, Constructor<? extends TimeEventImpl>> typesAndCtorsForTimeEvents; // for TimeEvents

    public EventImporter(TypeManager typeManager) throws Exception {
        this.typeManager = typeManager;

        Collection simpleEvents = typeManager.getTypeDescriptors(TypeManager.TYPE_SIMPLEEVENT);
        Collection timeEvents = typeManager.getTypeDescriptors(TypeManager.TYPE_TIMEEVENT);
        typesAndSimpleEventCtors = new HashMap<Object, Constructor<? extends SimpleEventImpl>>(simpleEvents.size());
        typesAndCtorsForTimeEvents = new HashMap<Object, Constructor<? extends TimeEventImpl>>(timeEvents.size());
        _initTypesAndCtors(simpleEvents, timeEvents);
    }
    
    public void initTypesAndCtors() throws SecurityException, NoSuchMethodException {
    	Collection simpleEvents = typeManager.getTypeDescriptors(TypeManager.TYPE_SIMPLEEVENT);
        Collection timeEvents = typeManager.getTypeDescriptors(TypeManager.TYPE_TIMEEVENT);
    	_initTypesAndCtors(simpleEvents, timeEvents);
    }
    protected void _initTypesAndCtors(Collection simpleEvents, Collection timeEvents) throws SecurityException, NoSuchMethodException {
        for (Object o : simpleEvents) {
        	initTypesAndCtorsLoopBody(o, typesAndSimpleEventCtors);
        }

        for (Object o : timeEvents) {
        	initTypesAndCtorsLoopBody(o, typesAndCtorsForTimeEvents);
        }
    }
	
    protected void initTypesAndCtorsLoopBody(Object o, HashMap m) throws SecurityException, NoSuchMethodException {
    	TypeDescriptor td = (TypeDescriptor) o;
        Class implKlass = td.getImplClass();

        if (isClusterMode()) {
        	int typeId = typeManager.getTypeDescriptor(implKlass).getTypeId();
        	if (!m.containsKey(typeId)) {
        		Constructor ctor = implKlass.getConstructor(Long.TYPE, String.class);
        		m.put(typeId, ctor);
        	}
        } else {
        	if (!m.containsKey(td.getURI())) {
        		Constructor ctor = implKlass.getConstructor(Long.TYPE, String.class);
        		m.put(td.getURI(), ctor);
        	}
        }
    }
   
    
    public void entitiesAdded() throws SecurityException, NoSuchMethodException {
        initTypesAndCtors();
    }
    
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}

//    public EntityImpl importEvent(long id) {
//        PortablePojo source = (PortablePojo) eximHelper.getPojoManager().findPojoByIdForUpdate(id);
//        if (source == null) {
//            return null;
//        }
//
//        return importEvent(source);
//    }
//
//    public EntityImpl importEvent(String extId) {
//        PortablePojo source = (PortablePojo) eximHelper.getPojoManager().findPojoByExtIdForUpdate(extId);
//        if (source == null) {
//            return null;
//        }
//
//        return importEvent(source);
//    }
    
    public EntityImpl importEvent(PortablePojo source) {
        SimpleEventImpl destination = null;
        TimeEventImpl destinationTimeEvent = null;
        try {
        	int typeId = source.getTypeId();
        	
            Constructor<? extends SimpleEventImpl> ctor = null;
            if (isClusterMode()) {
            	ctor = typesAndSimpleEventCtors.get(typeId);
            } else {
            	ctor = typesAndSimpleEventCtors.get(source.getEntity().getFullPath());
            }
            
            if (ctor != null) {
                //This is a simpleventimpl
                destination = ctor.newInstance(source.getId(), source.getExtId());

                boolean recovered = (Boolean) source.getPropertyValue(PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED, FieldType.BOOLEAN.toString());
                destination.setRecovered(recovered);
                destination.deserialize(new PortablePojoEventDeserializer(source, typeManager));
            } else {
                //this is for TimeEventImpl
                Constructor<? extends TimeEventImpl> ctorTimeEvent = null;
                
                if (isClusterMode()) {
                	ctorTimeEvent = typesAndCtorsForTimeEvents.get(typeId);
                } else {
                	ctor = typesAndSimpleEventCtors.get(source.getEntity().getFullPath());
                }
                destinationTimeEvent = ctorTimeEvent.newInstance(source.getId(), source.getExtId());
                destinationTimeEvent.deserialize(new PortablePojoEventDeserializer(source, typeManager));

                //these attributes have different names in TimeEvent.deserialize then they do here so the following code
                //can't be moved there
               
                destinationTimeEvent.setClosure((String)source.getPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_CLOSURE, FieldType.STRING.toString()));
                Long scheduledTime = (Long)source.getPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_NEXT, FieldType.LONG.toString());
                if (scheduledTime!=null) {
                    destinationTimeEvent.setScheduledTime(scheduledTime);
                }
                Long ttl = (Long) source.getPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_TTL, FieldType.LONG.toString());
                if (ttl!=null) {
                    destinationTimeEvent.setTTL(ttl);
                }
                Boolean fired = (Boolean) source.getPropertyValue(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_FIRED, FieldType.BOOLEAN.toString());
                if (fired!=null) {
                    destinationTimeEvent.setFired(fired);
                }
                return destinationTimeEvent;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return destination;
    }
}
