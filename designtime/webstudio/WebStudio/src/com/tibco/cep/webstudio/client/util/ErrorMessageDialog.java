package com.tibco.cep.webstudio.client.util;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * 
 * @author sasahoo
 *
 */
public class ErrorMessageDialog extends Dialog implements ClickHandler {
	
	GlobalImages globalImgs = GWT.create(GlobalImages.class);
	
	public static ErrorMessageDialog showException(Throwable e) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(e.getMessage(),  Arrays.toString(e.getStackTrace()) , true, true, null);
		dialog.draw();
		return dialog;
	}
	
	public static ErrorMessageDialog showError(String message) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(message, null, false, false, null);
		dialog.draw();
		return dialog;
	}

	public static ErrorMessageDialog showError(String message, BooleanCallback callback) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(message, null, false, false, callback);
		dialog.draw();
		return dialog;
	}
	
	public static ErrorMessageDialog showError(String message, String details) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(message, details, false, true, null);
		dialog.draw();
		return dialog;
	}


	public static ErrorMessageDialog showError(String message, String details,  BooleanCallback callback) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(message, details, false, true, callback);
		dialog.draw();
		return dialog;
	}

	public static ErrorMessageDialog showError(String message, String details, boolean showDetails) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(message, details, false, showDetails, null);
		dialog.draw();
		return dialog;
	}

	public static ErrorMessageDialog showError(String message, String details, boolean exception, boolean showDetails) {
		ErrorMessageDialog dialog = new ErrorMessageDialog(message, details, exception, showDetails, null);
		dialog.draw();
		return dialog;
	}
	
	private ErrorMessageDialog(String message, String details, boolean exception, boolean showDetails, BooleanCallback callback) {
		
		this.exception = exception;
		this.message = message;
		this.details = details;
		this.callback = callback;
		
		setAutoSize(true); 
		setShowToolbar(false); 
		setCanDragReposition(true);
		setTitle(messages.wsErrorDialogTitle()); 
		setShowModalMask(true);
		setIsModal(false);
		setShowFooter(false);
		setWidth(300);
		setHeight(100);

		setAlign(VerticalAlignment.CENTER);

		label = new Label();   
		label.setHeight(30);   
		label.setPadding(10);
		label.setWidth100();
		label.setAlign(Alignment.CENTER);   
		label.setValign(VerticalAlignment.CENTER);   
		label.setWrap(true);   
		
		Image img = new Image(globalImgs.error());
		
		label.setIcon(img.getUrl());   
		label.setShowEdges(true);   

		if (showDetails) {
			detailsbutton = new Button(messages.wsErrorDialogDetailsOn());
			detailsbutton.setAlign(Alignment.CENTER);
			detailsbutton.setValign(VerticalAlignment.CENTER);  
			detailsbutton.setHeight(20);
			detailsbutton.addClickHandler(this);
			detailsbutton.setWidth(55);
			if (exception) {
				label.setContents(messages.wsErrorDialogDescription(message));  
			} else {
				label.setContents(messages.wsErrorDialogWithDetailsDescription(message));  
			}
			form = new DynamicForm();
			form.setWidth(290);
			form.setHeight(180);

			form.setShowEdges(true);
			form.setPadding(5);
			form.setCanDragResize(false);
			form.setResizeFrom("R", "L");

			TextAreaItem textAreaItem = new TextAreaItem();
			textAreaItem.setShowTitle(false);

			textAreaItem.setValue(details);

			textAreaItem.setLength(5000);
			textAreaItem.setWidth(270);
			textAreaItem.setHeight("*");

			textAreaItem.setCanEdit(false);
			form.setFields(textAreaItem);
			form.setVisible(false);

			Label label_1 = new Label();   
			label_1.setHeight(2);   

			Label label_2 = new Label();   
			label_2.setHeight(2);   

			Label label_3 = new Label();   
			label_3.setHeight(5);   

			addItem(label);
			addItem(label_1);
			addItem(detailsbutton);
			addItem(label_2);
			addItem(form);
			addItem(label_3);
		} else {
			addItem(label);
			label.setContents(messages.wsErrorDialogWithNoDetailsDescription(message));
			Label label_3 = new Label();   
			label_3.setHeight(5);  
			addItem(label_3);
		}

		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();

		okbutton = new Button(messages.wsErrorDialogOKButton());
		okbutton.setAlign(Alignment.CENTER);

		Label elabel = new Label();   
		elabel.setWidth(100);
		okbutton.addClickHandler(this);

		layout.setMembers(elabel, okbutton);

		addItem(layout);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == okbutton) {
			if (callback != null) {
				callback.execute(true);
			}
			this.markForDestroy();          
			this.hide();        
		}
		if (event.getSource() == detailsbutton) {
			showFormDetails =  !showFormDetails;
			if (showFormDetails) {
				if (exception) {
					label.setContents(messages.wsErrorDialogWithNoDetailsButtonDescription(message));  
				} else {
					label.setContents(messages.wsErrorDialogWithNoDetailsDescription(message));  
				}
			} else {
				if (exception) {
					label.setContents(messages.wsErrorDialogDescription(message));  
				} else {
					label.setContents(messages.wsErrorDialogWithDetailsDescription(message));  
				}
			}
			if (form.isVisible()) {
				detailsbutton.setTitle(messages.wsErrorDialogDetailsOn());
				form.setVisible(false);
			} else {
				detailsbutton.setTitle(messages.wsErrorDialogDetailsOff());
				form.setVisible(true);
			}
		}
	}
	
	public String stackTraceToString(Throwable e) { 
	    StringBuilder sb = new StringBuilder(); 
	    for (StackTraceElement element : e.getStackTrace()) { 
	        sb.append(element.toString()); 
	        sb.append("\n"); 
	    } 
	    return sb.toString(); 
	} 

	protected GlobalMessages messages = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected Button okbutton;
	protected Button detailsbutton;
	protected DynamicForm form;
	protected String message;
	protected String details;
	protected boolean showFormDetails = false;
	protected BooleanCallback callback;
	protected boolean exception;
	protected Label label;
}
