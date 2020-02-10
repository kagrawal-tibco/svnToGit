/**
 * Created by Priyanka on 22/07/15
 */
beConfigModule.controller("beMachineViewCtrl",['$scope','$location','$rootScope','$q','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService','utilService','$http',
                                       function ($scope,$location, $rootScope,$q,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService,utilService,$http){
	 $('#loaderdiv').show();
	 $rootScope.loadingComplete.then(function( apps ){
		 $('#loaderdiv').hide();
		 $scope.newHostObj={};
		 $scope.masterHosts=ReferenceObjectConfigService.getReferenceObject("beHosts");
		 $scope.beHomeUsed={};
		 $scope.filteredValues={};
		 $scope.filteredValues.allHostsChecked = false;
		 $scope.extrenalJars={};
		 $scope.extrenalJars.files=[];
		 $scope.selectedHost;
		 $scope.selectAllHosts = function () {			
	            for (var i = 0; i < $scope.masterHosts.length; i++) {
	            		$scope.masterHosts[i].isChecked = $scope.filteredValues.allHostsChecked;
	            }
		    }
		 $scope.selectHostEntity = function () {          
	        for (var i = 0; i < $scope.masterHosts.length; i++) {
	            if (!$scope.masterHosts[i].isChecked) {
	            	$scope.filteredValues.allHostsChecked = false;
	                return;
	            }
	        }          
	        $scope.filteredValues.allHostsChecked = true;
	     }
		 
		 function getSelectedHostsList(){
	    	var selectedHostsList = []; 
			if($scope.masterHosts!=undefined){
				for (var i = 0; i < $scope.masterHosts.length; i++) {
			        if ($scope.masterHosts[i].isChecked) {
			            var host = $scope.masterHosts[i];
			            selectedHostsList.push(host.key);	                   
			        }
				} 
			}
			return selectedHostsList;
		 }
		 $scope.showOperations = function(){
		    	return getSelectedHostsList().length > 0; 
		 }
		 $scope.dismiss =function(){
			$scope.notification.show = false;
			if(!utilService.getAgentReachable()){
				$location.path("/");
			}
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
		 function getMasterHostsList(isMultipleDiscover){
		   	teaObjectService.reference({				
		   		'agentType': 'BusinessEvents',
		        'objectType': 'BusinessEvents',
		        'objectKey' :'BusinessEvents',
		        'reference' : 'MasterHosts'
			}).then(function (hosts) {
				$scope.masterHosts = hosts;	
				if(isMultipleDiscover){
					for(var i = 0; i < $scope.masterHosts.length; i++){
						if($scope.masterHosts[i].key == $scope.selectedHost.key){
							$scope.isSaveEnabled = false;
							$scope.isCreate=false;
							$scope.isError = false;
							$scope.hostToEdit=$scope.masterHosts[i];
							$scope.newHostObj.ipAdress=$scope.masterHosts[i].config.hostIp;
							$scope.newHostObj.hostOS=$scope.masterHosts[i].config.os;
							$scope.newHostObj.beHome=$scope.masterHosts[i].config.beHome;
							$scope.newHostObj.beTra=$scope.masterHosts[i].config.beTra;
							$scope.newHostObj.username=$scope.masterHosts[i].config.userName;
							$scope.newHostObj.password=$scope.masterHosts[i].config.password;
							$scope.newHostObj.sshPort=$scope.masterHosts[i].config.sshPort;
							$scope.newHostObj.hostName=$scope.masterHosts[i].name;
							$scope.newHostObj.deploymentPath=$scope.masterHosts[i].config.deploymentPath;
							$scope.newHostObj.beHome=$scope.masterHosts[i].config.beHome;
							$scope.newHostObj.beTra=$scope.masterHosts[i];
							$scope.newHostObj.be = $scope.masterHosts[i].config.be;
							$('#beHomesView').modal({
								show: true,
								keyboard: false,
								backdrop: 'static'
							});
					}
				}
				}
				ReferenceObjectConfigService.addReferenceObject("beHosts", hosts);
			}, function (failReason) {
			});
		 }
		 function getSupportedOs(){			
			teaObjectService.invoke({
				'agentType': 'BusinessEvents',
			    'objectType': 'BusinessEvents',
			    'objectKey' :'BusinessEvents',
			    'operation' : 'getSupportedOS',
				'methodType':'READ',
				'params': {
		       	}
			}).then(function (info) {						
				$scope.supportedOs = info;
			}, function (failReason) {
				console.log("failed");
			});	 
		 }
		 $scope.updateTRA= function(be){
			if(typeof be.beHome!=='undefined' && be.beHome.indexOf("\\") > -1){
				be.beTra=be.beHome+"\\bin\\be-engine.tra";
			}else if(typeof be.beHome!=='undefined' &&  be.beHome.indexOf("/") > -1)
				be.beTra=be.beHome+"/bin/be-engine.tra";
			else
				be.beTra="";
		 }
		 $scope.showCreateHost = function(){
			$scope.isCreate=true;
			$scope.isError = false;
			$scope.hostToEdit=undefined;
			$scope.newHostObj.hostName="machine0";
			$scope.newHostObj.ipAdress="";
			$scope.newHostObj.hostOS="";
			$scope.newHostObj.beHome="";
			$scope.newHostObj.beTra="";
			$scope.newHostObj.username="";
			$scope.newHostObj.password="";
			$scope.newHostObj.sshPort="22";
			$scope.newHostObj.deploymentPath="";
			$scope.newHostObj.be=[];
			$scope.newHostObj.operation="Create Machine";
			$('#createHost').modal({
			        show: true,
			        keyboard: false,
			        backdrop: 'static'
			   });
		}
		$scope.showEditHost = function(host){
			
			$scope.isSaveEnabled = false;
			$scope.isCreate=false;
			$scope.isError = false;
			$scope.hostToEdit=host;
			$scope.newHostObj.ipAdress=host.config.hostIp;
			$scope.newHostObj.hostOS=host.config.os;
			$scope.newHostObj.beHome=host.config.beHome;
			$scope.newHostObj.beTra=host.config.beTra;
			$scope.newHostObj.username=host.config.userName;
			$scope.newHostObj.password=host.config.password;
			$scope.newHostObj.sshPort=host.config.sshPort;
			$scope.newHostObj.hostName=host.name;
			$scope.newHostObj.deploymentPath=host.config.deploymentPath;
			var be=[];
			$scope.beHomeUsed={};
			if(typeof host.config.be!=='undefined' && null!=host.config.be){
				for (var i=0;i<host.config.be.length;i++){
						be[i]=host.config.be[i];
				}
			}
			$scope.newHostObj.be=be;
			$scope.newHostObj.operation="Edit Machine";
			updateUsedBEHomes(be);
			$('#createHost').modal({
			       show: true,
			       keyboard: false,
			       backdrop: 'static'
			});
		}
		function updateUsedBEHomes(be){
			teaObjectService.invoke({
				'agentID'  : $scope.hostToEdit.agentId,
				'agentType': $scope.hostToEdit.type.agentType,
				'objectType': $scope.hostToEdit.type.name,           		
				'objectKey': $scope.hostToEdit.key,
				'operation' : 'getHostInstances',
				'methodType':'READ'
			}).then(function (instances) {
				if(typeof instances!=='undefined' && null!==instances && instances.length>0){
				    for(var i=0;i<instances.length;i++){
				    	var instance=instances[i];
				    	if(typeof instance!=='undefined' && isBEId(be,instance.beId)){
				    	}
				    }
					
				}else{

					for (var i=0;i<be.length;i++){$scope.beHomeUsed[be[i].id]=false;}
				
				}
			}, function (failReason) {
				console.trace(failReason);
			});	
		 
		}
		function isBEId(be,instanceBEId){
			for (var i=0;i<be.length;i++){
				if(be[i].id===instanceBEId){
					$scope.beHomeUsed[be[i].id]=true;
				}else{
					$scope.beHomeUsed[be[i].id]=false;
				}
			}
		}
		 $scope.fetchBeHomes =function(){
				$('#loaderdiv').show();
				teaObjectService.invoke({
					'agentType': 'BusinessEvents',
			        'objectType': 'BusinessEvents',
			        'objectKey' :'BusinessEvents',
					'operation' : 'getBeHomes',
					'methodType':'READ',
			        'params': {                	
			            hostName : $scope.newHostObj.hostName,
			            ipAddress : $scope.newHostObj.ipAdress,                	 
			            hostOS : $scope.newHostObj.hostOS,
			            userName : $scope.newHostObj.username,
			            password : $scope.newHostObj.password,
			            sshPort:$scope.newHostObj.sshPort,
			        }      		
				}).then(function (info) {
					$('#loaderdiv').hide();
					if(null!==info){
						// $scope.behomes = info;
						for(var i=0;i<info.length;i++){
							var beHome=info[i].beHome;
						//	beHome=beHome.replace(/\//g, '\\');
							var version=info[i].version;
							var tra=info[i].beTra;
							
							var isExist=false;
							for(var j=0;j<$scope.newHostObj.be.length;j++){
								var beDetail=$scope.newHostObj.be[j];
								var beHome1=beDetail.beHome;
							//	beHome1=beHome1.replace(/\//g, '\\');
								if($.trim(beHome1)===$.trim(beHome)){
									var v1Parts=version.split('.');
									var v2Parts=beDetail.version.split('.');
									
									if(v1Parts.length>v2Parts.length){
										beDetail.version=version;
									}else if(v1Parts.length===4 && 4===v2Parts.length){{
										if(v1Parts[3]>v2Parts[3]){
											beDetail.version=version;
											}
										}
									}
									
									isExist=true;
									break;									
								}
							}
							if(!isExist && version!==undefined &&null!==version&&''!==version){
								var beDetails={beHome:beHome,beTra:tra,id:null,version:version};
								$scope.newHostObj.be.push(beDetails);	
								$scope.isSaveEnabled = true;
							}
						}
						
					}
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log(failReason.message);
					$scope.isError = true;
					$scope.errorMessage = failReason.message;
				});	 
				
		}
		$scope.addBEHome = function(){
			var beDetails={beHome:null,beTra:null,id:null,version:null};
			$scope.newHostObj.be.push(beDetails);
			$scope.isSaveEnabled = true;
		}
		$scope.removeBEDetails=function(index){
				$scope.newHostObj.be.splice(index, 1); 
				$scope.isSaveEnabled = true;
		}
		$scope.closeModal=function(){
			if(typeof $scope.hostToEdit!=='undefined')
				$scope.newHostObj.be= $scope.hostToEdit.config.be;
			else 
				$scope.newHostObj.be=[];	
				getMasterHostsList(null);
		 }
		$scope.changesInMachineDetails = function(){
			$scope.isSaveEnabled = true;
		}
		$scope.saveOrEditHost =function(){
			if($scope.isCreate)
				$scope.saveHost();
			else
				$scope.editHost();
		}
		$scope.saveHost = function(){
			$('#loaderdiv').show();
			var isExist = false;
			if(undefined===$scope.newHostObj.username||null==$scope.newHostObj.username||$.trim($scope.newHostObj.username)===''){
				$('#loaderdiv').hide();
				$scope.isError = true;
				$scope.errorMessage ="User name is required";
			}else{
				var masterHost= {hostName:$scope.newHostObj.hostName,hostId:null,hostIp:$scope.newHostObj.ipAdress,os:$scope.newHostObj.hostOS,userName:$scope.newHostObj.username,password:$scope.newHostObj.password,be:$scope.newHostObj.be,sshPort:$scope.newHostObj.sshPort,deploymentPath:$scope.newHostObj.deploymentPath}
				if(undefined!=$scope.newHostObj.be&& null!=$scope.newHostObj.be&&$scope.newHostObj.be.length>0){		
					for(var i=0;i<$scope.newHostObj.be.length;i++){		
						var beDetails=$scope.newHostObj.be[i];		
						for(var j=i+1;j<$scope.newHostObj.be.length;j++){		
							var nextBEDetails = $scope.newHostObj.be[j];		
							if($.trim(beDetails.beHome).replace(/\\/g,"/")===$.trim(nextBEDetails.beHome).replace(/\\/g,"/")){		
								isExist=true;		
									break;											
							}		
						}		
								
					}		
					if(isExist===true){	
						$('#loaderdiv').hide();
						$scope.isError = true;		
						$scope.errorMessage = "There are duplicate BE homes.";		
					}		
				}
				if($scope.isError==false){	
					//Remove empty be home details
					if(undefined!=$scope.newHostObj.be&& null!=$scope.newHostObj.be&&$scope.newHostObj.be.length>0){		
						for(var i=0;i<$scope.newHostObj.be.length;i++){		
							var beDetails=$scope.newHostObj.be[i];	
							if($.trim(beDetails.beHome)=='undefined' || $.trim(beDetails.beHome)=='' || $.trim(beDetails.beTra)=='undefined' || $.trim(beDetails.beTra)==''){
								$scope.newHostObj.be.splice(i, 1);
							}
						}
					}
					teaObjectService.invoke({
						'agentType': 'BusinessEvents',
						'objectType': 'BusinessEvents',
						'objectKey' :'BusinessEvents',
						'operation' : 'addMasterHost',
						'methodType':'UPDATE',
						'params': {                	
							masterHost : masterHost
						}      		
					}).then(function (message) {
						getMasterHostsList(null);
						$('#loaderdiv').hide();
						$('#createHost').modal('hide');
						/*
						 * $scope.notification = { severity: 'info', msg : message,
						 * show: true };
						 */
					}, function (failReason) {
						$('#loaderdiv').hide();
						$scope.isError = true;
						$scope.errorMessage = failReason.message;
					});	 
				}
			}
		}
		$scope.editHost = function(){
			$('#loaderdiv').show();
			$scope.isError = false;		
			$scope.errorMessage = '';
			var isExist = false;
			var masterHost= {hostName:$scope.newHostObj.hostName,hostId:$scope.newHostObj.hostId,hostIp:$scope.newHostObj.ipAdress,os:$scope.newHostObj.hostOS,userName:$scope.newHostObj.username,password:$scope.newHostObj.password,be:$scope.newHostObj.be,sshPort:$scope.newHostObj.sshPort,deploymentPath:$scope.newHostObj.deploymentPath}
			if(undefined===$scope.newHostObj.username||null==$scope.newHostObj.username||$.trim($scope.newHostObj.username)===''){
				$('#loaderdiv').hide();
				$scope.isError = true;
				$scope.errorMessage ="User name is required";
			}
			else{
				if(undefined!=$scope.newHostObj.be&& null!=$scope.newHostObj.be&&$scope.newHostObj.be.length>0){		
					for(var i=0;i<$scope.newHostObj.be.length;i++){		
						var beDetails=$scope.newHostObj.be[i];		
						for(var j=i+1;j<$scope.newHostObj.be.length;j++){		
							var nextBEDetails = $scope.newHostObj.be[j];		
							
							if($.trim(beDetails.beHome).replace(/\\/g,"/")===$.trim(nextBEDetails.beHome).replace(/\\/g,"/")){		
								isExist=true;		
									break;											
							}		
						}								
								
					}		
					if(isExist===true){	
						$('#loaderdiv').hide();
						$scope.isError = true;		
						$scope.errorMessage = "There are duplicate BE homes.";		
					}		
				}
				//Remove empty be home details
				if(undefined!=$scope.newHostObj.be&& null!=$scope.newHostObj.be&&$scope.newHostObj.be.length>0){		
					for(var i=0;i<$scope.newHostObj.be.length;i++){		
						var beDetails=$scope.newHostObj.be[i];	
						if($.trim(beDetails.beHome)=='undefined' || $.trim(beDetails.beHome)=='' || $.trim(beDetails.beTra)=='undefined' || $.trim(beDetails.beTra)==''){
							$scope.newHostObj.be.splice(i, 1);
						}
					}
				}
				if($scope.isError==false){
					teaObjectService.invoke({
						'agentID'  : $scope.hostToEdit.agentId,
						'agentType': $scope.hostToEdit.type.agentType,
						'objectType': $scope.hostToEdit.type.name,           		
						'objectKey': $scope.hostToEdit.key,
						'operation' : 'edit',
						'methodType':'UPDATE',
						'params': {                	
							masterHost:masterHost,
							version:$scope.hostToEdit.config.version
						}      		
					}).then(function (message) {
						getMasterHostsList(null);
						$('#loaderdiv').hide();
						$('#createHost').modal('hide');
						$('#beHomesView').modal('hide');
						/*
						 * $scope.notification = { severity: 'info', msg : message,
						 * show: true };
						 */
					}, function (failReason) {
						$('#loaderdiv').hide();
						$scope.isError = true;
						$scope.errorMessage = failReason.message;
					});	 
			 }
			}
		 }
		$scope.deleteHost = function(host,isMultiDelete){
			$scope.isMultipleHostDelete = isMultiDelete;
			$scope.selectedHost = host;
			$scope.operationMessage="Delete";
			$('#actionConfirmation').modal({
		        show: true,
		        keyboard: false,
		        backdrop: 'static'       
		    });	
		}
		$scope.operation = function(){
			 $('#loaderdiv').show();
			 if($scope.isMultipleHostDelete){
				 var selectedHostsList = getSelectedHostsList();
				 teaObjectService.invoke({
					 	'agentType': 'BusinessEvents',
				        'objectType': 'BusinessEvents',
				        'objectKey' :'BusinessEvents',
						'operation' : 'groupMasterHostsDelete',
						'methodType':'UPDATE',
				        'params': {     
				        	hosts : selectedHostsList
				        }
					}).then(function (message) {
						$('#actionConfirmation').modal('hide');
						$('#loaderdiv').hide();
						getMasterHostsList(null);
						$scope.filteredValues.allHostsChecked = false;
						/*
						 * $scope.notification = { severity: 'info', msg :
						 * message, show: true };
						 */
					}, function (failReason) {
						$('#actionConfirmation').modal('hide');
						$('#loaderdiv').hide();
						$scope.notification = {
						      severity: 'error',
						      msg : failReason.message,
						      show: true
						};
				});	 
			 }else{
				 teaObjectService.invoke({
						'agentID'  : $scope.selectedHost.agentId,
						'agentType': $scope.selectedHost.type.agentType,
						'objectType': $scope.selectedHost.type.name,           		
						'objectKey': $scope.selectedHost.key,
						'operation' : 'deleteHost',
						'methodType':'UPDATE'
					}).then(function (message) {
						$('#actionConfirmation').modal('hide');
						$('#loaderdiv').hide();
						getMasterHostsList(null);
						/*
						 * $scope.notification = { severity: 'info', msg :
						 * message, show: true };
						 */
					}, function (failReason) {
						$('#actionConfirmation').modal('hide');
						$('#loaderdiv').hide();
						$scope.notification = {
						      severity: 'error',
						      msg : failReason.message,
						      show: true
						};
				});	 
			 }
		}
		// Check user has permission or not
		$scope.hasPermission = function(permission){
			var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
			return utilService.checkPermission(permission,privileges);
		};   
		$scope.reloadPage = function(){
			getMasterHostsList(null);
		}
		$scope.browseFile = function(id){
			$('#'+id).click();
		}
		$scope.showUploadExternalJars = function(host){
			$scope.masterHost=host;
			$scope.isError = false;
			$scope.extrenalJars.files =[];
			$scope.extrenalJars.beId ="";
			$scope.extrenalJars.zipFileName="";
			$scope.extrenalJars.isErrorWhileUploading = false;
			$scope.extrenalJars.errorMessage="";
			$('#jarFiles').val("");
			$('#selectedFile').val("");
			$('#uploadExternalJarsModal').modal({
				show: true,
				keyboard: false,
				backdrop: 'static'
			});
		}
		$scope.finishUpload = function(){
			if(angular.isUndefined($scope.extrenalJars.files) || $scope.extrenalJars.files.length===0){
				$scope.extrenalJars.isErrorWhileUploading = true;
				$scope.extrenalJars.errorMessage="upload files";
				return;
			}
			$('#loaderdiv').show(); 			
			
			for(var i=0;i<$scope.extrenalJars.files.length;i++){
				var fd = new FormData();
				fd.append("file", $scope.extrenalJars.files[i]._file);					
				$http.post("/teas/fileupload/", fd, {
		            transformRequest: angular.identity,
		            headers: {
		                "Content-Type": undefined
		            }
		        }).success(function(data, status, headers, config) {        	  
		 			 	teaObjectService.invoke({
			 				'agentID'  : $scope.masterHost.agentId,
				            'agentType': $scope.masterHost.type.agentType,
			           		'objectType': $scope.masterHost.type.name,           		
			           		'objectKey': $scope.masterHost.key,
			            	'operation': 'uploadExternalJars',
			                'methodType': 'UPDATE',
			                'params': {                	
			                	jarFiles : data[0],
			                	beId:$scope.extrenalJars.beId
				             } 
			 			}).then(function (message) { 
			 				$('#loaderdiv').hide();
			 				$scope.extrenalJars.isErrorWhileUploading = false;
			 				$scope.extrenalJars.errorMessage="";
			 				$('#jarFiles').val("");
			 				$('#selectedFile').val("");
			 				$('.modal-backdrop').hide();
			 				$('#uploadExternalJarsModal').modal('hide');
			 		    }, function (failReason) {
		 		    		$('#loaderdiv').hide(); 
		 		    		$scope.extrenalJars.isErrorWhileUploading = true;
							$scope.extrenalJars.errorMessage=failReason.message;
		 		    	}); 
		        });
			}
			
		}
		
//		$scope.checkDefaultBEHome = function(beDetail){
//			 if(beDetail.beHome == $scope.selectedHost.config.beHome)
//				 return true;
//		 } 
		
		$scope.discoverBEHome=function(host, isMultipleDiscover){
			$('#loaderdiv').show(); 
			$scope.selectedHost = host;
			var selectedHostsList = [];
			if(isMultipleDiscover)
			   selectedHostsList = getSelectedHostsList();
			else
				selectedHostsList.push(host.key);
			teaObjectService.invoke({
				'agentType': 'BusinessEvents',
			    'objectType': 'BusinessEvents',
			    'objectKey' :'BusinessEvents',
			    'operation' : 'discoverBEHome',
				'methodType':'UPDATE',
				'params': {
					hosts : selectedHostsList
		       	}
			}).then(function (info) {	
				$('#loaderdiv').hide(); 
				getMasterHostsList(true);
				
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				console.log("failed");
				$scope.notification = {
					      severity: 'error',
					      msg : failReason.message,
					      show: true
					};
			});	 
		}
		getSupportedOs();
		getMasterHostsList(null);
	 }, function (failReason) {
			$('#loaderdiv').hide(); 
	    })
	   
}]);	
	