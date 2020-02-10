package com.tibco.be.functions.VRF;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.runtime.service.decision.impl.DTImpl;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.loader.VRFImplSet;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.util.CodegenFunctions;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 26, 2008
 * Time: 7:28:00 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "VRF",
        synopsis = "Utility Functions to Operate on Virtual Rule Functions")
public class VRFFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "invokeVRFImplByName",
        signature = "int[] invokeVRFImplByName (String vrfURI, String implName, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfURI", type = "String", desc = "The URI of the virtual rule function."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "implName", type = "String", desc = "The name of the virtual rule function implementation."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The input arguments.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int[]", desc = "Integer array containing the matched rule Ids."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invoke a virtual rule function implementation by name.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "Object[] sample_args = {arg1, arg2};  "
        		+ "VRF.invokeVRFImplByName(\"/Virtual_RF/Sample_VirtualRuleFunction\",\"Sample_Impl_Name\",sample_args);"
    )
    public static int[] invokeVRFImplByName(String vrfURI, String implName, Object[] args) {
        VRFImpl impl = (VRFImpl) getVRFImplByName(vrfURI, implName);
        if (impl != null) {
            return CodegenFunctions.invokeVRFImpl(impl, args);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "invokeVRFImpl",
        signature = "int[] invokeVRFImpl (Object vrfImpl, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfImpl", type = "Object", desc = "A virtual rule function implementation object (gotten from getVRFImplByName or getVRFImpls)."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The input arguments.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int[]", desc = "Integer array containing the matched rule Ids."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invoke a virtual rule function implementation.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "Object vrfImpl = VRF.getVRFImplByName(\"/Virtual_RF/Sample_VirtualRuleFunction\",\"Sample_Impl\");  "
        		+ "Object[] sample_args = {arg1, arg2};  "
        		+ "VRF.invokeVRFImpl (vrfImpl, sample_args);"
    )
    public static int[] invokeVRFImpl(Object vrfImpl, Object[] args) {
        if (vrfImpl instanceof VRFImpl) {
        	return CodegenFunctions.invokeVRFImpl((VRFImpl)vrfImpl, args);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "invokeVRFImpls",
        signature = "Object invokeVRFImpls (Object[] vrfImpls, Object[] args, Object[] returnValues)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfImpls", type = "Object[]", desc = "An array of virtual rule function implementation objects (gotten from getVRFImpls or getVRFImplByName)."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The input arguments."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "returnValues", type = "Object[]", desc = "Optional.  If non-null, the result of invoking each element of vrfImpls will be placed into returnValues in the same order as vrfImpls.  Functions returning void will add null entries to returnValues.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Result Map containing the matched rules associated to the executed Decision Table. Key-Value pair structure, with Key being the Decision Table name and value being the integer array containing the matched rule Ids."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invoke an array of virtual rule function implementations.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "Object[] vrfImplArray = VRF.getVRFImpls(\"/Virtual_RF/Sample_VirtualRuleFunction\");  "
        		+ "Object[] sample_args = {arg1, arg2};  "
        		+ "VRF.invokeVRFImpls (vrfImplArray, sample_args, null);"
        		
        		
    )
    public static Object invokeVRFImpls(Object[] vrfImpls, Object[] args, Object[] returnValues) {
        return invokeVRFImpls_collection(Arrays.asList(vrfImpls), args, returnValues);
    }
    
    private static Object invokeVRFImpls_collection(Collection vrfImpls, Object[] args, Object[] returnValues) {
        if (vrfImpls != null) {
            //only applies to VRFImpls that are DTImpls
            //-1 means unset, 0 false, 1 true,
            //2 means that oneRowOnly used to be 1, and now a row has matched so no more DTImpls should be invoked 
            byte oneRowOnly = -1;
            int ii = -1;
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for (VRFImpl vrf : (Collection<VRFImpl>)vrfImpls) {
                ii++;
                Object ret = null;
                if (vrf != null) {
                    //value of oneRowOnly for first DTImpl decides behavior for the rest 
                    if (oneRowOnly < 0 && vrf instanceof DTImpl) {
                        oneRowOnly = ((DTImpl)vrf).isOneRowOnly() ? (byte)1 : (byte)0;
                    }
                    //if oneRowOnly is 1 or 2, then DTImpls must be treated specially
                    //if it is 2, then don't invoke the DTImpl
                    if(oneRowOnly >=1 && vrf instanceof DTImpl) {
                        if(oneRowOnly < 2) {
                            if(((DTImpl)vrf).invoke_explicitReturn(args)) oneRowOnly = 2;
                        }
                        //can't just do break; here because the next VRFImpl may not be a DTImpl
                    } else {
                        ret = vrf.invoke(args);
                        resultMap.put(vrf.getClass().getSimpleName(), ret);
                    }
                    
                    if (returnValues != null && ii < returnValues.length) {
                        returnValues[ii] = ret;
                    }
                }
            }
            return resultMap;
        }
        return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "invokeAllVRFImpls",
        signature = "Object invokeAllVRFImpls (String vrfURI, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfURI", type = "String", desc = "The URI of the virtual rule function."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The input arguments.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Result Map containing the matched rules associated to the executed Decision Table. Key-Value pair structure, with Key being the Decision Table name and value being the integer array containing the matched rule Ids."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invoke all implementations of a virtual rule function.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "Object[] sample_args = {arg1, arg2};  "
        		+ "VRF.invokeAllVRFImpls(\"/Virtual_RF/Sample_VirtualRuleFunction\",sample_args);"
        		
    )
    public static Object invokeAllVRFImpls(String vrfURI, Object[] args) {
        if (vrfURI != null && vrfURI.length() > 0) {
            ClassLoader cl = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            if (cl instanceof BEClassLoader) {
                VRFImplSet impls = ((BEClassLoader)cl).getVRFRegistry().getVRFImplSet(vrfURI);
                if (impls != null) {
                    Object[] ret = null;
                    //all DTs return void, return values array only needed for other VRFImpls
                    for (int ii = impls.vrfStartIndex; ii < impls.lowPrioStartIndex; ii++) {
                        VRFImpl vrfImpl = impls.impls.get(ii);
                        if (!vrfImpl.isVoid()) {
                            ret = new Object[impls.impls.size()];
                            Arrays.fill(ret, null);
                            break;
                        }
                    }
                    
                    Map<String, Object> resultMap = new HashMap<String, Object>();

                    //only applies to VRFImpls that are DTImpls
                    //-1 means unset, 0 false, 1 true,
                    //2 means that oneRowOnly used to be 1, and now a row has matched so no more DTImpls should be invoked
                    byte oneRowOnly = -1;
                    //layout is <dtimpls with prio 1 thru 5><vrfimpls that aren't dtimpls><dtimpls with prio greater than 5>
                    oneRowOnly = invokeDTs(0, impls.vrfStartIndex, oneRowOnly, (List)impls.impls, args, resultMap);
                    //this region of the list is for VRFImpls that aren't DTImpls and therefore don't have a priority
                    for (int ii = impls.vrfStartIndex; ii < impls.lowPrioStartIndex; ii++) {
                        Object retVal = impls.impls.get(ii).invoke(args);
                        if(ret != null) resultMap.put(impls.impls.get(ii).getClass().getSimpleName(), retVal); //ret[ii + impls.vrfStartIndex] = retVal;
                    }
                    invokeDTs(impls.lowPrioStartIndex, impls.impls.size(), oneRowOnly, (List)impls.impls, args, resultMap);
                    return resultMap;
                }
            }
        }
        return null;
    }
    
    private static byte invokeDTs(int startIndex, int endIndex, byte oneRowOnly, List<DTImpl> impls, Object[] args, Map<String, Object> resultMap) {
        for (int ii = startIndex; ii < endIndex; ii++) {
            if(oneRowOnly == 2) break;
            DTImpl dt = impls.get(ii);            
            //value of oneRowOnly for first DTImpl decides behavior for the rest
            if (oneRowOnly < 0) {
                oneRowOnly = dt.isOneRowOnly() ? (byte)1 : (byte)0;
            }
            
            if (oneRowOnly == 1) {
                if(dt.invoke_explicitReturn(args)) oneRowOnly = 2;
            } else {
                int[] matchedRows = (int[])dt.invoke(args);
                resultMap.put(dt.getClass().getSimpleName(), matchedRows);
            }
        }
        return oneRowOnly;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getVRFImplNames",
        synopsis = "Get the names of all available implementations of a virtual rule function.",
        signature = "String[] getVRFImplNames (String vrfURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfURI", type = "String", desc = "The URI of the virtual rule function.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "List of names of available implementations.  Null if none found."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the names of all available implementations of a virtual rule function.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "String[] implNames = VRF.getVRFImplNames(\"/Virtual_RF/Sample_VirtualRuleFunction\");  "
        		+ "Here implNames is array of names of all available implementations of a virtual rule function."
    )
    public static String[] getVRFImplNames(String vrfURI) {
        if (vrfURI != null && vrfURI.length() > 0) {
            ClassLoader cl = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            if (cl instanceof BEClassLoader) {
                Collection<String> names = ((BEClassLoader)cl).getVRFRegistry().getVRFImplNames(vrfURI);
                if (names != null && names.size() > 0) {
                    return names.toArray(new String[names.size()]);
                }
            }
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getVRFImpls",
        signature = "Object[] getVRFImpls (String vrfURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfURI", type = "String", desc = "The URI of the virtual rule function.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "List of available implementations.  Null if none found."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get all the available implementations of a virtual rule function.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "Object[] vrfImplArray = VRF.getVRFImpls(\"/Virtual_RF/Sample_VirtualRuleFunction\");  "
        		+ "Here vrfImplArray variable have all implementations of virtual rule function."
    )
    public static Object[] getVRFImpls(String vrfURI) {
        Collection<VRFImpl> impls = getVRFImpls_collection(vrfURI);
        if (impls != null && impls.size() > 0) {
            return impls.toArray(new VRFImpl[impls.size()]);
        }
        return null;
    }
    
    
    //do not give the result of this function to code that may modify it
    private static Collection<VRFImpl> getVRFImpls_collection(String vrfName) {
        if (vrfName != null && vrfName.length() > 0) {
            ClassLoader cl = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            if (cl instanceof BEClassLoader) {
                return ((BEClassLoader)cl).getVRFRegistry().getVRFImpls(vrfName);
            }
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getVRFImplByName",
        signature = "Object getVRFImplByName (String vrfURI, String implName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfURI", type = "String", desc = "The URI of the virtual rule function."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "implName", type = "String", desc = "The name of the virtual rule function implementation.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The implementation.  Null if none found."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get an implementation of a virtual rule function.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = "Object vrfImpl = VRF.getVRFImplByName(\"/Virtual_RF/Sample_VirtualRuleFunction\",\"Sample_Impl\");  "
        		+ "Here vrfImpl object is the implementation of a virtual rule function."
    )
    public static Object getVRFImplByName(String vrfURI, String implName) {
        if (vrfURI != null && vrfURI.length() > 0 && implName != null && implName.length() > 0) {
            ClassLoader cl = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            if (cl instanceof BEClassLoader) {
                return ((BEClassLoader)cl).getVRFRegistry().getVRFImpl(vrfURI, implName);
            }
        }
        return null;
    }
}
