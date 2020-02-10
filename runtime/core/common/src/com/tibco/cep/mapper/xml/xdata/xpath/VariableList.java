package com.tibco.cep.mapper.xml.xdata.xpath;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.SAXException;

import com.tibco.xml.channel.sequence.XmlSequenceHandler;
import com.tibco.xml.channel.variable.VariableContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.helpers.NoNamespace;

public final class VariableList implements VariableContext, Serializable
{
    private transient VariableDefinitionList mDefinitionList;
    private HashMap mVariables = new HashMap(); // change into an array later...
    private static XmlSequence nullXmlSequence = new NullXmlSequence();

    // Use release 1.1.0 serialVersionUID so that checkpoint files will be readable
    // by future versions.
    private static final long serialVersionUID = 6074076064799030174L;

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField gf = in.readFields();
        mVariables = (HashMap) gf.get("mVariables", null);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        ObjectOutputStream.PutField pf = out.putFields();
        pf.put("mVariables", mVariables);
        pf.write(out);
    }

    public VariableList(VariableDefinitionList list) {
        mDefinitionList = list;
        list.lock();
    }

    public VariableList(VariableDefinitionList list, VariableList valueList) {
        mDefinitionList = list;
        mVariables = (HashMap)valueList.mVariables.clone();
    }

    public VariableDefinitionList getDefinitionList() {
        return mDefinitionList;
    }

    public Variable getVariable(ExpandedName name) {
        return (Variable)mVariables.get(name);
    }

    public boolean containsKey(ExpandedName name)
    {
        if (NoNamespace.isNoNamespaceURI(name.getNamespaceURI()))
        {
            if (mDefinitionList.hasVariable(name)) {
                return true;
            } else {
                return mVariables.containsKey(name);
            }
        }
        return false;
    }

    public XmlSequence getVariableValue(ExpandedName name)
    {
        if (NoNamespace.isNoNamespaceURI(name.getNamespaceURI()))
        {
            Variable v = (Variable) mVariables.get(name);
            if (v!=null)
            {
                XiNode n = v.getValue();
                if (n!=null)
                {
                    return n;
                }
                return new XsDouble(v.getNumber());
            }
        }
        return nullXmlSequence;
    }

    public void setVariableValue(ExpandedName name, XmlSequence value)
    {
        if (NoNamespace.isNoNamespaceURI(name.getNamespaceURI()))
        {
            mVariables.put(name,value);
            return;
        }
    }

    public boolean isSet(ExpandedName name) {
        return mVariables.containsKey(name);
    }

    public void setVariable(ExpandedName name, Variable value) {
        if (name.getLocalName().charAt(0) == '$') {
            throw new IllegalArgumentException("Variable name can not start with $: " + name.getLocalName());
        }
        mVariables.put(name,value);
    }

    public void clearVariable(ExpandedName name) {
        mVariables.remove(name);
    }

    public void restore(VariableDefinitionList definitions, SmNamespaceProvider sp) throws SAXException {
        // for all variables, restore...
        mDefinitionList = definitions;
        Iterator it = getVariableNames();
        while (it.hasNext()) {
            ExpandedName name = (ExpandedName) it.next();
            Variable variable = getVariable(name);
            if (variable == null) {
                //System.out.println("variable null for name " + name);
                continue;
            }
            XiNode value = variable.getValue();
            if (value == null) {
                //System.out.println("variable value null for name " + name);
                continue;
            }
        }
    }

    /**
     * Returns the names of all the variables contained
     * in this list.
     */
    public Iterator getVariableNames() {
        return mVariables.keySet().iterator();
    }

    public void copyValuesFrom(VariableList list) {
        VariableDefinition[] d = mDefinitionList.getVariables();
        for (int i=0;i<d.length;i++) {
            VariableDefinition vd = d[i];
            ExpandedName name = vd.getName();
            Variable v = list.getVariable(name);
            if (v!=null) {
                setVariable(name,v);
            }
        }
    }

    public String toString() {
        Iterator i = getVariableNames();
        boolean any = false;
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        while (i.hasNext()) {
            if (any) {
                sb.append(", ");
            }
            any = true;
            String str = (String) i.next();
            sb.append(str);
        }
        sb.append("}");
        return sb.toString();
    }

    public void list(PrintStream ps) {
        Iterator i = getVariableNames();
        ps.println("VariableList {");
        while (i.hasNext()) {
            ExpandedName exName = (ExpandedName) i.next();
            ps.println("\t" + exName + ": " + getVariable(exName));
        }
        ps.println("}");
    }
}

    class NullXmlSequence implements XmlSequence {
        public int size() {
            return 0;
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean isSingleton() {
            return true;
        }

        public boolean isNodeSet() {
            return false;
        }

        public boolean isAtomSet() {
            return false;
        }

        public XmlItem getItem(int i) throws ArrayIndexOutOfBoundsException {
            return null;
        }

        public void serialize(XmlSequenceHandler xmlSequenceHandler) throws SAXException {
        }

    }

