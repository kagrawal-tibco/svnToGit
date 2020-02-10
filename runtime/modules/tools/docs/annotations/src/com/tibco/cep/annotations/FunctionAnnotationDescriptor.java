package com.tibco.cep.annotations;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * User: nprade
 * Date: 4/18/12
 * Time: 1:21 PM
 */
public class FunctionAnnotationDescriptor
        implements FunctionJavaDoc {


    private String cautions;
    private String description;
    private final Set<String> domain = new HashSet<String>();
    private String example;
    private FunctionParamDescriptor freturn;
    private boolean mapper;
    private String name;
    private final List<FunctionParamDescriptor> params = new ArrayList<FunctionParamDescriptor>();
    private String see;
    private String signature;
    private String synopsis;
    private String version;


    @Override
    public Class<? extends Annotation> annotationType() {
        return FunctionJavaDoc.class;
    }


    @Override
    public String cautions() {
        return cautions;
    }


    @Override
    public String description() {
        return description;
    }


    @Override
    public String[] domain() {
        return domain.toArray(new String[domain.size()]);
    }


    @Override
    public String example() {
        return example;
    }


    @Override
    public FunctionParamDescriptor freturn() {
        return freturn;
    }


    public Set<String> getDomain() {
        return domain;
    }


    public List<FunctionParamDescriptor> getParams() {
        return params;
    }


    @Override
    public boolean mapper() {
        return mapper;
    }


    @Override
    public String name() {
        return this.name;
    }


    @Override
    public FunctionParamDescriptor[] params() {
        return params.toArray(new FunctionParamDescriptor[params.size()]);
    }


    @Override
    public String see() {
        return see;
    }


    public void setCautions(String cautions) {
        this.cautions = cautions;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setExample(String example) {
        this.example = example;
    }


    public void setFreturn(FunctionParamDescriptor freturn) {
        this.freturn = freturn;
    }


    public void setMapper(boolean mapper) {
        this.mapper = mapper;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setSee(String see) {
        this.see = see;
    }


    public void setSignature(String signature) {
        this.signature = signature;
    }


    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }


    public void setVersion(String version) {
        this.version = version;
    }


    @Override
    public String signature() {
        return signature;
    }


    @Override
    public String synopsis() {
        return this.synopsis;
    }


    @Override
    public String toString() {

        final FunctionParamDescriptor returnDesc = (null == freturn)
                ? new FunctionParamAnnotationDescriptor("", "void", "")
                : freturn;

        final StringBuilder sb = new StringBuilder("@")
                .append(annotationType().getName()).append("(\n")
                .append("    name = \"").append(AnnotationHelper.escape(name)).append("\",\n")
                .append("    synopsis = \"").append(AnnotationHelper.escape(synopsis)).append("\",\n")
                .append("    signature = \"").append(AnnotationHelper.escape(signature)).append("\",\n")
                .append("    params = {");

        boolean comma = false;
        for (final FunctionParamDescriptor param : params) {
            if (comma) {
                sb.append(",");
            }
            else {
                comma = true;
            }
            sb.append("\n        ").append(param);
        }
        sb
                .append("\n    },\n")
                .append("    freturn = ").append(returnDesc).append(",\n")
                .append("    version = \"").append(AnnotationHelper.escape(version)).append("\",\n")
                .append("    see = \"").append(AnnotationHelper.escape(see)).append("\",\n")
                .append("    mapper = ").append(mapper).append(",\n")
                .append("    description = \"").append(AnnotationHelper.escape(description)).append("\",\n")
                .append("    cautions = \"").append(AnnotationHelper.escape(cautions)).append("\",\n")
                .append("    domain = \"");

        comma = false;
        for (final String d : domain) {
            if (comma) {
                sb.append(",");
            }
            else {
                comma = true;
            }
            sb.append(AnnotationHelper.escape(d));
        }
        sb
                .append("\",\n")
                .append("    example = \"").append(AnnotationHelper.escape(example)).append("\"\n)");

        return sb.toString();
    }


    @Override
    public String version() {
        return signature;
    }


}
