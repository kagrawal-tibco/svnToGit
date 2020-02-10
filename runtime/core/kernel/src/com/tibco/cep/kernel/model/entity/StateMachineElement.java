package com.tibco.cep.kernel.model.entity;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 31, 2009
 * Time: 10:12:22 AM
 * To change this template use File | Settings | File Templates.
 */

public interface StateMachineElement {
    public Element getOwnerElement();
    public java.util.List<Integer> getReadyStates();
}
