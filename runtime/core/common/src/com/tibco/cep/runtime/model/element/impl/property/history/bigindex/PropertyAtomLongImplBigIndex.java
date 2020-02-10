package com.tibco.cep.runtime.model.element.impl.property.history.bigindex;

import com.tibco.cep.runtime.model.element.impl.property.BigIndexHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomLongImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 25, 2006
 * Time: 3:17:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomLongImplBigIndex extends PropertyAtomLongImpl {
    //this plus the status byte adds up to 32 bits so the object won't waste space with padding
    byte id_14_7;
    short id_30_15;

    public PropertyAtomLongImplBigIndex(int historySize, Object owner) {
        super(historySize, owner);
    }

    public PropertyAtomLongImplBigIndex(int historySize, Object owner, long defaultValue) {
        super(historySize, owner, defaultValue);
    }

    @Override
    public PropertyAtomLongImplBigIndex copy(Object newOwner) {
        return _copy(new PropertyAtomLongImplBigIndex(0, newOwner));
    }

    protected PropertyAtomLongImplBigIndex _copy(PropertyAtomLongImplBigIndex ret) {
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