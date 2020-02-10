/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerConstants;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ProjectsListResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author aathalye
 *
 */
public class ProjectsListResponseProcessor<O extends Object, R extends IResponse> implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor)
	 */
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#processResponse(java.io.InputStream)
	 */
	public IResponse processResponse(final OperationType responseType,
			HttpResponse httpResponse,
			                         final InputStream responseStream) throws ResponseProcessingException {
		if (responseType.equals(OperationType.PROJECTS_LIST)) {
			ProjectsListResponse response = new ProjectsListResponse();
			// Deal with the response.
			XiNode rootNode = null;
			try {
				InputSource inputSource = new InputSource(responseStream);
				rootNode = RMSUtil.getXiNodeFromSource(inputSource);
				XiNode servedProjectsNode = getRootServedProjectsNode(rootNode);
				String[] projectNames = getProjects(servedProjectsNode);
				response.holdResponseObject(projectNames);
				return response;
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		} 
		return processor.processResponse(responseType, httpResponse, responseStream);
	}
	
	private XiNode getRootServedProjectsNode(XiNode rootNode) {
		//Get root child
		XiNode projectsRootNode = 
			XiChild.getChild(rootNode, ArtifactsManagerConstants.EX_AMS_C_SERVEDPROJECTS_NAME);
		return projectsRootNode;
	}
	
	@SuppressWarnings("unchecked")
	private String[] getProjects(XiNode servedProjectsNode) {
		List<String> projectNames = new ArrayList<String>();
		Iterator<XiNode> projectNamesNodes = 
			XiChild.getIterator(servedProjectsNode, ArtifactsManagerConstants.EX_PROJECT_NAMES);
		while (projectNamesNodes.hasNext()) {
			XiNode projectNamesNode = projectNamesNodes.next();
			String projectName = projectNamesNode.getStringValue();
			projectNames.add(projectName);
		}
		return projectNames.toArray(new String[projectNames.size()]);
	}
}