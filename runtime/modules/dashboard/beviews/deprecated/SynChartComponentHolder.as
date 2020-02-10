package com.tibco.be.views.deprecated{
	
	import com.tibco.be.views.core.Kernel;
	import com.tibco.be.views.core.ui.ActionContext;
	import com.tibco.be.views.core.ui.ActionContextProvider;
	import com.tibco.be.views.core.ui.DynamicMenuDefinition;
	import com.tibco.be.views.core.ui.BEVMenu;
	import com.tibco.be.views.core.ui.SynTooltip;
	import com.tibco.be.views.core.ui.controls.BEVPopUpMenuButton;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponentContext;
	
	import flash.display.BitmapData;
	import flash.events.MouseEvent;
	
	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.core.BitmapAsset;
	import mx.core.DragSource;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.graphics.ImageSnapshot;
	import mx.managers.DragManager;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	
	public class SynChartComponentHolder extends UIComponent implements BEVComponentContext, ActionContextProvider{
		
    	private static var defaultStylesInitialized:Boolean = setDefaultStyles();

		private static function setDefaultStyles():Boolean{
			if(StyleManager.getStyleDeclaration("SynChartComponentHolder") == null){
				
				var titleControlStyleDeclaration:CSSStyleDeclaration = new CSSStyleDeclaration();
				titleControlStyleDeclaration.setStyle("color",0xFFFFFF);
				titleControlStyleDeclaration.setStyle("rollOverColor",0xCCCCCC);
				//titleControlStyleDeclaration.setStyle("color",0xCCCCCC);
				//titleControlStyleDeclaration.setStyle("rollOverColor",0xFFFFFF);
				
				StyleManager.setStyleDeclaration(".componentholdertitlestyle",titleControlStyleDeclaration,false);
				
				var componentHolderStyleDeclaration:CSSStyleDeclaration = new CSSStyleDeclaration();
				componentHolderStyleDeclaration.setStyle("borderColor",0x727087);
				componentHolderStyleDeclaration.setStyle("borderThickness",0.5);
				componentHolderStyleDeclaration.setStyle("borderAlpha",0.5);
				componentHolderStyleDeclaration.setStyle("backgroundColor",0xCBD9E1);
				componentHolderStyleDeclaration.setStyle("headerHeight",18);
				componentHolderStyleDeclaration.setStyle("headerColor",0x85819D);
				componentHolderStyleDeclaration.setStyle("headerPadding",1);
				componentHolderStyleDeclaration.setStyle("titleStyleName","componentholdertitlestyle");
				
				StyleManager.setStyleDeclaration("SynChartComponentHolder",componentHolderStyleDeclaration,true);
			}
//        	var temp:BEVChartComponentHolder = new BEVChartComponentHolder();
        	return true;
        	
        }
        
        protected var _titleBar:UIComponent;
        protected var _titleControl:UIComponent;
        protected var _captionButtonHolder:HBox;
		protected var _component:BEVComponent;
		
		public function SynChartComponentHolder(component:BEVComponent):void{
			_component = component;
			
			_titleBar = new UIComponent();
			
			_captionButtonHolder = new HBox();
			_captionButtonHolder.setStyle("horizontalGap",0);
			_captionButtonHolder.setStyle("paddingRight",2);
			_captionButtonHolder.setStyle("verticalAlign","middle");
			
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		public function get component():BEVComponent{ return _component; }	
		
		override protected function createChildren():void{
//			Logger.instance.logA(this,Logger.DEBUG,component.componentname+"::createChildren");
			
			if(_titleControl == null){
				_titleControl = new Label();
				var styleDeclaration:CSSStyleDeclaration = StyleManager.getStyleDeclaration("."+getStyle("titleStyleName"));
				if(styleDeclaration != null){
					_titleControl.styleName = styleDeclaration;
				}				
				//TODO figure out how to handle title change in the component
				(_titleControl as Label).text = _component.componentTitle;
				_titleBar.addChild(_titleControl);
			}
			
			_titleBar.addChild(_captionButtonHolder);
			
			addChild(_titleBar);				
			addChild(_component);
		}
		
		public function addCaptionButton(captionButton:Button):Button{
			_captionButtonHolder.addChild(captionButton);
			invalidateDisplayList();
			return captionButton;
		}	
		
		public  function addCaptionButtonAt(captionButton:Button, index:int):Button{
			_captionButtonHolder.addChildAt(captionButton, index);
			invalidateDisplayList();
			return captionButton;
		}
		
		public  function removeCaptionButton(captionButton:Button):Button{
			_captionButtonHolder.removeChild(captionButton);
			invalidateDisplayList();
			return captionButton;
		}
		
		public  function removeCaptionButtonAt(index:int):Button{
			var captionButton:Button = _captionButtonHolder.removeChildAt(index) as Button;
			if(captionButton != null){
				invalidateDisplayList();
			}
			return captionButton;
		}							
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
//			Logger.instance.logA(this,Logger.DEBUG,component.componentname+"::updateDisplayList");
			var borderThickness:int = getStyle("borderThickness");
			var sides:String = getStyle("borderSides");

			//draw the border & fill in the background 
			graphics.clear();
			if(getStyle("borderStyle") == "none") return;
			graphics.lineStyle(borderThickness, getStyle("borderColor"), getStyle("borderAlpha"));
			graphics.beginFill(getStyle("backgroundColor"));
			if(sides == "left"){
				graphics.drawRect(0,0,borderThickness,unscaledHeight);
			}
			else if(sides == "right"){
				graphics.drawRect(unscaledWidth-borderThickness,0,borderThickness,unscaledHeight);
			}
			else{
				graphics.drawRect(0,0,unscaledWidth,unscaledHeight);
			}
			graphics.endFill();
			
			
			//draw the header
			_titleBar.setActualSize(unscaledWidth,getStyle("headerHeight"));
			_titleBar.move(0,0);
			_titleBar.graphics.clear();
			_titleBar.graphics.beginFill(getStyle("headerColor"));
			_titleBar.graphics.drawRect(0,0,_titleBar.width,_titleBar.height);
			//lets layout the title label 
			layoutHeader(unscaledWidth,_titleBar.height);
			//lets layout the component if any
			if(_component != null){
				_component.width = unscaledWidth;
				_component.height = unscaledHeight - getStyle("headerHeight");
				_component.move(0,getStyle("headerHeight"));
			}
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		protected function layoutHeader(unscaledWidth:Number, unscaledHeight:Number):void{
			//we will let the title have 75% of the width
			_titleControl.width = Math.min(_titleControl.measuredWidth,unscaledWidth * .75);
			_titleControl.height = unscaledHeight - 2;
			_titleControl.move(getStyle("headerPadding"),1);
			
			for(var i:int = 0 ; i < _captionButtonHolder.numChildren ; i++){
				_captionButtonHolder.getChildAt(i).height = unscaledHeight - 2;
				_captionButtonHolder.getChildAt(i).width = unscaledHeight - 2; 
			}
			
			_captionButtonHolder.setActualSize(_captionButtonHolder.getExplicitOrMeasuredWidth(), unscaledHeight);
			_captionButtonHolder.move(unscaledWidth - _captionButtonHolder.getExplicitOrMeasuredWidth(), 1);			
		}
		
		/**
		 * Shows a menu 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param menuDefinition the XML which defines the menu 
		 * @param actionCtxProvider The action context provider 
		 */ 
		public function showMenu(x:int,y:int,menuDefinition:XML,actionCtxProvider:ActionContextProvider):void{
			var menu:BEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(menuDefinition, actionCtxProvider);
			menu.show(x,y);
		}
		
		/**
		 * Shows a menu 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param menuDefinition the DynamicMenuDefinition which defines the menu 
		 * @param actionCtxProvider The action context provider 
		 */		
		public function showDynamicMenu(x:int, y:int, menuDefinition:DynamicMenuDefinition, actionCtxProvider:ActionContextProvider):void{
			var menu:BEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingDynamicDefintion(menuDefinition, actionCtxProvider);
			menu.show(x,y);			
		}
		
		/**
		 * Shows a tooltip 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param tooltip The actual tooltip to be shown
		 */ 
		public function showTooltip(x:int, y:int,tooltip:String):void{
			var tooltipHolder:SynTooltip = Kernel.instance.uimediator.tooltipprovider.getTooltip(tooltip);
			tooltipHolder.show(x,y);
		}
		
		/**
		 * Logs a message against the component name 
		 * @param logLevel The level of logging 
		 * @param logMessage The message to be logged 
		 */ 
		public function log(logLevel:Number,logMessage:String):void{
//			Logger.instance.logA(component, logLevel, logMessage, null);
		}
		
		/**
		 * Handles a exception on behalf of the component 
		 * @param logLevel The level of logging 
		 * @param exceptionMessage The message to be logged 
		 * @param error The exception 
		 */ 
		public function handleException(logLevel:Number,exceptionMessage:String,exception:Error):void{
//			Logger.instance.logA(component, logLevel, exceptionMessage, exception);
		}
		
		private function handleCreationComplete(event:FlexEvent):void{
			addEventListener(MouseEvent.MOUSE_DOWN, handleDragStart);
		}
		
		private function handleDragStart(event:MouseEvent):void{
			var compHolder:SynChartComponentHolder = null;
			try{
				if(!(event.target.parent is SynChartComponentHolder)){ return; }
				compHolder = event.target.parent as SynChartComponentHolder;
			}
			catch(error:Error){ return; }
			var dragSource:DragSource = new DragSource();
			var dragProxy:ImageSnapshot = new ImageSnapshot();
			var img:Image = new Image();
			var bmd:BitmapData = ImageSnapshot.captureBitmapData(compHolder as SynChartComponentHolder);
			img.source = new BitmapAsset(bmd);
			img.alpha = 0.6;
			img.width = this.width;
			img.height = this.height;
			DragManager.doDrag(this, dragSource, event, img);
		}
		
		/**
		 * Searches for a component within the current dashboard based on a name 
		 * @param name the name of a component 
		 * @param searchPeers  if true,then the search is only limited to the components within the same parent. 
		 * 									The parent is the component's parent
		 */ 
		public function getComponentByName(name:String,searchPeers:Boolean):BEVComponent{
			return searchComponentByName(name,component.componentContainer, searchPeers);
		}
		
		private function searchComponentByName(name:String, container:IBEVContainer, searchPeers:Boolean):BEVComponent{
			var comp:BEVComponent = container.getComponentByName(name);
			if(searchPeers || comp != null){ return comp; }
			for each (var childContainer: IBEVContainer in container.childContainers){
				comp = childContainer.getComponentByName(name);
				if(comp != null){ return comp; }
			}
			var parentContainer:IBEVContainer = container.containerParent;
			if(parentContainer == null){ return null; }
			return searchComponentByName(name, parentContainer, false);				
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			throw new Error("Unsupported Operation");
		}
		
		/**
		 * Sets the interactions of the component with the outside world. Typically these interaction will
		 * manifest themselves as a menu.
		 */ 		
		public function setComponentInteractions(actionConfig:XML):void{
//			Logger.instance.logA(this,Logger.DEBUG,component.componentname+"::setComponentInteractions");
			if(_titleControl != null){
				_titleBar.removeChild(_titleControl);
			}
			var actionableTitleCntrl:BEVPopUpMenuButton = new BEVPopUpMenuButton(this);
			var styleDeclaration:CSSStyleDeclaration = StyleManager.getStyleDeclaration("."+getStyle("titleStyleName"));
			if(styleDeclaration != null){
				actionableTitleCntrl.styleName = styleDeclaration;
			}				
			actionableTitleCntrl.text = component.componentTitle;
			actionableTitleCntrl.menuConfig = actionConfig;
			_titleControl = actionableTitleCntrl;
			_titleBar.addChildAt(actionableTitleCntrl,0);
			invalidateDisplayList();
		}
				
	}
}


//			var hole:Object = null;
//			if(sides != "left top right bottom"){
//				if(sides == "left"){
//					hole ={ x: 0,
//							 y: 0,
//							 w: unscaledWidth - borderThickness * 2,
//							 h: unscaledHeight,
//							 r: 0 };
//				}
//				else if(sides == "right"){
//					hole ={ x: borderThickness,
//							 y: borderThickness,
//							 w: unscaledWidth - borderThickness,
//							 h: unscaledHeight - borderThickness * 2,
//							 r: 0 };
//				}
//			}
//			