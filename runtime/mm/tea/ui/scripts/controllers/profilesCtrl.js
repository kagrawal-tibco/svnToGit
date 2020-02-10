/**
 * Created by Rohini Jadhav on 28/07/16
 */

beConfigModule.directive('inputDropdown', function($compile) {
    
    var template = 
        '<input class="inputStyle" type="text" ng-click="setOffset($event)" ng-change="inputChange()"  ng-model="ngModel" placeholder="Name">' +
        '<ul id="appPropdropdown-menu" class="appPropdropdown-menu dropdown-menu" ng-if="keyList.length">' + 
            '<li ng-repeat="value in keyList" ng-mousedown="select($event, value, ngModel)">' +
                '{{value}}</li>' + 
            '</li>' +
        '</ul>';
    

    return {
        restrict: 'EA',
        scope: {
            ngModel: '=',
            list: '=',
			filterListMethod: '&',
            onSelect: '&'
        },
        template: template,
        link: function(scope, element, attrs) {
            element.addClass('input-dropdown');
			scope.keyList = scope.list || [];
			scope.listOfKey = [];
			angular.copy(scope.list, scope.listOfKey);
			scope.setOffset = function(e){
				var topPosition = parseInt(e.pageY - 145);
				$(".appPropdropdown-menu").css({'top' : topPosition});
			}
			
			scope.inputChange = function(){
				if (!scope.ngModel) {
                   scope.keyList = scope.list || [];
                   return;
                 }

				if (scope.filterListMethod) {
					var promise = scope.filterListMethod({userInput: scope.ngModel});
					if (promise) {
						promise.then(function(keyList) {
							scope.keyList = keyList;
						});
					}
				}
			}
			
            scope.select = function(e, value, oldValue) {
                scope.ngModel = value;
                scope.keyList.splice(scope.keyList.indexOf(value), 1);
				if(scope.listOfKey.indexOf(oldValue) != -1)
					scope.keyList.push(oldValue);
				scope.onSelect({$event: e, value: value});
            };
			
        }
    };
});

beConfigModule
		.controller(
				"profilesViewCtrl",
				[
						'$scope',
						'$sce',
						'$q',
						'$location',
						'$rootScope',
						'$timeout',
						'fileUpload',
						'teaLocation',
						'teaObjectService',
						'teaScopeDecorator',
						'$http',
						'StorageService',
						'ReferenceObjectConfigService',
						'utilService',
						function($scope, $sce, $q, $location, $rootScope,
								$timeout, fileUpload, teaLocation,
								teaObjectService, teaScopeDecorator, $http,
								StorageService, ReferenceObjectConfigService,
								utilService) {
							$('#loaderdiv').show();
							$rootScope.loadingComplete
									.then(
											function(apps) {
												$('#loaderdiv').hide();
												$scope.profiles = ReferenceObjectConfigService.getReferenceObject("profiles");
												$scope.profileObject = ReferenceObjectConfigService.getReferenceObject("profiles");			
												$scope.isCreate = true;
												$scope.isEdit = true;
												$scope.isProfileEdit = false;
												$scope.configurationType = "global";
												$scope.isDuplcateProfileName = false;
												$scope.globalVariables = [];
												$scope.systemProperties = [];
												$scope.beProperties = [];
												$scope.bpKey = [];
												$scope.spKey = [];
												$scope.profileVariables = {};
												$scope.profileName = "";
												$scope.configurationChangesFlags = {};
												$scope.configurationChangesFlags.isSystemVariableChanged = false;
												$scope.configurationChangesFlags.isBEPropChanged = false;
												$scope.configurationChangesFlags.isGlobalVariableChanged = false;		
												$scope.instanceGlobalVariables = [];
												$scope.isLoad = false;
												if (ReferenceObjectConfigService.getSelectedApp() != undefined)
													$scope.selectedAppName = ReferenceObjectConfigService.getSelectedApp();
												   

												$scope.showOperations = function() {
													return getSelectedProfilesList().length > 0;
												}
												$scope.filterStringList = function(userInput, configurationType) {
													var filter = $q.defer();
													var normalisedInput = userInput.toLowerCase();
													var filteredArray = [];
													if(configurationType == 'SYSPROPS'){
													    $.each($scope.spKey, function(index, key){
															if(key.indexOf(normalisedInput) != -1)
																filteredArray.push(key);
														})
													}
													if(configurationType == 'BEPROPS'){
													    $.each($scope.bpKey, function(index, key){
															if(key.indexOf(normalisedInput) != -1)
																filteredArray.push(key);
														})
													}
													filter.resolve(filteredArray);
													return filter.promise;
												};
												
												$scope.showCopyProfile = function(){
													var profile = $scope.selectedProfile;
													if(profile == undefined)
													   return true;
													else 
														return false;
												}
												
												//popup window to confirm profile change
												var optionTarget;
												
												function changeProfileSelectionConfirm(){
													$scope.operationMessage="Change Profile";
													$('#actionConfirmation').modal({
												        show: true,
												        keyboard: false,
												        backdrop: 'static'       
												    });	
												}
												$scope.operation = function(){//set application default profile code
													$('#loaderdiv').show();
													$('#actionConfirmation').modal('hide');
													$(optionTarget).prop('checked', true);
													var profile = $scope.selectedProfile;
													var profileName = ""
													if (undefined !== profile)
														profileName = profile.name;
													$('#loaderdiv').show();
													
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'setApplicationDefaultProfile',
														'methodType' : 'UPDATE',
														'params' : {
																	profileName : profileName
														}
													})
													.then(function(data) {
														$('#loaderdiv').hide();
														getApplicationInfo();
																			
													},
													function(failReason) {
														$('#loaderdiv').hide();
														$scope.notification = {
														    severity : 'error',
															msg : failReason.message,
															show : true
														};
													});
												}
												
												loadInstanceGlobalVariables();
												
												function loadInstanceGlobalVariables() {
													teaObjectService.invoke({
														'agentID'  : $scope.selectedAppName.agentId,
														'agentType': $scope.selectedAppName.type.agentType,
														'objectType': $scope.selectedAppName.type.name,           		
														'objectKey': $scope.selectedAppName.key,
														'operation' : 'getApplicationGVNameAndType',
														'methodType':'READ'
													}).then(function (gv) {
														if(gv != "")
														   $scope.instanceGlobalVariables = gv;														
													}, function (failReason) {
														console.trace(failReason);
													});
													
												}

												$scope.dismiss = function() {
													$scope.notification.show = false;
													if (!utilService.getAgentReachable()) {
														$location.path("/");
													}
												}

												$scope.sort = {
													column : '',
													descending : false
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

												function getProfilesList() {
													$scope.profiles = [];
													$scope.profileConfigType = {};
													var profiles = $scope.selectedAppName.config.profiles;
													for (var i = 0; i < profiles.length; i++) {
														var profile = profiles[i];
														$scope.profileConfigType[profile] = "global";
														profile = new String(profile);
														$scope.profiles.push({
															'name': profiles[i]
														});
													}
												}
												
												$scope.showCopyProfileWizard = function() {
													$scope.operationMessage="Copy Profile";
													$('#copyApplicationProfile').modal({
												        show: true,
												        keyboard: false,
												        backdrop: 'static'       
												    });	
												}

												
											   function loadProperties() {
													$scope.beProp = [];
													$scope.bpKey = [];
													$scope.spProp = [];
													$scope.spKey = [];
													teaObjectService.invoke({
										            	'agentID'  : $scope.selectedAppName.agentId,
										            	'agentType': $scope.selectedAppName.type.agentType,
										            	'objectType': $scope.selectedAppName.type.name,
										            	'objectKey': $scope.selectedAppName.key,
										            	'operation' : 'getApplicationPropperties',
										            	'methodType': 'UPDATE'
										        	}).then(function (data) {
														$scope.globalVariables = [];
														
														var gv = data['GV'];
														var systemProps = data['SYSTEMPROPS'];
														var beProps = data['BEPROPS'];
													
														if (typeof gv !== 'undefined'|| null !== gv) {
															$.each(gv, function(index, value) {
																var entry = {
																	key : index,
																	value : value
																};
																$scope.globalVariables.push(entry);
															})
														
														}
														if (typeof systemProps !== 'undefined' || null !== systemProps) {
															$.each(systemProps, function(index, value) {
																var entry = {
																	key : index,
																	value : value
																};
																$scope.spProp.push(entry);
															    $scope.spKey.push(entry.key);
																for(var i = 0; i < $scope.systemProperties.length; i++){
																	if($scope.systemProperties[i].key == entry.key)
																		$scope.spKey.splice($scope.spKey.indexOf(entry.key), 1);
																} 
															})
															$scope.configurationChangesFlags.isSystemVariableChanged = true;
														}

														if (typeof beProps !== 'undefined' || null !== beProps) {
															$.each(beProps, function(index, value) {
																var entry = {
																	key : index,
																	value : value
																};
															    $scope.beProp.push(entry);
																$scope.bpKey.push(entry.key);
															    for(var i = 0; i < $scope.beProperties.length; i++){
																	if($scope.beProperties[i].key == entry.key)
																		$scope.bpKey.splice($scope.bpKey.indexOf(entry.key), 1);
																} 
															})
															$scope.configurationChangesFlags.isBEPropChanged = true;
														}
														
										        	}, function (failReason) {
										            });
												}

												$scope.showAddProfileWizard = function() {
													$scope.systemProperties = [];
									             	$scope.beProperties = [];
												    loadProperties();
													$scope.profileName = "";
													$scope.configurationType = "global";
													$scope.configurationChangesFlags = {};
													$scope.configurationChangesFlags.isSystemVariableChanged = false;
													$scope.configurationChangesFlags.isBEPropChanged = false;
													$scope.configurationChangesFlags.isGlobalVariableChanged = false;
													$scope.isEditProfile = false;
													$scope.isDuplcateProfileName = false;
													$('#addApplicationProfile').modal(
														{
															show : true,
															keyboard : false,
															backdrop : 'static'
														});
												}

												$scope.showEditProfileWizard = function(profile) {
													$scope.isEditProfile = true;
													$scope.configurationType = "global";
													$scope.selectedProfile = profile;
												//	$scope.globalVariables = [];
												//	$scope.systemProperties = [];
												//	$scope.beProperties = [];
													$scope.profileName = profile.name;
													$scope.configurationChangesFlags = {};
													$scope.configurationChangesFlags.isSystemVariableChanged = false;
													$scope.configurationChangesFlags.isBEPropChanged = false;
													$scope.configurationChangesFlags.isGlobalVariableChanged = false;
													$scope.isDuplcateProfileName = false;
													$scope.getApplicationProfileDetails(profile);
													
													$('#addApplicationProfile').modal(
													{
														show : true,
														keyboard : false,
														backdrop : 'static'
													});
												}

												$scope.showDeleteProfileWizard = function(profile) {
													$scope.profileName = profile.name;
													$scope.isEditProfile = false;
													$('#deleteApplicationProfile').modal(
													{
														show : true,
														keyboard : false,
														backdrop : 'static'
													});
												}

												$scope.removeVariable = function(index) {
													if ($scope.configurationType === 'global') {
														$scope.globalVariables.splice(index, 1);
														$scope.configurationChangesFlags.isGlobalVariableChanged = true;
													} else if ($scope.configurationType === 'systemProps') {
														$scope.systemProperties.splice(index, 1);
														$scope.configurationChangesFlags.isSystemVariableChanged = true;
													} else if ($scope.configurationType === 'beProps') {
														for(var i = 0; i < $scope.beProp.length; i++){
															if($scope.beProperties[index].key == $scope.beProp[i].key)
																$scope.bpKey.push($scope.beProp[i].key);
														}
														$scope.beProperties.splice(index, 1);
														$scope.configurationChangesFlags.isBEPropChanged = true;
													}
												}

												$scope.isDisabled = function() {
													return !(($scope.configurationChangesFlags.isGlobalVariableChanged
															|| $scope.configurationChangesFlags.isSystemVariableChanged || $scope.configurationChangesFlags.isBEPropChanged) && this.profileName != undefined && this.profileName != "") ? true
															: false;
												}
												
												$scope.copyApplicationProfile = function(){
													$scope.newProfileName = $('#copyprofileNameTextField').val();
													$scope.selectedProfile = $scope.selectedApp.config.defaultProfile;
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'copyApplicationProfile',
														'methodType' : 'UPDATE',
															'params' : {
															oldProfileName : $scope.selectedProfile,
															newProfileName : $scope.newProfileName
														}
													})
													.then(function(resp) {
														$('#loaderdiv').hide();
														$scope.isDuplcateProfileName = false;
														$('#copyApplicationProfile').modal('hide');
														$('#loaderdiv').show();
													    $('#copyprofileNameTextField').val("");
														getApplicationInfo();
													},
													function(failReason) {
														$scope.isDuplcateProfileName = true;
													});

												}
												
												$scope.saveApplicationProfile = function() {
													$scope.profileName = $('#profileNameTextField').val();
													var gv = {};
													var systemProps = {};
													var beProps = {};
													for (var i = 0; i < $scope.globalVariables.length; i++) {
														var entry = $scope.globalVariables[i];
														removeDuplicate(i, entry);
														if(entry.key!='undefined' && $.trim(entry.key)!='')
															gv[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.systemProperties.length; i++) {
														var entry = $scope.systemProperties[i];
														removeDuplicate(i, entry);
														if(entry.key!='undefined' && $.trim(entry.key)!='')
															systemProps[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.beProperties.length; i++) {
														var entry = $scope.beProperties[i];
														removeDuplicate(i, entry);
														if(entry.key!='undefined' && $.trim(entry.key)!='')
															beProps[entry.key] = entry.value;
													}
													
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'saveApplicationProfile',
														'methodType' : 'UPDATE',
														'params' : {
															profileName : $scope.profileName,
															globalVariables : gv,
															systemProperties : systemProps,
															beProperties : beProps,
															isEdit : false
														}
													})
													.then(function(resp) {
														$('#loaderdiv').hide();
														$scope.globalVariables = [];
														$scope.systemProperties = [];
														$scope.beProperties = [];		
														$scope.isDuplcateProfileName = false;
														$('#addApplicationProfile').modal('hide');
														$('#loaderdiv').show();
														$('#profileNameTextField').val("");
														getApplicationInfo();
													},
													function(failReason) {
														$scope.isDuplcateProfileName = true;
													});

												}
												$scope.editApplicationProfile = function() {
													var gv = {};
													var systemProps = {};
													var beProps = {};
													for (var i = 0; i < $scope.globalVariables.length; i++) {
														var entry = $scope.globalVariables[i];
														removeDuplicate(i, entry);
														if(entry.key!='undefined' && $.trim(entry.key)!='')
															gv[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.systemProperties.length; i++) {
														var entry = $scope.systemProperties[i];
														removeDuplicate(i, entry);
														if(entry.key!='undefined' && $.trim(entry.key)!='')
															systemProps[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.beProperties.length; i++) {
														var entry = $scope.beProperties[i];
														removeDuplicate(i, entry);
														if(entry.key!='undefined' && $.trim(entry.key)!='')
															beProps[entry.key] = entry.value;
													}
													$('#addApplicationProfile').modal('hide');
													$('#loaderdiv').show();
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'saveApplicationProfile',
														'methodType' : 'UPDATE',
														'params' : {
															profileName : $scope.profileName,
															globalVariables : gv,
															systemProperties : systemProps,
															beProperties : beProps,
															isEdit : true
														}
													})
													.then(function(resp) {
														$('#loaderdiv').hide();
														$scope.isDuplcateProfileName = false;
														getApplicationInfo();
													},
													function(failReason) {
														$('#loaderdiv').hide();
														$scope.isDuplcateProfileName = true;
														$scope.notification = {
															severity : 'error',
															msg : failReason.message,
															show : true
														};
													});

												}


												$scope.getApplicationProfileDetails = function(profileName) {
													$scope.selectedObj = $scope.selectedAppName;
													$('#loaderdiv').show();
													$scope.configurationType = "global";
													$scope.profileName = profileName.name;
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'getApplicationProfileDetails',
														'methodType' : 'READ',
														'params' : {
															profileName : $scope.profileName
														}
													})
													.then(function(resp) {
														var gv = resp['GV'];
														var systemProps = resp['SYSTEMPROPS'];
														var beProps = resp['BEPROPS'];
														$scope.globalVariables = [];
														$scope.systemProperties = [];
														$scope.beProperties = [];
														if (typeof gv !== 'undefined'|| null !== gv) {
															$.each(gv, function(index, value) {
																var entry = {
																	key : index,
																	value : value
																};
																$scope.globalVariables.push(entry);
																$scope.checkGV(entry);
															})
															$scope.profileVariables[profileName.name + "_gv"] = $scope.globalVariables;
														}
														if (typeof systemProps !== 'undefined' || null !== systemProps) {
															$.each(systemProps, function(index, value) {
																var entry = {
																	key : index,
																	value : value
																};
																$scope.systemProperties.push(entry);
															})
															$scope.profileVariables[profileName.name + "_sysProps"] = $scope.systemProperties;
														}

														if (typeof beProps !== 'undefined' || null !== beProps) {
															$.each(beProps, function(index, value) {
																var entry = {
																	key : index,
																	value : value
																};
															    $scope.beProperties.push(entry);
															})
															$scope.profileVariables[profileName.name + "_beProps"] = $scope.beProperties;
														}
														loadProperties();
														$('#loaderdiv').hide();
														},
													function(failReason) {
														$('#loaderdiv').hide();
															$scope.notification = {
															    severity : 'error',
																msg : failReason.message,
																show : true
															};
													});

												}

												$scope.updateProfile = function(profileName) {
													$scope.profileName = profileName;
												}

												function removeDuplicate(index, globalVariable) {
													if ($scope.configurationType === 'global') {
														for (var i = 0; i < $scope.globalVariables.length; i++) {
															var entry = $scope.globalVariables[i];
															if (index !== i) {
																if (entry.key === globalVariable.key
																		&& entry.value === entry.value) {
																	$scope.globalVariables.splice(i, 1);
																}
															}
														}
													} else if ($scope.configurationType === 'systemProps') {
														for (var i = 0; i < $scope.systemProperties.length; i++) {
															var entry = $scope.systemProperties[i];
															if (index !== i) {
																if (entry.key === globalVariable.key
																		&& entry.value === entry.value) {
																	$scope.systemProperties.splice(i, 1);
																}
															}
														}
													} else if ($scope.configurationType === 'beProps') {
														for (var i = 0; i < $scope.beProperties.length; i++) {
															var entry = $scope.beProperties[i];
															if (index !== i) {
																if (entry.key === globalVariable.key
																		&& entry.value === entry.value) {
																	$scope.beProperties.splice(i, 1);
																}
															}
														}
													}
												}
												
												$scope.checkGV=function(gv){
													$.each($scope.instanceGlobalVariables, function(key, val) {
														if(key == gv.key)
															 gv.type = val;
													});													
												}

												$scope.addNewProperty = function() {
													var entry = {
														key : null,
														value : null
													};
													if ($scope.configurationType === 'global') {
														$scope.configurationChangesFlags.isGlobalVariableChanged = true;
														$scope.globalVariables.push(entry);
													} else if ($scope.configurationType === 'systemProps') {
														$scope.configurationChangesFlags.isSystemVariableChanged = true;
														$scope.systemProperties.push(entry);
													} else if ($scope.configurationType === 'beProps') {
														$scope.configurationChangesFlags.isBEPropChanged = true;
														$scope.beProperties.push(entry);
													}
												}

												$scope.setConfigurationType = function(type) {
														$scope.configurationType = type;
												}
												
												$scope.setProfileConfigType = function(type, profile) {
													if (undefined === profile)
														$scope.configurationType = type;
													else
														$scope.profileConfigType[profile.name] = type;
												}

												$scope.isSetConfigType = function(type, profile) {
													if (undefined === profile)
														return $scope.configurationType === type;
													else
														return $scope.profileConfigType[profile.name] === type;

												}

												$scope.notification = {
													severity : 'info',
													msg : '',
													show : false
												};

												function populateAllApplications() {
													var deferredRequest = StorageService.getApplication();
													deferredRequest.then(function(apps) {
														$scope.appObject = apps;
													},
													function(failReason) {
													});
												}
												$scope.deleteApplicationProfile = function() {
													$('#deleteApplicationProfile').modal('hide');
													$('#loaderdiv').show();
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'deleteApplicationProfile',
														'methodType' : 'UPDATE',
														'params' : {
															profileName : $scope.profileName
														}
													})
													.then(function(resp) {
														$('#loaderdiv').hide();
														getApplicationInfo();
													},
													function(failReason) {
														$('#loaderdiv').hide();
														$scope.notification = {
															severity : 'error',
															msg : failReason.message,
															show : true
														};
													});

												}
												$scope.setApplicationDefaultProfile = function(event,profile) {
													$scope.selectedProfile = profile;
													event.preventDefault();
													optionTarget = event.target;
													changeProfileSelectionConfirm();
												}
												function getApplicationInfo() {
													teaObjectService.invoke(
													{
														'agentID' : $scope.selectedAppName.agentId,
														'agentType' : $scope.selectedAppName.type.agentType,
														'objectType' : $scope.selectedAppName.type.name,
														'objectKey' : $scope.selectedAppName.key,
														'operation' : 'getApplication',
														'methodType' : 'READ'
													})
													.then(function(data) {
														$scope.selectedAppName.config.author = data.author;
														$scope.selectedAppName.config.clusterName = data.clusterName;
														$scope.selectedAppName.config.comments = data.comments;
														$scope.selectedAppName.config.description = data.description;
														$scope.selectedAppName.config.deploymentStatus = data.deploymentStatus;
														$scope.selectedAppName.config.version = data.version;
														$scope.selectedAppName.config.profiles = data.profiles;
														$scope.selectedAppName.config.defaultProfile = data.defaultProfile;
														getProfilesList();
													},
													function(failReason) {
													});
												}
												//Check user has permission or not
												$scope.hasPermission = function(permission){
													var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
													return utilService.checkPermission(permission,privileges);
												}; 
												getApplicationInfo();
												
											}, function(failReason) {
												$('#loaderdiv').hide();
											})
						} ]);
