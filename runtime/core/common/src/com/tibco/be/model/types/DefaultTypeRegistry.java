package com.tibco.be.model.types;

import java.util.HashMap;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 6:08:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTypeRegistry implements TypeConverterHandler{

    final ExpandedName registryName;
    HashMap converters= new HashMap();
    HashMap renderers= new HashMap();
    ThreadLocal n2f = new ThreadLocal();
    ThreadLocal f2n = new ThreadLocal();

    /**
     *
     * @param registryName
     */
    public DefaultTypeRegistry(ExpandedName registryName) {
        this.registryName=registryName;
    }

    /**
     *
     * @param tc
     */
    public void addConverter(TypeConverter tc) {
        addConverter(tc.getNativeClass(), tc.getForeignClass(), tc);
    }

    /**
     *
     * @param nativeClass
     * @param foreignClass
     * @param tc
     */
    public void addConverter(Class nativeClass, Class foreignClass, TypeConverter tc) {
        converters.put(new TwoWayKey(nativeClass, foreignClass), tc);
        if (tc instanceof TypeRenderer) {
            renderers.put(new TwoWayKey(nativeClass, foreignClass), tc);
        }
    }

    /**
     *
     * @param nativeClass
     * @param foreignClass
     * @return
     */
    public TypeConverter nativeToForeign(Class nativeClass, Class foreignClass) {
        TwoWayKey tw=(TwoWayKey) n2f.get();
        if (tw == null) {
            tw=new TwoWayKey(nativeClass, foreignClass);
            n2f.set(tw);
            return (TypeConverter) converters.get(tw);
        } else {
            tw.foreignClass=foreignClass;
            tw.nativeClass=nativeClass;
            return (TypeConverter) converters.get(tw);
        }
    }

    /**
     *
     * @param nativeClass
     * @param foreignClass
     * @return
     */
    public TypeRenderer foreignToNative(Class nativeClass, Class foreignClass) {
        TwoWayKey tw=(TwoWayKey) f2n.get();
        if (tw == null) {
            tw=new TwoWayKey(nativeClass, foreignClass);
            f2n.set(tw);
            return (TypeRenderer) renderers.get(tw);
        } else {
            tw.foreignClass=foreignClass;
            tw.nativeClass=nativeClass;
            return (TypeRenderer) renderers.get(tw);
        }
    }

    class TwoWayKey {
        Class nativeClass;
        Class foreignClass;

        TwoWayKey(Class nativeClass, Class foreignClass) {
            this.nativeClass=nativeClass;
            this.foreignClass=foreignClass;
        }

        /**
         *
         * @param obj
         * @return
         */
        public boolean equals(Object obj) {
            boolean eq= super.equals(obj);
            if (!eq) {
                if (obj instanceof TwoWayKey) {
                    return (((TwoWayKey)obj).nativeClass == nativeClass) && (((TwoWayKey)obj).foreignClass == foreignClass);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }

        public int hashCode() {
            return nativeClass.hashCode();
        }
    }
}

