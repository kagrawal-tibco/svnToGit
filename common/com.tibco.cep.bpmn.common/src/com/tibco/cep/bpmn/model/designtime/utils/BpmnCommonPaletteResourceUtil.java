package com.tibco.cep.bpmn.model.designtime.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import com.tibco.cep.studio.common.palette.DocumentRoot;
import com.tibco.cep.studio.common.palette.PaletteModel;
import com.tibco.cep.studio.common.palette.PalettePackage;
import com.tibco.cep.studio.common.palette.util.PaletteResourceFactoryImpl;

public class BpmnCommonPaletteResourceUtil {
	
	public static final String DEFAULT_BEBPMNPALETTE = "default.beprocesspalette";
	public static final String PALETTE_FILE_EXTN = "beprocesspalette";
	
	 public static PaletteModel loadDefaultProcesspalette() {
		try {
			List<URL> urlList = new LinkedList<URL>();
			Enumeration<URL> e = BpmnCommonPaletteResourceUtil.getDefaultBePaletteSysResEnumerationList();
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			e = BpmnCommonPaletteResourceUtil.getDefaultBePaletteResEnumerationList();
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			URL url = urlList.get(0);
			
			String urlPath = url.getPath().indexOf(".jar") == -1 ? url.getPath() : extractDefaultPaletteFile(url.getPath());
			URI uri = URI.createFileURI(urlPath);
			Resource resource = getResource(uri);
			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMIResource.OPTION_ENCODING, "UTF-8");
			resource.load(url.openStream(),options);
			DocumentRoot dr = (DocumentRoot) resource.getContents().get(0);
			PaletteModel model = dr.getModel();
			return model;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}	
	
	public static Resource getResource(URI uri) {
		Map<String, Object> packageRegistry = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		PalettePackage.eINSTANCE.setName(PalettePackage.eNAME);
		PalettePackage.eINSTANCE.setNsPrefix(PalettePackage.eNS_PREFIX);
		packageRegistry.put(PalettePackage.eNS_PREFIX, PalettePackage.eNS_URI);
		ResourceSet resourceSet = new ResourceSetImpl();

		// add file extension to registry
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				PALETTE_FILE_EXTN, new PaletteResourceFactoryImpl());

		Resource resource = resourceSet.createResource(uri);
		return resource;
	}
	
	@SuppressWarnings("static-access")
	public static Enumeration<URL> getDefaultBePaletteSysResEnumerationList(){
		Enumeration<URL> e=null;
		try {
			e = BpmnCommonPaletteResourceUtil.class.getClassLoader()
					.getSystemResources("/schema/" + DEFAULT_BEBPMNPALETTE);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	
	public static Enumeration<URL> getDefaultBePaletteResEnumerationList(){
		Enumeration<URL> e=null;
		try {
			e = BpmnCommonPaletteResourceUtil.class.getClassLoader().getResources("/schema/" + DEFAULT_BEBPMNPALETTE);
			if (!e.hasMoreElements()) {
				e = BpmnCommonPaletteResourceUtil.class.getClassLoader().getResources("schema/" + DEFAULT_BEBPMNPALETTE);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	
	private static String extractDefaultPaletteFile(String path) {
		String extractedFilePath = path;
		String jarPath = path.substring(path.indexOf("file:/") + 6, path.indexOf(".jar") + 4);
		
		String destinationPath = jarPath.substring(0, jarPath.lastIndexOf("/") + 1) + "schema/";
		File extractedFile = new File(destinationPath + DEFAULT_BEBPMNPALETTE);
		if (extractedFile.exists()) {
			return extractedFile.getAbsolutePath();
		}
		
		FileOutputStream fos = null;
		InputStream is = null;
		
		try {
			JarFile jar = new JarFile(jarPath);
			Enumeration<JarEntry> jarEntries = jar.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry file = (JarEntry) jarEntries.nextElement();
				if (file.getName().equals("schema/" + DEFAULT_BEBPMNPALETTE)) {
					File destinationFilePath = new File(destinationPath);
					destinationFilePath.mkdirs();
					
					File f = new File(destinationPath + DEFAULT_BEBPMNPALETTE);
					if (!f.exists()) {
						f.createNewFile();
					}
					
					is = jar.getInputStream(file);
				    fos = new FileOutputStream(f);
				    while (is.available() > 0) {
				    	fos.write(is.read());
				    }
				    extractedFilePath = f.getAbsolutePath();
				    break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) fos.close();
				if (is != null) is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return extractedFilePath;
	}
}
