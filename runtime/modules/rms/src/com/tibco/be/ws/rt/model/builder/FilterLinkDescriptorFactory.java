package com.tibco.be.ws.rt.model.builder;

import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.BinaryRelationalOpDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.ConstantValueLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.ExpressionLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.IdentifierLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.PrimaryExpressionLinkDescriptor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

import java.lang.reflect.Constructor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilterLinkDescriptorFactory {

    @SuppressWarnings("unchecked")
    public static <F extends IFilterLinkDescriptor> F createDescriptor(RulesASTNode node) throws Exception {
        switch (node.getType()) {
            case RulesParser.EXPRESSION : {
                Constructor<ExpressionLinkDescriptor> constructor =
                        ExpressionLinkDescriptor.class.getConstructor(RulesASTNode.class);
                return (F)constructor.newInstance(node);
            }
            case RulesParser.Identifier : {
                Constructor<IdentifierLinkDescriptor> constructor =
                        IdentifierLinkDescriptor.class.getConstructor(RulesASTNode.class);
                return (F)constructor.newInstance(node);
            }
            case RulesParser.PRIMARY_EXPRESSION : {
                Constructor<PrimaryExpressionLinkDescriptor> constructor =
                        PrimaryExpressionLinkDescriptor.class.getConstructor(RulesASTNode.class);
                return (F)constructor.newInstance(node);
            }
            case RulesParser.GT :
            case RulesParser.LT :
            case RulesParser.GE :
            case RulesParser.LE :
            case RulesParser.EQ : {
                Constructor<BinaryRelationalOpDescriptor> constructor =
                        BinaryRelationalOpDescriptor.class.getConstructor(RulesASTNode.class);
                return (F)constructor.newInstance(node);
            }
            case RulesParser.StringLiteral :
            case RulesParser.FloatingPointLiteral :
            case RulesParser.NullLiteral :
            case RulesParser.DecimalLiteral :
            case RulesParser.HexLiteral :
            case RulesParser.CharacterLiteral :
                Constructor<ConstantValueLinkDescriptor> constructor =
                        ConstantValueLinkDescriptor.class.getConstructor(RulesASTNode.class);
                return (F)constructor.newInstance(node);
            default: {
                throw new UnsupportedOperationException("Invalid node type");
            }
        }
    }
}
