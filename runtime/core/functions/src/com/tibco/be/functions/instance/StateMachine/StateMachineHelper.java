package com.tibco.be.functions.instance.StateMachine;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;

import java.util.ArrayList;

/**
 * @author ishaan
 * @version 1.0 Oct 26, 2004, 12:40:25 PM
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Instance.StateMachine",
        synopsis = "Utility functions to operate on StateMachines")
public class StateMachineHelper {

     @com.tibco.be.model.functions.BEFunction(
        name = "getCurrentStatePaths",
        synopsis = "Returns an array of the full paths of current states (if any). The state machine could be\nconcurrently in many states so all the active state paths are returned.\nThe returned paths are the runtime paths which could be different from their design time equivalents.\nThe state name is nested; e.g., A$B implies B as a sub-state within A.",
        signature = "String[] getCurrentStatePaths(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance on which to get the state machine state paths.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "An array of all the current state paths."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of the full paths of current states (if any). The state machine could be\nconcurrently in many states so all the active state paths are returned.\nThe returned paths are the runtime paths which could be different from their design time equivalents.\nThe state name is nested; e.g., A$B implies B as a sub-state within A.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String[] getCurrentStatePaths(Concept instance) {
        ArrayList currentStates = new ArrayList();

        if (instance != null) {
            Property.PropertyStateMachine [] stateMachines= instance.getStateMachineProperties();
            for(int i=0; i < stateMachines.length; i++) {
                Property.PropertyStateMachine smprop= stateMachines[i];
                StateMachineConcept sm= smprop.getStateMachineConcept();
                if (sm != null) {
                    Property [] allStates= sm.getProperties();
                    for (int j=0; j < allStates.length; j++) {
                        PropertyStateMachineState state= (PropertyStateMachineState) allStates[j];
                        if (state != null) {
                            if (state.isActive() || state.isReady()) {
                                currentStates.add(getStateFullPath(instance, sm, state));
                            }
                        }
                    }
                }
            }
        }
        String [] tmp = new String[currentStates.size()];
        return (String []) currentStates.toArray(tmp);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDesignTimeCurrentStatePaths",
        synopsis = "Returns an array of the full paths of current states (if any). The state machine could \nconcurrently be in many states so all the active state paths are returned. The state paths returned\nare of the format: ConceptPath/StateMachinePath/StateName. The StateMachinePath is calculated\nas C.S where C is the design time state machine <code> concept </code> owner and S is the state machine name.\nThe <code> concept </code> C can be different from the current concept due to polymorphic state machines. The state name\nis nested; e.g., A$B implies B as a sub-state within A.",
        enabled = @Enabled(value=false),
        signature = "String[] getDesignTimeCurrentStatePaths(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance on which to get the state machine state paths.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "An array of all the current state names."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of the full paths of current states (if any). The state machine could \nconcurrently be in many states so all the active state paths are returned. The state paths returned\nare of the format: ConceptPath/StateMachinePath/StateName. The StateMachinePath is calculated\nas C.S where C is the design time state machine <code> concept </code> owner and S is the state machine name.\nThe <code> concept </code> C can be different from the current concept due to polymorphic state machines. The state name\nis nested; e.g., A$B implies B as a sub-state within A.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String[] getDesignTimeCurrentStatePaths(Concept instance) {
        return getCurrentStatePaths(instance);
    }


    private static String getStateFullPath(Concept cept, StateMachineConcept sm, PropertyStateMachineState state) {
//        String modelPath = ModelNameUtil.generatedClassNameToModelPath(cept.getClass().getName());
//        return modelPath + "/" + state.getStateMachineName() + "/" + state.getName();
        String smPath = ModelNameUtil.generatedClassNameToStateMachineModelPath(sm.getClass().getName());
        return smPath + "/" + state.getName();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getCurrentStateNames",
        synopsis = "Returns an array of the current states (if any). The state machine could\nconcurrently be in many states so all the active states names are returned. The state name returned\nis the name defined at design time without the path. If the state machine is in a sub-state of the\ncomposite state then both the composite state as well as the sub-state are returned.",
        signature = "String[] getCurrentStateNames(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance on which to get the state machine state names.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "An array of all the current state names."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of the current states (if any). The state machine could\nconcurrently be in many states so all the active states names are returned. The state name returned\nis the name defined at design time without the path. If the state machine is in a sub-state of the\ncomposite state then both the composite state as well as the sub-state are returned.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String[] getCurrentStateNames(Concept instance) {
        ArrayList currentStates = new ArrayList();

        if (instance != null) {
            Property.PropertyStateMachine [] stateMachines= instance.getStateMachineProperties();
            for(int i=0; i < stateMachines.length; i++) {
                Property.PropertyStateMachine smprop= stateMachines[i];
                StateMachineConcept sm= smprop.getStateMachineConcept();
                if (sm != null) {
                    Property [] allStates= sm.getProperties();
                    for (int j=0; j < allStates.length; j++) {
                        PropertyStateMachineState state= (PropertyStateMachineState) allStates[j];
                        if (state != null) {
                            if (state.isActive() || state.isReady()) {
                                currentStates.add(state.getName());
                            }
                        }
                    }
                }
            }
        }
        String [] tmp = new String[currentStates.size()];
        return (String []) currentStates.toArray(tmp);
    }
}
