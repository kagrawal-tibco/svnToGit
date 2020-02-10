/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.model.element.PropertyDefinition;


/**
 * This is the item provider adapter for a {@link com.tibco.cep.decisionproject.ontology.Property} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertyItemProvider
	extends AbstractResourceItemProvider
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addDataTypePropertyDescriptor(object);
			addHistorySizePropertyDescriptor(object);
			addHistoryPolicyPropertyDescriptor(object);
			addMultiplePropertyDescriptor(object);
			addDefaultValuePropertyDescriptor(object);
			addPropertyResourceTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Data Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDataTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Property_dataType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Property_dataType_feature", "_UI_Property_type"),
				 OntologyPackage.Literals.PROPERTY__DATA_TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the History Size feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHistorySizePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Property_historySize_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Property_historySize_feature", "_UI_Property_type"),
				 OntologyPackage.Literals.PROPERTY__HISTORY_SIZE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the History Policy feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHistoryPolicyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Property_historyPolicy_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Property_historyPolicy_feature", "_UI_Property_type"),
				 OntologyPackage.Literals.PROPERTY__HISTORY_POLICY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Multiple feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMultiplePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Property_multiple_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Property_multiple_feature", "_UI_Property_type"),
				 OntologyPackage.Literals.PROPERTY__MULTIPLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Default Value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefaultValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Property_defaultValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Property_defaultValue_feature", "_UI_Property_type"),
				 OntologyPackage.Literals.PROPERTY__DEFAULT_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Property Resource Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPropertyResourceTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Property_propertyResourceType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Property_propertyResourceType_feature", "_UI_Property_type"),
				 OntologyPackage.Literals.PROPERTY__PROPERTY_RESOURCE_TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns Property.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {		
		Property property = (Property)object;
		
		int dataType = property.getDataType();		
		return getImage(dataType,object);
		//return overlayImage(object, getResourceLocator().getImage("full/obj16/Property"));
	}

	/**
	 * This returns Property.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	private Object getImage(int type , Object object) {
		switch (type) {
			case PropertyDefinition.PROPERTY_TYPE_INTEGER:
				return overlayImage(object, getResourceLocator().getImage("property/iconInteger16"));
			case PropertyDefinition.PROPERTY_TYPE_REAL:
				return overlayImage(object, getResourceLocator().getImage("property/iconReal16"));
			case PropertyDefinition.PROPERTY_TYPE_LONG:
				return overlayImage(object, getResourceLocator().getImage("property/iconLong16"));
			case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
				return overlayImage(object, getResourceLocator().getImage("property/iconBoolean16"));
			case PropertyDefinition.PROPERTY_TYPE_DATETIME:
				return overlayImage(object, getResourceLocator().getImage("property/iconDate16"));
			case PropertyDefinition.PROPERTY_TYPE_STRING:
				return overlayImage(object, getResourceLocator().getImage("property/iconString16"));
			case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
				return overlayImage(object, getResourceLocator().getImage("property/iconConcept16"));
			case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
				return overlayImage(object, getResourceLocator().getImage("property/iconConceptRef16"));
			default:
				return overlayImage(object, getResourceLocator().getImage("full/obj16/Default"));
		}
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public String getText(Object object) {
		String label = ((Property)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Property_type") :label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Property.class)) {
			case OntologyPackage.PROPERTY__DATA_TYPE:
			case OntologyPackage.PROPERTY__HISTORY_SIZE:
			case OntologyPackage.PROPERTY__HISTORY_POLICY:
			case OntologyPackage.PROPERTY__MULTIPLE:
			case OntologyPackage.PROPERTY__DEFAULT_VALUE:
			case OntologyPackage.PROPERTY__PROPERTY_RESOURCE_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
