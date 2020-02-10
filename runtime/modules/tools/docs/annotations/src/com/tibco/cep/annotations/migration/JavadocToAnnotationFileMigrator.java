package com.tibco.cep.annotations.migration;


/**
 * User: nprade
 * Date: 4/18/12
 * Time: 1:20 PM
 */
public class JavadocToAnnotationFileMigrator {


    private final StringBuffer content = new StringBuffer();
    private JavadocToAnnotationCommentMigrator javadocCommentMigrator = null;

    private boolean changed = false;


    public String getContent() {
        return this.content.toString();
    }


    public boolean isChanged() {
        return changed;
    }


    public void processLine(
            String line) {

        boolean isStart = false;

        if (null == javadocCommentMigrator) {
            final int javadocStartIndex = line.indexOf("/**");
            final int lineCommentIndex = line.indexOf("//");
            if ((javadocStartIndex < 0)
                    || ((lineCommentIndex >= 0) && (lineCommentIndex < javadocStartIndex))) {
                content.append(line)
                        .append("\n");
                return;
            }
            else {
                content.append(line.substring(0, javadocStartIndex));
                javadocCommentMigrator = new JavadocToAnnotationCommentMigrator();
                line = line.substring(javadocStartIndex);
                isStart = true;
            }
        }

        final int endIndex = line.indexOf("*/", isStart ? 3 : 0);
        if (endIndex < 0) {
            javadocCommentMigrator.processLine(line);
        }
        else {
            javadocCommentMigrator.processLine(line.substring(0, endIndex + 2));
            javadocCommentMigrator.close();
            String javadocComment = javadocCommentMigrator.getContent();
            if (javadocCommentMigrator.isChanged()) {
                changed = true;
                javadocComment = javadocComment.replaceAll("\n", "\n    ");
            }
            content.append(javadocComment).append("\n");
            javadocCommentMigrator = null;
            if (line.length() > (endIndex + 2)) {
                processLine(line.substring(endIndex + 2));
            }
        }

    }


}
