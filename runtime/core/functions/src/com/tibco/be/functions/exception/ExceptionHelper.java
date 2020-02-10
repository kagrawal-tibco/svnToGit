package com.tibco.be.functions.exception;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.model.exception.BEException;
import com.tibco.cep.runtime.model.exception.impl.BEExceptionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 7, 2006
 * Time: 12:45:46 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Exception",
        synopsis = "Functions to create and modify instances of type Exception")
public class ExceptionHelper {

    @com.tibco.be.model.functions.BEFunction(
        name = "newException",
        synopsis = "Creates a new Exception Instance based on the data passed in.",
        signature = "Exception newException (String errorType, String message, Exception cause)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "errorType", type = "String", desc = "indicates the type of error this exception represents"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "String", desc = "more detailed information about the error this exception represents"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cause", type = "Exception", desc = "an exception that has caused this exception")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Exception", desc = "The newly created Exception Instance."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This creates a new Exception Instance based on the data passed in.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static BEException newException(String errorType, String message, BEException cause) {
        return new BEExceptionImpl(errorType, message, cause);
    }
}
