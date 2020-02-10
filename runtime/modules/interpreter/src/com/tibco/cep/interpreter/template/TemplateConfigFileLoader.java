package com.tibco.cep.interpreter.template;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.util.RuleTemplateInstanceDeserializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * User: nprade
 * Date: 3/5/12
 * Time: 1:50 PM
 */
public class TemplateConfigFileLoader {


    final TemplateConfigRegistry catalog;


    public TemplateConfigFileLoader() {

        this(TemplateConfigRegistry.getInstance());
    }


    public TemplateConfigFileLoader(
            TemplateConfigRegistry catalog) {

        this.catalog = catalog;
    }


    private RuleTemplateInstance load(
            File file)
            throws Exception {

        if (!file.getName().endsWith(".ruletemplateinstance")) {
            return null;
        }
        return load(new FileInputStream(file), getRTIName(file.getName()));
    }



    public void loadAll(
            File file,
            boolean recursive)
            throws Exception {

        if (file.isDirectory()) {
            for (final File f : file.listFiles()) {
                try {
                    if (f.isDirectory()) {
                        if (recursive) {
                            this.loadAll(f, true);
                        }
                    }
                    else {
                        this.load(f);
                    }
                }
                catch (Exception e) {
                    LogManagerFactory.getLogManager().getLogger(TemplateConfigFileLoader.class)
                            .log(Level.WARN, "Failed to load template file: " + file, e);
                }
            }
        }
        else {
            load(new FileInputStream(file), getRTIName(file.getName()));
        }
    }


    public RuleTemplateInstance load(InputStream inputStream, String rtiName)
            throws Exception {

        RuleTemplateInstanceDeserializer ruleTemplateInstanceDeserializer = new RuleTemplateInstanceDeserializer(inputStream);
        ruleTemplateInstanceDeserializer.deserialize();

        final RuleTemplateInstance ruleTemplateInstance = ruleTemplateInstanceDeserializer.getDeserialized();
        if (ruleTemplateInstance.getName() == null || ruleTemplateInstance.getName().isEmpty()) ruleTemplateInstance.setName(rtiName);

        this.catalog.addTemplateConfig(ruleTemplateInstance);

        return ruleTemplateInstance;
    }
    
    private String getRTIName(String rtiName) {
    	int extnIndex = rtiName.lastIndexOf(".");
    	if (extnIndex != -1) {
    		rtiName = rtiName.substring(0, extnIndex);
    	}
    	return rtiName;
    }


}
