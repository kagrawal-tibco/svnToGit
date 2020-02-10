package com.tibco.cep.studio.dashboard.core.util;

import java.util.Collection;

public class DisplayValueFormat {

	public static enum FORMAT_TYPE {
		NUMBER, DATE, TIME
	};

	public static enum FORMAT_STYLE {
		SHORT, MEDIUM, LONG, FULL, INTEGER, CURRENCY, PERCENT, PATTERN
	};

	private String label;

	private FORMAT_TYPE formatType;

	private FORMAT_STYLE formatStyle;

	public DisplayValueFormat(String label, FORMAT_TYPE formatType, FORMAT_STYLE formatStyle) {
		super();
		this.label = label;
		this.formatType = formatType;
		this.formatStyle = formatStyle;
	}

	public final String getLabel() {
		return label;
	}

	public final FORMAT_TYPE getFormatType() {
		return formatType;
	}

	public final FORMAT_STYLE getFormatStyle() {
		return formatStyle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisplayValueFormat other = (DisplayValueFormat) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	public String getDisplayValueFormat(String fieldName, String pattern) {
		if (isPattern() == true) {
			if (pattern == null) {
				throw new IllegalArgumentException("No pattern specified for " + formatStyle);
			}
			//parse the pattern
			DisplayFormatParser parser = new DisplayFormatParser(pattern);
			// is pattern complex like Foo{Bar} or contains more then one argument e.g. {foo} is {bar}
			if (parser.isSimple() == false) {
				// yes, it is, return it as it is
				return pattern;
			}
			//if format type is null and we are a pattern then return the pattern (string & boolean)
			if (formatType == null) {
				// yes, it is, return it as it is
				return pattern;
			}
			//if format type is not null and we are a pattern then return the pattern if the argument(s) in it does not match field (numeric & datetime)
			//by this point we know for sure that there is only one argument in the pattern
			Collection<String> arguments = parser.getArguments();
			if (arguments.isEmpty() == false && arguments.contains(fieldName) == false) {
				//the pattern does not use the field name, return the pattern as it is
				return pattern;
			}
		}
		// we are dealing with standard pattern
		StringBuilder sb = new StringBuilder("{");
		sb.append(fieldName);
		if (formatType != null) {
			sb.append(",");
			sb.append(formatType.toString().toLowerCase());
			if (formatStyle != null) {
				sb.append(",");
				if (FORMAT_STYLE.PATTERN.compareTo(formatStyle) == 0) {
					sb.append(pattern);
				} else {
					sb.append(formatStyle.toString().toLowerCase());
				}
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public String getPattern(String displayValueFormat) {
		if (formatStyle == null) {
			return "";
		}
		if (FORMAT_STYLE.PATTERN.compareTo(formatStyle) != 0) {
			return "";
		}
		if (displayValueFormat == null) {
			return "";
		}
		// is pattern custom like Foo{Bar}
		DisplayFormatParser displayFormatParser = new DisplayFormatParser(displayValueFormat);
		if (displayFormatParser.isSimple() == false) {
			// yes, it is, return it as it is
			return displayValueFormat;
		}
		String[] argumentDetails = displayFormatParser.getArgumentDetails();
		if (argumentDetails.length == 3) {
			return argumentDetails[2];
		}
		return "";
	}

	public boolean isPattern() {
		if (formatStyle == null) {
			return false;
		}
		return formatStyle.compareTo(FORMAT_STYLE.PATTERN) == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());
		sb.append("[label=");
		sb.append(label);
		sb.append(",style=");
		sb.append(formatStyle);
		sb.append(",type=");
		sb.append(formatType);
		sb.append("]");
		return sb.toString();
	}

}
