/**
 * 
 */
package com.tibco.cep.studio.core.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The class is used to migrate the pom.xml file
 * 
 * @author dijadhav
 *
 */
public class PomFileMigrator extends DefaultStudioProjectMigrator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.core.migration.IStudioProjectMigrator#getPriority()
	 */
	@Override
	public int getPriority() {
		return 10;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator#
	 * migrateFile(java.io.File, java.io.File,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {

		if ("pom.xml".equalsIgnoreCase(file.getName())) {
			monitor.subTask("- Converting Project pom file " + file.getName());
			processBEConfigFile(file);
			return;
		}

	}

	private static void processBEConfigFile(File file) {
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			// doc.getDocumentElement().normalize();

			// In migration add maven compiler version
			NodeList properties = doc.getElementsByTagName("properties");
			if (null != properties && properties.getLength() == 1) {
				Node propertiesNode = properties.item(0);
				NodeList childNodes = propertiesNode.getChildNodes();
				boolean hasSourceCompiler = false;
				boolean hasTargetCompiler = false;
				for (int temp = 0; temp < childNodes.getLength(); temp++) {
					Node nNode = childNodes.item(temp);

					if (null != nNode) {
						String name = nNode.getNodeName();
						switch (name.trim()) {
						case "maven.compiler.source":
							nNode.setTextContent("11");
							hasSourceCompiler = true;
							break;
						case "maven.compiler.target":
							nNode.setTextContent("11");
							hasTargetCompiler = true;
							break;
						default:
							break;
						}
					}

				}

				if (!hasSourceCompiler) {
					Element sourceCompiler = doc.createElement("maven.compiler.source");
					sourceCompiler.appendChild(doc.createTextNode("11"));
					propertiesNode.appendChild(sourceCompiler);
				}
				if (!hasTargetCompiler) {
					Element targetCompiler = doc.createElement("maven.compiler.target");
					targetCompiler.appendChild(doc.createTextNode("11"));
					propertiesNode.appendChild(targetCompiler);
				}
			}

			String installerLocation = "";
			String useBeHome = "";
			String be_home = "";
			NodeList baseImageConfigNodeList = doc.getElementsByTagName("baseImageConfig");
			if (null != baseImageConfigNodeList && baseImageConfigNodeList.getLength() == 1) {
				Node baseImageConfigNode = baseImageConfigNodeList.item(0);
				if (null != baseImageConfigNode) {
					NodeList childNodes = baseImageConfigNode.getChildNodes();
					for (int temp = 0; temp < childNodes.getLength(); temp++) {
						Node nNode = childNodes.item(temp);
						if (null != nNode) {
							if (nNode.getNodeType() == Node.ELEMENT_NODE
									&& nNode.getNodeName().equals("installers_location")) {
								installerLocation = nNode.getTextContent();
							}
							if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.getNodeName().equals("useBEHome")) {
								useBeHome = nNode.getTextContent();
							}
							if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.getNodeName().equals("be_home")) {
								be_home = nNode.getTextContent();
							}
						}

					}
					baseImageConfigNode.getParentNode().removeChild(baseImageConfigNode);

					// update appImageConfig
					NodeList appImageConfigList = doc.getElementsByTagName("appImageConfig");
					if (null != appImageConfigList && appImageConfigList.getLength() == 1) {
						Node appImageConfigNode = appImageConfigList.item(0);
						childNodes = appImageConfigNode.getChildNodes();
						for (int temp = 0; temp < childNodes.getLength(); temp++) {
							Node nNode = childNodes.item(temp);

							if (null != nNode) {
								String name = nNode.getNodeName();
								switch (name.trim()) {
								case "baseBEImage":
								case "maintainer":
								case "email":
								case "labels":
								case "overwriteDockerfile":
									appImageConfigNode.removeChild(nNode);
									break;

								default:
									break;
								}
							}

						}
						Element useBEHomeEle = doc.createElement("useBEHome");// usebe home flag
						useBEHomeEle.appendChild(doc.createTextNode(useBeHome));
						appImageConfigNode.appendChild(useBEHomeEle);

						Element be_homeEle = doc.createElement("be_home");// be home
						be_homeEle.appendChild(doc.createTextNode(be_home));
						appImageConfigNode.appendChild(be_homeEle);

						Element installerLocationEle = doc.createElement("installers_location");// installers_location
						installerLocationEle.appendChild(doc.createTextNode(installerLocation));
						appImageConfigNode.appendChild(installerLocationEle);

					}
				}

			}
			

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(file);

			transformer.transform(source, result);

			String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
			String xml = readFile(file);
			xml = prefix + xml;
			stringToFile(file, xml);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String readFile(File pomFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(pomFile));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					if (line.equals("--><project>")) {
						line = "-->\n<project>";
					}
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	private static void stringToFile(File pomFile, String text) {
		try {
			PrintWriter out = new PrintWriter(pomFile);
			out.println(text);
			out.close();
		} catch (Exception e) {

		}
	}
}
