package com.tibco.cep.studio.core.converter.sharedresource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.cep.repo.BEProject;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;

public class CustomSharedResourceProvider {
	private static final List<String> ignoreFileTypeList = new ArrayList<String>();
	private static final Set<String> ignoreFileNameList = new HashSet<String>();
	private static final List<String> inclusionList = new ArrayList<String>();
	private String designerProjectRoot;
	private String targetRootLocation;
	private BEProject project;
	
	static {
		ignoreFileTypeList.add("concept");
		ignoreFileTypeList.add("event");
		ignoreFileTypeList.add("rule");
		ignoreFileTypeList.add("rulefunction");
		ignoreFileTypeList.add("ruleset");
		ignoreFileTypeList.add("scorecard");
		ignoreFileTypeList.add("instance");
		ignoreFileTypeList.add("concept");
		ignoreFileTypeList.add("dm");
		ignoreFileTypeList.add("destination");
		ignoreFileTypeList.add("channel");
		ignoreFileTypeList.add(IndexUtils.EAR_EXTENSION);
		ignoreFileTypeList.add("statemachine");
		ignoreFileTypeList.add("standalonestatemachine");
		ignoreFileTypeList.add("standalonerule");
		ignoreFileTypeList.add("rulefunctionimpl");		
		
		//ignoreFileTypeList.add("substvar");
		//ignoreFileTypeList.add("id");
		//ignoreFileTypeList.add("sharedjdbc");
		//ignoreFileTypeList.add("sharedjmscon");
		//ignoreFileTypeList.add("sharedjmsapp");
		//ignoreFileTypeList.add("sharedhttp");	
		//ignoreFileTypeList.add("sharedjndiconfig");
		ignoreFileTypeList.add("folder");
		ignoreFileTypeList.add("dat");
		// ignoreFileTypeList.add("process");
		ignoreFileTypeList.add("time");
		ignoreFileTypeList.add("conceptview");
		
		// type which can be imported
		inclusionList.add("txt");
		inclusionList.add("xml");
		inclusionList.add("xsd");
		
		// specific file names to be ignored
		ignoreFileNameList.add(".project");		
	}
	
	public CustomSharedResourceProvider (BEProject project){
		this.project = project;
	}
	
	public void copyResources(File toBaseLocation){
		if (project == null || toBaseLocation == null) return;
		
		targetRootLocation = toBaseLocation.getAbsolutePath();
		 
		// recurse through whole project and get the list of files to be copied
		VFileFactory vfilefFactory = project.getVFileFactory();
		if (vfilefFactory == null) return;
		VFileDirectory dir = vfilefFactory.getRootDirectory();	
		designerProjectRoot = dir.getFullURI();
		// replace all segment separator to path separator
		if (designerProjectRoot.indexOf(SharedResourceElements.SEGMENT_SEPARATOR) != -1){
			designerProjectRoot = File.separatorChar != SharedResourceElements.SEGMENT_SEPARATOR ? 
									designerProjectRoot.replace(SharedResourceElements.SEGMENT_SEPARATOR, File.separatorChar): designerProjectRoot; 
		}
		File designerProjectRootFile = new File(designerProjectRoot);
		if (!designerProjectRootFile.exists()) return;		
		writeNonBEResources(designerProjectRootFile);
		
		
	}
	
	
	private void writeNonBEResources(File root){
	
			File[] children = root.listFiles(new java.io.FileFilter(){
				public boolean accept(File file) {
					if (file.isDirectory()){
						return true;
					}
					else {
						// get the extension
						String name = file.getName();
						if (shouldIgnoreFile(name)) {
							return false;
						}
						int index = name.lastIndexOf('.');
						if (index != -1){
							String ext = name.substring(index +1);
							if (shouldIgnoreExtension(ext)){
								return false;
							} else {
								return true;
							}
						} else {
							return false;
						}						
					}					
				}				
			});
			if (root.isDirectory() && children.length == 0) {
				String absPath = root.getAbsolutePath();
				String relPath = absPath.substring(designerProjectRoot.length());
				if (!relPath.startsWith(File.separator)){
					relPath = File.separator + relPath;
				}
				String toLocation = targetRootLocation + relPath;			
				File targetFile = new File (toLocation);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
					try {
						targetFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			for (File child : children){
				if (child.isDirectory()){
					writeNonBEResources(child);
				} else {
					String absPath = child.getAbsolutePath();
					String relPath = absPath.substring(designerProjectRoot.length());
					if (!relPath.startsWith(File.separator)){
						relPath = File.separator + relPath;
					}
					String toLocation = targetRootLocation + relPath;			
					File targetFile = new File (toLocation);
					File parentFile = targetFile.getParentFile();
					File rootFile = new File (parentFile.getAbsolutePath());
					if (rootFile != null && !rootFile.exists()){
						parentFile.mkdirs();
					}
					copy(child , targetFile);
				}
			}
		
	}
	
	public boolean shouldIgnoreFile(String name) {
		return ignoreFileNameList.contains(name);
	}

	public boolean shouldIgnoreExtension(String ext) {
		return ignoreFileTypeList.contains(ext);
	}

	private void copy(File sourceFile , File targetFile){
		if (sourceFile == null || targetFile == null) return ;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			inStream = new FileInputStream(sourceFile);
			outStream = new FileOutputStream(targetFile);
			writeBytes(inStream, outStream);
			
			
		} catch (Exception e){
			// TODO
		} finally {
			try {
				if (inStream != null){
					inStream.close();
				}
				if (outStream != null){
					outStream.close();
				}
			} catch (Exception e){
				//TODO
			}
		}
		
		
	}
	

	/**
	 * Write bytes present in the <tt>InputStream</tt> to the specified <tt>OutputStream</tt>.
	 * @param inStream
	 * @param outStream
	 * @throws Exception
	 */
	private static void writeBytes(InputStream inStream, OutputStream outStream)
			throws IOException {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		byte[] bufferArray = buf.array();
		WritableByteChannel wbc = Channels.newChannel(outStream);
		int count = inStream.read(bufferArray);		
		while (count >= 0 || buf.position() > 0) {
			int currentPosition = buf.position();
			if (count != -1){
				((Buffer)buf).position(currentPosition + count);
			}
			((Buffer)buf).flip();
			// Write the bytes to the channel
			wbc.write(buf);
			if (buf.hasRemaining()) {
				buf.compact();
			} else {
				((Buffer)buf).clear();
			}
			int off = buf.position();
			count = inStream.read(bufferArray,off,1024);
		}
		wbc.close();
		
	}
	
	

}
