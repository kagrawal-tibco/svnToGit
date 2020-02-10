/**
 * 
 */
package com.tibco.cep.studio.core.util.packaging.impl;

import static com.tibco.cep.studio.core.repo.RepoConstants.ZIP_EXTENSION;
import static com.tibco.cep.studio.core.util.packaging.PackagingConstants.EXCLUSIONS;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;

/**
 * Packages the project contents into the specified BAR.
 * @author aathalye
 *
 */
public class ProjectPackager {
	
	/**
	 * The BAR to pack this project in.
	 */
	private MutableServiceArchive bar;
	
	
	public ProjectPackager(final MutableServiceArchive bar) {
		this.bar = bar;
	}
	
	/**
	 * Package the entire project into the BAR.
	 * @param projectName -> Name of the project to pack
	 * @param projectPath -> Absolute location of the project on file system
	 * @throws Exception
	 */
	public void packageProject(String projectName, 
			                   String projectPath) throws Exception {
		File srcDir = new File(projectPath);
		//Check if directory exists
		if (!srcDir.exists()) {
			throw new IOException("Specified project path " + projectPath + " does not exist");
		}
		if (!srcDir.isDirectory()) {
			throw new IOException("Specified project path " + projectPath + " is not a directory");
		}
		//Zip up the contents
		InputStream zippedContents = zipProject(projectPath,projectName);
		
		if (zippedContents != null) {
			close(projectName, zippedContents);
		}
	}
	
	/**
	 * Creates a zip file out of a folder, and its contents Zips the contents
	 * recursively.
	 * @param projectName 
	 * 
	 * @param srcFolder:
	 *            The folder to zip
	 * 
	 */
	
	private InputStream zipProject(final String srcFolder, String projectName) {
		ZipOutputStream zip = null;
		//Write zipped content to a byte stream
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			zip = new ZipOutputStream(bos);
			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(projectName);
			StudioCorePlugin.getDesignerModelManager().saveIndex(index, true);
			String uri = IndexUtils.getIndexLocation(projectName);
			addToZip(".metadata"+File.separator+".plugins"+File.separator+StudioCorePlugin.PLUGIN_ID+File.separator+"index"
					, uri, zip);
//			dumper.dumpToJar(uri, "/", new FileFilter() {
//				@Override
//				public boolean accept(File pathname) {
//					return pathname.getName().equals(
//							ontology.getName() + ".idx");
//				}
//			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		boolean b = addFolderToZip(null, srcFolder, zip);
		if (b) {
			try {
				zip.flush();
				zip.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
		//Read the written byte array
		byte[] bytes = bos.toByteArray();
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * Write the content of srcFile in a new ZipEntry, named path+srcFile, of
	 * the zip stream. The result is that the srcFile will be in the path folder
	 * in the generated archive.
	 * 
	 * @param path
	 *            String, the relative path with the root archive.
	 * @param srcFile
	 *            String, the absolute path of the file to add
	 * @param zip
	 *            ZipOutputStram, the stream to use to write the given file.
	 */
	private void addToZip(final String path, 
			              final String srcFile,
			              final ZipOutputStream zip) {
		File file = new File(srcFile);
		if (file.isDirectory()) {
			//Filter files like .svn
			for (String exclusion : EXCLUSIONS) {
				if (srcFile.contains(exclusion)) {
					return;
				}
			}
			addFolderToZip(path, srcFile, zip);
		} else {
			
			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			try {
				FileInputStream in = new FileInputStream(srcFile);
				zip.putNextEntry(new ZipEntry(path + "/" + file.getName()));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
				in.close();

			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}

	}

	/**
	 * Add the srcFolder to the zip stream.
	 * 
	 * @param path
	 *            String, the relative path with the root archive.
	 * @param srcFolder
	 *            String, the absolute path of the file to add
	 * @param zip
	 *            ZipOutputStram, the stream to use to write the given file.
	 */
	private boolean addFolderToZip(String path, 
			                       String srcFolder,
			                       ZipOutputStream zip) {
		File folder = new File(srcFolder);
		boolean flag = false;
		if (folder.exists()) {
			String fileListe[] = folder.list();
			try {
				for (int loop = 0; loop < fileListe.length; loop++) {
					if (null == path) {
						addToZip(folder.getName(), srcFolder + "/" + fileListe[loop], zip);
					} else {
						addToZip(path + "/" + folder.getName(), srcFolder + "/"
								+ fileListe[loop], zip);
					}
				}
				flag = true;
			} catch (Exception ex) {
				ex.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @param projectName -> Name of the zip file
	 * @param zippedContents -> {@link InputStream} representing the zipped content of the project
	 * @throws Exception
	 */
	private void close(String projectName, InputStream zippedContents) throws Exception {
		final ZipVFileFactory vFactory = (ZipVFileFactory) bar.getVFileFactory();
        final VFileDirectory rootVDir = vFactory.getRootDirectory();
        final VFileStream file = rootVDir.createChild(projectName + ZIP_EXTENSION, null);
		
		
		try {
			file.update(zippedContents);
			zippedContents.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
