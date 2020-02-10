package com.tibco.cep.query.exec;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 7, 2007
 * Time: 4:47:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExecutionClassInfo {
    /**
     * Returns an execution class
     *
     * @return Class
     */
    Class getClazz();

    /**
     * Returns the execution class name
     *
     * @return String
     */
    String getClazzName();
}
