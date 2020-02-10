/**
 * Created by Priyanka on 22/04/15
 */
beConfigModule.controller("machineViewCtrl",['$scope','$location','$rootScope','$q','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService','utilService',
                                       function ($scope,$location, $rootScope,$q,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService,utilService){
	$('#loaderdiv').show();
	$rootScope.loadingComplete.then(function( apps ){ 
			$('#loaderdiv').hide();
			$scope.hosts=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Host");
			$scope.selectedApp=ReferenceObjectConfigService.getSelectedApp();
			$scope.newHostObj={};
			
			/*Creating breadCrumbs*/
			ReferenceObjectConfigService.setBreadCrumbObject();
	 	   	ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Machines',type:{name : 'machines'}});
	 		if(Storage)
	 			localStorage.setItem("breadCrumbs", JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
			$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
			
			$scope.notification = {
		            severity: 'info',
		            msg : '',
		            show: false
			};
			$scope.dismiss =function(){
				$scope.notification.show = false;
				if(!utilService.getAgentReachable()){
					$location.path("/");
				}
			}
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
}]);	
	