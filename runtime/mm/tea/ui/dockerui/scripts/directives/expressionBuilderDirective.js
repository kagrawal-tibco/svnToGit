/**
 * Created by priyanka on 17/12/15
 */
beConfigModule.directive('expressionTree',  ['teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory',
                                     function (teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory) {
    
	return {
        	restrict: 'E',
        	scope: {
        		attributes: "="
        	},
        	replace: true,
        	templateUrl: 'partials/templates/directive/expressionBuilder.html',
        	link: function(scope, el, attrs) {
        		scope.conditionArray = scope.$parent[attrs.conditions];  
        		scope.setAttribute = function(conditionArray,attr){
        			conditionArray.data.exp.attribute = attr;
        		}
        		scope.integerOperators = {"EQ":"==","GE":">=","LE":"<=","LT":"<","GT":">","NEQ":"!="};
        		scope.dimensionOperators = {"EQ":"==","NEQ":"!="} ;
        		scope.booleanOperators = {"EQ":"=="} ;
        		scope.stringOperators = {"IN":"IN","NEQ":"!="};
        		scope.addNewExpression = function(arrayToPushInto){
        			var newExpression = {isNode:false,data:{exp:{attribute:{} ,operator : "" , value:""}}};
        			arrayToPushInto.push(newExpression);
        		}
        		scope.toggleToConditionNode = function(index,arrayToPushInto,expression){
        			var existingExp = {isNode:false,data:{exp:expression}};
        			var newExpression = {isNode:false,data:{exp:{attribute:{} ,operator : "" , value:""}}};
        			var newConditionNode = {isNode:true , data:{isNested:true,op :"" , exp:[existingExp,newExpression]}};
        			arrayToPushInto[index] = newConditionNode;
        		}
                scope.deleteExpression = function (index, expressionArray,isNode,isNested) {
                    expressionArray.splice(index, 1);
                    if(isNode && expressionArray.length == 0)
                    	scope.addNewExpression(expressionArray);	
                }
                scope.isNumber = function (evt) {
                    evt = (evt) ? evt : window.event;
                    var charCode = (evt.which) ? evt.which : evt.keyCode;
                    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        return false;
                    }
                    return true;
                }
                scope.isdecimalNumber =  function(evt){
                    var charCode = (evt.which) ? evt.which : evt.keyCode;
                    if (charCode != 46 && charCode > 31 
                      && (charCode < 48 || charCode > 57))
                       return false;

                    return true;
                 }
                scope.getName = function(name){
                	if(name == 'MEASUREMENT_NAME')
                		return "";
                	else
                		return "------------------------------------"
                }
        	}
		};
	}
 ]);