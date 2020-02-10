package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter;
import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenterExtension;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class BrowserInformationControl extends AbstractInformationControl {

	/** The control's browser widget */
	private Browser fBrowser;
	
	/** The information presenter, or <code>null</code> if none. */
	private final IInformationPresenter fPresenter;
	
	/** A cached text presentation */
	private final TextPresentation fPresentation= new TextPresentation();
	
	public BrowserInformationControl(Shell parent, boolean isResizeable) {
		super(parent, isResizeable);
		fPresenter= new StudioInformationPresenterExtension();
		create();
	}

	public BrowserInformationControl(Shell parent, String statusFieldText) {
		this(parent, statusFieldText, new StudioInformationPresenterExtension());
	}

	public BrowserInformationControl(Shell parent, String statusFieldText, IInformationPresenter presenter) {
		super(parent, statusFieldText);
		fPresenter= presenter;
		create();
	}

	public BrowserInformationControl(Shell parent, ToolBarManager toolBarManager) {
		this(parent, toolBarManager, new StudioInformationPresenterExtension());
	}

	public BrowserInformationControl(Shell parent, ToolBarManager toolBarManager, IInformationPresenter presenter) {
		super(parent, toolBarManager);
		fPresenter = presenter;
		create();
	}

	/**
	 * Creates a default information control with the given shell as parent.
	 * No information presenter is used to process the information
	 * to be displayed.
	 *
	 * @param parent the parent shell
	 */
	public BrowserInformationControl(Shell parent) {
		this(parent, (String)null, null);
	}

	/**
	 * Creates a default information control with the given shell as parent. The given
	 * information presenter is used to process the information to be displayed.
	 *
	 * @param parent the parent shell
	 * @param presenter the presenter to be used
	 */
	public BrowserInformationControl(Shell parent, IInformationPresenter presenter) {
		this(parent, (String)null, presenter);
	}

	/*
	 * @see org.eclipse.jface.text.AbstractInformationControl#createContent(org.eclipse.swt.widgets.Composite)
	 */
	protected void createContent(Composite parent) {
		fBrowser = new Browser(parent, SWT.MULTI | SWT.READ_ONLY);
		fBrowser.setForeground(parent.getForeground());
		fBrowser.setBackground(parent.getBackground());
		fBrowser.setFont(JFaceResources.getDialogFont());
	}
	
	/*
	 * @see IInformationControl#setInformation(String)
	 */
	public void setInformation(String content) {
		if (content.indexOf("<html>") != -1) {
			fBrowser.setText(content);
			updateBrowserSize(content);
			return;
		}
		if (fPresenter == null) {
			fBrowser.setText(content);
			updateBrowserSize(content);
			return;
		} 

		fPresentation.clear();

		int maxWidth= -1;
		int maxHeight= -1;
		Point constraints= getSizeConstraints();
		if (constraints != null) {
			maxWidth= constraints.x;
			maxHeight= constraints.y;
			Rectangle trim= computeTrim();
			maxWidth-= trim.width;
			maxHeight-= trim.height;
		}
		if (isResizable())
			maxHeight= Integer.MAX_VALUE;

		if (fPresenter instanceof IInformationPresenterExtension) {
			content= ((IInformationPresenterExtension)fPresenter).updatePresentation(fBrowser, content, fPresentation, maxWidth, maxHeight);
		}

		if (content != null) {
			fBrowser.setText(content);
		} else {
			fBrowser.setText(""); //$NON-NLS-1$
		}
		Point computeSize = fBrowser.computeSize(400, 80);
		setSizeConstraints(computeSize.x, computeSize.y);
	}

	private void updateBrowserSize(String content) {
		String text = content;
		Pattern pattern = Pattern.compile("width=\"([0-9]+)\"");
		Matcher matcher = pattern.matcher(text);
		int maxWidth = fBrowser.getSize().x;
		while (matcher.find()) {
			String widthString = matcher.group();
			try {
				int width = Integer.parseInt(widthString.substring(7, widthString.length()-1));
				maxWidth = Math.max(maxWidth, width);
			} catch (NumberFormatException e) {
			}
		}
		maxWidth = Math.max(maxWidth, 400); // make it at least 400 pixels wide regardless
		setSizeConstraints(maxWidth, getSizeConstraints().y);
	}

	@Override
	public Point computeSizeConstraints(int widthInChars, int heightInChars) {
		Point computeSize = fBrowser.computeSize(widthInChars, heightInChars);
		computeSize.y = Math.max(computeSize.y, 300);
		computeSize.x = Math.max(computeSize.x, 750);
		System.out.println("computed size is "+computeSize.x);
		return computeSize;
	}

	/*
	 * @see IInformationControl#setForegroundColor(Color)
	 */
	public void setForegroundColor(Color foreground) {
		super.setForegroundColor(foreground);
		fBrowser.setForeground(foreground);
	}

	/*
	 * @see IInformationControl#setBackgroundColor(Color)
	 */
	public void setBackgroundColor(Color background) {
		super.setBackgroundColor(background);
		fBrowser.setBackground(background);
	}

	/*
	 * @see IInformationControlExtension#hasContents()
	 */
	public boolean hasContents() {
		return true;//fBrowser.getText().length() > 0;
	}

	/*
	 * @see org.eclipse.jface.text.IInformationControlExtension5#getInformationPresenterControlCreator()
	 * @since 3.4
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			/*
			 * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(org.eclipse.swt.widgets.Shell)
			 */
			public IInformationControl createInformationControl(Shell parent) {
				return new BrowserInformationControl(parent, (ToolBarManager) null, fPresenter);
			}
		};
	}
	
}
