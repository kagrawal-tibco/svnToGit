/**
 * 
 */
package com.tibco.cep.studio.ui.editors.globalvar;

import java.util.Comparator;

import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesDescriptor;

/**
 * @author mgujrath
 *
 */
public class NameComparator implements Comparator<GlobalVariablesDescriptor>{
		@Override
		public int compare(GlobalVariablesDescriptor o1,
				GlobalVariablesDescriptor o2) {
			// TODO Auto-generated method stub
			return o1.getName().compareTo(o2.getName());
		}
}
