package com.tibco.cep.metric.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.cep.metric.condition.ast.filterLexer;
import com.tibco.cep.metric.condition.ast.filterParser;

public class Translator {

	private Tree tree;
	private filterLexer lexer;
	private filterParser parser;
	private Visitor visitor;
    private int _bindIndex = 0;

	public Translator(final String input, List valueList, List<Integer> typeList) throws Exception {
		if (input == null || input.length()==0) {
			return;
		}
		CharStream charStream = new ANTLRStringStream(input.trim());
		lexer = new filterLexer(charStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		parser = new filterParser(tokens);
		tree = (CommonTree) parser.selector().getTree();
		visitor = new Visitor(valueList, typeList);

		if (tree == null) {
			throw new Exception("condition [" + input + "] could not be parsed");
		}
	}

	public final void translateCondition(StringBuffer sql, DBConceptMap entityMap, boolean isDrilldown) throws Exception {
		if (tree == null) {
			return; //null input was passed in
		}
        if (isDrilldown) {
            visitor.setDrilldown(isDrilldown);
        }
		visitor.visit(tree, sql, null, entityMap);
        _bindIndex = visitor.getBindIndex();
	}

	public final void translateCondition(StringBuffer sql, DBConceptMap entityMap, Map<String, Object> bindValues, boolean isDrilldown) throws Exception {
		if (tree == null) {
			return; //null input was passed in
		}
        if (isDrilldown) {
            visitor.setDrilldown(isDrilldown);
        }
		visitor.visit(tree, sql, bindValues, entityMap);
	}

    public int getNextBindIndex() {
        return _bindIndex;
    }

    public List getBindValueList() {
        return visitor.getBindValueList();
    }

    public List<Integer> getBindTypeList() {
        return visitor.getBindTypeList();
    }

	public static void main(String[] args) throws Exception {
		//Translator translator = new Translator(args[1]);
		List valueList = new ArrayList();
		List<Integer> typeList = new ArrayList<Integer>();

		StringBuffer sb = new StringBuffer();
		Translator translator = new Translator("(GroupOne = \"Test1\" and GroupTwo = \"Test2\" and Count > 888)", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne = \"Test1\" or GroupTwo = \"Test2\") and Count > 888", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne in (\"Hello1\", \"Hello2\", \"Hello3\") and Count > 1288", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("((GroupOne = \"Hello\" or GroupOne = \"SayNo\") and (Count > 1288 or Count5 < 10))", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne = \"Hello\" or GroupOne like \"ha*\")", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne between 20 and 32)", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne not like \"choochoo%\" and (Count - 34) > (54 * 34))", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne not between 20 and 32)", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne not in (\"Hello1\", \"Hello2\", \"Hello3\") and Count > 1288", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne is null and (Count - 34) > (54 * 34))", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("(GroupOne is not null and (Count - 34) > (54 * 34))", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne is null and @id = 123", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne is null and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.out.println(sb.toString());

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne like \"%_%\" and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.err.println(sb.toString()+valueList);

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne = \"abc123_@##$%abc\" and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.err.println(sb.toString()+valueList);

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne != \"abc123_@##$%abc\" and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.err.println(sb.toString()+valueList);

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne like \"%%%\" and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.err.println(sb.toString()+valueList);

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne like \"%abc\" and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.err.println(sb.toString()+valueList);

		sb = new StringBuffer();
		valueList.clear();
		typeList.clear();
		translator = new Translator("GroupOne like \"%##$%abc\" and @extId is not null", valueList, typeList);
		translator.translateCondition(sb, null, false);
		System.err.println(sb.toString()+valueList);

	}
}
