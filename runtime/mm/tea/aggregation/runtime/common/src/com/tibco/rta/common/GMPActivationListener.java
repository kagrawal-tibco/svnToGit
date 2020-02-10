package com.tibco.rta.common;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 16/5/13
 * Time: 11:05 AM
 * Notification interface when first message is delivered to SPM engine.
 */
public interface GMPActivationListener {

    /**
     * Callback when an SPM engine is activated.
     */
    public void onActivate();
    
    /**
     * Callback when network fails
     */
    public void onDeactivate();
}
