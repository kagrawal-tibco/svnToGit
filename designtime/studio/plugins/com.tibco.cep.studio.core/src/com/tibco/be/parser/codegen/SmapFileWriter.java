package com.tibco.be.parser.codegen;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.semantic.SmapGenVisitor;
import com.tibco.be.parser.semantic.SmapStratum;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.xml.DomSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jan 3, 2009
 * Time: 1:35:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmapFileWriter {
    public static final String SMAP_ENCODING = "UTF-8";
    public static final String SMAP_EXTENSION=".smap";
    public static final String SMAP_INDENTING="yes";
    public static final String SMAP_OUTPUT_FORMAT="xml";

    private String javaName;
    private String packageName;
    private char separator;
    private File targetDir;

	private JavaFolderLocation targetDirLocation;

    public SmapFileWriter(String javaName, String packageName, char separator, File targetdir) {
        this.javaName = javaName;
        this.packageName = packageName;
        this.separator = separator;
        this.targetDir = targetdir;
    }


    public SmapFileWriter(String javaFileName, String javaPackageName,
			char separator, JavaFolderLocation targetDirLocation) {
    	this.javaName = javaFileName;
        this.packageName = javaPackageName;
        this.separator = separator;
        this.targetDirLocation = targetDirLocation;
	}


	/**
	 * @param rinfo
	 * @throws Exception
	 */
	public void writeSMapXmlFile(RuleInfo rinfo) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();
//        domf.setValidating(true);
//        domf.setNamespaceAware(true);

        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        writerSmapNodes(rinfo, outFilePath, doc, debugNode);
        Writer writer = getStreamWriter(outFileDir, this.javaName+SMAP_EXTENSION);
        
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc,SMAP_ENCODING, SMAP_INDENTING, SMAP_OUTPUT_FORMAT, pw);
        pw.flush();
        pw.close();

        return;

    }
	
	/**
	 * @param rinfo
	 * @throws Exception
	 */
	public void writeSMapXmlStream(RuleInfo rinfo) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        writerSmapNodes(rinfo, outFilePath, doc, debugNode);

        File outFile = new File(outFileDir, javaName + ".smap");
        if (!outFileDir.exists()) {
            outFileDir.mkdirs();
        }
        
        Writer writer = getStreamWriter(this.targetDirLocation, this.packageName,
				this.javaName + SMAP_EXTENSION);
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc,SMAP_ENCODING, SMAP_INDENTING,SMAP_OUTPUT_FORMAT, pw);
        pw.flush();
        pw.close();


        return;

    }

    /**
     * @param sblb
     * @param outFilePath
     * @param doc
     * @param debugNode
     */
    private void writerSmapNodes(StateMachineBlockLineBuffer.StateBlockLineBuffer sblb, String outFilePath, Document doc, Element debugNode) {
        for(Iterator<StateMachineBlockLineBuffer.MethodBlockLineBuffer> it = sblb.getMethodBlockLineBufferMap().values().iterator();it.hasNext();) {
             StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb = it.next();
             writerSmapNodes(mblb.getRuleInfo(),outFilePath,doc,debugNode);
        }

        for(Iterator<StateMachineBlockLineBuffer.StateBlockLineBuffer> it = sblb.getChildStateBlocks().values().iterator();it.hasNext();) {
            StateMachineBlockLineBuffer.StateBlockLineBuffer chsblb = it.next();
            writerSmapNodes(chsblb,outFilePath,doc,debugNode);
        }

    }

    private void writerSmapNodes(RuleInfo rinfo, String outFilePath, Document doc, Element debugNode) {
        // smap conditions
        for (Iterator<Map.Entry<String, SmapStratum>> it = rinfo.getConditionStratumMap().entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, SmapStratum> entry = it.next();
            String className = entry.getKey();
            SmapStratum stratum = entry.getValue();
            Element smapNode = doc.createElement("smap");
            debugNode.appendChild(smapNode);
            smapNode.setAttribute("src", rinfo.getFullName());
            smapNode.setAttribute("code", packageName + this.separator + className);
//            System.out.println("src="+rinfo.getFullName()+" code="+className);
            String filePath = outFilePath + File.separatorChar + className;
            if(rinfo.getRuleBlockBuffer() != null) {
                SmapGenVisitor conditionVisitor = new SmapGenVisitor(filePath, stratum, rinfo.getRuleBlockBuffer().getWhenOffset());
                for (Iterator cit = rinfo.getWhenTrees(); cit.hasNext();) {
                    RootNode condition = (RootNode) cit.next();
                    if (condition.getUserContext().equals(className)) {
                        SmapStratum conditionStratum = conditionVisitor.visitNode(condition);
//            conditionStratum.optimizeLineSection();
                        List<SmapStratum.LineInfo> li = conditionStratum.getLineData();
                        for (int i = 0; i < li.size(); i++) {
                            SmapStratum.LineInfo line = li.get(i);
//            for (Iterator<SmapStratum.LineInfo> itl = li.iterator(); itl.hasNext();) {
//                SmapStratum.LineInfo line = itl.next();
                            Element mapNode = doc.createElement("map");
                            mapNode.setAttribute("line", line.getXmlString());
//                System.out.println("line="+line.getXmlString());
                            smapNode.appendChild(mapNode);
                        }
                    }
                }
            }

        }

        for (Iterator<Map.Entry<String, SmapStratum>> it = rinfo.getActionStratumMap().entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, SmapStratum> entry = it.next();
            String className = entry.getKey();
            SmapStratum stratum = entry.getValue();
            Element smapNode = doc.createElement("smap");
            debugNode.appendChild(smapNode);
            smapNode.setAttribute("src", rinfo.getFullName());
            smapNode.setAttribute("code", packageName + this.separator + className);

            String filePath = outFilePath + File.separatorChar + className;
            if(rinfo.getRuleBlockBuffer() != null) {
                SmapGenVisitor actionVisitor = new SmapGenVisitor(filePath, stratum, rinfo.getRuleBlockBuffer().getThenOffset());
                SmapStratum actionStratum = actionVisitor.visitNodes(rinfo.getThenTrees());
//            actionStratum.optimizeLineSection();
                for (SmapStratum.LineInfo line : actionStratum.getLineData()) {
                    Element mapNode = doc.createElement("map");
                    mapNode.setAttribute("line", line.getXmlString());
                    smapNode.appendChild(mapNode);
                }
            }
        }
    }

//    public void writeSMapFile(RuleInfo rinfo, Map stratumMap) throws IOException {
//        String outFilePath = packageName.replace(this.separator, File.separatorChar);
//        File outFileDir = new File(targetDir, outFilePath);
//
//        String javaFilePath = outFilePath;
//        if (!javaFilePath.endsWith(File.separator)) {
//            javaFilePath += File.separator;
//        }
//        javaFilePath = javaFilePath + javaName;
//        if (!javaFilePath.endsWith(".java")) {
//            javaFilePath += ".java";
//        }
//
//        SmapGenerator smapGenerator = new SmapGenerator();
//        SmapStratum s = new SmapStratum("RSP");
//        smapGenerator.setOutputFileName(SmapGenVisitor.unqualify(javaFilePath));
//        // smap conditions
//        for (Iterator<Map.Entry<String, SmapStratum>> it = rinfo.getConditionStratumMap().entrySet().iterator(); it.hasNext();) {
//            Map.Entry<String, SmapStratum> entry = it.next();
//            String className = entry.getKey();
//            SmapStratum stratum = entry.getValue();
//            String filePath = outFilePath + File.separatorChar + className;
//            SmapGenVisitor conditionVisitor = new SmapGenVisitor(filePath, stratum, rinfo.getRuleBlockBuffer().getWhenOffset() - 1);
//            SmapStratum conditionStratum = conditionVisitor.visitNodes(rinfo.getWhenTrees());
//            conditionStratum.optimizeLineSection();
//            smapGenerator.addStratum(conditionStratum, true);
//            File outFile = new File(outFileDir, className + ".smap");
//            if (!outFileDir.exists()) {
//                outFileDir.mkdirs();
//            }
//            PrintWriter pw =
//                    new PrintWriter(
//                            new OutputStreamWriter(
//                                    new FileOutputStream(outFile),
//                                    SMAP_ENCODING));
//            pw.write(smapGenerator.getString());
//            pw.flush();
//            pw.close();
//        }
//
//        // smap actions
//        smapGenerator = new SmapGenerator();
//        s = new SmapStratum("RSP");
//        smapGenerator.setOutputFileName(SmapGenVisitor.unqualify(javaFilePath));
//        for (Iterator<Map.Entry<String, SmapStratum>> it = rinfo.getActionStratumMap().entrySet().iterator(); it.hasNext();) {
//            Map.Entry<String, SmapStratum> entry = it.next();
//            String className = entry.getKey();
//            SmapStratum stratum = entry.getValue();
//            String filePath = outFilePath + File.separatorChar + className;
//            SmapGenVisitor actionVisitor = new SmapGenVisitor(filePath, stratum, rinfo.getRuleBlockBuffer().getThenOffset() - 1);
//            SmapStratum actionStratum = actionVisitor.visitNodes(rinfo.getThenTrees());
//            actionStratum.optimizeLineSection();
//            smapGenerator.addStratum(actionStratum, true);
//            File outFile = new File(outFileDir, className + ".smap");
//            if (!outFileDir.exists()) {
//                outFileDir.mkdirs();
//            }
//            PrintWriter pw =
//                    new PrintWriter(
//                            new OutputStreamWriter(
//                                    new FileOutputStream(outFile),
//                                    SMAP_ENCODING));
//            pw.write(smapGenerator.getString());
//            pw.flush();
//            pw.close();
//        }
//
//
//    }

    public void writeSMapXmlFile(Map<String, RuleInfo> rinfoMap) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        for(RuleInfo rinfo : rinfoMap.values()){
            writerSmapNodes(rinfo,outFilePath,doc,debugNode);
        }
        Writer writer = getStreamWriter(outFileDir, javaName + SMAP_EXTENSION);
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc, SMAP_ENCODING, "yes", "xml", pw);
        pw.flush();
        pw.close();
    }
    
    /**
     * @param rinfoMap
     * @throws Exception
     */
    public void writeSMapXmlStream(Map<String, RuleInfo> rinfoMap) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        for(RuleInfo rinfo : rinfoMap.values()){
            writerSmapNodes(rinfo,outFilePath,doc,debugNode);
        }
        
        Writer writer = getStreamWriter(this.targetDirLocation,
				this.packageName, this.javaName + SMAP_EXTENSION);
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc, SMAP_ENCODING, "yes", "xml", pw);
        pw.flush();
        pw.close();
    }

    /**
     * @param smblb
     * @throws Exception
     */
    public void writeSMapXmlFile(StateMachineBlockLineBuffer smblb) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        for(StateMachineBlockLineBuffer.StateBlockLineBuffer sblb: smblb.getStateMap().values()){
            writerSmapNodes(sblb,outFilePath,doc,debugNode);
        }
        for(StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb : smblb.getTransitionMap().values()){
            writerSmapNodes(tblb.getRuleInfo(),outFilePath,doc,debugNode);
        }
        Writer writer = getStreamWriter(outFileDir,this.javaName+SMAP_EXTENSION);
        
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc, SMAP_ENCODING, "yes", "xml", pw);
        pw.flush();
        pw.close();
    }
    
    /**
     * @param smblb
     * @throws Exception
     */
    public void writeSMapXmlStream(StateMachineBlockLineBuffer smblb) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);
        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        for(StateMachineBlockLineBuffer.StateBlockLineBuffer sblb: smblb.getStateMap().values()){
            writerSmapNodes(sblb,outFilePath,doc,debugNode);
        }
        for(StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb : smblb.getTransitionMap().values()){
            writerSmapNodes(tblb.getRuleInfo(),outFilePath,doc,debugNode);
        }
        Writer writer = getStreamWriter(this.targetDirLocation,
				this.packageName, this.javaName + SMAP_EXTENSION);
        
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc, SMAP_ENCODING, SMAP_INDENTING,SMAP_OUTPUT_FORMAT, pw);
        pw.flush();
        pw.close();
    }
    
    /**
     * @param targetDir
     * @param fileName
     * @return
     * @throws Exception
     */
    protected Writer getStreamWriter(File targetDir,String fileName) throws Exception {
    	if (!targetDir.exists()) {
    		targetDir.mkdirs();
    	}
    	File outFile = new File(targetDir, fileName);
    	PrintWriter pw =new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFile),
                SMAP_ENCODING));
    	return pw;
    }
    
    /**
     * @param targetDirLoc
     * @param packageName
     * @param fileName
     * @return
     * @throws IOException
     */
    protected Writer getStreamWriter(JavaFolderLocation targetDirLoc,String packageName,String fileName) throws IOException {
    	JavaFileLocation outFile = targetDirLoc.addFile(packageName, fileName);
    	return outFile.openWriter();    	
    }


	public void writeSMapXmlFile(byte[] source, String entityPath) throws Exception {
		String outFilePath = packageName.replace(this.separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        writerSourceSmapNodes(source, outFilePath,entityPath, doc, debugNode);
        Writer writer = getStreamWriter(outFileDir, this.javaName+SMAP_EXTENSION);
        
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc,SMAP_ENCODING, SMAP_INDENTING, SMAP_OUTPUT_FORMAT, pw);
        pw.flush();
        pw.close();
		
	}
	public void writeSMapXmlStream(byte[] source, String entityPath) throws Exception {
        String outFilePath = packageName.replace(this.separator, File.separatorChar);
//        File outFileDir = new File(targetDir, outFilePath);

        DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = domf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element debugNode = doc.createElement("debug");
        doc.appendChild(debugNode);

        writerSourceSmapNodes(source, outFilePath,entityPath, doc, debugNode);

//        File outFile = new File(outFileDir, javaName + ".smap");
//        if (!outFileDir.exists()) {
//            outFileDir.mkdirs();
//        }
        
        Writer writer = getStreamWriter(this.targetDirLocation, this.packageName,
				this.javaName + SMAP_EXTENSION);
        PrintWriter pw =new PrintWriter(writer);
        DomSerializer.serialize(doc,SMAP_ENCODING, SMAP_INDENTING,SMAP_OUTPUT_FORMAT, pw);
        pw.flush();
        pw.close();


        return;

    }


	private void writerSourceSmapNodes(byte[] source, String outFilePath, String entityPath, Document doc, Element debugNode) throws Exception {
        Element smapNode = doc.createElement("smap");
        debugNode.appendChild(smapNode);
        smapNode.setAttribute("src",entityPath);
        smapNode.setAttribute("code", packageName + this.separator + this.javaName);
//        System.out.println("src="+rinfo.getFullName()+" code="+className);
        BufferedReader br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(source), SMAP_ENCODING));
        String s = null;
        int line=1;
        while((s=br.readLine()) != null){
        	 Element mapNode = doc.createElement("map");
             mapNode.setAttribute("line", String.format("%d:%d",line,line));
             smapNode.appendChild(mapNode);
             line++;
        }
        
		
	}


}
