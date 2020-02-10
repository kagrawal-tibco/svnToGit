package com.tibco.cep.interpreter.evaluator.factory;

import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import org.antlr.runtime.tree.CommonTree;

/**
 * User: nprade
 * Date: 9/8/11
 * Time: 4:01 PM
 */
public interface IEvaluatorDescriptorFactory {


    EvaluatorDescriptor make(
            CommonTree expression,
            TupleInfoDescriptor inputDescriptor)
            throws Exception;


}
