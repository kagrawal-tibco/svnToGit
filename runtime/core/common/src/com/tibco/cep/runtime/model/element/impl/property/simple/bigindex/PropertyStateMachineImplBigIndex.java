package com.tibco.cep.runtime.model.element.impl.property.simple.bigindex;

import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.impl.property.BigIndexHelper;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:29:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyStateMachineImplBigIndex extends PropertyStateMachineImpl {
    //this plus the status byte adds up to 32 bits so the object won't waste space with padding
    byte id_14_7;
    short id_30_15;

    public PropertyStateMachineImplBigIndex(Object owner) {
        super(owner);
    }

    public PropertyStateMachineImplBigIndex(Object owner, ContainedConcept defaultValue) {
        super(owner, defaultValue);
    }

    @Override
    public PropertyAtomSimple copy(Object newOwner) {
        return _copy(new PropertyStateMachineImplBigIndex(newOwner));
    }


    protected PropertyStateMachineImplBigIndex _copy(PropertyStateMachineImplBigIndex ret) {
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