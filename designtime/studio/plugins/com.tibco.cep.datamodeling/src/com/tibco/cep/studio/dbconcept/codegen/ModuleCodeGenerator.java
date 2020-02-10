package com.tibco.cep.studio.dbconcept.codegen;

import java.util.Map;

import com.tibco.be.parser.codegen.IModuleCodeGenerator;
import com.tibco.cep.designtime.model.Entity;

public class ModuleCodeGenerator implements IModuleCodeGenerator {

	public ModuleCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateModule(Entity entity, Map<?,?> archiveProperties)
			throws Exception {
//		String typeName = (String) entity.getTransientProperty("typeName");
//        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
        generateModuleCode(entity, archiveProperties);
	}
	
	public void generateModuleCode(Entity entity, Map<?, ?> archiveProperties) throws Exception {
//        JavaFile jFile = null;
//        File targetDir = null;
//        if (jFile != null) {
//            CodeGenHelper.writeFile(jFile, targetDir, '.');
//        }
    }

	@Override
	public void generateModuleStream(Entity entity, Map<?, ?> archiveProperties)
			throws Exception {
//		String typeName = (String) entity.getTransientProperty("typeName");
////        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
//        JavaFile jFile = null;
//        JavaFolderLocation targetDir = null;
//        if (jFile != null) {
//            CodeGenHelper.writeStream(jFile, targetDir, '.');
//        }
		
	}

}
