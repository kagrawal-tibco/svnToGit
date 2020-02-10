package com.tibco.be.migration.expimp.providers.bdb;

import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.cep.kernel.service.logging.Level;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 20, 2008
 * Time: 6:02:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BdbImporter_v2_0 {

    public BdbImporter_v2_0() {

    }

    public void init(ExpImpContext context, ExpImpStats stats) {

        context.getLogger().log(Level.ERROR, "Import to BDB is not supported in this version.");
    }

    public void importAll() {


    }

    public void destroy() {


    }
}