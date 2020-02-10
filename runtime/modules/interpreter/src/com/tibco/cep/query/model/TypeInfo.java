package com.tibco.cep.query.model;

import com.tibco.cep.runtime.model.TypeManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 5, 2007
 * Time: 4:41:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TypeInfo {


    //BE supported Type Masks
    int TYPE_NULL       = 0x0;
    int TYPE_VOID		= 0x1;
    int TYPE_ATOM       = 0x2;
    int TYPE_ARRAY 	    = 0x4;
    int TYPE_PRIMITIVE  = 0x8;
    int TYPE_NUMBER     = 0x10;
    int TYPE_BOXED      = 0x20;
    int TYPE_BUILTIN    = 0x40;
    int TYPE_OBJECT 	= 0x80;
    int TYPE_COMPARABLE = 0x100;
    int TYPE_PROPERTY   = 0x200;
    int TYPE_ATTRIBUTE  = 0x400;
    int TYPE_CONCEPT    = 0x800  | TYPE_OBJECT;
    int TYPE_EVENT      = 0x1000  | TYPE_OBJECT;
    int TYPE_CONTAINED  = 0x2000  | TYPE_PROPERTY ;
    int TYPE_REFERENCE  = 0x4000  | TYPE_PROPERTY ;
    // Primitive Types
    int TYPE_CHAR 		= 0x10000  | TYPE_PRIMITIVE | TYPE_BUILTIN | TYPE_COMPARABLE;
    int TYPE_STRING 	= 0x20000  | TYPE_OBJECT    | TYPE_BUILTIN | TYPE_COMPARABLE;
    int TYPE_BOOLEAN 	= 0x40000  | TYPE_PRIMITIVE | TYPE_BUILTIN | TYPE_COMPARABLE;
    int TYPE_INTEGER   	= 0x80000 | TYPE_PRIMITIVE | TYPE_BUILTIN | TYPE_NUMBER | TYPE_COMPARABLE;
    int TYPE_LONG 		= 0x100000 | TYPE_PRIMITIVE | TYPE_BUILTIN | TYPE_NUMBER | TYPE_COMPARABLE;
    int TYPE_DOUBLE		= 0x200000 | TYPE_PRIMITIVE | TYPE_BUILTIN | TYPE_NUMBER | TYPE_COMPARABLE;
    int TYPE_DATETIME 	= 0x400000 | TYPE_OBJECT    | TYPE_BUILTIN | TYPE_COMPARABLE;
    int TYPE_PROPERTY_ARRAY 			= TYPE_ARRAY     | TYPE_PROPERTY;
    int TYPE_PROPERTY_ATOM  			= TYPE_ATOM      | TYPE_PROPERTY;
    int TYPE_PROPERTY_CONTAINED_ARRAY 	= TYPE_CONTAINED | TYPE_ARRAY;
    int TYPE_PROPERTY_REFERENCE_ARRAY 	= TYPE_REFERENCE | TYPE_ARRAY;
    int TYPE_ENTITY = TYPE_CONCEPT | TYPE_EVENT ;



    /**
     * returns the type mask
     * @return int the type mask
     */
    int getTypeFlag();


    /**
     * returns the type class
     * @return Class
     */
    Class getTypeClass();


    /**
     * returns true if the type is a concept , otherwise false
     * @return boolean returns true if the type is a concept , otherwise false
     */
    boolean isConcept();

    /**
     * returns true if the type is an event , otherwise false
     * @return boolean returns true if the type is an event  , otherwise false
     */
    boolean isEvent();

    /**
     * returns true if the type is an entity , otherwise false
     * @return boolean returns true if the type is an entity  , otherwise false
     */
    boolean isEntity();

    /**
     * returns true if the type is a property , otherwise false
     * @return boolean returns true if the type is a property  , otherwise false
     */
    boolean isProperty();

    /**
     * returns true if the type is a property atom , otherwise false
     * @return boolean returns true if the type is a property atom , otherwise false
     */
    boolean isPropertyAtom();

    /**
     * returns true if the type is a property array , otherwise false
     * @return boolean returns true if the type is a property array, otherwise false
     */
    boolean isPropertyArray();

        /**
     * returns true if the type is a referenced concept property , otherwise false
     * @return boolean returns true if the type is a referenced concept property , otherwise false
     */
    boolean isReferencedConceptProperty();
    /**
     * returns true if the type is a contained concept property , otherwise false
     * @return boolean returns true if the type is a contained concept property , otherwise false
     */
    boolean isContainedConceptProperty();

    /**
     * returns true if the type is a contained concept property array , otherwise false
     * @return boolean returns true if the type is a contained concept property array  , otherwise false
     */
    boolean isPropertyContainedArray();

    /**
     * returns true if the type is a referenced concept property array , otherwise false
     * @return boolean returns true if the type is a referenced concept property array  , otherwise false
     */
    boolean isPropertyReferencedArray();

    /**
     * returns true if the type is an attribute , otherwise false
     * @return boolean returns true if the type is an attribute  , otherwise false
     */
    boolean isAttribute();

    /**
     * returns true if the type is a Java object , otherwise false
     * @return boolean returns true if the type is a Java object  , otherwise false
     */
    boolean isJavaObject();

    /**
     * returns true if the type is a java builting type , otherwise false
     * @return boolean returns true if the type is a java builting type  , otherwise false
     */
    boolean isBuiltInJavaType();

    /**
     * returns true if the type is a Java primitive type , otherwise false
     * @return boolean returns true if the type is a Java primitive type  , otherwise false
     */
    boolean isJavaPrimitiveType();

    /**
     * 
     * @return true if it's a boolean Java type.
     */
    boolean isBoolean();
    
    /**
     * returns true if the type java boxed type , otherwise false
     * @return boolean returns true if the type is a java boxed type  , otherwise false
     */
    boolean isBoxedType();

    /**
     * returns true if the type is an atom , otherwise false
     * @return boolean returns true if the type is an atom , otherwise false
     */
    boolean isAtom();

    /**
     * returns true if the type is an array , otherwise false
     * @return boolean returns true if the type is an array  , otherwise false
     */
    boolean isArray();

    /**
     * returns true if the type is a java.lang.char type , otherwise false
     * @return boolean returns true if the type is a java.lang.char type  , otherwise false
     */
    boolean isChar();

    /**
     * returns true if the type is a java.lang.String type , otherwise false
     * @return boolean returns true if the type is a java.lang.String type  , otherwise false
     */
    boolean isString();

    /**
     * returns true if the type is a java.lang.Number type , otherwise false
     * @return boolean returns true if the type is a java.lang.Number type  , otherwise false
     */
    boolean isNumber();

    /**
     * returns true if the type implements java.lang.Comaparable , otherwise false
     * @return boolean returns true if the type implements java.lang.Comaparable type  , otherwise false
     */
    boolean isComparable();

    /**
     * returns true if the type is a java.lang.long type , otherwise false
     * @return boolean returns true if the type is a java.lang.long type  , otherwise false
     */
    boolean isLong();

    /**
     * returns true if the type is a java.lang.Integer type , otherwise false
     * @return boolean returns true if the type is a java.lang.Integer type  , otherwise false
     */
    boolean isInt();

    /**
     * returns true if the type is a java.lang.Double type , otherwise false
     * @return boolean returns true if the type is a java.lang.Double type  , otherwise false
     */
    boolean isDouble();

    /**
     * returns true if the type is a java.util.Calendar type , otherwise false
     * @return boolean returns true if the type is a java.util.Calendar type  , otherwise false
     */
    boolean isDateTime();

    /**
     * Returns the designtime class of the model type
     * @return Class the designtime class
     */
    Class getDesigntimeClass();

    /**
     * Returns the runtime implemented class of the model type
     * @param tm
     * @return Class the implemented class
     */
    Class getRuntimeClass(TypeManager tm) throws Exception;


    TypeInfo getArrayItemType();


    Object getBeNameSpaceObject();


    TypeInfo getBoxedTypeInfo(TypeManager tm) throws Exception;
}
