/**
 *
 */
package com.tibco.be.bemm.functions;

import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.LikeFilter;
import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "BEMM.coherence.filters",
        synopsis = "Functions for querying the Cache")
public class CoherenceFilterHelper {

    @com.tibco.be.model.functions.BEFunction(
        name = "C_Like",
        synopsis = "A like filter",
        signature = "Object C_Like(Object value1, Object value2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value1", type = "Object", desc = "LHS (ChainedExtractor returned by C_XXXAtomGetter)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value2", type = "Object", desc = "RHS (Constants returned by C_XXXConstant)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A like filter",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
   public static Object C_Like(Object value1, Object value2){
       if (value1 instanceof ValueExtractor) {
           ValueExtractor ve = (ValueExtractor) value1;
           return new LikeFilter(ve,(String)value2,'\\',true);
       } else {
           throw new RuntimeException("Value1 Expected to be a ChainedExtractor " + value1);
       }
   }

}
