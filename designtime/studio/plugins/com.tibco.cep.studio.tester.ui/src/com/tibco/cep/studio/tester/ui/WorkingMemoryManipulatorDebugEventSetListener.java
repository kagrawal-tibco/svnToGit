package com.tibco.cep.studio.tester.ui;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;
import com.tibco.cep.studio.debug.input.WorkingMemoryManipulatorInputTask;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;

/**
 * 
 * @author smarathe
 *
 */
public class WorkingMemoryManipulatorDebugEventSetListener implements IDebugEventSetListener{

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		if (events == null || events.length == 0) {
			return;
		}
		DebugEvent event = events[0];
		Object source = event.getSource();
		if (source instanceof IRuleRunTarget) {
			final IRuleRunTarget runTarget = (IRuleRunTarget)source;
			if (event.getKind() == DebugEvent.MODEL_SPECIFIC) {
				if(event.getDetail() == IRuleRunTarget.DEBUG_TASK_REPONSE) {
					//Then do further processing
					try {
						Object data = event.getData();
						if(data instanceof WorkingMemoryManipulatorInputTask) {
							int numberOfObjects = StudioUIPlugin.getDefault().
							getPreferenceStore().getInt(StudioUIPreferenceConstants.WM_OBJCECTS_SHOW_MAX_NO);
							WMContentsInputTask inputTask = new WMContentsInputTask(runTarget, ((WorkingMemoryManipulatorInputTask)data).getRuleSessionName(), numberOfObjects);
							runTarget.addInputVmTask(inputTask);
						} 
					}catch(Exception e) {
					}
				}
			}
		}
	}
}
