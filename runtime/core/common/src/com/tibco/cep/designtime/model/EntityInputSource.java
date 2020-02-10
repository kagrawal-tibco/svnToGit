package com.tibco.cep.designtime.model;


import org.xml.sax.InputSource;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 11:43:24 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class wraps an Entity and provides as an input Source
 * No Need to do double deserializing of Entity - esp. when one wants to deserialize and add to
 * TnsCache.
 * Also @see com.tibco.cep.repo.provider.OntologyResourceProvider
 */
public class EntityInputSource extends InputSource {


    Entity e;


    public EntityInputSource(Entity e) {
        super();
        this.e = e;
    }


    public Entity getEntity() {
        return e;
    }
}
