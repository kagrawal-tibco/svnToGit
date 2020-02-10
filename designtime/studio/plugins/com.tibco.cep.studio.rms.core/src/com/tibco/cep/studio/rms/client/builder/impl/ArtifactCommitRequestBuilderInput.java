/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_COMMIT;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_CONTENT;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_EXTENSION;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_TYPE;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_PATH;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_COMMITTED_ARTIFACT_OPERATION;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_PROJECT_NAMES;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_UPDATE_TIME;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants.EX_ARTIFACT_COMMIT_VERSION;

import java.util.Date;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.model.ArtifactCommitMetaData;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Build each artifact commit data
 * e.g:
 * <b>
 * 	&lt;ns0:AMS_C_ArtifactCommitted extId = \"123456@/Concepts/Domain\" xmlns:ns0=\"www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_ArtifactCommitted\" Id=\"81\">\n" +
        "      &lt;artifactPath&gt;/Concepts/Domain&lt;/artifactPath&gt;\n" +
        "      &lt;artifactContent&gt;H4sIAAAAAAAAAK2RQUvDMBiG74L/IeTeJW3XzpXWUdNOBirCELzW5LOLNElJ4zr/vVnRwvDqJZDk&#xD;eR++vMk3J9WhI9hBGl3gcEExAs2NkLotcLlnux3e3F5f5UJllVGN1OikZDYHonPAK/SQ+fMCH5zr&#xD;M0LGcVwY1S6Mbcnr425GhktkjCciojT02MOeH0A1gdSDazSH35RQc4hwo4iTb9wQDj0RMMhWO6nA&#xD;X1ggygjoiJjmxEg3Coa+4VBgwowX9m4gGL2bToC9PDujBa5+gl7Lrezd9EKM7l92VYFpHNIkDe+C&#xD;LYuroK7XUVDWURJsV6wKt8t1UrEbjMyowT5b8wHcPU1SZkFIxxoryr7vJG/OWuwrRSgH7ayEAfle&#xD;MvfVe9rXvPfVd/BnimPTfXqgZHVFKU1WS7/GaZhGaYzJf+mWaZKmky4n84/73TdCw38CJwIAAA==&#xD;&lt;/artifactContent&gt;\n" +
        "      &lt;artifactFileExtension&gt;domain&lt;/artifactFileExtension&gt;\n" +
        "   &lt;/ns0:AMS_C_ArtifactCommitted&gt;
 * </b> 
 * @author aathalye
 *
 */
public class ArtifactCommitRequestBuilderInput extends AbstractRequestBuilderInput {
	
	
	private ArtifactCommitMetaData artifactCommitMetaData;
	
	/**
	 * @param artifact
	 */
	public ArtifactCommitRequestBuilderInput(ArtifactCommitMetaData artifactCommitMetaData) {
		this.artifactCommitMetaData = artifactCommitMetaData;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.client.builder.impl.ArtifactContentsRequestBuilderInput#buildXMLPart()
	 */
	@Override
	protected XiNode buildXMLPart() {
		Artifact artifact = artifactCommitMetaData.getArtifact();
		//Get its path and type
		String artifactPath = artifact.getArtifactPath();
		//The path contains
		String artifactType = artifact.getArtifactExtension();
		String artifactExtension = artifact.getArtifactExtension();
		String artifactContent = artifact.getArtifactContent();
		String operation = artifactCommitMetaData.getArtifactOperation().getLiteral();
		Date updateTime = artifact.getUpdateTime();
		String commitVersion = artifact.getCommittedVersion();
		
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		//Create element artifact
		XiNode artifactElement = factory.createElement(EX_ARTIFACT_COMMIT);
		root.appendChild(artifactElement);
		
		String patternId = artifactCommitMetaData.getPatternId();
		String extId = patternId + "@" + artifactPath;
		
		XiNode extIdAttr = factory.createAttribute("extId", extId);
		artifactElement.setAttribute(extIdAttr);
		
		XiNode artifactPathElement = factory.createElement(EX_ARTIFACT_PATH);
		artifactPathElement.setStringValue(artifactPath);
		artifactElement.appendChild(artifactPathElement);
		
		XiNode artifactTypeElement = factory.createElement(EX_ARTIFACT_TYPE);
		artifactTypeElement.setStringValue(artifactType);
		artifactElement.appendChild(artifactTypeElement);
		
		XiNode artifactExtnElement = factory.createElement(EX_ARTIFACT_EXTENSION);
		artifactExtnElement.setStringValue(artifactExtension);
		artifactElement.appendChild(artifactExtnElement);
		
		XiNode artifactContentElement = factory.createElement(EX_ARTIFACT_CONTENT);
		artifactContentElement.setStringValue(artifactContent);
		artifactElement.appendChild(artifactContentElement);
		
		XiNode projectElement = factory.createElement(EX_PROJECT_NAMES);
		projectElement.setStringValue(artifactContent);
		artifactElement.appendChild(artifactContentElement);
		
		XiNode operationElement = factory.createElement(EX_COMMITTED_ARTIFACT_OPERATION);
		operationElement.setStringValue(operation);
		artifactElement.appendChild(operationElement);

		if (updateTime != null) {
			XiNode updateTimeElement = factory.createElement(EX_ARTIFACT_UPDATE_TIME);
			updateTimeElement.setStringValue(String.valueOf(updateTime.getTime()));
			artifactElement.appendChild(updateTimeElement);
		}

		if (commitVersion != null) {
			XiNode commitVersionElement = factory.createElement(EX_ARTIFACT_COMMIT_VERSION);
			commitVersionElement.setStringValue(commitVersion);
			artifactElement.appendChild(commitVersionElement);
		}

		return root;
	}
}
