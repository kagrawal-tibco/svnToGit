package com.tibco.cep.dashboard.psvr.text.url;

import com.tibco.cep.kernel.service.logging.Level;

public class SubstitutionUtil {
	
	public static String resolve(SubstitutionContext substitutionContext, String refString) {
		if (SubstitutionInfo.isReference(substitutionContext.getLogger(),refString) == false) {
			return refString;
		}
		SubstitutionInfo subsInfo = new SubstitutionInfo(substitutionContext.getLogger(),refString);
		return resolve(substitutionContext, subsInfo);
	}

	public static String resolve(SubstitutionContext substitutionContext, SubstitutionInfo subsInfo) {
		try {
			StringBuilder sb = new StringBuilder();
			if (subsInfo.getType().equals(SubstitutionType.CONSTANT) == false){
				sb.append(subsInfo.getPrepender());
			}
			sb.append(subsInfo.getType().getSubstitutor().substitute(subsInfo.getKey(), substitutionContext));
			if (subsInfo.getType().equals(SubstitutionType.CONSTANT) == false){
				sb.append(subsInfo.getAppender());
			}
			return sb.toString();
		} catch (Exception ex) {
			substitutionContext.getLogger().log(Level.WARN, "Parameter substitution failed for " + subsInfo, ex);
			return null;
		}
	}

}