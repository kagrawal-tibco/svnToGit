package editor;

import com.jidesoft.editor.KeywordMap;
import com.jidesoft.editor.tokenmarker.Token;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: May 17, 2008
 * Time: 4:03:52 PM
 * To change this template use File | Settings | File Templates.
 */

public class OQLTokenMarker extends BaseTokenMarker
{
	// public members
	public OQLTokenMarker()
	{
		super(getKeywordMap(), true);
	}

	public static KeywordMap getKeywordMap()
	{
		if (oqlKeywords == null) {
			oqlKeywords = new KeywordMap(true); // ignorecase=true            
            addKeywords();
			addDataTypes();
			addOperators();
		}
		return oqlKeywords;
	}

	private static void addKeywords()
	{  
        oqlKeywords.add("AS", Token.KEYWORD1);
        oqlKeywords.add("ABS",Token.KEYWORD2);
        oqlKeywords.add("AVG",Token.KEYWORD2);
        oqlKeywords.add("BY", Token.KEYWORD1);
        oqlKeywords.add("BETWEEN", Token.KEYWORD1);
        oqlKeywords.add("ASC",Token.KEYWORD1);
        oqlKeywords.add("AVG",Token.KEYWORD1);
        oqlKeywords.add("CHAR",Token.KEYWORD1);
        oqlKeywords.add("COUNT",Token.KEYWORD1);
        oqlKeywords.add("DAYS",Token.KEYWORD1);
        oqlKeywords.add("DATETIME",Token.KEYWORD1);
        oqlKeywords.add("DEAD",Token.KEYWORD1);
        oqlKeywords.add("DESC",Token.KEYWORD1);
        oqlKeywords.add("DEFINED",Token.KEYWORD1);
        oqlKeywords.add("DIGITS",Token.KEYWORD1);
        oqlKeywords.add("DISTINCT",Token.KEYWORD1);
        oqlKeywords.add("EMIT",Token.KEYWORD1);
        oqlKeywords.add("EXISTS",Token.KEYWORD1);
        oqlKeywords.add("FALSE",Token.KEYWORD1);
        oqlKeywords.add("FIRST",Token.KEYWORD1);
        oqlKeywords.add("FROM",Token.KEYWORD1);
        oqlKeywords.add("GROUP",Token.KEYWORD1);
        oqlKeywords.add("HAVING",Token.KEYWORD1);
        oqlKeywords.add("HOURS",Token.KEYWORD1);
        oqlKeywords.add("IN",Token.KEYWORD1);
        oqlKeywords.add("IS",Token.KEYWORD1);
        oqlKeywords.add("LATEST_FULL",Token.KEYWORD1);
        oqlKeywords.add("LIKE",Token.KEYWORD1);
        oqlKeywords.add("LIMIT",Token.KEYWORD1);
        oqlKeywords.add("LONG",Token.KEYWORD1);
        oqlKeywords.add("LAST",Token.KEYWORD1);
        oqlKeywords.add("MAINTAIN",Token.KEYWORD1);
        oqlKeywords.add("MAX",Token.KEYWORD1);
        oqlKeywords.add("MILLISECONDS",Token.KEYWORD1);
        oqlKeywords.add("MIN",Token.KEYWORD1);
        oqlKeywords.add("MOD",Token.KEYWORD1);
        oqlKeywords.add("NEW",Token.KEYWORD1);
        oqlKeywords.add("NULL",Token.KEYWORD1);
        oqlKeywords.add("OFFSET",Token.KEYWORD1);
        oqlKeywords.add("ORDER",Token.KEYWORD1);
        oqlKeywords.add("PENDING_COUNT",Token.KEYWORD1);
        oqlKeywords.add("POLICY",Token.KEYWORD1);
        oqlKeywords.add("PURGE",Token.KEYWORD1);
        oqlKeywords.add("SELECT",Token.KEYWORD1);
        oqlKeywords.add("SECONDS",Token.KEYWORD1);
        oqlKeywords.add("SLIDING",Token.KEYWORD1);
        oqlKeywords.add("STATEFUL_CAPTUE",Token.KEYWORD1);
        oqlKeywords.add("SUM",Token.KEYWORD1);
        oqlKeywords.add("TRUE",Token.KEYWORD1);
        oqlKeywords.add("TUMBLING",Token.KEYWORD1);
        oqlKeywords.add("USING",Token.KEYWORD1);
        oqlKeywords.add("UNIQUE",Token.KEYWORD1);
        oqlKeywords.add("UNDEFINED",Token.KEYWORD1);
        oqlKeywords.add("WHEN",Token.KEYWORD1);
        oqlKeywords.add("WHERE",Token.KEYWORD1);

	}

	private static void addDataTypes()
	{

		oqlKeywords.add("LONG",Token.KEYWORD1);
		oqlKeywords.add("DOUBLE",Token.KEYWORD1);
		oqlKeywords.add("CHAR",Token.KEYWORD1);
		oqlKeywords.add("STRING",Token.KEYWORD1);
		oqlKeywords.add("BOOLEAN",Token.KEYWORD1);
		oqlKeywords.add("DATETIME",Token.KEYWORD1);
		oqlKeywords.add("DATE",Token.KEYWORD1);
		oqlKeywords.add("TIME",Token.KEYWORD1);
		oqlKeywords.add("TIMESTAMP",Token.KEYWORD1);
	}



	private static void addOperators()
	{
		oqlKeywords.add("ALL",Token.OPERATOR);
		oqlKeywords.add("AND",Token.OPERATOR);
		oqlKeywords.add("BETWEEN",Token.OPERATOR);
		oqlKeywords.add("BY",Token.OPERATOR);
		oqlKeywords.add("FOR",Token.OPERATOR);
		oqlKeywords.add("EXCEPT",Token.OPERATOR);
		oqlKeywords.add("EXISTS",Token.OPERATOR);
		oqlKeywords.add("IN",Token.OPERATOR);
		oqlKeywords.add("INTERSECT",Token.OPERATOR);
		oqlKeywords.add("LIKE",Token.OPERATOR);
		oqlKeywords.add("NOT",Token.OPERATOR);
		oqlKeywords.add("NULL",Token.OPERATOR);
		oqlKeywords.add("OR",Token.OPERATOR);
		oqlKeywords.add("UNION",Token.OPERATOR);

	}





	private static KeywordMap oqlKeywords;
}

