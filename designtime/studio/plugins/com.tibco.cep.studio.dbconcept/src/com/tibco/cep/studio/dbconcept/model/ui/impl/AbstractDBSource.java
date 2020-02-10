package com.tibco.cep.studio.dbconcept.model.ui.impl;


import com.tibco.cep.designtime.model.service.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 29, 2005
 * Time: 11:35:45 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDBSource implements DataSource {
    public String m_jdbcChannelRef;

    public AbstractDBSource (String jdbcChannelRef) {
        this.m_jdbcChannelRef = jdbcChannelRef;
    }

    public String getJDBCChannelRef() {
        return m_jdbcChannelRef;
    }

}
