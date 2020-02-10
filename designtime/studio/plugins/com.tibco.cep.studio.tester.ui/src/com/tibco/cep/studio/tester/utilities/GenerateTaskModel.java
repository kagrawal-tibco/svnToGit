package com.tibco.cep.studio.tester.utilities;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class GenerateTaskModel {

	public enum GenerationType {
	
		RANDOM("Random"),
		INCREMENTAL("Incremental"),
		ENUMERATE("Enumerate"),
		CONSTANT("Constant"),
		PREFIXED("Prefixed");
		
		private String dispName;

		GenerationType(String name) {
			this.dispName = name;
		}
		
		public String getDispName() {
			return dispName;
		}
		
		
	}
	
	public class GenerationOptions {
		
		private boolean enabled = true;
		private Entity property;
		private String min = ""; // the minimum value for ints, the minimum length for strings, or the minimum date/time for DateTime fields
		private String max = ""; // the max value for ints, the max length for strings, or the maximum date/time for DateTime fields
		private String prefix = ""; // the (optional) prefix for strings, can be used for a constant value
		private String incrementBy = "1"; // increment each value by this amount
		private int enumCtr = 0;
		private GenerationType generationType = GenerationType.RANDOM;
		private String incrementUnit;
		private boolean allowNull = false;
		private double nullProbability = 0;
		
		public GenerationOptions(Entity prop) {
			this.property = prop;
			if (((PropertyDefinition)prop).getDomainInstances().size() > 0) {
				this.generationType = GenerationType.ENUMERATE;
			}
		}
		
		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public boolean isAllowNull() {
			return allowNull;
		}

		public void setAllowNull(boolean allowNull) {
			this.allowNull = allowNull;
		}

		public double getNullProbability() {
			return nullProbability;
		}

		public void setNullProbability(double nullProbability) {
			this.nullProbability = nullProbability;
		}

		public int getEnumCtr() {
			return enumCtr;
		}

		public void setEnumCtr(int enumCtr) {
			this.enumCtr = enumCtr;
		}

		public String getIncrementBy() {
			return incrementBy;
		}
		public void setIncrementBy(String incrementBy) {
			this.incrementBy = incrementBy;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public Entity getProperty() {
			return property;
		}
		public void setProperty(Entity property) {
			this.property = property;
		}
		public String getMin() {
			return min;
		}
		public void setMin(String min) {
			this.min = min;
		}
		public String getMax() {
			return max;
		}
		public void setMax(String max) {
			this.max = max;
		}
		public void setGenerationType(GenerationType type) {
			this.generationType = type;
		}
		public GenerationType getGenerationType() {
			return generationType;
		}
		public void setIncrementUnit(String unit) {
			this.incrementUnit = unit;
		}
		public String getIncrementUnit() {
			return incrementUnit;
		}
	}

	private int numRows; // the number of rows to generate
	private Map<PropertyDefinition, GenerationOptions> options;
	
	public GenerateTaskModel() {
		this.options = new HashMap<>();
		this.numRows = 5;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public Map<PropertyDefinition, GenerationOptions> getOptions() {
		return options;
	}

	public void setOptions(Map<PropertyDefinition, GenerationOptions> options) {
		this.options = options;
	}
	
	
}
