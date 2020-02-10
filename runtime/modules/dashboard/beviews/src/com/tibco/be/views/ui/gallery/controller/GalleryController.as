package com.tibco.be.views.ui.gallery.controller{
	import com.tibco.be.views.core.io.PSVRClient;
	import com.tibco.be.views.ui.gallery.view.ComponentGallery;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.collections.ArrayCollection;
	import mx.containers.TitleWindow;
	import mx.controls.Alert;
	import mx.controls.Text;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;
    
	public class GalleryController
	{
		private var parentUIComponent:UIComponent;
		private var compGallery:ComponentGallery;
		private var fullGalleryXml:XMLList;
		private var currentDirectory:XMLList;
		private var menuArray:Array;
		[Bindable] private var menuCollection:ArrayCollection = new ArrayCollection(menuArray);
		private var parentFolder:String = "ROOT";
		private var xDist:Number = 0;
		
		// A collection for the scaling-in-size metrics
		private var metricStripArray:Array;
		[Bindable] private var metricStripCollection:ArrayCollection = new ArrayCollection(metricStripArray);
		
		private var goingLeft:Boolean = false;
		private var goingRight:Boolean = false;
		
		public function GalleryController(parentUIComponent:UIComponent):void {
			this.parentUIComponent = parentUIComponent;
		}
		
		public function init():void {
			PSVRClient.instance.sendRequest(this,galleryModelLoaded,"getcompgallerymodel",null,galleryModelLoadFailed,null);
		}
		
		public function createTimer():void {
            var myTimer:Timer = new Timer(10);
            myTimer.addEventListener("timer", timerHandler);
            myTimer.start();
        }
        
        //does the moving
        public function timerHandler(event:TimerEvent):void {
            if(goingLeft){
            	moveLeft();
            	compGallery.leftArrow.alpha = 1;
        	} else if(goingRight){
            	moveRight();
            	compGallery.rightArrow.alpha = 1;
        	} else{
        		compGallery.leftArrow.alpha = .6;
        		compGallery.rightArrow.alpha = .6
        	}
        	
        	if (metricStripCollection.length > 0) {
        		addRepeaterItemListeners();
        	}
        }
        
        private function setLeftTrue(event:MouseEvent):void {
        	goingLeft = true;
        }
        
        private function setRightTrue(event:MouseEvent):void {
        	goingRight = true;
        }
        
        private function setLeftFalse(event:MouseEvent):void {
        	goingLeft = false;
        }
        
        private function setRightFalse(event:MouseEvent):void {
        	goingRight = false;
        	
        }
        
        private function moveLeft():void {
			compGallery.metricStrip.horizontalScrollPosition -= 2;
		}
		
		private function moveRight():void {
			compGallery.metricStrip.horizontalScrollPosition += 2;
		}
		
		private function galleryModelLoaded(success:Boolean, xmlStr:XMLList, stateObj:Object = null):void {
			CursorManager.removeBusyCursor();
			compGallery = ComponentGallery(PopUpManager.createPopUp(parentUIComponent,ComponentGallery,true));
            //compGallery.addEventListener(FlexEvent.CREATION_COMPLETE, compGalleryCreated);
            centerWindow(compGallery);
			xmlLoaded(xmlStr);
			//trace(xmlStr);
		}
		
		private function xmlLoaded(loadedXml:XMLList):void {
			fullGalleryXml = loadedXml;
			currentDirectory = fullGalleryXml;
			compGallery.menuItems.dataProvider = menuCollection;
			compGallery.folderItems.dataProvider = currentDirectory.compgallery.category;
			//compGallery.metricItems.dataProvider = currentDirectory.compgallery.category.comp;
			activateGalleryEventListeners();
		}
		
		private function galleryModelLoadFailed(event:Event):void {
			CursorManager.removeBusyCursor();
			Alert.show("Gallery Reading Error");
		}
		
		private function centerWindow(window:TitleWindow):void {
            window.x = Application.application.width/2 - window.width/2;
            window.y = Application.application.height/2 - window.height/2;
		}
		
		// Initialize all of the listeners for all the components in gallery
		private function activateGalleryEventListeners():void {
			compGallery.addEventListener(CloseEvent.CLOSE, galleryClosed);
			compGallery.upButton.addEventListener(MouseEvent.CLICK, upArrowClicked);
			compGallery.upButton.addEventListener(MouseEvent.MOUSE_OVER, scaleUp);
			compGallery.upButton.addEventListener(MouseEvent.MOUSE_OUT, scaleDown);
			compGallery.homeMenuItem.addEventListener(MouseEvent.CLICK, goHome);
			compGallery.leftArrow.addEventListener(MouseEvent.MOUSE_OVER, setLeftTrue);
			compGallery.rightArrow.addEventListener(MouseEvent.MOUSE_OVER, setRightTrue);
			compGallery.leftArrow.addEventListener(MouseEvent.MOUSE_OUT, setLeftFalse);
			compGallery.rightArrow.addEventListener(MouseEvent.MOUSE_OUT, setRightFalse);
			formatGallery();
			makeRolloverCollection();
			addRepeaterItemListeners();
			createTimer();
		}
		
		private function formatGallery():void {
			for each (var item:galleryMetric in compGallery.galleryFolders) {
				item.x = incrementX();
			}
			for each (var mtrcItem:galleryMetric in compGallery.galleryMetrics) {
				mtrcItem.x = incrementX();
			}
		}
		
		private function addRepeaterItemListeners():void {
			for each (var item:galleryMetric in compGallery.galleryFolders) {
				item.addEventListener(MouseEvent.CLICK, folderClicked);
				item.addEventListener(MouseEvent.MOUSE_OVER, scaleUp);
				item.addEventListener(MouseEvent.MOUSE_OUT, scaleDown);
			}
			for each (var mtrcItem:galleryMetric in compGallery.galleryMetrics) {
				mtrcItem.addEventListener(MouseEvent.MOUSE_OVER, scaleUp);
				mtrcItem.addEventListener(MouseEvent.MOUSE_OUT, scaleDown);
			}
		}
		
		private function scaleUp(event:MouseEvent):void {
			metricStripCollection.getItemAt(metricStripCollection.getItemIndex(event.currentTarget)).showEffect("primary");
			
			//trace ("Rolled On");

			for each (var item:galleryMetric in metricStripCollection) {
				item.hideEffect();
			}
			if (metricStripCollection.length == 1)
			{
				// do nothing
			} else{
				if (metricStripCollection.getItemIndex(event.currentTarget) == 0) {
					metricStripCollection.getItemAt(metricStripCollection.getItemIndex(event.currentTarget)+1).showEffect("secondary");
				} else if (metricStripCollection.getItemIndex(event.currentTarget) == metricStripCollection.length-1) {
					metricStripCollection.getItemAt(metricStripCollection.getItemIndex(event.currentTarget)-1).showEffect("secondary");
				} else{
					metricStripCollection.getItemAt(metricStripCollection.getItemIndex(event.currentTarget)-1).showEffect("secondary");
					metricStripCollection.getItemAt(metricStripCollection.getItemIndex(event.currentTarget)+1).showEffect("secondary");
				}
			}
		}
		
		private function scaleDown(event:MouseEvent):void {
			//trace ("Rolled Off");
			for each (var item:galleryMetric in metricStripCollection) {
				item.hideEffect();
			}
			if (metricStripCollection.getItemIndex(event.currentTarget) >= 0) {
				metricStripCollection.getItemAt(metricStripCollection.getItemIndex(event.currentTarget)).hideEffect();
			} else{
				// do nothing
			}
		}
		
		private function galleryClosed(event:CloseEvent):void {
			PopUpManager.removePopUp(compGallery);
		}
		
		private function folderClicked(event:MouseEvent):void {
			
			xDist = 0;
			
			var folderName:String = event.currentTarget.imgTitle;
			var ctgryID:String = event.currentTarget.metricID;
			var prntFolder:String = event.currentTarget.parentID;
			
			trace ("Current Category: " + ctgryID + " Parent Folder: " + prntFolder);
			if (prntFolder == "ROOT") {
				currentDirectory = new XMLList(currentDirectory.compgallery.category.(@id == ctgryID).category);
			} else{
				currentDirectory = new XMLList(currentDirectory.category.(@parentid == ctgryID));
			}
			compGallery.folderItems.dataProvider = currentDirectory;
			//compGallery.metricItems.dataProvider = currentDirectory.category.comp;
			trace (currentDirectory);
			
			parentFolder = prntFolder;
			
			addFolderToMenu(folderName, ctgryID);
			
			formatGallery();
			makeRolloverCollection();
			addRepeaterItemListeners();
			
			//Below this is the original stuff
			
			/*
			xDist = 0;
			
			var folderName:String = event.currentTarget.imgTitle;
			var ctgryID:String = event.currentTarget.metricID;
			var prntFolder:String = event.currentTarget.parentID;
			
			currentDirectory = new XMLList(fullGalleryXml.compgallery.category.(@id == ctgryID).category);
			compGallery.folderItems.dataProvider = currentDirectory;
			trace (currentDirectory);
			
			parentFolder = prntFolder;
			
			addFolderToMenu(folderName, ctgryID);
			
			formatGallery();
			makeRolloverCollection();
			addRepeaterItemListeners();
			*/
		}
		
		private function upArrowClicked(event:MouseEvent):void {
			xDist = 0;
			
			if (parentFolder == "ROOT" || null || "") {
				currentDirectory = new XMLList(fullGalleryXml);
				compGallery.folderItems.dataProvider = currentDirectory.compgallery.category;
				//compGallery.metricItems.dataProvider = currentDirectory.compgallery.category.comp;
			} else{
				currentDirectory = new XMLList(fullGalleryXml.descendants().category.(@parentid == parentFolder));
				//currentDirectory = new XMLList(fullGalleryXml.compgallery.category.(@id == parentFolder).category);
				compGallery.folderItems.dataProvider = currentDirectory;
				//compGallery.metricItems.dataProvider = currentDirectory.comp;
				parentFolder = fullGalleryXml.descendants().category.(@id == parentFolder).@parentid;
			}
			subtractFolderFromMenu();
			
			formatGallery();
			makeRolloverCollection();
			addRepeaterItemListeners();
		}
		
		private function makeRolloverCollection():void {
			
			metricStripCollection.removeAll();
			// take the up arrow, put it in the collection
			metricStripCollection.addItem(compGallery.upButton);
			// take the folders, put them in the collection
			for each (var item:galleryMetric in compGallery.galleryFolders) {
				metricStripCollection.addItem(item);
			}
			for each (var mtrcItem:galleryMetric in compGallery.galleryMetrics) {
				metricStripCollection.addItem(mtrcItem);
			}
		}
		
		public function goHome(event:MouseEvent):void {
			xDist = 0;
			currentDirectory = fullGalleryXml;
			compGallery.folderItems.dataProvider = currentDirectory.compgallery.category;
			//compGallery.metricItems.dataProvider = currentDirectory.compgallery.category.comp;
			menuCollection.removeAll();
			
			formatGallery();
			makeRolloverCollection();
			addRepeaterItemListeners();
		}
		
		private function addFolderToMenu(fldrName:String, ctgryID:String):void {
			var menuText:Text = new Text();
			menuText.text = fldrName;
			menuText.toolTip = ctgryID;
			menuCollection.addItem(menuText);
			menuCollection.getItemAt(menuCollection.length-1).addEventListener(MouseEvent.MOUSE_UP, goHome);
		}
		
		private function subtractFolderFromMenu():void {
			if (menuCollection.length == 0) {
				
			} else{
				menuCollection.removeItemAt(menuCollection.length-1);
			}
		}
		
		private function menuItemClick(folderID:String, menuItem:Object):void {
			xDist = 0;
			
			if (fullGalleryXml.category.(@id == folderID).@parentid == "ROOT") {
				currentDirectory = new XMLList(fullGalleryXml.category);
			} else{
				currentDirectory = new XMLList(fullGalleryXml.descendants().category.(@id == folderID));
			}
			
			var clickedIndex:Number = menuCollection.getItemIndex(menuItem);
			
			//if the menu item is the last item, don't remove anything.
			for (var i:int=menuCollection.length-1; i>clickedIndex; i--)
			{
			  menuCollection.removeItemAt(i);
			}
		}
		
		public function incrementX():Number {
			if (xDist == 0) {
				xDist += 100;
			} else if (xDist == 100) {
				xDist += 80;
			}
			else{
				xDist += 80;
			}
			return xDist;
		}
	}
}