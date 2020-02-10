package com.tibco.cep.bpmn.runtime.activity.mapper;

import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:45:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Mapper {
	

    void inputTransform(Job context, Variables variables) throws Exception;

    void outputTransform(Job context, Variables variables) throws Exception;
}
