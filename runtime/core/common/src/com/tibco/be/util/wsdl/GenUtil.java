package com.tibco.be.util.wsdl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.cep.repo.mutable.MutableBEProject;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

public class GenUtil {

	public static void writeToVFile(MutableBEProject project, String path, byte[] value) throws Exception {
		try {
			VFileFactory vfac = project.getVFileFactory();
			VFile file = vfac.create(path, true);
			InputStream is = new ByteArrayInputStream(value);
			((VFileStream) file).update(is);
		} catch (Exception e) {
			throw new Exception(e.getMessage() + ". file path="+path);
		}
	}
	
	public static void writeToFile(String dirName, String fileName, String xsd)
	throws Exception, IOException {
		File dir = new File(dirName);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new Exception("Can't create schema folder: " + dirName);
			}
		}

		File file = new File(dir, fileName);
		FileWriter fw = new FileWriter(file);
		fw.write(xsd);
		fw.flush();
		fw.close();
	}
	
	public static XiNode getVFileAsXiNode(VFileStream fileStream) throws Exception {
		InputStream is = fileStream.getInputStream();
		XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(is)).getRootNode();
		if(rootNode != null && rootNode.getFirstChild() != null){
			XiNode repoNode = rootNode.getFirstChild();
			XiNode earNode = XiChild.getChild(repoNode, ExpandedName.makeName("enterpriseArchive"));
			return earNode;
		}
		return null;
	}
	
	/*
	 * Find Archive Files in Project root directory
	 * @param VFileDirectory root directory
	 * @param List the list in which VFileStram object is added
	 * @return void
	 */
	public static void findArchive(VFileDirectory dir, List<VFileStream> list)
	throws Exception {
		for (Iterator i = dir.getChildren(); i.hasNext();) {
			VFile vff = (VFile) i.next();
			if (vff instanceof VFileDirectory) {
				findArchive((VFileDirectory)vff, list);
			} else if (vff instanceof VFileStream) {
				if (vff.getURI().endsWith(".archive")) {
					list.add((VFileStream)vff);
				}
			}
		}
	}
	
	/*
	 * Find Archive Files in Project root directory
	 * @param VFileDirectory root directory
	 * @param List the list of VFileStram objects
	 * @return
	 */
	public static void findArchive(VFileDirectory dir, List<VFileStream> list, String archiveURI)
	throws Exception {
		if(archiveURI == null || "".equals(archiveURI)){
			throw new IllegalArgumentException("Archive URI can not be null");
		}
		for (Iterator i = dir.getChildren(); i.hasNext();) {
			VFile vff = (VFile) i.next();
			if (vff instanceof VFileDirectory) {
				findArchive((VFileDirectory)vff, list, archiveURI);
			} else if (vff instanceof VFileStream) {
				if (vff.getURI().endsWith(".archive")){
					String vFileURI = vff.getURI();
					if(archiveURI.startsWith("/")){
						archiveURI = archiveURI.substring(1);
					}
					if(!archiveURI.endsWith(".archive")){
						archiveURI = archiveURI.concat(".archive");
					}
					if(vFileURI.equals(archiveURI)){
						list.add((VFileStream)vff);
					}
				}
			}
		}
	}
}
