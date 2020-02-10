package com.tibco.cep.studio.wizard.as.internal.wizard.pages;

import static com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel._PROP_NAME_ERRORS;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel._PROP_NAME_WIZARD_PAGE_DESCRIPTION;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel._PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel._PROP_NAME_WIZARD_PAGE_TITLE;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel._PROP_NAME_WIZARD_PAGE_TITLE_IMAGE;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;

import java.util.Arrays;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;
import com.tibco.cep.studio.wizard.as.internal.beans.databinding.converters.ErrorsToStringConverter;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.IASWizardPageView;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public class ASGenericWizardPage<C extends IASWizardPageController<M>, M extends IASWizardPageModel> extends WizardPage implements
        IASWizardPage<C, M> {

	// View
	protected IASWizardPageView<C, M>  view;

	// Wizard
	private IDataBindingProvider     additionalDataBindingProvider;
	private IWizardPageHandler<C, M> wizardPageHandler;

	// Bindings
	private DataBindingContext       bc;

	/**
	 * 
	 * @param view
	 * @param additionalDataBindingProvider
	 *            can not be null, please use EmptyDataBindingProvider.
	 * @param wizardPageHandler
	 *            can not be null, please use EmptyWizardPageHandler if you need
	 *            an 'Empty' one.
	 */
	public ASGenericWizardPage(IASWizardPageView<C, M> view, IDataBindingProvider additionalDataBindingProvider,
	        IWizardPageHandler<C, M> wizardPageHandler) {
		super(view.getController().getModel().getWizardPageName());
		Assert.isNotNull(view, Messages.getString("ASGenericWizardPage.non_null_validation_view")); //$NON-NLS-1$
		this.view = view;
		Assert.isNotNull(additionalDataBindingProvider, Messages.getString("ASGenericWizardPage.non_null_validation_databinding")); //$NON-NLS-1$
		this.additionalDataBindingProvider = additionalDataBindingProvider;
		Assert.isNotNull(wizardPageHandler, Messages.getString("ASGenericWizardPage.non_null_validation_page_handler")); //$NON-NLS-1$
		this.wizardPageHandler = wizardPageHandler;
		initialize();
	}

	private void initialize() {
	    view.getController().getModel().setASWizardPage(this);
    }

	@Override
	public void createControl(Composite parent) {
		initUI(parent);
		attachBindings();
	}

	@Override
	public IWizardPageHandler<C, M> getPageHandler() {
		return wizardPageHandler;
	}

	@Override
	public C getASWizardPageController() {
		return view.getController();
	}

	@Override
	public void dispose() {
		unattachBindings();
		super.dispose();
	}

	protected void initUI(Composite parent) {
		Object[] args = Arrays.asList(parent, view.getController()).toArray();
		Control control = (Control) view.createComponent(args);
		setControl(control);
	}

	private void attachBindings() {
		bc = new DataBindingContext();
		IASWizardPageModel model = view.getController().getModel();

		// Property title
		bc.bindValue(PojoObservables.observeValue(this, "title"), //$NON-NLS-1$
		        BeansObservables.observeValue(model, _PROP_NAME_WIZARD_PAGE_TITLE),
		        // Model -> View one-way update strategy
		        new UpdateValueStrategy(POLICY_NEVER), null);

		// Property description
		bc.bindValue(PojoObservables.observeValue(this, "description"), //$NON-NLS-1$
		        BeansObservables.observeValue(model, _PROP_NAME_WIZARD_PAGE_DESCRIPTION),
		        // Model -> View one-way update strategy
		        new UpdateValueStrategy(POLICY_NEVER), null);

		// Property imageDescriptor
		bc.bindValue(PojoObservables.observeValue(this, "imageDescriptor"), //$NON-NLS-1$
		        BeansObservables.observeValue(model, _PROP_NAME_WIZARD_PAGE_TITLE_IMAGE),
		        // Model -> View one-way update strategy
		        new UpdateValueStrategy(POLICY_NEVER), null);


		if (model.isWizardPageErrorMessageBindingEnabled()) {
    		// Model Error List to Model Error Message
    		bc.bindValue(BeansObservables.observeValue(model, _PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE),
    				BeansObservables.observeValue(model, _PROP_NAME_ERRORS),
    		        new UpdateValueStrategy(POLICY_NEVER),
    		        new UpdateValueStrategy().setConverter(new ErrorsToStringConverter()));

    		// Property errorMessage
    		bc.bindValue(PojoObservables.observeValue(this, "errorMessage"), //$NON-NLS-1$
    		        BeansObservables.observeValue(model, _PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE),
    		        // Model -> View one-way update strategy
    		        new UpdateValueStrategy(POLICY_NEVER), null);
		}

		additionalDataBindingProvider.bind(bc, this);
	}

	private void unattachBindings() {
		bc.dispose();
		bc = null;
	}

	@Override
    public IWizardContainer getWizardContainer() {
	    return getContainer();
    }

}
