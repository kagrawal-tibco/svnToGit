package com.tibco.cep.pattern.integ.master;

import java.io.Serializable;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Dec 21, 2009 / Time: 11:56:56 AM
*/
public class MessageId implements Serializable {
    protected long id;

    @Optional
    protected String extId;

    public MessageId(long id, @Optional String extId) {
        this.id = id;
        this.extId = extId;
    }

    public MessageId(long id) {
        this(id, null);
    }

    public long getId() {
        return id;
    }

    public String getExtId() {
        return extId;
    }

    //-------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageId)) {
            return false;
        }

        MessageId messageId = (MessageId) o;

        if (id != messageId.id) {
            return false;
        }
        if (extId != null ? !extId.equals(messageId.extId) : messageId.extId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (extId != null ? extId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", extId='" + extId + "}";
    }
}
