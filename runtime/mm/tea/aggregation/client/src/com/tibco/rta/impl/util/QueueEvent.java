package com.tibco.rta.impl.util;

import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueEvent extends EventObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3913012126494895297L;

	public QueueEvent(Object source) {
        super(source);
    }
}
