package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ButtonUtils {

    public static final int INLINE_BUTTON_HEIGHT = 20;
    public static final int INLINE_BUTTON_WIDTH = 20;

    public static JButton makeBarButton(JButton button, Icon icon, String tooltip) {
        button.setIcon(icon);
        setupBarButton(button,tooltip);
        button.setDefaultCapable(false);
        return button;
    }

    public static JButton makeBarButton(Icon icon, String tooltip) {
        JButton button = new JButton(icon);
        setupBarButton(button,tooltip);
        button.setDefaultCapable(false);
        return button;
    }

    public static JButton makeBarButton(Icon icon, String tooltip, final String disabledTooltip) {
        JButton button = new JButton(icon) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String getToolTipText() {
                if (super.isEnabled()) {
                    return super.getToolTipText();
                }
                return disabledTooltip;
            }
        };
        setupBarButton(button,tooltip);
        button.setDefaultCapable(false);
        return button;
    }

    public static JToggleButton makeBarToggleButton(Icon icon, String tooltip) {
        JToggleButton button = new JToggleButton(icon);
        setupBarButton(button,tooltip);
        return button;
    }

    public static void setupBarButton(AbstractButton button, String tooltip) {
        Dimension sz = new Dimension(INLINE_BUTTON_WIDTH,INLINE_BUTTON_HEIGHT);
        button.setPreferredSize(sz);
        button.setMinimumSize(sz);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
    }
}

