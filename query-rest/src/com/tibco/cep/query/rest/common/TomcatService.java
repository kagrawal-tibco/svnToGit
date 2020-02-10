package com.tibco.cep.query.rest.common;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.query.rest.service.Service;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 2/13/14
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class TomcatService implements Runnable, Service
{

   //  public static void main(String args[]) throws LifecycleException, ServletException {
  com.tibco.cep.kernel.service.logging.Logger logger;

    public TomcatService() {
        logger = LogManagerFactory.getLogManager().getLogger(TomcatService.class);
    }

    private void startRestService() throws LifecycleException, ServletException, InterruptedException {
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


        server.start();
        server.getServer().await();
     //  Thread.currentThread().join();



    }

    @Override
    public void run() {
//        try {
//            startRestService();
//            logger.log(Level.INFO,"Done with run() ");
//            while(true){}
//        } catch (LifecycleException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (ServletException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

    @Override
    public void start() throws ServletException, InterruptedException, LifecycleException {
        startRestService();
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
