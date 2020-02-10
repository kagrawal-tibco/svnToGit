/**
 * @author ishaan
 * @version Jun 22, 2004, 8:32:26 PM
 */
package com.tibco.cep.designtime.model;


import com.tibco.be.util.BEResourceBundle;


public class BEModelBundle extends BEResourceBundle {


    protected static final BEModelBundle m_instance = new BEModelBundle();


    protected BEModelBundle() {
        super("com.tibco.cep.designtime.model.properties.model");
    }


    public synchronized static BEModelBundle getBundle() {
        return m_instance;
    }
}
