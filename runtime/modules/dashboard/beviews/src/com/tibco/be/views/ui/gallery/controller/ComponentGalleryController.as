package com.tibco.be.views.ui.gallery.controller{
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.ui.gallery.view.BreadCrumbsLink;
	import com.tibco.be.views.ui.gallery.view.ComponentGallery;
	import com.tibco.be.views.ui.gallery.view.ComponentGalleryMetric;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.controls.LinkButton;
	import mx.controls.Menu;
	import mx.events.FlexEvent;
	import mx.events.MenuEvent;
	
	
	public class ComponentGalleryController implements IEventBusListener{
		
		private var _view:ComponentGallery;
		
		/**
		 * The compgallery XML node received from the server on the getcompgallerymodel request.
		*/ 
		private var _galleryXML:XML
		
		/**
		 * An array of ComponentGalleryMetric items currently being displayed.
		*/
		private var _displayedMetrics:Array;
		
		/**
		 * A stack representing the breadcrumbs navigation state.
		*/
		private var _buttonStack:Array;
		
		/**
		 * There's no straigt-forward way of getting the popup button's selected menu item on the
		 * click event.  We thus must maintain a button to XML node map to enable navigation by
		 * simply clicking the popup button.
		*/ 
		private var _button2NodeMap:Dictionary;

		public function ComponentGalleryController(componentGallery:ComponentGallery){
			_view = componentGallery;
			_buttonStack = new Array();
			_button2NodeMap = new Dictionary();
		}
		
		public function init():void{
			registerListeners();
			EventBus.instance.dispatchEvent(new ConfigRequestEvent(ConfigRequestEvent.GET_COMPONENT_GALLERY, this));
		}
		
		public function destroy():void{
			EventBus.instance.removeEventListener(ConfigRequestEvent.GET_COMPONENT_GALLERY, handleServerResponse);
		}
		
		private function setup(galleryXML:XML):void{
			_galleryXML = new XML(galleryXML);
			var homeButton:BreadCrumbsLink = buildBreadCrumbButton("Home");
			addMenuToButton(homeButton, _galleryXML.category);
			_button2NodeMap[homeButton] = _galleryXML;
			_view.hb_BreadCrumbsBar.addChild(homeButton);
		}
		
		/**
		 * Creates and sets the menu to be displayed for the provided PopUpButton based off of the
		 * component directories listed in the menuItemsXML parameter. 
		*/
		private function addMenuToButton(button:BreadCrumbsLink, menuItemsXML:XMLList):void{
			var menuItems:ArrayCollection = new ArrayCollection();
			for each(var node:XML in menuItemsXML){
				var menuItem:Object = new Object();
				menuItem.label = new String(node.name);
				menuItem.node = node;
				menuItem.buttonIndex = _buttonStack.length;
				menuItems.addItem(menuItem);
			}
			var menu:Menu = Menu.createMenu(null, menuItems);
			menu.addEventListener(MenuEvent.ITEM_CLICK, handleMenuItemClick);
			menu.setStyle("textAlign", "left");
			menu.setStyle("leftIconGap", 3);
			menu.setStyle("rightIconGap", 3);
			button.menu = menu;
			_buttonStack.push(button);
		}
		
		private function buildBreadCrumbButton(label:String):BreadCrumbsLink{
			var button:BreadCrumbsLink = new BreadCrumbsLink();
			button.linkLabel = label;
			button.addEventListener(FlexEvent.CREATION_COMPLETE, handleButtonCreated);
			return button;
		}
		
		private function handleButtonCreated(event:FlexEvent):void{
			var bclTarget:BreadCrumbsLink = event.currentTarget as BreadCrumbsLink;
			bclTarget.lb_DirectoryLink.addEventListener(MouseEvent.CLICK, handleButtonClick);
			bclTarget.lb_DirectoryLink.addEventListener(MouseEvent.MOUSE_OVER, closeOtherMenus);
		}
		
		private function closeOtherMenus(event:MouseEvent):void{
			try{
				var bclTarget:BreadCrumbsLink = event.target.parent.parent as BreadCrumbsLink;
				if(bclTarget == null) return;				
				for each(var bcl:BreadCrumbsLink in _buttonStack){
					if(bcl != bclTarget){
						bcl.menu.hide();
					}
				}
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.DEBUG, "ComponentGalleryController.closeOtherMenus - " + error.message);
			}
		}
		
		private function handleButtonClick(event:MouseEvent):void{
			try{
				if(event.target is LinkButton){
					var button:BreadCrumbsLink = event.target.parent.parent as BreadCrumbsLink;
					if(button == null) return;				
					removeButtons(_buttonStack.indexOf(button)+1);
					displayComponents(_button2NodeMap[button] as XML);
				}
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.DEBUG, "ComponentGalleryController.handleButtonClick - " + error.message);
			}
		}
		
		private function handleMenuItemClick(event:MenuEvent):void{
			if(event.item.node == undefined || event.item.label == undefined || event.item.node == undefined
			   || event.item.buttonIndex == undefined){
				Logger.log(DefaultLogEvent.DEBUG, "ComponentGalleryController.handleMenuItemClick - the event-provided node is invalid.");
				return;
			}
			
			removeButtons(Number(event.item.buttonIndex)+1);

			//add a new button for the clicked item
			var newBCButton:BreadCrumbsLink = buildBreadCrumbButton(event.item.label);
			addMenuToButton(newBCButton, event.item.node.category);
			_view.hb_BreadCrumbsBar.addChild(newBCButton);
			
			//display the components for the selected item
			displayComponents(event.item.node);
			
			//update the button to XML node mapping
			_button2NodeMap[newBCButton] = event.item.node;
		}
		
		/**
		 * Removes breadcrumbs buttons starting at the provided iStart index and continuing to iEnd.
		 * If no iEnd is specified, all buttons following the one at iStart are removed.
		*/
		private function removeButtons(iStart:Number, iEnd:Number=-1):void{
			iEnd = iEnd < 0 ? _buttonStack.length:iEnd;
			for(var i:int = iStart; i < iEnd; i++){
				var deleteMe:BreadCrumbsLink = _buttonStack.pop();
				_view.hb_BreadCrumbsBar.removeChild(deleteMe);
				delete _button2NodeMap[deleteMe];
			}
		}
		
		/**
		 * Displays the components listed in the componentsXML in the gallery.
		*/
		private function displayComponents(componentsXML:XML):void{
			var componentList:XMLList = componentsXML.comp;
			_view.hb_ComponentsView.removeAllChildren();
			_displayedMetrics = new Array(); //not sure which is less costly new Array or splice
			for(var i:int = 0; i < componentList.length(); i++){
				var galleryMetric:ComponentGalleryMetric = new ComponentGalleryMetric();
				galleryMetric.config = (componentList[i] as XML);
//				galleryMetric.addEventListener(MouseEvent.ROLL_OVER, handleMetricMouseOver);
//				galleryMetric.addEventListener(MouseEvent.ROLL_OUT, handleMetricMouseOut);
				_view.hb_ComponentsView.addChild(galleryMetric);
				_displayedMetrics.push(galleryMetric);
			}
		}
		
		private function handleMetricMouseOver(event:MouseEvent):void{
			try{
				var metric:ComponentGalleryMetric = event.target as ComponentGalleryMetric;
				if(metric == null) return;
				var iMetric:int = _displayedMetrics.indexOf(metric);
				
				var leftMetric:ComponentGalleryMetric;
				var rightMetric:ComponentGalleryMetric;
				if(iMetric-1 >= 0){
					leftMetric = _displayedMetrics[iMetric-1] as ComponentGalleryMetric;
				}
				if(iMetric+1 < _displayedMetrics.length){
					rightMetric = _displayedMetrics[iMetric+1] as ComponentGalleryMetric;
				}
				metric.enlarge.play([metric]);
//				metric.semiEnlarge.play([rightMetric, leftMetric]);
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.DEBUG, "ComponentGalleryController.handleMetricMouseOver - " + error.message);
			}
		}
		
		private function handleMetricMouseOut(event:MouseEvent):void{
			try{
				var metric:ComponentGalleryMetric = event.target as ComponentGalleryMetric;
				if(metric == null) return;
				var iMetric:int = _displayedMetrics.indexOf(metric);
				
				var leftMetric:ComponentGalleryMetric;
				var rightMetric:ComponentGalleryMetric;
				if(iMetric-1 >= 0){
					leftMetric = _displayedMetrics[iMetric-1] as ComponentGalleryMetric;
				}
				if(iMetric+1 < _displayedMetrics.length){
					rightMetric = _displayedMetrics[iMetric+1] as ComponentGalleryMetric;
				}
				metric.revert.play([metric]);
//				metric.revert.play([metric, rightMetric, leftMetric]);
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.DEBUG, "ComponentGalleryController.handleMetricMouseOut - " + error.message);
			}
		}
		
		private function handleServerResponse(response:ConfigResponseEvent):void{
			if(!isRecipient(response)) return;
			if(response.command == ConfigRequestEvent.GET_COMPONENT_GALLERY){
				setup(response.dataAsXML);
			}
			else if(response.command == ConfigRequestEvent.REMOVE_COMPONENT_COMMAND){
				for each(var galleryComponent:ComponentGalleryMetric in _displayedMetrics){
					if(galleryComponent.componentId == response.request.getXMLParameter("componentid")){
						galleryComponent.enableMetric();
						break;
					}
				}
			}
		}
				
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleServerResponse);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			if(event.intendedRecipient == this){ return true; }
			if(event is ConfigResponseEvent){
				return (event as ConfigResponseEvent).command == ConfigRequestEvent.REMOVE_COMPONENT_COMMAND;
			}
			return false;
		}
		
	}
}