package com.tibco.cep.studio.mapper.ui.data.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * A set of constants for spacing/alignment to create a consistent look.
 */
public class DisplayConstants {
    private static final Border INTERNAL_BORDER;
    private static final Border TREE_CONTENTS_BORDER;
    private static final Border COMPRESSED_TREE_CONTENTS_BORDER;
    private static final Border EMPTY_BORDER;

    static {
        INTERNAL_BORDER = BorderFactory.createEmptyBorder(6,6,5,5);
        TREE_CONTENTS_BORDER = BorderFactory.createEmptyBorder(4,1,8,1);
        COMPRESSED_TREE_CONTENTS_BORDER = BorderFactory.createEmptyBorder(4,4,8,1);
        EMPTY_BORDER = BorderFactory.createEmptyBorder(0,0,0,0);
    }

    /**
     * The standard border for sizing one window area inside a larger one, currently an empty 5,5,6,6 pixel border
     */
    public static Border getInternalBorder() {
        return INTERNAL_BORDER;
    }

    /**
     * The border applied around tree contents (inside the scroll bar)
     */
    public static Border getTreeContentsBorder() {
        return TREE_CONTENTS_BORDER;
    }

    /**
     * The border applied around basic trees (where indent has been compressed from around 20 to 11 pixels)
     */
    public static Border getCompressedTreeContentsBorder() {
        return COMPRESSED_TREE_CONTENTS_BORDER;
    }

    /**
     * A 0,0,0,0 empty border.
     */
    public static Border getEmptyBorder() {
        return EMPTY_BORDER;
    }

    /**
     * Creates a header/body panel, where the header is shaded & outlined as in a label.
     * @param header The header
     * @param body The body.
     * @return The panel.
     */
    public static JPanel createHeaderPanel(Component header, Component body)
    {
        JPanel p = new JPanel(new BorderLayout());
        p.add(body);
        JPanel area = new JPanel(new BorderLayout());
        area.add(header);
        Border space = BorderFactory.createEmptyBorder(4,8,8,8);
        Border bevel = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        area.setBorder(BorderFactory.createCompoundBorder(bevel,space));
        area.setBackground(new Color(255,255,240));
        JPanel spacer = new JPanel(new BorderLayout());
        spacer.add(area);
        spacer.setOpaque(false);
        spacer.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        p.add(spacer,BorderLayout.NORTH);
        return p;
    }
}
