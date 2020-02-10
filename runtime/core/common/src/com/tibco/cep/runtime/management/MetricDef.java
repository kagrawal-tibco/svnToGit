package com.tibco.cep.runtime.management;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.tibco.cep.runtime.metrics.DataDef;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 28, 2009 Time: 11:17:05 AM
*/
public class MetricDef implements Externalizable {
    protected FQName name;

    protected DataDef dataDef;

    public MetricDef() {
    }

    public MetricDef(FQName name, DataDef dataDef) {
        this.name = name;
        this.dataDef = dataDef;
    }

    public FQName getName() {
        return name;
    }

    public DataDef getDataDef() {
        return dataDef;
    }

    //------------

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(dataDef);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (FQName) in.readObject();
        dataDef = (DataDef) in.readObject();
    }

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("MetricDef[name="+name.toString());
    	sb.append(",datadef=[");
    	sb.append(dataDef.toString());
    	sb.append("]");
    	return sb.toString();
    }
}
