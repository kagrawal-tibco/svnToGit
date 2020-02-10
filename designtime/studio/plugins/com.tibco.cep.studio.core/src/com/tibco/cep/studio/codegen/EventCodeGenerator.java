package com.tibco.cep.studio.codegen;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.EventClassGeneratorSmap;
import com.tibco.be.parser.codegen.IEventCodeGenerator;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.SmapFileWriter;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.semantic.SmapStratum;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;

public class EventCodeGenerator implements IEventCodeGenerator {

	public EventCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateEvent(Event event, CodeGenContext ctx) throws Exception{
		generateEventJavaFile((Event) event,
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS), 
				(File)ctx.get(CodeGenConstants.BE_CODEGEN_TIME_EVENT_DIR),
				(File)ctx.get(CodeGenConstants.BE_CODEGEN_EVENT_DIR),
				(Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG),
				(Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));

	}
	
	public void generateEventJavaFile(
            Event event, Properties oversizeStringConstants, File timeEventTargetDir, File eventTargetDir,
            boolean enableDebug, Ontology o, Map<String, Map<String, int[]>> propInfoCache) throws Exception {
    	RuleInfo rinfo = new RuleInfo();
    	File targetDir;
    	if(event.getType() == Event.TIME_EVENT) {
    		targetDir = timeEventTargetDir;
    	} else {
    		targetDir = eventTargetDir;
    	}
    	//rinfo.setPath(event.getFullPath());
    	JavaClassWriter jClassWriter = EventClassGeneratorSmap.makeEventClass(event, oversizeStringConstants, rinfo, o, propInfoCache);
    	JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(event.getName(),
    			ModelNameUtil.modelPathToExternalForm(event.getFolder().getFullPath())
    			, targetDir
    			,ModelNameUtil.RULE_SEPARATOR_CHAR);
    	jfwriter.addClass(jClassWriter);
    	jfwriter.writeFile();
//            if(genSmapFile == true || enableDebug ) {
		if(enableDebug ) {
			rinfo.getActionStratumMap().put(jClassWriter.getClassName(),new SmapStratum("RSP"));
			SmapFileWriter smwriter = CodeGenHelper.setupSmapFileWriter(jClassWriter.getName(),
					ModelNameUtil.modelPathToExternalForm(event.getFolder().getFullPath()),
					targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			smwriter.writeSMapXmlFile(rinfo);
		}
    }

	@Override
	public void generateEventStream(Event event, CodeGenContext ctx)
			throws Exception {
		generateEventJavaStream((Event) event,
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS), 
				(JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_TIME_EVENT_DIR),
				(JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_EVENT_DIR),
				(Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG),
				(Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		
	}

	private void generateEventJavaStream(Event event, Properties oversizeStringConstants,
			JavaFolderLocation timeEventTargetDir,
			JavaFolderLocation eventTargetDir, Boolean enableDebug, Ontology o
			, Map<String, Map<String, int[]>> propInfoCache) throws Exception
	{
		RuleInfo rinfo = new RuleInfo();
		JavaFolderLocation targetDir;
    	if(event.getType() == Event.TIME_EVENT) {
    		targetDir = timeEventTargetDir;
    	} else {
    		targetDir = eventTargetDir;
    	}
    	//rinfo.setPath(event.getFullPath());
    	JavaClassWriter jClassWriter = EventClassGeneratorSmap.makeEventClass(event, oversizeStringConstants, rinfo, o, propInfoCache);
    	JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(event.getName(),
    			ModelNameUtil.modelPathToExternalForm(event.getFolder().getFullPath())
    			, targetDir
    			,ModelNameUtil.RULE_SEPARATOR_CHAR);
    	jfwriter.addClass(jClassWriter);
    	jfwriter.writeStream();
//            if(genSmapFile == true || enableDebug ) {
		if(enableDebug ) {
			rinfo.getActionStratumMap().put(jClassWriter.getClassName(),new SmapStratum("RSP"));
			SmapFileWriter smwriter = CodeGenHelper.setupSmapStreamWriter(jClassWriter.getName(),
					ModelNameUtil.modelPathToExternalForm(event.getFolder().getFullPath()),
					targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			smwriter.writeSMapXmlStream(rinfo);
		}
		
	}

}
