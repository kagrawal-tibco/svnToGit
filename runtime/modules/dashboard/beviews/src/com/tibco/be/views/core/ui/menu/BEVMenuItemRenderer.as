package com.tibco.be.views.core.ui.menu{

	import com.tibco.be.views.ui.DynamicMenuColorIcon;
	
	import mx.controls.menuClasses.MenuItemRenderer;
	import mx.controls.menuClasses.MenuListData;

	public class BEVMenuItemRenderer extends MenuItemRenderer{
		
		public static const MAX_MENU_ITEM_TEXT_LENGTH:int = 27;
		public static const TRUNCATION_INDICATOR:String = "...";
		
		private var _iconColor:uint = DynamicMenuColorIcon.DEFAULT_ICON_COLOR;
		
		public function BEVMenuItemRenderer(){
			super();
		}
		
		override public function set data(value:Object):void{
    		if(value is XML && value.name() == "menuitem"){
    			if(listData is MenuListData && value.@iconColor != undefined){
	    			(listData as MenuListData).icon = DynamicMenuColorIcon;
	    			_iconColor = parseInt(value.@iconColor, 16);
	    		}
	    		if(String(value.@label).length > MAX_MENU_ITEM_TEXT_LENGTH + TRUNCATION_INDICATOR.length){
	    			//Since renderers are recycled in a menu, maintaining instance variables for
	    			//tooltips or trying to set label.toolTip here won't work. Instead, we append a
	    			//tooltip attribute to the data which will be set during updateDispalyList
	    			value.@tooltip = new String(value.@label);
	    			value.@label = String(value.@label).substr(0, MAX_MENU_ITEM_TEXT_LENGTH) + TRUNCATION_INDICATOR;
	    		}
    		}
    		super.data = value;
    	}
			
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			if(icon != null && icon is DynamicMenuColorIcon){
				(icon as DynamicMenuColorIcon).setbMapData(_iconColor);
			}
			if(data.@tooltip != undefined){
				label.toolTip = data.@tooltip;
			}
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		override protected function measure():void{
			explicitHeight = label.textHeight;
			super.measure();
		}
		
	}
}