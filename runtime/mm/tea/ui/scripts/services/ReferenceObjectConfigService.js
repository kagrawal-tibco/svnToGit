/**
 * Created by priyanka on 30/4/15
 */
beConfigModule.factory(
    'ReferenceObjectConfigService',
    ['$q', 'teaObjectService',function($q,teaObjectService){
        var referenceObjectMap = [];
        var appObjects;
        var selectedApp;
        var selectedInstance;
        var selectedHost;
   	 	var isAppsObject =false;
   	 	var selectedNavObject="instances";
   	 	var selectedProcessingUnit;
   	 	var selectedProcessingUnitAgent;
   	 	var selectedAppName;
   	 	var breadCrumbs;
   	 	var loadedChartsConfig;
   	 	var selectedMasterHost;
   	    var loadedSelfChartsConfig;
   	    var enableAdditionalCharts;
   	 
	 	if(Storage && typeof localStorage.getItem("breadCrumbs") != 'undefined')
	 		breadCrumbs=JSON.parse(localStorage.getItem("breadCrumbs"));
	 	if(Storage && typeof localStorage.getItem("navObject")!= 'undefined')
	 		selectedNavObject=JSON.parse(localStorage.getItem("navObject"));
	 	if(Storage && typeof localStorage.getItem("selectedApp") != 'undefined')
	 		selectedApp=JSON.parse(localStorage.getItem("selectedApp"));
	 	if(Storage && typeof localStorage.getItem("selectedInstance") != 'undefined')
	 		selectedInstance=JSON.parse(localStorage.getItem("selectedInstance"));
	 	if(Storage && typeof localStorage.getItem("selectedHost") != 'undefined')
	 		selectedHost=JSON.parse(localStorage.getItem("selectedHost"));
	 	if(Storage && typeof localStorage.getItem("selectedProcessingUnit") != 'undefined')
	 		selectedProcessingUnit=JSON.parse(localStorage.getItem("selectedProcessingUnit"));
	 	if(Storage && typeof localStorage.getItem("selectedProcessingUnitAgent") != 'undefined')
	 		selectedProcessingUnitAgent=JSON.parse(localStorage.getItem("selectedProcessingUnitAgent"));
	 	if(Storage && typeof localStorage.getItem("runningAgent") != 'undefined')
	 		selectedInstanceAgent=JSON.parse(localStorage.getItem("runningAgent"));
	 	if(Storage && typeof localStorage.getItem("chartsConfig") != 'undefined')
	 		loadedChartsConfig=JSON.parse(localStorage.getItem("chartsConfig"));
	 	if(Storage && typeof localStorage.getItem("selectedMasterHost") != 'undefined')
	 		selectedMasterHost=JSON.parse(localStorage.getItem("selectedMasterHost"));
	 	if(Storage && typeof localStorage.getItem("selfChartsConfig") != 'undefined')
	 		loadedSelfChartsConfig=JSON.parse(localStorage.getItem("selfChartsConfig"));
	 	if(Storage && typeof localStorage.getItem("enableAdditionalCharts") != 'undefined')
	 		enableAdditionalCharts=JSON.parse(localStorage.getItem("enableAdditionalCharts"));
   	 	return {
            addReferenceObject: function(referenceName, referenceObject){
                referenceObjectMap[referenceName] = referenceObject;
            },
            getReferenceObject: function(referenceName){
                return referenceObjectMap[referenceName];
            }, 
            getSelectedNavObject : function(){
            	return selectedNavObject;
            },
            setSelectedNavObject :function(obj){
            	selectedNavObject = obj;
            },
            getBreadCrumbObject : function(){
            	return breadCrumbs;
            },
            setBreadCrumbObject :function(){
            	breadCrumbs = [];
            },
            setSelectedApp: function(app){            
            	selectedApp = app;
            },
            getSelectedApp: function(){            	
                return selectedApp;
            },
            setAppObjects: function(appObject){
            	appObjects = appObject;
            },
            getAppObjects: function(){
                return appObjects;
            },
            setSelectedInstance: function(instance){
            	selectedInstance = instance;
            },
            getSelectedInstance: function(){
                return selectedInstance;
            },
            setSelectedHost: function(host){
            	selectedHost = host;
            },
            getSelectedHost: function(){
                return selectedHost;
            },
            setSelectedMasterHost: function(host){
            	selectedMasterHost = host;
            },
            getSelectedMasterHost: function(){
                return selectedMasterHost;
            },
            setSelectedProcessingUnit: function(pu){
            	selectedProcessingUnit = pu;
            },
            getSelectedProcessingUnit: function(){
                return selectedProcessingUnit;
            },
            setSelectedProcessingUnitAgent: function(processingUnitAgent){
            	selectedProcessingUnitAgent = processingUnitAgent;
            },
            getSelectedProcessingUnitAgent: function(){
                return selectedProcessingUnitAgent;
            },
            setSelectedInstanceAgent: function(instanceAgent){
            	selectedInstanceAgent = instanceAgent;
            },
            getSelectedInstanceAgent: function(){
                return selectedInstanceAgent;
            },  
            setChartsConfig: function(chartsConfig){
            	loadedChartsConfig = chartsConfig;
            },
            getChartsConfig: function(){
                return loadedChartsConfig;
            },
            setSelfChartsConfig: function(chartsConfig){
            	loadedSelfChartsConfig = chartsConfig;
            },
            getSelfChartsConfig: function(){
                return loadedSelfChartsConfig;
            },
            setEnableAdditionalCharts: function(passedEnableAdditionalCharts){
            	enableAdditionalCharts=passedEnableAdditionalCharts;
            },
            isEnableAdditionalCharts: function(){
                return enableAdditionalCharts;
            }
        }
 }]);