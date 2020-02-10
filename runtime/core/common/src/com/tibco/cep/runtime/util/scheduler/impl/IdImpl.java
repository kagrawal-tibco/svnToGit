package com.tibco.cep.runtime.util.scheduler.impl;

import com.tibco.cep.runtime.util.scheduler.Id;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class IdImpl implements Id {

    private final long id;
    
    public IdImpl(long id) {
        this.id = id;
    }

    public long getValue() {
        return this.id;
    }

    public int compareTo(Id obj) {
        return (int)(this.id - obj.getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdImpl other = (IdImpl) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
}
