package com.tibco.cep.ui.monitor.util{
	import mx.controls.Alert;
	
	public class Util{
		
		public static function errorMessage(message:String, callBack:Function=null):void{
			Alert.show(message, "ERROR", 4, null, callBack);
		}
		
		public static function infoMessage(message:String, callBack:Function=null):void{
			Alert.show(message,"Info", 4, null, callBack);
		}
		
		public static function PopInvokeOperMessage(message:String, operationFullPath:String):void{
			Alert.show(message,operationFullPath, 4);
		}
		
		/**
		 * Searches the provided xml structure for a particular node whose ID matches the provided
		 * nodeId.
		 */
		public static function findNodeIn(nodeId:String, xml:XML):XML{
			if(xml.@id != undefined && xml.@id == nodeId) return xml;
			for each(var child:XML in xml.children()){
				var subSearchResult:XML = findNodeIn(nodeId, child);
				if(subSearchResult != null) return subSearchResult;
			}
			return null;
		}

		/** 
		 * For the XML node with name specified in the second parameter determines and returns 
		 * the value of the attribute specified in the third parameter. 
		 * 
		 * @param xml - Topology tree XML Object to search
		 * @param xmlNodeName - Name of the XML node to search for the atribute value. It can be one of:
		 * 						site, machine, cluster, process, agent, mcacheobjects 
		 * @param attrName - Name of the attribute to search in the XML topology. Depending on the node
		 * 					 it can be one of: id, name, predefined, active, deployed, lastDeploy
		 * 
		 * @return The value of the attribute specified for the node specified.
		 * */
		 public static function getAttributeValue(xml:XML, xmlNodeName:String, attrName:String):String {
		 	var ancestor:XML = xml;
			while(ancestor != null && (ancestor.localName() as String) != xmlNodeName){
				ancestor = ancestor.parent() as XML;
			}
			return ancestor == null ? null : String(ancestor.@[attrName]);
		 }
		 
		 public static function findSimpleName(fqn:String):String {
			var fqnSplit:Array = fqn.split("/");
			return fqnSplit[fqnSplit.length-1];
		}
		
		/**
		 * Builds a path string from ancestor node names.
		 * For example
		 * 	<root name="theRoot"><node name="aNode"></node></root> => "theRoot/aNode/"
		 */
		 public static function getAncestoryNamePath(xml:XML):String{
		 	var ancestor:XML = xml;
			var ancestryPath:String = "";
			while(ancestor != null && (ancestor.localName() as String) != "site"){
				ancestryPath = String(ancestor.@name) + "/" + ancestryPath;
				ancestor = ancestor.parent() as XML;
			}
			return ancestryPath;
		 }
		 
		 public static function searchForAncestory(searchFor:String, xml:XML, currentPath:String=""):XML{
		 	if(searchFor.indexOf(currentPath) < 0) return null; //trim the search tree
		 	currentPath += String(xml.@name) + "/";
		 	if(currentPath == searchFor) return xml;
		 	for each(var subTree:XML in xml.children()){
		 		var searchResult:XML = searchForAncestory(searchFor, subTree, currentPath); 
		 		if(searchResult != null) return searchResult;
		 	}
		 	return null;
		 }
		 
		 public static function isANumber(str:String):Boolean { return !isNaN(Number(str)); }

	}
}