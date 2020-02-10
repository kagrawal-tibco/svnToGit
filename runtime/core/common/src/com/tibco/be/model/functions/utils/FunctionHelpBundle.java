package com.tibco.be.model.functions.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.util.BEResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 6, 2004
 * Time: 3:44:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionHelpBundle extends BEResourceBundle {
    protected static FunctionHelpBundle m_instance = null;
    protected static final String PREFIX= "be.help.functions.category";

    protected FunctionHelpBundle(InputStream stream) throws IOException {
        //super("com.tibco.be.model.functions.resources.functions-help");
        super(stream);
    }

    /**
     *
     * @return
     */
    public synchronized static FunctionHelpBundle getBundle() throws Exception {
        Enumeration e = null;
        ArrayList urlList = new ArrayList();
        if (m_instance == null) {

            e = FunctionHelpBundle.class.getClassLoader().getSystemResources("functions-help.properties");
            while (e.hasMoreElements()) {
                urlList.add(e.nextElement());
            }
            Properties p = new Properties();
            for (final Object anUrlList : urlList) {
                final URL url = (URL) anUrlList;
                final Properties loadedProps = new Properties();
                final InputStream is = url.openStream();
                try {
                    loadedProps.load(is);
                }
                finally {
                    is.close();
                }
                for (final Map.Entry<Object, Object> entry : loadedProps.entrySet()) {
                    final Object k = entry.getKey();
                    if (!p.containsKey(k)) {
                        p.put(k, entry.getValue());
                    }
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            p.store(baos, ""); //todo Comment?
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            baos.close();            
            m_instance = new FunctionHelpBundle(bais);
            bais.close();
            p.clear();


//            Enumeration pn = p.propertyNames();
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            while(pn.hasMoreElements()) {
//                String propertyName = (String) pn.nextElement();
//                pw.println(propertyName+"="+p.getProperty(propertyName,""));
//            }

        }
        return m_instance;


    }

    /**
     *
     * @param bundle
     * @param key
     * @return
     */
    public static String [] toStringArray(FunctionHelpBundle bundle, String key) {
        String size= bundle.getString(key + ".size");
        if ((size != null) && (size.trim().length() > 0)) {
            int szInt=Integer.parseInt(size);
            String [] ret= new String[szInt];
            for (int i=0; i < ret.length; i++) {
                ret[i]= bundle.getString(key+"."+i);
            }
            return ret;
        }
        return null;
    }

    /**
     *
     * @param function
     * @return
     */
    public String tooltip (JavaStaticFunction function) {
        return getString(PREFIX + "." + function.getName().getNamespaceURI() + ".method." + function.getName().getLocalName() + ".tooltip");
    }
    /**
     *
     * @param function
     * @return
     */
    public String signature (JavaStaticFunction function) {
        return getString(PREFIX + "." + function.getName().getNamespaceURI() + ".method." + function.getName().getLocalName() + ".signature");
    }

}

