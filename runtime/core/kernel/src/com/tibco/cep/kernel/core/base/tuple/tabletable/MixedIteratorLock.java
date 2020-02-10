package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.rete.DefaultGuard;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 11, 2009
 * Time: 4:51:18 PM
 * 
 * For use by TableTableS.  Allows for safely double unlocking mixed tables.  
 * Lock - > get iterator -> iterator unlocks table after iterating past shared table and switching to thread local table -> caller unlocks again in finally block after finishing iteration.
 */
public class MixedIteratorLock extends DefaultGuard
{
    public void unlock() {
        if(isHeldByCurrentThread()) super.unlock();
    }
}
