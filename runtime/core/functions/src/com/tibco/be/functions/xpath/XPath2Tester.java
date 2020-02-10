package com.tibco.be.functions.xpath;

import javax.xml.namespace.QName;

import org.genxdm.bridgekit.xs.BuiltInSchema;
import org.genxdm.typed.TypedContext;
import org.genxdm.xs.types.Type;

import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.functions.VariableImpl;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.VariableListImpl;
import com.tibco.genxdm.bridge.xinode.XiProcessingContext;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Text;

public class XPath2Tester {

	public static void main(String[] args) {
		XPath2Helper helper = new XPath2Helper();
		
		QName variableName = new QName("s");
		XiNode typedVariableNode = new Text("Greetings");
		Type variableType = BuiltInSchema.SINGLETON.STRING;
		XiProcessingContext pcx = new XiProcessingContext();
		TypedContext<XiNode, XmlAtomicValue> typedContext = pcx.getTypedContext(BESchemaComponentCacheManager.getInstance().getCache());
		boolean compatibleMode = false;
		VariableList list = new VariableListImpl();
		Variable var = new VariableImpl("s", "Greetings");
		list.addVariable(var);
		String s = helper.evalAsString("concat($s, \"Hi\", \"There\")", list, compatibleMode);
		System.out.println("Result: "+s);
	}
	
}
