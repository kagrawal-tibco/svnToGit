package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.i18n.SmartGwtMessages;

/**
 * 
 * @author moshaikh
 */
public class CustomizedSmartgwtMessages implements SmartGwtMessages {

	private static SmartGwtMessages originalSgwtMessages = GWT.create(SmartGwtMessages.class);
	private Map<String, String> customizedSgwtMessages;

	public CustomizedSmartgwtMessages(Map<String, String> customizedSgwtMessages) {
		this.customizedSgwtMessages = customizedSgwtMessages;
	}

	/**
	 * Returns value(if available) for the specified key from the messages
	 * passed. Return null if custom properties file or the key is not
	 * available.
	 * 
	 * @param messages
	 * @param key
	 * @param replaceValues
	 * @return
	 */
	public static String getPropertyValue(Map<String, String> messages,
			String key, String... replaceValues) {
		String message = messages.get(key);
		if (message != null && replaceValues != null) {
			for (int i = 0; i < replaceValues.length; i++) {
				message = message.replace("{" + i + "}", replaceValues[i]);
			}
		}
		return message;
	}

	private String getPropertyValue(String key, String... replaceValues) {
		return getPropertyValue(customizedSgwtMessages, key, replaceValues);
	}

	@Override
	public String advancedHiliteEditor_invalidCriteriaPrompt() {
		String val = getPropertyValue("advancedHiliteEditor_invalidCriteriaPrompt");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_invalidCriteriaPrompt();
	}

	@Override
	public String button_title() {
		String val = getPropertyValue("button_title");
		return val != null ? val : originalSgwtMessages.button_title();
	}

	@Override
	public String calendar_addEventButtonHoverText() {
		String val = getPropertyValue("calendar_addEventButtonHoverText");
		return val != null ? val : originalSgwtMessages.calendar_addEventButtonHoverText();
	}

	@Override
	public String calendar_cancelButtonTitle() {
		String val = getPropertyValue("calendar_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.calendar_cancelButtonTitle();
	}

	@Override
	public String calendar_datePickerHoverText() {
		String val = getPropertyValue("calendar_datePickerHoverText");
		return val != null ? val : originalSgwtMessages.calendar_datePickerHoverText();
	}

	@Override
	public String calendar_dayViewTitle() {
		String val = getPropertyValue("calendar_dayViewTitle");
		return val != null ? val : originalSgwtMessages.calendar_dayViewTitle();
	}

	@Override
	public String calendar_detailsButtonTitle() {
		String val = getPropertyValue("calendar_detailsButtonTitle");
		return val != null ? val : originalSgwtMessages.calendar_detailsButtonTitle();
	}

	@Override
	public String calendar_eventNameFieldTitle() {
		String val = getPropertyValue("calendar_eventNameFieldTitle");
		return val != null ? val : originalSgwtMessages.calendar_eventNameFieldTitle();
	}

	@Override
	public String calendar_eventDescriptionFieldTitle() {
		String val = getPropertyValue("calendar_eventDescriptionFieldTitle");
		return val != null ? val : originalSgwtMessages.calendar_eventDescriptionFieldTitle();
	}

	@Override
	public String calendar_eventStartDateFieldTitle() {
		String val = getPropertyValue("calendar_eventStartDateFieldTitle");
		return val != null ? val : originalSgwtMessages.calendar_eventStartDateFieldTitle();
	}

	@Override
	public String calendar_eventEndDateFieldTitle() {
		String val = getPropertyValue("calendar_eventEndDateFieldTitle");
		return val != null ? val : originalSgwtMessages.calendar_eventEndDateFieldTitle();
	}

	@Override
	public String calendar_eventLaneFieldTitle() {
		String val = getPropertyValue("calendar_eventLaneFieldTitle");
		return val != null ? val : originalSgwtMessages.calendar_eventLaneFieldTitle();
	}

	@Override
	public String calendar_invalidDateMessage() {
		String val = getPropertyValue("calendar_invalidDateMessage");
		return val != null ? val : originalSgwtMessages.calendar_invalidDateMessage();
	}

	@Override
	public String calendar_monthViewTitle() {
		String val = getPropertyValue("calendar_monthViewTitle");
		return val != null ? val : originalSgwtMessages.calendar_monthViewTitle();
	}

	@Override
	public String calendar_nextButtonHoverText() {
		String val = getPropertyValue("calendar_nextButtonHoverText");
		return val != null ? val : originalSgwtMessages.calendar_nextButtonHoverText();
	}

	@Override
	public String calendar_previousButtonHoverText() {
		String val = getPropertyValue("calendar_previousButtonHoverText");
		return val != null ? val : originalSgwtMessages.calendar_previousButtonHoverText();
	}

	@Override
	public String calendar_saveButtonTitle() {
		String val = getPropertyValue("calendar_saveButtonTitle");
		return val != null ? val : originalSgwtMessages.calendar_saveButtonTitle();
	}

	@Override
	public String calendar_timelineViewTitle() {
		String val = getPropertyValue("calendar_timelineViewTitle");
		return val != null ? val : originalSgwtMessages.calendar_timelineViewTitle();
	}

	@Override
	public String calendar_weekViewTitle() {
		String val = getPropertyValue("calendar_weekViewTitle");
		return val != null ? val : originalSgwtMessages.calendar_weekViewTitle();
	}

	@Override
	public String calendar_weekPrefix() {
		String val = getPropertyValue("calendar_weekPrefix");
		return val != null ? val : originalSgwtMessages.calendar_weekPrefix();
	}

	@Override
	public String colorItem_pickerIconPrompt() {
		String val = getPropertyValue("colorItem_pickerIconPrompt");
		return val != null ? val : originalSgwtMessages.colorItem_pickerIconPrompt();
	}

	@Override
	public String colorPicker_basicColorLabel() {
		String val = getPropertyValue("colorPicker_basicColorLabel");
		return val != null ? val : originalSgwtMessages.colorPicker_basicColorLabel();
	}

	@Override
	public String colorPicker_blueFieldPrompt() {
		String val = getPropertyValue("colorPicker_blueFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_blueFieldPrompt();
	}

	@Override
	public String colorPicker_blueFieldTitle() {
		String val = getPropertyValue("colorPicker_blueFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_blueFieldTitle();
	}

	@Override
	public String colorPicker_cancelButtonTitle() {
		String val = getPropertyValue("colorPicker_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_cancelButtonTitle();
	}

	@Override
	public String colorPicker_greenFieldPrompt() {
		String val = getPropertyValue("colorPicker_greenFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_greenFieldPrompt();
	}

	@Override
	public String colorPicker_greenFieldTitle() {
		String val = getPropertyValue("colorPicker_greenFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_greenFieldTitle();
	}

	@Override
	public String colorPicker_htmlFieldPrompt() {
		String val = getPropertyValue("colorPicker_htmlFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_htmlFieldPrompt();
	}

	@Override
	public String colorPicker_htmlFieldTitle() {
		String val = getPropertyValue("colorPicker_htmlFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_htmlFieldTitle();
	}

	@Override
	public String colorPicker_hueFieldPrompt() {
		String val = getPropertyValue("colorPicker_hueFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_hueFieldPrompt();
	}

	@Override
	public String colorPicker_hueFieldTitle() {
		String val = getPropertyValue("colorPicker_hueFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_hueFieldTitle();
	}

	@Override
	public String colorPicker_lessButtonTitle() {
		String val = getPropertyValue("colorPicker_lessButtonTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_lessButtonTitle();
	}

	@Override
	public String colorPicker_lumFieldPrompt() {
		String val = getPropertyValue("colorPicker_lumFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_lumFieldPrompt();
	}

	@Override
	public String colorPicker_lumFieldTitle() {
		String val = getPropertyValue("colorPicker_lumFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_lumFieldTitle();
	}

	@Override
	public String colorPicker_moreButtonTitle() {
		String val = getPropertyValue("colorPicker_moreButtonTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_moreButtonTitle();
	}

	@Override
	public String colorPicker_okButtonTitle() {
		String val = getPropertyValue("colorPicker_okButtonTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_okButtonTitle();
	}

	@Override
	public String colorPicker_opacitySliderLabel() {
		String val = getPropertyValue("colorPicker_opacitySliderLabel");
		return val != null ? val : originalSgwtMessages.colorPicker_opacitySliderLabel();
	}

	@Override
	public String colorPicker_redFieldPrompt() {
		String val = getPropertyValue("colorPicker_redFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_redFieldPrompt();
	}

	@Override
	public String colorPicker_redFieldTitle() {
		String val = getPropertyValue("colorPicker_redFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_redFieldTitle();
	}

	@Override
	public String colorPicker_satFieldPrompt() {
		String val = getPropertyValue("colorPicker_satFieldPrompt");
		return val != null ? val : originalSgwtMessages.colorPicker_satFieldPrompt();
	}

	@Override
	public String colorPicker_satFieldTitle() {
		String val = getPropertyValue("colorPicker_satFieldTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_satFieldTitle();
	}

	@Override
	public String colorPicker_selectTitle() {
		String val = getPropertyValue("colorPicker_selectTitle");
		return val != null ? val : originalSgwtMessages.colorPicker_selectTitle();
	}

	@Override
	public String colorPicker_selectedColorLabel() {
		String val = getPropertyValue("colorPicker_selectedColorLabel");
		return val != null ? val : originalSgwtMessages.colorPicker_selectedColorLabel();
	}

	@Override
	public String dataBoundComponent_addFormulaFieldText() {
		String val = getPropertyValue("dataBoundComponent_addFormulaFieldText");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_addFormulaFieldText();
	}

	@Override
	public String dataBoundComponent_addSummaryFieldText() {
		String val = getPropertyValue("dataBoundComponent_addSummaryFieldText");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_addSummaryFieldText();
	}

	@Override
	public String dataBoundComponent_duplicateDragMessage() {
		String val = getPropertyValue("dataBoundComponent_duplicateDragMessage");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_duplicateDragMessage();
	}

	@Override
	public String dataBoundComponent_editFormulaFieldText() {
		String val = getPropertyValue("dataBoundComponent_editFormulaFieldText");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_editFormulaFieldText();
	}

	@Override
	public String dataBoundComponent_editSummaryFieldText() {
		String val = getPropertyValue("dataBoundComponent_editSummaryFieldText");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_editSummaryFieldText();
	}

	@Override
	public String dataBoundComponent_offlineMessage() {
		String val = getPropertyValue("dataBoundComponent_offlineMessage");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_offlineMessage();
	}

	@Override
	public String dataBoundComponent_removeFormulaFieldText() {
		String val = getPropertyValue("dataBoundComponent_removeFormulaFieldText");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_removeFormulaFieldText();
	}

	@Override
	public String dataBoundComponent_removeSummaryFieldText() {
		String val = getPropertyValue("dataBoundComponent_removeSummaryFieldText");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_removeSummaryFieldText();
	}

	@Override
	public String dataBoundComponent_requiredFieldMessage() {
		String val = getPropertyValue("dataBoundComponent_requiredFieldMessage");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_requiredFieldMessage();
	}

	@Override
	public String dataBoundComponent_unknownErrorMessage() {
		String val = getPropertyValue("dataBoundComponent_unknownErrorMessage");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_unknownErrorMessage();
	}

	@Override
	public String dataBoundComponent_editHilitesDialogTitle() {
		String val = getPropertyValue("dataBoundComponent_editHilitesDialogTitle");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_editHilitesDialogTitle();
	}

	@Override
	public String dataBoundComponent_offlineSaveMessage() {
		String val = getPropertyValue("dataBoundComponent_offlineSaveMessage");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_offlineSaveMessage();
	}

	@Override
	public String dataBoundComponent_emptyExportMessage() {
		String val = getPropertyValue("dataBoundComponent_emptyExportMessage");
		return val != null ? val : originalSgwtMessages.dataBoundComponent_emptyExportMessage();
	}

	@Override
	public String dataSource_offlineMessage() {
		String val = getPropertyValue("dataSource_offlineMessage");
		return val != null ? val : originalSgwtMessages.dataSource_offlineMessage();
	}

	@Override
	public String detailViewer_configureFieldsText() {
		String val = getPropertyValue("detailViewer_configureFieldsText");
		return val != null ? val : originalSgwtMessages.detailViewer_configureFieldsText();
	}

	@Override
	public String facetChart_regressionLinesContextMenuItemTitle() {
		String val = getPropertyValue("facetChart_regressionLinesContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.facetChart_regressionLinesContextMenuItemTitle();
	}

	@Override
	public String facetChart_hideRegressionLinesContextMenuItemTitle() {
		String val = getPropertyValue("facetChart_hideRegressionLinesContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.facetChart_hideRegressionLinesContextMenuItemTitle();
	}

	@Override
	public String facetChart_linearRegressionLinesContextMenuItemTitle() {
		String val = getPropertyValue("facetChart_linearRegressionLinesContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.facetChart_linearRegressionLinesContextMenuItemTitle();
	}

	@Override
	public String facetChart_polynomialRegressionLinesContextMenuItemTitle() {
		String val = getPropertyValue("facetChart_polynomialRegressionLinesContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.facetChart_polynomialRegressionLinesContextMenuItemTitle();
	}

	@Override
	public String facetChart_polynomialDegreeRegressionLinesContextMenuItemTitle() {
		String val = getPropertyValue("facetChart_polynomialDegreeRegressionLinesContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.facetChart_polynomialDegreeRegressionLinesContextMenuItemTitle();
	}

	@Override
	public String facetChart_polynomialDegreePrompt() {
		String val = getPropertyValue("facetChart_polynomialDegreePrompt");
		return val != null ? val : originalSgwtMessages.facetChart_polynomialDegreePrompt();
	}

	@Override
	public String facetChart_invalidPolynomialDegreeMessage() {
		String val = getPropertyValue("facetChart_invalidPolynomialDegreeMessage");
		return val != null ? val : originalSgwtMessages.facetChart_invalidPolynomialDegreeMessage();
	}

	@Override
	public String dateChooser_cancelButtonTitle() {
		String val = getPropertyValue("dateChooser_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.dateChooser_cancelButtonTitle();
	}

	@Override
	public String dateChooser_firstDayOfWeek() {
		String val = getPropertyValue("dateChooser_firstDayOfWeek");
		return val != null ? val : originalSgwtMessages.dateChooser_firstDayOfWeek();
	}

	@Override
	public String dateChooser_todayButtonTitle() {
		String val = getPropertyValue("dateChooser_todayButtonTitle");
		return val != null ? val : originalSgwtMessages.dateChooser_todayButtonTitle();
	}

	@Override
	public String dateChooser_fiscalYearFieldTitle() {
		String val = getPropertyValue("dateChooser_fiscalYearFieldTitle");
		return val != null ? val : originalSgwtMessages.dateChooser_fiscalYearFieldTitle();
	}

	@Override
	public String dateChooser_weekFieldTitle() {
		String val = getPropertyValue("dateChooser_weekFieldTitle");
		return val != null ? val : originalSgwtMessages.dateChooser_weekFieldTitle();
	}

	@Override
	public String dateChooser_timeItemTitle() {
		String val = getPropertyValue("dateChooser_timeItemTitle");
		return val != null ? val : originalSgwtMessages.dateChooser_timeItemTitle();
	}

	@Override
	public String dateItem_daySelectorPrompt() {
		String val = getPropertyValue("dateItem_daySelectorPrompt");
		return val != null ? val : originalSgwtMessages.dateItem_daySelectorPrompt();
	}

	@Override
	public String dateItem_invalidDateStringMessage() {
		String val = getPropertyValue("dateItem_invalidDateStringMessage");
		return val != null ? val : originalSgwtMessages.dateItem_invalidDateStringMessage();
	}

	@Override
	public String dateItem_monthSelectorPrompt() {
		String val = getPropertyValue("dateItem_monthSelectorPrompt");
		return val != null ? val : originalSgwtMessages.dateItem_monthSelectorPrompt();
	}

	@Override
	public String dateItem_pickerIconPrompt() {
		String val = getPropertyValue("dateItem_pickerIconPrompt");
		return val != null ? val : originalSgwtMessages.dateItem_pickerIconPrompt();
	}

	@Override
	public String dateItem_selectorFormat() {
		String val = getPropertyValue("dateItem_selectorFormat");
		return val != null ? val : originalSgwtMessages.dateItem_selectorFormat();
	}

	@Override
	public String dateItem_yearSelectorPrompt() {
		String val = getPropertyValue("dateItem_yearSelectorPrompt");
		return val != null ? val : originalSgwtMessages.dateItem_yearSelectorPrompt();
	}

	@Override
	public String timeItem_hourItemTitle() {
		String val = getPropertyValue("timeItem_hourItemTitle");
		return val != null ? val : originalSgwtMessages.timeItem_hourItemTitle();
	}

	@Override
	public String timeItem_hourItemPrompt() {
		String val = getPropertyValue("timeItem_hourItemPrompt");
		return val != null ? val : originalSgwtMessages.timeItem_hourItemPrompt();
	}

	@Override
	public String timeItem_minuteItemTitle() {
		String val = getPropertyValue("timeItem_minuteItemTitle");
		return val != null ? val : originalSgwtMessages.timeItem_minuteItemTitle();
	}

	@Override
	public String timeItem_minuteItemPrompt() {
		String val = getPropertyValue("timeItem_minuteItemPrompt");
		return val != null ? val : originalSgwtMessages.timeItem_minuteItemPrompt();
	}

	@Override
	public String timeItem_secondItemTitle() {
		String val = getPropertyValue("timeItem_secondItemTitle");
		return val != null ? val : originalSgwtMessages.timeItem_secondItemTitle();
	}

	@Override
	public String timeItem_secondItemPrompt() {
		String val = getPropertyValue("timeItem_secondItemPrompt");
		return val != null ? val : originalSgwtMessages.timeItem_secondItemPrompt();
	}

	@Override
	public String timeItem_millisecondItemTitle() {
		String val = getPropertyValue("timeItem_millisecondItemTitle");
		return val != null ? val : originalSgwtMessages.timeItem_millisecondItemTitle();
	}

	@Override
	public String timeItem_millisecondItemPrompt() {
		String val = getPropertyValue("timeItem_millisecondItemPrompt");
		return val != null ? val : originalSgwtMessages.timeItem_millisecondItemPrompt();
	}

	@Override
	public String timeItem_ampmItemTitle() {
		String val = getPropertyValue("timeItem_ampmItemTitle");
		return val != null ? val : originalSgwtMessages.timeItem_ampmItemTitle();
	}

	@Override
	public String timeItem_ampmItemPrompt() {
		String val = getPropertyValue("timeItem_ampmItemPrompt");
		return val != null ? val : originalSgwtMessages.timeItem_ampmItemPrompt();
	}

	@Override
	public String timeItem_invalidTimeStringMessage() {
		String val = getPropertyValue("timeItem_invalidTimeStringMessage");
		return val != null ? val : originalSgwtMessages.timeItem_invalidTimeStringMessage();
	}

	@Override
	public String dateRangeDialog_cancelButtonTitle() {
		String val = getPropertyValue("dateRangeDialog_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.dateRangeDialog_cancelButtonTitle();
	}

	@Override
	public String dateRangeDialog_clearButtonTitle() {
		String val = getPropertyValue("dateRangeDialog_clearButtonTitle");
		return val != null ? val : originalSgwtMessages.dateRangeDialog_clearButtonTitle();
	}

	@Override
	public String dateRangeDialog_headerTitle() {
		String val = getPropertyValue("dateRangeDialog_headerTitle");
		return val != null ? val : originalSgwtMessages.dateRangeDialog_headerTitle();
	}

	@Override
	public String dateRangeDialog_okButtonTitle() {
		String val = getPropertyValue("dateRangeDialog_okButtonTitle");
		return val != null ? val : originalSgwtMessages.dateRangeDialog_okButtonTitle();
	}

	@Override
	public String dateRangeItem_fromTitle() {
		String val = getPropertyValue("dateRangeItem_fromTitle");
		return val != null ? val : originalSgwtMessages.dateRangeItem_fromTitle();
	}

	@Override
	public String dateRangeItem_toTitle() {
		String val = getPropertyValue("dateRangeItem_toTitle");
		return val != null ? val : originalSgwtMessages.dateRangeItem_toTitle();
	}

	@Override
	public String dateRangeItem_invalidRangeErrorMessage() {
		String val = getPropertyValue("dateRangeItem_invalidRangeErrorMessage");
		return val != null ? val : originalSgwtMessages.dateRangeItem_invalidRangeErrorMessage();
	}

	@Override
	public String numberUtil_currencySymbol() {
		String val = getPropertyValue("numberUtil_currencySymbol");
		return val != null ? val : originalSgwtMessages.numberUtil_currencySymbol();
	}

	@Override
	public String numberUtil_groupingSymbol() {
		String val = getPropertyValue("numberUtil_groupingSymbol");
		return val != null ? val : originalSgwtMessages.numberUtil_groupingSymbol();
	}

	@Override
	public String numberUtil_decimalSymbol() {
		String val = getPropertyValue("numberUtil_decimalSymbol");
		return val != null ? val : originalSgwtMessages.numberUtil_decimalSymbol();
	}

	@Override
	public String numberUtil_negativeSymbol() {
		String val = getPropertyValue("numberUtil_negativeSymbol");
		return val != null ? val : originalSgwtMessages.numberUtil_negativeSymbol();
	}

	@Override
	public String date_dateSeparator() {
		String val = getPropertyValue("date_dateSeparator");
		return val != null ? val : originalSgwtMessages.date_dateSeparator();
	}

	@Override
	public String date_shortDateFormat() {
		String val = getPropertyValue("date_shortDateFormat");
		return val != null ? val : originalSgwtMessages.date_shortDateFormat();
	}

	@Override
	public String date_shortDatetimeFormat() {
		String val = getPropertyValue("date_shortDatetimeFormat");
		return val != null ? val : originalSgwtMessages.date_shortDatetimeFormat();
	}

	@Override
	public String date_normalDateFormat() {
		String val = getPropertyValue("date_normalDateFormat");
		return val != null ? val : originalSgwtMessages.date_normalDateFormat();
	}

	@Override
	public String date_normalDatetimeFormat() {
		String val = getPropertyValue("date_normalDatetimeFormat");
		return val != null ? val : originalSgwtMessages.date_normalDatetimeFormat();
	}

	@Override
	public String date_inputFormat() {
		String val = getPropertyValue("date_inputFormat");
		return val != null ? val : originalSgwtMessages.date_inputFormat();
	}

	@Override
	public String date_shortDayNames_1() {
		String val = getPropertyValue("date_shortDayNames_1");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_1();
	}

	@Override
	public String date_shortDayNames_2() {
		String val = getPropertyValue("date_shortDayNames_2");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_2();
	}

	@Override
	public String date_shortDayNames_3() {
		String val = getPropertyValue("date_shortDayNames_3");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_3();
	}

	@Override
	public String date_shortDayNames_4() {
		String val = getPropertyValue("date_shortDayNames_4");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_4();
	}

	@Override
	public String date_shortDayNames_5() {
		String val = getPropertyValue("date_shortDayNames_5");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_5();
	}

	@Override
	public String date_shortDayNames_6() {
		String val = getPropertyValue("date_shortDayNames_6");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_6();
	}

	@Override
	public String date_shortDayNames_7() {
		String val = getPropertyValue("date_shortDayNames_7");
		return val != null ? val : originalSgwtMessages.date_shortDayNames_7();
	}

	@Override
	public String date_shortMonthNames_1() {
		String val = getPropertyValue("date_shortMonthNames_1");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_1();
	}

	@Override
	public String date_shortMonthNames_10() {
		String val = getPropertyValue("date_shortMonthNames_10");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_10();
	}

	@Override
	public String date_shortMonthNames_11() {
		String val = getPropertyValue("date_shortMonthNames_11");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_11();
	}

	@Override
	public String date_shortMonthNames_12() {
		String val = getPropertyValue("date_shortMonthNames_12");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_12();
	}

	@Override
	public String date_shortMonthNames_2() {
		String val = getPropertyValue("date_shortMonthNames_2");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_2();
	}

	@Override
	public String date_shortMonthNames_3() {
		String val = getPropertyValue("date_shortMonthNames_3");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_3();
	}

	@Override
	public String date_shortMonthNames_4() {
		String val = getPropertyValue("date_shortMonthNames_4");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_4();
	}

	@Override
	public String date_shortMonthNames_5() {
		String val = getPropertyValue("date_shortMonthNames_5");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_5();
	}

	@Override
	public String date_shortMonthNames_6() {
		String val = getPropertyValue("date_shortMonthNames_6");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_6();
	}

	@Override
	public String date_shortMonthNames_7() {
		String val = getPropertyValue("date_shortMonthNames_7");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_7();
	}

	@Override
	public String date_shortMonthNames_8() {
		String val = getPropertyValue("date_shortMonthNames_8");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_8();
	}

	@Override
	public String date_shortMonthNames_9() {
		String val = getPropertyValue("date_shortMonthNames_9");
		return val != null ? val : originalSgwtMessages.date_shortMonthNames_9();
	}

	@Override
	public String dialog_ApplyButtonTitle() {
		String val = getPropertyValue("dialog_ApplyButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_ApplyButtonTitle();
	}

	@Override
	public String dialog_AskForValueTitle() {
		String val = getPropertyValue("dialog_AskForValueTitle");
		return val != null ? val : originalSgwtMessages.dialog_AskForValueTitle();
	}

	@Override
	public String dialog_AskTitle() {
		String val = getPropertyValue("dialog_AskTitle");
		return val != null ? val : originalSgwtMessages.dialog_AskTitle();
	}

	@Override
	public String dialog_CancelButtonTitle() {
		String val = getPropertyValue("dialog_CancelButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_CancelButtonTitle();
	}

	@Override
	public String dialog_ConfirmTitle() {
		String val = getPropertyValue("dialog_ConfirmTitle");
		return val != null ? val : originalSgwtMessages.dialog_ConfirmTitle();
	}

	@Override
	public String dialog_DoneButtonTitle() {
		String val = getPropertyValue("dialog_DoneButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_DoneButtonTitle();
	}

	@Override
	public String dialog_LoginButtonTitle() {
		String val = getPropertyValue("dialog_LoginButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_LoginButtonTitle();
	}

	@Override
	public String dialog_LoginErrorMessage() {
		String val = getPropertyValue("dialog_LoginErrorMessage");
		return val != null ? val : originalSgwtMessages.dialog_LoginErrorMessage();
	}

	@Override
	public String dialog_LoginTitle() {
		String val = getPropertyValue("dialog_LoginTitle");
		return val != null ? val : originalSgwtMessages.dialog_LoginTitle();
	}

	@Override
	public String dialog_NoButtonTitle() {
		String val = getPropertyValue("dialog_NoButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_NoButtonTitle();
	}

	@Override
	public String dialog_OkButtonTitle() {
		String val = getPropertyValue("dialog_OkButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_OkButtonTitle();
	}

	@Override
	public String dialog_PasswordTitle() {
		String val = getPropertyValue("dialog_PasswordTitle");
		return val != null ? val : originalSgwtMessages.dialog_PasswordTitle();
	}

	@Override
	public String dialog_SayTitle() {
		String val = getPropertyValue("dialog_SayTitle");
		return val != null ? val : originalSgwtMessages.dialog_SayTitle();
	}

	@Override
	public String dialog_UserNameTitle() {
		String val = getPropertyValue("dialog_UserNameTitle");
		return val != null ? val : originalSgwtMessages.dialog_UserNameTitle();
	}

	@Override
	public String dialog_WarnTitle() {
		String val = getPropertyValue("dialog_WarnTitle");
		return val != null ? val : originalSgwtMessages.dialog_WarnTitle();
	}

	@Override
	public String dialog_YesButtonTitle() {
		String val = getPropertyValue("dialog_YesButtonTitle");
		return val != null ? val : originalSgwtMessages.dialog_YesButtonTitle();
	}

	@Override
	public String dynamicForm_errorsPreamble() {
		String val = getPropertyValue("dynamicForm_errorsPreamble");
		return val != null ? val : originalSgwtMessages.dynamicForm_errorsPreamble();
	}

	@Override
	public String dynamicForm_formSubmitFailedWarning() {
		String val = getPropertyValue("dynamicForm_formSubmitFailedWarning");
		return val != null ? val : originalSgwtMessages.dynamicForm_formSubmitFailedWarning();
	}

	@Override
	public String filterBuilder_addButtonPrompt() {
		String val = getPropertyValue("filterBuilder_addButtonPrompt");
		return val != null ? val : originalSgwtMessages.filterBuilder_addButtonPrompt();
	}

	@Override
	public String filterBuilder_matchAllTitle() {
		String val = getPropertyValue("filterBuilder_matchAllTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_matchAllTitle();
	}

	@Override
	public String filterBuilder_matchAnyTitle() {
		String val = getPropertyValue("filterBuilder_matchAnyTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_matchAnyTitle();
	}

	@Override
	public String filterBuilder_matchNoneTitle() {
		String val = getPropertyValue("filterBuilder_matchNoneTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_matchNoneTitle();
	}

	@Override
	public String filterBuilder_missingFieldPrompt() {
		String val = getPropertyValue("filterBuilder_missingFieldPrompt");
		return val != null ? val : originalSgwtMessages.filterBuilder_missingFieldPrompt();
	}

	@Override
	public String filterBuilder_radioOptions_and() {
		String val = getPropertyValue("filterBuilder_radioOptions_and");
		return val != null ? val : originalSgwtMessages.filterBuilder_radioOptions_and();
	}

	@Override
	public String filterBuilder_radioOptions_not() {
		String val = getPropertyValue("filterBuilder_radioOptions_not");
		return val != null ? val : originalSgwtMessages.filterBuilder_radioOptions_not();
	}

	@Override
	public String filterBuilder_radioOptions_or() {
		String val = getPropertyValue("filterBuilder_radioOptions_or");
		return val != null ? val : originalSgwtMessages.filterBuilder_radioOptions_or();
	}

	@Override
	public String filterBuilder_rangeSeparator() {
		String val = getPropertyValue("filterBuilder_rangeSeparator");
		return val != null ? val : originalSgwtMessages.filterBuilder_rangeSeparator();
	}

	@Override
	public String filterBuilder_removeButtonPrompt() {
		String val = getPropertyValue("filterBuilder_removeButtonPrompt");
		return val != null ? val : originalSgwtMessages.filterBuilder_removeButtonPrompt();
	}

	@Override
	public String filterBuilder_subClauseButtonPrompt() {
		String val = getPropertyValue("filterBuilder_subClauseButtonPrompt");
		return val != null ? val : originalSgwtMessages.filterBuilder_subClauseButtonPrompt();
	}

	@Override
	public String filterBuilder_subClauseButtonTitle() {
		String val = getPropertyValue("filterBuilder_subClauseButtonTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_subClauseButtonTitle();
	}

	@Override
	public String filterBuilder_topOperator() {
		String val = getPropertyValue("filterBuilder_topOperator");
		return val != null ? val : originalSgwtMessages.filterBuilder_topOperator();
	}

	@Override
	public String filterBuilder_operatorPickerTitle() {
		String val = getPropertyValue("filterBuilder_operatorPickerTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_operatorPickerTitle();
	}

	@Override
	public String filterBuilder_fieldPickerTitle() {
		String val = getPropertyValue("filterBuilder_fieldPickerTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_fieldPickerTitle();
	}

	@Override
	public String filterBuilder_radioOperatorTitle() {
		String val = getPropertyValue("filterBuilder_radioOperatorTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_radioOperatorTitle();
	}

	@Override
	public String filterBuilder_topOperatorTitle() {
		String val = getPropertyValue("filterBuilder_topOperatorTitle");
		return val != null ? val : originalSgwtMessages.filterBuilder_topOperatorTitle();
	}

	@Override
	public String FilterBuilder_inlineAndTitle() {
		String val = getPropertyValue("FilterBuilder_inlineAndTitle");
		return val != null ? val : originalSgwtMessages.FilterBuilder_inlineAndTitle();
	}

	@Override
	public String FilterBuilder_inlineOrTitle() {
		String val = getPropertyValue("FilterBuilder_inlineOrTitle");
		return val != null ? val : originalSgwtMessages.FilterBuilder_inlineOrTitle();
	}

	@Override
	public String FilterBuilder_inlineAndNotTitle() {
		String val = getPropertyValue("FilterBuilder_inlineAndNotTitle");
		return val != null ? val : originalSgwtMessages.FilterBuilder_inlineAndNotTitle();
	}

	@Override
	public String filterClause_removeButtonPrompt() {
		String val = getPropertyValue("filterClause_removeButtonPrompt");
		return val != null ? val : originalSgwtMessages.filterClause_removeButtonPrompt();
	}

	@Override
	public String filterClause_fieldPickerTitle() {
		String val = getPropertyValue("filterClause_fieldPickerTitle");
		return val != null ? val : originalSgwtMessages.filterClause_fieldPickerTitle();
	}

	@Override
	public String filterClause_valueItemTitle() {
		String val = getPropertyValue("filterClause_valueItemTitle");
		return val != null ? val : originalSgwtMessages.filterClause_valueItemTitle();
	}

	@Override
	public String formItem_pickerIconPrompt() {
		String val = getPropertyValue("formItem_pickerIconPrompt");
		return val != null ? val : originalSgwtMessages.formItem_pickerIconPrompt();
	}

	@Override
	public String formulaBuilder_autoHideCheckBoxLabel() {
		String val = getPropertyValue("formulaBuilder_autoHideCheckBoxLabel");
		return val != null ? val : originalSgwtMessages.formulaBuilder_autoHideCheckBoxLabel();
	}

	@Override
	public String formulaBuilder_builderTypeText() {
		String val = getPropertyValue("formulaBuilder_builderTypeText");
		return val != null ? val : originalSgwtMessages.formulaBuilder_builderTypeText();
	}

	@Override
	public String formulaBuilder_cancelButtonTitle() {
		String val = getPropertyValue("formulaBuilder_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_cancelButtonTitle();
	}

	@Override
	public String formulaBuilder_defaultNewFieldTitle() {
		String val = getPropertyValue("formulaBuilder_defaultNewFieldTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_defaultNewFieldTitle();
	}

	@Override
	public String formulaBuilder_helpTextIntro() {
		String val = getPropertyValue("formulaBuilder_helpTextIntro");
		return val != null ? val : originalSgwtMessages.formulaBuilder_helpTextIntro();
	}

	@Override
	public String formulaBuilder_helpWindowTitle() {
		String val = getPropertyValue("formulaBuilder_helpWindowTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_helpWindowTitle();
	}

	@Override
	public String formulaBuilder_instructionsTextStart() {
		String val = getPropertyValue("formulaBuilder_instructionsTextStart");
		return val != null ? val : originalSgwtMessages.formulaBuilder_instructionsTextStart();
	}

	@Override
	public String formulaBuilder_invalidBlankPrompt() {
		String val = getPropertyValue("formulaBuilder_invalidBlankPrompt");
		return val != null ? val : originalSgwtMessages.formulaBuilder_invalidBlankPrompt();
	}

	@Override
	public String formulaBuilder_invalidBuilderPrompt() {
		String val = getPropertyValue("formulaBuilder_invalidBuilderPrompt");
		return val != null ? val : originalSgwtMessages.formulaBuilder_invalidBuilderPrompt();
	}

	@Override
	public String formulaBuilder_invalidGeneratedFunctionPrompt() {
		String val = getPropertyValue("formulaBuilder_invalidGeneratedFunctionPrompt");
		return val != null ? val : originalSgwtMessages.formulaBuilder_invalidGeneratedFunctionPrompt();
	}

	@Override
	public String formulaBuilder_keyColumnTitle() {
		String val = getPropertyValue("formulaBuilder_keyColumnTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_keyColumnTitle();
	}

	@Override
	public String formulaBuilder_sampleHeaderTitle() {
		String val = getPropertyValue("formulaBuilder_sampleHeaderTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_sampleHeaderTitle();
	}

	@Override
	public String formulaBuilder_samplePromptForRecord() {
		String val = getPropertyValue("formulaBuilder_samplePromptForRecord");
		return val != null ? val : originalSgwtMessages.formulaBuilder_samplePromptForRecord();
	}

	@Override
	public String formulaBuilder_samplePromptOutput() {
		String val = getPropertyValue("formulaBuilder_samplePromptOutput");
		return val != null ? val : originalSgwtMessages.formulaBuilder_samplePromptOutput();
	}

	@Override
	public String formulaBuilder_saveButtonTitle() {
		String val = getPropertyValue("formulaBuilder_saveButtonTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_saveButtonTitle();
	}

	@Override
	public String formulaBuilder_saveConfirmationPrompt() {
		String val = getPropertyValue("formulaBuilder_saveConfirmationPrompt");
		return val != null ? val : originalSgwtMessages.formulaBuilder_saveConfirmationPrompt();
	}

	@Override
	public String formulaBuilder_sourceFieldColumnTitle() {
		String val = getPropertyValue("formulaBuilder_sourceFieldColumnTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_sourceFieldColumnTitle();
	}

	@Override
	public String formulaBuilder_testButtonTitle() {
		String val = getPropertyValue("formulaBuilder_testButtonTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_testButtonTitle();
	}

	@Override
	public String formulaBuilder_titleFieldTitle() {
		String val = getPropertyValue("formulaBuilder_titleFieldTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_titleFieldTitle();
	}

	@Override
	public String formulaBuilder_validBuilderPrompt() {
		String val = getPropertyValue("formulaBuilder_validBuilderPrompt");
		return val != null ? val : originalSgwtMessages.formulaBuilder_validBuilderPrompt();
	}

	@Override
	public String formulaBuilder_defaultErrorText() {
		String val = getPropertyValue("formulaBuilder_defaultErrorText");
		return val != null ? val : originalSgwtMessages.formulaBuilder_defaultErrorText();
	}

	@Override
	public String formulaBuilder_saveAddAnotherButtonTitle() {
		String val = getPropertyValue("formulaBuilder_saveAddAnotherButtonTitle");
		return val != null ? val : originalSgwtMessages.formulaBuilder_saveAddAnotherButtonTitle();
	}

	@Override
	public String formulaBuilder_warnDuplicateTitlesMessage() {
		String val = getPropertyValue("formulaBuilder_warnDuplicateTitlesMessage");
		return val != null ? val : originalSgwtMessages.formulaBuilder_warnDuplicateTitlesMessage();
	}

	@Override
	public String groupingMessages_byDayOfMonthTitle() {
		String val = getPropertyValue("groupingMessages_byDayOfMonthTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byDayOfMonthTitle();
	}

	@Override
	public String groupingMessages_byDayTitle() {
		String val = getPropertyValue("groupingMessages_byDayTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byDayTitle();
	}

	@Override
	public String groupingMessages_byHoursTitle() {
		String val = getPropertyValue("groupingMessages_byHoursTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byHoursTitle();
	}

	@Override
	public String groupingMessages_byMillisecondsTitle() {
		String val = getPropertyValue("groupingMessages_byMillisecondsTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byMillisecondsTitle();
	}

	@Override
	public String groupingMessages_byMinutesTitle() {
		String val = getPropertyValue("groupingMessages_byMinutesTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byMinutesTitle();
	}

	@Override
	public String groupingMessages_byMonthTitle() {
		String val = getPropertyValue("groupingMessages_byMonthTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byMonthTitle();
	}

	@Override
	public String groupingMessages_byQuarterTitle() {
		String val = getPropertyValue("groupingMessages_byQuarterTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byQuarterTitle();
	}

	@Override
	public String groupingMessages_bySecondsTitle() {
		String val = getPropertyValue("groupingMessages_bySecondsTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_bySecondsTitle();
	}

	@Override
	public String groupingMessages_byUpcomingTitle() {
		String val = getPropertyValue("groupingMessages_byUpcomingTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byUpcomingTitle();
	}

	@Override
	public String groupingMessages_byWeekTitle() {
		String val = getPropertyValue("groupingMessages_byWeekTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byWeekTitle();
	}

	@Override
	public String groupingMessages_byYearTitle() {
		String val = getPropertyValue("groupingMessages_byYearTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_byYearTitle();
	}

	@Override
	public String groupingMessages_upcomingBeforeTitle() {
		String val = getPropertyValue("groupingMessages_upcomingBeforeTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingBeforeTitle();
	}

	@Override
	public String groupingMessages_upcomingLaterTitle() {
		String val = getPropertyValue("groupingMessages_upcomingLaterTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingLaterTitle();
	}

	@Override
	public String groupingMessages_upcomingNextMonthTitle() {
		String val = getPropertyValue("groupingMessages_upcomingNextMonthTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingNextMonthTitle();
	}

	@Override
	public String groupingMessages_upcomingNextWeekTitle() {
		String val = getPropertyValue("groupingMessages_upcomingNextWeekTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingNextWeekTitle();
	}

	@Override
	public String groupingMessages_upcomingThisWeekTitle() {
		String val = getPropertyValue("groupingMessages_upcomingThisWeekTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingThisWeekTitle();
	}

	@Override
	public String groupingMessages_upcomingTodayTitle() {
		String val = getPropertyValue("groupingMessages_upcomingTodayTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingTodayTitle();
	}

	@Override
	public String groupingMessages_upcomingTomorrowTitle() {
		String val = getPropertyValue("groupingMessages_upcomingTomorrowTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_upcomingTomorrowTitle();
	}

	@Override
	public String groupingMessages_weekNumberTitle() {
		String val = getPropertyValue("groupingMessages_weekNumberTitle");
		return val != null ? val : originalSgwtMessages.groupingMessages_weekNumberTitle();
	}

	@Override
	public String groupingMessages_timezoneMinutesSuffix() {
		String val = getPropertyValue("groupingMessages_timezoneMinutesSuffix");
		return val != null ? val : originalSgwtMessages.groupingMessages_timezoneMinutesSuffix();
	}

	@Override
	public String groupingMessages_timezoneSecondsSuffix() {
		String val = getPropertyValue("groupingMessages_timezoneSecondsSuffix");
		return val != null ? val : originalSgwtMessages.groupingMessages_timezoneSecondsSuffix();
	}

	@Override
	public String grouping_byDayOfMonthTitle() {
		String val = getPropertyValue("grouping_byDayOfMonthTitle");
		return val != null ? val : originalSgwtMessages.grouping_byDayOfMonthTitle();
	}

	@Override
	public String grouping_byDayTitle() {
		String val = getPropertyValue("grouping_byDayTitle");
		return val != null ? val : originalSgwtMessages.grouping_byDayTitle();
	}

	@Override
	public String grouping_byHoursTitle() {
		String val = getPropertyValue("grouping_byHoursTitle");
		return val != null ? val : originalSgwtMessages.grouping_byHoursTitle();
	}

	@Override
	public String grouping_byMillisecondsTitle() {
		String val = getPropertyValue("grouping_byMillisecondsTitle");
		return val != null ? val : originalSgwtMessages.grouping_byMillisecondsTitle();
	}

	@Override
	public String grouping_byMinutesTitle() {
		String val = getPropertyValue("grouping_byMinutesTitle");
		return val != null ? val : originalSgwtMessages.grouping_byMinutesTitle();
	}

	@Override
	public String grouping_byMonthTitle() {
		String val = getPropertyValue("grouping_byMonthTitle");
		return val != null ? val : originalSgwtMessages.grouping_byMonthTitle();
	}

	@Override
	public String grouping_byQuarterTitle() {
		String val = getPropertyValue("grouping_byQuarterTitle");
		return val != null ? val : originalSgwtMessages.grouping_byQuarterTitle();
	}

	@Override
	public String grouping_bySecondsTitle() {
		String val = getPropertyValue("grouping_bySecondsTitle");
		return val != null ? val : originalSgwtMessages.grouping_bySecondsTitle();
	}

	@Override
	public String grouping_byUpcomingTitle() {
		String val = getPropertyValue("grouping_byUpcomingTitle");
		return val != null ? val : originalSgwtMessages.grouping_byUpcomingTitle();
	}

	@Override
	public String grouping_byWeekTitle() {
		String val = getPropertyValue("grouping_byWeekTitle");
		return val != null ? val : originalSgwtMessages.grouping_byWeekTitle();
	}

	@Override
	public String grouping_byYearTitle() {
		String val = getPropertyValue("grouping_byYearTitle");
		return val != null ? val : originalSgwtMessages.grouping_byYearTitle();
	}

	@Override
	public String grouping_upcomingBeforeTitle() {
		String val = getPropertyValue("grouping_upcomingBeforeTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingBeforeTitle();
	}

	@Override
	public String grouping_upcomingLaterTitle() {
		String val = getPropertyValue("grouping_upcomingLaterTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingLaterTitle();
	}

	@Override
	public String grouping_upcomingNextMonthTitle() {
		String val = getPropertyValue("grouping_upcomingNextMonthTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingNextMonthTitle();
	}

	@Override
	public String grouping_upcomingNextWeekTitle() {
		String val = getPropertyValue("grouping_upcomingNextWeekTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingNextWeekTitle();
	}

	@Override
	public String grouping_upcomingThisWeekTitle() {
		String val = getPropertyValue("grouping_upcomingThisWeekTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingThisWeekTitle();
	}

	@Override
	public String grouping_upcomingTodayTitle() {
		String val = getPropertyValue("grouping_upcomingTodayTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingTodayTitle();
	}

	@Override
	public String grouping_upcomingTomorrowTitle() {
		String val = getPropertyValue("grouping_upcomingTomorrowTitle");
		return val != null ? val : originalSgwtMessages.grouping_upcomingTomorrowTitle();
	}

	@Override
	public String grouping_weekNumberTitle() {
		String val = getPropertyValue("grouping_weekNumberTitle");
		return val != null ? val : originalSgwtMessages.grouping_weekNumberTitle();
	}

	@Override
	public String grouping_timezoneMinutesSuffix() {
		String val = getPropertyValue("grouping_timezoneMinutesSuffix");
		return val != null ? val : originalSgwtMessages.grouping_timezoneMinutesSuffix();
	}

	@Override
	public String grouping_timezoneSecondsSuffix() {
		String val = getPropertyValue("grouping_timezoneSecondsSuffix");
		return val != null ? val : originalSgwtMessages.grouping_timezoneSecondsSuffix();
	}

	@Override
	public String hiliteRule_removeButtonPrompt() {
		String val = getPropertyValue("hiliteRule_removeButtonPrompt");
		return val != null ? val : originalSgwtMessages.hiliteRule_removeButtonPrompt();
	}

	@Override
	public String hiliteRule_colorFieldTitle() {
		String val = getPropertyValue("hiliteRule_colorFieldTitle");
		return val != null ? val : originalSgwtMessages.hiliteRule_colorFieldTitle();
	}

	@Override
	public String hiliteRule_iconFieldTitle() {
		String val = getPropertyValue("hiliteRule_iconFieldTitle");
		return val != null ? val : originalSgwtMessages.hiliteRule_iconFieldTitle();
	}

	@Override
	public String hiliteRule_foregroundColorTitle() {
		String val = getPropertyValue("hiliteRule_foregroundColorTitle");
		return val != null ? val : originalSgwtMessages.hiliteRule_foregroundColorTitle();
	}

	@Override
	public String hiliteRule_backgroundColorTitle() {
		String val = getPropertyValue("hiliteRule_backgroundColorTitle");
		return val != null ? val : originalSgwtMessages.hiliteRule_backgroundColorTitle();
	}

	@Override
	public String hiliteEditor_addAdvancedRuleButtonTitle() {
		String val = getPropertyValue("hiliteEditor_addAdvancedRuleButtonTitle");
		return val != null ? val : originalSgwtMessages.hiliteEditor_addAdvancedRuleButtonTitle();
	}

	@Override
	public String hiliteEditor_saveButtonTitle() {
		String val = getPropertyValue("hiliteEditor_saveButtonTitle");
		return val != null ? val : originalSgwtMessages.hiliteEditor_saveButtonTitle();
	}

	@Override
	public String hiliteEditor_cancelButtonTitle() {
		String val = getPropertyValue("hiliteEditor_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.hiliteEditor_cancelButtonTitle();
	}

	@Override
	public String hiliteEditor_availableFieldsColumnTitle() {
		String val = getPropertyValue("hiliteEditor_availableFieldsColumnTitle");
		return val != null ? val : originalSgwtMessages.hiliteEditor_availableFieldsColumnTitle();
	}

	@Override
	public String advancedHiliteEditor_title() {
		String val = getPropertyValue("advancedHiliteEditor_title");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_title();
	}

	@Override
	public String advancedHiliteEditor_saveButtonTitle() {
		String val = getPropertyValue("advancedHiliteEditor_saveButtonTitle");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_saveButtonTitle();
	}

	@Override
	public String advancedHiliteEditor_cancelButtonTitle() {
		String val = getPropertyValue("advancedHiliteEditor_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_cancelButtonTitle();
	}

	@Override
	public String advancedHiliteEditor_invalidHilitePrompt() {
		String val = getPropertyValue("advancedHiliteEditor_invalidHilitePrompt");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_invalidHilitePrompt();
	}

	@Override
	public String advancedHiliteEditor_filterGroupTitle() {
		String val = getPropertyValue("advancedHiliteEditor_filterGroupTitle");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_filterGroupTitle();
	}

	@Override
	public String advancedHiliteEditor_appearanceGroupTitle() {
		String val = getPropertyValue("advancedHiliteEditor_appearanceGroupTitle");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_appearanceGroupTitle();
	}

	@Override
	public String advancedHiliteEditor_targetFieldsItemTitle() {
		String val = getPropertyValue("advancedHiliteEditor_targetFieldsItemTitle");
		return val != null ? val : originalSgwtMessages.advancedHiliteEditor_targetFieldsItemTitle();
	}

	@Override
	public String multiFilePicker_emptyMessage() {
		String val = getPropertyValue("multiFilePicker_emptyMessage");
		return val != null ? val : originalSgwtMessages.multiFilePicker_emptyMessage();
	}

	@Override
	public String iMenuButton_title() {
		String val = getPropertyValue("iMenuButton_title");
		return val != null ? val : originalSgwtMessages.iMenuButton_title();
	}

	@Override
	public String listGrid_autoFitAllText() {
		String val = getPropertyValue("listGrid_autoFitAllText");
		return val != null ? val : originalSgwtMessages.listGrid_autoFitAllText();
	}

	@Override
	public String listGrid_autoFitFieldText() {
		String val = getPropertyValue("listGrid_autoFitFieldText");
		return val != null ? val : originalSgwtMessages.listGrid_autoFitFieldText();
	}

	@Override
	public String listGrid_cancelEditingConfirmationMessage() {
		String val = getPropertyValue("listGrid_cancelEditingConfirmationMessage");
		return val != null ? val : originalSgwtMessages.listGrid_cancelEditingConfirmationMessage();
	}

	@Override
	public String listGrid_clearAllSortingText() {
		String val = getPropertyValue("listGrid_clearAllSortingText");
		return val != null ? val : originalSgwtMessages.listGrid_clearAllSortingText();
	}

	@Override
	public String listGrid_clearFilterText() {
		String val = getPropertyValue("listGrid_clearFilterText");
		return val != null ? val : originalSgwtMessages.listGrid_clearFilterText();
	}

	@Override
	public String listGrid_clearSortFieldText() {
		String val = getPropertyValue("listGrid_clearSortFieldText");
		return val != null ? val : originalSgwtMessages.listGrid_clearSortFieldText();
	}

	@Override
	public String listGrid_configureSortText() {
		String val = getPropertyValue("listGrid_configureSortText");
		return val != null ? val : originalSgwtMessages.listGrid_configureSortText();
	}

	@Override
	public String listGrid_configureGroupingText() {
		String val = getPropertyValue("listGrid_configureGroupingText");
		return val != null ? val : originalSgwtMessages.listGrid_configureGroupingText();
	}

	@Override
	public String listGrid_confirmDiscardEditsMessage() {
		String val = getPropertyValue("listGrid_confirmDiscardEditsMessage");
		return val != null ? val : originalSgwtMessages.listGrid_confirmDiscardEditsMessage();
	}

	@Override
	public String listGrid_deleteRecordContextMenuItemTitle() {
		String val = getPropertyValue("listGrid_deleteRecordContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.listGrid_deleteRecordContextMenuItemTitle();
	}

	@Override
	public String listGrid_discardEditsSaveButtonTitle() {
		String val = getPropertyValue("listGrid_discardEditsSaveButtonTitle");
		return val != null ? val : originalSgwtMessages.listGrid_discardEditsSaveButtonTitle();
	}

	@Override
	public String listGrid_dismissEmbeddedComponentContextMenuItemTitle() {
		String val = getPropertyValue("listGrid_dismissEmbeddedComponentContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.listGrid_dismissEmbeddedComponentContextMenuItemTitle();
	}

	@Override
	public String listGrid_emptyMessage() {
		String val = getPropertyValue("listGrid_emptyMessage");
		return val != null ? val : originalSgwtMessages.listGrid_emptyMessage();
	}

	@Override
	public String listGrid_fieldVisibilitySubmenuTitle() {
		String val = getPropertyValue("listGrid_fieldVisibilitySubmenuTitle");
		return val != null ? val : originalSgwtMessages.listGrid_fieldVisibilitySubmenuTitle();
	}

	@Override
	public String listGrid_freezeFieldText() {
		String val = getPropertyValue("listGrid_freezeFieldText");
		return val != null ? val : originalSgwtMessages.listGrid_freezeFieldText();
	}

	@Override
	public String listGrid_freezeOnLeftText() {
		String val = getPropertyValue("listGrid_freezeOnLeftText");
		return val != null ? val : originalSgwtMessages.listGrid_freezeOnLeftText();
	}

	@Override
	public String listGrid_freezeOnRightText() {
		String val = getPropertyValue("listGrid_freezeOnRightText");
		return val != null ? val : originalSgwtMessages.listGrid_freezeOnRightText();
	}

	@Override
	public String listGrid_groupByText() {
		String val = getPropertyValue("listGrid_groupByText");
		return val != null ? val : originalSgwtMessages.listGrid_groupByText();
	}

	@Override
	public String listGrid_loadingDataMessage() {
		String val = getPropertyValue("listGrid_loadingDataMessage");
		return val != null ? val : originalSgwtMessages.listGrid_loadingDataMessage();
	}

	@Override
	public String listGrid_maxExpandedRecordsPrompt() {
		String val = getPropertyValue("listGrid_maxExpandedRecordsPrompt");
		return val != null ? val : originalSgwtMessages.listGrid_maxExpandedRecordsPrompt();
	}

	@Override
	public String listGrid_newRecordRowMessage() {
		String val = getPropertyValue("listGrid_newRecordRowMessage");
		return val != null ? val : originalSgwtMessages.listGrid_newRecordRowMessage();
	}

	@Override
	public String listGrid_openRecordEditorContextMenuItemTitle() {
		String val = getPropertyValue("listGrid_openRecordEditorContextMenuItemTitle");
		return val != null ? val : originalSgwtMessages.listGrid_openRecordEditorContextMenuItemTitle();
	}

	@Override
	public String listGrid_recordEditorCancelButtonTitle() {
		String val = getPropertyValue("listGrid_recordEditorCancelButtonTitle");
		return val != null ? val : originalSgwtMessages.listGrid_recordEditorCancelButtonTitle();
	}

	@Override
	public String listGrid_recordEditorSaveButtonTitle() {
		String val = getPropertyValue("listGrid_recordEditorSaveButtonTitle");
		return val != null ? val : originalSgwtMessages.listGrid_recordEditorSaveButtonTitle();
	}

	@Override
	public String listGrid_removeFieldTitle() {
		String val = getPropertyValue("listGrid_removeFieldTitle");
		return val != null ? val : originalSgwtMessages.listGrid_removeFieldTitle();
	}

	@Override
	public String listGrid_sortFieldAscendingText() {
		String val = getPropertyValue("listGrid_sortFieldAscendingText");
		return val != null ? val : originalSgwtMessages.listGrid_sortFieldAscendingText();
	}

	@Override
	public String listGrid_sortFieldDescendingText() {
		String val = getPropertyValue("listGrid_sortFieldDescendingText");
		return val != null ? val : originalSgwtMessages.listGrid_sortFieldDescendingText();
	}

	@Override
	public String listGrid_unfreezeFieldText() {
		String val = getPropertyValue("listGrid_unfreezeFieldText");
		return val != null ? val : originalSgwtMessages.listGrid_unfreezeFieldText();
	}

	@Override
	public String listGrid_ungroupText() {
		String val = getPropertyValue("listGrid_ungroupText");
		return val != null ? val : originalSgwtMessages.listGrid_ungroupText();
	}

	@Override
	public String listGrid_asynchGroupingPrompt() {
		String val = getPropertyValue("listGrid_asynchGroupingPrompt");
		return val != null ? val : originalSgwtMessages.listGrid_asynchGroupingPrompt();
	}

	@Override
	public String listGrid_hiliteReplaceValueFieldTitle() {
		String val = getPropertyValue("listGrid_hiliteReplaceValueFieldTitle");
		return val != null ? val : originalSgwtMessages.listGrid_hiliteReplaceValueFieldTitle();
	}

	@Override
	public String listGrid_filterButtonPrompt() {
		String val = getPropertyValue("listGrid_filterButtonPrompt");
		return val != null ? val : originalSgwtMessages.listGrid_filterButtonPrompt();
	}

	@Override
	public String listGrid_loadingMessage() {
		String val = getPropertyValue("listGrid_loadingMessage");
		return val != null ? val : originalSgwtMessages.listGrid_loadingMessage();
	}

	@Override
	public String listGrid_warnOnRemovalMessage() {
		String val = getPropertyValue("listGrid_warnOnRemovalMessage");
		return val != null ? val : originalSgwtMessages.listGrid_warnOnRemovalMessage();
	}

	@Override
	public String listGrid_sorterButtonTitle() {
		String val = getPropertyValue("listGrid_sorterButtonTitle");
		return val != null ? val : originalSgwtMessages.listGrid_sorterButtonTitle();
	}

	@Override
	public String listGrid_expansionEditorSaveDialogPrompt() {
		String val = getPropertyValue("listGrid_expansionEditorSaveDialogPrompt");
		return val != null ? val : originalSgwtMessages.listGrid_expansionEditorSaveDialogPrompt();
	}

	@Override
	public String listGrid_expansionEditorSaveButtonTitle() {
		String val = getPropertyValue("listGrid_expansionEditorSaveButtonTitle");
		return val != null ? val : originalSgwtMessages.listGrid_expansionEditorSaveButtonTitle();
	}

	@Override
	public String listGrid_formulaBuilderSpanTitleSeparator() {
		String val = getPropertyValue("listGrid_formulaBuilderSpanTitleSeparator");
		return val != null ? val : originalSgwtMessages.listGrid_formulaBuilderSpanTitleSeparator();
	}

	@Override
	public String listGrid_sortEditorSpanTitleSeparator() {
		String val = getPropertyValue("listGrid_sortEditorSpanTitleSeparator");
		return val != null ? val : originalSgwtMessages.listGrid_sortEditorSpanTitleSeparator();
	}

	@Override
	public String listGrid_hiliteEditorSpanTitleSeparator() {
		String val = getPropertyValue("listGrid_hiliteEditorSpanTitleSeparator");
		return val != null ? val : originalSgwtMessages.listGrid_hiliteEditorSpanTitleSeparator();
	}

	@Override
	public String presetCriteriaItem_customOptionTitle() {
		String val = getPropertyValue("presetCriteriaItem_customOptionTitle");
		return val != null ? val : originalSgwtMessages.presetCriteriaItem_customOptionTitle();
	}

	@Override
	public String menuButton_title() {
		String val = getPropertyValue("menuButton_title");
		return val != null ? val : originalSgwtMessages.menuButton_title();
	}

	@Override
	public String treeMenuButton_unselectedTitle() {
		String val = getPropertyValue("treeMenuButton_unselectedTitle");
		return val != null ? val : originalSgwtMessages.treeMenuButton_unselectedTitle();
	}

	@Override
	public String miniDateRangeItem_fromDateOnlyPrefix() {
		String val = getPropertyValue("miniDateRangeItem_fromDateOnlyPrefix");
		return val != null ? val : originalSgwtMessages.miniDateRangeItem_fromDateOnlyPrefix();
	}

	@Override
	public String miniDateRangeItem_pickerIconPrompt() {
		String val = getPropertyValue("miniDateRangeItem_pickerIconPrompt");
		return val != null ? val : originalSgwtMessages.miniDateRangeItem_pickerIconPrompt();
	}

	@Override
	public String miniDateRangeItem_toDateOnlyPrefix() {
		String val = getPropertyValue("miniDateRangeItem_toDateOnlyPrefix");
		return val != null ? val : originalSgwtMessages.miniDateRangeItem_toDateOnlyPrefix();
	}

	@Override
	public String multiComboBoxItem_defaultHint() {
		String val = getPropertyValue("multiComboBoxItem_defaultHint");
		return val != null ? val : originalSgwtMessages.multiComboBoxItem_defaultHint();
	}

	@Override
	public String multiFileItem_emptyMessage() {
		String val = getPropertyValue("multiFileItem_emptyMessage");
		return val != null ? val : originalSgwtMessages.multiFileItem_emptyMessage();
	}

	@Override
	public String multiFileItem_editButtonPrompt() {
		String val = getPropertyValue("multiFileItem_editButtonPrompt");
		return val != null ? val : originalSgwtMessages.multiFileItem_editButtonPrompt();
	}

	@Override
	public String multiFileItem_removeButtonPrompt() {
		String val = getPropertyValue("multiFileItem_removeButtonPrompt");
		return val != null ? val : originalSgwtMessages.multiFileItem_removeButtonPrompt();
	}

	@Override
	public String multiFileItem_pickerUploadButtonInitialTitle() {
		String val = getPropertyValue("multiFileItem_pickerUploadButtonInitialTitle");
		return val != null ? val : originalSgwtMessages.multiFileItem_pickerUploadButtonInitialTitle();
	}

	@Override
	public String multiFileItem_pickerUploadButtonTitle() {
		String val = getPropertyValue("multiFileItem_pickerUploadButtonTitle");
		return val != null ? val : originalSgwtMessages.multiFileItem_pickerUploadButtonTitle();
	}

	@Override
	public String multiFileItem_pickerCancelButtonTitle() {
		String val = getPropertyValue("multiFileItem_pickerCancelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiFileItem_pickerCancelButtonTitle();
	}

	@Override
	public String multiFileItem_pickerAddAnotherFileButtonTitle() {
		String val = getPropertyValue("multiFileItem_pickerAddAnotherFileButtonTitle");
		return val != null ? val : originalSgwtMessages.multiFileItem_pickerAddAnotherFileButtonTitle();
	}

	@Override
	public String multiFileItem_pickerUploadProgressLabel() {
		String val = getPropertyValue("multiFileItem_pickerUploadProgressLabel");
		return val != null ? val : originalSgwtMessages.multiFileItem_pickerUploadProgressLabel();
	}

	@Override
	public String multiSortDialog_addLevelButtonTitle() {
		String val = getPropertyValue("multiSortDialog_addLevelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_addLevelButtonTitle();
	}

	@Override
	public String multiSortDialog_applyButtonTitle() {
		String val = getPropertyValue("multiSortDialog_applyButtonTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_applyButtonTitle();
	}

	@Override
	public String multiSortDialog_ascendingTitle() {
		String val = getPropertyValue("multiSortDialog_ascendingTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_ascendingTitle();
	}

	@Override
	public String multiSortDialog_cancelButtonTitle() {
		String val = getPropertyValue("multiSortDialog_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_cancelButtonTitle();
	}

	@Override
	public String multiSortDialog_copyLevelButtonTitle() {
		String val = getPropertyValue("multiSortDialog_copyLevelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_copyLevelButtonTitle();
	}

	@Override
	public String multiSortDialog_deleteLevelButtonTitle() {
		String val = getPropertyValue("multiSortDialog_deleteLevelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_deleteLevelButtonTitle();
	}

	@Override
	public String multiSortDialog_descendingTitle() {
		String val = getPropertyValue("multiSortDialog_descendingTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_descendingTitle();
	}

	@Override
	public String multiSortDialog_directionFieldTitle() {
		String val = getPropertyValue("multiSortDialog_directionFieldTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_directionFieldTitle();
	}

	@Override
	public String multiSortDialog_firstSortLevelTitle() {
		String val = getPropertyValue("multiSortDialog_firstSortLevelTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_firstSortLevelTitle();
	}

	@Override
	public String multiSortDialog_invalidListPrompt() {
		String val = getPropertyValue("multiSortDialog_invalidListPrompt");
		return val != null ? val : originalSgwtMessages.multiSortDialog_invalidListPrompt();
	}

	@Override
	public String multiSortDialog_levelDownPrompt() {
		String val = getPropertyValue("multiSortDialog_levelDownPrompt");
		return val != null ? val : originalSgwtMessages.multiSortDialog_levelDownPrompt();
	}

	@Override
	public String multiSortDialog_levelUpPrompt() {
		String val = getPropertyValue("multiSortDialog_levelUpPrompt");
		return val != null ? val : originalSgwtMessages.multiSortDialog_levelUpPrompt();
	}

	@Override
	public String multiSortDialog_otherSortLevelTitle() {
		String val = getPropertyValue("multiSortDialog_otherSortLevelTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_otherSortLevelTitle();
	}

	@Override
	public String multiSortDialog_propertyFieldTitle() {
		String val = getPropertyValue("multiSortDialog_propertyFieldTitle");
		return val != null ? val : originalSgwtMessages.multiSortDialog_propertyFieldTitle();
	}

	@Override
	public String multiSortDialog_title() {
		String val = getPropertyValue("multiSortDialog_title");
		return val != null ? val : originalSgwtMessages.multiSortDialog_title();
	}

	@Override
	public String multiGroupDialog_addLevelButtonTitle() {
		String val = getPropertyValue("multiGroupDialog_addLevelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_addLevelButtonTitle();
	}

	@Override
	public String multiGroupDialog_applyButtonTitle() {
		String val = getPropertyValue("multiGroupDialog_applyButtonTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_applyButtonTitle();
	}

	@Override
	public String multiGroupDialog_cancelButtonTitle() {
		String val = getPropertyValue("multiGroupDialog_cancelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_cancelButtonTitle();
	}

	@Override
	public String multiGroupDialog_copyLevelButtonTitle() {
		String val = getPropertyValue("multiGroupDialog_copyLevelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_copyLevelButtonTitle();
	}

	@Override
	public String multiGroupDialog_deleteLevelButtonTitle() {
		String val = getPropertyValue("multiGroupDialog_deleteLevelButtonTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_deleteLevelButtonTitle();
	}

	@Override
	public String multiGroupDialog_groupingFieldTitle() {
		String val = getPropertyValue("multiGroupDialog_groupingFieldTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_groupingFieldTitle();
	}

	@Override
	public String multiGroupDialog_invalidListPrompt() {
		String val = getPropertyValue("multiGroupDialog_invalidListPrompt");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_invalidListPrompt();
	}

	@Override
	public String multiGroupDialog_levelDownPrompt() {
		String val = getPropertyValue("multiGroupDialog_levelDownPrompt");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_levelDownPrompt();
	}

	@Override
	public String multiGroupDialog_levelUpPrompt() {
		String val = getPropertyValue("multiGroupDialog_levelUpPrompt");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_levelUpPrompt();
	}

	@Override
	public String multiGroupDialog_propertyFieldTitle() {
		String val = getPropertyValue("multiGroupDialog_propertyFieldTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_propertyFieldTitle();
	}

	@Override
	public String multiGroupDialog_title() {
		String val = getPropertyValue("multiGroupDialog_title");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_title();
	}

	@Override
	public String multiGroupDialog_firstGroupLevelTitle() {
		String val = getPropertyValue("multiGroupDialog_firstGroupLevelTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_firstGroupLevelTitle();
	}

	@Override
	public String multiGroupDialog_otherGroupLevelTitle() {
		String val = getPropertyValue("multiGroupDialog_otherGroupLevelTitle");
		return val != null ? val : originalSgwtMessages.multiGroupDialog_otherGroupLevelTitle();
	}

	@Override
	public String operators_andTitle() {
		String val = getPropertyValue("operators_andTitle");
		return val != null ? val : originalSgwtMessages.operators_andTitle();
	}

	@Override
	public String operators_betweenInclusiveTitle() {
		String val = getPropertyValue("operators_betweenInclusiveTitle");
		return val != null ? val : originalSgwtMessages.operators_betweenInclusiveTitle();
	}

	@Override
	public String operators_betweenTitle() {
		String val = getPropertyValue("operators_betweenTitle");
		return val != null ? val : originalSgwtMessages.operators_betweenTitle();
	}

	@Override
	public String operators_iBetweenInclusiveTitle() {
		String val = getPropertyValue("operators_iBetweenInclusiveTitle");
		return val != null ? val : originalSgwtMessages.operators_iBetweenInclusiveTitle();
	}

	@Override
	public String operators_iBetweenTitle() {
		String val = getPropertyValue("operators_iBetweenTitle");
		return val != null ? val : originalSgwtMessages.operators_iBetweenTitle();
	}

	@Override
	public String operators_containsFieldTitle() {
		String val = getPropertyValue("operators_containsFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_containsFieldTitle();
	}

	@Override
	public String operators_containsTitle() {
		String val = getPropertyValue("operators_containsTitle");
		return val != null ? val : originalSgwtMessages.operators_containsTitle();
	}

	@Override
	public String operators_endsWithFieldTitle() {
		String val = getPropertyValue("operators_endsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_endsWithFieldTitle();
	}

	@Override
	public String operators_endsWithTitle() {
		String val = getPropertyValue("operators_endsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_endsWithTitle();
	}

	@Override
	public String operators_equalsFieldTitle() {
		String val = getPropertyValue("operators_equalsFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_equalsFieldTitle();
	}

	@Override
	public String operators_equalsTitle() {
		String val = getPropertyValue("operators_equalsTitle");
		return val != null ? val : originalSgwtMessages.operators_equalsTitle();
	}

	@Override
	public String operators_greaterOrEqualFieldTitle() {
		String val = getPropertyValue("operators_greaterOrEqualFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_greaterOrEqualFieldTitle();
	}

	@Override
	public String operators_greaterOrEqualTitle() {
		String val = getPropertyValue("operators_greaterOrEqualTitle");
		return val != null ? val : originalSgwtMessages.operators_greaterOrEqualTitle();
	}

	@Override
	public String operators_greaterThanFieldTitle() {
		String val = getPropertyValue("operators_greaterThanFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_greaterThanFieldTitle();
	}

	@Override
	public String operators_greaterThanTitle() {
		String val = getPropertyValue("operators_greaterThanTitle");
		return val != null ? val : originalSgwtMessages.operators_greaterThanTitle();
	}

	@Override
	public String operators_iContainsTitle() {
		String val = getPropertyValue("operators_iContainsTitle");
		return val != null ? val : originalSgwtMessages.operators_iContainsTitle();
	}

	@Override
	public String operators_iEndsWithTitle() {
		String val = getPropertyValue("operators_iEndsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_iEndsWithTitle();
	}

	@Override
	public String operators_iEqualsTitle() {
		String val = getPropertyValue("operators_iEqualsTitle");
		return val != null ? val : originalSgwtMessages.operators_iEqualsTitle();
	}

	@Override
	public String operators_iNotContainsTitle() {
		String val = getPropertyValue("operators_iNotContainsTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotContainsTitle();
	}

	@Override
	public String operators_iNotEndsWithTitle() {
		String val = getPropertyValue("operators_iNotEndsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotEndsWithTitle();
	}

	@Override
	public String operators_iNotEqualTitle() {
		String val = getPropertyValue("operators_iNotEqualTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotEqualTitle();
	}

	@Override
	public String operators_iNotStartsWithTitle() {
		String val = getPropertyValue("operators_iNotStartsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotStartsWithTitle();
	}

	@Override
	public String operators_iStartsWithTitle() {
		String val = getPropertyValue("operators_iStartsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_iStartsWithTitle();
	}

	@Override
	public String operators_inSetTitle() {
		String val = getPropertyValue("operators_inSetTitle");
		return val != null ? val : originalSgwtMessages.operators_inSetTitle();
	}

	@Override
	public String operators_iregexpTitle() {
		String val = getPropertyValue("operators_iregexpTitle");
		return val != null ? val : originalSgwtMessages.operators_iregexpTitle();
	}

	@Override
	public String operators_isNullTitle() {
		String val = getPropertyValue("operators_isNullTitle");
		return val != null ? val : originalSgwtMessages.operators_isNullTitle();
	}

	@Override
	public String operators_lessOrEqualFieldTitle() {
		String val = getPropertyValue("operators_lessOrEqualFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_lessOrEqualFieldTitle();
	}

	@Override
	public String operators_lessOrEqualTitle() {
		String val = getPropertyValue("operators_lessOrEqualTitle");
		return val != null ? val : originalSgwtMessages.operators_lessOrEqualTitle();
	}

	@Override
	public String operators_lessThanFieldTitle() {
		String val = getPropertyValue("operators_lessThanFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_lessThanFieldTitle();
	}

	@Override
	public String operators_lessThanTitle() {
		String val = getPropertyValue("operators_lessThanTitle");
		return val != null ? val : originalSgwtMessages.operators_lessThanTitle();
	}

	@Override
	public String operators_notContainsTitle() {
		String val = getPropertyValue("operators_notContainsTitle");
		return val != null ? val : originalSgwtMessages.operators_notContainsTitle();
	}

	@Override
	public String operators_notEndsWithTitle() {
		String val = getPropertyValue("operators_notEndsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_notEndsWithTitle();
	}

	@Override
	public String operators_notEqualFieldTitle() {
		String val = getPropertyValue("operators_notEqualFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_notEqualFieldTitle();
	}

	@Override
	public String operators_notEqualTitle() {
		String val = getPropertyValue("operators_notEqualTitle");
		return val != null ? val : originalSgwtMessages.operators_notEqualTitle();
	}

	@Override
	public String operators_notInSetTitle() {
		String val = getPropertyValue("operators_notInSetTitle");
		return val != null ? val : originalSgwtMessages.operators_notInSetTitle();
	}

	@Override
	public String operators_notNullTitle() {
		String val = getPropertyValue("operators_notNullTitle");
		return val != null ? val : originalSgwtMessages.operators_notNullTitle();
	}

	@Override
	public String operators_notStartsWithTitle() {
		String val = getPropertyValue("operators_notStartsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_notStartsWithTitle();
	}

	@Override
	public String operators_notTitle() {
		String val = getPropertyValue("operators_notTitle");
		return val != null ? val : originalSgwtMessages.operators_notTitle();
	}

	@Override
	public String operators_orTitle() {
		String val = getPropertyValue("operators_orTitle");
		return val != null ? val : originalSgwtMessages.operators_orTitle();
	}

	@Override
	public String operators_regexpTitle() {
		String val = getPropertyValue("operators_regexpTitle");
		return val != null ? val : originalSgwtMessages.operators_regexpTitle();
	}

	@Override
	public String operators_startsWithFieldTitle() {
		String val = getPropertyValue("operators_startsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_startsWithFieldTitle();
	}

	@Override
	public String operators_startsWithTitle() {
		String val = getPropertyValue("operators_startsWithTitle");
		return val != null ? val : originalSgwtMessages.operators_startsWithTitle();
	}

	@Override
	public String operators_iEqualsFieldTitle() {
		String val = getPropertyValue("operators_iEqualsFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iEqualsFieldTitle();
	}

	@Override
	public String operators_iNotEqualFieldTitle() {
		String val = getPropertyValue("operators_iNotEqualFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotEqualFieldTitle();
	}

	@Override
	public String operators_iContainsFieldTitle() {
		String val = getPropertyValue("operators_iContainsFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iContainsFieldTitle();
	}

	@Override
	public String operators_iStartsWithFieldTitle() {
		String val = getPropertyValue("operators_iStartsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iStartsWithFieldTitle();
	}

	@Override
	public String operators_iEndsWithFieldTitle() {
		String val = getPropertyValue("operators_iEndsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iEndsWithFieldTitle();
	}

	@Override
	public String operators_notContainsFieldTitle() {
		String val = getPropertyValue("operators_notContainsFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_notContainsFieldTitle();
	}

	@Override
	public String operators_notStartsWithFieldTitle() {
		String val = getPropertyValue("operators_notStartsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_notStartsWithFieldTitle();
	}

	@Override
	public String operators_notEndsWithFieldTitle() {
		String val = getPropertyValue("operators_notEndsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_notEndsWithFieldTitle();
	}

	@Override
	public String operators_iNotContainsFieldTitle() {
		String val = getPropertyValue("operators_iNotContainsFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotContainsFieldTitle();
	}

	@Override
	public String operators_iNotStartsWithFieldTitle() {
		String val = getPropertyValue("operators_iNotStartsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotStartsWithFieldTitle();
	}

	@Override
	public String operators_iNotEndsWithFieldTitle() {
		String val = getPropertyValue("operators_iNotEndsWithFieldTitle");
		return val != null ? val : originalSgwtMessages.operators_iNotEndsWithFieldTitle();
	}

	@Override
	public String menu_emptyMessage() {
		String val = getPropertyValue("menu_emptyMessage");
		return val != null ? val : originalSgwtMessages.menu_emptyMessage();
	}

	@Override
	public String pickListMenu_emptyMessage() {
		String val = getPropertyValue("pickListMenu_emptyMessage");
		return val != null ? val : originalSgwtMessages.pickListMenu_emptyMessage();
	}

	@Override
	public String pickTreeItem_emptyMenuMessage() {
		String val = getPropertyValue("pickTreeItem_emptyMenuMessage");
		return val != null ? val : originalSgwtMessages.pickTreeItem_emptyMenuMessage();
	}

	@Override
	public String portlet_closeConfirmationMessage() {
		String val = getPropertyValue("portlet_closeConfirmationMessage");
		return val != null ? val : originalSgwtMessages.portlet_closeConfirmationMessage();
	}

	@Override
	public String printWindow_printButtonTitle() {
		String val = getPropertyValue("printWindow_printButtonTitle");
		return val != null ? val : originalSgwtMessages.printWindow_printButtonTitle();
	}

	@Override
	public String printWindow_title() {
		String val = getPropertyValue("printWindow_title");
		return val != null ? val : originalSgwtMessages.printWindow_title();
	}

	@Override
	public String relativeDateItem_daysAgoTitle() {
		String val = getPropertyValue("relativeDateItem_daysAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_daysAgoTitle();
	}

	@Override
	public String relativeDateItem_daysFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_daysFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_daysFromNowTitle();
	}

	@Override
	public String relativeDateItem_hoursAgoTitle() {
		String val = getPropertyValue("relativeDateItem_hoursAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_hoursAgoTitle();
	}

	@Override
	public String relativeDateItem_hoursFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_hoursFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_hoursFromNowTitle();
	}

	@Override
	public String relativeDateItem_millisecondsAgoTitle() {
		String val = getPropertyValue("relativeDateItem_millisecondsAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_millisecondsAgoTitle();
	}

	@Override
	public String relativeDateItem_millisecondsFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_millisecondsFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_millisecondsFromNowTitle();
	}

	@Override
	public String relativeDateItem_minutesAgoTitle() {
		String val = getPropertyValue("relativeDateItem_minutesAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_minutesAgoTitle();
	}

	@Override
	public String relativeDateItem_minutesFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_minutesFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_minutesFromNowTitle();
	}

	@Override
	public String relativeDateItem_monthsAgoTitle() {
		String val = getPropertyValue("relativeDateItem_monthsAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_monthsAgoTitle();
	}

	@Override
	public String relativeDateItem_monthsFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_monthsFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_monthsFromNowTitle();
	}

	@Override
	public String relativeDateItem_quartersAgoTitle() {
		String val = getPropertyValue("relativeDateItem_quartersAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_quartersAgoTitle();
	}

	@Override
	public String relativeDateItem_quartersFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_quartersFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_quartersFromNowTitle();
	}

	@Override
	public String relativeDateItem_pickerIconPrompt() {
		String val = getPropertyValue("relativeDateItem_pickerIconPrompt");
		return val != null ? val : originalSgwtMessages.relativeDateItem_pickerIconPrompt();
	}

	@Override
	public String relativeDateItem_presetOptions_minus_1m() {
		String val = getPropertyValue("relativeDateItem_presetOptions_minus_1m");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_minus_1m();
	}

	@Override
	public String relativeDateItem_presetOptions_minus_1w() {
		String val = getPropertyValue("relativeDateItem_presetOptions_minus_1w");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_minus_1w();
	}

	@Override
	public String relativeDateItem_presetOptions_plus_1m() {
		String val = getPropertyValue("relativeDateItem_presetOptions_plus_1m");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_plus_1m();
	}

	@Override
	public String relativeDateItem_presetOptions_plus_1w() {
		String val = getPropertyValue("relativeDateItem_presetOptions_plus_1w");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_plus_1w();
	}

	@Override
	public String relativeDateItem_presetOptions_today() {
		String val = getPropertyValue("relativeDateItem_presetOptions_today");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_today();
	}

	@Override
	public String relativeDateItem_presetOptions_tomorrow() {
		String val = getPropertyValue("relativeDateItem_presetOptions_tomorrow");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_tomorrow();
	}

	@Override
	public String relativeDateItem_presetOptions_yesterday() {
		String val = getPropertyValue("relativeDateItem_presetOptions_yesterday");
		return val != null ? val : originalSgwtMessages.relativeDateItem_presetOptions_yesterday();
	}

	@Override
	public String relativeDateItem_secondsAgoTitle() {
		String val = getPropertyValue("relativeDateItem_secondsAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_secondsAgoTitle();
	}

	@Override
	public String relativeDateItem_secondsFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_secondsFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_secondsFromNowTitle();
	}

	@Override
	public String relativeDateItem_todayTitle() {
		String val = getPropertyValue("relativeDateItem_todayTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_todayTitle();
	}

	@Override
	public String relativeDateItem_weeksAgoTitle() {
		String val = getPropertyValue("relativeDateItem_weeksAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_weeksAgoTitle();
	}

	@Override
	public String relativeDateItem_weeksFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_weeksFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_weeksFromNowTitle();
	}

	@Override
	public String relativeDateItem_yearsAgoTitle() {
		String val = getPropertyValue("relativeDateItem_yearsAgoTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_yearsAgoTitle();
	}

	@Override
	public String relativeDateItem_yearsFromNowTitle() {
		String val = getPropertyValue("relativeDateItem_yearsFromNowTitle");
		return val != null ? val : originalSgwtMessages.relativeDateItem_yearsFromNowTitle();
	}

	@Override
	public String richTextEditor_alignCenterPrompt() {
		String val = getPropertyValue("richTextEditor_alignCenterPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_alignCenterPrompt();
	}

	@Override
	public String richTextEditor_alignLeftPrompt() {
		String val = getPropertyValue("richTextEditor_alignLeftPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_alignLeftPrompt();
	}

	@Override
	public String richTextEditor_alignRightPrompt() {
		String val = getPropertyValue("richTextEditor_alignRightPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_alignRightPrompt();
	}

	@Override
	public String richTextEditor_backgroundColorPrompt() {
		String val = getPropertyValue("richTextEditor_backgroundColorPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_backgroundColorPrompt();
	}

	@Override
	public String richTextEditor_boldSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_boldSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_boldSelectionPrompt();
	}

	@Override
	public String richTextEditor_colorPrompt() {
		String val = getPropertyValue("richTextEditor_colorPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_colorPrompt();
	}

	@Override
	public String richTextEditor_copySelectionPrompt() {
		String val = getPropertyValue("richTextEditor_copySelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_copySelectionPrompt();
	}

	@Override
	public String richTextEditor_cutSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_cutSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_cutSelectionPrompt();
	}

	@Override
	public String richTextEditor_fontPrompt() {
		String val = getPropertyValue("richTextEditor_fontPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_fontPrompt();
	}

	@Override
	public String richTextEditor_fontSizePrompt() {
		String val = getPropertyValue("richTextEditor_fontSizePrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_fontSizePrompt();
	}

	@Override
	public String richTextEditor_indentSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_indentSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_indentSelectionPrompt();
	}

	@Override
	public String richTextEditor_italicSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_italicSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_italicSelectionPrompt();
	}

	@Override
	public String richTextEditor_justifyPrompt() {
		String val = getPropertyValue("richTextEditor_justifyPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_justifyPrompt();
	}

	@Override
	public String richTextEditor_linkPrompt() {
		String val = getPropertyValue("richTextEditor_linkPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_linkPrompt();
	}

	@Override
	public String richTextEditor_linkUrlTitle() {
		String val = getPropertyValue("richTextEditor_linkUrlTitle");
		return val != null ? val : originalSgwtMessages.richTextEditor_linkUrlTitle();
	}

	@Override
	public String richTextEditor_outdentSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_outdentSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_outdentSelectionPrompt();
	}

	@Override
	public String richTextEditor_pasteSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_pasteSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_pasteSelectionPrompt();
	}

	@Override
	public String richTextEditor_underlineSelectionPrompt() {
		String val = getPropertyValue("richTextEditor_underlineSelectionPrompt");
		return val != null ? val : originalSgwtMessages.richTextEditor_underlineSelectionPrompt();
	}

	@Override
	public String rpcManager_defaultPrompt() {
		String val = getPropertyValue("rpcManager_defaultPrompt");
		return val != null ? val : originalSgwtMessages.rpcManager_defaultPrompt();
	}

	@Override
	public String rpcManager_fetchDataPrompt() {
		String val = getPropertyValue("rpcManager_fetchDataPrompt");
		return val != null ? val : originalSgwtMessages.rpcManager_fetchDataPrompt();
	}

	@Override
	public String rpcManager_removeDataPrompt() {
		String val = getPropertyValue("rpcManager_removeDataPrompt");
		return val != null ? val : originalSgwtMessages.rpcManager_removeDataPrompt();
	}

	@Override
	public String rpcManager_saveDataPrompt() {
		String val = getPropertyValue("rpcManager_saveDataPrompt");
		return val != null ? val : originalSgwtMessages.rpcManager_saveDataPrompt();
	}

	@Override
	public String rpcManager_timeoutErrorMessage() {
		String val = getPropertyValue("rpcManager_timeoutErrorMessage");
		return val != null ? val : originalSgwtMessages.rpcManager_timeoutErrorMessage();
	}

	@Override
	public String rpcManager_validateDataPrompt() {
		String val = getPropertyValue("rpcManager_validateDataPrompt");
		return val != null ? val : originalSgwtMessages.rpcManager_validateDataPrompt();
	}

	@Override
	public String selectOtherItem_otherTitle() {
		String val = getPropertyValue("selectOtherItem_otherTitle");
		return val != null ? val : originalSgwtMessages.selectOtherItem_otherTitle();
	}

	@Override
	public String selectOtherItem_selectOtherPrompt() {
		String val = getPropertyValue("selectOtherItem_selectOtherPrompt");
		return val != null ? val : originalSgwtMessages.selectOtherItem_selectOtherPrompt();
	}

	@Override
	public String selection_selectionRangeNotLoadedMessage() {
		String val = getPropertyValue("selection_selectionRangeNotLoadedMessage");
		return val != null ? val : originalSgwtMessages.selection_selectionRangeNotLoadedMessage();
	}

	@Override
	public String summaryBuilder_autoHideCheckBoxLabel() {
		String val = getPropertyValue("summaryBuilder_autoHideCheckBoxLabel");
		return val != null ? val : originalSgwtMessages.summaryBuilder_autoHideCheckBoxLabel();
	}

	@Override
	public String summaryBuilder_builderTypeText() {
		String val = getPropertyValue("summaryBuilder_builderTypeText");
		return val != null ? val : originalSgwtMessages.summaryBuilder_builderTypeText();
	}

	@Override
	public String summaryBuilder_helpTextIntro() {
		String val = getPropertyValue("summaryBuilder_helpTextIntro");
		return val != null ? val : originalSgwtMessages.summaryBuilder_helpTextIntro();
	}

	@Override
	public String time_AMIndicator() {
		return originalSgwtMessages.time_AMIndicator();
	}

	@Override
	public String time_PMIndicator() {
		return originalSgwtMessages.time_PMIndicator();
	}

	@Override
	public String treeGrid_cantDragIntoChildMessage() {
		String val = getPropertyValue("treeGrid_cantDragIntoChildMessage");
		return val != null ? val : originalSgwtMessages.treeGrid_cantDragIntoChildMessage();
	}

	@Override
	public String treeGrid_cantDragIntoSelfMessage() {
		String val = getPropertyValue("treeGrid_cantDragIntoSelfMessage");
		return val != null ? val : originalSgwtMessages.treeGrid_cantDragIntoSelfMessage();
	}

	@Override
	public String treeGrid_parentAlreadyContainsChildMessage() {
		String val = getPropertyValue("treeGrid_parentAlreadyContainsChildMessage");
		return val != null ? val : originalSgwtMessages.treeGrid_parentAlreadyContainsChildMessage();
	}

	@Override
	public String treeGrid_offlineNodeMessage() {
		String val = getPropertyValue("treeGrid_offlineNodeMessage");
		return val != null ? val : originalSgwtMessages.treeGrid_offlineNodeMessage();
	}

	@Override
	public String columnTree_backButtonTitle() {
		String val = getPropertyValue("columnTree_backButtonTitle");
		return val != null ? val : originalSgwtMessages.columnTree_backButtonTitle();
	}

	@Override
	public String validator_mustBeEarlierThan() {
		String val = getPropertyValue("validator_mustBeEarlierThan");
		return val != null ? val : originalSgwtMessages.validator_mustBeEarlierThan();
	}

	@Override
	public String validator_mustBeExactLength() {
		String val = getPropertyValue("validator_mustBeExactLength");
		return val != null ? val : originalSgwtMessages.validator_mustBeExactLength();
	}

	@Override
	public String validator_mustBeGreaterThan() {
		String val = getPropertyValue("validator_mustBeGreaterThan");
		return val != null ? val : originalSgwtMessages.validator_mustBeGreaterThan();
	}

	@Override
	public String validator_mustBeLaterThan() {
		String val = getPropertyValue("validator_mustBeLaterThan");
		return val != null ? val : originalSgwtMessages.validator_mustBeLaterThan();
	}

	@Override
	public String validator_mustBeLessThan() {
		String val = getPropertyValue("validator_mustBeLessThan");
		return val != null ? val : originalSgwtMessages.validator_mustBeLessThan();
	}

	@Override
	public String validator_mustBeLongerThan() {
		String val = getPropertyValue("validator_mustBeLongerThan");
		return val != null ? val : originalSgwtMessages.validator_mustBeLongerThan();
	}

	@Override
	public String validator_mustBeShorterThan() {
		String val = getPropertyValue("validator_mustBeShorterThan");
		return val != null ? val : originalSgwtMessages.validator_mustBeShorterThan();
	}

	@Override
	public String validator_notABoolean() {
		String val = getPropertyValue("validator_notABoolean");
		return val != null ? val : originalSgwtMessages.validator_notABoolean();
	}

	@Override
	public String validator_notAColor() {
		String val = getPropertyValue("validator_notAColor");
		return val != null ? val : originalSgwtMessages.validator_notAColor();
	}

	@Override
	public String validator_notADate() {
		String val = getPropertyValue("validator_notADate");
		return val != null ? val : originalSgwtMessages.validator_notADate();
	}

	@Override
	public String validator_notADecimal() {
		String val = getPropertyValue("validator_notADecimal");
		return val != null ? val : originalSgwtMessages.validator_notADecimal();
	}

	@Override
	public String validator_notAFunction() {
		String val = getPropertyValue("validator_notAFunction");
		return val != null ? val : originalSgwtMessages.validator_notAFunction();
	}

	@Override
	public String validator_notARegex() {
		String val = getPropertyValue("validator_notARegex");
		return val != null ? val : originalSgwtMessages.validator_notARegex();
	}

	@Override
	public String validator_notAString() {
		String val = getPropertyValue("validator_notAString");
		return val != null ? val : originalSgwtMessages.validator_notAString();
	}

	@Override
	public String validator_notATime() {
		String val = getPropertyValue("validator_notATime");
		return val != null ? val : originalSgwtMessages.validator_notATime();
	}

	@Override
	public String validator_notAnIdentifier() {
		String val = getPropertyValue("validator_notAnIdentifier");
		return val != null ? val : originalSgwtMessages.validator_notAnIdentifier();
	}

	@Override
	public String validator_notAnInteger() {
		String val = getPropertyValue("validator_notAnInteger");
		return val != null ? val : originalSgwtMessages.validator_notAnInteger();
	}

	@Override
	public String validator_notOneOf() {
		String val = getPropertyValue("validator_notOneOf");
		return val != null ? val : originalSgwtMessages.validator_notOneOf();
	}

	@Override
	public String validator_requiredField() {
		String val = getPropertyValue("validator_requiredField");
		return val != null ? val : originalSgwtMessages.validator_requiredField();
	}

	@Override
	public String validator_mustBeLaterThanTime() {
		String val = getPropertyValue("validator_mustBeLaterThanTime");
		return val != null ? val : originalSgwtMessages.validator_mustBeLaterThanTime();
	}

	@Override
	public String validator_mustBeEarlierThanTime() {
		String val = getPropertyValue("validator_mustBeEarlierThanTime");
		return val != null ? val : originalSgwtMessages.validator_mustBeEarlierThanTime();
	}

	@Override
	public String window_title() {
		String val = getPropertyValue("window_title");
		return val != null ? val : originalSgwtMessages.window_title();
	}

	public Map<String, String> getMessages() {
		return customizedSgwtMessages;
	}
}
