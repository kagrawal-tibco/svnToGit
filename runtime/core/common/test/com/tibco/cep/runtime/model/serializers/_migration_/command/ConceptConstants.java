package com.tibco.cep.runtime.model.serializers._migration_.command;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionKey;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:41:43 PM
*/
public interface ConceptConstants {
    ConversionKey<Long> KEY_ID = new ConversionKey<Long>("id", Long.class);

    ConversionKey<String> KEY_EXT_ID = new ConversionKey<String>("extId", String.class);

    ConversionKey<Integer> KEY_TYPE_ID = new ConversionKey<Integer>("typeId", Integer.class);

    ConversionKey<Integer> KEY_VERSION = new ConversionKey<Integer>("version", Integer.class);

    ConversionKey<Boolean> KEY_IS_DELETED = new ConversionKey<Boolean>("deleted", Boolean.class);

    ConversionKey<ConceptOrReference> KEY_PARENT_ID =
            new ConversionKey<ConceptOrReference>("parentId", ConceptOrReference.class);
}
