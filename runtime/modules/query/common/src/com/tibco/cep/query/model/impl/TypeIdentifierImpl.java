package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeIdentifier;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.resolution.ResolutionHelper;


/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 8, 2008
* Time: 2:06:19 PM
*/
public class TypeIdentifierImpl
        extends IdentifierImpl
        implements TypeIdentifier {


    protected TypeIdentifierImpl(ModelContext parentContext, CommonTree tree, java.lang.String name, TypeInfo typeInfo) {
        super(parentContext, tree, name);
        this.setTypeInfo(typeInfo);
    }


    public boolean resolveContext() throws Exception {
        return this.isResolved();
    }


    public static class Boolean extends TypeIdentifierImpl {
        public Boolean(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.PRIMITIVE_BOOLEAN);
        }
    }


    public static class Concept extends Entity {
        public Concept(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, TypeInfoImpl.CONCEPT);
            this.isBaseType = true;
        }
        public Concept(ModelContext parentContext, CommonTree tree, java.lang.String path) {
            super(parentContext, tree, path);
            this.isBaseType = false;
        }
    }

    public static class DateTime extends TypeIdentifierImpl {
        public DateTime(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.DATETIME);
        }
    }

    public static class Double extends TypeIdentifierImpl {
        public Double(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.PRIMITIVE_DOUBLE);
        }
    }

    public static class Entity extends TypeIdentifierImpl {
        boolean isBaseType;

        public Entity(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.ENTITY);
            this.isBaseType = true;
        }

        public Entity(ModelContext parentContext, CommonTree tree, java.lang.String path) {
            super(parentContext, tree, path, null);
            this.isBaseType = false;
        }

        protected Entity(ModelContext parentContext, CommonTree tree, TypeInfo typeInfo) {
            super(parentContext, tree, tree.getText(), typeInfo);
            this.isBaseType = false;
        }

        public boolean isResolved() {
            return this.isBaseType || super.isResolved();
        }

        public boolean resolveContext() throws Exception {
            if (!this.isResolved()) {
                this.setIdentifiedContext(ResolutionHelper.resolveIdentifier(this));
            }
            return this.isResolved();
        }
    }

    public static class Event extends Entity {
        public Event(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, TypeInfoImpl.EVENT);
            this.isBaseType = true;
        }
        public Event(ModelContext parentContext, CommonTree tree, java.lang.String path) {
            super(parentContext, tree, path);
            this.isBaseType = false;
        }
    }

    public static class Integer extends TypeIdentifierImpl {
        public Integer(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.PRIMITIVE_INTEGER);
        }
    }

    public static class Long extends TypeIdentifierImpl {
        public Long(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.PRIMITIVE_LONG);
        }
    }

    public static class Object extends TypeIdentifierImpl {
        public Object(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.OBJECT);
        }
    }

    public static class String extends TypeIdentifierImpl {
        public String(ModelContext parentContext, CommonTree tree) {
            super(parentContext, tree, tree.getText(), TypeInfoImpl.STRING);
        }
    }

}
