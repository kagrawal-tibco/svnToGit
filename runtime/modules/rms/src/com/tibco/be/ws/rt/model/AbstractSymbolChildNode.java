package com.tibco.be.ws.rt.model;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/12
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSymbolChildNode<T> {

    /**
         * Property/Concept/Event
         */
    protected T wrapped;

    private String alias;

    private String type;

    private boolean visited;
    
    private String[] domainModelPath;

    /**
     * Every node gets a unique id.
     */
    private String guid;

    protected AbstractSymbolChildNode(T wrapped) {
        this.wrapped = wrapped;
        guid = UUID.randomUUID().toString();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getGUID() {
        return guid;
    }

    public T getWrappedEntity() {
        return wrapped;
    }

    public void setWrapped(T wrapped) {
        this.wrapped = wrapped;
    }
    
    public String[] getDomainModelPath() {
		return domainModelPath;
	}

	public void setDomainModelPath(String[] domainModelPath) {
		this.domainModelPath = domainModelPath;
	}

	/**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[GUID] -> ");
        stringBuilder.append(guid);
        stringBuilder.append(" ");
        stringBuilder.append("[Alias] -> ");
        stringBuilder.append(alias);
        stringBuilder.append(" ");
        stringBuilder.append("[Type] -> ");
        stringBuilder.append(type);

        return stringBuilder.toString();
    }
}
