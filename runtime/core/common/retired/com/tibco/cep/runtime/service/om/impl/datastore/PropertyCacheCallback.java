package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.model.element.Property;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 25, 2004
 * Time: 7:17:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyCacheCallback implements CacheCallBack {

    private PropertyManager propman;

    PropertyCacheCallback(PropertyManager propman) {
        this.propman = propman;
    }

    public boolean EntryFallOff(Object key, Object value) {
        Property prop = (Property) value;
        PropertyKey propkey = (PropertyKey) key;

        /* If the last property is dirty we will not let it fall off till
           the next time checkpointer is run and this gets saved.
        */
        /*TODO if(prop.isDirty()) {
            return propman.dirtyFallOff(propkey, prop);
        }
*/
        // No need for explicitly calling evict now.
        //( (AbstractConceptImpl) prop.getSubject() ).evictProperty(propkey.fieldId());
        return true;
    }
}
