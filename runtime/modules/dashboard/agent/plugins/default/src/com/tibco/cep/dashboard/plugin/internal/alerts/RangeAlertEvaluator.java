package com.tibco.cep.dashboard.plugin.internal.alerts;

import java.util.Date;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.alerts.AbstractAlertEvaluator;
import com.tibco.cep.dashboard.psvr.alerts.AlertActionable;
import com.tibco.cep.dashboard.psvr.alerts.EvalException;
import com.tibco.cep.dashboard.psvr.alerts.VisualAlertActionable;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALAlert;
import com.tibco.cep.dashboard.psvr.mal.model.MALRangeAlert;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualAlertAction;
import com.tibco.cep.kernel.service.logging.Level;

public class RangeAlertEvaluator extends AbstractAlertEvaluator {

	private boolean enabled;

	private boolean invalidInputs;

	private double upperThreshold;

	private double lowerThreshold;

	private AlertActionable[] actionables;

	public RangeAlertEvaluator() {
		enabled = false;
		upperThreshold = Double.NaN;
		lowerThreshold = Double.NaN;
	}

	@Override
	protected void setAlert(MALAlert alert) {
		super.setAlert(alert);

		MALRangeAlert rangeAlert = (MALRangeAlert) alert;

		enabled = rangeAlert.getEnabled();

		if (rangeAlert.hasUpperValue() == true) {
			upperThreshold = rangeAlert.getUpperValue();
		}

		if (rangeAlert.hasLowerValue() == true) {
			lowerThreshold = rangeAlert.getLowerValue();
		}

		invalidInputs = (upperThreshold == Double.NaN && lowerThreshold == Double.NaN);
		if (invalidInputs == true){
			MessageGeneratorArgs args = new MessageGeneratorArgs(null,URIHelper.getURI(rangeAlert));
			logger.log(Level.WARN, messageGenerator.getMessage("range.alert.empty.definition", args));
		}
		invalidInputs = (upperThreshold == lowerThreshold);
		if (invalidInputs == true){
			MessageGeneratorArgs args = new MessageGeneratorArgs(null,URIHelper.getURI(rangeAlert),Double.toString(upperThreshold));
			logger.log(Level.WARN, messageGenerator.getMessage("range.alert.ineffective.definition", args));
		}
		else {
			actionables = new AlertActionable[rangeAlert.getActionCount()];
			for (int i = 0; i < rangeAlert.getAction().length; i++) {
				actionables[i] = new VisualAlertActionable((MALVisualAlertAction) rangeAlert.getAction()[i]);
			}
		}
	}

	@Override
	public AlertActionable[] evaluate(MALFieldMetaInfo field,Tuple tuple) throws EvalException {
		if (enabled == false || invalidInputs == true){
			return null;
		}
		FieldValue fieldValue = tuple.getFieldValueByName(field.getName());
		if (fieldValue.isNull() == true){
			return null;
		}
		double value = Double.NaN;
		if (field.isNumeric() == true){
			value = ((Number)fieldValue.getValue()).doubleValue();
		}
		else if (field.isDate() == true){
			value = ((Date)fieldValue.getValue()).getTime();
		}
		else {
			MessageGeneratorArgs args = new MessageGeneratorArgs(null,URIHelper.getURI(alert),field.getName(),field.getDataType());
			logger.log(Level.WARN, messageGenerator.getMessage("range.alert.nonnumeric.comparision", args));
		}
		if (value != Double.NaN){
			boolean success = false;
			if (Double.isNaN(lowerThreshold) == false && Double.isNaN(upperThreshold) == false){
				success = value >= lowerThreshold && value < upperThreshold;
			}
			else if (Double.isNaN(lowerThreshold) == true){
				success = value < upperThreshold;
			}
			else {
				success = value >= lowerThreshold;
			}
			if (success == true){
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, alert.getName() + "[" + lowerThreshold + "," + upperThreshold + "] evaluated as true using " + field.getName() + "[" + value + "] against [" + tuple + "]");
				}
				return actionables;
			}
			else {
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, alert.getName() + "[" + lowerThreshold + "," + upperThreshold + "] evaluated as false using " + field.getName() + "[" + value + "] against [" + tuple + "]");
				}
				return null;
			}
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, alert.getName() + "[" + lowerThreshold + "," + upperThreshold + "] evaluated as null using " + field.getName() + "[" + value + "] against [" + tuple + "]");
		}
		return null;
	}

	@Override
	public void shutdown() throws NonFatalException {
		actionables = null;
	}





}
