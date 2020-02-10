package test{
    import flash.display.Sprite;
    import flash.events.*;
    import flash.net.XMLSocket;
    
    import mx.controls.TextArea;

    public class XMLSocketExample extends Sprite {
        private var hostName:String = "127.0.0.1";
        private var port:uint = 4444;
        private var socket:XMLSocket;
        private var outputArea:TextArea;

        public function XMLSocketExample(outputArea:TextArea){
        	try{
	        	this.outputArea = outputArea;
	            socket = new XMLSocket();
	            configureListeners(socket);
	            socket.connect(hostName, port);
	        }
	        catch(ex:Error){
	        	outputArea.text = "Caught Error: " + ex.message;
	        }
        }

        public function send(data:Object):void {
            socket.send(data);
        }

        private function configureListeners(dispatcher:IEventDispatcher):void {
            dispatcher.addEventListener(Event.CLOSE, closeHandler);
            dispatcher.addEventListener(Event.CONNECT, connectHandler);
            dispatcher.addEventListener(DataEvent.DATA, dataHandler);
            dispatcher.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
            dispatcher.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            dispatcher.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
        }

        private function closeHandler(event:Event):void {
            outputArea.text += ("\ncloseHandler: " + event);
        }

        private function connectHandler(event:Event):void {
            outputArea.text = ("connectHandler: " + event);
            //socket.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
        }

        private function dataHandler(event:DataEvent):void {
            outputArea.text += ("\ndataHandler: " + event);
        }

        private function ioErrorHandler(event:IOErrorEvent):void {
            outputArea.text += ("\nioErrorHandler: " + event);
        }

        private function progressHandler(event:ProgressEvent):void {
            outputArea.text += ("\nprogressHandler loaded:" + event.bytesLoaded + " total: " + event.bytesTotal);
        }

        private function securityErrorHandler(event:SecurityErrorEvent):void {
            outputArea.text += ("\nsecurityErrorHandler: " + event);
        }
    }
}