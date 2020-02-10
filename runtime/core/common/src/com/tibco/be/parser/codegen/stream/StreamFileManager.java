package com.tibco.be.parser.codegen.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import com.tibco.cep.runtime.service.loader.DynamicLoader;


public class StreamFileManager extends
		ForwardingJavaFileManager<JavaFileManager> {
	
	
	
	private final Map<URI, JavaFileObject> fileObjects = new HashMap<URI, JavaFileObject>();
	
	private RootFolderLocation root;
	
	//private StreamClassLoaderImpl classloader;

    private ClassLoader classloader;

	private AbstractStreamGenerator streamGenerator;

	/**
	 * @param fileManager
	 * @param clazzloader
	 * @param sg TODO
	 */
	public StreamFileManager(JavaFileManager fileManager,
			ClassLoader clazzloader, AbstractStreamGenerator sg) {
		super(fileManager);
		//this.classloader = new StreamClassLoaderImpl(clazzloader);
        this.classloader = clazzloader;
		this.streamGenerator = sg;
	}

	/**
	 * @param source
	 * @param target
	 * @throws IOException
	 */
//	private void add(AbstractResource source, JarOutputStream target)
//			throws IOException {
//		if (source.isFolder()) {
//			String name = source.getPath().replace("\\", "/");
//			name = source.getPath().replace("//", "/");
//			if (!name.isEmpty()) {
//				if (!name.endsWith("/"))
//					name += "/";
//				JarEntry entry = new JarEntry(name);
//				// entry.setTime(source.lastModified());
//				target.putNextEntry(entry);
//				target.closeEntry();
//			}
//			for (AbstractResource nestedFile : ((JavaFolderLocation) source)
//					.files())
//				add(nestedFile, target);
//			return;
//		}
//		String name = source.getPath().replace("\\", "/");
//		name = source.getPath().replace("//", "/");
//		JarEntry entry = new JarEntry(name);
//		// entry.setTime(source.lastModified());
//		target.putNextEntry(entry);
//		final InputStream in = ((JavaFileLocation) source).openInputStream();
//		final byte[] bytes = new byte[2048];
//		for (int bytesRead = in.read(bytes); bytesRead >= 0; bytesRead = in
//				.read(bytes)) {
//			target.write(bytes, 0, bytesRead);
//		}// for
//		target.closeEntry();
//
//	}

	/**
	 * @param jos
	 * @param packageName
	 * @param folders
	 * @throws IOException
	 */
	private void createPackageFolders(JarOutputStream jos, String packageName,
			Set<String> folders) throws IOException {
		String[] parts = packageName.split("/");
		String path = "";
		if (packageName.isEmpty()) {
			return;
		}
		for (String s : parts) {
			path = path.isEmpty() ? s : path + "/" + s;
			String pathEntry = null;
			if (!path.endsWith("/"))
				pathEntry = path + "/";

			if (!folders.contains(pathEntry)) {
				folders.add(pathEntry);
				JarEntry entry = new JarEntry(pathEntry);
				jos.putNextEntry(entry);
				jos.closeEntry();
			}
		}

	}

	/**
	 * @param parent
	 * @param folderPath
	 *            ("a/b/c")
	 * @return
	 * @throws IOException
	 */
	private JavaFolderLocation getAddPackageFolder(JavaFolderLocation parent,
			String folderPath) throws IOException {
		String[] parts = folderPath.split(AbstractResource.PATH_SEPARATOR);
		JavaFolderLocation pFolder = parent;
		for (String p : parts) {
			boolean found = false;
			for (AbstractResource f : pFolder.files()) {
				if (f.isFolder() && f.getName().equals(p)) {
					found = true;
					pFolder = (JavaFolderLocation) f;
					break;
				}
			}
			if (!found) {
				if (p.isEmpty()) {
					break;
				}
				pFolder = pFolder.addFolder(p);
			}
		}
		return pFolder;
	}

	public ClassLoader getClassLoader() {
		return this.classloader;
	}

	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location) {
		return this.classloader;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public RootFolderLocation getCompiledFolder() throws IOException {
		RootFolderLocation root = new RootFolderLocation("", this);
		List<Map.Entry<URI, JavaFileObject>> entries = getFileEntries(Kind
				.values());
		for (Entry<URI, JavaFileObject> entry : entries) {
			URI uri = entry.getKey();
			String[] parts = uri.toString().split(
					AbstractResource.PATH_SEPARATOR);
			String packageName = parts[1].replaceAll("\\.",
					AbstractResource.PATH_SEPARATOR);
			JavaFolderLocation folder = getAddPackageFolder(root, packageName);
			folder.addFile(entry.getValue());
		}
		return root;
	}

	/**
	 * @param kinds
	 * @return
	 */
	public List<Map.Entry<URI, JavaFileObject>> getFileEntries(Kind[] kinds) {
		List<Map.Entry<URI, JavaFileObject>> files = new ArrayList<Map.Entry<URI, JavaFileObject>>();
		for (Entry<URI, JavaFileObject> entry : fileObjects.entrySet()) {
			if(entry.getValue() instanceof JavaFileObjectImpl) {
				JavaFileObjectImpl fObj = (JavaFileObjectImpl) entry.getValue();
				if(fObj.isLibClass())
					continue;
			}
			if (Arrays.asList(kinds).contains(entry.getValue().getKind())) {
				files.add(entry);
			}
		}
		Collections.sort(files,
				new Comparator<Map.Entry<URI, JavaFileObject>>() {
					@Override
					public int compare(Entry<URI, JavaFileObject> o1,
							Entry<URI, JavaFileObject> o2) {
						String[] sp1 = o1.getKey().toString().split("/");
						String[] sp2 = o2.getKey().toString().split("/");
						if (sp1[1] == null) {
							sp1[1] = "";
						}
						if (sp2[1] == null) {
							sp2[1] = "";
						}
						return sp1[1].compareTo(sp2[1]);
					}
				});
		return files;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.tools.ForwardingJavaFileManager#getFileForInput(javax.tools
	 * .JavaFileManager.Location, java.lang.String, java.lang.String)
	 */
	@Override
	public FileObject getFileForInput(Location location, String packageName,
			String relativeName) throws IOException {
		FileObject o = fileObjects
				.get(uri(location, packageName, relativeName));
		if (o != null)
			return o;
		return super.getFileForInput(location, packageName, relativeName);
	}

	/* (non-Javadoc)
	 * @see javax.tools.ForwardingJavaFileManager#getFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, java.lang.String, javax.tools.FileObject)
	 */
	@Override
	public FileObject getFileForOutput(Location location, String packageName,
			String relativeName, FileObject sibling) throws IOException {
		// TODO Auto-generated method stub
		return super.getFileForOutput(location, packageName, relativeName,
				sibling);
	}

	/**
	 * @param kinds
	 * @return
	 */
	public List<JavaFileObject> getFileObjects(Kind[] kinds) {
		List<JavaFileObject> files = new ArrayList<JavaFileObject>();
		for (Entry<URI, JavaFileObject> entry : fileObjects.entrySet()) {
			if (Arrays.asList(kinds).contains(entry.getValue().getKind())) {
				files.add(entry.getValue());
			}
		}
		Collections.sort(files, new Comparator<JavaFileObject>() {
			@Override
			public int compare(JavaFileObject o1, JavaFileObject o2) {
				return o1.toUri().compareTo(o2.toUri());
			}
		});
		return files;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public InputStream getJarInputStream() throws IOException {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION,
				"1.0");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		JarOutputStream jos = new JarOutputStream(baos, manifest);
		List<Map.Entry<URI, JavaFileObject>> entries = getFileEntries(Kind
				.values());
		Set<String> folders = new HashSet<String>();
		for (Entry<URI, JavaFileObject> entry : entries) {
			URI uri = entry.getKey();
			String[] parts = uri.toString().split("/");
			String packageName = parts[1].replaceAll("\\.", "/");
			String fileName = parts[2];

			createPackageFolders(jos, packageName, folders);

			if (StandardLocation.valueOf(parts[0]) == StandardLocation.CLASS_OUTPUT) {
				fileName += Kind.CLASS.extension;
			}
			String pathEntry = null;
			if (packageName.isEmpty()) {
				pathEntry = fileName;
			} else {
				pathEntry = packageName + AbstractResource.PATH_SEPARATOR
						+ fileName;
			}
			JarEntry ze = new JarEntry(new ZipEntry(pathEntry));
			jos.putNextEntry(ze);
			final InputStream in = entry.getValue().openInputStream();
			final byte[] bytes = new byte[2048];
			for (int bytesRead = in.read(bytes); bytesRead >= 0; bytesRead = in
					.read(bytes)) {
				jos.write(bytes, 0, bytesRead);
			}// for
			in.close();
			jos.closeEntry();
		}
		jos.flush();
		jos.close();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				baos.toByteArray());
		baos.close();
		return inputStream;
	}

//	public InputStream getJarInputStreamx() throws IOException {
//		Manifest manifest = new Manifest();
//		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION,
//				"1.0");
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		File f = new File("C:\\workingdir\\be.jar");
//		FileOutputStream fos = new FileOutputStream(f);
//		JarOutputStream target = new JarOutputStream(baos, manifest);
//		add(getCompiledFolder(), target);
//		target.close();
//		return new ByteArrayInputStream(baos.toByteArray());
//	}

	@Override
	public JavaFileObject getJavaFileForInput(Location location,
			String className, Kind kind) throws IOException {
		// TODO Auto-generated method stub
		return super.getJavaFileForInput(location, className, kind);
	}
	
	/**
	 * Create a JavaFileImpl for an output class file and store it in the
	 * classloader.
	 * 
	 * @see javax.tools.ForwardingJavaFileManager#getJavaFileForOutput(javax.tools.JavaFileManager.Location,
	 *      java.lang.String, javax.tools.JavaFileObject.Kind,
	 *      javax.tools.FileObject)
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String qualifiedName, Kind kind, FileObject outputFile)
			throws IOException {
		JavaFileObject file = new JavaFileObjectImpl(qualifiedName, kind, getStreamGenerator());
		if (kind == Kind.CLASS) {
			final int dotPos = qualifiedName.lastIndexOf('.');
			final String className = dotPos == -1 ? qualifiedName
					: qualifiedName.substring(dotPos + 1);
			final String packageName = dotPos == -1 ? "" : qualifiedName
					.substring(0, dotPos);
			fileObjects.put(uri(location, packageName, className), file);
		}
		((DynamicLoader)classloader).addJavaFile(qualifiedName, file);
		return file;
	}

	public RootFolderLocation getRootFolder(String name) {
		if (root == null) {
			root = new RootFolderLocation(name, this);
		}
		return root;
	}

	public AbstractStreamGenerator getStreamGenerator() {
		return streamGenerator;
	}

	@Override
	public String inferBinaryName(Location loc, JavaFileObject file) {
		String result;
		// For our JavaFileImpl instances, return the file's name, else
		// simply run the default implementation
		if (file instanceof JavaFileObjectImpl) {
			String name = file.getName();
			if(name.endsWith(".class")) {
				name = name.substring(0, name.length() - ".class".length());
			}
			result = name.replaceAll("/", ".");
		} else
			result = super.inferBinaryName(loc, file);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.tools.ForwardingJavaFileManager#list(javax.tools.JavaFileManager
	 * .Location, java.lang.String, java.util.Set, boolean)
	 */
	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName,
			Set<Kind> kinds, boolean recurse) throws IOException {
		Iterable<JavaFileObject> result = super.list(location, packageName,
				kinds, recurse);
		ArrayList<JavaFileObject> files = new ArrayList<JavaFileObject>();
		if (location == StandardLocation.CLASS_PATH
				&& kinds.contains(JavaFileObject.Kind.CLASS)) {
			for (Entry<URI, JavaFileObject> entry : fileObjects.entrySet()) {
				URI uri = entry.getKey();
				JavaFileObject fileObject = entry.getValue();
				String pkgName = uri.getPath().substring(uri.getPath().indexOf("/")+1, uri.getPath().lastIndexOf("/"));
				boolean match = false;
				if(recurse) {
					match = pkgName.startsWith(packageName);
				} else {
					match = pkgName.equals(packageName);
				}
				if (fileObject.getKind() == Kind.CLASS
						&& match)
					files.add(fileObject);
			}
			files.addAll(((DynamicLoader)classloader).files());
		} else if (location == StandardLocation.SOURCE_PATH
				&& kinds.contains(JavaFileObject.Kind.SOURCE)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == Kind.SOURCE
						&& file.toUri().toString().startsWith(packageName))
					files.add(file);
			}
		}
		for (JavaFileObject file : result) {
			files.add(file);
		}
		return files;
	}

	/**
	 * Load a class that was generated by this instance or accessible from its
	 * parent class loader. Use this method if you need access to additional
	 * classes compiled by
	 * {@link #compile(String, CharSequence, DiagnosticCollector, Class...)
	 * compile()}, for example if the primary class contained nested classes or
	 * additional non-public classes.
	 * 
	 * @param qualifiedClassName
	 *            the name of the compiled class you wish to load
	 * @return a Class instance named by <var>qualifiedClassName</var>
	 * @throws ClassNotFoundException
	 *             if no such class is found.
	 */
	@SuppressWarnings("unchecked")
	public Class<?> loadClass(final String qualifiedClassName)
			throws ClassNotFoundException {
		return classloader.loadClass(qualifiedClassName);
	}

	/**
	 * @param location
	 * @param file
	 */
	public void putFileForInput(StandardLocation location, String packageName,
			String relativeName, JavaFileObject file) {
		fileObjects.put(uri(location, packageName, relativeName), file);
		// if(packageName != null && !packageName.isEmpty()) {
		// classloader.add(packageName+"."+relativeName, file);
		// } else {
		// classloader.add(relativeName, file);
		// }
	}

	/**
	 * Convert a location and class name to a URI
	 */
	private URI uri(Location location, String packageName, String relativeName) {
		return AbstractStreamGenerator.toURI(location.getName() + '/' + packageName
				+ '/' + relativeName);
	}
	

}