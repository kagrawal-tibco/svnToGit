package com.tibco.cep.ui.monitor.io {
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.TopologyMenuController;
	import com.tibco.cep.ui.monitor.panes.MetricPane;

	import mx.core.Application;

	// Singleton class
	public class PSVRClient implements IUpdateable {
		internal static var _instance : PSVRClient;
		protected var _token : String;
		protected var _psvrUpdatesClient : IPSVRUpdatesClient;

		public function PSVRClient() : void {
			_psvrUpdatesClient = new TimerBasedPSVRUpdatesClient(this);
		}

		public function get username() : String {
			return "username";
		}

		public function get password() : String {
			return "password";
		}

		public static function get instance() : PSVRClient {
			if (_instance == null) {
				var passedInParams : Object = Application.application.parameters;
				if (passedInParams.hasOwnProperty("baseURL") == true) {
					var baseURL : String = passedInParams.baseURL;
					if (baseURL == "local") {
						_instance = new LocalPSVRClient();
					} else {
						_instance = new RemotePSVRClient(baseURL);
					}
				} else {
					_instance = new RemotePSVRClient();
				}
			}
			return _instance;
		}

		public function getApplicationConfig(callBack : IUpdateable) : void {/*virtual*/
		}

		public function login(username : String, password : String, callBack : IUpdateable) : void {/*virtual*/
		}

		public function logout(callBack : IUpdateable) : void {
			// callBack should only be non-null in decendant classes
			_psvrUpdatesClient.unsubscribeAllPanes();
			_psvrUpdatesClient.unsubscribeTopology();
			_psvrUpdatesClient.shutdown();
		}

		public function crash() : void {
			_psvrUpdatesClient.unsubscribeAllPanes();
			_psvrUpdatesClient.unsubscribeTopology();
			_psvrUpdatesClient.shutdown();
		}

		// Topology calls
		public function getTopologyXML(callBack : IUpdateable) : void {/*virtual*/
		}

		public function getSiteTopology(callBack : IUpdateable) : void {/*virtual*/
		}

		public function updateSiteTopology(callBack : IUpdateable) : void {/*virtual*/
		}

		public function subscribeTopology(controller : TopologyMenuController, callBack : IUpdateable) : void {
			_psvrUpdatesClient.subscribeTopology(controller);

			update("subscribetopology", null);

			if (callBack != null)
				callBack.update("subscribetopology", null);
		}

		public function unsubscribeTopology(callBack : IUpdateable) : void {
			_psvrUpdatesClient.unsubscribeTopology();
			update("unsubscribetopology", null);
			if (callBack != null) callBack.update("unsubscribetopology", null);
		}

		public function purgeTopology(callBack : IUpdateable) : void { /*virtual*/
		}

		// Panel cals
		public function getPanelComponents(monitoredEntityID : String, callBack : IUpdateable) : void { /*virtual*/
		}

		public function getMethodPanel(monitoredEntityID : String, methodID : String, callBack : IUpdateable) : void { /*virtual*/
		}

		// Process and Agent Methods layout
		public function getAgentMethodsLayout(callBack : IUpdateable) : void {/*virtual*/
		}

		public function getProcessMethodsLayout(callBack : IUpdateable) : void {/*virtual*/
		}

		// Method invocation
		public function invokeMethod(parameters : String, monitoredEntityID : String, methodID : String, callBack : IUpdateable, operation : String = "invokeagentmethod") : void { /*virtual*/
		}

		public function invokeResumeBtn(parameters : String, monitoredEntityID : String, methodID : String, callBack : IUpdateable, operation : String = "invokeresumebtn") : void { /*virtual*/
		}

		public function invokePauseBtn(parameters : String, monitoredEntityID : String, methodID : String, callBack : IUpdateable, operation : String = "invokepausebtn") : void { /*virtual*/
		}

		public function startPU(monitoredEntityID : String, username : String, password : String, callBack : IUpdateable) : void {/*virtual*/
		}

		public function startStopThreadAnalyzer(monitoredEntityID : String, isStartTA : Boolean, threadReportDir : String, samplingInterval : String, username : String, password : String, callBack : IUpdateable) : void {/*virtual*/
		}

		public function executeCommand(monitoredEntityID : String, username : String, password : String, command : String, callBack : IUpdateable) : void {/*virtual*/
		}

		// Deployment
		public function deployDU(monitoredEntityID : String, selDusStIdsTokenStr : String, username : String, password : String, callBack : IUpdateable) : void {/*virtual*/
		}

		// Edit Global Variables
		public function getGVs(monitoredEntityID : String, selDusStIdsTokenStr : String, callBack : IUpdateable) : void {/*virtual*/
		}

		public function setGVs(monitoredEntityID : String, newGVValue : String, callBack : IUpdateable) : void {/*virtual*/
		}

		// BE system component calls
		public function suspend(monitoredItem : XML) : void {
		}

		public function resume(monitoredItem : XML) : void {
		}

		public function deploy(monitoredItem : XML) : void {
		}

		public function kill(monitoredItem : XML) : void {
		}

		// Pane calls
		public function getPaneConfig(monitoredEntityID : String, pane : MetricPane, callBack : IUpdateable) : void { /*virtual*/
		}

		public function getPaneData(monitoredEntityID : String, pane : MetricPane, callBack : IUpdateable) : void { /*virtual*/
		}

		public function subscribePane(monitoredEntityID : String, pane : MetricPane, callBack : IUpdateable) : void {
			_psvrUpdatesClient.subscribePane(monitoredEntityID, pane);

			update("subscribepane", null);

			if (callBack != null)
				callBack.update("subscribepane", null);
		}

		public function unsubscribePane(monitoredEntityID : String, pane : MetricPane, callBack : IUpdateable) : void {
			_psvrUpdatesClient.unsubscribePane(monitoredEntityID, pane);
			update("unsubscribepane", null);
			if (callBack != null) callBack.update("unsubscribepane", null);
		}

		public function unsubscribeAllPanes(callBack : IUpdateable) : void {
			_psvrUpdatesClient.unsubscribeAllPanes();

			update("unsubscribeallpanes", null);

			if (callBack != null)
				callBack.update("unsubscribeallpanes", null);
		}

		// IUpdateable
		public function update(operation : String, data : XML) : void {
			if (operation == "subscribepane" || operation == "subscribetopology") {
				_psvrUpdatesClient.startUpdates();
			} else if (operation == "unsubscribeallpanes") {
				// _psvrUpdatesClient.stopUpdates(); // updates should continue for topology
			} else if (operation == "unsubscribetopology") {
			}
		}

		public function updateFailure(operation : String, message : String, code : uint) : void {
			// do nothing
		}
	}
}