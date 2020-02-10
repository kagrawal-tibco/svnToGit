package com.tibco.be.functions.custom;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.repo.GlobalVariables;
import static com.tibco.be.model.functions.FunctionDomain.ACTION;

/**
 * User: Puneet Nayyar
 * Date: January 11, 2005
 * Time: 5:47:53 PM
 */

/**
 * @.category This class provides sample functions for two categories
 * @.synopsis Sample functions to get to the history of the concept insances.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Custom",
		category = "Categories.SimpleOnes",
		synopsis = "Sample functions to get the history of the concept instances."
)
public class CustomJavaHelper1{

    @com.tibco.be.model.functions.BEFunction(
    	name = "firstSample",
    	synopsis = "plug-in java code.",
    	signature = "void firstSample()",
    	params = {
    	},
    	freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    	version = "1.0",
    	see = "",
    	mapper = @com.tibco.be.model.functions.BEMapper(),
    	description = "plug-in java code.",
    	cautions = "none",
    	fndomain = {ACTION},
    	example = "")
    
    public static void firstSample()
    {
        //To Access a RuleSession - call this method
        RuleSession session = RuleSessionManager.getCurrentRuleSession();

        //From a session call its accessor method
        GlobalVariables gVars = session.getRuleServiceProvider().getGlobalVariables();

        //do blah....


    }
}
