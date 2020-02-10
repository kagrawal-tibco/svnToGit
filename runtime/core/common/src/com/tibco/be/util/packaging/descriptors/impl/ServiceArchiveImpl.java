package com.tibco.be.util.packaging.descriptors.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.ApplicationArchive;
import com.tibco.be.util.packaging.descriptors.DeploymentDescriptor;
import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.be.util.packaging.descriptors.VFileFactoryProvider;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/*
 * User: nprade
 * Date: Feb 5, 2010
 * Time: 7:09:29 PM
 */

// Referenced classes of package com.tibco.archive.impl:
//            DefaultVFileFactoryProvider, XiNodePassingFactory, ServiceArchivePassingFactory, URIFilter,
//            ApplicationArchivePluginSourceFormat, DeploymentDescriptorRegistry


public class ServiceArchiveImpl
        implements MutableServiceArchive { // }, URIFilter {


    static ExpandedName ELEMENT_TABS = ExpandedName.makeName("Tabs");
    static ExpandedName ELEMENT_TAB = ExpandedName.makeName("Tab");
    static ExpandedName ELEMENT_SECTION = ExpandedName.makeName("Section");
    static ExpandedName ELEMENT_NAME = ExpandedName.makeName("name");
    static ExpandedName ELEMENT_TYPE = ExpandedName.makeName("type");
    static String EXTERNAL_FILE_DEPENDENCY = "EXTERNAL_FILE_DEPENDENCY";
    static String EXTERNAL_JAR_DEPENDENCY = "EXTERNAL_JAR_DEPENDENCY";

//    private PluginManager mManager;
    private ApplicationArchive appArchive;
    List<DeploymentDescriptor> deploymentDescriptors;
    String name, description, version, owner;
    Date creationDate, lastModificationDate;
    private VFileFactory vFileFactory;
    protected VFileFactoryProvider vFileFactoryProvider;


    public ServiceArchiveImpl(String name)
            throws Exception {
        this(name, (new DefaultVFileFactoryProvider(null)));
    }


    public ServiceArchiveImpl(String name, VFileFactoryProvider vfilefactoryprovider)
            throws Exception {
        this.setName(name);
        this.creationDate = new Date();
        this.deploymentDescriptors = new ArrayList<DeploymentDescriptor>();
        this.description = null;
        this.lastModificationDate = new Date();
//        mManager = null;
//        mManager = PluginManagerFactory.createPluginManager();
        this.owner = null;
        this.version = null;
        this.vFileFactory = vfilefactoryprovider.getVFileFactory(null);
        this.vFileFactoryProvider = vfilefactoryprovider;
    }



    @Override
    public void addDeploymentDescriptor(DeploymentDescriptor deploymentdescriptor) {
        deploymentdescriptor.onAdd(this);
        this.deploymentDescriptors.add(deploymentdescriptor);
    }


//    public Map diffServiceArchiveContents(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException {
//        ServiceArchiveImpl servicearchiveimpl = this;
//        if (servicearchive != null && (!(servicearchive instanceof ApplicationArchive) || !(servicearchiveimpl instanceof ApplicationArchive)) && (!(servicearchive instanceof ServiceArchive) || !(servicearchiveimpl instanceof ServiceArchive))) {
//            throw new IllegalArgumentException("Must compare two application or service archives - mixed types s" +
//                    "pecified"
//            );
//        }
//        if (servicearchive != null && !servicearchive.getName().equals(servicearchiveimpl.getName()) && !(servicearchive instanceof ApplicationArchive)) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Comparing two service archives with different names: ").append(servicearchive.getName()).append(" and ").append(servicearchiveimpl.getName()).toString());
//        }
//        HashMap hashmap = new HashMap();
//        HashSet hashset = new HashSet();
//        if (servicearchive != null) {
//            setMapResults(hashmap, hashset, getVFileFactory(), servicearchive.getVFileFactory().getRootDirectory(), this);
//        }
//        setMapResults2(hashmap, hashset, getVFileFactory().getRootDirectory(), this);
//        return hashmap;
//    }
//
//
//    void setMapResults(Map map, Set set, VFileFactory vfilefactory, VFileDirectory vfiledirectory, URIFilter urifilter)
//            throws ObjectRepoException, IOException {
//        Iterator iterator = vfiledirectory.getChildren();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            VFile vfile = (VFile) iterator.next();
//            if (vfile instanceof VFileDirectory) {
//                setMapResults(map, set, vfilefactory, (VFileDirectory) vfile, urifilter);
//            } else if (vfile instanceof VFileStream) {
//                String s = vfile.getURI();
//                if (urifilter == null || !urifilter.filterURI(s)) {
//                    set.add(s);
//                    InputStream inputstream = ((VFileStream) vfile).getInputStream();
//                    VFile vfile1 = vfilefactory.get(s);
//                    if (vfile1 instanceof VFileDirectory) {
//                        throw new IllegalArgumentException((new StringBuilder()).append("VFileDirectory was a file and now is a directory: ").append(s).toString());
//                    }
//                    if (vfile1 == null) {
//                        map.put(s, null);
//                    } else {
//                        InputStream inputstream1 = ((VFileStream) vfile1).getInputStream();
//                        if (inputstream == null) {
//                            throw new IllegalArgumentException((new StringBuilder()).append("Source VFile at uri ").append(s).append(" was null.").toString());
//                        }
//                        if (inputstream1 == null) {
//                            throw new IllegalArgumentException((new StringBuilder()).append("Destination VFile at uri ").append(s).append(" was null.").toString());
//                        }
//                        if (!ApplicationArchiveFactory.equals(inputstream, inputstream1)) {
//                            map.put(s, ((VFileStream) vfile1).getInputStream());
//                        }
//                    }
//                }
//            }
//        } while (true);
//    }
//
//
//    void setMapResults2(Map map, Set set, VFileDirectory vfiledirectory, URIFilter urifilter)
//            throws ObjectRepoException, IOException {
//        Iterator iterator = vfiledirectory.getChildren();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            VFile vfile = (VFile) iterator.next();
//            if (vfile instanceof VFileDirectory) {
//                setMapResults2(map, set, (VFileDirectory) vfile, urifilter);
//            } else if (vfile instanceof VFileStream) {
//                String s = vfile.getURI();
//                if ((urifilter == null || !urifilter.filterURI(s)) && !set.contains(s)) {
//                    map.put(s, ((VFileStream) vfile).getInputStream());
//                }
//            }
//        } while (true);
//    }
//
//
//    public static void displayVFileFactory(String s, ApplicationArchive applicationarchive)
//            throws ObjectRepoException {
//        displayVFileFactory((new StringBuilder()).append(s).append(" ").append(applicationarchive.getName()).toString(), applicationarchive.getVFileFactory().getRootDirectory());
//        ServiceArchive servicearchive;
//        for (Iterator iterator = applicationarchive.getServiceArchives().iterator(); iterator.hasNext(); displayVFileFactory((new StringBuilder()).append(s).append(" ").append(servicearchive.getName()).toString(), servicearchive.getVFileFactory().getRootDirectory())) {
//            servicearchive = (ServiceArchive) iterator.next();
//        }
//
//    }
//
//
//    public static void displayVFileFactory(String s, VFileDirectory vfiledirectory)
//            throws ObjectRepoException {
//        System.err.println((new StringBuilder()).append("Begin ").append(s).toString());
//        Iterator iterator = vfiledirectory.getChildren();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            VFile vfile = (VFile) iterator.next();
//            if (vfile instanceof VFileDirectory) {
//                displayVFileFactory(s, (VFileDirectory) vfile);
//            } else if (vfile instanceof VFileStream) {
//                String s1 = vfile.getURI();
//                System.err.println((new StringBuilder()).append("uri(").append(((VFileStream) vfile).getSize()).append(") ").append(s1).toString());
//            }
//        } while (true);
//        System.err.println((new StringBuilder()).append("End ").append(s).toString());
//    }
//
//
//    public VFileFactory getClassLoaderExtensionFile(String s)
//            throws ObjectRepoException {
//        if (vFileFactory.exists(s)) {
//            return new ZipVFileFactory(new ZipInputStream(((VFileStream) vFileFactory.get(s)).getInputStream()));
//        } else {
//            return null;
//        }
//    }


    @Override
    public void destroy() {
        if (this.vFileFactory != null) {
            this.vFileFactory.destroy();
            this.vFileFactory = null;
        }
    }


    @Override
    public ApplicationArchive getApplicationArchive() {
        return this.appArchive;
    }


    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }


    @Override
    public DeploymentDescriptor getDeploymentDescriptorByName(ExpandedName xmlType, String name) {
        if (xmlType == null) {
            throw new IllegalArgumentException("DeploymentDescriptor type must be specified.");
        }
        if ((name == null) || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be specified.");
        }
        for (DeploymentDescriptor dd : this.deploymentDescriptors) {
            final ExpandedName ddXmlType = dd.getTypeXName();
            if (ddXmlType.equals(ddXmlType) && name.equals(dd.getName())) {
                return dd;
            }
        }

        return null;
    }


    @Override
    public List<DeploymentDescriptor> getDeploymentDescriptors() {
        return Collections.unmodifiableList(this.deploymentDescriptors);
    }


    @Override
    public List<DeploymentDescriptor> getDeploymentDescriptors(ExpandedName xmlType) {
        if (xmlType == null) {
            throw new IllegalArgumentException("DeploymentDescriptor type must be specified.");
        }
        List<DeploymentDescriptor> list = new ArrayList<DeploymentDescriptor>();
        for (final DeploymentDescriptor dd : this.deploymentDescriptors) {
            if (dd.getTypeXName().equals(xmlType)) {
                list.add(dd);
            }
        }
        return list;
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public String getFileName() {
        return this.getName();
    }


    @Override
    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }


    private static Method getMethod(Class c, String methodName, Class argumentClasses[])
            throws NoSuchMethodException {
        if (c == null) {
            throw new NoSuchMethodException(methodName);
        }
        final Method method = c.getMethod(methodName, argumentClasses);
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        return method;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public String getOwner() {
        return this.owner;
    }


//    protected String getResourceEntryName(String s) {
//        return (this.getName() + System.getProperty("file.separator") + s);
//    }


    @Override
    public String getVersion() {
        return this.version;
    }


    @Override
    public VFileFactory getVFileFactory() {
        return this.vFileFactory;
    }


    protected void pack()
            throws IOException, ObjectRepoException {

        final StartAsOneOf startasoneof = (StartAsOneOf) this.getDeploymentDescriptorByName(
                Constants.DD.XNames.DEPLOYMENT_DESCRIPTORS, Constants.DD.XNames.START_AS_ONE_OF.localName);
        if ((startasoneof == null)
                && !(this instanceof ApplicationArchive)) {
            throw new IOException("Please specify a StartAsOneOf for service archive: " + this.getName());
        }

        final VFileFactory vfilefactory = this.getVFileFactory();
        final VFileStream vfilestream;
        if (vfilefactory.exists(Constants.DD.FILENAME)) {
            vfilestream = (VFileStream) vfilefactory.get(Constants.DD.FILENAME);
        } else {
            vfilestream = (VFileStream) vfilefactory.create(Constants.DD.FILENAME, true);
        }

        final XiFactory xifactory = XiFactoryFactory.newInstance();
        final XiNode docNode = xifactory.createDocument();
        final XiNode rootNode = xifactory.createElement(Constants.DD.XNames.DEPLOYMENT_DESCRIPTORS);
        rootNode.setNamespace(xifactory.createNamespace("", Constants.DD.NAMESPACE));

        XiNode node;

        if (this.name != null) {
            node = xifactory.createElement(Constants.DD.XNames.NAME);
            node.setStringValue(this.name);
            rootNode.appendChild(node);
        }
        if (this.description != null) {
            node = xifactory.createElement(Constants.DD.XNames.DESCRIPTION);
            node.setStringValue(this.description);
            rootNode.appendChild(node);
        }
        if (this.version != null) {
            node = xifactory.createElement(Constants.DD.XNames.VERSION);
            node.setStringValue(this.version);
            rootNode.appendChild(node);
        }
        if (this.owner != null) {
            node = xifactory.createElement(Constants.DD.XNames.OWNER);
            node.setStringValue(this.owner);
            rootNode.appendChild(node);
        }
        if (this.creationDate != null) {
            node = xifactory.createElement(Constants.DD.XNames.CREATION_DATE);
            node.setStringValue(DateFormat.getDateTimeInstance(3, 3, Locale.US).format(this.creationDate));
            rootNode.appendChild(node);
        }                                                          if (this instanceof ApplicationArchive) {
            node = xifactory.createElement(Constants.DD.XNames.IS_APPLICATION_ARCHIVE);
            node.setStringValue("true");
            rootNode.appendChild(node);
        }
        docNode.appendChild(rootNode);
        for (final DeploymentDescriptor dd : this.deploymentDescriptors) {
            rootNode.appendChild(dd.toXiNode(xifactory));
        }

//        SingleThreadedByteArrayOutputStream singlethreadedbytearrayoutputstream = new SingleThreadedByteArrayOutputStream();
//        XiSerializer.serialize(docNode, singlethreadedbytearrayoutputstream, "UTF-8", true);
//        vfilestream.update(new ByteArrayInputStream(singlethreadedbytearrayoutputstream.toByteArrayNoCopy(), 0, singlethreadedbytearrayoutputstream.getCount()));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XiSerializer.serialize(docNode, baos, "UTF-8", true);
        vfilestream.update(new ByteArrayInputStream(baos.toByteArray(), 0, baos.size()));
    }


//    public ServiceArchiveImpl(ApplicationArchivePluginSourceFormat applicationarchivepluginsourceformat, ApplicationArchive applicationarchive, VFileFactoryProvider vfilefactoryprovider)
//            throws Exception {
//        deploymentDescriptors = new ArrayList();
//        description = null;
//        version = null;
//        creationDate = new Date();
//        owner = null;
//        lastModificationDate = new Date();
//        mManager = null;
//        if (applicationarchive != null) {
//            mManager = PluginManagerFactory.createPluginManager(applicationarchive.getClassLoader());
//        } else {
//            mManager = PluginManagerFactory.createPluginManager();
//        }
//        vFileFactoryProvider = vfilefactoryprovider;
//        loadServiceArchive(applicationarchivepluginsourceformat, mManager);
//        PluginManager pluginmanager = mManager;
//        XiParser xiparser = XiParserFactory.newInstance();
//        VFileFactory vfilefactory = applicationarchivepluginsourceformat.getVFileFactory();
//        String s = "TIBCO.xml";
//        InputStream inputstream = null;
//        if (vfilefactory.exists(s)) {
//            VFile vfile = vfilefactory.get(s);
//            if (vfile instanceof VFileDirectory) {
//                throw new IllegalArgumentException((new StringBuilder()).append("Cannot have a directory with name ").append(s).toString());
//            }
//            inputstream = ((VFileStream) vfile).getInputStream();
//        }
//        vFileFactory = vfilefactory;
//        if (inputstream == null) {
//            return;
//        }
//        XiNode xinode = xiparser.parse(new InputSource(inputstream));
//        XiNode xinode1 = xinode.getFirstChild();
//        Iterator iterator = xinode1.getChildren();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            XiNode xinode2 = (XiNode) iterator.next();
//            ExpandedName expandedname = xinode2.getName();
//            if (expandedname != null) {
//                if (expandedname.equals(ARCHIVE_NAME)) {
//                    name = xinode2.getStringValue();
//                } else if (expandedname.equals(VERSION)) {
//                    mVersion = xinode2.getStringValue();
//                } else if (expandedname.equals(OWNER)) {
//                    owner = xinode2.getStringValue();
//                } else if (expandedname.equals(DESCRIPTION)) {
//                    description = xinode2.getStringValue();
//                } else if (expandedname.equals(CREATION_DATE)) {
//                    String s1 = xinode2.getStringValue();
//                    if (s1 == null || s1.length() == 0) {
//                        creationDate = new Date();
//                    } else {
//                        try {
//                            creationDate = DateFormat.getDateTimeInstance(3, 3, Locale.US).parse(s1);
//                        }
//                        catch (ParseException parseexception) {
//                            creationDate = new Date();
//                        }
//                    }
//                } else if (expandedname.equals(IS_APPLICATION_ARCHIVE)) {
//                    boolean flag = false;
//                    flag = xinode2.getTypedValue().getAtom(0).castAsBoolean();
//                    if (flag && !(this instanceof ApplicationArchive)) {
//                        throw new IllegalArgumentException("Cannot instantiate an application archive as a service archive.");
//                    }
//                } else {
//                    try {
//                        XiNodePassingFactory xinodepassingfactory = null;
//                        try {
//                            xinodepassingfactory = (XiNodePassingFactory) pluginmanager.getComponentSource().getComponentFactory(XiNodePassingFactory.XINODE_PASSING_FACTORY);
//                        }
//                        catch (PluginException pluginexception1) {
//                            pluginmanager.addClassPathPlugin(new XiNodePassingFactory(), null);
//                            xinodepassingfactory = (XiNodePassingFactory) pluginmanager.getComponentSource().getComponentFactory(XiNodePassingFactory.XINODE_PASSING_FACTORY);
//                        }
//                        if (xinodepassingfactory != null) {
//                            xinodepassingfactory.setNode(xinode2);
//                        }
//                        ServiceArchivePassingFactory servicearchivepassingfactory = null;
//                        try {
//                            servicearchivepassingfactory = (ServiceArchivePassingFactory) pluginmanager.getComponentSource().getComponentFactory(ServiceArchivePassingFactory.SERVICE_ARCHIVE_PASSING_FACTORY);
//                        }
//                        catch (PluginException pluginexception2) {
//                            pluginmanager.addClassPathPlugin(new ServiceArchivePassingFactory(), null);
//                            servicearchivepassingfactory = (ServiceArchivePassingFactory) pluginmanager.getComponentSource().getComponentFactory(ServiceArchivePassingFactory.SERVICE_ARCHIVE_PASSING_FACTORY);
//                        }
//                        if (servicearchivepassingfactory != null) {
//                            servicearchivepassingfactory.setServiceArchive(this);
//                        }
//                        DeploymentDescriptor deploymentdescriptor = (DeploymentDescriptor) pluginmanager.getComponentSource().getComponentFactory(xinode2.getName().getExpandedForm()).getInstance(pluginmanager.getComponentSource());
//                        if (deploymentdescriptor != null) {
//                            addDeploymentDescriptor(deploymentdescriptor);
//                        }
//                    }
//                    catch (PluginException pluginexception) {
//                        pluginexception.printStackTrace();
//                    }
//                }
//            }
//        } while (true);
//        if (name == null) {
//            throw new IllegalArgumentException("Name must be specified in XML");
//        }
//        ClassLoaderExtension classloaderextension = (ClassLoaderExtension) getDeploymentDescriptorByName(ClassLoaderExtension.DEPLOYMENT_DESCRIPTOR_TYPE, "ClassLoaderExtension");
//        if (classloaderextension != null) {
//            for (int i = 0; i < classloaderextension.getPathNames().size(); i++) {
//                String s2 = (String) classloaderextension.getPathNames().get(i);
//                VFileFactory vfilefactory1 = null;
//                if (applicationarchive == null) {
//                    vfilefactory1 = vFileFactoryProvider.getVFileFactory(s2);
//                } else {
//                    vfilefactory1 = getClassLoaderExtensionFile(s2);
//                }
//                if (vfilefactory1 != null) {
//                    applicationarchivepluginsourceformat.addClassPathResolver(vfilefactory1.getRootDirectory());
//                }
//            }
//
//        }
//    }


//    static void loadServiceArchive(PluginSourceFormat pluginsourceformat, PluginManager pluginmanager)
//            throws IOException, PluginException {
//        ArrayList arraylist = new ArrayList();
//        arraylist.add(pluginsourceformat);
//        pluginmanager.loadPluginSourceSet(arraylist.iterator(), null);
//        pluginmanager.activateSingletons();
//    }
//


    @Override
    public void save(OutputStream outputstream)
            throws Exception {
        if (outputstream == null) {
            throw new IllegalArgumentException("Stream must be specified.");
        }

        this.pack();

        try {
            final Method method = getMethod(this.vFileFactory.getClass(), "save", new Class[]{OutputStream.class});
            method.invoke(this.vFileFactory, new Object[]{
                    outputstream
            });
        }
        catch (IllegalAccessException illegalaccessexception) {
            throw new IllegalArgumentException(illegalaccessexception.toString());
        }
        catch (InvocationTargetException invocationtargetexception) {
            if ((null != invocationtargetexception)
                    && (null != invocationtargetexception.getTargetException())) {
                throw new IllegalArgumentException(invocationtargetexception.getTargetException().toString());
            }
            if (null != invocationtargetexception) {
                throw new IllegalArgumentException(invocationtargetexception.toString());
            } else {
                throw new IllegalArgumentException("NULL InvocationTargetException");
            }
        }
    }


//    public boolean doesContentEqual(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException {
//        if (servicearchive == null) {
//            throw new IllegalArgumentException("Source ServiceArchive must be specified.");
//        }
//        HashMap hashmap = new HashMap();
//        if (!vfileSizeEquals(servicearchive.getVFileFactory().getRootDirectory(), getVFileFactory(), this, hashmap)) {
//            return false;
//        }
//        if (anyNotInSeen(getVFileFactory().getRootDirectory(), this, hashmap)) {
//            return false;
//        }
//        byte abyte0[] = new byte[2048];
//        byte abyte1[] = new byte[2048];
//        return vfileEquals(servicearchive.getVFileFactory().getRootDirectory(), getVFileFactory(), this, abyte0, abyte1);
//    }
//
//
//    boolean anyNotInSeen(VFileDirectory vfiledirectory, URIFilter urifilter, Map map)
//            throws IOException, ObjectRepoException {
//        label0:
//        {
//            Iterator iterator = vfiledirectory.getChildren();
//            VFile vfile;
//            label1:
//            do {
//                do {
//                    if (!iterator.hasNext()) {
//                        break label0;
//                    }
//                    vfile = (VFile) iterator.next();
//                    if (!(vfile instanceof VFileDirectory)) {
//                        continue label1;
//                    }
//                } while (!anyNotInSeen((VFileDirectory) vfile, urifilter, map));
//                return true;
//            }
//            while (!(vfile instanceof VFileStream) || map.containsKey(vfile.getURI()) || urifilter != null && urifilter.filterURI(vfile.getURI()));
//            return true;
//        }
//        return false;
//    }
//
//
//    boolean vfileEquals(VFileDirectory vfiledirectory, VFileFactory vfilefactory, URIFilter urifilter, byte abyte0[], byte abyte1[])
//            throws IOException, ObjectRepoException {
//        Iterator iterator = vfiledirectory.getChildren();
//        label0:
//        do {
//            VFile vfile;
//            do {
//                if (!iterator.hasNext()) {
//                    break label0;
//                }
//                vfile = (VFile) iterator.next();
//                if (!(vfile instanceof VFileDirectory)) {
//                    continue;
//                }
//                if (!vfileEquals((VFileDirectory) vfile, vfilefactory, urifilter, abyte0, abyte1)) {
//                    return false;
//                }
//                continue label0;
//            } while (!(vfile instanceof VFileStream));
//            String s = vfile.getURI();
//            if ((urifilter == null || !urifilter.filterURI(s)) && !ApplicationArchiveFactory.equals(vfile, vfilefactory.get(s), abyte0, abyte1)) {
//                return false;
//            }
//        } while (true);
//        return true;
//    }
//
//
//    boolean vfileSizeEquals(VFileDirectory vfiledirectory, VFileFactory vfilefactory, URIFilter urifilter, Map map)
//            throws IOException, ObjectRepoException {
//        Iterator iterator = vfiledirectory.getChildren();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            VFile vfile = (VFile) iterator.next();
//            if (vfile instanceof VFileDirectory) {
//                if (!vfileSizeEquals((VFileDirectory) vfile, vfilefactory, urifilter, map)) {
//                    return false;
//                }
//            } else if (vfile instanceof VFileStream) {
//                String s = vfile.getURI();
//                if (urifilter != null && urifilter.filterURI(s)) {
//                    map.put(s, null);
//                } else {
//                    VFile vfile1 = vfilefactory.get(s);
//                    if ((vfile instanceof VFileStream) && (vfile1 instanceof VFileStream) && ((VFileStream) vfile).getSize() != ((VFileStream) vfile1).getSize()) {
//                        return false;
//                    }
//                    map.put(s, null);
//                }
//            }
//        } while (true);
//        return true;
//    }
//
//
//    void setMapAction(VFileDirectory vfiledirectory, Map map, URIFilter urifilter, boolean flag)
//            throws IOException, ObjectRepoException {
//        Iterator iterator = vfiledirectory.getChildren();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            VFile vfile = (VFile) iterator.next();
//            if (vfile instanceof VFileDirectory) {
//                setMapAction((VFileDirectory) vfile, map, urifilter, flag);
//            } else if (vfile instanceof VFileStream) {
//                String s = vfile.getURI();
//                if (urifilter == null || !urifilter.filterURI(s)) {
//                    if (flag) {
//                        map.put(s, null);
//                    } else {
//                        map.put(s, ((VFileStream) vfile).getInputStream());
//                    }
//                }
//            }
//        } while (true);
//    }
//
//
//    public boolean areDeploymentDescriptorsDifferent(DeploymentDescriptorProvider deploymentdescriptorprovider) {
//        return areDeploymentDescriptorsDifferent(deploymentdescriptorprovider, false);
//    }
//
//
//    public boolean areDeploymentDescriptorsDifferent(DeploymentDescriptorProvider deploymentdescriptorprovider, boolean flag) {
//        if (deploymentdescriptorprovider == null) {
//            throw new IllegalArgumentException("Original ServiceArchive must be specified.");
//        }
//        HashMap hashmap = new HashMap();
//        Iterator iterator = getDeploymentDescriptors().iterator();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            DeploymentDescriptor deploymentdescriptor = (DeploymentDescriptor) iterator.next();
//            if (!deploymentdescriptor.getType().equals(Modules.DEPLOYMENT_DESCRIPTOR_TYPE)) {
//                DeploymentDescriptor deploymentdescriptor2 = deploymentdescriptorprovider.getDeploymentDescriptorByName(deploymentdescriptor.getType(), deploymentdescriptor.getName());
//                if (deploymentdescriptor2 == null) {
//                    return true;
//                }
//                if (deploymentdescriptor.diff(deploymentdescriptor2, flag)) {
//                    return true;
//                }
//                hashmap.put(deploymentdescriptor.getName(), null);
//            }
//        } while (true);
//        for (Iterator iterator1 = deploymentdescriptorprovider.getDeploymentDescriptors().iterator(); iterator1.hasNext();) {
//            DeploymentDescriptor deploymentdescriptor1 = (DeploymentDescriptor) iterator1.next();
//            if (!deploymentdescriptor1.getType().equals(Modules.DEPLOYMENT_DESCRIPTOR_TYPE) && !hashmap.containsKey(deploymentdescriptor1.getName())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//
//    public String diffServiceArchive(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException {
//        return diffServiceArchive(servicearchive, false);
//    }
//
//
//    public String diffServiceArchive(ServiceArchive servicearchive, boolean flag)
//            throws IOException, ObjectRepoException {
//        if (servicearchive == null) {
//            throw new IllegalArgumentException("Original ServiceArchive must be specified.");
//        }
//        ServiceArchiveImpl servicearchiveimpl = this;
//        if ((!(servicearchive instanceof ApplicationArchive) || !(servicearchiveimpl instanceof ApplicationArchive)) && (!(servicearchive instanceof ServiceArchive) || !(servicearchiveimpl instanceof ServiceArchive))) {
//            throw new IllegalArgumentException("Must compare two application or service archives - mixed types s" +
//                    "pecified"
//            );
//        }
//        if (!servicearchive.getName().equals(servicearchiveimpl.getName()) && !(servicearchive instanceof ApplicationArchive)) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Comparing two service archives with different names: ").append(servicearchive.getName()).append(" and ").append(servicearchiveimpl.getName()).toString());
//        }
//        if (!servicearchiveimpl.doesContentEqual(servicearchive)) {
//            return "UPDATED_ARCHIVE_DEPLOY_NECESSARY";
//        }
//        if (servicearchiveimpl.areDeploymentDescriptorsDifferent(servicearchive, flag)) {
//            return "UPDATED_DD_DEPLOY_NECESSARY";
//        } else {
//            return "UNCHANGED_ARCHIVE";
//        }
//    }
//
//
//    public String diffServiceArchiveExtended(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException {
//        return diffServiceArchiveExtended(servicearchive, false);
//    }
//
//
//    public String diffServiceArchiveExtended(ServiceArchive servicearchive, boolean flag)
//            throws IOException, ObjectRepoException {
//        ServiceArchiveImpl servicearchiveimpl = this;
//        String s = diffServiceArchive(servicearchive, flag);
//        if (s == "UNCHANGED_ARCHIVE") {
//            ApplicationArchive applicationarchive = servicearchiveimpl.getApplicationArchive();
//            s = applicationarchive.diffServiceArchiveExtended(servicearchive.getApplicationArchive(), flag);
//        }
//        return s;
//    }
//
//
//    public List getTabNames(Locale locale)
//            throws SAXException, IOException, ObjectRepoException {
//        if (locale == null) {
//            throw new IllegalArgumentException("Locale must be specified.");
//        }
//        InputStream inputstream = getTabsInputStream();
//        SchemaModelProvider schemamodelprovider = getTabsProvider();
//        XiParser xiparser = XiParserFactory.newInstance();
//        XiNode xinode = xiparser.parse(new InputSource(inputstream), schemamodelprovider);
//        XiNode xinode1 = xinode.getFirstChild();
//        Iterator iterator = xinode1.getChildren();
//        ArrayList arraylist = new ArrayList();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            XiNode xinode2 = (XiNode) iterator.next();
//            if (xinode2.getName() != null && xinode2.getName().equals(ELEMENT_TAB)) {
//                String s = XiChild.getChild(xinode2, ELEMENT_NAME).getStringValue();
//                arraylist.add(s);
//            }
//        } while (true);
//        return arraylist;
//    }
//
//
//    public String getTabDisplayName(Locale locale, String s) {
//        String s1 = LocalizedException.getFormattedMessage("Archive", locale, (new StringBuilder()).append("tab.").append(s).toString(), null);
//        if (s1 != null && s1.length() > 0) {
//            return s1;
//        } else {
//            return s;
//        }
//    }
//
//
//    public List getTabIDs()
//            throws SAXException, IOException, ObjectRepoException {
//        return getTabNames(Locale.getDefault());
//    }
//
//
//    private SchemaModelProvider getTabsProvider()
//            throws IOException, SAXException {
//        if (mManager == null) {
//            throw new IllegalArgumentException("Can only access tabs if service archive is loaded from an InputS" +
//                    "tream."
//            );
//        }
//        DefaultSchemaCache defaultschemacache = new DefaultSchemaCache();
//        ClassLoader classloader = getClassLoader();
//        InputStream inputstream = classloader.getResourceAsStream("Tabs.xsd");
//        if (inputstream == null) {
//            throw new RuntimeException("Could not load Tabs.xsd. This schema must be on the classpath.");
//        } else {
//            SmSchema smschema = SmParseSupport.parseSchema(new InputSource(inputstream));
//            defaultschemacache.putSchema(smschema.getNamespace(), smschema);
//            return defaultschemacache;
//        }
//    }
//
//
//    private InputStream getTabsInputStream()
//            throws IOException, ObjectRepoException {
//        Object obj = null;
//        if (getVFileFactory().exists("Tabs.xml")) {
//            VFile vfile = getVFileFactory().get("Tabs.xml");
//            if (vfile instanceof VFileStream) {
//                obj = ((VFileStream) vfile).getInputStream();
//            }
//        }
//        if (obj == null) {
//            ArrayList arraylist = new ArrayList();
//            Iterator iterator = getDeploymentDescriptors().iterator();
//            do {
//                if (!iterator.hasNext()) {
//                    break;
//                }
//                DeploymentDescriptor deploymentdescriptor = (DeploymentDescriptor) iterator.next();
//                if (deploymentdescriptor.getCanConfigureAtDeployment() && deploymentdescriptor.getAdministratorComponentClassName() != null) {
//                    arraylist.add(deploymentdescriptor);
//                }
//            } while (true);
//            Object aobj[] = arraylist.toArray();
//            Arrays.sort(aobj);
//            XiFactory xifactory = XiFactoryFactory.newInstance();
//            XiNode xinode = xifactory.createDocument();
//            XiNode xinode1 = xifactory.createElement(ELEMENT_TABS);
//            xinode.appendChild(xinode1);
//            if (aobj.length > 0) {
//                XiNode xinode2 = xifactory.createElement(ELEMENT_TAB);
//                xinode1.appendChild(xinode2);
//                XiNode xinode3 = xifactory.createElement(ELEMENT_NAME);
//                xinode3.setStringValue("Advanced");
//                xinode2.appendChild(xinode3);
//                for (int i = 0; i < aobj.length; i++) {
//                    XiNode xinode4 = xifactory.createElement(ELEMENT_SECTION);
//                    xinode2.appendChild(xinode4);
//                    XiNode xinode5 = xifactory.createElement(ELEMENT_NAME);
//                    xinode5.setStringValue(((DeploymentDescriptor) aobj[i]).getName());
//                    xinode4.appendChild(xinode5);
//                    xinode4.appendChild(new Element(ELEMENT_TYPE, ((DeploymentDescriptor) aobj[i]).getType()));
//                }
//
//            }
//            SingleThreadedByteArrayOutputStream singlethreadedbytearrayoutputstream = new SingleThreadedByteArrayOutputStream();
//            XiSerializer.serialize(xinode, singlethreadedbytearrayoutputstream, "UTF-8", true);
//            obj = new ByteArrayInputStream(singlethreadedbytearrayoutputstream.toByteArray());
//        }
//        return ((InputStream) (obj));
//    }
//
//
//    public List getDeploymentDescriptorsByTabName(String s)
//            throws SAXException, IOException, ObjectRepoException {
//        if (s == null) {
//            throw new IllegalArgumentException("Tab name must be specified.");
//        }
//        InputStream inputstream = getTabsInputStream();
//        SchemaModelProvider schemamodelprovider = getTabsProvider();
//        XiParser xiparser = XiParserFactory.newInstance();
//        XiNode xinode = xiparser.parse(new InputSource(inputstream), schemamodelprovider);
//        XiNode xinode1 = xinode.getFirstChild();
//        ArrayList arraylist = new ArrayList();
//        for (Iterator iterator = xinode1.getChildren(); iterator.hasNext();) {
//            XiNode xinode2 = (XiNode) iterator.next();
//            if (xinode2.getName() != null && xinode2.getName().equals(ELEMENT_TAB) && s.equals(XiChild.getChild(xinode2, ELEMENT_NAME).getStringValue())) {
//                Iterator iterator1 = xinode2.getChildren();
//                while (iterator1.hasNext()) {
//                    XiNode xinode3 = (XiNode) iterator1.next();
//                    if (xinode3.getName() != null && xinode3.getName().equals(ELEMENT_SECTION)) {
//                        try {
//                            ExpandedName expandedname = XiChild.getChild(xinode3, ELEMENT_TYPE).getTypedValue().getAtom(0).castAsQName(xinode);
//                            DeploymentDescriptor deploymentdescriptor = getDeploymentDescriptorByName(expandedname, XiChild.getChild(xinode3, ELEMENT_NAME).getStringValue());
//                            if (deploymentdescriptor != null) {
//                                arraylist.add(deploymentdescriptor);
//                            }
//                        }
//                        catch (XmlAtomicValueCastException xmlatomicvaluecastexception) {
//                            xmlatomicvaluecastexception.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//
//        return arraylist;
//    }


//    public ClassLoader getClassLoader() {
//        if (mManager == null) {
//            break MISSING_BLOCK_LABEL_43;
//        }
//        ClassLoader classloader = (ClassLoader) mManager.getComponentSource().getComponent(DeploymentDescriptorRegistry.CLASS_LOADER_COMPONENT_NAME);
//        if (classloader == null) {
//            return getClass().getClassLoader();
//        }
//        return classloader;
//        PluginException pluginexception;
//        pluginexception;
//        return getClass().getClassLoader();
//    }


    @Override
    public void setApplicationArchive(ApplicationArchive applicationarchive) {
        this.appArchive = applicationarchive;
    }


    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }


    @Override
    public void removeDeploymentDescriptor(DeploymentDescriptor deploymentdescriptor) {
//        deploymentdescriptor.onRemove(this);
        this.deploymentDescriptors.remove(deploymentdescriptor);
    }


    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name must be specified");
        } else if ("Shared Archive.sar".equals(name)) {
            throw new IllegalArgumentException(
                    "Cannot give a service archive the name of the shared archive: Shared Archive.sar");
        } else if ("TIBCOApplicationArchive".equals(name)) {
            throw new IllegalArgumentException(
                    "Cannot give a service archive the name of the shared archive: TIBCOApplicationArchive");
        } else if (name.indexOf(";") >= 0) {
            throw new IllegalArgumentException("Names cannot have the semicolon character.");

        }
        this.name = name;
    }


    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public void setVersion(String version) {
        this.version = version;
    }


    @Override
    public String toString() {
        return this.name;
    }

//
//    protected String getInstanceVarFileName(String s) {
//        if (null == s || s.equals("")) {
//            int i = name.indexOf('.');
//            if (i > 0) {
//                s = name.substring(0, i);
//            } else {
//                s = name;
//            }
//        }
//        return (new StringBuilder()).append("TIBCO_SI_VARS_").append(s).append(".xml").toString();
//    }
//
//
//    public NameValuePairs getServiceInstanceVars(String s) {
//        NameValuePairs namevaluepairs;
//        VFileStream vfilestream;
//        namevaluepairs = null;
//        String s1 = getInstanceVarFileName(s);
//        vfilestream = null;
//        try {
//            vfilestream = (VFileStream) vFileFactory.get(s1);
//        }
//        catch (ObjectRepoException objectrepoexception) {
//        }
//        if (null == vfilestream) {
//            return null;
//        }
//        InputStream inputstream = vfilestream.getInputStream();
//        if (inputstream == null) {
//            return null;
//        }
//        try {
//            XiParser xiparser = XiParserFactory.newInstance();
//            XiNode xinode = xiparser.parse(new InputSource(inputstream));
//            PluginManager pluginmanager = mManager;
//            XiNode xinode1 = xinode.getFirstChild();
//            Iterator iterator = xinode1.getChildren();
//            do {
//                if (null != namevaluepairs || !iterator.hasNext()) {
//                    break;
//                }
//                XiNode xinode2 = (XiNode) iterator.next();
//                ExpandedName expandedname = xinode2.getName();
//                if (expandedname != null && !expandedname.equals(ARCHIVE_NAME) && !expandedname.equals(VERSION) && !expandedname.equals(OWNER) && !expandedname.equals(CREATION_DATE)) {
//                    try {
//                        XiNodePassingFactory xinodepassingfactory = null;
//                        try {
//                            xinodepassingfactory = (XiNodePassingFactory) pluginmanager.getComponentSource().getComponentFactory(XiNodePassingFactory.XINODE_PASSING_FACTORY);
//                        }
//                        catch (PluginException pluginexception1) {
//                            pluginmanager.addClassPathPlugin(new XiNodePassingFactory(), null);
//                            xinodepassingfactory = (XiNodePassingFactory) pluginmanager.getComponentSource().getComponentFactory(XiNodePassingFactory.XINODE_PASSING_FACTORY);
//                        }
//                        if (xinodepassingfactory != null) {
//                            xinodepassingfactory.setNode(xinode2);
//                        }
//                        ServiceArchivePassingFactory servicearchivepassingfactory = null;
//                        try {
//                            servicearchivepassingfactory = (ServiceArchivePassingFactory) pluginmanager.getComponentSource().getComponentFactory(ServiceArchivePassingFactory.SERVICE_ARCHIVE_PASSING_FACTORY);
//                        }
//                        catch (PluginException pluginexception2) {
//                            pluginmanager.addClassPathPlugin(new ServiceArchivePassingFactory(), null);
//                            servicearchivepassingfactory = (ServiceArchivePassingFactory) pluginmanager.getComponentSource().getComponentFactory(ServiceArchivePassingFactory.SERVICE_ARCHIVE_PASSING_FACTORY);
//                        }
//                        if (servicearchivepassingfactory != null) {
//                            servicearchivepassingfactory.setServiceArchive(this);
//                        }
//                        DeploymentDescriptor deploymentdescriptor = (DeploymentDescriptor) pluginmanager.getComponentSource().getComponentFactory(xinode2.getName().getExpandedForm()).getInstance(pluginmanager.getComponentSource());
//                        if (deploymentdescriptor != null && (deploymentdescriptor instanceof NameValuePairs)) {
//                            namevaluepairs = (NameValuePairs) deploymentdescriptor;
//                        }
//                    }
//                    catch (PluginException pluginexception) {
//                        pluginexception.printStackTrace();
//                    }
//                }
//            } while (true);
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return namevaluepairs;
//    }
//
//
//    public void setServiceInstanceVars(String s, NameValuePairs namevaluepairs) {
//        if (null == s) {
//            throw new IllegalArgumentException("null service instance name");
//        }
//        String s1 = getInstanceVarFileName(s);
//        VFileStream vfilestream = null;
//        try {
//            vfilestream = (VFileStream) vFileFactory.get(s1);
//        }
//        catch (ObjectRepoException objectrepoexception) {
//        }
//        if (null == namevaluepairs) {
//            if (vfilestream != null) {
//                vfilestream.delete();
//            }
//            return;
//        }
//        try {
//            if (null == vfilestream) {
//                vfilestream = (VFileStream) vFileFactory.create(s1, false);
//            }
//            XiFactory xifactory = XiFactoryFactory.newInstance();
//            XiNode xinode = xifactory.createDocument();
//            XiNode xinode1 = xifactory.createElement(DeploymentDescriptor.ROOT_ELEMENT);
//            xinode1.setNamespace(xifactory.createNamespace("", "http://www.tibco.com/xmlns/dd"));
//            xinode.appendChild(xinode1);
//            if (name != null) {
//                XiNode xinode2 = xifactory.createElement(ARCHIVE_NAME);
//                xinode2.setStringValue(name);
//                xinode1.appendChild(xinode2);
//            }
//            xinode1.appendChild(namevaluepairs.toXiNode(xifactory));
//            SingleThreadedByteArrayOutputStream singlethreadedbytearrayoutputstream = new SingleThreadedByteArrayOutputStream();
//            XiSerializer.serialize(xinode, singlethreadedbytearrayoutputstream, "UTF-8", true);
//            vfilestream.update(new ByteArrayInputStream(singlethreadedbytearrayoutputstream.toByteArrayNoCopy(), 0, singlethreadedbytearrayoutputstream.getCount()));
//        }
//        catch (ObjectRepoException objectrepoexception1) {
//        }
//        catch (IOException ioexception) {
//        }
//    }

}