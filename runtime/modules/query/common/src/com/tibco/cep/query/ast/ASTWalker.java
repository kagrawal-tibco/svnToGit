package com.tibco.cep.query.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.ast.parser.BEOqlParser;
import com.tibco.cep.query.model.AcceptType;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.DeleteContext;
import com.tibco.cep.query.model.EmitType;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.FunctionIdentifier;
import com.tibco.cep.query.model.GroupClause;
import com.tibco.cep.query.model.HavingClause;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.OrderClause;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.model.PurgeClause;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.SlidingWindow;
import com.tibco.cep.query.model.SortCriterion;
import com.tibco.cep.query.model.StreamPolicy;
import com.tibco.cep.query.model.StreamPolicyBy;
import com.tibco.cep.query.model.TimeWindow;
import com.tibco.cep.query.model.TumblingWindow;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.Window;
import com.tibco.cep.query.model.impl.AbstractOperator;
import com.tibco.cep.query.model.impl.AggregateFunctionIdentifierImpl;
import com.tibco.cep.query.model.impl.AliasedIdentifierImpl;
import com.tibco.cep.query.model.impl.ArrayExpressionImpl;
import com.tibco.cep.query.model.impl.BinaryExpressionImpl;
import com.tibco.cep.query.model.impl.BinaryOperatorImpl;
import com.tibco.cep.query.model.impl.BindVariableImpl;
import com.tibco.cep.query.model.impl.BooleanLiteralImpl;
import com.tibco.cep.query.model.impl.CharLiteralImpl;
import com.tibco.cep.query.model.impl.DateTimeLiteralImpl;
import com.tibco.cep.query.model.impl.DeleteContextImpl;
import com.tibco.cep.query.model.impl.DistinctClauseImpl;
import com.tibco.cep.query.model.impl.FieldListImpl;
import com.tibco.cep.query.model.impl.FromClauseImpl;
import com.tibco.cep.query.model.impl.GroupClauseImpl;
import com.tibco.cep.query.model.impl.HavingClauseImpl;
import com.tibco.cep.query.model.impl.IdentifierImpl;
import com.tibco.cep.query.model.impl.NamedSelectContextImpl;
import com.tibco.cep.query.model.impl.NullLiteralImpl;
import com.tibco.cep.query.model.impl.NumberLiteralImpl;
import com.tibco.cep.query.model.impl.OrderClauseImpl;
import com.tibco.cep.query.model.impl.PathFunctionIdentifierImpl;
import com.tibco.cep.query.model.impl.PathIdentifierImpl;
import com.tibco.cep.query.model.impl.ProjectionAttributesImpl;
import com.tibco.cep.query.model.impl.ProjectionImpl;
import com.tibco.cep.query.model.impl.PurgeClauseImpl;
import com.tibco.cep.query.model.impl.ScopedIdentifierImpl;
import com.tibco.cep.query.model.impl.SelectContextImpl;
import com.tibco.cep.query.model.impl.SlidingWindowImpl;
import com.tibco.cep.query.model.impl.SortCriterionImpl;
import com.tibco.cep.query.model.impl.StreamImpl;
import com.tibco.cep.query.model.impl.StreamPolicyByImpl;
import com.tibco.cep.query.model.impl.StreamPolicyImpl;
import com.tibco.cep.query.model.impl.StringLiteralImpl;
import com.tibco.cep.query.model.impl.TimeWindowImpl;
import com.tibco.cep.query.model.impl.TumblingWindowImpl;
import com.tibco.cep.query.model.impl.TypeExpressionImpl;
import com.tibco.cep.query.model.impl.TypeIdentifierImpl;
import com.tibco.cep.query.model.impl.UnaryExpressionImpl;
import com.tibco.cep.query.model.impl.UnaryOperatorImpl;
import com.tibco.cep.query.model.impl.WhereClauseImpl;
import com.tibco.cep.query.service.QueryLogger;

/**
 * The class walks the AST Tree created from Antlr Parser
 *
 * @author pdhar
 *
 */
public class ASTWalker {

    private static final int FIRST_CHILD = 0;
    private static final HashMap<Integer,Integer> tokenOpMap = new HashMap<Integer,Integer>();

    static {
        tokenOpMap.put(BEOqlParser.ARRAY_INDEX,Operator.OP_ARRAY);
        tokenOpMap.put(BEOqlParser.RANGE_EXPRESSION,Operator.OP_RANGE);
        tokenOpMap.put(BEOqlParser.UNARY_MINUS,Operator.OP_UMINUS);
        tokenOpMap.put(BEOqlParser.TOK_PLUS,Operator.OP_PLUS);
        tokenOpMap.put(BEOqlParser.TOK_STAR,Operator.OP_MULTIPLY);
        tokenOpMap.put(BEOqlParser.TOK_SLASH,Operator.OP_DIVIDE);
        tokenOpMap.put(BEOqlParser.TOK_MINUS,Operator.OP_MINUS);
        tokenOpMap.put(BEOqlParser.TOK_ABS,Operator.OP_ABS);
        tokenOpMap.put(BEOqlParser.TOK_NOT,Operator.OP_NOT);
        tokenOpMap.put(BEOqlParser.OR_EXPRESSION,Operator.OP_OR);
        tokenOpMap.put(BEOqlParser.AND_EXPRESSION,Operator.OP_AND);
        tokenOpMap.put(BEOqlParser.BLOCK_EXPRESSION,Operator.OP_GROUP);
        tokenOpMap.put(BEOqlParser.BETWEEN_CLAUSE,Operator.OP_BETWEEN);
        tokenOpMap.put(BEOqlParser.TOK_EQ,Operator.OP_EQ);
        tokenOpMap.put(BEOqlParser.TOK_NE,Operator.OP_NE);
        tokenOpMap.put(BEOqlParser.TOK_GT,Operator.OP_GT);
        tokenOpMap.put(BEOqlParser.TOK_LT,Operator.OP_LT);
        tokenOpMap.put(BEOqlParser.TOK_GE,Operator.OP_GE);
        tokenOpMap.put(BEOqlParser.TOK_LE,Operator.OP_LE);
        tokenOpMap.put(BEOqlParser.TOK_CONCAT,Operator.OP_CONCAT);
        tokenOpMap.put(BEOqlParser.TOK_AT,Operator.OP_AT);
        tokenOpMap.put(BEOqlParser.TOK_MOD,Operator.OP_MOD);
        tokenOpMap.put(BEOqlParser.LIKE_CLAUSE,Operator.OP_LIKE);
        tokenOpMap.put(BEOqlParser.TOK_BETWEEN,Operator.OP_BETWEEN);
        tokenOpMap.put(BEOqlParser.IN_CLAUSE,Operator.OP_IN);
        tokenOpMap.put(BEOqlParser.TOK_DOT,Operator.OP_DOT);
        tokenOpMap.put(BEOqlParser.EXISTS_CLAUSE,Operator.OP_EXISTS);
        tokenOpMap.put(BEOqlParser.CAST_EXPRESSION, Operator.OP_CAST);
    }


    private CommonTree rooASTree;
    private SelectContext selectContext;
    private DeleteContext deleteContext;
    private CommonTree projectionAttributesTree;
    private CommonTree fromClauseTree;
    private CommonTree whereClauseTree;
    private CommonTree groupClauseTree;
    private CommonTree orderClauseTree;

    private boolean isDebug = true;
    private Logger logger;


    public ASTWalker(CommonTree ast) {        
        if (ast.getType() == BEOqlParser.DELETE_EXPR || ast.getType() == BEOqlParser.SELECT_EXPR) {
            this.rooASTree = ast;
        } else {
            throw new IllegalArgumentException("Invalid AST specified");
        }
    }

    /**
     * This class is the constructor
     *
     * @param ast
     *            The root AST node from the Parser
     * @param queryContext
     * @throws Exception
     */
    public ASTWalker(CommonTree ast, QueryContext queryContext) throws Exception {
        this(ast);
        if(queryContext instanceof SelectContext) {
            this.selectContext = (SelectContext)queryContext;
        } else if(queryContext instanceof DeleteContext) {
            this.deleteContext = (DeleteContext)queryContext;
        }
        if (null != queryContext.getProjectContext() &&
                null != queryContext.getProjectContext().getRuleServiceProvider()) {
            this.logger = queryContext.getProjectContext().getRuleServiceProvider().getLogger(this.getClass());
            isDebug = ((BEProperties) queryContext.getProjectContext().getRuleServiceProvider()
                    .getProperties()).getBoolean("com.tibco.be.query.ast.debug", true);
        } else  {
            this.logger = new QueryLogger(this.getClass().getSimpleName());
            isDebug = Boolean.parseBoolean(System.getProperty("com.tibco.be.query.ast.debug", "true"));
        }
    }
    
    public ASTWalker(CommonTree ast, Logger logger, boolean isDebug) {
        rooASTree = ast;
        this.logger = logger;
        this.isDebug = isDebug;
    }
    
//    public static void walkExpressionStatic(CommonTree ast, ModelContext ctx, Logger logger, boolean isDebug) throws Exception {
//        ASTWalker astw = new ASTWalker(ast, logger, isDebug);
//        astw.walkExpression(ast, ctx);
//    }

    /**
     * @return the ast
     */
    protected CommonTree getRoot() {
        return rooASTree;
    }

    public boolean isDebug() {
        return isDebug;
    }

    /**
     * This method walks the FROM Clause and it is walked before any other
     * clause is walked
     *
     * @return FromClause the model FromClause
     * @throws Exception
     */
    public FromClause walkFromClause(ModelContext context) throws Exception {
        CommonTree root = (CommonTree) getRoot();
        this.fromClauseTree = (CommonTree) root .getFirstChildWithType(BEOqlParser.FROM_CLAUSE);
        FromClauseImpl fromClause = new FromClauseImpl(context, this.fromClauseTree);
        walkFromClause((CommonTree) this.fromClauseTree, fromClause);
        return fromClause;
    }

    /**
     * This method walks the projection attributes to the create the model
     *
     * @param parentContext QueryContext
     * @return ProjectionAttributes the model ProjectionAttributes
     * @throws Exception
     */
    public ProjectionAttributes walkProjectionAttributes(QueryContext parentContext)
            throws Exception {
        ProjectionAttributesImpl projectionAttributes = null;
        this.projectionAttributesTree = (CommonTree) getRoot().getFirstChildWithType(BEOqlParser.PROJECTION_ATTRIBUTES);
        projectionAttributes = new ProjectionAttributesImpl(parentContext, this.projectionAttributesTree);
        for (int i = 0; i < projectionAttributesTree.getChildCount(); i++) {
            CommonTree projectionTree = (CommonTree) projectionAttributesTree.getChild(i);            
            switch (projectionTree.getType()) {
                case BEOqlParser.PROJECTION:
                    this.walkSimpleProjection(projectionTree, projectionAttributes);
                    break;
            }
        }
        return projectionAttributes;
    }



    /**
     * This method walks the scoped projections( from and entity) and regular projection
     * @param parent
     * @param projectionAttributesContext
     * @throws Exception
     */
     public void walkSimpleProjection(CommonTree parent,
                                      ModelContext projectionAttributesContext) throws Exception {

        final Projection projection = new ProjectionImpl(projectionAttributesContext, parent);

        final CommonTree aliasTree = (CommonTree) parent.getFirstChildWithType(BEOqlParser.ALIAS_CLAUSE);
        if (null != aliasTree) {
            projection.setAlias(aliasTree.getChild(FIRST_CHILD).getText(), false);
        } else {

            final String alias = projectionAttributesContext.getRootContext().getIdGenerator()
                                .nextIdentifier().toString();
            projection.setAlias(alias, true);
        }


        final CommonTree expressionTree = (CommonTree) parent.getChild(FIRST_CHILD);
        this.walkExpression(expressionTree, projection);
    }




   /**
     * This method walks the WHERE clause
     *
     * @param context
     * @throws Exception
     */
    public WhereClause walkWhereClause(ModelContext context) throws Exception {
       this.whereClauseTree = (CommonTree) getRoot().getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
       if (null == this.whereClauseTree) {
           return null;
       } else {
           final WhereClauseImpl whereClause = new WhereClauseImpl(context, this.whereClauseTree);
           this.walkWhereClause(this.whereClauseTree, whereClause);
           return whereClause;
       }
   }

   public void walkWhereClause(CommonTree parent, ModelContext parentContext) throws Exception {
        walkExpression((CommonTree) parent.getChild(FIRST_CHILD),parentContext);
    }


    public GroupClause walkGroupClause(SelectContext select)
            throws Exception {
        this.groupClauseTree = (CommonTree) getRoot().getFirstChildWithType(BEOqlParser.GROUP_CLAUSE);
        if (null == this.groupClauseTree) {
            return null;
        } else {
            final GroupClause groupClause = new GroupClauseImpl(select, this.groupClauseTree);
            this.walkGroupClause(this.groupClauseTree, groupClause);
            return groupClause;
        }
    }


    private void walkGroupClause(CommonTree groupClauseTree, GroupClause groupClause) throws Exception {
        CommonTree tree = (CommonTree) groupClauseTree.getFirstChildWithType(BEOqlParser.HAVING_CLAUSE);
        if (null != tree) {
            this.walkHavingClause(tree, groupClause);
        }
        tree = (CommonTree) groupClauseTree.getFirstChildWithType(BEOqlParser.FIELD_LIST);
        final FieldList fieldList = new FieldListImpl(groupClause, groupClauseTree);
        this.walkFieldList(tree, fieldList);
        
        groupClause.setFieldList(fieldList);
    }

    private void walkHavingClause(CommonTree tree, GroupClause groupClause) throws Exception {
        final HavingClause having = new HavingClauseImpl(groupClause,  tree);
        tree = (CommonTree) tree.getChild(FIRST_CHILD);
        this.walkExpression(tree, having);
        
        groupClause.setHavingClause(having);
    }


    private OrderClause walkOrderClause(SelectContext selectContext) throws Exception {
        this.orderClauseTree = (CommonTree) getRoot().getFirstChildWithType(BEOqlParser.ORDER_CLAUSE);
        if (null == this.orderClauseTree) {
            return null;
        } else {
            final OrderClause orderClause = new OrderClauseImpl(selectContext, this.orderClauseTree);
            this.walkOrderClause(this.orderClauseTree, orderClause);
            return orderClause;
        }
    }


    private void walkOrderClause(CommonTree tree, OrderClause order) throws Exception {
        List<SortCriterion> list = new ArrayList<SortCriterion>();
        
        final int numChildren = tree.getChildCount();
        for (int i=0; i<numChildren; i++) {
            final CommonTree sortTree = (CommonTree) tree.getChild(i);
            final CommonTree sortLimitTree = (CommonTree) sortTree.getFirstChildWithType(BEOqlParser.LIMIT_CLAUSE);
            Object limitFirst = null;
            Object limitOffset = null;
            if (null != sortLimitTree) {
//                limitFirst = sortLimitTree.getChild(FIRST_CHILD).getText();
//                final CommonTree sortLimitOffsetTree = (CommonTree) sortLimitTree.getChild(FIRST_CHILD+1);
//                if (null != sortLimitOffsetTree) {
//                    limitOffset = sortLimitOffsetTree.getText();
//                }

                CommonTree nLiteral = (CommonTree) sortLimitTree.getChild(FIRST_CHILD);
                if (null != nLiteral) {
                    switch (nLiteral.getType()) {
                        case BEOqlParser.DIGITS: {
                            limitFirst = new Integer(nLiteral.getText());
                        }
                        break;
                        case BEOqlParser.BIND_VARIABLE_EXPRESSION: {
                            limitFirst = new BindVariableImpl(order, sortLimitTree,
                                    nLiteral.getChild(FIRST_CHILD).getText());
                        }
                        break;
                    }
                }
                nLiteral = (CommonTree) sortLimitTree.getChild(FIRST_CHILD + 1);
                if (null != nLiteral) {
                    switch (nLiteral.getType()) {
                        case BEOqlParser.DIGITS: {
                            limitOffset = new Integer(nLiteral.getText());
                        }
                        break;
                        case BEOqlParser.BIND_VARIABLE_EXPRESSION: {
                            limitOffset = new BindVariableImpl(order, sortLimitTree,
                                    nLiteral.getChild(FIRST_CHILD).getText());
                        }
                        break;
                    }
                }


            }

            final CommonTree sortDirectionTree = (CommonTree) sortTree.getFirstChildWithType(BEOqlParser.SORT_DIRECTION);
            SortCriterion.Direction direction = null;
            if (null != sortDirectionTree) {
                if (null != sortDirectionTree.getFirstChildWithType(BEOqlParser.TOK_ASC)) {
                    direction = SortCriterion.Direction.ASC;
                } else if (null != sortDirectionTree.getFirstChildWithType(BEOqlParser.TOK_DESC)) {
                    direction = SortCriterion.Direction.DESC;
                }
            }
            SortCriterion sort = new SortCriterionImpl(order, direction, limitFirst, limitOffset, sortTree);
            this.walkExpression((CommonTree) sortTree.getChild(FIRST_CHILD), sort);
            
            list.add(sort);
        }
        
        SortCriterion[] criteria = list.toArray(new SortCriterion[list.size()]);
        order.setSortCriteria(criteria);
    }


    public void walkExpression(CommonTree parent, ModelContext parentContext) throws Exception {
            switch (parent.getType()) {
                case BEOqlParser.BLOCK_EXPRESSION:
                case BEOqlParser.TOK_NOT:
                case BEOqlParser.TOK_ABS:
                case BEOqlParser.EXISTS_CLAUSE:
                case BEOqlParser.UNARY_MINUS: {
                    int optype = tokenOpMap.get(parent.getType());
                    final UnaryOperatorImpl op = new UnaryOperatorImpl(
                            AbstractOperator.getValidMask(optype),
                            optype);
                    final Expression exp = new UnaryExpressionImpl(parentContext, parent, op);
                    this.walkExpression((CommonTree) parent.getChild(FIRST_CHILD), exp);
                }
                break;
                case BEOqlParser.IDENTIFIER:
                    new IdentifierImpl(parentContext, parent, parent.getText());
                    break;
                case BEOqlParser.SCOPE_IDENTIFIER:
                    new ScopedIdentifierImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    break;
                case BEOqlParser.SELECT_EXPR: {
                        final boolean isPartOfExists = (parent.getType() == BEOqlParser.EXISTS_CLAUSE);
                        final SelectContext subselect = new SelectContextImpl(parentContext, isPartOfExists,
                                parent);
                        final CommonTree distinctTree = (CommonTree) parent.getFirstChildWithType(BEOqlParser.TOK_DISTINCT);
                        if (distinctTree != null) {
                            new DistinctClauseImpl(subselect, distinctTree);
                        }
                        walkSelectClause(parent, subselect);
                    }
                    break;
                case BEOqlParser.DELETE_EXPR: {
                        final DeleteContext deleteContext = new DeleteContextImpl(parentContext, parent);
                        walkDeleteClause(parent, deleteContext);
                    }
                    break;
                case BEOqlParser.BIND_VARIABLE_EXPRESSION: {
                        new BindVariableImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    }
                    break;
                case BEOqlParser.NUMBER_LITERAL:
                    new NumberLiteralImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    break;
                case BEOqlParser.CHAR_LITERAL:
                    new CharLiteralImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    break;
                case BEOqlParser.STRING_LITERAL:
                    if (parentContext instanceof BinaryExpression) {
                        final int opType = ((BinaryExpression) parentContext).getOperator().getOpType();
                        if ((opType == Operator.OP_AT) || (opType == Operator.OP_DOT)) {
                            new PathIdentifierImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                            break;
                        }
                    }
                    new StringLiteralImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    break;
                case BEOqlParser.NULL_LITERAL:
                    new NullLiteralImpl(parentContext, parent);
                    break;
                case BEOqlParser.BOOLEAN_LITERAL:
                    new BooleanLiteralImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    break;
                case BEOqlParser.DATETIME_LITERAL:
                    new DateTimeLiteralImpl(parentContext, parent, parent.getChild(FIRST_CHILD).getText());
                    break;
                case BEOqlParser.FIELD_LIST: {
                        final FieldList fl = new FieldListImpl(parentContext, parent);
                        walkFieldList(parent,fl);
                    }
                    break;
                case BEOqlParser.ARRAY_INDEX: {
                        CommonTree identifierExpr = ((CommonTree) parent.getChild(FIRST_CHILD));
                        CommonTree indexExpr = (CommonTree) parent.getChild(FIRST_CHILD+1);
                        final ArrayExpressionImpl aei = new ArrayExpressionImpl(parentContext, parent);
                        walkExpression(identifierExpr,aei);
                        walkExpression(indexExpr,aei);
                    }
                    break;
//                case BEOqlParser.ARRAY_INDEX:
                case BEOqlParser.RANGE_EXPRESSION:
                case BEOqlParser.OR_EXPRESSION:
                case BEOqlParser.AND_EXPRESSION:
                case BEOqlParser.BETWEEN_CLAUSE:
                case BEOqlParser.TOK_CONCAT:
                case BEOqlParser.TOK_EQ:
                case BEOqlParser.TOK_GE:
                case BEOqlParser.TOK_GT:
                case BEOqlParser.TOK_LE:
                case BEOqlParser.TOK_DOT:
                case BEOqlParser.TOK_AT:
                case BEOqlParser.TOK_LT:
                case BEOqlParser.TOK_MINUS:
                case BEOqlParser.TOK_NE:
                case BEOqlParser.LIKE_CLAUSE:
                case BEOqlParser.TOK_MOD:
                case BEOqlParser.TOK_PLUS:
                case BEOqlParser.TOK_STAR:
                case BEOqlParser.IN_CLAUSE:
                case BEOqlParser.TOK_SLASH:{
                    int optype = tokenOpMap.get(parent.getType());
                    final BinaryOperatorImpl op = new BinaryOperatorImpl(
                            AbstractOperator.getValidMask(optype),
                            optype);
                    final BinaryExpression exp = new BinaryExpressionImpl(parentContext, parent, op);
                    this.walkExpression((CommonTree) parent.getChild(FIRST_CHILD), exp);
                    this.walkExpression((CommonTree) parent.getChild(FIRST_CHILD+1), exp);
                } break;
                case BEOqlParser.CAST_EXPRESSION: {
                    int optype = tokenOpMap.get(parent.getType());
                    final BinaryOperatorImpl op = new BinaryOperatorImpl(
                            AbstractOperator.getValidMask(optype),
                            optype);
                    final BinaryExpression exp = new BinaryExpressionImpl(parentContext, parent, op);
                    this.walkCastType(parent, exp);
                } break;
                case BEOqlParser.FUNCTION_EXPRESSION:
                {
                    final String fname = parent.getChild(FIRST_CHILD).getText();
                    final FunctionIdentifier fi = new AggregateFunctionIdentifierImpl(parentContext, parent, fname);
                    walkFunctionArgs((CommonTree) parent.getFirstChildWithType(BEOqlParser.ARG_LIST),fi);
                    final CommonTree distinctTree = (CommonTree) parent.getFirstChildWithType(BEOqlParser.TOK_DISTINCT);
                    if (distinctTree != null) {
                        new DistinctClauseImpl(fi, distinctTree);
                    }
                }
                break;
                case BEOqlParser.PATHFUNCTION_EXPRESSION:
                {
                    CommonTree pathExpr = (CommonTree) parent.getFirstChildWithType(BEOqlParser.PATH_EXPRESSION);
                    final String fname = makePath(pathExpr);
                    final FunctionIdentifier fi = new PathFunctionIdentifierImpl(parentContext, parent, fname);
                    walkFunctionArgs((CommonTree) parent.getFirstChildWithType(BEOqlParser.ARG_LIST),fi);
                }
                break;
                case BEOqlParser.TOK_BOOLEAN:
                    new TypeIdentifierImpl.Boolean(parentContext, parent);
                    break;
                case BEOqlParser.TOK_CONCEPT:
                    new TypeIdentifierImpl.Concept(parentContext, parent);
                    break;
                case BEOqlParser.TOK_DATETIME:
                    new TypeIdentifierImpl.DateTime(parentContext, parent);
                    break;
                case BEOqlParser.TOK_DOUBLE:
                    new TypeIdentifierImpl.Double(parentContext, parent);
                    break;
                case BEOqlParser.TOK_ENTITY:
                    new TypeIdentifierImpl.Entity(parentContext, parent);
                    break;
                case BEOqlParser.TOK_EVENT:
                    new TypeIdentifierImpl.Event(parentContext, parent);
                    break;
                case BEOqlParser.TOK_INT:
                    new TypeIdentifierImpl.Integer(parentContext, parent);
                    break;
                case BEOqlParser.TOK_LONG:
                    new TypeIdentifierImpl.Long(parentContext, parent);
                    break;
                case BEOqlParser.TOK_OBJECT:
                    new TypeIdentifierImpl.Object(parentContext, parent);
                    break;
                case BEOqlParser.TOK_STRING:
                    new TypeIdentifierImpl.String(parentContext, parent);
                    break;
                case BEOqlParser.PATH_EXPRESSION:
                    final String path = this.makePath(parent);
                    new TypeIdentifierImpl.Entity(parentContext, parent, path);
                    break;
                default:
                    break;
            }
    }


    private void walkCastType(CommonTree parent, BinaryExpression castExp) throws Exception {
        this.walkExpression((CommonTree) parent.getChild(FIRST_CHILD), castExp);
        final CommonTree typeChild = (CommonTree) parent.getChild(FIRST_CHILD + 1);
        byte arrayDepth = 0;
        final int numChildren = parent.getChildCount();
        for (int i = FIRST_CHILD + 2; i < numChildren; i++) {
            if (parent.getChild(i).getType() == BEOqlParser.TOK_LBRACK) {
                arrayDepth++;
            }
        }
        final TypeExpressionImpl typeExp = new TypeExpressionImpl(castExp, typeChild, arrayDepth);
        this.walkExpression(typeChild, typeExp);
    }


    public static ASTWalker walkSelectClause(CommonTree parent, SelectContext selectContext) throws Exception {
        final ASTWalker walker = new ASTWalker(parent, selectContext);
        final FromClause from = walker.walkFromClause(selectContext);
        walker.walkLimitClause(parent, selectContext);
        walker.walkProjectionAttributes(selectContext);
        walker.walkWhereClause(selectContext);
        walker.walkGroupClause(selectContext);
        walker.walkOrderClause(selectContext);
        return walker;
    }

    public static ASTWalker walkDeleteClause(CommonTree parent, DeleteContext deleteContext) throws Exception {        
        final ASTWalker walker = new ASTWalker(parent, deleteContext);
        walker.walkFromClause(deleteContext);
        walker.walkWhereClause(deleteContext);
        return walker;
    }

    private void walkFieldList(CommonTree parent, ModelContext fieldList) throws Exception{
        for(int i =0; i < parent.getChildCount(); i ++ ) {
            walkExpression((CommonTree) parent.getChild(i),fieldList);
        }
    }

    private void walkFunctionArgs(CommonTree argList, FunctionIdentifier fi) throws Exception {
        if(argList == null) {
            return;
        }
        
        for(int i =0; i < argList.getChildCount(); i ++ ) {
            walkExpression((CommonTree) argList.getChild(i),fi);
        }
    }


    public void walkFromClause(CommonTree parent, FromClauseImpl fc) throws Exception {
        Entity ec = null;
        String entity;
        String alias;
        for (int i = 0; i < parent.getChildCount(); i++) {
            CommonTree child = (CommonTree) parent.getChild(i);
            switch (child.getType()) {
                case BEOqlParser.ITERATOR_DEF:
                    this.walkEntityDefinition(child, fc);
                    break;
            }
        }
    }


    protected void walkEntityDefinition(CommonTree child, FromClauseImpl fc) throws Exception {
        String alias;
        final CommonTree selectExpression = (CommonTree) child.getFirstChildWithType(BEOqlParser.SELECT_EXPR);
        CommonTree aliasClause = (CommonTree) child.getFirstChildWithType(BEOqlParser.ALIAS_CLAUSE);

        if (null != selectExpression) { // Uses subselect
            if (aliasClause == null) {
                alias = fc.getRootContext().getIdGenerator().nextIdentifier().toString();
            } else {
                alias = aliasClause.getChild(FIRST_CHILD).getText();
            }
            final SelectContext ctx = new SelectContextImpl(fc, alias, false, selectExpression);

            final CommonTree distinctTree = (CommonTree) selectExpression.getFirstChildWithType(BEOqlParser.TOK_DISTINCT);
            if (distinctTree != null) {
                new DistinctClauseImpl(ctx, distinctTree);
            }

            //logger.logDebug("Added Subselect - Alias:" + alias + " Line:" + selectExpression.getLine());
            walkSelectClause((CommonTree) selectExpression,ctx);
        } else { // Uses path expression
            final CommonTree pathExpression = (CommonTree) child.getFirstChildWithType(BEOqlParser.PATH_EXPRESSION);
            final CommonTree streamDefinition = (CommonTree) child.getFirstChildWithType(BEOqlParser.STREAM_DEF);

            if ((aliasClause == null) && (null != streamDefinition)) {
                aliasClause = (CommonTree) streamDefinition.getFirstChildWithType(BEOqlParser.ALIAS_CLAUSE);
            }
            boolean pseudoAlias = false;
            if (aliasClause == null) {
                alias = fc.getRootContext().getIdGenerator().nextIdentifier().toString();
                pseudoAlias = true;
            } else {
                alias = aliasClause.getChild(FIRST_CHILD).getText();
                pseudoAlias = false;
            }

            final String entityPath = makePath(pathExpression);

            final AliasedIdentifier id = new AliasedIdentifierImpl(fc, child, entityPath, alias, pseudoAlias);

            if (null != streamDefinition) {
                this.walkStreamDefinition(streamDefinition, id);
            }
            //logger.logDebug("Added Entity:" + entityPath + " Alias:" + alias + " Line:" + pathExpression.getLine());
        }
    }

    protected void walkStreamDefinition(CommonTree streamDef, AliasedIdentifier parent) throws Exception {
        CommonTree tree;

        final EmitType emitType = findEmitType(streamDef);

        final AcceptType acceptType = findAcceptType(streamDef);

        final StreamImpl stream = new StreamImpl(parent, streamDef, emitType, acceptType);
        
        parent.setStream(stream);

        tree = (CommonTree) streamDef.getFirstChildWithType(BEOqlParser.POLICY_CLAUSE);
        if (null != tree) {
            this.walkPolicyClause(tree, stream);
        }
    }

    private EmitType findEmitType(CommonTree tree) {
        tree = (CommonTree) tree.getFirstChildWithType(BEOqlParser.EMIT_CLAUSE);
        EmitType emitType = null;
        if (null != tree) {
            if (null != tree.getFirstChildWithType(BEOqlParser.EMIT_NEW)) {
                emitType = EmitType.NEW;
            } else if (null != tree.getFirstChildWithType(BEOqlParser.EMIT_DEAD)) {
                emitType = EmitType.DEAD;
            }
        }
        return emitType;
    }

    private AcceptType findAcceptType(CommonTree tree) {
        tree = (CommonTree) tree.getFirstChildWithType(BEOqlParser.ACCEPT_CLAUSE);
        AcceptType acceptType = AcceptType.ALL;
        if (null != tree && null != tree.getFirstChildWithType(BEOqlParser.ACCEPT_NEW)) {
                acceptType = AcceptType.NEW;
        }
        return acceptType;
    }

    protected void walkPolicyClause(CommonTree policyClause, StreamImpl stream) throws Exception {
        final StreamPolicy policy = new StreamPolicyImpl(stream, policyClause);

        CommonTree tree;

        tree = (CommonTree) policyClause.getFirstChildWithType(BEOqlParser.POLICY_BY_CLAUSE);
        if (null != tree) {
            this.walkPolicyByClause(tree, policy);
        }

        tree = (CommonTree) policyClause.getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
        if (null != tree) {
            final WhereClauseImpl whereClause = new WhereClauseImpl(policy, policyClause);            
            this.walkWhereClause(tree, whereClause);
        }

        tree = (CommonTree) policyClause.getFirstChildWithType(BEOqlParser.WINDOW_DEF);
        if (null != tree) {
            this.walkWindowDefinition(tree, policy);
        }
    }

    protected void walkPolicyByClause(CommonTree policyByClause, StreamPolicy policy) throws Exception {

        final StreamPolicyBy policyBy = new StreamPolicyByImpl(policy, policyByClause);

        final int numChildren = policyByClause.getChildCount();
        for (int i=0; i<numChildren; i++) {
            final CommonTree idTree = (CommonTree) policyByClause.getChild(i);
            walkExpression(idTree, policyBy);
        }
    }


    protected void walkWindowDefinition(CommonTree windowDef, StreamPolicy policy) throws Exception {
        CommonTree tree;

        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.TUMBLING_WINDOW);
        if (null != tree) {
            this.walkTumblingWindowDefinition((CommonTree) tree, policy);
            return;
        }

        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.SLIDING_WINDOW);
        if (null != tree) {
            this.walkSlidingWindowDefinition((CommonTree) tree, policy);
            return;
        }

        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.TIME_WINDOW);
        if (null != tree) {
            this.walkTimeWindowDefinition((CommonTree) tree, policy);
            return;
        }

        throw new Exception("Unknown window type");
    }


    protected void walkTimeWindowDefinition(CommonTree windowDef, StreamPolicy policy) throws Exception {
        final long size = Long.parseLong(windowDef.getChild(0).getText());
        final String unit = windowDef.getChild(1).getText();
        CommonTree tree;        
        
        final TimeWindow w = new TimeWindowImpl(policy, windowDef, size, unit);

        final CommonTree usingTree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.USING_CLAUSE);
        if (null != usingTree) {
            walkExpression((CommonTree)usingTree.getChild(0), w);                
        }
                        
        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
        if (null != tree) {
            this.walkWhereClause(tree, w);
        }
        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.PURGE_CLAUSE);
        if (null != tree) {
            this.walkPurgeClause((CommonTree) tree, w);
        }
    }


    protected void walkSlidingWindowDefinition(CommonTree windowDef, StreamPolicy policy) throws Exception {
        final int size = Integer.parseInt(windowDef.getChild(0).getText());
        final SlidingWindow w = new SlidingWindowImpl(policy, windowDef, size);

        CommonTree tree;
        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
        if (null != tree) {
            this.walkWhereClause(tree, w);
        }
        tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.PURGE_CLAUSE);
        if (null != tree) {
            this.walkPurgeClause((CommonTree) tree, w);
        }
    }

    protected void walkTumblingWindowDefinition(CommonTree windowDef, StreamPolicy policy) throws Exception {
        final int size = Integer.parseInt(windowDef.getChild(0).getText());
        final TumblingWindow w = new TumblingWindowImpl(policy, windowDef, size);

        final CommonTree tree = (CommonTree) windowDef.getFirstChildWithType(BEOqlParser.WHERE_CLAUSE);
        if (null != tree) {
            this.walkWhereClause(tree, w);
        }
    }


    protected void walkPurgeClause(CommonTree purgeClause, Window w) throws Exception {
        final PurgeClause p = new PurgeClauseImpl(w, purgeClause);

        CommonTree tree;
        tree = (CommonTree) purgeClause.getFirstChildWithType(BEOqlParser.PURGE_FIRST_CLAUSE);
        if (null != tree) {
            this.walkExpression((CommonTree) tree.getChild(FIRST_CHILD), p);
        }
        tree = (CommonTree) purgeClause.getFirstChildWithType(BEOqlParser.PURGE_WHEN_CLAUSE);
        if (null != tree) {
            this.walkExpression((CommonTree) tree.getChild(FIRST_CHILD), p);
        }

    }

    public void walkLimitClause(CommonTree parentNode, ModelContext parentContext) throws Exception {
        CommonTree limitsClause = (CommonTree) parentNode.getFirstChildWithType(BEOqlParser.LIMIT_CLAUSE);
        if(null != limitsClause ) {
            if(parentContext instanceof SelectContextImpl) {
                final SelectContextImpl select = (SelectContextImpl) parentContext;
                CommonTree nLiteral = (CommonTree) limitsClause.getChild(FIRST_CHILD);
                if (null != nLiteral) {
                    switch (nLiteral.getType()) {
                        case BEOqlParser.DIGITS: {
                            select.setLimitFirst(new Integer(nLiteral.getText()));
                        } break;
                        case BEOqlParser.BIND_VARIABLE_EXPRESSION: {
                            select.setLimitFirst(new BindVariableImpl(parentContext, parentNode,
                                    nLiteral.getChild(FIRST_CHILD).getText()));
                        } break;
                    }
                }
                nLiteral = (CommonTree) limitsClause.getChild(FIRST_CHILD+1);
                if (null != nLiteral) {
                    switch (nLiteral.getType()) {
                        case BEOqlParser.DIGITS: {
                            select.setLimitOffset(new Integer(nLiteral.getText()));
                        } break;
                        case BEOqlParser.BIND_VARIABLE_EXPRESSION: {
                            select.setLimitOffset(new BindVariableImpl(parentContext, parentNode,
                                    nLiteral.getChild(FIRST_CHILD).getText()));
                        } break;
                    }
                }
                //logger.logDebug("Found limits clause first:"+select.getFirst()+" offset:"+select.getOffset());
            } else {
                throw new Exception("Invalid parent context");
            }

        }

    }

    /**
     * @return the selcnxt
     */
    protected SelectContext getSelectContext() {
        return selectContext;
    }

    protected DeleteContext getDeleteContext() {
        return deleteContext;
    }
    /**
     * @param selcnxt
     *            the selcnxt to set
     */
    protected void setContext(NamedSelectContextImpl selcnxt) {
        this.selectContext = selcnxt;
    }

    class NodeSearchInfo {
        CommonTree node;
        int nodeDepth;

        public NodeSearchInfo(CommonTree node, int nodeDepth) {
            this.node = node;
            this.nodeDepth = nodeDepth;
        }

        public CommonTree getNode() {
            return node;
        }

        public int getNodeDepth() {
            return nodeDepth;
        }
    }
    public NodeSearchInfo[] findASTNodeByType(CommonTree parent,int nodeType) {
        List<NodeSearchInfo> nodeList = new ArrayList<NodeSearchInfo>();
        int nodeDepth = 0;
        findASTNodeByType(parent,nodeType,nodeList,nodeDepth);
        return nodeList.toArray(new NodeSearchInfo[0]);
    }

    private void findASTNodeByType(CommonTree parent,int nodeType, List<NodeSearchInfo> nodeList ,int nodeDepth ) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            CommonTree child = (CommonTree) parent.getChild(i);
            if(child.getType() == nodeType) {
                nodeList.add(new NodeSearchInfo(child,nodeDepth));
            }
            if (child.getChildCount() > 0) {
                findASTNodeByType(child,nodeType,nodeList,nodeDepth + 1);
            }
        }
    }

    protected String makePath(CommonTree pathExpr) {
        final StringBuilder path = new StringBuilder();
        final int numChildren = pathExpr.getChildCount();
        for (int i=0; i<numChildren; i++) {
            path.append(pathExpr.getChild(i).getText());
        }
        return path.toString();
    }

} // end ASTWalker.java

