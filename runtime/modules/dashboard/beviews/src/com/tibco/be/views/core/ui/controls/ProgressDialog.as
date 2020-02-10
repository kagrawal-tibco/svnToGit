package com.tibco.be.views.core.ui.controls{
	
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.utils.IProgressListener;
	
	import flash.display.DisplayObjectContainer;
	import flash.filters.BlurFilter;
	
	import mx.containers.Panel;
	import mx.controls.ProgressBar;
	import mx.controls.ProgressBarMode;
	import mx.core.ScrollPolicy;
	import mx.effects.Fade;
	import mx.events.EffectEvent;
	import mx.managers.PopUpManager;

	public final class ProgressDialog implements IProgressListener{
		
		public static function show(parent:DisplayObjectContainer, title:String, backgroundFunction:Function):ProgressDialog{
			var pgDlg:ProgressDialog = new ProgressDialog(parent);
			pgDlg._panel.title = title;
			
			PopUpManager.addPopUp(pgDlg._panel,parent,true);
			PopUpManager.centerPopUp(pgDlg._panel);
			pgDlg._backgroundFunction = backgroundFunction;
			pgDlg._fadeInEffect.addEventListener(EffectEvent.EFFECT_END,pgDlg.progressDlgFadeInHandler);
			pgDlg._fadeInEffect.play();
			return pgDlg;
		}
		
		private var _parent:DisplayObjectContainer;
		private var _fadeInEffect:Fade;
		private var _fadeOutEffect:Fade;
		private var _backgroundFunction:Function;
		private var _closeFunction:Function;
		private var _panel:Panel;
		private var _progressBar:ProgressBar;
		private var _mainTaskName:String;
		private var _tasks:Array;
		private var _mainTask:Object;
		private var _currentTask:Object;
		private var _subTaskScale:Number;
		
		function ProgressDialog(parent:DisplayObjectContainer):void{
			_parent = parent;
			
			_panel = new Panel();
			_panel.layout = "horizontal";
			_panel.width = 400;
			//panel.height = 70;
			_panel.verticalScrollPolicy = ScrollPolicy.OFF;
			_panel.setStyle("verticalAlign","middle");
			_panel.title = "Progress...";
			_panel.setStyle("fontFamily", "Arial");
			_panel.setStyle("borderThicknessBottom",0);
			_panel.setStyle("borderThicknessLeft",0);
			_panel.setStyle("borderThicknessRight",0);
			_panel.setStyle("borderThicknessTop",0);
			_panel.setStyle("backgroundColor",0xFFFFFF);
			_panel.setStyle("headerColors",[0xCACACA, 0x808080]);
			_panel.setStyle("highlightAlphas",[0.16, 0.0]);
			_panel.setStyle("horizontalGap",0);
			_panel.setStyle("paddingLeft",0);
			_panel.setStyle("paddingRight",0);
			_panel.setStyle("paddingBottom",20);
			_panel.setStyle("headerHeight",20);
			_panel.setStyle("dropShadowEnabled",true);
			//we use a filter enable effects on contained text while avoidint embeded fonts
			_panel.filters = [new BlurFilter(0,0)]
			
			_progressBar = new ProgressBar();
			_progressBar.percentWidth = 100;
			//progressBar.height = 45;
			_progressBar.indeterminate = true;
			_progressBar.setStyle("trackHeight",15);  
			_progressBar.setStyle("labelWidth",350);
			_progressBar.setStyle("textIndent",10);
			_progressBar.label = "";
			_panel.addChild(_progressBar);
			
			_tasks = new Array();
			
			_fadeInEffect = new Fade(this._panel);
			_fadeInEffect.alphaFrom = 0.0;
			_fadeInEffect.alphaTo = 1.0;
			_fadeInEffect.duration = 500;
			
			_fadeOutEffect = new Fade(this._panel);
			_fadeOutEffect.alphaFrom = 1.0;
			_fadeOutEffect.alphaTo = 0.0;
			_fadeOutEffect.duration = 500;	
			
		}
		
		private function progressDlgFadeInHandler(event:EffectEvent):void{
			_fadeInEffect.removeEventListener(EffectEvent.EFFECT_END, progressDlgFadeInHandler);
			_backgroundFunction();
		}
		
		private function progressDlgFadeOutHandler(event:EffectEvent):void{
			_fadeOutEffect.removeEventListener(EffectEvent.EFFECT_END, progressDlgFadeOutHandler);
			PopUpManager.removePopUp(this._panel);
			if(_closeFunction != null){
				_closeFunction();
			}
		}

		public function startMainTask(taskName:String, taskUnits:Number = -1):void{
			this._mainTaskName = taskName;
			_progressBar.label = _mainTaskName;
			if(taskUnits == -1){
				_progressBar.indeterminate = true;
			}
			else{
				_progressBar.indeterminate = false;
				_progressBar.mode = ProgressBarMode.MANUAL;
				_progressBar.minimum = 0;
				_progressBar.maximum = 100;
				_subTaskScale = 100/taskUnits;
			}
			_mainTask ={"name":taskName, "units":taskUnits, "increment":_subTaskScale};
			_currentTask = _mainTask;
		}
		
		public function startSubTask(taskName:String, taskUnits:Number = -1):void	{
			_progressBar.label = _mainTaskName+" - "+taskName;
			_currentTask ={"name":taskName, "units":taskUnits};
			if(taskUnits != -1){
				_currentTask["increment"] = _subTaskScale/taskUnits;
			}
			_tasks.push(_currentTask);
		}
		
		public function worked(taskUnits:Number):Boolean{
			if(_currentTask["units"] < 0){
				return true;
			}
			//check if the taskUnits arg is more then the current task's units
			if(_currentTask["units"] <= taskUnits){
				//we will complete the task
				if(_currentTask == _mainTask){
					completedMainTask();
				}
				else{
					completedSubTask();
				}
				return true;
			}
			else{
				_currentTask["units"] = _currentTask["units"] - taskUnits;
				var newValue:int = _progressBar.value + (taskUnits * _currentTask["increment"]);
				_progressBar.setProgress(newValue,100);
				return false;
			}
		}
		
		public function errored(errMsg:String, errorEvent:ServerResponseEvent):void{
			_progressBar.label = _mainTaskName+" - Errored";
			_progressBar.setProgress(100,100);
		}
		
		public function completedSubTask():void{
			var newValue:int = _progressBar.value + (_currentTask["units"] * _currentTask["increment"]);
			_progressBar.setProgress(newValue,100);
			_currentTask["units"] = 0;
			//pop out the current task
			_tasks.pop();
			//make the previous task current task
			_currentTask = _tasks.pop();
			_mainTask["units"]--;
			if(_mainTask["units"] == 0){
				completedMainTask();
			}
		}
		
		public function completedMainTask():void{
			_progressBar.label = _mainTaskName+" - Completed";
			_progressBar.setProgress(100,100);
			_currentTask = _mainTask; 			
		}
		
		public function hide(hideHandler:Function = null):void{
			_closeFunction = hideHandler;
			_fadeOutEffect.addEventListener(EffectEvent.EFFECT_END,progressDlgFadeOutHandler);
			_fadeOutEffect.play();
		}		
		
	}
}