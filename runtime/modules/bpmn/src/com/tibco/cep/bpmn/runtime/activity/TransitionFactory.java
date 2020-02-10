package com.tibco.cep.bpmn.runtime.activity;


/**
 * @author pdhar
 *
 */
public class TransitionFactory {

    public static TransitionFactory INSTANCE = new TransitionFactory();


    private TransitionFactory() {

    }


    public Transition newTransition( String transitionId) throws Exception
    {
        Transition transition = new DefaultTransition();
        TaskRegistry.getInstance().addTransition(transitionId, transition);

       return transition;
    }



    
//    public Transition newTransition(InitContext context, String transitionName) throws Exception
//    {
//    	final String type = BpmnMetaModel.INSTANCE.getExpandedName(BpmnModelClass.SEQUENCE_FLOW).toString();
//        Class<? extends Transition> transitionClass = TaskRegistry.getInstance().getTransitionType(type);
//        if (transitionClass == null) throw new Exception(String.format("Invalid tra type name :%s", type));
//
//        Transition transition = transitionClass.newInstance();
//
//        transition.init(context, new Object[] {transitionName});
//
//       return transition;
//    }


}
