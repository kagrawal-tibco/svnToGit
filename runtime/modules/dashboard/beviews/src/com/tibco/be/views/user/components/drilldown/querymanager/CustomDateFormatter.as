package com.tibco.be.views.user.components.drilldown.querymanager
{
	import mx.formatters.DateFormatter;

	public class CustomDateFormatter extends DateFormatter
	{
		public function CustomDateFormatter()
		{
			super();
		}
		
		// unfortunately parseDate() method in parent class DateFormatter is protected in Flex 3.0!
		// need to override in this class
		public function parseDate(str:String):Date
    	{
        	return parseDateString(str);
    	}   
	}
}