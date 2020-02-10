package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.TypeChecker;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.XPathCheckArguments;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Purposefully package private.
 * A utility class for searching errors, monitors cursor & document changes. (Not documented yet)
 */
class XPathErrorFinder {
   private JTextComponent mEditor;
   private Listener mListener = new Listener(); // internal.
   private Vector<ChangeListener> mListeners = new Vector<ChangeListener>();
   private boolean mAllowEmptyString;
   private SmSequenceType mExpectedReturnType;
   private ExprContext mState;

   // Current input state:
//    private XData mInputData; // may be null
//    private XType mCurrentContext; // may be null.

   // Current output state:
   private XPathTypeReport mCurrentTypeReport = new XPathTypeReport();
   private Object mCurrentReturnData;

   int mChangeNumber = 1;
   int mDocChangeNumber = 1;
   String mUpToCurrent;
   String mBeyond;
   private boolean mIsWaitingForRun;

   public XPathErrorFinder(JTextComponent editor) {
      mEditor = editor;
      editor.getDocument().addDocumentListener(mListener);
      editor.addCaretListener(mListener);
   }

   public void setExprContext(ExprContext state) {
      mState = state;
      runNow();
   }

   public ExprContext getExprContext() {
      return mState;
   }

   /*
   public void setRootData(XData data) {
       //mInputElement = null;
       mInputData = data;
       runNow();
   }*/

   /*public void setCurrentContext(XType type) {
       mCurrentContext = type;
   }*/

   /*
   public XType getCurrentContext() {
       return mCurrentContext;
   }*/

   public void setAllowsEmptyString(boolean val) {
      mAllowEmptyString = val;
   }

   public boolean getAllowsEmptyString() {
      return mAllowEmptyString;
   }

   public void setExpectedReturnType(SmSequenceType type) {
      mExpectedReturnType = type;
   }

   public SmSequenceType getExpectedReturnType() {
      return mExpectedReturnType;
   }

   public XPathTypeReport getCurrentTypeReport() {
      return mCurrentTypeReport;
   }

   public Object getCurrentReturnData() {
      return mCurrentReturnData;
   }

   public void addChangeListener(ChangeListener changeListener) {
      mListeners.add(changeListener);
   }

   public void removeChangeListener(ChangeListener changeListener) {
      mListeners.remove(changeListener);
   }

   /*private static int findCutoff(String xpath, int cursor) {
       for (int at = cursor-1;at>0;at--) {
           char c = xpath.charAt(at);
           if (c=='.') {
               return at-1;
           }
           char p = xpath.charAt(cursor-1);
           // logic need work:
           if (p==' ' || p=='\t' || p=='\n' || p=='\r' || p==';' || p=='"') {
               return at;
           }
       }
       return 0;
   }*/

   private void findError(String xpath) {//, int cursor) {
      Token[] tokens = Lexer.lex(xpath);
      final Parser ret = new Parser(tokens);

//        int cutoff = findCutoff(xpath,cursor);
//        final String upToCurrent = xpath.substring(0,cutoff);
//        final String beyond = xpath.substring(cutoff,cursor);//@ move thread.
//        final EcmaScriptParser cursorRet = EcmaScriptParser.parse(upToCurrent,mEntryState);

//        mState = cursorRet.getProgramState().getGlobal().toType();
      ErrorMessageList errors = ret.getErrorMessageList();
      XPathTypeReport retTypeReport;
      Object retData = null;
      if (mAllowEmptyString && xpath.length() == 0) {
         retTypeReport = new XPathTypeReport();
      }
      else {
         if (errors.size() > 0) {
            retTypeReport = new XPathTypeReport(errors);
         }
         else {
            // now do semantic check:
            if (mState != null) { // if null, don't do any semantic checking.
               Expr expr = ret.getExpression();
               ExprContext context = mState;
               retTypeReport = expr.evalType(context);
/*                    if (mInputData!=null) {
                        //??? runs type stuff twice..
                        CompiledExpr ce = null;//@expr.buildCompiledExpr(mInputType,context);
                        retData = null;//@ce.evaluate(mInputData,mExprContext);
                    }*/
               if (retTypeReport.errors.size() == 0) {
                  // now do a return type check:
                  SmSequenceType r = mExpectedReturnType;
                  if (r != null) {
                     // If we have a type checker...
                     XPathCheckArguments args = new XPathCheckArguments(); //WCETODO -- need to be passed in.
                     ErrorMessageList typeError = TypeChecker.checkSerialized(retTypeReport.xtype, r, new TextRange(0, xpath.length()), args);
                     if (typeError.size() > 0) {
                        // update type report:
                        retTypeReport = new XPathTypeReport(retTypeReport.xtype,
                                                            typeError);
                     }
                  }
               }
            }
            else {
               // no syntax errors, nothing to check.
               retTypeReport = new XPathTypeReport();
            }
         }
      }
      final XPathTypeReport ftypeReport = retTypeReport;

      final Object fretData = retData;
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            mCurrentTypeReport = ftypeReport;
            mCurrentReturnData = fretData;
            mIsWaitingForRun = false;
            fireChange();
         }
      });
   }

   private void fireChange() {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = 0; i < mListeners.size(); i++) {
         ChangeListener cl = mListeners.get(i);
         cl.stateChanged(ce);
      }
   }

   private class Runner extends javax.swing.Timer implements ActionListener {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Runner() {
         super(1000, null); // 1000-> not used.
         setRepeats(false); // repeats is on by default...
         addActionListener(this);
      }

      public void actionPerformed(ActionEvent ae) {
         rerun(null);
      }
   }

   Runner mRunner = new Runner();

   private void changed(int firstAffectedCharacter) {
      Element el = mEditor.getDocument().getRootElements()[0];
      int lineNo = el.getElementIndex(firstAffectedCharacter) + 1;
      Element line = el.getElement(lineNo - 1);
      int colNo = (firstAffectedCharacter - line.getStartOffset()) + 1;
      // What line is this?

      mChangeNumber++;
      mDocChangeNumber++;
      ErrorMessageList eml = mCurrentTypeReport.errors.getAllMessagesBefore(colNo);//@new TextLocation(lineNo,colNo));
      mCurrentTypeReport = new XPathTypeReport(eml);
      mCurrentReturnData = null;
      mIsWaitingForRun = true;
      fireChange();
      rescheduleRun();
   }

   private void rescheduleRun() {
      mIsWaitingForRun = true;
      mRunner.stop();
      mRunner.setDelay(500);
      mRunner.setRepeats(false);
      mRunner.start();
   }

   public void runNow() {
      runNow(null);
   }

   public void runNow(Runnable runWhenDone) {
      mRunner.stop();
      rerun(runWhenDone);
   }

   public boolean isWaitingForRun() {
      return mIsWaitingForRun;
   }

   private void rerun(final Runnable runWhenDone) {
      // This could be kinda bad... we're getting whole text in Swing thread,
      // probably not an issue unless document is HUGE. (can optimize later...)
      final String javaScript = mEditor.getText();
      //final int cursor = mEditor.getCaretPosition();
      Thread t = new Thread(new Runnable() {
         public void run() {
            findError(javaScript);//,cursor);
            if (runWhenDone != null) {
               runWhenDone.run();
            }
         };
      });
      // error checking is MINIMUM priority! -- don't slow down typing, etc.
      t.setPriority(Thread.MIN_PRIORITY);
      t.start();
   }

   private class Listener implements DocumentListener, CaretListener {
      public void removeUpdate(DocumentEvent de) {
         changed(de.getOffset());
      }

      public void insertUpdate(DocumentEvent de) {
         changed(de.getOffset());
      }

      public void changedUpdate(DocumentEvent de) {
         changed(de.getOffset());
      }

      public void caretUpdate(CaretEvent ce) {
         mChangeNumber++;
         rescheduleRun();
      }
   }
}
