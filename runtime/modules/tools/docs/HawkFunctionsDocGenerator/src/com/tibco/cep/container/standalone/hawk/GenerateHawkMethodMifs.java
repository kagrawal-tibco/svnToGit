package com.tibco.cep.container.standalone.hawk;


import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: May 11, 2007
 * Time: 6:19:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateHawkMethodMifs {

    protected static final Pattern PATTERN_ANTIQUOTE = Pattern.compile("`", Pattern.MULTILINE);
    protected static final Pattern PATTERN_ANTISLASH = Pattern.compile("\\\\", Pattern.MULTILINE);
    protected static final Pattern PATTERN_CRLN = Pattern.compile("\\r?\\n", Pattern.MULTILINE);
    protected static final Pattern PATTERN_GT = Pattern.compile(">", Pattern.MULTILINE);
    protected static final Pattern PATTERN_METHOD_HELP = Pattern.compile("%%method_help%%", Pattern.MULTILINE);
    protected static final Pattern PATTERN_METHOD_NAME = Pattern.compile("%%method_name%%", Pattern.MULTILINE);
    protected static final Pattern PATTERN_METHOD_TYPE = Pattern.compile("%%method_type%%", Pattern.MULTILINE);
    protected static final Pattern PATTERN_NEWID = Pattern.compile("%%new_id%%", Pattern.MULTILINE);
    protected static final Pattern PATTERN_PARAM = Pattern.compile("%%param_start%%(?:\\s*\r?\n)?"
            + "((?:.*?\\r?\\n)*?)"
            + "\\s*%%param_stop%%(?:\\s*\r?\n)?", Pattern.MULTILINE);
    protected static final Pattern PATTERN_PARAM_HELP = Pattern.compile("%%param_help%%", Pattern.MULTILINE);
    protected static final Pattern PATTERN_PARAM_NAME = Pattern.compile("%%param_name%%", Pattern.MULTILINE);
    protected static final Pattern PATTERN_PARAMS_REF = Pattern.compile("%%params_ref_start%%(?:\\s*\r?\n)?"
            + "((?:.*?\\r?\\n)*?)"
            + "\\s*%%params_ref_stop%%(?:\\s*\r?\n)?", Pattern.MULTILINE);
    protected static final Pattern PATTERN_QUOTE = Pattern.compile("'", Pattern.MULTILINE);
    protected static final Pattern PATTERN_REF_EMPTY = Pattern.compile("%%ref_empty_start%%(?:\\s*\r?\n)?"
            + "((?:.*?\\r?\\n)*?)"
            + "\\s*%%ref_empty_stop%%(?:\\s*\r?\n)?", Pattern.MULTILINE);
    protected static final Pattern PATTERN_REF_NOTEMPTY = Pattern.compile("%%ref_not_empty_start%%(?:\\s*\r?\n)?"
            + "((?:.*?\\r?\\n)*?)"
            + "\\s*%%ref_not_empty_stop%%(?:\\s*\r?\n)?", Pattern.MULTILINE);
    protected static final Pattern PATTERN_RETURN = Pattern.compile("%%return_start%%(?:\r?\n)?"
            + "((?:.*?\\r?\\n)*?)"
            + "\\s*%%return_stop%%(?:\\s*\r?\n)?", Pattern.MULTILINE);
    protected static final Pattern PATTERN_RETURNS_REF = Pattern.compile("%%returns_ref_start%%(?:\\s*\r?\n)?"
            + "((?:.*?\\r?\\n)*?)"
            + "\\s*%%returns_ref_stop%%(?:\\s*\r?\n)?", Pattern.MULTILINE);
    protected static final Pattern PATTERN_TAB = Pattern.compile("\\t", Pattern.MULTILINE);


    private HawkRuleAdministrator hawkRuleAdministrator;
    private String templateForMethod;
    private int nextUniqueId;


    public GenerateHawkMethodMifs(File templateFile, int nextUniqueId) throws IOException {
        this.hawkRuleAdministrator = new HawkRuleAdministrator(null, null);
        this.nextUniqueId = nextUniqueId;
        this.loadTemplate(templateFile);
    }


    private static String escapeForReplaceAll(CharSequence text) {
        return text.toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
    }


    protected String formatText(String text) {
        return this.formatText(text, true);
    }


    protected String formatText(String text, boolean crLnToSpace) {
        if (null == text) {
            return "";
        } else {
            text = text.trim();
            // Escapes \ into \\
            text = PATTERN_ANTISLASH.matcher(text).replaceAll("\\\\");
            // Escapes ' into \q
            text = PATTERN_QUOTE.matcher(text).replaceAll("\\\\q");
            // Escapes ` into \Q
            text = PATTERN_ANTIQUOTE.matcher(text).replaceAll("\\\\Q");
            // Escapes > into \>
            text = PATTERN_GT.matcher(text).replaceAll("\\\\>");
            // Escapes a tab into \t
            text = PATTERN_TAB.matcher(text).replaceAll("\\\\t");
            if (crLnToSpace) {
                text = PATTERN_CRLN.matcher(text).replaceAll(" ");
            }
            return text;
        }//else
    }//formatText


    public void generate(File outDir) throws Exception {
        outDir.mkdirs();
        if (!outDir.isDirectory()) {
            throw new IllegalArgumentException("File is not a directory.");
        }
        if (!outDir.canWrite()) {
            throw new IllegalArgumentException("Directory is not writable.");
        }

        for (Iterator it = this.hawkRuleAdministrator.getAmiMethods().iterator(); it.hasNext();) {
            final AmiMethod method = (AmiMethod) it.next();
            this.generate(outDir, method);
        }
    }


    protected void generate(File outDir, AmiMethod method) throws IOException {
        if (!outDir.isDirectory()) {
            throw new IllegalArgumentException("File is not a directory");
        }

        final String methodName = method.getName();
        System.out.println(methodName);
        final String fileName = methodName + ".mif";
        final File file = new File(outDir, fileName);
        file.createNewFile();
        if (!file.canWrite()) {
            throw new IllegalArgumentException("Method File is not writable: " + fileName);
        }

        final StringBuffer mifBuffer = this.generate(method);

        final FileOutputStream fos = new FileOutputStream(file);
        fos.write(mifBuffer.toString().getBytes());
        fos.close();
    }


    protected StringBuffer generate(AmiMethod method) {
        CharSequence mif = PATTERN_METHOD_NAME.matcher(this.templateForMethod)
                .replaceAll(escapeForReplaceAll(this.formatText(method.getName())));
        mif = PATTERN_METHOD_HELP.matcher(mif).replaceAll(escapeForReplaceAll(this.formatText(method.getHelp())));
        mif = PATTERN_METHOD_TYPE.matcher(mif).replaceAll(
                escapeForReplaceAll(this.formatText(method.getType().toString())));
        mif = this.processParameters(method.getArguments(), PATTERN_PARAM.matcher(mif), PATTERN_PARAMS_REF);
        mif = this.processParameters(method.getReturns(), PATTERN_RETURN.matcher(mif), PATTERN_RETURNS_REF);

        Matcher newIdMatcher = PATTERN_NEWID.matcher(mif);
        mif = new StringBuffer();
        for (int uniqueId = this.nextUniqueId; newIdMatcher.find(); uniqueId++) {
            newIdMatcher.appendReplacement((StringBuffer) mif, "" + uniqueId);
        }
        newIdMatcher.appendTail((StringBuffer) mif);
        return (StringBuffer) mif;
    }


    protected HawkRuleAdministrator getHawkRuleAdministrator() {
        return this.hawkRuleAdministrator;
    }


    protected String getTemplateForMethod() {
        return this.templateForMethod;
    }


    protected void loadTemplate(File templateFile) throws IOException {
        if (!templateFile.isFile()) {
            throw new IllegalArgumentException("Template File is not a file.");
        }
        final StringBuffer sb = new StringBuffer();

        final Reader reader = new FileReader(templateFile);
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        int sizeRead = reader.read(buffer, 0, bufferSize);
        while (sizeRead >= 0) {
            sb.append(new String(buffer, 0, sizeRead));
            sizeRead = reader.read(buffer, 0, bufferSize);
        }
        reader.close();

        this.templateForMethod = sb.toString();
    }


    public static void main(String[] args) throws Exception {

        final File outDir = new File(args[0]);
        final File templateFile = new File(args[1]);
        final int nextUniqueId = Integer.parseInt(args[2]);

        final GenerateHawkMethodMifs generator = new GenerateHawkMethodMifs(templateFile, nextUniqueId);
        generator.generate(outDir);
    }


    protected StringBuffer processParameters(AmiParameterList methodParams, Matcher paramMatcher,
                                             Pattern referencePattern) {
        final boolean noParameter = (null == methodParams) || (methodParams.size() < 1);
        StringBuffer buffer;

        for (buffer = new StringBuffer(); paramMatcher.find(); ) {
            final String paramTemplate = paramMatcher.group(1);
            paramMatcher.appendReplacement(buffer, "");
            if (noParameter) {
                CharSequence param = PATTERN_PARAM_NAME.matcher(paramTemplate).replaceAll("");
                param = PATTERN_PARAM_HELP.matcher(param).replaceAll("");
                buffer.append(param);
            } else {
                for (Iterator it = methodParams.iterator(); it.hasNext();) {
                    final AmiParameter methodParam = (AmiParameter) it.next();
                    CharSequence param = PATTERN_PARAM_NAME.matcher(paramTemplate)
                            .replaceAll(escapeForReplaceAll(this.formatText(methodParam.getName())));
                    param = PATTERN_PARAM_HELP.matcher(param)
                            .replaceAll(escapeForReplaceAll(this.formatText(methodParam.getHelp())));
                    buffer.append(param);
                }//for
            }//if
        }//for
        paramMatcher.appendTail(buffer);

        final Matcher referenceMatcher = referencePattern.matcher(buffer);
        for (buffer = new StringBuffer(); referenceMatcher.find();) {
            String ref = referenceMatcher.group(1);
            Matcher matcher = PATTERN_REF_EMPTY.matcher(ref);
            if (matcher.find()) {
                if (noParameter) {
                    ref = matcher.replaceAll(escapeForReplaceAll(matcher.group(1)));
                } else {
                    ref = matcher.replaceAll("");
                }
            }
            matcher = PATTERN_REF_NOTEMPTY.matcher(ref);
            if (matcher.find()) {
                if (noParameter) {
                    ref = matcher.replaceAll("");
                } else {
                    ref = matcher.replaceAll(escapeForReplaceAll(matcher.group(1)));
                }
            }
            referenceMatcher.appendReplacement(buffer, ref);
        }//for
        referenceMatcher.appendTail(buffer);

        return buffer;
    }


}