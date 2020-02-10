package com.tibco.be.util.packaging.descriptors;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 6:07:53 PM
 */


import com.tibco.be.util.packaging.descriptors.impl.ServiceArchiveImpl;

// Referenced classes of package com.tibco.archive.helpers:
//            VFileFactoryProvider


public class ServiceArchiveFactory {


//    public static final String BASE_RESOURCE_NAME = "Archive";

//
//    public ServiceArchiveFactory() {
//    }
//
//
//    public static String getDiffMessage(String s, Locale locale) {
//        return (String) ResourceBundle.getBundle("Archive", locale).getObject(s);
//    }
//
//
//    public static ServiceArchive getServiceArchive(InputStream inputstream)
//            throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        return a(new DefaultVFileFactoryProvider(inputstream));
//    }
//
//
//    private static ServiceArchive a(VFileFactoryProvider vfilefactoryprovider)
//            throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        return getServiceArchive(vfilefactoryprovider, null);
//    }
//
//
//    static ServiceArchive getServiceArchive(VFileFactoryProvider vfilefactoryprovider, String s)
//            throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        ApplicationArchivePluginSourceFormat applicationarchivepluginsourceformat = new ApplicationArchivePluginSourceFormat(vfilefactoryprovider, s);
//
//        return new ServiceArchiveImpl(applicationarchivepluginsourceformat, null, vfilefactoryprovider);
//    }
//
//
//    public static ServiceArchive getServiceArchive(File file)
//            throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        if (file == null) {
//            throw new IllegalArgumentException("A File must be specified when loading a service archive.");
//        } else {
//            return getServiceArchive(((VFileFactoryProvider) (new DefaultVFileFactoryProvider(new FileInputStream(file)))), "TIBCOServiceArchive");
//        }
//    }
//


    public static MutableServiceArchive newServiceArchive(String name) {
        if ((null == name) || name.isEmpty()) {
            throw new IllegalArgumentException("A name must be specified when creating an application archive.");
        }
        try {
            return new ServiceArchiveImpl(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


//    public static MutableServiceArchive newServiceArchive(String s, VFileFactory vfilefactory)
//            throws ObjectRepoException, IOException {
//        if (s == null || s.length() == 0) {
//            throw new IllegalArgumentException("A name must be specified when creating an application archive.");
//        } else {
//            return new ServiceArchiveImpl(s);
//        }
//    }


}