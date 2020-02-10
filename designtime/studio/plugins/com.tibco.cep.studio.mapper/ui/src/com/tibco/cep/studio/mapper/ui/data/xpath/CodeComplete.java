package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JList;
import javax.swing.JTextField;

import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmSequenceType;

class CodeComplete {
   private JTextField mComponent;
   private Window mPopup;
   private SmElement mRoot;
//   private SmSequenceType mCurrentContext;
//   private boolean mWindowListenerAdded; // init flag.
//   private int mFontWidth;

   private JList mList;
//   private TextRange mCurrentRange;

   public CodeComplete(JTextField component) {
      mComponent = component;
      component.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();
            switch (keyCode) {
               case KeyEvent.VK_SPACE:
                  {
                     if (ke.isControlDown()) {
                        // do not consume because of hacky stuff in editor.
                        // (editor has to special case control space because o.w. it
                        // shows up as a space)
                        if (!isOpen()) {
                           codeIt();
                        }
                     }
                     break;
                  }
               case KeyEvent.VK_ESCAPE:
                  {
                     if (isOpen()) {
                        ke.consume();
                        close();
                     }
                     break;
                  }
               case KeyEvent.VK_DOWN:
                  {
                     if (isOpen()) {
                        ke.consume();
                        moveDown();
                     }
                     break;
                  }
               case KeyEvent.VK_ENTER:
                  {
                     if (isOpen()) {
                        ke.consume();
                        accept();
                     }
                     break;
                  }
               case KeyEvent.VK_UP:
                  {
                     if (isOpen()) {
                        ke.consume();
                        moveUp();
                     }
                     break;
                  }
            }
         }
      });
      component.addFocusListener(new FocusListener() {
         public void focusLost(FocusEvent fe) {
            close();
         }

         public void focusGained(FocusEvent fe) {
            close();
         }
      });
   }

   public void setRootElement(SmElement root) {
      mRoot = root;
   }

   public void setCurrentContext(SmSequenceType context) {
//      mCurrentContext = context;
   }

   public void setTextFieldFontWidth(int fontWidth) {
//      mFontWidth = fontWidth;
   }

//   private int mRunningOn = -1;

   /**
    * Shows the code complete popup.
    */
   private void codeIt() {
      close();
      codeUpdated();
   }

   public boolean isOpen() {
      return mPopup != null;
   }

   private void moveUp() {
      int newindex = mList.getSelectedIndex() - 1;
      select(newindex);
   }

   private void moveDown() {
      int newindex = mList.getSelectedIndex() + 1;
      select(newindex);
   }

   private void accept() {
      int index = mList.getSelectedIndex();
      close();
      if (index < 0) {
         return;
      }
      String val = (String) mList.getSelectedValue();
      accept(val);
   }

   private TextRange computeTextRange() {
      int caret = mComponent.getCaretPosition();
      String text = mComponent.getText();
      if (text.length() == 0) {
         return new TextRange(0, 0);
      }
      Token[] tokens = Lexer.lex(text);
      for (int i = 0; i < tokens.length; i++) {
         Token at = tokens[i];
         if (at.getTextRange().containsPosition(caret)) {
            if (!at.isNodeType() && at.getType() != Token.TYPE_NAME_TEST) {
               int pos = at.getTextRange().getEndPosition();
               return new TextRange(pos, pos);
            }
            if (i > 0) {
               Token prev = tokens[i - 1];
               if (prev.getType() == Token.TYPE_ATTRIBUTE) {
                  // include the '@' part...
                  return new TextRange(prev.getTextRange(), at.getTextRange());
               }
            }
            return at.getTextRange();
         }
      }
      return null;
   }

   private void accept(String val) {
      TextRange range = computeTextRange();
      if (range == null) {
         // sanity.
         return;
      }
      String text = mComponent.getText();
      String before = text.substring(0, range.getStartPosition());
      String after = text.substring(range.getEndPosition(), text.length());
      mComponent.setText(before + val + after);
      mComponent.setCaretPosition(before.length() + val.length());
   }

   private void select(int index) {
      if (index < 0) {
         index = 0;
      }
      if (index >= mList.getModel().getSize()) {
         index = mList.getModel().getSize() - 1;
      }
      if (index < 0) { // i.e. if model size is zero.
         return;
      }
      mList.setSelectedIndex(index);
   }

//   private Window getParentOf(Component c) {
//      if (c instanceof Window) {
//         return (Window) c;
//      }
//      return getParentOf(c.getParent());
//   }
//
//   private Point getLocationInsideWindow(Component c) {
//      if (c == null || c instanceof Window) {
//         return new Point(0, 0);
//      }
//      Rectangle r = c.getBounds();
//      Point p = getLocationInsideWindow(c.getParent());
//      return new Point(r.x + p.x, r.y + p.y);
//   }

   private void close() {
      if (mPopup != null) {
         mPopup.dispose();
      }
      mPopup = null;
   }

   private void codeUpdated() {
      if (mRoot == null) {
         throw new RuntimeException("Null root");
      }
      /*
      String text = mComponent.getText();
      int pos = mComponent.getCaret().getMark();
      String atText = text.substring(0,pos);
      String beyond = null;//mErrorFinder.mBeyond;
      String fullText = atText + "%%"; // marker for code complete.
      Parser parser = new Parser(Lexer.lex(fullText));
      Expr expr = parser.getExpression();
      XType xroot = new DocumentXType(mRoot);
      ExprContext context = new ExprContext(xroot);
      XType xt = mCurrentContext;
      if (xt==null) {
          xt = xroot;
      }
      expr.evalType(xt,context);
      String[] options = null;
      CodeCompleteData ccd = context.getCodeComplete();
      if (ccd!=null) {
          options = ccd.getPossibleStrings();
          mCurrentRange = ccd.getContextRange();
      } else {
          mCurrentRange = null;
      }

      Window parent = getParentOf(mComponent);
      addWindowListener(parent);
      JWindow tip = new JWindow(parent);
      mPopup = tip;
      mList = new JList();
      DefaultListModel model = new DefaultListModel();
      if (options!=null) {
          for (int i=0;i<options.length;i++) {
              model.addElement(options[i]);
          }
      }
      mList.setModel(model);
      mList.setBackground(new Color(255,255,235)); // yellowy

      tip.getContentPane().add(new JScrollPane(mList));
      ((JPanel)tip.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.black,1));
      tip.pack();
      Point global = parent.getLocation();
      Point lc = getLocationInsideWindow(mComponent);
      int textLocIn = 0;
      if (ccd!=null) { // shouldn't happen.
          textLocIn = ccd.getContextRange().getStartPosition() * mFontWidth;
          textLocIn -= mComponent.getScrollOffset();
      }
      Point net = new Point(global.x+lc.x+textLocIn,global.y+lc.y);
      Point belowOffset = new Point(0,mComponent.getSize().height);
      Dimension size = tip.getSize();
      // see if it fits below, if so, use it:
      Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
      int ypos;
      if (desktopSize.height<=net.y + size.height + belowOffset.y) {
          // doesn't fit, use above.
          ypos = net.y-(size.height+2);
      } else {
          // place below.
          ypos = net.y+belowOffset.y;
      }
      int xpos = net.x;
      if (desktopSize.width<=net.x + size.width) {
          // doesn't fit, skootch (sp?) over:
          xpos = desktopSize.width - size.width;
      }
      if (xpos<0) {
          xpos = 0;
      }
      tip.setLocation(xpos,ypos);
      tip.setVisible(true);*/
   }

//   private void addWindowListener(Window window) {
//      if (mWindowListenerAdded) {
//         return;
//      }
//      mWindowListenerAdded = true;
///* NYI       window.addWindowListener(new WindowListener() {
//        });*/
//   }
}
