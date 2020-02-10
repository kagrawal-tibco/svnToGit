package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.impl.RuleDefImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_RULES_NAME;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class RuleDefCollection {

    private List<RuleDef> ruleDefs = new ArrayList<RuleDef>();

    public RuleDefCollection(List<RuleDef> ruleDefs) {
        this.ruleDefs = ruleDefs;
    }

    protected RuleDefCollection() {

    }

    @XmlElement(name = ELEM_RULES_NAME, type = RuleDefImpl.class)
    public List<RuleDef> getRuleDefs() {
        return ruleDefs;
    }

    private void setRuleDefs(List<RuleDef> ruleDef) {
        ruleDefs = ruleDef;
    }
}
