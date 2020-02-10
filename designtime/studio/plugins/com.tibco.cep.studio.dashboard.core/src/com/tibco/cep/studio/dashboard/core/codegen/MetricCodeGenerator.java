package com.tibco.cep.studio.dashboard.core.codegen;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.IMetricConceptCodeGenerator;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;

public class MetricCodeGenerator implements IMetricConceptCodeGenerator {

    public MetricCodeGenerator() {
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void generateConcept(Concept cept, CodeGenContext ctx) throws Exception {

        File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);

        Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);

        Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer.fromConcept(cept, o);

        if (cept.isMetricTrackingEnabled() == false) {

	        JavaClassWriter jClassWriter = MetricClassGeneratorSmap.makeMetricFile(cept,
							                    (Properties) ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
							                    smblbMap,
							                    (Ontology) ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));

	        JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(cept.getName(),
	                							ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath()),
	                							targetDir,
	                							ModelNameUtil.RULE_SEPARATOR_CHAR);

	        jfwriter.addClass(jClassWriter);

	        jfwriter.writeFile();
        }
        else {
	        final JavaClassWriter jMetricSupportClassWriter = MetricClassGeneratorSmap.makeMetricSupportFile(cept,
							                    (Properties) ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
							                    smblbMap,
							                    (Ontology) ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));

	        JavaFileWriter jmfwriter = CodeGenHelper.setupJavaFileWriter(cept.getName()/* + "DVM"*/,
								                ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath()),
								                targetDir,
								                ModelNameUtil.RULE_SEPARATOR_CHAR);

	        jmfwriter.addClass(jMetricSupportClassWriter);

	        jmfwriter.writeFile();
        }
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void generateConceptStream(Concept cept, CodeGenContext ctx) throws Exception {

		JavaFolderLocation targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);

		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);

        Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer.fromConcept(cept, o);

        if (cept.isMetricTrackingEnabled() == false) {
	        JavaClassWriter jClassWriter = MetricClassGeneratorSmap.makeMetricFile(cept,
							                    (Properties) ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
							                    smblbMap,
							                    (Ontology) ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));

	        JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(cept.getName(),
								                ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath()),
								                targetDir,
								                ModelNameUtil.RULE_SEPARATOR_CHAR);

	        jfwriter.addClass(jClassWriter);

	        jfwriter.writeStream();
        }

        else {
	        JavaClassWriter jMetricSupportClassWriter = MetricClassGeneratorSmap.makeMetricSupportFile(cept,
							                    (Properties) ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
							                    smblbMap,
							                    (Ontology) ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
							                    (Map) ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));

	        JavaFileWriter jmfwriter = CodeGenHelper.setupJavaStreamWriter(cept.getName() /*+ "DVM"*/,
								                ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath()),
								                targetDir,
								                ModelNameUtil.RULE_SEPARATOR_CHAR);

	        jmfwriter.addClass(jMetricSupportClassWriter);

	        jmfwriter.writeStream();
        }

	}

}
