package com.tibco.jxpath.compiler;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public class JXPathASTErrorNode extends JXPathASTNode {

	
    protected TokenStream input;
	public TokenStream getInput() {
		return input;
	}

	public Token getStart() {
		return start;
	}

	public Token getStop() {
		return stop;
	}

	public RecognitionException getE() {
		return e;
	}

	private Token start;
	private Token stop;
	private RecognitionException e;

	public JXPathASTErrorNode(TokenStream input, Token start, Token stop,
			RecognitionException e) {
		super(start);
    	this.input = input;
    	this.start = start;
    	this.stop = stop;
    	this.e = e;
    }

}
