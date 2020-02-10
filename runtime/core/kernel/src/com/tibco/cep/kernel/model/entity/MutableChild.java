package com.tibco.cep.kernel.model.entity;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 9, 2009
 * Time: 3:33:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableChild extends Mutable{
    public int getPropertyLevel();
    public int getPropertyIndex();

}
