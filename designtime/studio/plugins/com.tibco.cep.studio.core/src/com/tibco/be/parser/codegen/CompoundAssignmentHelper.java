package com.tibco.be.parser.codegen;

import static com.tibco.be.parser.codegen.CompoundAssignment.DECR;
import static com.tibco.be.parser.codegen.CompoundAssignment.INCR;
import static com.tibco.be.parser.codegen.CompoundAssignment.MINUSEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.PCNTEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.PLUSEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.POST_DECR;
import static com.tibco.be.parser.codegen.CompoundAssignment.POST_INCR;
import static com.tibco.be.parser.codegen.CompoundAssignment.SLASHEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.STAREQ;

import com.tibco.be.parser.RuleGrammarConstants;
import com.tibco.be.parser.Token;

public class CompoundAssignmentHelper {
	public static CompoundAssignment fromToken(Token tok) {
        return fromTokenKind(tok.kind);
    }

    public static CompoundAssignment fromTokenKind(int kind) {
        switch(kind) {
            case RuleGrammarConstants.INCR: return INCR;
            case RuleGrammarConstants.DECR: return DECR;
            case RuleGrammarConstants.PLUSEQ: return PLUSEQ;
            case RuleGrammarConstants.MINUSEQ: return MINUSEQ;
            case RuleGrammarConstants.STAREQ: return STAREQ;
            case RuleGrammarConstants.SLASHEQ: return SLASHEQ;
            case RuleGrammarConstants.PCNTEQ: return PCNTEQ;
            case RuleGrammarConstants.POST_INCR: return POST_INCR;
            case RuleGrammarConstants.POST_DECR: return POST_DECR;
        }
        return null;
    }

}
