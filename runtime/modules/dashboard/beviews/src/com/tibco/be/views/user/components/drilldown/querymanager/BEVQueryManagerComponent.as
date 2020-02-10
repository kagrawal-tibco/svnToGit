package com.tibco.be.views.user.components.drilldown.querymanager{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.tasks.ServerCommandProcessor;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.components.drilldown.events.BEVDrillDownRequest;
	import com.tibco.be.views.user.components.drilldown.querymanager.view.QueryManagerMainPanel;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.TableTreeUpdateEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.*;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.utils.Dictionary;
	
	import mx.core.Application;
	import com.tibco.be.views.user.components.drilldown.tabletree.TableTreeCommands;

	public class BEVQueryManagerComponent extends BEVComponent{
		//view elements
		private var _qmContainer:QueryManagerMainPanel;
		private var downloadURL:URLRequest;
		private var fileRef:FileReference = new FileReference();

		public function BEVQueryManagerComponent(){
			super();
			_autonomousLoading = false;
		}
		
		public function fireEvent(typeId:String):void{
			var getDDDataReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_QUERY_MANAGER_FIELDS, this);
			getDDDataReq.addXMLParameter("typeid",typeId);
			EventBus.instance.dispatchEvent(getDDDataReq);
		}
		
		public function fireEventExecuteQuery(qmModelXML:XML):void{
			var getDDDataReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.QUERY_MANAGER_EXECUTE_QUERY, this);
			getDDDataReq.addXMLParameter("querymgrmodel",qmModelXML);
			EventBus.instance.dispatchEvent(getDDDataReq);
		}
		
		public function fireEventGetQMExportOptionsDefaults():void{
			var getDDDataReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.QUERY_MANAGER_GET_EXPORT_OPTIONS, this);
			EventBus.instance.dispatchEvent(getDDDataReq);
		}
		
		public function fireEventExportQueryData(type:String, depth:String, pertypecount:String, overallcount:String, includesystemfields:String):void{
			//create the raw url 
			var url:String = "http://"+Configuration.instance.serverName+":"+Configuration.instance.serverPort+Configuration.instance.commandContextPath;
//			trace("Export URL from Configuration Is "+url);
			//check if we are running is browser or not 
			if (Configuration.instance.serverName == "" && Configuration.instance.serverPort == "") {
				//we are in the browser , check if the command context path is relative or absolute  
				if (Configuration.instance.commandContextPath.charAt(0) != '/') {
					//the command context path is relative , so we need to bump up one level since the swf resides in swfs folder 
					url = "../"+Configuration.instance.commandContextPath;
				}
			}
//			trace("Export URL should be "+url);
			downloadURL = new URLRequest(url);
			downloadURL.method = URLRequestMethod.POST;
			var params:Dictionary = new Dictionary();
			params["command"] = BEVDrillDownRequest.QUERY_MANAGER_EXPORT_QUERY_DATA;
			params["type"] = type;
			params["depth"] = depth;
			params["pertypecount"] = pertypecount;
			params["overallcount"] = overallcount;
			params["includesystemfields"] = includesystemfields;
			var qmModelXML:XML = _qmContainer._controller.getQueryMgrModel();
			params["querymgrmodel"] = qmModelXML;
			if(Session.instance.token != null && Session.instance.token != ""){
				params["stoken"] = Session.instance.token;
			}
			downloadURL.contentType = "text/xml; charset=UTF-8";
			//Set the request payload (POST data) and send the request
			downloadURL.data = ServerCommandProcessor.getRequestXMLString(params);			
			configureListeners(fileRef);
            fileRef.download(downloadURL, type == "csv"?"export.csv":"export.xml");
			
			//EventBus.instance.dispatchEvent(getDDDataReq);
			// do not use EventBus, instead fire the URL Request and handle it here itself
		}
		
         private function configureListeners(dispatcher:IEventDispatcher):void{
            dispatcher.addEventListener(Event.CANCEL, cancelHandler);
            dispatcher.addEventListener(Event.COMPLETE, completeHandler);
            dispatcher.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            dispatcher.addEventListener(Event.OPEN, openHandler);
            dispatcher.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            dispatcher.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
            dispatcher.addEventListener(Event.SELECT, selectHandler);
        }

        private function cancelHandler(event:Event):void{
            trace("cancelHandler: " + event);
        }

        private function completeHandler(event:Event):void{
            trace("completeHandler: " + event);
        }

        private function ioErrorHandler(event:IOErrorEvent):void{
            trace("ioErrorHandler: " + event);
        }

        private function openHandler(event:Event):void{
            trace("openHandler: " + event);
        }

        private function progressHandler(event:ProgressEvent):void{
            var file:FileReference = FileReference(event.target);
            trace("progressHandler name=" + file.name + " bytesLoaded=" + event.bytesLoaded + " bytesTotal=" + event.bytesTotal);
        }

        private function securityErrorHandler(event:SecurityErrorEvent):void{
            trace("securityErrorHandler: " + event);
        }

        private function selectHandler(event:Event):void{
            var file:FileReference = FileReference(event.target);
            trace("selectHandler: name=" + file.name + " URL=" + downloadURL.url);
        }
        	
		override public function init():void{
			super.init();
			// TODO add later...		
			Logger.log(DefaultLogEvent.INFO, "Getting Query manager component Configuration For "+componentId+" with mode as "+componentMode);
			var getDDDataReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_QUERY_MANAGER_MODEL, this);
			EventBus.instance.dispatchEvent(getDDDataReq);
		}
		
		override protected function createChildren():void{
			_qmContainer = new QueryManagerMainPanel();
			_qmContainer.bevQMComponent = this;
			addChild(_qmContainer);
			super.createChildren();
		}
		
		override public function handleResponse(response:ConfigResponseEvent):void{
			if(response.failMessage != ""){
			 	Logger.log(DefaultLogEvent.CRITICAL, "Error loading the component data for " + this + " due to ["+response.failMessage+"]");
			 	var msg:String = "Failed to load data due to ["+response.failMessage+"]...";
			 	if(response.command == BEVDrillDownRequest.QUERY_MANAGER_EXECUTE_QUERY){
			 		msg = response.failMessage;
			 	} 
			 	MessageBox.show(DisplayObjectContainer(Application.application), "Query Manager", msg);
			 	return;
			}
			else{
				switch(response.command){
					case(BEVDrillDownRequest.GET_QUERY_MANAGER_MODEL):
						_state = DATA_LOADED;
						componentData = response.dataAsXML;
						break;				
					case(BEVDrillDownRequest.GET_QUERY_MANAGER_FIELDS):
						var responseData:XML = response.dataAsXML;
						_qmContainer.updateFieldsEditorPane(responseData);
						break;
					case(BEVDrillDownRequest.QUERY_MANAGER_EXECUTE_QUERY):
						//TODO: Error handling / invalid query handling
						//broadcast successful query so other components can update accordingly
						EventBus.instance.dispatchEvent(new EventBusEvent(EventTypes.SUCCESSFUL_QUERY_RESPONSE));
						break;
					case(BEVDrillDownRequest.QUERY_MANAGER_GET_EXPORT_OPTIONS):
						responseData = response.dataAsXML;
						trace(responseData);
						showQueryExportDialog(responseData);
						break;
					case(BEVDrillDownRequest.QUERY_MANAGER_EXPORT_QUERY_DATA):
						// We will never get a response here since we don't use EventBus for export...
						break;
				}
			}
		}
		
		private function handleTableTreeUpdate(tableTreeEvent:TableTreeUpdateEvent):void{
			_qmContainer.export.enabled = tableTreeEvent.isTableExportEnabled;
		}
		
		// parse the XML response of query manager get export options and show popup dialog filled with default
		// values recieved from the backend
		private function showQueryExportDialog(qmExportDefaultOptions:XML):void{
			var type:String;
			var depth:String;
			var pertypecount:String;
			var overallcount:String;
			var includesystemfields:String;
			
			for each(var option:XML in qmExportDefaultOptions.option){
				switch(String(option.@name)){
					case("type"):
						type = new String(option);
						break;
					case("depth"):
						depth = new String(option);
						break;
					case("pertypecount"):
						pertypecount = new String(option);
						break;
					case("overallcount"):
						overallcount = new String(option);
						break;
					case("includesystemfields"):
						includesystemfields = new String(option);
						break;
				}
			}
			_qmContainer.showQueryExportDialog(type,depth,pertypecount,overallcount,includesystemfields);
		}
				
		//Called when property componentData is set in the parent BEVComponent class
		override protected function handleDataSet(dataXML:XML):void{
			_qmContainer.init(dataXML);
		}
		
		override public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
			EventBus.instance.addEventListener(TableTreeUpdateEvent.TABLE_UPDATED, handleTableTreeUpdate);
		}
	}
}