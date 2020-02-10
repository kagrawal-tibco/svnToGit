package com.tibco.cep.dashboard.psvr.text.url;

import java.net.URLEncoder;
import java.util.Calendar;

import com.tibco.cep.dashboard.config.GlobalConfiguration;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;

class DateTimeSubstitutor implements Substitutor {
	
	private static final long serialVersionUID = 3616196788956520411L;

	private static final String TIME_NOW = "NOW";

	private static final String[] KEYS = new String[] { 
		BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.STARTOFDAY.toString(), 
		BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.ENDOFDAY.toString(), 
		BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.TODAY.toString(), 
		TIME_NOW 
	};

	public String substitute(String key, SubstitutionContext context) throws Exception {
		Calendar calendar = Calendar.getInstance();
		if (key == null) {
			context.getLogger().log(Level.WARN, "Can't resolve system context with null key=" + key);
			throw new Exception("Can't resolve system context with null key.");
		} else if (key.equals(BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.STARTOFDAY.toString())) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			String result = GlobalConfiguration.getInstance().getDateTimeFormat().format(calendar.getTime());
			return URLEncoder.encode(result, "UTF-8");
		} else if (key.equals(BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.ENDOFDAY.toString())) {
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			String result = GlobalConfiguration.getInstance().getDateTimeFormat().format(calendar.getTime());
			return URLEncoder.encode(result, "UTF-8");
		} else if (key.equals(BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.TODAY.toString())) {
			String result = GlobalConfiguration.getInstance().getDateFormat().format(calendar.getTime());
			return URLEncoder.encode(result, "UTF-8");
		} else if (key.equals(DateTimeSubstitutor.TIME_NOW)) {
			String result = GlobalConfiguration.getInstance().getDateTimeFormat().format(calendar.getTime());
			return URLEncoder.encode(result, "UTF-8");
		} else {
			context.getLogger().log(Level.WARN, "Unsupported substitution key for system context:" + key);
			throw new Exception("Unsupported substitution key for system context:" + key);
		}
	}
	
	@Override
	public String[] getSupportedKeys() {
		return KEYS;
	}
	
}
