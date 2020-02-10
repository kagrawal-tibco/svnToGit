package com.tibco.be.ws.rt.model;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.rule.Symbol;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SymbolChildNodeFactory {

    public static <T> AbstractSymbolChildNode createSymbolChildNode(T wrapped) {
        if (wrapped instanceof PropertyDefinition) {
            PROPERTY_TYPES propertyType = ((PropertyDefinition)wrapped).getType();
            if (!(propertyType == PROPERTY_TYPES.CONCEPT) && !(propertyType == PROPERTY_TYPES.CONCEPT_REFERENCE)) {
                return new SimpleSymbolChildNode(((PropertyDefinition)wrapped));
            } else {
                PropertyDefinition wrappedPropertyDefinition = (PropertyDefinition)wrapped;
                //Get containing path
                String conceptTypePath = wrappedPropertyDefinition.getConceptTypePath();
//                if (conceptTypePath != null && !conceptTypePath.endsWith(".concept")) conceptTypePath += ".concept";
                return new ContainerSymbolChildNode(new SymbolEssentials(wrappedPropertyDefinition.getName(), conceptTypePath));
            }
        } else if (wrapped instanceof Symbol) {
            Symbol wrappedSymbol = (Symbol)wrapped;
            String symbolType = wrappedSymbol.getType();
            return new ContainerSymbolChildNode(new SymbolEssentials(wrappedSymbol.getIdName(), symbolType));
        }
        return null;
    }
}
