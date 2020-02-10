package com.tibco.cep.pattern.dsl;

import com.tibco.cep.pattern.integ.impl.dsl2.Definition;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Pattern;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Pattern.*;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Source;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Source.Subscription;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Source.SubscriptionCorrelate;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Source.SubscriptionMatch;
import com.tibco.cep.pattern.integ.impl.dsl2.Definition.Util.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/*
* Author: Anil Jeswani / Date: Nov 16, 2009 / Time: 2:46:42 PM
*/
@Test(groups = {"ValidPatternsTest", "All"}, sequential = true)
public class ValidPatternsTest {

    static final String VALID_PATTERNS_TEST_FILE =
            "r:\\dev\\be\\leo\\pattern\\pattern-lang\\test\\com\\tibco\\cep\\pattern\\dsl\\validpatterns.txt";

    static final String DELIMITER = "::";
    Map<String, String> patternsToTest = new ConcurrentHashMap<String, String>();
    
    int countStartsWith;

    @BeforeClass
    public void setUp() {
        try {
            int idx;
            String testpattern;
            BufferedReader br = new BufferedReader(new FileReader(VALID_PATTERNS_TEST_FILE));
            while ((testpattern = br.readLine()) != null) {
                idx = testpattern.indexOf(DELIMITER);
                patternsToTest.put(testpattern.substring(0, idx), testpattern.substring(idx + 2));
            }
            Assert.assertNotNull(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMain() throws LanguageException {
        int i=0;
        String expected, actual;

        for (String pattern : patternsToTest.keySet()) {
            System.out.println("Testing Pattern " + ++i + "::" + pattern);
            PatternParserHelper helper = new PatternParserHelper(pattern);
            Definition definition = helper.parse();
            expected = patternsToTest.get(pattern).trim();
            actual = print(definition).trim();
            System.out.println("Expected output " + i + "::" + expected);
            System.out.println("Actual output " + i + "::" + actual +"\n");
            Assert.assertEquals(actual, expected, "Test Pattern " + i);
            patternsToTest.put(actual, expected);
        }
    }

    @SuppressWarnings("unchecked")
	private String print(Definition definition) {
        StringBuffer buf = new StringBuffer("define pattern ");
        buf.append("\"").append(definition.getUri()).append("\"");
        buf.append(" using ");
        int i=0;
        for (Source sources : definition.getSources()) {
            if (i != 0) {
                buf.append(" and ");
            }
            printEvents(buf, sources);
            i++;
        }
        buf.append(" with ");
        i=0;
        for (Source sources : definition.getSources()) {
            if (i != 0) {
                buf.append(" and ");
            }
            printSubscription(buf, sources);
            i++;
        }
        Pattern p = definition.getPattern();
        for (PatternMember pm : p.getMembers()) {
            if (pm instanceof PatternMembers) {
                buf.append("");
                for (Object pmList : ((PatternMembers) pm).getList()) {
                    buf.append("");
                    printMembers(buf, pmList, true);
                }
            } else {
            	countStartsWith++;
                printMembers(buf, pm, true);
            }
        }
        return buf.toString();
    }

    private void printEvents(StringBuffer buf, Source sources) {
        buf.append("\"").append(sources.getEvent().getUri()).append("\"");
        buf.append(" as ");
        buf.append(escapeKeyword(sources.getAlias()));
    }

    private String escapeKeyword(String alias) {
        String[] keywords = {"DEFINE", "PATTERN", "USING", "EVENT", "AS", "WHERE", "AND", "SUCH", "THAT", "STARTS",
                "WITH", "ANY", "ONE", "ALL", "THEN", "REPEAT", "WITHIN", "DURING", "TO", "TIMES", "AFTER", "DAYS",
                "HOURS", "MINUTES", "SECONDS", "MILLISECONDS", "TRUE", "FALSE", "NULL"};
        Set<String> s = new HashSet<String>(Arrays.asList(keywords));
        if (s.contains(alias.toUpperCase())) {
            return "#"+alias;
        }
        return alias;
    }

    @SuppressWarnings("unchecked")
	private void printSubscription(StringBuffer buf, Source sources) {
        Subscription sub = sources.getSubscription();
        if (sub==null) return;
        buf.append(escapeKeyword(sources.getAlias()));
        buf.append(".");
        if (sub instanceof SubscriptionCorrelate) {
            buf.append(((SubscriptionCorrelate) sub).getField());
        } else {
            SubscriptionMatch sm = (SubscriptionMatch) sub;
            Identifier id = sm.getWith();
            if (id instanceof Actual) {
                if (((Actual) id).getValue() instanceof Calendar) {
                    Calendar c = (Calendar) ((Actual) id).getValue();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy, MM, dd, HH, mm, ss, SSS");
                    buf.append(((SubscriptionMatch) sub).getField()).append("=$datetime(").append(s.format(c.getTime())).append(")");
                } else if (((Actual) id).getValue() instanceof Long) {
                    buf.append(((SubscriptionMatch) sub).getField()).append("=").append(((Actual) id).getValue()).append("l");
                } else if (((Actual) id).getValue() instanceof Double) {
                    buf.append(((SubscriptionMatch) sub).getField()).append("=").append(((Actual) id).getValue()).append("d");
                } else if (((Actual) id).getValue() instanceof String) {
                    buf.append(((SubscriptionMatch) sub).getField()).append("=").append("\"").append(((Actual) id).getValue()).append("\"");
                } else {
                    buf.append(((SubscriptionMatch) sub).getField()).append("=").append(((Actual) id).getValue());
                }
            } else {
                buf.append(((SubscriptionMatch) sub).getField()).append("=$").append(((Parameter) id).getBind());
            }
        }
    }

    @SuppressWarnings("unchecked")
	private void printMembers(StringBuffer buf, Object pmList, boolean printStartsWith) {
        if (pmList == null) return;
        if (pmList instanceof StartsWith) {
            if (printStartsWith) buf.append(" starts with ");
            buf.append(escapeKeyword(((SourceRef)((StartsWith)pmList).getMember()).getSourceAlias()));
        } else if (pmList instanceof StartsWithAll) {
            if (printStartsWith) buf.append(" starts with ");
            printItems(buf, pmList);
        } else if (pmList instanceof StartsWithAnyOne) {
            if (printStartsWith) buf.append(" starts with ");
            printItems(buf, pmList);
        } else if (pmList instanceof StartsWithRepeat) {
            if (printStartsWith) buf.append(" starts with ");
            printItems(buf, pmList);
        } else if (pmList instanceof SourceRef) {
            buf.append(escapeKeyword(((SourceRef) pmList).getSourceAlias()));
        } else if (pmList instanceof Then) {
            printThen(buf, pmList);
        } else if (pmList instanceof ThenAnyOne) {
            buf.append(" then ");
            printItems(buf, pmList);
        } else if (pmList instanceof ThenAll) {
            buf.append(" then ");
            printItems(buf, pmList);
        } else if (pmList instanceof ThenRepeat) {
            buf.append(" then ");
            printItems(buf, pmList);
        } else if (pmList instanceof ThenWithin) {
            printThenWithin(buf, pmList);
        } else if (pmList instanceof ThenAfter) {
            printThenAfter(buf, pmList);
        } else if (pmList instanceof ThenDuring) {
            printThenDuring(buf, pmList);
        } else if (pmList instanceof PatternMembers) {
            for (Object pm : ((PatternMembers) pmList).getList()) {
                printMembers(buf, pm, false);
            }
        }
    }

    private void printThen(StringBuffer buf, Object pmList) {
        buf.append(" then ");
        printItems(buf, ((Then)pmList).getMember());
    }

    private void printThenWithin(StringBuffer buf, Object pmList) {
        buf.append(" then within ");
        ThenWithin tw = (ThenWithin) pmList;
        printTime(buf, tw.getTime());
        printItems(buf, ((ThenWithin) pmList).getMember());
    }

    private void printThenAfter(StringBuffer buf, Object pmList) {
        buf.append(" then after ");
        ThenAfter ta = (ThenAfter) pmList;
        printTime(buf, ta.getTime());
    }

    private void printThenDuring(StringBuffer buf, Object pmList) {
        buf.append(" then during ");
        ThenDuring tw = (ThenDuring) pmList;
        printTime(buf, tw.getTime());
        printItems(buf, ((ThenDuring) pmList).getMember());
    }

    @SuppressWarnings("unchecked")
	private void printTime(StringBuffer buf, TimeIdentifier id) {
        if (id instanceof ActualTime) {
            ActualTime at = (ActualTime) id;
            buf.append(at.getValue()).append(" ");
            buf.append(at.getTimeUnit()).append(" ");
        } else {
            ParameterTime pt = (ParameterTime) id;
            buf.append("$").append(pt.getBind()).append(" ");
            buf.append(pt.getTimeUnit()).append(" ");
        }
    }

    @SuppressWarnings("unchecked")
	private void printItems(StringBuffer buf, Object pmList) {
        if (pmList instanceof SourceRef) {
            buf.append(escapeKeyword(((SourceRef)(pmList)).getSourceAlias()));
        } else if (pmList instanceof StartsWithAnyOne || pmList instanceof ThenAnyOne) {
            printAnyOne(buf, pmList);
        } else if (pmList instanceof StartsWithRepeat || pmList instanceof ThenRepeat) {
            printRepeat(buf, pmList);
        } else if (pmList instanceof StartsWithAll || pmList instanceof ThenAll) {
            printAll(buf, pmList);
        } else {
            if (((PatternMembers) pmList).getList().size()>1) {
                buf.append("(");
            }
            for (Object sourceRef : ((PatternMembers) pmList).getList()) {
                printMembers(buf, sourceRef, false);
            }
            if (((PatternMembers) pmList).getList().size()>1) {
                buf.append(")");
            }
        }
    }

    @SuppressWarnings("unchecked")
	private void printAnyOne(StringBuffer buf, Object pmList) {
        buf.append(" any one ");
        buf.append("(");
        int i=0;
        for (Object sourceRef : ((ThenAnyOne) pmList).getMembers()) {
            if (sourceRef instanceof PatternMembers) {
                if (i!=0) buf.append(", ");
                if (((PatternMembers) sourceRef).size()>1) {
                    buf.append("(");
                }
                printMembers(buf, sourceRef, false);
                if (((PatternMembers) sourceRef).size()>1) {
                    buf.append(")");
                }
                i++;
            } else {
                if (i!=0) buf.append(", ");
                buf.append(escapeKeyword(((SourceRef)sourceRef).getSourceAlias()));
                i++;
            }
        }
        buf.append(")");
    }

    @SuppressWarnings("unchecked")
	private void printRepeat(StringBuffer buf, Object pmList) {
        buf.append(" repeat ");
        ThenRepeat tr = (ThenRepeat) pmList;
        if (tr.getMin() instanceof Actual) {
            Actual min = (Actual) tr.getMin();
            buf.append(min.getValue()).append(" to ");
        } else {
            Parameter min = (Parameter) tr.getMin();
            buf.append("$").append(min.getBind()).append(" to ");
        }
        if (tr.getMax() instanceof Actual) {
            Actual max = (Actual) tr.getMax();
            buf.append(max.getValue()).append(" times ");
        } else {
            Parameter max = (Parameter) tr.getMax();
            buf.append("$").append(max.getBind()).append(" times ");
        }
        printItems(buf, tr.getMember());
    }

    @SuppressWarnings("unchecked")
	private void printAll(StringBuffer buf, Object pmList) {
        buf.append(" all ");
        buf.append("(");
        int i=0;
        for (Object sourceRef : ((ThenAll) pmList).getMembers()) {
            if (sourceRef instanceof PatternMembers) {
                if (i!=0) buf.append(", ");
                if (((PatternMembers) sourceRef).size()>1) {
                    buf.append("(");
                }
                printMembers(buf, sourceRef, false);
                if (((PatternMembers) sourceRef).size()>1) {
                    buf.append(")");
                }
                i++;
            } else {
                if (i!=0) buf.append(", ");
                buf.append(escapeKeyword(((SourceRef)sourceRef).getSourceAlias()));
                i++;
            }
        }
        buf.append(")");
    }

    @AfterClass
    public void tearDown() {
        //todo
    }
}
