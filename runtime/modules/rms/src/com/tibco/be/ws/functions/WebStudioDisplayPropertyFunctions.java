/**
 * 
 */
package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.ws.display.DisplayHelper;

/**
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Display",
        synopsis = "Display Property functions.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS", value=true))

public class WebStudioDisplayPropertyFunctions {
	
	 @com.tibco.be.model.functions.BEFunction(
         name = "getDisplayProperties",
         signature = "String getDisplayProperties(String projectPath, String localeCode)",
         params = {
             @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "Project Path"),
             @com.tibco.be.model.functions.FunctionParamDescriptor(name = "localeCode", type = "String", desc = "Locale Code associated to the browser.")
         },
         freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Display property associated to the project"),
         version = "5.1",
         see = "",
         mapper = @com.tibco.be.model.functions.BEMapper(),
         description = "Get the display properties associated to the project.",
         cautions = "",
         fndomain = {ACTION},
         example = ""
     )
	public static String getDisplayProperties(String projectPath, String localeCode) {
		return DisplayHelper.getDisplayProperties(projectPath, localeCode);
	}

}
