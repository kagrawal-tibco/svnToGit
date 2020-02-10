package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDAtomicSimpleTypeDefinition;

/**
 * A static factory for initializing the bridge types between the java
 * primitives and the XSD primitives.
 * 
 * Because the SynXSDAtomicSimpleType's are used/re-used often a static map is
 * used to share instances of SynXSDAtomicSimpleType for efficiency
 */
public class SynAtomicTypeFactory {

    private static Map<String, SynXSDAtomicSimpleTypeDefinition> typeMap = new HashMap<String, SynXSDAtomicSimpleTypeDefinition>();

    public static final SynXSDAtomicSimpleTypeDefinition getAtomicType(Class<?> javaTypeClass) {

        if (false == typeMap.containsKey(javaTypeClass.getName())) {
            typeMap.put(javaTypeClass.getName(), new SynXSDAtomicSimpleTypeDefinition(javaTypeClass));
        }
        return (SynXSDAtomicSimpleTypeDefinition) typeMap.get(javaTypeClass.getName());

    }

}
