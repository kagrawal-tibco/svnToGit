package com.tibco.cep.ui.monitor.util
{
	import mx.utils.StringUtil;
	
	public class XMLNodesConsts
	{
		//Machine Node
		public static const MACHINE_NODE:String = "Machine";
		public static const MACHINE_NODE_NAME_ATTR:String = "mfqn";
		
		public static const MACHINE_NODE_PATTERN:String = "<Machine mfqn=\"{0}\"/>";
		
		//GVs Node
		public static const GVS_NODE:String = "GlobalVariables";
		public static const GVS_NODE_DU_NAME_ATTR:String = "dun";
		
		public static const GVS_NODE_PATTERN:String = "<GlobalVariables dun=\"{0}\"/>";

		//GV Node
		public static const GV_NODE:String = "GlobalVariable";
		public static const GV_NODE_NAME_ATTR:String = "name";
		public static const GV_NODE_DEF_VAL_ATTR:String = "defaultValue";
		public static const GV_NODE_CURR_VAL_ATTR:String = "currentValue";
		
		public static const GV_NODE_PATTERN:String = "<GlobalVariable name=\"{0}\" defaultValue=\"{1}\" currentValue=\"{2}\"/>";

	}
}