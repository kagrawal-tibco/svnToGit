package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PERIOD_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PURGE_FREQUENCY_PERIOD;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_PURGE_TIME_DAY;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_UNIT_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RETENTION_POLICY;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.model.TimeUnits.Unit;
import com.tibco.rta.model.mutable.MutableRetentionPolicy;

@XmlRootElement(name=ELEM_RETENTION_POLICY)
@XmlAccessorType(XmlAccessType.NONE)
public class RetentionPolicyImpl implements MutableRetentionPolicy {
	
	
	protected Qualifier qualifier;
	
	protected long multiplier;
	
	protected TimeUnits.Unit timeUnit;
	
	protected long purgeFrequencyPeriod;
	
	protected String purgeTimeOfDay;
	
	protected String hierarchyName;
	
	protected RetentionPolicyImpl() {
		
	}
	
	public RetentionPolicyImpl(Qualifier qualifier, TimeUnits.Unit timeUnit, long multiplier, String purgeTimeOfDay,long purgeFrequencyPeriod) {
		ModelValidations.validateDailyPurgeTime(purgeTimeOfDay);
		ModelValidations.validateRetentionTimeUnit(timeUnit);
		this.qualifier = qualifier;
		this.timeUnit = timeUnit;
		this.multiplier = multiplier;
		this.purgeTimeOfDay = purgeTimeOfDay;
		this.purgeFrequencyPeriod = purgeFrequencyPeriod;
	}


	public void setPurgeFrequencyPeriod(long purgeFrequencyPeriod) {
		this.purgeFrequencyPeriod = purgeFrequencyPeriod;
	}

	
	@Override
	public Qualifier getQualifier() {
		return qualifier;
	}	
	
	@Override
	public void setQualifier(Qualifier qualifier) {
		this.qualifier = qualifier;
	}

	@Override
	public void setRetentionUnitCount(long unitCount) {
		this.multiplier = unitCount;
	}

	@Override
	public void setRetentionUnit(Unit timeUnit) {
		ModelValidations.validateRetentionTimeUnit(timeUnit);
		this.timeUnit = timeUnit;
	}

	@Override
	public void setPurgeTimeOfDay(String purgeTimeOfDay) {
		this.purgeTimeOfDay = purgeTimeOfDay;
	}


	@Override
	public long getRetentionPeriod() {
		return TimeUnitsImpl.getTimePeriod(timeUnit, multiplier);
	}

	@Override
    public String getHierarchyName() {
		return hierarchyName;
    }

	@Override
    public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;	    
    }
	
	@XmlAttribute(name=ATTR_PURGE_FREQUENCY_PERIOD)
	public long getPurgeFrequencyPeriod() {
		return purgeFrequencyPeriod;
	}

	@XmlAttribute(name=ATTR_PURGE_TIME_DAY)
	@Override
	public String getPurgeTimeOfDay() {
		return purgeTimeOfDay;
	}

	@XmlAttribute(name=ATTR_UNIT_NAME)
	@Override
	public Unit getRetentionUnit() {
		return timeUnit;
	}

	@XmlAttribute(name=ATTR_PERIOD_NAME)
	@Override
	public long getRetentionUnitCount() {
		return multiplier;
	}

	@XmlAttribute(name=ATTR_TYPE_NAME)
	private String getType() {
		if (qualifier.equals(Qualifier.HIERARCHY)) {
			return hierarchyName;
		}
		return qualifier.name();
	}
	
	private void setType(String type) {
		if ("FACT".equalsIgnoreCase(type)) {
			qualifier = Qualifier.FACT;
		} else {
			qualifier = Qualifier.HIERARCHY;
			setHierarchyName(type);			
		}
	}

	

}
