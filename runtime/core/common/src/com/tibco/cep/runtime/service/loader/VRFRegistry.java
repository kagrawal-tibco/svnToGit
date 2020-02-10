package com.tibco.cep.runtime.service.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 2, 2008
 * Time: 7:08:16 PM
 * To change this template use File | Settings | File Templates.
 */

/*
    Unscientific test with JDK 5 server VM and all methods warmed up by 3000 untimed calls:
    400ms to create 54000 classloader instances.  Approx 600 bytes per classloader.
    Less than 1ms to create a new classloader and load one 7KB class (avg over 5000 loads)
    
    lastloader will be used to load new VRF impl classes unless a class by that name is already loaded
    by lastLoader.  In that case, lastLoader is replaced with a new classLoader
    
    A VRFClassLoader will be collected when no VRFImpl instances in the registry are 
    instances of classes loaded by that classLoader
    
    current hot deploy implementation (as of April 2009) loads all classes in a hot deploy directory
    so assuming classes are never deleted from that directory, 
    the behavior would be to discard lastLoader and re load all the classes into a new loader 

 */
public class VRFRegistry {
//    private HashMap<String, HashMap<String, VRFImpl>> registry = new HashMap<String, HashMap<String, VRFImpl>>();
    private HashMap<String, VRFImpl> defaultRegistry = new HashMap<String, VRFImpl>();
    private HashMap<ImplKey, VRFImpl> implRegistry = new HashMap<ImplKey, VRFImpl>();
    private HashMap<String, VRFImplSet> implSetRegistry = new HashMap<String, VRFImplSet>();
    private Logger logger;
    private VRFClassLoader lastLoader;
    private Method makeImpl = null;
    private RuleServiceProvider rsp;
    
    public VRFRegistry(ClassLoader parentClassLoader, Logger logger, RuleServiceProvider rsp) {
        lastLoader = new VRFClassLoader(parentClassLoader);
        this.logger = logger;
        this.rsp = rsp;
    }
    
    public Collection<VRFImpl> getVRFImpls(String vrfURI) {
        if(vrfURI != null && vrfURI.length() > 0) {
            VRFImplSet impls = implSetRegistry.get(vrfURI);
            if(impls != null) {
                return impls.impls;
            }
        }
        return null;
    }
    
    public VRFImplSet getVRFImplSet(String vrfURI) {
        return implSetRegistry.get(vrfURI);
    }

    public Collection<String> getVRFImplNames(String vrfURI) {
        if(vrfURI != null && vrfURI.length() > 0) {
            VRFImplSet impls = implSetRegistry.get(vrfURI);
            if(impls != null) {
                return impls.names;
            }
        }
        return null;
    }

    public VRFImpl getVRFImpl(String vrfURI, String implName) {
        if(vrfURI != null && implName != null && vrfURI.length() > 0 && implName.length() > 0) {
            return implRegistry.get(implKey(vrfURI, implName));
        }
        return null;
    }
    
    public VRFImpl getVRFDefaultImpl(String vrfURI) {
        if(vrfURI != null && vrfURI.length() > 0) {
            return defaultRegistry.get(vrfURI);
        }
        return null;
    }
    
    public void setVRFDefaultImpl(String vrfURI, String implName) {
        if(vrfURI != null && implName != null && vrfURI.length() > 0 && implName.length() > 0) {
            VRFImpl entry = implRegistry.get(implKey(vrfURI, implName));
            if(entry != null) {
                setVRFDefault(vrfURI, entry);
            }
        
        }
    }
    
    private ImplKey implKey(String vrfURI, String implName) {
        return new ImplKey(vrfURI, implName);
    }
    
    protected boolean addVRFImpl(String generatedClassName, byte[] bytecode) throws InstantiationException, IllegalAccessException {
        if(generatedClassName != null && bytecode != null && generatedClassName.length() > 0) {
            VRFImpl impl = null;
            int lastDot = generatedClassName.lastIndexOf('.');
            if(lastDot < 0) return false;
            
            //TODO this is only activated by calls from cluster hot deploy.
            //make both cluster and DM hotdeploy work the same
            String extension = generatedClassName.substring(lastDot);
            if(extension.equals(".class")) {
                generatedClassName = generatedClassName.substring(0,lastDot);
            } else if(extension.equals(".rulefunctionimpl")) {
                generatedClassName = generatedClassName.substring(0,lastDot);

                try {
                    if(makeImpl == null) {
                        Class eevi = getClass().getClassLoader().loadClass("com.tibco.cep.decision.table.interpreted.impl.ExpressionEvaluatorVRFImpl");
                        makeImpl = eevi.getMethod("makeVRFImpl", InputStream.class, RuleServiceProvider.class);
                    }
                    impl = (VRFImpl)makeImpl.invoke(null, new ByteArrayInputStream(bytecode), ((BEClassLoader)lastLoader.getParent()).serviceProvider);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchMethodException ex) {
                    throw new RuntimeException(ex);
                }
            }
            
            //DM doesn't add the .class extension and does not pass in rulefunctionimpl files
            //so infer here that if impl is null the input must be a class
            if(impl == null) {
                Class c = addClass(generatedClassName, bytecode);
                if(VRFImpl.class.isAssignableFrom(c) && StaticInitHelper.initializeClass(c, logger, rsp)) {
                    impl = (VRFImpl) c.newInstance();
                }
            }

            String vrfURI = ModelNameUtil.vrfImplClassFQNToVRFURI(generatedClassName);
            String implName = ModelNameUtil.vrfImplClassFQNToImplName(generatedClassName);
            addVRFImplInternal(impl, vrfURI, implName);
            return true;
        }
        return false;
    }
        
    private void addVRFImplInternal(VRFImpl impl, String vrfURI, String implName) {
        if(impl != null) {
            VRFImpl prevImpl = null;
            ImplKey ik = implKey(vrfURI, implName);
            prevImpl = implRegistry.get(ik);

            //remove the old impl before inserting new one
            if(prevImpl != null) {
                removeVRFImpl(vrfURI, implName, true);
            }

            VRFImplSet implSet = implSetRegistry.get(vrfURI);
            if(implSet == null) {
                implSet = new VRFImplSet();
                implSetRegistry.put(vrfURI, implSet);
            }

            //only set the new impl as default if it was inserted at the
            //start of the list of impls
            if(implSet.insertImpl(impl, implName)) {
                setVRFDefault(vrfURI, impl);
            }
            
            //do this last because removeVRFImpl looks in here for the previous impl
            implRegistry.put(ik, impl);

        }
    }
       
    protected void clearAllImpls() {
        
        vrfImplsRemoved(implRegistry.values());
       
        implRegistry.clear();
        implSetRegistry.clear();
        defaultRegistry.clear();
    }

    protected void clearAllDefaults() {
        defaultRegistry.clear();
    }
    
    protected void clearVRFDefault(String vrfURI) {
        defaultRegistry.remove(vrfURI);
    }

    protected void setVRFDefault(String vrfURI, VRFImpl entry) {
        defaultRegistry.put(vrfURI, entry);
    }
    protected void clearVRFImpls(String vrfURI) {
        clearVRFImplsInner(vrfURI);
    }
    private void clearVRFImplsInner(String vrfURI) {
        VRFImplSet implSet = implSetRegistry.remove(vrfURI);
        defaultRegistry.remove(vrfURI);

        if(implSet != null) {
            vrfImplsRemoved(implSet.impls);
            for(String implName : implSet.names) {
                implRegistry.remove(implKey(vrfURI, implName));
            }
        }
    }
    
    protected void clearVRFImpls(Collection<String> vrfURIs) {
        if(vrfURIs == null) return;
        for(String vrfURI : vrfURIs) {
            clearVRFImplsInner(vrfURI);
        }
    }


    protected boolean removeVRFImpl(String vrfURI, String implName) {
        return removeVRFImpl(vrfURI, implName, false);
    }
    private  boolean removeVRFImpl(String vrfURI, String implName, boolean keepImplSet) {
    	VRFImpl impl = null;
        if(vrfURI != null && vrfURI.length() > 0 && implName != null && implName.length() > 0) {
            impl = implRegistry.remove(implKey(vrfURI, implName));
            vrfImplRemoved(impl);
            
            VRFImplSet implSet = implSetRegistry.get(vrfURI);
            VRFImpl def = getVRFDefaultImpl(vrfURI);
            
            if(implSet == null) {
            	if(def == impl) defaultRegistry.remove(vrfURI);
            } else if(!keepImplSet && implSet.impls.size() <= 1) {
                implSetRegistry.remove(vrfURI);
                if(def == impl) defaultRegistry.remove(vrfURI);
            } else {
                if(def == implSet.removeImpl(implName)) {
                    if(implSet.impls.size() > 0) defaultRegistry.put(vrfURI, implSet.impls.get(0));
                    else defaultRegistry.remove(vrfURI);
                }
            }
        }
        return impl != null;
    }
        
    private void vrfImplsRemoved(Collection<VRFImpl> entries) {
        for(VRFImpl entry : entries) {
            vrfImplRemoved(entry);
        }
    }
    
    private void vrfImplRemoved(VRFImpl entry) {
        if(entry != null) {
            StaticInitHelper.uninitializeClass(entry.getClass(), logger, rsp);
        }
    }
    
    //TODO should check if the bytes are the same as the previous version?
    private Class addClass(String name, byte[] bytes) {
        if(lastLoader.hasClass(name)) {
            lastLoader = new VRFClassLoader(lastLoader.getParent());
        }
        
        return lastLoader.defineVRFClass(name, bytes);
    }
    
    private static class VRFClassLoader extends ClassLoader {
        public VRFClassLoader(ClassLoader parent) {
            super(parent);
        }
        
        public final Class defineVRFClass(String name, byte[] bytes) {
            return defineClass(name, bytes, 0, bytes.length);
        }
        
        public final boolean hasClass(String name) {
           return findLoadedClass(name) != null;
        }
    }
    
    private class ImplKey { //implements Comparable<ImplKey> {
        private String vrfURI;
        private String implName;
        private int hashCode;
        //private int priority;
        
        private ImplKey(String vrfURI, String implName) {
            this.vrfURI = vrfURI;
            this.implName = implName;
            hashCode = new HashCodeBuilder().append(vrfURI).append(implName).toHashCode();
        }
        
        public int hashCode() {
            return hashCode;
        }
        
        public boolean equals(Object o) {
            if(o instanceof ImplKey) {
                ImplKey ik = (ImplKey)o;
                return (ik.implName == implName || implName != null && implName.equals(ik.implName)) 
                        && (ik.vrfURI == vrfURI || vrfURI != null && vrfURI.equals(ik.vrfURI));
                        //&& ik.priority == priority;
            }
            return false;
        }
    }
}