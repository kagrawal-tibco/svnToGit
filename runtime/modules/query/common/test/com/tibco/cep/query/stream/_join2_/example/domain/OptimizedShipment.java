package com.tibco.cep.query.stream._join2_.example.domain;

import java.util.Date;

/*
* Author: Ashwin Jayaprakash Date: Jun 4, 2009 Time: 5:18:44 PM
*/
public class OptimizedShipment {
    protected Number id;

    protected Date deliveryDate;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":: " + getId() + ", " + getDeliveryDate();
    }
}
