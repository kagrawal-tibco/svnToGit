package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.ControllablePage;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

public abstract class ChartEditorBasePage extends AbstractEntityEditorPage implements ControllablePage {

	private boolean disable;

	private List<BaseForm> forms;

	private ExceptionHandler exceptionHandler;

//	private ChartPreviewForm chartPreviewForm;

	public ChartEditorBasePage(FormEditor editor, LocalElement localElement, String id, String title) {
		super(editor, localElement, id, title);
		disable = false;
		forms = new LinkedList<BaseForm>();
	}

	@Override
	protected void createFormContent(IManagedForm mform) {
		super.createFormContent(mform);
		mform.getForm().setText("Chart");
	}

	@Override
	protected final void createControl(IManagedForm mform, Composite parent) throws Exception {
		FormToolkit toolkit = mform.getToolkit();
		//create form + chart preview holder
		Composite pageComposite = toolkit.createComposite(parent);
		pageComposite.setLayout(new GridLayout(2,false));
		//add form body client to hold main form(s)
		formBodyClient = toolkit.createComposite(pageComposite);
		//let sub class layout the form holder and add its forms
		createForm(toolkit, formBodyClient);
		//set layout data for formBodyClient
		formBodyClient.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
//		//add chart preview form
//		chartPreviewForm = new ChartPreviewForm(toolkit,pageComposite);
//		chartPreviewForm.init();
//		chartPreviewForm.getControl().setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,true));
//		chartPreviewForm.setExceptionHandler(exceptionHandler);
		//paint the borders for form look n feel
		toolkit.paintBordersFor(pageComposite);
	}

	protected abstract void createForm(FormToolkit toolkit, Composite parent) throws Exception;

	protected void registerForm(BaseForm form){
		forms.add(form);
		if (exceptionHandler != null){
			form.setExceptionHandler(exceptionHandler);
		}
	}

	protected void deregisterForm(BaseForm form){
		forms.remove(form);
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		for (BaseForm form : forms) {
			form.setInput(localElement);
			if (disable == true){
				form.disableAll();
			}
		}
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		if (forms != null){
			for (BaseForm form : forms) {
				form.setExceptionHandler(exceptionHandler);
			}
		}
	}

	@Override
	public void disable() {
		disable = true;
		for (BaseForm form : forms) {
			form.disableAll();
		}
//		if (chartPreviewForm != null) {
//			chartPreviewForm.disableAll();
//		}
	}

	@Override
	public void enable() {
		disable = false;
		for (BaseForm form : forms) {
			form.enableAll();
		}
//		chartPreviewForm.enableAll();
	}

	@Override
	public void refresh() throws Exception {
		//we are calling do populate since populateControl resets status
		doPopulateControl(getLocalElement());
	}

	@Override
	public void refreshPreview(LocalElement localElement) {
//		try {
//			chartPreviewForm.setInput(localElement);
//		} catch (Exception e) {
//			String message = "could not refresh preview on page['"+getTitle()+"'] for "+getLocalElement();
//			exceptionHandler.log(new Status(IStatus.WARNING,exceptionHandler.getPluginId(),message,e));
//		}
	}
}