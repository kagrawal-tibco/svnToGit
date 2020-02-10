package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.impl.TimeEventFunction;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFTimeEventFunction extends EMFOntologyModelFunction implements TimeEventFunction {

    private ExpandedName fFunctionName;
    private Class [] fParameterTypes;
    private String [] fParameterNames;

    EMFTimeEventFunction(Event model, ExpandedName functionName) {
        super(model);
        this.fFunctionName=functionName;
        setFunctionDomains(new FunctionDomain[]{ACTION});
    }

	@Override
	String getDescription() {
        return "Schedules a time event of type <" + getModel().getFullPath() + ">";
	}

	@Override
	public Entity[] getEntityArguments() {
        return new Entity[0];
	}

	@Override
	public Entity getEntityReturnType() {
		return null;
	}

	@Override
	String[] getParameterNames() {
        putArgs(false);
        return fParameterNames;
	}

	@Override
	public String code() {
        return this.getModelClass() + "." + fFunctionName.getLocalName();
	}

	@Override
	public boolean doesModify() {
		return false;
	}

	@Override
	public Class[] getArguments() {
        putArgs(false);
        return fParameterTypes;
	}

	@Override
	public String getDocumentation() {
		return code();
	}

	@Override
	public ExpandedName getName() {
		return fFunctionName;
	}

	@Override
	public Class getReturnClass() {
        return com.tibco.cep.kernel.model.entity.Event.class;
	}

	@Override
	public Class[] getThrownExceptions() {
		return new Class[0];
	}

	@Override
	public boolean isTimeSensitive() {
		return false;
	}

	@Override
	public boolean requiresAsync() {
		return false;
	}

    void putArgs(boolean refresh) {

        try {
            if (refresh || (fParameterTypes == null)) {
                fParameterTypes = new Class[] {long.class, String.class, long.class};
                fParameterNames = new String[] {"delay", "closure", "ttl"};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
