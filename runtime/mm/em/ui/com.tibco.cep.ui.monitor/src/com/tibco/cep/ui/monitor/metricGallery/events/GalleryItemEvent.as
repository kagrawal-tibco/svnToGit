package com.tibco.cep.ui.monitor.metricGallery.events
{
	import com.tibco.cep.ui.monitor.metricGallery.GalleryItem;
	
	import flash.events.Event;

	public class GalleryItemEvent extends Event
	{
		public static const RESTORE_METRIC:String = "restoreMetric";
		
		public var galleryItem:GalleryItem;
		
		public function GalleryItemEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false){
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event{
			return new GalleryItemEvent(type);
		}
		
	}
}