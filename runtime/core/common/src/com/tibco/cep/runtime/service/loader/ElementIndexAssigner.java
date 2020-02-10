package com.tibco.cep.runtime.service.loader;

import java.lang.reflect.Field;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jul 12, 2006
 * Time: 2:27:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ElementIndexAssigner {

    public static String printIndex(Class c) throws Exception{
        if ((c != null) && ConceptImpl.class.isAssignableFrom(c)) {
            MetaProperty[] mprops = (MetaProperty[])c.getField(CGConstants.META_PROPS_ARRAY).get(null);
            StringBuilder bld = new StringBuilder();
            //get the property info for class
            bld.append("\n");
            bld.append("[").append(c.getName()).append("\n").append(
                    "\tnumProperties = ").append(mprops.length).append(";\n");

            if(ContainedConcept.class.isAssignableFrom(c)) {
                Field parentPropertyClassIdField = c.getField(CGConstants.PARENT_PROPERTY_NAME);
                String parentPropertyName = (String) parentPropertyClassIdField.get(null);
                bld.append("\tparentPropertyName = ").append(parentPropertyName).append(";\n");
            }

            for(int ii = 0; ii < mprops.length; ii++) {
                MetaProperty mprop = mprops[ii];
                bld.append(String.format("\t %s Id(%s);\n", mprop.getName(), ii));
            }
            bld.append("]\n");
            return bld.toString();
        }
        return null;
    }

}
