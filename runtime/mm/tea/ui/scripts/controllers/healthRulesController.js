/**
 * Created by Priyanka on 07/12/15
 */
beConfigModule.controller("healthRulesCtrl",['$scope','$location','$rootScope','$q','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService','utilService','StorageService',
                                       function ($scope,$location, $rootScope,$q,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService,utilService,StorageService){
	$('#loaderdiv').show();
	$rootScope.loadingComplete.then(function( apps ){ 
			$('#loaderdiv').hide();
			$scope.selectedApp=ReferenceObjectConfigService.getSelectedApp();
			$scope.processingUnits=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_ProcessingUnits");
			$scope.ruleEntityAndAttibutes = StorageService.getRuleAttributes();
			$scope.entityHealthValuesMap = StorageService.getEnityHealthValueMap();
			$scope.alertLevelMap = {'1$Normal':'Normal','2$Low':'Low','3$Medium':'Medium','4$High':'High'};
			$scope.search={};
			$scope.applicationRules;
			$scope.attributeList="";
			$scope.filteredValues={};
			$scope.filteredValues.allRulesChecked = false;
			$scope.dismiss =function(){
				$scope.notification.show = false;
				if(!utilService.getAgentReachable()){
					$location.path("/");
				}
			}
			$scope.selectedEntity="";
			
			$scope.changeEntity = function(key){
				$scope.selectedEntity=key;
				$scope.attributeList = $scope.ruleEntityAndAttibutes[key].attributes;
				$scope.attributeList.sort(function(a, b) {
				    return parseInt(a.index) - parseInt(b.index);
				});
				$scope.alertText = $scope.ruleEntityAndAttibutes[key].alertText;
			}
			$scope.selectAllRules = function () {			
	            for (var i = 0; i < $scope.applicationRules.length; i++) {
	            		$scope.applicationRules[i].isChecked = $scope.filteredValues.allRulesChecked;
	            }
		    }
			$scope.selectRulesEntity = function () {          
				for (var i = 0; i < $scope.applicationRules.length; i++) {
					if (!$scope.applicationRules[i].isChecked) {
						$scope.filteredValues.allRulesChecked = false;
						return;
					}
				}          
				$scope.filteredValues.allRulesChecked = true;
			}
			$scope.getSelectedEnableDisableRulesList = function(toEnable){
	    		var selectedRulesList = []; 
				if($scope.applicationRules!=undefined){
					for (var i = 0; i < $scope.applicationRules.length; i++) {
			            if ($scope.applicationRules[i].isChecked && $scope.applicationRules[i].enabled == toEnable) {
			                var rule = $scope.applicationRules[i];
			                selectedRulesList.push(rule.name);	                   
			            }
			        } 
	    		}
				return selectedRulesList;
			}
			$scope.getAllSelectedRulesList = function(){
	    		var selectedRulesList = []; 
				if($scope.applicationRules!=undefined){
					for (var i = 0; i < $scope.applicationRules.length; i++) {
			            if ($scope.applicationRules[i].isChecked) {
			                var rule = $scope.applicationRules[i];
			                selectedRulesList.push(rule.name);	                   
			            }
			        } 
	    		}
				return selectedRulesList;
			}
			function getApplicationRules(){
				var isRuleAdminPermission = $scope.hasPermission(30);
				teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'getRules',
					'methodType': 'READ',
					'params' :{
						isTeaAdminPermission   : isRuleAdminPermission
					}
				}).then(function (data) {
					 $scope.applicationRules = data;
					 $scope.applicationRules.sort(function(a, b) {
		    				return parseFloat(b.createdDate) - parseFloat(a.createdDate);
					 });
					 $('#loaderdiv').hide();
				}, function (failReason) {
				});	
			}
			$scope.showEditRuleWizard = function(rule){
				$scope.isError=false;
				$scope.isRuleCreate = false;
				$scope.isDeleteInProgress = false;
				$scope.ruleObj = rule;
				$scope.selectedEntity = $scope.ruleObj.name.split('$')[1];
				$scope.ruleObj.entity = $scope.selectedEntity;
				$scope.setActionsArray=[];
			    $scope.clearActionsArray=[];
				if($scope.ruleObj.setActionDefs != null)
					$scope.setActionsArray=angular.copy($scope.ruleObj.setActionDefs);
				if($scope.ruleObj.clearActionDefs != null)
					$scope.clearActionsArray=angular.copy($scope.ruleObj.clearActionDefs);
				$scope.setConditionExpArray = getTreeObject($scope.ruleObj.setCondition.filter.filters[1]); //AS we dont want to include first two filters having information of application and entity
				$scope.clearConditionExpArray = getTreeObject($scope.ruleObj.clearCondition.filter.filters[1]);
				$scope.isAddNewAction = false;
				$scope.ruleCreationwizardSteps = [{
  			    	"id" : "1" ,
  			    	"name" : "General",
  			    	"isSelected" : true , 
  			    	"isVisited"  : true ,
  			    	"template"   : 'partials/templates/ruleTemplates/general.html'
  			    },{
  			    	"id" : "2" ,
  			    	"name" : "Set Conditions",
  			    	"isSelected" : true , 
  			    	"isVisited"  : true ,
  			    	"template"   : 'partials/templates/ruleTemplates/setConditionsTemplate.html'
  			    },{
  			    	"id" : "3" ,
  			    	"name" : "Set Actions",
  			    	"isSelected" : true , 
  			    	"isVisited"  : true ,
  			    	"template"   : 'partials/templates/ruleTemplates/setActionsTemplate.html'
  			    }, {
  			    	"id" : "4" ,
  			    	"name" : "Clear Conditions",
  			    	"isSelected" : true , 
  			    	"isVisited"  : true ,
  			    	"template"   : 'partials/templates/ruleTemplates/clearConditionsTemplate.html'
  			    }, {
  			    	"id" : "5" ,
  			    	"name" : "Clear Actions",
  			    	"isSelected" : true , 
  			    	"isVisited"  : true ,
  			    	"template"   : 'partials/templates/ruleTemplates/clearActionsTemplate.html'
  			    }];
				$scope.step = 0;
				$('#newRulePanel').modal({
			        show: true,
			        keyboard: false,
			        backdrop: 'static'
			   });
			}
			$scope.showCreateNewRuleWizard = function (){
				$scope.isError=false;
				$scope.isRuleCreate = true;
				$scope.isDeleteInProgress = false;
				$scope.setActionsArray=[];
			    $scope.clearActionsArray=[];
			    $scope.setConditionExpArray = {isNode : true,data :{isNested:false,op :"AND",exp : [{isNode : false,
		        			        	data:{exp : {attribute:{} ,operator : "" , value:""}}}]}};
		        $scope.clearConditionExpArray = {isNode : true,data :{isNested:false,op:"AND",exp :[{isNode : false,
		        			        	data:{exp : {attribute:{} ,operator : "" , value:""}}}]}};
				var setConditionObj = {batchSize:1,batchSize: 1,createdDate: null,cubeName: "BEMMCube",
						filter: {},hierarchyName: "instancehealthhierarchy",measurementName: null,
						modifiedDate: null,name: "setCondition",orderByTuples:[],queryType: "STREAMING",
						schemaName: "BE_MM",sortOrder: null,userName: null,version: null};
				var clearConditionObj = angular.copy(setConditionObj);
				$scope.ruleObj = {clearActionDefs:$scope.clearActionsArray,clearCondition:clearConditionObj,createdDate:"",description:"",displayName:"",enabled:true,
	        			modifiedDate:"",name:"",entity:"",ownerSchema:"",priority:"",propertyNames:[],scheduleName:"",
	        			setActionDefs:$scope.setActionsArray,setCondition:setConditionObj,streamingQuery:"",userName:"",version:""};
				$scope.isAddNewAction = false;
				$scope.ruleCreationwizardSteps = [{
  			    	"id" : "1" ,
  			    	"name" : "General",
  			    	"isSelected" : true , 
  			    	"isVisited"  : true ,
  			    	"template"   : 'partials/templates/ruleTemplates/general.html'
  			    },{
  			    	"id" : "2" ,
  			    	"name" : "Set Conditions",
  			    	"isSelected" : false , 
  			    	"isVisited"  : false ,
  			    	"template"   : 'partials/templates/ruleTemplates/setConditionsTemplate.html'
  			    },{
  			    	"id" : "3" ,
  			    	"name" : "Set Actions",
  			    	"isSelected" : false , 
  			    	"isVisited"  : false ,
  			    	"template"   : 'partials/templates/ruleTemplates/setActionsTemplate.html'
  			    }, {
  			    	"id" : "4" ,
  			    	"name" : "Clear Conditions",
  			    	"isSelected" : false , 
  			    	"isVisited"  : false ,
  			    	"template"   : 'partials/templates/ruleTemplates/clearConditionsTemplate.html'
  			    }, {
  			    	"id" : "5" ,
  			    	"name" : "Clear Actions",
  			    	"isSelected" : false , 
  			    	"isVisited"  : false ,
  			    	"template"   : 'partials/templates/ruleTemplates/clearActionsTemplate.html'
  			    }];
				$scope.step = 0;
				$('#newRulePanel').modal({
			        show: true,
			        keyboard: false,
			        backdrop: 'static'
			   });
			}
			$scope.nextStep = function() {
	            $scope.step++;
	            $scope.isAddNewAction = false;
	            $scope.ruleCreationwizardSteps[$scope.step].isSelected = true;
	        }
	        $scope.prevStep = function() {
	        	if($scope.isRuleCreate)
	        		$scope.ruleCreationwizardSteps[$scope.step].isSelected = false;
	        	$scope.isAddNewAction = false;
	            $scope.step--;
	        }
	        $scope.navigateToStep = function(id){
	        	$scope.step = id-1;
	        	$scope.ruleCreationwizardSteps[$scope.step].isSelected = true;
	        }
	        $scope.areAllStepsRight = function(){
	        	if($scope.ruleObj.entity != undefined && $scope.ruleObj.entity!="" && $scope.ruleObj.name!= undefined && $scope.ruleObj.name != "" && $scope.isConditionsValid($scope.setConditionExpArray) &&
	        			$scope.setActionsArray.length>0 && $scope.isConditionsValid($scope.clearConditionExpArray) && $scope.clearActionsArray.length>0 )
	        		return true;
	        	else
	        		return false;
	        }
	        $scope.isStepCorrect = function(id){
	        	if(id == 1){
	        		if($scope.ruleObj.entity != undefined && $scope.ruleObj.entity!="" && $scope.ruleObj.name!= undefined && $scope.ruleObj.name != "")
	        			return true;
	        		else
	        			return false;	
	        	}else if(id==2){
	        		return $scope.isConditionsValid($scope.setConditionExpArray);
	        	}else if(id==3){
	        		return $scope.setActionsArray.length>0;
	        	}else if(id==4){
	        		return $scope.isConditionsValid($scope.clearConditionExpArray);
	        	}else if(id==5){
	        		return $scope.clearActionsArray.length>0;
	        	}
	        }
	        $scope.createRule = function() {
	        	var isRuleAdminPermission = $scope.hasPermission(30);
	        	$scope.ruleObj.setCondition.filter = getFilterObject($scope.setConditionExpArray);
	        	$scope.ruleObj.clearCondition.filter = getFilterObject($scope.clearConditionExpArray);
	        	$('#loaderdiv').show();
	            teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'createRule',
					'methodType': 'UPDATE',
					'params' :{
						rule   : JSON.stringify($scope.ruleObj),
						entity : $scope.selectedEntity,
						isRuleAdminPermission:isRuleAdminPermission,
						version: $scope.selectedApp.config.version
					}
				}).then(function (data) {
					$('#loaderdiv').hide();
					$('#newRulePanel').modal('hide');
					getApplicationRules();
					/*$scope.notification = {
				            severity: 'info',
				            msg : data,
				            show: true
				    };*/
				}, function (failReason) {
					$('#loaderdiv').hide();
					$scope.isError = true;
 					$scope.errorMessage = failReason.message;
				});
	        }
	        $scope.editRule = function(){
	        	var isRuleAdminPermission = $scope.hasPermission(30);
	        	$scope.ruleObj.setCondition.filter = getFilterObject($scope.setConditionExpArray);
	        	$scope.ruleObj.clearCondition.filter = getFilterObject($scope.clearConditionExpArray);
	        	$scope.ruleObj.setActionDefs=$scope.setActionsArray;
	        	$scope.ruleObj.clearActionDefs=$scope.clearActionsArray;
	        	$scope.ruleObj.name = $scope.ruleObj.name.split('$')[2];
	        	$('#loaderdiv').show();
	            teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'updateRule',
					'methodType': 'UPDATE',
					'params' :{
						rule   : JSON.stringify($scope.ruleObj),
						entity : $scope.selectedEntity,
						isRuleAdminPermission:isRuleAdminPermission,
						version: $scope.selectedApp.config.version
					}
				}).then(function (data) {
					$('#loaderdiv').hide();
					$('#newRulePanel').modal('hide');
					getApplicationRules();
					/*$scope.notification = {
				            severity: 'info',
				            msg : data,
				            show: true
				    };*/
				}, function (failReason) {
					$('#loaderdiv').hide();
					getApplicationRules();
					$scope.isError = true;
 					$scope.errorMessage = failReason.message;
				});
	        }
	        function getFilterObject(conditions){
	        	return iterateConditions(conditions);
	        }
	        function iterateConditions(conditions){
	        	var filter = {filters:[],type:""};
	        	filter.type = conditions.data.op;
	        	for(var i=0 ; i < conditions.data.exp.length ; i++ ){
	        		if(conditions.data.exp[i].isNode){
	        			filter.filters.push(iterateConditions(conditions.data.exp[i]));
	        		}
	        		else{
	        			var attrVal = conditions.data.exp[i].data.exp.value;
	        			if(conditions.data.exp[i].data.exp.attribute['storage-datatype'] == "INTEGER" || conditions.data.exp[i].data.exp.attribute['storage-datatype'] == "LONG"){
	        				attrVal = parseInt(attrVal);
	        			}else if(conditions.data.exp[i].data.exp.attribute['storage-datatype'] == "DOUBLE"){
	        				attrVal = parseFloat(attrVal);
	        			}
	        			var obj = {key:conditions.data.exp[i].data.exp.attribute.name,keyQualifier:conditions.data.exp[i].data.exp.attribute.type,metricQualifier:null,type:conditions.data.exp[i].data.exp.operator,
						value:
							{value:conditions.data.exp[i].data.exp.value,
							 datatype:conditions.data.exp[i].data.exp.attribute['storage-datatype']
							}
						};
						
						
	        			filter.filters.push(obj);
	        		}
	        	}
	        	return filter;
	        }
	        function getTreeObject(filter){
	        	var filterObj = filter;
	        	$scope.attributeList = $scope.ruleEntityAndAttibutes[$scope.selectedEntity].attributes;
	        	$scope.expArray = {isNode : true,data :{isNested:false,op :filterObj.type,exp :[]}};
	        	for(var i=0;i<filterObj.filters.length;i++){
	        		$scope.expArray.data.exp.push(iterateFilterObjectToTreeObject(filterObj.filters[i]));
	        	}
	        	return $scope.expArray;
	        }
	        function iterateFilterObjectToTreeObject(filterObject){
	        	var expObject;
	        	if(filterObject.type != "AND" && filterObject.type != "OR"){
	        			var selectedAttrObj = null;
	        			var operators={};
	        			for(var j=0 ; j<$scope.attributeList.length ; j++){
	        				if($scope.attributeList[j].name == filterObject.key){
	        					selectedAttrObj = $scope.attributeList[j];
	        					break;
	        				}
	        			}
	        			var treeExp = {attribute:selectedAttrObj ,operator : filterObject.type, value:filterObject.value};
	        			expObject = {data : {exp:treeExp},isNode:false};
	        	}else{
	        		expObject = {isNode : true,data :{isNested:true,op :filterObject.type,exp :[]}};
	        		for(var i=0;i<filterObject.filters.length;i++){
		        		expObject.data.exp.push(iterateFilterObjectToTreeObject(filterObject.filters[i]));
		        	}
	        	}
	        	return expObject;
	        }
	        $scope.addNewAction = function(){
	        	$scope.isAddNewAction = true;
	        	$scope.isEditAction = false;
	        	var actionsAllowedForEntity = $scope.ruleEntityAndAttibutes[$scope.selectedEntity].actions;
	        	var topLevelactionDescriptors = StorageService.getActionDescriptors();
	        	$scope.actionDescriptors=[];
	        	for(var i=0;i<topLevelactionDescriptors.length;i++){
	        		for(var j=0;j<actionsAllowedForEntity.length;j++){
	        			if(topLevelactionDescriptors[i].name == actionsAllowedForEntity[j].name){
	        				topLevelactionDescriptors[i].alertText=actionsAllowedForEntity[j].alertText;
	        				$scope.actionDescriptors.push(topLevelactionDescriptors[i]);
	        			}
	        		}
	        	}
	        	var setSelected = $scope.actionDescriptors[0];
	        	$scope.action={actionFunctionDescriptor:setSelected,alertLevel:null,name:""};
	        }
	        $scope.editAction = function(index,actionsArray){
	        	$scope.indexOfActionToEdit = index;
	        	$scope.isDeleteInProgress = false;
	        	$scope.isEditAction = true;
	        	$scope.isAddNewAction = true;
	        	var actionsAllowedForEntity = $scope.ruleEntityAndAttibutes[$scope.selectedEntity].actions;
	        	var topLevelactionDescriptors = StorageService.getActionDescriptors();
	        	$scope.actionDescriptors=[];
	        	for(var i=0;i<topLevelactionDescriptors.length;i++){
	        		for(var j=0;j<actionsAllowedForEntity.length;j++){
	        			if(topLevelactionDescriptors[i].name == actionsAllowedForEntity[j].name)
	        				$scope.actionDescriptors.push(topLevelactionDescriptors[i]);
	        		}
	        	}
	        	$scope.action = actionsArray[index];
	        	for(var i=0;i<$scope.actionDescriptors.length;i++){
	        		if(actionsArray[index].name ==$scope.actionDescriptors[i].name){
	        			var setSelected = $scope.actionDescriptors[i];
	        			setSelected.functionParamValues = angular.copy(actionsArray[index].actionFunctionDescriptor.functionParamValues);
	        			break;
	        		}
	        	}
	        	$scope.action={actionFunctionDescriptor:setSelected,alertLevel:actionsArray[index].alertLevel,name:actionsArray[index].name};
	        }
	        $scope.initiateDelete = function(index){
	        	$scope.indexToDelete = index;
	        	$scope.isDeleteInProgress = true;
	        }
	        $scope.cancelDelete = function(){
	        	$scope.isDeleteInProgress = false;
	        }
	        $scope.deleteAction = function(index,actionsArray){
	        	actionsArray.splice(index,1);
	        	$scope.isDeleteInProgress = false;
	        }
	        $scope.closeNewActionPanel = function(){
	        	$scope.isAddNewAction = false;
	        }
	        $scope.saveAction = function(actionsArray){
	        	if($scope.isEditAction){
	        		actionsArray[$scope.indexOfActionToEdit] = $scope.action;
	        		$scope.isAddNewAction = false;
	        	}else{
	        		$scope.isAddNewAction = false;
		        	$scope.action.name = $scope.action.actionFunctionDescriptor.name;
		        	actionsArray.push($scope.action);
	        	}
	        }
	        $scope.getDateFromLong = function(milliSec){
	        	return utilService.getDateFromLong(milliSec);
	        }
	        $scope.deleteRule = function(rule,isMultiple){
	        	$scope.isMultipleRulesSelected = isMultiple;
	    		$scope.operationMessage="Delete Rule";
	    		$scope.ruleObj =rule; 
	    		$('#actionConfirmation').modal({
	    	        show: true,
	    	        keyboard: false,
	    	        backdrop: 'static'       
	    	    });	
	    	}
	    	$scope.operation = function(){
	    		$('#loaderdiv').show();
	    		var selectedRulesList = [];
	    		if($scope.isMultipleRulesSelected){
	    			selectedRulesList = $scope.getAllSelectedRulesList();
	    		}else
	    			selectedRulesList.push($scope.ruleObj.name);
	    		teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'deleteRule',
					'methodType': 'UPDATE',
					'params' :{
						ruleNames   : selectedRulesList,
						version: $scope.selectedApp.config.version
					}
				}).then(function (data) {
					$('#loaderdiv').hide();
					$('#actionConfirmation').modal('hide');
					$scope.filteredValues.allRulesChecked = false;
					getApplicationRules();
						/*$scope.notification = {
					            severity: 'info',
					            msg : data,
					            show: true
					    };*/
				}, function (failReason) {
					$('#loaderdiv').hide();
					$('#actionConfirmation').modal('hide');
					$scope.notification = {
					      severity: 'error',
					      msg : failReason.message,
					      show: true
					};
				});
	    	}
	    	$scope.sort = {
		            column: '',
		            descending: false
		    }
			$scope.onSortCall = function(column) {
	            var sort = $scope.sort;
	 
	            if (sort.column == column) {
	                sort.descending = !sort.descending;
	            }else if(sort.column == ''){
	            	 sort.column = column;
	                 sort.descending =  !sort.descending;
	            }else {
	                sort.column = column;
	                sort.descending = false;
	            }
	        }
	    	$scope.isConditionsValid = function(conditions){
	    		if(conditions.data.op==undefined || conditions.data.op == null || conditions.data.op == ""){
	        		return false;
	        	}else if(conditions.data.exp.length <= 1){
	        		if(conditions.$$hashKey != undefined)
	        			return false;
	        	}
	        	for(var i=0 ; i < conditions.data.exp.length ; i++ ){
	        		if(conditions.data.exp[i].isNode){
	        			 if(!$scope.isConditionsValid(conditions.data.exp[i]))
	        				 return false;
	        		}
	        		else if(conditions.data.exp[i].data.exp.value.toString() == "" || conditions.data.exp[i].data.exp.value == null || conditions.data.exp[i].data.exp.attribute.name == null || conditions.data.exp[i].data.exp.attribute.name =="" ||conditions.data.exp[i].data.exp.operator==null || conditions.data.exp[i].data.exp.operator=="")
	        				return false;
	        	}
	        	return true;
	    	}
	    	$scope.filterFunction = function(rule){
				if(($scope.search.name == undefined || $scope.search.name == null || $scope.search.name == "" || (angular.uppercase(rule.name.split('$')[2]).indexOf(angular.uppercase($scope.search.name)) > -1)) &&
				   ($scope.search.entity == undefined || $scope.search.entity == null || $scope.search.entity == "" || ((angular.uppercase($scope.ruleEntityAndAttibutes[rule.name.split('$')[1]].displayName)).indexOf(angular.uppercase($scope.search.entity)) > -1)) &&
				   ($scope.search.userName == undefined || $scope.search.userName == null || $scope.search.userName == "" || (angular.uppercase(rule.userName).indexOf(angular.uppercase($scope.search.userName)) > -1)) &&
				   ($scope.search.createdDate == undefined || $scope.search.createdDate == null || $scope.search.createdDate == "" || (angular.uppercase($scope.getDateFromLong(rule.createdDate)).indexOf(angular.uppercase($scope.search.createdDate)) > -1)) &&
				   ($scope.search.modifiedDate == undefined || $scope.search.modifiedDate == null || $scope.search.modifiedDate == "" || (angular.uppercase($scope.getDateFromLong(rule.modifiedDate)).indexOf(angular.uppercase($scope.search.modifiedDate)) > -1)) &&
				   ($scope.search.enabled == undefined || $scope.search.enabled == null || $scope.search.enabled == "" || (angular.uppercase((rule.enabled).toString()).indexOf(angular.uppercase($scope.search.enabled)) > -1))){
					return true;
				}
				else
					return false;
			}
	    	$scope.updateRuleState = function(toEnable){
	    		var selectedRulesList=[];
	    		selectedRulesList = $scope.getSelectedEnableDisableRulesList(!toEnable);
	    		teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'updateRulesState',
					'methodType': 'UPDATE',
					'params' :{
						ruleNames   : selectedRulesList,
						isEnable	   : toEnable,
						version: $scope.selectedApp.config.version
					}
				}).then(function (data) {
					$('#loaderdiv').hide();
					getApplicationRules();
					$scope.filteredValues.allRulesChecked = false;
					/*$scope.notification = {
				            severity: 'info',
				            msg : data,
				            show: true
				    };*/
				}, function (failReason) {
					$('#loaderdiv').hide();
					getApplicationRules();
					$scope.filteredValues.allRulesChecked = false;
					$scope.notification = {
				            severity: 'error',
				            msg : failReason.message,
				            show: true
				    };
				});
	    		
	    	}
	    	//Check user has permission or not
			$scope.hasPermission = function(permission){
				var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
				return utilService.checkPermission(permission,privileges);
			}; 
	    	$scope.reloadPage = function(){
				$('#loaderdiv').show();
				getApplicationRules();
			}
	        getApplicationRules();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
}]);
beConfigModule.filter('orderObjectBy', function(){
	 return function(input, attribute) {
	    if (!angular.isObject(input)) return input;
	    
	    var array = [];
	    for(var objectKey in input) {
	    	input[objectKey]['newKey'] = objectKey; 
	           array.push(input[objectKey]);
	    }

	    array.sort(function(a, b){
	        a = parseInt(a[attribute]);
	        b = parseInt(b[attribute]);
	        return a - b;
	    });
	    return array;
	 }
});
	