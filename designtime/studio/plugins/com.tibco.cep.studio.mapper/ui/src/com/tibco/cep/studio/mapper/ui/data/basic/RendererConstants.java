package com.tibco.cep.studio.mapper.ui.data.basic;

import java.awt.Color;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class RendererConstants {
   public static final Color SHADOW_COLOR;
   public static final Color DARK_SHADOW_COLOR;
   public static final Color HILITE_COLOR;
   public static final Color LITE_HILITE_COLOR;
   public static final int ERROR_OVERLAP = 10;

   static {
      UIDefaults table = UIManager.getLookAndFeelDefaults();
      SHADOW_COLOR = table.getColor("controlShadow");
      DARK_SHADOW_COLOR = table.getColor("controlDkShadow");
      HILITE_COLOR = table.getColor("controlHighlight");
      LITE_HILITE_COLOR = table.getColor("controlLtHighlight");
   }
}
