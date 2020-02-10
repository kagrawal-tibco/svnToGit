package bdb;

import com.tibco.cep.runtime.service.om.OMException;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Nov 16, 2006
 * Time: 2:46:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Loader {

    void load() throws OMException;
}
