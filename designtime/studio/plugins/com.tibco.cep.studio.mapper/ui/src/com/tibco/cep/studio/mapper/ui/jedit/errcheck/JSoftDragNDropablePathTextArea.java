package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropManager;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropable;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaDefaults;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaPainter;

/**
 * Has the soft drag'n'drop ability into text.<br>
 * To enable it, just implement {@link TextDragNDropHandler} and set it with {@link #setTextDragNDropHandler(com.tibco.ui.jedit.errcheck.TextDragNDropHandler)}.
 */
public class JSoftDragNDropablePathTextArea extends JExtendedEditTextArea implements SoftDragNDropable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle mDropHintBounds; // the area that surrounds the range below
    private TextRange m_dropHintRange;

    private boolean mDropHintIsSurround; // applies to above, indicates if this is a 'surround' drop or just a drop.
    //private boolean mDrawSelectionNoFocus = true; // paint selection even if it's not focused (is there an easy way to do this???)
    private TextRange mLastDragInsertedRange;
    private TextRange mDrawInsertedRange; // for boxing last drop area
    private TextDragNDropHandler mTextDragNDropHandler;
    public static final Color FLASH_COLOR = new Color(235,235,255);
    private static final int DROP_SURROUND_X_OFFSET = 3;

    public JSoftDragNDropablePathTextArea()
    {
    }


    // no dragging:
    public Object startDrag(SoftDragNDropManager manager, Point pressedAt, Point mouseAt) {
        return null;
    }

    // no dragging:
    public Rectangle getDragHintBounds() {
        return null;
    }

    // no dragging:
    public void dragStopped() {
    }

    public void setTextDragNDropHandler(TextDragNDropHandler textDragNDropHandler) {
        mTextDragNDropHandler = textDragNDropHandler;
    }

    public TextDragNDropHandler getTextDragNDropHandler() {
        return mTextDragNDropHandler;
    }

    public boolean dragOver(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
        if (mTextDragNDropHandler==null) {
            return false;
        }
        if (!isEditable())
        {
            return false;
        }        
        TextRange str = mTextDragNDropHandler.computeSurroundDropTextRange(dragObject,mouseAt);
        if (str!=null)
        {
            mLastDragInsertedRange = str;
            mDropHintBounds = getDropTargetHintBounds(str,true); // true -> surround.
            mDropHintBounds.x-=DROP_SURROUND_X_OFFSET;
            mDropHintBounds.width+=DROP_SURROUND_X_OFFSET*2;
            m_dropHintRange = str;
            mDropHintIsSurround = true;
            return true;
        }
        TextRange tr = mTextDragNDropHandler.computeDropTextRange(dragObject,mouseAt);
        if (tr==null) {
            mDropHintBounds = null;
            m_dropHintRange = null;

            // record range so that on drop we can just use the last one rather than
            // recomputing (which may fail because of selection changes caused by the mouse up)
            mLastDragInsertedRange = null;
            return false;
        } else {
            mLastDragInsertedRange = tr;
            mDropHintIsSurround = false;
            mDropHintBounds = getDropTargetHintBounds(tr,false); // false-> not surround.
            m_dropHintRange = tr;
            return true;
        }
    }

    public Rectangle getDropHintBounds() {
        return mDropHintBounds;
    }

    public void dropStopped() {
        mDropHintBounds = null;
    }

    public boolean drop(SoftDragNDropManager manager, Point mouseAt, Object dropObject) {
        if (mTextDragNDropHandler==null)
        {
            return false;
        }
        if (!isEditable())
        {
            return false;
        }
        mDropHintBounds = null;
        if (mLastDragInsertedRange==null) { // shouldn't happen.
            return false;
        }
        TextRange dropRange = mLastDragInsertedRange;// don't recompute because selection is altered by drop: getInsertedRange(text,item.path,tr);

        final String oldText = getText();
        final String newText;
        TextRange insertedRange;
        if (!mDropHintIsSurround)
        {
            String dropXPath = mTextDragNDropHandler.computeDropString(dropRange,dropObject);
            if (dropXPath==null) {
                return false;
            }
            newText = insertAt(getText(),dropXPath,dropRange);
            insertedRange = new TextRange(dropRange.getStartPosition(),dropRange.getStartPosition()+dropXPath.length());
        }
        else
        {
            String[] dropXPaths = mTextDragNDropHandler.computeSurroundDropStrings(dropRange,dropObject);
            if (dropXPaths==null || dropXPaths.length!=2) {
                return false;
            }
            int sp = dropRange.getStartPosition();
            String b = insertAt(getText(),dropXPaths[0],new TextRange(sp,sp));
            int e = dropRange.getEndPosition()+dropXPaths[0].length();
            newText = insertAt(b,dropXPaths[1],new TextRange(e,e));
            int textWidth = dropXPaths[0].length()+dropXPaths[1].length()+dropRange.getLength();
            insertedRange = new TextRange(dropRange.getStartPosition(),dropRange.getStartPosition()+textWidth);
        }

        // update text:
        getDocument().addUndoableEdit(new AbstractUndoableEdit() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void undo() throws CannotUndoException {
                setText( oldText);
            }

            public void redo() {
                setText(newText);
            }

            public boolean canUndo() {
                return true;
            }

            public boolean canRedo() {
                return true;
            }

            public String getUndoPresentationName() {
                return "Undo drop";
            }

//            public String getReddoPresentationName() {
//                return "Redo drop";
//            }
        });
        setText(newText);

        // select end of newly inserted text
        setSelectionStart(insertedRange.getEndPosition());
        setSelectionEnd(insertedRange.getEndPosition());

        // Note: the following line is required against JEditTextArea, but not against JTextArea -- I guess it is just
        // a subtle difference.
        setCaretPosition(insertedRange.getEndPosition());

        // focus me up:
        grabFocus();

        // draw a box around the inserted area for 500ms
        mDrawInsertedRange = insertedRange;
        javax.swing.Timer deselectRange = new javax.swing.Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (mDrawInsertedRange!=null) {
                    mDrawInsertedRange = null;
                    repaint();
                }
            }
        });
        deselectRange.setRepeats(false); // REPEATS is on by default! yikes.
        deselectRange.start();
        repaint();
        return true;
    }

    public void paint(Graphics g) {
        //mCodeComplete.setTextFieldFontWidth(getCharWidth());
        super.paint(g);
        if (mDropHintBounds!=null) {
            if (mDropHintIsSurround)
            {
                g.setColor(new Color(0,0,255,128)); // clearly blue means surround.
                int yOffset = painter.getFontMetrics().getLeading() + painter.getFontMetrics().getMaxDescent();
                int height = painter.getFontMetrics().getHeight();
                Point p1 = getUpperLeftPixelForOffset(m_dropHintRange.getStartPosition());
                Point p2 = getUpperLeftPixelForOffset(m_dropHintRange.getEndPosition());
                // tweak pixels a bit:
                p1.y += yOffset;
                // tweak pixels a bit:
                p2.y += yOffset;

                g.drawRect(p1.x+3-DROP_SURROUND_X_OFFSET,p1.y,DROP_SURROUND_X_OFFSET-1,height-1);
                g.drawRect(p2.x,p2.y,DROP_SURROUND_X_OFFSET-1,height-1);

                Color baseColor = new Color(40,255,255,80);
                g.setColor(baseColor);

                Point rc1 = getRowAndColumnForOffset(m_dropHintRange.getStartPosition());
                Point rc2 = getRowAndColumnForOffset(m_dropHintRange.getEndPosition());
                for (int i=rc1.y;i<=rc2.y;i++)
                {
                    if (i==rc1.y && i==rc2.y)
                    {
                        g.fillRect(p1.x,p1.y,p2.x-p1.x,height-1);
                    }
                    else
                    {
                        if (i==rc1.y)
                        {
                            g.fillRect(p1.x,p1.y,1000,height-1);
                        }
                        else
                        {
                            if (i==rc2.y)
                            {
                                g.fillRect(0,p2.y,p2.x,height-1);
                            }
                            else
                            {
                                int y = super.lineToY(i);
                                // tweak pixels a bit:
                                y += yOffset;
                                g.fillRect(0,y,1000,height-1);
                            }
                        }
                    }
                }
            }
            else
            {
                g.setColor(Color.red);
                g.drawRect(mDropHintBounds.x,mDropHintBounds.y,mDropHintBounds.width-1,mDropHintBounds.height-1);
            }
        }
    }

    private String insertAt(String xpath, String insertXPath, TextRange range) {
        int start = range.getStartPosition();
        if (start>xpath.length()) {
            return xpath + insertXPath;
        }
        String before = xpath.substring(0,start);
        int end = Math.min(xpath.length(),range.getEndPosition());
        String after = xpath.substring(end,xpath.length());
        return before + insertXPath + after;
    }

    public void drawDraggingIndicator(Graphics g, SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
        // no dragging;
    }

    // no dragging:
    public Rectangle getDraggingIndicatorBounds(SoftDragNDropManager manager, Point mouseAt, Object dragObject) {
        return null;
    }

    private Rectangle getDropTargetHintBounds(TextRange range, boolean isSurround)
    {
        Point p1 = getUpperLeftPixelForOffset(range.getStartPosition());
        Point p2 = getUpperLeftPixelForOffset(range.getEndPosition());
        if (p1.y!=p2.y)
        {
            // it's multiline.
            p2.x = 1000; // make it take the whole area.
        }
        if (isSurround)
        {
            p2.y += 5; // give some extra space here.
        }
        p2.y += painter.getFontMetrics().getHeight();
        if (p1==null || p2==null) {
            return null;
        }
        // tweak pixels a bit:
        p1.y += painter.getFontMetrics().getLeading() + painter.getFontMetrics().getMaxDescent()-1;
        p1.y = Math.max(0,p1.y);
        p2.x++;
        p2.y+=2;
        p1.x--;

        int minWidth = 6;
        if (p2.x<p1.x+minWidth) {
            if (p1.x<=0) {
                p2.x+=minWidth;
            } else {
                p1.x-=minWidth/2;
                p2.x+=minWidth/2;
            }
        }
        p1.x = Math.max(0,p1.x);

        return new Rectangle(p1,new Dimension(p2.x-p1.x,p2.y-p1.y));
    }

    protected TextAreaPainter buildPainter(TextAreaDefaults defaults) {
        return new MySoftTextAreaPainter(defaults);
    }

    protected class MySoftTextAreaPainter extends JExtendedEditTextArea.MyTextAreaPainter {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MySoftTextAreaPainter(TextAreaDefaults defaults) {
            super(defaults);
        }

        protected void paintHighlight(Graphics g, int line, int y)
        {
            super.paintHighlight(g,line,y);

            // paint inserted box:
            if (mDrawInsertedRange!=null) {
                try {
                    int start = mDrawInsertedRange.getStartPosition();
                    int sline = getLineOfOffset(start);
                    if (sline==line) {
                        int end = mDrawInsertedRange.getEndPosition();
                        Point p1 = getUpperLeftPixelForOffset(start);
                        Point p2 = getUpperLeftPixelForOffset(end);
                        g.setColor(FLASH_COLOR);

                        g.fillRect(p1.x,p1.y,(p2.x-p1.x),fm.getHeight()+fm.getMaxDescent());
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    // eat it... just in case, don't want to mess up the painting too much.
                }
            }
        }
    }
}
