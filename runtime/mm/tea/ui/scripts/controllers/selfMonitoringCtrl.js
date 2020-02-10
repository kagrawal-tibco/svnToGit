/**
 * Created by Priyanka on 03/06/16
 */
beConfigModule.controller("selfMonitoringCtrl",['$scope','$location','$rootScope','$q','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService','utilService',
                                       function ($scope,$location, $rootScope,$q,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService,utilService){
	 $('#loaderdiv').show();
	 $rootScope.loadingComplete.then(function( apps ){
		 $('#loaderdiv').hide();
		 $scope.isChartRefreshed = 0;
		 $scope.entity={
				 name           : "BeTeaAgent",
				 type:{
					 agentType  : "BusinessEvents",
					 name       : "BusinessEvents"
				 },
				 status:{
					 state      : "Running"
				 },
				 agentId        : "BusinessEvents",
				 objectType     : "BusinessEvents",           		
				 objectKey      : "BusinessEvents"
		 };   
		
		 var getGCDetails=function (){
			 var timer;
			teaObjectService.invoke({
				'agentID'  : $scope.entity.agentId,
	            'agentType': $scope.entity.type.agentType,
	       		'objectType': $scope.entity.type.name,           		
	       		'objectKey': "BusinessEvents",
	       		'operation' : 'getGCDetails',
	       		'methodType':'READ',
	       		'params': {
					
		       	}
			}).then(function (gcDetails) {
				timer = $timeout(getGCDetails, 60000);
				$scope.gcDetails = gcDetails;
				$('#loaderdiv').hide();
			}, function (failReason) {
				$('#loaderdiv').hide();
				console.log("failed");
			});	 
			$scope.$on("$destroy",function(event) {
                $timeout.cancel(timer);
            }); 
		}

		var getMemeoryPools=function (){
			teaObjectService.invoke({
					'agentID'  : $scope.entity.agentId,
		            'agentType': $scope.entity.type.agentType,
		       		'objectType': $scope.entity.type.name,           		
		       		'objectKey': $scope.entity.key,
		       		'operation' : 'getMemeoryPools',
		       		'methodType':'READ',
		       		'params': {
						
			       	}
			}).then(function (memeoryPools) {						
					$scope.memeoryPoolsList = memeoryPools;	
					$scope.memoryPoolName = $scope.memeoryPoolsList[0];
					$('#loaderdiv').hide();
			}, function (failReason) {
					console.log("failed");
					$('#loaderdiv').hide();
			});	 
		}
		 $scope.reloadPage = function(){
				$scope.isChartRefreshed++;
				getMemeoryPools();
				getGCDetails();
		 }
		 getMemeoryPools();
		 getGCDetails();
		 $scope.chartSections = ReferenceObjectConfigService.getSelfChartsConfig();
	 }, function (failReason) {
			$('#loaderdiv').hide(); 
	 });
}]);	
	