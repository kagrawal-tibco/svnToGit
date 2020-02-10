/**
 * 
 */
package com.tibco.cep.query.model.impl;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.TypeManager;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author pdhar
 *
 */
public class TypeInfoImpl implements TypeInfo {


    public static final TypeInfo BOOLEAN;
    public static final TypeInfo BYTE;
    public static final TypeInfo CLASS;
    public static final TypeInfo CONCEPT;
    public static final TypeInfo DATETIME;
    public static final TypeInfo DOUBLE;
    public static final TypeInfo ENTITY;
    public static final TypeInfo EVENT;
    public static final TypeInfo FLOAT;
    public static final TypeInfo INTEGER;
    public static final TypeInfo LONG;
    public static final TypeInfo OBJECT;
    public static final TypeInfo PRIMITIVE_BOOLEAN;
    public static final TypeInfo PRIMITIVE_BYTE;
    public static final TypeInfo PRIMITIVE_DOUBLE;
    public static final TypeInfo PRIMITIVE_FLOAT;
    public static final TypeInfo PRIMITIVE_INTEGER;
    public static final TypeInfo PRIMITIVE_LONG;
    public static final TypeInfo PRIMITIVE_SHORT;
    public static final TypeInfo SHORT;
    public static final TypeInfo STRING;

    private Class type;
    private TypeInfo arrayItemTypeInfo;
    private int typeFlag;
    private Object beNameSpaceObject;


    private static HashMap sigMap = new HashMap();

    static {
        sigMap.put(Boolean.class.getName(),"Z");
        sigMap.put(Byte.class.getName(),"B");
        sigMap.put(Character.class.getName(),"C");
        sigMap.put(Short.class.getName(),"S");
        sigMap.put(Long.class.getName(),"J");
        sigMap.put(Integer.class.getName(),"I");
        sigMap.put(Float.class.getName(),"F");
        sigMap.put(Double.class.getName(),"D");
        sigMap.put("null","N");
        sigMap.put(void.class.getName(),"V");

        try {
            BOOLEAN = new TypeInfoImpl(Boolean.class);
            BYTE = new TypeInfoImpl(Byte.class);
            CLASS = new TypeInfoImpl(Class.class);
            CONCEPT = new TypeInfoImpl(Concept.class);
            DATETIME = new TypeInfoImpl(Calendar.class);
            DOUBLE = new TypeInfoImpl(Double.class);
            ENTITY = new TypeInfoImpl(Entity.class);
            EVENT = new TypeInfoImpl(Event.class);
            FLOAT = new TypeInfoImpl(Float.class);
            INTEGER = new TypeInfoImpl(Integer.class);
            LONG = new TypeInfoImpl(Long.class);
            OBJECT = new TypeInfoImpl(Object.class);
            PRIMITIVE_BOOLEAN = new TypeInfoImpl(boolean.class);
            PRIMITIVE_BYTE = new TypeInfoImpl(byte.class);
            PRIMITIVE_DOUBLE = new TypeInfoImpl(double.class);
            PRIMITIVE_FLOAT = new TypeInfoImpl(float.class);
            PRIMITIVE_INTEGER = new TypeInfoImpl(int.class);
            PRIMITIVE_LONG = new TypeInfoImpl(long.class);
            PRIMITIVE_SHORT = new TypeInfoImpl(short.class);
            SHORT = new TypeInfoImpl(Short.class);
            STRING = new TypeInfoImpl(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize constants.", e);
        }
    }

    private TypeInfoImpl(Class clazz, int typeFlag, Object beNameSpaceObject) {
        this.type = clazz;
        this.typeFlag = typeFlag;
        this.beNameSpaceObject = beNameSpaceObject;
    }

    public TypeInfoImpl(Object classOrInstance) throws Exception {
        typeFlag = 0;
        if(null == classOrInstance) {
            typeFlag |= TYPE_NULL;
            return;
        }
        boolean isInstance = ((classOrInstance instanceof Class) ? false : true);
        Class clazz = (Class) ((classOrInstance instanceof Class) ? classOrInstance : classOrInstance.getClass());
        this.type = clazz;
        if (void.class.getName().equals(clazz.getName())) {
            typeFlag |= TYPE_VOID;
        }
        if (TypeHelper.isBuiltinJavaDataType(clazz)) {
            typeFlag |= TYPE_BUILTIN;
            if (clazz.isPrimitive()) {
                typeFlag |= TYPE_PRIMITIVE;
            } else {
                typeFlag |= TYPE_OBJECT;
            }
            if (clazz.isArray()) {
                typeFlag |= TYPE_ARRAY;
            } else {
                typeFlag |= TYPE_ATOM;
            }
            Class boxedType = TypeHelper.getBoxedClass(clazz);
            if (TypeHelper.isBoxedType(clazz)) {
                typeFlag |= TYPE_BOXED;
            }

            if(Comparable.class.isAssignableFrom(boxedType)) {
                typeFlag |= TYPE_COMPARABLE;
            }

            if (boxedType.equals(Number.class) || Number.class.isAssignableFrom(boxedType)) {
                typeFlag |= TYPE_NUMBER;
            }
            if (boxedType.equals(Character.class) || boxedType.equals(char.class)) {
                typeFlag |= TYPE_CHAR;
            }
            if (boxedType.equals(Boolean.class) || boxedType.equals(boolean.class)) {
                typeFlag |= TYPE_BOOLEAN;
            }
            if (boxedType.equals(Byte.class) || boxedType.equals(byte.class)) {
                typeFlag |= TYPE_INTEGER;
            }
            if (boxedType.equals(Short.class) || boxedType.equals(short.class)) {
                typeFlag |= TYPE_INTEGER;
            }
            if (boxedType.equals(Integer.class) || boxedType.equals(int.class)) {
                typeFlag |= TYPE_INTEGER;
            }
            if (boxedType.equals(Long.class) || boxedType.equals(long.class)) {
                typeFlag |= TYPE_LONG;
            }
            if (boxedType.equals(Float.class) || boxedType.equals(float.class)) {
                typeFlag |= TYPE_DOUBLE;
            }
            if (boxedType.equals(Double.class) || boxedType.equals(double.class)) {
                typeFlag |= TYPE_DOUBLE;
            }
        } else if (TypeHelper.isJavaBuiltinDataTypeArray(clazz)) {
            typeFlag |= TYPE_ARRAY | TYPE_BUILTIN;
            this.arrayItemTypeInfo = new TypeInfoImpl(clazz.getComponentType());
        } else if (clazz.isArray()) {
            typeFlag |= TYPE_ARRAY;
            this.arrayItemTypeInfo = new TypeInfoImpl(clazz.getComponentType());

        } else {
//    		 DesignTime
            if (com.tibco.cep.designtime.model.element.Concept.class.isAssignableFrom(clazz)) {
                typeFlag |= TYPE_CONCEPT;
                if (isInstance == true && classOrInstance instanceof Concept) {
                    beNameSpaceObject = classOrInstance;
                }
            }
            else if (com.tibco.cep.designtime.model.event.Event.class.isAssignableFrom(clazz)) {
                typeFlag |= TYPE_EVENT;
                if (isInstance == true && classOrInstance instanceof Event) {
                    beNameSpaceObject = classOrInstance;
                }
            }
            else if (com.tibco.cep.designtime.model.element.PropertyDefinition.class.isAssignableFrom(clazz)) {
                typeFlag |= TYPE_PROPERTY;
                if (classOrInstance != null && classOrInstance instanceof com.tibco.cep.designtime.model.element.PropertyDefinition)
                {
                    com.tibco.cep.designtime.model.element.PropertyDefinition pd = (PropertyDefinition) classOrInstance;
                    PropertyTypeInfo ti = getTypeInfoFromPropertyDefinition
                            ((com.tibco.cep.designtime.model.element.PropertyDefinition) classOrInstance);
                    typeFlag |= ti.getTypeFlag();
                    beNameSpaceObject = ti.getBeNameSpaceObject();
                    this.type = ti.getClazz();
                    if (pd.isArray()) {
                        this.arrayItemTypeInfo = new TypeInfoImpl(ti.getArrayItemClazz(), ti.getArrayItemTypeFlag(),
                            ti.getBeNameSpaceObject());
                    }
                }
            }
            else if (com.tibco.cep.designtime.model.event.EventPropertyDefinition.class.isAssignableFrom(clazz)) {
                typeFlag |= TYPE_PROPERTY;
                if (classOrInstance != null && classOrInstance instanceof com.tibco.cep.designtime.model.event.EventPropertyDefinition)
                {
                    com.tibco.cep.designtime.model.event.EventPropertyDefinition pd = (EventPropertyDefinition) classOrInstance;
                    PropertyTypeInfo ti = getTypeInfoFromPropertyDefinition
                            ((com.tibco.cep.designtime.model.event.EventPropertyDefinition) classOrInstance);
                    typeFlag |= ti.getTypeFlag();
                    beNameSpaceObject = pd.getOwner().getFullPath();
                    this.type = ti.getClazz();
                }
            }

            // Runtime
            else if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(clazz)) {
                typeFlag |= TYPE_CONCEPT;
            }
            else if (com.tibco.cep.kernel.model.entity.Event.class.isAssignableFrom(clazz)) {
                typeFlag |= TYPE_EVENT;
            }
            else if (com.tibco.cep.runtime.model.element.PropertyAtom.class.isAssignableFrom(clazz)) {
                typeFlag |= (TYPE_PROPERTY | TYPE_ATOM);
                if (com.tibco.cep.runtime.model.element.Property.PropertyContainedConcept.class.isAssignableFrom(clazz))
                {
                    typeFlag |= TYPE_CONTAINED;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyConceptReference.class.isAssignableFrom(clazz))
                {
                    typeFlag |= TYPE_REFERENCE;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyBoolean.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_BOOLEAN;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyDouble.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_DOUBLE;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyInt.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_INTEGER;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyLong.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_LONG;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyString.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_STRING;
                }
                if (com.tibco.cep.runtime.model.element.Property.PropertyDateTime.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_DATETIME;
                }
            }
            else if (com.tibco.cep.runtime.model.element.PropertyArray.class.isAssignableFrom(clazz)) {
                typeFlag |= (TYPE_PROPERTY | TYPE_ARRAY);
                if (com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_CONTAINED;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayConceptReference.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_REFERENCE;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayBoolean.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_BOOLEAN;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayString.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_STRING;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayInt.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_INTEGER;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayLong.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_LONG;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayDouble.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_DOUBLE;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayDateTime.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_DATETIME;
                }
                if (com.tibco.cep.runtime.model.element.PropertyArrayBoolean.class.isAssignableFrom(clazz)) {
                    typeFlag |= TYPE_BOOLEAN;
                }

            }
        }

    }


    /**
     * @return Class the java class of the Result Type
     */
    public Class getTypeClass(){
        return type;
    }

    public int getTypeFlag() {
        return typeFlag;
    }



    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof TypeInfoImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final TypeInfoImpl that = (TypeInfoImpl) o;
        return this.type.equals(that.type)
                && (this.typeFlag == that.typeFlag)
                && (((null == this.arrayItemTypeInfo) && (null == that.arrayItemTypeInfo))
                    || (null != this.arrayItemTypeInfo) && this.arrayItemTypeInfo.equals(that.arrayItemTypeInfo));
    }


    public int hashCode() {
        long longHash = this.type.hashCode();
        longHash = longHash * 29 + this.typeFlag;
        longHash ^= (longHash >>> 32);
        longHash = longHash * 29 + ((null == this.arrayItemTypeInfo) ? 0 : this.arrayItemTypeInfo.hashCode());
        longHash ^= (longHash >>> 32);
        return (int) longHash;        
    }


    public Object getBeNameSpaceObject() {
        return beNameSpaceObject;
    }


    private class PropertyTypeInfo {
        int typeFlag;
        int arrayItemTypeFlag;
        Object beNameSpaceObject;
        Class clazz;
        Class arrayItemClazz;

        PropertyTypeInfo(int typeFlag, int arrayItemTypeFlag, Object beNamespaceObject, Class clazz, Class arrayItemClazz) {
            this.typeFlag = typeFlag;
            this.arrayItemTypeFlag = arrayItemTypeFlag;
            this.beNameSpaceObject = beNamespaceObject;
            this.clazz = clazz;
            this.arrayItemClazz = arrayItemClazz;
        }

        public Object getBeNameSpaceObject() {
            return beNameSpaceObject;
        }

        public Class getClazz() {
            return clazz;
        }

        public Class getArrayItemClazz() {
            return arrayItemClazz;
        }

        public int getTypeFlag() {
            return typeFlag;
        }

        public int getArrayItemTypeFlag() {
            return arrayItemTypeFlag;
        }
    }
    /**
     * This method maps the BE concept property type to Query Types
     * @param pd
     * @return TypeInfoImpl
     */
    private  PropertyTypeInfo getTypeInfoFromPropertyDefinition(PropertyDefinition pd) throws Exception {
        int typeFlag = 0;
        int arrayItemTypeFlag = 0;
        Object beNamespaceObject=pd;
        Class clazz = null;
        Class arrayItemClazz = null;
        if(pd.isArray()) {
            typeFlag |= TypeInfoImpl.TYPE_PROPERTY_ARRAY;
        } else {
            typeFlag |= TypeInfoImpl.TYPE_PROPERTY_ATOM;
        }
        if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT){
            final TypeInfo ti = new TypeInfoImpl(pd.getConceptType());
            typeFlag |= ti.getTypeFlag() | TypeInfoImpl.TYPE_CONTAINED;
            this.beNameSpaceObject = ti.getBeNameSpaceObject();
            if(pd.isArray()) {
                arrayItemTypeFlag |= ti.getTypeFlag() | TypeInfoImpl.TYPE_CONTAINED;
                clazz = Array.newInstance(ti.getDesigntimeClass(), 0).getClass();
                arrayItemClazz = pd.getConceptType().getClass();
            } else {
                clazz = ti.getDesigntimeClass();
                arrayItemClazz = null;                                      
            }
//    		final TypeManager tm = context.getProjectContext().getRuleServiceProvider().getTypeManager();
//            TypeManager.TypeDescriptor td = tm.getTypeDescriptor(pd.getConceptTypePath());
//            clazz = td.getImplClass();
//            if(pd.isArray()) {
//            	clazz = Class.forName(clazz.getName()+"[].class");
//            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE){
            final TypeInfo ti = new TypeInfoImpl(pd.getConceptType());
            typeFlag |= ti.getTypeFlag() | TypeInfoImpl.TYPE_REFERENCE;
            this.beNameSpaceObject = ti.getBeNameSpaceObject();
            if(pd.isArray()) {
                arrayItemTypeFlag |= ti.getTypeFlag() | TypeInfoImpl.TYPE_REFERENCE ;
                clazz = Array.newInstance(ti.getDesigntimeClass(), 0).getClass();
                arrayItemClazz = pd.getConceptType().getClass();
            } else {
                clazz = ti.getDesigntimeClass();
                arrayItemClazz = null;
            }
//    		final TypeManager tm = context.getProjectContext().getRuleServiceProvider().getTypeManager();
//            TypeManager.TypeDescriptor td = tm.getTypeDescriptor(pd.getConceptTypePath());
//            clazz = td.getImplClass();
//            if(pd.isArray()) {
//            	clazz = Class.forName(clazz.getName()+"[].class");
//            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_BOOLEAN) {
            typeFlag |= TypeInfoImpl.TYPE_BOOLEAN;
            if(pd.isArray()) {
                clazz = boolean[].class;
                arrayItemClazz = boolean.class;
                arrayItemTypeFlag |= PRIMITIVE_BOOLEAN.getTypeFlag();

            } else {
                clazz = boolean.class;
                arrayItemClazz = null;
            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_INTEGER) {
            typeFlag |= TypeInfoImpl.TYPE_INTEGER;
            if(pd.isArray()) {
                clazz = int[].class;
                arrayItemClazz = int.class;
                arrayItemTypeFlag |= PRIMITIVE_INTEGER.getTypeFlag();
            } else {
                clazz = int.class;
                arrayItemClazz = null;
            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_LONG) {
            typeFlag |= TypeInfoImpl.TYPE_LONG;
            if(pd.isArray()) {
                clazz = long[].class;
                arrayItemClazz = long.class;
                arrayItemTypeFlag |= PRIMITIVE_LONG.getTypeFlag();
            } else {
                clazz = long.class;
                arrayItemClazz = null;
            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_STRING) {
            typeFlag |= TypeInfoImpl.TYPE_STRING;
            if(pd.isArray()) {
                clazz = String[].class;
                arrayItemClazz = String.class;
                arrayItemTypeFlag |= STRING.getTypeFlag();
            } else {
                clazz = String.class;
                arrayItemClazz = null;
            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_REAL) {
            typeFlag |= TypeInfoImpl.TYPE_DOUBLE;
            if(pd.isArray()) {
                clazz = double[].class;
                arrayItemClazz = double.class;
                arrayItemTypeFlag |= PRIMITIVE_DOUBLE.getTypeFlag();
            } else {
                clazz = double.class;
                arrayItemClazz = null;
            }
        }
        else if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_DATETIME) {
            typeFlag |= TypeInfoImpl.TYPE_DATETIME;
            if(pd.isArray()) {
                clazz = Calendar[].class;
                arrayItemClazz = Calendar.class;
                arrayItemTypeFlag |= DATETIME.getTypeFlag();
            } else {
                clazz = Calendar.class;
                arrayItemClazz = null;
            }
        }
        return new PropertyTypeInfo(typeFlag, arrayItemTypeFlag, beNamespaceObject, clazz, arrayItemClazz);
    }



    /**
     * Returns the typeFlag from the Event property definition
     * @param pd
     * @return TypeInfoImpl
     */
    private PropertyTypeInfo getTypeInfoFromPropertyDefinition(EventPropertyDefinition pd) throws Exception{
        int typeFlag = 0;
        Class clazz = null;
        Object beNamespaceObject = pd;
        typeFlag |= TypeInfoImpl.TYPE_ATOM | TypeInfoImpl.TYPE_PROPERTY;
        RDFPrimitiveTerm type = pd.getType();
        if(type.equals(RDFTypes.INTEGER)) {
            typeFlag |= TypeInfoImpl.TYPE_INTEGER;
            clazz = int.class;
        }
        if(type.equals(RDFTypes.BOOLEAN)){
            typeFlag |= TypeInfoImpl.TYPE_BOOLEAN;
            clazz = boolean.class;
        }
        if(type.equals(RDFTypes.LONG)){
            typeFlag |= TypeInfoImpl.TYPE_LONG;
            clazz = long.class;
        }
        if(type.equals(RDFTypes.DOUBLE)){
            typeFlag |= TypeInfoImpl.TYPE_DOUBLE;
            clazz = double.class;
        }
        if(type.equals(RDFTypes.STRING)){
            typeFlag |= TypeInfoImpl.TYPE_STRING;
            clazz = String.class;
        }
        if(type.equals(RDFTypes.DATETIME)){
            typeFlag |= TypeInfoImpl.TYPE_DATETIME;
            clazz = Calendar.class;
        }
        if(clazz == null) {
            return null;
        } else {
            return new PropertyTypeInfo(typeFlag, 0, beNamespaceObject, clazz, null);
        }
    }

    /**
     * returns true if the type is an array , otherwise false
     *
     * @return boolean returns true if the type is an array  , otherwise false
     */
    public boolean isArray() {
        return ((typeFlag & TYPE_ARRAY) == TYPE_ARRAY );
    }

    /**
     * returns true if the type is an atom , otherwise false
     *
     * @return boolean returns true if the type is an atom , otherwise false
     */
    public boolean isAtom() {
        return ((typeFlag & TYPE_ATOM) == TYPE_ATOM );
    }

    /**
     * returns true if the type is an attribute , otherwise false
     *
     * @return boolean returns true if the type is an attribute  , otherwise false
     */
    public boolean isAttribute() {
        return ((typeFlag & TYPE_ATTRIBUTE) == TYPE_ATTRIBUTE );
    }

    /**
     * returns true if the type java boxed type , otherwise false
     *
     * @return boolean returns true if the type is a java boxed type  , otherwise false
     */
    public boolean isBoxedType() {
        return ((typeFlag & TYPE_BOXED) == TYPE_BOXED );
    }

    /**
     * returns true if the type java boolean type , otherwise false
     *
     * @return returns true if the type java boolean type , otherwise false
     */
    public boolean isBoolean() {
        return ((typeFlag & TYPE_BOOLEAN) == TYPE_BOOLEAN);
    }
    
    
    /**
     * returns true if the type is a java builting type , otherwise false
     *
     * @return boolean returns true if the type is a java builting type  , otherwise false
     */
    public boolean isBuiltInJavaType() {
        return ((typeFlag & TYPE_BUILTIN) == TYPE_BUILTIN );
    }

    /**
     * returns true if the type is a java.lang.char type , otherwise false
     *
     * @return boolean returns true if the type is a java.lang.char type  , otherwise false
     */
    public boolean isChar() {
        return ((typeFlag & TYPE_CHAR) == TYPE_CHAR );
    }

    /**
     * returns true if the type is a concept , otherwise false
     *
     * @return boolean returns true if the type is a concept , otherwise false
     */
    public boolean isConcept() {
        return ((typeFlag & TYPE_CONCEPT) == TYPE_CONCEPT);
    }

    /**
     * returns true if the type is a contained concept property , otherwise false
     *
     * @return boolean returns true if the type is a contained concept property , otherwise false
     */
    public boolean isContainedConceptProperty() {
        return ((typeFlag & TYPE_CONTAINED) == TYPE_CONTAINED );
    }

    /**
     * returns true if the type is a referenced concept property , otherwise false
     *
     * @return boolean returns true if the type is a referenced concept property , otherwise false
     */
    public boolean isReferencedConceptProperty() {
        return ((typeFlag & TYPE_REFERENCE) == TYPE_REFERENCE);
    }

    /**
     * returns true if the type is a java.util.Calendar type , otherwise false
     *
     * @return boolean returns true if the type is a java.util.Calendar type  , otherwise false
     */
    public boolean isDateTime() {
        return ((typeFlag & TYPE_DATETIME) == TYPE_DATETIME);
    }

    /**
     * returns true if the type is a java.lang.Double type , otherwise false
     *
     * @return boolean returns true if the type is a java.lang.Double type  , otherwise false
     */
    public boolean isDouble() {
        return ((typeFlag & TYPE_DOUBLE) == TYPE_DOUBLE);
    }

    /**
     * returns true if the type is an entity , otherwise false
     *
     * @return boolean returns true if the type is an entity  , otherwise false
     */
    public boolean isEntity() {
        return this.isEvent() || this.isConcept();
    }

    /**
     * returns true if the type is an event , otherwise false
     *
     * @return boolean returns true if the type is an event  , otherwise false
     */
    public boolean isEvent() {
        return ((typeFlag & TYPE_EVENT) == TYPE_EVENT);
    }

    /**
     * returns true if the type is a java.lang.Integer type , otherwise false
     *
     * @return boolean returns true if the type is a java.lang.Integer type  , otherwise false
     */
    public boolean isInt() {
        return ((typeFlag & TYPE_INTEGER) == TYPE_INTEGER);
    }

    /**
     * returns true if the type is a Java object , otherwise false
     *
     * @return boolean returns true if the type is a Java object  , otherwise false
     */
    public boolean isJavaObject() {
        return ((typeFlag & TYPE_OBJECT) == TYPE_OBJECT);
    }

    /**
     * returns true if the type is a Java primitive type , otherwise false
     *
     * @return boolean returns true if the type is a Java primitive type  , otherwise false
     */
    public boolean isJavaPrimitiveType() {
        return ((typeFlag & TYPE_PRIMITIVE) == TYPE_PRIMITIVE);
    }

    /**
     * returns true if the type is a java.lang.long type , otherwise false
     *
     * @return boolean returns true if the type is a java.lang.long type  , otherwise false
     */
    public boolean isLong() {
       return ((typeFlag & TYPE_LONG) == TYPE_LONG);
    }

    /**
     * returns true if the type is a property , otherwise false
     *
     * @return boolean returns true if the type is a property  , otherwise false
     */
    public boolean isProperty() {
        return ((typeFlag & TYPE_PROPERTY) == TYPE_PROPERTY);
    }

    /**
     * returns true if the type is a java.lang.String type , otherwise false
     *
     * @return boolean returns true if the type is a java.lang.String type  , otherwise false
     */
    public boolean isString() {
        return ((typeFlag & TYPE_STRING) == TYPE_STRING);
    }

    /**
     * returns true if the type is a java.lang.Number type , otherwise false
     *
     * @return boolean returns true if the type is a java.lang.Number type  , otherwise false
     */
    public boolean isNumber() {
        return ((typeFlag & TYPE_NUMBER) == TYPE_NUMBER);
    }

    /**
     * returns true if the type implements java.lang.Comaparable , otherwise false
     *
     * @return boolean returns true if the type implements java.lang.Comaparable type  , otherwise false
     */
    public boolean isComparable() {
        return ((typeFlag & TYPE_COMPARABLE) == TYPE_COMPARABLE);
    }

    /**
     * returns true if the type is a property array , otherwise false
     *
     * @return boolean returns true if the type is a property array, otherwise false
     */
    public boolean isPropertyArray() {
        return ((typeFlag & TYPE_PROPERTY_ARRAY) == TYPE_PROPERTY_ARRAY);
    }

    /**
     * returns true if the type is a property atom , otherwise false
     *
     * @return boolean returns true if the type is a property atom , otherwise false
     */
    public boolean isPropertyAtom() {
        return ((typeFlag & TYPE_PROPERTY_ATOM) == TYPE_PROPERTY_ATOM);
    }

    /**
     * returns true if the type is a contained concept property array , otherwise false
     *
     * @return boolean returns true if the type is a contained concept property array  , otherwise false
     */
    public boolean isPropertyContainedArray() {
        return ((typeFlag & TYPE_PROPERTY_CONTAINED_ARRAY) == TYPE_PROPERTY_CONTAINED_ARRAY);
    }

    /**
     * returns true if the type is a referenced concept property array , otherwise false
     *
     * @return boolean returns true if the type is a referenced concept property array  , otherwise false
     */
    public boolean isPropertyReferencedArray() {
        return ((typeFlag & TYPE_PROPERTY_REFERENCE_ARRAY) == TYPE_PROPERTY_REFERENCE_ARRAY);
    }

    /**
     * Returns the designtime class of the model type
     *
     * @return Class the designtime class
     */
    public Class getDesigntimeClass() {
        return this.type;
    }


    public TypeInfo getArrayItemType() {
        return this.arrayItemTypeInfo;
    }


    public TypeInfo getBoxedTypeInfo(TypeManager tm) throws Exception {
        final Class runtimeClass = this.getRuntimeClass(tm);
        if (runtimeClass.isPrimitive()) {
            return new TypeInfoImpl(TypeHelper.getBoxedClass(this.type));
        }
        return this;
    }


    public TypeInfo getUnboxedTypeInfo(TypeManager tm) throws Exception {
        final Class runtimeClass = this.getRuntimeClass(tm);
        if (runtimeClass.isPrimitive()) {
            return this;
        }
        return new TypeInfoImpl(TypeHelper.getUnboxedClass(this.type));
    }


    /**
     * Returns the runtime implemented class of the model type
     *
     * @param tm
     * @return Class the implemented class
     */
    public Class getRuntimeClass(TypeManager tm) throws Exception {
        if (this.isBuiltInJavaType()) {
            // Java built-in types
            return this.type;

        } else if (this.isPropertyAtom()) {
//                      if (this.beNameSpaceObject instanceof EventPropertyDefinition) {
//                          EventPropertyDefinition pd = (EventPropertyDefinition) this.beNameSpaceObject;
//                          entity = pd.getOwner();
//                          return this.type;
//                      }
            if (this.isContainedConceptProperty()) {
                if (null != this.beNameSpaceObject &&
                        this.beNameSpaceObject instanceof PropertyDefinition) {
                    com.tibco.cep.designtime.model.Entity entity;
                    PropertyDefinition pd = (PropertyDefinition) this.beNameSpaceObject;
                    entity = pd.getConceptType();
                    TypeManager.TypeDescriptor td = tm.getTypeDescriptor(entity.getFullPath());
                    return td.getImplClass();
                } // end null != beNamespaceObject
                else {
                    throw new IllegalStateException("NamespaceObject not found");
                }

            } else if (this.isReferencedConceptProperty()) {
                if (null != this.beNameSpaceObject &&
                        this.beNameSpaceObject instanceof PropertyDefinition) {
                    com.tibco.cep.designtime.model.Entity entity;
                    PropertyDefinition pd = (PropertyDefinition) this.beNameSpaceObject;
                    entity = pd.getConceptType();
                    TypeManager.TypeDescriptor td = tm.getTypeDescriptor(entity.getFullPath());
                    return td.getImplClass();
                } // end null != beNamespaceObject
                else {
                    throw new IllegalStateException("NamespaceObject not found");
                }

            } else {
                return this.type;
            }
        } else if (this.isPropertyArray()) {
            if (this.isPropertyContainedArray()) {
                if (null != this.beNameSpaceObject &&
                        this.beNameSpaceObject instanceof PropertyDefinition) {
                    com.tibco.cep.designtime.model.Entity entity;
                    PropertyDefinition pd = (PropertyDefinition) this.beNameSpaceObject;
                    entity = pd.getConceptType();
                    TypeManager.TypeDescriptor td = tm.getTypeDescriptor(entity.getFullPath());
                    return td.getImplClass();
                } // end null != beNamespaceObject
                else {
                    throw new IllegalStateException("NamespaceObject not found");
                }

            } else if (this.isPropertyReferencedArray()) {
                if (null != this.beNameSpaceObject &&
                        this.beNameSpaceObject instanceof PropertyDefinition) {
                    com.tibco.cep.designtime.model.Entity entity;
                    PropertyDefinition pd = (PropertyDefinition) this.beNameSpaceObject;
                    entity = pd.getConceptType();
                    TypeManager.TypeDescriptor td = tm.getTypeDescriptor(entity.getFullPath());
                    return td.getImplClass();
                } // end null != beNamespaceObject
                else {
                    throw new IllegalStateException("NamespaceObject not found");
                }

            } else {
                return this.type;
            }

        } else if (this.isArray()) {
            return Array.newInstance(this.getArrayItemType().getRuntimeClass(tm), 0).getClass();

        } else if (this.isConcept()) {
            if (Entity.class.equals(this.type)
                    || com.tibco.cep.kernel.model.entity.Entity.class.equals(this.type)) {
                return com.tibco.cep.kernel.model.entity.Entity.class;
            } else if (Concept.class.equals(this.type)
                    || com.tibco.cep.runtime.model.element.Concept.class.equals(this.type)) {
                return com.tibco.cep.runtime.model.element.Concept.class;
            } else if (com.tibco.cep.kernel.model.entity.Entity.class.isAssignableFrom(this.type)) {
                return this.type;
            } else if (null != this.beNameSpaceObject) {
                TypeManager.TypeDescriptor td = null;
                if (this.beNameSpaceObject instanceof com.tibco.cep.designtime.model.element.Concept) {
                    com.tibco.cep.designtime.model.element.Concept c = (com.tibco.cep.designtime.model.element.Concept) this.beNameSpaceObject;
                    td = tm.getTypeDescriptor(c.getFullPath());
                } else if (this.beNameSpaceObject instanceof PropertyDefinition) {
                    PropertyDefinition pd = (PropertyDefinition) this.beNameSpaceObject;
                    td = tm.getTypeDescriptor(pd.getConceptTypePath());
                }
                return td.getImplClass();
            }

        } else if (this.isEvent()) {
            if (Entity.class.equals(this.type)
                    || com.tibco.cep.kernel.model.entity.Entity.class.equals(this.type)) {
                return com.tibco.cep.kernel.model.entity.Entity.class;
            } else if (Event.class.equals(this.type)
                    || com.tibco.cep.kernel.model.entity.Event.class.equals(type)) {
                return com.tibco.cep.kernel.model.entity.Event.class;
            } else if (null != this.beNameSpaceObject) {
                com.tibco.cep.designtime.model.event.Event e = (com.tibco.cep.designtime.model.event.Event) this.beNameSpaceObject;
                TypeManager.TypeDescriptor td = tm.getTypeDescriptor(e.getFullPath());
                return td.getImplClass();
            }

        } else if (this.equals(TypeInfoImpl.ENTITY)) {
            return com.tibco.cep.kernel.model.entity.Entity.class;

        } else if (this.type.equals(com.tibco.cep.kernel.model.entity.Entity.class)) {
            return com.tibco.cep.kernel.model.entity.Entity.class;
        }

        return this.type;
    }


//    public static void main(String[] args) {
//        TypeInfo t;
//
//        try {
//            t= new TypeInfoImpl(int.class);
//            t= new TypeInfoImpl(int[].class);
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }

}
