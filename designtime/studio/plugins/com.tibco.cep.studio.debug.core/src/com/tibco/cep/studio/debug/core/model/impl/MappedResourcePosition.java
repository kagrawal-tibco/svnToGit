package com.tibco.cep.studio.debug.core.model.impl;

/*
@author ssailapp
@date Jul 21, 2009
*/

public class MappedResourcePosition extends ResourcePosition implements IMappedResourcePosition {
    public static final int BE_POSITION = 1;
    public static final int JAVA_POSITION = 2;


    public static final int FIRST_LINE = 1;
    public static final int MIDDLE_LINE = 2;
    public static final int LAST_LINE = 4;

    private int lineMask;
    private int positionType;
    private MappedResourcePosition crossMappedPosition;

    public MappedResourcePosition(int lineNumber, String resourceName, int positionType) {
        super(lineNumber, resourceName);
        setPositionType(positionType);
    }

    @Override
	public int getLineMask() {
        return lineMask;
    }

    public void setLineMask(int mask) {
        this.lineMask = mask;
    }

    @Override
	public int getPositionType() {
        return this.positionType;
    }

    public void setPositionType(int positionType) {
        this.positionType = positionType;
    }

    public void setCrossMappedPosition(MappedResourcePosition mappedPosition) {
        this.crossMappedPosition = mappedPosition;
    }

    @Override
	public IMappedResourcePosition getCrossMappedPosition() {
        return crossMappedPosition;
    }
}
