package com.tibco.cep.ui.monitor.io{
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.UIConsts;
	
	
	public class LocalPSVRClient extends PSVRClient implements IUpdateable{
		
		public function LocalPSVRClient(){
		}
		
		public override function getApplicationConfig(callBack:IUpdateable):void{
			var url:String = "data/app_config.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("getApplicationConfig",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function login(username:String, password:String, callBack:IUpdateable):void{
			var url:String = "data/login.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("login",url,null,new DelegateUpdateableImpl(this,callBack));
			psvrRequest.sendRequest();
		}
		
		public override function logout(callBack:IUpdateable):void{
			super.logout(null);
			if(callBack != null) callBack.update("logout", null);
		}
		
		
		//Topology Calls
		public override function getTopologyXML(callBack:IUpdateable):void{
			var url:String = "data/topology.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("gettopologyxmlfile",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function getSiteTopology(callBack:IUpdateable):void{
			var url:String = "data/site_small.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("gettopology",url,null,callBack);
			psvrRequest.sendRequest();
		}
		public override function updateSiteTopology(callBack:IUpdateable):void{
			var url:String = "data/site_small.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("updatetopololgy",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function purgeTopology(callBack:IUpdateable):void{
			var url:String = "data/site_small.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("purgetopology",url,null,new DelegateUpdateableImpl(this,callBack));
			psvrRequest.sendRequest();
		}
		public override function getAgentMethodsLayout(callBack:IUpdateable):void{
			var url:String = "data/agent_methodslayout_demo.xml";
			//if it exists locally there is no need to do HTTP Request
			var psvrRequest:PSVRAgent = new PSVRAgent("getagentmethodslayout",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function getProcessMethodsLayout(callBack:IUpdateable):void{
			var url:String = "data/process_methodslayout_demo.xml";
			//if it exists locally there is no need to do HTTP Request
			var psvrRequest:PSVRAgent = new PSVRAgent("getprocessmethodslayout",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		//Panel Calls
		public override function getPanelComponents(monitoredEntityID:String, callBack:IUpdateable):void{
			var url:String = "data/panels/" + monitoredEntityID.replace("#","") + "_demo.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("getpanelcomponents",url,null,callBack);
			psvrRequest.sendRequest();
		}
		public override function getMethodPanel(monitoredEntityID:String, methodID:String, callBack:IUpdateable):void{
			var id:String = "cover-all"; //methodID.split("#")[0];
			var url:String = "data/panels/methodPanels/" + id + "_demo.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("getmethodpanel",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		//Method invocation
		public override function invokeMethod(parameters:String, monitoredEntityID:String, methodID:String, callBack:IUpdateable, operation:String="invokeagentmethod"):void {
			var id:String = "tabularData";			//uncomment to simulate tabular data 
//			var id:String = "singularValue";		//uncomment to simulate singular value data
//			var id:String = "error";				//uncomment to test errors
			var url:String = "data/methodInvocation/" + id + "_demo.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent(operation,url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function invokeResumeBtn(parameters:String, monitoredEntityID:String, methodID:String, callBack:IUpdateable, operation:String="invokeresumebtn"):void {
			invokeMethod(parameters, monitoredEntityID, methodID, callBack, operation);	
	 	}
	 	
	 	public override function invokePauseBtn(parameters:String, monitoredEntityID:String, methodID:String, callBack:IUpdateable, operation:String="invokepausebtn"):void {
			invokeMethod(parameters, monitoredEntityID, methodID, callBack, operation);	
	 	}
		
		public override function startPU(monitoredEntityID:String, username:String, password:String, callBack:IUpdateable):void {
//			var url:String = "data/methodInvocation/startPU_demo.xml";
//			var url:String = "data/editGV/error_demo.xml";
			var url:String = "data/methodInvocation/error_demo.xml";
			
			var psvrRequest:PSVRAgent = new PSVRAgent("startpu",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		//TODO: Put default values ??
		public override function startStopThreadAnalyzer(monitoredEntityID:String, isStartTA:Boolean, threadReportDir:String, 
							samplingInterval:String, username:String, password:String, callBack:IUpdateable):void{ 
			var url:String = "data/methodInvocation/startStopThreadAnalyzer_demo.xml";
			
			var psvrRequest:PSVRAgent = new PSVRAgent("startstopta",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function executeCommand(monitoredEntityID:String, username:String, password:String, command:String, callBack:IUpdateable):void { 
			var url:String = "data/methodInvocation/executeCommand_demo.xml";
			
			var psvrRequest:PSVRAgent = new PSVRAgent("executecommand",url,null,callBack);
			psvrRequest.sendRequest();
		
		}
		
		public override function deployDU(monitoredEntityID:String, selDusStIdsTokenStr:String, username:String, password:String, callBack:IUpdateable):void {
			var url:String = "data/methodInvocation/deployPU_demo.xml";
			
			var psvrRequest:PSVRAgent = new PSVRAgent(UIConsts.DEPLOY_DU,url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function getGVs(monitoredEntityID:String, selDusStIdsTokenStr:String, callBack:IUpdateable):void {
			var url:String = "data/editGV/getEmptyGVMultDUs_demo.xml";
//			var url:String = "data/editGV/getGVMultDUs_demo.xml";
//			var url:String = "data/editGV/getGV_demo.xml";
//			var url:String = "data/editGV/error_demo.xml";
			
			var psvrRequest:PSVRAgent = new PSVRAgent("getgvs",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function setGVs(monitoredEntityID:String, newGVValue:String, callBack:IUpdateable):void {
			var url:String = "data/editGV/setGV_demo.xml";
			
			var psvrRequest:PSVRAgent = new PSVRAgent(UIConsts.SET_GVS,url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		
		//Pane Calls
		public override function getPaneConfig(monitoredEntityID:String,pane:MetricPane, callBack:IUpdateable):void{
		
		}		
		
		public override function getPaneData(monitoredEntityID:String, pane:MetricPane, callBack:IUpdateable):void{
			//var url:String = "data/" + monitoredEntityID + "_" + pane.type + ".xml";
			if(pane.type == null) return;
			var url:String = "data/" + pane.type + "_demo.xml";
			var psvrRequest:PSVRAgent = new PSVRAgent("getpanedata",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function unsubscribeAllPanes(callBack:IUpdateable):void{
			super.unsubscribeAllPanes(callBack);
		}
		
	}
}