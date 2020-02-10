package com.tibco.cep.studio.rms.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.ParentResource;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.util.StudioConfig;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;

public class RMSUtil {
	
	private static XiParser parser;
	
	static {
		parser = XiParserFactory.newInstance();
	}
	/**
	 * Write bytes present in the input stream to the specified outputstream.
	 * 
	 * @param inStream
	 * @param outStream
	 * @throws Exception
	 */
	public static void writeBytes(InputStream inStream, OutputStream outStream)
			throws Exception {
		IOUtils.writeBytes(inStream, outStream);
	}
	
	public static List<String> parseFolders(String folderName) {
		final List<String> folderStack = new ArrayList<String>();
		/*
		 * if ("/".equals(folderName)) { folderStack.add(folderName); return
		 * folderStack; }
		 */
		StringTokenizer tok = new StringTokenizer(folderName, "/");
		int counter = 0;
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			if (token != null && !token.trim().equals("")) {
				folderStack.add(counter++, token);
			}
		}
		return folderStack;
	}

	/**
	 * it returns resource at a given path ,
	 * limitation : the resource should have parent as folder , other wise result will not be right
	 * @param pr
	 * @param path
	 * @return
	 */
	public static AbstractResource getResourceAtPath(final ParentResource pr,
			final String path, final ResourceType resourceType) {
		if (path == null || pr == null) {
			return null;
		}
		// Dirty logic. Again cant help
		List<String> resources = parseFolders(path);
		return getResourceAtPath(pr, resources, resourceType);
	}

	private static AbstractResource getResourceAtPath(
			final AbstractResource ar, final List<String> resources,
			final ResourceType resourceType) {
		AbstractResource res = ar;
		int count = 1;
		for (String resource : resources) {
			if (res instanceof ParentResource) {
				ParentResource parent = (ParentResource) res;
				ResourceType resType = null;
				if (count != resources.size()) {
					resType = ResourceType.FOLDER;
				} else {
					resType = resourceType;
				}
				res = parent.findChild(resource, resType);
				count++;
			}

		}
		return res;
	}

	public static String getParentPath(Stack<String> parentStack) {
		StringBuilder sb = new StringBuilder("/");
		if (parentStack == null) {
			return sb.toString();
		}
		while (!parentStack.isEmpty()) {
			String top = parentStack.pop();
			sb.append(top);
			sb.append("/");
		}
		return sb.toString();
	}

	/**
	 * @param baseURL
	 * @param destProperty
	 * @param defaultDest
	 * @return
	 */
	public static String createRequestURL(final String baseURL,
			final String destProperty, final String defaultDest) {
		StringBuilder sb = new StringBuilder(baseURL);
		sb.append(defaultDest);
		return sb.toString();
	}

	
	public static Document getDocFromSource(final InputSource inputSource,
			final boolean namespaceAware) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		docFactory.setNamespaceAware(namespaceAware);
		Document doc = docFactory.newDocumentBuilder().parse(inputSource);
		return doc;
	}
	
	public static XiNode getXiNodeFromSource(final InputSource inputSource) throws Exception {
		return parser.parse(inputSource);
	}

	/**
	 * Creates a zip file out of a folder, and its contents Zips the contents
	 * recursively.
	 * 
	 * @param srcFolder:
	 *            The folder to zip
	 * @param destZipFile
	 *            The zip file to create
	 */
	public static void zipFolder(final String srcFolder,
			final String destZipFile) {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		try {
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);
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
		} else {
			try {
				fileWriter.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			File destFile = new File(destZipFile);
			if (destFile.exists()) {
				// remove it
				boolean b1 = destFile.delete();
				System.out.println(b1);
			}
		}
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
	private static void addToZip(final String path, final String srcFile,
			final ZipOutputStream zip) {
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			try {
				FileInputStream in = new FileInputStream(srcFile);
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
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
	 * add the srcFolder to the zip stream.
	 * 
	 * @param path
	 *            String, the relative path with the root archive.
	 * @param srcFolder
	 *            String, the absolute path of the file to add
	 * @param zip
	 *            ZipOutputStram, the stream to use to write the given file.
	 */
	static private boolean addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) {
		File folder = new File(srcFolder);
		boolean flag = false;
		if (folder.exists()) {
			String fileListe[] = folder.list();
			try {
				for (int i = 0; i < fileListe.length; i++) {
					if (null == path) {
						addToZip("", srcFolder + "/" + fileListe[i], zip);
					} else {
						addToZip(path + "/" + folder.getName(), srcFolder + "/"
								+ fileListe[i], zip);
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

//	public static void main(String[] r) {
//		String sourceFolder = "E:/software/workingdir/ATTProject/data/TestData";
//		String zipFile = "E:/software/workingdir/ATTProject/data/TestData.zip";
//		zipFolder(sourceFolder, zipFile);
//	}

	/**
	 * Unzip bytes of zipped content, into the location specified by param.
	 * 
	 * @param testDataBytes:
	 *            Bytes of zipped data
	 * @param dpTargetLocation:
	 *            The directory to unzip contents
	 * @param localFileName:
	 *            The file name of the zip
	 * @param
	 * @throws Exception
	 */
	public static void unzipTestData(final byte[] testDataBytes,
			final String dpTargetLocation, final String localFileName,
			final String unzipFlderName) throws Exception {
		String zipLocation = dpTargetLocation + File.separatorChar
				+ localFileName;
		OutputStream outStream = new FileOutputStream(zipLocation);
		InputStream inStream = new ByteArrayInputStream(testDataBytes);
		RMSUtil.writeBytes(inStream, outStream);
		outStream.close();
		try {
			BufferedOutputStream destination = null;
			FileInputStream fis = new FileInputStream(zipLocation);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			ZipEntry entry;
			int SIZE = 1024;
			String unZipLocation = dpTargetLocation + File.separatorChar
					+ unzipFlderName;
			File unzipDir = new File(unZipLocation);
			if (!unzipDir.exists()) {
				unzipDir.mkdirs();
			}
			while ((entry = zis.getNextEntry()) != null) {
				int count;
				byte data[] = new byte[SIZE];
				// write the files to the disk
				File fileEntry = new File(unZipLocation + File.separatorChar
						+ entry.getName());
				if (!fileEntry.exists()) {
					// Get its parent
					File parent = fileEntry.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
				}
				FileOutputStream fos = new FileOutputStream(fileEntry);
				destination = new BufferedOutputStream(fos, SIZE);
				while ((count = zis.read(data, 0, SIZE)) != -1) {
					destination.write(data, 0, count);
				}
				destination.flush();
				destination.close();
			}
			zis.close();
			/*
			 * File remove = new File(zipLocation); if (remove.exists()) {
			 * //Delete it remove.delete(); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a file from local filesystem, and convert to byte array.
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(final File f) throws IOException {
		return IOUtils.readBytes(f.getAbsolutePath());
	}

	/**
	 * Build the <b>Base URL</b> for the RMS server
	 * 
	 * @return the URL
	 */
	public static String buildRMSURL() {
		String cachedURL = LocationStore.newInstance().getCachedLoginURL();
		if (cachedURL != null) {
			return cachedURL;
		}
		StringBuilder sb = new StringBuilder();
		String rms_Host = StudioConfig.getInstance().getProperty("rms.host");
		final String HTTP_PROTOCOL = "http://";
		sb.append(HTTP_PROTOCOL);
		if (rms_Host == null) {
			rms_Host = "localhost";
		}
		sb.append(rms_Host.trim());
		String rms_Port = StudioConfig.getInstance().getProperty("rms.port");
		if (rms_Port == null) {
			rms_Port = "5000";
		}
		sb.append(':');
		sb.append(rms_Port.trim());
		String rms_Context_Root = StudioConfig.getInstance().getProperty("rms.context.root");
		if (rms_Context_Root == null) {
			rms_Context_Root = "/Transports/Channels/AMS_CH_ArtifactCommunicationHTTPChannel/";
		}
		if (!(rms_Context_Root.charAt(0) == '/')) {
			sb.append(File.separatorChar);
		}
		if (!(rms_Context_Root.charAt(rms_Context_Root.length() - 1) == '/')) {
			sb.append(File.separatorChar);
		}
		sb.append(rms_Context_Root.trim());
		return sb.toString();
	}

	/**
	 * Deserialize String representation of an {@code EObject}.
	 * 
	 * @param <T>
	 * @param serialized
	 * @return the deserialized <b>EObject</b>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T deserializeEObjectFromString(
			String serialized, String namespace, EPackage INSTANCE)
			throws Exception {
		if (serialized == null || serialized.length() == 0) {
			throw new IllegalArgumentException("Argument passed invalid");
		}
		byte[] bytes = serialized.getBytes("UTF-8");
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put(namespace, INSTANCE);
		XMLResource resource = new XMLResourceImpl();
		resource.load(bis, null);
		EObject eobject = resource.getContents().get(0);
		return (T) eobject;
	}

	/**
	 * This method takes a folder name, and builds a folder hierarchy under the
	 * given {@code ParentResource} object.
	 * <p>
	 * <code>
	 * ParentResource pr = ....;
	 * buildFolderStructure(pr, "/Folder");
	 * </code>
	 * </p>
	 * 
	 * @param pr
	 * @param folderName:
	 *            The fully qualified folder path
	 * @return the <tt>ParentResource</tt> matching the newly created folder.
	 */
	public static ParentResource buildFolderStructure(final ParentResource pr,
			final String folderName) {
		ParentResource child = OntologyFactory.eINSTANCE.createFolder();
		child.setName(folderName);
		ParentResource parent = pr;
		// If the parent already contains a child
		// with this name, reuse it, else create a
		// new one
		ParentResource matchingChild = (ParentResource) parent.findChild(
				folderName, ResourceType.FOLDER);
		child = (matchingChild != null) ? (ParentResource) matchingChild
				: child;
		parent.addChild(child);
		return child;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getLoggedInUserRolesCumulative() {
		List<Role> userRoles = AuthTokenUtils.getLoggedInUserRoles();
		StringBuilder cumulativeRoleString = new StringBuilder();
		for (Role userRole : userRoles) {
			String roleName = userRole.getName();
			cumulativeRoleString.append(roleName);
			cumulativeRoleString.append(";");
		}
		return cumulativeRoleString.toString();
	}

	/**
	 * Serialize an {@code EObject} and return its <tt>String</tt>
	 * implementation
	 * 
	 * @param eObject
	 * @return string implementation of the <tt>EObject</tt>
	 * @throws IOException
	 */
	public static String serializeEObject(final EObject eObject)
			throws IOException {
		XMLResource resource = new XMLResourceImpl();
		resource.getContents().add(eObject);
		Writer writer = new StringWriter();
		BufferedWriter buf = new BufferedWriter(writer);
		resource.save(buf, null);
		return writer.toString();
	}
	
	/**
	 * Ensure the logged-in user has access to generate deployable
	 * @param projectName project name to check access for
	 * @return boolean true if user has access, false otherwise 
	 */
	public static boolean ensureGenerateDeployableAccess(String projectName) {
		List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
		IAction genDeployPrjAction = ActionsFactory.getAction(com.tibco.cep.security.authz.utils.ResourceType.PROJECT, "gen_deploy");
		Permit permit = SecurityUtil.ensurePermission(projectName, null, com.tibco.cep.security.authz.utils.ResourceType.PROJECT, 
															roles, genDeployPrjAction, PermissionType.BERESOURCE);
		return Permit.ALLOW == permit;
	}
}