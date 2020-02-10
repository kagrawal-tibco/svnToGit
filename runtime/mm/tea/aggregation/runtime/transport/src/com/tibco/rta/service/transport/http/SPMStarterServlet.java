package com.tibco.rta.service.transport.http;

import com.tibco.rta.engine.RtaEngine;
import com.tibco.rta.engine.RtaEngineFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 31/1/14
 * Time: 8:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SPMStarterServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        String configFilePath = config.getInitParameter("spmConfigLocation");
        Properties configuration = new Properties();
        try {
            configuration.load(new FileInputStream(new File(configFilePath)));
            RtaEngine engine = RtaEngineFactory.getInstance().getEngine(configuration);

            engine.init(configuration);
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
