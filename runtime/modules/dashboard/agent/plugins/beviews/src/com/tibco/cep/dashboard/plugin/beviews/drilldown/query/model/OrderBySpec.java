package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

public class OrderBySpec implements java.io.Serializable {

    private static final long serialVersionUID = 2889987615128222686L;

    private String mOrderByField;
    private boolean mAscending;

    public OrderBySpec(String fieldName, boolean ascending) {
        mOrderByField = fieldName;
        mAscending = ascending;
    }

    public String getOrderByField() {
        return this.mOrderByField;
    }

    public void setOrderByField(String aOrderByField) {
        this.mOrderByField = aOrderByField;
    }

    public boolean getAscending() {
        return this.mAscending;
    }

    public void setAscending(boolean aAscending) {
        this.mAscending = aAscending;
    }

    @Override
    public String toString() {
        return mOrderByField + (mAscending ? " ASC" : " DESC");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new OrderBySpec(this.mOrderByField, this.mAscending);
    }

}