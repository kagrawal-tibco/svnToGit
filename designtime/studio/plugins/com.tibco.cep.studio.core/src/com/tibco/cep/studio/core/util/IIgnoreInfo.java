package com.tibco.cep.studio.core.util;

public interface IIgnoreInfo {
		/**
		 * Return the string specifying the pattern of this ignore. The string
		 * may include the wildcard characters '*' and '?'. If you wish to
		 * include either of these characters verbatim (i.e. you do not wish
		 * them to expand to wildcards), you must escape them with a backslash '\'.
		 * <p>
		 * If you are using string literals in Java to represent the patterns, don't 
		 * forget escape characters are represented by "\\".
		 * 
		 * @return the pattern represented by this ignore info
		 */
		public String getPattern();
		/**
		 * Return whether or not this ignore info is enabled. A disabled ignore
		 * info remains in the global ignore list, but no attempt is made to match
		 * with it to determine resource ignore state.
		 * 
		 * @return whether the ignore info is enabled
		 */
		public boolean getEnabled();
}
