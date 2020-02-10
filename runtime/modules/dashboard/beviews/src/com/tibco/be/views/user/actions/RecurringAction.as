package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	
	import flash.events.Event;
	import flash.events.TimerEvent;
	import flash.utils.Timer;

	public class RecurringAction extends AbstractAction implements IStoppableAction{
		
		private var _workerAction:AbstractAction;
		private var _actionContext:ActionContext;
		private var _timer:Timer;
		
		public function RecurringAction(){
			registerAction(CommandTypes.RECURRING);
		}
		
		override protected function createNewInstance():AbstractAction{
			return new RecurringAction();
		}
		
		override public function execute(actionContext:ActionContext):void{
			if(_actionConfig.actionconfig != undefined){
				_workerAction = ActionRegistry.instance.getAction(_actionConfig.actionconfig[0]);
				if(_workerAction != null){
					_actionContext = actionContext;
					var period:Number = new Number(_configParams["period"]);
					if(_timer != null){
						_timer.stop();
						_timer = null;
					}
					_timer = new Timer(period);
					_timer.addEventListener(TimerEvent.TIMER, handleTimerEvent);
					_timer.start();
				}
			}
		}
		
		public function handleTimerEvent(event:TimerEvent):void	{
			if(_workerAction != null){
				_workerAction.execute(_actionContext);
			}
		}
		
		public function stop():void{
			if(_timer != null){
				_timer.removeEventListener(TimerEvent.TIMER, handleTimerEvent);
				_timer.stop();
				_timer = null;
			}
			_workerAction = null;
			_actionContext = null;
		}
		
		public function shouldStopOnEvent(event:Event):Boolean{
			return false;
		}		
		
	}
}