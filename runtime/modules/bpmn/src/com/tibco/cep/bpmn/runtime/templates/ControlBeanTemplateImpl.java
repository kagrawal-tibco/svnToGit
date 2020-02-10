package com.tibco.cep.bpmn.runtime.templates;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.tibco.cep.bpmn.codegen.ControlClassGenerator;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ControlBeanTemplateImpl implements CodegenTemplate {

	Class templateClass;
	ProcessAgent pac;
	private boolean contained;
	private static final String[] PRIMITIVE_NAMES = { "int", "long", "double", "boolean", "String", "DateTime", "null", "void", "Object" };

	public static final String ENCODING	 = "UTF-8"; //$NON-NLS-1$

	private static Logger logger = LogManagerFactory.getLogManager().getLogger(ControlBeanTemplateImpl.class);

	
	public ControlBeanTemplateImpl(Class templateClass, boolean isContained) {
		this.templateClass = templateClass;
		this.contained = isContained;
	}
	

	@Override
	public String getName() {
		return templateClass.getSimpleName();
	}


	@Override
	public void init(ProcessAgent context) throws Exception {
		this.pac = context;

	}


	@Override
	public Class getTemplateClass() throws Exception {
		return templateClass;
	}
	
	
	@Override
	public JitCompilable generate() throws Exception {
		RuleServiceProvider rsp = pac.getRuleServiceProvider();

        ControlClassGenerator cgen = new ControlClassGenerator(templateClass,contained);
        
        ByteArrayOutputStream codeStream = cgen.generate();
        
        JitCompilable compilable = new JitCompilable(codeStream, cgen.getPackageName(), cgen.getClassName());
        

        
        logger.log(Level.INFO, String.format("Generating code for control class:%s.%s.java", cgen.getPackageName(),cgen.getClassName()));
        

        
        String genFolder = rsp.getProperties().getProperty("be.gen.process.dir",null);
        if(genFolder != null ) {
        	File folder = new File(genFolder);
        	if(folder.exists()) {
        		File packageFolder = new File(genFolder,ModelUtils.convertPackageToPath(cgen.getPackageName()));
        		if(!packageFolder.exists()){
        			packageFolder.mkdirs();
        		}
        		File outFile = new File(packageFolder,cgen.getClassName()+".java");
        		if(outFile.exists()) {
        			outFile.delete();
        		}
        		logger.log(Level.INFO, String.format("Writing process source file:%s", outFile.getPath()));
        		FileOutputStream fos = new FileOutputStream(outFile);
        		codeStream.writeTo(fos);
        		fos.flush();
        		fos.close();
        	}
        }


        return compilable;
	}

	

}
