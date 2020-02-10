package com.tibco.cep.studio.ui.editors.utils.kstreams;

public class TransformationConstants {

	public static String[] DURATION_TYPES = new String[] { "Nanoseconds", "Milliseconds", "Seconds", "Minutes", "Hours",
			"Days" };

	public static final String INPUT_PREDICATE = "Predicate";
	public static final String INPUT_VALUEMAPPER = "ValueMapper";
	public static final String INPUT_KEYVALUEMAPPER = "KeyValueMapper";
	public static final String INPUT_INITIALIZER = "Initializer";
	public static final String INPUT_AGGREGATOR = "Aggregator";
	public static final String INPUT_REDUCER = "Reducer";
	public static final String INPUT_JOIN_TOPIC = "Topic";
	public static final String INPUT_VALUE_JOINER = "ValueJoiner";
	public static final String INPUT_JOINWINDOWS = "JoinWindows";
	public static final String INPUT_JOINWINDOWUNITS = "JoinWindowUnits";
	public static final String INPUT_JOINWINDOWS_WINDOW = "window";
	public static final String INPUT_JOINWINDOWS_AFTER = "after";
	public static final String INPUT_JOINWINDOWS_BEFORE = "before";
	public static final String INPUT_JOINWINDOWS_GRACE = "grace";
}
