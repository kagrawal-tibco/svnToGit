package com.tibco.cep.runtime.service.tester.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.tester.core.TesterRun;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

public abstract class TesterObject {

	protected Object wrappedObject;
    protected InvocationObject invocationObject;
	protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private static final int TIBCO_BE_NS_LENGTH = "www.tibco.com/be/ontology".length();
	private TesterRun testerRun;
	protected String timestamp;
	protected XiNode tempRoot;
	
	public abstract void serialize(XiFactory factory, XiNode rootNode) throws Exception;

	public TesterObject() {
		super();
	}


    public TesterObject(InvocationObject invocationObject, Object wrappedObject) {
        this.wrappedObject = wrappedObject;
        this.invocationObject = invocationObject;
        this.timestamp = sdf.format(new Date());
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Object getWrappedObject() {
	    return wrappedObject;
	}

	public void setWrappedObject(Object wrappedObject) {
	    this.wrappedObject = wrappedObject;
	}

	/**
	 * @return the testerRun
	 */
	public final TesterRun getTesterRun() {
	    return testerRun;
	}

	/**
	 * @param testerRun the testerRun to set
	 */
	public final void setTesterRun(TesterRun testerRun) {
	    this.testerRun = testerRun;
	}

	/**
	 * The runtime path of this object
	 *
	 * @return
	 */
	public String getNamespace() {
	    String namespace = null;
	    if (wrappedObject instanceof Concept) {
	        namespace = ((Concept) wrappedObject).getExpandedName().getNamespaceURI();
	    } else if (wrappedObject instanceof SimpleEvent) {
	        namespace = ((SimpleEvent) wrappedObject).getExpandedName().getNamespaceURI();
	    }
	    if (namespace != null) {
	        namespace = namespace.substring(TIBCO_BE_NS_LENGTH, namespace.length());
	    }
	    return namespace;
	}
	
    public void setInvocationObject(InvocationObject invocationObject) {
        this.invocationObject = invocationObject;
    }

    /**
     * @return the invocationObject
     */
    public InvocationObject getInvocationObject() {
        return invocationObject;
    }

}