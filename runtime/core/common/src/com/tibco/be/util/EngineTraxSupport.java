package com.tibco.be.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Templates;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;

public class EngineTraxSupport extends TraxSupport 
{
	protected static final ConcurrentHashMap<String, TemplatesArgumentPair> templateCache = new ConcurrentHashMap();
	
	public static TemplatesArgumentPair getTemplates(String key, String serializedTemplate, TypeManager typeMan) throws Exception {
        TemplatesArgumentPair tap = templateCache.get(key);
        if(tap == null) {
            tap = new TAPSync().init(templateCache, key, serializedTemplate, typeMan);
        }
        return tap;
    }
	
    protected static TemplatesArgumentPair makeTAP(String serializedTemplate, TypeManager typeMan) throws Exception {
        ArrayList<String> parms = new ArrayList();
        String xsltTemplate = XSTemplateSerializer.deSerialize(serializedTemplate, parms, null);
        Class recvParameterClass = null;
        Class dvmClass = null;
        if (parms.size() > 0) {
            String modelnm = parms.get(0);
            TypeDescriptor td = typeMan.getTypeDescriptor(modelnm);
            if(td != null) recvParameterClass = td.getImplClass();
            td = typeMan.getTypeDescriptor(modelnm + "DVM");
            if(td != null) dvmClass = td.getImplClass();
        }

        return makeTAP(xsltTemplate, recvParameterClass, dvmClass, parms);
    }

    static private class TAPSync extends TemplatesArgumentPair {
        private volatile TemplatesArgumentPair tap = null;
        private RuntimeException savedEx = null;

        public TAPSync() {
            super(null,null,null, null);
        }

        synchronized public TemplatesArgumentPair init(ConcurrentHashMap<String, TemplatesArgumentPair> cache,
                                   String key, String serializedTemplate, TypeManager typeMan) throws Exception
        {
            TemplatesArgumentPair curr = cache.putIfAbsent(key, this);
            if(curr == null) {
            	try {
            		tap = makeTAP(serializedTemplate, typeMan);
            	} catch (Exception ex) {
            		cache.remove(key);
            		savedEx = new RuntimeException("Caching this template failed on another thread.  This is the saved exception from that thread.", ex);
            		throw ex;
            	}
            	cache.put(key, tap);
            } else {
                tap = curr;
            }
            return tap;
        }

        @Override
        public Templates getTemplates() {
            if(tap == null) return getTemplates_sync();
            else return tap.getTemplates();
        }

        @Override
        public List<String> getParamNames() {
            if(tap == null) return getParamNames_sync();
            else return tap.getParamNames();
        }

        @Override
        public Class getRecvParameterClass() {
            if(tap == null) return getRecvParameterClass_sync();
            else return tap.getRecvParameterClass();
        }
        
        @Override
        public Class getDvmClz() {
        	if(tap == null) return getDvmClz_sync();
            else return tap.getDvmClz();
    	}

        @Override
        public String getXSLT() {
        	if(tap == null) return getXSLT_sync();
            else return tap.getXSLT();
    	}
        
        @Override
    	public String getInstanceVarName() {
        	if(tap == null) return getInstanceVarName_sync();
            else return tap.getInstanceVarName();
    	}
        
        private synchronized Templates getTemplates_sync() {
        	if(savedEx != null) throw savedEx;
            return tap.getTemplates();
        }

        private synchronized List<String> getParamNames_sync() {
        	if(savedEx != null) throw savedEx;
            return tap.getParamNames();
        }

        private synchronized Class getRecvParameterClass_sync() {
        	if(savedEx != null) throw savedEx;
            return tap.getRecvParameterClass();
        }
        
        private synchronized Class getDvmClz_sync() {
        	if(savedEx != null) throw savedEx;
            return tap.getDvmClz();
        }
        
        private synchronized String getXSLT_sync() {
        	if(savedEx != null) throw savedEx;
            return tap.getXSLT();
        }
        
        private synchronized String getInstanceVarName_sync() {
        	if(savedEx != null) throw savedEx;
            return tap.getInstanceVarName();
        }
    }
}
