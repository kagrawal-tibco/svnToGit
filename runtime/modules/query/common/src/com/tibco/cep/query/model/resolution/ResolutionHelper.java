package com.tibco.cep.query.model.resolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.AggregateFunctionIdentifier;
import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.ArrayAccessProxy;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.DeleteContext;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionIdentifier;
import com.tibco.cep.query.model.GroupClause;
import com.tibco.cep.query.model.HavingClause;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.OrderClause;
import com.tibco.cep.query.model.PathFunctionIdentifier;
import com.tibco.cep.query.model.PathIdentifier;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.ScopedIdentifier;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.Stream;
import com.tibco.cep.query.model.TypeIdentifier;
import com.tibco.cep.query.model.impl.ArrayLengthAttributeImpl;
import com.tibco.cep.query.model.impl.EntityAttributeProxyImpl;
import com.tibco.cep.query.model.impl.EntityPropertyProxyImpl;
import com.tibco.cep.query.model.impl.IsSetAttributeImpl;

/*
 * User: pdhar
 * Date: Oct 27, 2007
 * Time: 11:27:44 PM
 */
public class ResolutionHelper {

    public static ModelContext resolveIdentifier (ScopedIdentifier sid) throws Exception {
        return null;
    }


    public static ModelContext resolveIdentifier (Identifier id ) throws Exception{
        if (null == id) {
            throw new NullPointerException();
        }

        ModelContext ctx = null;
        if (id instanceof FunctionIdentifier) {
            // resolve path function and aggregate function
            ctx = resolveFunctionIdentifier((FunctionIdentifier)id);
        }
        else if (id instanceof AliasedIdentifier ) {
            // resolve from clause aliased identifiers
            ctx = resolveAliasedIdentifier((AliasedIdentifier)id);
        }
        else if (id instanceof PathIdentifier) {
            // resolve path identfier being directly used in a binary expression
            ctx = resolvePathIdentifier((PathIdentifier)id);
        }
        else if (id instanceof TypeIdentifier) {
            // Resolves a path-based type identifier.
            ctx = resolveTypeIdentifier((TypeIdentifier)id);
        }

        if (null != ctx) {
            return ctx;
        }

        return resolveIdentifierInternal(id);

        // It is the responsibility of the caller to set the identified context and its type info
    }


    private static ModelContext resolveIdentifierInternal(Identifier id) throws Exception {
        final String name = id.getName();

        if (null == name) {
            throw new IllegalArgumentException("Identifier name is null.");
        }

        // Checks for the two special cases: right operand of . (=> property) and right operand of @ (=> attribute)
        if (id.getParentContext() instanceof BinaryExpression) {
            final Expression leftOperand = ((BinaryExpression) id.getParentContext()).getLeftExpression();
            final Operator op = ((BinaryExpression) id.getParentContext()).getOperator();
            final int opType = op.getOpType();

            if (id != leftOperand) {
                final ModelContext identifiedCtx = leftOperand.getIdentifiedContext();
                switch (opType) {
                    case Operator.OP_DOT: {
                        if (identifiedCtx instanceof AliasedIdentifier) {
                            final Entity e = (Entity) ((AliasedIdentifier) identifiedCtx).getIdentifiedContext();
                            final EntityProperty p = e.getEntityProperty(id.getName());
                            if (null == p) {
                                throw new ResolveException("No property '" + id.getSourceString()
                                        +"' in " + e);
                            }
                            return makePropertyProxy(identifiedCtx, p);
                        }
                        else if (identifiedCtx instanceof EntityPropertyProxy) {
                            try {
                                return makePropertyProxy((EntityPropertyProxy) identifiedCtx, name);
                            } catch (Exception e) {
                                throw new ResolveException("Cannot resolve property: " + id.getSourceString(), e);
                            }
                        }
                        else if (identifiedCtx instanceof EntityAttributeProxy) {
                            try {
                                return makePropertyProxy((EntityAttributeProxy) identifiedCtx, name);
                            } catch (Exception e) {
                                throw new ResolveException("Cannot resolve property: " + id.getSourceString(), e);
                            }
                        }
                        else if (identifiedCtx instanceof ArrayAccessProxy) {
                            final ModelContext parent = identifiedCtx.getParentContext();
                            if (parent instanceof ProxyContext) {
                                try {
                                    return makePropertyProxy((ArrayAccessProxy) identifiedCtx, (ProxyContext)parent, name);
                                } catch (Exception e) {
                                    throw new ResolveException("Cannot resolve property: " + id.getSourceString(), e);
                                }
                            }
                            else {
                                throw new ResolveException("Cannot resolve what the array applies to: " + id.getSourceString());
                            }
                        }
                        else if (identifiedCtx instanceof SelectContext) {
                            //TODO
                            throw new ResolveException("Cannot resolve property: " + id.getSourceString());
                        }
                        else {
                            throw new ResolveException("Cannot resolve what the identifier applies to: " + id.getSourceString());
                        }
                    }
                    //break; (unreachable)
                    case Operator.OP_AT: {
                        if (identifiedCtx instanceof AliasedIdentifier) {
                            final Entity e = (Entity) ((AliasedIdentifier) identifiedCtx).getIdentifiedContext();
                            final EntityAttribute a = e.getEntityAttribute(name);
                            if (null == a) {
                                throw new ResolveException("No attribute '" + id.getSourceString()
                                        +"' in " + e);
                            }
                            return makeAttributeProxy(identifiedCtx, a);
                        }
                        else if (identifiedCtx instanceof EntityPropertyProxy) {
                            if ("length".equals(name)
                                    && ((PropertyDefinition) (((EntityPropertyProxy) identifiedCtx).getProperty().getOntologyObject())).isArray()) {
                                return new ArrayLengthAttributeImpl(identifiedCtx);
                            } else if ("isSet".equals(name)) {
                                return new IsSetAttributeImpl(identifiedCtx);
                            } else {
                                return makeAttributeProxy((EntityPropertyProxy) identifiedCtx, name);
                            }
                        }
                        else if (identifiedCtx instanceof EntityAttributeProxy) {
                            if ("length".equals(name)
                                    && ((PropertyDefinition) (((EntityAttributeProxy) identifiedCtx).getAttribute().getOntologyObject())).isArray()) {
                                return new ArrayLengthAttributeImpl(identifiedCtx);
                            } else if ("isSet".equals(name)) {
                                return new IsSetAttributeImpl(identifiedCtx);
                            } else {
                                return makeAttributeProxy((EntityAttributeProxy) identifiedCtx, name);
                            }
                        }
                        else if (identifiedCtx instanceof ArrayAccessProxy) {
                            final ModelContext parent = identifiedCtx.getParentContext();
                            if (parent instanceof ProxyContext) {
                                try {
                                    return makeAttributeProxy((ArrayAccessProxy) identifiedCtx, (ProxyContext)parent, name);
                                } catch (Exception e) {
                                    throw new ResolveException("Cannot resolve attribute: " + id.getSourceString(), e);
                                }
                            }
                            else {
                                throw new ResolveException("Cannot resolve what the array applies to: " + id.getSourceString());
                            }
                        }
                        else if (identifiedCtx instanceof Function) {
                            if ("length".equals(name) && leftOperand.getTypeInfo().isArray()) {
                                return new ArrayLengthAttributeImpl(leftOperand);
                            } else if ("isSet".equals(name) && leftOperand.getTypeInfo().isProperty()) {
                                return new IsSetAttributeImpl(leftOperand);
                            }
                        }
                        else if (identifiedCtx instanceof SelectContext) {
                            //TODO
                            throw new ResolveException("Cannot resolve attribute: " + id.getSourceString());
                        }
                        else {
                            throw new ResolveException("Cannot resolve what the identifier applies to: " + id.getSourceString());
                        }
                    }
                    //break; (unreachable)
                }//switch
            }//if
        }//if

        // Not a special case
        // => The identifier could refer to an alias or a property or an attribute, in the current scope.

        Set<EntityPropertyProxy> properties = new HashSet<EntityPropertyProxy>();
        Set<EntityAttributeProxy> attributes = new HashSet<EntityAttributeProxy>();
        for (ModelContext scopeItem : lookupScope(id)) {
            if ((scopeItem instanceof Aliased) && name.equals(((Aliased) scopeItem).getAlias())) {
                if (scopeItem instanceof Projection) {
                    return ((Projection) scopeItem).getExpression();
                }
                return scopeItem;
            }
            if (scopeItem instanceof Projection) {
                final ModelContext c = ((Projection) scopeItem).getExpression().getIdentifiedContext();
                if (c instanceof AliasedIdentifier) {
                    scopeItem = c;
                } else if ((c instanceof EntityPropertyProxy)
                        && name.equals(((EntityPropertyProxy) c).getProperty().getName())) {
                    properties.add(((EntityPropertyProxy) c));
                } else if ((c instanceof EntityAttributeProxy)
                        && name.equals(((EntityAttributeProxy) c).getAttribute().getName())) {
                    attributes.add(((EntityAttributeProxy) c));
                }
            }
            if (scopeItem instanceof AliasedIdentifier) {
                final Entity e = (Entity) ((AliasedIdentifier) scopeItem).getIdentifiedContext();
                if (null != e) {
                    final EntityProperty p = e.getEntityProperty(id.getName());
                    if (null != p) {
                        properties.add(makePropertyProxy(scopeItem, p));
                    }
                    final EntityAttribute a = e.getEntityAttribute(id.getName());
                    if (null != a) {
                        attributes.add(makeAttributeProxy(scopeItem, a));
                    }
                }
            }
        }

        // Not an alias
        if (properties.size() + attributes.size() > 1) {
            throw new ResolveException("Ambiguous identifier (" + properties.size() +" properties): '" + id.getSourceString());
        }

        if (properties.size() > 0) {
            return properties.iterator().next();
        }

        if (attributes.size() > 0) {
            return attributes.iterator().next();
        }

        // Not a property or an attribute either...
        //todo: fix the order by resolution. 
        throw new ResolveException("Cannot resolve identifier: " + id.getSourceString());
    }


    private static ModelContext makeAttributeProxy(ArrayAccessProxy arrayAccessProxy, ProxyContext arrayProxy,
                                                   String name) throws Exception {
        Object ontologyObj = null;
        if (arrayProxy instanceof EntityPropertyProxy) {
            ontologyObj = ((EntityPropertyProxy) arrayProxy).getProperty().getOntologyObject();
        }
        else if (arrayProxy instanceof EntityAttributeProxy) {
            ontologyObj = ((EntityAttributeProxy) arrayProxy).getAttribute().getOntologyObject();
        }

        if (null != ontologyObj) {
            if ((ontologyObj instanceof PropertyDefinition) && ((PropertyDefinition) ontologyObj).isArray()) {
                final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
                if (null != c) {
                    final Entity e = arrayProxy.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                    final EntityAttribute attr = e.getEntityAttribute(name);
                    if (null == attr) {
                        throw new ResolveException("No attribute '" + name + "' in: " + arrayAccessProxy);
                    }
                    return makeAttributeProxy(arrayAccessProxy, attr);
                }
            }
        }
        throw new ResolveException("Attempted to access a property of a type that is not a Concept: "+ arrayProxy);
    }


    private static ModelContext makePropertyProxy(ArrayAccessProxy arrayAccessProxy, ProxyContext arrayProxy,
                                                  String name) throws Exception {
        Object ontologyObj = null;
        if (arrayProxy instanceof EntityPropertyProxy) {
            ontologyObj = ((EntityPropertyProxy) arrayProxy).getProperty().getOntologyObject();
        }
        else if (arrayProxy instanceof EntityAttributeProxy) {
            ontologyObj = ((EntityAttributeProxy) arrayProxy).getAttribute().getOntologyObject();
        }

        if (null != ontologyObj) {
            if ((ontologyObj instanceof PropertyDefinition) && ((PropertyDefinition) ontologyObj).isArray()) {
                final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
                if (null != c) {
                    final Entity e = arrayProxy.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                    final EntityProperty prop = e.getEntityProperty(name);
                    if (null == prop) {
                        throw new ResolveException("No property '" + name + "' in: " + arrayAccessProxy);
                    }
                    return makePropertyProxy(arrayAccessProxy, prop);
                }
            }
        }
        throw new ResolveException("Attempted to access a property of a type that is not a Concept: "+ arrayProxy);
    }


    private static EntityPropertyProxy makePropertyProxy(EntityPropertyProxy owner, String subPropName)
            throws Exception {
        final Object ontologyObj = owner.getProperty().getOntologyObject();
        if ((ontologyObj instanceof PropertyDefinition) && !((PropertyDefinition) ontologyObj).isArray()) {
            final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
            if (null != c) {
                final Entity e = owner.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                final EntityProperty prop = e.getEntityProperty(subPropName);
                if (null == prop) {
                    throw new ResolveException("No property '" + subPropName + "' in: " + owner);
                }
                return makePropertyProxy(owner, prop);
            }
        }
        throw new ResolveException("Attempted to access a property of a type that is not a Concept: "+ owner);
    }


    private static EntityPropertyProxy makePropertyProxy(EntityAttributeProxy owner, String subPropName)
            throws Exception {
        final Object ontologyObj = owner.getAttribute().getOntologyObject();
        if ((ontologyObj instanceof PropertyDefinition) && !((PropertyDefinition) ontologyObj).isArray()) {
            final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
            if (null != c) {
                final Entity e = owner.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                final EntityProperty prop = e.getEntityProperty(subPropName);
                if (null == prop) {
                    throw new ResolveException("No property '" + subPropName + "' in: " + owner);
                }
                return makePropertyProxy(owner, prop);
            }
        }
        throw new ResolveException("Attempted to access a property of a type that is not a Concept: "+ owner);
    }


    private static EntityAttributeProxy makeAttributeProxy(EntityPropertyProxy owner, String subPropName)
            throws Exception {
        final Object ontologyObj = owner.getProperty().getOntologyObject();
        if ((ontologyObj instanceof PropertyDefinition) && !((PropertyDefinition) ontologyObj).isArray()) {
            final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
            if (null != c) {
                final Entity e = owner.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                final EntityAttribute a = e.getEntityAttribute(subPropName);
                if (null == a) {
                    throw new ResolveException("No attribute '" + subPropName + "' in: " + owner);
                }
                return makeAttributeProxy(owner, a);
            }
        }
        throw new ResolveException("Attempted to access a property of a type that is not a Concept: "+ owner);
    }


    private static EntityAttributeProxy makeAttributeProxy(EntityAttributeProxy owner, String subPropName)
            throws Exception {
        final Object ontologyObj = owner.getAttribute().getOntologyObject();
        if ((ontologyObj instanceof PropertyDefinition) && !((PropertyDefinition) ontologyObj).isArray()) {
            final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
            if (null != c) {
                final Entity e = owner.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                final EntityAttribute a = e.getEntityAttribute(subPropName);
                if (null == a) {
                    throw new ResolveException("No attribute '" + subPropName + "' in: " + owner);
                }
                return makeAttributeProxy(owner, a);
            }
        }
        throw new ResolveException("Attempted to access an attribute of a type that is not a Concept: "+ owner);
    }


    private static EntityPropertyProxy makePropertyProxy(ModelContext owner, EntityProperty x) {
        for (Iterator it = owner.getChildrenIterator(); it.hasNext(); ) {
            final Object child = it.next();
            if ((child instanceof EntityPropertyProxy) && ((EntityPropertyProxy)child).getProperty().equals(x)) {
                return (EntityPropertyProxy) child;
            }
        }
        return new EntityPropertyProxyImpl(owner, x);
    }


    private static EntityAttributeProxy makeAttributeProxy(ModelContext owner, EntityAttribute x) {
        for (Iterator it = owner.getChildrenIterator(); it.hasNext(); ) {
            final Object child = it.next();
            if ((child instanceof EntityAttributeProxy) && ((EntityAttributeProxy)child).getAttribute().equals(x)) {
                return (EntityAttributeProxy) child;
            }
        }
        return new EntityAttributeProxyImpl(owner, x);
    }


    /**
     *  Returns the identified contexts for the given identifier depending on the scope
     * @param id
     * @return List of ModelContext.
     * @throws Exception
     */
    public static List<ModelContext> resolveScopedIdentifier (ScopedIdentifier id) throws Exception {
        final List<ModelContext> identifiedContexts = new ArrayList<ModelContext>();

        // Checks if the parent is a binary expression with dot operator
        if (id.getParentContext() instanceof BinaryExpression) {
            final Expression leftOperand = ((BinaryExpression) id.getParentContext()).getLeftExpression();
            final Operator op = ((BinaryExpression) id.getParentContext()).getOperator();
            final int opType = op.getOpType();

            if (id != leftOperand) { // is right operand
                if (opType != Operator.OP_DOT) {
                    throw new ResolveException("Cannot use ScopedIdentifier " + id.getSourceString()
                            + " with operator " + op
                            + " in: " + ((QueryContext) id.getParentContext()).getSourceString());
                }

                //TODO: Comment below is wrong: could be any expression that resolves to an entity, instead!
                // Left operand can be a Identifier for an PathIdentifier or Entity Alias , Array Identifier *NEW*
                // or a sub select Expression alias from the FROM clause
                // left operand has been resolved before this one
                if (leftOperand.getTypeInfo().isEntity()) {
                    final ModelContext leftIdentifiedCtx = leftOperand.getIdentifiedContext();
                    Entity entity;
                    if (leftIdentifiedCtx instanceof Expression) {
                        entity = (Entity) ((Expression) leftIdentifiedCtx).getIdentifiedContext();
                    } else if (leftIdentifiedCtx instanceof EntityPropertyProxy) {
                        final EntityPropertyProxy owner = (EntityPropertyProxy) leftIdentifiedCtx;
                        final Object ontologyObj = owner.getProperty().getOntologyObject();
                        if ((ontologyObj instanceof PropertyDefinition) && !((PropertyDefinition) ontologyObj).isArray()) {
                            final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
                            if (null == c) {
                                throw new ResolveException("Concept not found for: "+ ontologyObj);
                            }
                            entity = owner.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                        } else {
                            throw new ResolveException("Attempted to access a property of a type that is not a Concept: "+ owner);
                        }
                    } else if (leftIdentifiedCtx instanceof EntityAttributeProxy) {
                        final EntityAttributeProxy owner = (EntityAttributeProxy) leftIdentifiedCtx;
                        final Object ontologyObj = owner.getAttribute().getOntologyObject();
                        if ((ontologyObj instanceof PropertyDefinition) && !((PropertyDefinition) ontologyObj).isArray()) {
                            final Concept c = ((PropertyDefinition) ontologyObj).getConceptType();
                            if (null == c) {
                                throw new ResolveException("Concept not found for: "+ ontologyObj);
                            }
                            entity = owner.getProjectContext().getEntityRegistry().getEntityByPath(c.getFullPath());
                        } else {
                            throw new ResolveException("Attempted to access an attribute of a type that is not an Entity: "+ owner);
                        }
                    } else {
                        throw new ResolveException("Unsupported use of ScopedIdentifier " + id.getSourceString()
                                + " in: " + ((QueryContext) id.getParentContext()).getSourceString());
                    }

                    final List<ModelContext> scope = new ArrayList<ModelContext>();
                    if(id.getOwnerContext() instanceof SelectContext) {
                        final GroupClause groupClause = ((SelectContext)id.getOwnerContext()).getGroupClause();
                        if ((null == groupClause)
                                || (null == groupClause.getFieldList())
                                || (groupClause.getFieldList().getSize() < 1)) {
                            for (EntityAttribute attrib : entity.getEntityAttributes()) {
                                identifiedContexts.add(makeAttributeProxy(leftIdentifiedCtx, attrib));
                            }
                            for (EntityProperty prop : entity.getEntityProperties()) {
                                identifiedContexts.add(makePropertyProxy(leftIdentifiedCtx, prop));
                            }
                        } else {
                            final FieldList fieldList = groupClause.getFieldList();
                            final int fieldSize = fieldList.getSize();
                            for (int i = 0; i < fieldSize; i++) {
                                final Expression e = fieldList.getExpression(i);
                                scope.add(e);
                                final ModelContext c = e.getIdentifiedContext();
                                if (null != c) {
                                    scope.add(c);
                                }
                            }
                            for (EntityAttribute attrib : entity.getEntityAttributes()) {
                                final EntityAttributeProxy proxy = makeAttributeProxy(leftIdentifiedCtx, attrib);
                                if (scope.contains(proxy)) {
                                    identifiedContexts.add(proxy);
                                }
                            }
                            for (EntityProperty prop : entity.getEntityProperties()) {
                                final EntityPropertyProxy proxy = makePropertyProxy(leftIdentifiedCtx, prop);
                                if (scope.contains(proxy)) {
                                    identifiedContexts.add(proxy);
                                }
                            }
                        }
                    }
                }
                else { //TODO handle expressions too, before throwing an exception.
                    throw new ResolveException("Cannot use left operand " + leftOperand.getSourceString()
                            + " with operator " + op
                            + " in: " + ((QueryContext) id.getParentContext()).getSourceString());
                }
            } // end is right operand

        } // end BinaryExpression

        // if parent is not a binary expression then it must be a
        // functionIdentifier i.e count(*) or
        // select expression i.e select *
        else if (id.getParentContext() instanceof FunctionIdentifier ) {
            if(!(id.getParentContext() instanceof AggregateFunctionIdentifier)) {
                throw new ResolveException("Scope identifier * is only allowed with aggregate function: "
                        + Function.AGGREGATE_FUNCTION_COUNT);
            }
            AggregateFunctionIdentifier agfi = (AggregateFunctionIdentifier) id.getParentContext();
            if(!agfi.getName().equals(Function.AGGREGATE_FUNCTION_COUNT)) {
                throw new ResolveException("Scope identifier * is only allowed with aggregate function: "
                        + Function.AGGREGATE_FUNCTION_COUNT);
            }
            // in this case the identified contexts are all the entities from the from clause
            // even though they are not used in building the projection
            final FromClause fromCtx = id.getOwnerContext().getFromClause();
            for(Iterator it = fromCtx.getChildrenIterator(); it.hasNext();) {
                final QueryContext ctx = (QueryContext) it.next();
                if (ctx instanceof AliasedIdentifier) {
                    final Entity entity = (Entity) ((AliasedIdentifier) ctx).getIdentifiedContext();
                    final EntityProperty[] props = entity.getEntityProperties();
                    for(int i=0 ; i < props.length; i++) {
                        identifiedContexts.add(makePropertyProxy(ctx, props[i]));
                    }
                    final EntityAttribute[] attribs = entity.getEntityAttributes();
                    for(int i=0 ; i < attribs.length; i++) {
                        identifiedContexts.add(makeAttributeProxy(ctx, attribs[i]));
                    }
                }
                else if (ctx instanceof SelectContext) {
                    //TODO
                    return identifiedContexts;
                }
                else {
                    throw new ResolveException("Could not resolve member of FROM " + ctx.getSourceString()
                            + " for ScopedIdentifier " + id.getSourceString());
                }
            } // end entities

        } // end function identifier

        // select expression i.e select *
        else if (id.getParentContext() instanceof Projection) {

            if(id.getOwnerContext() instanceof SelectContext) {
                final GroupClause groupClause = ((SelectContext)id.getOwnerContext()).getGroupClause();
                if ((null != groupClause)
                        && (null != groupClause.getFieldList())
                        && (groupClause.getFieldList().getSize() > 0)) {
                    // select * from ... group by ...
                    // => the scope is constituted of the group by field(s).
                    final FieldList fieldList = groupClause.getFieldList();
                    final int fieldSize = fieldList.getSize();
                    for (int i = 0; i < fieldSize; i++) {
                        final Expression e = fieldList.getExpression(i);
                        identifiedContexts.add(e);
                    }

                } else {
                    // in this case the identified contexts are all the entities from the from clause
                    //  they are  used in building the projection at runtime
                    FromClause fromCtx = id.getOwnerContext().getFromClause();
                    for (Iterator it = fromCtx.getChildrenIterator(); it.hasNext();) {
                        final QueryContext ctx = (QueryContext) it.next();
                        if (ctx instanceof AliasedIdentifier) {
                            final Entity entity = (Entity) ((AliasedIdentifier) ctx).getIdentifiedContext();
                            final EntityAttribute[] attribs = entity.getEntityAttributes();
                            for(int i=0 ; i < attribs.length; i++) {
                                identifiedContexts.add(makeAttributeProxy(ctx, attribs[i]));
                            }
                            final EntityProperty[] props = entity.getEntityProperties();
                            for(int i=0 ; i < props.length; i++) {
                                identifiedContexts.add(makePropertyProxy(ctx, props[i]));
                            }
                        }
                        else if (ctx instanceof SelectContext) {
                            //TODO
                            return identifiedContexts;
                        }
                        else {
                            throw new ResolveException("Could not resolve member of FROM " + ctx.getSourceString()
                                    + " for ScopedIdentifier " + id.getSourceString());
                        }
                    } // end entities
                }
            }
        }
        else {
            throw new ResolveException("Could not resolve context of ScopedIdentifier: " + id.getSourceString());
        }

        return identifiedContexts;
    }

    /**
     * Returns the identified context of a path identifier
     * @param pathIdentifier
     * @return ModelContext
     * @throws Exception
     */
    private static ModelContext resolvePathIdentifier(PathIdentifier pathIdentifier) throws Exception {
        final Entity entityModel = pathIdentifier.getProjectContext().getEntityRegistry().getEntityByPath(pathIdentifier.getName());
        if (null == entityModel) {
            throw new ResolveException("Path identifier does not match any entity: " + pathIdentifier.getSourceString());
        }
        return entityModel;
    }

    /**
     * Returns the identified context of a type identifier
     * @param typeIdentifier
     * @return ModelContext
     * @throws Exception
     */
    private static ModelContext resolveTypeIdentifier(TypeIdentifier typeIdentifier) throws Exception {
        final Entity entityModel = typeIdentifier.getProjectContext().getEntityRegistry().getEntityByPath(typeIdentifier.getName());
        if (null == entityModel) {
            throw new ResolveException("Type identifier does not match any entity: " + typeIdentifier.getSourceString());
        }
        return entityModel;
    }

    /**
     * Returns the identified context of a aliased identifier
     * @param aliasedIdentifier
     * @return ModelContext
     * @throws Exception
     */
    private static Entity resolveAliasedIdentifier(AliasedIdentifier aliasedIdentifier) throws Exception {
        ModelContext ctx = aliasedIdentifier.getProjectContext().getEntityRegistry().getEntityByPath(aliasedIdentifier.getName());
        if(null == ctx) {
            //Lets try the Aliases.
            com.tibco.cep.designtime.model.Entity e = resolveEntityFromOntologyAliasTable(aliasedIdentifier.getProjectContext(),aliasedIdentifier.getName());
            if (e != null) {
                ctx = aliasedIdentifier.getProjectContext().getEntityRegistry().getEntityByPath(e.getFullPath());
            }
            if (null == ctx) {
                throw new ResolveException("FROM entity not found for: "+ aliasedIdentifier.getSourceString());
            }

        }
        return (Entity) ctx;
    }

    private static com.tibco.cep.designtime.model.Entity resolveEntityFromOntologyAliasTable(ProjectContext projContext,  String name) {
        Ontology o = projContext.getOntology();
        com.tibco.cep.designtime.model.Entity e = o.getAlias(name);
        if (e == null) return null;
        return e;
    }

    /**
     * Returns the identified context of a function identifier
     * @param functionIdentifier
     * @return ModelContext
     * @throws Exception
     */
    private static Function resolveFunctionIdentifier(FunctionIdentifier functionIdentifier) throws Exception {
        Function pfunc = null;
        if (functionIdentifier instanceof PathFunctionIdentifier) {
            pfunc = functionIdentifier.getProjectContext().getFunctionRegistry().getFunctionByPath(functionIdentifier.getName());
            if (pfunc == null) {
                com.tibco.cep.designtime.model.Entity e = resolveEntityFromOntologyAliasTable(functionIdentifier.getProjectContext(),functionIdentifier.getName());
                if (e != null) {
                    pfunc = functionIdentifier.getProjectContext().getFunctionRegistry().getFunctionByPath(e.getFullPath());
                }
            }

        }
        if(functionIdentifier instanceof AggregateFunctionIdentifier) {
            pfunc = functionIdentifier.getProjectContext().getFunctionRegistry().getFunctionByPath(Function.PATH_SEPARATOR + functionIdentifier.getName());
        }

        if (null == pfunc) {
            throw new ResolveException("Function not found for path: " + functionIdentifier.getSourceString());
        } else {
            if (functionIdentifier.getArgumentCount() != pfunc.getArgumentCount()) {
                throw new ResolveException("Expected function argument count: " + pfunc.getArgumentCount()
                        + " specified: " + functionIdentifier.getArgumentCount());
            }
            return pfunc;
        }
    }

    /**
     * Finds the ModelContext matching an Entity path in the scope of a ModelContext.
     * @param scopedContext ModelContext whose scope is searched.
     * @param entityPath String path of the Entity to find in the scope.
     * @return ModelContext matching entityPath in the scope of scopedContext.
     * @throws Exception
     */
    public static Aliased lookupAliasedByEntityPath(ModelContext scopedContext, String entityPath) throws Exception {
        if ((null == scopedContext.getOwnerContext()) || (scopedContext.getOwnerContext() == scopedContext)) {
            return null;
        }
        FromClause from = scopedContext.getOwnerContext().getFromClause();        
        final String matchingAlias = from.getAliasByEntityPath(entityPath);
        if (null == matchingAlias) {
            return lookupAliasedByEntityPath(scopedContext.getOwnerContext(), entityPath);
        }
        return from.getAliasedByAlias(matchingAlias);
    }



    /**
     * Finds the scope of a ModelContext.
     * @param scopedContext ModelContext whose scope is searched.
     * @return List of ModelContext that constitute the scope of scopedContext.
     * @throws Exception
     */
    public static Set<ModelContext> lookupScope(ModelContext scopedContext) throws Exception {
        if ((null == scopedContext) || (scopedContext instanceof NamedSelectContext)) {
            return new HashSet<ModelContext>();
        }

        Set<ModelContext> scope;

        ModelContext c;
        for (c = scopedContext.getParentContext(); !((c instanceof SelectContext) ||
                (c instanceof DeleteContext)); c = c.getParentContext()) {
            if (c instanceof Stream) {
                scope = new HashSet<ModelContext>(1);
                scope.add(c.getParentContext());
                return scope;

            } else if (c instanceof OrderClause || c instanceof HavingClause) {
                scope = new HashSet<ModelContext>(Arrays.asList(
                        c.getOwnerContext().getFromClause().getChildren()));
                for (Projection projChild : ((NamedSelectContext)c.getOwnerContext()).getProjectionAttributes().getAllProjections()) {
                    scope.add(projChild);
                    addExpressionElements(projChild.getExpression(), scope);
                }
                return scope;
            }//if
        }

        if(c instanceof SelectContext) {
            final SelectContext select = (SelectContext) c;
            if (select.isPartOfExists()) {
                scope = lookupScope(select);
            } else {
                scope = lookupScope(null);
            }
            final FromClause from = select.getFromClause();
            scope.addAll(Arrays.asList(from.getChildren()));

            return scope;
        } else {
            final DeleteContext delete = (DeleteContext) c;
            scope = lookupScope(null);
            final FromClause from = delete.getFromClause();
            scope.addAll(Arrays.asList(from.getChildren()));
            return scope;
        }
    }


    private static void addExpressionElements(Expression ctx, Set<ModelContext> set) throws ResolveException {
        if (null != ctx) {
            set.add(ctx.getIdentifiedContext());
            final ModelContext[] children = ctx.getChildren();
            if (null != children) {
                for (ModelContext childCtx : ctx.getChildren()) {
                    set.add(childCtx);
                    if (childCtx instanceof Expression) {
                        final Expression e = (Expression) childCtx;
                        set.add(e.getIdentifiedContext());
                        addExpressionElements(e, set);
                    }
                }
            }
        }
    }


    /**
     * Finds the ModelContext matching an alias name in the scope of a ModelContext.
     * @param scopedContext ModelContext whose scope is searched.
     * @param alias String alias of the ModelContext to find in the scope.
     * @return ModelContext matching alias in the scope of scopedContext.
     * @throws Exception
     */
    private static ModelContext lookupContextByAlias(ModelContext scopedContext, String alias) throws Exception {
        if ((null == scopedContext) || (null == alias)) {
            return null;
        }
        ModelContext ctx = scopedContext.getContextMap().get(alias);
        if (null == ctx) {
            final QueryContext select = scopedContext.getOwnerContext();
            if ((null != select) && (scopedContext != select)) {
                return lookupContextByAlias(select.getParentContext(), alias);
            }
            return null;
        }

        return ctx;
    }

    /**
     * Given an aliasedIdentifier it gets the Entity it actually refers to
     * @param context
     * @param alias
     * @return ModelContext
     * @throws Exception
     */
    private static ModelContext getAliasedIdentifiedContext(AliasedIdentifier context,String alias) throws Exception {
        if(null == context) {
            throw new IllegalArgumentException("Null argument passed to getAliasedIdentified context.");
        }
        if(null != alias && !context.getAlias().equals(alias)) {
            throw new IllegalArgumentException("Alias argument did not match with the Identifier.");
        }
        Entity ictx = (Entity) context.getIdentifiedContext();
        if(null == ictx) {
            return context.getProjectContext().getEntityRegistry().getEntityByPath(context.getName());
        } else {
            return context.getIdentifiedContext();
        }

    }

}
