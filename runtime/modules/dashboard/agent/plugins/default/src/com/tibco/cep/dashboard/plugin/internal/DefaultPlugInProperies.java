package com.tibco.cep.dashboard.plugin.internal;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;
import com.tibco.cep.dashboard.config.PropertyKey.DATA_TYPE;

public interface DefaultPlugInProperies extends PropertyKeys {
	
	public static final PropertyKey SPAN_THRESHOLD = new PropertyKey("builder.span.adjustment.threshold","The threshold for span which will trigger the readjustment", DATA_TYPE.Integer, new Integer(1));
	
	public static final PropertyKey CLASSIC_LAYOUT_FIRST_PARTITION_SPAN = new PropertyKey("builder.classiclayout.firstpartition.span","The span value for left most partition (containing page set selector) when rendering classing layout", DATA_TYPE.Integer, new Integer(10));
	
	public static final PropertyKey EMERGENCY_SPAN = new PropertyKey("builder.emergency.span","The span value to use for all under threshold spans when no space is available", DATA_TYPE.Integer, new Integer(10));

}
