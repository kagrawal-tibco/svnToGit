package com.tibco.cep.repo.mutable;


import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.VFileHelper;
import com.tibco.cep.repo.mutable.provider.MutableOntologyResourceProvider;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.repo.provider.impl.OntologyResourceProviderImpl;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.streamfile.FileFactory;
import com.tibco.objectrepo.vfile.tibrepo.RepoVFileFactory;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:55:23 AM
 * To change this template use File | Settings | File Templates.
 * @deprecated
 */
public class MutableBEProject extends BEProject implements MutableProject{

    public MutableBEProject(
            String path) {
        super(path);
    }

    public MutableBEProject(
            List providers,
            String path) {
        super(providers, path);
    }

    protected void init() {
        globalvariables = new GlobalVariablesProviderImpl();
        ontologyProvider = new MutableOntologyResourceProvider(this);
        providerFactory.registerProvider(ontologyProvider);
        providerFactory.registerProvider(globalvariables);

    }

    public void save() throws Exception
    {
        try {
            for (Iterator providers = providerFactory.getProviders().iterator(); providers.hasNext(); ) {
                ResourceProvider provider = (ResourceProvider)providers.next();
                if (provider instanceof MutableResourceProvider) {
                    if (((MutableResourceProvider)provider).isDirty()) {
                        updateProviderChanges(((MutableResourceProvider)provider));
                    }
                }
            }
            commitVFile(this.vfFactory);
        }
        catch (Exception e) {
            ((FileFactory)vfFactory).rollback();
            e.printStackTrace();
            throw e;
        }

        //clear the dirty status
        for (Iterator providers = providerFactory.getProviders().iterator(); providers.hasNext(); ) {
            ResourceProvider provider = (ResourceProvider)providers.next();
            if (provider instanceof MutableResourceProvider) {
                ((MutableResourceProvider)provider).clearDirty();
            }
        }

    }


    public void saveAs(String path) throws Exception {
        final VFileFactory newVfFactory = VFileHelper.createVFileFactory(path, null);
        //for (Iterator providers = providerFactory.getProviders().iterator(); providers.hasNext(); ) {
        //}
        for (Iterator it = this.getOntology().getEntities().iterator(); it.hasNext(); ) {
            this.saveAs((Entity) it.next(), newVfFactory);
        }
        this.commitVFile(newVfFactory);
        if (null != this.vfFactory) {
            this.vfFactory.destroy();
        }
        this.vfFactory = newVfFactory;
    }


    private void saveAs(Entity entity, VFileFactory newVfFactory) throws Exception {
        final String newPath = entity.getFullPath() +
                OntologyResourceProviderImpl.getExtension(entity);
        final VFile file = newVfFactory.create(newPath, true);
        if (file instanceof VFileStream) {
            final InputStream in = OntologyResourceProviderImpl.getInputStream(entity);
            ((VFileStream)file).update(in);
        }
    }


    private void updateProviderChanges(MutableResourceProvider provider) throws Exception
    {
        for (Iterator itr = provider.getChangeList().iterator(); itr.hasNext(); ) {
            ResourceChangeNotificationEvent evt = (ResourceChangeNotificationEvent) itr.next();
            updateVFileEntries(evt);
        }
    }


    private void updateVFileEntries(ResourceChangeNotificationEvent evt) throws Exception {

        String oldpath = evt.getOldPath() + evt.getExtension();


        switch (evt.getModificationCode()) {
            case ENTITY_DELETE:
                if (vfFactory.exists(oldpath) || vfFactory.existsPersistently(oldpath)) {
                    vfFactory.delete(oldpath);
                }
                break;
            case ENTITY_UPDATE:
            {
                this.updateVFileContent(evt);
                break;
            }
            case ENTITY_MOVED:
            {
                String newpath = evt.getFullPath() + evt.getExtension();
                VFile file = vfFactory.get(oldpath);
                file.moveTo(newpath, true);

                this.updateVFileContent(evt);
                break;
            }
            case ENTITY_RENAME:
            {
                String newName = evt.getName() +  evt.getExtension();
                VFile file = vfFactory.get(oldpath);
                file.renameTo(newName);

                this.updateVFileContent(evt);
                break;
            }
            case ENTITY_ADDED:
            {
                final String newpath = evt.getFullPath() + evt.getExtension();
                VFile file = vfFactory.get(newpath);
                if (null == file) {
                    file = vfFactory.create(newpath, true);
                }

                this.updateVFileContent(evt);
                break;
            }
        }

    }



    private void updateVFileContent(ResourceChangeNotificationEvent evt) throws Exception {
        final String path = evt.getFullPath() + evt.getExtension();
        final InputStream in = evt.getInputStream();
        final VFile file = vfFactory.get(path);
        if (file instanceof VFileStream) {
            ((VFileStream)file).update(in);
        }
    }


    private void commitVFile(VFileFactory factory) throws Exception {

        if (factory instanceof ZipVFileFactory) {
            ((ZipVFileFactory)factory).save();
        }
        else if (factory instanceof FileFactory){
            ((FileFactory)factory).save();
        }
        else if (factory instanceof RepoVFileFactory) {
            ((RepoVFileFactory)factory).save();
        }
    }

    public boolean isDirty() {
        for (Iterator providers = providerFactory.getProviders().iterator(); providers.hasNext(); ) {
            ResourceProvider provider = (ResourceProvider)providers.next();
            if (provider instanceof MutableResourceProvider) {
                if (((MutableResourceProvider)provider).isDirty()) return true;
            }
        }

        return false;
    }
}
