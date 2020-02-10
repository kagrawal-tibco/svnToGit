package com.tibco.cep.runtime.model.serializers.as;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.data.ASTuple;
import com.tibco.cep.as.kit.map.KeyMultiValueTupleAdaptor;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.managed.EximHelper;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoManager;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;

/*
* Author: Ashwin Jayaprakash / Date: 5/15/12 / Time: 11:23 AM
*/

/**
 * Another layer...another sub-class. We don't want to add any BE dependencies in the "as.kit" package.
 */
public class AbstractTupleAdaptor extends KeyMultiValueTupleAdaptor<Long, Entity> {
    protected Field asHashMapEntries;

    protected static AsEximHelper eximHelper;

    protected AbstractTupleAdaptor(String keyColumnName, Class<Long> keyClass, String[] fieldNames,
                                   DataType[] dataTypes, TupleCodec tupleCodec,
                                   TypeManager tm, MetadataCache mdc, int entityTypeId) throws Exception {
        super(keyColumnName, keyClass, fieldNames, dataTypes, tupleCodec);
        
        if (eximHelper == null) {
	        this.eximHelper = new AsEximHelper(tm);
        }

        this.asHashMapEntries = ASTuple.class.getSuperclass().getDeclaredField("entries");
        this.asHashMapEntries.setAccessible(true);
    }

    @Override
    public final Entity extractValue(Tuple tuple) {
        return eximHelper.translate(tuple);
    }

    @Override
    public final Tuple setValue(Tuple tuple, Entity value) {
        return eximHelper.translate(value, tuple);
    }

    //------------

    protected class AsEximHelper extends EximHelper<Tuple> {
    	private AsPortablePojoManager portablePojoManager;
        
    	public AsEximHelper(TypeManager tm) throws Exception {
            super(tm);
            
//            reference = this;
            portablePojoManager = new AsPortablePojoManager();
        }

        @Override
        protected PortablePojo transform(Tuple tuple) {
            try {
                HashMap asInternalMap = (HashMap) asHashMapEntries.get(tuple);

                return AsPortablePojo.toPojo(asInternalMap);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected Tuple transform(PortablePojo intermediate, Tuple destination) {
            try {
                asHashMapEntries.set(destination, ((AsPortablePojo) intermediate).toAsFormat());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            return destination;
        }
        
        @Override
        public PortablePojoManager getPojoManager() {
        	return portablePojoManager;
        }
    }
}
