package com.tibco.cep.studio.wizard.as.internal.wizard.pages;

import static org.eclipse.swt.SWT.WRAP;
import static org.eclipse.swt.layout.GridData.CENTER;
import static org.eclipse.swt.layout.GridData.FILL;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.internal.wizard.handlers.WizardPageHandlerAdapter;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

@SuppressWarnings("rawtypes")
public class WarningWizardPage extends WizardPage implements IASWizardPage {

	private static final String _PAGE_NAME_WARNING		= "com.tibco.cep.studio.wizard.as.wizard.page.warning"; //$NON-NLS-1$
	private static final String _PAGE_TITLE				= Messages.getString("WarningWizardPage.page_title"); //$NON-NLS-1$
	private static final String _PAGE_DESCRIPTION		= Messages.getString("WarningWizardPage.page_description"); //$NON-NLS-1$
	private static final String _PAGE_WARNING_MESSAGE	= Messages.getString("WarningWizardPage.close_and_resel_valid_as_prj"); //$NON-NLS-1$

	// Controls
	private Composite           parent;
	private Composite			container;
	private Label				messageLabel;

	// Handler
	private Handler				handler;


	public WarningWizardPage() {
		super(_PAGE_NAME_WARNING, _PAGE_TITLE, null);
	}

	@Override
	public void createControl(Composite parent) {
		this.parent = parent;
		initialize();
		setControl(container);
	}

	private void initialize() {
		initUI();
		initListener();
		initData();
	}

	private void initUI() {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		messageLabel = new Label(container, WRAP);
		GridData layoutData = new GridData(FILL, CENTER, true, false);
		layoutData.widthHint = computeMessageLabelWidth(container.getBounds(), layout);
		messageLabel.setLayoutData(layoutData);
	}

	private int computeMessageLabelWidth(Rectangle bounds, GridLayout layout) {
		return bounds.width - layout.marginLeft - layout.marginRight - layout.horizontalSpacing;
	}

	private void initListener() {
		handler = new Handler();

		container.addControlListener(handler);
    }

	private void initData() {
		setPageComplete(false);
		setDescription(_PAGE_DESCRIPTION);
		messageLabel.setText(_PAGE_WARNING_MESSAGE);
	}

	@Override
    public IWizardPageHandler getPageHandler() {
	    return new WizardPageHandlerAdapter();
    }

	@Override
    public IASWizardPageController<?> getASWizardPageController() {
	    return null;
    }

	@Override
    public IWizardContainer getWizardContainer() {
	    return getContainer();
    }

	private class Handler extends ControlAdapter {

		@Override
        public void controlResized(ControlEvent e) {
			if (e.getSource() instanceof Composite) {
				Composite container = (Composite) e.getSource();
				GridLayout layout = (GridLayout) container.getLayout();
				Rectangle bounds = container.getBounds();
				GridData layoutData = (GridData) messageLabel.getLayoutData();
				layoutData.widthHint = computeMessageLabelWidth(bounds, layout);
				container.layout();
			}
        }

	}

}
