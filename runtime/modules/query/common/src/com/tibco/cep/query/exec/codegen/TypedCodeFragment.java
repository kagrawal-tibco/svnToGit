package com.tibco.cep.query.exec.codegen;

import java.util.LinkedHashMap;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.utils.TypeHelper;

/*
 * User: nprade
 * Date: Jan 25, 2008
 * Time: 4:26:13 PM
 */
public class TypedCodeFragment {


    protected StringBuilder source;
    protected TypeInfo typeInfo;
    protected LinkedHashMap<String, String> usedColumnNamesToType;


    public TypedCodeFragment(StringBuilder source, TypeInfo typeInfo) {
        this.source = source;
        this.typeInfo = typeInfo;
        this.usedColumnNamesToType = new LinkedHashMap<String, String>();
    }


    public TypedCodeFragment(StringBuilder source, TypeInfo typeInfo, String columnName, String className) {
        this(source, typeInfo);
        this.addUsedColumnName(columnName, className);
    }


    public void addUsedColumnName(String columnName, String className) {
        this.usedColumnNamesToType.put(columnName, className);
    }


    public void addUsedColumnNames(TypedCodeFragment otherFragment) {
        this.usedColumnNamesToType.putAll(otherFragment.usedColumnNamesToType);
    }


    public StringBuilder getSource() {
        return this.source;
    }


    public StringBuilder getSourceBoxed() throws Exception {
        final Class typeClass = this.typeInfo.getTypeClass();
        if ((null != typeClass) && typeClass.isPrimitive()) {
            return new StringBuilder(TypeHelper.class.getCanonicalName())
                .append(".getBoxed(")
                .append(this.source)
                .append(")");
        } else {
            return this.source;
        }
    }


    public StringBuilder getSourceUnboxed() throws Exception {
        if (this.typeInfo.getTypeClass().isPrimitive()) {
            return this.source;
        } else {
            return new StringBuilder(TypeHelper.class.getCanonicalName())
                .append(".getUnboxed(")
                .append(this.source)
                .append(")");
        }
    }


    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }


    public LinkedHashMap<String, String> getUsedColumnNames() {
        return this.usedColumnNamesToType;
    }


    public void set(StringBuilder source, TypeInfo typeInfo) {
        this.setSource(source);
        this.setTypeInfo(typeInfo);
    }


    public void set(StringBuilder source, TypeInfo typeInfo, LinkedHashMap<String,String> usedColumnNames) {
        this.set(source, typeInfo);
        this.usedColumnNamesToType.clear();
        this.usedColumnNamesToType.putAll(usedColumnNames);
    }


    public void setSource(StringBuilder source) {
        this.source = source;
    }


    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }


    public String toString() {
        return this.source.toString();
    }


}
