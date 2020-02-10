beConfigModule.controller("applicationManagmentCtrl",['$scope','$sce','$q','$location','$rootScope', '$timeout', 'fileUpload','teaLocation', 'teaObjectService', 'teaScopeDecorator','$http','StorageService','ReferenceObjectConfigService','utilService',
                                       function ($scope,$sce,$q,$location, $rootScope,$timeout,fileUpload, teaLocation, teaObjectService, teaScopeDecorator ,$http,StorageService,ReferenceObjectConfigService,utilService){
	$('#loaderdiv').show();
	$rootScope.loadingComplete.then(function( apps ){
	$('#loaderdiv').hide();
	$scope.importData={};
	$scope.createData={};
	$scope.appDescription;
	$scope.test=true;
	$scope.operationMessage="Delete Deployment";
	$scope.appObject = ReferenceObjectConfigService.getReferenceObject("Application");
	$scope.masterHosts=ReferenceObjectConfigService.getReferenceObject("beHosts");
	$scope.selectedObj;
	$scope.isCreate=true;
	$scope.isEdit=true;
	$scope.isProfileEdit=false;
	$scope.operationTitle = "Create Deployment";
	$scope.configurationType  = "global";
	$scope.globalVariables=[];
	$scope.systemProperties=[];
	$scope.beProperties=[];
	$scope.profileName="";
	$scope.configurationChangesFlags ={};
	$scope.configurationChangesFlags.isSystemVariableChanged = false;
	$scope.configurationChangesFlags.isBEPropChanged = false;
	$scope.configurationChangesFlags.isGlobalVariableChanged = false;
//	$scope.showImportApplication = function(){	
//		$scope.importData.applicationName="";
//		$scope.importData.files=undefined;
//		$scope.importData.EARfiles=undefined;
//		$scope.importData.CDDfiles=undefined;
//		$scope.isError = false;
//		$('#stfilepath').val("");
//		$('#cddfilepath').val("");
//		$('#earfilepath').val("");
//		$('#stfile').val("");
//		$('#earfile').val("");
//		$('#cddfile').val("");
//		$('#importApplication').modal({
//	        show: true,
//	        keyboard: false,
//	        backdrop: 'static'       
//	       
//	    });		
//	}
	$scope.reloadPage = function(){
		populateAllApplications();
	}
	$scope.showAddProfileWizard = function(app){	
		$scope.selectedObj = app;	
		$scope.globalVariables=[];
		$scope.systemProperties=[];
		$scope.beProperties=[];
		$scope.profileName="";
		$scope.configurationChangesFlags ={};
		$scope.configurationChangesFlags.isSystemVariableChanged = false;
		$scope.configurationChangesFlags.isBEPropChanged = false;
		$scope.configurationChangesFlags.isGlobalVariableChanged = false;
		$scope.isEditProfile=false;	
		$('#addApplicationProfile').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'       
	    });	
	}
	$scope.showEditProfileWizard = function(app){	
		$scope.selectedObj = app;
		$scope.isEditProfile=true;	
		$('#addApplicationProfile').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'       
	    });	
	}
	$scope.showDeleteProfileWizard=function(app){
		$scope.selectedObj = app;	
		$scope.isEditProfile=false;	
		$('#deleteApplicationProfile').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'       
	    });	
	}
	$scope.removeVariable=function(index){
		if($scope.configurationType==='global'){
			$scope.globalVariables.splice(index, 1); 
			$scope.configurationChangesFlags.isGlobalVariableChanged=true;
		}else if($scope.configurationType==='systemProps'){
			$scope.systemProperties.splice(index, 1); 
			$scope.configurationChangesFlags.isSystemVariableChanged=true;
		}else if($scope.configurationType==='beProps'){
			$scope.beProperties.splice(index, 1); 
			$scope.configurationChangesFlags.isBEPropChanged=true;
		}
	}
	$scope.isDisabled=function(){
		return !($scope.configurationChangesFlags.isGlobalVariableChanged || $scope.configurationChangesFlags.isSystemVariableChanged || $scope.configurationChangesFlags.isBEPropChanged)?true:false;
	}

	
	$scope.updateProfile=function(profileName){
		$scope.profileName=profileName;
	}
	function removeDuplicate(index,globalVariable){
		if($scope.configurationType==='global'){
			for(var i=0;i<$scope.globalVariables.length;i++){
				var entry=$scope.globalVariables[i];
				if(index!==i){
					if(entry.key===globalVariable.key && entry.value===entry.value){
						$scope.globalVariables.splice(i, 1); 
					}
				}			
			}		
		}else if($scope.configurationType==='systemProps'){
			for(var i=0;i<$scope.systemProperties.length;i++){
				var entry=$scope.systemProperties[i];
				if(index!==i){
					if(entry.key===globalVariable.key && entry.value===entry.value){
						$scope.systemProperties.splice(i, 1); 
					}
				}			
			}	
		}else if($scope.configurationType==='beProps'){
			for(var i=0;i<$scope.beProperties.length;i++){
				var entry=$scope.beProperties[i];
				if(index!==i){
					if(entry.key===globalVariable.key && entry.value===entry.value){
						$scope.beProperties.splice(i, 1); 
					}
				}			
			}	
		}
	}
	$scope.addNewProperty=function(){
		var entry={key:null,value:null};
		if($scope.configurationType==='global'){
			$scope.configurationChangesFlags.isGlobalVariableChanged =true;
			$scope.globalVariables.push(entry);
		}else if($scope.configurationType==='systemProps'){
			$scope.configurationChangesFlags.isSystemVariableChanged =true;
			$scope.systemProperties.push(entry);
		}else if($scope.configurationType==='beProps'){
			$scope.configurationChangesFlags.isBEPropChanged =true;
			$scope.beProperties.push(entry);
		}
	}
	
	$scope.setConfigurationType = function(type){
	    	$scope.configurationType = type;
	}
	$scope.isSetCofigType=function(type){
	    	return $scope.configurationType === type;
	}
//	$scope.showImportExportedApplicationsWizard= function(){	
//		$scope.importData.files=undefined;		
//		$scope.isError = false;
//		$('#exportedzipfilepath').val("");
//		$('#exportedzipfile').val("");
//		$('#importExportedApplication').modal({
//	        show: true,
//	        keyboard: false,
//	        backdrop: 'static'       
//	       
//	    });		
//	}
	$scope.importExportedApplication =function(files){
		$('#loaderdiv').show(); 
		var fd = new FormData();       
        fd.append("file", files);
		$http.post("/teas/fileupload/", fd, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": undefined
            }
        }).success(function(data, status, headers, config) {        	  
 			 teaObjectService.invoke({
 				'agentID'  : 'BE',
 	            'agentType': 'BusinessEvents',
            	'objectType': 'BusinessEvents',           		
            	'objectKey': 'BusinessEvents',
            	'operation': 'upload',
                'methodType': 'UPDATE',
                'params': {       
                	name:'',
                	description:'',
               	 	cddpath :undefined,
               	 	earpath :undefined,
               	 	stPath : data[0],
               	 	isCreate : false ,
              	 	isExportedCreate : true
                 	}      		
 			 }).then(function (resp) { 
 				$('#loaderdiv').hide(); 
 				$('#importExportedApplication').modal('hide'); 
 				/*$scope.notification = {
 			            severity: 'info',
 			            msg : resp,
 			            show: true
 			    };*/
 				populateAllApplications();
 			 }, function (failReason) {
 				$('#loaderdiv').hide();  					
 				$scope.isError = true;
				$scope.errorMessage = failReason.message;
 			 });
        });			
	}
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
	$scope.showCreateApplication = function(){
		$scope.createData.earfilesCreateApp=undefined;
		$scope.createData.CDDfilesCreateApp=undefined;
		$scope.operationTitle = "New Deployment";
		$scope.createData.applicationName = "";
		$scope.createData.monitorableOnly=false;
		$('#cddfilepathforCreateApp').val("");
		$('#earfilepathforCreateApp').val("");
		$('#earfileforCreateApp').val("");
		$('#cddfileforCreateApp').val("");
		$scope.isCreate=true;
		$scope.isEdit=false;	
		$scope.isError = false;
		$("#createApplication").css("height","220px");
		$('#createApplication').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'
	    });
	}
   	function populateAllApplications() {
        var deferredRequest = StorageService.getApplication();
        deferredRequest.then(function (apps) {
           $scope.appObject = apps;
        }, function (failReason) {
           
        });
   	}   
	$scope.browseFile = function(id){
		$('#'+id).click();
	}
	$scope.setPathValue = function(id,fileId){
		$('#'+id).val($('#'+fileId).val().replace(/^.*[\\\/]/, ''));
	} 
	$scope.readFileContent = function($fileContent){
        $scope.content = $fileContent;
        var xmlDoc = $.parseXML($scope.content);
        var site = xmlDoc.getElementsByTagName('site');
        $scope.importData.applicationName= site[0].attributes.name.value;
    }
    $scope.setDeploymentName = function(){
    	var fpath = $('#earfilepathforCreateApp').val();
    	if($scope.isCreate)
    		$scope.createData.applicationName = fpath.substring(0, fpath.lastIndexOf('.'));
    }
	$scope.operation = function(){
		$('#actionConfirmation').modal('hide'); 
		$('#loaderdiv').show(); 
		 teaObjectService.invoke({
			 	'agentID'  : $scope.selectedObj.agentId,
	            'agentType': $scope.selectedObj.type.agentType,
	       		'objectType': $scope.selectedObj.type.name,           		
	       		'objectKey': $scope.selectedObj.key,
	       		'operation' : 'delete',
	       		'methodType': 'DELETE'	                		
			 }).then(function (resp) {	
				$('#loaderdiv').hide(); 
				/*$scope.notification = {
			            severity: 'info',
			            msg : resp,
			            show: true
			    };*/
				populateAllApplications();
			 }, function (failReason) {		
				$('#loaderdiv').hide(); 
				$scope.notification = {
			            severity: 'error',
			            msg :failReason.message,
			            show: true
			    };
			 });
	}
	$scope.updateTRAandLog = function(app){
		$scope.isAllowSaveTRAConfig = false;
		$scope.selectedObj = app;
		$scope.traAndLogUpdates=[];
		$scope.existingConfigurations=[];
		$scope.isError = false;
		if(app.config.hostTraConfigs != null && app.config.hostTraConfigs.hostTraConfig.length>0){
			$scope.existingConfigurations = app.config.hostTraConfigs.hostTraConfig;
			for(var i=0 ; i < $scope.existingConfigurations.length ; i++){
				var newObj = {hostId:$scope.existingConfigurations[i].hostId,uploadFile:false,deploymentPath:"",uploadTRAfile:null,traFilePath:$scope.existingConfigurations[i].traPath,isDeleted:false,isExists:false};
				$scope.traAndLogUpdates.push(newObj);
			}
		}
		$('#updateTRAandLog').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'
	    });
	}
	$scope.addNewTRAandLogUpdate = function(){
		var newObj = {hostId:"",uploadFile:true,deploymentPath:"",uploadTRAfile:null,traFilePath:"",isDeleted:false,isExists:false};
		$scope.traAndLogUpdates.push(newObj);
	}
	$scope.removeTraAndLogUpdate = function(index){
		$scope.traAndLogUpdates[index].isDeleted=true;
	}
	$scope.checkIfTRAConfigAlreadyExists = function(index , hostId){
		var isAlreadyExists = false;
		for(var i=0 ; i<$scope.traAndLogUpdates.length;i++ ){
			if(i != index && $scope.traAndLogUpdates[i].hostId == hostId){
				var isAlreadyExists = true;
				break;
			}
		}
		$scope.traAndLogUpdates[index].isExists = isAlreadyExists;
	}
	$scope.updateTRAConfiguration = function(){
		var traAndLogUpdatesToSave = [];
		var errCnt = 0;
		for(var i=0 ; i<$scope.traAndLogUpdates.length ; i++){
			if(!$scope.traAndLogUpdates[i].isExists && $scope.traAndLogUpdates[i].hostId!="" && ($scope.traAndLogUpdates[i].traFilePath!="" || ($scope.traAndLogUpdates[i].uploadTRAfile!=null) && $scope.traAndLogUpdates[i].deploymentPath!="")){
				traAndLogUpdatesToSave.push($scope.traAndLogUpdates[i]);
			}else{
				$('#loaderdiv').hide();
				$scope.isError = true;
				$scope.errorMessage = 'All fields are mandatory';
				return;
			}
		}
		$('#loaderdiv').show();
		var fd = new FormData();
		for(var i=0 ; i<traAndLogUpdatesToSave.length ; i++){
			fd.append("file", traAndLogUpdatesToSave[i].uploadTRAfile);
		}
		if(traAndLogUpdatesToSave.length < 1 && $scope.existingConfigurations.length == traAndLogUpdatesToSave.length){
			$('#loaderdiv').hide();
			$scope.isError = true;
			$scope.errorMessage = 'There are no configuration changes';
			return;
		}
		$http.post("/teas/fileupload/", fd, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": undefined
            }
        }).success(function(data, status, headers, config) {    
        	angular.forEach(traAndLogUpdatesToSave, function(traAndLogUpdateObj,index) {
        			teaObjectService.invoke({
    	 				'agentID'  : $scope.selectedObj.agentId,
    		            'agentType': $scope.selectedObj.type.agentType,
    	           		'objectType': $scope.selectedObj.type.name,           		
    	           		'objectKey': $scope.selectedObj.key,
    	            	'operation': 'uploadTraConfiguration',
    	                'methodType': 'UPDATE',
    	                'params': { 
    	                	hostId : traAndLogUpdateObj.hostId,
    	                	uploadFile:traAndLogUpdateObj.uploadFile,
    	                	deploymentPath:traAndLogUpdateObj.deploymentPath,
    	                	uploadTRAfile:data[index],
    	                	traFilePath:traAndLogUpdateObj.traFilePath,
            				isDeleted:traAndLogUpdateObj.isDeleted
    		             } 
    	 			}).then(function (message) {
    	 				$('#updateTRAandLog').modal('hide'); 
    	 				if(index == traAndLogUpdatesToSave.length-1){
    	 					if(errCnt == traAndLogUpdatesToSave.length){
    	 						$('#loaderdiv').hide();
    	    	 		    	$scope.notification = {
    	    	 					  severity: 'error',
    	    	 					  msg : failReason.message,
    	    	 					  show: true
    	    	 				};
    	 					}else
    	 						saveHostTRAConfiguration();
    	 				}
    	 		    }, function (failReason) {
    	 		    	errCnt++;
    	 		    	if(index == traAndLogUpdatesToSave.length-1 && errCnt == traAndLogUpdatesToSave.length){
    	 		    		$scope.isError = true;
    	 					$scope.errorMessage = failReason.message;
    	 					$('#loaderdiv').hide();
	 					}else if(index == traAndLogUpdatesToSave.length-1 && errCnt != traAndLogUpdatesToSave.length){
	 						$('#updateTRAandLog').modal('hide'); 
	 						saveHostTRAConfiguration();
	 					}    	 		    	
    	 		    }); 
			});
        });
	}
	function saveHostTRAConfiguration(){
		teaObjectService.invoke({
			'agentID'  : $scope.selectedObj.agentId,
	        'agentType': $scope.selectedObj.type.agentType,
        	'objectType': $scope.selectedObj.type.name,           		
        	'objectKey': $scope.selectedObj.key,
         	'operation': 'saveHostTraConfiguration',
            'methodType': 'UPDATE',
            'params': { 
	             } 
		}).then(function (message) { 
			//loadApplicationTraConfig()
			populateAllApplications();
			$('#loaderdiv').hide();
			/*$scope.notification = {
			    severity: 'info',
			    msg : message,
			    show: true
			};*/
		}, function (failReason) {
		    $('#loaderdiv').hide(); 
			$scope.notification = {
			   severity: 'error',
			   msg : failReason.message,
			   show: true
			};
		});  
	}
	$scope.loadApplicationTraConfig = function(){
		teaObjectService.invoke({
			'agentID'  : $scope.selectedObj.agentId,
			'agentType': $scope.selectedObj.type.agentType,
			'objectType': $scope.selectedObj.type.name,           		
			'objectKey': $scope.selectedObj.key,
			'operation' : 'loadTraConfiguration',
			'methodType': 'READ'
		}).then(function (data) {
			
		}, function (failReason) {
			
		});	
	}
	$scope.deleteApplication = function(app){
		$scope.selectedObj = app;
		$('#actionConfirmation').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'       
	    });	
	}
//	$scope.editApplication = function(app){
//		$scope.createData.earfilesCreateApp=undefined;
//		$scope.createData.CDDfilesCreateApp=undefined;
//		$('#cddfilepathforCreateApp').val("");
//		$('#earfilepathforCreateApp').val("");
//		$('#earfileforCreateApp').val("");
//		$('#cddfileforCreateApp').val("");
//		$scope.operationTitle = "Edit Deployment";
//		$scope.createData.monitorableOnly = false;
//		$scope.isCreate=false;
//		$scope.isEdit=true;		
//		$scope.isError = false;
//		$scope.createData.applicationName = app.name;
//		$scope.selectedObj =app;
//		$('#createApplication').modal({
//	        show: true,
//	        keyboard: false,
//	        backdrop: 'static'
//	    });
//	}
	
//	$scope.loadApplicationLevelCDD = function(app){
//		teaObjectService.invoke({
//			'agentID'  : app.agentId,
//			'agentType': app.type.agentType,
//			'objectType': app.type.name,           		
//			'objectKey': app.key,
//			'operation' : 'showApplicationCDDContent',
//			'methodType': 'READ'
//		}).then(function (data) {
//			 var oXML;
//			 if (window.ActiveXObject) { //for IE
//				oXML = new ActiveXObject("Microsoft.XMLDOM"); oXML.loadXML(data);
//			 }else {// code for Chrome, Safari, Firefox, Opera, etc.
//				oXML= (new DOMParser()).parseFromString(data, "text/xml");
//			 }
//			 $scope.xmlString = xmlToString(oXML,-1);
//			 $scope.applicationCddContent= $sce.trustAsHtml($scope.xmlString);
//			 $('#applicationCdd').modal({
//				 show: true,
//				 keyboard: false,
//				 backdrop: 'static'
//			 });
//		}, function (failReason) {
//			
//		});	
//	 }
	 function xmlToString(xml,index) {
		var index = index + 1;
		var tab = "";
		for(var t=0 ; t<index;t++){
			tab=tab + "&nbsp;"+"&nbsp;"+"&nbsp;";
		}
		var obj = "";
		if (xml.nodeType == 1) { // element
			//attributes
			if (xml.attributes.length > 0) {
				for (var j = 0; j < xml.attributes.length; j++) {
					var attribute = xml.attributes.item(j);
					obj = obj+tab+"<strong>"+"&lt" + attribute.nodeName +"&gt" +"</strong>" + attribute.nodeValue +"<strong>" +"&lt/" + attribute.nodeName +"&gt" + "</strong>"+ "<br>";	
				}
			}
		}
		if (xml.hasChildNodes()) {//children
			for(var i = 0; i < xml.childNodes.length; i++) {
				var item = xml.childNodes.item(i);
				var nodeName = item.nodeName;
				if (item.nodeType == 1 && item.attributes.length == 0 && item.childNodes.length==1 && item.childNodes.item(0) !=null && item.childNodes.item(0).nodeName =="#text"){
					obj = obj+tab+"<strong>"+"&lt" + nodeName +"&gt" +"</strong>"  + item.innerHTML +"<strong>" +"&lt/" + nodeName +"&gt" + "</strong>"+ "<br>" ;
				}
				else if(nodeName != "#text"){
					obj = obj+tab+"<strong>"+"&lt" + nodeName +"&gt" +"</strong>" + "<br>";
					obj = obj + xmlToString(item,index);
					obj = obj+tab+"<strong>" +"&lt/" + nodeName +"&gt" + "</strong>"+ "<br>";	
				}
			}
		}
		return obj;
	 }
	 $scope.showHotDeployModal = function(app){
		 	$scope.selectedAppForHotDeploy = app;
		 	$scope.hotDeploy = {};
			$scope.hotDeploy.earfilesCreateApp =undefined;
			$scope.hotDeploy.isFileUploaded = true;
			$('#earfileforHotDeploy').val("");
			$('#earfilepathforHotDeploy').val("");
			$('#hotDeployInstance').modal({
				show: true,
				keyboard: false,
				backdrop: 'static'
			});
	}
	$scope.hotDeployApp = function(earfile){
			if(angular.isUndefined(earfile)){
				$scope.hotDeploy.isFileUploaded = false;
				return;
			}
			$('#loaderdiv').show(); 
			$('#hotDeployInstance').modal('hide'); 
			var fd = new FormData();       
	        fd.append("file", earfile);
			$http.post("/teas/fileupload/", fd, {
	            transformRequest: angular.identity,
	            headers: {
	                "Content-Type": undefined
	            }
	        }).success(function(data, status, headers, config) {        	  
	 			 teaObjectService.invoke({
	 				'agentID'  : $scope.selectedAppForHotDeploy.agentId,
		            'agentType': $scope.selectedAppForHotDeploy.type.agentType,
		       		'objectType': $scope.selectedAppForHotDeploy.type.name,           		
		       		'objectKey': $scope.selectedAppForHotDeploy.key,
	            	'operation': 'hotdeployAll',
	                'methodType': 'UPDATE',
	                'params': {                	
	                	earpath : data[0]
	                 	}      		
	 			 }).then(function (resp) { 		
	 				$('#loaderdiv').hide();
	 				populateAllApplications();
	 				/*$scope.notification = {
	 			            severity: 'info',
	 			            msg : resp,
	 			            show: true
	 			    };*/
	 			 }, function (failReason) {
	 				$('#loaderdiv').hide();  					
	 				$scope.notification = {
	 			            severity: 'error',
	 			            msg : failReason.message,
	 			            show: true
	 			    };
	 			 });
	        });
	}
	
	$scope.importApplication = function(files,CDDfiles,EARfiles,applicationName){
		$('#loaderdiv').show(); 
		var fd = new FormData();       
        fd.append("file", files);
        fd.append("file", CDDfiles);
        fd.append("file", EARfiles);
		$http.post("/teas/fileupload/", fd, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": undefined
            }
        }).success(function(data, status, headers, config) {        	  
 			 teaObjectService.invoke({
 				'agentID'  : 'BE',
 	            'agentType': 'BusinessEvents',
            	'objectType': 'BusinessEvents',           		
            	'objectKey': 'BusinessEvents',
            	'operation': 'upload',
                'methodType': 'UPDATE',
                'params': {                	
                	name : applicationName,
               	 	description : "",                	 
               	 	cddpath : data[1],
               	 	earpath : data[2],
               	 	stPath : data[0],
               	 	isCreate : false ,
               	 	isExportedCreate : false
                 	}      		
 			 }).then(function (resp) { 
 				$('#loaderdiv').hide(); 
 				$('#importApplication').modal('hide'); 
 				/*$scope.notification = {
 			            severity: 'info',
 			            msg : resp,
 			            show: true
 			    };*/
 				populateAllApplications();
 			 }, function (failReason) {
 				$('#loaderdiv').hide();  					
 				$scope.isError = true;
				$scope.errorMessage = failReason.message;
 			 });
        });			
	}
	
	$scope.createApplication = function(applicationName,CDDfilesCreateApp,earfilesCreateApp,monitorableOnly) {
		$('#loaderdiv').show(); 
		var fd = new FormData();       
        fd.append("file", earfilesCreateApp);
        fd.append("file", CDDfilesCreateApp);       
		$http.post("/teas/fileupload/", fd, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": undefined
            }
        }).success(function(data, status, headers, config) {       	
 			 teaObjectService.invoke({
 				'agentID'  : 'BE',
 	            'agentType': 'BusinessEvents',
            	'objectType': 'BusinessEvents',           		
            	'objectKey': 'BusinessEvents',
            	'operation': 'upload',
                'methodType': 'UPDATE',
                'params': {
                	 name : applicationName,
                	 description : "",                	 
                	 cddpath : data[1],
                	 earpath :  data[0],
                	 stPath : undefined,
                	 isCreate :$scope.isCreate,
                	 isExportedCreate:false,
                	 isMonitorable:monitorableOnly
                	}      		
 			 }).then(function (resp) {
 				$('#loaderdiv').hide(); 
 				$('#createApplication').modal('hide');  
 				/*$scope.notification = {
 			            severity: 'info',
 			            msg : resp,
 			            show: true
 			    };*/
 				populateAllApplications();
 			 }, function (failReason) {
 				$('#loaderdiv').hide();  			
 				if(failReason.status==500 && failReason.message=="Operation unauthorized" )
 				{
 					console.log("You are unauthorized to perform this action.");
 					$('#createApplication').modal('hide');  
 					$scope.notification = {
 							severity: 'error',
 							msg : 'You are not authorized to perform this action.',
 							show: true
 					};
 				}	
 				else
 				{
 					$scope.isError = true;
 					$scope.errorMessage = failReason.message;
 				}
 			 });
        });	
	}
	$scope.doEditApplication = function(CDDfilesCreateApp,earfilesCreateApp) {
		$('#loaderdiv').show(); 
		var fd = new FormData();       
        fd.append("file", earfilesCreateApp);
        fd.append("file", CDDfilesCreateApp);       
		$http.post("/teas/fileupload/", fd, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": undefined
            }
        }).success(function(data, status, headers, config) { 
        	var isCDDMentioned=false;
        	var isEARMentioned=false;
        	if($scope.isEdit){
        		if(typeof CDDfilesCreateApp ==='undefined' || '' === CDDfilesCreateApp)        			
        			data[1]="";
        		else
        			isCDDMentioned = true;
        		if(typeof earfilesCreateApp ==='undefined' || '' === earfilesCreateApp)
        			data[0]="";
        		else
        			isEARMentioned=true;
        	}
 			 teaObjectService.invoke({
 				'agentID'  : $scope.selectedObj.agentId,
	            'agentType': $scope.selectedObj.type.agentType,
	       		'objectType': $scope.selectedObj.type.name,           		
	       		'objectKey': $scope.selectedObj.key,
            	'operation': 'upload',
                'methodType': 'UPDATE',
                'params': {            	 
                	 cddpath : data[1],
                	 earpath :  data[0],
 			 		 isCDDMentioned :isCDDMentioned,
 			 		 isEARMentioned:isEARMentioned,
 			 		 version:$scope.selectedObj.config.version
                	}      		
 			 }).then(function (resp) {
 				$('#loaderdiv').hide();
 				$('#createApplication').modal('hide');  
 				/*$scope.notification = {
 			            severity: 'info',
 			            msg : resp,
 			            show: true
 			    };*/
 				populateAllApplications();
 			 }, function (failReason) {
 				$('#loaderdiv').hide();  			
 				if(failReason.status==500 && failReason.message=="Operation unauthorized" )
 				{
 					console.log("You are unauthorized to perform this action.");
 					$('#createApplication').modal('hide');  
 					$scope.notification = {
 							severity: 'error',
 							msg : 'You are not authorized to perform this action.',
 							show: true
 					};
 				}	
 				else
 				{
 					$scope.isError = true;
 					$scope.errorMessage = failReason.message;
 				}
 			 });
        });	
	}	
//	$scope.showImportMigratedApplicationsWizard = function(){
//		$scope.exportedFileLocation=undefined;
//		$scope.isError = false;
//		$('#zipfilepathforMigrateApp').val("");
//		$('#zipfileforMigrateApp').val("");
//		$scope.errorMessage="";
//		$('#importMigratedAppWizard').modal({
//			show: true,
//			keyboard: false,
//			backdrop: 'static'
//		});
//	}
	$scope.importMigratedApplications = function(zipfilepath){
		$('#loaderdiv').show(); 
		var fd = new FormData();     
        fd.append("file", zipfilepath);
		$http.post("/teas/fileupload/", fd, {
            transformRequest: angular.identity,
            headers: {
                "Content-Type": undefined
            }
        }).success(function(data, status, headers, config) {		
			teaObjectService.invoke({
				'agentID'  : 'BE',
				'agentType': 'BusinessEvents',
				'objectType': 'BusinessEvents',           		
				'objectKey': 'BusinessEvents',
				'operation': 'migrate',
				'methodType': 'READ',
				'params': {
					exportedfilePath : data[0],
					source : 'GUI'
					}      		
				 }).then(function (resp) {
					 $scope.migratedData = resp;
					 $('#importMigratedAppWizard').modal('hide');
					 $('#loaderdiv').hide(); 
					 showCreateMigratedApplicationWizard();
				 }, function (failReason) {
					 $('#loaderdiv').hide(); 
					 $scope.errorMessage = failReason.message;
				 });
		});
	}
	function showCreateMigratedApplicationWizard(){
		$scope.migratedAppStep=0;
		$scope.isError = false;
		$scope.errorMessage="";
		$scope.appsSelected=false;
		$scope.machinesCreated=false;
		//$scope.app = migratedData[0][$scope.migratedAppStep];
		//$scope.machines = migratedData[1];
		$('#createMigratedApplicationWizard').modal({
			show: true,
			keyboard: false,
			backdrop: 'static'
		});
	}
	$scope.proceedAppsSelect = function(){
		$scope.appsSelected=true;
		
		//remove unselected applications
		var validApplications = [];
		for(var i=0;i<$scope.migratedData[0].length;i++){
			if(($scope.migratedData[0][i].isSelected==true)){
				validApplications.push($scope.migratedData[0][i]);
			}
		}
		$scope.migratedData[0] = validApplications;
		
		//remove machines not related to selected applications
		var validMachines = [];
		for(var i=0;i<$scope.migratedData[1].length;i++){
			for(var k=0;k<$scope.migratedData[0].length;k++){
				if( ($scope.migratedData[1][i].relatedApps.indexOf( $scope.migratedData[0][k].name ))!=-1){
					validMachines.push($scope.migratedData[1][i]);
					break;
				}
			}
		}
		$scope.migratedData[1] = validMachines;
		
	}
	
	/*$scope.disableCreate=function(){
		if($scope.newHostObj.hostName=='' || angular.isUndefined($scope.newHostObj.hostName) || $scope.newHostObj.ipAdress=='' || $scope.newHostObj.username=='' || $scope.newHostObj.password=='')
			return true;
		return false;
	}*/
	
	$scope.populateDeployPath = function(self){
		for(var i=0;i< $scope.migratedData[0].length;i++){
			for(var j=0;j< $scope.migratedData[0][i].instance.length;j++){
				var instance = $scope.migratedData[0][i].instance[j];
				if(instance.machineName ==  self.machine.machineName){
					$scope.migratedData[0][i].instance[j].deployPath = self.machine.deploymentPath;
				}
			}
		}
	}
	
	$scope.checkForm = function(){
		for(var i=0;i< $scope.migratedData[0].length;i++){
			for(var j=0;j< $scope.migratedData[0][i].instance.length;j++){
				var instance = $scope.migratedData[0][i].instance[j];
				if( (instance.deployPath=="" || instance.deployPath==undefined || instance.deployPath==null) ||
					(instance.instanceName=="" || instance.instanceName==undefined || instance.instanceName==null) ||
					(instance.machineName=="" || instance.machineName==undefined || instance.machineName==null) ||
					(instance.jmxPort=="" || instance.jmxPort==undefined || instance.jmxPort==null) ||
					(instance.pu=="" || instance.pu==undefined || instance.pu==null) ){
					return true;
				}
			}
		}
		for(var i=0;i< $scope.migratedData[1].length;i++){
			var machine = $scope.migratedData[1][i];
			if(  (machine.deploymentPath=="" || machine.deploymentPath==undefined || machine.deploymentPath==null) || 
				 (machine.machineName=="" || machine.machineName==undefined || machine.machineName==null) ||
				 (machine.ip=="" || machine.ip==undefined || machine.ip==null) ||
				 (machine.os=="" || machine.os==undefined || machine.os==null) ||
				 (machine.sshUser=="" || machine.sshUser==undefined || machine.sshUser==null) ||
				 (machine.sshPass=="" || machine.sshPass==undefined || machine.sshPass==null) ||
			  	 (machine.sshPort=="" || machine.sshPort==undefined || machine.sshPort==null) ){
				return true;
			}
		}
		return false;
	}
	
	$scope.moveToNextMigratedApp = function(){
		$scope.migratedAppStep++;
		$scope.isError = false;
		$scope.app = $scope.migratedData[0][$scope.migratedAppStep];
		$scope.errorMessage="";
	}
	$scope.moveToPrevMigratedApp = function(){
		$scope.migratedAppStep--;
		$scope.isError = false;
		$scope.app = $scope.migratedData[0][$scope.migratedAppStep];
		$scope.errorMessage="";
	}
		
	$scope.migrateAll = function(){
		teaObjectService.invoke({
			'agentID'  : 'BE',
	        'agentType': 'BusinessEvents',
        	'objectType': 'BusinessEvents',           		
        	'objectKey': 'BusinessEvents',
			'operation' : 'createMigratedApplication',
			'methodType':'UPDATE',
	        'params': {                	
	        	modifiedList : $scope.migratedData 
	        }      		
		}).then(function (message) {
			$('#loaderdiv').hide(); 
			$('#createMigratedApplicationWizard').modal('hide');  
			populateAllApplications();
			
		}, function (failReason) {
			 $('#loaderdiv').hide(); 
			 $scope.isError = true;
			 $scope.errorMessage=failReason.message;
		});	
	}
	
//	$scope.exportApplication  = function(app){
//		 $scope.selectedObj = app;
//			$('#loaderdiv').show(); 
//			teaObjectService.invoke({
//				'agentID'  : $scope.selectedObj.agentId,
//	            'agentType': $scope.selectedObj.type.agentType,
//	       		'objectType': $scope.selectedObj.type.name,           		
//	       		'objectKey': $scope.selectedObj.key,
//	        	'operation': 'export',
//				'methodType': 'READ'
//			}).then(function (data) {
//				$('#loaderdiv').hide(); 
//				var url = window.location.protocol + "//" + window.location.host + '/tea/' + data;
//	            //download the file from the URL
//	            $scope.downloadFromURL(url);
//			}, function (failReason) {
//				$('#loaderdiv').hide(); 
//				$scope.notification = {
//		            severity: 'error',
//		            msg : failReason.message,
//		            show: true
//				};
//			});	 
//		}
		$scope.downloadFromURL = function (downloadurl) {
	        $http({
	            method: 'GET',
	            url: downloadurl
	        }).success(function (data) {
	            if (navigator.appVersion && navigator.appVersion.indexOf('MSIE') >= 0) {
	                //The way to make download work on IE
	                navigator.msSaveBlob(new Blob([data], {type: "application/octet-stream"}));
	            } else {
	                //For browsers other than IE, we can just have download=true to support the download of the file.
	                window.location.href = downloadurl + "?download=true";
	            }
	        });
	    }
	
	//Check user has permission or not
	$scope.hasPermission = function(permission){
		var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
		return utilService.checkPermission(permission,privileges);
	};
	$scope.isEditEnabled = function(){
		if($('#cddfilepathforCreateApp').val() == "" && $('#earfilepathforCreateApp').val()=="")
			return false;
		return true;
		
	}
	$scope.allowTRAConfigurationSave = function(){
		$scope.isAllowSaveTRAConfig = true;
	}
   }, function (failReason) {
		$('#loaderdiv').hide(); 
   })
   $scope.updateSize=function()
   {
		if($scope.createData.monitorableOnly===false){
			$("#createApplication").css("height","420px");
		}else{
			$("#createApplication").css("height","300px");
		}
   }
}]);
beConfigModule.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
            	if(!scope.$$phase){
            	scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });
            	}else
            	modelSetter(scope, element[0].files[0]);
                
            });
        }
    };
}]);
beConfigModule.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);
beConfigModule.directive('onReadFile', function ($parse) {
	return {
		restrict: 'A',
		scope: false,
		link: function(scope, element, attrs) {
            var fn = $parse(attrs.onReadFile);
            
			element.on('change', function(onChangeEvent) {
				var reader = new FileReader();
                
				reader.onload = function(onLoadEvent) {
					scope.$apply(function() {
						fn(scope, {$fileContent:onLoadEvent.target.result});
					});
				};

				reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
			});
		}
	};
});
	