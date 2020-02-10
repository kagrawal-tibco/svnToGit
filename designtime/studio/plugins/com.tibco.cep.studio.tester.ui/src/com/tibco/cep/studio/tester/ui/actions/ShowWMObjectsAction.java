package com.tibco.cep.studio.tester.ui.actions;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class ShowWMObjectsAction extends Action {

	private String ruleSessionName;
	@SuppressWarnings("unused")
	private IWorkbenchWindow window; 
	
	private IRuleRunTarget runTarget;
	@SuppressWarnings("unused")
	private String uri;
	
	/**
	 * @param window
	 * @param runTarget
	 * @param name
	 * @param session
	 * @param uri
	 */
	public ShowWMObjectsAction(IWorkbenchWindow window, 
			                   IRuleRunTarget runTarget, 
			                   String name, 
			                   String session, 
			                   String uri) {
		super(session, AS_PUSH_BUTTON);
		if (!name.equalsIgnoreCase(Messages.getString("tester.no.sessions"))) {
			setImageDescriptor(StudioTesterUIPlugin.getImageDescriptor("icons/launch_rule.gif"));
			setText(session);
			setToolTipText(uri);
		} else {
			setText(name);
			setToolTipText(name);
		}
		this.window = window;
		this.runTarget = runTarget;
		this.ruleSessionName = session;
		this.uri = uri;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			int numberOfObjects = StudioUIPlugin.getDefault().
			getPreferenceStore().getInt(StudioUIPreferenceConstants.WM_OBJCECTS_SHOW_MAX_NO);
			WMContentsInputTask inputTask = new WMContentsInputTask(runTarget, ruleSessionName, numberOfObjects);
			runTarget.addInputVmTask(inputTask);
		} catch (DebugException e) {
			StudioTesterUIPlugin.log(e);
		}
	}
}