package com.tibco.cep.studio.core.grammar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class EntityInfo {

	protected Entity fEntity;
	
	public EntityInfo(Entity entity) {
		this.fEntity = entity;
	}

	public Entity getEntity() {
		return fEntity;
	}

	public String getGUID() { return getEntity().getGUID(); }
	public String getDescription() { return getEntity().getDescription(); }
	public String getName() { return getEntity().getName(); }
	public String getFolder() { return ModelUtils.convertPathToPackage(getEntity().getFolder()); }
	public String getFullPath() {
		return ModelUtils.convertPathToPackage(getEntity().getFullPath());
	}
	public List<MetadataProperty> getMetadataProperties() {
		PropertyMap extendedProperties = getEntity().getExtendedProperties();
		EList<Entity> properties = extendedProperties.getProperties();
		List<MetadataProperty> metadataProps = new ArrayList<EntityInfo.MetadataProperty>();
		
		for (Entity entity : properties) {
			metadataProps.add(new MetadataProperty(entity));
		}
		return metadataProps;
	}
	class MetadataProperty {
		private Entity entity;
		
		public MetadataProperty(Entity entity) {
			this.entity = entity;
		}
		
		public String getName() {
			return RuleGrammarUtils.escapeSimpleString(entity.getName());
		}
		
		public String getValue() {
			if (entity instanceof SimpleProperty) {
				return RuleGrammarUtils.escapeSimpleString(((SimpleProperty) entity).getValue());
			}
			if (entity instanceof ObjectProperty) {
				EObject value = ((ObjectProperty) entity).getValue();
				if (value instanceof PropertyMap) {
					((PropertyMap) value).getProperties();
				}
			}
			return null;
		}
		
		public boolean isMap() {
			if (entity instanceof ObjectProperty) {
				EObject value = ((ObjectProperty) entity).getValue();
				if (value instanceof PropertyMap) {
					return true;
				}
			}
			return false;
		}
		
		public List<MetadataProperty> getMapProperties() {
			if (entity instanceof ObjectProperty) {
				EObject value = ((ObjectProperty) entity).getValue();
				if (value instanceof PropertyMap) {
					PropertyMap extendedProperties = (PropertyMap) value;
					EList<Entity> properties = extendedProperties.getProperties();
					List<MetadataProperty> metadataProps = new ArrayList<EntityInfo.MetadataProperty>();

					for (Entity entity : properties) {
						metadataProps.add(new MetadataProperty(entity));
					}
					return metadataProps;
				}
			}
			return null;
		}
	}
	
	public class Property {
		private PropertyDefinition propertyDef;
		
		public Property(PropertyDefinition propDef) {
			this.propertyDef = propDef;
		}
		public String getType() {
			if (propertyDef.getConceptTypePath() != null && propertyDef.getConceptTypePath().length() > 0) {
				return ModelUtils.convertPathToPackage(propertyDef.getConceptTypePath());
			} else {
				return propertyDef.getType().getLiteral();
			}
		}
		public int getHistorySize() {
			return propertyDef.getHistorySize();
		}
		public String getHistoryPolicy() {
			if (propertyDef.getHistoryPolicy() == 0) {
				return "CHANGES_ONLY";
			}
			return "ALL_VALUES";
		}
		public boolean isContained() { return propertyDef.getType() == PROPERTY_TYPES.CONCEPT; }
		public boolean isArray() { return propertyDef.isArray(); }
		public String getName() { return propertyDef.getName(); }
		
		public boolean getHasDomains() {
			EList<DomainInstance> domainInstances = propertyDef.getDomainInstances();
			return domainInstances.size() > 0;
		}
		
		public String getDomain() { 
			EList<DomainInstance> domainInstances = propertyDef.getDomainInstances();
			StringBuilder buf = new StringBuilder();
			for (int i=0; i<domainInstances.size(); i++) {
				DomainInstance domainInstance = domainInstances.get(i);
				buf.append(ModelUtils.convertPathToPackage(domainInstance.getResourcePath()));
				if (i < domainInstances.size()-1) {
					buf.append(", ");
				}
			}
			return buf.toString();
		}
		public boolean getRequiresAttrBlock() {
			if (propertyDef.getType() == PROPERTY_TYPES.CONCEPT) {
				return true;
			}
			if (propertyDef.getHistorySize() > 0) {
				return true;
			}
			if (propertyDef.getHistoryPolicy() != 0) {
				return true;
			}
			return false;
		}
	}

}
