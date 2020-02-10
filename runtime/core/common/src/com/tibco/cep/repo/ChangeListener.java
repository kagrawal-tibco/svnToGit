package com.tibco.cep.repo;



/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jul 12, 2006
 * Time: 6:35:21 AM
 * To change this template use File | Settings | File Templates.
 */


public interface ChangeListener {
    void notify (ChangeEvent e);

    public interface ChangeEvent {
        String[] getChangedProjects();
        String[] getDeleted();
        String[] getAdded();
    }

}
