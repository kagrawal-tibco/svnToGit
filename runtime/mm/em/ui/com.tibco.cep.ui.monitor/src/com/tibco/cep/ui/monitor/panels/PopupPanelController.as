package com.tibco.cep.ui.monitor.panels
{
	import com.tibco.cep.ui.monitor.TopologyMenuController;
	import com.tibco.cep.ui.monitor.sitetopology.Cluster;
	import com.tibco.cep.ui.monitor.sitetopology.Host;
	import com.tibco.cep.ui.monitor.sitetopology.Site;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.TopologyConstants;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.Event;
	
	import mx.containers.Panel;
	import mx.controls.CheckBox;
	import mx.controls.Label;
	import mx.controls.Spacer;
	import mx.controls.TextInput;
	import mx.events.FlexEvent;
	import mx.events.ValidationResultEvent;
	import mx.managers.PopUpManager;
	import mx.validators.NumberValidator;
	import mx.validators.StringValidator;
	import mx.validators.Validator;
	
	public class PopupPanelController
	{
		//icon images embed objects references		
		[Embed("/assets/images/datatypes/iconString16.gif")]
		protected static var StringTypeIcon:Class;
		
		[Embed("/assets/images/datatypes/iconBoolean16.gif")]
		protected static var BooleanTypeIcon:Class;
		
		[Embed("assets/images/datatypes/iconInteger16.gif")]
		protected static var IntegerTypeIcon:Class;
		
		[Embed("assets/images/datatypes/iconReal16.gif")]
		protected static var DoubleTypeIcon:Class;
		
		protected static const BTN_OK_TOOLTIP:String = "Please check the form entries for required fields or values of incorrect data types";
		
		private static var instance:PopupPanelController;  
		private static var _isPopUpOpen:Boolean;
		private static var _popUpView:Panel;
		
		private var _validatorsList:Array;
		
		protected var _monitoredNode:XML;
		protected var _topologyTreeController:TopologyMenuController;
		protected var _siteName:String; 
		protected var _clusterName:String; 
		protected var _machineName:String;
		protected var _site:Site; 
		protected var _cluster:Cluster; 
		protected var _host:Host;
		
		public function PopupPanelController() { instance=this; }	//constructor
	
		private static function getThis():PopupPanelController { return instance; }
		
		// ************************************* Set Data *********************************
		protected function setData():void {
			_machineName = _topologyTreeController.getMachineName(_monitoredNode);
			
			_clusterName = 
				Util.getAttributeValue(_monitoredNode,TopologyConstants.CLUSTER_NODE,
									TopologyConstants.TOP_FILE_CLUSTER_NODE_NAME_ATTR);
			
			_siteName = 
				Util.getAttributeValue(_monitoredNode,TopologyConstants.SITE_NODE,
									TopologyConstants.NAME_ATTR);
			
			_site = _topologyTreeController.getSite(_siteName);
			
			var msg:String;
			var msg1:String;
			
			if (_site == null) {
				msg = "Site";
				msg1 = _siteName;
			} else {
				_cluster = _site.getCluster(_clusterName); 
			
                if (_cluster == null) {
                    msg = "Cluster";
                    msg1 = _clusterName;
                } else {
                    _host = _cluster.getHost(_machineName);

                    if (_host == null) {
                        msg  = "Machine";
                        msg1  = _machineName;
                    }
                }
            }

			if (_site == null || _cluster == null || _host == null) {
				Logger.logWarning(this,"{0} '{1}' not defined in site topology file", msg, msg1);
			}
		}
		
		/** returns the class referencing the embed icon for the specified datatype*/
		protected static function getIconImageClass(argDataType:String):Class {
			if (argDataType == "java.lang.string"){
				return StringTypeIcon;
			}
			else if (argDataType == "boolean" ){
				return BooleanTypeIcon;
			}
			else if (argDataType == "byte" || argDataType == "short" || argDataType == "int" || argDataType == "long"){
				return IntegerTypeIcon;
			} 
			else if (argDataType == "float" || argDataType == "double"){
				return DoubleTypeIcon;
			}
			else {  
				Logger.logWarning(instance, "There is no icon for type: " + argDataType);
				return null;
			}				
		} //getIconImageClass
		
		// ******************************** Field Validators Manipulation ************************
		/**  creates the validator Object appropriate to the argument type passed in as parameter */
		protected static function createTypeValidator(argDataType:String, allowNegative:Boolean, 
															minValue:Object, maxValue:Object):Validator {
			var validator:Validator;
			if (argDataType == "java.lang.string"){
				validator = new StringValidator();
			}
			else if (argDataType == "boolean" ){
				//TODO: BOOLEAN VALIDATOR
				trace ("[PanelControllerUtil] - Warning: Unhandled datatype: " + argDataType + ". Created String validator.");
				validator = new StringValidator();
			}
			else if (argDataType == "byte" || argDataType == "short" || argDataType == "int" || argDataType == "long"){
				validator = new NumberValidator();
				(validator as NumberValidator).domain = "int";
				(validator as NumberValidator).allowNegative = allowNegative;
				(validator as NumberValidator).negativeError = "This argument must be non-negative";
				(validator as NumberValidator).minValue = minValue;
				(validator as NumberValidator).lowerThanMinError = "This argument must be greater or equal to " + (validator as NumberValidator).minValue;
				(validator as NumberValidator).maxValue = maxValue;
				(validator as NumberValidator).integerError = "This argument must be an integer number.";
				(validator as NumberValidator).invalidCharError = "This argument must be an integer number.";
				(validator as NumberValidator).invalidFormatCharsError = "This argument must be an integer number.";
				(validator as NumberValidator).decimalPointCountError = "This argument must be an integer number.";
			} 
			else if (argDataType == "float" || argDataType == "double"){
				validator = new NumberValidator();
				(validator as NumberValidator).domain = "real";
				(validator as NumberValidator).allowNegative = allowNegative;
				(validator as NumberValidator).negativeError = "This argument must be non-negative";
				(validator as NumberValidator).minValue = minValue;
				(validator as NumberValidator).lowerThanMinError = "This argument must be greater or equal to " + (validator as NumberValidator).minValue;
				(validator as NumberValidator).maxValue = maxValue;
				(validator as NumberValidator).integerError = "This argument must be a real number.";
				(validator as NumberValidator).invalidCharError = "This argument must be a real number.";
				(validator as NumberValidator).invalidFormatCharsError = "This argument must be a real number.";
			}
			else { 
				Logger.logWarning(instance, "Unhandled datatype: " + argDataType + ". Created String validator.");
				validator = new StringValidator();
			}
			return validator;
		} //getTypeValidator
		
		/** sets the text validator for the provided TextInput object 
		 *  validates if the field is required, and if  the user's input is of the correct datatype
		 *  @param isRequired: set to true if the field is required, set to false otherwise
		 * 	@returns: Validor created for the provided TextInput object
		 * */
		protected function setValidator(textInputObj:TextInput, isRequired:String="false", 
										argDataType:String = "java.lang.string", allowNegative:Boolean=true, 
										minValue:Object=NaN, maxValue:Object=NaN):Validator {
			var validator:Validator;
			validator = createTypeValidator(argDataType, allowNegative, minValue, maxValue);
			
			validator.source = textInputObj;
			validator.property = "text";				//it is required to set this property to "text"
			//if required field is not defined it assumes it is false.
			validator.required = isRequired.toLowerCase() == "true" ? true : false;
			
//				validator.trigger = if ommitted the component specified in src property is used 
			validator.triggerEvent = Event.CHANGE;//FlexEvent.DATA_CHANGE;//TextEvent.TEXT_INPUT; 	
		
			//activate/deactivate the OK button
			textInputObj.addEventListener(FlexEvent.INVALID, handleInvalidInput);
			textInputObj.addEventListener(FlexEvent.VALID, handleValidInput);
			return validator;
		}
		
		protected function removeValidator(textInputObj:TextInput):void {
			if (textInputObj == null) return;
			
			textInputObj.removeEventListener(FlexEvent.INVALID, handleInvalidInput);
			textInputObj.removeEventListener(FlexEvent.VALID, handleValidInput);
		}
		
		/** The events need to be dispatched for the empty TextInput fields that have 
		* empty values to get validated immediately after the panel is created. If we don't send this
		* event, these fields only get validated after the user interacts with the correspondnig TextInput 
		*/ 
		protected function dispatchChangeEvents(viewObj:Object):void {
			if (viewObj == null )
				return;
			
			//Label,TextInput,etc... have no children => to avoid getChildren throw exception	
			if (viewObj is Label || viewObj is TextInput || viewObj is Spacer || viewObj is CheckBox)  
				return;
					
			for each (var child:Object in viewObj.getChildren()) {
				if (child is TextInput) {
					child.dispatchEvent(new Event(Event.CHANGE));
				}
				dispatchChangeEvents(child);
			}
		}
		
		/** Iterates over every validator in this view and checks the status (valid/invalid)
		 * @returns: true if every validator validates to true, false otherwise. */
		protected function isViewValid():Boolean {
			var isValid:Boolean=true;
			for each (var validator:Validator in validatorsList) { // is true if every vaildator validates to true
				isValid = isValid && ((validator.validate().type == ValidationResultEvent.VALID)?true:false);
			}
			return isValid;
		}
		
		// ********************** Event Listeners Overriden in subclasses *********************
		/** DeActivate the OK button */
		protected function handleInvalidInput(event:Event):void {/*Virtual*/} 
		
		/** Activate the OK button */
		protected function handleValidInput(event:Event):void {/*Virtual*/}
		
		// ******************************* Manipulate views ***********************************
		protected static function createPopUp(view:Panel, currentPanel:GeneralPanel):void {
			_popUpView = view;
            PopUpManager.addPopUp(view, currentPanel.currentPage, true);
            PopUpManager.centerPopUp(view);
            _isPopUpOpen = true;
    	}
    	
    	public static function closePopUp():void {
			PopUpManager.removePopUp(_popUpView);		//even if it is null nothing happens, so no need to handle that edge case
			_popUpView = null;
			_isPopUpOpen = false;
		}
    	
    	public function createView():void{/*virtual*/}
    	
    	// ****************************** Getters and Setters *********************************
    	public static function get isPopUpOpen():Boolean { return _isPopUpOpen; }
    	
    	public static function set isPopUpOpen(isPopupOpen:Boolean):void { _isPopUpOpen = isPopupOpen; }
    	
    	public function get validatorsList():Array { return _validatorsList; }
    	
    	public function set validatorsList(validatorsList:Array):void { _validatorsList = validatorsList; }
    	
    	
	} //class
} //package