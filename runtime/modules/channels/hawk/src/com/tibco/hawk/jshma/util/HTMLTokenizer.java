// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.

package com.tibco.hawk.jshma.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

//------------------------------------------------------------------------
// Class HTMLTokenizer is a form of StreamTokenizer which knows about
// HTML tags (but not HTML structure !!)
//------------------------------------------------------------------------
/**
 * This class is for internal use only.
 */
public class HTMLTokenizer extends StreamTokenizer {

	public static int HTML_TEXT = -1;
	public static int HTML_UNKNOWN = -2;
	public static int HTML_EOF = -3;

	// The following class constants are used to identify HTML tags
	// note that each tag type has an odd- and even-numbered ID, depending on
	// whether the tag is a start or end tag.
	// These constants are returned by nextHTML()

	public static int TAG_HTML = 0, TAG_html = 1;
	public static int TAG_HEAD = 2, TAG_head = 3;
	public static int TAG_BODY = 4, TAG_body = 5;
	public static int TAG_H1 = 6, TAG_h1 = 7;
	public static int TAG_H2 = 8, TAG_h2 = 9;
	public static int TAG_H3 = 10, TAG_h3 = 11;
	public static int TAG_H4 = 12, TAG_h4 = 13;
	public static int TAG_H5 = 14, TAG_h5 = 15;
	public static int TAG_H6 = 16, TAG_h6 = 17;
	public static int TAG_H7 = 18, TAG_h7 = 19;
	public static int TAG_CENTER = 20, TAG_center = 21;
	public static int TAG_PRE = 22, TAG_pre = 23;
	public static int TAG_TITLE = 24, TAG_title = 25;
	public static int TAG_HORIZONTAL = 26;
	public static int TAG_DT = 28, TAG_dt = 29;
	public static int TAG_DD = 30, TAG_dd = 31;
	public static int TAG_DL = 32, TAG_dl = 33;
	public static int TAG_IMAGE = 34, TAG_image = 35;
	public static int TAG_BOLD = 36, TAG_bold = 37;
	public static int TAG_APPLET = 38, TAG_applet = 39;
	public static int TAG_PARAM = 40, TAG_param = 41;
	public static int TAG_PARAGRAPH = 42;
	public static int TAG_ADDRESS = 44, TAG_address = 45;
	public static int TAG_STRONG = 46, TAG_strong = 47;
	public static int TAG_LINK = 48, TAG_link = 49;
	public static int TAG_ORDERED_LIST = 50, TAG_ordered_list = 51;
	public static int TAG_LIST = 52, TAG_list = 53;
	public static int TAG_LIST_ITEM = 54, TAG_list_item = 55;
	public static int TAG_CODE = 56, TAG_code = 57;
	public static int TAG_EMPHASIZE = 58, TAG_emphasize = 59;
	public static int TAG_TABLE = 60, TAG_table = 61;
	public static int TAG_TR = 62, TAG_tr = 63;
	public static int TAG_TD = 64, TAG_td = 65;

	// When extending this list, make sure that substring collisions do not
	// introduce bugs. For example: tag "A" has to come AFTER "ADDRESS"
	// otherwise all "ADDRESS" tags will be seen as "A" tags..

	String[] default_tags = { "HTML", "HEAD", "BODY", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "CENTER", "PRE",
			"TITLE", "HR", "DT", "DD", "DL", "IMG", "B", "APPLET", "PARAM", "P", "ADDRESS", "STRONG", "A", "OL", "UL",
			"LI", "CODE", "EM", "TABLE", "TR", "TD" };
	String[] tags = default_tags;

	boolean outsideTag = true;

	// ------------------------------------------------------------------------
	// The HTMLTokenizer relies on a two-state state machine: the stream can
	// be "inside a tag" (between < and > ) or "outside" a tag (between > and <.
	// ------------------------------------------------------------------------
	public HTMLTokenizer(InputStream inputStream, String[] tags) {
		super(new InputStreamReader(inputStream));

		if (tags != null)
			this.tags = tags;

		resetSyntax(); // start with a blank character type table
		wordChars(0, 255); // we want to stumble over < and > only,
		ordinaryChars('<', '<'); // all the rest is considered "words"
		ordinaryChars('>', '>');

		outsideTag = true; // we start being outside any HTML tags
	}

	// ------------------------------------------------------------------------
	// grab next HTML tag, text or EOF
	// ------------------------------------------------------------------------
	public int nextHTML() throws IOException {
		int tok;

		switch (tok = nextToken()) {
		case StreamTokenizer.TT_EOF:
			return HTML_EOF;

		case '<':
			outsideTag = false; // we're inside
			return nextHTML(); // decode type

		case '>':
			outsideTag = true;
			return nextHTML();

		case StreamTokenizer.TT_WORD:
			if (!outsideTag) {
				return tagType(); // decode tag type
			} else {
				// if ( onlyWhiteSpace(sval) ) {
				// return nextHTML();
				// } else {
				return HTML_TEXT;
				// }
			}
		default:
			System.out.println("ERROR: unknown TT " + tok);
		}
		return HTML_UNKNOWN;
	}

	// ------------------------------------------------------------------------
	// inter-tag words which consist only of white space are swallowed (skipped)
	// this method tests whether a string can be considered as white space
	// ------------------------------------------------------------------------
	public static boolean onlyWhiteSpace(String s) {

		char ch;

		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (!(ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r')) {
				return false;
			}
		}
		return true;
	}

	// ------------------------------------------------------------------------
	// We've just hit a '<' tag start character, now identify the type of tag
	// we're dealing with.
	// ------------------------------------------------------------------------
	protected int tagType() {

		boolean endTag = false;
		String input;
		int start = 0;
		int tagID;

		// System.out.println("sval=" + sval);
		input = sval;

		if (input.charAt(0) == '/') { // is this an end tag (like </HTML>) ?
			start++; // skip slash
			endTag = true;
		}
		// go through the list of known tags, try to match one
		for (int tag = 0; tag < tags.length; tag++) {
			if (input.regionMatches(true, start, tags[tag], 0, tags[tag].length())) {
				tagID = tag * 2 + (endTag ? 1 : 0);
				return tagID;
			}
		}
		return HTML_UNKNOWN;

	}
} // End of class HTMLTokenizer

