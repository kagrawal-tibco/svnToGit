package com.tibco.cep.studio.ui.util;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioStatusLineItem extends ContributionItem {

	private int charWidth;
	public CLabel label;
	public CLabel imgLabel;
	private Composite statusLine = null;
	private int widthHint = -1;
	private int heightHint = -1;
	private int background=-1;
	private Image image;
	private Image disabledImage;
	public String text = "";
	public final static int DEFAULT_CHAR_WIDTH = 40;
	public final static Color COLOR_DARK_GRAY = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
	public final static Color COLOR_WIDGET_FOREGROUND = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
	public final static String suffix = "(offline)";

	/**
	 * @param id
	 * @param background
	 * @param image
	 */
	public StudioStatusLineItem(String id,int background, Image image) {
		this(id, DEFAULT_CHAR_WIDTH,background, image);
	}

	/**
	 * @param id
	 * @param charWidth
	 * @param background
	 * @param image
	 */
	public StudioStatusLineItem(String id, int charWidth, int background,Image image) {
		super(id);
		this.charWidth = charWidth;
		this.background = background;
		this.image = image;
		this.disabledImage = new Image(Display.getDefault(),image,SWT.IMAGE_DISABLE);
		setVisible(false); // no text to start with
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.Composite)
	 */
	public void fill(Composite parent) {
		statusLine = parent;
		Label sep = new Label(parent, SWT.SEPARATOR);
		imgLabel = new CLabel(statusLine, SWT.SHADOW_NONE);
		label = new CLabel(statusLine, SWT.SHADOW_NONE);
		if (widthHint < 0) {
			GC gc = new GC(statusLine);
			gc.setFont(statusLine.getFont());
			FontMetrics fm = gc.getFontMetrics();
			widthHint = fm.getAverageCharWidth() * charWidth;
			heightHint = fm.getHeight();
			gc.dispose();
		}
		StatusLineLayoutData data = new StatusLineLayoutData();
		data.widthHint = widthHint;
		label.setLayoutData(data);
		label.setBackground(Display.getDefault().getSystemColor(background));
		
		data = new StatusLineLayoutData();
		data.widthHint = 25;
		imgLabel.setLayoutData(data);
//		imgLabel.setBackground(Display.getDefault().getSystemColor(background));
		
		updateStatusLine(text);
		
		data = new StatusLineLayoutData();
		data.heightHint = heightHint;
		sep.setLayoutData(data);
	}

	/**
	 * @return
	 */
	public Point getDisplayLocation() {
		if ((label != null) && (statusLine != null)) {
			return statusLine.toDisplay(label.getLocation());
		}
		return null;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 */
	public void setText(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.text = text;
		if (label != null && !label.isDisposed() 
				&& imgLabel != null && !imgLabel.isDisposed()) {
			updateStatusLine(this.text);
		}
		if (this.text.length() == 0) {
			if (isVisible()) {
				updateContributorManager(false);
			}
		} else {
			if (!isVisible()) {
				updateContributorManager(true);
			}
		}
	}

	/**
	 * @param isVisible
	 */
	private void updateContributorManager(boolean isVisible){
		setVisible(isVisible);
		IContributionManager contributionManager = getParent();
		if (contributionManager != null) {
			contributionManager.update(true);
		}
	}

	/**
	 * @param text
	 */
	private void updateStatusLine(String text){
		label.setText(text);
		label.setForeground(text.contains(suffix)?COLOR_DARK_GRAY:COLOR_WIDGET_FOREGROUND);
		imgLabel.setImage(text.contains(suffix)?disabledImage:image);
	}
}