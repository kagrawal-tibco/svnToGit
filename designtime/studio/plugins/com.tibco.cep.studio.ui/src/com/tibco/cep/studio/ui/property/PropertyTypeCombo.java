package com.tibco.cep.studio.ui.property;


import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleTextAdapter;
import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * 
 * @author sasahoo
 *
 */
public class PropertyTypeCombo extends Composite {
    boolean noSelection, ignoreDefaultSelection, ignoreCharacter, ignoreModify;
    int cbtHook, scrollWidth, visibleCount = 5;

    private Composite comboComposite;
    private Label imageLabel;
    private Text text;
    private Table table;
    private int visibleItemCount = 4;
    private Shell popup;
    private Button arrow;
    private boolean hasFocus;
    private Listener listener, filter;
    private Color foreground, background;
    private Font font;
    private int style;
    private boolean isTextImageField = false;
     
//    private boolean isStyleCombo;
   
    public PropertyTypeCombo(Composite parent, int style, boolean isStyleCombo, boolean isTextImageField) {
    	this(parent,style,isStyleCombo);
    	this.isTextImageField = isTextImageField;
    }
    
    public PropertyTypeCombo(Composite parent, int style,boolean isStyleCombo) {
        super(parent, style = checkStyle(style));
        this.style = style;
        /* This code is intentionally commented */
        //if ((style & SWT.H_SCROLL) != 0) this.style |= SWT.H_SCROLL;
        this.style |= SWT.H_SCROLL;

        int textStyle = SWT.SINGLE;
        if ( (style & SWT.READ_ONLY) != 0 ) {
            textStyle |= SWT.READ_ONLY;
        }
        if ( (style & SWT.FLAT) != 0 ) {
            textStyle |= SWT.FLAT;
        }

        this.comboComposite = new Composite(this, SWT.NONE);
        TableWrapLayout tableWrapLayout = new TableWrapLayout();
        tableWrapLayout.numColumns = 2;
        tableWrapLayout.topMargin = 2;
        tableWrapLayout.bottomMargin = 0;
        tableWrapLayout.leftMargin = 2;
        tableWrapLayout.rightMargin = 0;
        this.comboComposite.setLayout(tableWrapLayout);
        this.comboComposite.setLayoutData(new TableWrapData());

        this.imageLabel = new Label(this.comboComposite, SWT.NONE);
        this.imageLabel.setLayoutData(new TableWrapData());
        this.imageLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        this.text = new Text(this.comboComposite, SWT.READ_ONLY);
        this.text.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

        this.comboComposite.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_ARROW));

        int arrowStyle = SWT.ARROW | SWT.DOWN;
        if ( (style & SWT.FLAT) != 0 ) {
            arrowStyle |= SWT.FLAT;
        }
      
        this.arrow = new Button(this, arrowStyle);

        this.listener = new Listener() {
            public void handleEvent(Event event) {
                if ( PropertyTypeCombo.this.popup == event.widget ) {
                    popupEvent(event);
                    return;
                }
                if ( PropertyTypeCombo.this.text == event.widget ) {
                    textEvent(event);
                    return;
                }
                if ( PropertyTypeCombo.this.table == event.widget ) {
                    listEvent(event);
                    return;
                }
                if ( PropertyTypeCombo.this.arrow == event.widget ) {
                    arrowEvent(event);
                    return;
                }
                if ( PropertyTypeCombo.this == event.widget ) {
                    comboEvent(event);
                    return;
                }
                if ( getShell() == event.widget ) {
                    handleFocus(SWT.FocusOut);
                }
            }
        };
        
        this.filter = new Listener() {
            public void handleEvent(Event event) {
                Shell shell = ((Control) event.widget).getShell();
                if ( shell == PropertyTypeCombo.this.getShell() ) {
                    handleFocus(SWT.FocusOut);
                }
            }
        };

        int[] comboEvents = { SWT.Dispose, SWT.Move, SWT.Resize };
        for (int i = 0; i < comboEvents.length; i++) {
            this.addListener(comboEvents[i], this.listener);
        }

        int[] textEvents = { SWT.KeyDown, SWT.KeyUp, SWT.Modify, SWT.MouseDown, SWT.MouseUp, SWT.Traverse, SWT.FocusIn };
        for (int i = 0; i < textEvents.length; i++) {
            this.text.addListener(textEvents[i], this.listener);
        }

        int[] arrowEvents = { SWT.Selection, SWT.FocusIn };
        for (int i = 0; i < arrowEvents.length; i++) {
            this.arrow.addListener(arrowEvents[i], this.listener);
        }

        createPopup(-1);
        initAccessible();
        
        if(!isStyleCombo){
        	this.arrow.setVisible(false);
        }
    }

   
    public void add(Image image, String string) {
        checkWidget();
        if ( string == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }

        TableItem newItem = new TableItem(this.table, SWT.FILL);
        newItem.setText(string);//
        if ( image != null ) {
            newItem.setImage(image);
            this.imageLabel.setImage(image);
        }
    }

    public void addModifyListener(ModifyListener listener) {
        checkWidget();
        if ( listener == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        TypedListener typedListener = new TypedListener(listener);
        addListener(SWT.Modify, typedListener);
    }

    public void addSelectionListener(SelectionListener listener) {
        checkWidget();
        if ( listener == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        TypedListener typedListener = new TypedListener(listener);
        addListener(SWT.Selection, typedListener);
        addListener(SWT.DefaultSelection, typedListener);
    }

   
    public void addVerifyListener(VerifyListener listener) {
        checkWidget();
        if ( listener == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        TypedListener typedListener = new TypedListener(listener);
        addListener(SWT.Verify, typedListener);
    }

    static int checkStyle(int style) {
        int mask = SWT.BORDER | SWT.READ_ONLY | SWT.FLAT | SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT;
        return style & mask;
    }

    public Point computeSize(int wHint, int hHint, boolean changed) {
        checkWidget();
        int width = 0, height = 0;
        String[] items = getStringsFromTable();
        int textWidth = 0;
        GC gc = new GC(this.comboComposite);
        int spacer = gc.stringExtent(" ").x; //$NON-NLS-1$
        for (int i = 0; i < items.length; i++) {
            textWidth = Math.max(gc.stringExtent(items[i]).x, textWidth);
        }
        gc.dispose();
        Point textSize = this.comboComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT, changed);
        Point arrowSize = this.arrow.computeSize(SWT.DEFAULT, SWT.DEFAULT, changed);
        Point listSize = this.table.computeSize(wHint, SWT.DEFAULT, changed);
        int borderWidth = getBorderWidth();

        height = Math.max(hHint, Math.max(textSize.y, arrowSize.y) + 2 * borderWidth);
        width = Math.max(wHint, Math.max(textWidth + 2 * spacer + arrowSize.x + 2 * borderWidth, listSize.x));
        return new Point(width + 10, height);
    }

  
    public void deselect(int index) {
        checkWidget();
        this.table.deselect(index);
    }

    public void deselectAll() {
        checkWidget();
        this.text.clearSelection();
        this.table.deselectAll();
    }

    public String getItem(int index) {
        checkWidget();
        return this.table.getItem(index).getText();
    }

    public int getItemCount() {
        checkWidget();
        return this.table.getItemCount();
    }

    public int getItemHeight() {
        checkWidget();
        return this.table.getItemHeight();
    }

    public TableItem[] getItems() {
        checkWidget();
        return this.table.getItems();
    }

   
    public int getOrientation() {
        checkWidget();
        return this.style & (SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT);
    }

  
    public Point getSelection() {
        checkWidget();
        return this.text.getSize();
    }

   
    public int getSelectionIndex() {
        checkWidget();
        return this.table.getSelectionIndex();
    }

   
    public String getText() {
        checkWidget();
        return this.text.getText();
    }

    public int getTextHeight() {
        checkWidget();
        return this.text.getSize().x;
    }

   
    public int getVisibleItemCount() {
        checkWidget();
        return this.visibleCount;
    }

  
    public int indexOf(String string) {
        checkWidget();
        if ( string == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        return Arrays.asList(getStringsFromTable()).indexOf(string);
    }

   
    public void remove(int index) {
        checkWidget();
        this.table.remove(index);
    }

  
    public void remove(int start, int end) {
        checkWidget();
        this.table.remove(start, end);
    }

   
    public void remove(String string) {
        checkWidget();
        if ( string == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        int index = -1;
        for (int i = 0, n = this.table.getItemCount(); i < n; i++) {
            if ( this.table.getItem(i).getText().equals(string) ) {
                index = i;
                break;
            }
        }
        remove(index);
    }

    
    public void removeAll() {
        checkWidget();
        this.text.setText(""); //$NON-NLS-1$
        this.table.removeAll();
    }

   
    public void removeModifyListener(ModifyListener listener) {
        checkWidget();
        if ( listener == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        removeListener(SWT.Modify, listener);
    }

    public void removeSelectionListener(SelectionListener listener) {
        checkWidget();
        if ( listener == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        removeListener(SWT.Selection, listener);
        removeListener(SWT.DefaultSelection, listener);
    }

    public void select(int index) {
        checkWidget();
        if ( index == -1 ) {
            this.table.deselectAll();
            this.text.setText(""); //$NON-NLS-1$
            return;
        }
        if ( 0 <= index && index < this.table.getItemCount() ) {
            if ( index != getSelectionIndex() ) {
                this.text.setText(this.table.getItem(index).getText());
                this.imageLabel.setImage(this.table.getItem(index).getImage());
                this.text.selectAll();
                this.table.select(index);
                this.table.showSelection();
            }
        }
    }

    public void setFont(Font font) {
        checkWidget();
        super.setFont(font);
        this.font = font;
        this.text.setFont(font);
        this.table.setFont(font);
        internalLayout(true);
    }

   
    public void setText(String string) {
        checkWidget();
        if ( string == null ) {
            SWT.error(SWT.ERROR_NULL_ARGUMENT);
        }
        int index = -1;
        for (int i = 0, n = this.table.getItemCount(); i < n; i++) {
            if ( this.table.getItem(i).getText().equals(string) ) {
                index = i;
                break;
            }
        }
        if ( index == -1 ) {
            this.table.deselectAll();
            this.text.setText(string);
            return;
        }
        this.text.setText(string);
        this.text.selectAll();
        this.table.setSelection(index);
        this.table.showSelection();
    }

    public void setToolTipText(String string) {
        checkWidget();
        super.setToolTipText(string);
        this.arrow.setToolTipText(string);
        this.comboComposite.setToolTipText(string);
    }

   
    public void setVisibleItemCount(int count) {
        checkWidget();
        if ( count < 0 ) {
            return;
        }
        this.visibleItemCount = count;
    }

   
    public boolean getEditable() {
        checkWidget();
        return this.text.getEditable();
    }

    void handleFocus(int type) {
        if ( isDisposed() ) {
            return;
        }
        switch (type) {
            case SWT.FocusIn: {
                if ( this.hasFocus ) {
                    return;
                }
                if ( getEditable() ) {
                    this.text.selectAll();
                }
                this.hasFocus = true;
                Shell shell = getShell();
                shell.removeListener(SWT.Deactivate, this.listener);
                shell.addListener(SWT.Deactivate, this.listener);
                Display display = getDisplay();
                display.removeFilter(SWT.FocusIn, this.filter);
                display.addFilter(SWT.FocusIn, this.filter);
                Event e = new Event();
                notifyListeners(SWT.FocusIn, e);
                break;
            }
            case SWT.FocusOut: {
                if ( !this.hasFocus ) {
                    return;
                }
                Control focusControl = getDisplay().getFocusControl();
                if ( focusControl == this.arrow || focusControl == this.table || focusControl == this.comboComposite ) {
                    return;
                }
                this.hasFocus = false;
                Shell shell = getShell();
                shell.removeListener(SWT.Deactivate, this.listener);
                Display display = getDisplay();
                display.removeFilter(SWT.FocusIn, this.filter);
                Event e = new Event();
                notifyListeners(SWT.FocusOut, e);
                break;
            }
        }
    }

    void createPopup(int selectionIndex) {
        // create shell and list
        this.popup = new Shell(getShell(), SWT.NO_TRIM | SWT.ON_TOP);
        int style = getStyle();
        int listStyle = SWT.SINGLE | SWT.V_SCROLL;
        if ( (style & SWT.FLAT) != 0 ) {
            listStyle |= SWT.FLAT;
        }
        if ( (style & SWT.RIGHT_TO_LEFT) != 0 ) {
            listStyle |= SWT.RIGHT_TO_LEFT;
        }
        if ( (style & SWT.LEFT_TO_RIGHT) != 0 ) {
            listStyle |= SWT.LEFT_TO_RIGHT;
        }
        // create a table instead of a list.
        this.table = new Table(this.popup, listStyle);
        if ( this.font != null ) {
            this.table.setFont(this.font);
        }
        if ( this.foreground != null ) {
            this.table.setForeground(this.foreground);
        }
        if ( this.background != null ) {
            this.table.setBackground(this.background);
        }

        int[] popupEvents = { SWT.Close, SWT.Paint, SWT.Deactivate };
        for (int i = 0; i < popupEvents.length; i++) {
            this.popup.addListener(popupEvents[i], this.listener);
        }
        int[] listEvents = { SWT.MouseUp, SWT.Selection, SWT.Traverse, SWT.KeyDown, SWT.KeyUp, SWT.FocusIn, SWT.Dispose };
        for (int i = 0; i < listEvents.length; i++) {
            this.table.addListener(listEvents[i], this.listener);
        }

        if ( selectionIndex != -1 ) {
            this.table.setSelection(selectionIndex);
        }
    }

    boolean isDropped() {
        return this.popup.getVisible();
    }

    void dropDown(boolean drop) {
    	if(isTextImageField){
    		return;
    	}
    	if ( drop == isDropped() ) {
    		return;
    	}
    	if ( !drop ) {
    		this.popup.setVisible(false);
    		if ( !isDisposed() && this.arrow.isFocusControl() ) {
    			this.comboComposite.setFocus();
    		}
    		return;
    	}

    	if ( getShell() != this.popup.getParent() ) {
    		int selectionIndex = this.table.getSelectionIndex();
    		this.table.removeListener(SWT.Dispose, this.listener);
    		this.popup.dispose();
    		this.popup = null;
    		this.table = null;
    		createPopup(selectionIndex);
    	}

    	Point size = getSize();
    	int itemCount = this.table.getItemCount();
    	itemCount = (itemCount == 0) ? this.visibleItemCount : Math.min(this.visibleItemCount, itemCount);
    	int itemHeight = this.table.getItemHeight() * itemCount;
    	Point listSize = this.table.computeSize(SWT.DEFAULT, itemHeight, false);
    	this.table.setBounds(1, 1, Math.max(size.x - 2, listSize.x), listSize.y);

    	int index = this.table.getSelectionIndex();
    	if ( index != -1 ) {
    		this.table.setTopIndex(index);
    	}
    	Display display = getDisplay();
    	Rectangle listRect = this.table.getBounds();
    	Rectangle parentRect = display.map(getParent(), null, getBounds());
    	Point comboSize = getSize();
    	Rectangle displayRect = getMonitor().getClientArea();
    	int width = Math.max(comboSize.x, listRect.width + 2);
    	int height = listRect.height + 2;
    	int x = parentRect.x;
    	int y = parentRect.y + comboSize.y;
    	if ( y + height > displayRect.y + displayRect.height ) {
    		y = parentRect.y - height;
    	}
    	this.popup.setBounds(x, y, width, height);
    	this.popup.setVisible(true);
    	this.table.setFocus();
    }

    void listEvent(Event event) {
        switch (event.type) {
            case SWT.Dispose:
                if ( getShell() != this.popup.getParent() ) {
                    int selectionIndex = this.table.getSelectionIndex();
                    this.popup = null;
                    this.table = null;
                    createPopup(selectionIndex);
                }
                break;
            case SWT.FocusIn: {
                handleFocus(SWT.FocusIn);
                break;
            }
            case SWT.MouseUp: {
                if ( event.button != 1 ) {
                    return;
                }
                dropDown(false);
                break;
            }
            case SWT.Selection: {
                int index = this.table.getSelectionIndex();
                if ( index == -1 ) {
                    return;
                }
                this.text.setText(this.table.getItem(index).getText());
                this.text.selectAll();
                this.imageLabel.setImage(this.table.getItem(index).getImage());
                this.table.setSelection(index);
                Event e = new Event();
                e.time = event.time;
                e.stateMask = event.stateMask;
                e.doit = event.doit;
                notifyListeners(SWT.Selection, e);
                event.doit = e.doit;
                break;
            }
            case SWT.Traverse: {
                switch (event.detail) {
                    case SWT.TRAVERSE_RETURN:
                    case SWT.TRAVERSE_ESCAPE:
                    case SWT.TRAVERSE_ARROW_PREVIOUS:
                    case SWT.TRAVERSE_ARROW_NEXT:
                        event.doit = false;
                        break;
                }
                Event e = new Event();
                e.time = event.time;
                e.detail = event.detail;
                e.doit = event.doit;
                e.character = event.character;
                e.keyCode = event.keyCode;
                notifyListeners(SWT.Traverse, e);
                event.doit = e.doit;
                event.detail = e.detail;
                break;
            }
            case SWT.KeyUp: {
                Event e = new Event();
                e.time = event.time;
                e.character = event.character;
                e.keyCode = event.keyCode;
                e.stateMask = event.stateMask;
                notifyListeners(SWT.KeyUp, e);
                break;
            }
            case SWT.KeyDown: {
                if ( event.character == SWT.ESC ) {
                    // Escape key cancels popup list
                    dropDown(false);
                }
                if ( (event.stateMask & SWT.ALT) != 0
                                && (event.keyCode == SWT.ARROW_UP || event.keyCode == SWT.ARROW_DOWN) ) {
                    dropDown(false);
                }
                if ( event.character == SWT.CR ) {
                    // Enter causes default selection
                    dropDown(false);
                    Event e = new Event();
                    e.time = event.time;
                    e.stateMask = event.stateMask;
                    notifyListeners(SWT.DefaultSelection, e);
                }
                // At this point the widget may have been disposed.
                // If so, do not continue.
                if ( isDisposed() ) {
                    break;
                }
                Event e = new Event();
                e.time = event.time;
                e.character = event.character;
                e.keyCode = event.keyCode;
                e.stateMask = event.stateMask;
                notifyListeners(SWT.KeyDown, e);
                break;

            }
        }
    }

    void arrowEvent(Event event) {
        switch (event.type) {
            case SWT.FocusIn: {
                handleFocus(SWT.FocusIn);
                break;
            }
            case SWT.Selection: {
                dropDown(!isDropped());
                break;
            }
        }
    }

    void comboEvent(Event event) {
        switch (event.type) {
            case SWT.Dispose:
                if ( this.popup != null && !this.popup.isDisposed() ) {
                    this.table.removeListener(SWT.Dispose, this.listener);
                    this.popup.dispose();
                }
                Shell shell = getShell();
                shell.removeListener(SWT.Deactivate, this.listener);
                Display display = getDisplay();
                display.removeFilter(SWT.FocusIn, this.filter);
                this.popup = null;
                this.comboComposite = null;
                this.table = null;
                this.arrow = null;
                break;
            case SWT.Move:
                dropDown(false);
                break;
            case SWT.Resize:
                internalLayout(false);
                break;
        }
    }

    void internalLayout(boolean changed) {
        if ( isDropped() ) {
            dropDown(false);
        }
        Rectangle rect = getClientArea();
        int width = rect.width;
        int height = rect.height;
        Point arrowSize = this.arrow.computeSize(SWT.DEFAULT, height, changed);
        this.comboComposite.setBounds(0, 0, width - arrowSize.x, height);
        this.arrow.setBounds(width - arrowSize.x, 0, arrowSize.x, arrowSize.y);
    }

    void popupEvent(Event event) {
        switch (event.type) {
            case SWT.Paint:
                // draw black rectangle around list
                Rectangle listRect = this.table.getBounds();
                Color black = getDisplay().getSystemColor(SWT.COLOR_BLACK);
                event.gc.setForeground(black);
                event.gc.drawRectangle(0, 0, listRect.width + 1, listRect.height + 1);
                break;
            case SWT.Close:
                event.doit = false;
                dropDown(false);
                break;
            case SWT.Deactivate:
                dropDown(false);
                break;
        }
    }

    Label getAssociatedLabel() {
        Control[] siblings = getParent().getChildren();
        for (int i = 0; i < siblings.length; i++) {
            if ( siblings[i] == PropertyTypeCombo.this ) {
                if ( i > 0 && siblings[i - 1] instanceof Label ) {
                    return (Label) siblings[i - 1];
                }
            }
        }
        return null;
    }

    char getMnemonic(String string) {
        int index = 0;
        int length = string.length();
        do {
            while ((index < length) && (string.charAt(index) != '&')) {
                index++;
            }
            if ( ++index >= length ) {
                return '\0';
            }
            if ( string.charAt(index) != '&' ) {
                return string.charAt(index);
            }
            index++;
        } while (index < length);
        return '\0';
    }

    String[] getStringsFromTable() {
        String[] items = new String[this.table.getItems().length];
        for (int i = 0, n = items.length; i < n; i++) {
            items[i] = this.table.getItem(i).getText();
        }
        return items;
    }

    String stripMnemonic(String string) {
        int index = 0;
        int length = string.length();
        do {
            while ((index < length) && (string.charAt(index) != '&')) {
                index++;
            }
            if ( ++index >= length ) {
                return string;
            }
            if ( string.charAt(index) != '&' ) {
                return string.substring(0, index - 1) + string.substring(index, length);
            }
            index++;
        } while (index < length);
        return string;
    }

    void initAccessible() {
        AccessibleAdapter accessibleAdapter = new AccessibleAdapter() {
            public void getName(AccessibleEvent e) {
                String name = null;
                Label label = getAssociatedLabel();
                if ( label != null ) {
                    name = stripMnemonic(label.getText());
                }
                e.result = name;
            }

            public void getKeyboardShortcut(AccessibleEvent e) {
                String shortcut = null;
                Label label = getAssociatedLabel();
                if ( label != null ) {
                    String text = label.getText();
                    if ( text != null ) {
                        char mnemonic = getMnemonic(text);
                        if ( mnemonic != '\0' ) {
                            shortcut = "Alt+" + mnemonic; //$NON-NLS-1$
                        }
                    }
                }
                e.result = shortcut;
            }

            public void getHelp(AccessibleEvent e) {
                e.result = getToolTipText();
            }
        };
        getAccessible().addAccessibleListener(accessibleAdapter);
        this.comboComposite.getAccessible().addAccessibleListener(accessibleAdapter);
        this.table.getAccessible().addAccessibleListener(accessibleAdapter);

        this.arrow.getAccessible().addAccessibleListener(new AccessibleAdapter() {
            public void getName(AccessibleEvent e) {
                e.result = isDropped() ? SWT.getMessage("SWT_Close") : SWT.getMessage("SWT_Open"); //$NON-NLS-1$ //$NON-NLS-2$
            }

            public void getKeyboardShortcut(AccessibleEvent e) {
                e.result = "Alt+Down Arrow"; //$NON-NLS-1$
            }

            public void getHelp(AccessibleEvent e) {
                e.result = getToolTipText();
            }
        });

        getAccessible().addAccessibleTextListener(new AccessibleTextAdapter() {
            public void getCaretOffset(AccessibleTextEvent e) {
                e.offset = PropertyTypeCombo.this.text.getCaretPosition();
            }
        });

        getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
            public void getChildAtPoint(AccessibleControlEvent e) {
                Point testPoint = toControl(e.x, e.y);
                if ( getBounds().contains(testPoint) ) {
                    e.childID = ACC.CHILDID_SELF;
                }
            }

            public void getLocation(AccessibleControlEvent e) {
                Rectangle location = getBounds();
                Point pt = toDisplay(location.x, location.y);
                e.x = pt.x;
                e.y = pt.y;
                e.width = location.width;
                e.height = location.height;
            }

            public void getChildCount(AccessibleControlEvent e) {
                e.detail = 0;
            }

            public void getRole(AccessibleControlEvent e) {
                e.detail = ACC.ROLE_COMBOBOX;
            }

            public void getState(AccessibleControlEvent e) {
                e.detail = ACC.STATE_NORMAL;
            }

            public void getValue(AccessibleControlEvent e) {
                e.result = getText();
            }
        });

        this.text.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
            public void getRole(AccessibleControlEvent e) {
                e.detail = ACC.ROLE_LABEL;
            }
        });

        this.arrow.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
            public void getDefaultAction(AccessibleControlEvent e) {
                e.result = isDropped() ? SWT.getMessage("SWT_Close") : SWT.getMessage("SWT_Open"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        });
    }

    void textEvent(Event event) {
        switch (event.type) {
            case SWT.FocusIn: {
                handleFocus(SWT.FocusIn);
                break;
            }
            case SWT.KeyDown: {
                if ( event.character == SWT.CR ) {
                    dropDown(false);
                    Event e = new Event();
                    e.time = event.time;
                    e.stateMask = event.stateMask;
                    notifyListeners(SWT.DefaultSelection, e);
                }
                //At this point the widget may have been disposed.
                // If so, do not continue.
                if ( isDisposed() ) {
                    break;
                }

                if ( event.keyCode == SWT.ARROW_UP || event.keyCode == SWT.ARROW_DOWN ) {
                    event.doit = false;
                    if ( (event.stateMask & SWT.ALT) != 0 ) {
                        boolean dropped = isDropped();
                        this.text.selectAll();
                        if ( !dropped ) {
                            setFocus();
                        }
                        dropDown(!dropped);
                        break;
                    }

                    int oldIndex = getSelectionIndex();
                    if ( event.keyCode == SWT.ARROW_UP ) {
                        select(Math.max(oldIndex - 1, 0));
                    } else {
                        select(Math.min(oldIndex + 1, getItemCount() - 1));
                    }
                    if ( oldIndex != getSelectionIndex() ) {
                        Event e = new Event();
                        e.time = event.time;
                        e.stateMask = event.stateMask;
                        notifyListeners(SWT.Selection, e);
                    }
                    //At this point the widget may have been disposed.
                    // If so, do not continue.
                    if ( isDisposed() ) {
                        break;
                    }
                }

                // Further work : Need to add support for incremental search in 
                // pop up list as characters typed in text widget

                Event e = new Event();
                e.time = event.time;
                e.character = event.character;
                e.keyCode = event.keyCode;
                e.stateMask = event.stateMask;
                notifyListeners(SWT.KeyDown, e);
                break;
            }
            case SWT.KeyUp: {
                Event e = new Event();
                e.time = event.time;
                e.character = event.character;
                e.keyCode = event.keyCode;
                e.stateMask = event.stateMask;
                notifyListeners(SWT.KeyUp, e);
                break;
            }
            case SWT.Modify: {
                this.table.deselectAll();
                Event e = new Event();
                e.time = event.time;
                notifyListeners(SWT.Modify, e);
                break;
            }
            case SWT.MouseDown: {
                if ( event.button != 1 ) {
                    return;
                }
                if ( this.text.getEditable() ) {
                    return;
                }
                boolean dropped = isDropped();
                this.text.selectAll();
                if ( !dropped ) {
                    setFocus();
                }
                dropDown(!dropped);
                break;
            }
            case SWT.MouseUp: {
                if ( event.button != 1 ) {
                    return;
                }
                if ( this.text.getEditable() ) {
                    return;
                }
                this.text.selectAll();
                break;
            }
            case SWT.Traverse: {
                switch (event.detail) {
                    case SWT.TRAVERSE_RETURN:
                    case SWT.TRAVERSE_ARROW_PREVIOUS:
                    case SWT.TRAVERSE_ARROW_NEXT:
                        // The enter causes default selection and
                        // the arrow keys are used to manipulate the list contents so
                        // do not use them for traversal.
                        event.doit = false;
                        break;
                }

                Event e = new Event();
                e.time = event.time;
                e.detail = event.detail;
                e.doit = event.doit;
                e.character = event.character;
                e.keyCode = event.keyCode;
                notifyListeners(SWT.Traverse, e);
                event.doit = e.doit;
                event.detail = e.detail;
                break;
            }
        }
    }
    public Text getTextWidget(){
    	return text;
    }
    public void setImage(Image image){
    	imageLabel.setImage(image);
    }
    public Composite getContainer(){
    	return comboComposite;
    }
}
