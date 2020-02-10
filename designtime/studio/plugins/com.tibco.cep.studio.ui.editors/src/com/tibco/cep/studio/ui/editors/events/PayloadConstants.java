/**
 * 
 */
package com.tibco.cep.studio.ui.editors.events;

import java.util.ResourceBundle;

/**
 * @author mgujrath
 *
 */
public class PayloadConstants {
	
	private static final String BUNDLE_NAME = "com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.Resources"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private static String contxtComboItems[] = {
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.complexelement.label"),
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.elementoftype.label"),
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.elementreference.label"),
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.attributeoftype.label"),
		RESOURCE_BUNDLE
				 .getString("ae.parametereditor.choice.label"),
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.groupreference.label") ,
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.anyelement.label")};

	public static String[] getContxtComboItems() {
		return contxtComboItems;
	}
	
	private static String cardinalityComboItems[] = {
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.required.label"),
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.optional.label")+"(?)",
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.atleastone.label")+"(*)",
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.repeating.label")+"(+)"};

	public static String[] getCardinalityComboItems() {
		return cardinalityComboItems;
	}

	
	
	
	private static String typeComboItems[] = {
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.string.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.integer.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.decimal.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.boolean.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.genericdatetime.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.genericbinary.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.anyuri.label"),
		RESOURCE_BUNDLE
				.getString("ae.xsdtypes.anytype.label"),
		RESOURCE_BUNDLE
				.getString("ae.parametereditor.anytype.label")
			};

	public static String[] getTypeComboItems() {
		return typeComboItems;
	}


}
