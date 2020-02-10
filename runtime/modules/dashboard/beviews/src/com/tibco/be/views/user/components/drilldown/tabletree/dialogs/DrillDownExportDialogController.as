package com.tibco.be.views.user.components.drilldown.tabletree.dialogs {
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.tasks.ServerCommandProcessor;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.user.components.drilldown.events.BEVDrillDownRequest;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	import flash.events.IEventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.utils.Dictionary;
	
	import mx.events.FlexEvent;
	
	
	public class DrillDownExportDialogController implements IEventBusListener {
		
		private var _parent:DisplayObjectContainer;
		
		private var _defaultOptions:XML;
		
		private var _dialog:DrillDownExportDialog;
		
		private var _downloadFileRef:FileReference = new FileReference();
		
		public function DrillDownExportDialogController(parent:DisplayObjectContainer) {
			_parent = parent;
		}
		
		public function init():void {
			registerListeners();
			//fire to get defaults
			var ddExportDlgOptsReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.DRILLDOWN_GET_EXPORT_OPTIONS, this);
			EventBus.instance.dispatchEvent(ddExportDlgOptsReq);
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		
		public function unregisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this; 
		}

		private function handleResponse(respObj:ConfigResponseEvent):void{
			if(!isRecipient(respObj)){ return; }
			if(respObj.command == BEVDrillDownRequest.DRILLDOWN_GET_EXPORT_OPTIONS){
				if(respObj.failMessage != ""){
				 	Logger.log(DefaultLogEvent.CRITICAL, "Error loading the drilldown export dialog default options for " + this + " due to ["+respObj.failMessage+"]");
				 	MessageBox.show(_parent, "Drilldown Export", "Failed to load default options",null, respObj.failMessage);
				}
				else{
					handleDefaultOptions(respObj.dataAsXML);
				}					
			}
		}
		
		private function handleDefaultOptions(data:XML):void {
			_defaultOptions = new XML(data);
			_dialog = new DrillDownExportDialog();
			_dialog.addEventListener(FlexEvent.CREATION_COMPLETE, handleDialogCreationComplete);
			Kernel.instance.uimediator.uicontroller.openWindow(_dialog, _parent, true);
			Kernel.instance.uimediator.uicontroller.centerWindow(_dialog);			
		}

		private function handleDialogCreationComplete(event:FlexEvent):void {
			for each(var option:XML in _defaultOptions.option){
				switch(String(option.@name)) {
					case"type":
						_dialog.exportType = new String(option);
						break;

					case "includesystemfields":
						_dialog.includeSystemFields = ("true" == new String(option).toLowerCase());
						break;
				}
			}
			_dialog.okButton.addEventListener(MouseEvent.CLICK, handleOkButtonClick);
			_dialog.cancelButton.addEventListener(MouseEvent.CLICK, handleCancelButtonClick);
		}
		
		private function handleOkButtonClick(event:MouseEvent):void {
			//create the base URL
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
			//create the URL request object 
			var downloadURL:URLRequest = new URLRequest(url);
			//set the method as POST
			downloadURL.method = URLRequestMethod.POST;
			//create the parameters to be submitted
			var params:Dictionary = new Dictionary();
			params["command"] = BEVDrillDownRequest.DRILLDOWN_EXPORT_QUERY_DATA;
			params["type"] = _dialog.exportType;
			params["includesystemfields"] = _dialog.includeSystemFields;
			params["stoken"] = Session.instance.token;
			params["sessionid"] = Session.instance.sessionId;
			//set the content type 
			downloadURL.contentType = "text/xml; charset=UTF-8";
			//Set the request payload (POST data) and send the request
			downloadURL.data = ServerCommandProcessor.getRequestXMLString(params);			
			_downloadFileRef.download(downloadURL, _dialog.exportType == "csv"?"export.csv":"export.xml");			
			close();
		}
		
		private function registerDownloadListeners():void {
            _downloadFileRef.addEventListener(Event.CANCEL, cancelHandler);
            _downloadFileRef.addEventListener(Event.COMPLETE, completeHandler);
            _downloadFileRef.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            _downloadFileRef.addEventListener(Event.OPEN, openHandler);
            _downloadFileRef.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            _downloadFileRef.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
            _downloadFileRef.addEventListener(Event.SELECT, selectHandler);			
		}
		
		private function unregisterDownloadListeners():void {
			_downloadFileRef.removeEventListener(Event.CANCEL, cancelHandler);
            _downloadFileRef.removeEventListener(Event.COMPLETE, completeHandler);
            _downloadFileRef.removeEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            _downloadFileRef.removeEventListener(Event.OPEN, openHandler);
            _downloadFileRef.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
            _downloadFileRef.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
            _downloadFileRef.removeEventListener(Event.SELECT, selectHandler);
		}
		
		private function handleCancelButtonClick(event:MouseEvent):void {
			close();
		}
		
		private function close():void {
			_dialog.okButton.removeEventListener(MouseEvent.CLICK, handleOkButtonClick);
			_dialog.cancelButton.removeEventListener(MouseEvent.CLICK, handleCancelButtonClick);
			unregisterListeners();
			Kernel.instance.uimediator.uicontroller.closeWindow(_dialog);
			_dialog = null;			
		}
		
         private function configureListeners(dispatcher:IEventDispatcher):void{
        }

        private function cancelHandler(event:Event):void{
            unregisterDownloadListeners();
        }

        private function completeHandler(event:Event):void{
            unregisterDownloadListeners()
        }

        private function ioErrorHandler(event:IOErrorEvent):void{
			Logger.log(DefaultLogEvent.CRITICAL, "An error occured while communicating with the server["+event.text+"]");        	
			MessageBox.show(_parent,"Export Error","An error occured while communicating with the server",null,event.text);            
        }

        private function openHandler(event:Event):void{
        }

        private function progressHandler(event:ProgressEvent):void{
        }

        private function securityErrorHandler(event:SecurityErrorEvent):void{
        	Logger.log(DefaultLogEvent.CRITICAL, "An security error occured while communicating with the server["+event.text+"]");
            MessageBox.show(_parent,"Export Error","An security error occured while communicating with the server",null,event.text);
        }

        private function selectHandler(event:Event):void{
        }		
	}
}