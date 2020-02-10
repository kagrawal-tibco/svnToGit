package com.tibco.cep.studio.debug.smap;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;

/*
@author ssailapp
@date Jul 30, 2009 2:57:58 PM
 */

public class SMapResourceProvider  implements ResourceProvider{
	protected Map<String,byte[]> m_registry = new HashMap<String,byte[]>();
	protected String jarUri;
    public SMapResourceProvider() {

    }
    public String getName() {
        return SMapResourceProvider.class.getName();
    }
    
    @Override
    public void init() throws Exception {
    	// TODO Auto-generated method stub
    	
    }

    public boolean supportsResource(String uri) {
        String lower = uri.toLowerCase();
        if (lower.endsWith(".smap") || lower.endsWith(".jar")) 
        	return true;
        
        return false;
    }
    
    
    @Override
    public int deserializeResource(String uri, InputStream is, Project project,
    		VFileStream stream) throws Exception {
    	if(uri.toLowerCase().endsWith(".jar")) {
    		final ZipVFileFactory vFileFactory = new ZipVFileFactory(new ZipInputStream(is));
    		jarUri = vFileFactory.getURI();
            final VFileDirectory dir = vFileFactory.getRootDirectory();
            ((BEProject) project).scanDirectory(dir, uri, null);
    	} else {
    		String smapUri = uri.substring(jarUri.length());
    		m_registry.put(smapUri, getBytes(is));
    	}
    	return ResourceProvider.SUCCESS_CONTINUE;
    }

	static private byte[] getBytes(InputStream is) throws Exception {
		final ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    for(int x = is.read(); x != -1; x = is.read()) {
	        buf.write(x);
	    }
	    return buf.toByteArray();
	
	}
	
	public Collection<String> getAllResourceURI() {
        return m_registry.keySet();
    }
	
	public byte[] getResourceAsByteArray(String uri) {
        return (byte[]) m_registry.get(uri);
    }

    
//    public void readFromJarFile(JarInputStream jis) throws Exception {
//
//        ByteArray buf = new ByteArray(8192);
//        for (JarEntry je = jis.getNextJarEntry(); je != null; je = jis.getNextJarEntry()) {
//            buf.reset();
//            if (je.isDirectory()) continue;
//            String pathname = je.getName();
//            if(pathname.endsWith(".smap")) {
//                byte[] b = getBytes(jis, buf);
//                this.register(pathname, new ByteArrayInputStream(b));
//            }
//        }
//    }
//
//    static private byte[] getBytes(JarInputStream jis, ByteArray buf) throws Exception {
//        for(int x = jis.read(); x != -1; x = jis.read()) {
//            buf.add((byte)x);
//        }
//        return buf.getValue();
//
//    }
}
