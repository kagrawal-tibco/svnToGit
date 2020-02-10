package com.tibco.cep.webstudio.client.widgets;

import java.util.Date;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.panels.CustomVerticalPanel;

/**
 * A widget to take date time input from user.
 * @author moshaikh
 *
 */
@SuppressWarnings("deprecation")
public class DateTimePicker extends HorizontalPanel implements HasValue<Date>, HasEnabled {
	
	private static final DateTimeFormat SIMPLE_DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd");
	private static NumberFormat DOUBLE_DIGIT_NUMBER_FORMAT = NumberFormat.getFormat("00");
	private static final String TEXT_COLOR = "BLUE";
	
	private boolean enabled = true;
	
	private TextBox dateTextBox;
	private DateChooser dateChooser;
	private TextBox hoursTextBox;
	private TextBox minsTextBox;
	private TextBox secTextBox;
	
	private static CustomPopupPanel datePickerPopupPanel = new CustomPopupPanel();
	private static boolean mouseOverPopupPanel = false;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	static {
		datePickerPopupPanel.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				mouseOverPopupPanel = false;
				datePickerPopupPanel.clear();
				datePickerPopupPanel.hide();
			}
		});
		datePickerPopupPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				mouseOverPopupPanel = true;	
			}
		});
		datePickerPopupPanel.getElement().getStyle().setZIndex(999999);
		datePickerPopupPanel.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
		RootPanel.get().add(datePickerPopupPanel);
	}
	
	public DateTimePicker(Date date) {
		NumberFormat.setForcedLatinDigits(true);
		DOUBLE_DIGIT_NUMBER_FORMAT = NumberFormat.getFormat("00");
		this.add(createDatePicker());
		hoursTextBox = createNumberPicker(0, 23, globalMsgBundle.datepicker_hours_tip());
		this.add(hoursTextBox);
		this.add(createLabel(" : "));
		minsTextBox = createNumberPicker(0, 59, globalMsgBundle.datepicker_minutes_tip());
		this.add(minsTextBox);
		this.add(createLabel(" : "));
		secTextBox = createNumberPicker(0, 59, globalMsgBundle.datepicker_seconds_tip());
		this.add(secTextBox);
		
		this.getElement().getStyle().setDisplay(Display.INLINE);
		this.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		
		this.setValue(date);
	}
	
	/**
	 * Creates a inline label with the specified text as content.
	 * @param text
	 * @return
	 */
	private InlineLabel createLabel(String text) {
		InlineLabel label = new InlineLabel(text);
		label.getElement().getStyle().setColor("GREY");
		label.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		return label;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}
	
	public HandlerRegistration addDataChangedHandler(DataChangedHandler handler){
		return addHandler(handler, DataChangedEvent.getType());
		
	}
	
	private void dateTimePickerValueChanged() {
		setValue(getValue(), true);
	}

	@Override
	public Date getValue() {
		Date date = dateChooser.getData();
		if(date != null) {
			date.setHours(Integer.parseInt(hoursTextBox.getValue()));
			date.setMinutes(Integer.parseInt(minsTextBox.getValue()));
			date.setSeconds(Integer.parseInt(secTextBox.getValue()));
		}
		return date;
	}

	@Override
	public void setValue(Date date) {
		if (date != null) {
			dateTextBox.setText(SIMPLE_DATE_FORMAT.format(date));
			dateChooser.setData(date);
			hoursTextBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(date.getHours()));
			minsTextBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(date.getMinutes()));
			secTextBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(date.getSeconds()));
		}
		else {
			dateTextBox.setText(date == null ? globalMsgBundle.datepicker_empty_label() : SIMPLE_DATE_FORMAT.format(date));
		}
	}

	@Override
	public void setValue(Date value, boolean fireEvents) {
		if (fireEvents && value != null) {
			ValueChangeEvent.fire(this, value);
		}
	}
	
	private Widget createDatePicker() {
		dateTextBox = new TextBox();
		dateTextBox.setReadOnly(true);
		dateTextBox.setWidth("90px");
		dateTextBox.getElement().getStyle().setMarginLeft(5, Unit.PX);
		dateTextBox.getElement().getStyle().setMarginRight(5, Unit.PX);
		dateTextBox.getElement().getStyle().setColor(TEXT_COLOR);
		
		dateChooser = new DateChooser();
		dateChooser.setCloseOnEscapeKeypress(true);
		dateChooser.setShowTodayButton(false);
		dateChooser.setStartYear(1900);
		dateChooser.setEndYear(2100);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			dateChooser.setBaseWeekendStyle("dateChooserWeekendStyle");
			dateChooser.setFirstDayOfWeek(6);
		}
		// On datePicker value change show the new date in textBox and fire ValueChanged event.
		dateChooser.addDataChangedHandler(new DataChangedHandler() {
			@Override
			public void onDataChanged(DataChangedEvent event) {
				// TODO Auto-generated method stub
				Date newDate = dateChooser.getData();
				dateTextBox.setText(SIMPLE_DATE_FORMAT.format(newDate));
				dateChooser.setVisible(false);
				dateTimePickerValueChanged();
			}
		});
		
		// Show/Hide datePicker onClick on textBox. (Optional since MouseOver/Out handlers are added to the panel)
		dateTextBox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(dateChooser.isVisible()){
					dateChooser.setVisible(false);
				}
				else {
					dateChooser.setVisible(enabled);
				}
			}
		});
		
		CustomVerticalPanel panel = new CustomVerticalPanel();
		panel.add(dateTextBox);
		// Make panel style inline to avoid being created on newLine.
		panel.getElement().getStyle().setDisplay(Display.INLINE);
		panel.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		
		// MouseOver & MouseOut handlers to show/hide datePicker. 
		panel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (!enabled) {
					return;
				}
				datePickerPopupPanel.hide();
				datePickerPopupPanel.clear();
				datePickerPopupPanel.setPopupPosition(dateTextBox.getAbsoluteLeft() - 3, dateTextBox.getAbsoluteTop() + 22);
				datePickerPopupPanel.add(dateChooser);
				dateChooser.setVisible(true);
				datePickerPopupPanel.show();
			}
		});
		panel.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				if (!enabled) {
					return;
				}
				Timer timer = new Timer() {
		            @Override
		            public void run() {
		            	if (!mouseOverPopupPanel) {
		            		datePickerPopupPanel.hide();
		            		datePickerPopupPanel.clear();
		            		mouseOverPopupPanel = false;
		            	}
		            }
		        };
		        timer.schedule(200);
			}
		});
		return panel.asWidget();
	}
	
	private TextBox createNumberPicker(final int minValue, final int maxValue, String title) {
		final TextBox textBox = new TextBox();
		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				int val = 0;
				try {
					val = Integer.parseInt(event.getValue());
					if (val < minValue || val > maxValue) {
						val = minValue;
					}
				} catch(Exception e) {
					val = minValue;
				}
				textBox.setText(DOUBLE_DIGIT_NUMBER_FORMAT.format(val));
				dateTimePickerValueChanged();
			}
		});
		textBox.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				textBox.selectAll();
			}
		});
		textBox.setTitle(title);
		textBox.setMaxLength(2);
		textBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(minValue));
		textBox.setWidth("25px");
		textBox.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		textBox.getElement().getStyle().setColor(TEXT_COLOR);
		return textBox;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		dateTextBox.setEnabled(enabled);
		dateChooser.setVisible(enabled);
		hoursTextBox.setEnabled(enabled);
		minsTextBox.setEnabled(enabled);
		secTextBox.setEnabled(enabled);
		this.enabled = enabled;
	}
	
	@Override
	public void setStyleName(String style) {
		dateTextBox.setStyleName(style);
		dateChooser.setStyleName(style);
		hoursTextBox.setStyleName(style);
		minsTextBox.setStyleName(style);
		secTextBox.setStyleName(style);
	}
	
	/**
	 * PopupPanel customised to enable adding MouseOver and MouseOut Handlers.
	 * @author moshaikh
	 */
	private static class CustomPopupPanel extends PopupPanel implements
			HasMouseOverHandlers, HasMouseOutHandlers {
		
		@Override
		public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
			return addDomHandler(handler, MouseOutEvent.getType());
		}
		
		@Override
		public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
			return addDomHandler(handler, MouseOverEvent.getType());
		}
	}
}
