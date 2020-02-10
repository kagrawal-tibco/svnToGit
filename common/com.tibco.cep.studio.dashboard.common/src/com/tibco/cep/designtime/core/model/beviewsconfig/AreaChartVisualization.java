/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Area Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The extension of chart visualization to handle generation of area charts
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization#getFillOpacity <em>Fill Opacity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAreaChartVisualization()
 * @model extendedMetaData="name='AreaChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface AreaChartVisualization extends LineChartVisualization {
	/**
	 * Returns the value of the '<em><b>Fill Opacity</b></em>' attribute.
	 * The default value is <code>"50"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fill Opacity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fill Opacity</em>' attribute.
	 * @see #isSetFillOpacity()
	 * @see #unsetFillOpacity()
	 * @see #setFillOpacity(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAreaChartVisualization_FillOpacity()
	 * @model default="50" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='fillOpacity'"
	 * @generated
	 */
	int getFillOpacity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization#getFillOpacity <em>Fill Opacity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fill Opacity</em>' attribute.
	 * @see #isSetFillOpacity()
	 * @see #unsetFillOpacity()
	 * @see #getFillOpacity()
	 * @generated
	 */
	void setFillOpacity(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization#getFillOpacity <em>Fill Opacity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFillOpacity()
	 * @see #getFillOpacity()
	 * @see #setFillOpacity(int)
	 * @generated
	 */
	void unsetFillOpacity();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AreaChartVisualization#getFillOpacity <em>Fill Opacity</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Fill Opacity</em>' attribute is set.
	 * @see #unsetFillOpacity()
	 * @see #getFillOpacity()
	 * @see #setFillOpacity(int)
	 * @generated
	 */
	boolean isSetFillOpacity();

} // AreaChartVisualization
