package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.Date;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;

class SampleValuesGenerator {

	private String[][] strings = new String[][] { { "Suspended", "Normal" }, { "Suspended", "Normal" }, { "Suspended", "Normal" }, { "Suspended", "Normal" }, { "Suspended", "Normal" } };

	private int[] intlongs = new int[] { 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

	private double[] doubles = new double[] { 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 10000000.0, 100000000.0 };

	SampleValuesGenerator() {

	}

	public FieldValue valueFor(DataType dataType, int fieldIndex) {
		if (BuiltInTypes.STRING.equals(dataType)) {
			return new FieldValue(dataType, strValue(fieldIndex));
		} else if (BuiltInTypes.BOOLEAN.equals(dataType)) {
			return new FieldValue(dataType, boolValue());
		} else if (BuiltInTypes.DATETIME.equals(dataType)) {
			return new FieldValue(dataType, dtValue(fieldIndex));
		} else if (BuiltInTypes.DOUBLE.equals(dataType)) {
			return new FieldValue(dataType, dblValue(fieldIndex));
		} else if (BuiltInTypes.FLOAT.equals(dataType)) {
			return new FieldValue(dataType, (float) dblValue(fieldIndex));
		} else if (BuiltInTypes.INTEGER.equals(dataType)) {
			return new FieldValue(dataType, intValue(fieldIndex));
		} else if (BuiltInTypes.LONG.equals(dataType)) {
			return new FieldValue(dataType, (long) intValue(fieldIndex));
		} else if (BuiltInTypes.SHORT.equals(dataType)) {
			return new FieldValue(dataType, (short) intValue(fieldIndex));
		}
		return null;
	}

	private Comparable<?> dtValue(int fieldIndex) {
		Date date = new Date();
		return date;
	}

	private boolean boolValue() {
		int option = (int) (Math.random() * 2);
		return (option == 1);
	}

	int intValue(int fieldIndex) {
		int r = (fieldIndex % intlongs.length);
		return (int) (Math.random() * intlongs[r]);
	}

	String strValue(int fieldIndex) {
		int r = (fieldIndex % strings.length);
		int c = (int) (Math.random() * strings[r].length);
		return strings[r][c];
	}

	double dblValue(int fieldIndex) {
		int r = (fieldIndex % doubles.length);
		return Math.random() * doubles[r];
	}

}