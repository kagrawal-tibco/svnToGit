package com.tibco.cep.repo;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.repo.provider.OntologyResourceProvider;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.repo.provider.impl.OntologyResourceProviderImpl;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.impl.TargetNamespaceCache;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFragment;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 29, 2006
 * Time: 4:56:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEProject implements  Project {

    protected static HashMap ontologyToBEProject = new HashMap();
    // protected static XiParser parser = XiParserFactory.newInstance();

    protected TargetNamespaceCache tnsCache;

    protected ResourceProviderFactory providerFactory = new ResourceProviderFactory();
    protected HashMap cachedElements = new HashMap ();




    protected VFileFactory vfFactory;

    protected GlobalVariablesProvider globalvariables;
    protected OntologyResourceProvider<? extends BEProject> ontologyProvider;
    protected String repoPath;



    public BEProject(
            String path) {
        this(VFileHelper.createVFileFactory(path, null));
        this.repoPath = path;
    }


    public BEProject(
            List providers,
            String path) {
        this(path);
        if (providers == null) return;
        Iterator r = providers.iterator();
        while (r.hasNext()) {
            ResourceProvider provider = (ResourceProvider) r.next();
            providerFactory.registerProvider(provider);
        }
    }

    public BEProject(VFileFactory factory) {
        if (null == factory) {
            throw new NullPointerException();
        }
        vfFactory = factory;
        this.init();
    }
    
    
    public String getRepoPath() {
		return repoPath;
	}

    protected void init() {
        globalvariables = new GlobalVariablesProviderImpl();
        ontologyProvider = new OntologyResourceProviderImpl<BEProject>(this);

        providerFactory.registerProvider(ontologyProvider);
        providerFactory.registerProvider(globalvariables);

    }

    public void load() throws Exception{

        tnsCache =  getTnsCache();
        loadVFiles();
        //Note the order, first load all the entities then set the entity listener
        ontologyProvider.init();
        BEProject.ontologyToBEProject.put(ontologyProvider.getOntology(), this);
        cacheSmElements();
    }


    public ResourceProviderFactory getProviderFactory() {
        return providerFactory;
    }

    public SmElement getSmElement(ExpandedName name)  {
        return (SmElement)cachedElements.get(name);
    }

    public String getName() {
        return vfFactory.getRootDirectory().getName();
    }

    public boolean isValidDesignerProject() {
        try {
            VFile f = vfFactory.get("/.folder");
            if (f != null) return true;
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }


    /**
     * Create an empty project from the path specified and return the project
     * @return  a BEProject
     */
    public Project createProject()
            throws Exception {

        ontologyProvider.init();
        BEProject.ontologyToBEProject.put(ontologyProvider.getOntology(), this);
        return this;
    }


    public void cacheSmElements() {


        Iterator ir = getTnsCache().getLocations();
        while (ir.hasNext()) {
            String loc = (String)ir.next();
            TnsDocument doc = getTnsCache().getDocument(loc);
            Iterator frags = doc.getFragments();

            while (frags.hasNext()) {
                TnsFragment frag = (TnsFragment) frags.next();
                Iterator r = frag.getComponents(SmComponent.ELEMENT_TYPE);
                while (r.hasNext()) {
                    TnsComponent comp = (TnsComponent)r.next();
                    if(!cachedElements.containsKey(comp.getExpandedName())){
                    	cachedElements.put(comp.getExpandedName(), comp);
                    }
                }
            }
        }

    }

    /**
     * Disposes of the resources used by the project.
     * Do not use the project, its VFileFactory or its TnsCache after calling this method.
     */
    public void close() {
        ontologyToBEProject.remove(ontologyProvider.getOntology());

        final List providers = new ArrayList(providerFactory.getProviders());
        for (Iterator it = providers.iterator(); it.hasNext(); ) {
            ResourceProvider provider = (ResourceProvider) it.next();
            providerFactory.unRegisterProvider(provider);
        }
        if(tnsCache!=null){
        	((TargetNamespaceCache)tnsCache).flush();
        }
        if(vfFactory!=null){
        	vfFactory.destroy();
        }

        tnsCache = null;
        providerFactory = null;
        globalvariables = null;
        ontologyProvider = null;
        providerFactory = null;
        vfFactory = null;
    }

    /**
     * @param e an Entity
     * @return the BEProject that this entity is a part of, null if none found.
     */
    public static Project getBEProject(Entity e) {
        if (null == e) {
            return null;
        } else {
            return (Project) com.tibco.cep.repo.BEProject.ontologyToBEProject.get(e.getOntology());
        }
    }



    /**
     * @param o an Ontology
     * @return the BEProject that manages the Ontlogy, null if none found.
     */
    public static Project getBEProject(Ontology o) {
        return (Project) com.tibco.cep.repo.BEProject.ontologyToBEProject.get(o);
    }


    protected void loadVFiles() throws Exception {
        scanDirectory(vfFactory.getRootDirectory(), "", ontologyProvider.getOntology());
    }


    public void scanDirectory(VFileDirectory dir, String path, Ontology ontology) throws Exception {
        for (Iterator i = dir.getChildren(); i.hasNext(); ) {
            VFile vff = (VFile) i.next();

            if (vff instanceof VFileDirectory) {
                String newPath;
                if (path.length() < 1) {
                    newPath = vff.getName();
                } else {
                    newPath = path + "/" + vff.getName();
                }
                scanDirectory((VFileDirectory)vff, newPath, ontology);
            } else if (vff instanceof VFileStream) {
                scanFile((VFileStream) vff, path, ontology);
            }
        }
    }


    protected void scanFile(VFileStream file, String path, Ontology ontology ) throws Exception {
        try {
            final String uri = file.getFullURI();
            for (Iterator r = providerFactory.getProviders().iterator(); r.hasNext(); ) {
                final ResourceProvider provider = (ResourceProvider) r.next();

                if (provider.supportsResource(uri)) {
                    final int ret = provider.deserializeResource(uri, file.getInputStream(), this, file);
                    if ((ret & ResourceProvider.STOP) == ResourceProvider.STOP) {
                        break;
                    }
                }
            }

        }
        catch (Exception e) {
            System.out.println("** Exception while processing file: " + path);
            e.printStackTrace();
        }

    }
    
    public <T extends BETargetNamespaceCache> T getTnsCache() {
    	if(this.tnsCache == null) {
			this.tnsCache = (T) new BETargetNamespaceCache(){
			@Override
			public void resourceChanged(String sysId, InputStream is) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public String getRootURI() {
				return null;
			}};
		}
		return (T) this.tnsCache;
    }

    public VFileFactory getVFileFactory() {
        return vfFactory;
    }


    public Ontology getOntology() {
        return ontologyProvider.getOntology();
    }


    public com.tibco.cep.repo.GlobalVariables getGlobalVariables() {
        return globalvariables;
    }


    public SmElement getSmElement(Entity e) {

        String nsURI = RDFTnsFlavor.BE_NAMESPACE + e.getFullPath();
        ExpandedName name = ExpandedName.makeName(nsURI, e.getName());

        return (SmElement)cachedElements.get(name);
    }

    public OntologyResourceProvider getOntologyProvider() {
        return ontologyProvider;
    }




}
