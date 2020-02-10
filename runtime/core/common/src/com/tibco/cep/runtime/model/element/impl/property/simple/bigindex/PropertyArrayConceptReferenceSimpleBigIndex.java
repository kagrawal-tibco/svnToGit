package com.tibco.cep.runtime.model.element.impl.property.simple.bigindex;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.BigIndexHelper;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:01:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayConceptReferenceSimpleBigIndex extends PropertyArrayConceptReferenceSimple {
    //this plus the status byte adds up to 32 bits so the object won't waste space with padding
    byte id_14_7;
    short id_30_15;

    public PropertyArrayConceptReferenceSimpleBigIndex(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        PropertyArrayConceptReferenceSimpleBigIndex ret = (PropertyArrayConceptReferenceSimpleBigIndex)_copy(new PropertyArrayConceptReferenceSimpleBigIndex((ConceptImpl)newOwner, 0));;
        ret.id_14_7 = id_14_7;
        ret.id_30_15 = id_30_15;
        return ret;
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