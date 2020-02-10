package com.tibco.cep.kernel.service;

import com.tibco.cep.kernel.helper.TimerTaskOnce;
import com.tibco.cep.kernel.helper.TimerTaskRepeat;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 12, 2006
 * Time: 7:11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TimeManager {

    void scheduleOnceOnly(TimerTaskOnce timerTask, long time);

    void scheduleRepeating(TimerTaskRepeat timerTask, long time, long period);

    void unregisterEvent(Class eventClass);

    void registerEvent(Class eventClass);
}
