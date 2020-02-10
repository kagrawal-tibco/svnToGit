package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 12, 2006
 * Time: 7:09:55 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class BaseTimeManager implements TimeManager {

    protected WorkingMemoryImpl workingMemory;
    protected Logger logger;

    protected void init(WorkingMemoryImpl wm) {
        workingMemory = wm;
        logger = wm.getLogManager().getLogger(BaseTimeManager.class);
    }

    protected BaseHandle createHandle(Event ruleTimeEvent) {
        try {
            return workingMemory.getAddEventHandle(ruleTimeEvent);
        } catch (DuplicateExtIdException e) {
            e.printStackTrace();
            //impossible
            throw new RuntimeException(e);
        }
    }

    abstract public void start();

    abstract public void stop();

    abstract public void shutdown();

    abstract public void reset();

    abstract public Event scheduleOnceOnlyEvent(Event event, long delay);

    abstract public BaseHandle scheduleOnceOnlyEvent2(Event event, long delay);

    abstract public BaseHandle loadScheduleOnceOnlyEvent(Event event, long delay);

    abstract public void scheduleEventExpiry(Handle handle, long expireTTL);



}
