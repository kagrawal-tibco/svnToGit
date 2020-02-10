package com.tibco.be.views.user.components.processmodel{
	import mx.effects.Fade;
	import mx.effects.Parallel;
	
	
	public class PMUtils{
		
		public static const DEFAULT_EFFECT_DURATION:int = 1000;
		
		public function PMUtils(){
			
		}
		
		public static function buildDefaultParallel(duration:int=-1):Parallel{
			var parallel:Parallel = new Parallel();
			parallel.duration = duration > 0 ? duration:DEFAULT_EFFECT_DURATION;
			return parallel;
		}
		
		public static function buildDefaultFadeIn(target:Object):Fade{
			var fadeIn:Fade = new Fade(target);
			fadeIn.alphaFrom = 0;
			fadeIn.alphaTo = 1;
			return fadeIn;
		}
		
		public static function buildDefaultFadeOut(target:Object):Fade{
			var fadeOut:Fade = new Fade(target);
			fadeOut.alphaFrom = 1;
			fadeOut.alphaTo = 0;
			return fadeOut;
		}

	}
}