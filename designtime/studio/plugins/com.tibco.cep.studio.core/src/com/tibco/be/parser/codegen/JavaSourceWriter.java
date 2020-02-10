package com.tibco.be.parser.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Nov 8, 2008 Time: 11:05:41 AM To
 * change this template use File | Settings | File Templates.
 */
public class JavaSourceWriter {
	private byte[] fileBuffer;
	protected static final String BRK = CGConstants.BRK;
	protected String packageName = null;
	protected String shortName;
	protected ArrayList<JavaClassWriter> classes = new ArrayList<JavaClassWriter>();
	private char separator;
	private File targetDir;
	private JavaFolderLocation targetDirLocation;
	private String entityPath;

	/**
	 * @param shortName
	 * @param javaPackageName
	 * @param targetDir
	 * @param separator
	 */
	public JavaSourceWriter(String shortName, String javaPackageName, File targetDir, String entityPath,char separator) {
		this.shortName = shortName;
		this.packageName = javaPackageName;
		this.fileBuffer = new byte[0];
		this.targetDir = targetDir;
		this.entityPath = entityPath;
		this.separator = separator;
	}

	public JavaSourceWriter(String shortName, String javaPackageName, JavaFolderLocation targetDir,String entityPath, char separator) {
		this.shortName = shortName;
		this.packageName = javaPackageName;
		this.fileBuffer = new byte[0];
		this.targetDirLocation = targetDir;
		this.entityPath = entityPath;
		this.separator = separator;
	}

	public JavaSourceWriter(String shortName, String javaPackageName,String entityPath) {
		this(shortName, javaPackageName, (File) null,entityPath, File.separatorChar);
	}

	/**
	 * @return the targetDir
	 */
	public File getTargetDir() {
		return targetDir;
	}

	/**
	 * @param targetDir
	 *            the targetDir to set
	 */
	public void setTargetDir(File targetDir) {
		this.targetDir = targetDir;
	}

	public void setTargetFolder(JavaFolderLocation targetLoc) {
		this.targetDirLocation = targetLoc;
	}

	public void setPackage(String packageName) {
		this.packageName = packageName;
	}

	public String getShortName() {
		return shortName;
	}

	public String getPackage() {
		return packageName;
	}
	public String getEntityPath() {
		return entityPath;
	}

	public byte[] getFileBuffer() {
		return fileBuffer;
	}

	public String toString() {
		return fileBuffer.toString();
	}

	public void writeFile() throws IOException {
		String outFilePath = getPackage().replace('.', File.separatorChar);
		File outFileDir = new File(targetDir, outFilePath);
		File outFile = new File(outFileDir, getShortName() + CodeGenConstants.JAVA_FILE_EXTENSION);
		if (!outFileDir.exists()) {
			outFileDir.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(outFile, false);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		writeStream(osw);
	}

	public void writeStream() throws IOException {
		JavaFileLocation outFile = targetDirLocation.addFile(getPackage(), getShortName() + CodeGenConstants.JAVA_FILE_EXTENSION);
		writeStream(outFile.openWriter());
	}

	public void writeStream(Writer output) throws IOException {
		Writer writer = new BufferedWriter(output);
		Charset cs = Charset.forName("UTF-8");
		CharsetDecoder dec = cs.newDecoder();
		ByteBuffer bb = ByteBuffer.wrap(fileBuffer);
		CharBuffer cb = dec.decode(bb);
		writer.write(cb.toString());
		writer.flush();
		writer.close();
	}

	public void addSource(byte[] source) {
		fileBuffer = new byte[source.length];
		System.arraycopy(source, 0, fileBuffer, 0, source.length);
	}

}
