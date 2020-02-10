package com.tibco.cep.query.rest.service;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/21/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryService implements Service {
    private boolean started = false;
    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(QueryService.class);
    private Tomcat server = null;

    public QueryService() {
    }

    @Override
    public void start() {

//        TomcatService tomcatService = new TomcatService();
//        try {
//            tomcatService.startRestService();
//        } catch (LifecycleException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (ServletException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        // Start the embedded server
//        Runnable runnable = new Runnable() {
//            public void run() {
                /*try {
                        //this is the name of the .war
                        //if you are running in .war mode, then you need to have a contextPath
                        //and supply it to addWebapp call
                        //Note that the web.xml in the .war should be inside the WEB-INF directory. Otherwise, tomcat will not find it.
                        String contextPath = "/appRestQuery";//"/RestQuery";

                        //String contextPath = "/";
                        String host = "localhost";
                        int port = 8080;
                        String appBase = "C:/appRest"; //"C:/appBaseRest";

                        server = new Tomcat();
                        server.setHostname(host);
                        server.setBaseDir(appBase);
                        server.setPort(port);

                        StandardHost localHost = (StandardHost) server.getHost();
                        localHost.setAppBase(appBase);
                        localHost.setUnpackWARs(true);
                        localHost.setAutoDeploy(true);

                        StandardContext rootContext = (StandardContext) server.addContext(contextPath, appBase);
                        // Define DefaultServlet.
                        Wrapper defaultServlet = rootContext.createWrapper();
                        defaultServlet.setName("default");
                        //set the servlet
                        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
                        defaultServlet.addInitParameter("debug", "1");
                        defaultServlet.addInitParameter("listings", "false");
                        defaultServlet.setLoadOnStartup(1);
                        rootContext.addChild(defaultServlet);
                        rootContext.addServletMapping("/", "default");
                        Engine engine = server.getEngine();
                        engine.setDefaultHost(host);
                        engine.setName("TomcatEngine");

                        //the 2nd parameter of addWebapp is a url ( string )
                        //eg: http://localhost:8080/RestQuery/asg/ctofservice
                        //if "unpack" war is true, then tomcat explodes the .war into a directory with this url name ( in this case /RestQuery dir )
                        Context context = server.addWebapp(localHost, "/RestQuery", appBase + contextPath);
                        //context.setAltDDName("C://appBaseRest//WebContent//WEB-INF//web.xml");

                        server.start();
                        server.getServer().await();
                    } catch(Throwable e) {
                        LOGGER.log(Level.ERROR, "Error starting the query-rest service");
                    }*/

                //----------------------------

                String contextPath = "/appRestQuery";// "/RESTJerseyExample";
                String host = "localhost";
                int port = 8080;
                String appBase = "C:/appRest/RestQuery";//"C:/appRest"; //"C:/appBaseRest";
                String webApp = "RESTJerseyExample.war";


                Tomcat server = new Tomcat();
                server.setHostname(host);
                //server.setBaseDir(appBase);
                server.setPort(port);

                StandardHost localHost = (StandardHost) server.getHost();
                localHost.setAppBase(appBase);
                //  localHost.setUnpackWARs(true);
                localHost.setAutoDeploy(true);

                StandardContext rootContext = (StandardContext) server.addContext(contextPath, appBase);
                // Define DefaultServlet.
                Wrapper defaultServlet = rootContext.createWrapper();
                defaultServlet.setName("default");
                defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
                defaultServlet.addInitParameter("debug", "0");
                defaultServlet.addInitParameter("listings", "false");
                defaultServlet.setLoadOnStartup(1);
                rootContext.addChild(defaultServlet);
                {
                    rootContext.addServletMapping("/", "default");
                }

                Engine engine = server.getEngine();
                engine.setDefaultHost(host);
                engine.setName("TomcatEngine");

                server.addWebapp(localHost, "/RestQuery", appBase);// + contextPath);


                try {
                    server.start();
                } catch (LifecycleException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                server.getServer().await();
               // }
//        };
//
//        new Thread(null, runnable, "Query-Rest-Startup").start();
//        started = true;
    }

    @Override
    public void stop() {
        if (started) {
            try {
            // Stop the server
            this.server.stop();
            } catch (Throwable e) {
                LOGGER.log(Level.ERROR, "Error stopping the query-rest service");
            }
        }
    }
}
