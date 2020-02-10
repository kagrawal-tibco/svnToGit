/**
 * 
 */
package com.tibco.cep.query.model.impl;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;


/**
 * @author pdhar
 *
 */
public class EntityImpl extends AbstractRegistryEntryContext implements
        Entity {

    private String entityName;

    /**
     * @param context
     * @param name
     * @param tree
     */
    public EntityImpl(ModelContext context, String name, CommonTree tree) {
        super(context);
        if(name.startsWith("/")) {
            entityName = name;
        } else {
            entityName = "/"+name;
        }
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.Entity#getEntityExpandedName()
      */
    public String getEntityPath() {
            return entityName;
    }

    /**
     * @return EntityAttributes
     */
    public EntityAttribute getEntityAttribute(String name) throws Exception {
        if((null != name) && isResolved() && this.hasChildren()) {
            final ModelContext[] children = getDescendantContextsByType(this, ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE);
            for (int i=0; i<children.length; i++) {
                final EntityAttribute child = (EntityAttribute) children[i];
                if (name.equals(child.getName())) {
                    return child;
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.Entity#getEntityName()
      */
    public String getEntityName() {
        if (entityName.lastIndexOf("/") != -1)
            return entityName.substring(entityName.lastIndexOf("/") + 1);
        else
            return entityName;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.Entity#setEntityName(java.lang.String)
      */
    public void setEntityName(String name) {
        this.entityName = name;

    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#getContextType()
      */
    public int getContextType() {
        return ModelContext.CTX_TYPE_ENTITY;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Entity#getEntityClass()
      */
    public Class getEntityClass() throws Exception{
        if(isResolved() && this.typeInfo != null) {
            return this.typeInfo.getRuntimeClass(getRootContext().getProjectContext().getRuleServiceProvider().getTypeManager());
        }
        return null;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Entity#getEntityProperties()
      */
    public EntityProperty[] getEntityProperties() throws Exception {
        if(isResolved() && this.hasChildren()) {
            return Arrays.asList(getDescendantContextsByType(this,ModelContext.CTX_TYPE_ENTITY_PROPERTY)).toArray(new EntityProperty[0]);
        }
        return null;

    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Entity#getEntityAttributes()
      */
    public EntityAttribute[] getEntityAttributes() throws Exception{
        if(isResolved() && this.hasChildren()) {
            return Arrays.asList(getDescendantContextsByType(this,ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE)).toArray(new EntityAttribute[0]);
        }
        return null;

    }

    /**
     * @return EntityProperty
     */
    public EntityProperty getEntityProperty(String name) throws Exception {
        if((null != name) && isResolved() && this.hasChildren()) {
            final ModelContext[] children = getDescendantContextsByType(this, ModelContext.CTX_TYPE_ENTITY_PROPERTY);
            for (int i=0; i<children.length; i++) {
                final EntityProperty child = (EntityProperty) children[i];
                if (name.equals(child.getName())) {
                    return child;
                }
            }
        }
        return null;
    }


    public TypeInfo getTypeInfo() throws Exception {
        return this.typeInfo;
    }

    public String toString() {
        return this.entityName;
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.impl.AbstractModelContext#resolveContext()
      */
    public boolean resolveContext() throws Exception {
        final Ontology ontology = this.getProjectContext().getOntology();
        com.tibco.cep.designtime.model.Entity entity = ontology.getEntity(entityName);

        // What is this for?
        // Get all the entity properties so they can be used as identified contexts which
        // are properties where as the Entity alias or path identifiers map to this entity object itself.
        // e.g. A.customerId where "/Concepts/Customer" as A and customerId is a Entity Property created
        // below. During projection and where clause resolution of A.customerId , identifier A's identified context is
        // this EntityImpl and identifier customerId's identified context is EntityPropertyImpl created below for
        // property "customerId".This is prefetching all the the known data which comes from the "from clause" rest of
        // the model is mostly identifiers,functions and literals which has no information other than being in context of
        // its parent identifier/function.

        if (entity instanceof Concept) {
            Concept concept = (Concept) entity;
            List conceptPropertyDefinitions = concept.getAllPropertyDefinitions();
            for (Iterator iter = conceptPropertyDefinitions.iterator(); iter.hasNext();) {
                final PropertyDefinition element = (PropertyDefinition) iter.next();
                final TypeInfoImpl ti = new TypeInfoImpl(element);
                new EntityPropertyImpl(this, null, element.getName(), ti, element, element.isArray());
                this.logger.log(Level.DEBUG, "Registered concept property %s.%s",
                        this.getEntityName(), element.getName());
            }
            for (Iterator it = concept.getAttributeDefinitions().iterator(); it.hasNext(); ) {
                final PropertyDefinition element = (PropertyDefinition) it.next();
                final TypeInfoImpl ti = new TypeInfoImpl(element);
                new EntityAttributeImpl(this, null, element.getName(), ti, element);
                this.logger.log(Level.DEBUG, "Registered concept attribute %s@%s",
                        this.getEntityName(), element.getName());
            }
        }
        else if (entity instanceof Event) {
            Event event = (Event) entity;
            List eventPropertyDefinitions = event.getAllUserProperties();
            for (Iterator iter = eventPropertyDefinitions.iterator(); iter
                    .hasNext();) {
                final EventPropertyDefinition element = (EventPropertyDefinition) iter.next();
                final TypeInfoImpl ti = new TypeInfoImpl(element);
                new EntityPropertyImpl(this, null, element.getPropertyName(), ti, element, false);
                this.logger.log(Level.DEBUG, "Registered event property %s.%s",
                        this.getEntityName(), element.getPropertyName());
            }

            for (Iterator it = event.getAttributeDefinitions().iterator(); it.hasNext(); ) {
                final EventPropertyDefinition element = (EventPropertyDefinition) it.next();
                final TypeInfoImpl ti = new TypeInfoImpl(element);
                new EntityAttributeImpl(this, null, element.getPropertyName(), ti, element);
                this.logger.log(Level.DEBUG, "Registered event attribute %s@%s",
                        this.getEntityName(), element.getPropertyName());
            }
        }

        if (null != entity) {
            this.typeInfo = new TypeInfoImpl(entity);
            return true;
        }

        return false;
    }


    public boolean isResolved() {
       return null != this.typeInfo;
   }


}
