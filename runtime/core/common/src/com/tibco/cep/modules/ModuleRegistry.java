package com.tibco.cep.modules;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.InputSource;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 7, 2007
 * Time: 2:53:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModuleRegistry {

    private static ModuleRegistry gInstance = null;
    static XiParser parser = XiParserFactory.newInstance();

    private HashMap modules = new HashMap();

    public synchronized static ModuleRegistry getInstance() {
        if (gInstance == null) {
            gInstance = new ModuleRegistry();
            gInstance.loadModules();
        }

        return gInstance;
    }

    protected ModuleRegistry() {

    }

    public CEPModule getModule(String name) {
        return (CEPModule) modules.get(name);
    }

    private void loadModules() {
        try {

            StringBuffer loaded_files = new StringBuffer();
            ArrayList urlList = new ArrayList();
            Enumeration e = ModuleRegistry.class.getClassLoader().getSystemResources("cep-modules.xml");
            while (e.hasMoreElements()) {
                urlList.add(e.nextElement());
            }
            e = ModuleRegistry.class.getClassLoader().getResources("cep-modules.xml");
            while (e.hasMoreElements()) {
                urlList.add(e.nextElement());
            }

            for (Iterator iter = urlList.iterator(); iter.hasNext();) {
                URL url = (URL)iter.next();
                String filename = url.getFile();
                if (filename.indexOf("!")!=-1) filename=filename.substring(0, filename.indexOf("!"));
                filename = url.getHost() +  "/" +  filename.substring(filename.lastIndexOf("/") + 1, filename.length());
                if (loaded_files.indexOf(filename)!=-1) { // already loaded ?
                    System.out.println("Ignoring module :" + url.getFile() + " already loaded from a different location");
                    continue;
                } else loaded_files.append(filename+";"); // record loading
                InputStream is = url.openStream();
                System.out.println("Loading modules from :" + url);
                XiNode document = parser.parse(new InputSource(is));
                loadModulesFromDocument(document);
            }


        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void loadModulesFromDocument(XiNode node) {

        XiNode modulesNode = XiChild.getChild(node, ExpandedName.makeName("modules"));

        Iterator itr = XiChild.getIterator(modulesNode, ExpandedName.makeName("module"));
        while (itr.hasNext()) {
            XiNode moduleNode = (XiNode) itr.next();
            loadModule(moduleNode);
        }

    }

    private void loadModule(XiNode moduleNode) {
        String moduleName = XiAttribute.getStringValue(moduleNode, "name");
//        XiNode dtNode = XiChild.getChild(moduleNode, ExpandedName.makeName("designtime"));
//        String dtFactory = XiAttribute.getStringValue(dtNode, "factory");

        XiNode rtNode = XiChild.getChild(moduleNode, ExpandedName.makeName("runtime"));
        String rtFactory = XiAttribute.getStringValue(rtNode, "factory");

//        XiNode cgNode = XiChild.getChild(moduleNode, ExpandedName.makeName("codegen"));
//        String cgFactory = XiAttribute.getStringValue(cgNode, "factory");

//        XiNode uiNode = XiChild.getChild(moduleNode, ExpandedName.makeName("uiresource"));
//        String uiFactory = XiAttribute.getStringValue(uiNode, "factory");

        try {
            CEPModule module = new CEPModule(moduleName,rtFactory);
            modules.put(moduleName, module);
        }
        catch (Exception e) {
            System.out.println("Error loading module -" + moduleName);
            e.printStackTrace();
        }

    }

    public Collection getModules() {
        return modules.values();
    }
}
