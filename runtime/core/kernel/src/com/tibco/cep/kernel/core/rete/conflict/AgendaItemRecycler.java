package com.tibco.cep.kernel.core.rete.conflict;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 3:25:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaItemRecycler {

    static public void recycle(AgendaItem item) {
        item.recycle();
    }

}
