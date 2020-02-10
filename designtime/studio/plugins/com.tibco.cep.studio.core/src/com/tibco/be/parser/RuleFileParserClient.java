package com.tibco.be.parser;

import com.tibco.be.parser.tree.NameNode;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Feb 3, 2005
 * Time: 3:19:32 PM
 * To change this template use File | Settings | File Templates.
 * 
 * For parsing full rule (.rul) files with the CompilationUnit() nonterminal
 */
public interface RuleFileParserClient extends ParserClient {
    /**
     * Parser calls this before it starts parsing a rule block.
     * Multiple rule blocks may be in a single file.
     * The parser won't call this if it starts and finishes
     * parsing inside of a rule block (for example parsing a single statement).
     */ 
    void newRule();

    /**
     * Parser calls this with the identifier in angle brackets as the argument:
     * Rule <name> {
     * @param name name of the rule about to be parsed
     */ 
    void setName(String name);

    /**
     * For every statement in the attribute block of a rule, the parser calls this method
     * with the type of attribute and the value specified
     * @param attribute
     * @param value
     */ 
    void addAttribute(Token attribute, String value);

    /**
     * For every import statement in a rule, the parser calls this method
     * with the fully specified name of the imported type.
     * Example: import folder1.folder2.Order;
     * importType is the NamedNode resulting from the parse of "folder1.folder2.order"
     * The path of folder1.folder2.Order in the model/ontology would be /folder1/folder2/Order
     * @param importType
     */ 
    void addImport(NameNode importType);
    
    /**
     * For every statement in the declaration block of a rule, the parser calls this method
     * with either the name of the type 
     * and an identifer used to refer to an instance
     * of this type in the when and then blocks.
     * Example: folder1.folder2.Order O;
     * declType is folder1.folder2.Order and declName is "O"
     * The parser does no checking at this stage, so declType may not exist in the model.
     * @param declType
     * @param declName
     */ 
    void addDeclaration(String declType, String declName);
}
