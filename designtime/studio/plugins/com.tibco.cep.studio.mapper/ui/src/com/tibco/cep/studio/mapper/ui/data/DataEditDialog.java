package com.tibco.cep.studio.mapper.ui.data;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DataEditDialog extends JPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//   private DataTree mTree;
   private boolean mHitOk;
   private JTextArea mTextArea = new JTextArea();

   public static String showDialog(Window window, String text) {
      JDialog jd;
      if (window instanceof JDialog) {
         jd = new JDialog((JDialog) window);
      }
      else {
         jd = new JDialog((JFrame) window);
      }
      DataEditDialog ded = new DataEditDialog(jd, encode(text, false));
      jd.getContentPane().add(ded);
      jd.pack();
      jd.setLocationRelativeTo(window);
      jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      jd.setVisible(true);
      if (ded.mHitOk) {
         return decode(ded.mTextArea.getText());
      }
      return null;
   }

   /**
    * If includeNewLine, new lines (\n) are encoded, otherwise not.
    */
   private static String encode(String str, boolean includeNewLine) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < str.length(); i++) {
         char c = str.charAt(i);
         switch (c) {
            case 0:
               sb.append("&#O;");
               break;
            case '\t':
               sb.append("&#9;");
               break;
            case '\n':
               {
                  if (includeNewLine) {
                     sb.append("&#10;");
                  }
                  else {
                     sb.append(c);
                  }
               }
               ;
               break;
            case '\r':
               {
                  if (includeNewLine) {
                     sb.append("&#13;");
                  }
                  else {
                     sb.append(c);
                  }
               }
               ;
               break;
            default:
               sb.append(c);
               break;
         }
      }
      return sb.toString();
   }

   private static String decode(String str) {
      return str;
   }

   private DataEditDialog(final JDialog jd, String text) {
      super(new BorderLayout());
      add(mTextArea);
      mTextArea.setText(text);
      mTextArea.setFont(new Font("monospaced", 0, 12));
      JPanel buttons = new JPanel(new BorderLayout());
      JButton ok = new JButton("OK");
      JButton cancel = new JButton("Cancel");
      ok.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            mHitOk = true;
            jd.dispose();
         }
      });
      cancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            mHitOk = false;
            jd.dispose();
         }
      });
      JPanel spacer = new JPanel(new GridLayout(1, 2, 4, 0));
      spacer.add(ok);
      spacer.add(cancel);
      spacer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      buttons.add(spacer, BorderLayout.EAST);

      add(buttons, BorderLayout.SOUTH);
   }
}
