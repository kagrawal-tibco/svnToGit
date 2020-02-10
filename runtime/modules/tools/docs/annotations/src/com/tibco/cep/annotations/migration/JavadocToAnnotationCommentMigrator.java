package com.tibco.cep.annotations.migration;

import com.tibco.cep.annotations.CategoryAnnotationDescriptor;
import com.tibco.cep.annotations.FunctionAnnotationDescriptor;
import com.tibco.cep.annotations.FunctionParamAnnotationDescriptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: nprade
 * Date: 4/18/12
 * Time: 3:55 PM
 */
public class JavadocToAnnotationCommentMigrator {


    private static final Pattern PATTERN_LINE = Pattern.compile(
            "^\\s*(?:\\u002a|(?:/\\u002a\\u002a))(?:/|(?:\\s*" +
                    "((@\\S+)?.*?)" +
                    "(?:\\u002a/)?" +
                    "))$");

    private static final Pattern PATTERN_TAG = Pattern.compile(
            "@(\\S+)(?:\\s+((\\S+)(?:\\s+((\\S+)(?:\\s+(.+\\s*)*)?))?))?(?:\\u002a/)?\n?");

    private static final Pattern PATTERN_ESCAPE = Pattern.compile(
            "\\{@(\\S+)(?:\\s+([^\\}]+?(?:\\s+([^\\s\\}\\)]+))?))?\\}");


    private StringBuffer rawContent = new StringBuffer();
    private StringBuffer tag = new StringBuffer();
    private CategoryAnnotationDescriptor catAnnotation = new CategoryAnnotationDescriptor();
    private FunctionAnnotationDescriptor fnAnnotation = new FunctionAnnotationDescriptor();


    public void close() {
        if ((null != tag) && (tag.length() > 0)) {
            processTag();
            tag = null;
        }
    }


    public String getContent() {
        if (null != catAnnotation.category()) {
            return catAnnotation.toString();
        }
        else if (null != fnAnnotation.name()) {
            return fnAnnotation.toString();
        }
        else {
            return rawContent.toString().substring(1);
        }
    }


    public boolean isChanged() {
        return (null != fnAnnotation.name())
                || (null != catAnnotation.category());
    }


    public void processLine(
            String line) {

        if (null == tag) {
            throw new IllegalStateException("Closed");
        }

        rawContent.append("\n").append(line);

        final Matcher matcher = PATTERN_LINE.matcher(line);
        if (matcher.matches()) {
            if (null != matcher.group(2)) {
                processTag();
                tag = new StringBuffer();
            }
            final String text = matcher.group(1);
            if (null != text) {
                tag.append(text);
            }
            tag.append("\n");
        }
    }


    private void processTag() {
        final Matcher matcher = PATTERN_TAG.matcher(tag);
        if (matcher.matches()) {
            final String tagName = matcher.group(1);
            final String text1 = matcher.group(2);
            final String word1 = matcher.group(3);
            final String text2 = matcher.group(4);
            final String word2 = matcher.group(5);
            final String text3 = matcher.group(6);
            if (".category".equals(tagName)) {
                final String text = unescape(text1);
                if (!"public-api".equals(text)) {
                    catAnnotation.setCategory(text);
                }
            }
            else  if (".cautions".equals(tagName)) {
                fnAnnotation.setCautions(unescape(text1));
            }
            else if (".description".equals(tagName)) {
                fnAnnotation.setDescription(unescape(text1));
            }
            else if (".domain".equals(tagName)) {
                fnAnnotation.getDomain().add(unescape(text1));
            }
            else if (".example".equals(tagName)) {
                fnAnnotation.setExample(unescape(text1));
            }
            else if (".mapper".equals(tagName)) {
                fnAnnotation.setMapper(Boolean.valueOf(word1));
            }
            else if (".name".equals(tagName)) {
                fnAnnotation.setName(unescape(text1));
            }
            else if (".see".equals(tagName)) {
                fnAnnotation.setSee(unescape(text1));
            }
            else if (".signature".equals(tagName)) {
                fnAnnotation.setSignature(unescape(text1));
            }
            else if (".synopsis".equals(tagName)) {
                final String text = unescape(text1);
                catAnnotation.setSynopsis(text);
                fnAnnotation.setSynopsis(unescape(text1));
            }
            else if (".version".equals(tagName)) {
                fnAnnotation.setVersion(unescape(text1));
            }
            else if ("param".equals(tagName)) {
                fnAnnotation.getParams().add(
                        new FunctionParamAnnotationDescriptor(
                                unescape(word1), unescape(word2), unescape(text3)));
            }
            else if ("return".equals(tagName)) {
                fnAnnotation.setFreturn(new FunctionParamAnnotationDescriptor(
                        "", unescape(word1), unescape(text2)));
            }
        }
    }


    private static String unescape(
            String text) {

        if (null == text) {
            return null;
        }

        final Matcher matcher = PATTERN_ESCAPE.matcher(text.replaceAll("\\\\", "\\\\\\\\"));
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            final String tagName = matcher.group(1);
            final String value = matcher.group(2);
            final String label = matcher.group(3);
            if ("code".equals(tagName)) {
                matcher.appendReplacement(sb, "<code>");
                sb.append(value.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"))
                        .append("</code>");
            }
            else if ("link".equals(tagName) || "linkplain".equals(tagName) || "value".equals(tagName)) {
                if ((null != label) && !label.isEmpty()) {
                    matcher.appendReplacement(sb, label);
                }
                else {
                    matcher.appendReplacement(sb, "<code>");
                    sb.append(value.replaceAll("(\\S)#", "$1.").replaceAll("(^|\\s)#", "$1"))
                            .append("</code>");
                }
            }
            else if ("literal".equals(tagName)) {
                matcher.appendReplacement(sb,
                        value.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
            }
            else {
                matcher.appendReplacement(sb, "");
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


}
