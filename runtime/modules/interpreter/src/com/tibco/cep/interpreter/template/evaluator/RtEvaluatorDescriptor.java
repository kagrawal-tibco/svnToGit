package com.tibco.cep.interpreter.template.evaluator;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;


/**
 * User: nprade
 * Date: 2/16/12
 * Time: 1:13 PM
 */
public class RtEvaluatorDescriptor
        extends EvaluatorDescriptor {


    protected Map<String, Set<String>> entityClassNameToUsedPropertyName =
            new LinkedHashMap<String, Set<String>>();
    protected Set<String> usedClassNames = new HashSet<String>();


    public RtEvaluatorDescriptor(
            ExpressionEvaluator e,
            TypeInfo t) {

        super(e, t);
    }


    public void addDeclarationUsage(
            String entityClassName,
            String entityPropertyName) {

        Set<String> propNames = this.entityClassNameToUsedPropertyName.get(entityClassName);
        if (null == propNames) {
            propNames = new HashSet<String>();
            this.entityClassNameToUsedPropertyName.put(entityClassName, propNames);
        }
        if (null != entityPropertyName) {
            propNames.add(entityPropertyName);
        }
    }


    public void addUsed(
            EvaluatorDescriptor descriptor) {

        super.addUsedColumnNames(descriptor);
        if (descriptor instanceof RtEvaluatorDescriptor) {
            final RtEvaluatorDescriptor rtDescriptor = (RtEvaluatorDescriptor) descriptor;
            this.usedClassNames.addAll(rtDescriptor.usedClassNames);
            for (final Map.Entry<String, Set<String>> entry
                    : rtDescriptor.entityClassNameToUsedPropertyName.entrySet()) {
                final String name = entry.getKey();
                Set<String> propNames = this.entityClassNameToUsedPropertyName.get(name);
                if (null == propNames) {
                    propNames = new HashSet<String>();
                    this.entityClassNameToUsedPropertyName.put(name, propNames);
                }
                propNames.addAll(entry.getValue());
            }
        }
    }


    public void addUsedClassNames(
            String usedClassName) {

        this.usedClassNames.add(usedClassName);
    }


    public Map<String, Set<String>> getDeclarationUsages() {
        return Collections.unmodifiableMap(this.entityClassNameToUsedPropertyName);
    }


    public Set<String> getUsedClassNames() {
        return Collections.unmodifiableSet(this.usedClassNames);
    }


}
