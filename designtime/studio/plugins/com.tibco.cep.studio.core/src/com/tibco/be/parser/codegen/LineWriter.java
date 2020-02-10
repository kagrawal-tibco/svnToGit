package com.tibco.be.parser.codegen;

import java.io.IOException;
import java.io.PrintWriter;

import com.tibco.be.parser.Token;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 8, 2008
 * Time: 1:45:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class LineWriter {
    public static int TAB_WIDTH = 2;
    public static String SPACES = "                              ";

    // Current indent level:
    private int indent = 0;
    private int virtual_indent = 0;

    // The sink writer:
    PrintWriter writer;

    // servlet line numbers start from 1
    private int javaLine = 1;


    public LineWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void close() throws IOException {
        writer.close();
    }



    // -------------------- Access informations --------------------

    public int getJavaLine() {
        return javaLine;
    }

    // -------------------- Formatting --------------------

    public void pushIndent() {
        virtual_indent += TAB_WIDTH;
        if (virtual_indent >= 0 && virtual_indent <= SPACES.length())
            indent = virtual_indent;
    }

    public void popIndent() {
        virtual_indent -= TAB_WIDTH;
        if (virtual_indent >= 0 && virtual_indent <= SPACES.length())
            indent = virtual_indent;
    }

    /**
     * Print a standard comment for echo outputed chunk.
     *
     * @param start The starting position of the JSP chunk being processed.
     * @param stop  The ending position of the JSP chunk being processed.
     */
    public void printComment(Token start, Token stop, char[] chars) {
        if (start != null && stop != null) {
            println("// from=" + "(" + start.beginLine + "," + start.beginColumn + ")");
            println("//   to=(" + stop.beginLine + "," + stop.beginColumn + ")");
        }

        if (chars != null)
            for (int i = 0; i < chars.length;) {
                printin();
                print("// ");
                while (chars[i] != '\n' && i < chars.length)
                    writer.print(chars[i++]);
            }
    }

    /**
     * Prints the given string followed by '\n'
     */
    public void println(String s) {
        javaLine++;
        writer.println(s);
    }

    /**
     * Prints a '\n'
     */
    public void println() {
        javaLine++;
        writer.println("");
    }

    /**
     * Prints the current indention
     */
    public void printin() {
        writer.print(SPACES.substring(0, indent));
    }

    /**
     * Prints the current indention, followed by the given string
     */
    public void printin(String s) {
        writer.print(SPACES.substring(0, indent));
        writer.print(s);
    }

    /**
     * Prints the current indention, and then the string, and a '\n'.
     */
    public void printil(String s) {
        javaLine++;
        writer.print(SPACES.substring(0, indent));
        writer.println(s);
    }

    /**
     * Prints the given char.
     * <p/>
     * Use println() to print a '\n'.
     */
    public void print(char c) {
        writer.print(c);
    }

    /**
     * Prints the given int.
     */
    public void print(int i) {
        writer.print(i);
    }

    /**
     * Prints the given string.
     * <p/>
     * The string must not contain any '\n', otherwise the line count will be
     * off.
     */
    public void print(String s) {
        writer.print(s);
    }

    /**
     * Prints the given string.
     * <p/>
     * If the string spans multiple lines, the line count will be adjusted
     * accordingly.
     */
    public void printMultiLn(String s) {
        int index = 0;

        // look for hidden newlines inside strings
        while ((index = s.indexOf('\n', index)) > -1) {
            javaLine++;
            index++;
        }

        writer.print(s);
    }
}
