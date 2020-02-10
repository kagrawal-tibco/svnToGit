package com.tibco.cep.persister.bdb;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/11/12
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewAndPersister {

    int view;
    String persisterID;

    public ViewAndPersister(int v,String id) {
        view = v;
        persisterID = id;
    }

    public int getView() {
        return view;
    }

    @Override
    public String toString() {
        return (view +"\t"+persisterID );
    }
}
