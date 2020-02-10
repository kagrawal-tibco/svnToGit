/**
 * Created by priyanka on 09/10/15
 */
beConfigModule.factory('utilService',function(){
		var isAgentNotReachable = true;
		var timeDimensionMap = {"minutes":"3600","hours":"86400"};
		var refreshTimeDimensionMap = {"minutes":"301","hours":"3601"};
		
   	 	return {
   	 		getAgentReachable : function(){
   	 			return isAgentNotReachable;
   	 		},
   	 		setAgentRechable : function (val){
   	 			isAgentNotReachable = val;
   	 		},
   	 		getDateFromLong : function(milliSec){
   	 			var date = new Date(milliSec);
   	 			var dateString = date.toString().substr(0,date.toString().indexOf("GMT"));
   	 			return dateString;
   	 		},
            getFormattedDate: function(upTime){
				var oneDay = 24*60*60*1000;
				var days = Math.floor(upTime/(oneDay)),
				hours = Math.floor(upTime % oneDay/ 36e5),
			    minutes = Math.floor(upTime % 36e5 / 60000),
			    seconds = Math.floor(upTime % 60000 / 1000);
				var formattedUpTime = "" ;
				if(days > 0)
					formattedUpTime = days+"d," +hours + "h," + minutes + "m," + seconds +"s";
				else if(hours > 0)
					formattedUpTime = hours + "h," + minutes + "m," + seconds +"s";
				else if(minutes > 0)
					formattedUpTime = minutes + "m," + seconds +"s";
				else if(seconds > 0)
					formattedUpTime =seconds +"s";
				
				return formattedUpTime;
            },
            convertMemoryToByte : function(value){
            	var memoryInBytes = 0;
            	if(value.toLowerCase().indexOf('k')>-1)
            		memoryInBytes  = parseInt(value.slice(0,value.length-1)) * 1024;
				else if(value.toLowerCase().indexOf('m')>-1)
					memoryInBytes  = parseInt(value.slice(0,value.length-1)) * 1048576;
				else if(value.toLowerCase().indexOf('g')>-1)
					memoryInBytes  = parseInt(value.slice(0,value.length-1)) * 1073741824;
				else if(value.toLowerCase().indexOf('b')>-1)
					memoryInBytes  = parseInt(value.slice(0,value.length-1));
            	return memoryInBytes;
            },
            checkPermission:function(permission,privileges){

        		var hasPermission=false;
        		
        		//TEA Admin have all permissions.
        		for (var i = 0; i < privileges.length; i++) {
                    if (privileges[i].product === "TEA" && privileges[i].objectType === "all") {
                        if (privileges[i].permissions.full_control) {
                        	return true;
                        }
                    }
                }
        	
        		//Permissions other than TEA Admin user
        		switch(permission)
        		{
        			//Check user has permission for create/import/edit deployment
        			case 1:{
        				 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.CREATE_DEPLOYMENT_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for delete deployment
        			case 2:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DELETE_DEPLOYMENT_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;			

        			//Check user has permission for hot deployment
        			case 3:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.HOT_DEPLOY_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        		
        			//Check user has permission for create host
        			case 4:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.CREATE_HOST_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for edit host
        			case 5:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_HOST_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for delete host
        			case 6:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DELETE_HOST_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for create instance
        			case 7:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.CREATE_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for start instance
        			case 8:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.START_PU_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for stop instance
        			case 9:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.STOP_PU_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			//Check user has permission for kill instance
        			case 10:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.KILL_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			
        			
        			//Check user has permission for deploy instance
        			case 11:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DEPLOY_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			

        			//Check user has permission for undeploy instance
        			case 12:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UNDEPLOY_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        		

        			//Check user has permission for update instance global variables
        			case 13:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_GV_VAR_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			

        			//Check user has permission for update instance system properties
        			case 14:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_SYSTEM_PROPS_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			


        			//Check user has permission for update instance JVM properties
        			case 15:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_JVM_PROPS_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        		
        			//Check user has permission for copy instance
        			case 16:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.COPY_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;	
        			
        			//Check user has permission for update of instance log level
        			case 17:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_LOG_LEVEL_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;

        			//Check user has permission for update of instance
        			case 18:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;

        			//Check user has permission for delete of instance
        			case 19:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DELETE_INSTANCE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for suspend running agent
        			case 20:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.SUSPEND_AGENT_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			
        			break;
        			//Check user has permission for resume running agent
        			case 21:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.RESUME_AGENT_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for update deployment
        			case 22:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_DEPLOYMENT_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission to read the be tea agent components
        			case 23:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.BE_TEA_AGENT_READ_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for upload the TRA
        			case 24:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPLOAD_TRA_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for upload the classes
        			case 25:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPLOAD_CLASSES_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for deploy the uploaded classes
        			case 26:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DEPLOY_CLASSES_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for upload the rule template
        			case 27:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPLOAD_RULE_TEMPLATE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for deploy the uploaded the rule template
        			case 28:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DEPLOY_RULE_TEMPLATE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			//Check user has permission for update business events properties
        			case 29:{
        			 
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_BE_PROPS_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			case 30:{
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.RULE_ADMIN_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			case 31:{
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.ADD_PROFILE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			case 32:{
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPDATE_PROFILE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			case 33:{
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.DELETE_PROFILE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			case 34:{
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.SET_DEFAULT_PROFILE_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        			case 35:{
        				for (var i = 0; i < privileges.length; i++) {
        					if (privileges[i].product === "BusinessEvents" && privileges[i].objectType === "all") {
        						if (privileges[i].permissions.UPLOAD_CUSTOM_JAR_PERMISSION) {
        							hasPermission=true;
        						}
        					}				
        				}
        			}
        			break;
        		 default:
        			 
        		}
        		return hasPermission;
        	
            	
            },
            getDefaultChartOptions:function()
    		{
    			defaultChartOptions = {
    				credits: {
    					enabled: false
    				},
    				title: {
    					text: "Default Text"
    				},
    				legend: {
    					padding: 2,
    					margin: 2
    				},
    				margin:2,
    				xAxis: {
    					title: {
    						text: 'x-axis'
    					},
    					type: 'datetime',
    					minRange: 5000
    				},
    				scrollbar: {
    					enabled: false
    				},
    				yAxis: {
    					title: {
    						text: 'y-axis'
    					},
    					plotLines: [{
    						value: 0,
    						width: 1,
    						color: '#808080'
    					}]
    				},
    				chart: {
    					type: 'spline'  ,
    					height : 250
    				},
    				tooltip: {
    					formatter: function () {
    						return '<b>' + this.series.name + '</b><br/>' +
    						Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
    						Highcharts.numberFormat(this.y, 0) +"bytes";
    					}
    				},
    				isUpdated:0,
    				chartId : 'defaultId',
    				series: [],
    				getData : function getData(i) {
    					return angular.copy(this.series[i].data);
    				},
    				setData : function setDonutChartData(i,seriesData) {
    					this.series[i].data = seriesData;
    					this.isUpdated += 1;
    				},
    				plotOptions: {
    		            series: {
    		                marker: {
    		                    enabled: true,
    		                    symbol: 'circle',
    		                    radius: 4
    		                }
    		            }
    		        },
    				xconfig:{},
    				yconfig:{}

    			};
    			return defaultChartOptions;
    		},
    		getFormattedTextForChartTooltip:function(type,data,precision)
    	   	{
    			try
    				{
    					if(type=="time"){
    						return Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',data);	
    					}
    					else if(type=="percent")
    					{
    						return Highcharts.numberFormat(data, precision)+" %";
    					}
    					else if(type=="mb")
    					{
    						return Highcharts.numberFormat(data, precision)+" MBs";
    					}
    					else if(type=="number")
    					{
    						return Highcharts.numberFormat(data, precision);
    					}
    					else
    						return data;
    				}
    			catch(err)
    			{
    				console.log("Error while formatting data"+err);
    				return data;
    			}
    	   	},
    		getChartTimeDimensionThreshold:function(dim,isRefresh)
    		{
    			try
    			{
    				if(!isRefresh)
    					return timeDimensionMap[dim];
    				else
    					return refreshTimeDimensionMap[dim];
    			}
    			catch(err)
    			{
    				console.log("Error while getting map"+err);
    				return data;
    			}
    		}
        }
 });