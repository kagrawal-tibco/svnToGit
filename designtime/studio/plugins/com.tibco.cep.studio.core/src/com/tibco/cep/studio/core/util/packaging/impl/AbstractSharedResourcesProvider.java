package com.tibco.cep.studio.core.util.packaging.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;

import com.tibco.be.util.ByteArray;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.packaging.ISharedArchiveResourcesProvider;

abstract public class AbstractSharedResourcesProvider implements
		ISharedArchiveResourcesProvider {

	public List<File> getSharedResources(File rootFolder) throws CoreException,
			IOException {
					ArrayList<File> files = new ArrayList<File> ();
					File[] members = rootFolder.listFiles();
					for (File resource : members) {
						if(resource.isDirectory()) {
							if(!StudioCore.isIgnoredHint(resource)){
			//				if(!isMatch(EMFSarPackager.excludedFolderPatterns, resource.toURI().toString().toLowerCase())){
								getSharedResources(resource,files);
							}
						} else {
							if(!StudioCore.isIgnoredHint(resource)){
			//				if(!isMatch(EMFSarPackager.excludedFileExtensionPatterns, resource.toURI().toString().toLowerCase())){
								files.add(resource);
							}
						}
					}
					return files;
				}

	public void getSharedResources(File folder, ArrayList<File> files)
			throws CoreException, IOException {
					File[] members = folder.listFiles();
					for (File resource : members) {
						if(resource.isDirectory()) {
							if(!StudioCore.isIgnoredHint(resource)){
			//				if(!isMatch(EMFSarPackager.excludedFolderPatterns, resource.toURI().toString().toLowerCase())){
								getSharedResources(resource,files);
							}
						} else{
							if(!StudioCore.isIgnoredHint(resource)){
			//				if(!isMatch(EMFSarPackager.excludedFileExtensionPatterns, resource.toURI().toString().toLowerCase())){
								files.add(resource);
							}
						}
					}
					return ;
				}

	public byte[] getBytes(JarInputStream jis, ByteArray buf) throws Exception {
		for (int x = jis.read(); x != -1; x = jis.read()) {
			buf.add((byte) x);
		}
		return buf.getValue();
	
	}

	public void addResource(File resource, JarOutputStream jarOutputStream, List<String> entries,
			File projectPath) throws IOException, CoreException {
				final String entryName = resource.toURI().getPath().substring(projectPath.toURI().getPath().length());
				FileInputStream fis = new FileInputStream(resource);
				addResource(fis, entryName, jarOutputStream,entries);		
			}

	public void addResource(InputStream in, String entryName, JarOutputStream jarOutputStream,
			List<String> entries) throws IOException, CoreException {
				Collections.sort(entries);
				if(!(Collections.binarySearch(entries,entryName) >= 0)) {
					final JarEntry entry = new JarEntry(new ZipEntry(entryName));
					jarOutputStream.putNextEntry(entry);
					final byte[] bytes = new byte[BUFFER_SIZE];
					for (int bytesRead = in.read(bytes); bytesRead >= 0; bytesRead = in.read(bytes)) {
						jarOutputStream.write(bytes, 0, bytesRead);
					}//for
					in.close();
					jarOutputStream.closeEntry();
					entries.add(entryName);
				} else {
					StudioCorePlugin.log("Duplicate entry:"+entryName);
				}
				
			}

	public boolean isMatch(Set<Pattern> patternSet, String str) {
		boolean result = false;
		for (Pattern p: patternSet) {
			if(p.matcher(str).matches()) {
				result = true;
				break;
			}			
		}
		return result;
	}

	protected class SharedResourceURIHandler implements URIHandler{
		URI baseURI = null;
		private String projectName;
		private URI projURI;
		
		public SharedResourceURIHandler(String projectName) {
			this.projectName = projectName;
			String projPath = ResourcesPlugin.getWorkspace().getRoot().getProject(this.projectName).getFullPath().makeRelative().toPortableString();
			this.projURI = URI.createPlatformResourceURI(projPath,false);
		}
	
		@Override
		public void setBaseURI(URI uri) {
			baseURI = uri;
		}
	
		@Override
		public URI resolve(URI uri) {
			URI resUri = uri;
			return uri;
		}
	
		@Override
		public URI deresolve(URI uri) {
			URI resUri = URI.createURI(uri.path());
			resUri = resUri.appendFragment(uri.fragment());
			if(uri.isPlatformResource()) {
				IPath uriPath = new Path(uri.path());
				IPath projectUriPath = new Path(projURI.path());
				if(projectUriPath.isPrefixOf(uriPath)){
					uriPath = uriPath.removeFirstSegments(uriPath.matchingFirstSegments(projectUriPath)).makeAbsolute();
					return URI.createURI(uriPath.toPortableString()).appendFragment(uri.fragment());
				}
			}
			return resUri;
		}
	}

	

}
