package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.UUID;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 * 
 */
class TupleHolder {

	Logger logger;
	String loggingIdentifier;

	String id;
	Tuple tuple;

	boolean updated;
	long lastUpdateTime;

	boolean reset;
	long lastResetTime;
	Tuple resetPriorTuple;

	TupleHolder(Logger logger, String loggingIdentifier, Tuple tuple) {
		this.logger = logger;
		this.loggingIdentifier = loggingIdentifier;
		setTuple(tuple);
	}

	private void setTuple(Tuple tuple) {
		try {
			this.tuple = (Tuple) tuple.clone();
			this.id = String.valueOf(tuple.getId());
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("could not clone tuple[id=" + tuple.getId() + "]", e);
		}
	}

	void updateTuple(Tuple tuple) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Updating " + this.tuple + " with " + tuple + " in " + loggingIdentifier);
		}
		// we update the tuple
		setTuple(tuple);
		updated = true;
		reset = false;
		// remove the resetPriorTuple
		resetPriorTuple = null;
		lastUpdateTime = System.currentTimeMillis();
	}

	void resetTuple(Tuple tuple) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Resetting " + this.tuple + " to " + tuple + " in " + loggingIdentifier);
		}
		// we update the tuple
		setTuple(tuple);
		// we do not keep the previous tuple since it is getting replaced
		reset = true;
		updated = false;
		lastResetTime = System.currentTimeMillis();
	}

	void resetTuple(MALFieldMetaInfo[] resettableFields) {
		// store the existing tuple as the prior record
		resetPriorTuple = tuple;
		// force the cloning of the tuple again
		setTuple(tuple);
		// we reset the tuple's resettable fields
		String tupleToString = null;
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			tupleToString = tuple.toString();
		}
		for (int i = 0; i < resettableFields.length; i++) {
			tuple.setFieldNullByName(resettableFields[i].getName());
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Resetting " + tupleToString + " to " + tuple + " in " + loggingIdentifier);
		}
		reset = true;
		updated = false;
		this.id = "dbagent::" + UUID.randomUUID().toString();
		lastResetTime = System.currentTimeMillis();
	}

	boolean matchesTuple(Tuple tuple, MALFieldMetaInfo[] matchingGroupByFields) {
		if (matchingGroupByFields == null || matchingGroupByFields.length == 0) {
			return false;
		}
		for (int i = 0; i < matchingGroupByFields.length; i++) {
			MALFieldMetaInfo field = matchingGroupByFields[i];
			FieldValue thisTupleFldValue = this.tuple.getFieldValueByName(field.getName());
			FieldValue otherTupleFldValue = tuple.getFieldValueByName(field.getName());
			if (thisTupleFldValue.compareTo(otherTupleFldValue) != 0) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("com.tibco.cep.dashboard.core.data.TupleHolder[");
		buffer.append("id=" + id);
		buffer.append(",tuple=" + tuple);
		buffer.append(",updated=" + updated);
		buffer.append(",lastupdatetime=" + lastUpdateTime);
		buffer.append(",reset=" + reset);
		buffer.append(",lastresettime=" + lastResetTime);
		buffer.append(",hashcode=" + hashCode());
		buffer.append("]");
		return buffer.toString();
	}
}