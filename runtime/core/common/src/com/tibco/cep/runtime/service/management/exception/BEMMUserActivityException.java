package com.tibco.cep.runtime.service.management.exception;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Aug 2, 2010
 * Time: 3:51:44 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * The BEMMUserActivityException should be thrown when the exception is a direct consequence of a user action. This action can
 * be for instance an illegal value passed in as an argument by the user (from the UI), or any action that it is not allowed
 * for the current runtime environment. <p/>
 *
 * Examples are an invalid session name being passed in, or trying to stop an agent that had already been stopped.
 * Such actions will likely cause an exception and an "error/warning" message, but in general it is
 * expected that no harm should result of it. However the user should be notified of the outcome of his/her action <p/>
 *
 * This exception is the mechanism through which the user is going to receive the notification. Note that the agent is
 * running remotely, hence by using exceptions it's possible to propagate the information to the server and UI running remotely
 * */

public class BEMMUserActivityException extends BEMMException{

    public BEMMUserActivityException() {
        super();
    }

    public BEMMUserActivityException(String message) {
        super(message);
    }

    public BEMMUserActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public BEMMUserActivityException(Throwable cause) {
        super(cause);
    }
}
