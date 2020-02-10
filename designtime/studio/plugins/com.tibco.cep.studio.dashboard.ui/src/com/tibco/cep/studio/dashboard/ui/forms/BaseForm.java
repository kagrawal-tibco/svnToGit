package com.tibco.cep.studio.dashboard.ui.forms;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

public abstract class BaseForm implements ExceptionHandler {

	protected String title;

	protected FormToolkit formToolKit;

	private Composite parent;

	protected boolean showGroup;

	protected Composite formComposite;

	protected List<Control> controls;

	protected List<BaseForm> forms;

	protected LocalElement localElement;

	protected PropertyChangeSupport propertyChangeSupport;

	private boolean listenersDisabled;

	private ExceptionHandler exceptionHandler;

	public BaseForm(String title, FormToolkit formToolKit, Composite parent, boolean showGroup) {
		exceptionHandler = new DefaultExceptionHandler();
		propertyChangeSupport = new PropertyChangeSupport(this);
		controls = new LinkedList<Control>();
		forms = new LinkedList<BaseForm>();
		this.title = title;
		this.formToolKit = formToolKit;
		this.parent = parent;
		this.showGroup = showGroup;
		if (showGroup == true) {
			formComposite = createGroup(this.parent, title, SWT.NONE);
		} else {
			formComposite = createComposite(this.parent, SWT.NONE);
		}
		listenersDisabled = true;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		for (BaseForm form : forms) {
			form.setExceptionHandler(exceptionHandler);
		}
	}

	public final String getTitle(){
		return title;
	}

	public abstract void init();

	public final Control getControl(){
		return formComposite;
	}

	public void disableAll() {
		for (Control control : controls) {
			control.setEnabled(false);
		}
		for (BaseForm form : forms) {
			form.disableAll();
		}
	}

	public void enableAll() {
		for (Control control : controls) {
			control.setEnabled(true);
		}
		for (BaseForm form : forms) {
			form.enableAll();
		}
	}


	protected Composite createComposite(Composite parent, int style) {
		if (formToolKit != null) {
			return formToolKit.createComposite(parent, style);
		}
		return new Composite(parent, style);
	}

	protected Group createGroup(Composite parent, String title, int style) {
		Group group = new Group(parent, SWT.NONE);
		if (formToolKit != null) {
			formToolKit.adapt(group);
		}
		group.setText(title);
		return group;
	}

	protected Label createLabel(Composite parent, String text, int style) {
		Label label = null;
		if (formToolKit != null) {
			label = formToolKit.createLabel(parent, text, style);
		} else {
			label = new Label(parent, style);
			label.setText(text);
		}
		return label;
	}

	protected Button createButton(Composite parent, String text, int style) {
		Button button = null;
		if (formToolKit != null) {
			button = formToolKit.createButton(parent, text, style);
		} else {
			button = new Button(parent, style);
			button.setText(text);
		}
		controls.add(button);
		return button;
	}

	protected Combo createCombo(Composite parent, int style) {
		Combo combo = new Combo(parent, style);
		if (formToolKit != null) {
			formToolKit.adapt(combo, true, true);
		}
		controls.add(combo);
		return combo;
	}

	protected org.eclipse.swt.widgets.List createList(Composite parent, int style) {
		org.eclipse.swt.widgets.List list = new org.eclipse.swt.widgets.List(parent, style);
		if (formToolKit != null) {
			formToolKit.adapt(list, true, true);
		}
		controls.add(list);
		return list;
	}

	protected Scale createScale(Composite parent, int style) {
		Scale scale = new Scale(parent, style);
		if (formToolKit != null) {
			formToolKit.adapt(scale, true, true);
		}
		controls.add(scale);
		return scale;
	}

	protected Spinner createSpinner(Composite parent, int style) {
		Spinner spinner = new Spinner(parent, style);
		if (formToolKit != null) {
			formToolKit.adapt(spinner, true, true);
		}
		controls.add(spinner);
		return spinner;
	}

	protected Table createTable(Composite parent, int style) {
		Table table = null;
		if (formToolKit != null) {
			table = formToolKit.createTable(parent, style);
		} else {
			table = new Table(parent, style);
		}
		controls.add(table);
		return table;
	}

	protected Text createText(Composite parent, final String value, int style) {
		final Text text;
		if (formToolKit != null) {
			text = formToolKit.createText(parent, "", style);
		} else {
			text = new Text(parent, style|SWT.BORDER);
		}
		controls.add(text);
		if (StringUtil.isEmpty(value) == false) {
			Display.getCurrent().asyncExec(new Runnable() {

				@Override
				public void run() {
					if (text != null && text.isDisposed() == false) {
						text.setText(value);
					}

				}

			});
		}
		return text;
	}

	protected Tree createTree(Composite parent, int style) {
		Tree tree = null;
		if (formToolKit != null) {
			tree = formToolKit.createTree(parent, style);
		} else {
			tree = new Tree(parent, style);
		}
		controls.add(tree);
		return tree;
	}

	protected void addControl(Control control) {
		controls.add(control);
	}

	protected void addForm(BaseForm form) {
		forms.add(form);
	}

	public void setInput(LocalElement localElement) throws Exception{
		LocalElement oldLocalElement = this.localElement;
		if (this.localElement != null){
			disableListeners();
		}
		this.localElement = localElement;
		inputChanged(oldLocalElement,localElement);
		refreshEnumerations();
		refreshSelections();
		enableListeners();
		setChildrenFormInput(localElement);
	}

	protected void setChildrenFormInput(LocalElement localElement) throws Exception {
		for (BaseForm form : forms) {
			form.setInput(localElement);
		}
	}

	public final LocalElement getInput(){
		return localElement;
	}

	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {

	}

	public final void disableListeners(){
		if (listenersDisabled == false){
			doDisableListeners();
			for (BaseForm form : forms) {
				form.disableListeners();
			}
			listenersDisabled = true;
		}
	}

	protected abstract void doDisableListeners();

	public abstract void refreshEnumerations();

	public abstract void refreshSelections();

	public final void enableListeners(){
		if (listenersDisabled == true){
			doEnableListeners();
			for (BaseForm form : forms) {
				form.enableListeners();
			}
			listenersDisabled = false;
		}
	}

	protected abstract void doEnableListeners();

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public void log(IStatus status) {
		exceptionHandler.log(status);
	}

	/**
	 * @param status
	 */
	public void logAndAlert(IStatus status) {
		logAndAlert(title,status);
	}

	@Override
	public void logAndAlert(String title, IStatus status) {
		exceptionHandler.logAndAlert(title, status);
	}

	@Override
	public String getPluginId() {
		return exceptionHandler.getPluginId();
	}

	@Override
	public IStatus createStatus(int severity, String message, Throwable exception) {
		return exceptionHandler.createStatus(severity, message, exception);
	}

	public void dispose() {
		doDisableListeners();
	}

	@Override
	public String toString() {
		return getClass().getName()+"["+title+"]";
	}

	protected List<BaseForm> getForms(){
		return Collections.unmodifiableList(forms);
	}
}
