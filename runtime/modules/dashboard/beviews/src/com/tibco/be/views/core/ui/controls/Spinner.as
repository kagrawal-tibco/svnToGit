/*
http://grakl.com/flex/SpinnerApp/SpinnerApp.html
http://grakl.com/wordpress/?p=5
#
Bruce, 22. January 2009, 14:23

What open source license are you using, Jake? I’d like to use the spinner component (as is, or
maybe with some change) in a product for my company, and I need to know a bit more about the open
source license…
 
#
Jake, 22. January 2009, 15:59

Hey Bruce, I’m glad you found this useful.

This component is under the DWTFYW license, which, when paraphrased and censored, means, “Do
whatever you want”. You can use this code in whatever way you want to. You can use it without
restrictions, you can change it as much as you want, you can even leave it exactly the same and try
to sell it (though I doubt you’d have much success).

However, if you want to add a link to my blog in the comments at the top of Spinner.as, I’d
appreciate it. That way if I ever quit playing Team Fortress 2 and write another useful component
people will be able to find it more easily.
*/
package com.tibco.be.views.core.ui.controls{
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	
	
	[Style(name="tickColor",type="uint",format="Color",inherit="no")]
	/**
	 * Creates a spinning "loader" component that is sort of an indeterminate progress bar.
	 * @author jhawkes
	 * 
	 */
	public class Spinner extends UIComponent {
		private static var STYLE_TICK_COLOR:String = "tickColor";
		private var tickColorChanged:Boolean;
		
        private static var classConstructed:Boolean = classConstruct();
        
         // Make sure we create the ticks the first time updateDisplayList is called
        private var creation:Boolean = true;
        
        
		private var fadeTimer:Timer;
		private var _isPlaying:Boolean;
		
		private var _numTicks:int = 12;
		private var numTicksChanged:Boolean;
		
		private var _size:Number = 30;
		private var sizeChanged:Boolean;
		
		private var _tickWidth:Number = 3;
		private var tickWidthChanged:Boolean;
		
		private var _speed:int = 1000;
		[Bindable] public var fadeSpeed:int = 600;
		
		public var autoPlay:Boolean = true;
		
		
		public function Spinner() {
			super();
			
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		
		private function handleCreationComplete(e:FlexEvent):void {
			removeEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
			if (autoPlay) {
				play();
			}
		}
		
		
		/**
		 * Set the height and width based on the size of the spinner. This should be more robust, but oh well.
		 */
		override protected function measure():void {
			super.measure();
			
			width = _size;
			height = _size;
		}
		
		
		/**
		 * Override the updateDisplayList method
		 */
		 override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
		 	if (tickColorChanged || numTicksChanged || sizeChanged || tickWidthChanged || creation) {
		 		creation = false;
		 		// Find out whether it's playing so we can restart it later if we need to
				var wasPlaying:Boolean = _isPlaying;
				
				// stop the spinning
				stop();
				
				// Remove all children
				for (var i:int = numChildren - 1; i >= 0; i--) {
					removeChildAt(i);
				}
				
				// Re-create the children
				var radius:Number = size / 2;
				var angle:Number = 2 * Math.PI / _numTicks; // The angle between each tick
				var tickWidth:Number = (_tickWidth != -1) ? _tickWidth : size / 10;
				var tickColor:uint = getStyle(STYLE_TICK_COLOR);
				
				var currentAngle:Number = 0;
				for (var j:int = 0; j < _numTicks; j++) {
					
					var xStart:Number = radius + Math.sin(currentAngle) * ((_numTicks + 2) * tickWidth / 2 / Math.PI);
					var yStart:Number = radius - Math.cos(currentAngle) * ((_numTicks + 2) * tickWidth / 2 / Math.PI);
					var xEnd:Number = radius + Math.sin(currentAngle) * (radius - tickWidth);
					var yEnd:Number = radius - Math.cos(currentAngle) * (radius - tickWidth);
					
					var t:Tick = new Tick(xStart, yStart, xEnd, yEnd, tickWidth, tickColor);
						t.alpha = 0.1;
						
					this.addChild(t);
					
					currentAngle += angle;
				}
				
				// Start the spinning again if it was playing when this function was called.
				if (wasPlaying) {
					play();
				}
				
				tickColorChanged = false;
				numTicksChanged = false;
				sizeChanged = false;
				tickWidthChanged = false;
			}
		}
		 
		 
		private static function classConstruct():Boolean {
			if (!StyleManager.getStyleDeclaration("Spinner")) {
				// If there is no CSS definition for StyledRectangle, 
				// then create one and set the default value.
				var newStyleDeclaration:CSSStyleDeclaration = new CSSStyleDeclaration();
				newStyleDeclaration.setStyle(STYLE_TICK_COLOR, 0x000000);
				StyleManager.setStyleDeclaration("Spinner", newStyleDeclaration, true);
			}
			return true;
		}
		
		override public function styleChanged(styleProp:String):void {
			if (styleProp == STYLE_TICK_COLOR) {
				tickColorChanged = true;
				invalidateDisplayList();
			}
		}

		
		/**
		 * Begin the circular fading of the ticks.
		 */
		public function play():void {
			if (! _isPlaying) {
				fadeTimer = new Timer(speed / _numTicks, 0);
				// Anonymous functions are especially useful as simple event handlers
				fadeTimer.addEventListener(TimerEvent.TIMER, function (e:TimerEvent):void {
					var tickNum:int = int(fadeTimer.currentCount % _numTicks);
					
					if (numChildren > tickNum) {
						var tick:Tick = getChildAt(tickNum) as Tick;
							tick.fade(fadeSpeed != 1 ? fadeSpeed : speed * 6 / 10);
					}
				});
				fadeTimer.start();
				_isPlaying = true;
			}
		}
		
		/**
		 * Stop the spinning.
		 */
		public function stop():void {
			if (fadeTimer != null && fadeTimer.running) {
				_isPlaying = false;
				fadeTimer.stop();
			}
		}
		
		/**
		 * The overall diameter of the spinner; also the height and width.
		 */
		[Bindable]
		public function set size(value:Number):void {
			if (value != _size) {
				_size = value;
				sizeChanged = true;
				invalidateDisplayList();
				invalidateSize();
			}
		}
		
		public function get size():Number {
			return _size;
		}
		
		/**
		 * The number of 'spokes' on the spinner.
		 */
		[Bindable]
		public function set numTicks(value:int):void {
			if (value != _numTicks) {
				_numTicks = value;
				numTicksChanged = true;
				invalidateDisplayList();
			}
		}
		
		public function get numTicks():int {
			return _numTicks;
		}
		
		/**
		 * The width of the 'spokes' on the spinner.
		 */
		[Bindable]
		public function set tickWidth(value:int):void {
			if (value != _tickWidth) {
				_tickWidth = value;
				tickWidthChanged = true;
				invalidateDisplayList();
			}
		}
		
		public function get tickWidth():int {
			return _tickWidth;
		}
		
		/**
		 * The duration (in milliseconds) that it takes for the spinner to make one revolution.
		 */
		[Bindable]
		public function set speed(value:int):void {
			if (value != _speed) {
				_speed = value;
				
				if (fadeTimer != null) {
					fadeTimer.stop();
					fadeTimer.delay = value / _numTicks;
					fadeTimer.start();
				}
			}
		}
		
		public function get speed():int {
			return _speed;
		}
		
		
		public function get isPlaying():Boolean {
			return _isPlaying;
		}
	}
}