package com.tibco.cep.ui.monitor.metricGallery
{
	import com.tibco.cep.ui.monitor.metricGallery.events.GalleryItemEvent;
	
	import flash.display.Graphics;
	import flash.events.MouseEvent;
	
	import mx.controls.Button;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.core.UIComponent;

	public class GalleryItem extends UIComponent
	{
		[Embed(source='assets/images/metricIcon.png')]
		private var imCls:Class;
				
		private var metricLbl:Label;
		private var img:Image;
		private var restoreBtn:Button;
		private var padding:int = 4;
		private var bg:UIComponent;
		
		private var __paneID:String;
		private var __paneType:String;
		private var __monitoredNode:XML;
		
		public function GalleryItem(){
			super();
			
			width = 170;
			height = 50;
			
			bg = new UIComponent();
			this.addChild(bg);
			bg.width = width;
			bg.height = height;
			
			img = new Image();
			this.addChild(img);
			img.width = img.height = 40;
			img.source = imCls;
			img.x = padding;
			img.y = padding;
			
			metricLbl = new Label();
			this.addChild(metricLbl);
			metricLbl.x = img.x + img.width + padding;
			metricLbl.width = width - metricLbl.x - padding;
			metricLbl.height = 20;		
			metricLbl.styleName = "formLblStyle";	
			
			restoreBtn = new Button();
			this.addChild(restoreBtn);
			restoreBtn.x = metricLbl.x;
			restoreBtn.height = 20;
			restoreBtn.y = img.y + img.height - restoreBtn.height;			
			restoreBtn.width = width - restoreBtn.x - padding;
			restoreBtn.label = "Restore Metric";
			restoreBtn.styleName = "restoreBtnStyle";
			restoreBtn.addEventListener(MouseEvent.CLICK, handleRestoreBtnClick);
			
			paintBG();
		}//end of Constructor
		
		/**
		 * @private
		 * Paint the background of the item
		 */		
		private function paintBG():void{
			var g:Graphics = bg.graphics;
			var curveRad:int = 4;
			var bgColor:uint = 0xE3EEFB;
			
			g.clear();
			g.beginFill(bgColor, 0.3);
			g.drawRoundRectComplex(0, 0, width, height, curveRad, curveRad, curveRad, curveRad);
			g.endFill();
		}//end of paintBG
		
		/**
		 * @private
		 * Gets triggered when user restores the metric 
		 * @param eventObj
		 */		
		private function handleRestoreBtnClick(eventObj:MouseEvent):void{
			var eObj:GalleryItemEvent = new GalleryItemEvent(GalleryItemEvent.RESTORE_METRIC);
			eObj.galleryItem = this;
			
			dispatchEvent(eObj);
		}//end of handleRestoreBtnClick
		
		//-----------------------
		//PROPERTIES
		//-----------------------
		public function set metricLabel(val:String):void{
			metricLbl.text = val;
		}		
		
		public function set paneID(val:String):void{
			__paneID = val;
		}
		
		public function set paneType(val:String):void{
			__paneType = val;	
		}
		
		public function get paneType():String{
			return __paneType;
		}
		
		public function set monitoredNode(val:XML):void{ 
			__monitoredNode = val; 
		}
		public function get monitoredNode():XML{ return __monitoredNode }		
	}
}