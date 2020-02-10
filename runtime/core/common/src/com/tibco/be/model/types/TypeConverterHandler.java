package com.tibco.be.model.types;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 3:33:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TypeConverterHandler {
    /**
     *
     * @param tc
     */
    void addConverter(TypeConverter tc);
    /**
     *
     * @param nativeClass
     * @param foreignClass
     * @param tc
     */
    void addConverter(Class nativeClass, Class foreignClass, TypeConverter tc);


    /**
     *
     * @param nativeClass
     * @param foreignClass
     * @return
     */
    TypeConverter nativeToForeign(Class nativeClass, Class foreignClass);


    /**
     *
     * @param nativeClass
     * @param foreignClass
     * @return
     */
    TypeRenderer foreignToNative(Class nativeClass, Class foreignClass);
}
