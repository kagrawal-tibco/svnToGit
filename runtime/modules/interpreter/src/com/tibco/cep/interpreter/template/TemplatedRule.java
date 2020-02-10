package com.tibco.cep.interpreter.template;

import com.tibco.cep.interpreter.ExpressionScope;
import com.tibco.cep.interpreter.SimpleTuple;
import com.tibco.cep.interpreter.template.evaluator.RtEvaluatorDescriptor;
import com.tibco.cep.interpreter.template.evaluator.RuleTemplateEvaluatorDescriptorFactory;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.ConditionImpl;
import com.tibco.cep.kernel.model.rule.impl.RuleImpl;
import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.webstudio.model.rule.instance.*;

import java.lang.reflect.Method;
import java.util.*;


/**
 * User: nprade
 * Date: 2/15/12
 * Time: 11:38 AM
 */
public abstract class TemplatedRule
        extends RuleImpl {


    protected boolean configured = false;
    protected Condition[] rtBaseConditions;
    protected DependencyIndex[][] rtBaseDependencyIndices;
    protected Map<String, ? extends Tuple> rtBindings;
    protected ExpressionScope rtBindingScope;
    protected String rtId = null;
    protected ExpressionEvaluator[] rtThenEvaluators;
    private RuleTemplateEvaluatorDescriptorFactory factory;
    private String rtiName;


    @SuppressWarnings("UnusedDeclaration")
    protected TemplatedRule(
            String _name,
            String uri) {
        super(_name, uri);
    }
    
    @Override
    public String getName() {
    	return (rtiName != null && !rtiName.isEmpty()) ? rtiName : super.getName();
    }

    @SuppressWarnings("UnusedDeclaration")
    protected TemplatedRule(
            String _name,
            String uri,
            int _priority) {
        super(_name, uri, _priority);
    }


    @Override
    public void activate() {
        if (!this.isConfigured()) {
            throw new IllegalStateException("Attempted to activate a TemplatedRule that is not configured.");
        }
        super.activate();
    }


    public void configure(
            RuleTemplateInstance rti)
            throws Exception {

        this.rtId = rti.getId();
        this.rtiName = rti.getName();
        int priority = rti.getPriority();
        if (priority > 0 && priority <= 10) this.setPriority(priority);
        this.configureBindings(rti);
        this.configureConditions(rti, factory);
        this.configureAction(rti, factory);

        this.configured = true;
    }


    protected void configureAction(
            RuleTemplateInstance config,
            RuleTemplateEvaluatorDescriptorFactory factory) {

        final List<ExpressionEvaluator> evaluators = new ArrayList<ExpressionEvaluator>();

        final Iterator<ExpressionScope> scopes = this.getActionScopes().iterator();
        for (final Command command : config.getActions().getActions()) {
            final BuilderSubClause commandBuilderSubClause = command.getSubClause();
            if (commandBuilderSubClause == null) {
                evaluators.add(ConstantValueEvaluator.NULL);
            }
            else {
                final List<Filter> filters = commandBuilderSubClause.getFilters();
                if (!filters.isEmpty()) {
                    final EvaluatorDescriptor ed = factory.make(command, filters, scopes.next());
                    if (ed != null) {
                        evaluators.add(ed.getEvaluator());
                    }
                }
            }
        }
        this.rtThenEvaluators = evaluators.toArray(new ExpressionEvaluator[evaluators.size()]);
    }
    
    private ExpressionScope getScopefromAlias(List<ExpressionScope> scopes, String alias) {
        for (ExpressionScope expressionScope : scopes) {
            Class<?> clazz = expressionScope.get(alias);
            if (clazz != null) {
                //Return this scope.
                return expressionScope;
            }
        }
        return null;
    }


    protected void configureBindings(
            RuleTemplateInstance config) {

        this.rtBindingScope = new ExpressionScope();
        this.configureBindingScope();

        final Map<String, Tuple> bindingNameToValues = new LinkedHashMap<String, Tuple>();
        for (final Binding b : config.getBindings()) {
            bindingNameToValues.put(
                    b.getId(),
                    new SimpleTuple(convert(b.getValue(), this.rtBindingScope.get(b.getId()))));
        }

        this.rtBindings = Collections.unmodifiableMap(bindingNameToValues);
    }


    protected abstract void configureBindingScope();


    protected void configureConditions(
            RuleTemplateInstance config,
            RuleTemplateEvaluatorDescriptorFactory factory) {

        final MultiFilter conditionFilter = config.getConditionFilter();

        if (null == conditionFilter) {
            setConditions(rtBaseConditions);
        }
        else {
            final ExpressionScope scope = new ExpressionScope();
            scope.putAll(rtBindingScope);
            for (final Identifier id : m_identifiers) {
                scope.put(id.getName(), id.getType());
            }

            final RtEvaluatorDescriptor descriptor = factory.make(null, conditionFilter, scope);

            this.updateIdentifierDependencyBitArrayForCustomCondition(descriptor);

            final Condition[] newConditions = Arrays.copyOf(rtBaseConditions, rtBaseConditions.length + 1);

            newConditions[rtBaseConditions.length] = new CustomCondition(this, descriptor);
            setConditions(newConditions);
        }
    }


    private static Object convert(
            String stringValue,
            Class knownClass) {

        if (String.class == knownClass) {
            return stringValue;
        }
        else if (Boolean.class.isAssignableFrom(knownClass) || (boolean.class == knownClass)) {
            return TypeHelper.toBoolean(stringValue);
        }
        else if (Integer.class.isAssignableFrom(knownClass) || (int.class == knownClass)) {
            return TypeHelper.toInteger(stringValue);
        }
        else if (Calendar.class.isAssignableFrom(knownClass) || (int.class == knownClass)) {
            try {
                return TypeHelper.toDateTime(stringValue);
            } catch(Exception e) {
                return TypeHelper.toDateTime(stringValue + ".000+0000");
            }
        }
        else if (Long.class.isAssignableFrom(knownClass) || (long.class == knownClass)) {
            return TypeHelper.toLong(stringValue);
        }
        else if (com.tibco.cep.query.utils.TypeHelper.isNumericClass(knownClass)) {
            return TypeHelper.toDouble(stringValue);
        }
        else {
            return null; // todo?
        }
    }


    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TemplatedRule)
                && ((TemplatedRule) obj).getUri().equals(this.getUri());
    }


    protected abstract List<ExpressionScope> getActionScopes();


    @SuppressWarnings("UnusedDeclaration")
    public String getConfigId() {
        return this.rtId;
    }

    public String getRTIName() {
		return rtiName;
	}

	@SuppressWarnings("UnusedDeclaration")
    public RuleTemplateEvaluatorDescriptorFactory getEvaluatorDescriptorFactory() {
        return this.factory;
    }


    @Override
    public String getUri() {
        return (null == this.rtId)
                ? super.getUri()
                : "RT" + this.rtId;
    }


    @Override
    public int hashCode() {
        return (null == this.rtId)
                ? super.hashCode()
                : this.rtId.hashCode();
    }


    //    public void initialize(
//            ProjectContext projectContext,
//            TypeManager typeManager)
//            throws Exception {
//
//        this.setEvaluatorDescriptorFactory(
//                new RuleTemplateEvaluatorDescriptorFactory(projectContext, typeManager));
//    }
//
//
    public void initialize(
            RuleTemplateEvaluatorDescriptorFactory factory)
            throws Exception {

        this.setEvaluatorDescriptorFactory(factory);
    }


    public boolean isConfigured() {
        return this.configured;
    }


    @SuppressWarnings("UnusedDeclaration")
    protected Tuple makeTuple(
            Binding b) {

        return new SimpleTuple(convert(b.getValue(), this.rtBindingScope.get(b.getId())));
    }


    public void setEvaluatorDescriptorFactory(
            RuleTemplateEvaluatorDescriptorFactory factory) {
        this.factory = factory;
    }


    protected void updateIdentifierDependencyBitArrayForCustomCondition(
            RtEvaluatorDescriptor conditionDescriptor) {

        final DependencyIndex[][] dependencyIndices =
                Arrays.copyOf(rtBaseDependencyIndices, rtBaseDependencyIndices.length);

        final Map<String, Set<String>> declarationUsages = conditionDescriptor.getDeclarationUsages();

        int i = 0;
        for (final Identifier id : this.getIdentifiers()) {
            final Set<String> propNames = declarationUsages.get(id.getName());
            if (null != propNames) {
                try {
                    final Class type = id.getType();
                    if (Concept.class.isAssignableFrom(type)) {
                        final Method method = type.getMethod("getPropertyIndex_static", String.class);

                        final Set<DependencyIndex> newIndices = new HashSet<DependencyIndex>();
                        if (dependencyIndices[i] != null) {
                        	Collections.addAll(newIndices, dependencyIndices[i]);
                        }
                        for (final String propName : propNames) {
                            newIndices.add(
                                    new DependencyIndex(
                                            PropertyImpl.dirtyIndex_static((Integer)
                                                    method.invoke(null, propName))));
                        }
                        dependencyIndices[i] = newIndices.toArray(new DependencyIndex[newIndices.size()]);
                    }
                    else if (Event.class.isAssignableFrom(type)) {
                        dependencyIndices[i] = new DependencyIndex[0];
                    }
                    else {
                        // ?
                        throw new Exception("Unsupported identifier type: " + type.getName());
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);//todo explicit error
                }
            }
            i++;
        }

        setIdentifierDependencyBitArray(dependencyIndices);
    }


    public static class CustomCondition
            extends ConditionImpl {


        protected String[] rtEvalNames;
        protected String[] rtIdentifierNames;
        protected RtEvaluatorDescriptor rtEvaluatorDescriptor;


        public CustomCondition(
                Rule rule,
                RtEvaluatorDescriptor descriptor) {
            super(rule);

            this.rtEvaluatorDescriptor = descriptor;

            final LinkedHashMap<String, String> usedIdentifierMap = this.rtEvaluatorDescriptor.getUsedColumnNames();

            final List<Identifier> usedIdentifiers = new ArrayList<Identifier>();
            final List<String> usedIdentifierNames = new ArrayList<String>();
            final List<String> evalNames = new ArrayList<String>();
            evalNames.addAll(((TemplatedRule) rule).rtBindingScope.keySet());

            for (final Identifier id : rule.getIdentifiers()) {
                final String name = id.getName();
                if (usedIdentifierMap.containsKey(name)) {
                    usedIdentifiers.add(id);
                    usedIdentifierNames.add(name);
                    evalNames.add(name);
                }
            }

            m_identifiers = usedIdentifiers.toArray(new Identifier[usedIdentifiers.size()]);
            rtEvalNames = evalNames.toArray(new String[evalNames.size()]);
            rtIdentifierNames = usedIdentifierNames.toArray(new String[usedIdentifierNames.size()]);
        }


        public boolean eval(
                Object[] objects) {

            boolean $rv = true;

            if (null != rtEvaluatorDescriptor.getEvaluator()) {
                final FixedKeyHashMap<String, Tuple> rtScope = new FixedKeyHashMap<String, Tuple>(rtEvalNames);

                int i=0;
                for (final String id : rtIdentifierNames) {
                    rtScope.put(id, new com.tibco.cep.interpreter.SimpleTuple(objects[i++]));
                }
                for (final Map.Entry<String, ? extends Tuple> e : ((TemplatedRule) getRule()).rtBindings.entrySet()) {
                    rtScope.put(e.getKey(), e.getValue());
                }

                $rv = (Boolean) rtEvaluatorDescriptor.getEvaluator().evaluate(null, null, rtScope);
            }

            return $rv;
        }


        public String[] getDependsOn() {
            final Set<String> usedClassNames = this.rtEvaluatorDescriptor.getUsedClassNames();

            //todo ----------- update RTEDF to actually populate usedClassNames ----------------

            return usedClassNames.toArray(new String[usedClassNames.size()]);
        }


        public String getSourceText() {
            try {
                return new String(new byte[]{/*...*/}, "UTF-8");    //todo ?
            }
            catch (java.lang.Exception ex) {
                return "";
            }
        }


        public String toString() {
            return getSourceText() + "(" + getClass().getName() + ")";
        }
    }


}
