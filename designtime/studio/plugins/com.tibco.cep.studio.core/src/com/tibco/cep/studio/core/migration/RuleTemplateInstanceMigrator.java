/**
 * 
 */
package com.tibco.cep.studio.core.migration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.util.RuleTemplateInstanceDeserializer;
import com.tibco.cep.webstudio.model.rule.instance.util.RuleTemplateInstanceSerializer;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Migrator for RTI. Pre-5.2 RTI filters did not have Id values, this migrator adds Id values to filters in 5.1.x projects.
 *  
 * @author vpatil
 */
public class RuleTemplateInstanceMigrator extends DefaultStudioProjectMigrator {
	
	@Override
	public int getPriority() {
		return 50;
	}
	
	@Override
	protected void migrateFile(File parentFile, File file,
			IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!"ruletemplateinstance".equalsIgnoreCase(ext)) {
			return;
		}
		monitor.subTask("Converting Rule Template Instance file " + file.getName());
		try {
			RuleTemplateInstanceDeserializer rtiDeserializer = new RuleTemplateInstanceDeserializer(file);
			rtiDeserializer.deserialize();
			RuleTemplateInstance ruleTemplateInstance = rtiDeserializer.getDeserialized();
			
			transformRuleTemplateInstance(ruleTemplateInstance);
			serializeTransformedModel(file, ruleTemplateInstance);
			
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
	}
	
	/**
	 * 
	 * @param ruleTemplateInstance
	 */
	private void transformRuleTemplateInstance(RuleTemplateInstance ruleTemplateInstance) {
		// commands
		transformMultiFilter(ruleTemplateInstance.getConditionFilter());
		
		// actions
		List<Command> commands = ruleTemplateInstance.getActions().getActions();
		for(Command cmd : commands) {
			BuilderSubClause builderSubClause = cmd.getSubClause();
			if (builderSubClause != null) {
				for (Filter filter : builderSubClause.getFilters()) {
					transformSingleFilter(filter);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param multiFilter
	 */
	private void transformMultiFilter(MultiFilter multiFilter) {
		if (multiFilter != null && (multiFilter.getFilterId() == null || multiFilter.getFilterId().isEmpty())) {
			multiFilter.setFilterId(UUID.randomUUID().toString());
		}
		
		if (multiFilter != null) {
			BuilderSubClause builderSubClause = multiFilter.getSubClause();
			if (builderSubClause != null) {
				for (Filter filter : builderSubClause.getFilters()) {
					transformSingleFilter(filter);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param filter
	 */
	private void transformSingleFilter(Filter filter) {
		if (filter != null && (filter.getFilterId() == null || filter.getFilterId().isEmpty())) {
			filter.setFilterId(UUID.randomUUID().toString());
		}
		if (filter instanceof MultiFilter) transformMultiFilter((MultiFilter)filter);
	}
	
	/**
	 * 
	 * @param file
	 * @param ruleTemplateInstance
	 */
	private void serializeTransformedModel(File file, RuleTemplateInstance ruleTemplateInstance) {		
        //Serialize it to xml
        RuleTemplateInstanceSerializer ruleTemplateInstanceSerializer = new RuleTemplateInstanceSerializer((RuleTemplateInstance)ruleTemplateInstance);
        ruleTemplateInstanceSerializer.serialize();
        XiNode rootDoc = ruleTemplateInstanceSerializer.getSeralized();
        
        FileOutputStream fos = null;
        ByteArrayOutputStream bos = null;        
        try {
        	bos = new ByteArrayOutputStream();
            //True parameter is for pretty printing.
            XiSerializer.serialize(rootDoc, bos, "UTF-8", true);
            
            fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bos.close();
                fos.close();
            } catch (IOException e) {
                //Ignore for now
            }
        }
	}
}
