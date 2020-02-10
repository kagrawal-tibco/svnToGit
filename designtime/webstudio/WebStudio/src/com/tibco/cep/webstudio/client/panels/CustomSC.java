package com.tibco.cep.webstudio.client.panels;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * This is a utility class which allows customising the button texts of sgwt dialogs such as SC.confirm, SC.askForValue etc. 
 * @author moshaikh
 */
public class CustomSC {
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Show the confirm dialog with ok and cancel button values picked from Global message bundle.
	 * @param title
	 * @param message
	 * @param callback
	 */
	public static void confirm(String title, String message, BooleanCallback callback) {
		confirm(title, message, globalMsgBundle.button_ok(), globalMsgBundle.button_cancel(), callback);
	}
	
	/**
	 * Show the confirm dialog with ok and cancel button values picked from Global message bundle.
	 * @param title
	 * @param message
	 * @param callback
	 */
	public static void askforValue(String title, String message, ValueCallback callback) {
		askforValue(title, message, globalMsgBundle.button_ok(), globalMsgBundle.button_cancel(), callback);
	}
	
	/**
	 * Show the confirm dialog with ok and cancel button values picked from Global message bundle.
	 * @param message
	 * @param callback
	 */
	public static void askforValue(String message, ValueCallback callback) {
		askforValue(globalMsgBundle.ask_value_dialog_title(), message, globalMsgBundle.button_ok(), globalMsgBundle.button_cancel(), callback);
	}
	
	/**
	 * Show the say dialog with ok button values picked from Global message bundle.
	 * @param title
	 * @param message
	 * @param callback
	 */
	public static void say(String title, String message) {
		say(title, message, globalMsgBundle.button_ok(), null);
	}
	
	/**
	 * Show the say dialog with ok button values picked from Global message bundle.
	 * @param message
	 */
	public static void say(String message) {
		say(globalMsgBundle.text_note(), message, globalMsgBundle.button_ok(), null);
	}
	
	/**
	 * Show the say dialog with ok button values picked from Global message bundle.
	 * @param message
	 * @param callback
	 */
	public static void say(String message, BooleanCallback callback) {
		say(globalMsgBundle.text_note(), message, globalMsgBundle.button_ok(), callback);
	}
	
	/**
	 * Shows the warn dialog with ok button value picked from the Global message bundle.
	 * @param message
	 * @param callback
	 */
	public static void warn(String message, BooleanCallback callback) {
		warn(globalMsgBundle.text_warning(), message, globalMsgBundle.button_ok(), callback);
	}
	
	/**
	 * Shows the warn dialog with ok button value picked from the Global message bundle.
	 * @param message
	 */
	public static void warn(String message) {
		warn(globalMsgBundle.text_warning(), message, globalMsgBundle.button_ok(), null);
	}
	
	private static void confirm(String title, String message,
			String okButtonText, String cancelButtonText, BooleanCallback callback) {
		final Dialog dialogProperties = new Dialog();
		dialogProperties.setWidth(360);
		
		if(LocaleInfo.getCurrentLocale().isRTL()){
			dialogProperties.setStyleName("customSC");
		}
		final Button okButton = new Button(okButtonText);
		okButton.setBaseStyle("buttonRounded");
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogProperties.okClick();
			}
		});
		
		final Button cancelButton = new Button(cancelButtonText);
		cancelButton.setBaseStyle("buttonRounded");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogProperties.cancelClick();
			}
		});
		
		dialogProperties.setButtons(okButton, cancelButton);
		SC.confirm(title, message, callback, dialogProperties);
	}
	
	private static void askforValue(String title, String message, String okButtonText, String cancelButtonText, ValueCallback callback) {
		final Dialog dialogProperties = new Dialog();
		dialogProperties.setWidth(360);
		
		if(LocaleInfo.getCurrentLocale().isRTL()){
			dialogProperties.setStyleName("customSC");
		}
		final Button okButton = new Button(okButtonText);
		okButton.setBaseStyle("buttonRounded");
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogProperties.okClick();
			}
		});
		
		final Button cancelButton = new Button(cancelButtonText);
		cancelButton.setBaseStyle("buttonRounded");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogProperties.cancelClick();
			}
		});
		
		dialogProperties.setButtons(okButton, cancelButton);
		SC.askforValue(title, message, null, callback, dialogProperties);
	}
	
	private static void say(String title, String message, String okButtonText, BooleanCallback callback) {
		final Dialog dialogProperties = new Dialog();
		dialogProperties.setWidth(360);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			dialogProperties.setStyleName("customSC");
		}
		
		final Button okButton = new Button(okButtonText);
		okButton.setBaseStyle("buttonRounded");
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogProperties.okClick();
			}
		});
		
		if (callback == null) {
			callback = new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
				}
			};
		}
		
		dialogProperties.setButtons(okButton);
		dialogProperties.setIcon("[SKIN]say.png");
		
		SC.confirm(title, message, callback, dialogProperties);
	}
	
	private static void warn(String title, String message, String okButtonText, BooleanCallback callback) {
		final Dialog dialogProperties = new Dialog();
		dialogProperties.setWidth(360);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			dialogProperties.setStyleName("customSC");
		}
		final Button okButton = new Button(okButtonText);
		okButton.setBaseStyle("buttonRounded");
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogProperties.okClick();
			}
		});
		
		if (callback == null) {
			callback = new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
				}
			};
		}
		
		dialogProperties.setButtons(okButton);
		SC.warn(title, message, callback, dialogProperties);
	}
}
