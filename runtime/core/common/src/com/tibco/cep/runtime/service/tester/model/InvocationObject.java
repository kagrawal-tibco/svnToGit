package com.tibco.cep.runtime.service.tester.model;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_CAUSAL_OBJECTS;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_CAUSAL_OBJECTS_END_STATE;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_INVOCATION_OBJECT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_INVOCATION_OBJECT_NS;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_INVOCATION_OBJECT_TYPE;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 12:31:59 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class InvocationObject extends ReteObject {

    private CausalObject[] inputEndState; // the end state of the input objects
    private CausalObject[] inputStartState; // the initial state of the input objects

    private InvocationObjectType invocationObjectType;
	private XiNode endStateObjectsNode;
	private XiNode startStateObjectsNode;

    public InvocationObject() {

    }

    public InvocationObject(Object wrappedObject, Object[] input) {
        super(wrappedObject);
        if (wrappedObject instanceof Rule) {
            invocationObjectType = InvocationObjectType.RULE;
        } else if (wrappedObject instanceof RuleFunction) {
            invocationObjectType = InvocationObjectType.RULEFUNCTION;
        } else{
        	invocationObjectType = InvocationObjectType.UNKNOWN;
        }
        this.inputStartState = new ReteObject.CausalObject[input.length];
        for (int loop = 0, length = input.length; loop < length; loop++) {
            this.inputStartState[loop] = new CausalObject(input[loop], ReteChangeType.ASSERT);
        }
    }

    public XiNode getEndStateSnapshotNode() {
		return endStateObjectsNode;
	}
    
    public XiNode getStartStateSnapshotNode() {
		return startStateObjectsNode;
	}
    
    /* (non-Javadoc)
      * @see com.tibco.cep.studio.tester.core.ReteObject#getCausalObjects()
      */
    public TesterObject[] getCausalObjects() {
        return inputStartState;
    }

    public void setInputStartState(Object[] startStateObjects) {
        this.inputStartState = new ReteObject.CausalObject[startStateObjects.length];
        for (int loop = 0, length = startStateObjects.length; loop < length; loop++) {
            this.inputStartState[loop] = (CausalObject) startStateObjects[loop];// new CausalObject(endStateObjects[loop], ReteChangeType.ASSERT);
        }
        try {
        	if (inputStartState != null) {
        		clearChildren(startStateObjectsNode);
        		for (CausalObject causalObjectStartState : inputStartState) {
        			//Append children
        			causalObjectStartState.serialize(XiSupport.getXiFactory(), startStateObjectsNode);
        		}
        	}
		} catch (Exception e) {
		}
    }
    
    private void clearChildren(XiNode parentNode) {
    	if (parentNode == null) {
    		return;
    	}
    	XiNodeUtilities.removeAllChildren(parentNode);
	}

	public void setInputEndState(Object[] endStateObjects) {
        this.inputEndState = new ReteObject.CausalObject[endStateObjects.length];
        for (int loop = 0, length = endStateObjects.length; loop < length; loop++) {
            this.inputEndState[loop] = (CausalObject) endStateObjects[loop];// new CausalObject(endStateObjects[loop], ReteChangeType.ASSERT);
        }
        try {
        	if (inputEndState != null) {
        		clearChildren(endStateObjectsNode);
        		for (CausalObject causalObjectEndState : inputEndState) {
        			//Append children
        			causalObjectEndState.serialize(XiSupport.getXiFactory(), endStateObjectsNode);
        		}
        	}
		} catch (Exception e) {
		}
    }
    
    @Override
    public void serialize(XiFactory factory, XiNode executionObjectNode) throws Exception {
        XiNode invocationObjectNode = factory.createElement(EX_INVOCATION_OBJECT);

        String namespace = null;
        if (wrappedObject instanceof Rule) {
            namespace = ((Rule)wrappedObject).getUri();
        } else if (wrappedObject instanceof RuleFunction) {
            namespace = ((RuleFunction)wrappedObject).getSignature();
        }
        XiNode namespaceNode = factory.createElement(EX_INVOCATION_OBJECT_NS);
        namespaceNode.setStringValue(namespace);
        invocationObjectNode.appendChild(namespaceNode);

        XiNode typeNode = factory.createElement(EX_INVOCATION_OBJECT_TYPE);
        typeNode.setStringValue(invocationObjectType.toString());
        invocationObjectNode.appendChild(typeNode);

        executionObjectNode.appendChild(invocationObjectNode);

        //Serialize causal objects
        startStateObjectsNode = factory.createElement(EX_CAUSAL_OBJECTS);
        executionObjectNode.appendChild(startStateObjectsNode);

        for (CausalObject causalObject : inputStartState) {
            //Append children
            causalObject.serialize(factory, startStateObjectsNode);
        }
        
        //Serialize causal objects
        endStateObjectsNode = factory.createElement(EX_CAUSAL_OBJECTS_END_STATE);
        executionObjectNode.appendChild(endStateObjectsNode);

        if (inputEndState != null) {
        	for (CausalObject causalObjectEndState : inputEndState) {
        		//Append children
        		causalObjectEndState.serialize(factory, endStateObjectsNode);
        	}
        }
    }

	public void setInputEndStateSnapshot(XiNode endStateSnapshotNode) {
		if (endStateObjectsNode != null) {
			XiNode parent = endStateObjectsNode.getParentNode();
			parent.removeChild(endStateObjectsNode);
			parent.appendChild(endStateSnapshotNode);
		}
		endStateObjectsNode = endStateSnapshotNode;
	}
	
	public void setInputStartStateSnapshot(XiNode startStateSnapshotNode) {
		if (startStateObjectsNode != null) {
			XiNode parent = startStateObjectsNode.getParentNode();
			parent.removeChild(startStateObjectsNode);
			parent.appendChild(startStateSnapshotNode);
		}
		startStateObjectsNode = startStateSnapshotNode;
	}
}
