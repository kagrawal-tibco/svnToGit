package com.tibco.cep.dashboard.plugin.beviews;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import com.tibco.cep.dashboard.cep_dashboardVersion;
import com.tibco.cep.dashboard.config.ClientConfiguration;

class ClientConfigurationFile {

	private static final String CLIENT_CONFIG_XML_HEADER =
		"<!-- \n" +
		"	This file is auto-generated every time dashboard agent starts\n" +
		"	All modifications to the file will be lost\n" +
		"	Please update the appropriate cdd file to reflect changes needed in this file\n" +
		"   Created On {0} by {1}\n" +
		"-->\n";

	private File file;

	private BufferedWriter writer;

	void save(ClientConfiguration clientConfiguration, File file) throws IOException{
		//validate the arguments
		if (file == null || clientConfiguration == null) {
			throw new IllegalArgumentException();
		}
		if (file.isDirectory() == true) {
			throw new IllegalArgumentException(file.getAbsolutePath()+" is not a file");
		}
		//create the file if it does not exist
		if (file.exists() == false) {
			boolean created = file.createNewFile();
			if (created == false) {
				throw new IOException("could not create "+file.getAbsolutePath());
			}
		}
		this.file = file;
		//create content of the file
		StringBuilder sb = new StringBuilder();
		//append header
		sb.append(MessageFormat.format(CLIENT_CONFIG_XML_HEADER, new Date(), cep_dashboardVersion.getComponent() + " " + cep_dashboardVersion.getVersion()));
		//append the actual content
		sb.append(clientConfiguration.toXML(true));

		writer = new BufferedWriter(new FileWriter(file));
		writer.write(sb.toString());
		writer.flush();
		//we do not close the writer, that prevent deletion & updation of the file from out side
	}

	void delete() throws IOException {
		if (writer != null) {
			writer.close();
		}
		file.delete();
	}

	String getLocation(){
		return file.getAbsolutePath();
	}

}
