package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHYS_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_HIERARCHY_NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.mutable.MutableCube;
import com.tibco.rta.model.mutable.MutableDimensionHierarchy;

/**
 * Default implementation of the {@link Cube}
 */
@XmlAccessorType(XmlAccessType.NONE)
public class CubeImpl extends MetadataElementImpl implements MutableCube {

	private static final long serialVersionUID = -2491780241786808593L;

	protected Map<String, MutableDimensionHierarchy> dimensionHierarchys = new LinkedHashMap<String, MutableDimensionHierarchy>();
    
    protected CubeImpl() {
		super();
	}
    
	public CubeImpl(String name) {
		super(name);
	}

	public CubeImpl(String name, RtaSchema rtaSchema) {
		super(name, rtaSchema);
	}

	@Override
	public <T extends DimensionHierarchy> T getDimensionHierarchy(String name) {
		return (T) dimensionHierarchys.get(name);
	}
	
	@XmlElementWrapper(name=ELEM_HIERARCHYS_NAME)
	@XmlElement(name=ELEM_HIERARCHY_NAME, type=DimensionHierarchyImpl.class)
	@Override
	public <T extends DimensionHierarchy> Collection<T> getDimensionHierarchies() {		
		return new ArrayList(dimensionHierarchys.values());
	}
	

	
	@Override
	synchronized public MutableDimensionHierarchy newDimensionHierarchy(String name) throws DuplicateSchemaElementException {
		if (null == name) {
			throw new NullPointerException("Dimension Hierarchy name is not specified while defining the cube");
		}
		MutableDimensionHierarchy dimHr = dimensionHierarchys.get(name);
        // Check if it is already present in the map
        if (null != dimHr) {
			throw new DuplicateSchemaElementException(String.format("DimensionHierarchy %s already defined", name));
        }
		dimHr = new DimensionHierarchyImpl(name, this);
		dimensionHierarchys.put(name, dimHr);
		return dimHr;
	}

	@Override
	public MutableDimensionHierarchy removeDimensionHierarchy(String name) {
		//TODO: Check reference count before removing from the hierarchy
		return dimensionHierarchys.remove(name);
	}

}