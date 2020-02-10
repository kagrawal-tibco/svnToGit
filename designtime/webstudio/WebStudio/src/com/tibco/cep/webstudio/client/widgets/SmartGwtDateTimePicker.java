package com.tibco.cep.webstudio.client.widgets;


import java.util.Date;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasValue;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.FocusHandler;
import com.smartgwt.client.widgets.form.fields.events.FocusEvent;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;


/**
 * A smartgwt widget to take date time input from user.
 * @author rkhera
 *
 */
@SuppressWarnings("deprecation")
public class SmartGwtDateTimePicker extends HLayout implements HasValue<Date>, HasEnabled {
	
	private static final DateTimeFormat SIMPLE_DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd");
	private static NumberFormat DOUBLE_DIGIT_NUMBER_FORMAT;

	//private static final NumberFormat DOUBLE_DIGIT_NUMBER_FORMAT_locale = 
	
	
	
	
	private boolean enabled = true;
	
	private TextItem dateTextBox;
	private DateChooser dateChooser;
	private TextItem hoursTextBox;
	private TextItem minsTextBox;
	private TextItem secTextBox;
	private DynamicForm form;
	private Date choosenDate;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public SmartGwtDateTimePicker(Date date) {
		NumberFormat.setForcedLatinDigits(true);
		DOUBLE_DIGIT_NUMBER_FORMAT = NumberFormat.getFormat("00");
		this.addMember(createDatePicker());
		hoursTextBox = createNumberPicker(0, 23, globalMsgBundle.datepicker_hours_tip());
		minsTextBox = createNumberPicker(0, 59, globalMsgBundle.datepicker_minutes_tip());
		secTextBox = createNumberPicker(0, 59, globalMsgBundle.datepicker_seconds_tip());
		hoursTextBox.setWidth(25);
		minsTextBox.setWidth(25);
		secTextBox.setWidth(25);
		form = new DynamicForm();
		form.setNumCols(8);
		form.setFields(dateTextBox, hoursTextBox, minsTextBox, secTextBox);
		this.addMember(form);
		/*this.getElement().getStyle().setDisplay(Display.INLINE);
		this.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);*/
		this.setValue(date);
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
		Date date;
		if(choosenDate !=null){
			 date = choosenDate;
		}
		else{
		    date = dateChooser.getData();
		}
		
		if(date != null) {
			date.setHours(Integer.parseInt(hoursTextBox.getValueAsString()));
			date.setMinutes(Integer.parseInt(minsTextBox.getValueAsString()));
			date.setSeconds(Integer.parseInt(secTextBox.getValueAsString()));
		}
		return date;
	}

	@Override
	public void setValue(Date date) {
		if (date != null) {
			dateTextBox.setValue(SIMPLE_DATE_FORMAT.format(date));
			dateChooser.setData(date);
			/*hoursTextBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(date.getHours()));
			minsTextBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(date.getMinutes()));
			secTextBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(date.getSeconds()))*/;
		}
		else {
			dateTextBox.setValue(date == null ? globalMsgBundle.datepicker_empty_label() : SIMPLE_DATE_FORMAT.format(date));
		}
	}

	@Override
	public void setValue(Date value, boolean fireEvents) {
		if (fireEvents && value != null) {
			ValueChangeEvent.fire(this, value);
		}
	}
	
	public void destroyDateChooser(){
		dateChooser.destroy();
	}
	
	private Canvas createDatePicker() {
		
		dateTextBox = new TextItem("");
		dateTextBox.setDisabled(false);
		dateTextBox.setCanEdit(false);
		dateTextBox.setWidth("90px");
		dateTextBox.setLeft(5);
		
		dateChooser = new DateChooser();
		dateChooser.setCloseOnEscapeKeypress(true);
		dateChooser.setCanHover(false);
		dateChooser.setShowTodayButton(false);
		dateChooser.setVisible(false);
		dateChooser.setStartYear(1900);
		dateChooser.setEndYear(2100);
		dateChooser.setStyleName("smartGwtDateChooser");
		
		if(LocaleInfo.getCurrentLocale().isRTL()){
			dateChooser.setBaseWeekendStyle("dateChooserWeekendStyle");
			dateChooser.setFirstDayOfWeek(6);
		}
		
		// On datePicker value change show the new date in textBox and fire ValueChanged event.
		dateChooser.addDataChangedHandler(new DataChangedHandler() {
			@Override
			public void onDataChanged(DataChangedEvent event) {
				// TODO Auto-generated method stub
				choosenDate = dateChooser.getData();
				dateTextBox.setValue(SIMPLE_DATE_FORMAT.format(choosenDate));
				dateChooser.hide();
				dateTimePickerValueChanged();
			}
		});
		
		// Show/Hide datePicker onClick on textBox. (Optional since MouseOver/Out handlers are added to the panel)	
		dateTextBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(dateChooser.isVisible()){
					dateChooser.setVisible(false);
				}
				else {
					dateChooser.setVisible(true);
				}
			}
		});
		
		return dateChooser;
	}
	
	private TextItem createNumberPicker(final int minValue, final int maxValue, String title) {
		final TextItem textBox = new TextItem();
	
		textBox.addChangedHandler(new ChangedHandler(){
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				int val = 0;
				try {
		
					val = Integer.parseInt(textBox.getValueAsString());
					if (val < minValue || val > maxValue) {
						val = minValue;
					}
				} catch(Exception e) {
					val = minValue;
				}
				textBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(val));
				dateTimePickerValueChanged();
			}
			
		});
		textBox.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				textBox.selectValue();
			}
		});
	
		textBox.setTitle("");
		textBox.setValue(DOUBLE_DIGIT_NUMBER_FORMAT.format(minValue));
		textBox.setWidth("25px");
		textBox.setVAlign(VerticalAlignment.CENTER);
		textBox.setTooltip(title);
		return textBox;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		dateTextBox.setDisabled(false);
		dateChooser.setDisabled(false);
		dateChooser.setVisible(enabled);
		hoursTextBox.setDisabled(false);
		minsTextBox.setDisabled(false);
		secTextBox.setDisabled(false);
		dateTextBox.setCanEdit(true);
		hoursTextBox.setCanEdit(true);
		minsTextBox.setCanEdit(true);
		secTextBox.setCanEdit(true);
		this.enabled = enabled;
	}
	
	@Override
	public void setStyleName(String style) {

		dateTextBox.setCellStyle(style);
		dateChooser.setStyleName(style);
		hoursTextBox.setCellStyle(style);
		minsTextBox.setCellStyle(style);
		secTextBox.setCellStyle(style);
	}
}
