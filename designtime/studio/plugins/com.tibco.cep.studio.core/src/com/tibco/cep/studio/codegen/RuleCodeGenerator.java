package com.tibco.cep.studio.codegen;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.IRuleCodeGenerator;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.RuleClassGeneratorSmap;
import com.tibco.be.parser.codegen.SmapFileWriter;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.impl.RuleObjectParser;
import com.tibco.be.parser.impl.RuleTemplateInfo;
import com.tibco.be.parser.impl.RuleTemplateParser;
import com.tibco.be.parser.semantic.RuleInfoSymbolTable;
import com.tibco.be.parser.semantic.RuleTemplateSymbolTable;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleTemplate;

public class RuleCodeGenerator implements IRuleCodeGenerator {

	public RuleCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateRule(Rule rul, CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		List errorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULE_DIR);
		try{
            final RuleInfo rinfo;
            final RuleInfoSymbolTable symbolTable;
            if (rul instanceof RuleTemplate) {
                rinfo = RuleTemplateParser.parseRuleTemplate((RuleTemplate) rul, o);
                symbolTable = new RuleTemplateSymbolTable((RuleTemplateInfo) rinfo, o);
            }
            else {
                rinfo = RuleObjectParser.parseRule(rul, o);
                symbolTable = new RuleInfoSymbolTable(rinfo, o);
            }
            if(rinfo.getErrors().size() <= 0) {
                JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(ModelNameUtil.getShortNameFromPath(rinfo.getFullName())
                        , ModelNameUtil.modelPathToExternalForm(ModelNameUtil.getFolderFromPath(rinfo.getFullName()))
                        , targetDir
                        ,ModelNameUtil.RULE_SEPARATOR_CHAR);
                JavaClassWriter jClass = RuleClassGeneratorSmap.makeRuleClassNew(
                        rinfo,
                        symbolTable,
                        (Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
                        (Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
                        o,
                        (Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE)
                );
                jfwriter.addClass(jClass);
                jfwriter.writeFile();
                boolean debug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
                if(debug) {
                    SmapFileWriter smwriter = CodeGenHelper.setupSmapFileWriter(ModelNameUtil.getShortNameFromPath(rinfo.getFullName())
                            , ModelNameUtil.modelPathToExternalForm(ModelNameUtil.getFolderFromPath(rinfo.getFullName()))
                            , targetDir
                            ,ModelNameUtil.RULE_SEPARATOR_CHAR);
                    smwriter.writeSMapXmlFile(rinfo);
                }

            }
            //errorList.addAll(rinfo.getErrors());
            for (RuleError ruleError : rinfo.getErrors()) {
            	ruleError.setName(rul.getFullPath());
            	errorList.add(ruleError);
            }
        } catch (ParseException pe) {
        	RuleError err = RuleError.makeSyntaxError(pe);
        	err.setName(rul.getFullPath());
            errorList.add(err);
        }

	}

	@Override
	public void generateRuleStream(Rule rul, CodeGenContext ctx)
			throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		List errorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		boolean debug = (Boolean)ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		JavaFolderLocation targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_RULE_DIR);
		try{
            RuleInfo rinfo;
            final RuleInfoSymbolTable symbolTable;
            if (rul instanceof RuleTemplate) {
                rinfo = RuleTemplateParser.parseRuleTemplate((RuleTemplate) rul, o);
                symbolTable = new RuleTemplateSymbolTable((RuleTemplateInfo) rinfo, o);
            }
            else {
                rinfo = RuleObjectParser.parseRule(rul, o);
                symbolTable = new RuleInfoSymbolTable(rinfo, o);
            }
            if(rinfo.getErrors().size() <= 0) {
                JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(ModelNameUtil
                        .getShortNameFromPath(rinfo.getFullName())
                        , ModelNameUtil.modelPathToExternalForm(ModelNameUtil.getFolderFromPath(rinfo.getFullName()))
                        , targetDir
                        , ModelNameUtil.RULE_SEPARATOR_CHAR);
                JavaClassWriter jClass = RuleClassGeneratorSmap.makeRuleClassNew(
                        rinfo,
                        symbolTable,
                        (Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
                        (Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE),
                        o,
                        (Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE)
                );
                jfwriter.addClass(jClass);
                jfwriter.writeStream();
                if(debug) {
                    SmapFileWriter smwriter = CodeGenHelper.setupSmapStreamWriter(ModelNameUtil
                            .getShortNameFromPath(rinfo.getFullName())
                            , ModelNameUtil
                            .modelPathToExternalForm(ModelNameUtil.getFolderFromPath(rinfo.getFullName()))
                            , targetDir
                            , ModelNameUtil.RULE_SEPARATOR_CHAR);
                    smwriter.writeSMapXmlStream(rinfo);
                }

            }
            //errorList.addAll(rinfo.getErrors());
            for (RuleError ruleError : rinfo.getErrors()) {
            	ruleError.setName(rul.getFullPath());
            	errorList.add(ruleError);
            }
        } catch (ParseException pe) {
        	RuleError err = RuleError.makeSyntaxError(pe);
        	err.setName(rul.getFullPath());
            errorList.add(err);
        }
		
	}

}
