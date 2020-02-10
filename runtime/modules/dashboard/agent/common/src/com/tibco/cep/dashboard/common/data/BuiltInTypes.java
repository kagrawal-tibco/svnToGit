package com.tibco.cep.dashboard.common.data;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.config.GlobalConfiguration;

public final class BuiltInTypes {

	public static final DataType BOOLEAN = new DataType("boolean","false"){

		private static final long serialVersionUID = -8091952271835723336L;

		@Override
		public String toString(Object o) {
			return Boolean.toString((Boolean) o);
		}

		@Override
		public Comparable<?> valueOf(String s) throws IllegalArgumentException {
			return Boolean.valueOf(s);
		}

	};

	public static final DataType DOUBLE = new DataType("double","0.0"){

		private static final long serialVersionUID = -6131587809572466520L;

		@Override
		public String toString(Object o) {
			return Double.toString((Double) o);
		}

		@Override
		public Comparable<?> valueOf(String s) {
			if (s == null) {
				throw new IllegalArgumentException("NULL argument");
			}
			try {
				return Double.valueOf(s);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(s+" is not a "+getDataTypeID());
			}
		}

	};

	public static final DataType FLOAT = new DataType("float","0.0"){

		private static final long serialVersionUID = 7164292539450927L;

		@Override
		public String toString(Object o) {
			return Float.toString((Float) o);
		}

		@Override
		public Comparable<?> valueOf(String s) {
			if (s == null) {
				throw new IllegalArgumentException("NULL argument");
			}
			try {
				return Float.valueOf(s);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(s+" is not a "+getDataTypeID());
			}
		}

	};

	public static final DataType INTEGER = new DataType("int","0"){

		private static final long serialVersionUID = -1195858708069465005L;

		@Override
		public String toString(Object o) {
			return Integer.toString((Integer) o);
		}

		@Override
		public Comparable<?> valueOf(String s) {
			if (s == null) {
				throw new IllegalArgumentException("NULL argument");
			}
			try {
				return Integer.valueOf(s);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(s+" is not a "+getDataTypeID());
			}
		}

	};

	public static final DataType LONG = new DataType("long","0"){

		private static final long serialVersionUID = 7793309237468023230L;

		@Override
		public String toString(Object o) {
			return Long.toString((Long) o);
		}

		@Override
		public Comparable<?> valueOf(String s) {
			if (s == null) {
				throw new IllegalArgumentException("NULL argument");
			}
			try {
				return Long.valueOf(s);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(s+" is not a "+getDataTypeID());
			}
		}

	};

	public static final DataType SHORT = new DataType("short","0"){

		private static final long serialVersionUID = 6620202050947823499L;

		@Override
		public String toString(Object o) {
			return Short.toString((Short) o);
		}

		@Override
		public Comparable<?> valueOf(String s) {
			if (s == null) {
				throw new IllegalArgumentException("NULL argument");
			}
			try {
				return Short.valueOf(s);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(s+" is not a "+getDataTypeID());
			}
		}

	};

	public static final DataType STRING = new DataType("string",null){

		private static final long serialVersionUID = 9016492691743578432L;

		@Override
		public String toString(Object o) {
			return (String)o;
		}

		@Override
		public Comparable<?> valueOf(String s) {
			return s;
		}

	};

	public static final DataType DATETIME = new DataType("datetime","0"){

		private static final long serialVersionUID = 587278261183595348L;

		@Override
		public String toString(Object o) {
			if (o == null){
				return null;
			}
			Date date = null;
			if (o instanceof Long){
				date = new Date((Long) o);
			}
			else {
				date = (Date) o;
			}
			return GlobalConfiguration.getInstance().getDateTimeFormat().format(date);
		}

		@Override
		public Comparable<?> valueOf(String s) {
			if (s == null) {
				throw new IllegalArgumentException("NULL argument");
			}
			try {
				long l = Long.parseLong(s);
				return new Date(l);
			} catch (NumberFormatException e) {
				Date date = parse(s, GlobalConfiguration.getInstance().getDateTimeFormats());
				if (date == null){
					date = parse(s, GlobalConfiguration.getInstance().getTimeFormats());
					if (date == null){
						date = parse(s, GlobalConfiguration.getInstance().getDateFormats());
					}
				}
				if (date == null){
					throw new IllegalArgumentException(s+" is not a "+getDataTypeID());
				}
				return date;
			}
		}

		private Date parse(String s, DateFormat[] formats) {
			for (DateFormat dateFormat : formats) {
				DateFormat clonedDateFormat = (DateFormat) dateFormat.clone();
				clonedDateFormat.setLenient(false);
				ParsePosition position = new ParsePosition(0);
				Date date = clonedDateFormat.parse(s, position);
				if (date != null && position.getErrorIndex() == -1 && position.getIndex() == s.length()) {
					return date;
				}
			}
			return null;
		}

		@SuppressWarnings("unused")
		public Long toLong(Date d){
			return d.getTime();
		}

	};

	private static Map<String,DataType> TYPE_DATATYPE_MAP;

	private static List<String> NUMERIC_DATATYPES;

	static{
		TYPE_DATATYPE_MAP = new HashMap<String, DataType>();
		TYPE_DATATYPE_MAP.put(BOOLEAN.getDataTypeID(), BOOLEAN);
		TYPE_DATATYPE_MAP.put(DOUBLE.getDataTypeID(), DOUBLE);
		TYPE_DATATYPE_MAP.put(FLOAT.getDataTypeID(), FLOAT);
		TYPE_DATATYPE_MAP.put(INTEGER.getDataTypeID(), INTEGER);
		TYPE_DATATYPE_MAP.put(LONG.getDataTypeID(), LONG);
		TYPE_DATATYPE_MAP.put(SHORT.getDataTypeID(), SHORT);
		TYPE_DATATYPE_MAP.put(STRING.getDataTypeID(), STRING);
		TYPE_DATATYPE_MAP.put(DATETIME.getDataTypeID(), DATETIME);

		NUMERIC_DATATYPES = new ArrayList<String>(5);
		NUMERIC_DATATYPES.add(DOUBLE.getDataTypeID());
		NUMERIC_DATATYPES.add(FLOAT.getDataTypeID());
		NUMERIC_DATATYPES.add(INTEGER.getDataTypeID());
		NUMERIC_DATATYPES.add(LONG.getDataTypeID());
		NUMERIC_DATATYPES.add(SHORT.getDataTypeID());

	}

	public static DataType resolve(String type){
		return TYPE_DATATYPE_MAP.get(type.toLowerCase());
	}

	public static boolean isNumeric(DataType dataType) {
		return NUMERIC_DATATYPES.contains(dataType.getDataTypeID());
	}
}
