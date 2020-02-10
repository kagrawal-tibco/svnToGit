package com.tibco.cep.studio.ui.doc;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.epf.richtext.IRichText;
import org.eclipse.epf.richtext.IRichTextEditor;
import org.eclipse.epf.richtext.IRichTextToolBar;
import org.eclipse.epf.richtext.RichText;
import org.eclipse.epf.richtext.RichTextCommand;
import org.eclipse.epf.richtext.RichTextListener;
import org.eclipse.epf.richtext.RichTextResources;
import org.eclipse.epf.richtext.RichTextSelection;
import org.eclipse.epf.richtext.RichTextToolBar;
import org.eclipse.epf.richtext.actions.AddLinkAction;
import org.eclipse.epf.richtext.actions.AddOrderedListAction;
import org.eclipse.epf.richtext.actions.AddTableAction;
import org.eclipse.epf.richtext.actions.AddUnorderedListAction;
import org.eclipse.epf.richtext.actions.BoldAction;
import org.eclipse.epf.richtext.actions.ClearContentAction;
import org.eclipse.epf.richtext.actions.CopyAction;
import org.eclipse.epf.richtext.actions.CutAction;
import org.eclipse.epf.richtext.actions.FindReplaceAction;
import org.eclipse.epf.richtext.actions.FontNameAction;
import org.eclipse.epf.richtext.actions.FontSizeAction;
import org.eclipse.epf.richtext.actions.FontStyleAction;
import org.eclipse.epf.richtext.actions.IndentAction;
import org.eclipse.epf.richtext.actions.ItalicAction;
import org.eclipse.epf.richtext.actions.OutdentAction;
import org.eclipse.epf.richtext.actions.PasteAction;
import org.eclipse.epf.richtext.actions.PastePlainTextAction;
import org.eclipse.epf.richtext.actions.SubscriptAction;
import org.eclipse.epf.richtext.actions.SuperscriptAction;
import org.eclipse.epf.richtext.actions.TidyActionGroup;
import org.eclipse.epf.richtext.actions.UnderlineAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.ITextViewerExtension6;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.IUndoManagerExtension;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.HTMLTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.operations.OperationHistoryActionHandler;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.texteditor.IAbstractTextEditorHelpContextIds;
import org.eclipse.ui.texteditor.IReadOnlyDependent;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class ExtendedRichTextEditor implements IRichTextEditor, SelectionListener {

	protected static final String HTML_TAB_NAME = RichTextResources.htmlTab_text;
	protected boolean debug;
	protected String basePath;
	protected ViewForm form;
	protected IRichTextToolBar toolBar;
	protected Composite content;
	protected CTabFolder tabFolder;
	protected CTabItem richTextTab;
	protected CTabItem htmlTab;
	protected IRichText richText;
	protected TextViewer sourceViewer;
	protected IDocument currentDoc;
	protected DropTarget sourceEditDropTarget;
	protected Menu contextMenu;
	protected boolean sourceModified = false;
	protected boolean editable = true;
	private OperationHistoryActionHandler undoAction;
	private OperationHistoryActionHandler redoAction;
	private IEditorSite editorSite;

	/** The actions registered with the editor. */
	private Map<String, IAction> fActions= new HashMap<String, IAction>(10);
	
	/** The verify key listener for activation code triggering. */
	private ActivationCodeTrigger fActivationCodeTrigger= new ActivationCodeTrigger();
	
	/** The editor's action activation codes. */
	@SuppressWarnings("rawtypes")
	private List fActivationCodes= new ArrayList(2);
	
	final IUndoManager undoManager= new TextViewerUndoManager(10);
	
	protected static final String PREVIEW_TAB = "PREVIEW";
	protected static final String PREVIEW_TAB_TOOLTIP = "Preview";
	protected static final String EDIT_TAB = "Text";
	protected CTabItem previewTab;
	protected CustomBrowser browser;
	private String[] fKeyBindingScopes;
	
	private FontStyleAction fontStyleAction;
	private FontNameAction fontNameAction;
	private FontSizeAction fontSizeAction;
	
	private CutAction CutAction;
	private CopyAction CopyAction;
	private PasteAction PasteAction;
	private ClearContentAction ClearContentAction;
	private BoldAction BoldAction;
	private ItalicAction ItalicAction;
	private UnderlineAction UnderlineAction;
	private SubscriptAction SubscriptAction;
	private SuperscriptAction SuperscriptAction;
	
	private TidyActionGroup TidyActionGroup;
	private AddOrderedListAction AddOrderedListAction;
	private AddUnorderedListAction AddUnorderedListAction;
	private OutdentAction OutdentAction;
	private IndentAction IndentAction;
	
	private FindReplaceAction FindReplaceAction;
	private AddLinkAction AddLinkAction;
	private AddTableAction AddTableAction;
	
	private boolean showAddTable = true;
	
	/**
	 * @param parent
	 * @param style
	 * @param editorSite
	 */
	public ExtendedRichTextEditor(Composite parent, int style, IEditorSite editorSite, boolean showAddTable) {
		this(parent, style, editorSite, null, showAddTable);
	}

	/**
	 * @param parent
	 * @param style
	 * @param editorSite
	 * @param basePath
	 */
	public ExtendedRichTextEditor(Composite parent, int style, IEditorSite editorSite, String basePath, boolean showAddTable) {
		this.basePath = basePath;
		this.editorSite = editorSite;
		this.showAddTable = showAddTable;
		debug = StudioUIPlugin.getDefault().isDebugging();
		init(parent, style);
	}

	/**
	 * @param parent
	 * @param style
	 */
	protected void init(Composite parent, int style) {
		try {
			form = new ViewForm(parent, style);
			form.marginHeight = 0;
			form.marginWidth = 0;

			toolBar = new RichTextToolBar(form, SWT.FLAT, this);

			content = new Composite(form, SWT.FLAT);
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			content.setLayout(layout);

			tabFolder = createEditorTabFolder(content, style);
			
			tabFolder.addSelectionListener(this);

			//form.setTopCenter(((RichTextToolBar)toolBar).getToolbarMgr().getControl());
			form.setTopLeft(((RichTextToolBar)toolBar).getToolbarMgr().getControl());
			form.setContent(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLayoutData(Object layoutData) {
		if (form != null) {
			form.setLayoutData(layoutData);
		}
	}

	public Object getLayoutData() {
		if (form != null) {
			return form.getLayoutData();
		}
		return null;
	}

	/**
	 * Sets focus to this editor.
	 */
	public void setFocus() {
		if (richText != null) {
			richText.setFocus();
		}
		setSelection(0);
		if (toolBar != null && tabFolder != null) {
			toolBar.updateToolBar(editable);
		}

	}

	/**
	 * Tells the control it does not have focus.
	 */
	public void setBlur() {
		if (richText != null) {
			richText.setBlur();
		}
	}
	
	/**
	 * Checks whether this editor has focus.
	 * 
	 * @return <code>true</code> if this editor has the user-interface focus
	 */
	public boolean hasFocus() {
		if (richText != null) {
			return richText.hasFocus();
		}
		return false;
	}

	/**
	 * Selects the Rich Text or HTML tab.
	 * 
	 * @param index
	 *            <code>0</code> for the Rich Text tab, <code>1</code> for
	 *            the HTML tab.
	 */
	public void setSelection(int index) {
		if (tabFolder != null) {
			tabFolder.setSelection(index);
		}
	}

	/**
	 * Returns the base path used for resolving text and image links.
	 * 
	 * @return the base path used for resolving links specified with <href>,
	 *         <img>, etc.
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * Returns the base URL of the rich text control whose content was last
	 * copied to the clipboard.
	 * 
	 * @return the base URL of a rich text control
	 */
	public URL getCopyURL() {
		if (richText != null) {
			return richText.getCopyURL();
		}
		return null;
	}

	/**
	 * Sets the base URL of the rich text control whose content was last copied
	 * to the clipboard.
	 */
	public void setCopyURL() {
		if (richText != null) {
			richText.setCopyURL();
		}
	}

	/**
	 * Returns the editable state.
	 * 
	 * @return <code>true</code> if the content can be edited
	 */
	public boolean getEditable() {
		return editable;
	}

	/**
	 * Sets the editable state.
	 * 
	 * @param editable
	 *            the editable state
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
		if (toolBar != null && tabFolder != null) {
			toolBar.updateToolBar(editable);
		}
		if (richText != null) {
			richText.setEditable(editable);
		}
		if (sourceViewer != null) {
			sourceViewer.setEditable(editable);
		}
	}

	/**
	 * Checks whether the content has been modified.
	 * 
	 * @return <code>true</code> if the content has been modified
	 */
	public boolean getModified() {
		if (richText != null) {
			return richText.getModified();
		}
		return false;
	}

	/**
	 * Sets the modified state.
	 * 
	 * @param modified
	 *            the modified state
	 */
	public void setModified(boolean modified) {
		if (richText != null) {
			richText.setModified(modified);
		}
	}

	/**
	 * Returns the rich text content.
	 * 
	 * @return the rich text content formatted in XHTML
	 */
	public String getText() {
		if (sourceModified) {
			setText(getSourceEdit().getText());
			setModified(true);
			sourceModified = false;
		}
		if (richText != null) {
			return richText.getText();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Sets the rich text content.
	 * 
	 * @param text
	 *            the rich text content in XHTML format
	 */
	public void setText(String text) {
		if (richText != null) {
			richText.setText(text);
		}
		sourceModified = false;
		if (tabFolder != null) {
			if (toolBar != null) {
				toolBar.updateToolBar(editable);
			}
			if (getSourceEdit() != null) {
				removeModifyListeners();
				currentDoc.set(text);
				addModifyListeners();
			}
		}
	}
	
	protected void addModifyListeners() {
		if (currentDoc != null) {
			currentDoc.addDocumentListener(sourceEditDocumentListener);
		}
	}

	protected void removeModifyListeners() {
		if (currentDoc != null) {
			currentDoc.removeDocumentListener(sourceEditDocumentListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.epf.richtext.IRichText#checkModify()
	 */
	public void checkModify() {
		richText.checkModify();
		if (sourceModified) {
			notifyModifyListeners();
		}
		if (debug) {
			printDebugMessage("checkModify", "modified=" + sourceModified); //$NON-NLS-1$ //$NON-NLS-2$	
		}
	}

	/**
	 * Restores the rich text content back to the initial value.
	 */
	public void restoreText() {
		if (richText != null) {
			richText.restoreText();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.epf.richtext.IRichText#getSelected()
	 */
	public RichTextSelection getSelected() {
		if (tabFolder.getSelection() == htmlTab) {
			String HTMLsource = getSourceEdit().getText();
			Point sel = sourceViewer.getSelectedRange();
			int selStartIndex = sel.x;
			int selEndIndex = sel.x + sel.y - 1;
			richText.getSelected().clear();
			richText.getSelected().setText(HTMLsource.substring(selStartIndex, selEndIndex + 1));
		}
		return richText.getSelected();
	}

	/**
	 * Returns an application specific property value.
	 * 
	 * @param key
	 *            the name of the property
	 * @return the value of the property or <code>null</code> if it has not
	 *         been set
	 */
	public Object getData(String key) {
		if (richText != null) {
			richText.getData(key);
		}
		return null;
	}

	/**
	 * Sets an application specific property name and value.
	 * 
	 * @param key
	 *            the name of the property
	 * @param value
	 *            the new value for the property
	 */
	public void setData(String key, Object value) {
		if (richText != null) {
			richText.setData(key, value);
		}
	}

	/**
	 * Executes the given rich text command. The supported command strings are
	 * defined in <code>RichTextCommand<code>.
	 * 
	 * @param	command		a rich text command string
	 * @return	a status code returned by the executed command
	 */
	public int executeCommand(String command) {
		if (richText != null) {
			return richText.executeCommand(command);
		}
		return 0;
	}

	/**
	 * Executes the given rich text command with a single parameter. The
	 * supported command strings are defined in <code>RichTextCommand<code>.
	 * 
	 * @param	command		a rich text command string
	 * @param	param		a parameter for the command or <code>null</code>
	 * @return	a status code returned by the executed command
	 */
	public int executeCommand(String command, String param) {
		if (richText != null) {
			return richText.executeCommand(command, param);
		}
		return 0;
	}

	/**
	 * Executes the given rich text command with an array of parameters. The
	 * supported command strings are defined in <code>RichTextCommand<code>.
	 * 
	 * @param	command		a rich text command string
	 * @param	params		an array of parameters for the command or <code>null</code>
	 * @return	a status code returned by the executed command
	 */
	public int executeCommand(String command, String[] params) {
		if (richText != null) {
			return richText.executeCommand(command, params);
		}
		return 0;
	}

	/**
	 * Disposes the operating system resources allocated by this editor.
	 */
	public void dispose() {
		if (contextMenu != null && !contextMenu.isDisposed()) {
			contextMenu.dispose();
			contextMenu = null;
		}
		if (sourceEditDropTarget != null) {
			sourceEditDropTarget.dispose();
			sourceEditDropTarget = null;
		}
		if (fActivationCodeTrigger != null) {
			fActivationCodeTrigger.uninstall();
			fActivationCodeTrigger= null;
		}
		removeModifyListeners();
		if (getSourceEdit() != null) {
			getSourceEdit().removeListener(SWT.Deactivate,
					sourceEditDeactivateListener);
			getSourceEdit().removeKeyListener(sourceEditKeyListener);
			sourceEditDeactivateListener = null;
			sourceEditKeyListener = null;
		}
		
		if (sourceViewer != null) {
			sourceViewer= null;
		}

		if (fActions != null) {
			fActions.clear();
			fActions= null;
		}
		if (fActivationCodes != null) {
			fActivationCodes.clear();
			fActivationCodes= null;
		}

		if (richText != null) {
			richText.dispose();
			richText = null;
		}
	}

	/**
	 * Checks whether this control has been disposed.
	 * 
	 * @return <code>true</code> if this control is disposed successfully
	 */
	public boolean isDisposed() {
		if (richText != null) {
			return richText.isDisposed();
		}
		return true;
	}

	/**
	 * Returns the modify listeners attached to this editor.
	 * 
	 * @return an iterator for retrieving the modify listeners
	 */
	public Iterator<ModifyListener> getModifyListeners() {
		if (richText != null) {
			richText.getModifyListeners();
		}
		return null;
	}

	/**
	 * Adds a listener to the collection of listeners who will be notified when
	 * keys are pressed and released within this editor.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 */
	public void addKeyListener(KeyListener listener) {
		if (richText != null) {
			richText.addKeyListener(listener);
		}
	}

	/**
	 * Removes a listener from the collection of listeners who will be notified
	 * when keys are pressed and released within this editor.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 */
	public void removeKeyListener(KeyListener listener) {
		if (richText != null) {
			richText.removeKeyListener(listener);
		}
	}

	/**
	 * Adds a listener to the collection of listeners who will be notified when
	 * the content of this editor is modified.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 */
	public void addModifyListener(ModifyListener listener) {
		if (richText != null) {
			richText.addModifyListener(listener);
		}
	}

	/**
	 * Removes a listener from the collection of listeners who will be notified
	 * when the content of this editor is modified.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 */
	public void removeModifyListener(ModifyListener listener) {
		if (richText != null) {
			richText.removeModifyListener(listener);
		}
	}

	/**
	 * Adds the listener to the collection of listeners who will be notifed when
	 * this editor is disposed.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 */
	public void addDisposeListener(DisposeListener listener) {
		if (richText != null) {
			richText.addDisposeListener(listener);
		}
	}

	/**
	 * Removes a listener from the collection of listeners who will be notified
	 * when this editor is disposed.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 */
	public void removeDisposeListener(DisposeListener listener) {
		if (richText != null) {
			richText.removeDisposeListener(listener);
		}
	}

	/**
	 * Adds a listener to the collection of listeners who will be notified when
	 * help events are generated for this editor.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 */
	public void addHelpListener(HelpListener listener) {
		if (richText != null) {
			richText.addHelpListener(listener);
		}
	}

	/**
	 * Removes a listener from the collection of listeners who will be notified
	 * when help events are generated for this editor.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 */
	public void removeHelpListener(HelpListener listener) {
		if (richText != null) {
			richText.removeHelpListener(listener);
		}
	}

	/**
	 * Adds the listener to the collection of listeners who will be notifed when
	 * an event of the given type occurs within this editor.
	 * 
	 * @param eventType
	 *            the type of event to listen for
	 * @param listener
	 *            the listener which should be notified when the event occurs
	 */
	public void addListener(int eventType, Listener listener) {
		if (richText != null) {
			richText.addListener(eventType, listener);
		}
	}

	/**
	 * Removes the listener from the collection of listeners who will be notifed
	 * when an event of the given type occurs within this editor.
	 * 
	 * @param eventType
	 *            the type of event to listen for
	 * @param listener
	 *            the listener which should no longer be notified when the event
	 *            occurs
	 */
	public void removeListener(int eventType, Listener listener) {
		if (richText != null) {
			richText.removeListener(eventType, listener);
		}
	}

	/**
	 * Returns the event listeners attached to this editor.
	 * 
	 * @return an iterator for retrieving the event listeners attached to this
	 *         editor
	 */
	public Iterator<RichTextListener> getListeners() {
		if (richText != null) {
			return richText.getListeners();
		}
		return null;
	}

	/**
	 * Notifies the modify listeners that the rich text editor content has
	 * changed.
	 */
	public void notifyModifyListeners() {
		if (richText != null) {
			Event event = new Event();
			event.display = Display.getCurrent();
			event.widget = richText.getControl();

			for (Iterator<ModifyListener> i = getModifyListeners(); i != null && i.hasNext();) {
				ModifyListener listener = i.next();
				listener.modifyText(new ModifyEvent(event));
			}
		}
	}

	/**
	 * Creates the underlying rich text control.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param style
	 *            the style for the control
	 * @param basePath
	 *            the path used for resolving links
	 */
	protected IRichText createRichTextControl(Composite parent, int style,
			String basePath) {
		return new ExtendedRichText(parent, style, basePath);
	}

	/**
	 * Creates the editor tab folder.
	 * 
	 * @param parent
	 *            the parent control
	 * @param style
	 *            the style for the control
	 * @return a new editor toolbar
	 */
	protected CTabFolder createEditorTabFolder(Composite parent, int style) {
		CTabFolder folder = new CTabFolder(parent, SWT.FLAT | SWT.BOTTOM);
		folder.setLayout(new GridLayout(1, true));
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite richTextComposite = new Composite(folder, SWT.FLAT);
		GridLayout richTextCompositeLayout = new GridLayout(1, false);
		richTextCompositeLayout.marginHeight = 0;
		richTextCompositeLayout.marginWidth = 0;
		richTextComposite.setLayout(richTextCompositeLayout);
		richTextComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		richText = createRichTextControl(richTextComposite, style, basePath);
		richText.setData(PROPERTY_NAME, this);
		richText.getFindReplaceAction().setRichText(this);

		richTextTab = new CTabItem(folder, SWT.FLAT);
		richTextTab.setText(EDIT_TAB);
		richTextTab.setToolTipText(RichTextResources.richTextTab_toolTipText);
		richTextTab.setControl(richTextComposite);

		Composite htmlComposite = new Composite(folder, SWT.FLAT);
		htmlComposite.setLayout(new GridLayout());

		sourceViewer = new TextViewer(htmlComposite, SWT.FLAT | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		sourceViewer.setUndoManager(undoManager);
		setDocument(null);
		addModifyListeners();
		getSourceEdit().addListener(SWT.Deactivate, sourceEditDeactivateListener);
		getSourceEdit().addKeyListener(sourceEditKeyListener);
		
//		contextMenu = new Menu(parent.getShell(), SWT.POP_UP);
//		getSourceEdit().setMenu(contextMenu);
//		fillContextMenu(contextMenu);
		
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 400;
		data.heightHint = 200;
		sourceViewer.getTextWidget().setLayoutData(data);
		
		sourceViewer.setEditable(true);
		
		htmlTab = new CTabItem(folder, SWT.NONE);
		htmlTab.setText(HTML_TAB_NAME);
		htmlTab.setToolTipText(RichTextResources.htmlTab_toolTipText); 
		htmlTab.setControl(htmlComposite);

		Composite browserComposite = new Composite(folder, SWT.NO_FOCUS);
		browserComposite.setLayout(new GridLayout());

		browser = new CustomBrowser(browserComposite, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 400;
		data.heightHint = 200;
		browser.setLayoutData(data);

		previewTab = new CTabItem(folder, SWT.NONE);
		previewTab.setText(PREVIEW_TAB);
		previewTab.setToolTipText(PREVIEW_TAB); 
		previewTab.setControl(browserComposite);
		
		fillToolBar(toolBar);
		
		initializeActivationCodeTrigger();
		createActions();
		
		folder.setSelection(0);

		//Setting Default Font Style/Name/Size in the toolbar
		fontStyleAction.getCCombo().select(0);
		fontNameAction.getCCombo().select(41);
		fontSizeAction.getCCombo().select(2);
		
		return folder;
	}

	
	private void setDocument(IDocument doc) {
		if (doc == null) {
			doc = new Document();
		}
		// clean up old doc
		undoManager.disconnect();
		IDocument oldDoc = sourceViewer.getDocument();
		if (oldDoc != null) {
			oldDoc.removeDocumentListener(sourceEditDocumentListener);
		}

		// hook up new doc
		currentDoc = doc;
		sourceViewer.setDocument(currentDoc);
		currentDoc.addDocumentListener(sourceEditDocumentListener);
		undoManager.connect(sourceViewer);
		if (undoAction != null) {
			undoAction.setContext(getUndoContext());
		}
		if (redoAction != null) {
			redoAction.setContext(getUndoContext());
		}
	}
	
	/**
	 * Returns the HTML source edit control.
	 * 
	 * @return a <code>StyleText</code> object.
	 */
	public StyledText getSourceEdit() {
		if (sourceViewer != null) {
			return sourceViewer.getTextWidget();
		}
		return null;
	}

	/**
	 * Inserts text at the selection (overwriting the selection).
	 */
	public void addHTML(String text) {
		if (text == null || text.length() == 0) 
			return;
		if (tabFolder.getSelection() == richTextTab) {
			executeCommand(RichTextCommand.ADD_HTML, text);
		} else if (tabFolder.getSelection() == htmlTab) {
			String oldHTML = getSourceEdit().getText();
			Point sel = sourceViewer.getSelectedRange();
			int selStartIndex = sel.x;
			int selEndIndex = sel.x + sel.y - 1;
			String newHTML = oldHTML.substring(0, selStartIndex) + text
					+ oldHTML.substring(selEndIndex + 1);
			removeModifyListeners();
			currentDoc.set(newHTML);
			addModifyListeners();
			updateRichText(newHTML);
		}
	}

	
	/**
	 * Inserts an image at the selection (overwriting the selection).
	 */
	public void addImage(String imageURL, String height, String width, String altTag) {
		if (tabFolder.getSelection() == richTextTab) {
			executeCommand(
					RichTextCommand.ADD_IMAGE,
					new String[] {
							imageURL,
							height, width, altTag });
		} else if (tabFolder.getSelection() == htmlTab) {
			StringBuffer imageLink = new StringBuffer();
			// order of these attributes is the same as JTidy'ed HTML
			imageLink.append("<img"); //$NON-NLS-1$
			if (height.length() > 0) {
				imageLink.append(" height=\"" + height + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (altTag.length() > 0) {
				imageLink.append(" alt=\"" + altTag + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			imageLink.append(" src=\"" + imageURL + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			if (width.length() > 0) {
				imageLink.append(" width=\"" + width + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			imageLink.append(" />"); //$NON-NLS-1$
			String oldHTML = getSourceEdit().getText();
			Point sel = sourceViewer.getSelectedRange();
			int selStartIndex = sel.x;
			int selEndIndex = sel.x + sel.y - 1;
			String newHTML = oldHTML.substring(0, selStartIndex) + imageLink.toString()
					+ oldHTML.substring(selEndIndex + 1);
			removeModifyListeners();
			currentDoc.set(newHTML);
			addModifyListeners();
			updateRichText(newHTML);
		}
	}

	/**
	 * Checks whether the HTML tab is selected.
	 * 
	 * @return <code>true</code> if the HTML tab is selected.
	 */
	public boolean isHTMLTabSelected() {
		return (tabFolder.getSelection() == htmlTab);
	}

	/**
	 * Fills the context menu with menu items.
	 * 
	 * @param contextMenu
	 *            a context menu containing rich text actions
	 */
	protected void fillContextMenu(Menu contextMenu) {
		final MenuItem cutItem = new MenuItem(contextMenu, SWT.PUSH);
		cutItem.setText(RichTextResources.cutAction_text);
		cutItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				CutAction action = new CutAction(ExtendedRichTextEditor.this);
				action.execute(ExtendedRichTextEditor.this);
			}
		});
		final MenuItem copyItem = new MenuItem(contextMenu, SWT.PUSH);
		copyItem.setText(RichTextResources.copyAction_text); 
		copyItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				CopyAction action = new CopyAction(ExtendedRichTextEditor.this);
				action.execute(ExtendedRichTextEditor.this);
			}
		});
		final MenuItem pasteItem = new MenuItem(contextMenu, SWT.PUSH);
		pasteItem.setText(RichTextResources.pasteAction_text); 
		pasteItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				PasteAction action = new PasteAction(ExtendedRichTextEditor.this);
				action.execute(ExtendedRichTextEditor.this);
			}
		});
		
		final MenuItem pastePlainTextItem = new MenuItem(contextMenu, SWT.PUSH);
		pastePlainTextItem.setText(RichTextResources.pastePlainTextAction_text);
		pastePlainTextItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				PastePlainTextAction action = new PastePlainTextAction(ExtendedRichTextEditor.this);
				action.execute(ExtendedRichTextEditor.this);
			}
		});

		contextMenu.addMenuListener(new MenuListener() {
			public void menuHidden(MenuEvent e) {
			}

			public void menuShown(MenuEvent e) {
				String selectedText = getSelected().getText();
				boolean selection = selectedText.length() > 0;
				cutItem.setEnabled(editable && selection);
				copyItem.setEnabled(selection);
				pasteItem.setEnabled(editable);
				pastePlainTextItem.setEnabled(editable);
			}
		});
	}

	/**
	 * Updates the content of the rich text control without updating the HTML
	 * source editor.
	 * <p>
	 * This method should be called by the HTML source editor to sync up its
	 * content with the rich text control.
	 * 
	 * @param text
	 *            the rich text content in XHTML format
	 */
	private void updateRichText(String text) {
		if (richText != null) {
			richText.setText(text);
			richText.checkModify();
		}
		sourceModified = false;
		if (tabFolder != null) {
			if (toolBar != null) {
				toolBar.updateToolBar(editable);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void addDropSupportToStyledText() {
		// this function based heavily on the example at:
		// http://www.eclipse.org/articles/Article-SWT-DND/DND-in-SWT.html
		
		 	// Allow data to be copied to the drop target
			int operations = DND.DROP_MOVE |  DND.DROP_COPY | DND.DROP_DEFAULT;
			sourceEditDropTarget = new DropTarget(getSourceEdit(), operations);
		 
			// Receive data in Text or HTML format
			final TextTransfer textTransfer = TextTransfer.getInstance();
			final HTMLTransfer htmlTransfer = HTMLTransfer.getInstance();
			Transfer[] types = new Transfer[] {htmlTransfer, textTransfer};
			sourceEditDropTarget.setTransfer(types);
			 
			sourceEditDropTarget.addDropListener(new DropTargetListener() {
			  public void dragEnter(DropTargetEvent event) {
			     if (event.detail == DND.DROP_DEFAULT) {
			         if ((event.operations & DND.DROP_COPY) != 0) {
			             event.detail = DND.DROP_COPY;
			         } else {
			             event.detail = DND.DROP_NONE;
			         }
			     }
			     if (!getEditable()) {
		             event.detail = DND.DROP_NONE;
			     }
			     // will accept text but prefer to have HTML dropped
			     for (int i = 0; i < event.dataTypes.length; i++) {
			         if (htmlTransfer.isSupportedType(event.dataTypes[i])){
			             event.currentDataType = event.dataTypes[i];
			             break;
			         }
			     }
			   }
			   public void dragOver(DropTargetEvent event) {
			        event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_INSERT_AFTER | DND.FEEDBACK_SCROLL;
			    }
			    public void dragOperationChanged(DropTargetEvent event) {
			        if (event.detail == DND.DROP_DEFAULT) {
			            if ((event.operations & DND.DROP_COPY) != 0) {
			                event.detail = DND.DROP_COPY;
			            } else {
			                event.detail = DND.DROP_NONE;
			            }
			        }
			    }
			    public void dragLeave(DropTargetEvent event) {
			    }
			    public void dropAccept(DropTargetEvent event) {
			    }
			    public void drop(DropTargetEvent event) {
			        if (textTransfer.isSupportedType(event.currentDataType) || 
			        		htmlTransfer.isSupportedType(event.currentDataType)) {
			            String text = (String)event.data;
			            addHTML(text);
			        }
			    }
			});
	}
	
	/**
	 * Displays the given debug message to the console.
	 */
	private void printDebugMessage(String method, String msg, String text) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("RichTextEditor[").append(
			"RichTextControlHandle" /* richText.getControl().handle */).append(']') //$NON-NLS-1$
				.append('.').append(method);

		if (msg != null && msg.length() > 0) {
			strBuf.append(": ").append(msg); //$NON-NLS-1$
		}
		if (text != null && text.length() > 0) {
			strBuf.append('\n').append(text);
		}
		System.out.println(strBuf);
	}

	/**
	 * Displays the given debug message to the console.
	 */
	private void printDebugMessage(String method, String msg) {
		printDebugMessage(method, msg, null);
	}
	
	public FindReplaceAction getFindReplaceAction() {
		return richText.getFindReplaceAction();
	}

	public void setFindReplaceAction(FindReplaceAction findReplaceAction) {
		if (richText != null) {
			richText.setFindReplaceAction(findReplaceAction);
			richText.getFindReplaceAction().setRichText(this);

		}
	}
	
	public void setInitialText(String text) {
		if (richText != null) {
			richText.setInitialText(text);
		}
		if (getSourceEdit() != null) {
			removeModifyListeners();
			setDocument(new Document(text));
			addModifyListeners();
		}

	}
	
	
	/**
	 * from org.eclipse.ui.texteditor.AbstractTextEditor#getUndoContext()
	 * Returns this editor's viewer's undo manager undo context.
	 *
	 * @return the undo context or <code>null</code> if not available
	 */
	private IUndoContext getUndoContext() {
		if (sourceViewer instanceof ITextViewerExtension6) {
			IUndoManager undoManager= ((ITextViewerExtension6)sourceViewer).getUndoManager();
			if (undoManager instanceof IUndoManagerExtension)
				return ((IUndoManagerExtension)undoManager).getUndoContext();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	protected void createActions() {
		createUndoRedoActions();
		// select all
		Action selectAllAction = new Action() {
			@Override
			public void run() {
				getSourceEdit().selectAll();
			}
		};
		selectAllAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.SELECT_ALL);
		registerAction(ActionFactory.SELECT_ALL.getId(), selectAllAction);
	}

	/*
	 * from org.eclipse.ui.texteditor.AbstractTextEditor#createUndoRedoActions()
	 */
	@SuppressWarnings("deprecation")
	protected void createUndoRedoActions() {
		IUndoContext undoContext= getUndoContext();
		if (undoContext != null) {
			// Use actions provided by global undo/redo
			
			// Create the undo action
			undoAction= new UndoActionHandler(getEditorSite(), undoContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(undoAction, IAbstractTextEditorHelpContextIds.UNDO_ACTION);
			undoAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.UNDO);
			registerAction(ITextEditorActionConstants.UNDO, undoAction);

			// Create the redo action.
			redoAction= new RedoActionHandler(getEditorSite(), undoContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(redoAction, IAbstractTextEditorHelpContextIds.REDO_ACTION);
			redoAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.REDO);
			registerAction(ITextEditorActionConstants.REDO, redoAction);
		}
	}
	
	private IEditorSite getEditorSite() {
		return editorSite;
	}


	/**
	 * Registers the given undo/redo action under the given ID and
	 * ensures that previously installed actions get disposed. It
	 * also takes care of re-registering the new action with the
	 * global action handler.
	 * 
	 * @param actionId	the action id under which to register the action
	 * @param action	the action to register
	 */
	private void registerAction(String actionId, IAction action) {
		IAction oldAction= getAction(actionId);
		if (oldAction instanceof OperationHistoryActionHandler)
			((OperationHistoryActionHandler)oldAction).dispose();

		setAction(actionId, action);
		
		IActionBars actionBars= getEditorSite().getActionBars();
		if (actionBars != null)
			actionBars.setGlobalActionHandler(actionId, action);
	}

	
	/*
	 * @see ITextEditor#getAction(String)
	 */
	public IAction getAction(String actionID) {
		assert actionID != null;
		IAction action= (IAction) fActions.get(actionID);

//		if (action == null) {
//			action= findContributedAction(actionID);
//			if (action != null)
//				setAction(actionID, action);
//		}

		return action;
	}

	/*
	 * @see ITextEditor#setAction(String, IAction)
	 */
	public void setAction(String actionID, IAction action) {
		assert actionID != null;
		if (action == null) {
			action= (IAction) fActions.remove(actionID);
			if (action != null)
				fActivationCodeTrigger.unregisterActionFromKeyActivation(action);
		} else {
			fActions.put(actionID, action);
			fActivationCodeTrigger.registerActionForKeyActivation(action);
		}
	}
	
	/**
	 * Initializes the activation code trigger.
	 *
	 */
	private void initializeActivationCodeTrigger() {
		fActivationCodeTrigger.install();
		fActivationCodeTrigger.setScopes(fKeyBindingScopes);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epf.richtext.RichTextEditor#fillToolBar(org.eclipse.epf.richtext.IRichTextToolBar)
	 */
	@Override
	public void fillToolBar(IRichTextToolBar toolBar) {
		fontStyleAction = new FontStyleAction(this);
		fontNameAction = new FontNameAction(this);
		fontSizeAction = new FontSizeAction(this);
		
		toolBar.getToolbarMgr().add(fontStyleAction);
		toolBar.getToolbarMgr().add(fontNameAction);
		toolBar.getToolbarMgr().add(fontSizeAction);
		toolBar.addSeparator();
		
		CutAction = new CutAction(this);
		CopyAction = new CopyAction(this);
		PasteAction = new PasteAction(this){
			@Override
			public void execute(IRichText richText) {
				PastePlainTextAction action = new PastePlainTextAction(ExtendedRichTextEditor.this);
				action.execute(ExtendedRichTextEditor.this);
			}
		};
		ClearContentAction = new ClearContentAction(this);
		BoldAction = new BoldAction(this);
		ItalicAction = new ItalicAction(this);
		UnderlineAction = new UnderlineAction(this);
		SubscriptAction = new SubscriptAction(this);
		SuperscriptAction = new SuperscriptAction(this);

		TidyActionGroup = new TidyActionGroup(this);
		AddOrderedListAction = new AddOrderedListAction(this);
		AddUnorderedListAction = new AddUnorderedListAction(this);
		OutdentAction = new OutdentAction(this);
		IndentAction = new IndentAction(this);

		FindReplaceAction = new FindReplaceAction(this) {
			@Override
			public void execute(IRichText richText) {
				richText.getFindReplaceAction().execute(richText);
			}
		};
		AddLinkAction = new AddLinkAction(this);
		
		if(showAddTable) { 
			AddTableAction = new AddTableAction(this);
		}
		
		toolBar.addAction(CutAction);
		toolBar.addAction(CopyAction);
		toolBar.addAction(PasteAction);
		toolBar.addSeparator();
		toolBar.addAction(ClearContentAction);
		toolBar.addSeparator();
		toolBar.addAction(BoldAction);
		toolBar.addAction(ItalicAction);
		toolBar.addAction(UnderlineAction);
		toolBar.addSeparator();
		toolBar.addAction(SubscriptAction);
		toolBar.addAction(SuperscriptAction);
		toolBar.addSeparator();
		toolBar.addAction(TidyActionGroup);
		toolBar.addSeparator();
		toolBar.addAction(AddOrderedListAction);
		toolBar.addAction(AddUnorderedListAction);
		toolBar.addSeparator();
		toolBar.addAction(OutdentAction);
		toolBar.addAction(IndentAction);
		toolBar.addSeparator();
		toolBar.addAction(FindReplaceAction);
		toolBar.addSeparator();
		toolBar.addAction(AddLinkAction);
		
		//TODO: below hiding for now, feature enable 
		//this later for extended usage with emf model binding
//		toolBar.addAction(new AddImageAction(this)); 
		if(showAddTable) { 
			toolBar.addAction(AddTableAction);
		}
	}
	
	public void addKeyListener (KeyAdapter adapter) {
		if (richText instanceof ExtendedRichText) {
			ExtendedRichText field = (ExtendedRichText)richText;
			if (field != null 
					&& field.getEditorControl() != null 
					&& !field.getEditorControl().isDisposed()) {
				field.getEditorControl().addKeyListener(adapter);
			}
		}
	}
	
	public void addFocusListener (FocusListener adapter) {
		if (richText instanceof ExtendedRichText) {
			ExtendedRichText field = (ExtendedRichText)richText;
			if (field != null 
					&& field.getEditorControl() != null 
					&& !field.getEditorControl().isDisposed()) {
				field.getEditorControl().addFocusListener(adapter);
			}
		}
		if (getSourceEdit() != null) {
			getSourceEdit().addFocusListener(adapter);
		}
	}
	
	public void removeFocusListener (FocusListener adapter) {
		if (richText instanceof ExtendedRichText) {
			ExtendedRichText field = (ExtendedRichText)richText;
			if (field != null 
					&& field.getEditorControl() != null 
					&& !field.getEditorControl().isDisposed()) {
				field.getEditorControl().removeFocusListener(adapter);
			}
		}
		if (getSourceEdit() != null) {
			getSourceEdit().removeFocusListener(adapter);
		}
	}
	
	public Control getEditorControl() {
		if (richText instanceof ExtendedRichText) {
			ExtendedRichText field = (ExtendedRichText)richText;
			if (field.getEditorControl() != null) {
				return field.getEditorControl();
			}
		}
		return null;
	}
	
	public Control getControl() {
		return form;
	}

	public IRichText getRichTextControl() {
		return richText;
	}
	
	public ExtendedRichText getTextControl() {
		return (ExtendedRichText)richText;
	}

	public String getFormattedText() {
		return richText.getText();
	}

	protected IDocumentListener sourceEditDocumentListener= new IDocumentListener() {
		public void documentAboutToBeChanged(DocumentEvent event) {
		}
		public void documentChanged(DocumentEvent event) {
			sourceModified = true;
			if (richText != null && richText instanceof RichText) {
				richText.notifyModifyListeners();
			}
		}
	};


	// The deactivate listener for the sourceEdit control.
	protected Listener sourceEditDeactivateListener = new Listener() {
		public void handleEvent(Event event) {
			if (sourceModified) {
				updateRichText(sourceViewer.getTextWidget().getText());
				setModified(true);
				sourceModified = false;
			}
		}
	};

	// The key listener for the sourceEdit control.
	protected KeyListener sourceEditKeyListener = new KeyListener() {
		public void keyPressed(KeyEvent e) {
			Object adapter = PlatformUI.getWorkbench().getAdapter(
					IBindingService.class);
			if (adapter != null && adapter instanceof IBindingService) {
				int accel = SWTKeySupport
						.convertEventToUnmodifiedAccelerator(e);
				KeyStroke stroke = SWTKeySupport
						.convertAcceleratorToKeyStroke(accel);
				KeySequence seq = KeySequence.getInstance(stroke);
				Binding bind = ((IBindingService) adapter).getPerfectMatch(seq);
				if (bind != null) {
					ParameterizedCommand command = bind
							.getParameterizedCommand();
					if (command != null) {
						String cmdId = command.getId();
						if (cmdId != null
								&& cmdId
										.equals("org.eclipse.ui.edit.findReplace")) { //$NON-NLS-1$
							richText.getFindReplaceAction().execute(ExtendedRichTextEditor.this);
						}
					}
				}
			}
		}

		public void keyReleased(KeyEvent e) {
		}
	};

	/**
	 * Internal key verify listener for triggering action activation codes.
	 */
	class ActivationCodeTrigger implements VerifyKeyListener {

		/** Indicates whether this trigger has been installed. */
		private boolean fIsInstalled= false;
		/**
		 * The key binding service to use.
		 */
		@SuppressWarnings("deprecation")
		private org.eclipse.ui.IKeyBindingService fKeyBindingService;
		
		@SuppressWarnings("unused")
		private IHandlerService handlerService;
		
		/*
		 * @see VerifyKeyListener#verifyKey(org.eclipse.swt.events.VerifyEvent)
		 */
		public void verifyKey(VerifyEvent event) {

			ActionActivationCode code= null;
			int size= fActivationCodes.size();
			for (int i= 0; i < size; i++) {
				code= (ActionActivationCode) fActivationCodes.get(i);
				if (code.matches(event)) {
					IAction action= getAction(code.fActionId);
					if (action != null) {

						if (action instanceof IUpdate)
							((IUpdate) action).update();

						if (!action.isEnabled() && action instanceof IReadOnlyDependent) {
							IReadOnlyDependent dependent= (IReadOnlyDependent) action;
							boolean writable= dependent.isEnabled(true);
							if (writable) {
								event.doit= false;
								return;
							}
						} else if (action.isEnabled()) {
							event.doit= false;
							action.run();
							return;
						}
					}
				}
			}
		}

		@SuppressWarnings("deprecation")
		public void install() {
			if (!fIsInstalled) {
				if (sourceViewer instanceof ITextViewerExtension) {
					ITextViewerExtension e= (ITextViewerExtension) sourceViewer;
					e.prependVerifyKeyListener(this);
				} else {
					StyledText text= sourceViewer.getTextWidget();
					text.addVerifyKeyListener(this);
				}
//				IWorkbenchWindow window = getEditorSite().getWorkbenchWindow();
//				handlerService = (IHandlerService) window.getService(IHandlerService.class);
				 
				fKeyBindingService= getEditorSite().getKeyBindingService();
				fIsInstalled= true;
			}
		}

		public void uninstall() {
			if (fIsInstalled) {

				if (sourceViewer instanceof ITextViewerExtension) {
					ITextViewerExtension e= (ITextViewerExtension) sourceViewer;
					e.removeVerifyKeyListener(this);
				} else if (sourceViewer != null) {
					StyledText text= sourceViewer.getTextWidget();
					if (text != null && !text.isDisposed())
						text.removeVerifyKeyListener(fActivationCodeTrigger);
				}

				fIsInstalled= false;
				fKeyBindingService= null;
//				handlerService = null;
			}
		}

		/**
		 * Registers the given action for key activation.
		 * @param action the action to be registered
		 */
		@SuppressWarnings("deprecation")
		public void registerActionForKeyActivation(IAction action) {
			if (action.getActionDefinitionId() != null)
				fKeyBindingService.registerAction(action);
//			handlerService.activateHandler(action.getActionDefinitionId(), new ActionHandler(action));
		}

		/**
		 * The given action is no longer available for key activation
		 * @param action the action to be unregistered
		 */
		@SuppressWarnings("deprecation")
		public void unregisterActionFromKeyActivation(IAction action) {
			if (action.getActionDefinitionId() != null)
				fKeyBindingService.unregisterAction(action);
//			handlerService.activateHandler(action.getActionDefinitionId(),new ActionHandler(action));
		}

		/**
		 * Sets the key binding scopes for this editor.
		 * @param keyBindingScopes the key binding scopes
		 */
		public void setScopes(String[] keyBindingScopes) {
			if (keyBindingScopes != null && keyBindingScopes.length > 0) {
//				fKeyBindingService.setScopes(keyBindingScopes);
			}
		}
	}
	
	/**
	 * Representation of action activation codes.
	 */
	static class ActionActivationCode {

		/** The action id. */
		public String fActionId;
		/** The character. */
		public char fCharacter;
		/** The key code. */
		public int fKeyCode= -1;
		/** The state mask. */
		public int fStateMask= SWT.DEFAULT;

		/**
		 * Creates a new action activation code for the given action id.
		 * @param actionId the action id
		 */
		public ActionActivationCode(String actionId) {
			fActionId= actionId;
		}

		/**
		 * Returns <code>true</code> if this activation code matches the given verify event.
		 * @param event the event to test for matching
		 * @return whether this activation code matches <code>event</code>
		 */
		public boolean matches(VerifyEvent event) {
			return (event.character == fCharacter &&
						(fKeyCode == -1 || event.keyCode == fKeyCode) &&
						(fStateMask == SWT.DEFAULT || event.stateMask == fStateMask));
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (tabFolder.getSelection() == htmlTab || tabFolder.getSelection() == previewTab) {
			removeModifyListeners();
			String text = getText();
			currentDoc.set(text);
			sourceModified = false;
			addModifyListeners();
			if (toolBar != null) {
				toolBar.updateToolBar(editable);
			}
			if (tabFolder.getSelection() == previewTab) {
//				String html = HtmlFormatter.convertToDisplayHtml(getText());
				browser.setHtml(currentDoc.get());
			}
			((RichTextToolBar)toolBar).getToolbarMgr().getControl().setEnabled(false);
			((RichTextToolBar)toolBar).getToolbarMgrCombo().getControl().setEnabled(false);
			
			if (tabFolder.getSelection() == htmlTab) {
				additionalToolBarEnable();
			}
			
			
		} else if (tabFolder.getSelection() == richTextTab ) { 
			((RichTextToolBar)toolBar).getToolbarMgr().getControl().setEnabled(true);
			((RichTextToolBar)toolBar).getToolbarMgrCombo().getControl().setEnabled(true);
			updateRichText(getSourceEdit().getText());
			setModified(true);
			if (toolBar != null) {
				toolBar.updateToolBar(editable);
			}
		}
	}
	
	public void additionalToolBarEnable() {

	}
	
	public void enableToolBar(boolean enabled) {
		fontStyleAction.setEnabled(enabled);
		fontNameAction.setEnabled(enabled);
		fontSizeAction.setEnabled(enabled);
		
		CutAction.setEnabled(enabled);
		CopyAction.setEnabled(enabled);
		PasteAction.setEnabled(enabled);
		ClearContentAction.setEnabled(enabled);
		BoldAction.setEnabled(enabled);
		ItalicAction.setEnabled(enabled);
		UnderlineAction.setEnabled(enabled);
		SubscriptAction.setEnabled(enabled);
		SuperscriptAction.setEnabled(enabled);
		
		TidyActionGroup.setEnabled(enabled);
		AddOrderedListAction.setEnabled(enabled);
		AddUnorderedListAction.setEnabled(enabled);
		OutdentAction.setEnabled(enabled);
		IndentAction.setEnabled(enabled);
		
		FindReplaceAction.setEnabled(enabled);
		AddLinkAction.setEnabled(enabled);
		if (AddTableAction != null) {
			AddTableAction.setEnabled(enabled);
		}
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public IRichTextToolBar getToolBar() {
		return toolBar;
	}

}