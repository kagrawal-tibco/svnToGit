/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page Set Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete subclass of Visualization to handle pageset selection row in the pageset selector.
 * 			Contains one/tow ColumnSeriesConfig
 * 				if one then the ColumnSeriesConfig contains two valueformatters which give the text and indicator
 * 				if two then the ColumnSeriesConfig need to map to each other via the CategoryFormatter.
 * 					One ColumnSeriesConfig contains indicator and the other text
 * 			Contains reference to a PageSet
 * 			
 * <!-- end-model-doc -->
 *
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPageSetVisualization()
 * @model extendedMetaData="name='PageSetVisualization' kind='elementOnly'"
 * @generated
 */
public interface PageSetVisualization extends TextVisualization {
} // PageSetVisualization
