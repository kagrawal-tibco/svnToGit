package com.tibco.cep.dashboard.psvr.biz;

public class RawBizResponseImpl extends BaseBizResponse {
	
	public RawBizResponseImpl(String content) {
		super(SUCCESS_STATUS,content);
	}

	@Override
	public String toString() {
		return message;
	}

}
