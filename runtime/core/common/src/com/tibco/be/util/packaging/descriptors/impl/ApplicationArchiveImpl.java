package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 8, 2010
 * Time: 1:27:36 PM
 */


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.ApplicationArchive;
import com.tibco.be.util.packaging.descriptors.MutableApplicationArchive;
import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.be.util.packaging.descriptors.ServiceArchive;
import com.tibco.be.util.packaging.descriptors.ServiceArchiveFactory;
import com.tibco.be.util.packaging.descriptors.VFileFactoryProvider;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;


public class ApplicationArchiveImpl
        extends ServiceArchiveImpl
        implements MutableApplicationArchive {


    boolean initialized;
    List<ServiceArchive> serviceArchives;
    VFileFactory sharedArchiveVFileFactory;


    public ApplicationArchiveImpl(String name, VFileFactoryProvider vfilefactoryprovider)
            throws Exception {
        super(name, vfilefactoryprovider);
        this.serviceArchives = new ArrayList<ServiceArchive>();
        this.sharedArchiveVFileFactory = null;
        this.initialized = false;
    }


    @Override
    public void addServiceArchive(ServiceArchive servicearchive) {
        ((ServiceArchiveImpl) servicearchive).setApplicationArchive(this);
        this.serviceArchives.add(servicearchive);
        Modules modules = (Modules) this.getDeploymentDescriptorByName(
                Constants.DD.XNames.DEPLOYMENT_DESCRIPTORS, Constants.DD.XNames.MODULES.localName);
        if (modules == null) {
            modules = new Modules();
            this.addDeploymentDescriptor(modules);
        }
        modules.addModule(servicearchive);
    }


    @Override
    public MutableServiceArchive addServiceArchive(String name) {
        if ((name == null) || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be specified and be of at least one character");
        } else {
            final MutableServiceArchive msa = ServiceArchiveFactory.newServiceArchive(name);
            this.addServiceArchive(msa);
            return msa;
        }
    }


    @Override
    public void destroy() {
        for (final ServiceArchive sa : this.serviceArchives) {
            sa.destroy();
        }

        if (this.sharedArchiveVFileFactory != null) {
            this.sharedArchiveVFileFactory.destroy();
            this.sharedArchiveVFileFactory = null;
        }
        super.destroy();
    }


    @Override
    public ApplicationArchive getApplicationArchive() {
        return this;
    }


    @Override
    public ServiceArchive getServiceArchiveByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must be specified.");
        }
        for (ServiceArchive sa : this.getServiceArchives()) {
            if ((sa.getName() != null)
                    && sa.getName().equals(name)) {
                return sa;
            }
        }
        return null;
    }


    @Override
    public List<ServiceArchive> getServiceArchives() {
        return Collections.unmodifiableList(this.serviceArchives);
    }


    @Override
    public VFileFactory getSharedArchive()
            throws IOException, ObjectRepoException {
        if (!this.initialized) {
            this.initialized = true;
            this.sharedArchiveVFileFactory = this.vFileFactoryProvider.getVFileFactory("Shared Archive.sar");
        }
        return this.sharedArchiveVFileFactory;
    }


    @Override
    public void removeServiceArchive(ServiceArchive servicearchive) {
        if (servicearchive == null) {
            throw new IllegalArgumentException("Service Archive must be specified.");
        }
        this.serviceArchives.remove(servicearchive);
        final Modules modules = (Modules) this.getDeploymentDescriptorByName(
                Constants.DD.XNames.DEPLOYMENT_DESCRIPTORS, Constants.DD.XNames.MODULES.localName);
        if (modules != null) {
            modules.removeModule(servicearchive);
        }
    }


    public void save(OutputStream outputstream)
            throws Exception {
        if (outputstream == null) {
            throw new IllegalArgumentException("Stream must be specified.");
        }
        final VFileFactory vfilefactory = this.getVFileFactory();
        if (this.serviceArchives.isEmpty()) {
            throw new IllegalArgumentException("No Service archive.");
        }
        for (final ServiceArchive sa : this.serviceArchives) {
            final String fileName = sa.getFileName();
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ((MutableServiceArchive) sa).save(baos);

            final VFileStream vfilestream;
            if (vfilefactory.exists(fileName)) {
                vfilestream = (VFileStream) vfilefactory.get(fileName);
            } else {
                vfilestream = (VFileStream) vfilefactory.create(fileName, true);
            }
            vfilestream.update(new ByteArrayInputStream(baos.toByteArray(), 0, baos.size()));
        }

        super.save(outputstream);
    }


//    public VFileFactory getExternalJarFile()
//            throws IOException, ObjectRepoException {
//        if (!_fldnew) {
//            _fldnew = true;
//            _fldfor = vFileFactoryProvider.getVFileFactory("lib.zip");
//        }
//        return _fldfor;
//    }
//
//
//    public VFileFactory getExternalResourceFile()
//            throws IOException, ObjectRepoException {
//        if (!_flddo) {
//            _flddo = true;
//            _fldint = vFileFactoryProvider.getVFileFactory("resources.zip");
//        }
//        return _fldint;
//    }


    //            public ApplicationArchiveImpl(ApplicationArchivePluginSourceFormat applicationarchivepluginsourceformat)
//                throws IOException, SAXException, XmlAtomicValueCastException, ObjectRepoException, PluginException {
//        super(applicationarchivepluginsourceformat, null, applicationarchivepluginsourceformat.getProvider());/*  */        serviceArchives = new ArrayList();/*  */        sharedArchiveVFileFactory = null;/*  */        a = false;/*  */        _fldfor = null;/*  */        _fldnew = false;/*  */        _fldint = null;/*  */        _flddo = false;
//
//        VFileFactory vfilefactory = getExternalJarFile();
//        if(vfilefactory != null) {
//            VFile vfile = vfilefactory.get("WEB-INF/classes");
//            if(vfile != null && (vfile instanceof VFileDirectory)) {
//                applicationarchivepluginsourceformat.addClassPathResolver((VFileDirectory)vfile);                    }
//            VFile vfile1 = vfilefactory.get("WEB-INF/lib");
//
//            if(vfile1 != null && (vfile1 instanceof VFileDirectory)) {
//                Iterator iterator = ((VFileDirectory)vfile1).getChildren();
//                do { if(!iterator.hasNext()) {
//                        break;                            }                    VFile vfile2 = (VFile)iterator.next();
//                    if(vfile2.getURI().endsWith(".jar")) {
//                        applicationarchivepluginsourceformat.getPluginClassPathContext().addClassPathResolver(new JarInputStreamClassPathResolver(new JarInputStream(((VFileStream)vfile2).getInputStream()), applicationarchivepluginsourceformat.getPluginClassPathContext()));                            }
//                        }
//                        while(true);
//                    }
//                }
//            }
//
//            protected String getResourceEntryName(String s) {
//        return s;
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
//
//
//
//            public Map diffApplicationArchive(ApplicationArchive applicationarchive)
//                throws IOException, ObjectRepoException {
//        return diffApplicationArchive(applicationarchive, false);
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
//
//            public Map diffApplicationArchive(ApplicationArchive applicationarchive, boolean flag)
//                throws IOException, ObjectRepoException {
//        ApplicationArchiveImpl applicationarchiveimpl = this;
//        HashMap hashmap = new HashMap();
//        hashmap.putAll(a(applicationarchive));
//        hashmap.putAll(_mthdo(applicationarchive));
//        hashmap.put(null, diffServiceArchive(applicationarchive, flag));
//        for(Iterator iterator = applicationarchive.getServiceArchives().iterator(); iterator.hasNext();) {
//
//            ServiceArchive servicearchive = (ServiceArchive)iterator.next();
//            ServiceArchive servicearchive2 = applicationarchiveimpl.getServiceArchiveByName(servicearchive.getName());
//            if(servicearchive2 == null) {
//                hashmap.put(servicearchive.getName(), "REMOVED_ARCHIVE");                    }
//                    else {
//                hashmap.put(servicearchive.getName(), servicearchive2.diffServiceArchive(servicearchive, flag));                    }                }
//
//        Iterator iterator1 = applicationarchiveimpl.getServiceArchives().iterator();
//        do { if(!iterator1.hasNext()) {
//                break;                    }            ServiceArchive servicearchive1 = (ServiceArchive)iterator1.next();
//            ServiceArchive servicearchive3 = applicationarchive.getServiceArchiveByName(servicearchive1.getName());
//            if(servicearchive3 == null) {
//                if(servicearchive1.isConfigurationNecessary()) {
//                    hashmap.put(servicearchive1.getName(), "NEW_ARCHIVE_CONFIG_NECESSARY");                        }
//                        else {
//                    hashmap.put(servicearchive1.getName(), "NEW_ARCHIVE_NO_CONFIG_NECESSARY");                        }                    }


    //                }
//                while(true);
//        if(_mthif(applicationarchive)) {
//            hashmap.put("Shared Archive.sar", "UPDATED_ARCHIVE_DEPLOY_NECESSARY");                }
//                else {
//            hashmap.put("Shared Archive.sar", "UNCHANGED_ARCHIVE");                }
//        return hashmap;            }
//
//            private Map a(ApplicationArchive applicationarchive)
//                throws IOException, ObjectRepoException {
//        HashMap hashmap = new HashMap();
//        if(a(((ApplicationArchiveImpl)applicationarchive).getExternalResourceFile(), getExternalResourceFile())) {
//            hashmap.put("resources.zip", "UPDATED_ARCHIVE_DEPLOY_NECESSARY");                }
//                else
//        if(getExternalResourceFile() != null) {
//            hashmap.put("resources.zip", "UNCHANGED_ARCHIVE");
//                }
//
//        return hashmap;            }
//
//            private boolean _mthif(ApplicationArchive applicationarchive)
//                throws ObjectRepoException, IOException {
//        return a(((ApplicationArchiveImpl)applicationarchive).getSharedArchive(), getSharedArchive());            }
//
//            private boolean a(VFileFactory vfilefactory, VFileFactory vfilefactory1)
//                throws ObjectRepoException, IOException {
//        if(vfilefactory == null && vfilefactory1 == null) {
//            return false;                }
//        if(vfilefactory == null && vfilefactory1 != null || vfilefactory != null && vfilefactory1 == null) {
//
//            return true;
//                }
//        HashMap hashmap = new HashMap();
//        if(!vfileSizeEquals(vfilefactory1.getRootDirectory(), vfilefactory, null, hashmap)) {
//            return true;                }
//        if(anyNotInSeen(vfilefactory.getRootDirectory(), null, hashmap)) {
//            return true;                }
//        byte abyte0[] = new byte[2048];
//        byte abyte1[] = new byte[2048];
//        return !vfileEquals(vfilefactory1.getRootDirectory(), vfilefactory, null, abyte0, abyte1);
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
//
//            public String diffServiceArchiveExtended(ServiceArchive servicearchive, boolean flag)
//                throws IOException, ObjectRepoException {
//        String s = diffServiceArchive(servicearchive, flag);
//        if(s == "UNCHANGED_ARCHIVE") {
//            ApplicationArchiveImpl applicationarchiveimpl = this;
//            ApplicationArchive applicationarchive = servicearchive.getApplicationArchive();
//            if((applicationarchiveimpl instanceof ApplicationArchive) && (applicationarchive instanceof ApplicationArchive)) {
//                if(applicationarchiveimpl.isExternalFileChanged(applicationarchive) && a(EXTERNAL_FILE_DEPENDENCY)) {
//                    return "UPDATED_ARCHIVE_DEPLOY_NECESSARY";                        }
//                if(applicationarchiveimpl.isExternalJarChanged(applicationarchive) && a(EXTERNAL_JAR_DEPENDENCY)) {
//                    return "UPDATED_ARCHIVE_DEPLOY_NECESSARY";                        }
//                    }
//            if(_mthif(applicationarchive)) {
//                return "UPDATED_ARCHIVE_DEPLOY_NECESSARY";                    }
//                }
//        return s;
//            }
//
//            private boolean a(String s) {
//        NameValuePairs namevaluepairs = (NameValuePairs)getDeploymentDescriptorByName(NameValuePairs.DEPLOYMENT_DESCRIPTOR_TYPE, NameValuePairs.EXTERNAL_DEPENDENCY_DEPLOYMENT_DESCRIPTOR_NAME);
//        if(namevaluepairs != null) {
//            NVPair nvpair = namevaluepairs.getNameValuePair(s);
//            if(nvpair != null) {
//                String s1 = nvpair.getValue();
//                if(s1 != null && s1.length() > 0) {
//                    return true;
//                        }
//                    }
//                }
//        return false;            }
//
//            private Map _mthdo(ApplicationArchive applicationarchive)
//                throws IOException, ObjectRepoException {
//        HashMap hashmap = new HashMap();
//        if(a(((ApplicationArchiveImpl)applicationarchive).getExternalJarFile(), getExternalJarFile())) {
//            hashmap.put("lib.zip", "UPDATED_ARCHIVE_DEPLOY_NECESSARY");                }
//                else
//        if(getExternalJarFile() != null) {
//            hashmap.put("lib.zip", "UNCHANGED_ARCHIVE");
//                }
//
//        return hashmap;            }
//
//            public boolean isExternalFileChanged(ApplicationArchive applicationarchive)
//                throws IOException, ObjectRepoException {
//        Map map = a(applicationarchive);
//        return map.get("resources.zip") == "UPDATED_ARCHIVE_DEPLOY_NECESSARY";            }
//
//            public boolean isExternalJarChanged(ApplicationArchive applicationarchive)
//                throws IOException, ObjectRepoException {
//        Map map = _mthdo(applicationarchive);
//        return map.get("lib.zip") == "UPDATED_ARCHIVE_DEPLOY_NECESSARY";
//            }
//
//
//
//            public boolean filterURI(String s) {
//        if(super.filterURI(s)) {
//            return true;                }
//        for(Iterator iterator = getServiceArchives().iterator(); iterator.hasNext();) {
//
//            ServiceArchive servicearchive = (ServiceArchive)iterator.next();
//            if(servicearchive.getFileName().equals(s)) {
//                return true;                    }                }
//
//        return s.equals("Shared Archive.sar");
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
//
//
//
//
//            public static byte[] getBytes(InputStream inputstream)
//                throws IOException {
//        return ApplicationArchiveFactory.getBytes(inputstream);
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
//            public Map diffApplicationArchiveContents(ApplicationArchive applicationarchive)
//                throws IOException, ObjectRepoException {
//        ApplicationArchiveImpl applicationarchiveimpl = this;
//        HashMap hashmap = new HashMap();
//        hashmap.put(null, diffServiceArchiveContents(applicationarchive));
//
//        if(applicationarchive != null) {
//            for(Iterator iterator = applicationarchive.getServiceArchives().iterator(); iterator.hasNext();) {
//
//                ServiceArchive servicearchive = (ServiceArchive)iterator.next();
//                ServiceArchive servicearchive2 = applicationarchiveimpl.getServiceArchiveByName(servicearchive.getName());
//                if(servicearchive2 == null) {
//                    HashMap hashmap1 = new HashMap();
//                    setMapAction(servicearchive.getVFileFactory().getRootDirectory(), hashmap1, (URIFilter)servicearchive, true);
//                    hashmap.put(servicearchive.getName(), hashmap1);                        }
//                        else {
//                    hashmap.put(servicearchive.getName(), servicearchive2.diffServiceArchiveContents(servicearchive));                        }                    }
//
//                }
//        Iterator iterator1 = applicationarchiveimpl.getServiceArchives().iterator();
//        do { if(!iterator1.hasNext()) {
//                break;                    }            ServiceArchive servicearchive1 = (ServiceArchive)iterator1.next();
//            ServiceArchive servicearchive3 = null;
//            if(applicationarchive != null) {
//                servicearchive3 = applicationarchive.getServiceArchiveByName(servicearchive1.getName());                    }
//            if(servicearchive3 == null) {
//                HashMap hashmap2 = new HashMap();
//                setMapAction(servicearchive1.getVFileFactory().getRootDirectory(), hashmap2, (URIFilter)applicationarchive, false);
//                hashmap.put(servicearchive1.getName(), hashmap2);                    }
//                }
//                while(true);
//        VFileFactory vfilefactory = null;
//        if(applicationarchive != null) {
//            vfilefactory = ((ApplicationArchiveImpl)applicationarchive).getSharedArchive();                }
//        VFileFactory vfilefactory1 = getSharedArchive();
//        if(vfilefactory == null && vfilefactory1 == null) {
//            hashmap.put("Shared Archive.sar", new HashMap());                }                else
//        if(vfilefactory == null && vfilefactory1 != null) {
//            HashMap hashmap3 = new HashMap();
//            setMapAction(vfilefactory1.getRootDirectory(), hashmap3, null, false);
//            hashmap.put("Shared Archive.sar", hashmap3);                }                else
//        if(vfilefactory != null && vfilefactory1 == null) {
//            HashMap hashmap4 = new HashMap();


    //            setMapAction(vfilefactory.getRootDirectory(), hashmap4, null, true);
//            hashmap.put("Shared Archive.sar", hashmap4);                }
//                else {
//            HashMap hashmap5 = new HashMap();
//            HashSet hashset = new HashSet();
//            setMapResults(hashmap5, hashset, vfilefactory1, vfilefactory.getRootDirectory(), this);
//            setMapResults2(hashmap5, hashset, vfilefactory1.getRootDirectory(), this);
//            hashmap.put("Shared Archive.sar", hashmap5);
//                }
//        return hashmap;
//            }
//
//            public void merge(DeploymentDescriptorProvider deploymentdescriptorprovider) {
//        merge(deploymentdescriptorprovider, null);
//            }
//
//
//
//
//
//
//
//            public void merge(DeploymentDescriptorProvider deploymentdescriptorprovider, DeploymentDescriptorProvider deploymentdescriptorprovider1) {
//        if(!(deploymentdescriptorprovider instanceof ApplicationArchive)) {
//            throw new IllegalArgumentException("Must specify an ApplicationArchive");                }
//        ApplicationArchive applicationarchive = (ApplicationArchive)deploymentdescriptorprovider;
//        setDescription(applicationarchive.getDescription());
//        setOwner(applicationarchive.getOwner());
//        setVersion(applicationarchive.getVersion());
//        setCreationDate(applicationarchive.getCreationDate());
//        super.merge(applicationarchive, deploymentdescriptorprovider1);                ServiceArchive servicearchive;                MutableServiceArchive mutableservicearchive;
//                ServiceArchive servicearchive1;
//        for(Iterator iterator = applicationarchive.getServiceArchives().iterator(); iterator.hasNext(); mutableservicearchive.merge(servicearchive, servicearchive1)) {
//
//            servicearchive = (ServiceArchive)iterator.next();
//            mutableservicearchive = null;
//            mutableservicearchive = (MutableServiceArchive)getServiceArchiveByName(servicearchive.getName());
//            if(mutableservicearchive == null) {
//                mutableservicearchive = addServiceArchive(servicearchive.getName());
//                    }
//            mutableservicearchive.setDescription(servicearchive.getDescription());
//            mutableservicearchive.setOwner(servicearchive.getOwner());
//            mutableservicearchive.setVersion(servicearchive.getVersion());
//            mutableservicearchive.setCreationDate(servicearchive.getCreationDate());
//
//            servicearchive1 = null;
//            if(null != deploymentdescriptorprovider1) {
//                servicearchive1 = ((ApplicationArchive)deploymentdescriptorprovider1).getServiceArchiveByName(servicearchive.getName());                    }                }
//
//            }
//
//            public Object clone()
//                throws CloneNotSupportedException {
//        ApplicationArchiveImpl applicationarchiveimpl = (ApplicationArchiveImpl)super.clone();
//        applicationarchiveimpl.serviceArchives = new ArrayList();
//        for(Iterator iterator = serviceArchives.iterator(); iterator.hasNext(); applicationarchiveimpl.serviceArchives.add(((ServiceArchive)iterator.next()).clone())) { }
//
//
//
//        return applicationarchiveimpl;
//            }



}
