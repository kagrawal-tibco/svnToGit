package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.studio.mapper.ui.data.utils.VerticalLabelPanel;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The error message portion of the {@link XPathEditWindow}.<br>
 * Handles error display stuff.
 * WCETODO rename to 'generic', move.
 */
public class XPathEditWindowErrorArea extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private NamespaceContextRegistry m_namespaceContextRegistry;
   private JLabel mErrorLabel;
   private JPanel mErrorLabelArea;
   private JPanel mErrorArea;
   private Expr m_currentExpr;
   private FixCallback m_callback;
   private Font m_appFont;

   /**
    * @deprecated Use version which passes in application font.
    */
   public XPathEditWindowErrorArea(FixCallback fixCallback) {
      this(fixCallback, null);
   }

   public XPathEditWindowErrorArea(FixCallback fixCallback, Font appFont) {
      super(new BorderLayout());
      m_appFont = appFont;
      if (fixCallback == null) {
         throw new NullPointerException();
      }
      m_callback = fixCallback;

      JLabel label = new JLabel(XPathEditResources.EVALUATING_LABEL);
      mErrorLabel = label;
      mErrorArea = new JPanel(new BorderLayout());
      mErrorArea.setOpaque(true);
      mErrorArea.setBackground(Color.white);

      mErrorLabelArea = new VerticalLabelPanel(label, mErrorArea);
      mErrorLabelArea.setVisible(false);
      add(mErrorLabelArea, BorderLayout.CENTER);
   }

   public interface FixCallback {
      void setFixed(String newExpression);
   }

   public boolean isShowingError() {
      return mErrorLabelArea.isVisible();
   }

   public void update(Expr expr, XPathTypeReport report) {
      m_currentExpr = expr;

      if (report.errors.getErrorAndWarningMessages().length == 0) {
         // No errors or warnings:
         mErrorLabelArea.setVisible(false);
      }
      else {
         // had an error only:
         mErrorArea.removeAll();
         final ErrorMessage em = report.errors.getErrorAndWarningMessages()[0];
         if (em.getType() == ErrorMessage.TYPE_ERROR) {
            mErrorLabel.setText(XPathEditResources.EVALERROR_LABEL);
         }
         else {
            mErrorLabel.setText(XPathEditResources.EVALWARNING_LABEL);
         }
         JPanel panel = new JPanel(new BorderLayout());
         panel.setBorder(BorderFactory.createEmptyBorder(4, 11, 4, 11));
         JLabel label = new JLabel("<html>" + em.getMessage() + "</html>");
         if (m_appFont != null) {
            // Potential fix for 1-18QN70, where the Japanese version didn't show up correctly; try setting the
            // app font explicitly:
            label.setFont(m_appFont);
         }
         panel.add(label, BorderLayout.WEST);
         panel.setOpaque(true);
         panel.setBackground(Color.white);
         mErrorArea.add(panel, BorderLayout.WEST);
         mErrorArea.add(new JLabel(), BorderLayout.CENTER);

         if (em.getFilter() != null) {
            // Add 'fix' button:
            JButton fix = new JButton(XPathEditResources.FIX_LABEL);
            fix.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  applyFix(em);
               }
            });
            JPanel spacer = new JPanel(new BorderLayout());
            spacer.add(fix, BorderLayout.WEST);
            spacer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            mErrorArea.add(spacer, BorderLayout.EAST);
         }

         mErrorLabelArea.setVisible(true);


         revalidate();
         repaint();
      }
   }

   public void setNamespaceImporter(NamespaceContextRegistry namespaceContextRegistry) {
      m_namespaceContextRegistry = namespaceContextRegistry;
   }

   private void applyFix(ErrorMessage em) {
      if (em.getFilter() == null || m_currentExpr == null) // sanity
      {
         return;
      }
      Expr ne = em.getFilter().filter(m_currentExpr, m_namespaceContextRegistry);
      String nxp = ne.toExactString();
      m_callback.setFixed(nxp);
   }
}
