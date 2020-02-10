package com.tibco.cep.kernel.core.base.tuple;

/*
* Author: Suresh Subramani / Date: 8/22/12 / Time: 6:14 PM
*/
public class JoinTableCollectionProvider {

    static final JoinTableCollectionProvider gProvider = new JoinTableCollectionProvider();
    static ThreadLocal<JoinTableCollection> jtcLocals = new ThreadLocal<JoinTableCollection>();

    private JoinTableCollection joinTableCollection = new DefaultJoinTableCollection();

    public static JoinTableCollectionProvider getInstance() {
        return gProvider;
    }

    public JoinTableCollection getJoinTableCollection() {
        return jtcLocals.get() == null ? joinTableCollection :jtcLocals.get();
    }

    public void setThreadJoinTableCollection(JoinTableCollection joinTableCollection) {
        jtcLocals.set(joinTableCollection);
    }

    public void clearThreadJoinTableCollection() {
        jtcLocals.remove();
    }


}
