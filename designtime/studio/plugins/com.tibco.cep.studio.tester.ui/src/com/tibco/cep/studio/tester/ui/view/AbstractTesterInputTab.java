package com.tibco.cep.studio.tester.ui.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.views.InputTab;
import com.tibco.cep.studio.debug.ui.views.RuleDebugInputView;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;
import com.tibco.cep.studio.ui.viewers.PopupListViewer;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

@SuppressWarnings("unused")
public abstract class AbstractTesterInputTab extends InputTab implements SelectionListener, ModifyListener {

	public static final String TESTER_INPUT_BTN_ASSERT = "Start Test";
	
	public static final String CURRENT_SELECT_TABLE_PROJECT = "testDataTableProject";
	
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

//	private RGB titleAreaRGB;

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
	
	private boolean valid;

	private Composite messageArea;

	protected Text launchTargetName, ruleSessionName;
	protected Button select, assertButton, launchTargetBrowseBtn, launchTargetClearBtn, ruleSessionBrowseBtn, ruleSessionClearBtn;
	
	protected Table testDataSelectTable;
	
	private TableEditor tableEditor;
	
	protected IProject project;
	
	protected Map<String, String> checkedTestaDataFiles =  new HashMap<String, String>();
	protected Set<TableItem> checkedTestaData =  new HashSet<TableItem>();
//	protected ToolItem refreshToolItem;

	protected ToolItem upRowButton;
	protected ToolItem downRowButton;
	protected ToolItem refreshButton;
	
	protected final java.awt.Font awtFont = new java.awt.Font("Tahoma", 0, 11); 
	
	protected Map<TableItem, Button> itemButtonEditorMap = new HashMap<TableItem, Button>();
	
	
	protected String clusterName = null;

	protected Map<String, String> targetProjectMap = new HashMap<String, String>();
	protected Map<String, String> targetClusterMap = new HashMap<String, String>();

	protected ToolBar toolBar;
	
	public String getClusterName() {
		return clusterName;
	}
	
	public AbstractTesterInputTab() {
		super("Tester Data");
	}
	
	/**
	 * @param ruleDebugInputView
	 */
	public AbstractTesterInputTab(RuleDebugInputView ruleDebugInputView) {
		super(ruleDebugInputView,"Tester Data");
	}

	@Override
	public void createControl(Composite parent) {
		// create the overall composite
		setControl(new Composite(parent, SWT.NONE));
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		// initialize the dialog units
		initializeFormUnits(control);
		FormLayout layout = new FormLayout();
		control.setLayout(layout);
		// Now create a work area for the rest of the dialog
		workArea = new Composite(control, SWT.NONE);
		GridLayout childLayout = new GridLayout();
		childLayout.marginHeight = 0;
		childLayout.marginWidth = 0;
		childLayout.verticalSpacing = 0;
		workArea.setLayout(childLayout);
		resetWorkAreaAttachments(control);
		workArea.setFont(JFaceResources.getDialogFont());
		// initialize the dialog units
		initializeFormUnits(workArea);
		// create the dialog area and button bar
		dialogArea = createFormArea(workArea);
//		buttonBar = createFormButtonBar(workArea);
		createFormContents(dialogArea);
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
	
	protected void showErrorMessage() {
		showErrorMessage(errorMessage);
	}
	
	/**
	 * @param newErrorMessage
	 */
	protected void showErrorMessage(String errorMessage) {
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
//		bottomFillerLabel.setVisible(visible);
//		leftFillerLabel.setVisible(visible);
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
	 * @param composite
	 * @return
	 */
	private Composite createTestDataArea(Composite composite) {
		toolBar=createToolbar(composite);
		testDataSelectTable = new Table(composite, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
		testDataSelectTable.setLayout(new GridLayout());
		testDataSelectTable.addSelectionListener(this);
		GridData data = new GridData(GridData.FILL_BOTH);
		testDataSelectTable.setLayoutData(data);
		testDataSelectTable.setLinesVisible(true);
		testDataSelectTable.setHeaderVisible(true);
		handleDoubleClick();
		addColumns();
		setTestTableSelectEditableSupport(testDataSelectTable);
		createSelectButton(composite);
        return composite;
	}
	
	private void handleDoubleClick(){
		testDataSelectTable.addListener(SWT.MouseDoubleClick, new Listener(){
			@Override
			public void handleEvent(Event event) {
				Display.getDefault().asyncExec(new Runnable(){

					@Override
					public void run() {
						String inputPath = StudioUIPlugin.getDefault()
						.getPreferenceStore().getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);
						int index = testDataSelectTable.getSelectionIndex();
						if (index >= 0 && index < testDataSelectTable.getItemCount()) {
							String path = testDataSelectTable.getItem(index).getText();
							StringBuilder stringBuilder = new StringBuilder(inputPath);
							stringBuilder.append(path);
							path = stringBuilder.toString();
							IPath iPath = Path.fromOSString(path);
							IFile resource = project.getFile(iPath);
							try {
								IDE.openEditor(getInputView().getSite().getPage(), resource);
							} catch (PartInitException e) {
								e.printStackTrace();
							}
						}
					}});
			}});
	}
	
	protected void addColumns() {
		TableColumn resPathColumn = new TableColumn(testDataSelectTable, SWT.NULL);
		resPathColumn.setText("");

		TableColumn destinationsColumn = new TableColumn(testDataSelectTable, SWT.NULL);
		destinationsColumn.setText("");

		new TableColumn(testDataSelectTable, SWT.NULL);
		
		autoTableLayout();
		
		populateTestData();
	}
	
	protected void autoTableLayout() {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(testDataSelectTable);
		for (TableColumn column : testDataSelectTable.getColumns()) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		testDataSelectTable.setLayout(autoTableLayout);
	}
	

	/**
	 * @param parent
	 * @return
	 */
	protected Control createSelectButton(Composite parent) {
		select = new Button(parent, SWT.CHECK);
		select.setText("Select / deselect all");
		select.setSelection(false);
		select.addSelectionListener(this);
		select.setEnabled(false);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(1, false);
		parent.setLayout(glayout);
		GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
		assertButton = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		assertButton.setText(TESTER_INPUT_BTN_ASSERT);
		assertButton.setLayoutData(gd);
		assertButton.addSelectionListener(this);
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
		
		Group testDataGroup = new Group(formComposite, SWT.NONE);
		GridLayout mlayout = new GridLayout();
		mlayout.marginWidth = 0;
		mlayout.marginHeight = 0;
		mlayout.horizontalSpacing = 0;
		testDataGroup.setLayout(mlayout);
		testDataGroup.setText(Messages.getString("input.select.test.data"));
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		testDataGroup.setLayoutData(gd);

		createTestDataArea(testDataGroup);
		
		Group rhsSashGroup = new Group(formComposite,SWT.None);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		layout.marginTop=0;
		rhsSashGroup.setLayout(layout);
		rhsSashGroup.setText("Input");

		Composite rhsComposite = new Composite(rhsSashGroup,SWT.NONE);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 0;
		glayout.marginHeight = 0;
		glayout.horizontalSpacing = 0;
		rhsComposite.setLayout(glayout);
		rhsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
//		messageArea = createMessageComposite(rhsComposite);
		createFieldComposite(rhsComposite);
		buttonBar = createFormButtonBar(rhsComposite);
		formComposite.setWeights(new int[] { 5, 4});
		return formComposite;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private Composite createMessageComposite(final Composite parent) {
		final Composite refComposite = new Composite(parent,SWT.NONE);
		String headerTxt = "Select Test Data for assert";
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
		launchTargetName.addModifyListener(this);
		launchTargetBrowseBtn = new Button(refComposite, SWT.FLAT);
		GridData tbbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		tbbgd.widthHint = 25;
		tbbgd.heightHint = 25;
		launchTargetBrowseBtn.setImage(StudioDebugUIPlugin.getImage("icons/browse.gif"));
		launchTargetBrowseBtn.setLayoutData(tbbgd);
		launchTargetBrowseBtn.addSelectionListener(this);
		launchTargetClearBtn = new Button(refComposite, SWT.FLAT);
		GridData tcbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		tcbgd.widthHint = 25;
		tcbgd.heightHint = 25;
		launchTargetClearBtn.setImage(StudioDebugUIPlugin.getImage("icons/clear.gif"));
		launchTargetClearBtn.setLayoutData(tcbgd);
		launchTargetClearBtn.addSelectionListener(this);
		new Label(refComposite, SWT.NONE).setText("Rule Session:");
		ruleSessionName = new Text(refComposite, SWT.BORDER);
		ruleSessionName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ruleSessionName.addModifyListener(this);
		ruleSessionBrowseBtn = new Button(refComposite, SWT.FLAT);
		GridData rsbbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		rsbbgd.widthHint = 25;
		rsbbgd.heightHint = 25;
		ruleSessionBrowseBtn.setImage(StudioDebugUIPlugin.getImage("icons/browse.gif"));
		ruleSessionBrowseBtn.setLayoutData(rsbbgd);
		ruleSessionBrowseBtn.addSelectionListener(this);
		ruleSessionClearBtn = new Button(refComposite, SWT.FLAT);
		GridData rscbgd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		rscbgd.widthHint = 25;
		rscbgd.heightHint = 25;
		ruleSessionClearBtn.setImage(StudioDebugUIPlugin.getImage("icons/clear.gif"));
		ruleSessionClearBtn.setLayoutData(rscbgd);
		ruleSessionClearBtn.addSelectionListener(this);
		refresh();
		return refComposite;
	}
	
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
					ruleSessionClearBtn.setEnabled(!ruleSessionName.getText().isEmpty()
							&& !launchTargetName.getText().isEmpty()
							&& getDebugTarget() != null);
				}
				if(assertButton != null && !assertButton.isDisposed()) {
					assertButton.setEnabled(getDebugTarget() != null &&
							!ruleSessionName.getText().isEmpty() &&
							isValid()  );					
				}
				
//				if (refreshToolItem != null && !refreshToolItem.isDisposed()) {
//					refreshToolItem.setEnabled(project == null ? false : true);
//				}

				if (refreshButton != null && !refreshButton.isDisposed()) {
					refreshButton.setEnabled(project == null ? false : true);
				}
				if (upRowButton != null && !upRowButton.isDisposed()) {
					upRowButton.setEnabled(project == null ? false : true);
				}
				if (downRowButton != null && !downRowButton.isDisposed()) {
					downRowButton.setEnabled(project == null ? false : true);
				}

			} // end run
		});
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
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @author pdhar
	 *
	 */
	protected class TargetLabelProvider extends LabelProvider{
		
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
			}
			return super.getText(element);
		}
		
		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}
	}
	
	protected void populateTestData() {

		checkedTestaData.clear();
		if (select != null && !select.isDisposed()) {
			select.setSelection(false);
		}
		TableItem[] items = testDataSelectTable.getItems();
		for (TableItem tableItem : items) {
			if (itemButtonEditorMap.containsKey(tableItem)) {
				itemButtonEditorMap.get(tableItem).dispose();
			}
			tableItem.dispose();
		}
		itemButtonEditorMap.clear();
		if (project == null) {
			return;
		}

		String destination = "";
		List<IResource> output = TesterCoreUtils.getTestDataItems(project);

		for (IResource resource : output) {
			String fullPath ="/"+ (((IFile) resource).getLocation().toString().split("/"+ project.getName() + "/"))[1];
			if (resource.getLocation().getFileExtension()
					.equalsIgnoreCase("scorecardtestdata")) {
				createItem(fullPath, resource.getLocation().toString(),
						"icons/scorecardTestData.png", destination);
			}
			if (resource.getLocation().getFileExtension()
					.equalsIgnoreCase("concepttestdata")) {
				createItem(fullPath, resource.getLocation().toString(),
						"icons/conceptTestData.png", destination);
			}
			if (resource.getLocation().getFileExtension()
					.equalsIgnoreCase("eventtestdata")) {
				
				
				Entity entity = IndexUtils.getEntity(project.getName(),
						TesterCoreUtils.getEntityInfo(((IFile) resource).getLocation()
								.toString()));
				SimpleEvent event = (SimpleEvent) entity;
				if (event.getDestination() != null) {
					StringBuilder sb = new StringBuilder();
					destination = sb.append(event.getChannelURI()).append("/")
							.append(event.getDestinationName()).toString();
				}
				createItem(fullPath, resource.getLocation().toString(),
						"icons/eventTestData.png", destination);
			}
		}

		testDataSelectTable.setData(CURRENT_SELECT_TABLE_PROJECT,
				project.getName());
		testDataSelectTable.forceFocus();

	}
	
	/**
	 * @param checkedItems
	 * @param itemToSearch
	 * @return
	 */
	protected boolean contains(Set<TableItem> checkedItems, TableItem itemToSearch) {
		for (TableItem checkedItem : checkedItems) {
			if (checkedItem.getText(0).equals(itemToSearch.getText(0))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param file
	 * @param imagePath
	 */
	protected void createItem(String relPath, String file, String imagePath, String destination) {
		File f = new File(file);
		if (f.exists()) {
			TableItem item = new TableItem(testDataSelectTable, SWT.CENTER | SWT.CHECK);
			item.setImage(0, StudioDebugUIPlugin.getImage(imagePath));
			item.setText(0, relPath);
			item.setText(1, destination);
			item.setData(file);
			if (f.getName().endsWith(".eventtestdata")) {
				Image image = StudioUIPlugin.getDefault().getImage("icons/destination.png");
				if (!destination.isEmpty()) {
					image = new DecorationOverlayIcon( image, StudioTesterUIPlugin.getImageDescriptor("icons/default.gif"),
							IDecoration.TOP_LEFT).createImage();
				}
				item.setImage(1,image);
				createBrowseButtonTableEditor(item, destination);
			}
		}
	}
	
	/**
	 * @param parentControl
	 * @param input
	 * @param labelProvider
	 * @return
	 */
	protected ISelection getPopupListSelection(Control parentControl,Object input, TargetLabelProvider labelProvider) {
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

	public Button getAssertButton() {
		return assertButton;
	}
	
	/**
	 * @param parent
	 * @param duplicate
	 * @return
	 */
	protected ToolBar createToolbar(Composite parent) {
        ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
        toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        
        upRowButton = new ToolItem(toolBar, SWT.PUSH);
        Image addImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_UP);
        upRowButton.setImage(addImg);
        upRowButton.setToolTipText(Messages.getString("Up"));
        upRowButton.setText(Messages.getString("Up"));
        upRowButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				swap(true);
			}
		});
        
        downRowButton = new ToolItem(toolBar, SWT.PUSH);
        Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DOWN);
        downRowButton.setImage(delImg);
        downRowButton.setToolTipText(Messages.getString("Down"));
        downRowButton.setText(Messages.getString("Down"));
        downRowButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				swap(false);
			}
		});

        refreshButton = new ToolItem(toolBar, SWT.PUSH);
        Image duplImg = StudioTesterUIPlugin.getDefault().getImage("icons/refresh_16x16.png");
        refreshButton.setImage(duplImg);
        refreshButton.setToolTipText(Messages.getString("refresh"));
        refreshButton.setText(Messages.getString("refresh"));
        refreshButton.addListener(SWT.Selection, new Listener() {

        	@Override
        	public void handleEvent(Event event) {
        		populateTestData();
        	}
        });
        toolBar.pack();
        return toolBar;
	}
	
	/**
	 * @param table
	 * @param up
	 */
	protected void swap(boolean up) {
		if (testDataSelectTable == null || testDataSelectTable.isDisposed()) {
			return;
		}
		int index = testDataSelectTable.getSelectionIndex();
		if(index == -1){
			return;
		}
		int swapIndex = -1 ;
		if (up) {
			swapIndex = index - 1;
			if (swapIndex == -1) {
				return;
			}
		} else {
			swapIndex = index + 1;
			if (swapIndex == testDataSelectTable.getItemCount()) {
				return;
			}
		}
		String currentItem = testDataSelectTable.getItem(index).getText();
		boolean currentSelect = testDataSelectTable.getItem(index).getChecked();
		String swapItem = testDataSelectTable.getItem(swapIndex).getText();
		boolean swapSelect = testDataSelectTable.getItem(swapIndex).getChecked();
		
		String currentDest = testDataSelectTable.getItem(index).getText(1);
		String swapDest = testDataSelectTable.getItem(swapIndex).getText(1);
		
		Object currentData = testDataSelectTable.getItem(index).getData();
		Object swapData = testDataSelectTable.getItem(swapIndex).getData();

		
		testDataSelectTable.getItem(swapIndex).setText(currentItem);
		testDataSelectTable.getItem(swapIndex).setChecked(currentSelect);
		testDataSelectTable.getItem(swapIndex).setText(1, currentDest);
		testDataSelectTable.getItem(swapIndex).setData(currentData);

		
		testDataSelectTable.getItem(index).setText(swapItem);
		testDataSelectTable.getItem(index).setChecked(swapSelect);
		testDataSelectTable.getItem(index).setText(1, swapDest);
		testDataSelectTable.getItem(index).setData(swapData);

		
		testDataSelectTable.select(swapIndex);
	}
	
	private void createBrowseButtonTableEditor(final TableItem item, String destination) {
		if (item.isDisposed()) {
			return;
		}
		TableEditor editor = new TableEditor (testDataSelectTable);
		Button browseButton = new Button(testDataSelectTable, SWT.CENTER);
		browseButton.setImage(StudioUIPlugin.getDefault().getImage("icons/browse_file_system.gif"));
		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!item.isDisposed()) {
					String destinationURI = item.getText(1);
					openDestinationSelector(destinationURI, item);
				}
			}
		});
		browseButton.pack();

		if (!destination.isEmpty() || getClusterName() == null) {
			browseButton.setEnabled(false);	
		}
		
		editor.minimumWidth = browseButton.getSize ().x;
		editor.horizontalAlignment = SWT.LEFT;
		itemButtonEditorMap.put(item, browseButton);
		editor.setEditor(browseButton, item, 2);
	}
	
	/**
	 * @param destinationURI
	 * @param item
	 */
	private void openDestinationSelector(String destinationURI, TableItem item) {
		Map<String, Destination> map = CommonIndexUtils.getAllDestinationsURIMaps(project.getName());
		Destination destination = null;
		if (destinationURI!= null && !destinationURI.isEmpty() && map.containsKey(destinationURI)) {
			destination = map.get(destinationURI);
		}
		TesterFilteredDestinationSelector picker = new TesterFilteredDestinationSelector(Display.getDefault().getActiveShell(),
				project.getName(),
				destination);
		if (picker.open() == Dialog.OK) {
			if(picker.getFirstResult() instanceof Destination) {
				destination = (Destination) picker.getFirstResult();
				DriverConfig driverConfig = destination.getDriverConfig();
				Channel channel = driverConfig.getChannel();
				StringBuilder sBuilder = new StringBuilder();
				String newChannelURI =  sBuilder.append(channel.getFolder()).append(channel.getName()).toString();
				sBuilder = new StringBuilder();
				String path = sBuilder.append(channel.getFolder())
						.append(channel.getName())
						.append("/")
						.append(destination.getName())
						.toString();
				item.setText(1, path);
			}
		}

	}

	public void setTestTableSelectEditableSupport(final Table table) {
		tableEditor = new TableEditor(table);
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		table.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final TableItem item = table.getItem(index);
					boolean editable = true;
					String fullPath = item.getText(0);
					fullPath = fullPath.substring(0, fullPath.indexOf(CommonIndexUtils.DOT));
					Entity entity = CommonIndexUtils.getEntity(project.getName(), fullPath);
					if (entity instanceof Concept) {
						editable = false;
					}
					if (entity instanceof SimpleEvent) {
						if (((SimpleEvent)entity).getDestination() != null ) {
							editable = false;
						}
					}
					
					if (getClusterName() == null) {
						editable = false;
					}
					
					for (int i = 0; i < table.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						final int editColumn = i;  
						if (i == 1) {
							if (rect.contains(pt)) {
								final int column = i;
								Control tmp = new Text(table, SWT.NONE);
								final Control text = tmp;
								text.addKeyListener(new KeyAdapter() {
									/* (non-Javadoc)
									 * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
									 */
									@Override
									public void keyReleased(KeyEvent e) {
										//if (e.stateMask == SWT.CTRL && e.keyCode == 'x') {
										//text.cut();
										//}
										if (e.stateMask == SWT.CTRL && e.keyCode == 'c') {
											if (text instanceof Text) {
												((Text) text).copy();
											} 
										}
										if (e.stateMask == SWT.CTRL && e.keyCode == 'v') {
											if (text instanceof Text) {
												((Text) text).paste();
											} 
										}
									}});
								if (text instanceof Text) {
									((Text) text).setEditable(editable);
								}
								Listener textListener = new Listener() {
									public void handleEvent(final org.eclipse.swt.widgets.Event e) {
										String oldText = null;
										String newText = null;
										switch (e.type) {
										case SWT.FocusOut:
											if (item.isDisposed()) {
												break;
											}
											oldText = item.getText(editColumn);
											String colText = "";
											if (text instanceof Text) {
												colText = ((Text) text).getText();
											} 
											item.setText(column, colText);
											newText = item.getText(editColumn);

											if (!isValidDestinationURI(oldText, newText)) {
												item.setText(column, oldText);
												text.dispose();
												break;
											}
											text.dispose();
											break;
										case SWT.Traverse:
											switch (e.detail) {
											case SWT.TRAVERSE_RETURN:
												if (item.isDisposed()) {
													break;
												}
												oldText = item.getText(editColumn);
												colText = "";
												if (text instanceof Text) {
													colText = ((Text) text).getText();
												} 
												item.setText(column, colText);
												newText = item.getText(editColumn);

												if (!isValidDestinationURI(oldText, newText)) {
													item.setText(column, oldText);
													text.dispose();
													break;
												}
												text.dispose();
												break;
												// FALL THROUGH
											case SWT.TRAVERSE_ESCAPE:
												text.dispose();
												e.doit = false;
											}
											break;
										}
									}
								};

								text.addListener(SWT.FocusOut, textListener);
								text.addListener(SWT.Traverse, textListener);

								tableEditor.setEditor(text, item, i);
								if (text instanceof Text) {
									((Text)text).setText(item.getText(i));
									((Text)text).selectAll();
								} 
								text.setFocus();
								return;
							}
							if (!visible && rect.intersects(clientArea)) {
								visible = true;
							}
						}
					}
					if (!visible) {
						return;
					}
					index++;
				}
			}
		});
	}
	
	/**
	 * @param oldText
	 * @param newText
	 * @return
	 */
	protected boolean isValidDestinationURI(String oldText, String newText) {
		if (!oldText.trim().equals(newText.trim())) {
			if (newText.isEmpty()) {
				return true;
			}
			
			if (!newText.isEmpty()) {
				Map<String, Destination> map = CommonIndexUtils.getAllDestinationsURIMaps(project.getName());
				if (map.containsKey(newText)) {
					return true;
				}
			}

		}
		return false;
	}
}