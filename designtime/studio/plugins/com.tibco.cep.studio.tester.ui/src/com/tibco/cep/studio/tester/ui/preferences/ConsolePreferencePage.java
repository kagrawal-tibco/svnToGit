package com.tibco.cep.studio.tester.ui.preferences;

import java.util.StringTokenizer;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.preferences.OverlayPreferenceStore;

public class ConsolePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	
	private OverlayPreferenceStore overlayPreferenceStore;
	public static final String Id = "com.tibco.cep.studio.tester.ui.consolePreferencePage";
	private static int DEBUG 		= 0;
	private static int INFO			= 1;
	private static int WARNING		= 2;
	private static int ERROR		= 3;
	private static int FATAL		= 4;
	private static int DEFAULT		= 5;
	private static int MAX			= 6;

	private static String[] consoleDisplayNames = new String[MAX];
	private static String[] consoleColorTypes = new String[MAX];
	private static String[] consoleStyleTypes = new String[MAX];
	static {
		consoleColorTypes[DEBUG]   = TesterPreferenceConstants.CONSOLE_COLOR_DEBUG;
		consoleColorTypes[INFO]    = TesterPreferenceConstants.CONSOLE_COLOR_INFO;
		consoleColorTypes[WARNING] = TesterPreferenceConstants.CONSOLE_COLOR_WARN;
		consoleColorTypes[ERROR]   = TesterPreferenceConstants.CONSOLE_COLOR_ERROR;
		consoleColorTypes[FATAL]   = TesterPreferenceConstants.CONSOLE_COLOR_FATAL;
		consoleColorTypes[DEFAULT] = TesterPreferenceConstants.CONSOLE_COLOR_DEFAULT;

		consoleStyleTypes[DEBUG]   = TesterPreferenceConstants.CONSOLE_STYLE_DEBUG;
		consoleStyleTypes[INFO]    = TesterPreferenceConstants.CONSOLE_STYLE_INFO;
		consoleStyleTypes[WARNING] = TesterPreferenceConstants.CONSOLE_STYLE_WARN;
		consoleStyleTypes[ERROR]   = TesterPreferenceConstants.CONSOLE_STYLE_ERROR;
		consoleStyleTypes[FATAL]   = TesterPreferenceConstants.CONSOLE_STYLE_FATAL;
		consoleStyleTypes[DEFAULT] = TesterPreferenceConstants.CONSOLE_STYLE_DEFAULT;
		
		consoleDisplayNames[DEBUG] 	= Messages.getString("ConsolePreferencePage.Debug"); //$NON-NLS-1$
		consoleDisplayNames[INFO] 	= Messages.getString("ConsolePreferencePage.Info"); //$NON-NLS-1$
		consoleDisplayNames[WARNING]= Messages.getString("ConsolePreferencePage.Warning"); //$NON-NLS-1$
		consoleDisplayNames[ERROR] 	= Messages.getString("ConsolePreferencePage.Error"); //$NON-NLS-1$
		consoleDisplayNames[FATAL]	= Messages.getString("ConsolePreferencePage.Fatal"); //$NON-NLS-1$
		consoleDisplayNames[DEFAULT]= Messages.getString("ConsolePreferencePage.Default"); //$NON-NLS-1$
	}
	
	private List streamTypesList;
	private ColorSelector textColorSelector;
	private Button italicsButton;
	private Button boldButton;
	
	public ConsolePreferencePage() {
		super();
		setPreferenceStore(StudioTesterUIPlugin.getDefault().getPreferenceStore());
		overlayPreferenceStore = new OverlayPreferenceStore(getPreferenceStore(), getOverlayKeys());
	}

	private OverlayKey[] getOverlayKeys() {
		OverlayKey[] keys = new OverlayKey[MAX * 2];
		for (int i = 0; i < MAX; i++) {
			keys[i] = new OverlayKey(consoleColorTypes[i], OVERLAYKEYTYPES.STRING);
		}
		
		for (int i = MAX; i < MAX * 2; i++) {
			keys[i] = new OverlayKey(consoleStyleTypes[i-MAX], OVERLAYKEYTYPES.INT);
		}
		
		return keys;
	}

	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {
		overlayPreferenceStore.load();
		
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Group consoleComposite = new Group(composite, SWT.NULL);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		consoleComposite.setLayout(layout);
		consoleComposite.setLayoutData(data);
		consoleComposite.setText(Messages.getString("ConsolePreferencePage.ColorsAndStyles"));
		
		Composite messageTypeGroup = new Composite(consoleComposite, SWT.NULL);
		messageTypeGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		messageTypeGroup.setLayout(new GridLayout(1, false));
		
		Label messageTypeLabel = new Label(messageTypeGroup, SWT.NULL);
		messageTypeLabel.setText(Messages.getString("ConsolePreferencePage.MessageType")); //$NON-NLS-1$
		
		streamTypesList = new List(messageTypeGroup, SWT.BORDER);
		streamTypesList.setSize(200, 200);
		streamTypesList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		streamTypesList.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
		
			}
		
		});
		
		Composite group = new Composite(consoleComposite, SWT.NULL);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(1, false));

		Label styleLabel = new Label(group, SWT.NULL);
		styleLabel.setText(Messages.getString("ConsolePreferencePage.Style")); //$NON-NLS-1$
		
		italicsButton = new Button(group, SWT.CHECK);
		italicsButton.setText(Messages.getString("ConsolePreferencePage.Italics")); //$NON-NLS-1$
		
		boldButton = new Button(group, SWT.CHECK);
		boldButton.setText(Messages.getString("ConsolePreferencePage.Bold")); //$NON-NLS-1$

		SelectionListener listener = new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				setStyleButtonValue();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
		
			}
		
		};
		italicsButton.addSelectionListener(listener);
		boldButton.addSelectionListener(listener);
		
		Label colorLabel = new Label(group, SWT.NULL);
		colorLabel.setText(Messages.getString("ConsolePreferencePage.TextColor")); //$NON-NLS-1$
		
		textColorSelector = new ColorSelector(group);
		textColorSelector.setColorValue(new RGB(200,200,200));
		textColorSelector.addListener(new IPropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent event) {
				int index = streamTypesList.getSelectionIndex();
				if (index != -1) {
					RGB rgb = textColorSelector.getColorValue();
					overlayPreferenceStore.setValue(consoleColorTypes[index], rgb.red + ", " + rgb.green + ", " + rgb.blue); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		
		});
		
		streamTypesList.setItems(consoleDisplayNames);
		streamTypesList.select(DEBUG);
		update();
		return composite;

	}

	@Override
	protected void performApply() {
		overlayPreferenceStore.performApply();
	}

	@Override
	protected void performDefaults() {
		overlayPreferenceStore.performDefaults();
		update();
	}

	@Override
	public boolean performOk() {
		return overlayPreferenceStore.performOk();
	}

	private void update() {
		updateColorSelector();
		updateStyleButtons();
	}
	
	private void updateStyleButtons() {
		int selectionIndex = streamTypesList.getSelectionIndex();
		int styleValue = overlayPreferenceStore.getInt(consoleStyleTypes[selectionIndex]);
		boldButton.setSelection((styleValue & SWT.BOLD) != 0);
		italicsButton.setSelection((styleValue & SWT.ITALIC) != 0);
	}

	private void setStyleButtonValue() {
		int index = streamTypesList.getSelectionIndex();
		if (index != -1) {
			int style = 0;
			if (boldButton.getSelection()) {
				style |= SWT.BOLD;
			}
			if (italicsButton.getSelection()) {
				style |= SWT.ITALIC;
			}
			overlayPreferenceStore.setValue(consoleStyleTypes[index], style);
		}
	}

	private void updateColorSelector() {
		int selectionIndex = streamTypesList.getSelectionIndex();
		String rgbValue = overlayPreferenceStore.getString(consoleColorTypes[selectionIndex]);
		int[] rgb = getRGB(rgbValue);
		textColorSelector.setColorValue(new RGB(rgb[0], rgb[1], rgb[2]));
	}

	private int[] getRGB(String rgbValue) {
		int[] rgb = new int[] { 200, 200, 200 };
		if (rgbValue == null) {
			return rgb;
		}
		StringTokenizer st = new StringTokenizer(rgbValue, ","); //$NON-NLS-1$
		int i = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token != null && i < 3){
				rgb[i++] = Integer.parseInt(token.trim());
			}
		}
		return rgb;
	}

}
