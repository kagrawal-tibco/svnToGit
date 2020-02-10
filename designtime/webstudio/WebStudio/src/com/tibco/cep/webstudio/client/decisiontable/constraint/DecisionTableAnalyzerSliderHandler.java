package com.tibco.cep.webstudio.client.decisiontable.constraint;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.EditorExitEvent;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableAnalyzerSliderHandler implements com.smartgwt.client.widgets.form.fields.events.ChangeHandler, com.smartgwt.client.widgets.form.fields.events.EditorExitHandler {

	private SliderItem minSliderItem, maxSliderItem;
	private TextItem minTextItem, maxTextItem;
	private boolean showError = false;
	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private float minV;
	private float maxV;
	
	/**
	 * @param minSliderItem
	 * @param maxSliderItem
	 * @param maxTextItem 
	 * @param minTextItem 
	 * @param g 
	 * @param  
	 */
	public DecisionTableAnalyzerSliderHandler(SliderItem minSliderItem, 
			                           SliderItem maxSliderItem, TextItem minTextItem, TextItem maxTextItem, float minV, float maxV ) {
		this.minSliderItem = minSliderItem;
		this.maxSliderItem = maxSliderItem;
		this.minTextItem = minTextItem;
		this.maxTextItem = maxTextItem;
		this.minV = minV;
		this.maxV = maxV;
	}

	@Override
	public void onChange(final ChangeEvent event) {
		if(event.getSource() == minSliderItem || event.getSource() == maxSliderItem){
			int value = (Integer)event.getValue();
			int min = (Integer)minSliderItem.getValue();
			int max = (Integer)maxSliderItem.getValue();
			if (event.getSource() == minSliderItem) {
				minTextItem.setValue(String.valueOf(value));
				if (value > max) {
					maxSliderItem.setValue(value);
					maxTextItem.setValue(String.valueOf(value));

				} 
			} else if (event.getSource() == maxSliderItem) {
				maxTextItem.setValue(String.valueOf(value));
				if (value < min) {
					minSliderItem.setValue(value);
					minTextItem.setValue(String.valueOf(value));

				} 
			} 
		} 
	}

	@Override
	public void onEditorExit(EditorExitEvent event) {

		if (event.getSource() == minTextItem){

			if(Float.parseFloat(event.getValue().toString()) >= minV && Float.parseFloat(event.getValue().toString())<=maxV){
				minSliderItem.setValue(Float.parseFloat(event.getValue().toString()));
			}else{
				minTextItem.setValue(minSliderItem.getValue().toString());
			}

		} else if (event.getSource() == maxTextItem){

			if(Float.parseFloat(event.getValue().toString()) <= maxV && Float.parseFloat(event.getValue().toString()) >= minV){
				maxSliderItem.setValue(Float.parseFloat(event.getValue().toString()));
			}else{
				maxTextItem.setValue(maxSliderItem.getValue());
			}

		}


	}
	
}
