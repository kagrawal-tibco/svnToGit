package com.tibco.be.parser.codegen;

import java.util.Iterator;
import java.util.LinkedHashSet;

import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.RootNode;

/**
 * A class for generating codes to a buffer. Included here are some support
 * for tracking source to Java lines mapping.
 */
public class LineBuffer implements CharSequence {

//    private Node node;

    private java.io.CharArrayWriter charWriter;

    protected LineWriter out;

    protected LinkedHashSet<Node> nodes = new LinkedHashSet<Node>();

    LineBuffer() {
        this(null);
    }

    LineBuffer(Node n) {

//        if (n != null && n.getChildren().hasNext()) {
//            n.setChildrenGeneratedInBuffer(true);
//        }
        if (n != null && !nodes.contains(n)) {
            nodes.add(n);
        }
        charWriter = new java.io.CharArrayWriter();
        out = new LineWriter(new java.io.PrintWriter(charWriter));
    }

//    public LineWriter getOut() {
//        return out;
//    }

    /**
     * Adjust the Java Lines. This is necessary because the Java lines
     * stored with the nodes are relative the beginning of this buffer and
     * need to be adjusted when this buffer is inserted into the source.
     */
    public void adjustJavaLines(final int offset) {

        for (Iterator<Node> it = nodes.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n != null) {
                adjustJavaLine(n, offset);
            }
        }
    }


    private static void adjustJavaLine(Node n, int offset) {
        if (n.getBeginJavaLine() > 0) {
            int preBegin = n.getBeginJavaLine();
            int preEnd = n.getEndJavaLine();
            n.setBeginJavaLine(n.getBeginJavaLine() + offset);
            n.setEndJavaLine(n.getEndJavaLine() + offset);
            int postBegin = n.getBeginJavaLine();
            int postEnd = n.getEndJavaLine();
            //System.out.println("pre:{"+preBegin+","+preEnd+ "}->post:{"+postBegin+","+postEnd+"}"+ ((Object)n) );
        }

        for (Iterator it = n.getChildren(); it.hasNext();) {
            adjustJavaLine((Node) it.next(), offset);
        }

    }

    public void append(StringBuilder body) {
        out.printMultiLn(body.toString());
    }

    public LineBuffer append(String s) {
        out.printMultiLn(s);
        return this;
    }


    public LineBuffer append(char c) {
        out.print(c);
        return this;
    }
    
    public LineBuffer append(int i) {
        out.print(i);
        return this;
    }


    public LineBuffer append(LineBuffer lineBuffer) {
        lineBuffer.adjustJavaLines(out.getJavaLine() - 1);
        out.printMultiLn(lineBuffer.toString());
        return this;
    }

    public int getJavaLine() {
        return out.getJavaLine();
    }

    public void insert(int i, String s) {
        int lastLineCount = out.getJavaLine();
        int index = 0;

        // look for hidden newlines inside strings
        while ((index = s.indexOf('\n', index)) > -1) {
            index++;
        }
        adjustJavaLines(index);
        StringBuilder sb = new StringBuilder(charWriter.toString());
        sb.insert(i, s);
        charWriter.reset();
        charWriter.append(sb);
    }


    /**
     * Returns the <code>char</code> value at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing. </p>
     * <p/>
     * <p>If the <code>char</code> value specified by the index is a
     * <a href="Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * @param index the index of the <code>char</code> value to be returned
     * @return the specified <code>char</code> value
     * @throws IndexOutOfBoundsException if the <tt>index</tt> argument is negative or not less than
     *                                   <tt>length()</tt>
     */
    public char charAt(int index) {
        return charWriter.toString().charAt(index);
    }

    /**
     * Returns the length of this character sequence.  The length is the number
     * of 16-bit <code>char</code>s in the sequence.</p>
     *
     * @return the number of <code>char</code>s in this sequence
     */
    public int length() {
        return charWriter.size();
    }


    /**
     * Returns a new <code>CharSequence</code> that is a subsequence of this sequence.
     * The subsequence starts with the <code>char</code> value at the specified index and
     * ends with the <code>char</code> value at index <tt>end - 1</tt>.  The length
     * (in <code>char</code>s) of the
     * returned sequence is <tt>end - start</tt>, so if <tt>start == end</tt>
     * then an empty sequence is returned. </p>
     *
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return the specified subsequence
     * @throws IndexOutOfBoundsException if <tt>start</tt> or <tt>end</tt> are negative,
     *                                   if <tt>end</tt> is greater than <tt>length()</tt>,
     *                                   or if <tt>start</tt> is greater than <tt>end</tt>
     */
    public CharSequence subSequence(int start, int end) {
        return charWriter.toString().substring(start, end);
    }

    public String toString() {
        return charWriter.toString();
    }

    public void registerNode(RootNode node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
        }
    }
}
