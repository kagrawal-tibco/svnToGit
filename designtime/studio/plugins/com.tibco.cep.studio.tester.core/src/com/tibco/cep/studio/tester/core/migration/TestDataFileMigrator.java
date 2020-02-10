package com.tibco.cep.studio.tester.core.migration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

public class TestDataFileMigrator extends DefaultStudioProjectMigrator {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator#migrateFile(java.io.File, java.io.File, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!CommonUtil.getTestDataExtensions().contains(ext)) {
			return;
		}
		monitor.subTask("- Converting Test Data file "+file.getName());
		processTestDataFile(parentFile, file);
	}

	/**
	 * @param parentFile
	 * @param file
	 */
	private void processTestDataFile(File parentFile, File file) {
		InputStream is = null;
		BufferedWriter bufWriter = null;
		try {
			if (isUTF_8Compatible(file)) {
				return; // don't  process if test data file is already UTF-8 compatible
			}
			is = new FileInputStream(file);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			XiNode root = XiParserFactory.newInstance().parse(new InputSource(is));
			testDataDefaultAllSelected(root);

			bufWriter = new BufferedWriter(new FileWriter(file));
			DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(bufWriter, "UTF-8");
			root.serialize(handler);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (bufWriter != null) {
					bufWriter.close();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	/**
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	private void testDataDefaultAllSelected(XiNode root) {
		XiNode firstChild = root.getFirstChild();
		Iterator<XiNode> nodeIterator = firstChild.getChildren();
		while (nodeIterator.hasNext()) {
			XiNode node = nodeIterator.next();
			XiNode sAttribElement = node.getAttribute(ExpandedName.makeName("isSelected"));
			if (sAttribElement == null) {
				XiFactory factory = XiSupport.getXiFactory();
				XiNode selectAttribElement = factory.createAttribute(ExpandedName.makeName("isSelected"),"true");
				node.setAttribute(selectAttribElement);
			}
		}
	}

	/**
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private boolean isUTF_8Compatible(File file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		int i = 0;
		while ((line = reader.readLine()) != null) {
			if (i == 0) {
				String s = "encoding=\"UTF-8\"";
				if (line.contains(s)) {
					return true;
				}
			}
			i++;
		}
		reader.close();
		return false;
	}

	@Override
	public int getPriority() {
		return 1; // high priority, run before others
	}

}
