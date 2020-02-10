package com.tibco.cep.bpmn.core.doc;

import java.io.File;

import com.tibco.cep.studio.core.util.DocUtils;

/**
 * This POJO class is the parent to all the classes that provide data to
 * StringTemplates.
 * 
 * @author moshaikh
 * 
 */
public class DocumentDataBean {

	private String currentVersionName;
	private String sinceVersion;

	/**
	 * Absolute path for documentation file that should be created using this
	 * bean and StringTemplate.
	 */
	private String docFilePath;

	/**
	 * Name of the Template that is to be used while creating documentation
	 * file.
	 */
	private String templateName;

	/**
	 * Root directory of generated documents.
	 */
	private String docRootDir;

	/**
	 * The id of the entity that isrepresented by this bean (A unique identifier
	 * that is used to store all created beans in
	 * {@link DocumentationBeansStore}).
	 */
	private String entityId;

	public DocumentDataBean(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * Returns a path to file represented by this bean relative to documentation
	 * root directory.
	 * 
	 * @return
	 */
	public String getFileRelativePath() {
		return docFilePath.substring(docFilePath.lastIndexOf("/doc/") + 5);
	}

	public String getDocFilePath() {
		return docFilePath;
	}

	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getIndexFilePath() {
		return DocUtils.computeRelativePath(
				new File(docFilePath).getParentFile(), new File(docRootDir
						+ "/" + "index.html"));
	}

	public String getDocRootDir() {
		return docRootDir;
	}

	public void setDocRootDir(String docRootDir) {
		this.docRootDir = docRootDir;
	}

	public String getCurrentVersionName() {
		return currentVersionName;
	}

	public void setCurrentVersionName(String currentVersionName) {
		this.currentVersionName = currentVersionName;
	}

	public String getSinceVersion() {
		return sinceVersion;
	}

	public void setSinceVersion(String sinceVersion) {
		this.sinceVersion = sinceVersion;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * Computes the linkURL for documentHyperLink if not already present. Uses
	 * targetId attribute of {@link DocumentHyperLink} object to fetch target
	 * bean for its location.
	 * 
	 * @param documentHyperLink
	 * @return
	 */
	protected void computeHyperLink(DocumentHyperLink documentHyperLink) {
		if (documentHyperLink == null || documentHyperLink.getLink() != null
				|| documentHyperLink.getTargetId() == null) {
			return;
		}
		DocumentDataBean targetBean = DocumentationBeansStore
				.getBean(documentHyperLink.getTargetId());
		if (targetBean == null || targetBean.getDocFilePath() == null) {
			return;
		}
		documentHyperLink.setLink(DocUtils.computeRelativePath(new File(
				getDocFilePath()).getParentFile(),
				new File(targetBean.getDocFilePath())));
	}
}