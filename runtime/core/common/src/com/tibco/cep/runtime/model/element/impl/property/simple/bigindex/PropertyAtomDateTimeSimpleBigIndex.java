package com.tibco.cep.runtime.model.element.impl.property.simple.bigindex;

import java.util.Calendar;

import com.tibco.cep.runtime.model.element.impl.property.BigIndexHelper;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:29:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDateTimeSimpleBigIndex extends PropertyAtomDateTimeSimple {
    //this plus the status byte adds up to 32 bits so the object won't waste space with padding
    byte id_14_7;
    short id_30_15;

    public PropertyAtomDateTimeSimpleBigIndex(Object owner) {
        super(owner);
    }

    public PropertyAtomDateTimeSimpleBigIndex(Object owner, Calendar defaultValue) {
        super(owner, defaultValue);
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyAtomDateTimeSimple(newOwner));
    }

    protected PropertyAtomDateTimeSimpleBigIndex _copy(PropertyAtomDateTimeSimpleBigIndex ret) {
        super._copy(ret);
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