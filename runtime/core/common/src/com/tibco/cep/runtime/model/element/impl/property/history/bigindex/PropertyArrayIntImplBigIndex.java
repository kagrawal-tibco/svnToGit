package com.tibco.cep.runtime.model.element.impl.property.history.bigindex;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.BigIndexHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayIntImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:53:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayIntImplBigIndex extends PropertyArrayIntImpl {
    //this plus the status byte adds up to 32 bits so the object won't waste space with padding
    byte id_14_7;
    short id_30_15;

    public PropertyArrayIntImplBigIndex(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArrayImpl copy(Object owner) {
        PropertyArrayIntImplBigIndex copy = (PropertyArrayIntImplBigIndex)_copy(new PropertyArrayIntImplBigIndex ((ConceptImpl)owner, 0));
        copy.id_14_7 = id_14_7;
        copy.id_30_15 = id_30_15;
        return copy;
    }

    @Override
    public int getPropertyIndex() {
        return BigIndexHelper.makePropIdx(id_30_15, id_14_7, (byte) super.getPropertyIndex());
    }

    @Override
    public void setPropertyIndex(int propertyIndex) {
        id_30_15 = BigIndexHelper.make_30_15(propertyIndex);
        id_14_7 = BigIndexHelper.make_14_7(propertyIndex);
        super.setPropertyIndex(BigIndexHelper.make_6_0(propertyIndex));
    }
}