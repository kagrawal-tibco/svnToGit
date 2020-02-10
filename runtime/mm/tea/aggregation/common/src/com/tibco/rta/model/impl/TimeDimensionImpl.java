package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DIMENSION_TYPE;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TIME_DIMENSION_FREQUENCY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TIME_DIMENSION_QTR_OFFSET;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TIME_DIMENSION_UNIT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.model.TimeUnits.Unit;

/**
 * Default implementation of {@link TimeDimension}
 */
@XmlAccessorType(XmlAccessType.NONE)
public class TimeDimensionImpl extends DimensionImpl implements TimeDimension {

	private static final long serialVersionUID = -266821987844152834L;
	
    protected TimeUnits timeUnits;
    
    
    private int frequency;
        
    private String unitName;
    
	public TimeDimensionImpl(Attribute associatedAttribute) {
		this(null, associatedAttribute);
	}

	public TimeDimensionImpl(String name, Attribute associatedAttribute) {
		this(name, associatedAttribute, Unit.SECOND, -1);
	}
	
	public TimeDimensionImpl(String name, Attribute associatedAttribute, Unit unit, int frequency) {
		super(name, associatedAttribute);
		this.timeUnits = new TimeUnitsImpl(unit, frequency);
		this.frequency = timeUnits.getMultiplier();
		unitName = timeUnits.getTimeUnit().toString();
	}
	
	public TimeDimensionImpl() {
		
	}


	@Override
	public TimeUnits getTimeUnit() {
		return timeUnits;
	}

    /**
     * Return the frequency for this time dimension.
     *
     * @return
     */
	
	
    @Override
    public int getMultiplier() {
        return timeUnits.getMultiplier();
    }
    
	
	public int getFrequency() {
		return frequency;
	}
	
	
	public String getUnitName() {
		return unitName;
	}

	@XmlAttribute(name = ATTR_TIME_DIMENSION_UNIT)
	private void setUnitName(String name) {
		unitName = name;
	}

	@XmlAttribute(name = ATTR_TIME_DIMENSION_FREQUENCY)
	private void setFrequency(int count) {
		frequency = count;
	}
	
//    @XmlAttribute(name=ATTR_DIMENSION_TYPE)
    private String getType() {
    	return "time";
    }
        
    @XmlAttribute(name=ATTR_TIME_DIMENSION_QTR_OFFSET)
	private Integer getQTROffset() {
		if (timeUnits.getTimeUnit().toString().equalsIgnoreCase(TimeUnits.Unit.QUARTER.name())) {
			return timeUnits.getFirstQtrStartMonth();
		}
		return null;
	}
    
}