package com.tibco.rta.model.rule.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_FUNCTION_PARAM_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.impl.FunctionDescriptorImpl;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;

public class ActionFunctionDescriptorImpl extends FunctionDescriptorImpl implements ActionFunctionDescriptor {

	private static final long serialVersionUID = -3329522401378788908L;

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_ACTIONS.getCategory());
	// protected String category;
	protected DataType dataType;

	protected List<FunctionParamValue> actionParamValues = new ArrayList<FunctionParamValue>();

	public ActionFunctionDescriptorImpl(String name, String category, String implClassName, DataType dataType, String desc) {
		super(name, implClassName, category);
		this.description = desc;
		this.dataType = dataType;
		// this.category = category;
	}
	
	protected ActionFunctionDescriptorImpl() {

	}

	@XmlElement(name=ELEM_FUNCTION_PARAM_VALUE, type=FunctionParamValueImpl.class)
	@Override
	@JsonDeserialize(as=ArrayList.class, contentAs=FunctionParamValueImpl.class)
	public List<FunctionParamValue> getFunctionParamValues() {
		return actionParamValues;
	}

	@Override
	public void addFunctionParamValue(FunctionParamValue value) {
		for (FunctionParamValue functionParamValue: actionParamValues) {
			if (functionParamValue.getName().equals(value.getName())) {
				try {
					functionParamValue.setValue(value.getValue());
				} catch (DataTypeMismatchException e) {
					LOGGER.log(Level.ERROR, "", e);
				}
				return;
			}
		}
		this.actionParamValues.add(value);
	}
}
