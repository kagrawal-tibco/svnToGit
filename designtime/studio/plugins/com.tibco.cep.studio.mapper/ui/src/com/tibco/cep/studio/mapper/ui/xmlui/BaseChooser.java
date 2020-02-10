package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.BetterJDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.utils.ScreenUtilities;

/**
 * Base class for a chooser dialog with a preview area.
 * WCETODO -- need to reconcile w/ whatever Designer has now..., but before that, need to merge with XMLChooser
 */
public abstract class BaseChooser extends BetterJDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
    * The tree to display information
    */
   private JPanel m_previewArea;
   private boolean m_okHit = false;
   private String m_prefsPrefix;

   /**
    * The DesignerDocument associated with this project
    */
   protected final UIAgent uiAgent;
   private JSplitPane m_pickAndPreviewSplitter;

   /**
    * PreferencesId is used for writing preferences to designer file (i.e. last window size, etc.)
    *
    * @param doc The DesignerDocument
    */
   protected BaseChooser(UIAgent uiAgent) {
      super(uiAgent, uiAgent.getFrame(), ResourceBundleManager.getMessage("resource.chooser.title"), true);
      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

      this.uiAgent = uiAgent;
   }

   protected void init(JComponent mainView, String prefsPrefix) {
      m_pickAndPreviewSplitter = new JSplitPane();
      m_pickAndPreviewSplitter.setContinuousLayout(true);
      JPanel previewLabelledArea = new JPanel(new BorderLayout());
      previewLabelledArea.add(createUnderlineLabel(QNamePanelResources.PREVIEW), BorderLayout.NORTH);
      m_previewArea = new JPanel(new BorderLayout());
      previewLabelledArea.add(m_previewArea);
      m_pickAndPreviewSplitter.setRightComponent(previewLabelledArea);
      m_pickAndPreviewSplitter.setLeftComponent(mainView);
      m_prefsPrefix = prefsPrefix;

      getContentPane().add(m_pickAndPreviewSplitter, BorderLayout.CENTER);

      // right component set on setPreview

      JPanel buttons = buildOkCancelButtons();
      getContentPane().add(buttons, BorderLayout.SOUTH);
      pack();

      int dividerLoc = PreferenceUtils.readInt(uiAgent, prefsPrefix + ".splitterLocation", 300);
      m_pickAndPreviewSplitter.setDividerLocation(dividerLoc);

      Dimension defaultSize = getSize();
      Dimension size = PreferenceUtils.readDimension(uiAgent, prefsPrefix + ".windowSize",
                                                     new Dimension(500, 300),
                                                     Toolkit.getDefaultToolkit().getScreenSize(),
                                                     defaultSize);

      setSize(size);

      Point wloc = PreferenceUtils.readLocation(uiAgent, prefsPrefix + ".windowLocation", null, size);
      if (wloc == null) {
         ScreenUtilities.centerInScreen(this);
      }
      else {
         setLocation(wloc);
      }
   }

   /**
    * Save the dialogs location and size
    */
   public void dispose() {
      // save preferences:
      PreferenceUtils.writeInt(uiAgent, m_prefsPrefix + ".splitterLocation", m_pickAndPreviewSplitter.getDividerLocation());
      PreferenceUtils.writeDimension(uiAgent, m_prefsPrefix + ".windowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, m_prefsPrefix + ".windowLocation", getLocation());

      super.dispose();
   }

   /**
    * Enable the preview panel and add the specified component
    *
    * @param c The component to add to the right side of the splitpane
    */
   public void setPreview(JComponent c) {
      if (c == null) {
         c = new JLabel("Internal error: Null preview"); // Shouldn't happen, but don't crash....
      }
      m_previewArea.removeAll();
      m_previewArea.add(c);
      m_previewArea.revalidate();
      m_previewArea.repaint();
   }

   /**
    * Clear the preview panel and add the specified component
    */
   public void clearPreview() {
      setPreview(new JLabel());
   }

   private JPanel buildOkCancelButtons() {
      JPanel buttons = new JPanel();
      buttons.setLayout(new GridLayout(1, 2, 8, 0));

      JButton ok = new JButton(ResourceBundleManager.getMessage("ok"));
      ok.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            m_okHit = true;
            dispose();
         }
      });

      buttons.add(ok);

      JButton cancel = new JButton(ResourceBundleManager.getMessage("cancel"));
      cancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            m_okHit = false;
            dispose();
         }
      });

      buttons.add(cancel);

      JPanel spacer = new JPanel(new BorderLayout());
      spacer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      spacer.add(buttons, BorderLayout.EAST);
      return spacer;
   }

   public boolean isOK() {
      return m_okHit;
   }

   private static JLabel createUnderlineLabel(String name) {
      JLabel lbl = new JLabel(name);
      // A Simple underlining border (Move to somewhere common)
      lbl.setBorder(new Border() {
         public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.black);
            int yat = y + height - 1;
            g.drawLine(x, yat, x + width, yat);
         }

         public Insets getBorderInsets(Component c) {
            return new Insets(0, 2, 3, 0);
         }

         public boolean isBorderOpaque() {
            return true;
         }

      });
      return lbl;
   }
}

