/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plot Area Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getBackground <em>Background</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getForeground <em>Foreground</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPlotAreaFormat()
 * @model extendedMetaData="name='PlotAreaFormat' kind='elementOnly'"
 * @generated
 */
public interface PlotAreaFormat extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Background</em>' containment reference.
	 * @see #setBackground(BackgroundFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPlotAreaFormat_Background()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='background'"
	 * @generated
	 */
	BackgroundFormat getBackground();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getBackground <em>Background</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Background</em>' containment reference.
	 * @see #getBackground()
	 * @generated
	 */
	void setBackground(BackgroundFormat value);

	/**
	 * Returns the value of the '<em><b>Foreground</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		                    Decides the pattern to be used as background in the plot area. This is an optional element. Absence of this indicates plain plotting area background.
	 * 		                    
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Foreground</em>' containment reference.
	 * @see #setForeground(ForegroundFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPlotAreaFormat_Foreground()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='foreground'"
	 * @generated
	 */
	ForegroundFormat getForeground();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat#getForeground <em>Foreground</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Foreground</em>' containment reference.
	 * @see #getForeground()
	 * @generated
	 */
	void setForeground(ForegroundFormat value);

} // PlotAreaFormat
