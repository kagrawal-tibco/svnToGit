/**
 * Created by Rohini Jadhav on 28/07/16
 */

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
												$scope.profiles = ReferenceObjectConfigService
														.getReferenceObject("profiles");
												$scope.profileObject = ReferenceObjectConfigService
														.getReferenceObject("profiles");
												$scope.isCreate = true;
												$scope.isEdit = true;
												$scope.isProfileEdit = false;
												$scope.configurationType = "global";
												$scope.globalVariables = [];
												$scope.systemProperties = [];
												$scope.beProperties = [];
												$scope.profileVariables = {};

												$scope.profileName = "";
												$scope.configurationChangesFlags = {};
												$scope.configurationChangesFlags.isSystemVariableChanged = false;
												$scope.configurationChangesFlags.isBEPropChanged = false;
												$scope.configurationChangesFlags.isGlobalVariableChanged = false;

												if (ReferenceObjectConfigService
														.getSelectedApp() != undefined)
													$scope.selectedAppName = ReferenceObjectConfigService
															.getSelectedApp();

												$scope.showOperations = function() {
													return getSelectedProfilesList().length > 0;
												}

												$scope.dismiss = function() {
													$scope.notification.show = false;
													if (!utilService
															.getAgentReachable()) {
														$location.path("/");
													}
												}

												$scope.sort = {
													column : '',
													descending : false
												}

												$scope.onSortCall = function(
														column) {
													var sort = $scope.sort;
													if (sort.column == column) {
														sort.descending = !sort.descending;
													} else if (sort.column == '') {
														sort.column = column;
														sort.descending = !sort.descending;
													} else {
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
														profile = new String(
																profile);
														$scope.profiles
																.push(profile);
													}
												}

												$scope.showAddProfileWizard = function() {
													$scope.globalVariables = [];
													$scope.systemProperties = [];
													$scope.beProperties = [];
													$scope.profileName = "";
													$scope.configurationType = "global";
													$scope.configurationChangesFlags = {};
													$scope.configurationChangesFlags.isSystemVariableChanged = false;
													$scope.configurationChangesFlags.isBEPropChanged = false;
													$scope.configurationChangesFlags.isGlobalVariableChanged = false;
													$scope.isEditProfile = false;
													$('#addApplicationProfile')
															.modal(
																	{
																		show : true,
																		keyboard : false,
																		backdrop : 'static'
																	});
												}

												$scope.showEditProfileWizard = function(
														profile) {
													$scope.isEditProfile = true;
													$scope.configurationType = "global";
													$scope.selectedProfile = profile;
													$scope.globalVariables = [];
													$scope.systemProperties = [];
													$scope.beProperties = [];
													$scope.profileName = profile
															.toString();
													$scope.configurationChangesFlags = {};
													$scope.configurationChangesFlags.isSystemVariableChanged = false;
													$scope.configurationChangesFlags.isBEPropChanged = false;
													$scope.configurationChangesFlags.isGlobalVariableChanged = false;
													$scope
															.getApplicationProfileDetails(profile);
													$('#addApplicationProfile')
															.modal(
																	{
																		show : true,
																		keyboard : false,
																		backdrop : 'static'
																	});
												}

												$scope.showDeleteProfileWizard = function(
														profile) {
													$scope.profileName = profile
															.toString();
													$scope.isEditProfile = false;
													$(
															'#deleteApplicationProfile')
															.modal(
																	{
																		show : true,
																		keyboard : false,
																		backdrop : 'static'
																	});
												}

												$scope.removeVariable = function(
														index) {
													if ($scope.configurationType === 'global') {
														$scope.globalVariables
																.splice(index,
																		1);
														$scope.configurationChangesFlags.isGlobalVariableChanged = true;
													} else if ($scope.configurationType === 'systemProps') {
														$scope.systemProperties
																.splice(index,
																		1);
														$scope.configurationChangesFlags.isSystemVariableChanged = true;
													} else if ($scope.configurationType === 'beProps') {
														$scope.beProperties
																.splice(index,
																		1);
														$scope.configurationChangesFlags.isBEPropChanged = true;
													}
												}

												$scope.isDisabled = function() {
													return !(($scope.configurationChangesFlags.isGlobalVariableChanged
															|| $scope.configurationChangesFlags.isSystemVariableChanged || $scope.configurationChangesFlags.isBEPropChanged) && this.profileName != undefined && this.profileName != "") ? true
															: false;
												}
												$scope.saveApplicationProfile = function() {
													$scope.profileName = $(
															'#profileNameTextField')
															.val();
													var gv = {};
													var systemProps = {};
													var beProps = {};
													for (var i = 0; i < $scope.globalVariables.length; i++) {
														var entry = $scope.globalVariables[i];
														removeDuplicate(i,
																entry);
														gv[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.systemProperties.length; i++) {
														var entry = $scope.systemProperties[i];
														removeDuplicate(i,
																entry);
														systemProps[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.beProperties.length; i++) {
														var entry = $scope.beProperties[i];
														removeDuplicate(i,
																entry);
														beProps[entry.key] = entry.value;
													}
													$('#addApplicationProfile')
															.modal('hide');
													$('#loaderdiv').show();
													teaObjectService
															.invoke(
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
																		$(
																				'#loaderdiv')
																				.hide();
																		getApplicationInfo();
																	},
																	function(
																			failReason) {
																		$(
																				'#loaderdiv')
																				.hide();
																		$scope.notification = {
																			severity : 'error',
																			msg : failReason.message,
																			show : true
																		};
																	});

												}
												$scope.editApplicationProfile = function() {

													var gv = {};
													var systemProps = {};
													var beProps = {};
													for (var i = 0; i < $scope.globalVariables.length; i++) {
														var entry = $scope.globalVariables[i];
														removeDuplicate(i,
																entry);
														gv[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.systemProperties.length; i++) {
														var entry = $scope.systemProperties[i];
														removeDuplicate(i,
																entry);
														systemProps[entry.key] = entry.value;
													}
													for (var i = 0; i < $scope.beProperties.length; i++) {
														var entry = $scope.beProperties[i];
														removeDuplicate(i,
																entry);
														beProps[entry.key] = entry.value;
													}
													$('#addApplicationProfile')
															.modal('hide');
													$('#loaderdiv').show();
													teaObjectService
															.invoke(
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
															.then(
																	function(
																			resp) {
																		$(
																				'#loaderdiv')
																				.hide();
																		getApplicationInfo();
																	},
																	function(
																			failReason) {
																		$(
																				'#loaderdiv')
																				.hide();
																		$scope.notification = {
																			severity : 'error',
																			msg : failReason.message,
																			show : true
																		};
																	});

												}


												$scope.getApplicationProfileDetails = function(
														profileName) {
													$scope.selectedObj = $scope.selectedAppName;
													$('#loaderdiv').show();
													$scope.configurationType = "global";
													$scope.profileName = profileName
															.toString();
													teaObjectService
															.invoke(
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
															.then(
																	function(
																			resp) {
																		var gv = resp['GV'];
																		var systemProps = resp['SYSTEMPROPS'];
																		var beProps = resp['BEPROPS'];
																		$scope.globalVariables = [];
																		$scope.systemProperties = [];
																		$scope.beProperties = [];
																		if (typeof gv !== 'undefined'
																				|| null !== gv) {

																			$
																					.each(
																							gv,
																							function(
																									index,
																									value) {
																								var entry = {
																									key : index,
																									value : value
																								};
																								$scope.globalVariables
																										.push(entry);
																							})
																			$scope.profileVariables[profileName
																					.toString()
																					+ "_gv"] = $scope.globalVariables;
																		}
																		if (typeof systemProps !== 'undefined'
																				|| null !== systemProps) {
																			$
																					.each(
																							systemProps,
																							function(
																									index,
																									value) {
																								var entry = {
																									key : index,
																									value : value
																								};
																								$scope.systemProperties
																										.push(entry);
																							})
																			$scope.profileVariables[profileName
																					.toString()
																					+ "_sysProps"] = $scope.systemProperties;
																		}

																		if (typeof beProps !== 'undefined'
																				|| null !== beProps) {
																			$
																					.each(
																							beProps,
																							function(
																									index,
																									value) {
																								var entry = {
																									key : index,
																									value : value
																								};
																								$scope.beProperties
																										.push(entry);
																							})
																			$scope.profileVariables[profileName
																					.toString()
																					+ "_beProps"] = $scope.beProperties;
																		}
																		$(
																				'#loaderdiv')
																				.hide();
																	},
																	function(
																			failReason) {
																		$(
																				'#loaderdiv')
																				.hide();
																		$scope.notification = {
																			severity : 'error',
																			msg : failReason.message,
																			show : true
																		};
																	});

												}

												$scope.updateProfile = function(
														profileName) {
													$scope.profileName = profileName;
												}

												function removeDuplicate(index,
														globalVariable) {
													if ($scope.configurationType === 'global') {
														for (var i = 0; i < $scope.globalVariables.length; i++) {
															var entry = $scope.globalVariables[i];
															if (index !== i) {
																if (entry.key === globalVariable.key
																		&& entry.value === entry.value) {
																	$scope.globalVariables
																			.splice(
																					i,
																					1);
																}
															}
														}
													} else if ($scope.configurationType === 'systemProps') {
														for (var i = 0; i < $scope.systemProperties.length; i++) {
															var entry = $scope.systemProperties[i];
															if (index !== i) {
																if (entry.key === globalVariable.key
																		&& entry.value === entry.value) {
																	$scope.systemProperties
																			.splice(
																					i,
																					1);
																}
															}
														}
													} else if ($scope.configurationType === 'beProps') {
														for (var i = 0; i < $scope.beProperties.length; i++) {
															var entry = $scope.beProperties[i];
															if (index !== i) {
																if (entry.key === globalVariable.key
																		&& entry.value === entry.value) {
																	$scope.beProperties
																			.splice(
																					i,
																					1);
																}
															}
														}
													}
												}

												$scope.addNewProperty = function() {
													var entry = {
														key : null,
														value : null
													};
													if ($scope.configurationType === 'global') {
														$scope.configurationChangesFlags.isGlobalVariableChanged = true;
														$scope.globalVariables
																.push(entry);
													} else if ($scope.configurationType === 'systemProps') {
														$scope.configurationChangesFlags.isSystemVariableChanged = true;
														$scope.systemProperties
																.push(entry);
													} else if ($scope.configurationType === 'beProps') {
														$scope.configurationChangesFlags.isBEPropChanged = true;
														$scope.beProperties
																.push(entry);
													}
												}

												$scope.setConfigurationType = function(
														type, profile) {
													if (undefined === profile)
														$scope.configurationType = type;
													else
														$scope.profileConfigType[profile
																.toString()] = type;
												}

												$scope.isSetConfigType = function(
														type, profile) {
													if (undefined === profile)
														return $scope.configurationType === type;
													else
														return $scope.profileConfigType[profile
																.toString()] === type;

												}

												$scope.notification = {
													severity : 'info',
													msg : '',
													show : false
												};

												function populateAllApplications() {
													var deferredRequest = StorageService
															.getApplication();
													deferredRequest
															.then(
																	function(
																			apps) {
																		$scope.appObject = apps;
																	},
																	function(
																			failReason) {

																	});
												}
												$scope.deleteApplicationProfile = function() {
													$(
															'#deleteApplicationProfile')
															.modal('hide');
													$('#loaderdiv').show();
													teaObjectService
															.invoke(
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
															.then(
																	function(
																			resp) {
																		$(
																				'#loaderdiv')
																				.hide();
																		getApplicationInfo();
																	},
																	function(
																			failReason) {
																		$(
																				'#loaderdiv')
																				.hide();
																		$scope.notification = {
																			severity : 'error',
																			msg : failReason.message,
																			show : true
																		};
																	});

												}
												$scope.setApplicationDefaultProfile = function(
														profile) {
													var profileName = ""
													if (undefined !== profile)
														profileName = profile
																.toString();
													$('#loaderdiv').show();
													teaObjectService
															.invoke(
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
															.then(
																	function(
																			data) {
																		$(
																				'#loaderdiv')
																				.hide();
																		getApplicationInfo();
																		
																	},
																	function(
																			failReason) {
																		$(
																				'#loaderdiv')
																				.hide();
																		$scope.notification = {
																			severity : 'error',
																			msg : failReason.message,
																			show : true
																		};
																	});
												}
												function getApplicationInfo() {
													teaObjectService
															.invoke(
																	{
																		'agentID' : $scope.selectedAppName.agentId,
																		'agentType' : $scope.selectedAppName.type.agentType,
																		'objectType' : $scope.selectedAppName.type.name,
																		'objectKey' : $scope.selectedAppName.key,
																		'operation' : 'getApplication',
																		'methodType' : 'READ'
																	})
															.then(
																	function(
																			data) {
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
																	function(
																			failReason) {
																	});
												}

												getProfilesList();
											}, function(failReason) {
												$('#loaderdiv').hide();
											})
						} ]);
