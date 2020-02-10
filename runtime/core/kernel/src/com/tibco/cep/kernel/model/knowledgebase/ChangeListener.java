package com.tibco.cep.kernel.model.knowledgebase;

import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.helper.ActionExecutionContext;
import com.tibco.cep.kernel.helper.EventExpiryExecutionContext;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext;
import com.tibco.cep.kernel.model.entity.Event;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 2:38:16 AM
 * <p>
 * <b>
 * All implementations of this interface will get notified
 * of Rete change activities synchronously.
 * <p>
 * e.g : If an event is asserted implementations will be notified
 * immediately, hence it is important to make sure by each
 * implementation that it does not perform any resource intensive
 * operation.
 * </p>
 * </b>
 * </p>
 *
 */
public interface ChangeListener {

    void asserted(Object obj, ExecutionContext context);

    void modified(Object obj, ExecutionContext context);

    void retracted(Object obj, ExecutionContext context);

    void eventExpired(Event evt);

    void scheduledTimeEvent(Event evt, ExecutionContext context);

    void ruleFired(AgendaItem context);

    void actionExecuted(ActionExecutionContext context);

    void eventExpiryExecuted(EventExpiryExecutionContext context);

    void functionExecuted(FunctionExecutionContext context);

    void functionExecuted(FunctionMapArgsExecutionContext context);
}
