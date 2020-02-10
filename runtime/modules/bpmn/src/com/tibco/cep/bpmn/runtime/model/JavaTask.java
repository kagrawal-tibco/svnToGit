package com.tibco.cep.bpmn.runtime.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * This annotation identifies a Class that can be used for BPMN Java Task
 * 
 * <pre>
 *  package com.tibco
 * 
 *   import java.util.Calendar;
 *  import com.tibco.cep.bpmn.runtime.activity.tasks.JavaTaskContext;
 *  import com.tibco.cep.bpmn.runtime.activity.tasks.ModelType;
 *  import com.tibco.cep.bpmn.runtime.activity.tasks.ModelTypeMap;
 *  import com.tibco.cep.bpmn.runtime.activity.tasks.TaskContextType;
 *  import com.tibco.cep.bpmn.runtime.model.JavaTask;
 *  import com.tibco.cep.bpmn.runtime.model.JavaTaskMethod;
 *  import com.tibco.cep.runtime.model.element.Concept;
 *  import com.tibco.cep.runtime.session.RuleServiceProvider;
 *  import com.tibco.cep.runtime.session.RuleSession;
 *  
 * {@literal @}JavaTask
 * public class TestJavaTask {
 *    {@literal @}JavaTaskContext(TaskContextType.NAME)
 *    String taskName;
 *    {@literal @}JavaTaskContext(TaskContextType.RULE_SERVICE_PROVIDER)
 *    RuleServiceProvider rsp;
 *    {@literal @}JavaTaskContext(TaskContextType.RULE_SESSION)
 *    RuleSession ruleSession;
 *     
 *    {@literal @}JavaTaskMethod
 *    {@literal @}ModelTypeMap(type=ModelType.CONTAINED_CONCEPT, uri="/Concepts/ConceptA")
 *    public  Concept  taskFunction(
 *            {@literal @}ModelTypeMap(type=ModelType.INT)  int a,
 *            {@literal @}ModelTypeMap(type=ModelType.STRING) String b,
 *            {@literal @}ModelTypeMap(type=ModelType.DATETIME) Calendar c,
 *            {@literal @}ModelTypeMap(type=ModelType.CONTAINED_CONCEPT, uri="/Concepts/ConceptA") Concept cc,
 *            {@literal @}ModelTypeMap(type=ModelType.PROCESS, uri="/Processes/ProcessA") Concept pp) {
 *        return null;
 *    }
 * }
 * </pre>
 * 
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface JavaTask {

}
