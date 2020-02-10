package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.JEditTextArea;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxDocument;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaDefaults;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaPainter;

/**
 * This extends the {@link com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.JEditTextArea} to fix issues and add new features. Use this instead of {@link JEditTextArea} directly.<br>
 * It was done as an extension since {@link com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.JEditTextArea} comes from open-source, so extending it allows easier
 * separation for new versions, etc.
 * <br>
 * Summary of new features and fixes.
 * <ul>
 * <li>Adds the ability to do error underlining. See {@link #setCodeErrorFinder(com.tibco.ui.jedit.errcheck.CodeErrorFinder)}</li>
 * <li>Adds auto-fix completion proposal support (works along with error underlining)</li>
 * <li>Automatically sets a new document (by default JEditTextArea uses the same <b>global</b> document.</li>
 * <li>Adds to ability to hide scrollbars (use {@link #hideScrollbars()})</li>
 * <li>Adds actions for cut/copy/paste under the windows bindings</li>
 * <li>Adds undo/redo support and windows bindings</li>
 * <li>Adds the {@link #setFieldMode()} method which configures the area to look/work like a JTextField, removing alot of the need for JTextFields.</li>
 * <li>Forwards setFont() and getFont() to the painter to behave more like JTextArea</li>
 * <li>Forwards setBackground() and getBackground() to the painter to behave more like JTextArea</li>
 * <li>By default, turns off end-of-line markers.  This can be set with {@link com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaPainter#setEOLMarkersPainted(boolean)}</li>
 * <li>By default, turns off selected line hiliting.  This can be set with {@link com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaPainter#setLineHighlightEnabled(boolean)}</li>
 * <li>By default, turns off 'paint-invalid' -- which really means paint marker on blank lines.</li>
 * <li>By default, sets the caret color to the system default caret color (same as JTextArea).</li>
 * <li>By default, changes the matching bracket painting to overlay with a fill color rather than draw a box around it. (Looks nicer)</li>
 * <li>By default, sets the bracket painting color to a light blue (for above)</li>
 * <li>Internally, forwards tool-tip requests like a JTextArea would (JEdit doesn't do this normally)</li>
 * <li>Adds get/set caret color methods like JTextArea (implemented by forwarding to JEditTextArea's internal painter</li>
 * <li>Adds utility methods {@link #getUpperLeftPixelForOffset(int) and {@link #getRowAndColumnForOffset(int)} and {@link #getOffsetForPoint(java.awt.Point)}}.</li>
 * <li>Adds method {@link #setRows(int)} to be like JTextArea --- this sets the 'preferred' number of rows</li>
 * <li>Fixed bug so that caret is not visible until text area is focused.</li>
 * <li>Fixed bug so that bracket hiliting isn't painted when text area is not focused.</li>
 * <li>Fixed minor bug in background --- background on JExtendedEditTextArea kept consistent.</li>
 * <li>Fixed a bug where if you did a setText() (or probably any other document manipulation) that left the scroll position beyond
 * the end of the document, JEdit got messed up.</li>
 * <li>By default, use the system window background color instead of hardcoding to white.</li>
 * </ul>
 */
public class JExtendedEditTextArea extends JEditTextArea {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorFinderRunner mErrorFinderRunner;
    private LineErrMsgList mLineErrors;
    private Color mErrorUnderlineColor = Color.red;
    private Color mWarningUnderlineColor = Color.orange;
    private UndoManager mUndoManager = new UndoManager();
    private boolean mPaintOverBracket = true;
    private Vector<ChangeListener> mTypeListeners = new Vector<ChangeListener>();
    private int mPreferredRowCount;
    private boolean mInlineEditingMode;
    private boolean mFieldMode;
    private JWindow mCurrentAutoFixPopup;
    private CompletionProposal[] mCurrentAutoFixProposals;
    private JWindow mCurrentCodeCompletePopup;

    public JExtendedEditTextArea() {
        super(createDefaults());

        mErrorFinderRunner = new ErrorFinderRunner(this);

        ActionListener cutAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cut();
            }
        };
        getInputHandler().addKeyBinding("C+x",cutAction);
        ActionListener copyAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copy();
            }
        };
        getInputHandler().addKeyBinding("C+c",copyAction);
        getInputHandler().addKeyBinding("C+INSERT",copyAction);
        ActionListener pasteAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paste();
            }
        };
        getInputHandler().addKeyBinding("C+v",pasteAction);
        getInputHandler().addKeyBinding("S+INSERT",pasteAction);

        getInputHandler().addKeyBinding("C+z",new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        getInputHandler().addKeyBinding("CS+z",new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });
        getInputHandler().addKeyBinding("C+a",new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAll();
            }
        });
        getInputHandler().addKeyBinding("A+ENTER",new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acceptCompletionProposal();
            }
        });
        // can't register ctrl-space like this, see override of processKeyEvent
        //getInputHandler().addKeyBinding("C+SPACE",new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        controlSpaceHit();
        //    }
        //});
        mUndoManager.setLimit(1000);
        super.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                mUndoManager.addEdit(e.getEdit());
            }
        });
        super.setCaretVisible(false);
        painter.setBracketHighlightColor(new Color(200,200,255)); // very light blue.

        getToolTipManager().registerComponent(getPainter());

        // need a focus listener for proper painting of selection:
        addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent fe) {
                repaint(); // properly paint non-focused selection
                hideAutoFixPopup();
                if (mCurrentCodeCompletePopup!=null) {
                    setCaretVisible(true);
                    repaint();
                }
            }

            public void focusGained(FocusEvent fe) {
                repaint(); // erase draw non-focused selection
            }
        });
        painter.setBackground(SystemColor.window);

        // for inset border (if any)
        super.setBackground(painter.getBackground());
        setOpaque(false); // so we can overpaint.
        painter.setOpaque(false);
    }

    static Color DISABLED_BACKGROUND_COLOR;

    static Color computeDisabledBackgroundColor()
    {
        if (DISABLED_BACKGROUND_COLOR==null)
        {
            DISABLED_BACKGROUND_COLOR = UIManager.getColor("TextField.inactiveBackground");
        }
        return DISABLED_BACKGROUND_COLOR;
    }

    static Color BACKGROUND_COLOR;

    static Color computeBackgroundColor()
    {
        if (BACKGROUND_COLOR==null)
        {
            BACKGROUND_COLOR = UIManager.getColor("TextField.background");
        }
        return BACKGROUND_COLOR;
    }

    public void setEnabled(boolean enabled)
    {
        setBackground(enabled ? computeBackgroundColor() : computeDisabledBackgroundColor());
    }

    /**
     * Set the preferred # of rows.<br>
     * @param preferredRowCount The preferred number of rows, <0 indicates don't care.
     */
    public void setRows(int preferredRowCount) {
        mPreferredRowCount = preferredRowCount;
        if (mPreferredRowCount<0) {
            setPreferredSize(null);
            setMinimumSize(null);
        } else {
            int w = getPreferredSize().width;
            FontMetrics fm = painter.getFontMetrics();
            int h = (fm.getHeight()*preferredRowCount) + fm.getLeading() + fm.getMaxDescent()+3; //3-> magic pixel offset.
            Dimension d = new Dimension(w,h);
            setPreferredSize(d);
            setMinimumSize(d);
        }
    }

    /**
     * Override required for field mode.
     */
    public boolean isManagingFocus()
	{
        if (mInlineEditingMode) {
            return false;
        }
        return super.isManagingFocus();
	}

    /**
     * Utility function which sets the border and sizing so that it looks/feels like a JTextField.<br>
     * This can effectively remove the need for JTextField entirely.
     */
    public void setFieldMode() {
        if (mFieldMode) {
            return;
        }
        Border tfb = new JTextField().getBorder(); // so wasteful! I love it.
        painter.setBorder(tfb);
        setBorder(BorderFactory.createEmptyBorder());
        setRows(1);
        setElectricScroll(0);
        hideScrollbars();

        Dimension d = getPreferredSize();
        setPreferredSize(new Dimension(d.width,d.height-3)); // 3 makes it match JTextField's sizing (at least in windows)

        // When there are multiple lines (may not be relevant if returns aren't permitted to be entered)...
        // draw them with a cute little marker:
        setEOLMarkersPainted(true);
        getPainter().setEOLMarkerColor(Color.blue);
        //getPainter().setDoubleBuffered(false); // no point in doing this on a 1-line thing. (painter is double-buffered by default)
        mFieldMode = true;
    }

    /**
     * Implementation override.
     */
    protected boolean isFieldMode() {
        // For keyboard processing, let's JEditTextArea know if it should forward UP,DOWN,LEFT,RIGHT & ENTER.
        return mFieldMode;
    }

    /**
     * Implementation details.
     * @param border
     */
    public void setBorder(Border border) {
        if (mFieldMode) {
            painter.setBorder(border);
        } else {
            super.setBorder(border);
        }
    }

    /**
     * Implementation details.
     */
    public Border getBorder() {
        if (mFieldMode) {
            return painter.getBorder();
        } else {
            return super.getBorder();
        }
    }

    /**
     * Sets the 'mode' --- inline editing components need to be painted with different y-pixel offsets.<br>
     * By default, this is false.
     * @param inline True if this is meant to be used as an inline (i.e. in a tree) editor, false otherwise.
     */
    public void setInlineEditingMode(boolean inline) {
        mInlineEditingMode = inline;
    }

    private ToolTipManager getToolTipManager() {
        return ToolTipManager.sharedInstance();
    }

    private void undo() {
        mUndoManager.undo();
    }

    private void redo() {
        mUndoManager.redo();
    }

    public void setFont(Font f) {
        super.setFont(f);
        painter.setFont(f);
        if (mPreferredRowCount>0) {
            setRows(mPreferredRowCount); // forces a recompute of preferred size.
        }
    }

    public Font getFont() {
        return painter.getFont();
    }

    /**
     * Call this to manually (re)start the error checking.<br>
     * This is useful in cases where the text is programmatically updated & an instant refresh is desired,
     * without making this call, exactly when the next error update occurs is undefined (it is currently set to
     * about 500ms -- this could be made configurable, etc., though).
     */
    public void startErrorCheck() {
        mErrorFinderRunner.runNow();
    }

    /**
     * Overrides so that this behaves like JTextArea.
     * @param color The background color.
     */
    public void setBackground(Color color) {
        super.setBackground(color);
        painter.setBackground(color);
    }

    public Color getBackground() {
        return painter.getBackground();
    }

    /**
     * Sets the caret color, as in JTextArea.
     * @param color The caret color.
     * Forwards this call to {@link com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaPainter#setCaretColor(java.awt.Color)}
     */
    public void setCaretColor(Color color) {
        painter.setCaretColor(color);
    }

    /**
     * Gets the caret color, as in JTextArea.
     * @return The caret color
     * Forwards this call to {@link com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaPainter#getCaretColor()}
     */
    public Color getCaretColor() {
        return painter.getCaretColor();
    }

    public void hideScrollbars() {
        super.vertical.setVisible(false);
        super.horizontal.setVisible(false);
        super.vertical.setPreferredSize(new Dimension(0,0));
        super.horizontal.setPreferredSize(new Dimension(0,0));
    }

    private static TextAreaDefaults createDefaults() {
        TextAreaDefaults defs = new TextAreaDefaults();
        defs.initializeDefaults();
        defs.eolMarkers = false;
        defs.caretColor = new JTextArea().getCaretColor(); // kind of a waste... fix this.
        defs.lineHighlight = false;
        defs.paintInvalid = false;
        return defs;
    }

    public void setEOLMarkersPainted(boolean val) {
        getPainter().setEOLMarkersPainted(val);
    }

    public boolean getEOLMarkersPainted() {
        return getPainter().getEOLMarkersPainted();
    }

    public void setDocument(SyntaxDocument document) {
        super.setDocument(document);
        super.setCaretPosition(0);
        super.scrollToCaret();
        if (mErrorFinderRunner!=null) {
            mErrorFinderRunner.setDocument(document);
        }
    }

    /**
     * Overrides to fix bug in JTextArea --- automatically sets caret to start & scrolls there.
     */
    public void setText(String text) {
        super.select(0,0);
        super.setOrigin(0,0);
        setCaretPosition(0);
        scrollToCaret(); // workaround for JEdit text area bug --- otherwise can get in bad state & throws exceptions left & right.

        super.setText(text);

        // Make sure it resets:
        super.setOrigin(0,0);
        this.repaint();
        super.select(0,0);

        // Immediately kick off error finding:
        mErrorFinderRunner.runNow();
    }


    /**
     * Also see {@link #addErrorCheckListener(javax.swing.event.ChangeListener)} for how to receive notifications of change.
     * @param errorFinder The error finder to be used in error checking.
     */
    public void setCodeErrorFinder(CodeErrorFinder errorFinder) {
        mErrorFinderRunner.setCodeErrorFinder(errorFinder);
    }

    public CodeErrorFinder getCodeErrorFinder() {
        return mErrorFinderRunner.getCodeErrorFinder();
    }

    /**
     * Fires when the error-checking has changed; either there is a new result or the old result has been invalidated &
     * a re-run is scheduled.<br>
     * Can get status of run with {@link #isRunningErrorReport()} to see if it is running (pending) or complete.
     */
    public void addErrorCheckListener(ChangeListener cl) {
        mTypeListeners.add(cl);
    }

    public void removeErrorCheckListener(ChangeListener cl) {
        mTypeListeners.remove(cl);
    }

    private void setErrCheckErrors(LineErrMsgList errors) {
        mLineErrors = errors;
    }

    /**
     * Retrieves the upper-left pixel coordinate given a document text offset.
     * @param textOffset The text offset in the document.
     * @return The pixel point, or null if not found.
     */
    public Point getUpperLeftPixelForOffset(int textOffset) {
        int yLine = super.getLineOfOffset(textOffset);
        int yoffset = super.getLineStartOffset(yLine);
        int xLineOffset = textOffset - yoffset;
        int x = super.offsetToX(yLine,xLineOffset);
        int y = super.lineToY(yLine);
        return new Point(x,y);
    }

    /**
     * Retrieves the row and column (both zero based) given a document text offset.
     * @param textOffset The text offset in the document.
     * @return The (row,column) point (zero based), or null if not found.
     */
    public Point getRowAndColumnForOffset(int textOffset) {
        int yLine = super.getLineOfOffset(textOffset);
        int yoffset = super.getLineStartOffset(yLine);
        int xLineOffset = textOffset - yoffset;
        return new Point(xLineOffset,yLine);
    }

    /**
     * Implementation override.
     * @param evt
     */
    public void processKeyEvent(KeyEvent evt)
	{
        if (evt.getKeyChar()==KeyEvent.VK_SPACE) {
            if ((evt.getModifiers()&KeyEvent.CTRL_MASK)>0) {
                // Ctrl-space:
                if (evt.getID()==KeyEvent.KEY_PRESSED) {
                    controlSpaceHit();
                }
                return;
            }
        }
        super.processKeyEvent(evt);
	}

    /**
     * Retrieves offset of the nearest character to the point..
     * @param pixelPoint The pixel location.
     * @return The document offset closest to that point.
     */
    public int getOffsetForPoint(Point pixelPoint) {
        int lineNo = super.yToLine(pixelPoint.y);
        int ystartoffset = super.getLineStartOffset(lineNo);
        return super.xToOffset(lineNo, pixelPoint.x)+ystartoffset;
    }

    /**
     * Implementation -- overrides from {@link JEditTextArea} in order to build a painter which paints error lines.
     * @param defaults The painter defaults.
     * @return The painter which paints error lines.
     */
    protected TextAreaPainter buildPainter(TextAreaDefaults defaults) {
        return new MyTextAreaPainter(defaults);
    }

    protected class MyTextAreaPainter extends TextAreaPainter {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean mInitialized = false;

        public MyTextAreaPainter(TextAreaDefaults defaults) {
            super(JExtendedEditTextArea.this,defaults);
            mInitialized = true;
        }

        public void paintOverline(Graphics g, int line, int x, int y) {
            drawErrorsLine(g,line,y);
        }

        protected void paintBracketHighlight(Graphics gfx, int line, int y)
        {
            if (!JExtendedEditTextArea.this.hasFocus() && mCurrentCodeCompletePopup!=null) {
                // don't paint this if we don't have focus (and there isn't a code complete showing)
                return;
            }
            if (!mPaintOverBracket) {
                super.paintBracketHighlight(gfx,line,y);
            }

            // remainder of function cut-and-pasted from super code verbatim, except drawRect changed to fillRect
            int position = textArea.getBracketPosition();
            if(position == -1) {
                return;
            }
            y += fm.getLeading() + fm.getMaxDescent();
            int x = textArea._offsetToX(line,position);
            gfx.setColor(bracketHighlightColor);
            // Hack!!! Since there is no fast way to get the character
            // from the bracket matching routine, we use ( since all
            // brackets probably have the same width anyway
            gfx.fillRect(x-1,y,fm.charWidth('(')+3,
                fm.getHeight() - 1);
        }

        public Point getToolTipLocation(MouseEvent me) {
            MouseEvent me2 = SwingUtilities.convertMouseEvent(this,me,JExtendedEditTextArea.this);
            return JExtendedEditTextArea.this.getToolTipLocation(me2);
        }

        public String getToolTipText(MouseEvent me) {
            MouseEvent me2 = SwingUtilities.convertMouseEvent(this,me,JExtendedEditTextArea.this);
            return JExtendedEditTextArea.this.getToolTipText(me2);
        }

        /**
         * Override required for field mode.
         */
        public boolean isManagingFocus()
        {
            if (mInitialized) { // somehow in JDK1.4 it calls here before JExtendedEditTextArea.this has been initialized.
                if (mInlineEditingMode) {
                    return false;
                }
            }
            return super.isManagingFocus();
        }

        /**
         * Override from base class.
         */
        protected void paintEOLMarker(Graphics gfx, int x, int y) {
            JExtendedEditTextArea.paintEOLMarker(gfx,x,y);
        }
    }

    /**
     * Paints a cute new line marker (an arrow whichs points right then down).<br>
     * Move this to somewhere more sensible.<br>
     * Does not change color, uses currently set color.
     * @param gfx The graphics.
     * @param x The x location to start at, assumes the rightmost x position on the character it's following.
     * @param y The y location to start at, assumes the base-line (bottom) y position of the line.
     */
    public static void paintEOLMarker(Graphics gfx, int x, int y) {
        int xr = x+5;
        int yu = y-5;
        gfx.drawLine(x+1,yu,xr,yu);
        gfx.drawLine(xr,yu,xr,y);
        for (int i=0;i<3;i++) {
            gfx.drawLine(xr-i,y-i,xr+i,y-i);
        }
    }

    private void drawErrorsLine(Graphics g, int line, int y) {
        // repaint anything that's an error (in a different light, of course)
        if (mLineErrors==null) {
            return;
        }
        CodeErrorMessage[] mErrors = mLineErrors.getMessagesOnLine(line);
        if (mErrors==null || mErrors.length==0) {
            return;
        }
        FontMetrics fm = painter.getFontMetrics();
        int baseCharWidth = fm.charWidth('w');
        int waveWidth = (baseCharWidth/2);
        int waveHeight = 2;
        for (int i=0;i<mErrors.length;i++) {
            CodeErrorMessage error = mErrors[i];
            TextRange loc = error.getTextRange();
            int s = loc.getStartPosition();
            int e = loc.getEndPosition();

            if (error.getSeverity()==CodeErrorMessage.TYPE_ERROR) {
                g.setColor(mErrorUnderlineColor);
            } else {
                if (error.getSeverity()==CodeErrorMessage.TYPE_WARNING) {
                    g.setColor(mWarningUnderlineColor);
                } else {
                    continue;
                    // otherwise, don't show, it's just annoying.
                }
            }
            if (e>=s) {
                Point p1 = getRowAndColumnForOffset(s);
                Point p2 = getRowAndColumnForOffset(e);
                if (p1==null || p2==null) {
                    System.err.println("Error not in range?" + s + " and " + e + " line " + line);
                } else {
                    int yat=y+waveHeight; // waveHeight-> a few pixels space.
                    int xleft;
                    if (p1.y<line) {
                        xleft = offsetToX(line,0);
                    } else {
                        xleft = offsetToX(line,p1.x);
                    }
                    int xright;
                    if (p2.y>line) {
                        xright = offsetToX(line,getLineLength(line));
                    } else {
                        xright = offsetToX(line,p2.x);
                    }
                    if (e-s<=1) {
                        xleft  -= baseCharWidth/2;
                        xright += baseCharWidth/2;
                    }
                    // Increase the clip rect to allow painting over the border.
                    Rectangle cr = g.getClipBounds();
                    g.setClip(cr.x,cr.y,cr.width,cr.height+2);
                    PaintUtils.drawWavyLine(g,waveWidth,waveHeight,xleft,xright,yat);
                    g.setClip(cr.x,cr.y,cr.width,cr.height); //restore.
                }
            } else {
                System.err.println("Error not in range?" + s + " and " + e + " line " + line);
            }
        }
    }

    public Point getToolTipLocation(MouseEvent me) {
        CodeErrorMessage err = findError(me.getPoint());
        if (err==null) {
            // Indicates to manager that there's no tool tip here (return null tool tip text does not)
            return null;
        }
        TextRange tl = err.getTextRange();
        Point pixel = getUpperLeftPixelForOffset(tl.getStartPosition());
        int inset = pixel.x-6; // tweaky pixel offset.
        int toolTipHeight = 25; // tweaky pixel offset.
        return new Point(inset,pixel.y-toolTipHeight);
    }

    public String getToolTipText(MouseEvent me) {
        CodeErrorMessage err = findError(me.getPoint());
        if (err==null) {
            return null;
        }
        return err.getMessage();
    }

    private CodeErrorMessage findError(Point at) {
        if (mLineErrors==null) {
            return null;
        }
        int offset = getOffsetForPoint(at);
        Point rc = getRowAndColumnForOffset(offset);
        int lineYPos = lineToY(rc.y);
        if (lineYPos+painter.getFontMetrics().getHeight()+20 < at.y) {
            // too far below the bottom of the document.
            return null;
        }
        CodeErrorMessage[] errs = mLineErrors.getMessagesOnLine(rc.y);

        // Find closest error:
        CodeErrorMessage smallestOne = null;
        int smallestDistance = 100;
        for (int i=0;i<errs.length;i++) {
            CodeErrorMessage em = errs[i];
            int distance = computeColumnDistance(em.getTextRange(),offset);
            if (distance<smallestDistance) {
                smallestOne = em;
                smallestDistance = distance;
            }
        }
        // If you weren't even close, forget it:
        if (smallestDistance>3) {
            return null;
        }
        return smallestOne;
    }

    /**
     * Checks if the error checking is currently running.
     * @return true if the error checking is currently running.
     */
    public boolean isRunningErrorReport() {
        return mErrorFinderRunner.isWaitingForRun();
    }

    /**
     * Called by input handler when attempts are made to edit non-editable.<br>
     * For overrides so that, for instance, it could check out a file or something.<br>
     * By default, does nothing.
     */
    public void editedNonEditable() {
    }

    /**
     * Called by input handler when attempts are made to do bad editing things such as backspacing
     * through the beginning.<br>
     * By default, does nothing.
     */
    public void badEditAttempted() {
    }

    /**
     * Utility function that returns the number of columns a range is from a specific column.
     */
    private int computeColumnDistance(TextRange tl, int colNo) {
        int startCol = tl.getStartPosition();
        int endCol = tl.getEndPosition();
        if (startCol<=colNo) {
            if (endCol>=colNo) {
                return 0;
            }
            return colNo-endCol;
        }
        return startCol-colNo;
    }

    /**
     * Package private, called by ErrorFinderRunner when error checking has finished. (invoked on swing thread)
     */
    void errorCheckFinished(LineErrMsgList errors, boolean fullUpdate)
    {
        setErrCheckErrors(errors);
        painter.repaint();
        //?repaint();
        if (fullUpdate) {
            boolean en = getToolTipManager().isEnabled();
            if (en) {
                getToolTipManager().setEnabled(false); // hacky way to disable/ restart tooltips.
                getToolTipManager().setEnabled(true);
            }

            fireErrCheckChange();

            int errct = errors.getMessageCount();
            for (int i=0;i<errct;i++) {
                CodeErrorMessage cem = errors.getMessage(i);
                CompletionProposal[] cp = cem.getAutoFixProposals();
                if (cp.length>0) {
                    // only show first popup.
                    showAutoFixPopup(cem.getTextRange(),cp);
                    break;
                }
            }
        } else {
            // If it's not a full update, it because typing is going on, so hide the popup:
            hideAutoFixPopup();
        }
        /* Caused timing issues: if (!hasFocus())
        {
            System.out.println("!hf with " + fullUpdate);
            // just in case, if we don't have focus, no popups.
            hideAutoFixPopup();
        }*/
    }

    /**
     * Accepts the current completion proposal (i.e. auto-fix), if any is available.
     */
    private void acceptCompletionProposal() {
        if (mCurrentAutoFixPopup==null) {
            return;
        }
        if (mCurrentAutoFixProposals==null || mCurrentAutoFixProposals.length==0) {
            return;
        }
        mCurrentAutoFixProposals[0].apply(getDocument());
        hideAutoFixPopup();
    }

    private void hideAutoFixPopup() {
        if (mCurrentAutoFixPopup!=null)
        {
            mCurrentAutoFixPopup.setVisible(false);
            mCurrentAutoFixPopup.dispose();
            mCurrentAutoFixPopup = null;
            mCurrentAutoFixProposals = null;
        }
    }

    private void showAutoFixPopup(TextRange affectedRange, CompletionProposal[] proposals) {
        hideAutoFixPopup();
        if (proposals==null || proposals.length==0)
        {
            return;
        }
        JToolTip jtt = super.createToolTip();
        jtt.setTipText(proposals[0].getDisplayString());
        Window fr = SwingUtilities.getWindowAncestor(this);
        if (fr==null)
        {
            // can happen in weird timing (when window is removed).
            hideAutoFixPopup();
            return;
        }
        JWindow jw = new JWindow(fr);
        Point pixelPoint = getUpperLeftPixelForOffset(affectedRange.getStartPosition());
        pixelPoint.y -= (jtt.getPreferredSize().height);
        Point absolutePoint = SwingUtilities.convertPoint(this,pixelPoint,fr);
        jw.getContentPane().add(jtt);
        Point p = fr.getLocationOnScreen();
        jw.setLocation(absolutePoint.x+p.x,absolutePoint.y+p.y);
        jw.pack();
        jw.setVisible(true);
        mCurrentAutoFixPopup = jw;
        mCurrentAutoFixProposals = proposals;
    }

    private void hideCodeCompletePopup() {
        if (mCurrentCodeCompletePopup!=null) {
            mCurrentCodeCompletePopup.setVisible(false);
            mCurrentCodeCompletePopup.dispose();
            mCurrentCodeCompletePopup = null;
            //mCurrentAutoFixProposals = null;
        }
    }

    private void showCodeCompletePopup(TextRange affectedRange, final CompletionProposal[] proposals) {
        hideCodeCompletePopup();
        hideAutoFixPopup();
        if (proposals==null || proposals.length==0) {
            return;
        }

        final JList list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(
                    JList jlist,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel lbl = (JLabel) super.getListCellRendererComponent(jlist, value, index, isSelected, cellHasFocus);
                CompletionProposal cp = (CompletionProposal) value;
                lbl.setText(cp.getDisplayString());
                return lbl;
            }

        });
        JScrollPane jsp = new JScrollPane(list);
        list.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                hideCodeCompletePopup();
            }

        });
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //jsp.setMaximumSize(new Dimension());
        DefaultListModel dlm = new DefaultListModel();
        for (int i=0;i<proposals.length;i++) {
            dlm.addElement(proposals[i]);
        }
        list.setModel(dlm);
        list.setVisibleRowCount(Math.min(dlm.getSize(),5));
        Window fr = SwingUtilities.getWindowAncestor(this);
        final JWindow jw = new JWindow(fr);
        Point pixelPoint = getUpperLeftPixelForOffset(affectedRange.getStartPosition());
        pixelPoint.y -= (jsp.getPreferredSize().height);
        Point absolutePoint = SwingUtilities.convertPoint(this,pixelPoint,fr);
        jw.getContentPane().add(jsp);
        Point p = fr.getLocationOnScreen();
        jw.setLocation(absolutePoint.x+p.x,absolutePoint.y+p.y);
        jw.pack();
        jw.setVisible(true);
        jw.requestFocus();
        mCurrentCodeCompletePopup = jw;
        list.setSelectedIndex(0);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jw.requestFocus();
                list.requestFocus();
                list.grabFocus();
            }
        });
        list.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                char kc = e.getKeyChar();
                switch (kc) {
                    case KeyEvent.VK_ESCAPE: // drop-through
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        hideCodeCompletePopup();
                        break;
                    case (char)KeyEvent.VK_ENTER: // drop-through
                    case KeyEvent.VK_SPACE:
                        // accept proposal:
                        int index = list.getSelectedIndex();
                        proposals[index].apply(getDocument());
                        hideCodeCompletePopup();
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
    }

    /**
     * Package private, called by ErrorFinderRunner when error checking has been rescheduled.
     */
    void errorRunPending() {
        // Fire notification:
        fireErrCheckChange();
    }

    private void fireErrCheckChange() {
        for (int i=0;i<mTypeListeners.size();i++) {
            ChangeListener cl = mTypeListeners.get(i);
            cl.stateChanged(new ChangeEvent(JExtendedEditTextArea.this));
        }
    }

    private void controlSpaceHit() {
        CodeErrorFinder cef = mErrorFinderRunner.getCodeErrorFinder();
        if (cef==null) {
            return;
        }
        int position = getCaretPosition();
        Document doc = getDocument();
        String text;
        try {
            text = new String(doc.getText(0,doc.getLength()));
        } catch (BadLocationException ble) {
            // can't happen.
            ble.printStackTrace();
            return;
        }
        StringReader sr = new StringReader(text);
        CompletionProposal[] proposals;
        try {
            proposals = cef.computeCompletionProposals(sr,position);
        } catch (IOException ioe) {
            // can't happen.
            ioe.printStackTrace();
            return;
        }
        showCodeCompletePopup(new TextRange(position,position),proposals);
    }
}
