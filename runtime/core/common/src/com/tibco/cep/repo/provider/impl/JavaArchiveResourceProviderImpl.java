package com.tibco.cep.repo.provider.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.datamodel.XiNode;

/*
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:26:54 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * Jar resource Provider
 */
public class JavaArchiveResourceProviderImpl
        implements JavaArchiveResourceProvider {


    private static final int BUFFER_SIZE = 2048;

    protected boolean open = false;
    protected Map<String, byte[]> registry = new HashMap<String, byte[]>();


    public JavaArchiveResourceProviderImpl() {
    }


    @Override
    public Collection getAllResourceURI() {
        return this.registry.keySet();
    }


    @Override
    public byte[] getResourceAsByteArray(String uri) {
        return this.registry.get(uri);
    }


    @Override
    public XiNode getResourceAsXiNode(String uri) throws Exception {
        return null;
    }


    @Override
    public void removeResource(String uri) {
        this.registry.remove(uri);
    }


    @Override
    public void init() throws Exception {
    }


    public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
        is.reset();
        this.register(uri, is);
        this.open = true;
        return SUCCESS_CONTINUE;
    }


    public boolean supportsResource(String uri) {
        return (null != uri)
                && uri.toLowerCase().endsWith(".jar");
    }


    protected void register(String uri, InputStream is) throws Exception {
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();
        final byte[] buffer = new byte[BUFFER_SIZE];

        is.reset();
        for (int bytesRead = 0; bytesRead >= 0;) {
            bytesRead = is.read(buffer);
            if (bytesRead > 0) {
                boas.write(buffer, 0, bytesRead);
            }//if
        }//for
        final byte[] data = boas.toByteArray();

        this.registry.put(uri, data);
    }

    
    @Override
	public List<URL> getJarResourceURLS() {
    	List<URL> urls = new ArrayList<>();
		for(Entry<String, byte[]> entry:registry.entrySet()) {
			final String uri = entry.getKey();
			final ByteArrayInputStream bais = new ByteArrayInputStream(entry.getValue());
			
			try {
				URI jarUri = new URI(uri);
				//URL jarURL = jarUri.toURL();
				File f = new File(uri);
				if(f.exists()) {
					// TIBCO Admin deployments have file URLS
					urls.add(new URL("file",null,0,uri.replaceFirst("\\/\\/", ""),new URLStreamHandler() {
						
						@Override
						protected URLConnection openConnection(URL u) throws IOException {
							
							return new URLConnection(u){

								@Override
								public void connect() throws IOException {
								}

								@Override
								public InputStream getInputStream() throws IOException {								
									return bais;
								}
								
								
								
							};
						}
					}));
				} else {
					//Regular deployments have mem: urls "mem:///1425358267433/be.jar"
					urls.add(new URL(jarUri.getScheme(),null,0,jarUri.getSchemeSpecificPart().replaceFirst("\\/\\/", ""),new URLStreamHandler() {
						
						@Override
						protected URLConnection openConnection(URL u) throws IOException {
							
							return new URLConnection(u){

								@Override
								public void connect() throws IOException {
								}

								@Override
								public InputStream getInputStream() throws IOException {								
									return bais;
								}
								
								
								
							};
						}
					}));
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return urls;
	}


	public void close() {
        for (final String uri : new HashSet<String>(this.registry.keySet())) {
            this.removeResource(uri);
        }
        this.open = false;
    }


    @Override
    public boolean isOpen() {
        return this.open;
    }


}
