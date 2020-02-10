package com.tibco.be.views.user.components.drilldown.querymanager.validators{
	
	import mx.utils.StringUtil;
	import mx.validators.ValidationResult;
	import mx.validators.Validator;

	public class CustomStringValidator extends Validator{
		// Define Array for the return value of doValidation().
        private var results:Array;
        private var _fieldName:String;
		
		public function CustomStringValidator(){
			super();
		}
		
		public function set fieldName(fName:String):void{
			this._fieldName = fName; 
		}
		
		override protected function doValidation(value:Object):Array{
			var inputStr:String = String(value);
			inputStr = StringUtil.trim(inputStr);
			
			results = [];
			super.requiredFieldError = _fieldName+": Input should not be empty"
            // Call base class doValidation().
            results = super.doValidation(value);
            // Return if there are errors.
            if(results.length > 0){
            	return results;
            }
            // check if the input value is empty
            if(inputStr == ""){
            	results.push(new ValidationResult(true,"Empty input", "emptyInput", _fieldName+": Input should not be empty"));
            	return results;
            }
            // currently only " special character is addressed. We filter more special characters
//            if(inputStr.indexOf("\"") != -1){
//            	results.push(new ValidationResult(true,"Invalid input", "invalidInput", _fieldName+": Input contains invalid characters"));
//            }
            return results;
		}
	}
}