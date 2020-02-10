/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/5/14
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.util.logging.*;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class RestSqlTest {

    public static void main(String args[]) throws LifecycleException {

        //String contextPath = "/RESTJerseyExample";
        String contextPath = "/";

        String host = "localhost";
        int port = 8080;
        String appBase = "C:/RESTJerseyExample/WebContent/WEB-INF/classes";

        //String appBase = "C:/appBaseRest";
        //String appBase = "C:/RESTJerseyExample/WebContent/";
        //String webApp = "RESTJerseyExample.war";

        /*
            try {
                java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");
                Handler fileHandler = new ConsoleHandler();
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.FINEST);
                fileHandler.setEncoding("UTF-8");
                logger.addHandler(fileHandler);
            } catch ( Exception e) {
                e.printStackTrace();
            }
        */

        Tomcat server = new Tomcat();
        server.setHostname(host);
        server.setBaseDir(appBase);
        server.setPort(port);

        StandardHost localHost = (StandardHost) server.getHost();
        localHost.setAppBase(appBase);
        //localHost.setUnpackWARs(true);
        localHost.setAutoDeploy(true);

        StandardContext rootContext = (StandardContext) server.addContext(contextPath, appBase);
        // Define DefaultServlet.
        Wrapper defaultServlet = rootContext.createWrapper();
        defaultServlet.setName("default");

        //set the servlet
        /*
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        rootContext.addChild(defaultServlet);
        rootContext.addServletMapping("/", "default");
        */

        defaultServlet.setServletClass("com.sun.jersey.spi.container.servlet.ServletContainer");
        defaultServlet.addInitParameter("debug", "1");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.addInitParameter("com.sun.jersey.config.property.packages", "com.tibco.cep.query.rest");
        defaultServlet.setLoadOnStartup(1);
        rootContext.addChild(defaultServlet);
        rootContext.addServletMapping("/", "default");

        Engine engine = server.getEngine();
        engine.setDefaultHost(host);
        engine.setName("TomcatEngine");

        Context context = server.addWebapp(localHost, "/Test", appBase + contextPath);
//context.setAltDDName("C:\\RESTJerseyExample\\xml\\web.xml");

//    String baseDir = new File(".").getAbsolutePath();
//      Context ctx = server.addContext(contextPath, baseDir);
//        Tomcat.addServlet(ctx, "Jersey Web Application", "com.sun.jersey.spi.container.servlet.ServletContainer");
//
//        Tomcat.addServlet(ctx, "ctofservice", "com.tibco.asg.server.CtoFService");
//
//
//        ctx.addServletMapping("/asg/*","Jersey Web Application" );
//        ctx.addServletMapping("/asg/ctofservice","ctofservice" );

        server.start();
        server.getServer().await();


    }
}

