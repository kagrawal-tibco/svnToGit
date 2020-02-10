package com.tibco.be.functions.IO;


import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;


/**
 * User: aamaya
 * Date: Jul 18, 2004
 * Time: 10:39:02 PM
 */
public class DebugOut {

    @com.tibco.be.model.functions.BEFunction(
        name = "debugOut",
        synopsis = "Output the String passed to the debug sink.",
        signature = "void debugOut (String str)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "A String to output to the debug sink.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Output the String passed to the debug sink.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static void debugOut(String str) {
        LogManagerFactory.getLogManager().getLogger("user").log(Level.INFO, str);
    }

}
