/**
 * Created by Priyanka on 11/01/16
 */
beConfigModule.service('savedAlerts', function() {
    var stringValue = '';    
    return {
        getAlert: function() {
            return stringValue;
        },
        setAlert: function(value) {
            stringValue = value;
        }
    }
});

beConfigModule.controller("alertsCtrl",['$scope','$location','$rootScope','$q','$timeout', 'savedAlerts', 'teaLocation', 'teaObjectService','teaEventNotifications' , 'teaScopeDecorator','ReferenceObjectConfigService','utilService','StorageService',
                                       function ($scope,$location, $rootScope,$q,$timeout, savedAlerts, teaLocation, teaObjectService, teaEventNotifications , teaScopeDecorator ,ReferenceObjectConfigService,utilService,StorageService){
	$('#loaderdiv').show();
	$rootScope.loadingComplete.then(function( apps ){ 
			$('#loaderdiv').hide();
			$scope.selectedApp=ReferenceObjectConfigService.getSelectedApp();
			$scope.ruleEntityAndAttibutes = StorageService.getRuleAttributes();
			$scope.loggedInUser = StorageService.getLoggedInUser();
			$scope.filteredValues={};
			$scope.search={};
			$scope.filteredValues.allAlertsChecked = false;
			$scope.filteredValues.isAdvancedSearch = false;
			$scope.sortvalues={AlertLevel:false,IsSetAction:false,ruleName:false,userName:false,created_time:true,AlertType:false,lastColumnSorted:'created_time'};
			//pagination data
			$scope.pageSizes = [5,10,25,50];
			$scope.itemsPerPage = 10;
			$scope.pagedItems = [];
			$scope.currentPage = 0;
			$scope.filterdItems=[];
			$scope.previousAlerts;
			//End of pagination data
			$scope.dismiss =function(){
				$scope.notification.show = false;
				if(!utilService.getAgentReachable()){
					$location.path("/");
				}
			}
			 
			$scope.checkPreviousAlerts = function(alert){
				for(var i = 0; i < $scope.previousAlerts.length; i++){
					if(alert.ruleName == $scope.previousAlerts[i].ruleName)
						return "previousAlerts";
				}
				
				return "newAlerts";
			}
			
			
			function getApplicationAlerts(){
				var isRuleAdminPermission = $scope.hasPermission(30);
				$('#loaderdiv').show();
				teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'getAlerts',
					'methodType': 'READ',
					'params' :{
						isRuleAdminPermission:isRuleAdminPermission
					}
				}).then(function (data) {
					 $scope.previousAlerts = savedAlerts.getAlert();
					 $scope.applicationAlerts = data;
					 savedAlerts.setAlert(data);
					 $scope.filterData(); //filter data to already set values after refresh
					 $('#loaderdiv').hide();
				}, function (failReason) {
				});	
			}
		//pagination code
			$scope.perPage = function(itemsPerPage){
				$scope.itemsPerPage = itemsPerPage;
				$scope.groupToPages();
			};
			$scope.groupToPages = function () {
				$scope.pagedItems = [];
				for (var i = 0; i < $scope.filterdItems.length; i++) {
				   if (i % $scope.itemsPerPage === 0) {
				      $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)] = [ $scope.filterdItems[i] ];
				   }else {
				      $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)].push($scope.filterdItems[i]);
				   }
				}
			};
			$scope.range = function (start, end) {
				var ret = [];
				if (!end) {
				   end = start;
				   start = 0;
				}
				for (var i = start; i < end; i++) {
				   ret.push(i);
				}
				return ret;
		   };
		   $scope.prevPage = function () {
			   if($scope.currentPage > 0) {
				  $scope.currentPage--;
			   }
		   };
		   $scope.nextPage = function () {
			   if ($scope.currentPage < $scope.pagedItems.length - 1) {
				  $scope.currentPage++;
			   }
		   };
		   $scope.setPage = function () {
			   $scope.currentPage = this.n;
		   };
		//end of pagination code
			
			$scope.selectAllAlerts = function () {			
	            for (var i = 0; i < $scope.filterdItems.length; i++) {
	            		$scope.filterdItems[i].isChecked = $scope.filteredValues.allAlertsChecked;
	            }
	            $scope.perPage($scope.itemsPerPage);
		    }
			$scope.selectAlertsEntity = function () {          
				for (var i = 0; i < $scope.filterdItems.length; i++) {
					if (!$scope.filterdItems[i].isChecked) {
						$scope.filteredValues.allAlertsChecked = false;
						$scope.perPage($scope.itemsPerPage);
						return;
					}
				}          
				$scope.filteredValues.allAlertsChecked = true;
				$scope.perPage($scope.itemsPerPage);
			}
			function getSelectedAlertsList(){
	    		var selectedAlertssList = []; 
				if($scope.filterdItems!=undefined){
					for (var i = 0; i < $scope.filterdItems.length; i++) {
			            if ($scope.filterdItems[i].isChecked) {
			                var alert = $scope.filterdItems[i];
			                selectedAlertssList.push(alert.alert_id);	                   
			            }
			        } 
	    		}
				return selectedAlertssList;
			}
			$scope.showOperations = function(){
		    	return getSelectedAlertsList().length > 0; 
		    }
			$scope.clearApplicationAlerts = function(){
				var isRuleAdminPermission = $scope.hasPermission(30);
				var selectedAlertssList=[];
				if(isRuleAdminPermission)
					selectedAlertssList = getSelectedAlertsList();
				else{
					if($scope.filterdItems!=undefined){
						for (var i = 0; i < $scope.filterdItems.length; i++) {
				            if ($scope.filterdItems[i].isChecked && ($scope.filterdItems[i].userName == $scope.loggedInUser)) {
				                var alert = $scope.filterdItems[i];
				                selectedAlertssList.push(alert.alert_id);	                   
				            }
				        } 
		    		}
				}
				if(selectedAlertssList.length  < 1){
					$scope.notification = {
				            severity: 'error',
				            msg : 'User not allowed to clear these alerts',
				            show: true
					};
					return;
				}
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
					'agentID'  : $scope.selectedApp.agentId,
					'agentType': $scope.selectedApp.type.agentType,
					'objectType': $scope.selectedApp.type.name,           		
					'objectKey': $scope.selectedApp.key,
					'operation' : 'clearAlerts',
					'methodType': 'UPDATE',
					'params' :{
						alertIds   : selectedAlertssList
					}
				}).then(function (data) {
					 $('#loaderdiv').hide(); 
					 /*$scope.notification = {
					            severity: 'info',
					            msg : data,
					            show: true
							};*/
					 getApplicationAlerts();
					 $scope.filteredValues.allAlertsChecked = false;
				}, function (failReason) {
					$('#loaderdiv').hide(); 
					getApplicationAlerts();
					$scope.filteredValues.allAlertsChecked = false;
					$scope.notification = {
				            severity: 'error',
				            msg : failReason.message,
				            show: true
						};
				});	
			}
			$scope.getDateFromLong = function(milliSec){
	        	return utilService.getDateFromLong(milliSec);
	        }
			$scope.sortArray = function(columnName,type){
				 $scope.sortvalues.lastColumnSorted = columnName;
				 $scope.sortvalues[columnName] = !type;
				 if(!type){//descending
					 if(columnName == 'created_time'){
						 $scope.filterdItems.sort(function(a, b) {
			    				return parseInt(b[columnName]) - parseInt(a[columnName]);
						 });  
					 }else{
						 $scope.filterdItems.sort( function(a, b) {
							   if (a[columnName].toString() >b[columnName].toString()) return -1;
							   else if (a[columnName].toString()<b[columnName].toString()) return 1;
							   else return 0;
						 }); 
					 }
				 }else{//ascending
					 if(columnName == 'created_time'){
						 $scope.filterdItems.sort(function(a, b) {
			    				return parseInt(a[columnName]) - parseInt(b[columnName]);
						 }); 
					 }else{
						 $scope.filterdItems.sort( function(a, b) {
							   if (b[columnName].toString() >a[columnName].toString()) return -1;
							   else if (b[columnName].toString()<a[columnName].toString()) return 1;
							   else return 0;
						 });
					 }
				 }
				 $scope.perPage($scope.itemsPerPage);
			}
			$scope.filterData = function(){
				$scope.filterdItems = angular.copy($scope.applicationAlerts);		
				$scope.filteredValues.allAlertsChecked = false;
				for (var i = $scope.applicationAlerts.length - 1; i >= 0; i--) {
					var alert = $scope.applicationAlerts[i];
					if(!(($scope.search.AlertType == undefined || $scope.search.AlertType == null || $scope.search.AlertType == "" || (angular.uppercase(alert.AlertType).indexOf(angular.uppercase($scope.search.AlertType)) > -1)) &&
							   ($scope.search.AlertLevel == undefined || $scope.search.AlertLevel == null || $scope.search.AlertLevel == "" || (angular.uppercase(alert.AlertLevel).indexOf(angular.uppercase($scope.search.AlertLevel)) > -1)) &&
							   ($scope.search.ruleName == undefined || $scope.search.ruleName == null || $scope.search.ruleName == "" || (angular.uppercase(alert.ruleName).indexOf(angular.uppercase($scope.search.ruleName)) > -1)) &&
							   ($scope.search.created_time == undefined || $scope.search.created_time == null || $scope.search.created_time == "" || (angular.uppercase($scope.getDateFromLong(alert.created_time)).indexOf(angular.uppercase($scope.search.created_time)) > -1)) &&
							   ($scope.search.userName == undefined || $scope.search.userName == null || $scope.search.userName == "" || (angular.uppercase(alert.userName).indexOf(angular.uppercase($scope.search.userName)) > -1)) &&
							   ($scope.search.IsSetAction == undefined || $scope.search.IsSetAction == null || $scope.search.IsSetAction == "" || (alert.IsSetAction && (angular.uppercase('set').indexOf(angular.uppercase($scope.search.IsSetAction))) > -1) || (!alert.IsSetAction && (angular.uppercase('clear').indexOf(angular.uppercase($scope.search.IsSetAction))) > -1)))){
						$scope.filterdItems.splice(i, 1);  
					}
				}
				$scope.sortArray($scope.sortvalues.lastColumnSorted,!$scope.sortvalues[$scope.sortvalues.lastColumnSorted]);
			}	
			//Check user has permission or not
			$scope.hasPermission = function(permission){
				var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
				return utilService.checkPermission(permission,privileges);
			};
			$scope.reloadPage = function(){
				$('#loaderdiv').show();
				getApplicationAlerts();
			}
			/*$scope.changeBackgroundColor = function(isExpanded,isMouseEnter,alertLevel){
				var color;
				if(alertLevel == 'High')
					color = '#F34D4D !important';
				else if(alertLevel == 'Medium')
					color = '#F3BA53 !important';
				else if(alertLevel == 'Low')
					color = '#F7F76E !important';
				else if(alertLevel == 'Normal')
					color = '#418641 !important';
				$scope.alertRowStyle={};
				if(isExpanded && isMouseEnter){
					$scope.alertRowStyle={'border-bottom':'none','background-color':color};
				}else if(!isExpanded && isMouseEnter){
					$scope.alertRowStyle={'background-color':color};
				}else if(isExpanded && !isMouseEnter){
					$scope.alertRowStyle={'border-bottom':'none','background-color':'white'};
				}
			}*/
			getApplicationAlerts();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
}]);	
	