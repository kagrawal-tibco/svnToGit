package com.tibco.be.functions.recorder;

import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.FileBasedRecorder;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;
/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 12, 2007
 * Time: 2:09:33 AM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog="Standard",
		category="Recorder",
		enabled = @Enabled(property="TIBCO.BE.function.catalog.recorder", value=false),
		synopsis = "Functions for Recorder")


public class RecorderFunctions {
	@com.tibco.be.model.functions.BEFunction(
			name = "startFileBasedRecorder",
			synopsis = "This method starts file based recording.",
			signature = "void startFileBasedRecorder(string dirPath, String mode)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(name="dirPath", type="String", desc="The directory path."),
					@com.tibco.be.model.functions.FunctionParamDescriptor(name="mode", type="String", desc="The mode of recording.")
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
			version = "1.0",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "This method starts file based recording.",
			cautions = "none",
			fndomain = {ACTION, CONDITION, QUERY, BUI},
			example = "")

    static public void startFileBasedRecorder(String dirPath, String mode) {
        RuleSessionImpl session = (RuleSessionImpl)RuleSessionManager.getCurrentRuleSession();
        FileBasedRecorder.start(session, dirPath, mode);
    }
	
	@com.tibco.be.model.functions.BEFunction(
			name = "stopFileBasedRecorder",
			synopsis = "This method stops file based recording.",
			signature = "void stopFileBasedRecorder(string dirPath, String mode)",
			params = {
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
			version = "1.0",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "This method stops file based recording.",
			cautions = "none",
			fndomain = {ACTION, CONDITION, QUERY, BUI},
			example = "")

    static public void stopFilerBasedRecorder() {
        FileBasedRecorder.stop((RuleSessionImpl) RuleSessionManager.getCurrentRuleSession());
    }
	
	@com.tibco.be.model.functions.BEFunction(
			name = "setFileBasedRecorderFilter",
			synopsis = "This method sets the file based recording filter.",
			signature = "void setFileBasedRecorderFilter(string functionURI)",
			params = {
					@com.tibco.be.model.functions.FunctionParamDescriptor(name="functionURI", type="String", desc="functionURI value")
			},
			freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
			version = "1.0",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "This method sets the file based recording filter.",
			cautions = "none",
			fndomain = {ACTION, CONDITION, QUERY, BUI},
			example = "")

    static public void setFileBasedRecorderFilter(String functionURI) {
        WorkingMemoryImpl workingMemory = ((WorkingMemoryImpl)((RuleSessionImpl) RuleSessionManager.getCurrentRuleSession()).getWorkingMemory());
        FileBasedRecorder fr = null;
        for (ChangeListener changeListener : workingMemory.getChangeListeners()) {
            if (changeListener instanceof FileBasedRecorder) {
                fr = (FileBasedRecorder)changeListener;
                break;
            }
        }
        fr.resetFilter();
        FileBasedRecorder.Filter f = new FileBasedRecorder.Filter(functionURI);
        fr.addFilter(f);
    }
}
