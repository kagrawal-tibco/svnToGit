package com.tibco.cep.ui.monitor.util
{
	public class ResultConst
	{
		//************************ XML element node names  ************************
		public static const OPERATION:String = "operation";
		public static const EVENT:String = "event";
		
		public static const WARNING:String = "warning"; 
		public static const INFO:String = "info";
		public static const SUCCESS:String = "success";
		public static const ERROR:String = "error";

		public static const ERROR_MSG:String = "errormessage";
		public static const ERROR_CODE:String = "errorcode";
		
	    public static const DATA:String = "data";
	    public static const VALUE:String = "value";
	    public static const TABLE:String = "table";
	
		//************************ XML attribute names  ************************
	    public static const OPER_NAME_ATTR:String = "name";
	    public static const ERROR_CODE_ATTR:String = "code";
	    
	    //************************ Error messages  ************************
	    public static const ERROR_UNEXPECT_DATA:String = "Unexpected XML element received from server";
	    public static const ERROR_STANDARD:String = "An error occurred during this operation";
	}
}