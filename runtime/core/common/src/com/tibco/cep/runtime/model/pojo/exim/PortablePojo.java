package com.tibco.cep.runtime.model.pojo.exim;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;

/*
* Author: Ashwin Jayaprakash / Date: 12/2/11 / Time: 3:23 PM
*/

public abstract class PortablePojo {
//    public static final String PROPERTY_NAME_KEY = "id";
//
//    public static final String PROPERTY_NAME_ID = PROPERTY_NAME_KEY;
//
//    public static final String PROPERTY_NAME_EXT_ID = "extId";
//
//    public static final String PROPERTY_NAME_TYPE_ID = "typeId__";
//
//    public static final String PROPERTY_NAME_VERSION = "version__";
//
//    public static final String PROPERTY_CONCEPT_NAME_PARENT = "parent__";
//
//    public static final String PROPERTY_CONCEPT_NAME_REV_REFERENCES = "rrf__";
//
//    // The TimeEvent constants
//    public static final String PROPERTY_NAME_TIME_EVENT_CLOSURE = "closure";
//    public static final String PROPERTY_NAME_TIME_EVENT_NEXT = "next";
//    public static final String PROPERTY_NAME_TIME_EVENT_TTL = "ttl";
//    public static final String PROPERTY_NAME_TIME_EVENT_FIRED = "fired";

    /**
     * {@value}
     */
    public static final int PROPERTY_VALUE_VERSION_DEFAULT = -1;

    protected long id;

    protected String extId;

    protected int typeId;
    
    //
    protected Entity entity;
    protected Object item;
  
    protected PortablePojo(long id, String extId, int typeId) {
        this.id = id;
        this.extId = extId;
        this.typeId = typeId;
    }

    public long getId() {
        return id;
    }
    
    public String getExtId() {
        return extId;
    }

    public int getTypeId() {
        return typeId;
    }

    /**
     * Internally, uses {@link #setPropertyValue(String, boolean)} and {@link #PROPERTY_NAME_VERSION}.
     *
     * @param version
     */
    public final void setVersion(int version) {
        setPropertyValue(PortablePojoConstants.PROPERTY_NAME_VERSION, version, FieldType.INTEGER.toString());
    }

    /**
     * @return
     * @see #setVersion(int)
     */
    public final int getVersion() {
        return (Integer) getPropertyValue(PortablePojoConstants.PROPERTY_NAME_VERSION, FieldType.INTEGER.toString());
    }
    
    /**
     * 
     * @param entityType
     */
    public void setEntityType(String entityType) {
    	if (entityType.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
			entityType = entityType.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
		}
    	RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		Ontology ontology = rsp.getProject().getOntology();
		 
		this.entity = ontology.getEntity(entityType);
	}

    /**
     * 
     * @param item
     */
	public void setItem(Object item) {
		this.item = item;
	}
	
	public Object getItem() {
		return item;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Entity getEntity() {
		 return entity;
	 }

    //--------------
	
	public abstract Object getPropertyValue(String name);

	public abstract Object getPropertyValue(String name, String type);

//    public abstract void setPropertyValue(String name, boolean value);
//
//    public abstract void setPropertyValue(String name, int value);
//
//    public abstract void setPropertyValue(String name, long value);
//
//    public abstract void setPropertyValue(String name, double value);

	public abstract void setPropertyValue(String name, Object value);
	
    public abstract void setPropertyValue(String name, Object value, String type);
    
    //--------------

//    public abstract Object getArrayPropertyValue(String name, int position);

//    public abstract Object[] getArrayPropertyValues(String name);
    public abstract Object[] getArrayPropertyValues(String name, String type);

//    public abstract void setArrayPropertyValue(String name, int totalLength, boolean value, int position);
//
//    public abstract void setArrayPropertyValue(String name, int totalLength, int value, int position);
//
//    public abstract void setArrayPropertyValue(String name, int totalLength, long value, int position);
//
//    public abstract void setArrayPropertyValue(String name, int totalLength, double value, int position);
//
//    public abstract void setArrayPropertyValue(String name, int totalLength, Object value, int position);
    
    //--------------

//    public abstract Object getHistoryPropertyValue(String name, int position);

//    public abstract Object[] getHistoryPropertyValues(String name);
    public abstract Object[] getHistoryPropertyValues(String name, String type);

//    public abstract void setHistoryPropertyValue(String name, int totalLength, boolean value, long time, int position);
//
//    public abstract void setHistoryPropertyValue(String name, int totalLength, int value, long time, int position);
//
//    public abstract void setHistoryPropertyValue(String name, int totalLength, long value, long time, int position);
//
//    public abstract void setHistoryPropertyValue(String name, int totalLength, double value, long time, int position);
//
//    public abstract void setHistoryPropertyValue(String name, int totalLength, Object value, long time, int position);
    
    //--------------

//    public abstract Object getArrayHistoryPropertyValue(String name, int position);

//    public abstract Object[] getArrayHistoryPropertyValues(String name);
    public abstract Object[] getArrayHistoryPropertyValues(String name, String type);

//    public abstract void setArrayHistoryPropertyValue(String name, int totalLength, boolean[] value, long[] time, int position);
//
//    public abstract void setArrayHistoryPropertyValue(String name, int totalLength, int[] value, long[] time, int position);
//
//    public abstract void setArrayHistoryPropertyValue(String name, int totalLength, long[] value, long[] time, int position);
//
//    public abstract void setArrayHistoryPropertyValue(String name, int totalLength, double[] value, long[] time, int position);
//
//    public abstract void setArrayHistoryPropertyValue(String name, int totalLength, Object[] value, long[] time, int position);
}
