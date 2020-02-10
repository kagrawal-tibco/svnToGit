package com.tibco.cep.studio.debug.ui.prefs;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferenceLinkArea;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.launch.ApplicationRuntime;
import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.ui.SWTFactory;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
@SuppressWarnings({"unused"})
public class StudioDebugPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage,IPropertyChangeListener  {

	/**
	 * This class exists to provide visibility to the
	 * <code>refreshValidState</code> method and to perform more intelligent
	 * clearing of the error message.
	 */
	protected class StudioDebugIntegerFieldEditor extends IntegerFieldEditor {						
		
		public StudioDebugIntegerFieldEditor(String name, String labelText, Composite parent) {
			super(name, labelText, parent);
		}

		protected void refreshValidState() {
			super.refreshValidState();
		}

		protected void clearErrorMessage() {
			if (canClearErrorMessage()) {
				super.clearErrorMessage();
			}
		}
	}
	
	// Suspend preference widgets
//	private Button fSuspendButton;
//	private Button fSuspendOnCompilationErrors;
//	private Button fSuspendDuringEvaluations;
//	private Button fOpenInspector;
//	private Button fPromptUnableToInstallBreakpoint;
	private Combo fSuspendVMorThread;
//	private Combo fWatchpoint;
	
	// Hot code replace preference widgets
//	private Button fAlertHCRButton;
//	private Button fAlertHCRNotSupportedButton;
//	private Button fAlertObsoleteButton;
//	private Button fPerformHCRWithCompilationErrors;
	// Timeout preference widgets
	private StudioDebugIntegerFieldEditor fTimeoutText;
	private StudioDebugIntegerFieldEditor fConnectionTimeoutText;

	public StudioDebugPreferencePage() {
		super();
		setPreferenceStore(StudioDebugUIPlugin.getDefault().getPreferenceStore());
		setDescription("General settings for Rule Debugging."); 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IJavaDebugHelpContextIds.JAVA_DEBUG_PREFERENCE_PAGE);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		//The main composite
		Composite composite = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, 0, 0, GridData.FILL);
		PreferenceLinkArea link = new PreferenceLinkArea(composite, SWT.NONE,
				"org.eclipse.debug.ui.DebugPreferencePage", "See <a>''{0}''</a> for general debug settings.", //$NON-NLS-1$
				(IWorkbenchPreferenceContainer) getContainer(),null);
		link.getControl().setFont(composite.getFont());
		Group group = SWTFactory.createGroup(composite, "Suspend Execution", 2, 1, GridData.FILL_HORIZONTAL); 
//		fSuspendButton = SWTFactory.createCheckButton(group, "Suspend &execution on uncaught exceptions", null, false, 2); 
//		fSuspendOnCompilationErrors = SWTFactory.createCheckButton(group, "Suspend execution on co&mpilation errors", null, false, 2); 
//		fSuspendDuringEvaluations = SWTFactory.createCheckButton(group,"Suspend for &breakpoints during evaluations", null, false, 2);
//		fOpenInspector = SWTFactory.createCheckButton(group, "Open popup when suspended on e&xception", null, false, 2);
		
		SWTFactory.createLabel(group, "Default suspend policy for new brea&kpoints:", 1);
		fSuspendVMorThread = new Combo(group, SWT.BORDER|SWT.READ_ONLY);
		fSuspendVMorThread.setItems(new String[]{"Suspend Thread", "Suspend VM"});
		fSuspendVMorThread.setFont(group.getFont());
		
//		SWTFactory.createLabel(group, "Default suspend policy for new &watchpoints:", 1);
//		fWatchpoint = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
//		fWatchpoint.setItems(new String[] {"Access & Modification", "Access", "Modification"});
//		fWatchpoint.setFont(group.getFont());
			
//		group = SWTFactory.createGroup(composite, "Hot Code Replace", 1, 1, GridData.FILL_HORIZONTAL);
//		fAlertHCRButton = SWTFactory.createCheckButton(group, "Show error when hot code replace &fails", null, false, 1); 
//		fAlertHCRNotSupportedButton = SWTFactory.createCheckButton(group, "Show error when hot code replace is not &supported", null, false, 1); 
//		fAlertObsoleteButton = SWTFactory.createCheckButton(group, "Show error when &obsolete methods remain after hot code replace", null, false, 1); 
//		fPerformHCRWithCompilationErrors = SWTFactory.createCheckButton(group, "Replace &classfiles containing compilation errors", null, false, 1);  

		group = SWTFactory.createGroup(composite, "Communication", 1, 1, GridData.FILL_HORIZONTAL);
		Composite space = SWTFactory.createComposite(group, group.getFont(), 1, 1, GridData.FILL_HORIZONTAL);
		int minValue;
        IEclipsePreferences coreStore= RuleDebugModel.getPreferences();
        IEclipsePreferences runtimeStore= ApplicationRuntime.getPreferences();
		fTimeoutText = new StudioDebugIntegerFieldEditor(RuleDebugModel.PREF_REQUEST_TIMEOUT, "Debugger &timeout (ms):", space);
		fTimeoutText.setPage(this);
		fTimeoutText.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		minValue= coreStore.getInt(RuleDebugModel.PREF_REQUEST_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT);
		fTimeoutText.setValidRange(minValue, Integer.MAX_VALUE);
		fTimeoutText.setErrorMessage(MessageFormat.format("Value must be a valid integer greater than or equal to {0} ms", new Object[] {new Integer(minValue)})); 
		fTimeoutText.load();
		fConnectionTimeoutText = new StudioDebugIntegerFieldEditor(ApplicationRuntime.PREF_CONNECT_TIMEOUT, "&Launch timeout (ms):", space); 
		fConnectionTimeoutText.setPage(this);
		fConnectionTimeoutText.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		minValue= runtimeStore.getInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT);
		fConnectionTimeoutText.setValidRange(minValue, Integer.MAX_VALUE);
		fConnectionTimeoutText.setErrorMessage(MessageFormat.format("Value must be a valid integer greater than or equal to {0} ms", new Object[] {new Integer(minValue)})); 
		fConnectionTimeoutText.load();
		
		SWTFactory.createVerticalSpacer(composite, 1);
//		fPromptUnableToInstallBreakpoint = SWTFactory.createCheckButton(composite, "Wa&rn when unable to install breakpoint due to missing line number attributes", null, false, 1);
		
		setValues();
		fTimeoutText.setPropertyChangeListener(this);
		fConnectionTimeoutText.setPropertyChangeListener(this);
		return composite;		
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		IEclipsePreferences coreStore = RuleDebugModel.getPreferences();
		IEclipsePreferences runtimeStore = ApplicationRuntime.getPreferences();
		
//		store.setValue(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_UNCAUGHT_EXCEPTIONS, fSuspendButton.getSelection());
//		store.setValue(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_COMPILATION_ERRORS, fSuspendOnCompilationErrors.getSelection());
//		coreStore.setValue(RuleDebugModel.PREF_SUSPEND_FOR_BREAKPOINTS_DURING_EVALUATION, fSuspendDuringEvaluations.getSelection());
		int selectionIndex = fSuspendVMorThread.getSelectionIndex();
		int policy = RuleBreakpoint.SUSPEND_THREAD;
		if (selectionIndex > 0) {
			policy = RuleBreakpoint.SUSPEND_VM;
		}
		coreStore.putInt(StudioDebugCorePlugin.PREF_DEFAULT_BREAKPOINT_SUSPEND_POLICY, policy);
//		coreStore.setValue(StudioDebugCorePlugin.PREF_DEFAULT_WATCHPOINT_SUSPEND_POLICY, fWatchpoint.getSelectionIndex());
//		store.setValue(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_FAILED, fAlertHCRButton.getSelection());
//		store.setValue(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_NOT_SUPPORTED, fAlertHCRNotSupportedButton.getSelection());
//		store.setValue(IStudioDebugPreferencesConstants.PREF_ALERT_OBSOLETE_METHODS, fAlertObsoleteButton.getSelection());
//		coreStore.setValue(RuleDebugModel.PREF_HCR_WITH_COMPILATION_ERRORS, fPerformHCRWithCompilationErrors.getSelection());
		coreStore.putInt(RuleDebugModel.PREF_REQUEST_TIMEOUT, fTimeoutText.getIntValue());
		runtimeStore.putInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT, fConnectionTimeoutText.getIntValue());
//		store.setValue(IStudioDebugPreferencesConstants.PREF_ALERT_UNABLE_TO_INSTALL_BREAKPOINT, fPromptUnableToInstallBreakpoint.getSelection());
//		store.setValue(IStudioDebugPreferencesConstants.PREF_OPEN_INSPECT_POPUP_ON_EXCEPTION, fOpenInspector.getSelection());
		try {
			RuleDebugModel.savePreferences();
		} catch (BackingStoreException e) {
			StudioDebugUIPlugin.log(e);
		}
		ApplicationRuntime.savePreferences();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		IPreferenceStore store = getPreferenceStore();
		IEclipsePreferences coreStore= RuleDebugModel.getPreferences();
		IEclipsePreferences runtimeStore= ApplicationRuntime.getPreferences();
		
//		fSuspendButton.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_UNCAUGHT_EXCEPTIONS));
//		fSuspendOnCompilationErrors.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_COMPILATION_ERRORS));
//		fSuspendDuringEvaluations.setSelection(coreStore.getDefaultBoolean(RuleDebugModel.PREF_SUSPEND_FOR_BREAKPOINTS_DURING_EVALUATION));
		int value = coreStore.getInt(StudioDebugCorePlugin.PREF_DEFAULT_BREAKPOINT_SUSPEND_POLICY,RuleBreakpoint.SUSPEND_THREAD);
		fSuspendVMorThread.select((value == RuleBreakpoint.SUSPEND_THREAD) ? RuleBreakpoint.SUSPEND_THREAD : RuleBreakpoint.SUSPEND_VM);
		value = coreStore.getInt(StudioDebugCorePlugin.PREF_DEFAULT_WATCHPOINT_SUSPEND_POLICY,RuleBreakpoint.SUSPEND_NONE);
//		fWatchpoint.select(value);
//		fAlertHCRButton.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_FAILED));
//		fAlertHCRNotSupportedButton.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_NOT_SUPPORTED));
//		fAlertObsoleteButton.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_OBSOLETE_METHODS));
//		fPerformHCRWithCompilationErrors.setSelection(coreStore.getDefaultBoolean(RuleDebugModel.PREF_HCR_WITH_COMPILATION_ERRORS));
//		fTimeoutText.setStringValue(new Integer(coreStore.getInt(RuleDebugModel.PREF_REQUEST_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT)).toString());
//		fConnectionTimeoutText.setStringValue(new Integer(runtimeStore.getInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT)).toString());
		
		int minValue = RuleDebugModel.DEF_REQUEST_TIMEOUT;
		
		fTimeoutText.setValidRange(minValue, Integer.MAX_VALUE);
		fTimeoutText.setErrorMessage(MessageFormat.format("Value must be a valid integer greater than or equal to {0} ms", new Object[] {new Integer(minValue)})); 
		
		fConnectionTimeoutText.setValidRange(minValue, Integer.MAX_VALUE);
		fConnectionTimeoutText.setErrorMessage(MessageFormat.format("Value must be a valid integer greater than or equal to {0} ms", new Object[] {new Integer(minValue)}));
				
		fTimeoutText.setStringValue(new Integer(RuleDebugModel.DEF_REQUEST_TIMEOUT).toString());
		fConnectionTimeoutText.setStringValue(new Integer(RuleDebugModel.DEF_REQUEST_TIMEOUT).toString());
		
		fTimeoutText.refreshValidState();
		fConnectionTimeoutText.refreshValidState();
		
		if (canClearErrorMessage()) {
			fTimeoutText.clearErrorMessage();
			fConnectionTimeoutText.clearErrorMessage();
		}
		
//		fPromptUnableToInstallBreakpoint.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_UNABLE_TO_INSTALL_BREAKPOINT));
//		fOpenInspector.setSelection(store.getDefaultBoolean(IStudioDebugPreferencesConstants.PREF_OPEN_INSPECT_POPUP_ON_EXCEPTION));
		super.performDefaults();	
	}
	
	/**
	 * Set the values of the component widgets based on the
	 * values in the preference store
	 */
	private void setValues() {
		IPreferenceStore store = getPreferenceStore();
		IEclipsePreferences coreStore = RuleDebugModel.getPreferences();
		IEclipsePreferences runtimeStore = ApplicationRuntime.getPreferences();
		
//		fSuspendButton.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_UNCAUGHT_EXCEPTIONS));
//		fSuspendOnCompilationErrors.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_COMPILATION_ERRORS));
//		fSuspendDuringEvaluations.setSelection(coreStore.getBoolean(RuleDebugModel.PREF_SUSPEND_FOR_BREAKPOINTS_DURING_EVALUATION));
		int value = coreStore.getInt(StudioDebugCorePlugin.PREF_DEFAULT_BREAKPOINT_SUSPEND_POLICY,RuleBreakpoint.SUSPEND_THREAD);
		fSuspendVMorThread.select((value == RuleBreakpoint.SUSPEND_THREAD ? 0 : 1));
//		fWatchpoint.select(coreStore.getInt(StudioDebugCorePlugin.PREF_DEFAULT_WATCHPOINT_SUSPEND_POLICY));
//		fAlertHCRButton.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_FAILED));
//		fAlertHCRNotSupportedButton.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_NOT_SUPPORTED));
//		fAlertObsoleteButton.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_OBSOLETE_METHODS));
//		fPerformHCRWithCompilationErrors.setSelection(coreStore.getBoolean(RuleDebugModel.PREF_HCR_WITH_COMPILATION_ERRORS));
		fTimeoutText.setStringValue(new Integer(coreStore.getInt(RuleDebugModel.PREF_REQUEST_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT)).toString());
		fConnectionTimeoutText.setStringValue(new Integer(runtimeStore.getInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT)).toString());
//		fPromptUnableToInstallBreakpoint.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_ALERT_UNABLE_TO_INSTALL_BREAKPOINT));
//		fOpenInspector.setSelection(store.getBoolean(IStudioDebugPreferencesConstants.PREF_OPEN_INSPECT_POPUP_ON_EXCEPTION));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(FieldEditor.IS_VALID)) {
			boolean newValue = ((Boolean) event.getNewValue()).booleanValue();
			// If the new value is true then we must check all field editors.
			// If it is false, then the page is invalid in any case.
			if (newValue) {
				if (fTimeoutText != null && event.getSource() != fTimeoutText) {
					fTimeoutText.refreshValidState();
				} 
				if (fConnectionTimeoutText != null && event.getSource() != fConnectionTimeoutText) {
					fConnectionTimeoutText.refreshValidState();
				}
			} 
			setValid(fTimeoutText.isValid() && fConnectionTimeoutText.isValid());
			getContainer().updateButtons();
			updateApplyButton();
		}
	}

	/**
	 * if the error message can be cleared or not
	 * @return true if the error message can be cleared, false otherwise
	 */
	protected boolean canClearErrorMessage() {
		if (fTimeoutText.isValid() && fConnectionTimeoutText.isValid()) {
			return true;
		}
		return false;
	}	
	
	

}
