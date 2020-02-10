/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.ParentResource;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.common.legacy.adapters.ConceptModelTransformer;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.decision.table.core.DecisionTableCorePlugin;


/**
 * This is the item provider adapter for a {@link com.tibco.cep.decisionproject.ontology.AbstractResource} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbstractResourceItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	
	private static class Key {

		private String project;

		private String resourcePath;

		Key(String project, String resourcePath) {
			this.project = project;
			this.resourcePath = resourcePath;
		}

		/**
		 * @return the project
		 */
		public final String getProject() {
			return project;
		}

		/**
		 * @return the tablePath
		 */
		public final String getResourcePath() {
			return resourcePath;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof AbstractResourceItemProvider.Key)) {
				return false;
			}
			AbstractResourceItemProvider.Key other = (AbstractResourceItemProvider.Key) obj;
			if (!this.project.equals(other.getProject())) {
				return false;
			}
			if (!this.resourcePath.equals(other.getResourcePath())) {
				return false;
			}
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return project.hashCode() * resourcePath.hashCode();
		}
	}
	
	protected static List<AbstractResource> implementationList;
	
	//Created a static because this item provider instance is not a singleton
	protected static Map<Key, AbstractResource> pathToResourcesMap = new HashMap<Key, AbstractResource>();
	/**
	 * This method returns the Children of a Resource
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
		
	private AbstractResource dereference(Property property) {
		//Get its data type
		int dataType = property.getDataType();
		if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
				|| dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
			String resourcePath = property.getPropertyResourceType();
			if (resourcePath != null && resourcePath.length() > 0) {
				//Do the real work
				String projectName = property.getOwnerProjectName();
				AbstractResource fromMap = pathToResourcesMap.get(new Key(projectName, resourcePath));
				if (fromMap != null) {
					return fromMap;
				}
				com.tibco.cep.designtime.core.model.element.Concept newConcept = 
					IndexUtils.getConcept(projectName, resourcePath);
				// clone the concept
				if (newConcept == null) {
					throw new RuntimeException ("Resource " + resourcePath + " does not have matching concept");
				}
				Concept oldConcept = OntologyFactory.eINSTANCE.createConcept();
				oldConcept = new ConceptModelTransformer().transform(newConcept, oldConcept);
				pathToResourcesMap.put(new Key(projectName, resourcePath), oldConcept);
				return oldConcept;
			}
		}
		return property;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		//pathToResourcesMap.remove(key)
	}



	/**
	 * 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	
	@Override
	 public Collection<?> getChildren(Object object) {
		ChildrenStore store = getChildrenStore(object);
		if (store != null) {
			return store.getChildren();
		}

		store = createChildrenStore(object);
		List<Object> result = store != null ? null : new ArrayList<Object>();
		EObject eObject = (EObject) object;
		if (eObject instanceof Concept) {
			Concept concept = (Concept)eObject;
			String path = concept.getFolder() + concept.getName();
			String projectName = concept.getOwnerProjectName();
			if (path != null && !pathToResourcesMap.containsKey(path) && projectName != null) {
				pathToResourcesMap.put(new Key(projectName, path), concept);
				/**
				 * BE-13672 - DT argument (concept) properties are shown twice for newly created/imported projects.
				 * No need to build the argument Concepts again, as they are already built.
				 * So commented the below method call.
				 */
				//LegacyDecisionTableCoreUtil.buildArgumentResource(projectName, concept, concept.getAlias(), true);
			}
			
		}
		for (EStructuralFeature feature : getAnyChildrenFeatures(object)) {
			if (feature.isMany()) {
				List<?> children = (List<?>) eObject.eGet(feature);
				int index = 0;
				for (Object unwrappedChild : children) {
					Object child = wrap(eObject, feature, unwrappedChild, index);
					
					
					if (store != null) {
						// added so that implementation is not added to children store of parent resource
						if (!(child instanceof Implementation)) {
							store.getList(feature).add(child);
						}
					} else {
						// added so that implementation is not added inside parent resource
						if (!(child instanceof Implementation)) {
							if (child instanceof Property) {
								//Resolve references
								AbstractResource resolved = dereference((Property)child);
								result.add(resolved);
							} else {
								result.add(child);
							}
						}
					}
					index++;
				}
			} else {
				Object child = eObject.eGet(feature);
				if (child != null) {
					child = wrap(eObject, feature, child,
							CommandParameter.NO_INDEX);
					if (child instanceof Concept) {
						Concept concept = (Concept)child;
						String path = concept.getFolder() + concept.getName();
						String projectName = concept.getOwnerProjectName();
						pathToResourcesMap.put(new Key(projectName, path), concept);
					}
					if (store != null) {
						// added so that implementation is not added to children store of parent resource
						if (!(child instanceof Implementation)) {
							store.setValue(feature, child);
						}
					} else {
						// added so that implementation is not added inside parent resource
						if (!(child instanceof Implementation)) {
							if (child instanceof Property) {
								//Resolve references
								AbstractResource resolved = dereference((Property)child);
								result.add(resolved);
							} else {
								result.add(child);
							}
						}
					}
				}
			}
		}
		return store != null ? store.getChildren() : result;
	}
	
	public	static <P extends ParentResource> void buildArgumentResource(String projectName, 
			final P old,
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> pDefs)//,
			/*List<ResourcePathRefs> processedEntitiesPaths) */{
		
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition pDef : pDefs) {
			Property prop = OntologyFactory.eINSTANCE.createProperty();
			int dataType = pDef.getType().getValue();
			//This is used only for contained/referenced concept
			if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
					|| dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
				prop.setPropertyResourceType(pDef.getConceptTypePath());
				prop.setMultiple(pDef.isArray());
				String resourcePath = prop.getPropertyResourceType();
				if (resourcePath != null && resourcePath.length() > 0) {
					Concept oldConcept = OntologyFactory.eINSTANCE.createConcept();
					com.tibco.cep.designtime.core.model.element.Concept newConcept = IndexUtils.getConcept(projectName, resourcePath);
					if (newConcept == null) {
						throw new RuntimeException ("Resource " + resourcePath + " does not have matching concept");
					}
					oldConcept.setName(newConcept.getName());
					oldConcept.setFolder(newConcept.getFolder());
					oldConcept.setOwnerProjectName(newConcept.getOwnerProjectName());
					if (old instanceof ArgumentResource) {
						ArgumentResource argsResource = (ArgumentResource)old;
						String alias = null;
						String addendum = (pDef.isArray()) ? "[]" : "";
						alias = argsResource.getAlias() + "." + pDef.getName() + addendum;
						oldConcept.setAlias(alias);
					}
					old.addChild(oldConcept);
					//DecisionTablePlugin.debug(LegacyDecisionTableUtil.class.getName(), "Contained or referenced concept : {0}", oldConcept.getName());
					/*ResourcePathRefs pathRefs = new ResourcePathRefs(resourcePath, parentResourcePath);
if (!processedEntitiesPaths.contains(pathRefs)) {
processedEntitiesPaths.add(pathRefs);
//Recurse this
buildArgumentResource(projectName, oldConcept, newConcept.getAllProperties(), processedEntitiesPaths);
}*/
				}
			} /*else {
				boolean hasAccess = true;
				String propertyPath = parentResourcePath + "/" + pDef.getName();
				if (StudioWorkbenchUtils.isStandaloneDecisionManger()) {
					hasAccess = ensureAccess(pDef.getOwnerProjectName(), propertyPath);
				}
				if (hasAccess) {
					String addendum = (pDef.isArray()) ? "[]" : "";
					prop.setName(pDef.getName() + addendum);
					prop.setDataType(dataType);
					prop.setMultiple(pDef.isArray());
					prop.setFolder(pDef.getFolder());
					prop.setHistoryPolicy(pDef.getHistoryPolicy());
					prop.setHistorySize(pDef.getHistorySize());
					prop.setOwnerProjectName(pDef.getOwnerProjectName());
					old.addChild(prop);
				}
			}*/
		}
	}

	/**
	 * 
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	  private Collection<? extends EStructuralFeature> getAnyChildrenFeatures(
			Object object) {
		Collection<? extends EStructuralFeature> result = getChildrenFeatures(object);
		return result;//.isEmpty() ? getChildrenFeatures(object) : result;
	}
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AbstractResourceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {
		if (object instanceof Table)
			return overlayImage(object, getResourceLocator().getImage("full/obj16/Table"));
		return super.getImage(object);
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

			addModifiedPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addFolderPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Modified feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addModifiedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AccessControlCandidate_modified_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AccessControlCandidate_modified_feature", "_UI_AccessControlCandidate_type"),
				 OntologyPackage.Literals.ACCESS_CONTROL_CANDIDATE__MODIFIED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractResource_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractResource_name_feature", "_UI_AbstractResource_type"),
				 OntologyPackage.Literals.ABSTRACT_RESOURCE__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractResource_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractResource_description_feature", "_UI_AbstractResource_type"),
				 OntologyPackage.Literals.ABSTRACT_RESOURCE__DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Folder feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFolderPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractResource_folder_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractResource_folder_feature", "_UI_AbstractResource_type"),
				 OntologyPackage.Literals.ABSTRACT_RESOURCE__FOLDER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public String getText(Object object) {		
		String label = ((AbstractResource)object).getName();
		
		label = label == null || label.length() == 0 ?
			getString("_UI_AbstractResource_type") :label;
		if (object instanceof Table){
			label = label+".dt";
		}
		return label;
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

		switch (notification.getFeatureID(AbstractResource.class)) {
			case OntologyPackage.ABSTRACT_RESOURCE__MODIFIED:
			case OntologyPackage.ABSTRACT_RESOURCE__NAME:
			case OntologyPackage.ABSTRACT_RESOURCE__DESCRIPTION:
			case OntologyPackage.ABSTRACT_RESOURCE__FOLDER:
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

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return DecisionTableCorePlugin.getDefault();
	}

}
