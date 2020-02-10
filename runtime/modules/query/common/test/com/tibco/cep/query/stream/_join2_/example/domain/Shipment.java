package com.tibco.cep.query.stream._join2_.example.domain;

import java.util.Date;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 6:55:03 PM
*/
public class Shipment {
    protected Number id;

    protected String customerLastName;

    protected String customerFirstName;

    protected double amount;

    protected Date deliveryDate;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":: " + getId() + ", " + getCustomerFirstName() + ", " +
                getCustomerLastName() + ", " + getAmount() + ", " + getDeliveryDate();
    }
}
