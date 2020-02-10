/**
 * Created by Priyanka on 15/07/15
 */
beConfigModule.controller("appAgentClassesCtrl",['$scope','$rootScope','$q','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService',
                                       function ($scope, $rootScope,$q,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService){
	$('#loaderdiv').show();
	$rootScope.loadingComplete.then(function( apps ){ 
			$('#loaderdiv').hide();	
			$scope.agents=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Agents");
			$scope.selectedApplication = ReferenceObjectConfigService.getSelectedApp();
			
			/*Creating breadCrumbs*/
			ReferenceObjectConfigService.setBreadCrumbObject();
	 	   	ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Agent Classes',type:{name : 'agent classes'}});
	 		if(Storage)
	 			localStorage.setItem("breadCrumbs",JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
			$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
			
			function getAgentInfo(){
				$scope.agentTypeList = [];
				angular.forEach($scope.agents, function(agent,index) {
					teaObjectService.invoke({
						'agentID'  : agent.agentId,
						'agentType': agent.type.agentType,
						'objectType': agent.type.name,           		
						'objectKey': agent.key,
						'operation' : 'getProcessingUnitAgent',
						'methodType': 'READ'
					}).then(function (data) {
						$scope.agents[index].config={};
						$scope.agents[index].config.agentType = data.agentType;
						if($scope.agentTypeList.length != 0 && $scope.agentTypeList.indexOf(data.agentType) < 0)
							$scope.agentTypeList.push(data.agentType);
						else if($scope.agentTypeList.length == 0)
							$scope.agentTypeList.push(data.agentType);
					}, function (failReason) {
					});	 
				});	
			}
			$scope.capitalizeFirstLetter = function(string) {
			    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
			}
			getAgentInfo();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
}]);	
	