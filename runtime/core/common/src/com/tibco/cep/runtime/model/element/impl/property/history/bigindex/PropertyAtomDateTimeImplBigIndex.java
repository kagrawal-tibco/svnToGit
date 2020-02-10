package com.tibco.cep.runtime.model.element.impl.property.history.bigindex;

import java.util.Calendar;

import com.tibco.cep.runtime.model.element.impl.property.BigIndexHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomDateTimeImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 25, 2006
 * Time: 3:17:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDateTimeImplBigIndex extends PropertyAtomDateTimeImpl {
    //this plus the status byte adds up to 32 bits so the object won't waste space with padding
    byte id_14_7;
    short id_30_15;

    public PropertyAtomDateTimeImplBigIndex(int historySize, Object owner) {
        super(historySize, owner);
    }

    public PropertyAtomDateTimeImplBigIndex(int historySize, Object owner, Calendar defaultValue) {
        super(historySize, owner, defaultValue);
    }

    @Override
    public PropertyAtomDateTimeImplBigIndex copy(Object newOwner) {
        return _copy(new PropertyAtomDateTimeImplBigIndex(0, newOwner));
    }

    protected PropertyAtomDateTimeImplBigIndex _copy(PropertyAtomDateTimeImplBigIndex ret) {
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