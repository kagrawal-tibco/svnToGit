/*
 * @(#)FieldArea.java 4/29/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.decision;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * A panel contains all the FieldBoxes.
 */
public class DecisionFieldArea extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int _areaType;
    private DecisionTablePane _decisionTablePane;
    private int _minimumSize = 25;

    private JTable _table;
    private int _startingColumnIndex;

    private class FieldAreaLayout implements LayoutManager {
        int hgap = DecisionTablePane.FIELD_GAP;

        public void addLayoutComponent(String name, Component comp) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension preferredLayoutSize(Container target) {
            synchronized (target.getTreeLock()) {
                Dimension dim = new Dimension(0, _minimumSize);
                int nmembers = target.getComponentCount();
                boolean firstVisibleComponent = true;

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = m.getPreferredSize();
                        dim.height = Math.max(dim.height, d.height);
                        if (firstVisibleComponent) {
                            firstVisibleComponent = false;
                        }
                        else {
                            dim.width += hgap * 2;
                        }
                        dim.width += d.width;
                    }
                }

                Insets insets = target.getInsets();
                dim.width += insets.left + insets.right + hgap * 2;
                dim.height += insets.top + insets.bottom;
                return dim;
            }
        }

        public Dimension minimumLayoutSize(Container target) {
            synchronized (target.getTreeLock()) {
                Dimension dim = new Dimension(0, _minimumSize);
                Insets insets = target.getInsets();
                dim.width += insets.left + insets.right + hgap * 2;
                dim.height += insets.top + insets.bottom;
                return dim;
            }
        }

        public Dimension actualPreferredLayoutSize(Container target) {
            synchronized (target.getTreeLock()) {
                Dimension dim = new Dimension(0, _minimumSize);
                int nmembers = target.getComponentCount();
                boolean firstVisibleComponent = true;

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = m.getPreferredSize();
                        dim.height = Math.max(dim.height, d.height);
                        if (firstVisibleComponent) {
                            firstVisibleComponent = false;
                        }
                        else {
                            dim.width += hgap * 2;
                        }
                        dim.width += d.width;
                    }
                }
                Insets insets = target.getInsets();
                dim.width += insets.left + insets.right + hgap * 2;
                dim.height += insets.top + insets.bottom;
                return dim;
            }
        }

        public boolean notFit(Container target) {
            Dimension size = actualPreferredLayoutSize(target);
            return size.width > target.getWidth();
        }

        public void layoutContainer(Container target) {
            synchronized (target.getTreeLock()) {
                Insets insets = target.getInsets();
                int maxwidth = target.getWidth() - (insets.left + insets.right + hgap * 2);
                int nmembers = target.getComponentCount();
                if (nmembers == 0) {
                    return;
                }
                int x = 0, y = insets.top;
                int rowh = 0, start = 0;

                boolean ltr = target.getComponentOrientation().isLeftToRight();

                boolean notFit = notFit(target);
                int shrinkWidth = target.getWidth() / nmembers - hgap * 2;

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = m.getPreferredSize();
                        if (notFit) {
                            d.width = shrinkWidth;
                        }
                        if (_table != null && i >= 0 && i < _table.getColumnModel().getColumnCount() - _startingColumnIndex) {
                            d.width = _table.getColumnModel().getColumn(i + _startingColumnIndex).getWidth() - hgap * 2;
                        }

                        Dimension size = new Dimension(d.width, Math.max(d.height, _minimumSize));
                        m.setSize(size);

                        if (x + d.width > maxwidth) {
                            d.width = maxwidth - x;
                        }
                        if ((x == 0) || ((x + d.width) <= maxwidth)) {
                            if (x > 0) {
                                x += hgap * 2;
                            }
                            x += d.width;
                            rowh = Math.max(rowh, d.height);
                        }
                        else {
                            System.out.println("not gonna happen");
                        }
                    }
                }
                moveComponents(target, insets.left + hgap, y, maxwidth - x, getHeight() - insets.top - insets.bottom, start, nmembers, ltr);
            }
        }

        private void moveComponents(Container target, int x, int y, int width, int height,
                                    int rowStart, int rowEnd, boolean ltr) {
            synchronized (target.getTreeLock()) {
                for (int i = rowStart; i < rowEnd; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        if (ltr) {
                            m.setLocation(x, y + (height - m.getHeight()) / 2);
                        }
                        else {
                            m.setLocation(target.getWidth() - x - m.getWidth(), y + (height - m.getHeight()) / 2);
                        }
                        x += m.getWidth() + hgap * 2;
                    }
                }
            }
        }
    }

    public DecisionFieldArea(DecisionTablePane decisionTablePane, int areaType) {
        _areaType = areaType;
        _decisionTablePane = decisionTablePane;
        initComponents();
    }

    protected void initComponents() {
        setLayout(new FieldAreaLayout());
        setBorder(BorderFactory.createEmptyBorder(DecisionTablePane.FIELD_GAP, 0, DecisionTablePane.FIELD_GAP, 0));
        setForeground(Color.GRAY);
    }

    public Component[] getFieldBoxes() {
        return getComponents();
    }

    public int getAreaType() {
        return _areaType;
    }

    public void setAreaType(int areaType) {
        _areaType = areaType;
    }

    public int getMinimumHeight() {
        return _minimumSize;
    }

    public void setMinimumHeight(int minimumHeight) {
        _minimumSize = minimumHeight;
    }

    @Override
    public Dimension getMinimumSize() {
        Insets insets = getInsets();
        Dimension minimumSize;
        if (getComponentCount() == 0) {
            int width = SwingUtilities.computeStringWidth(getFontMetrics(getFont()), _decisionTablePane.getFieldAreaMessage(getAreaType())) + 12;
            minimumSize = new Dimension(width + insets.left + insets.right, _minimumSize + insets.top + insets.bottom);
        }
        else {
            minimumSize = new Dimension(super.getMinimumSize().width, _minimumSize + insets.top + insets.bottom);
        }
        minimumSize.width += 2 * DecisionTablePane.FIELD_GAP; // add extra gap if the field area is not shrink so that user can tell data field area apart from the row field area.
        return minimumSize;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        if (getComponentCount() == 0) {
            preferredSize = new Dimension(Math.max(preferredSize.width, getMinimumSize().width), preferredSize.height);
        }
        else {
            Insets insets = getInsets();
            if (preferredSize.width < _minimumSize) {
                preferredSize.width = _minimumSize + insets.left + insets.right;
            }
            if (preferredSize.height < _minimumSize) {
                preferredSize.height = _minimumSize + insets.top + insets.bottom;
            }
        }
        return preferredSize;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getComponentCount() == 0) {
            g.setColor(getForeground());
            g.setFont(getFont());
            Insets insets = getInsets();
            g.drawString(_decisionTablePane.getFieldAreaMessage(getAreaType()), insets.left + 6, insets.top + (getHeight() - insets.top - insets.bottom) / 2 + g.getFontMetrics().getAscent() / 2);
        }
    }

    public void setTable(JTable table, int startColumnIndex) {
        _table = table;
        _startingColumnIndex = startColumnIndex;
    }

    public void setStartingColumnIndex(int startColumnIndex) {
        _startingColumnIndex = startColumnIndex;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Component[] components = getComponents();
        for (Component component : components) {
            component.invalidate();
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        Component[] components = getComponents();
        for (Component component : components) {
            component.doLayout();
        }
    }

    public void savePreferredWidth() {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof DecisionFieldBox) {
                DecisionFieldBox fieldBox = (DecisionFieldBox) component;
                fieldBox.getField().setPreferredWidth(fieldBox.getWidth());
                fieldBox.setSize(new Dimension(fieldBox.getWidth(), fieldBox.getPreferredSize().height));
            }
        }
    }
}