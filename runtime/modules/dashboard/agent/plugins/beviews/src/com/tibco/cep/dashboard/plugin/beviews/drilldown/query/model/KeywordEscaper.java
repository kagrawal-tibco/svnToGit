package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

final class KeywordEscaper {

	private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList(
		"$lastmod",
		"abs",
		"abstract",
		"accept",
		"ACTION",
		"AdvisoryEvent",
		"alias",
		"all",
		"and",
		"as",
		"asc",
		"assert",
		"attribute",
		"avg",
		"backwardChain",
		"between",
		"body",
		"boolean",
		"break",
		"by",
		"byte",
		"case",
		"catch",
		"char",
		"class",
		"Concept",
		"CONDITION",
		"const",
		"ContainedConcept",
		"continue",
		"count",
		"date",
		"DateTime",
		"days",
		"dead",
		"declare",
		"default",
		"delete",
		"desc",
		"distinct",
		"do",
		"double",
		"emit",
		"else",
		"entity",
		"enum",
		"Event",
		"except",
		"exists",
		"extends",
		"false",
		"final",
		"finally",
		"first",
		"float",
		"for",
		"forwardChain",
		"from",
		"goto",
		"group",
		"having",
		"hours",
		"if",
		"implements",
		"import",
		"in",
		"instanceof",
		"int",
		"interface",
		"intersect",
		"last",
		"is_defined",
		"is_undefined",
		"key",
		"last",
		"latest",
		"like",
		"limit",
		"lock",
		"long",
		"maintain",
		"max",
		"milliseconds",
		"min",
		"minutes",
		"mod",
		"moveto",
		"native",
		"new",
		"not",
		"null",
		"object",
		"offset",
		"or",
		"order",
		"order",
		"pending_count",
		"package",
		"policy",
		"priority",
		"private",
		"protected",
		"public",
		"purge",
		"QUERY",
		"rank",
		"requeue",
		"return",
		"rule",
		"rulefunction",
		"scope",
		"seconds",
		"select",
		"short",
		"SimpleEvent",
		"sliding",
		"static",
		"strictfp",
		"String",
		"sum",
		"super",
		"switch",
		"synchronized",
		"then",
		"this",
		"throw",
		"throws",
		"time",
		"TimeEvent",
		"timestamp",
		"transient",
		"true",
		"try",
		"tumbling",
		"undefined",
		"union",
		"unique",
		"using",
		"validity",
		"virtual",
		"void",
		"volatile",
		"when",
		"where",
		"while"
	));

	private static final Set<String> LOWERCASE_KEYWORDS = new HashSet<String>(KEYWORDS.size());

	static {
		for (String keyword : KEYWORDS) {
			LOWERCASE_KEYWORDS.add(keyword.toLowerCase());
		}
	}

	static boolean isKeyword(String word){
		return KEYWORDS.contains(word);
	}

	static boolean isKeyword(String word, boolean ignorecase){
		if (ignorecase == true) {
			return LOWERCASE_KEYWORDS.contains(word.toLowerCase());
		}
		return KEYWORDS.contains(word);
	}

	static String escapeKeyword(String word){
		if (isKeyword(word, true) == true) {
			return "#"+word;
		}
		return word;
	}
}
