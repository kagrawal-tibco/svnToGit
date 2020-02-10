package com.tibco.be.views.utils{
	
	import flash.utils.Dictionary;
	import flash.utils.getQualifiedClassName;
	
	import mx.collections.XMLListCollection;
	import mx.formatters.DateFormatter;
	
	/**
	 * Utility class
	 * */
	 
	public class BEVUtils{
		
		private static var DISPLAY_DATE_FORMAT_PATTERN:String = "MM/DD/YYYY LL:NN A";
		private static var SERVER_DATE_FORMAT_PATTERN:String = "MM/DD/YYYY JJ:NN";
		private static var serverDateFormatter:DateFormatter = null;
		private static var displayDateFormatter:DateFormatter = null;
		
		public static function getClassName(object:Object):String{
			var name:String = getQualifiedClassName(object);
			
			// If there is a package name, strip it off.
			var index:int = name.indexOf("::");
			if(index != -1){
			    name = name.substr(index + 2);
			}
			        
			return name;
		}
		
		/**
		 * @public
		 * @param String ["Format" - "value1 - value2"]
		 * Returns an array of all existing values between value1 and value2
		 * 
		 * */
		public static function getRange(value:String):Array{
			var rangeValue:Array=value.split("-");
			var rangeArr:Array=new Array();
			for(var i:int=Number(rangeValue[0]); i<=Number(rangeValue[1]);i++){
					rangeArr.push(i);
				}
			return rangeArr;
		}
		
		/**
		 * @public 
		 * @param String ["Format" - "value1 - value2"]
		 * Returns the min value
		 * */
		public static function getMinimumRange(value:String):Number{
			var rangeValue:Array=value.split("-");
			if(Number(rangeValue[0]) < Number(rangeValue[1])){
				return Number(rangeValue[0]);
			}
			else{
				return Number(rangeValue[1]);	
			}
			
			
		}
		
		/**
		 * @public 
		 * @param String ["Format" - "value1 - value2"]
		 * Returns the max value
		 * */
		public static function getMaximumRange(value:String):Number{
			var rangeValue:Array=value.split("-");
			if(Number(rangeValue[0]) > Number(rangeValue[1])){
				return Number(rangeValue[0]);
			}
			else{
				return Number(rangeValue[1]);	
			}
		}
		
		/**
		 * @public
		 * function find a value in string ["holder"] and replace with string [replacement] and return modifyed a string
		 * 
		 * */
		public static function searchAndReplace(holder:String, searchfor:String, replacement:String):String{
			var temparray:Array = holder.split(searchfor);
			holder = temparray.join(replacement);
			return (holder);
		}

		private static function getServerDateFormatter():DateFormatter{
			if(serverDateFormatter == null){
				serverDateFormatter = new DateFormatter();
				serverDateFormatter.formatString = SERVER_DATE_FORMAT_PATTERN;
			}
			return serverDateFormatter;
		}
		
		private static function getDisplayDateFormatter():DateFormatter{
			if(displayDateFormatter == null){
				displayDateFormatter = new DateFormatter();
				displayDateFormatter.formatString = DISPLAY_DATE_FORMAT_PATTERN;
			}
			return displayDateFormatter;
		}
		
		public static function parseServerDate(strDate:String):Date{
			//No API available to use pattern to parse.
			//As per Date class documentation, it supports the pattern defined in
			//SERVER_DATE_FORMAT_PATTERN as MM/DD/YYYY HH:MM:SS
			return new Date(strDate);	
		}

		public static function formatServerDate(dDate:Date):String{
			return getServerDateFormatter().format(dDate);
		}

		public static function formatDisplayDate(dDate:Date):String{
			return getDisplayDateFormatter().format(dDate);
		}
		
		public static function isValid(value:Object):Boolean{
			if(value == null){ return false; }
			if(value.toString().length == 0){ return false; }
			return true;
		}
		
		public static function xmlListCollToArray(xmlList:XMLListCollection):Array{
		    var arr:Array = new Array();
		    for each(var item:Object in xmlList){
		        arr.push(item);
		    }
		    return arr;
		}

		public static function xmlListToArray(xmlList:XMLList):Array{
		    var arr:Array = new Array();
		    for each(var item:Object in xmlList){
		        arr.push(item.toString());
		    }
		    return arr;
		}

		public static function toMultiLineString(arr:Array):String{
		    if(arr == null || arr.length == 0){ return null; }
		    var str:String = "";
		    for(var i:int = 0; i < arr.length; i++){
		        str += arr[i].toString();
		        if(i < arr.length - 1){
		            str += "\n";
		        }
		    }
		    return str;
		}
		
		public static function getDictionarySize(dict:Dictionary):int{
			var n:int = 0;
			for(var key:* in dict){ n++; }
			return n;
		}
		
		public static function clearDictionary(dict:Dictionary):void{
			for(var key:* in dict){ delete dict[key]; }
		}
		
		/** Floor-round a number to the nearest factor. For example:
		 * 	floorToFactor(127, 1) = 127
		 *	floorToFactor(124, 5) = 120
		 * 	floorToFactor(127, 10) = 120
		 *  floorToFactor(172, 100) = 100
		*/
		public static function floorToFactor(unrounded:Number, factor:Number):Number{
			return Math.floor(unrounded/factor)*factor;
		}
		
		/** Ceil-round a number to the nearest factor. For example:
		 *	ceilToFactor(122, 1) = 122
		 *  ceilToFactor(122, 5) = 125
		 * 	ceilToFactor(127, 10) = 130
		 * 	ceilToFactor(101, 100) = 200
		*/
		public static function ceilToFactor(unrounded:Number, factor:Number):Number{
			return Math.ceil(unrounded/factor)*factor;
		}
		
		/** Round a number to the nearest factor. For example:
		 *	roundToFactor(122, 1) = 122
		 *  roundToFactor(122, 5) = 120
		 * 	roundToFactor(127, 10) = 130
		 * 	roundToFactor(101, 100) = 100
		*/
		public static function roundToFactor(unrounded:Number, factor:Number):Number{
			return Math.round(unrounded/factor)*factor;
		}
	}
}