/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.decisionproject.ontology.Implementation;



/**
 * @author aathalye
 *
 */
public class ProjectArtifactsHolder {
	
	private byte[] aclBytes;
	
	private byte[] earBytes;
	
	private byte[] dmBytes;
	
	private byte[] testDataBytes;
	
	private Implementation[] approvedImpls;
	
	/**
	 * @return the aclBytes
	 */
	public byte[] getAclBytes() {
		return aclBytes;
	}

	/**
	 * @param aclBytes the aclBytes to set
	 */
	public void setAclBytes(byte[] aclBytes) {
		this.aclBytes = aclBytes;
	}

	/**
	 * @return the earBytes
	 */
	public byte[] getEarBytes() {
		return earBytes;
	}

	/**
	 * @param earBytes the earBytes to set
	 */
	public void setEarBytes(byte[] earBytes) {
		this.earBytes = earBytes;
	}

	/**
	 * @return the dmBytes
	 */
	public byte[] getDmBytes() {
		return dmBytes;
	}

	/**
	 * @param dmBytes the dmBytes to set
	 */
	public void setDmBytes(byte[] dmBytes) {
		this.dmBytes = dmBytes;
	}

	/**
	 * @return the testDataBytes
	 */
	public byte[] getTestDataBytes() {
		return testDataBytes;
	}

	/**
	 * @param testDataBytes the testDataBytes to set
	 */
	public void setTestDataBytes(byte[] testDataBytes) {
		this.testDataBytes = testDataBytes;
	}

	/**
	 * @return the approvedImpls
	 */
	public Implementation[] getApprovedImpls() {
		return approvedImpls;
	}

	/**
	 * @param approvedImpls the approvedImpls to set
	 */
	public void setApprovedImpls(Implementation[] approvedImpls) {
		this.approvedImpls = approvedImpls;
	}
}
