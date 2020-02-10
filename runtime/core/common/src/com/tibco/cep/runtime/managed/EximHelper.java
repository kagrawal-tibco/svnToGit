package com.tibco.cep.runtime.managed;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.model.pojo.exim.ConceptExporter;
import com.tibco.cep.runtime.model.pojo.exim.ConceptImporter;
import com.tibco.cep.runtime.model.pojo.exim.EventExporter;
import com.tibco.cep.runtime.model.pojo.exim.EventImporter;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager;

/*
* Author: Ashwin Jayaprakash / Date: 1/3/12 / Time: 1:45 PM
*/

/**
 * A helper class to export/import concepts and events from/to {@link PortablePojo}s as the intermediate type and finally to/from
 * {@link T}.
 * <p/>
 * The intermediate format is the final exported format - if {@link #transform(Object)} and {@link #transform(PortablePojo,
 * Object)} are not overridden.
 *
 * @param <T>
 */
public abstract class EximHelper<T> {
    protected static EximKit eximKit = null;

    protected static TypeManager typeManager;

//    protected static EximHelper reference;
    
    protected EximHelper(TypeManager tm) {
        if (typeManager == null) {
            typeManager = tm;
        }
    }

    protected static EximKit getKit() throws Exception {
        if (eximKit == null) {
            ConceptExporter conceptExporter = new ConceptExporter(typeManager);
            ConceptImporter conceptImporter = new ConceptImporter(typeManager);
            EventExporter eventExporter = new EventExporter(typeManager);
            EventImporter eventImporter = new EventImporter(typeManager);

            eximKit = new EximKit(conceptExporter, conceptImporter, eventExporter, eventImporter);
        }

        return eximKit;
    }
    
    public static void entitiesAdded() throws Exception {
    	EximKit eximKit = getKit();
    	if (eximKit != null) {
    		eximKit.conceptImporter.entitiesAdded();
    		eximKit.eventImporter.entitiesAdded();
    	}
    }

    public final Entity translate(T source) {
        try {
            PortablePojo pp = transform(source);

            EximKit kit = getKit();
//            try {
                if (pp.getVersion() == PortablePojo.PROPERTY_VALUE_VERSION_DEFAULT) {
                    return kit.eventImporter.importEvent(pp);
                }
                else if (pp.getVersion() > PortablePojo.PROPERTY_VALUE_VERSION_DEFAULT) {
                    return kit.conceptImporter.importConcept(pp);
                }
                else {
                    throw new IllegalArgumentException(
                            "The type [" + source.getClass().getName() + "] of this entity" +
                                    " [" + source + "] is unknown to this importer");
                }
//            }
//            finally {
//                getPojoManager().reset();
//            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected PortablePojo transform(T t) {
        return (PortablePojo) t;
    }

    public final T translate(Entity source, T destination) {
        try {
            EximKit kit = getKit();
            
            PortablePojo pp = createPojo(source);
            pp.setItem(destination);
            pp.setEntityType(source.getType());
            
//            try {
                if (source instanceof ConceptImpl) {
                    kit.conceptExporter.exportConcept((ConceptImpl) source, pp);
                }
                else if (source instanceof SimpleEventImpl) {
                    kit.eventExporter.exportEvent((SimpleEventImpl) source, pp);
                }
                else if (source instanceof TimeEventImpl) {
                    kit.eventExporter.exportTimeEvent((TimeEventImpl) source, pp);
                }
                else {
                    throw new IllegalArgumentException(
                            "The type [" + source.getClass().getName() + "] of this entity" +
                                    " [" + source + "] is unknown to this exporter");
                }
//            }
//            finally {
//            	getPojoManager().reset();
//            }

            return transform(pp, destination);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private PortablePojo createPojo(Entity source) {
    	long id = source.getId();
        String extId = source.getExtId();
        int typeId = typeManager.getTypeDescriptor(source.getClass()).getTypeId();
        
        return getPojoManager().createPojo(id, extId, typeId);
    }

    protected T transform(PortablePojo intermediate, T destination) {
        return (T) intermediate;
    }
    
    public abstract PortablePojoManager getPojoManager();

    //--------------

    public static interface PortablePojoManagerCreator {
        PortablePojoManager create();
    }

    static class EximKit {
        final ConceptExporter conceptExporter;

        final ConceptImporter conceptImporter;

        final EventExporter eventExporter;

        final EventImporter eventImporter;

        EximKit(ConceptExporter conceptExporter, ConceptImporter conceptImporter,
                EventExporter eventExporter, EventImporter eventImporter) {
            this.conceptExporter = conceptExporter;
            this.conceptImporter = conceptImporter;
            this.eventExporter = eventExporter;
            this.eventImporter = eventImporter;
        }
    }
}
