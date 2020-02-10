package com.tibco.be.views.core.ui{
	
	import flash.utils.Dictionary;
	
	public class DynamicParamsResolver{
		
		public static const INVOKING_UI_COMPONENT:String = "invokingUIComponent";
		
		public static const CURRENT_ROLE:String = "role.current";
	    public static const NEW_ROLE:String = "role.new";
	    		
		public static const SESSION_ID:String = "session.id";
		
	    public static const CURRENTPAGE_ID_PARAM:String = "currentpage.id";
	    
	    public static const CURRENTPANEL_ID_PARAM:String = "currentpanel.id";
	    
	    public static const CURRENTCOMPONENT_ID_PARAM:String = "currentcomponent.id";
	    public static const CURRENTCOMPONENT_TITLE_PARAM:String = "currentcomponent.title";
	    public static const CURRENTCOMPONENT_HELP_PARAM:String = "currentcomponentmodel.help";
	    
	    public static const CURRENTDATAROW_ID_PARAM:String = "currentdatarow.id";
	    public static const CURRENTDATAROW_LINK_PARAM:String = "currentdatarow.link";
	    
	    public static const CURRENTSERIES_ID_PARAM:String = "currentdatacolumn.seriesid";
	    public static const CURRENTDATACOLUMN_ID_PARAM:String = "currentdatacolumn.id";
	    public static const CURRENTDATACOLUMN_FIELD_NAME_PARAM:String = "currentdatacolumn.fieldname"
	    public static const CURRENTDATACOLUMN_LINK_PARAM:String = "currentdatacolumn.link";
	    public static const CURRENTDATACOLUMN_VALUE_PARAM:String = "currentdatacolumn.value";
	    public static const CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM:String = "currentdatacolumn.typespec.hrefprms";
	    
		private var _map:Dictionary;
		
		public function DynamicParamsResolver(){
			_map = new Dictionary();
		}
		
		public function setDynamicParamValue(key:String, value:Object):void{
			_map[key] = value;	
		}
		
		public function getDynamicParamValue(key:String):Object{
			if(_map[key] == undefined){ return null; }
			return _map[key];	
		}
		
		public function removeDynamicParamValue(key:String):Object{
			var existingValue:Object = getDynamicParamValue(key);
			delete _map[key];	
			return existingValue;
		}

	}
}