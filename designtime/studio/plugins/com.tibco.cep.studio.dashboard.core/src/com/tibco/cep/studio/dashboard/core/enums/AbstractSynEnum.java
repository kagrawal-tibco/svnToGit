package com.tibco.cep.studio.dashboard.core.enums;


public abstract class AbstractSynEnum implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9055015415194755184L;

	private static AbstractSynEnum[] Enums = null;

    private String mValue;

    protected AbstractSynEnum(String value) {
        this.mValue = value;
    }

    public boolean equals(Object b) {
        return this.mValue.equals(((AbstractSynEnum) b).mValue);
    }

    public String toString() {
        return this.mValue;
    }

    public static AbstractSynEnum fromString(String value) {

        for (int i = 0; i < getEnums().length; i++) {
            if (value.equals(getEnums()[i].toString())) return getEnums()[i];
        }
        throw new java.lang.IllegalArgumentException();
    }

    public static final AbstractSynEnum[] getEnums() {
        if (null == Enums) { throw new IllegalStateException("The Enums array can not be null.  It might not have been initialized properly."); }
        return Enums;
    }

    public static final void setEnums(AbstractSynEnum[] enums) {

        if (null == enums) { throw new IllegalArgumentException("The Enums array can not be null.  It might not have been initialized properly."); }
        Enums = enums;
    }
}