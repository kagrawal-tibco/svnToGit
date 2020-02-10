/*
 * @(#)Overview.java 12/6/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.tibco.cep.studio.ui.overview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class Overview extends JPanel implements ComponentListener,
		ChangeListener, PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane _scrollPane;
	private double _scale;
	private Color _selectionBorder = Color.BLACK;
	protected int _startX;
	protected int _startY;
	protected Rectangle _rectangle;
	protected BufferedImage _image;
	protected boolean _dragging = false;

	public Overview() {
		initComponents();
	}

	public Overview(JScrollPane scrollPane) {
		initComponents();
		setScrollPane(scrollPane);
	}

	public Overview(JScrollPane scrollPane, Dimension size) {
		initComponents();
		setScrollPane(scrollPane);
		setPreferredSize(size);
	}

	/**
	 * Init
	 */
	protected void initComponents() {
		_scale = 0.0;
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		setOpaque(true);

		MouseInputListener mil = new MouseInputAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point point = e.getPoint();
				scroll(point.x, point.y);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				_dragging = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				_dragging = false;
			}
		};
		addMouseListener(mil);
		addMouseMotionListener(mil);
	}

	public JScrollPane getScrollPane() {
		return _scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		if (_scrollPane != scrollPane) {
			if (_scrollPane != null) {
				_scrollPane.removeComponentListener(this);
				_scrollPane.removePropertyChangeListener("viewport", this);
				_scrollPane.getViewport().removeChangeListener(this);
			}
			_scrollPane = scrollPane;
			if (_scrollPane != null) {
				_scrollPane.addComponentListener(this);
				_scrollPane.addPropertyChangeListener("viewport", this);
				_scrollPane.getViewport().addChangeListener(this);
			}
			_image = null;
			repaint();
		}
	}

	public void componentResized(ComponentEvent e) {
		_image = null;
		repaint();
	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}

    private Dimension _previousSize;
    public void stateChanged(ChangeEvent e) {
        Dimension size = ((JViewport) e.getSource()).getViewSize();
        if(!SwtUtilities.equals(_previousSize, size)) {
            _image = null;
            _previousSize = size;
        }
        repaint();
    }

    public void propertyChange(PropertyChangeEvent evt) {
		if ("viewport".equals(evt.getPropertyName())) {
			if (evt.getOldValue() instanceof JViewport) {
				((JViewport) evt.getOldValue()).removeChangeListener(this);
			}
			if (evt.getNewValue() instanceof JViewport) {
				((JViewport) evt.getNewValue()).addChangeListener(this);
			}
		}
	}

	public void setSelectionBorderColor(Color selectionBorder) {
		_selectionBorder = selectionBorder;
	}

	public Color getSelectionBorder() {
		return _selectionBorder;
	}

	protected void paintComponent(Graphics g) {
		if (_scrollPane == null) {
			super.paintComponent(g);
			return;
		}
		Component viewComponent = _scrollPane.getViewport().getView();
		if (viewComponent == null) {
			super.paintComponent(g);
			return;
		}

		Insets insets = getInsets();
		double w = getWidth() - insets.left - insets.right;
		double h = getHeight() - insets.top - insets.bottom;
		double cw = viewComponent.getWidth();
		double ch = viewComponent.getHeight();
		_scale = Math.min(w / cw, h / ch);
		int iw = (int) (cw * _scale);
		int ih = (int) (ch * _scale);

		if (iw <= 0 || ih <= 0) {
			super.paintComponent(g);
			return;
		}

		if (_image == null) {
			_image = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = _image.createGraphics();

			g2d.scale(_scale, _scale);
			boolean wasDoubleBuffered = viewComponent.isDoubleBuffered();
			try {
				if (viewComponent instanceof JComponent) {
					((JComponent) viewComponent).setDoubleBuffered(false);
				}
				viewComponent.paint(g2d);
			} finally {
				if (viewComponent instanceof JComponent) {
					((JComponent) viewComponent)
							.setDoubleBuffered(wasDoubleBuffered);
				}
				g2d.dispose();
			}
		}

		if (_scrollPane != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			int xOffset = insets.left;
			int yOffset = insets.top;

			g2d.setColor(_scrollPane.getViewport().getView().getBackground());

			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(Color.BLACK);
			_startX = xOffset + (int) ((w - iw) / 2);
			_startY = yOffset + (int) ((h - ih) / 2);
			g2d.drawImage(_image, _startX, _startY, null);
			g2d.drawRect(_startX, _startY, iw, ih);

			int availableWidth = getWidth() - insets.left - insets.right;
			int availableHeight = getHeight() - insets.top - insets.bottom;
			Area area = new Area(new Rectangle(xOffset, yOffset,
					availableWidth, availableHeight));

			Rectangle viewRect = _scrollPane.getViewport().getViewRect();
			_rectangle = new Rectangle(_startX + (int) (viewRect.x * _scale),
					_startY + (int) (viewRect.y * _scale),
					(int) (viewRect.width * _scale),
					(int) (viewRect.height * _scale));
			_rectangle.width = Math.min(_rectangle.width, iw);
			_rectangle.height = Math.min(_rectangle.height, ih);
			area.subtract(new Area(_rectangle));
			g2d.setColor(new Color(255, 255, 255, 128));
			g2d.fill(area);
			g2d.setColor(_selectionBorder);
			g2d.drawRect(_rectangle.x, _rectangle.y, _rectangle.width,
					_rectangle.height);
			g2d.dispose();
		}
	}

	private void scroll(int x, int y) {
		if (_rectangle != null && _scrollPane!=null) {
			int deltaX = x - _startX - _rectangle.width / 2;
			int deltaY = y - _startY - _rectangle.height / 2;
			int cx = (int) (deltaX / _scale);
			int cy = (int) (deltaY / _scale);
			JViewport viewport = _scrollPane.getViewport();
			cx = Math.min(Math.max(0, cx), Math.max(0, viewport.getView()
					.getWidth()
					- viewport.getViewRect().width));
			cy = Math.min(Math.max(0, cy), Math.max(0, viewport.getView()
					.getHeight()
					- viewport.getViewRect().height));
			Rectangle old = _rectangle;
			viewport.setViewPosition(new Point(cx, cy));
			Rectangle viewRect = viewport.getViewRect();
			Rectangle rectangle = new Rectangle(_startX
					+ (int) (viewRect.x * _scale), (int) (viewRect.y * _scale),
					_rectangle.width, _rectangle.height);
			Rectangle clip = new Rectangle();
			Rectangle.union(old, rectangle, clip);
			clip.grow(2, 2);
			paintImmediately(clip);
		}
	}
}
