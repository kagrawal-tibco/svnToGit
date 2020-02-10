/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitleRef <em>Title Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltipRef <em>Tooltip Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIconRef <em>Icon Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitle <em>Title</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltip <em>Tooltip</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getPaletteItem <em>Palette Item</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getPaletteToolSet <em>Palette Tool Set</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getColor <em>Color</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#isInternal <em>Internal</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteGroup#isVisible <em>Visible</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup()
 * @model extendedMetaData="name='paletteGroup' kind='elementOnly'"
 * @generated
 */
public interface PaletteGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Title Ref</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title Ref</em>' attribute.
	 * @see #isSetTitleRef()
	 * @see #unsetTitleRef()
	 * @see #setTitleRef(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_TitleRef()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='titleRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTitleRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitleRef <em>Title Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title Ref</em>' attribute.
	 * @see #isSetTitleRef()
	 * @see #unsetTitleRef()
	 * @see #getTitleRef()
	 * @generated
	 */
	void setTitleRef(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitleRef <em>Title Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTitleRef()
	 * @see #getTitleRef()
	 * @see #setTitleRef(String)
	 * @generated
	 */
	void unsetTitleRef();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitleRef <em>Title Ref</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Title Ref</em>' attribute is set.
	 * @see #unsetTitleRef()
	 * @see #getTitleRef()
	 * @see #setTitleRef(String)
	 * @generated
	 */
	boolean isSetTitleRef();

	/**
	 * Returns the value of the '<em><b>Tooltip Ref</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tooltip Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tooltip Ref</em>' attribute.
	 * @see #isSetTooltipRef()
	 * @see #unsetTooltipRef()
	 * @see #setTooltipRef(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_TooltipRef()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='tooltipRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTooltipRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltipRef <em>Tooltip Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tooltip Ref</em>' attribute.
	 * @see #isSetTooltipRef()
	 * @see #unsetTooltipRef()
	 * @see #getTooltipRef()
	 * @generated
	 */
	void setTooltipRef(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltipRef <em>Tooltip Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTooltipRef()
	 * @see #getTooltipRef()
	 * @see #setTooltipRef(String)
	 * @generated
	 */
	void unsetTooltipRef();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltipRef <em>Tooltip Ref</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Tooltip Ref</em>' attribute is set.
	 * @see #unsetTooltipRef()
	 * @see #getTooltipRef()
	 * @see #setTooltipRef(String)
	 * @generated
	 */
	boolean isSetTooltipRef();

	/**
	 * Returns the value of the '<em><b>Icon Ref</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Icon Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon Ref</em>' attribute.
	 * @see #isSetIconRef()
	 * @see #unsetIconRef()
	 * @see #setIconRef(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_IconRef()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='iconRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getIconRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIconRef <em>Icon Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon Ref</em>' attribute.
	 * @see #isSetIconRef()
	 * @see #unsetIconRef()
	 * @see #getIconRef()
	 * @generated
	 */
	void setIconRef(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIconRef <em>Icon Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIconRef()
	 * @see #getIconRef()
	 * @see #setIconRef(String)
	 * @generated
	 */
	void unsetIconRef();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIconRef <em>Icon Ref</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Icon Ref</em>' attribute is set.
	 * @see #unsetIconRef()
	 * @see #getIconRef()
	 * @see #setIconRef(String)
	 * @generated
	 */
	boolean isSetIconRef();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #isSetTitle()
	 * @see #unsetTitle()
	 * @see #setTitle(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Title()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='title' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #isSetTitle()
	 * @see #unsetTitle()
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTitle()
	 * @see #getTitle()
	 * @see #setTitle(String)
	 * @generated
	 */
	void unsetTitle();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitle <em>Title</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Title</em>' attribute is set.
	 * @see #unsetTitle()
	 * @see #getTitle()
	 * @see #setTitle(String)
	 * @generated
	 */
	boolean isSetTitle();

	/**
	 * Returns the value of the '<em><b>Tooltip</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tooltip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tooltip</em>' attribute.
	 * @see #isSetTooltip()
	 * @see #unsetTooltip()
	 * @see #setTooltip(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Tooltip()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='tooltip' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTooltip();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltip <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tooltip</em>' attribute.
	 * @see #isSetTooltip()
	 * @see #unsetTooltip()
	 * @see #getTooltip()
	 * @generated
	 */
	void setTooltip(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltip <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTooltip()
	 * @see #getTooltip()
	 * @see #setTooltip(String)
	 * @generated
	 */
	void unsetTooltip();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltip <em>Tooltip</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Tooltip</em>' attribute is set.
	 * @see #unsetTooltip()
	 * @see #getTooltip()
	 * @see #setTooltip(String)
	 * @generated
	 */
	boolean isSetTooltip();

	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Icon</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #isSetIcon()
	 * @see #unsetIcon()
	 * @see #setIcon(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Icon()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='icon' namespace='##targetNamespace'"
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #isSetIcon()
	 * @see #unsetIcon()
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIcon()
	 * @see #getIcon()
	 * @see #setIcon(String)
	 * @generated
	 */
	void unsetIcon();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIcon <em>Icon</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Icon</em>' attribute is set.
	 * @see #unsetIcon()
	 * @see #getIcon()
	 * @see #setIcon(String)
	 * @generated
	 */
	boolean isSetIcon();

	/**
	 * Returns the value of the '<em><b>Palette Item</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.palette.PaletteItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Item</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette Item</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_PaletteItem()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='paletteItem' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PaletteItem> getPaletteItem();

	/**
	 * Returns the value of the '<em><b>Palette Tool Set</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.palette.PaletteGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Tool Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette Tool Set</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_PaletteToolSet()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='paletteToolSet' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PaletteGroup> getPaletteToolSet();

	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Color()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='color'"
	 * @generated
	 */
	String getColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Id()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Internal</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Internal</em>' attribute.
	 * @see #isSetInternal()
	 * @see #unsetInternal()
	 * @see #setInternal(boolean)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Internal()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='internal'"
	 * @generated
	 */
	boolean isInternal();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isInternal <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Internal</em>' attribute.
	 * @see #isSetInternal()
	 * @see #unsetInternal()
	 * @see #isInternal()
	 * @generated
	 */
	void setInternal(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isInternal <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInternal()
	 * @see #isInternal()
	 * @see #setInternal(boolean)
	 * @generated
	 */
	void unsetInternal();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isInternal <em>Internal</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Internal</em>' attribute is set.
	 * @see #unsetInternal()
	 * @see #isInternal()
	 * @see #setInternal(boolean)
	 * @generated
	 */
	boolean isSetInternal();

	/**
	 * Returns the value of the '<em><b>Visible</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visible</em>' attribute.
	 * @see #isSetVisible()
	 * @see #unsetVisible()
	 * @see #setVisible(boolean)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteGroup_Visible()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='visible'"
	 * @generated
	 */
	boolean isVisible();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isVisible <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visible</em>' attribute.
	 * @see #isSetVisible()
	 * @see #unsetVisible()
	 * @see #isVisible()
	 * @generated
	 */
	void setVisible(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isVisible <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVisible()
	 * @see #isVisible()
	 * @see #setVisible(boolean)
	 * @generated
	 */
	void unsetVisible();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isVisible <em>Visible</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Visible</em>' attribute is set.
	 * @see #unsetVisible()
	 * @see #isVisible()
	 * @see #setVisible(boolean)
	 * @generated
	 */
	boolean isSetVisible();

} // PaletteGroup
