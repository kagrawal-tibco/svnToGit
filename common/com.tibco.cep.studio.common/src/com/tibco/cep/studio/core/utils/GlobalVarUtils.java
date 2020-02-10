package com.tibco.cep.studio.core.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;

import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

public class GlobalVarUtils {
	
	/**
	 * @param provider
	 * @param config
	 * @throws IOException
	 * @throws Exception
	 */
	public static void loadProjectLibGlobalVariables(
			final GlobalVariablesProvider provider,
			StudioProjectConfiguration config) throws IOException, Exception {
		EList<ProjectLibraryEntry> referencedProjects = config.getProjectLibEntries();
		if (referencedProjects.size() > 0) {
			for (int i=referencedProjects.size()-1;i >= 0;i--) {
				ProjectLibraryEntry refProject =  referencedProjects.get(i);
				String libRef = refProject.getPath(refProject.isVar());
				System.out.println("Loading global vars: " + libRef);
				JarFile file = new JarFile(libRef);
				processProjectLibrary(file,provider);					
			}
		}
	}
	
	/**
	 * @param fJarFile
	 * @param provider
	 * @throws Exception
	 */
	public static void processProjectLibrary(JarFile fJarFile, GlobalVariablesProvider provider) throws Exception {
        if (fJarFile == null) {
            return;
        }
        Enumeration<JarEntry> entries = fJarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String fileName = entry.getName();
            if (fileName.lastIndexOf('\\') != -1) {
                fileName = fileName.substring(fileName.lastIndexOf('\\'));
            }
            if (fileName.lastIndexOf('/') != -1) {
                fileName = fileName.substring(fileName.lastIndexOf('/'));
            }
            if (fileName.lastIndexOf('.') != -1) {
                String extension = fileName.substring(fileName.lastIndexOf('.')+1);
                if (CommonIndexUtils.isGlobalVarType(extension) || 
                		provider.supportsResource(fileName)) {
                    processGlobalVarEntry(fJarFile,entry,provider);
                } 
            }
        }       
    }

	/**
	 * @param jarFile
	 * @param entry
	 * @param provider
	 * @throws Exception
	 */
	private static void processGlobalVarEntry(JarFile jarFile,JarEntry entry, GlobalVariablesProvider provider) throws Exception {
		byte[] contents = CommonIndexUtils.getJarEntryContents(jarFile, entry);
		com.tibco.cep.studio.common.util.Path p = new com.tibco.cep.studio.common.util.Path(entry.getName());
		String pathStr = p.removeFirstSegments(1).removeLastSegments(1).toString();
		if(!pathStr.isEmpty() && !pathStr.endsWith("/")){
			pathStr = pathStr+"/";
		}
		processFile(pathStr,new ByteArrayInputStream(contents),provider,jarFile.getName());		
		
	}
	
	
	/**
	 * @param path
	 * @param contents
	 * @param provider
	 * @throws Exception
	 */
	public static void processFile(String path,InputStream contents, GlobalVariablesProvider provider,String projectSource) throws Exception {
		XiNode node = XiParserFactory.newInstance().parse(new InputSource(contents));
		node = node.getFirstChild();
		provider.buildGlobalVariablesUsingRemoteRepository(path, node,projectSource);
	}

}
