package com.tibco.cep.mapper.xml.xdata;

import com.tibco.xml.schema.SmException;
import com.tibco.xml.schema.SmSchemaError;

public final class DefaultSchemaErrorHandler implements SmSchemaError.Handler {

    private boolean mThrowErrors = true;

    public DefaultSchemaErrorHandler() {
    }

    public DefaultSchemaErrorHandler(boolean throwErrors) {
        mThrowErrors = throwErrors;
    }

    public void error(SmSchemaError error) throws SmException {
        if (mThrowErrors) {
            throw new SmException("Error: " + error.toString());
        } else {
            System.err.println("Schema Parse Error: " + error);
        }
    }

    public void warning(SmSchemaError error) throws SmException {
        if (mThrowErrors) {
            throw new SmException("Warning: " + error.toString());
        } else {
            System.err.println("Schema Parse Warning: " + error);
        }
    }

	/**
	 * @return true iff constraint-checking for this schema should be performed
	 */
	public boolean isConstraintChecking() {
		return false;
	}

}
