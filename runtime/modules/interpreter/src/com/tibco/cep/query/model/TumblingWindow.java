package com.tibco.cep.query.model;

/**
 * Window which keeps a maximum number of entities for one cycle only.
 */
public interface TumblingWindow
    extends Window {


    /**
     * @return the maximum size of the window.
     */
    int getMaxSize();


}
