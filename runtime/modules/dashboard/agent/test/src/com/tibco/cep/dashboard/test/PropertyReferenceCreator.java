package com.tibco.cep.dashboard.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;

public class PropertyReferenceCreator {
	
	private static final String SEPARATOR = "#";

	private static final String[][] PROPERTY_KEYS_WITH_PREFIXES = new String[][] {
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.config.ConfigurationProperties"},
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.security.SecurityProperties"},
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.psvr.mal.MALProperties"},
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.psvr.streaming.StreamingProperties"},
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.psvr.rollover.RollOverProperties"},
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.psvr.biz.BizProperties"},
		new String[]{"be.agent.dashboard.plugin.default","com.tibco.cep.dashboard.plugin.internal.DefaultPlugInProperies"},
		new String[]{"be.agent.dashboard.plugin.beviews","com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties"},
		new String[]{"be.agent.dashboard","com.tibco.cep.dashboard.integration.be.BEIntegrationProperties"}
	};
	
	private File file;
	private BufferedWriter writer;

	private String separator;
	
	public PropertyReferenceCreator(File file, String separator) throws IOException{
		this.file = file;
		this.writer = new BufferedWriter(new FileWriter(this.file));
		if (StringUtil.isEmptyOrBlank(separator) == true){
			this.separator = SEPARATOR;
		}
		else {
			this.separator = separator;
		}
	}
	
	public void process() throws IOException {
		writer.append("Name");
		writer.append(separator);
		writer.append("Description");
		writer.append(separator);
		writer.append("Type");
		writer.append(separator);
		writer.append("Default Value");
		writer.newLine();
		writer.flush();
		for (String[] propertyKeysInfo : PROPERTY_KEYS_WITH_PREFIXES) {
			process(propertyKeysInfo[0],propertyKeysInfo[1]);
		}
	}
	
	protected void process(String propertyPrefix, String className){
		try {
			Class<? extends PropertyKeys> clazz = Class.forName(className, true, this.getClass().getClassLoader()).asSubclass(PropertyKeys.class);
			Field[] properties = clazz.getDeclaredFields();
			for (Field field : properties) {
				System.err.println(field);
				if (PropertyKey.class.isAssignableFrom(field.getType()) == true) {
					try {
						PropertyKey propertyKey = (PropertyKey) field.get(null);
						writer.append(propertyPrefix);
						writer.append(".");
						writer.append(propertyKey.getName());
						writer.append(separator);
						writer.append(propertyKey.getDescription());
						writer.append(separator);
						writer.append(propertyKey.getDataType().toString().toLowerCase());
						writer.append(separator);
						if (propertyKey.getDefaultValue() != null) {
							writer.append(String.valueOf(propertyKey.getDefaultValue()));
						}
						writer.newLine();
						writer.flush();
					} catch (IllegalArgumentException e) {
						System.err.println(className+"."+field.getName()+" is not initializable, skipping it...");
					} catch (IllegalAccessException e) {
						System.err.println(className+"."+field.getName()+" is not accessible, skipping it...");
					} catch (IOException e) {
						System.err.println("could not write reference information for "+className+"."+field.getName()+", skipping it...");
					}					
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println(className+" is not available, skipping it...");
		}
	}
	
	public static void main(String[] args) {
		File file = new File("dbagentpropref.txt");
		String separator = null;
		if (args.length != 0) {
			file = new File(args[0]);
			if (file.isDirectory() == true){
				System.err.println("Usage : java "+PropertyReferenceCreator.class.getName()+" <filename>");
				System.exit(-1);
			}
			if (args.length > 1){
				separator = args[1];
			}
		}
		try {
			PropertyReferenceCreator referenceCreator = new PropertyReferenceCreator(file, separator);
			referenceCreator.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
