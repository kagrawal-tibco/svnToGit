package com.tibco.be.functions.xinodeassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptToXiNodeFilter;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.xml.datamodel.XiNode;

/*
* Author: Ashwin Jayaprakash Date: Aug 21, 2008 Time: 10:17:45 PM
*/
public class SimpleRegexAssistant implements XiNodeAssistant, ConceptToXiNodeFilter<Matcher> {
    protected final Pattern pattern;

    public SimpleRegexAssistant(String stringPattern) {
        this.pattern = Pattern.compile(stringPattern);
    }

    public void filterAndFill(Concept concept, XiNode node) {
        concept.toXiNode(this, node);
    }

    //----------

    public Matcher beginSession() {
        return pattern.matcher("");
    }

    public boolean allow(Matcher matcher, Property property) {
        String name = property.getName();

        matcher.reset(name);

        return matcher.matches();
    }

    public void endSession(Matcher matcher) {
    }
}
