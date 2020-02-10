package com.tibco.rta.model.impl;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.TimeUnits;

/**
 * @author bgokhale
 *         <p/>
 *         Create various static methods for validations.
 */

public class ModelValidations {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    public static void validateDataType(Attribute attribute, Object value) throws DataTypeMismatchException {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Attribute [%s] being validated [%s]", attribute, value);
        }
        DataType dataType = attribute.getDataType();
        if (!validateDataType(dataType, value)) {
            throw new DataTypeMismatchException(attribute, value);
        }
    }

    public static boolean validateDataType(DataType dataType, Object value) throws DataTypeMismatchException {

        switch (dataType) {
            case BOOLEAN:
                if (value instanceof Boolean) {
                    return true;
                }
            case INTEGER:
                if (value instanceof Integer) {
                    return true;
                }
                break;
            case LONG:
                if (value instanceof Long) {
                    return true;
                }
                break;
            case DOUBLE:
                if (value instanceof Double) {
                    return true;
                }
                break;
            case STRING:
                if (value instanceof String) {
                    return true;
                }
                break;
            case SHORT:
                if (value instanceof Short) {
                    return true;
                }
                break;
            case BYTE:
                if (value instanceof Byte) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    public static void validateDailyPurgeTime(String hhmi) throws IllegalArgumentException {
        if (hhmi != null && hhmi.length() > 4) {
            throw new IllegalArgumentException(String.format("Format expected upto 4 characters: [hours][mins], got [%s]", hhmi));
        }
        try {
            Integer.parseInt(hhmi);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Format expected integeral: [hours][mins], got [%s]", hhmi));
        }
    }

    public static void validateRetentionTimeUnit(TimeUnits.Unit timeunit) throws IllegalArgumentException {
        switch (timeunit) {
            case SECOND:
            case MINUTE:
            case HOUR:
            case DAY:
            case WEEK: {
                return;
            }
            default: {
                throw new IllegalArgumentException(String.format("Expected one in SECOND, MINUTE, HOUR, DAY and WEEK are allowed. Found [%s]", timeunit));
            }
        }
    }
}
