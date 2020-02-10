package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.controls.BEVIconMenuButton;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.containers.HBox;
	import mx.containers.Panel;
	import mx.core.ScrollPolicy;
	import mx.core.UIComponent;
	
	public class AbstractBEVPanel extends Panel implements IBEVContainer{
		
		public static const HEADER_ENABLED_PANEL_STYLE:String = "headerEnabledPanel";
		public static const HEADER_DISABLED_PANEL_STYLE:String = "headerDisabledPanel";

    	private static var EMPTY_LIST:IList = new ArrayCollection();
		private static var HEADER_ELEMENT_HEIGHT:int = 18;
		
		protected var _parent:IBEVContainer;
        protected var _captionButtonHolder:HBox;
		protected var _containerID:String;
		protected var _containerName:String;
		protected var _containerTitle:String;
		protected var _containerType:String;
		protected var _runBackgroundProcess:Boolean;
		
		private var _interactionButton:UIComponent;
		
        public function AbstractBEVPanel(parent:IBEVContainer):void{
        	_parent = parent;
        	_captionButtonHolder = new HBox();
        	horizontalScrollPolicy = ScrollPolicy.AUTO;
        	verticalScrollPolicy = ScrollPolicy.AUTO;
        	clipContent = true;
        	styleName= "abstractBEVPanel";
        }
        
        /** Returns the id of the container */ 
		public function get containerId():String{ return _containerID; }
		
		/** Returns the name of the container */ 
		public function get containerName():String{ return _containerName; }
		
		/** Returns the title of the container */ 
		public function get containerTitle():String{ return _containerTitle; }
		
		/** Returns the type of the container */ 
		public function get containerType():String{ return _containerType; }
		
		/** Returns the container definition XML */ 
		public function get containerConfig():XML{ throw new Error("Unsupported Operation"); }
		
		/** Returns the components embedded inside the container */ 
		public function get childComponents():IList{ return EMPTY_LIST; }
		
		/** Returns the containers embedded inside the container */ 
		public function get childContainers():IList{ return EMPTY_LIST; }
		
		/** Returns the parent of the container. Can be null */ 
		public function get containerParent():IBEVContainer{ return _parent; }
		
		override public function set title(value:String):void {
			//Anand - 02/03/2012 see BE-14332
			//Do not set the title on the panel, since that will trigger a re-measuring 
			//the panel measure logic does not seem to honor truncation 
			_containerTitle = title;
		}
				
		/**
		 * Sets the title of the container 
		 * @param containerTitle The new title of the container 
		 */ 
		public function set containerTitle(containerTitle:String):void{
			_containerTitle = containerTitle;
			//Anand - 02/03/2012 see BE-14332
			//Do not set the title on the panel, since that will trigger a re-measuring 
			//the panel measure logic does not seem to honor truncation 
			//title = _containerTitle;
		}
        
        public function init(containerCfg:XML = null):void{ /* do nothing */ }
        
		override protected function createChildren():void{
			super.createChildren();
			_captionButtonHolder.setStyle("horizontalGap", 0);
			_captionButtonHolder.setStyle("horizontalAlign", "right");
			_captionButtonHolder.setStyle("verticalAlign", "middle");
			_captionButtonHolder.horizontalScrollPolicy = ScrollPolicy.OFF;
			_captionButtonHolder.verticalScrollPolicy = ScrollPolicy.OFF;
			_captionButtonHolder.setStyle("paddingRight", 5);
			titleBar.addChild(_captionButtonHolder);
		}
		
		public function getCaptionButtonIndex(captionButton:UIComponent):int{
			return _captionButtonHolder.getChildIndex(captionButton);
		}
		
		/**
		 * Searches for a component in the container 
		 * @param name the name of the component to be searched
		 */  
		public function getComponentByName(name:String):BEVComponent{ return null; }
		
		public function addCaptionButton(captionButton:UIComponent):UIComponent{
			_captionButtonHolder.addChild(captionButton);
			if(captionButton.width == 0){ 
				captionButton.width = HEADER_ELEMENT_HEIGHT;
			}
			if(captionButton.height == 0){
				captionButton.height = HEADER_ELEMENT_HEIGHT;
			}
			invalidateDisplayList();
			return captionButton;
		}	
		
		public function addCaptionButtonAt(captionButton:UIComponent,index:int):UIComponent{
			_captionButtonHolder.addChildAt(captionButton, index);
			captionButton.width = HEADER_ELEMENT_HEIGHT;
			captionButton.height = HEADER_ELEMENT_HEIGHT;			
			invalidateDisplayList();
			return captionButton;
		}
		
		public function removeCaptionButton(captionButton:UIComponent):UIComponent{
			_captionButtonHolder.removeChild(captionButton);
			invalidateDisplayList();
			return captionButton;
		}
		
		public function removeCaptionButtonAt(index:int):UIComponent{
			var captionButton:UIComponent = _captionButtonHolder.removeChildAt(index) as UIComponent;
			if(captionButton != null){
				invalidateDisplayList();
			}
			return captionButton;
		}	
		
		override protected function layoutChrome(unscaledWidth:Number, unscaledHeight:Number):void{
			super.layoutChrome(unscaledWidth, unscaledHeight);
			
			//layout the caption button holder 
			_captionButtonHolder.setActualSize(_captionButtonHolder.getExplicitOrMeasuredWidth(), titleBar.height);
			_captionButtonHolder.move(titleBar.width - _captionButtonHolder.getExplicitOrMeasuredWidth() - getStyle("borderThicknessRight"), 0);

			//layout the title
			//Anand - 02/03/2012 we brute force the width (to be able to truncate large titles see BE-14332)  			
			titleTextField.width = unscaledWidth * .75;
			titleTextField.text = _containerTitle;
			//titleTextField.height =  unscaledHeight;//getStyle("headerHeight");
			titleTextField.x = getStyle("borderThicknessLeft") + 2;
			//titleTextField.move(getStyle("borderThicknessLeft") + 2, (titleBar.height - titleTextField.getExplicitOrMeasuredHeight())/2);
			titleTextField.toolTip = titleTextField.text;
			if(!titleTextField.truncateToFit()){
				titleTextField.toolTip = null;
			}
		}
		
		/**
		 * Adds a component to the container 
		 * @param component The component to be added
		 * @param widthWeightage The width (colspan) for the component
		 * @param heightWeightage The height (rowspan) for the component
		 */ 
		public function addComponent(component:BEVComponent, widthWeightage: Number = -1,heightWeightage: Number = -1):void{ }
		
		/**
		 * Adds a component to the container 
		 * @param component The component to be added
		 * @param widthWeightage The width (colspan) for the component
		 * @param heightWeightage The height (rowspan) for the component
		 */ 		
		public function removeComponent(component:BEVComponent):void{ }
		
		/**
		 * Adds a container to the container
		 * @param container The container to be added
		 * @param heightWeightage The height (span) for the container. This is the percentage value
		 * @param widthWeightage The width (span) for the container. This is the percentage value
		 */ 
		public function addContainer(container:IBEVContainer, heightWeightage: Number = 20,widthWeightage: Number = -1):void{ }
		
		/**
		 * Removes a container from the container
		 * @param container The container to be removed
		 */ 
		public function removeContainer(container:IBEVContainer):void{ }		
		
		/**
		 * Sets the menu configuration XML for the contianer 
		 * @param menuConfig The menu configuration/definition XML 
		 */ 
		public function setInteractions(actionConfig:XML, actionContextProvider:IActionContextProvider):void {
			if(actionConfig != null && actionConfig.actionconfig != undefined && titleBar != null){
				if(_interactionButton != null){
					removeCaptionButton(_interactionButton);
					_interactionButton = null;
				}
				var menuButton:BEVIconMenuButton = new BEVIconMenuButton(actionContextProvider);
				menuButton.enabled = true;
				menuButton.menuConfig = actionConfig;
				_interactionButton = addCaptionButton(menuButton);
			}
		}
		
	}
}