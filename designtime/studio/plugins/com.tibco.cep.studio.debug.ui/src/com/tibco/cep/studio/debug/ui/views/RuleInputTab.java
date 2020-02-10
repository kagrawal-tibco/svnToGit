package com.tibco.cep.studio.debug.ui.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.DebugInputTask;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.mapper.RuleInputMapperControl;
import com.tibco.cep.studio.debug.ui.mapper.RuleInputMapperInvocationContext;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.viewers.PopupListViewer;
import com.tibco.cep.studio.ui.viewers.PopupTreeViewer;
import com.tibco.cep.studio.ui.viewers.TreeViewerFilter;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;

@SuppressWarnings({"unused"})
public class RuleInputTab extends InputTab {
	public static final String ENTITY_URI = "entity_uri";
	public static final String DESTINATION_URI = "destination_uri";
	public static final String RULESESSION_URI = "rulesession_uri";

	public static final String DEBUG_INPUT_BTN_SAVE = "Save";
	public static final String DEBUG_INPUT_BTN_LOAD = "Load";
	public static final String DEBUG_INPUT_BTN_ASSERT = "Assert";

	/**
	 * Image registry key for help image (value <code>"dialog_help_image"</code>).
	 */
	public static final String DLG_IMG_HELP = "dialog_help_image";
	/**
	 *  Space between an image and a label
	 */
	private static final int H_GAP_IMAGE = 5;
	/**
	 * Number of horizontal dialog units per character, value <code>4</code>.
	 */
	private static final int HORIZONTAL_DIALOG_UNIT_PER_CHAR = 4;

	/**
	 * Number of vertical dialog units per character, value <code>8</code>.
	 */
	private static final int VERTICAL_DIALOG_UNITS_PER_CHAR = 8;
	/**
	 * Image registry key for banner image (value
	 * <code>"dialog_title_banner_image"</code>).
	 */
	public static final String DLG_IMG_TITLE_BANNER = "dialog_title_banner_image";

	RuleDebugInputView parentView;

	private Composite control;	

	private Label titleLabel;

	private Label titleImageLabel;

	private Label bottomFillerLabel;

	private Label leftFillerLabel;

	private RGB titleAreaRGB;

	Color titleAreaColor;

	private boolean titleImageLargest = true;

	private Text messageLabel;

	private int messageLabelHeight;

	private Label messageImageLabel;

	private Image titleAreaImage;

	private FontMetrics fontMetrics;

	private Composite workArea;

	private Composite dialogArea;

	private Control buttonBar;

	private boolean helpAvailable;

	private String errorMessage;

	private boolean showingError;

	private String message;

	private Image messageImage;

	private WidgetListener wListener;

	private boolean valid;

	private Map<IDebugTarget,VmTask> executingTaskMap;

	private Composite messageArea;

	private boolean fUpdatedCustomFuncs = false;	

	private Button saveButton, loadButton, assertButton;

	private Text launchTargetName,entityName,destinationName,ruleSessionName;

	private Button launchTargetBrowseBtn, launchTargetClearBtn,
	entityBrowseBtn, entityClearBtn, destinationBrowseBtn,
	destinationClearBtn, ruleSessionBrowseBtn, ruleSessionClearBtn;
	/**
	 * mapper variables
	 */
	BindingEditorPanel bePanel;

	private Composite mapperComposite;

	private MapperControl mapperControl;

	private RuleInputMapperControl ruleInputmapperControl;

	private Map<IDebugTarget,MapperControl> mapperControlMap = new ConcurrentHashMap<IDebugTarget,MapperControl>();

	private Map<IDebugTarget,RuleInputMapperControl> ruleInputmapperControlMap = new ConcurrentHashMap<IDebugTarget,RuleInputMapperControl>();

	private StackLayout mapperlayout;

	IDebugEventSetListener debugListener;

	public RuleInputTab(RuleDebugInputView ruleDebugInputView) {
		super(ruleDebugInputView,"Rule Data");
		wListener = new WidgetListener();
		debugListener = new RuleDebugEventListener();
		DebugPlugin.getDefault().addDebugEventListener(debugListener);
		setExecutingTaskMap(new HashMap<IDebugTarget,VmTask>());
	}

	@Override
	public Image getImage() {
		return StudioDebugUIPlugin.getImage("icons/rules.png");
	}



	/**
	 * @return the control
	 */
	public Composite getControl() {
		return control;
	}



	/**
	 * @param control the control to set
	 */
	public void setControl(Composite control) {
		this.control = control;
	}

	/**
	 * @return the mapperComposite
	 */
	public Composite getMapperComposite() {
		return mapperComposite;
	}

	/**
	 * @return the mapperControl
	 */
	public MapperControl getMapperControl() {
		return mapperControl;
	}



	/**
	 * @param mapperControl the mapperControl to set
	 */
	public void setMapperControl(MapperControl mapperControl) {
		this.mapperControl = mapperControl;
	}




	/**
	 * @return the mapperlayout
	 */
	public StackLayout getMapperlayout() {
		return mapperlayout;
	}

	/**
	 * @param mapperlayout the mapperlayout to set
	 */
	public void setMapperlayout(StackLayout mapperlayout) {
		this.mapperlayout = mapperlayout;
	}





	/**
	 * @return the executingTaskMap
	 */
	public Map<IDebugTarget, VmTask> getExecutingTaskMap() {
		return executingTaskMap;
	}

	/**
	 * @param executingTaskMap the executingTaskMap to set
	 */
	public void setExecutingTaskMap(Map<IDebugTarget, VmTask> executingTaskMap) {
		this.executingTaskMap = executingTaskMap;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}



	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}



	/**
	 * @return the wListener
	 */
	public WidgetListener getWidgetListener() {
		return wListener;
	}



	/**
	 * @param listener the wListener to set
	 */
	public void setWidgetListener(WidgetListener listener) {
		wListener = listener;
	}



	@Override
	public void createControl(Composite parent) {
		// create the overall composite
		setControl(new Composite(parent, SWT.NONE));
		GridData gd = new GridData(SWT.DEFAULT,SWT.DEFAULT);
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment =SWT.FILL;
		GridLayout gl = new GridLayout();
		gl.marginWidth =0;
		gl.marginHeight = 0;
		control.setLayoutData(gd);
		control.setLayout(gl);
		// initialize the dialog units
		initializeFormUnits(control);
		FormLayout layout = new FormLayout();
		control.setLayout(layout);
		control.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		//		FormData fd = new FormData();
		//		fd.left = new FormAttachment(0,0);
		//		fd.bottom = new FormAttachment(100,0);
		//		fd.right = new FormAttachment(100,0);
		//		fd.top = new FormAttachment(0,0);
		//		control.setLayoutData(fd);
		// Now create a work area for the rest of the dialog
		workArea = new Composite(control, SWT.NONE);
		GridLayout childLayout = new GridLayout();
		childLayout.marginHeight = 0;
		childLayout.marginWidth = 0;
		childLayout.verticalSpacing = 0;
		childLayout.horizontalSpacing = 0;
		workArea.setLayout(childLayout);
		GridData childData = new GridData(SWT.DEFAULT,SWT.DEFAULT);
		childData.grabExcessHorizontalSpace = true;
		childData.grabExcessVerticalSpace = true;
		childData.horizontalAlignment = SWT.FILL;
		childData.verticalAlignment = SWT.FILL;
		workArea.setLayoutData(childData);
		//		Control top = createTitleArea(control);
		//		setTitle("Specify an event or concept, and which destination/rulesession you want to assert. "
		//		+ "Map the data once you have selected the data.");
		//		resetWorkAreaAttachments(top);
		resetWorkAreaAttachments(control);
		workArea.setFont(JFaceResources.getDialogFont());
		// initialize the dialog units
		initializeFormUnits(workArea);
		// create the dialog area and button bar
		dialogArea = createFormArea(workArea);
		//		buttonBar = createFormButtonBar(workArea);
		Composite sashFormComposite = createFormContents(dialogArea);
	}

	/**
	 * Initializes the computation of horizontal and vertical dialog units based
	 * on the size of current font.
	 * <p>
	 * This method must be called before any of the dialog unit based conversion
	 * methods are called.
	 * </p>
	 * 
	 * @param control
	 *            a control from which to obtain the current font
	 */
	protected void initializeFormUnits(Control control) {
		// Compute and store a font metric
		GC gc = new GC(control);
		gc.setFont(JFaceResources.getDialogFont());
		fontMetrics = gc.getFontMetrics();
		gc.dispose();
	}

	/**
	 * Creates the dialog's title area.
	 * 
	 * @param parent
	 *            the SWT parent for the title area widgets
	 * @return Control with the highest x axis value.
	 */
	private Control createTitleArea(Composite parent) {

		// add a dispose listener
		parent.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (titleAreaColor != null) {
					titleAreaColor.dispose();
				}
			}
		});
		// Determine the background color of the title bar
		Display display = parent.getDisplay();
		Color background;
		Color foreground;
		if (titleAreaRGB != null) {
			titleAreaColor = new Color(display, titleAreaRGB);
			background = titleAreaColor;
			foreground = null;
		} else {
			background = JFaceColors.getBannerBackground(display);
			foreground = JFaceColors.getBannerForeground(display);
		}

		parent.setBackground(background);
		int verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		int horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		// Dialog image @ right
		titleImageLabel = new Label(parent, SWT.CENTER);
		titleImageLabel.setBackground(background);
		titleAreaImage = StudioDebugUIPlugin.getImage("icons/mapper.png");
		if (titleAreaImage == null)
			titleImageLabel.setImage(
					//					StudioDebugUIPlugin.getImage("icons/title_banner_32x28.gif"));
					JFaceResources.getImage(DLG_IMG_TITLE_BANNER));
		else
			titleImageLabel.setImage(titleAreaImage);

		FormData imageData = new FormData();
		imageData.top = new FormAttachment(0, 0);
		// Note: do not use horizontalSpacing on the right as that would be a
		// regression from
		// the R2.x style where there was no margin on the right and images are
		// flush to the right
		// hand side. see reopened comments in 41172
		imageData.right = new FormAttachment(100, 0); // horizontalSpacing
		titleImageLabel.setLayoutData(imageData);
		// Title label @ top, left
		titleLabel = new Label(parent, SWT.LEFT|SWT.WRAP);
		JFaceColors.setColors(titleLabel, foreground, background);
		titleLabel.setFont(JFaceResources.getBannerFont());
		titleLabel.setText(" ");
		FormData titleData = new FormData();
		titleData.top = new FormAttachment(0, verticalSpacing);
		titleData.right = new FormAttachment(titleImageLabel);
		titleData.left = new FormAttachment(0, horizontalSpacing);
		titleLabel.setLayoutData(titleData);
		// Message image @ bottom, left
		messageImageLabel = new Label(parent, SWT.CENTER);
		messageImageLabel.setBackground(background);
		// Message label @ bottom, center
		messageLabel = new Text(parent, SWT.WRAP | SWT.READ_ONLY);
		JFaceColors.setColors(messageLabel, foreground, background);
		messageLabel.setText(" \n "); // two lines//$NON-NLS-1$
		messageLabel.setFont(JFaceResources.getDialogFont());
		messageLabelHeight = messageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		// Filler labels
		leftFillerLabel = new Label(parent, SWT.CENTER);
		leftFillerLabel.setBackground(background);
		bottomFillerLabel = new Label(parent, SWT.CENTER);
		bottomFillerLabel.setBackground(background);
		setLayoutsForNormalMessage(verticalSpacing, horizontalSpacing);
		determineTitleImageLargest();
		if (titleImageLargest)
			return titleImageLabel;
		return messageLabel;
	}

	/**
	 * Determine if the title image is larger than the title message and message
	 * area. This is used for layout decisions.
	 */
	private void determineTitleImageLargest() {
		int titleY = titleImageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		int verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		int labelY = titleLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		labelY += verticalSpacing;
		labelY += messageLabelHeight;
		labelY += verticalSpacing;
		titleImageLargest = titleY > labelY;
	}

	/**
	 * Set the layout values for the messageLabel, messageImageLabel and
	 * fillerLabel for the case where there is a normal message.
	 * 
	 * @param verticalSpacing
	 *            int The spacing between widgets on the vertical axis.
	 * @param horizontalSpacing
	 *            int The spacing between widgets on the horizontal axis.
	 */
	private void setLayoutsForNormalMessage(int verticalSpacing,
			int horizontalSpacing) {
		FormData messageImageData = new FormData();
		messageImageData.top = new FormAttachment(titleLabel, verticalSpacing);
		messageImageData.left = new FormAttachment(0, H_GAP_IMAGE);
		messageImageLabel.setLayoutData(messageImageData);
		FormData messageLabelData = new FormData();
		messageLabelData.top = new FormAttachment(titleLabel, verticalSpacing);
		messageLabelData.right = new FormAttachment(titleImageLabel);
		messageLabelData.left = new FormAttachment(messageImageLabel,
				horizontalSpacing);
		messageLabelData.height = messageLabelHeight;
		if (titleImageLargest)
			messageLabelData.bottom = new FormAttachment(titleImageLabel, 0,
					SWT.BOTTOM);
		messageLabel.setLayoutData(messageLabelData);
		FormData fillerData = new FormData();
		fillerData.left = new FormAttachment(0, horizontalSpacing);
		fillerData.top = new FormAttachment(messageImageLabel, 0);
		fillerData.bottom = new FormAttachment(messageLabel, 0, SWT.BOTTOM);
		bottomFillerLabel.setLayoutData(fillerData);
		FormData data = new FormData();
		data.top = new FormAttachment(messageImageLabel, 0, SWT.TOP);
		data.left = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(messageImageLabel, 0, SWT.BOTTOM);
		data.right = new FormAttachment(messageImageLabel, 0);
		leftFillerLabel.setLayoutData(data);
	}

	/**
	 * Display the given error message. The currently displayed message is saved
	 * and will be redisplayed when the error message is set to
	 * <code>null</code>.
	 * 
	 * @param newErrorMessage
	 *            the newErrorMessage to display or <code>null</code>
	 */
	public void setErrorMessageOld(String newErrorMessage) {
		// Any change?
		if (errorMessage == null ? newErrorMessage == null : errorMessage
				.equals(newErrorMessage))
			return;
		errorMessage = newErrorMessage;

		// Clear or set error message.
		if (errorMessage == null) {
			if (showingError) {
				// we were previously showing an error
				showingError = false;
			}
			// show the message
			// avoid calling setMessage in case it is overridden to call
			// setErrorMessage,
			// which would result in a recursive infinite loop
			if (message == null) // this should probably never happen since
				// setMessage does this conversion....
				message = ""; //$NON-NLS-1$
			updateMessage(message);
			messageImageLabel.setImage(messageImage);
			setImageLabelVisible(messageImage != null);
		} else {
			// Add in a space for layout purposes but do not
			// change the instance variable
			String displayedErrorMessage = " " + errorMessage; //$NON-NLS-1$
			updateMessage(displayedErrorMessage);
			if (!showingError) {
				// we were not previously showing an error
				showingError = true;
				messageImageLabel.setImage(JFaceResources
						.getImage(TitleAreaDialog.DLG_IMG_TITLE_ERROR));
				setImageLabelVisible(true);
			}
		}
		layoutForNewMessage();
	}

	/**
	 * @param newErrorMessage
	 */
	public void setErrorMessage(String newErrorMessage) {
		// Any change?
		if (errorMessage == null ? newErrorMessage == null : errorMessage
				.equals(newErrorMessage))
			return;
		errorMessage = newErrorMessage;
	}

	private void showErrorMessage() {
		showErrorMessage(errorMessage);
	}

	/**
	 * @param newErrorMessage
	 */
	private void showErrorMessage(String errorMessage) {
		// Clear or set error message.
		if (errorMessage == null) {
			if (showingError) {
				// we were previously showing an error
				showingError = false;
			}
			// show the message
			// avoid calling setMessage in case it is overridden to call
			// setErrorMessage,
			// which would result in a recursive infinite loop
			if (message == null) // this should probably never happen since
				// setMessage does this conversion....
				message = ""; //$NON-NLS-1$
			updateMessage(message);
			messageImageLabel.setImage(messageImage);
			setImageLabelVisible(messageImage != null);
		} else {
			// Add in a space for layout purposes but do not
			// change the instance variable
			String displayedErrorMessage = " " + errorMessage; //$NON-NLS-1$
			updateMessage(displayedErrorMessage);
			if (!showingError) {
				// we were not previously showing an error
				showingError = true;
				//				messageImageLabel.setImage(JFaceResources
				//						.getImage(TitleAreaDialog.DLG_IMG_TITLE_ERROR));
				messageImageLabel.setImage(getDisplay().getSystemImage(SWT.ICON_ERROR));
				setImageLabelVisible(true);
			}
		}
		layoutForNewMessage();
	}

	/**
	 * Make the label used for displaying error images visible depending on
	 * boolean.
	 * 
	 * @param visible
	 *            If <code>true</code> make the image visible, if not then
	 *            make it not visible.
	 */

	private void setImageLabelVisibleOld(boolean visible) {
		messageImageLabel.setVisible(visible);
		bottomFillerLabel.setVisible(visible);
		leftFillerLabel.setVisible(visible);
	}
	private void setImageLabelVisible(boolean visible) {
		messageImageLabel.setVisible(visible);
	}

	/**
	 * Re-layout the labels for the new message.
	 */
	private void layoutForNewMessageOld() {
		int verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		int horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		// If there are no images then layout as normal
		if (errorMessage == null && messageImage == null) {
			setImageLabelVisible(false);
			setLayoutsForNormalMessage(verticalSpacing, horizontalSpacing);
		} else {
			messageImageLabel.setVisible(true);
			bottomFillerLabel.setVisible(true);
			leftFillerLabel.setVisible(true);
			/**
			 * Note that we do not use horizontalSpacing here as when the
			 * background of the messages changes there will be gaps between the
			 * icon label and the message that are the background color of the
			 * shell. We add a leading space elsewhere to compendate for this.
			 */
			FormData data = new FormData();
			data.left = new FormAttachment(0, H_GAP_IMAGE);
			data.top = new FormAttachment(titleLabel, verticalSpacing);
			messageImageLabel.setLayoutData(data);
			data = new FormData();
			data.top = new FormAttachment(messageImageLabel, 0);
			data.left = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(messageLabel, 0, SWT.BOTTOM);
			data.right = new FormAttachment(messageImageLabel, 0, SWT.RIGHT);
			bottomFillerLabel.setLayoutData(data);
			data = new FormData();
			data.top = new FormAttachment(messageImageLabel, 0, SWT.TOP);
			data.left = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(messageImageLabel, 0, SWT.BOTTOM);
			data.right = new FormAttachment(messageImageLabel, 0);
			leftFillerLabel.setLayoutData(data);
			FormData messageLabelData = new FormData();
			messageLabelData.top = new FormAttachment(titleLabel,
					verticalSpacing);
			messageLabelData.right = new FormAttachment(titleImageLabel);
			messageLabelData.left = new FormAttachment(messageImageLabel, 0);
			messageLabelData.height = messageLabelHeight;
			if (titleImageLargest)
				messageLabelData.bottom = new FormAttachment(titleImageLabel,
						0, SWT.BOTTOM);
			messageLabel.setLayoutData(messageLabelData);
		}
		// Do not layout before the dialog area has been created
		// to avoid incomplete calculations.
		if (dialogArea != null)
			workArea.getParent().layout(true);
	}
	/**
	 * 
	 */
	private void layoutForNewMessage() {
		// If there are no images then layout as normal
		if (errorMessage == null && messageImage == null) {
			setImageLabelVisible(false);
		} else {
			messageImageLabel.setVisible(true);
		}
		// Do not layout before the dialog area has been created
		// to avoid incomplete calculations.
		if (dialogArea != null)
			workArea.getParent().layout(true);
		if( messageArea != null ) {
			messageArea.layout(true);
		}
	}

	/**
	 * Show the new message and image.
	 * 
	 * @param newMessage
	 * @param newImage
	 */
	private void showMessage(String newMessage, Image newImage) {
		// Any change?
		if (message.equals(newMessage) && messageImage == newImage) {
			return;
		}
		message = newMessage;
		if (message == null)
			message = "";//$NON-NLS-1$
		// Message string to be shown - if there is an image then add in
		// a space to the message for layout purposes
		String shownMessage = (newImage == null) ? message : " " + message; //$NON-NLS-1$  
		messageImage = newImage;
		if (!showingError) {
			// we are not showing an error
			updateMessage(shownMessage);
			messageImageLabel.setImage(messageImage);
			setImageLabelVisible(messageImage != null);
			layoutForNewMessage();
		}
	}

	/**
	 * Set the message text. If the message line currently displays an error,
	 * the message is saved and will be redisplayed when the error message is
	 * set to <code>null</code>.
	 * <p>
	 * Shortcut for <code>setMessage(newMessage, IMessageProvider.NONE)</code>
	 * </p>
	 * This method should be called after the dialog has been opened as it
	 * updates the message label immediately.
	 * 
	 * @param newMessage
	 *            the message, or <code>null</code> to clear the message
	 */
	public void setMessage(String newMessage) {
		setMessage(newMessage, IMessageProvider.NONE);
	}

	/**
	 * Sets the message for this dialog with an indication of what type of
	 * message it is.
	 * <p>
	 * The valid message types are one of <code>NONE</code>,
	 * <code>INFORMATION</code>,<code>WARNING</code>, or
	 * <code>ERROR</code>.
	 * </p>
	 * <p>
	 * Note that for backward compatibility, a message of type
	 * <code>ERROR</code> is different than an error message (set using
	 * <code>setErrorMessage</code>). An error message overrides the current
	 * message until the error message is cleared. This method replaces the
	 * current message and does not affect the error message.
	 * </p>
	 * 
	 * @param newMessage
	 *            the message, or <code>null</code> to clear the message
	 * @param newType
	 *            the message type
	 * @since 2.0
	 */
	public void setMessageOld(String newMessage, int newType) {
		Image newImage = null;
		if (newMessage != null) {
			switch (newType) {
			case IMessageProvider.NONE:
				break;
			case IMessageProvider.INFORMATION:
				newImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO);
				break;
			case IMessageProvider.WARNING:
				newImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
				break;
			case IMessageProvider.ERROR:
				newImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);
				break;
			}
		}
		showMessage(newMessage, newImage);
	}

	public void setMessage(String newMessage, int newType) {
		Image newImage = null;
		if (newMessage != null) {
			switch (newType) {
			case IMessageProvider.NONE:
				break;
			case IMessageProvider.INFORMATION:
				newImage = getDisplay().getSystemImage(SWT.ICON_INFORMATION);
				break;
			case IMessageProvider.WARNING:
				newImage = getDisplay().getSystemImage(SWT.ICON_WARNING);
				break;
			case IMessageProvider.ERROR:
				newImage = getDisplay().getSystemImage(SWT.ICON_ERROR);
				break;
			}
		}
		showMessage(newMessage, newImage);
	}

	/**
	 * Returns the number of pixels corresponding to the height of the given
	 * number of characters.
	 * <p>
	 * This method may only be called after <code>initializeDialogUnits</code>
	 * has been called.
	 * </p>
	 * <p>
	 * Clients may call this framework method, but should not override it.
	 * </p>
	 * 
	 * @param chars
	 *            the number of characters
	 * @return the number of pixels
	 */
	protected int convertHeightInCharsToPixels(int chars) {
		// test for failure to initialize for backward compatibility
		if (fontMetrics == null) {
			return 0;
		}
		return convertHeightInCharsToPixels(fontMetrics, chars);
	}

	/**
	 * Returns the number of pixels corresponding to the height of the given
	 * number of characters.
	 * <p>
	 * The required <code>FontMetrics</code> parameter may be created in the
	 * following way: <code>
	 * 	GC gc = new GC(control);
	 * 	gc.setFont(control.getFont());
	 * 	fontMetrics = gc.getFontMetrics();
	 * 	gc.dispose();
	 * </code>
	 * </p>
	 * 
	 * @param fontMetrics
	 *            used in performing the conversion
	 * @param chars
	 *            the number of characters
	 * @return the number of pixels
	 * @since 2.0
	 */
	public static int convertHeightInCharsToPixels(FontMetrics fontMetrics,
			int chars) {
		return fontMetrics.getHeight() * chars;
	}

	/**
	 * Returns the number of pixels corresponding to the given number of
	 * horizontal dialog units.
	 * <p>
	 * This method may only be called after <code>initializeDialogUnits</code>
	 * has been called.
	 * </p>
	 * <p>
	 * Clients may call this framework method, but should not override it.
	 * </p>
	 * 
	 * @param dlus
	 *            the number of horizontal dialog units
	 * @return the number of pixels
	 */
	protected int convertHorizontalDLUsToPixels(int dlus) {
		// test for failure to initialize for backward compatibility
		if (fontMetrics == null) {
			return 0;
		}
		return convertHorizontalDLUsToPixels(fontMetrics, dlus);
	}

	/**
	 * Returns the number of pixels corresponding to the given number of
	 * horizontal dialog units.
	 * <p>
	 * The required <code>FontMetrics</code> parameter may be created in the
	 * following way: <code>
	 * 	GC gc = new GC(control);
	 * 	gc.setFont(control.getFont());
	 * 	fontMetrics = gc.getFontMetrics();
	 * 	gc.dispose();
	 * </code>
	 * </p>
	 * 
	 * @param fontMetrics
	 *            used in performing the conversion
	 * @param dlus
	 *            the number of horizontal dialog units
	 * @return the number of pixels
	 * @since 2.0
	 */
	public static int convertHorizontalDLUsToPixels(FontMetrics fontMetrics,
			int dlus) {
		// round to the nearest pixel
		return (fontMetrics.getAverageCharWidth() * dlus + HORIZONTAL_DIALOG_UNIT_PER_CHAR / 2)
				/ HORIZONTAL_DIALOG_UNIT_PER_CHAR;
	}

	/**
	 * Returns the number of pixels corresponding to the given number of
	 * vertical dialog units.
	 * <p>
	 * This method may only be called after <code>initializeDialogUnits</code>
	 * has been called.
	 * </p>
	 * <p>
	 * Clients may call this framework method, but should not override it.
	 * </p>
	 * 
	 * @param dlus
	 *            the number of vertical dialog units
	 * @return the number of pixels
	 */
	protected int convertVerticalDLUsToPixels(int dlus) {
		// test for failure to initialize for backward compatibility
		if (fontMetrics == null) {
			return 0;
		}
		return convertVerticalDLUsToPixels(fontMetrics, dlus);
	}

	/**
	 * Returns the number of pixels corresponding to the given number of
	 * vertical dialog units.
	 * <p>
	 * The required <code>FontMetrics</code> parameter may be created in the
	 * following way: <code>
	 * 	GC gc = new GC(control);
	 * 	gc.setFont(control.getFont());
	 * 	fontMetrics = gc.getFontMetrics();
	 * 	gc.dispose();
	 * </code>
	 * </p>
	 * 
	 * @param fontMetrics
	 *            used in performing the conversion
	 * @param dlus
	 *            the number of vertical dialog units
	 * @return the number of pixels
	 * @since 2.0
	 */
	public static int convertVerticalDLUsToPixels(FontMetrics fontMetrics,
			int dlus) {
		// round to the nearest pixel
		return (fontMetrics.getHeight() * dlus + VERTICAL_DIALOG_UNITS_PER_CHAR / 2)
				/ VERTICAL_DIALOG_UNITS_PER_CHAR;
	}

	/**
	 * Returns the number of pixels corresponding to the width of the given
	 * number of characters.
	 * <p>
	 * This method may only be called after <code>initializeDialogUnits</code>
	 * has been called.
	 * </p>
	 * <p>
	 * Clients may call this framework method, but should not override it.
	 * </p>
	 * 
	 * @param chars
	 *            the number of characters
	 * @return the number of pixels
	 */
	protected int convertWidthInCharsToPixels(int chars) {
		// test for failure to initialize for backward compatibility
		if (fontMetrics == null) {
			return 0;
		}
		return convertWidthInCharsToPixels(fontMetrics, chars);
	}

	/**
	 * Returns the number of pixels corresponding to the width of the given
	 * number of characters.
	 * <p>
	 * The required <code>FontMetrics</code> parameter may be created in the
	 * following way: <code>
	 * 	GC gc = new GC(control);
	 * 	gc.setFont(control.getFont());
	 * 	fontMetrics = gc.getFontMetrics();
	 * 	gc.dispose();
	 * </code>
	 * </p>
	 * 
	 * @param fontMetrics
	 *            used in performing the conversion
	 * @param chars
	 *            the number of characters
	 * @return the number of pixels
	 * @since 2.0
	 */
	public static int convertWidthInCharsToPixels(FontMetrics fontMetrics,
			int chars) {
		return fontMetrics.getAverageCharWidth() * chars;
	}
	/**
	 * Update the contents of the messageLabel.
	 * 
	 * @param newMessage
	 *            the message to use
	 */
	private void updateMessage(String newMessage) {
		messageLabel.setText(newMessage);
	}

	/**
	 * Sets the title to be shown in the title area of this dialog.
	 * 
	 * @param newTitle
	 *            the title show
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setTitle(java.lang.String)
	 */
	public void setTitle(String newTitle) {
		if (titleLabel == null)
			return;
		String title = newTitle;
		if (title == null)
			title = "";//$NON-NLS-1$
		titleLabel.setText(title);
	}

	/**
	 * Sets the title bar color for this dialog.
	 * 
	 * @param color
	 *            the title bar color
	 */
	/**
	 * @param color
	 */
	public void setTitleAreaColor(RGB color) {
		titleAreaRGB = color;
	}

	/**
	 * Sets the title image to be shown in the title area of this dialog.
	 * 
	 * @param newTitleImage
	 *            the title image to be shown
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setTitleImage(org.eclipse.swt.graphics.Image)
	 */
	public void setTitleImage(Image newTitleImage) {

		titleAreaImage = newTitleImage;
		if (titleImageLabel != null) {
			titleImageLabel.setImage(newTitleImage);
			titleImageLabel.setVisible(newTitleImage != null);
			if (newTitleImage != null) {
				determineTitleImageLargest();
				Control top;
				if (titleImageLargest)
					top = titleImageLabel;
				else
					top = messageLabel;
				resetWorkAreaAttachments(top);
			}
		}
	}

	/**
	 * Reset the attachment of the workArea to now attach to top as the top
	 * control.
	 * 
	 * @param top
	 */
	private void resetWorkAreaAttachments(Control top) {
		FormData childData = new FormData();
		childData.top = new FormAttachment(top);
		childData.right = new FormAttachment(100, 0);
		childData.left = new FormAttachment(0, 0);
		childData.bottom = new FormAttachment(100, 0);
		workArea.setLayoutData(childData);
	}


	/**
	 * Creates and returns the contents of the upper part of this dialog (above
	 * the button bar).
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method creates
	 * and returns a new <code>Composite</code> with no margins and spacing.
	 * Subclasses should override.
	 * </p>
	 * 
	 * @param parent
	 *            The parent composite to contain the dialog area
	 * @return the dialog area control
	 */
	protected Composite createFormArea(Composite parent) {
		// create the top level composite for the dialog area
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());
		// Build the separator line
		//		Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		//		titleBarSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets
	 * .Composite)
	 */
	protected Composite createFormButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		composite
		.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		composite.setFont(parent.getFont());

		// create help control if needed
		if (isHelpAvailable()) {
			Control helpControl = createHelpControl(composite);
			((GridData) helpControl.getLayoutData()).horizontalIndent = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		}
		Control buttonSection = createButtonBar(composite);
		((GridData) buttonSection.getLayoutData()).grabExcessHorizontalSpace = true;
		return composite;
	}

	/**
	 * Creates and returns the contents of this dialog's button bar.
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method lays out
	 * a button bar and calls the <code>createButtonsForButtonBar</code>
	 * framework method to populate it. Subclasses may override.
	 * </p>
	 * <p>
	 * The returned control's layout data must be an instance of
	 * <code>GridData</code>.
	 * </p>
	 * 
	 * @param parent
	 *            the parent composite to contain the button bar
	 * @return the button bar control
	 */
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font
		// size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 0; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());

		// Add the buttons to the button bar.
		createButtonsForButtonBar(composite);
		return composite;
	}

	/**
	 * Returns whether or not context help is available for this dialog. This
	 * can affect whether or not the dialog will display additional help
	 * mechanisms such as a help control in the button bar.
	 * 
	 * @return whether or not context help is available for this dialog
	 */
	public boolean isHelpAvailable() {
		return helpAvailable;
	}

	/**
	 * Creates a new help control that provides access to context help.
	 * <p>
	 * The <code>TrayDialog</code> implementation of this method creates the
	 * control, registers it for selection events including selection, Note that
	 * the parent's layout is assumed to be a <code>GridLayout</code> and the
	 * number of columns in this layout is incremented. Subclasses may override.
	 * </p>
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the help control
	 */
	protected Control createHelpControl(Composite parent) {
		Image helpImage = JFaceResources.getImage(DLG_IMG_HELP);
		if (helpImage != null) {
			return createHelpImageButton(parent, helpImage);
		}
		return createHelpLink(parent);
	}

	/*
	 * Creates a button with a help image. This is only used if there is an
	 * image available.
	 */
	private ToolBar createHelpImageButton(Composite parent, Image image) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		((GridLayout) parent.getLayout()).numColumns++;
		toolBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		final Cursor cursor = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);
		toolBar.setCursor(cursor);
		toolBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				cursor.dispose();
			}
		});
		ToolItem item = new ToolItem(toolBar, SWT.NONE);
		item.setImage(image);
		item.setToolTipText(JFaceResources.getString("helpToolTip")); //$NON-NLS-1$
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				helpPressed();
			}
		});
		return toolBar;
	}

	/*
	 * Creates a help link. This is used when there is no help image available.
	 */
	private Link createHelpLink(Composite parent) {
		Link link = new Link(parent, SWT.WRAP | SWT.NO_FOCUS);
		((GridLayout) parent.getLayout()).numColumns++;
		link.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		link.setText("<a>" + IDialogConstants.HELP_LABEL + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
		link.setToolTipText(IDialogConstants.HELP_LABEL);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				helpPressed();
			}
		});
		return link;
	}

	/*
	 * Called when the help control is invoked. This emulates the keyboard
	 * context help behavior (e.g. F1 on Windows). It traverses the widget tree
	 * upward until it finds a widget that has a help listener on it, then
	 * invokes a help event on that widget.
	 */
	private void helpPressed() {
		if (getControl().getShell() != null) {
			Control c = getControl().getShell().getDisplay().getFocusControl();
			while (c != null) {
				if (c.isListening(SWT.Help)) {
					c.notifyListeners(SWT.Help, new Event());
					break;
				}
				c = c.getParent();
			}
		}
	}


	/**
	 * @param inputComposite
	 * @return
	 */
	private Composite createMapperArea(Composite inputComposite) {
		Composite mapperComposite = new Composite(inputComposite,SWT.NONE|SWT.BORDER);
		mapperlayout = new StackLayout();
		mapperComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		mapperComposite.setLayout(mapperlayout);

		initialize();

		MapperControl mc = null;
		if (MapperUtils.isSWTMapper(true)) {
			mc = new RuleInputMapperControl(NullDebugTarget.getInstance(),mapperComposite, null);
		} else {
			mc = new MapperControl(NullDebugTarget.getInstance(),mapperComposite);
			mapperlayout.topControl = mc.getFrameParent();
		}
		mapperControlMap.put(NullDebugTarget.getInstance(), mc);
		setMapperControl(mc);

		mapperComposite.layout();
		return mapperComposite;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(4, false);
		parent.setLayout(glayout);

		loadButton = new Button(parent, SWT.FLAT);
		loadButton.setImage(StudioUIPlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		loadButton.setText(DEBUG_INPUT_BTN_LOAD);
		loadButton.setLayoutData(gd);
		loadButton.addSelectionListener(wListener);		


		saveButton = new Button(parent, SWT.FLAT);
		loadButton.setImage(StudioUIPlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		saveButton.setText(DEBUG_INPUT_BTN_SAVE);
		saveButton.setLayoutData(gd);
		saveButton.addSelectionListener(wListener);

		assertButton = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		assertButton.setText(DEBUG_INPUT_BTN_ASSERT);
		assertButton.setLayoutData(gd);
		assertButton.addSelectionListener(wListener);
	}



	/**
	 * @param parent
	 * @return 
	 */
	public Composite createFormContents(final Composite parent) {

		SashForm formComposite = new SashForm(parent, SWT.HORIZONTAL |SWT.FILL|SWT.SMOOTH);
		GridLayout flayout = new GridLayout();
		flayout.marginWidth = 6;
		flayout.marginHeight = 6;
		flayout.horizontalSpacing = 6;
		formComposite.setLayout(flayout);
		formComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		formComposite.setFont(JFaceResources.getDialogFont());
		initializeFormUnits(formComposite);

		Group mapperGroup = new Group(formComposite, SWT.NONE);
		GridLayout mlayout = new GridLayout();
		mlayout.marginWidth = 0;
		mlayout.marginHeight = 0;
		mlayout.horizontalSpacing = 0;
		mapperGroup.setLayout(mlayout);
		mapperGroup.setText("Mapper");

		mapperComposite = createMapperArea(mapperGroup);

		Group rhsSashGroup = new Group(formComposite,SWT.None);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		layout.marginTop=0;
		rhsSashGroup.setLayout(layout);
		rhsSashGroup.setText("Input");
		/*
		SashForm rhsComposite = new SashForm(rhsSashGroup, SWT.VERTICAL |SWT.FILL | SWT.SMOOTH);
		rhsComposite.setEnabled(false);
		rhsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		rhsComposite.setFont(JFaceResources.getDialogFont());
		initializeFormUnits(formComposite);
		 */
		Composite rhsComposite = new Composite(rhsSashGroup,SWT.NONE);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 0;
		glayout.marginHeight = 0;
		glayout.horizontalSpacing = 0;
		rhsComposite.setLayout(glayout);
		rhsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		messageArea = createMessageComposite(rhsComposite);

		createFieldComposite(rhsComposite);

		buttonBar = createFormButtonBar(rhsComposite);
		formComposite.setWeights(new int[] { 5,4});
		return formComposite;

	}

	private Composite createMessageComposite(final Composite parent) {
		final Composite refComposite = new Composite(parent,SWT.NONE);
		String headerTxt = "Specify an event or concept, and which destination/rulesession you want to assert."
				+ "Map the data once you have selected the data.";
		GridLayout layout = new GridLayout(2,false);
		layout.verticalSpacing = 10;
		refComposite.setLayout( layout);
		refComposite.setLayoutData(new GridData(GridData.CENTER|GridData.FILL_BOTH));
		titleImageLabel = new Label(refComposite,SWT.LEFT);
		titleImageLabel.setImage(parent.getShell().getDisplay().getSystemImage(SWT.ICON_INFORMATION));
		titleImageLabel.pack();
		titleLabel = new Label(refComposite,SWT.WRAP);
		titleLabel.setText(headerTxt);
		titleLabel.pack();
		titleLabel.setFont(JFaceResources.getDialogFont());
		//		titleLabel.setFont(JFaceResources.getBannerFont());
		final GridData labelData = new GridData();
		labelData.horizontalSpan = 1;
		labelData.horizontalAlignment = SWT.FILL;
		Rectangle rect = refComposite.getClientArea();
		labelData.widthHint = (rect.width-titleImageLabel.getImage().getBounds().width)/4;
		labelData.heightHint = titleImageLabel.getImage().getBounds().height; 
		titleLabel.setLayoutData(labelData);
		refComposite.addControlListener(new ControlAdapter(){
			public void controlResized(ControlEvent e) {
				Rectangle bounds = refComposite.getClientArea();
				labelData.widthHint = bounds.width - titleImageLabel.getImage().getBounds().width;
				refComposite.layout();
			}});

		messageImageLabel = new Label(refComposite, SWT.CENTER);
		messageLabel = new Text(refComposite, SWT.WRAP | SWT.READ_ONLY);
		messageLabel.setFont(JFaceResources.getDialogFont());
		return refComposite;
	}

	private Composite createFieldComposite(final Composite parent) {
		final Composite refComposite = new Composite(parent, SWT.NONE);
		refComposite.setLayout(new GridLayout(4, false));
		refComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Launch Target
		new Label(refComposite, SWT.NONE).setText("Launch Target:");

		launchTargetName = new Text(refComposite, SWT.BORDER|SWT.READ_ONLY);
		launchTargetName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		launchTargetName.addModifyListener(wListener);
		launchTargetBrowseBtn = new Button(refComposite, SWT.FLAT);
		GridData tbbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		tbbgd.widthHint = 25;
		tbbgd.heightHint = 25;
		launchTargetBrowseBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/browse.gif"));
		launchTargetBrowseBtn.setLayoutData(tbbgd);
		launchTargetBrowseBtn.addSelectionListener(wListener);
		launchTargetClearBtn = new Button(refComposite, SWT.FLAT);
		GridData tcbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		tcbgd.widthHint = 25;
		tcbgd.heightHint = 25;
		launchTargetClearBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/clear.gif"));
		launchTargetClearBtn.setLayoutData(tcbgd);
		launchTargetClearBtn.addSelectionListener(wListener);

		// Entity
		new Label(refComposite, SWT.NONE).setText("Entity URI:");

		entityName = new Text(refComposite, SWT.BORDER);
		entityName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		entityName.addModifyListener(wListener);
		entityBrowseBtn = new Button(refComposite, SWT.FLAT);
		GridData ebbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		ebbgd.widthHint = 25;
		ebbgd.heightHint = 25;
		entityBrowseBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/browse.gif"));
		entityBrowseBtn.setLayoutData(ebbgd);
		entityBrowseBtn.addSelectionListener(wListener);
		entityClearBtn = new Button(refComposite, SWT.FLAT);
		GridData ecbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		ecbgd.widthHint = 25;
		ecbgd.heightHint = 25;
		entityClearBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/clear.gif"));
		entityClearBtn.setLayoutData(ecbgd);
		entityClearBtn.addSelectionListener(wListener);

		// destination

		new Label(refComposite, SWT.NONE).setText("Destination URI:");

		destinationName = new Text(refComposite, SWT.BORDER);
		destinationName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		destinationName.addModifyListener(wListener);

		destinationBrowseBtn = new Button(refComposite, SWT.FLAT);
		GridData dbbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		dbbgd.widthHint = 25;
		dbbgd.heightHint = 25;
		destinationBrowseBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/browse.gif"));
		destinationBrowseBtn.setLayoutData(dbbgd);
		destinationBrowseBtn.addSelectionListener(wListener);
		destinationClearBtn = new Button(refComposite, SWT.FLAT);
		GridData dcbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		dcbgd.widthHint = 25;
		dcbgd.heightHint = 25;
		destinationClearBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/clear.gif"));
		destinationClearBtn.setLayoutData(dcbgd);
		destinationClearBtn.addSelectionListener(wListener);
		// Rule Session

		new Label(refComposite, SWT.NONE).setText("Rule Session:");

		ruleSessionName = new Text(refComposite, SWT.BORDER);
		ruleSessionName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ruleSessionName.addModifyListener(wListener);
		ruleSessionBrowseBtn = new Button(refComposite, SWT.FLAT);
		GridData rsbbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		rsbbgd.widthHint = 25;
		rsbbgd.heightHint = 25;
		ruleSessionBrowseBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/browse.gif"));
		ruleSessionBrowseBtn.setLayoutData(rsbbgd);
		ruleSessionBrowseBtn.addSelectionListener(wListener);
		ruleSessionClearBtn = new Button(refComposite, SWT.FLAT);
		GridData rscbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		rscbgd.widthHint = 25;
		rscbgd.heightHint = 25;
		ruleSessionClearBtn.setImage(StudioDebugUIPlugin
				.getImage("icons/clear.gif"));
		ruleSessionClearBtn.setLayoutData(rscbgd);
		ruleSessionClearBtn.addSelectionListener(wListener);
		refresh();
		return refComposite;
	}

	private class WidgetListener extends SelectionAdapter implements ModifyListener, SelectionListener {
		public void modifyText(ModifyEvent e) {
			setErrorMessage(null);
			setValid(true);
			Object source = e.getSource();
			if( source == launchTargetName ) {
				if(launchTargetName.getText().isEmpty()) {
					setDebugTarget(null);
					setMapperControl(mapperControlMap.get(NullDebugTarget.getInstance()));
					if (!MapperUtils.isSWTMapper(true)) {
						getMapperlayout().topControl = getMapperControl().getFrameParent();
					}
					getMapperComposite().layout();
				}				

			} else if( source == entityName) {
				if(!entityName.getText().isEmpty()){
					ELEMENT_TYPES[] types = new ELEMENT_TYPES[] {
							ELEMENT_TYPES.SCORECARD,
							ELEMENT_TYPES.CONCEPT,
							ELEMENT_TYPES.SIMPLE_EVENT };
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IWorkspaceRoot root = workspace.getRoot();
					IProject project = root.getProject(getDebugTarget().getProjectName());
					DesignerElement element = null;
					if(project != null) {
						element = CommonIndexUtils.getElement(getDebugTarget().getProjectName(), entityName.getText());
						if(element == null || !Arrays.asList(types).contains(element.getElementType()) ) {
							setErrorMessage("Invalid Entity.");
							setValid(false);
						} else {
							setErrorMessage(null);
							setValid(true);
							final MapperControl mc = getMapperControl();
							if(mc != null) {
								mc.setEntityURI(entityName.getText());
							}
						}						
					}
				} else {
					MapperControl mc = getMapperControl();
					if(mc != null) {
						mc.setEntityURI(null);
					}
					setErrorMessage("Entity URI cannot be null");
					setValid(false);
				}

			} else if(source == destinationName){
				if(!destinationName.getText().isEmpty()){
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IWorkspaceRoot root = workspace.getRoot();
					IProject project = root.getProject(getDebugTarget().getProjectName());
					if(project != null) {
						Map<String, Destination> map = CommonIndexUtils.getAllDestinationsURIMaps(getDebugTarget().getProjectName());
						Destination element = map.get(destinationName.getText());
						if(element == null) {
							setErrorMessage("Invalid destination.");
							setValid(false);
						} else {
							setErrorMessage(null);
							setValid(true);
							MapperControl mc = getMapperControl();
							if(mc != null) {
								mc.setDestinationURI(destinationName.getText());
							}
						}						
					}
				} else {
					MapperControl mc = getMapperControl();
					if(mc != null) {
						mc.setDestinationURI(null);
					}
				}

			} else if( source == ruleSessionName) {
				String target = launchTargetName.getText();
				Map<String, String> ruleSessionMap = null;
				if (targetRuleSessionsMap.containsKey(target)) {
					ruleSessionMap = targetRuleSessionsMap.get(target);
				}
				String rsName = ruleSessionName.getText();
				if (rsName.isEmpty()) {
					setErrorMessage("RuleSession name cannot be null");
					setValid(false);
					MapperControl mc = getMapperControl();
					if(mc != null) {
						mc.setRuleSessionURI(null);
					}
				} 
				//				else if (targetAgentMap.containsKey(launchTargetName.getText()) && targetAgentMap.get(launchTargetName.getText())) {
				else if(ruleSessionMap != null && ruleSessionMap.containsKey(rsName) && ruleSessionMap.containsValue("com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession")) {
					MessageDialog.openInformation(getShell(), "Rule Input", "Process Agent is running. " +
							"There has been no rule data assert.");
					setValid(false);
				} else {
					Map<String,String> sessions = getInputView().getRuleSessions(getDebugTarget());
					if(!sessions.keySet().contains(rsName)) {
						setErrorMessage("Invalid RuleSession name");
						setValid(false);
						MapperControl mc = getMapperControl();
						if(mc != null) {
							mc.setRuleSessionURI(null);
						}
					} else {
						MapperControl mc = getMapperControl();
						if(mc != null) {
							mc.setRuleSessionURI(rsName);
						}
					}
				}
			}
			if(!isValid()) {
				if(isActive() && !launchTargetName.getText().isEmpty()) {
					showErrorMessage(null);
					showErrorMessage();
				} else {
					showErrorMessage(null);
				}
			} else {
				showErrorMessage(null);
			}
			refresh();
		}

		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == launchTargetClearBtn ) {
				MapperControl mc = getMapperControl();
				if(mc != null && !(mc.getDebugTarget() instanceof NullDebugTarget)) {
					mc.setEnabled(false);
					mc.setVisible(false);
					mc.setEntityURI(null);
					//					mc.updateMapperPanel(null);
				}
				setDebugTarget(NullDebugTarget.getInstance());
				mc = mapperControlMap.get(NullDebugTarget.getInstance());
				setMapperControl(mc);
				if (!MapperUtils.isSWTMapper(true)) {
					getMapperlayout().topControl = getMapperControl().getFrameParent();
				}
				mc.setVisible(true);
				mc.setEnabled(true);
				getMapperComposite().layout();
				launchTargetName.setText("");
				launchTargetName.setData(null);
				entityName.setText("");
				entityName.setData(null);
				destinationName.setText("");
				destinationName.setData(null);
				ruleSessionName.setText("");
			} else if( source == entityClearBtn) {
				entityName.setText("");
				entityName.setData(null);
				MapperControl mc = getMapperControl();
				if(mc != null && !(mc.getDebugTarget() instanceof NullDebugTarget)) {
					mc.setEnabled(false);
					mc.setVisible(false);
					mc.setEntityURI(null);
					//					mc.updateMapperPanel(null);
				}
			} else if ( source == destinationClearBtn ) {
				destinationName.setText("");
				destinationName.setData(null);
			} else if( source == ruleSessionClearBtn) {
				ruleSessionName.setText("");
			} else if (source == launchTargetBrowseBtn ) {
				Object input = getInputView().getRuleSessionMap().keySet();
				TargetLabelProvider labelProvider = new TargetLabelProvider();
				ISelection selected = getPopupListSelection(launchTargetName,input,labelProvider);
				if(selected instanceof IStructuredSelection) {
					Object sel = ((IStructuredSelection)selected).getFirstElement();
					if(sel instanceof IRuleRunTarget) {
						setDebugTarget((IRuleRunTarget) sel);
						if(getDebugTarget() != null) {
							launchTargetName.setText(labelProvider.getText(getDebugTarget()));
							launchTargetName.setData(getDebugTarget());
							MapperControl mc = mapperControlMap.get(getDebugTarget());
							if(getMapperControl() != null) {
								MapperControl oldmc = getMapperControl();
								oldmc.setEnabled(false);
								oldmc.setVisible(false);
							}
							setMapperControl(mc);
							if (!MapperUtils.isSWTMapper(true)) {
								getMapperlayout().topControl = getMapperControl().getFrameParent();
							}
							getMapperComposite().layout();
							//							if(getUIAgent() != StudioCorePlugin.getUIAgent(getDebugTarget().getProjectName())){
							//								setUIAgent(StudioCorePlugin.getUIAgent(getDebugTarget().getProjectName()));
							//							}
						}
					}
				}

			} else if ( source == entityBrowseBtn) {
				ELEMENT_TYPES[] types = new ELEMENT_TYPES[] {
						ELEMENT_TYPES.SCORECARD,
						ELEMENT_TYPES.CONCEPT,
						ELEMENT_TYPES.SIMPLE_EVENT };
				ViewerFilter[] filter = new ViewerFilter[]{
						new TreeViewerFilter(getDebugTarget().getWorkspaceProject(), types),
				};
				Object input = getDebugTarget().getWorkspaceProject();
				ISelection selected = getPopupTreeSelection(entityName, input, filter);
				if(selected instanceof IStructuredSelection) {
					Object element = ((IStructuredSelection)selected).getFirstElement();
					if(element instanceof IFile) {
						IResource res = (IResource) element;
						DesignerElement de = IndexUtils.getElement((IResource) res);
						String path = IndexUtils.getFullPath(res);
						entityName.setData(de);
						entityName.setText(path);
					} else if (element instanceof SharedEntityElement) {
						entityName.setData(element);
						entityName.setText(((SharedEntityElement) element).getSharedEntity().getFullPath());
					}
				} 

			} else if ( source == destinationBrowseBtn ) {
				ViewerFilter[] filter = new ViewerFilter[]{new TreeViewerFilter(
						getDebugTarget().getWorkspaceProject(), new ELEMENT_TYPES[] {
							ELEMENT_TYPES.DESTINATION})};
				Object input = getDebugTarget().getWorkspaceProject();
				ISelection selected = getPopupTreeSelection(destinationName, input, filter);
				if(selected instanceof IStructuredSelection) {
					Object element = ((IStructuredSelection)selected).getFirstElement();
					if(element instanceof ChannelDestinationNode) {
						Destination destination = (Destination) ((ChannelDestinationNode) element).getEntity();
						Channel channel = (Channel) destination.eContainer().eContainer();
						StringBuilder sbuilder = new StringBuilder(IndexUtils.getFullPath(channel));
						//sbuilder.append(".channel");
						sbuilder.append("/");
						sbuilder.append(destination.getName());
						destinationName.setData(destination);
						destinationName.setText(sbuilder.toString());
					} /*else if(element instanceof IResource) {
						destinationName.setData(IndexUtils.getElement((IResource) element));
						destinationName.setText(IndexUtils.getFullPath((IResource) element));
					}*/
				}

			} else if ( source == ruleSessionBrowseBtn ) {

				Set<String> input = getInputView().getRuleSessions(getDebugTarget()).keySet();
				if (input.contains(PROCESS_AGENT_RULE_SESSION)) {
					input.remove(PROCESS_AGENT_RULE_SESSION);
				}
				ISelection selection = getPopupListSelection(ruleSessionName, input, null);
				if(selection instanceof IStructuredSelection) {
					String element = (String) ((IStructuredSelection)selection).getFirstElement();
					if(element != null) {
						ruleSessionName.setText(element);
					}
				}
			} else if ( source == saveButton) {
				FileDialog fd = new FileDialog(saveButton.getShell(), SWT.SAVE);
				fd.setFilterExtensions(new String[] { "*.xml" });
				String fileName = fd.open();
				if(fileName != null) {
					MapperControl mc = getMapperControl();
					mc.setEntityURI(entityName.getText());
					mc.setDestinationURI(destinationName.getText());
					mc.setRuleSessionURI(ruleSessionName.getText());
					mc.saveDebugInputModel(fileName);
				}
			} else if( source == loadButton) {
				FileDialog fd = new FileDialog(loadButton.getShell(), SWT.OPEN);
				fd.setFilterExtensions(new String[] { "*.xml" });
				String fileName = fd.open();
				MapperControl mc = getMapperControl();
				if(fileName != null) {
					mc.loadDebugInputModel(fileName);
					if(mc.getEntityURI() != null) {
						entityName.setText(mc.getEntityURI());
						DesignerElement element = IndexUtils.getElement(getDebugTarget().getWorkspaceProject()
								.getName(), mc.getEntityURI());
						entityName.setData(element);
					}
					if(mc.getDestinationURI() != null) {
						destinationName.setText(mc.getDestinationURI());
						Map<String, Destination> map = CommonIndexUtils.getAllDestinationsURIMaps(getDebugTarget().getWorkspaceProject().getName());
						Destination element = map.get(mc.getDestinationURI());
						destinationName.setData(element);
					}
					if(mc.getRuleSessionURI() != null) {
						ruleSessionName.setText(mc.getRuleSessionURI());					
					}
				}
			} else if ( source == assertButton) {
				MapperControl mc = getMapperControl();
				if(mc.getEntityURI() != null && !mc.getEntityURI().equals(entityName.getText())){
					mc.setEntityURI(entityName.getText());
				}
				if(mc.getDestinationURI() != null && !mc.getDestinationURI().equals(destinationName.getText())){
					mc.setDestinationURI(destinationName.getText());
				}
				if(mc.getRuleSessionURI() != null && !mc.getRuleSessionURI().equals(ruleSessionName.getText())){
					mc.setRuleSessionURI(ruleSessionName.getText());
				}
				VmTask task = mc.assertDebugInput();
				if(task != null) {
					getExecutingTaskMap().put(getDebugTarget(), task);
					refresh();
				}

			}
		}


	}

	ISelection getPopupListSelection(Control parentControl,Object input, TargetLabelProvider labelProvider) {
		PopupListViewer listViewer = new PopupListViewer(parentControl
				.getShell(), SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		if(labelProvider != null) {
			listViewer.setLabelProvider(labelProvider);
		}
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setInput(input);
		Point location = parentControl.getLocation();
		Point dloc = parentControl.getParent().toDisplay(location);
		Rectangle bounds = parentControl.getBounds();
		bounds.x=dloc.x;
		bounds.y=dloc.y;
		return listViewer.open(bounds);
	}

	ISelection getPopupTreeSelection(Control parentControl,Object input,ViewerFilter [] viewerFilter) {
		if(parentControl == null) {
			return null;
		}
		PopupTreeViewer treeViewer = new PopupTreeViewer(parentControl.getShell());
		if(viewerFilter != null) {
			treeViewer.setViewerFilter(viewerFilter);
		}
		if(input == null) {
			return null;
		}
		treeViewer.setInput(input);
		treeViewer.expandAll();
		Point location = parentControl.getLocation();
		Point dloc = parentControl.getParent().toDisplay(location);
		Rectangle bounds = parentControl.getBounds();
		bounds.x=dloc.x;
		bounds.y=dloc.y;
		return treeViewer.open(bounds);
	}


	/**
	 * 
	 */
	public void refresh() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {

				// launch target
				if (launchTargetName != null && !launchTargetName.isDisposed() &&
						launchTargetBrowseBtn != null && !launchTargetBrowseBtn.isDisposed() &&
						launchTargetClearBtn != null && !launchTargetClearBtn.isDisposed()) {
					launchTargetName.setEnabled(getInputView().getRuleSessionMap().size() > 0);
					launchTargetBrowseBtn.setEnabled(getInputView().getRuleSessionMap().size() > 0);
					launchTargetClearBtn.setEnabled(!launchTargetName.getText().isEmpty()
							&& getInputView().getRuleSessionMap().size() > 0);
				}

				// entity name
				if (entityName != null && !entityName.isDisposed() &&
						entityBrowseBtn != null && !entityBrowseBtn.isDisposed() &&
						entityClearBtn != null && !entityClearBtn.isDisposed()) {
					entityName.setEnabled(getDebugTarget() != null
							&& !launchTargetName.getText().isEmpty());
					entityBrowseBtn.setEnabled(getDebugTarget() != null
							&& !launchTargetName.getText().isEmpty());
					entityClearBtn.setEnabled(!entityName.getText().isEmpty()
							&& !launchTargetName.getText().isEmpty()
							&& getDebugTarget() != null);

				}
				// mapper
				if(mapperComposite != null && getMapperControl() != null ) {
					//					mapperComposite.setEnabled(getDebugTarget() != null && getUIAgent() != null);
					//					bePanel.setEnabled(getDebugTarget() != null
					//							&& !launchTargetName.getText().isEmpty()
					//							&& getEntityElement() != null);
					MapperControl mc = getMapperControl();
					mc.setEnabled(true);
					mc.setVisible(true);
				}

				// destination name
				if (destinationName != null && !destinationName.isDisposed() &&
						destinationBrowseBtn != null && !destinationBrowseBtn.isDisposed() &&
						destinationClearBtn != null && !destinationClearBtn.isDisposed()) {

					boolean isEvent = false;
					Object dataObj = entityName.getData();
					if(dataObj instanceof DesignerElement) {
						DesignerElement res = (DesignerElement) dataObj;
						ELEMENT_TYPES[] types = new ELEMENT_TYPES[] {ELEMENT_TYPES.SIMPLE_EVENT };
						if(Arrays.asList(types).contains(res.getElementType())) {
							isEvent = true;
						}
					}
					destinationName.setEnabled(getDebugTarget() != null
							&& !launchTargetName.getText().isEmpty()
							&& isEvent);
					destinationBrowseBtn.setEnabled(getDebugTarget() != null 
							&& !launchTargetName.getText().isEmpty()
							&& isEvent);
					destinationClearBtn.setEnabled(!destinationName.getText()
							.isEmpty()
							&& !launchTargetName.getText().isEmpty()
							&& getDebugTarget() != null
							&& isEvent);

				}
				// rule session name
				if (ruleSessionName != null && !ruleSessionName.isDisposed() && 
						ruleSessionBrowseBtn != null && !ruleSessionBrowseBtn.isDisposed() && 
						ruleSessionClearBtn != null && !ruleSessionClearBtn.isDisposed()) {
					ruleSessionName
					.setEnabled(getInputView().getRuleSessions(getDebugTarget()) != null
					&& !launchTargetName.getText().isEmpty()
					&& getInputView().getRuleSessions(getDebugTarget()).size() > 0);
					ruleSessionBrowseBtn
					.setEnabled(getInputView().getRuleSessions(getDebugTarget()) != null
					&& !launchTargetName.getText().isEmpty()
					&& getInputView().getRuleSessions(getDebugTarget()).size() > 0);
					ruleSessionClearBtn.setEnabled(!ruleSessionName.getText()
							.isEmpty()
							&& !launchTargetName.getText().isEmpty()
							&& getDebugTarget() != null);


				}


				if(loadButton != null && !loadButton.isDisposed()) {
					loadButton.setEnabled(getDebugTarget() != null );					
				}
				if(saveButton != null && !saveButton.isDisposed()) {
					saveButton.setEnabled(getDebugTarget() != null && isValid() );					
				}
				if(assertButton != null && !assertButton.isDisposed()){
					boolean isEvent = false;
					Object dataObj = entityName.getData();
					if(dataObj instanceof DesignerElement) {
						DesignerElement res = (DesignerElement) dataObj;
						ELEMENT_TYPES[] types = new ELEMENT_TYPES[] {ELEMENT_TYPES.SIMPLE_EVENT };
						if(Arrays.asList(types).contains(res.getElementType())) {
							isEvent = true;
						}
					}
					assertButton.setEnabled(getDebugTarget() != null &&
							!entityName.getText().isEmpty() &&
							!ruleSessionName.getText().isEmpty() &&
							!(isEvent && destinationName.getText().isEmpty()) &&
							isValid() &&
							!isExecutingTask());					
				}
			} // end run

		});
	}

	/**
	 * @author pdhar
	 *
	 */
	class TargetLabelProvider extends LabelProvider{



		public TargetLabelProvider() {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {

		}

		@Override
		public String getText(Object element) {
			if(element instanceof IDebugTarget) {
				IDebugTarget dt = (IDebugTarget) element;
				try {
					return dt.getName();
				} catch (DebugException e) {
					StudioDebugUIPlugin.log(e);
				}
				//				try {
				//					final String projectName = ((IDebugTarget)element).getLaunch().getLaunchConfiguration().getAttribute(com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME,"");
				//					return projectName+"["+dt.getProcess().getLabel()+"]";
				//				} catch (CoreException e) {
				//					return "["+dt.getProcess().getLabel()+"]";
				//				}
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}

	}

	@Override
	protected void handleDebugTargetCreate(final IRuleRunTarget target) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				launchTargetName.setText("");
				entityName.setText("");
				destinationName.setText("");
				ruleSessionName.setText("");
				try {
					targetRuleSessionsMap.put(target.getName(), target.getRuleSessionMap());
					targetAgentMap.put(target.getName(), target.isProcessAgent());
				} catch (DebugException e) {
					e.printStackTrace();
				}

				updateCustomFunctions(target.getProjectName());

				MapperControl mc = null;
				if (MapperUtils.isSWTMapper(true)) {
					RuleInputMapperInvocationContext context = new RuleInputMapperInvocationContext(target.getProjectName(), new ArrayList<VariableDefinition>(), "");
					mc =  new RuleInputMapperControl(target, getMapperComposite(), context);
				}
				else {
					mc = new MapperControl(target,getMapperComposite());
				}
				if(mc.isEnabled()) {
					mc.setEnabled(false);
				}
				if(mc.isVisible()) {
					mc.setVisible(false);
				}
				setMapperControl(mc);
				mapperControlMap.put(target, mc);
				if (!MapperUtils.isSWTMapper(true)) {
					getMapperlayout().topControl = getMapperControl().getFrameParent();
				}
				getMapperComposite().layout();
			}
		});
		refresh();

	}

	@Override
	protected void handleDebugTargetRemove(IRuleRunTarget target) {
		if(getDebugTarget() == target) {
			setDebugTarget(null);
		}
		try {
			if (targetRuleSessionsMap.containsKey(target.getName())) {
				targetRuleSessionsMap.remove(target.getName());
			}
			if (targetAgentMap.containsKey(target.getName())) {
				targetAgentMap.remove(target.getName());
			}
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!launchTargetName.isDisposed()) {
					launchTargetName.setText("");
				}
				if(!entityName.isDisposed()) {
					entityName.setText("");
				}
				if(!destinationName.isDisposed()) {
					destinationName.setText("");
				}
				if(!ruleSessionName.isDisposed()) {
					ruleSessionName.setText("");	
				}

			}
		});
		refresh();
		if(mapperControlMap.containsKey(target)) {
			final MapperControl mc = mapperControlMap.remove(target);
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if(mc.isEnabled()) {
						mc.setEnabled(false);
					}
					if(mc.isVisible()) {
						mc.setVisible(false);
					}
					mc.dispose();
					MapperControl nmc = mapperControlMap.get(NullDebugTarget.getInstance());
					if (nmc != null) {
						nmc.setEnabled(true);
						nmc.setVisible(true);
					}
					setMapperControl(nmc);

					if (!MapperUtils.isSWTMapper(true)) {
						getMapperlayout().topControl = nmc.getFrameParent();
					}
					getMapperComposite().layout();

				}
			});
		}

	}


	public class RuleDebugEventListener implements IDebugEventSetListener {
		@Override
		public void handleDebugEvents(DebugEvent[] events) {
			if (events == null || events.length == 0) {
				return;
			}
			DebugEvent event = events[0];
			Object source = event.getSource();
			if (source instanceof IRuleRunTarget) {
				if (event.getKind() == DebugEvent.MODEL_SPECIFIC) {
					if(event.getDetail() == IRuleRunTarget.DEBUG_TASK_REPONSE) {

						//Then do further processing
						try {
							Object data = event.getData();
							if (data instanceof DebugInputTask) {
								final DebugInputTask task = (DebugInputTask) data;
								if(getExecutingTaskMap().containsKey(task.getDebugTarget())) {
									getExecutingTaskMap().remove(task.getDebugTarget());
									refresh();
								}
								// check if the task has response inside it.

								if (task.hasResponse()) {
									final Object response = task.getResponse();
									StudioDebugUIPlugin.debug("Task Response: " + (response != null? response.toString() : "None"));

								}

							}
						} catch (Exception e) {
							StudioDebugUIPlugin.log(e);
						}
					}
				}
			}

		}
	}

	@Override
	public void dispose() {
		if(debugListener != null) {
			DebugPlugin.getDefault().removeDebugEventListener(debugListener);
		}

	}

	/**
	 * Returns true if a task is executing
	 * @return
	 */
	boolean isExecutingTask() {
		return getExecutingTaskMap().containsKey(getDebugTarget());
	}

	private void initialize() {
		// wait for function catalog to be built
		FunctionsCatalogManager.waitForStaticRegistryUpdates();
	}

	public void updateCustomFunctions(String projectName) {
		if (!fUpdatedCustomFuncs) {
			MapperXSDUtils.updateCustomFunctions(projectName);
		}
		fUpdatedCustomFuncs = true;
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		updateEntityNameText();
	}

	@Override
	public void refreshControls() {
		updateEntityNameText();
	}

	private void updateEntityNameText() {
		if (getTabFolder().getSelection() != null && getTabFolder().getSelection().getText().equals("Rule Data")) {
			if (!entityName.getText().isEmpty()) {
				String entity = entityName.getText();
				entityName.setText("");
				entityName.setText(entity);
			}
		}
	}

}
