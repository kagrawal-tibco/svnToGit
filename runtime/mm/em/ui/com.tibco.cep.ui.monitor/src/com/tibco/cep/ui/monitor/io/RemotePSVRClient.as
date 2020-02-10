package com.tibco.cep.ui.monitor.io{
	
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.UIConsts;
	
	import flash.utils.Dictionary;
	
	import mx.utils.Base64Encoder;
	
	
	public class RemotePSVRClient extends PSVRClient implements IUpdateable {
		
		private static const CONTEXT_PATH:String = "/HttpChannel/"; 
		private static const GVS_XML_NS:String = "xmlns=\"http://www.example.org/SetGVSchema\"";
		
		private var _baseURL:String;
		private var _hostURL:String;
		
		private var _passWordEncoder:Base64Encoder;
		private var _username:String;
		private var _password:String;
		private var _encodedPwd:String;
		
		function RemotePSVRClient(hostURL:String=null){
			_hostURL = hostURL;
			if (hostURL != null) {
				_baseURL = hostURL + CONTEXT_PATH;
			}
			else {
				_baseURL = CONTEXT_PATH;
			}
			_passWordEncoder = new Base64Encoder();
		}
		
		public override function get username():String {
			return _username;
		}
		
		public override function get password():String {
			return _password;
		}
		
		public override function getApplicationConfig(callBack:IUpdateable):void{
			var url : String = "/app_config.xml";
			if (_hostURL != null){
				url = _hostURL + "/app_config.xml";
			}
			var psvrRequest:PSVRAgent = new PSVRAgent("getAppConfig",url,null,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function login(username:String, password:String, callBack:IUpdateable):void{
			var url : String = _baseURL + "login";
			var qParams : Dictionary = new Dictionary(true);
			
			_password = password;
			_username = username;
			
			_passWordEncoder.encode(password);
			_encodedPwd = _passWordEncoder.toString();
			
			qParams["username"] = encodeURIComponent(_username);
			qParams["password"] = encodeURIComponent(_encodedPwd);
			
			var psvrRequest:PSVRAgent = new PSVRAgent("login",url,qParams,new LoginDelegateUpdateableImpl(this,callBack));
			psvrRequest.sendRequest();
		}
		
		/** Returns the encoded key using Base64 encoding */
		private function getBase64Encode(key:String):String {
			var pwEncoder:Base64Encoder = new Base64Encoder();
			//do not insert new line characters after every 76 characters because it will cause
			//an error in the HTTP headers for longer passwords
			pwEncoder.insertNewLines = false;		
			pwEncoder.encode(key)
			return pwEncoder.toString();
		}
		
		public override function logout(callBack:IUpdateable):void{
			super.logout(null);
			var url : String = _baseURL + "logout";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			
			var psvrRequest:PSVRAgent = new PSVRAgent("logout",url,qParams,callBack);
			_token = null;
			_username = null;
			psvrRequest.sendRequest();
		}
		
		
		//Topology calls
		public override function getTopologyXML(callBack:IUpdateable):void {
			var url : String = _baseURL + "gettopologyxmlfile";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			
			var psvrRequest:PSVRAgent = new PSVRAgent("gettopologyxmlfile",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function getSiteTopology(callBack:IUpdateable):void{
			var url : String = _baseURL + "gettopology";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			
			var psvrRequest:PSVRAgent = new PSVRAgent("gettopology",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function updateSiteTopology(callBack:IUpdateable):void{
			var url : String = _baseURL + "gettopology";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			
			var psvrRequest:PSVRAgent = new PSVRAgent("updatetopololgy",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function purgeTopology(callBack:IUpdateable):void{
			var url : String = _baseURL + "purgetopology";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			
			var psvrRequest:PSVRAgent = new PSVRAgent("purgetopology",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function getAgentMethodsLayout(callBack:IUpdateable):void{
			var url : String = _baseURL + "getmethodslayout";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			qParams["entitytype"] = "agent";
			
			var psvrRequest:PSVRAgent = new PSVRAgent("getagentmethodslayout",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function getProcessMethodsLayout(callBack:IUpdateable):void{
			var url : String = _baseURL + "getmethodslayout";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			qParams["entitytype"] = "process";
			
			var psvrRequest:PSVRAgent = new PSVRAgent("getprocessmethodslayout",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		
		//Panel Calls
		public override function getPanelComponents(monitoredEntityID:String, callBack:IUpdateable):void{
			var url:String = _baseURL + "getpagetemplate";
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;			
			var psvrRequest:PSVRAgent = new PSVRAgent("getpanelcomponents",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		public override function getMethodPanel(monitoredEntityID:String, methodID:String, callBack:IUpdateable):void{
//			var url:String = _baseURL + "getmethodpanel";
			var url:String = _baseURL + "getmethoddescription";
			var qParams : Dictionary = new Dictionary(true);
			var methodIDSplit:Array = methodID.split("#");
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;	//In this method monitorable ID is used in studio just for logging
			qParams["entityType"] = methodIDSplit[methodIDSplit.length-3];
			qParams["methodGroup"] = methodIDSplit[methodIDSplit.length-2];
			qParams["methodName"] = methodIDSplit[methodIDSplit.length-1];
			var psvrRequest:PSVRAgent = new PSVRAgent("getmethodpanel",url,qParams,callBack);			
			psvrRequest.sendRequest();
		}
		
		//Method invocation
		public override function invokeMethod(parameters:String, monitoredEntityID:String, methodID:String, 
												callBack:IUpdateable, operation:String="invokeagentmethod"):void {
			var url:String = _baseURL + "invokeentitymethod";
			var qParams : Dictionary = new Dictionary(true);
			const TOKEN:String = "#";
			var methodIDSplit:Array = methodID.split(TOKEN);
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;	//need monitorable ID in studio rule to retrieve properties
			qParams["entityType"] = methodIDSplit[methodIDSplit.length-3];
			qParams["methodGroup"] = methodIDSplit[methodIDSplit.length-2];
			qParams["methodName"] = methodIDSplit[methodIDSplit.length-1];
			qParams["paramsTokenStr"] = encodeURIComponent(parameters);		//Encode to support multibyte and multilingual characters
			//pass the username and password behind the scenes for every method invocation.
			qParams["userName"] = encodeURIComponent(_username);	
			qParams["pwd"] = encodeURIComponent(_encodedPwd);	
			var psvrRequest:PSVRAgent = new PSVRAgent(operation,url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function invokeResumeBtn(parameters:String, monitoredEntityID:String, methodID:String, callBack:IUpdateable, operation:String="invokeresumebtn"):void {
			invokeMethod(parameters, monitoredEntityID, methodID, callBack, operation);	
	 	}
	 	
	 	public override function invokePauseBtn(parameters:String, monitoredEntityID:String, methodID:String, callBack:IUpdateable, operation:String="invokepausebtn"):void {
			invokeMethod(parameters, monitoredEntityID, methodID, callBack, operation);	
	 	}
		
		public override function startPU(monitoredEntityID:String, username:String, password:String, callBack:IUpdateable):void {
			var url:String = _baseURL + "startpu";
			var qParams : Dictionary = new Dictionary(true);
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			//Remote host username/password as supplied by the user
			qParams["username"]=encodeURIComponent(username);
			qParams["password"]=encodeURIComponent(getBase64Encode(password));	
			//pass the UI username and password behind the scenes to verify .
			qParams["uiUserName"] = encodeURIComponent(_username);	
			qParams["uiPwd"] = encodeURIComponent(_encodedPwd);
			
			var psvrRequest:PSVRAgent = new PSVRAgent("startpu",url,qParams,callBack);
			psvrRequest.sendRequest();
		} //startPU
		
		public override function startStopThreadAnalyzer(monitoredEntityID:String, isStartTA:Boolean, threadReportDir:String, 
							samplingInterval:String, username:String, password:String, callBack:IUpdateable):void{
			var url:String = _baseURL + "startstopta";
			var qParams : Dictionary = new Dictionary(true);
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			qParams["isStartTA"] = isStartTA;
			qParams["threadReportDir"] = encodeURIComponent(threadReportDir);	//for now it is not used. We are always passing ""
			qParams["samplingInterval"] = encodeURIComponent(samplingInterval);
			//Remote host username/password as supplied by the user
			qParams["username"] = encodeURIComponent(username);
			qParams["password"] = encodeURIComponent(getBase64Encode(password));
			
			var psvrRequest:PSVRAgent = new PSVRAgent("startstopta",url,qParams,callBack);
			psvrRequest.sendRequest();
		} //startThreadAnalyzer
		
		public override function executeCommand(monitoredEntityID:String, username:String, password:String, command:String, callBack:IUpdateable):void {
			var url:String = _baseURL + "executecommand";
			var qParams : Dictionary = new Dictionary(true);
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			//Remote host username/password as supplied by the user
			qParams["username"]=encodeURIComponent(username);
			qParams["password"]=encodeURIComponent(getBase64Encode(password));
			qParams["command"] =encodeURIComponent(command);
			//pass the UI username and password behind the scenes to verify .
			qParams["uiUserName"] = encodeURIComponent(_username);	
			qParams["uiPwd"] = encodeURIComponent(_encodedPwd);
			
			var psvrRequest:PSVRAgent = new PSVRAgent("executecommand",url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function deployDU(monitoredEntityID:String, selDusStIdsTokenStr:String, username:String, password:String, callBack:IUpdateable):void {
			var url:String = _baseURL + UIConsts.DEPLOY_DU;
			var qParams : Dictionary = new Dictionary(true);
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			//Tokenized string with the id's of the DU's selected for deployment	
			qParams["dusStIdsTokenStr"] = encodeURIComponent(selDusStIdsTokenStr);
			//Remote host username/password as supplied by the user
			qParams["username"]=encodeURIComponent(username);
			qParams["password"]=encodeURIComponent(getBase64Encode(password));
			//pass the UI username and password behind the scenes to verify .
			qParams["uiUserName"] = encodeURIComponent(_username);	
			qParams["uiPwd"] = encodeURIComponent(_encodedPwd);
			
			var psvrRequest:PSVRAgent = new PSVRAgent(UIConsts.DEPLOY_DU,url,qParams,callBack);
			psvrRequest.sendRequest();
		} //deployPU
		
		public override function getGVs(monitoredEntityID:String, selDusStIdsTokenStr:String, callBack:IUpdateable):void {
			var url:String = _baseURL + "getglobalvariables";
			var qParams : Dictionary = new Dictionary(true);
			
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			qParams["dusStIdsTokenStr"] = encodeURIComponent(selDusStIdsTokenStr);
			
			var psvrRequest:PSVRAgent = new PSVRAgent(UIConsts.GET_GVS,url,qParams,callBack);
			psvrRequest.sendRequest();
		}
		
		public override function setGVs(monitoredEntityID:String, newGvXML:String, callBack:IUpdateable):void {
			var url:String = _baseURL + "setglobalvariables";
			var gvXmlPayload:String = buildGvXml(_token, monitoredEntityID, newGvXML);
			
			var psvrRequest:PSVRAgent = new PSVRAgent(UIConsts.SET_GVS,url,null,callBack,gvXmlPayload);
			psvrRequest.sendRequest();
		}
		
		private function buildGvXml(token:String, monitorableid:String,newgvxml:String):String {
			var gvPayloadXml:String = "<request token=\""+ token + "\" monitorableid=\"" + 
										monitorableid + "\" " + GVS_XML_NS+">";
			gvPayloadXml+= "<payload>";
			gvPayloadXml+= newgvxml;
			gvPayloadXml+= "</payload>";
			gvPayloadXml+= "</request>";
			return gvPayloadXml;
		} 
		
		//Pane Calls
		public override function getPaneConfig(monitoredEntityID:String, pane:MetricPane, callBack:IUpdateable):void{
		
		}
		
		public override function getPaneData(monitoredEntityID:String, pane:MetricPane, callBack:IUpdateable):void{
			var url : String = _baseURL + "getcomponentdata";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			qParams["type"] = pane.type;
			
			var psvrRequest:PSVRAgent = new PSVRAgent("getpanedata",url,qParams,callBack);
			psvrRequest.sendRequest();			
		}
		
		public override function subscribePane(monitoredEntityID:String, pane:MetricPane, callBack:IUpdateable):void{
			var url : String = _baseURL + "subscribe";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			qParams["type"] = pane.type;
			
			var delegateUpdateable:IUpdateable = new RemoteSubscriptionProccessor(monitoredEntityID,pane,_psvrUpdatesClient,this,callBack);
			var psvrRequest:PSVRAgent = new PSVRAgent("subscribepane",url,qParams,delegateUpdateable);
			psvrRequest.sendRequest();				
		}
		
		public override function unsubscribePane(monitoredEntityID:String,pane:MetricPane, callBack:IUpdateable):void{
			var url : String = _baseURL + "unsubscribe";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
			qParams["monitorableid"] = monitoredEntityID;
			qParams["type"] = pane.type;
			
			var delegateUpdateable:IUpdateable = new RemoteSubscriptionProccessor(monitoredEntityID,pane,_psvrUpdatesClient,this,callBack);
			var psvrRequest:PSVRAgent = new PSVRAgent("unsubscribepane",url,qParams,delegateUpdateable);
			psvrRequest.sendRequest();
		}

		public override function unsubscribeAllPanes(callBack:IUpdateable):void{
			super.unsubscribeAllPanes(null);
			var url : String = _baseURL + "unsubscribeall";
			
			var qParams : Dictionary = new Dictionary(true);
			qParams["token"] = _token;
						
			var delegateUpdateable:IUpdateable = new RemoteSubscriptionProccessor(null,null,_psvrUpdatesClient,this,callBack);
			var psvrRequest:PSVRAgent = new PSVRAgent("unsubscribeallpanes",url,qParams,delegateUpdateable);
			psvrRequest.sendRequest();
		}		
		
		
		//IUpdateable
		public override function update(operation:String,data:XML):void {
			if (operation == "login") {
				_passWordEncoder.reset();
				_token = data.content;
			}
			super.update(operation,data);
		}
		
								
	}
}