package com.tibco.be.views.ui{

	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.geom.Rectangle;
	import flash.text.TextLineMetrics;
	
	import mx.controls.Label;
	import mx.core.IDataRenderer;
	import mx.core.UIComponent;

	public class BitmapLabel extends UIComponent implements IDataRenderer{
		
		public static const TOP_LEFT_ANCHOR:int = 0;
		public static const TOP_CENTER_ANCHOR:int = 1;
		public static const TOP_RIGHT_ANCHOR:int = 2;
		public static const MIDDLE_LEFT_ANCHOR:int = 3;
		public static const MIDDLE_CENTER_ANCHOR:int = 4;
		public static const MIDDLE_RIGHT_ANCHOR:int = 5;
		public static const BOTTOM_LEFT_ANCHOR:int = 6;
		public static const BOTTOM_CENTER_ANCHOR:int = 7;
		public static const BOTTOM_RIGHT_ANCHOR:int = 8;
		
		private var _label:Label;
		private var _labelBitmap:Bitmap;
		private var _labelBitmapData:BitmapData;
		private var _container:UIComponent;
		private var _labelRotation:Number;
		private var _drawLabelNeeded:Boolean;
		private var _rotationAnchorPosition:int;
		private var _incrementXYOnRotation:Boolean;
		
		public function BitmapLabel(){
			super();
			_label = new Label();
			_labelRotation = 0;
			_drawLabelNeeded = true;
			_rotationAnchorPosition = MIDDLE_CENTER_ANCHOR;
			_incrementXYOnRotation = false;
		}
		
		override public function get rotation():Number{ return _labelRotation; }
		public function get text():String{ return _label.text; }
		public function get data():Object{ return _label.text; }
		public function get incrementXYOnRotation():Boolean{ return _incrementXYOnRotation; }		
		
		override public function set styleName(value:Object):void{ _label.styleName = value; }
		public function set data(value:Object):void{ text = new String(value); }
		public function set rotationAnchorPosistion(value:int):void{
			_rotationAnchorPosition = (value < TOP_LEFT_ANCHOR || value > BOTTOM_RIGHT_ANCHOR) ? MIDDLE_CENTER_ANCHOR:value;
		}
		public function set incrementXYOnRotation(value:Boolean):void{ _incrementXYOnRotation = value; }
		public function set textAlign(value:String):void{ _label.setStyle("textAlign", value); }
		public function set fontFamily(value:String):void{ _label.setStyle("fontFamily", value); }
		public function set fontWeight(value:Object):void{ _label.setStyle("fontWeight", value); }
		public function set fontSize(value:Object):void{ _label.setStyle("fontSize", value); }
		public function set color(value:Object):void{ _label.setStyle("color", value); }
    	public function set selectable(value:Boolean):void{ _label.selectable = value; }
    	public function set truncateToFit(value:Boolean):void{ _label.truncateToFit = value; }
    	override public function set maxWidth(value:Number):void{
    		super.maxWidth = value;
    		_label.maxWidth = value;
    	}
		public function set text(value:String):void{
			if(value != _label.text){	
				_label.text = value;			
				_drawLabelNeeded = true;
			}
		}
		override public function set rotation(value:Number):void{
			value = value % 360;
			if(value < 0){ value += 360; }
			_labelRotation = value;
			switch(_rotationAnchorPosition){
				case(TOP_LEFT_ANCHOR):
					//defulat
					break;
				case(TOP_CENTER_ANCHOR):
					rotateAboutCenter(value, 0);
					break;
				case(MIDDLE_CENTER_ANCHOR):
					rotateAboutCenter(value, height/2);
					break;
				case(BOTTOM_CENTER_ANCHOR):
					rotateAboutCenter(value, height);
					break;
				default:
					//some cases not yet implemented
					break;
			}	
			invalidateDisplayList();
		}
		
		override public function setStyle(styleProp:String, newValue:*):void{ _label.setStyle(styleProp, newValue); }
		
		override protected function createChildren():void{
			super.createChildren();
			if(_container == null){
				_container = new UIComponent();
				addChild(_container);
				addChild(_label);
			}
			_labelBitmap = new Bitmap();
			_container.addChild(_labelBitmap);
		}
		
		override protected function measure():void{
			var t:String = _label.text; 
        
	        t = getMinimumText(t);
	
	        // Determine how large the textField would need to be
	        // to display the entire text.
	        var textFieldBounds:Rectangle = measureTextFieldBounds(t);
	
	        // Add in the padding.
	        measuredMinWidth = measuredWidth = textFieldBounds.width +
	            getStyle("paddingLeft") + getStyle("paddingRight");
	        measuredMinHeight = measuredHeight = textFieldBounds.height +
	            getStyle("paddingTop") + getStyle("paddingBottom");
	        
	        _label.measuredMinWidth = _label.measuredWidth = measuredWidth;
	        _label.measuredMinHeight = _label.measuredHeight = measuredHeight;
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			if(parent == null || unscaledWidth == 0 || unscaledHeight == 0){ return; }
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			_label.width = Math.min(_label.maxWidth, unscaledWidth);
			_label.height = unscaledHeight;
			if(_label.truncateToFit){
				_container.toolTip = _label.text;
				if(!truncateLabel()){
					_container.toolTip = null;
				}
				if(_labelRotation == 0){
					//this and _container currently separate for some reason with rotation resulting
					//in this capturing mouse over and no tip shown unless you do the below.
					//TODO: fix so that this and container are kept at the same coordinates
					this.toolTip = _container.toolTip;
				}
			}
			_label.validateNow();
			if(_drawLabelNeeded){ createBitmap(); }
			_drawLabelNeeded = false;
			_labelBitmap.rotation = _labelRotation;
//			_labelBitmap.opaqueBackground = new SolidColor(0);
		}
		
		private function createBitmap():void{
			if(_labelBitmapData != null){
				_labelBitmapData.dispose();
				if(_container.contains(_labelBitmap)){
					_container.removeChild(_labelBitmap);
				}
			}
			_labelBitmapData = new BitmapData(_label.width, _label.height);
			_labelBitmapData.fillRect(new Rectangle(0, 0, _label.width, _label.height), 0);
			_labelBitmapData.draw(_label);
			_labelBitmap.bitmapData = _labelBitmapData;
			_labelBitmap.smoothing = false;
			_label.text = "";
		}
		
		private function measureTextFieldBounds(s:String):Rectangle{
	        var lineMetrics:TextLineMetrics = measureText(_label.text);
	        return new Rectangle(0, 0, lineMetrics.width + 5, lineMetrics.height + 4);
	    }
	    
	    private function getMinimumText(t:String):String{
	        if(!t || t.length < 2){ t = "Wj"; }
	        return t;   
	    }
	    
	    private function truncateLabel(truncationIndicator:String = null):Boolean{
	        if(!truncationIndicator){
	            truncationIndicator = "...";
	        }
	        validateNow();
	        var originalText:String = _label.text;
	        var txt:TextLineMetrics = measureText(originalText);
	        var w:Number = _label.width;
	        if(originalText != "" && txt.width + 5 > w + 0.00000000000001){
	            var s:String = originalText;
	            originalText.slice(0, Math.floor((w / (txt.width + 5)) * originalText.length));
	            while(s.length > 1 && textWidth(_label.text) + 5 > w){
	                s = s.slice(0, -1);
	                _label.text = s + truncationIndicator;
	            }
	            return true;
	        }
	        return false;
	    }
	    
	    private function textWidth(text:String):Number{
	    	return measureText(text).width;
	    }
		
		private function rotateAboutCenter(rotation:Number, heightCorrection:Number):void{
			var w2:Number = width/2;
			var radians:Number = rotation*Math.PI/180;
			radians += Math.atan(heightCorrection/w2);
			if(_incrementXYOnRotation){ //hack for AxisRenderer labels
				if(rotation >= 0 && rotation < 90){
					x += w2 - w2*Math.cos(radians);
					y += -w2*Math.sin(radians);
				}
				else if(rotation <= 180){
					radians -= Math.PI/2;
					x += w2 + w2*Math.sin(radians);
					y += -w2*Math.cos(radians);
				}
				else if(rotation <= 270){
					radians -= Math.PI
					x += w2 + w2*Math.cos(radians);
					y += w2*Math.sin(radians);
				}
				else{
					radians -= 3*Math.PI/2
					x += w2 - w2*Math.sin(radians);
					y += w2*Math.cos(radians);
				}
			}
			else{
				if(rotation >= 0 && rotation < 90){
					x = w2 - w2*Math.cos(radians);
					y = -w2*Math.sin(radians);
				}
				else if(rotation <= 180){
					radians -= Math.PI/2;
					x = w2 + w2*Math.sin(radians);
					y = -w2*Math.cos(radians);
				}
				else if(rotation <= 270){
					radians -= Math.PI
					x = w2 + w2*Math.cos(radians);
					y = w2*Math.sin(radians);
				}
				else{
					radians -= 3*Math.PI/2
					x = w2 - w2*Math.sin(radians);
					y = w2*Math.cos(radians);
				}
			}
			y+=heightCorrection;
		}
		
	}
}