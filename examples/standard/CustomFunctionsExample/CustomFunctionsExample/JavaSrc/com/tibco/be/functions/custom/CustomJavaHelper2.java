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
		category = "Categories.TimeBasedPropertyValues",
		synopsis = "Sample functions to get the history of the concept instances."
)
public class CustomJavaHelper2{
    
    @com.tibco.be.model.functions.BEFunction(
    		name = "snapshotOverTime",
    		synopsis = "Serializes the history for a property over time and returns as a String.",
    		signature = "String snapshotOverTime(Object propertyDouble, long startTime, long endTime)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="propertyDouble", type="Object", desc="PropertyAtomDouble type Object.<br/>Property to serialize."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="startTime", type="long", desc="Start time for serialising the history values."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="endTime", type="long", desc="End time for serialising the history values.")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="String", desc="An XML String Serialization of the PropertyAtomDouble passed."),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Serializes the history for a property over time and returns as a String.",
    		cautions = "none",
    		fndomain = {ACTION},
    		example = "")
    
    public static String snapshotOverTime(Object propertyDouble, long startTime, long endTime) {
        try {
            StringBuffer snapshotmessage = new StringBuffer();

            snapshotmessage.append("<HISTORY>");
            for (long t=startTime; t<endTime; t+=1000) {/* we sample for 10 second intervals */
                double value = 0;
                try {
                    value = (PropertyAtomDouble.class.cast(propertyDouble)).getDouble(t);
                } catch (Exception e) {
                    value = -1;
                }
                snapshotmessage.append("<time>" + t + "</time> <value>" + value +"</value>");
            }
            snapshotmessage.append("</HISTORY>");
            return snapshotmessage.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}