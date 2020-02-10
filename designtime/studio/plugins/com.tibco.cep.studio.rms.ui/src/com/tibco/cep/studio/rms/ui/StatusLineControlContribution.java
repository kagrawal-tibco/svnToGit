package com.tibco.cep.studio.rms.ui;

import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.offline;

import org.eclipse.jface.action.IContributionManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import com.tibco.cep.studio.ui.StudioUIManager;

/**
 * Show status for Logged in/out User
 * @author sasahoo
 *
 */
public class StatusLineControlContribution extends WorkbenchWindowControlContribution {

	public CLabel imgLabel;
	private Image image = RMSUIPlugin.getDefault().getImage("icons/user.gif");
	private Image disabledImage= new Image(Display.getDefault(),image,SWT.IMAGE_DISABLE);
	public String text = "";
	public final static int DEFAULT_CHAR_WIDTH = 40;
	public final static Color COLOR_DARK_GRAY = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
	public final static Color COLOR_WIDGET_FOREGROUND = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
	
	@Override
	protected Control createControl(Composite parent) {
		imgLabel = new CLabel(parent, SWT.SHADOW_NONE);
//		StatusLineLayoutData data = new StatusLineLayoutData();
//		data.widthHint = 25;
//		imgLabel.setLayoutData(data);
		updateStatusLine(offline);
		StudioUIManager.getInstance().getGlobalContributionItems().put("com.tibco.cep.studio.rms.ui.auth.statusline", this);
		return imgLabel;
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
		if (imgLabel != null && !imgLabel.isDisposed()) {
			updateStatusLine(this.text);
		}
		if (this.text.length() == 0) {
			if (isVisible()) {
				updateContributor(false);
			}
		} else {
			if (!isVisible()) {
				updateContributor(true);
			}
		}
	}
	
	/**
	 * @param isVisible
	 */
	private void updateContributor(boolean isVisible){
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
		imgLabel.setText(text);
		imgLabel.setForeground(text.contains(offline) ? COLOR_DARK_GRAY : COLOR_WIDGET_FOREGROUND);
		imgLabel.setImage( text.contains(offline) ? disabledImage:image);
	}
}