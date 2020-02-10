package com.tibco.cep.annotations;

import com.tibco.be.model.functions.BEPackage;

import java.lang.annotation.Annotation;


/**
 * User: nprade
 * Date: 4/27/12
 * Time: 1:50 PM
 */
public class CategoryAnnotationDescriptor
        implements CategoryDoc {


    private String category;
    private String synopsis;


    public CategoryAnnotationDescriptor() {
    }


    public CategoryAnnotationDescriptor(
            String category,
            String synopsis) {
        this();
        this.category = category;
        this.synopsis = synopsis;
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return CategoryDoc.class;
    }


    @Override
    public String category() {
        return category;
    }


    public void setCategory(
            String text) {

        this.category = text;
    }


    public void setSynopsis(
            String text) {

        this.synopsis = text;
    }


    @Override
    public String synopsis() {
        return synopsis;
    }


    @Override
    public String toString() {
        return "@" + annotationType().getName()
                + "(\n    category = \"" + AnnotationHelper.escape(category)
                + "\",\n    synopsis = \"" + AnnotationHelper.escape(synopsis)
                + "\")";
    }
}
