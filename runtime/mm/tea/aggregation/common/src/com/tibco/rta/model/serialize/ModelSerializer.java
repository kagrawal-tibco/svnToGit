package com.tibco.rta.model.serialize;

import java.util.Collection;
import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.QueryDef;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/10/12
 * Time: 12:07 PM
 * All model serializers would need to implement this interface with a bounded type indicating
 * the type of target. Serializer and target would be bound together anyway.
 * <p>
 * e.g : XML serializer and xml target.
 * </p>
 *
 */
public interface ModelSerializer<T> {

    public void serialize(RtaSchema schema) throws Exception;

    public void serialize(Fact fact) throws Exception;

    public void serialize(List<Fact> facts) throws Exception;

    public void serialize(QueryDef queryDef) throws Exception;
    
    public void serialize(RuleDef ruleDef) throws Exception;
    
    public void serializeRules(List<RuleDef> ruleDefs) throws Exception;
    
    public T getTransformed();

	void serialize(MetricFunctionDescriptor mfd) throws Exception;

	void serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception;

	void serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception;
}
