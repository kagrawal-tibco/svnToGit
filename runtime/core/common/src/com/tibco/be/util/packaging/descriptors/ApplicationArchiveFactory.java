package com.tibco.be.util.packaging.descriptors;

import com.tibco.be.util.packaging.descriptors.impl.ApplicationArchiveImpl;
import com.tibco.be.util.packaging.descriptors.impl.DefaultVFileFactoryProvider;


public class ApplicationArchiveFactory {


    public ApplicationArchiveFactory() {
    }


    public static MutableApplicationArchive newApplicationArchive(String s) {
        if ((s == null) || s.isEmpty()) {
            throw new IllegalArgumentException("A name must be specified when creating an application archive.");
        }
        try {
            return new ApplicationArchiveImpl(s, new DefaultVFileFactoryProvider(null));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


//
//
//
//
//            public static MutableApplicationArchive newApplicationArchive(String s, VFileFactoryProvider vfilefactoryprovider)
//                throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        MutableApplicationArchive mutableapplicationarchive = a(vfilefactoryprovider);
//        mutableapplicationarchive.setName(s);
//        return mutableapplicationarchive;
//            }
//
//
//
//
//
//
//
//
//
//            public static ApplicationArchive getApplicationArchive(InputStream inputstream)
//                throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        return getApplicationArchive(((VFileFactoryProvider) (new DefaultVFileFactoryProvider(inputstream))));
//            }
//
//
//
//
//
//
//
//
//            public static ApplicationArchive getApplicationArchive(VFileFactoryProvider vfilefactoryprovider)
//                throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        return a(vfilefactoryprovider);
//            }
//
//
//
//
//
//
//
//
//            public static ApplicationArchive getApplicationArchive(File file)
//                throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        FileInputStream fileinputstream = new FileInputStream(file);
//                ApplicationArchive applicationarchive1;
//        ApplicationArchive applicationarchive = getApplicationArchive(((InputStream) (new ZipInputStream(fileinputstream))));
//        if(applicationarchive.getServiceArchives().size() == 0) {
//            String s = (String)ResourceBundle.getBundle("Archive", Locale.getDefault()).getObject("MISSING_SERVICE_ARCHIVE");
//            Object aobj[] = { applicationarchive.getName()
//                    }
//                    ;
//            throw new IllegalArgumentException(MessageFormat.format(s, aobj));
//                }
//        applicationarchive1 = applicationarchive;
//
//        fileinputstream.close(); return applicationarchive1;                Exception exception;        exception; fileinputstream.close(); throw exception;
//            }
//
//            private static MutableApplicationArchive a(VFileFactoryProvider vfilefactoryprovider)
//                throws IOException, SAXException, InstantiationException, IllegalAccessException, InvocationTargetException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        if(vfilefactoryprovider == null) {
//            throw new IllegalArgumentException("An VFileFactoryProvider must be specified when loading an applic" +"ation archive.");                }
//        ApplicationArchivePluginSourceFormat applicationarchivepluginsourceformat = new ApplicationArchivePluginSourceFormat(vfilefactoryprovider, "TIBCOApplicationArchive");
//        VFileFactory vfilefactory = applicationarchivepluginsourceformat.getVFileFactory();
//
//        ApplicationArchiveImpl applicationarchiveimpl = new ApplicationArchiveImpl(applicationarchivepluginsourceformat);
//
//        Modules modules = (Modules)applicationarchiveimpl.getDeploymentDescriptorByName(Modules.DEPLOYMENT_DESCRIPTOR_TYPE, "Modules");
//        if(modules != null) {
//            for(int i = 0; i < modules.getPathNames().size(); i++) {
//                String s = (String)modules.getPathNames().get(i);
//                if(!vfilefactory.exists(s)) {
//
//
//                    continue;                        }                ApplicationArchivePluginSourceFormat applicationarchivepluginsourceformat1 = new ApplicationArchivePluginSourceFormat(vfilefactoryprovider, s);
//
//                if(applicationarchivepluginsourceformat1.getVFileFactory() == null) {
//                    continue;
//                        }
//
//                try { Constructor constructor = applicationarchiveimpl.getClassLoader().loadClass(com/tibco/archive/impl/ServiceArchiveImpl.getName()).getConstructor(new Class[] { com/tibco/archive/impl/ApplicationArchivePluginSourceFormat, com/tibco/archive/ApplicationArchive, com/tibco/archive/helpers/VFileFactoryProvider                            }
//                            );
//                    applicationarchiveimpl.addServiceArchive((ServiceArchive)constructor.newInstance(new Object[] { applicationarchivepluginsourceformat1, applicationarchiveimpl, vfilefactoryprovider                            }                            ));                        }
//                catch(NoSuchMethodException nosuchmethodexception) {
//                    nosuchmethodexception.printStackTrace();                        }
//                catch(ClassNotFoundException classnotfoundexception) {
//                    classnotfoundexception.printStackTrace();
//                        }
//
//                    }
//
//                }
//        return applicationarchiveimpl;
//            }
//
//
//
//
//
//            public static SingleThreadedByteArrayOutputStream getOutputStream(InputStream inputstream)
//                throws IOException {
//        if(inputstream == null) {
//            throw new IllegalArgumentException("An InputStream must be specified when getting bytes.");                }
//        byte abyte0[] = new byte[2048];
//        SingleThreadedByteArrayOutputStream singlethreadedbytearrayoutputstream = new SingleThreadedByteArrayOutputStream(2048);
//
//
//
//        singlethreadedbytearrayoutputstream.reset();                int i;
//        while((i = inputstream.read(abyte0, 0, abyte0.length)) != -1)  {
//            singlethreadedbytearrayoutputstream.write(abyte0, 0, i);
//                }
//        inputstream.close();
//        return singlethreadedbytearrayoutputstream;
//            }
//
//
//
//
//
//            public static byte[] getBytes(InputStream inputstream)
//                throws IOException {
//        return getOutputStream(inputstream).toByteArray();            }
//
//            public static boolean equals(VFile vfile, VFile vfile1, byte abyte0[], byte abyte1[])
//                throws ObjectRepoException, IOException {
//        if(vfile == null && vfile1 == null) {
//            return true;                }
//        if((vfile instanceof VFileStream) && (vfile1 instanceof VFileStream)) {
//            if(vfile != null && vfile1 == null) {
//                return false;                    }
//            if(vfile == null && vfile1 != null) {
//                return false;                    }
//            if(vfile.equals(vfile1)) {
//                return true;                    }
//            if(((VFileStream)vfile).getSize() != ((VFileStream)vfile1).getSize()) {
//                return false;                    }
//            InputStream inputstream = ((VFileStream)vfile).getInputStream();
//            InputStream inputstream1 = ((VFileStream)vfile1).getInputStream();
//            boolean flag = equals(inputstream, inputstream1, abyte0, abyte1);
//            if(inputstream != null) {
//                inputstream.close();                    }
//            if(inputstream1 != null) {
//                inputstream1.close();                    }
//            return flag;                }
//        if((vfile instanceof VFileDirectory) && (vfile1 instanceof VFileDirectory)) {
//            if(vfile.equals(vfile1)) {
//                return true;                    }
//            if(vfile.getURI().equals(vfile1.getURI())) {
//                return true;                    }
//                }
//        return false;
//            }
//
//            public static boolean equals(InputStream inputstream, InputStream inputstream1)
//                throws IOException {
//                int i;
//        do { i = inputstream.read();
//            if(i != inputstream1.read()) {
//                inputstream.close();
//                inputstream1.close();
//                return false;                    }
//                }
//                while(i != -1);
//        inputstream.close();
//        inputstream1.close();
//        return true;
//            }
//
//
//            private static int a(InputStream inputstream, byte abyte0[])
//                throws IOException {
//        int i = -1;
//        int j = 0;
//
//        do {                    int k;            if((k = inputstream.read(abyte0, j, abyte0.length - j)) == -1) {
//                break;                    }            if(i == -1) {
//                i = k;                    }
//                    else {
//                i += k;                    }
//            j = i;
//                }
//
//                while(abyte0.length - j != 0);
//        return i;
//            }
//
//
//            public static boolean equals(InputStream inputstream, InputStream inputstream1, byte abyte0[], byte abyte1[])
//                throws IOException {
//        do { int i = a(inputstream, abyte0);
//            int j = a(inputstream1, abyte1);
//            if(i == -1 && j == -1) {
//                return true;                    }
//            if(i != j) {
//                return false;                    }
//            int k = 0; while(k < i) {                k++;
//                if(abyte0[k] != abyte1[k]) {
//                    return false;
//                        }
//                    }
//                }
//                while(true);
//            }


}
