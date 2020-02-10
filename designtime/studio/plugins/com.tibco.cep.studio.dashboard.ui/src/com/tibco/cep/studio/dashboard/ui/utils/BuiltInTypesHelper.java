package com.tibco.cep.studio.dashboard.ui.utils;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;

public class BuiltInTypesHelper {
	
	private static String[] primitiveTypeNames = new String[] {
		PROPERTY_TYPES.STRING.getLiteral(),
		PROPERTY_TYPES.INTEGER.getLiteral(),
		PROPERTY_TYPES.LONG.getLiteral(),
		PROPERTY_TYPES.DOUBLE.getLiteral(),
		PROPERTY_TYPES.BOOLEAN.getLiteral(),
		PROPERTY_TYPES.DATE_TIME.getLiteral()
	};
	

	public static final String[] getPrimitiveTypeNames() {
		return primitiveTypeNames;
	}
	
    public static Integer getFilteredTypeIndex(String name) throws Exception {
        for (int i = 0; i < primitiveTypeNames.length; i++) {
            if (name.equals(primitiveTypeNames[i])) {
                return new Integer(i);
            }
        }
        return new Integer(0);
    }

}
