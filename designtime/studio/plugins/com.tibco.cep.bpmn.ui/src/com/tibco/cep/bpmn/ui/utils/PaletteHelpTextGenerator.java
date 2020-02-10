/**
 * 
 */
package com.tibco.cep.bpmn.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.bpmn.ui.graph.palette.PaletteHelpmessages;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;

/**
 * @author sshekhar
 *
 */
public class PaletteHelpTextGenerator {
	
	public static Pattern tokenPattern = Pattern.compile("\\$\\{([^}]*)\\}");
	public static final String tokenStartString="${";
	public static final String tokenEndString="}";
	
	public static String getHelpText(BpmnPaletteGroupItem item,String helpSectionStr){
//		System.out.println(item.getTitle()+item.getId());
		
		String tempHelpTxt=item.getHelp(helpSectionStr);
		
		Matcher matcher = tokenPattern.matcher(tempHelpTxt); 
		while(matcher.find()){
			tempHelpTxt=tempHelpTxt.replace(tokenStartString+matcher.group(1).trim()+tokenEndString,PaletteHelpmessages.getString(matcher.group(1).trim()));
		}
		return tempHelpTxt;
	}
	
	
	
}
