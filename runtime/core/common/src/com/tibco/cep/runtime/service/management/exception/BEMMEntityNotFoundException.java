package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Aug 2, 2010
 * Time: 6:57:14 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * The BEMMEntityNotFoundException should be thrown when the user passes in from the UI an entity value that does
 * not exist in the current context. Although the value might not be illegal, the associated result is likely null or void.<p/>
 *
 * This exception is the mechanism through which the user is going to receive a meaningful notification information, and not
 * only that the result of the operation is null or void. Note that the agent is running remotely, hence by using exceptions
 * it is possible to propagate the information to the server and UI running remotely.<p/>
 *
 * An example is an invalid Rule URI that is provided by the user to the getRule(sessName, URI) operation. The normal output of such
 * operation shall be null. By throwing and catching this exception it is possible to present the user with a meaningful
 * message, e.g.: "No rule deployed with URI: <i>URI</i>" 
 * */

public class BEMMEntityNotFoundException extends BEMMException{

    public BEMMEntityNotFoundException() {
        super();
    }

    public BEMMEntityNotFoundException(String message) {
        super(message);
    }

    public BEMMEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BEMMEntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
