package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.cluster.system.EntityIdMask;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA. User: nleong Date: Jun 18, 2006 Time: 2:46:32 AM To change this
 * template use File | Settings | File Templates.
 */
abstract public class EntityImpl implements Entity {

    long id;
    public String extId;
    transient boolean loadedFromCache=false;

    static public int hashCode(long _value) {
        return (int) (_value ^ (_value >>> 32));
    }

    protected EntityImpl() {
        extId = null;
    }

    public EntityImpl(long _id) {
        extId = null;
        id = _id;
    }

    protected EntityImpl(long id, String _extId) {
        if ((_extId != null) && (_extId.length() > 0)) {
            extId = _extId;
        } else {
            extId=null;
        }
        this.id = id;
    }

    protected EntityImpl(String _extId) {
        if ((_extId != null) && (_extId.length() > 0)) {
            extId = _extId;
        } else {
            extId=null;
        }
    }

    public long getId() {
        return id;
    }

    public String getExtId() {
        return extId;
    }

    protected void setExtId(String extId) {
        if ((extId != null) && (extId.length() > 0)) {
            this.extId = extId;
        } else {
            this.extId = null;
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        if (EntityIdMask.isMasked(id)) {
            if (extId == null) {
                return getClass().getName() + "@site=" + EntityIdMask.getMaskedId(id) + "@id=" +
                        EntityIdMask.getEntityId(id);
            }
            else {
                return getClass().getName() + "@site=" + EntityIdMask.getMaskedId(id) + "@id=" +
                        EntityIdMask.getEntityId(id) + "@extId=" + extId;
            }

        }
        else {

            if (extId == null) {
                return getClass().getName() + "@id=" + id;
            }
            else {
                return getClass().getName() + "@id=" + id + "@extId=" + extId;
            }
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof EntityImpl) {
            return id == ((EntityImpl) obj).id;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return hashCode(id);
    }

    protected void baseXiNode(XiNode node) {
        node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_ID), String.valueOf(id));
        if (extId != null) {
            node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_EXTID), extId);
        }
    }

    public void setLoadedFromCache() {
        loadedFromCache=true;
    }

    public boolean isLoadedFromCache() {
        return loadedFromCache;
    }

}
