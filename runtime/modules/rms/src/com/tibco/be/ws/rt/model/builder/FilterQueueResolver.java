package com.tibco.be.ws.rt.model.builder;

import com.tibco.be.ws.functions.util.RULETEMPLATE_PARTICIPANT_EXTENSIONS;
import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.BinaryRelationalOpDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.ConstantValueLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.PrimaryExpressionLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.impl.ConstantFilterValueComponent;
import com.tibco.be.ws.rt.model.builder.impl.FilterLinkComponent;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterQueueResolver {

    private Queue<IFilterLinkDescriptor> queue;

    /**
     * Basic rule template symbols.
     */
    private List<Symbol> symbols;

    /**
     * List of rule template bindings.
     */
    private List<Binding> bindings;

    public FilterQueueResolver(Queue<IFilterLinkDescriptor> queue,
                               List<Symbol> symbols,
                               List<Binding> bindings) {
        //first element is operator, second is lhs and third is rhs
        this.queue = queue;
        this.symbols = symbols;
        this.bindings = bindings;
    }

    /**
     * Resolve binary relational operator used in filter expressions.
     * @return
     */
    public String resolveRelationalOperator() {
        IFilterLinkDescriptor topElement = queue.poll();
        if (topElement instanceof BinaryRelationalOpDescriptor) {
            return topElement.getName();
        }
        return null;
    }

    //Call from catalog function.

   /**
     * Resolve LHS part of the filter.
     * //TODO Make more optimal in terms of not having to do IO for same entity again.
     * @param scsIntegration
     * @param repoRootUrl
     * @param projectName
     * @param <T>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T extends IFilterComponent> List<T> resolveLHS(ISCSIntegration scsIntegration,
                                                           String repoRootUrl,
                                                           String projectName,
                                                           String earPath) throws Exception {
        IFilterLinkDescriptor topElement = queue.poll();
        List<T> filterComponents = new ArrayList<T>();
        //Use reference of parent link to resolve children descriptors as they come.
        String parentLink = null;
        if (topElement instanceof PrimaryExpressionLinkDescriptor) {
            List<IFilterLinkDescriptor> childrenDescriptors = ((PrimaryExpressionLinkDescriptor) topElement).getChildDescriptors();
            for (IFilterLinkDescriptor childDescriptor : childrenDescriptors) {
                parentLink =
                        resolveFilterLinkDescriptor(scsIntegration, repoRootUrl, projectName, childDescriptor, parentLink, earPath);
                FilterLinkComponent filterLinkComponent = new FilterLinkComponent(childDescriptor.getName(), parentLink);
                filterComponents.add((T)filterLinkComponent);
            }
        }
        return filterComponents;
    }

    /**
         * Resolve RHS part of the filter.
         * //TODO Make more optimal in terms of not having to do IO for same entity again.
         * @param scsIntegration
         * @param repoRootUrl
         * @param projectName
         * @param <T>
         * @return
         * @throws Exception
         */
    @SuppressWarnings("unchecked")
    public <T extends IFilterComponent> List<T> resolveRHS(ISCSIntegration scsIntegration,
                                                           String repoRootUrl,
                                                           String projectName,
                                                           String earPath) throws Exception {
        IFilterLinkDescriptor topElement = queue.poll();
        List<T> filterComponents = new ArrayList<T>();
        //Use reference of parent link to resolve children descriptors as they come.
        String parentLink = null;
        if (topElement instanceof PrimaryExpressionLinkDescriptor) {
            List<IFilterLinkDescriptor> childrenDescriptors = ((PrimaryExpressionLinkDescriptor) topElement).getChildDescriptors();
            for (IFilterLinkDescriptor childDescriptor : childrenDescriptors) {
                IFilterComponent filterLinkComponent;
                if (childDescriptor instanceof ConstantValueLinkDescriptor) {
                    filterLinkComponent = new ConstantFilterValueComponent(childDescriptor.getName());
                } else {
                    parentLink =
                            resolveFilterLinkDescriptor(scsIntegration, repoRootUrl, projectName, childDescriptor, parentLink, earPath);
                    filterLinkComponent = new FilterLinkComponent(childDescriptor.getName(), parentLink);
                }
                filterComponents.add((T)filterLinkComponent);
            }
        }
        return filterComponents;
    }

    /**
     *
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param childDescriptor
     * @param parentLink
     * @return
     * @throws Exception
     */
    private String resolveFilterLinkDescriptor(ISCSIntegration scsIntegration,
                                               String repoRootURL,
                                               String projectName,
                                               IFilterLinkDescriptor childDescriptor,
                                               String parentLink,
                                               String earPath) throws Exception {
        String name = childDescriptor.getName();
        //Check against one in symbols
        if (parentLink == null) {
            //This is element in scope.
            for (Symbol symbol : symbols) {
                if (symbol.getIdName().equals(name)) {
                    //Match found
                    String symbolType = symbol.getType();
                    String symbolExtension = WebstudioFunctionUtils.resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, symbol, earPath);
                    symbolType = (symbolExtension != null) ? symbolType + "." + symbolExtension : symbolType;
                    return symbolType;
                }
            }
            //Check in bindings
            for (Binding binding : bindings) {
                if (binding.getIdName().equals(name)) {
                    //Match found
                    return binding.getType();
                }
            }
        } else {
            int dotIndex = parentLink.indexOf('.');
            //Remove extension part if any from parentlink
            parentLink = (dotIndex > 0) ? parentLink.substring(0, dotIndex) : parentLink;
            //The rule participant in this.
            RuleParticipant ruleParticipant = null;
            for (RULETEMPLATE_PARTICIPANT_EXTENSIONS ruleParticipantExtn : RULETEMPLATE_PARTICIPANT_EXTENSIONS.values()) {
                ruleParticipant = searchRuleParticipant(scsIntegration, repoRootURL, projectName, parentLink, ruleParticipantExtn.getLiteral());
                if (ruleParticipant != null) {
                    break;
                }
            }
            PropertyDefinition matchingPropertyDefinition = null;
            String propertyType;
            String parentLinkExtension;
            if (ruleParticipant instanceof Concept) {
                Concept concept = ((Concept)ruleParticipant);
                while (matchingPropertyDefinition == null) {
                    matchingPropertyDefinition = concept.getPropertyDefinition(name, true);
                    if (matchingPropertyDefinition == null) {
                        String superConceptPath = concept.getSuperConceptPath();
                        if (superConceptPath != null && !superConceptPath.isEmpty()) {
                            concept = (Concept)searchRuleParticipant(scsIntegration,
                                    repoRootURL, projectName, superConceptPath, CommonIndexUtils.CONCEPT_EXTENSION);
                        }
                    }
                }
            } else if (ruleParticipant instanceof Event) {
                matchingPropertyDefinition = ((Event)ruleParticipant).getPropertyDefinition(name, false);
            }
            if (matchingPropertyDefinition != null) {
                PROPERTY_TYPES property_types = matchingPropertyDefinition.getType();
                if (property_types == PROPERTY_TYPES.CONCEPT || property_types == PROPERTY_TYPES.CONCEPT_REFERENCE) {
                    parentLinkExtension = CommonIndexUtils.CONCEPT_EXTENSION;
                    propertyType = matchingPropertyDefinition.getConceptTypePath() + "." + parentLinkExtension;
                } else {
                    propertyType = property_types.getName();
                }
                return propertyType;
            }
        }
        return null;
    }

    /**
     *
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param artifactPath
     * @param ruleParticipantExtn
     * @return
     * @throws Exception
     */
    private RuleParticipant searchRuleParticipant(ISCSIntegration scsIntegration, 
                                                  String repoRootURL,
                                                  String projectName,
                                                  String artifactPath,
                                                  String ruleParticipantExtn) throws Exception {
        RuleParticipant ruleParticipant = null;
        if (scsIntegration.fileExists(repoRootURL, projectName, artifactPath, ruleParticipantExtn)) {
            //Actually do the loading here.
            String entityContents = scsIntegration.showFileContents(repoRootURL,
                                                                    projectName,
                                                                    artifactPath,
                                                                    ruleParticipantExtn,
                                                                    null, null);
            ruleParticipant = (RuleParticipant)CommonIndexUtils.deserializeEObjectFromString(entityContents);
        }
        return ruleParticipant;
    }
}
