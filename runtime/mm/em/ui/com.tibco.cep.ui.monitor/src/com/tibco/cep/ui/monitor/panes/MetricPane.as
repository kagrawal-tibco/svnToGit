package com.tibco.cep.ui.monitor.panes{
	
	import com.tibco.cep.ui.monitor.AppConfig;
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.PromotedPaneRepository;
	import com.tibco.cep.ui.monitor.containers.InvalidPaneOverlay;
	import com.tibco.cep.ui.monitor.events.EventTypes;
	import com.tibco.cep.ui.monitor.events.MetricPaneUpdateFailure;
	import com.tibco.cep.ui.monitor.io.DelegateUpdateableImpl;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.pages.PanelPage;
	import com.tibco.cep.ui.monitor.panels.PanelLoader;
	import com.tibco.cep.ui.monitor.panes.chart.ChartPane;
	import com.tibco.cep.ui.monitor.panes.events.MetricPaneRemoveEvent;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.SortFunction;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.display.DisplayObject;
	import flash.display.Graphics;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.filters.DropShadowFilter;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import mx.charts.Legend;
	import mx.charts.chartClasses.CartesianChart;
	import mx.containers.Canvas;
	import mx.core.Application;
	import mx.core.DragSource;
	import mx.core.UIComponent;
	import mx.events.AdvancedDataGridEvent;
	import mx.events.DataGridEvent;
	import mx.events.DragEvent;
	import mx.events.FlexMouseEvent;
	import mx.managers.DragManager;
	import mx.managers.PopUpManager;

	public class MetricPane extends Canvas implements IUpdateable {
				
		public static const CONTAINER_WIDTH:int = 310;
		public static const CONTAINER_HEIGHT:int = 240;
		
		private const RESIZE_RIGHT:String = "resizeRight";
		
		//Display Modes
		public static const NORMAL_DISPLAY:String = "normalDisplay";
		public static const EXPANDED_DISPLAY:String = "expandedDisplay";
		public static const PROMOTED_DISPLAY:String = "promotedDisplay";
		public static const STATIC_DISPLAY:String = "staticDisplay";
		
		protected var RANDOMIZE_UPDATES:Boolean = AppConfig.instance.randomizeUpdates;
		
		private var _bg:Canvas;
		private var _header:MetricPaneHeader;
		private var _display:UIComponent;
		private var _legend:Legend;
		
		private var _dragClue:UIComponent;
		private var _dataCopy:XML;
		
		protected var _type:String;
		protected var _monitoredNode:XML;
		protected var _subscribed:Boolean;
		protected var _isDead:Boolean;
		protected var _ancestryPath:String;
		protected var _invalidComponentOverlay:InvalidPaneOverlay;
		protected var _inactivePane:InvalidPaneOverlay;
		protected var _notInCulsterOverlay:InvalidPaneOverlay;
		
		private var _padding:int = 8;
		private var _dragBegin:Boolean = false;
		private var _dispMode:String = NORMAL_DISPLAY;
		
		private var _expandedProxyPane:MetricPane;
		protected var _expandedPaneParent:MetricPane;
		
		private var resizeDir:String;
		private var resizeAnchorPoint:Point;
		
		public function MetricPane(title:String=""){
			super();
			_subscribed = false;			
			
			//properties for this canvas
			this.width = CONTAINER_WIDTH;
			this.height = CONTAINER_HEIGHT;
			this.styleName = "metricPaneStyle";
			this._isDead = false;
			
			_header = new MetricPaneHeader();
			_header.init(this, title);
			addChild(_header);
			
			//TODO
			//Comment this temporarily to disable panes resizing. When enabling this again
			//look at BE Views class OverlayBEVChartComponentHolder and ShowComponentOverlayAction
			 
//			this.addEventListener(MouseEvent.MOUSE_DOWN, handleBorderMouseDown);
//			this.addEventListener(MouseEvent.MOUSE_MOVE, handleMetricPaneMouseMove);
			
			//drag within
			this.addEventListener(DragEvent.DRAG_ENTER, handleCtrlDragEnter);
			this.addEventListener(DragEvent.DRAG_DROP, handleCtrlDragDrop);
			this.addEventListener(DragEvent.DRAG_OVER, handleDragOver);
			this.addEventListener(DragEvent.DRAG_EXIT, handleDragExit);
			
			_bg = new Canvas();
			this.addChild(_bg);			
			setBGFormat();
			
			//drag clue which is useful during drag-drop within the comp
			_dragClue = new UIComponent();
			this.addChild(_dragClue);
			_dragClue.visible = false;		
			
			paintDragClue();		
			
			//add shadow
			var shadow:DropShadowFilter = new DropShadowFilter();
			shadow.color = 0x666666;
			shadow.alpha = 0.6;
			shadow.distance = 3;
			this.filters = [shadow];
		}
		
		public function get ancestryPath():String{
			if(_ancestryPath == "undefined" || _ancestryPath == null)
				_ancestryPath = Util.getAncestoryNamePath(_monitoredNode);
			return _ancestryPath;
		}
		public function get isDead():Boolean{ return _isDead; }
		public function get isValid():Boolean{
			return (
				_invalidComponentOverlay == null ||
				!_invalidComponentOverlay.visible
			);
		}
		public function get metricLabel():String{ return _header.headerTitle; }
		public function get monitoredNode():XML{ return _monitoredNode }
		public function get paneId():String{
			if(_monitoredNode == null){
				throw new Error("MetricPane.pandId - Null monitoredNode");
				return;
			}
			var id:String = String(_monitoredNode.@id);
			return id + "-" + _type;
		}
		public function get subscribed():Boolean{ return _subscribed; }
		public function get type():String{ return _type; }
		
		public function set ancestryPath(value:String):void{ _ancestryPath = value; }	
		public function set displayMode(val:String):void{
			_dispMode = val;
			_header.displayMode = val;
			
			//adjust the metric pane...
			if(_dispMode == EXPANDED_DISPLAY){
				this.width *= 2;
				this.height *= 2;
			}
			else if(_dispMode == PROMOTED_DISPLAY){ }
			else if(_dispMode == STATIC_DISPLAY){ }
			
			setDisplayDimensions();
			
			//remove drag within
			this.removeEventListener(DragEvent.DRAG_ENTER, handleCtrlDragEnter);
			this.removeEventListener(DragEvent.DRAG_DROP, handleCtrlDragDrop);		
			this.removeEventListener(DragEvent.DRAG_OVER, handleDragOver);
			this.removeEventListener(DragEvent.DRAG_EXIT, handleDragExit);
			
			
			
		}
		public function set monitoredNode(value:XML):void{ _monitoredNode = value; }
		public function set subscribed(value:Boolean):void{ _subscribed = value; }
		
		public function init(panelLoader:PanelLoader):void {
			if(_monitoredNode == null || _monitoredNode.@active == ""){
				panelLoader.updateFailure("paneInit","Invalid Monitored Node",0)
				return;
			}
			
			
			Logger.logInfo(this, "Initializing pane [" + _type + "] on node [" + 
									Util.getAncestoryNamePath(_monitoredNode) + "]");
									
			PSVRClient.instance.getPaneData(String(_monitoredNode.@id), this, 
										    new DelegateUpdateableImpl(panelLoader, this) );
		}
		
		//this setter is necessary to handle the refresh in GlobalVarsTable
		protected function set dataCopy (dataCopy:XML):void {
			_dataCopy = dataCopy;
		}
		
		public function restore():void{
			PSVRClient.instance.getPaneData(
				String(_monitoredNode.@id), 
				this, 
				this
			);
		}
		
		public final function kill():void{
			_isDead = true;
			_display = null;
			_monitoredNode = null;
		}
		
		protected function setButtons(expand:Boolean=true, promote:Boolean=true, remove:Boolean=true):void{
			_header.btn_ExpandPane.enabled = expand;
			_header.btn_PromotePane.enabled = promote;
			_header.btn_RemovePane.enabled = remove;
		}

		/* ************************************* SET PANE OVERLAYS ************************************** */

		/**
		 * Called when a pane's parent node (process, machine, etc.) becomes inactive (active="false")
		 */
		public function setInactivePaneOverlay():void{
			//Only add the inactive pane overlay if it and the invalid component overlay and the not
			//in cluster overlay are not already being displayed
			clearOverlays();
			if(_inactivePane == null){
				_inactivePane = buildDefualtOverlay();
				_inactivePane.message = "\nEntity Inactive";
				addChild(_inactivePane);
			}
			_inactivePane.visible = true;
			_display.enabled = false;
			setButtons(false, false, false); //disable all buttons
		}
		
		public function setRemovedFromClusterOverlay():void{
		    //Only add the overlay if it and the inactive overlay are not already being displayed
			clearOverlays();
			if(_notInCulsterOverlay == null){
				_notInCulsterOverlay = buildDefualtOverlay();
				_notInCulsterOverlay.message = "Entity no longer discovered in the Cluster:";
				addChild(_notInCulsterOverlay);
			}
			_notInCulsterOverlay.visible = true;
			_display.enabled = false;
			setButtons(false, false, false); //disable all buttons
		}
		
		/**
		 * Called for a pane in the cluster overview when the pane's parent node becomes inactive or
		 * is no longer a member of the topology.
		*/
		public function setInvalidClusterComponentOverlay():void{
			//Only add the invalid overlay if it and the inactive overlay are not already being displayed
			clearOverlays();
			if(_invalidComponentOverlay == null){
				_invalidComponentOverlay = buildDefualtOverlay();
				_invalidComponentOverlay.message = "Invalid Cluster Component:";
				_invalidComponentOverlay.invalidComonentName = _ancestryPath;
				addChild(_invalidComponentOverlay);
			}
			_invalidComponentOverlay.visible = true;
			_display.enabled = false;
			setButtons(false, false, true); //activate just remove button, to remove from pane overlay
		}
		
		protected function buildDefualtOverlay():InvalidPaneOverlay{
			var overlay:InvalidPaneOverlay = new InvalidPaneOverlay();
			overlay.x = _display.x;
			overlay.y = _header.height + _padding + 2
			overlay.width = _display.width;
			overlay.height = this.height - _header.height - _padding * 2 - 4;
			return overlay;
		}
		
        public function clearOverlays():void{
        	if(_inactivePane != null){ _inactivePane.visible = false; }
        	if(_notInCulsterOverlay != null){ _notInCulsterOverlay.visible = false; }
        	if(_invalidComponentOverlay != null){ _invalidComponentOverlay.visible = false; }
        	_display.enabled = true;
			setButtons();	//enable all buttons
		}		
		
		/* ************************************* SET PANE LAYOUT / DIMENSIONS ************************************** */
		
		/**
		 * @private
		 * Sets the background styles
		 */		
		private function setBGFormat():void{
			_bg.styleName = "metricBGStyle";			
			_bg.width = this.width - _padding * 2;
			_bg.height = this.height - _header.height - _padding * 2;
			_bg.x = _padding;
			_bg.y = _header.height + _padding - 1;
		}
		
		/**
		 * Paints the drag clue which will indicate user where would the dragged item go after dropping
		 *@private 
		 */		
		private function paintDragClue():void{
			var g:Graphics = _dragClue.graphics;
			g.clear();
			g.beginFill(0xFFCC00, 1);
			g.drawRect(0, 1, 4, height - 2);
			g.endFill();
			_dragClue.x = 0;
		}		
		
		public function addDisplay(display:UIComponent):void{
			display.setStyle("text-align", "right");
			if(display is CartesianChart){
				var chart:CartesianChart = display as CartesianChart;
				chart.showDataTips = true;
				chart.dataTipFunction = ChartPane.formatDataTip;
			}
			_display = display;
			setDisplayDimensions();
			addChild(_display);
		}		
		
		public function addChartLegend(legend:Legend):void{
			_legend = legend;
			_legend.x = _display.x;
			_legend.y = _display.y;
			addChild(_legend);
			setDisplayDimensions();
		}
		
		/**
		 * @private 
		 * Based on the current width and height, the function sets the display component's dimensions
		 */		
		public function setDisplayDimensions():void{
			if(_display == null){
				Logger.log(this,"MetricPane.setDisplayDimensions - null display");
				return;
			}
			if(_legend != null){
				_display.x = _padding + 2;
				_display.y = _header.height + _padding + 20;
				_display.width = this.width - _padding * 2 - 4;
				_display.height = this.height - _header.height - _padding * 2 - 4 - 20;
			}
			else{
				_display.x = _padding + 2;
				_display.y = _header.height + _padding + 2;
				_display.width = this.width - _padding * 2 - 4;
				_display.height = this.height - _header.height - _padding * 2 - 4;				
			}			
			
			setBGFormat();
		}//end of setDisplayDimensions
		
		public function resize(size:int):void{
			if(size == 1){
				width = CONTAINER_WIDTH;
				//_display.percentWidth = DISPLAY_WIDTH;
				setDisplayDimensions()
			}
			else{
				width = (CONTAINER_WIDTH*size) + 8*(size-1); //8 is size of gap between panes
			}
			
			setBGFormat();
		}

		/* ************************************* VIRTUAL FUNCTIONS ************************************** */
				
		protected function fillDisplay(data:XML):void {/*virtual*/}
		
		protected function updateDisplay(data:XML):void {/*virtual*/}
		
		/* ************************************* UPDATE CALLBACKS ************************************** */
		
		public function update(operation:String,data:XML):void {
			//trace("Monitored ID = "+_monitoredNode.@id+",type="+_type+",data="+data.toXMLString());
			
			//If pane was discarded before this asyncronous callBack, update should do nothing
			if(_isDead) return;
			
			if(operation == "getpanedata") { //gets here after getPaneData is called in this.init()
				_dataCopy = data;
				fillDisplay(data);
				if(_subscribed == false && _monitoredNode.@active == "true"){
					PSVRClient.instance.subscribePane(String(_monitoredNode.@id),this,this);
				}
			} else if (	operation == "getgvs") {
				Logger.logWarning(this, "Trying to periodically update the global variables table. IT SHOULD NOT HAPPEN!");
			}
			else if(operation == "subscribepane"){
				_subscribed = true;
			}
			else if(operation == "unsubscribepane"){
				_subscribed = false;
			}
			else if(operation == "updatepanedata"){ //gets here on each periodic update. FireQueries in class
				_dataCopy = data					//TimerBasedPSVRUpdate calls this callback indirectly
				if (_expandedProxyPane != null){
					_expandedProxyPane.updateDisplay(data);
				}
				updateDisplay(data);
			} //else
		}
		
		public function updateFailure(operation:String,message:String,updateBatchId:uint):void {
//			Logger.logWarning(this,"update of MetricPane[title="+_title+",type="+_type+"] failed due to "+message);
			
			//Still want to subscribe the pane incase it comes back online
			if ( (operation == "getpanedata" || operation == "getgvs") && _subscribed == false) {
				PSVRClient.instance.subscribePane(String(_monitoredNode.@id),this,this);
				if (operation == "getgvs")
					Logger.logWarning(this, "Handling update failure for a variables table. IT SHOULD NOT HAPPEN!");
			}
			
			//Issue a metric update fail event to be handled by application controller classes
			dispatchEvent(
				new MetricPaneUpdateFailure(
					"Pane Type: " + _type + "\nOperation: " + operation + "\n\n" + message,
					updateBatchId
				)
			);
		}
		
		/* ************************************* EVENT HANDLERS ************************************** */
		
		/** Event handler that is called each time the user clicks to sort a DataGrid column.
		 *  Sets the 'SortFunction.columnDataField' property to the 'dataField' of the column being sorted.*/
		protected function handleColumnSort(event:Event):void {
			//Sets the column name that is to be sorted by the SortFunction.sortNumeric class 
			if (event is DataGridEvent)
				SortFunction.columnDataField = (event as DataGridEvent).dataField;
			else if (event is AdvancedDataGridEvent)
				SortFunction.columnDataField = (event as AdvancedDataGridEvent).dataField;
		}
		
		public function handlePageResize(width:Number, height:Number):void{ /* virtual */ }
		
		public function handleMouseDown(eventObj:MouseEvent):void{
			if(_dispMode == NORMAL_DISPLAY){
				_dragBegin = true;
			}else if(_dispMode == EXPANDED_DISPLAY){
				var curParent:DisplayObject = DisplayObject(Application.application);
				var bounds:Rectangle = new Rectangle(0, 0, curParent.width - this.width, curParent.height - this.height);
				
				this.startDrag(false, bounds);
			}			
		}
		
		public function handleItemDragOut(eventObj:MouseEvent):void{
			if(_dispMode == NORMAL_DISPLAY){
				if(eventObj.buttonDown && _dragBegin){
				 	var initiator:MetricPane = this;
				 	var proxy:MetricPane =  MetricPaneFactory.instance.buildMetricPane(_type);
				 	var dragSource:DragSource = new DragSource();
				 	proxy.fillDisplay(_dataCopy);
					dragSource.addData(new Object(), 'metricPane');
			 		DragManager.doDrag(initiator, dragSource, eventObj, proxy);
			 		_dragBegin = false;				
				}	
			}
		}
		
		public function handleMouseUp(eventObj:MouseEvent):void{
			this.stopDrag();
		}
		
		public function handleMouseDownOutside(eventObj:FlexMouseEvent):void{
			this.stopDrag();
		}
		
		public function handleMetricPaneMouseMove(eventObj:MouseEvent):void{
			if(!eventObj.buttonDown){
				this.stopDrag();
			}
			else if(eventObj.buttonDown){
				if(resizeDir == RESIZE_RIGHT){
//					this.width = eventObj.stageX;
					this.width = eventObj.localX;
				}
			}
		}
		
		public function handleBorderMouseDown(eventObj:MouseEvent):void{
			trace([eventObj.localX, eventObj.localY]);
			if(eventObj.localX > width - 4 && eventObj.localX < width){
				resizeAnchorPoint = new Point();
				resizeAnchorPoint.x = eventObj.localX;
				resizeAnchorPoint.y = eventObj.localY;
				resizeDir = RESIZE_RIGHT;
			}
		}
		
		/**
		 * @private
		 * Gets triggered when the user drags the library item onto the editor area
		 * @param evtObj
		 */		
		public function handleCtrlDragEnter(eventObj:DragEvent):void{
		 	if(eventObj.dragSource.hasFormat("metricPane")){
		 		DragManager.acceptDragDrop(MetricPane(eventObj.currentTarget));
		 	}
		}

	
		/**
		 * When user leaves the mouse button on the editor track
		 * @param eventObj
		 */
		public function handleCtrlDragDrop(eventObj:DragEvent):void{
			_dragClue.visible = false;
			
			if(eventObj.dragSource.hasFormat("metricPane")){
				trace(this.parent);
				//at this point we need to inform the parent that user has dropped something at this location
				
				var pRef:PanelPage = PanelPage(this.parent.parent);
				pRef.reArrangeItems(this, MetricPane(eventObj.dragInitiator));
			}
		}	
		
		/**
		 * Gets triggered while the user moves the cursor after drag enter
		 * @param eventObj
		 *private 
		 */		
		public function handleDragOver(eventObj:DragEvent):void{
			if(eventObj.dragSource.hasFormat("metricPane")){
				_dragClue.visible = true;
			}
		}
		
		/**
		 * Gets triggered when user drags out of the comp after drag enter
		 * @param eventObj
		 *@private 
		 */		
		public function handleDragExit(eventObj:DragEvent):void{
			_dragClue.visible = false;
		}
		
		public function handleExpanderClick(eventObj:Event):void{
			if(_dispMode != EXPANDED_DISPLAY){
				//create a expanded proxy pane and store it within the pane in the panel
			 	_expandedProxyPane =  MetricPaneFactory.instance.buildMetricPane(_type,this);
			 	//set the display mode
			 	_expandedProxyPane.displayMode = EXPANDED_DISPLAY;
			 	//load proxy pane with latest data
			 	_expandedProxyPane.fillDisplay(_dataCopy);		
			 	
//			 	PopUpManager.addPopUp(_expandedProxyPane, this, true);
			 	PopUpManager.addPopUp(_expandedProxyPane, DisplayObject(Application.application), true);
			 	PopUpManager.centerPopUp(_expandedProxyPane);
			}
			else if(_dispMode == EXPANDED_DISPLAY){
				//remove the expanded proxy pane  
				PopUpManager.removePopUp(this);
			}
		}
		
		public function handlePanePromotion(eventObj:Event):void{
			if(_dispMode == NORMAL_DISPLAY){
				PromotedPaneRepository.instance.addPane(this);	
			}
			else if(_dispMode == PROMOTED_DISPLAY){
				//Demotion handled by ClusterOverviewPage
				dispatchEvent(new Event(EventTypes.PANE_DEMOTED));
			}
		}
		
		/**
		 * Triggeres the removal of the metric from the metric pane and stores the information in the
		 * shared objects 
		 * @param eventObj
		 */		
		public function handlePaneRemove(eventObj:Event):void{
			var eObj:MetricPaneRemoveEvent = new MetricPaneRemoveEvent(MetricPaneRemoveEvent.METRICPANE_REMOVED);
			eObj.metricPane = this;
			eObj.monitoredNode = new XML(_monitoredNode);
			dispatchEvent(eObj);
		}
		
	}
}