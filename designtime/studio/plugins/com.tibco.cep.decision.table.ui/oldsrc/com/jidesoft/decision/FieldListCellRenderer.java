/*
 * @(#)FieldListCellRenderer.java 5/9/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.decision;

import java.awt.Component;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ConverterContextSupport;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.plaf.UIDefaultsLookup;

/**
 * TableCellRenderer which can accept ConverterContext. All TableCellRenderers we built extends this class.
 */
abstract class FieldListCellRenderer extends DefaultListCellRenderer implements ConverterContextSupport, SwingConstants {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static EditorContext CONTEXT_SENSITIVE_CONTEXT = new EditorContext("ContextSensitiveCellRenderer");
    private ConverterContext _context;
    private Class<?> _class;

    /**
     * Creates a context sensitive cell renderer.
     */
    public FieldListCellRenderer() {
        setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    }

    /**
     * Creates a context sensitive cell renderer for a specified type.
     *
     * @param clazz type
     */
    public FieldListCellRenderer(Class<?> clazz) {
        _class = clazz;
        setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    }

    /**
     * Creates a context sensitive cell renderer using the converter context.
     *
     * @param context converter context
     */
    public FieldListCellRenderer(ConverterContext context) {
        _context = context;
        setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    }

    /**
     * Creates a context sensitive cell renderer using specified type and the converter context.
     *
     * @param clazz   type
     * @param context converter context
     */
    public FieldListCellRenderer(Class<?> clazz, ConverterContext context) {
        _class = clazz;
        _context = context;
        setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        applyComponentOrientation(list.getComponentOrientation());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (value instanceof Icon) {
            setIcon((Icon) value);
            setText("");
        }
        else {
            setIcon(null);
            setText(convertElementToString(list.getLocale(), value, getType(), getConverterContext()));
        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());

        Border border = null;
        if (cellHasFocus) {
            if (isSelected) {
                border = UIDefaultsLookup.getBorder("List.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIDefaultsLookup.getBorder("List.focusCellHighlightBorder");
            }
        }
        else {
            border = noFocusBorder;
        }
        setBorder(border);

        return this;
    }

    /**
     * Sets the converter context.
     *
     * @param context converter context
     */
    public void setConverterContext(ConverterContext context) {
        _context = context;
    }

    /**
     * Gets the converter context.
     *
     * @return converter context
     */
    public ConverterContext getConverterContext() {
        return _context;
    }

    public Class<?> getType() {
        return _class;
    }

    public void setType(Class<?> clazz) {
        _class = clazz;
    }

    abstract public String convertElementToString(Locale locale, Object value, Class<?> clazz, ConverterContext context);
}