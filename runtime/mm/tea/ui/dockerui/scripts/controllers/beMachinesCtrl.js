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
		 function getMasterHostsList(){
		   	teaObjectService.reference({				
		   		'agentType': 'BusinessEvents',
		        'objectType': 'BusinessEvents',
		        'objectKey' :'BusinessEvents',
		        'reference' : 'MasterHosts'
			}).then(function (hosts) {
				$scope.masterHosts = hosts;	
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
			if(be.beHome.indexOf("\\") > -1){
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
							var version=info[i].version;
							var tra=info[i].beTra;
							
							var isExist=false;
							for(var j=0;j<$scope.newHostObj.be.length;j++){
								var beDetail=$scope.newHostObj.be[j];
								if($.trim(beDetail.beHome)===$.trim(beHome) && $.trim(tra)===$.trim(beDetail.beTra) && version===beDetail.version){
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
			 var masterHost= {hostName:$scope.newHostObj.hostName,hostId:null,hostIp:$scope.newHostObj.ipAdress,os:$scope.newHostObj.hostOS,userName:$scope.newHostObj.username,password:$scope.newHostObj.password,be:$scope.newHostObj.be,sshPort:$scope.newHostObj.sshPort,deploymentPath:$scope.newHostObj.deploymentPath}
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
				getMasterHostsList();
				$('#loaderdiv').hide();
				$('#createHost').modal('hide');
				/*$scope.notification = {
					severity: 'info',
					msg : message,
					show: true
				};*/
			}, function (failReason) {
				$('#loaderdiv').hide();
				$scope.isError = true;
				$scope.errorMessage = failReason.message;
			});	 
			
		}
		$scope.editHost = function(){
			$('#loaderdiv').show();
			var masterHost= {hostName:$scope.newHostObj.hostName,hostId:$scope.newHostObj.hostId,hostIp:$scope.newHostObj.ipAdress,os:$scope.newHostObj.hostOS,userName:$scope.newHostObj.username,password:$scope.newHostObj.password,be:$scope.newHostObj.be,sshPort:$scope.newHostObj.sshPort,deploymentPath:$scope.newHostObj.deploymentPath}

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
				getMasterHostsList();
				$('#loaderdiv').hide();
				$('#createHost').modal('hide');
				/*$scope.notification = {
					severity: 'info',
					msg : message,
					show: true
				};*/
			}, function (failReason) {
				$('#loaderdiv').hide();
				$scope.isError = true;
				$scope.errorMessage = failReason.message;
			});	 
			
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
						getMasterHostsList();
						$scope.filteredValues.allHostsChecked = false;
						/*$scope.notification = {
						      severity: 'info',
						      msg : message,
						      show: true
						};*/
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
						getMasterHostsList();
						/*$scope.notification = {
						      severity: 'info',
						      msg : message,
						      show: true
						};*/
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
			getMasterHostsList();
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
		$scope.discoverBEHome=function(){
			 var selectedHostsList = getSelectedHostsList();
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
			}, function (failReason) {
				console.log("failed");
			});	 
		 
		}
		getSupportedOs();
		getMasterHostsList();
	 }, function (failReason) {
			$('#loaderdiv').hide(); 
	    })
}]);	
	