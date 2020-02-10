package com.tibco.be.views.user.components.drilldown.querymanager{
	
	import mx.controls.ComboBox;
	import mx.collections.IViewCursor;
 	
	public class CustomComboBox extends ComboBox{
		
		public function CustomComboBox(){
			super();
		}
		
		override public function set selectedItem(value:Object):void{
			super.selectedItem = value;
			if(value != null && selectedIndex == -1){
				// do a painful search;
				if(collection && collection.length){
					var cursor:IViewCursor = collection.createCursor();
					while(!cursor.afterLast){
						var obj:Object = cursor.current;
						var nope:Boolean = false;
						for(var p:String in value){
							if(obj[p] !== value[p]){
								nope = true;
								break;
							}
						}
						if(!nope){
							super.selectedItem = obj;
							return;
						}
						cursor.moveNext();
					}
				}
			}
	 	}
	}
}