/**
 * 
 */
package com.tibco.cep.studio.core.util.packaging.impl;


import static com.tibco.be.util.packaging.Constants.ARCHIVE_EXTENSION;
import static com.tibco.be.util.packaging.Constants.NAME_BE_PROPERTIES;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.xml.sax.InputSource;

import com.tibco.be.util.BEJarVersionsInspector;
import com.tibco.be.util.BEJarVersionsInspector.Version;
import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.DeploymentDescriptor;
import com.tibco.be.util.packaging.descriptors.MutableApplicationArchive;
import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.be.util.packaging.descriptors.NameValuePair;
import com.tibco.be.util.packaging.descriptors.ServiceArchiveFactory;
import com.tibco.be.util.packaging.descriptors.impl.ComponentSoftwareReference;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairImpl;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairs;
import com.tibco.be.util.packaging.descriptors.impl.StartAsOneOf;
import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.core.util.packaging.BARPackager;
import com.tibco.cep.studio.core.util.packaging.RuntimeClassesPackager;
import com.tibco.cep.studio.core.util.packaging.SARPackager;
import com.tibco.objectrepo.object.SubstitutionVariable;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class EMFBarPackager implements BARPackager{
	
	private static final String BE_JAR_VERSIONS = "versions";
	protected MutableApplicationArchive ear;
	protected MutableServiceArchive bar;
	protected Ontology emfOntology;
	protected SubstitutionVariable[] globalVars;
	protected String classpath;
//	protected ProjectPackager projectPackager;
	protected RuntimeClassesPackager runtimeClassesPackager;
	private StudioProjectConfiguration projectConfig;
	@SuppressWarnings("unused")
	private String ownerProjectPath;
	private IProgressMonitor monitor;
	private boolean useLegacyCompiler;
	
	
	/**
	 * @param project
	 * @param ear
	 * @param emfOntology
	 * @param globalVars
	 * @param classpath
	 * @param useLegacyCompiler TODO
	 * @throws Exception
	 */
	public EMFBarPackager(EMFProject project, 
						  MutableApplicationArchive ear,
			              Ontology emfOntology,
			              SubstitutionVariable[] globalVars,
			              String classpath, boolean useLegacyCompiler) throws Exception {
		this(project,ear,emfOntology,globalVars,classpath,new NullProgressMonitor(), useLegacyCompiler);
	}
	
	/**
	 * @param project
	 * @param ear
	 * @param emfOntology
	 * @param globalVars
	 * @param classpath
	 * @param monitor
	 * @param useLegacyCompiler TODO
	 * @throws Exception
	 */
	public EMFBarPackager(EMFProject project, 
			  MutableApplicationArchive ear,
            Ontology emfOntology,
            SubstitutionVariable[] globalVars,
            String classpath,IProgressMonitor monitor, boolean useLegacyCompiler) throws Exception {
		setMonitor(monitor);
		setUseLegacyCompiler(useLegacyCompiler);
		monitor.beginTask("Build BAR Package...",3);
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		this.projectConfig = project.getProjectConfiguration();
		this.classpath = classpath;
		this.ownerProjectPath=project.getRepoPath();
		this.emfOntology = emfOntology;
		this.globalVars = globalVars;
		this.ear = ear;
		
		monitor.subTask("Setting BAR default parameters...");
		//BE Creates only one bar archive
		this.bar = ServiceArchiveFactory.newServiceArchive(this.ear.getName() + ARCHIVE_EXTENSION);
		this.setStartAsOneOf();
//	    this.setFaultTolerance();
	    this.setBEProperties();	
	    this.setBEJarVersions();
	    this.setExternalDependencies();
	    this.setServiceSettableVariables();
		ear.addServiceArchive(bar);		
		monitor.worked(1);
//		projectPackager = new ProjectPackager(bar);
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		try {
			monitor.subTask("Setting BAR parameters...");
			if (monitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			final String description = projectConfig.getEnterpriseArchiveConfiguration().getDescription();
			final String author = projectConfig.getEnterpriseArchiveConfiguration().getAuthor();
			//Get compile path
			final String outputPath = projectConfig.getEnterpriseArchiveConfiguration().getTempOutputPath();
			String compilePath = null;
			if ((outputPath != null) && !outputPath.isEmpty()) {
				compilePath = outputPath;
			}
			//Get the compileWithDebug parameter
			final boolean compileWithDebug = projectConfig.getEnterpriseArchiveConfiguration().isDebug();
			// delete temporay files
			final boolean deleteTempFiles = projectConfig.getEnterpriseArchiveConfiguration().isDeleteTempFiles();
			//Runtime classes packager
			bar.setOwner(author);
			bar.setDescription(description);
			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			monitor.subTask("Package runtime classes...");
			runtimeClassesPackager = new DefaultRuntimeClassesPackager(bar,
					emfOntology, compilePath, false, classpath,
					compileWithDebug,deleteTempFiles, new SubProgressMonitor(getMonitor(),100), useLegacyCompiler);

			if (runtimeClassesPackager != null) {
				// this would be null if no bars existed in the archive
				runtimeClassesPackager.close();
			}
			monitor.worked(1);

		} catch (Exception e) {
			if (e instanceof CoreException
					&& ((CoreException) e).getStatus().getSeverity() == Status.CANCEL) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			throw e;
		} finally {
			getMonitor().done();
		}
	}

	
	
	/**
	 * @return the projectConfig
	 */
	public StudioProjectConfiguration getProjectConfig() {
		return projectConfig;
	}

	/**
	 * @param projectConfig the projectConfig to set
	 */
	public void setProjectConfig(StudioProjectConfiguration projectConfig) {
		this.projectConfig = projectConfig;
	}

	/**
	 * @return the ownerProjectPath
	 */
	public String getOwnerProjectPath() {
		return ownerProjectPath;
	}

	/**
	 * @param ownerProjectPath the ownerProjectPath to set
	 */
	public void setOwnerProjectPath(String ownerProjectPath) {
		this.ownerProjectPath = ownerProjectPath;
	}

	/**
	 * @return the useLegacyCompiler
	 */
	public boolean isUseLegacyCompiler() {
		return useLegacyCompiler;
	}

	/**
	 * @param useLegacyCompiler the useLegacyCompiler to set
	 */
	public void setUseLegacyCompiler(boolean useLegacyCompiler) {
		this.useLegacyCompiler = useLegacyCompiler;
	}

	/**
	 * @return the monitor
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public <C extends SARPackager> C getSARPackager() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.packaging.BARPackager#getRuntimeClassesPackager()
	 */
	@SuppressWarnings("unchecked")
	public <R extends RuntimeClassesPackager> R getRuntimeClassesPackager() {
		return (R)runtimeClassesPackager;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.packaging.BARPackager#getServiceArchive()
	 */
	public MutableServiceArchive getServiceArchive() {
		return bar;
	}
	
	/**
	 * 
	 * @param barModel
	 */
	protected void setName(Map barModel) {
		this.bar.setName(barModel.get(Constants.PROPERTY_NAME_NAME)
				+ Constants.ARCHIVE_EXTENSION);
	}

	/**
	 * 
	 * @param barModel
	 */
	protected void setOwner(Map barModel) {
		this.bar.setOwner((String) barModel.get("authorProperty"));
	}
	
	
	/**
	 * 
	 * @param barModel
	 */
	protected void setDescription(Map barModel) {
        this.bar.setDescription((String) barModel.get("description"));
    }	
	
	/**
	 * 
	 * @param barModel
	 */
	protected void setVersion(Map barModel) {
        this.bar.setVersion((String) barModel.get("versionProperty"));
    }
	
	
	
	protected void setServiceSettableVariables(Map barModel) {
        final Set<SubstitutionVariable> vars = new HashSet<SubstitutionVariable>();
        vars.add(new SubstitutionVariable(Constants.DD.CDD, ""));
        vars.add(new SubstitutionVariable(Constants.DD.PUID, ""));

        for (int i = 0; i < this.globalVars.length; i++) {
            try {
                final SubstitutionVariable globalVar = this.globalVars[i];
                if (globalVar.isServiceSettable()) {
                    vars.add(globalVar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.updateServiceLevelVariables(vars);
    }

	/**
	 * 
	 */
	protected void setStartAsOneOf() {
	    final StartAsOneOf startAsOneOf = new StartAsOneOf();
        startAsOneOf.addComponentSoftwareReference(new ComponentSoftwareReference(
                cep_commonVersion.engineName,
                cep_commonVersion.engineMinVersion,
                null));
	    this.bar.addDeploymentDescriptor(startAsOneOf);
	}
	
	/**
	 * 
	 */
	protected void setBEJarVersions() {
		final NameValuePairs nvpairs = new NameValuePairs(BE_JAR_VERSIONS);
        nvpairs.setConfigurableAtDeployment(false);
		final BEJarVersionsInspector inspector = new BEJarVersionsInspector();
		SortedMap<String, Version> map = inspector.getJarVersions(this.classpath);
		for (Version v : map.values() ) {
			NameValuePair nvpair = new NameValuePairImpl(v.getName(), v.getVersion(), null, false);
            nvpair.setConfigurableAtDeployment(false);
			nvpairs.addNameValuePair(nvpair);
		}
		this.bar.addDeploymentDescriptor(nvpairs);
	}
	
    
    /**
     * 
     */
protected void setBEProperties() {
        final SortedMap<String , SubstitutionVariable> beProperties = new TreeMap<String, SubstitutionVariable>(); // SortedMap will merge duplicates and keep stuff sorted

        // Loads the properties from XML file and writes them as substitution variables
        try {
            final File file = new File (System.getProperty("BE_HOME")
                    + "/lib" + Constants.PATH_TO_DEPLOYMENT_FILE);
            if (file.exists()) {
                final InputStream is = new FileInputStream(file);
                try {
                    final InputSource source = new InputSource(is);

                    XiNode node = XiParserFactory.newInstance().parse(source);
                    node = XiChild.getChild(node, Constants.XNAME_PROPERTIES);
                    for (final Iterator i = XiChild.getIterator(node, Constants.XNAME_PROPERTY); i.hasNext(); ) {
                        try {
                        	final XiNode propNode = (XiNode) i.next();
                            final String name = XiChild.getString(propNode, Constants.XNAME_NAME);
                            final String description = XiChild.getString(propNode, Constants.XNAME_DESCRIPTION);
                            String defaultValue = XiChild.getString(propNode, Constants.XNAME_DEFAULT);
                            defaultValue = (null == defaultValue) ? "" : defaultValue;
                            beProperties.put(name,
                                    new SubstitutionVariable(name, defaultValue, description,
                                        false, true, "String", null, 0));
                        } catch (Exception ignored) {
                        }
                    }
                } finally {
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateDescriptors(Utils.getSubstitutionVariablesAsDescriptor(beProperties.values(),
                NAME_BE_PROPERTIES));
    }
    
    
    
    /**
     * 
     */
    protected void setExternalDependencies() {
        final NameValuePairs nvps = new NameValuePairs(NameValuePairs.EXTERNAL_DEPENDENCY_DEPLOYMENT_DESCRIPTOR_NAME);
        //TODO external dependencies
//        addExternalResourceBom(nvps, calculatedDependencies);
//        addExternalPathDescriptors(nvps, getExtraEarData());
        this.bar.addDeploymentDescriptor(nvps);
    }
    
    
    /**
     * 
     */
    protected void setServiceSettableVariables() {
        final Set<SubstitutionVariable> vars = new LinkedHashSet<SubstitutionVariable>();
        vars.add(new SubstitutionVariable(Constants.DD.CDD, ""));
        vars.add(new SubstitutionVariable(Constants.DD.PUID, ""));

        for (final SubstitutionVariable globalVar : this.globalVars) {
            try {
                if (globalVar.isServiceSettable()) {
                    vars.add(globalVar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.updateServiceLevelVariables(vars);
    }
    
    
    /**
     * 
     * @param other
     */
    private void updateDescriptors(DeploymentDescriptor other) {
        if (null != other) {
            final DeploymentDescriptor dd = this.bar.getDeploymentDescriptorByName(other.getTypeXName(), other.getName());
            if (null != dd) {
                if ((dd instanceof NameValuePairs) && (other instanceof NameValuePairs)) {
                    final NameValuePairs servicePairs = (NameValuePairs) dd;
                    final List<NameValuePair> pairs = ((NameValuePairs) other).getNameValuePairs();
                    NameValuePair p;
                    for (Iterator<NameValuePair> j = pairs.iterator(); j.hasNext(); servicePairs.addNameValuePair(p)) {
                        p = j.next();
                        final NameValuePair old = servicePairs.getNameValuePair(p.getName());
                        if (old != null) {
                            servicePairs.removeNameValuePair(old);
                        }
                    }//for
                } else {
                    bar.removeDeploymentDescriptor(dd);
                    bar.addDeploymentDescriptor(other);
                }
            } else {
                this.bar.addDeploymentDescriptor(other);
            }
        }
    }

    
    /**
     * 
     * @param serviceLevelVars
     */
    private void updateServiceLevelVariables(Collection serviceLevelVars) {
        if (serviceLevelVars.size() > 0) {
            this.updateDescriptors(Utils.getSubstitutionVariablesAsDescriptor(serviceLevelVars,
                    NameValuePairs.TRA_PROPERTIES_VARIABLES_DEPLOYMENT_DESCRIPTOR_NAME));
            final ArrayList settable = new ArrayList();
            for (Iterator it = serviceLevelVars.iterator(); it.hasNext();) {
                final SubstitutionVariable var = (SubstitutionVariable) it.next();
                if (var.isDeploymentSettable()) {
                    settable.add(var);
                }
            }
//            if (settable.size() > 0) {
//                this.updateDescriptors(Utils.getSubstitutionVariablesAsDescriptor(settable,
//                        NameValuePairs.REPO_INSTANCE_GLOBAL_VARIABLES_DEPLOYMENT_DESCRIPTOR_NAME));
//            }//if
        }//if
    }
}
