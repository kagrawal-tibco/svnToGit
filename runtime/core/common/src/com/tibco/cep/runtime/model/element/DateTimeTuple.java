package com.tibco.cep.runtime.model.element;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 20, 2006
 * Time: 11:02:42 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DateTimeTuple {
    /**
     *
     * @return
     */
    public String getTimeZone();

    /**
     *
     * @return
     */
    public long getTime();

}
