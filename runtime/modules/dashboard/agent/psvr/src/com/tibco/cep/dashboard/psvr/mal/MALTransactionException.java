package com.tibco.cep.dashboard.psvr.mal;

public class MALTransactionException extends MALException {

	private static final long serialVersionUID = 4817683161842412347L;
	
	public static enum CAUSE_OP {CREATION,EXECUTION,COMMIT,ROLLBACK}
	
	private CAUSE_OP causeOperation;

	public MALTransactionException(CAUSE_OP operation) {
		this.causeOperation = operation;
	}

	public MALTransactionException(CAUSE_OP operation,String message) {
		super(message);
		this.causeOperation = operation;
	}

	public MALTransactionException(CAUSE_OP operation,Throwable cause) {
		super(cause);
		this.causeOperation = operation;
	}

	public MALTransactionException(CAUSE_OP operation,String message, Throwable cause) {
		super(message, cause);
		this.causeOperation = operation;
	}

	public CAUSE_OP getCauseOperation() {
		return causeOperation;
	}

}
