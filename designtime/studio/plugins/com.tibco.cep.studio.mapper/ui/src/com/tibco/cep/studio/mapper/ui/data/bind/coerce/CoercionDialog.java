package com.tibco.cep.studio.mapper.ui.data.bind.coerce;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorResources;
import com.tibco.cep.studio.mapper.ui.data.utils.OKCancelPanel;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.xml.schema.SmNamespaceProvider;

/**
 * The dialog that handles coercions (i.e. casts/asserts)
 */
public class CoercionDialog extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private boolean m_okHit;

   private CoercionPanel m_panel;

private UIAgent uiAgent;

   public CoercionDialog(Frame frame) {
      super(frame);
   }

   public CoercionDialog(Dialog frame) {
      super(frame);
   }

   private void init(UIAgent uiAgent, DataTree dt, String xpath, CoercionSet coercionSet, SmNamespaceProvider provider, ExprContext exprContext, ImportRegistry ir, boolean editable) {
      setTitle(BindingEditorResources.COERCE_TITLE);

      this.uiAgent = uiAgent;
      if (provider == null) {
         throw new NullPointerException("Null provider");
      }

      m_panel = new CoercionPanel(uiAgent, dt, coercionSet, xpath, exprContext, ir);
      OKCancelPanel okc = new OKCancelPanel();

      okc.getOKButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            m_okHit = true;
            dispose();
         }
      });
      if (!editable) {
         okc.getOKButton().setEnabled(false);
      }
      m_panel.setEditable(editable);
      okc.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      getContentPane().add(m_panel, BorderLayout.CENTER);
      getContentPane().add(okc, BorderLayout.SOUTH);
   }

   public void dispose() {
      PreferenceUtils.writeDimension(uiAgent, "CoercionDialog.windowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, "CoercionDialog.windowLocation", getLocation());
      m_panel.writePreferences(uiAgent, "CoercionDialog");
      super.dispose();
   }

   private void rebuildSet(CoercionSet set) {
      m_panel.rebuildSet(set);
   }

   /**
    * For coercion:
    */
   public static boolean showDialog(Window window, UIAgent uiAgent,
                                    DataTree dt, String xpath, CoercionSet set, SmNamespaceProvider provider, ExprContext exprContext, ImportRegistry ir, boolean editable) {
      CoercionDialog sd;
      if (window instanceof Dialog) {
         sd = new CoercionDialog((Dialog) window);
      }
      else {
         sd = new CoercionDialog((Frame) window);
      }
      sd.setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      sd.init(uiAgent,
              dt,
              xpath,
              set,
              provider,
              exprContext,
              ir,
              editable);

      Dimension sz = PreferenceUtils.readDimension(uiAgent, "CoercionDialog.windowSize",
                                                   new Dimension(400, 200),
                                                   Toolkit.getDefaultToolkit().getScreenSize(),
                                                   new Dimension(450, 300));
      Point loc = PreferenceUtils.readLocation(uiAgent, "CoercionDialog.windowLocation", null, sz);
      sd.setSize(sz);
      if (loc == null) {
         sd.setLocationRelativeTo(window);
      }
      else {
         sd.setLocation(loc);
      }
      sd.m_panel.readPreferences(uiAgent, "CoercionDialog");
      sd.setModal(true);
      sd.setVisible(true);
      if (sd.m_okHit) {
         sd.rebuildSet(set);
         return true;
      }
      return sd.m_okHit;
   }
}

