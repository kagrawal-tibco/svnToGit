/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public abstract class AbstractArtifactsResponseProcessor {
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected String getArtifactPath(XiNode rootArtifactNode) {
		XiNode artifactPathNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_ARTIFACT_PATH);
		if (artifactPathNode != null) {
			return artifactPathNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * 
	 * @param historyEntryNode
	 * @return
	 */
	protected String getRevisionId(XiNode historyEntryNode) {
		XiNode revisionIdNode = 
			XiChild.getChild(historyEntryNode, ExpandedName.makeName("revisionId"));
		
		if (revisionIdNode != null) {
			String revisionIdString = revisionIdNode.getStringValue();
			return revisionIdString;
		}
		return null;
	}
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected String getArtifactExtension(XiNode rootArtifactNode) {
		XiNode artifactExtnNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_ARTIFACT_EXTENSION);
		if (artifactExtnNode != null) {
			return artifactExtnNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected String getArtifactStatus(XiNode rootArtifactNode) {
		XiNode artifactStatusNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_ARTIFACT_STATUS);
		if (artifactStatusNode != null) {
			return artifactStatusNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected String getArtifactOperation(XiNode rootArtifactNode) {
		XiNode artifactOperationNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_COMMITTED_ARTIFACT_OPERATION);
		if (artifactOperationNode != null) {
			return artifactOperationNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected String getArtifactType(XiNode rootArtifactNode) {
		XiNode artifactTypeNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_ARTIFACT_TYPE);
		if (artifactTypeNode != null) {
			return artifactTypeNode.getStringValue();
		}
		return null;
	}
	
	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected String getArtifactContent(XiNode rootArtifactNode) {
		XiNode artifactContentNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_ARTIFACT_CONTENT);
		if (artifactContentNode != null) {
			return artifactContentNode.getStringValue();
		}
		return null;
	}

	/**
	 * @param rootArtifactNode
	 * @return
	 */
	protected Date getArtifactUpdateTime(XiNode rootArtifactNode) {
		XiNode artifactUpdateTimeNode = 
			XiChild.getChild(rootArtifactNode, ArtifactsManagerConstants.EX_ARTIFACT_UPDATE_TIME);
		if (artifactUpdateTimeNode != null) {
			Date updateTime = null;
			try {
				long timeInMillis = Long.parseLong(artifactUpdateTimeNode.getStringValue());
				updateTime = new Date(timeInMillis);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			return updateTime;
		}
		return null;
	}

	protected boolean isErrorNode(XiNode node) {
		ExpandedName expName = node.getName();
		if (ArtifactsManagerConstants.EX_ERROR.equals(expName)) {
			return true;
		}
		return false;
	}
	
	protected Error getError(XiNode rootErrorNode) {
		Error error = new Error();
		XiNode errorCodeNode = 
			XiChild.getChild(rootErrorNode, ArtifactsManagerConstants.EX_ERROR_CODE);
		if (errorCodeNode != null) {
			error.setErrorCode(errorCodeNode.getStringValue());
		}
		XiNode errorStringNode = 
			XiChild.getChild(rootErrorNode, ArtifactsManagerConstants.EX_ERROR_STRING);
		if (errorStringNode != null) {
			error.setErrorString(errorStringNode.getStringValue());
		}
		XiNode errorDetailNode = 
			XiChild.getChild(rootErrorNode, ArtifactsManagerConstants.EX_ERROR_DETAIL);
		if (errorDetailNode != null) {
			error.setErrorDetail(errorDetailNode.getStringValue());
		}
		return error;
	}
	
	/**
	 * @param deflatedContents
	 * @return
	 * @throws IOException
	 */
	protected byte[] getInflatedContents(byte[] deflatedContents) throws IOException {
		ByteArrayOutputStream bos = null;
		try {
            Inflater decompressor = new Inflater();
		    decompressor.setInput(deflatedContents);

            bos = new ByteArrayOutputStream();
            // Compress the data
		    byte[] buf = new byte[1024];
		    while (!decompressor.finished()) {
                int count;
                try {
                    count = decompressor.inflate(buf);
                } catch (DataFormatException e) {
                    throw new RuntimeException(e);
                }
                bos.write(buf, 0, count);
		    }
		    
		    byte[] inflatedBytes = bos.toByteArray();
		    return inflatedBytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        	try {
        		if (bos != null) {
        			bos.close();
        		}
		    } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
	}
}
