package com.tibco.tea.agent.be.migration.file.generator;

/**
 * @author ssinghal
 *
 */

import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;

public interface BeTeaAgentExportService {
	
	public void init(Properties configuration);
	
	public List<MApplication> generate(DataSource zipFile, String source) throws Exception;
	public String[][] generateUsersAndRoles(DataSource xmlFile) throws Exception;
	public void deleteTempFiles();

}
