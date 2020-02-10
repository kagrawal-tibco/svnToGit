package com.tibco.cep.bpmn.core.codegen;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.Messages;

/**
 * @author pdhar
 *
 */
public class CodegenError {
	public static final int SEMANTIC_TYPE = 1;
    public static final int WARNING_TYPE = 2;
    public static final int MODEL_TYPE = 3;
    public static final int EMF_TYPE = 4;
    public static final int PLATFORM_RESOURCE_TYPE = 5;
    public static final int INTERNAL_TYPE = 6;
	
	protected String message;
	protected int errorType;
	protected Throwable exceptionInfo;
	
	protected EClass modelClass;
	protected EObject modelObject;
	protected IResource platformResource;
	protected Object source;
	
	
	
	/**
	 * @param message
	 * @param errorType
	 * @param exceptionInfo
	 */
	public CodegenError(String message, int errorType, Throwable exceptionInfo) {
		super();
		this.message = message;
		this.errorType = errorType;
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * @param message
	 * @param errorType
	 * @param modelObject
	 */
	public CodegenError(String message, int errorType, EObject modelObject,Throwable exceptionInfo) {
		super();
		this.message = message;
		this.errorType = errorType;
		this.modelObject = modelObject;
		this.modelClass = modelObject.eClass();
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * @param message
	 * @param errorType
	 * @param platformResource
	 * @param exceptionInfo
	 */
	public CodegenError(String message, int errorType,
			IResource platformResource, Throwable exceptionInfo) {
		super();
		this.message = message;
		this.errorType = errorType;
		this.platformResource = platformResource;
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * @param message
	 * @param errorType
	 * @param modelClass
	 * @param exceptionInfo
	 */
	public CodegenError(String message, int errorType, EClass modelClass,
			Throwable exceptionInfo) {
		super();
		this.message = message;
		this.errorType = errorType;
		this.modelClass = modelClass;
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the errorType
	 */
	public int getErrorType() {
		return errorType;
	}

	/**
	 * @return the exceptionInfo
	 */
	public Throwable getExceptionInfo() {
		return exceptionInfo;
	}

	/**
	 * @return the modelClass
	 */
	public EClass getModelClass() {
		return modelClass;
	}

	/**
	 * @return the modelObject
	 */
	public EObject getModelObject() {
		return modelObject;
	}

	/**
	 * @return the platformResource
	 */
	public IResource getPlatformResource() {
		return platformResource;
	}
	
	
	/**
	 * @return
	 */
	public boolean isSemanticError() {
        return errorType == SEMANTIC_TYPE;
    }
    
    /**
     * @return
     */
    public boolean isWarning() {
        return errorType == WARNING_TYPE;
    }
    
    /**
     * @return
     */
    public boolean isInternalError() {
        return errorType == INTERNAL_TYPE;
    }

	/**
	 * @return the source
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Object source) {
		this.source = source;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static String getErrorTypeStr(int type) {
		switch(type) {
		case INTERNAL_TYPE:
			return Messages.getString("bpmn.codegen.error.type.internal").trim(); // $NON-NLS-1$
		case EMF_TYPE:
			return Messages.getString("bpmn.codegen.error.type.model.type").trim(); // $NON-NLS-1$
		case MODEL_TYPE:
			return Messages.getString("bpmn.codegen.error.type.model.class").trim(); // $NON-NLS-1$
		case SEMANTIC_TYPE:
			return Messages.getString("bpmn.codegen.error.type.semantic").trim(); // $NON-NLS-1$
		case WARNING_TYPE:
			return Messages.getString("bpmn.codegen.error.type.warning").trim(); // $NON-NLS-1$
		}
		return "";
	}
	
	
	 public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(Messages.getString("bpmn.codegen.error")); //$NON-NLS-1$
		sb.append(Messages.getString("bpmn.codegen.error.type",// $NJON-NLS-1$
				getErrorTypeStr(this.errorType))); 
		if (this.message != null) {
			sb.append(Messages.getString("bpmn.codegen.error.message",// $NJON-NLS-1$
					this.message).trim()); 
		}
		if (this.modelClass != null) {
			sb.append(Messages.getString("bpmn.codegen.error.model.class",// $NJON-NLS-1$
					this.modelClass.getName()).trim()); 
		}
		if (this.modelObject != null) {
			sb.append(Messages.getString("bpmn.codegen.error.model.class",// $NJON-NLS-1$
					this.modelObject.toString()).trim()); 
		}
		if (this.platformResource != null) {
			sb.append(Messages.getString(
					"bpmn.codegen.error.platform.resource",// $NJON-NLS-1$
					this.platformResource.getFullPath().toPortableString()).trim()); 
		}
		if (this.exceptionInfo != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.flush();
			this.exceptionInfo.printStackTrace(pw);
			sb.append(Messages.getString(
					"bpmn.codegen.error.platform.exception",// $NJON-NLS-1$
					sw.getBuffer()).trim()); 
		}

		return sb.toString();
	}
	
	
	

}
