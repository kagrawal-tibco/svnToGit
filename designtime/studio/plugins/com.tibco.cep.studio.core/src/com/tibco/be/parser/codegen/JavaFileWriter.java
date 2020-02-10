package com.tibco.be.parser.codegen;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.tree.Node;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 8, 2008
 * Time: 11:05:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class JavaFileWriter {
    private LineBuffer fileBuffer;
    private File file;
    protected static final String BRK = CGConstants.BRK;
    protected String packageName = null;
    protected HashSet imports = new HashSet();
    protected String shortName;
    protected ArrayList<JavaClassWriter> classes = new ArrayList<JavaClassWriter>();
    private char separator;
    private File targetDir;
    private JavaFolderLocation targetDirLocation;



	/**
	 * @param shortName
	 * @param javaPackageName
	 * @param targetDir
	 * @param separator
	 */
	public JavaFileWriter(String shortName, String javaPackageName, File targetDir, char separator) {
        this.shortName =shortName;
        this.packageName = javaPackageName;
        this.fileBuffer = new LineBuffer(null);
        this.targetDir = targetDir;
        this.separator = separator;
    }
	
	public JavaFileWriter(String shortName, String javaPackageName,
			JavaFolderLocation targetDir, char separator) {
		this.shortName =shortName;
        this.packageName = javaPackageName;
        this.fileBuffer = new LineBuffer(null);
        this.targetDirLocation = targetDir;
        this.separator = separator;
	}
	
	public JavaFileWriter(String shortName, String javaPackageName) {
		this(shortName,javaPackageName,(File)null,File.separatorChar);
    }
    
    

    

	/**
	 * @return the targetDir
	 */
	public File getTargetDir() {
		return targetDir;
	}

	/**
	 * @param targetDir the targetDir to set
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

    public void addImport(String importPath) {
        imports.add(importPath);

    }

    public JavaClassWriter  createClass(String clazzName, Node node) {
        JavaClassWriter  newClazz = new JavaClassWriter(clazzName,node);
        addClass(newClazz);
        return newClazz;
    }

    public void addClass(JavaClassWriter clazz) {
        classes.add(clazz);
    }
    
    public Iterator<JavaClassWriter> getClasses() {
        return classes.iterator();
    }

    public LineBuffer getFileBuffer() {
        return fileBuffer;
    }

    public void writeBuffer(LineBuffer lbuffer,boolean adjust) {
//        StringBuffer ret = new StringBuffer();
        if(packageName != null) {
            lbuffer.append("package "+packageName+";"+BRK);
//            ret.append("package ");
//            ret.append(packageName);
//            ret.append(";" + BRK);
        }
        for(Iterator it = imports.iterator(); it.hasNext();) {
//            ret.append("import ");
//            ret.append((String)it.next());
//            ret.append(";" + BRK);
            lbuffer.append("import "+(String)it.next()+";"+BRK);
        }
        for(JavaClassWriter jcw :classes) {
//            ret.append("" + BRK + BRK);
//            ret.append(((JavaClass)it.next()).toStringBuffer());
            lbuffer.append(BRK);
            lbuffer.append(BRK);
//            if(adjust) {
//                jcw.adjustJavaLines(lbuffer.getJavaLine()-1);
//            }
            jcw.toLineBuffer(lbuffer,adjust);
//            lbuffer.append(jcw.toString());
        }
    }


    public String toString() {
        LineBuffer lb = new LineBuffer();
        writeBuffer(lb,false);
        return lb.toString();
    }



    public void writeFile() throws IOException {
        String outFilePath = getPackage().replace('.', File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);
        File outFile = new File(outFileDir, getShortName() + CodeGenConstants.JAVA_FILE_EXTENSION);
        if(!outFileDir.exists()) {
            outFileDir.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(outFile,false);
        OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
        writeStream(osw);
    }
    
    public void writeStream()  throws IOException {
    	JavaFileLocation outFile = targetDirLocation.addFile(getPackage(),
				getShortName() + CodeGenConstants.JAVA_FILE_EXTENSION);
    	writeStream(outFile.openWriter());
    }
    
    public void writeStream(Writer output) throws IOException {
    	Writer writer = new BufferedWriter(output);
        writeBuffer(this.fileBuffer,true);
        writer.write(getFileBuffer().toString());
        writer.flush();
        writer.close();
    }


//    public static void main(String [] args) {
//        try {
//            LineBuffer lb = new LineBuffer();
//            File targetDir = new File("C:\\temp");
//            JavaFileWriter jfw = new JavaFileWriter("test1","com.test",targetDir, ModelNameUtil.RULE_SEPARATOR_CHAR);
//            JavaClassWriter jcw = jfw.createClass("test1",null);
//            MethodRecWriter mr = jcw.createMethod("public","","test1");
//            mr.setBody("System.out.println(\"Hello World.test1\");");
//            JavaClassWriter ncw = jcw.createNestedClassWriter("nested1",null);
//            MethodRecWriter nmr = ncw.createMethod("public","","nested1");
//            nmr.setBody("System.out.println(\"Hello World.nested\");");
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
}
