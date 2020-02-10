/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.RuleFunction;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.cep.decisionproject.ontology.RuleFunction} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class RuleFunctionItemProvider extends AbstractResourceItemProvider
		implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RuleFunctionItemProvider(AdapterFactory adapterFactory) {
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

			addBodyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Body feature.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBodyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RuleFunction_body_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RuleFunction_body_feature", "_UI_RuleFunction_type"),
				 OntologyPackage.Literals.RULE_FUNCTION__BODY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			//childrenFeatures.add(OntologyPackage.Literals.RULE_FUNCTION__HEADER);
			//childrenFeatures.add(OntologyPackage.Literals.RULE_FUNCTION__ARGUMENTS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns RuleFunction.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {
		if (object instanceof RuleFunction) {
			RuleFunction rulefunction = (RuleFunction) object;
			EList<Property> property = rulefunction.getHeader().getProperty();
			for (Property prop : property) {
				if (prop.getName().equalsIgnoreCase("virtual")
						&& prop.getType().equalsIgnoreCase("boolean")
						&& prop.getValue().equalsIgnoreCase("true")) {
					return overlayImage(object, getResourceLocator().getImage(
							"full/obj16/virtualrulefunction"));
				}
			}
		}
		return overlayImage(object, getResourceLocator().getImage(
				"full/obj16/RuleFunction"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	@Override
	public String getText(Object object) {
		// create children children store for Rule function	
		String label = ((RuleFunction) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_RuleFunction_type")
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

		switch (notification.getFeatureID(RuleFunction.class)) {
			case OntologyPackage.RULE_FUNCTION__BODY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case OntologyPackage.RULE_FUNCTION__HEADER:
			case OntologyPackage.RULE_FUNCTION__ARGUMENTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors.add
			(createChildParameter
				(OntologyPackage.Literals.RULE_FUNCTION__HEADER,
				 OntologyFactory.eINSTANCE.createHeader()));

		newChildDescriptors.add
			(createChildParameter
				(OntologyPackage.Literals.RULE_FUNCTION__ARGUMENTS,
				 OntologyFactory.eINSTANCE.createArguments()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	@Override
	public Collection<?> getChildren(Object object) {
		//return super.getChildren(object);
		List<Object> childrenList = new ArrayList<Object>();
		if (object instanceof RuleFunction){
			RuleFunction rf = (RuleFunction)object;
			String rfPath = rf.getFolder()+rf.getName();
			if (implementationList != null){
				for (AbstractResource abs : implementationList){
					Implementation impl = (Implementation)abs;
					if (impl.getImplements().equals(rfPath)){
						childrenList.add(impl);
					}
				}
			}
		
		}
		return childrenList;
	}



}
