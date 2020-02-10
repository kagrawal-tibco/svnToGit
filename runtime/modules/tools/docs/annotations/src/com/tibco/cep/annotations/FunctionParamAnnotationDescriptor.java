package com.tibco.cep.annotations;

import com.tibco.be.model.functions.FunctionParamDescriptor;

import java.lang.annotation.Annotation;


/**
 * User: nprade
 * Date: 4/18/12
 * Time: 3:54 PM
 */
public class FunctionParamAnnotationDescriptor
        implements FunctionParamDescriptor {


    private String name;
    private String type;
    private String desc;


    public FunctionParamAnnotationDescriptor(
            String name,
            String type,
            String desc) {
        this.name = name;
        this.type = type;
        this.desc = desc;
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return FunctionParamDescriptor.class;
    }


    @Override
    public String desc() {
        return desc;
    }


    @Override
    public String name() {
        return name;
    }


    @Override
    public String type() {
        return type;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("@").append(annotationType().getName());
        sb.append("(name = \"").append(AnnotationHelper.escape(name))
                .append("\", type = \"").append(AnnotationHelper.escape(type))
                .append("\", desc = \"").append(AnnotationHelper.escape(desc))
                .append("\")");
        return sb.toString();
    }
}
