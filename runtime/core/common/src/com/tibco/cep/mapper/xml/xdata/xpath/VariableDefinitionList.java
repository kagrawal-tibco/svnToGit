package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

public final class VariableDefinitionList
{
    private ArrayList mVariables = new ArrayList();
    private boolean mLocked;

    public static final VariableDefinitionList EMPTY_LIST = new VariableDefinitionList();

    public VariableDefinitionList() {
    }

    public VariableDefinitionList(VariableDefinition[] vars) {
        mVariables.ensureCapacity(vars.length);
        for (int i=0;i<vars.length;i++) {
            mVariables.add(vars[i]);
        }
    }

    public VariableDefinitionList(VariableDefinition[] newVars, VariableDefinitionList oldList) {
        mVariables.ensureCapacity(newVars.length + oldList.size());
        VariableDefinition[] old = oldList.getVariables();
        for (int i=0;i<old.length;i++) {
            mVariables.add(old[i]);
        }
        for (int i=0;i<newVars.length;i++) {
            //mVariables.add(newVars[i]);
            set(newVars[i]);
        }
    }

    /**
     * Returns variables in order (Order is for display purposes only)
     */
    public VariableDefinition[] getVariables() {
        return (VariableDefinition[]) mVariables.toArray(new VariableDefinition[mVariables.size()]);
    }

    /**
     * Returns the global variables in order (Order is for display purposes only),
     * equivalent to getVariables() for those variables where isGlobal() is true.
     */
    public VariableDefinition[] getGlobalVariables() {
        ArrayList temp = new ArrayList();
        for (int i=0;i<mVariables.size();i++) {
            VariableDefinition v = (VariableDefinition) mVariables.get(i);
            if (v.isGlobal()) {
                temp.add(v);
            }
        }
        return (VariableDefinition[]) temp.toArray(new VariableDefinition[temp.size()]);
    }

    /**
     * Returns the local variables in order (Order is for display purposes only)
     * equivalent to getVariables() for those variables where isGlobal() is false.
     */
    public VariableDefinition[] getLocalVariables() {
        ArrayList temp = new ArrayList();
        for (int i=0;i<mVariables.size();i++) {
            VariableDefinition v = (VariableDefinition) mVariables.get(i);
            if (!v.isGlobal()) {
                temp.add(v);
            }
        }
        return (VariableDefinition[]) temp.toArray(new VariableDefinition[temp.size()]);
    }

    public VariableDefinition getVariable(ExpandedName name) {
        // fix.
        VariableDefinition[] d = getVariables();
        for (int i=0;i<d.length;i++) {
            if (d[i].getName().equals(name)) {
                return d[i];
            }
        }
        return null;
    }

    public ExpandedName[] getVariableNames() {
        ExpandedName[] result = new ExpandedName[size()];
        for (int i = 0; i< size(); i++) {
            result[i] = ((VariableDefinition)mVariables.get(i)).getName();
        }
        return result;
    }

    public boolean hasVariable(ExpandedName variableName) {

		// performance... replace the getVariable call because it creates objects...
		int count = mVariables.size();
		for (int index = 0; index < count; index++) {
			VariableDefinition varDef = (VariableDefinition)mVariables.get(index);
			if (varDef.getName().equals(variableName))
				return true;
		}
		return false;
        //return getVariable(variableName)!=null;
    }

    public int size() {
        return mVariables.size();
    }

    public SmSequenceType getVariableType(ExpandedName name) {
        if (name==null) {
            return null;
        }
        VariableDefinition t = getVariable(name);
        if (t==null) {
            return null;
        }
        return t.getType();
    }

    public void add(VariableDefinition variable) {
        if (mLocked)
        {
            throw new IllegalStateException("Variable list locked");
        }
        if (variable==null)
        {
            throw new NullPointerException();
        }
        mVariables.add(variable);
    }

    /**
     * Checks for existing.
     * @param variable
     */
    public void set(VariableDefinition variable)
    {
        if (mLocked)
        {
            throw new IllegalStateException("Variable list locked");
        }
        if (variable==null)
        {
            throw new NullPointerException();
        }
        for (int i=0;i<mVariables.size();i++) {
            VariableDefinition vd = (VariableDefinition) mVariables.get(i);
            if (vd.getName().equals(variable.getName()))
            {
                mVariables.set(i,variable);
                return;
            }
        }
        // doesn't exist, just add.
        add(variable);
    }

    public void remove(ExpandedName name)
    {
        VariableDefinition[] d = getVariables();
        for (int i=0;i<d.length;i++) {
            if (d[i].getName().equals(name)) {
                mVariables.remove(d[i]);
            }
        }
    }

    /**
     * Locks to make definition list immutable.
     */
    public void lock() {
        mLocked = true;
    }

    public Object clone() {
        return new VariableDefinitionList(getVariables());
    }

    /**
     * Equivalent to clone() then lock(), but returns 'this' if already locked.
     */
    public VariableDefinitionList createLockedCopy() {
        if (mLocked) {
            return this;
        }
        VariableDefinitionList vars = (VariableDefinitionList) clone();
        vars.lock();
        return vars;
    }

    public void format(StringBuffer sb) {
        VariableDefinition[] d = getVariables();
        for (int i=0;i<d.length;i++) {
            if (i>0) sb.append(", ");
            sb.append(d[i].getName());
            SmSequenceType t = d[i].getType();
            sb.append(" (" + t.toString() + ")");
        }
    }

    /**
     * Equivalent to <code>{@link #size}==0</code>.
     * @return
     */
    public boolean isEmpty()
    {
        return size()==0;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        format(sb);
        return sb.toString();
    }
}

