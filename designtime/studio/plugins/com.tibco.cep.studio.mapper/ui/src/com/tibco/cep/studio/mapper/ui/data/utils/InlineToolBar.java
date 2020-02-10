package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;

/**
 * 'Standard' layout for a toolbar meant for an inline editor.
 * Use ButtonUtils to make the buttons themselves.
 */
public class InlineToolBar extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel mLabel;
    private JPanel mButtons;

    public InlineToolBar(UIAgent uiAgent) {
        super(new BorderLayout());

        int labelDataSpacing = DataIcons.getLabelDataSpacing(); // same as label leftSpacing??
        int interButtonSpacing = 2;

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT,labelDataSpacing,0));
        panel.setBorder(BorderFactory.createEmptyBorder(2,0,2,0));

        mLabel = new JLabel();
        mLabel.setFont(uiAgent.getAppFont().deriveFont(Font.BOLD));
        panel.add(mLabel);
        mButtons = new JPanel(new FlowLayout(FlowLayout.LEFT,interButtonSpacing,0));

        // Add this so that it lays out the same even if there are no buttons.
        JComponent spacer = new JLabel();
        int buttonHeight = ButtonUtils.INLINE_BUTTON_HEIGHT;

        Dimension labelHeight = new Dimension(0,buttonHeight);
        spacer.setPreferredSize(labelHeight);
        spacer.setMinimumSize(labelHeight);

        mButtons.add(spacer);

        panel.add(mButtons);
        add(panel,BorderLayout.WEST);
        add(new JSeparator(),BorderLayout.SOUTH);
    }

    /**
     * Sets the label for the buttons, or null if none.
     */
    public void setLabel(String label) {
        mLabel.setText(label);
        revalidate();
    }

    public String getLabel() {
        return mLabel.getText();
    }

    public void setLabelToolTip(String labelTooltip) {
        mLabel.setToolTipText(labelTooltip);
    }

    public String getLabelToolTip()
    {
        return mLabel.getToolTipText();
    }

    public void addSeparator() {
        mButtons.add(new JLabel(" "));
    }

    public void addButton(AbstractButton button) {
        if (mButtons.getComponent(0) instanceof JLabel) {
            // remove the spacer.
            mButtons.removeAll();
        }
        mButtons.add(button);
    }
}

