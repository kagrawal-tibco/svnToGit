package com.tibco.cep.studio.codegen;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.be.parser.codegen.FileStreamGenerator;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;

/**
 * Usage:
 * Call generateXXJavaFiles then call compileSource.
 * This will require a classpath argument that includes
 * the model, engine, functions, util and possibly other packages.
 *
 * The generateRuleFiles, generateEventJavaFiles and generateConceptJava files take iterator arguments
 * which will likely come from the Ontology.get[Concepts, Rules, Events] methods.
 *
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 14, 2004
 * Time: 11:59:06 PM
 * To change this template use File | Settings | File Templates.
 * @deprecated Use {@link FileStreamGenerator }
 * 
 */
public class BulkGenerator extends BaseGenerator {
    public BulkGenerator(DefaultRuntimeClassesPackager packager, IProgressMonitor monitor) {
    	super(packager,monitor);
	}
    
}
