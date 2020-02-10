package com.tibco.be.views.user.components.drilldown.querymanager.validators{
	
	import mx.validators.ValidationResult;
	import mx.validators.Validator;

	public class RangeValidator extends Validator{
		
		// Define Array for the return value of doValidation().
        private var results:Array;
        private var _fieldName:String;        
        
		public function RangeValidator(){
			super();
		}
		
		public function set fieldName(fName:String):void{
			this._fieldName = fName; 
		}
		
		override protected function doValidation(value:Object):Array{
            var field1:String = value[0];
            var field2:String = value[1];
            
            // Clear results Array.
            results = [];
			
            // Call base class doValidation().
            results = super.doValidation(value);
            // Return if there are errors.
            if(results.length > 0){ return results; }
			
			var num1:Number = Number(field1);
			var num2:Number = Number(field2);
			if(num1 > num2){
				results.push(new ValidationResult(true,"Invalid Range", "invalidrange", _fieldName+": First value should be less than or equal to second value"));
			}
            return results;
        }
	}
}