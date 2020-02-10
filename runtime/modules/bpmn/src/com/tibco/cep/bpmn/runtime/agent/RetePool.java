package com.tibco.cep.bpmn.runtime.agent;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.service.time.BETimeManager;

/*
* Author: Suresh Subramani / Date: 5/12/12 / Time: 3:54 PM
*/
public class RetePool {
    final BlockingQueue<WorkingMemory> queue;

    public static RetePool createPool(int size, String pfx, ObjectManager objectManager) {
        final LogManager logManager = LogManagerFactory.getLogManager();

        final boolean isMultiEngineMode= true;
        final boolean isConcurrent = true;
        final BETimeManager timeManager = (BETimeManager)((BaseObjectManager) objectManager).getTimeManager();
        DefaultExceptionHandler exphandler = new DefaultExceptionHandler(logManager.getLogger(RetePool.class));// is this correct or should we use ProcessRuleSession.class

        RetePool pool = new RetePool(size);
        for (int i=0; i<size; i++) {
            String name = String.format("%s-%d", pfx, i);
            WorkingMemory wm = new ProcessWM(name, logManager, exphandler, (BaseObjectManager)objectManager, timeManager, null, isMultiEngineMode, isConcurrent);
            pool.add(wm);

        }

        return pool;


    }

    public RetePool(int size) {
        this.queue = new LinkedBlockingQueue<WorkingMemory>(size);
    }



    private  void add(WorkingMemory wm) {
        for (;;) {
            try {
                queue.put(wm);
                return;
            }
            catch (InterruptedException e) {
            }
        }
    }

    public WorkingMemory getFreeRete() {
        for (;;) {
            try {
                return queue.take();
            }
            catch (InterruptedException e) {
            }
        }
    }

    public void returnRete(WorkingMemory wm) {
        add(wm);
    }

    public void registerTypes(ProcessRuleSession processRuleSession) {
        Iterator<WorkingMemory> iterator = queue.iterator();
        while (iterator.hasNext()) {
            WorkingMemory wm = iterator.next();
            processRuleSession.registerTypes(wm);

        }
    }
}
