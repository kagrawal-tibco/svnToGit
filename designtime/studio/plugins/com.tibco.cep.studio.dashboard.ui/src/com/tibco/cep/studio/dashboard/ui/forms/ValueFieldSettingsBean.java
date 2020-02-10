package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.util.DisplayFormatParser;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormatProvider;

public class ValueFieldSettingsBean {

	private String fieldName;

	private String dataType;

	private List<DisplayValueFormat> displayValueFormats;

	private DisplayValueFormat displayValueFormat;

	private String pattern;

	public ValueFieldSettingsBean(String fieldName, String dataType) {
		this.fieldName = fieldName;
		this.dataType = dataType;
		this.displayValueFormats = DisplayValueFormatProvider.getDisplayValueFormats(dataType);
	}

	public List<DisplayValueFormat> getDisplayValueFormats() {
		return displayValueFormats;
	}

	public DisplayValueFormat getDefaultDisplayValueFormat(){
		return DisplayValueFormatProvider.getDefaultDisplayValueFormat(dataType);
	}

	public String getDisplayLabelFormat(DisplayValueFormat displayValueFormat){
		if (displayValueFormat.isPattern() == true){
			return displayValueFormat.getDisplayValueFormat(fieldName, pattern);
		}
		return displayValueFormat.getDisplayValueFormat(fieldName, "");
	}

	public void setDisplayValueFormat(DisplayValueFormat displayValueFormat) {
		this.displayValueFormat = displayValueFormat;
	}

	public DisplayValueFormat getDisplayValueFormat(){
		return displayValueFormat;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isUsingPattern(){
		if (displayValueFormat == null){
			return false;
		}
		return displayValueFormat.isPattern();
	}

	public void update(String displayLabelFormat) {
		this.displayValueFormat = DisplayValueFormatProvider.parse(fieldName, dataType, displayLabelFormat);
		if (this.displayValueFormat != null) {
			Collection<String> arguments = new DisplayFormatParser(displayLabelFormat).getArguments();
			if (arguments.size() == 1 && arguments.contains(fieldName) == true) {
				this.pattern = this.displayValueFormat.getPattern(displayLabelFormat);
				if (this.pattern == null){
					this.pattern = "";
				}
			}
			else {
				this.pattern = displayLabelFormat;
			}
		}
		else {
			this.pattern = "";
		}
	}

	public boolean haveSameDisplayValueFormats(ValueFieldSettingsBean valueFieldSettings){
		if (valueFieldSettings == null){
			return false;
		}
		return this.getDisplayValueFormats().equals(valueFieldSettings.getDisplayValueFormats());
	}

}
