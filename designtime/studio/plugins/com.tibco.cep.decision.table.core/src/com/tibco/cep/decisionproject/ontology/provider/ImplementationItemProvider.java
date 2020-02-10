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

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.cep.decisionproject.ontology.Implementation} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ImplementationItemProvider extends AbstractResourceItemProvider
		implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ImplementationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addStylePropertyDescriptor(object);
			addImplementsPropertyDescriptor(object);
			addVersionPropertyDescriptor(object);
			addLastModifiedByPropertyDescriptor(object);
			addLastModifiedPropertyDescriptor(object);
			addDirtyPropertyDescriptor(object);
			addLockedPropertyDescriptor(object);
			addShowDescriptionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Style feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addStylePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_style_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_style_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__STYLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Implements feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addImplementsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_implements_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_implements_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__IMPLEMENTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Version feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_version_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_version_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.REAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Last Modified By feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLastModifiedByPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_lastModifiedBy_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_lastModifiedBy_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__LAST_MODIFIED_BY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Last Modified feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLastModifiedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_lastModified_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_lastModified_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__LAST_MODIFIED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Dirty feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDirtyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_dirty_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_dirty_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__DIRTY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Locked feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLockedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_locked_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_locked_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__LOCKED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Show Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addShowDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Implementation_showDescription_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Implementation_showDescription_feature", "_UI_Implementation_type"),
				 OntologyPackage.Literals.IMPLEMENTATION__SHOW_DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	@Override
	public String getText(Object object) {
		String label = ((Implementation) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Implementation_type")
				: label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Implementation.class)) {
			case OntologyPackage.IMPLEMENTATION__STYLE:
			case OntologyPackage.IMPLEMENTATION__IMPLEMENTS:
			case OntologyPackage.IMPLEMENTATION__VERSION:
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY:
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED:
			case OntologyPackage.IMPLEMENTATION__DIRTY:
			case OntologyPackage.IMPLEMENTATION__LOCKED:
			case OntologyPackage.IMPLEMENTATION__SHOW_DESCRIPTION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * This returns RuleFunction.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {
		if (object instanceof Table) {
			Table table = (Table) object;
			if (table.isDeployed() && !table.isLocked()) {
				return overlayImage(object, getResourceLocator().getImage(
						"full/obj16/deployed_dt16x16"));
			}
			if (table.isDeployed() && table.isLocked()) {
				return overlayImage(object, getResourceLocator().getImage(
						"full/obj16/deployedLockTable"));
			}
			if (!table.isDeployed() && table.isLocked()) {
				return overlayImage(object, getResourceLocator().getImage(
						"full/obj16/lockTable"));
			}
			return overlayImage(object, getResourceLocator().getImage(
			"full/obj16/Table"));
		}
		return overlayImage(object, getResourceLocator().getImage(
		"full/obj16/Table"));
	}

}
