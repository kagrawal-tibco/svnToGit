/**
 * Created by priyanka on 05/06/15
 */
beConfigModule.controller("appProcessingUnitsCtrl",['$scope','$rootScope','$q','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService',
                                       function ($scope,$rootScope,$q,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService){
	$('#loaderdiv').show();
	$rootScope.loadingComplete.then(function( apps ){ 
		$('#loaderdiv').hide();
		teaScopeDecorator($scope);
		$scope.processingUnits=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_ProcessingUnits");	
		
		/*Creating breadCrumbs*/
		ReferenceObjectConfigService.setBreadCrumbObject();
	 	ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Processing units',type:{name : 'processing units'}});
		if(Storage)
			localStorage.setItem("breadCrumbs",JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
		$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
}]);	
	