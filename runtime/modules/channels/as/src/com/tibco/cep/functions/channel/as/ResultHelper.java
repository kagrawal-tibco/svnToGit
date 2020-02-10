package com.tibco.cep.functions.channel.as;

/**
* Generated Code from String Template.
* Date : Sep 17, 2012
*/

import com.tibco.as.space.*;
import com.tibco.as.space.remote.InvokeResult;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import static com.tibco.be.model.functions.FunctionDomain.ACTION;


@BEPackage(
		catalog = "ActiveSpaces",
        category = "Metaspace.Space.Result",
        synopsis = "Result functions")

public class ResultHelper {
	
	@BEFunction(
		name = "isSpaceResult",
        synopsis = "Returns true if the passed parameter is of SpaceResult type.",
        signature = "boolean isSpaceResult (Object result)",
		params = {
                @FunctionParamDescriptor(name="result", type="Object", desc="Instance of com.tibco.as.space.Result")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the passed parameter is of SpaceResult type"),
		version = "5.1.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns true if the passed parameter is of SpaceResult type",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
    public static boolean isSpaceResult (Object result) {
		return result instanceof SpaceResult;
	} 

	@BEFunction(
		name = "isInvokeResult",
        synopsis = "Returns true if the passed parameter is of InvokeResult type.",
		signature = "boolean isInvokeResult (Object result)",
		params = {
            @FunctionParamDescriptor(name="result", type="Object", desc="Instance of com.tibco.as.space.Result")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the passed parameter is of InvokeResult type"),
		version = "5.1.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns true if the passed parameter is of InvokeeResult type",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
    public static boolean isInvokeResult (Object result) {
		return result instanceof InvokeResult;
	} 

	@BEFunction(
  	    name = "getError",
  	    synopsis = "Returns the ASException caused by the Error.",
        signature = "Object getError (Object result)",
  	    params = {
            @FunctionParamDescriptor(name="result", type="Object", desc="Instance of SpaceResult or InvokeResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of com.tibco.as.space.ASException, from the Result object if there was an error during the execution of the operation"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the ASException caused by the Error",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
)
    public static Object getError (Object result) {
		Object error = null;
        if (result instanceof SpaceResult) {
  			error =  ((SpaceResult)result).getError();
        } else if (result instanceof InvokeResult) {
  			error =  ((InvokeResult)result).getError();
  		}
  		return error;
  	} 

  	@BEFunction(
  	    name = "getStatus",
        synopsis = "Returns the ASStatus status from the result.",
        signature = "Object getStatus (Object result)",
  	    params = {
        	@FunctionParamDescriptor(name="result", type="Object", desc="Instance of SpaceResult or InvokeResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of com.tibco.as.space.ASStatus, the ASStatus status from the result"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the ASStatus status from the result",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
    public static Object getStatus (Object result) {
  		Object status = null;
        if (result instanceof SpaceResult) {
  			status = ((SpaceResult)result).getStatus();
        } else if (result instanceof InvokeResult) {
  			status = ((InvokeResult)result).getStatus();
  		}
  		return status;
  	} 

  	@BEFunction(
  	    name = "getTuple",
  	    synopsis = "Returns the tuple from the result.",
        signature = "Object getTuple (Object result)",
  	    params = {
           @FunctionParamDescriptor(name="result", type="Object", desc="Instance of SpaceResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of com.tibco.as.space.Tuple, the tuple from the result"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the tuple from the result",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
    public static Object getTuple (Object result) {
        if (result instanceof SpaceResult) {
  			return ((SpaceResult)result).getTuple();
  		} 
  		return null;
  	} 

  	@BEFunction(
  	    name = "hasError",
        synopsis = "Returns true if the result has error, else false.",
        signature = "boolean hasError (Object result)",
  	    params = {
            @FunctionParamDescriptor(name="result", type="Object", desc="Instance of SpaceResult or InvokeResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the result has error, else false"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns true if the result has error, else false",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
  	public static boolean hasError ( Object result ) {
  		boolean hasError = false;
        if (result instanceof SpaceResult) {
  			hasError = ((SpaceResult)result).hasError();
        } else if (result instanceof InvokeResult) {
  			hasError= ((InvokeResult)result).hasError();
  		}
  		return hasError;
  	} 

  	@BEFunction(
  	    name = "getContext",
  	    synopsis = "Returns the context from the InvokeResult else null.",
        signature = "Object getContext (Object result)",
  	    params = {
            @FunctionParamDescriptor(name="result", type="Object", desc=" Instance of InvokeResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of com.tibco.as.space.Tuple, the context from the InvokeResult else null"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the context from the InvokeResult else null",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
    public static Object getContext (Object result) {
         if (result instanceof InvokeResult) {
  			return ((InvokeResult)result).getContext();
  		}
  		return null;
  	} 

  	@BEFunction(
  	    name = "getMember",
  	    synopsis = "Returns the Member from the InvokeResult else null.",
        signature = "Object getMember (Object result)",
  	    params = {
            @FunctionParamDescriptor(name="result", type="Object", desc=" Instance of InvokeResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of com.tibco.as.space.Member, the Member from the InvokeResult else null"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the Member from the InvokeResult else null",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
    public static Object getMember (Object result) {
        if (result instanceof InvokeResult) {
  			return ((InvokeResult)result).getMember();
  		}
  		return null;
  	} 

  	@BEFunction(
  	    name = "getReturn",
  	    synopsis = "Returns the result tuple from the InvokeResult else null.",
        signature = "Object getReturn (Object result)",
  	    params = {
            @FunctionParamDescriptor(name="result", type="Object", desc=" Instance of InvokeResult")
  	    },
  	    freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of com.tibco.as.space.Tuple, the result tuple from the InvokeResult else null"),
  	    version = "5.1.1",
  	    see = "",
  	    mapper = @com.tibco.be.model.functions.BEMapper(),
  	    description = "Returns the result tuple from the InvokeResult else null",
  	    cautions = "none",
  	    fndomain = {ACTION},
  	    example = ""
  	)
    public static Object getReturn (Object result) {
        if (result instanceof InvokeResult) {
  			return ((InvokeResult)result).getReturn();
  		}
  		return null;
  	} 
}
