package com.tibco.be.views.user.components.table.renderers{
	
	import mx.controls.dataGridClasses.DataGridItemRenderer;
	import mx.core.ClassFactory;

	//TODO we should rename this class to BEVColumnHeaderRendererFactory 
	public class BEVColumnHeaderRendererFactory extends ClassFactory {
		
		private var _textAlign:String;
		private var _fontSize:Number;
		private var _color:uint;
		private var _fontStyle:String;
		private var _fontWeight:String;
		private var _tooltip:String;
		
		public function BEVColumnHeaderRendererFactory(config:XML, data:XML){
			super();
			_textAlign = new String(config.@align);
			_color = parseInt(config.@fontcolor, 16);
			_fontSize = new Number(config.@fontsize);
			_fontWeight = (config.@fontstyle.indexOf("bold") != -1) ? "bold":"normal";
			_fontStyle = (config.@fontstyle.indexOf("italic") != -1) ? "italic":"normal";
			_tooltip = new String(data.tooltip);	
		}
		
		override public function newInstance():*{
			var renderer:DataGridItemRenderer = new CustomDataGridItemRenderer(_tooltip);
			renderer.setStyle("textAlign", _textAlign);
			renderer.setStyle("color", _color);
			renderer.setStyle("fontSize", _fontSize);
			renderer.setStyle("fontWeight", _fontWeight);
			renderer.setStyle("fontStyle", _fontStyle);
			return renderer;
		}
		
	}
	
}

import mx.controls.dataGridClasses.DataGridItemRenderer;

internal class CustomDataGridItemRenderer extends DataGridItemRenderer {
	
	private var _tooltip:String;
	
	public function CustomDataGridItemRenderer(tooltip:String) {
		super();
		_tooltip = tooltip;
	}
	
	override public function validateProperties():void {
		super.validateProperties();
		if (truncateToFit() == true) {
			super.toolTip = _tooltip;	
		}	
	}
	
}