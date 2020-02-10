package com.tibco.cep.studio.dashboard.core.enums;

public class InternalStatusEnum extends AbstractSynEnum {

    /**
     * 
     */
    private static final long serialVersionUID = -6990738761730464143L;

	/**
     * The element is newly created and has no association with an underlying
     * data object yet
     */
    public static final InternalStatusEnum StatusNew = new InternalStatusEnum("New");

    /**
     * The element has been modified since last syncrhonization with the
     * underlying data object
     */
    public static final InternalStatusEnum StatusModified = new InternalStatusEnum("Modified");

    /**
     * The element was instantiated with an underlying data object
     */
    public static final InternalStatusEnum StatusExisting = new InternalStatusEnum("Existing");

    /**
     * The element is marked for removal to be used for filtering and to signify
     * the associated element should be removed from the associated data object
     * at the next synchronization
     */
    public static final InternalStatusEnum StatusRemove = new InternalStatusEnum("Remove");

    protected InternalStatusEnum(String value) {
        super(value);
        setEnums(new InternalStatusEnum[] { StatusNew, StatusModified, StatusExisting, StatusRemove});
    }

}