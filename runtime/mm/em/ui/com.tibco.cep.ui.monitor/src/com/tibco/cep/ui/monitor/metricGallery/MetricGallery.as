package com.tibco.cep.ui.monitor.metricGallery
{
	import com.tibco.cep.ui.monitor.RemovedMetricManager;
	import com.tibco.cep.ui.monitor.metricGallery.events.GalleryItemEvent;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.display.Graphics;
	import flash.events.MouseEvent;
	import flash.filters.DropShadowFilter;
	
	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.core.UIComponent;
	import mx.effects.Move;
	import mx.events.EffectEvent;
	import mx.events.FlexMouseEvent;

	public class MetricGallery extends UIComponent
	{
		private static const WIDTH_CORRECTION:Number = -17;
		private static const CLOSED_HEIGHT:Number = 24;
		private static const OPEN_HEIGHT:Number = 110;
		
		private var itemHolder:HBox;
		private var holderBtn:Button;
		private var galleryContentHolder:UIComponent;
		private var galleryContentMask:UIComponent;
		private var panes:Array;
		
		public static const GALLERY_CLOSED:String = "galleryClosed";
		public static const GALLERY_OPENED:String = "galleryOpened";
		
		private var __displayState:String;
		private var __pageID:String;
		private var __updatingDisplay:Boolean=false;
		
		private var _monitoredNode:XML;
		
		private static var REMOVED_NODE:String = "removedNode";
		
		public function set monitoredNode(monitoredNode:XML):void {
			_monitoredNode = monitoredNode;
		}
		
		public function MetricGallery(monitoredNode:XML){
			super();
			_monitoredNode = monitoredNode;			
			initProperties();
		}//end of constructor
		
		/**
		 * @private
		 * Initiate the component's properties 
		 */		
		private function initProperties():void{
			__displayState = GALLERY_CLOSED; 
			
			galleryContentHolder = new UIComponent();
			this.addChild(galleryContentHolder);
			
			galleryContentMask = new UIComponent();
			this.addChild(galleryContentMask);
			
			itemHolder = new HBox;
			galleryContentHolder.addChild(itemHolder);
			itemHolder.styleName = "galleryItemHolderStyle";
			
			holderBtn = new Button();
			galleryContentHolder.addChild(holderBtn);
			holderBtn.addEventListener(MouseEvent.CLICK, handleHolderBtnClick);
			holderBtn.styleName = "metricGalleryHolderBtnStyle";
			holderBtn.label = "Metric Gallery";
			
			galleryContentHolder.mask = galleryContentMask;

			height = OPEN_HEIGHT;
			
			setDimensions();
			paintMask();
			setStateBasedDisplay(false);
			
			var shadow:DropShadowFilter = new DropShadowFilter();
			shadow.color = 0x666666;
			shadow.alpha = 0.6;
			shadow.distance = 3;
			shadow.angle = 90;
			this.filters = [shadow];
		}//end of initProperties
		
		
		/**
		 * @private 
		 * Paints the mask for the metric gallery
		 */
		private function paintMask():void{
			var g:Graphics = galleryContentMask.graphics;
			g.clear();
			g.beginFill(0xCCCC00, 1);
			g.drawRect(0, -4, width, height);
			g.endFill();
		}//end of paintMask
		
		/**
		 * @private
		 * Sets the component's display based on the current state 
		 * @param animate
		 * 
		 */		
		private function setStateBasedDisplay(animate:Boolean):void{
			this.cacheAsBitmap = true;
			if(animate){
				var move:Move = new Move();
				move.target = galleryContentHolder;
				
				if(__displayState == GALLERY_CLOSED){
					move.yFrom = galleryContentHolder.y;
					move.yTo = -(galleryContentHolder.height - holderBtn.height + 4);
				}
				else if(__displayState == GALLERY_OPENED){
					move.yFrom = galleryContentHolder.y;
					move.yTo = -4;					
				}
				
				move.play();
				move.addEventListener(EffectEvent.EFFECT_END, handleEffectEnd);
			}
			else{
				if(__displayState == GALLERY_CLOSED){
					galleryContentHolder.y = -(galleryContentHolder.height - holderBtn.height + 4);
				}
				else if(__displayState == GALLERY_OPENED){
					galleryContentHolder.y = -4;
				}
			}
		}//end of setStateBasedDisplay
		
		
		/**
		 * @private 
		 * Sets the dimensions of the component
		 */		
		private function setDimensions():void{
			holderBtn.width = this.width;
			holderBtn.height = CLOSED_HEIGHT;
			holderBtn.y = height - holderBtn.height;
			
			itemHolder.width = this.width;
			itemHolder.height = this.height - holderBtn.height;
			
			galleryContentHolder.height = holderBtn.height + itemHolder.height;
		}//end of setDimensions
		
		public function updateDisplay(w:Number, h:Number):void{
//			if(__updatingDisplay) return;
//			__updatingDisplay = true;
			width = w;
			height = OPEN_HEIGHT;
			setDimensions();			
			paintMask();
		}//end of updateDisplayList
		
		
		private function handleHolderBtnClick(eventObj:MouseEvent):void{
			__displayState = (__displayState == GALLERY_CLOSED)?GALLERY_OPENED:GALLERY_CLOSED;
			setStateBasedDisplay(true);
		}//end of handleHolderBtnClick
		
		private function handleMouseDownOutside(eventObj:FlexMouseEvent):void{
			__displayState = (__displayState == GALLERY_CLOSED)?GALLERY_OPENED:GALLERY_CLOSED;			
			setStateBasedDisplay(true);			
		}//end of handleMouseDownOutside
		
		/**
		 * @private
		 * Gets triggered once the effect playing ends 
		 * @param eventObj
		 */		
		private function handleEffectEnd(eventObj:EffectEvent):void{
			galleryContentHolder.mask = galleryContentMask;
		}//end of handleEffectEnd
		
		/**
		 * @private
		 * Gets triggered when user tries to restore metric back to the the page 
		 * @param eventObj
		 */		
		private function handleRestoreMetric(eventObj:GalleryItemEvent):void{
			//if the panes are inactive or the 
			if(_monitoredNode == null || _monitoredNode.name().toString() == REMOVED_NODE 
									  ||_monitoredNode.@active=="false") {
									  	
				Util.infoMessage("Trying to restore a pane in an inactive node. " + 
								 "Wait for the node to become active and try again.");
								 
				Logger.logDebug(this, "Trying to restore a pane in an inactive node. No pane restored");
				return;
			}
			
			var galleryItem:GalleryItem = GalleryItem(eventObj.galleryItem);
			
			//remove the instance of gallery item from the item holder
			itemHolder.removeChild(galleryItem);
			
			//remove the metric from the SharedObject list
			RemovedMetricManager.instance.removePane(__pageID, galleryItem.paneType);
			
			//at this point we need to tell the parent that the metric needs to be added to the panel again
			var eObj:GalleryItemEvent = new GalleryItemEvent(GalleryItemEvent.RESTORE_METRIC);
			eObj.galleryItem = galleryItem;
			
			dispatchEvent(eObj);
		}//end of handleRestoreMetric
		
		/**
		 * Displays the gallery items based on the panes array 
		 */
		private function showGalleryItems():void{
			var i:int;
			
			//remove all children
			itemHolder.removeAllChildren();
			
			//run through the list of items and paint them
			for(i=0;i<panes.length;i++){
				var gItem:GalleryItem = new GalleryItem();
				gItem.metricLabel = panes[i].label;
				gItem.paneID = panes[i].id;
				gItem.paneType = panes[i].type;
				gItem.monitoredNode = panes[i].monitoredNode;
				gItem.addEventListener(GalleryItemEvent.RESTORE_METRIC, handleRestoreMetric);
				
				itemHolder.addChild(gItem);
			}
		}//end of showGalleryItems
		
		/**
		 * Gets the latest data from the shared object for this page and displays the list in the gallery 
		 */		
		public function refresh():void{
			panes = RemovedMetricManager.instance.getPanesOfPage(__pageID);
			
			showGalleryItems();
		}//end of refresh
		
		
		//----------------------------
		//PROPERTIES
		//----------------------------
		public function set pageID(val:String):void{
			__pageID = val;
		}
	}
}