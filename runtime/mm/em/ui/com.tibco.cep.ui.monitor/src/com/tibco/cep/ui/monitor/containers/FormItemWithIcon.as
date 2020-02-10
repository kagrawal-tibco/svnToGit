package com.tibco.cep.ui.monitor.containers {
	import mx.binding.utils.BindingUtils;
	import mx.containers.FormItem;
	import mx.controls.Image;
	import mx.utils.StringUtil;

	public class FormItemWithIcon extends FormItem
	{
		private var _image:Image;
		private var _label:String;
		private var _imageSource:Class;
		
		public function FormItemWithIcon()
		{
			super();
		}
		
	 	private function createImage():void {
	 		_image = new Image();
			_image.width = 16;
			_image.height = 16;
			//again I've hardcoded these values for simplicity
			//You could if you wanted to create a versatile custom component load these values in from a CSS file
			_image.setStyle('verticalCenter', 0 );
			_image.setStyle('left', 5 );
			_image.source = _imageSource;
	 	}
		
		protected override function createChildren():void {
		    super.createChildren();
		    createImage();
			this.rawChildren.addChild(_image);
			//bind the string property to the image source property.
			BindingUtils.bindProperty(_image, 'source', this, 'imageSource');
		} //createChildren
		
 
		[Bindable]
		public function get imageSource() : Class {
			return _imageSource;
		} 
 
		//Sets the imageSource. I have added a number of spaces at the start to offset the 
		//width of the image.
		//The overall form width will be calculated from the width of the label (this is done inside the FormItem) 
		public function set imageSource(iconClass:Class) : void {
		  _imageSource = iconClass;
		  if(_imageSource != null){//setting the label (not using _label) will resize the form/formItems
		  	//add spaces to the trimed version to make sure you don't end up with 100's of spaces at the start.
			label = "   " + StringUtil.trim(_label);
		  } else {
				label = StringUtil.trim(_label);
		  }
		} //imageSource	
		
		//Sets the label.
		//If the imageSource has been set already then this will add spaces to the label
		public override function set label(str : String) : void {
			_label = str;
			if(_imageSource != null){
				_label = "   " + str;
			}
			else {
				_label = StringUtil.trim(str);
		  }
		  // call the super last, this will also force the reusing of the formItem and Form
		  super.label = _label;
		}
		
	} //class
} //package