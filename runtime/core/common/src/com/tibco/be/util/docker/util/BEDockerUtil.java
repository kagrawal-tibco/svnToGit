package com.tibco.be.util.docker.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.xml.sax.InputSource;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.Constants.DD;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.VFileHelper;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;



public class BEDockerUtil {

	public static String BE_DOCKERFILE_TEMPLATE					 = "Dockerfile.template";
	public static String BE_DOCKERFILE							 = "Dockerfile";


	//Tokens
	public static String BE_DOCKER_TOKEN_APP_HOME				 = "APP_HOME";
	public static String BE_DOCKER_TOKEN_TIBCO_HOME				 = "TIBCO_HOME";
	public static String BE_DOCKER_TOKEN_CDD					 = "CDD_FILE";
	public static String BE_DOCKER_TOKEN_EAR					 = "EAR_FILE";
	public static String BE_DOCKER_TOKEN_PRODUCT_IMAGE			 = "PRODUCT_IMAGE_NAME";
	public static String BE_DOCKER_TOKEN_MAINTAINER				 = "MAINTAINER";
	public static String BE_DOCKER_TOKEN_EMAIL					 = "EMAIL";

	public static String BE_DOCKER_ENV_JDBC_DRIVER		 		 = "BACKINGSTORE_JDBC_DRIVER";
	public static String BE_DOCKER_ENV_JDBC_URL		 			 = "BACKINGSTORE_JDBC_URL";
	public static String BE_DOCKER_ENV_JDBC_USERNAME		 	 = "BACKINGSTORE_JDBC_USERNAME";
	public static String BE_DOCKER_ENV_JDBC_PASSWORD		 	 = "BACKINGSTORE_JDBC_PASSWORD";
	public static String BE_DOCKER_ENV_JDBC_POOL_SIZE		 	 = "BACKINGSTORE_JDBC_POOL_SIZE";
	public static String BE_DOCKER_ENV_JDBC_LOGIN_TIMEOUT		 = "BACKINGSTORE_JDBC_LOGIN_TIMEOUT";
	public static String BE_DOCKER_ENV_JDBC_USE_SSL		 		 = "BACKINGSTORE_JDBC_USE_SSL";


	public static String BE_DOCKER_CNST_GLOBAL_VARIABLES		 = "Global Variables";
	public static String BE_DOCKER_CNST_SHARED_ALL				 = "Shared All";
	public static String BE_DOCKER_CNST_EXT_LIB_PATH			 = "/opt/tibco/be/ext";
	public static String BE_DOCKER_CNST_EXT_FILE_PATH			 = "/opt/tibco/be/files";
	public static String BE_DOCKER_CNST_TEMP_CDD 				 = "temp_cdd_file";
	public static String BE_DOCKER_CNST_FILES_FOLDER			 = "files";

	public static Pattern BE_DOCKER_CNST_GLOBAL_VARIABLE_PATTERN = Pattern.compile("%%(.*?)%%");
	public static Pattern BE_DOCKER_CNST_CDD_GV_PATTERN			 = Pattern.compile("tibco.clientVar.(.*?)");
	public static Pattern BE_DOCKER_CNST_WIN_PATH_PATTERN		 = Pattern.compile("(.*?:)");

	private static final XiParser PARSER = XiParserFactory.newInstance();
	private static final int BUFFER=2048;
	


	public static File[] getMatchingFiles(String workingDirectory,final String pattern){
		File directoryWithFiles= new File(workingDirectory);

		return directoryWithFiles.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename){  
				return filename.matches(pattern); 
			}
		} );
	}


	public static String getExpression(String variable) {
		return "${" + variable + "}";
	}

	public static StringBuilder readFile(File file){
		BufferedReader reader=null;
		StringBuilder builder = new StringBuilder("");
		try {
			reader = new BufferedReader(new FileReader(file));

			String line = "";
			String text = "";
			while((line = reader.readLine()) != null)
			{
				//text += line + "\r\n";
				builder.append(line + "\r\n");
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to find file : "+file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to read file :"+file.getAbsolutePath());
			e.printStackTrace();
		}
		finally{
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					//ignore
				}
			file=null;
		}
		return builder;
	}


	public static StringBuilder getDockerFileText(String imageName, String maintainer,
			String email, String cdd, String ear, String appHome, String tibcoHome) {

		URL path = BEDockerUtil.class.getResource(BE_DOCKERFILE_TEMPLATE);
		InputStream fis=BEDockerUtil.class.getResourceAsStream(BE_DOCKERFILE_TEMPLATE);

		Scanner s = new Scanner(fis).useDelimiter("\\A");
		String result = s.hasNext() ? s.next() : "";
		StringBuilder builder = new StringBuilder(result);

		if(builder!=null){
			replace(getExpression(BE_DOCKER_TOKEN_APP_HOME),appHome,builder);
			replace(getExpression(BE_DOCKER_TOKEN_TIBCO_HOME), tibcoHome, builder);
			replace(getExpression(BE_DOCKER_TOKEN_CDD), cdd,builder);
			replace(getExpression(BE_DOCKER_TOKEN_EAR), ear,builder);
			replace(getExpression(BE_DOCKER_TOKEN_PRODUCT_IMAGE), imageName,builder);
			replace(getExpression(BE_DOCKER_TOKEN_MAINTAINER), maintainer,builder);
			replace(getExpression(BE_DOCKER_TOKEN_EMAIL), email,builder);
		}
		return builder;
	}

	public static void replace( String target, String replacement, 
			StringBuilder builder ) { 
		int indexOfTarget = -1;
		while( ( indexOfTarget = builder.indexOf( target ) ) >= 0 ) { 
			builder.replace( indexOfTarget, indexOfTarget + target.length() , replacement );
		}
	}


	public static HashMap<String, String> getGlobalVariables(String earPath)
			throws Exception {

		HashMap<String,String> gvMap=new HashMap<String,String>();
		VFileFactory vfileFactory = VFileHelper.createVFileFactory(earPath, null);

		// Get the GV resource VFile
		VFile gvVFile = scanEARArchiveForGVResource(vfileFactory);
		// Get all the Global variables
		Collection<GlobalVariableDescriptor> allGlobalVariables = getGlobalVariables(BE_DOCKER_CNST_GLOBAL_VARIABLES,(VFileStream) gvVFile);

		for(GlobalVariableDescriptor gv:allGlobalVariables){
			gvMap.put(gv.getFullName(), gv.getValueAsString());
		}

		return gvMap;

	}

	private static Collection<GlobalVariableDescriptor> getGlobalVariables(String headerName, VFileStream gvFileStream)
			throws Exception {
		final XiNode doc = PARSER.parse(new InputSource(gvFileStream.getInputStream()));
		final XiNode root = doc.getFirstChild();
		Map<String, GlobalVariableDescriptor> globalVariablesMap = new LinkedHashMap<String, GlobalVariableDescriptor>();
		for (Iterator<?> itr = XiChild.getIterator(root, Constants.DD.XNames.NAME_VALUE_PAIRS); itr.hasNext();) {
			final XiNode node = (XiNode) itr.next();
			final String name = XiChild.getChild(node, Constants.DD.XNames.NAME).getStringValue();
			if (headerName.equalsIgnoreCase(name)) {
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR, globalVariablesMap);
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR_INTEGER, globalVariablesMap);
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR_BOOLEAN, globalVariablesMap);
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR_PASSWORD, globalVariablesMap);
			}
		}
		return globalVariablesMap.values();
	}

	public static String substituteGlobalVariables(HashMap<String,String> gvMap,String value){
		if(value!=null){
			final Matcher matcher = BEDockerUtil.BE_DOCKER_CNST_GLOBAL_VARIABLE_PATTERN.matcher(value);
			if(matcher.find()) {
				String gvName=matcher.group(1);
				String gvValue=gvMap.get(gvName);
				if(gvValue!=null&&!gvValue.isEmpty()){
					return gvValue;
				}
			}
			return value;
		}
		return "";
	}
	
	public static boolean isGlobalVariable(String name){
		if(name!=null){
			final Matcher matcher = BEDockerUtil.BE_DOCKER_CNST_GLOBAL_VARIABLE_PATTERN.matcher(name);
			if(matcher.find()) {
				return true;
			}
		}
		return false;
	}
	
	public static String getGlobalVariableName(String name){
		if(name!=null){
			final Matcher matcher = BEDockerUtil.BE_DOCKER_CNST_GLOBAL_VARIABLE_PATTERN.matcher(name);
			if(matcher.find()) {
				return matcher.group(1);
			}
		}
		return "";
	}

	private static VFile scanEARArchiveForGVResource(VFileFactory vfileFactory) throws ObjectRepoException {
		VFileDirectory rootDirectory = vfileFactory.getRootDirectory();
		// Get the VFile for GV File (TIBCO.xml)
		VFile gvVFile = null;
		for (Iterator<?> itr = rootDirectory.getChildren(); itr.hasNext();) {
			VFile vff = (VFile) itr.next();
			if (vff instanceof VFileStream) {
				String fileURI = vff.getFullURI().toLowerCase();
				if (fileURI.endsWith("tibco.xml")) {
					gvVFile = vff;
					break;
				}
			}
		}
		return gvVFile;
	}


	public static Resource scanSARArchiveForSharedResource(File ear,String sharedResource) throws ObjectRepoException, IOException {

		System.setProperty("org.eclipse.emf.common.util.URI.archiveSchemes", "ear bar sar");
		String resourcePath = sharedResource.startsWith("/") ? sharedResource.substring(1):sharedResource;

		String path = "archive:jar:"+URI.createURI(ear.getAbsolutePath()) + "!/Shared%20Archive.sar!/"+resourcePath;
		ResourceSet resourceSet = new ResourceSetImpl();
		final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
		registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
		resourceSet.setResourceFactoryRegistry(registry);
		Resource resource = resourceSet.createResource(URI.createURI(path));
		Map<Object,Object> options = new HashMap<Object,Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		resource.load(options);


		/*	VFileDirectory rootDirectory = ear.getRootDirectory();
		VFile gvVFile = null;
		for (Iterator<?> i = rootDirectory.getChildren(); i.hasNext();) {
			VFile vff = (VFile) i.next();
			if (vff instanceof VFileStream) {
				String uri = vff.getFullURI().toLowerCase();
				if (uri.endsWith(".sar")) {
					gvVFile = scanSARArchiveResource(vff,sharedResource);
					break;
				}
			}
		}*/
		return resource;
	}

	public static VFile scanSARArchiveResource(VFile barArchiveResourceVFile,String sharedResource) throws ObjectRepoException {
		ZipVFileFactory vFileFactory = new ZipVFileFactory(
				new ZipInputStream(((VFileStream) barArchiveResourceVFile).getInputStream()));
		VFileDirectory rootDirectory = vFileFactory.getRootDirectory();
		VFile file = null;

		for (Iterator<?> itr = rootDirectory.getChildren(); itr.hasNext();) {
			VFile vff = (VFile) itr.next();
			System.out.println(vff.getFullURI());
			if (vff instanceof VFileStream) {
				String fileURI = vff.getFullURI().toLowerCase();
				if (fileURI.indexOf(sharedResource)>0) {
					file = vff;
					break;
				}
			}
			else if(vff instanceof VFileDirectory){
				file=scanSARArchiveResource((VFile) itr.next(),sharedResource);
				if(file!=null)
					return file;
			}

		}
		return file;
	}


	private static void collectGlobalVariables(XiNode globalVariables, ExpandedName en,
			Map<String, GlobalVariableDescriptor> globalVariablesMap) {
		for (Iterator<?> it = XiChild.getIterator(globalVariables, en); it.hasNext();) {
			final XiNode gvNode = (XiNode) it.next();
			String name = XiChild.getString(gvNode, DD.XNames.NAME);
			String type = XiChild.getString(gvNode, DD.XNames.TYPE);
			if (type == null) {
				if (DD.XNames.NAME_VALUE_PAIR == en) {
					type = "String";
				} else if (DD.XNames.NAME_VALUE_PAIR_PASSWORD == en) {
					type = "Password";
				} else if (DD.XNames.NAME_VALUE_PAIR_BOOLEAN == en) {
					type = "Boolean";
				} else if (DD.XNames.NAME_VALUE_PAIR_INTEGER == en) {
					type = "Integer";
				}
			}
			final String value = XiChild.getString(gvNode, DD.XNames.VALUE);
			final boolean deploymentSettable = XiChild.getBoolean(gvNode, DD.XNames.DEPLOYMENT_SETTABLE, true);
			final boolean serviceSettable = XiChild.getBoolean(gvNode, DD.XNames.SERVICE_SETTABLE, true);
			final long modTime = XiChild.getLong(gvNode, DD.XNames.MOD_TIME, 0);
			final String description = "";
			final String constraint = "";

			int idx = name.lastIndexOf("/");
			String actualPath = "";
			if (idx != -1) {
				actualPath = name.substring(0, idx + 1);
				name = name.substring(idx + 1);
			}
			final GlobalVariableDescriptor gv = new GlobalVariableDescriptor(name, actualPath, value, type,
					deploymentSettable, serviceSettable, modTime, description, constraint);

			String key = gv.getFullName();
			if (globalVariablesMap.containsKey(key)) {
				gv.setOverridden(true);
			}
			globalVariablesMap.put(key, gv);
		}
	}


	public static File[] sortFiles(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int n1 = getIndex(o1.getName());
				int n2 = getIndex(o2.getName());
				return n1 - n2;
			}

			private int getIndex(String name) {
				int i = 0;
				try {
					String number = name.substring((name.indexOf('.')+1), name.length());
					i = Integer.parseInt(number);
				} catch(Exception e) {
					i = 0;
				}
				return i;
			}
		});
		return files;
	}

	public static void unzip(File source, File destination) throws IOException 
	{
		System.out.println("Unzipping - " + source.getName());
		int BUFFER = 40096;

		ZipFile zip = new ZipFile(source);
		try{
			destination.getParentFile().mkdirs();
			Enumeration zipFileEntries = zip.entries();

			// Process each entry
			while (zipFileEntries.hasMoreElements())
			{
				// grab a zip file entry
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(destination, currentEntry);
				//destFile = new File(newPath, destFile.getName());
				File destinationParent = destFile.getParentFile();

				// create the parent directory structure if needed
				destinationParent.mkdirs();

				if (!entry.isDirectory())
				{
					BufferedInputStream is = null;
					FileOutputStream fos = null;
					BufferedOutputStream dest = null;
					try{
						is = new BufferedInputStream(zip.getInputStream(entry));
						int currentByte;
						// establish buffer for writing file
						byte data[] = new byte[BUFFER];

						// write the current file to disk
						fos = new FileOutputStream(destFile);
						dest = new BufferedOutputStream(fos, BUFFER);

						// read and write until last byte is encountered
						while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
							dest.write(data, 0, currentByte);
						}
					} catch (Exception e){
						System.out.println("unable to extract entry:" + entry.getName());
						throw e;
					} finally{
						if (dest != null){
							dest.close();
						}
						if (fos != null){
							fos.close();
						}
						if (is != null){
							is.close();
						}
					}
				}else{
					//Create directory
					destFile.mkdirs();
				}

				if (currentEntry.endsWith(".sar"))
				{
					// found a sar file, try to extract into Shared Archive location
					unzip(destFile, new File(destinationParent.getAbsolutePath()+File.separator+"Shared Archive"));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Failed to successfully unzip:" + source.getName());
		} finally {
			zip.close();
		}
		//System.out.println("Done Unzipping:" + source.getName());
	}

	public static void zipArchive(File directory, File zipfile) throws IOException {
		java.net.URI base = directory.toURI();
		Deque<File> queue = new LinkedList<File>();
		queue.push(directory);
		OutputStream out = new FileOutputStream(zipfile);
		Closeable res = out;
		try {
			ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;
			while (!queue.isEmpty()) {
				directory = queue.pop();
				for (File kid : directory.listFiles()) {
					String name = base.relativize(kid.toURI()).getPath();
					if (kid.isDirectory()) {
						queue.push(kid);
						name = name.endsWith("/") ? name : name + "/";
						zout.putNextEntry(new ZipEntry(name));
					} else {
						zout.putNextEntry(new ZipEntry(name));
						copy(kid, zout);
						zout.closeEntry();
					}
				}
			}
		} finally {
			res.close();
		}
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			if (readCount < 0) {
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

	private static void copy(File file, OutputStream out) throws IOException {
		InputStream in = new FileInputStream(file);
		try {
			copy(in, out);
		} finally {
			in.close();
		}
	}

	private static void copy(InputStream in, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		try {
			copy(in, out);
		} finally {
			out.close();
		}
	}


	public static String getFileName(File ear) {
		String name=ear.getName();
		String nameWithoutExtension=name;
		if(name.lastIndexOf(".")>0)
			nameWithoutExtension=name.substring(0,name.lastIndexOf("."));

		return nameWithoutExtension;
	}

	public static void deleteDirectory(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				deleteDirectory(c);
		}
		if (!f.delete())
			throw new IOException("Failed to delete file: " + f);
	}


	public static byte[] getResourceAsBytes(String path) {
		ByteArrayOutputStream bos= new ByteArrayOutputStream();
		FileInputStream fileStream=null;
		try {
			fileStream =new FileInputStream(path);

			byte[] b = new byte[BUFFER];
			int c;
			while ((c = fileStream.read(b)) != -1) {
				bos.write(b, 0, c);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Shared Resource not found :"+path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception while reading Shared Resource:"+path);
			e.printStackTrace();
		}
		finally{
			try {
				fileStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bos.toByteArray();

	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}


	public static String transformPath(String absolutePath) {
		final Matcher matcher = BEDockerUtil.BE_DOCKER_CNST_WIN_PATH_PATTERN.matcher(absolutePath);
		
		if(matcher.find()) {
			absolutePath=matcher.replaceFirst("");
		}
		absolutePath=absolutePath.replace("\\","/");
		return absolutePath;
	}
	
	public static <T> T[] concatArrays(T[] first, T[] second) {
	  T[] result = Arrays.copyOf(first, first.length + second.length);
	  System.arraycopy(second, 0, result, first.length, second.length);
	  return result;
	}
}
