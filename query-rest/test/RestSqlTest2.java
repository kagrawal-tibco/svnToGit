/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/10/14
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 * http://localhost:8080/RestQuery/asg/ftocservice
 * http://localhost:8080/RestQuery/asg/ctofservice
 */

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class RestSqlTest2 {

    public static void main(String args[]) throws LifecycleException {

        //this is the name of the .war
        //if you are running in .war mode, then you need to have a contextPath
        //and supply it to addWebapp call
        //Note that the web.xml in the .war should be inside the WEB-INF directory. Otherwise, tomcat will not find it.
        //String contextPath = "/RestJerseyExample";
        String contextPath = "/RestJersey";

        //String contextPath = "/";
        String host = "localhost";
        int port = 8080;
        String appBase = "C:/appBaseRest";

        Tomcat server = new Tomcat();
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
    }
}
