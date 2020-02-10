package com.tibco.cep.analytics.terr.io;

/**
 * Created with IntelliJ IDEA.
 * User: dtsai
 * Date: 3/5/13
 * Time: 2:26 PM
 */

import com.tibco.terr.TerrJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerrConsoleExample {

    public static void main(String[] args) throws Throwable {
        System.out.println("TerrConsoleExample: Java-with-embedded-TERR console example");

        // set up output/input handlers
        TerrJava.OutputHandler out = new TerrJava.OutputHandler() {
            public void write(String data, boolean prompted) {
                System.out.print(data);
            }
        };
        TerrJava.InputHandler in = new TerrJava.InputHandler() {
            public String readLine() {
                try {
                    BufferedReader bin = new BufferedReader(
                            new InputStreamReader(System.in));
                    String cmd = bin.readLine();
                    return cmd;
                } catch (Exception ex) {
                    return "";
                }
            }
        };
        TerrJava.setOutputHandler(out);
        TerrJava.setInputHandler(in);

        // start TERR engine
        TerrJava.startEngine();

        // repeatedly read input lines and evaluate them
        String resultCode = "";
        String prompt = "";
        StringBuffer expression = new StringBuffer();

        while (true) {
            // get prompt string from TERR
            if (resultCode.equals("IncompleteString")) {
                prompt = TerrJava.evaluateToString("getOption('continueString')");
            } else if (resultCode.equals("IncompleteExpression")) {
                prompt = TerrJava.evaluateToString("getOption('continue')");
            } else {
                prompt = TerrJava.evaluateToString("getOption('prompt')");
                expression.setLength(0);
            }

            out.write(prompt, true);
            String line = in.readLine();
            // ignore empty input lines, except when we are reading a multi-line string
            if (line.isEmpty() && !resultCode.equals("IncompleteString")) {
                continue;
            }

            // accumulate multi-line expression, until we can parse it
            if (expression.length() > 0) {
                expression.append("\n");
            }
            expression.append(line);

            resultCode = TerrJava.evaluateInteractive(expression.toString());

            // exit if engine just evaluated q()
            if (resultCode.equals("Quit")) {
                resultCode = "Success";
                break;
            }

            // print error message if any
            if (resultCode.equals("EvaluationError") ||
                    resultCode.equals("ParserError")
                    ) {
                String error = TerrJava.getLastErrorMessage();
                if (!error.isEmpty()) {
                    out.write(error + "\n", false);
                }
            }
        }
    }

}
