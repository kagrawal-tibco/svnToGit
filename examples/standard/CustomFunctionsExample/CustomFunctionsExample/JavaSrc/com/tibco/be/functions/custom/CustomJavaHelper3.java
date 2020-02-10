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
		category = "Categories.Serialize",
		synopsis = "Sample functions to get the history of the concept instances."
)
public class CustomJavaHelper3{ 

 @com.tibco.be.model.functions.BEFunction(
    		name = "serializeConcept",
    		synopsis = "Serializes the Concept passed to an XML String which is returned.",
    		signature = "String serializeConcept(Object concept, boolean changedOnly, String nameSpace, String root)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="concept", type="Object", desc="Object of type Concept to serialize"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="changeOnly", type="boolean", desc="If true only data modified since the last rule engine conflict resolution cycle will be serialized."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="nameSpace", type="String", desc="A path describing the nameSpace to save in."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="root", type="String", desc="The name of the serialized data.")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="String", desc="XML string returned."),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Serialize the Concept passed to an XML String which is returned.",
    		cautions = "none",
    		fndomain = {ACTION},
    		example = "")
    
    public static String serializeConcept(Object concept, boolean changedOnly, String nameSpace, String root) {
        try {
            ExpandedName rootNm=ExpandedName.makeName(nameSpace, root);
            XiNode node = XiFactoryFactory.newInstance().createElement(rootNm);
            (Concept.class.cast(concept)).toXiNode(node, changedOnly);
            return XiSerializer.serialize(node);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
