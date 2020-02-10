package com.tibco.cep.releases.be;

import com.tibco.cep.releases.InstalledFilesChanges;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: nprade
 * Date: 7/8/11
 * Time: 4:24 PM
 */
public class BeAssembleProvider {


    private static final String HF_PATH_PREFIX = "/hotfix/";
    private static final Pattern SUBSTITUTION_PATTERN = Pattern.compile("<!--\\s*%%((?:.*/)?([\\w-]+))%%\\s*-->");


    private void appendIncludedPaths(
            String assemblyPath,
            StringBuffer sb,
            SortedSet<String> paths) {

        final StringBuilder hf = new StringBuilder();
        final StringBuilder noHf = new StringBuilder();

        if (null != paths) {
            for (final String path : paths) {
                if (path.startsWith(HF_PATH_PREFIX)) {
                    hf.append("\n\t\t\t\t<include name=\"")
                            .append(path.substring(HF_PATH_PREFIX.length()))
                            .append("*\"/>");

                } else {
                    noHf.append("\n\t\t\t\t<include name=\"")
                            .append(path.substring(1))
                            .append("*\"/>");
                }
            }
        }

        if ((hf.length() + noHf.length()) < 1) {
            sb.append("\n\t\t<mkdir dir=\"${be.exp}/Final/").append(assemblyPath).append("\"/>");

        } else {
            if (hf.length() > 0) {
                sb.append("\n\t\t<copy todir=\"${be.exp}/Final/")
                        .append(assemblyPath)
                        .append("/hotfix/\">\n\t\t\t<fileset dir=\"${ga.dir}/")
                        .append(assemblyPath)
                        .append("/\">")
                        .append(hf)
                        .append("\n\t\t\t</fileset>\n\t\t</copy>");
            }

            if (noHf.length() > 0) {
                sb.append("\n\t\t<copy todir=\"${be.exp}/Final/")
                        .append(assemblyPath)
                        .append("/\">\n\t\t\t<fileset dir=\"${ga.dir}/")
                        .append(assemblyPath)
                        .append("/\">")
                        .append(noHf)
                        .append("\n\t\t\t</fileset>\n\t\t</copy>");
            }
        }
    }


    public String getAssembleText(
            InstalledFilesChanges changes) {

        final StringBuffer sb = new StringBuffer();
        final Matcher matcher = SUBSTITUTION_PATTERN.matcher(TemplateHolder.TEMPLATE);
        while (matcher.find()) {
            final String substitutionString = matcher.group(1);
            final String assemblyName = matcher.group(2);
            matcher.appendReplacement(sb, "<!--");
            sb.append(assemblyName).append("-->");

            this.appendIncludedPaths(substitutionString, sb, changes.get(assemblyName));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


    private static class TemplateHolder {

        static final String TEMPLATE;
        static final String TEMPLATE_PATH = "assembleTemplate.xml";

        static {
            final InputStream is = TemplateHolder.class.getResourceAsStream(TEMPLATE_PATH);
            try {
                try {
                    final byte[] bytes = new byte[2048];
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        for (int numRead = is.read(bytes); numRead >= 0; numRead = is.read(bytes)) {
                            baos.write(bytes, 0, numRead);
                        }
                        TEMPLATE = new String(baos.toByteArray(), "UTF-8");
                    } finally {
                        baos.close();
                    }
                } finally {
                    is.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void main(
            String[] args)
            throws Exception {

        if (args.length < 2) {
            System.err.println("Please provide the URL, the branch name, "
                    +" and optionally the start revision number and the end revision number.");
            System.exit(-1);
        }

        final String url = args[0];
        final String branch = args[1];
        final long start = (args.length < 3) ? 0 : Long.parseLong(args[2]);
        final long end = (args.length < 4) ? -1 : Long.parseLong(args[3]);
        final BePackagingType packagingType = (args.length < 5)
                ? BePackagingType.HOTFIX
                : BePackagingType.valueOf(args[4]);

        System.err.println("Generating assemble.xml file content for URL <" + url
                + ">, branch '" + branch
                + "', changes " + start + " to " + end + ".");

        final BeVcsBranchContext vcsBranchContext = new BeVcsBranchContext(url, branch);

        final BeChangesProvider changesProvider = (BePackagingType.HOTFIX == packagingType)
                ? new BeHfChangesProvider(vcsBranchContext)
                : new BeSpChangesProvider(vcsBranchContext);

        final InstalledFilesChanges changes = changesProvider.getChanges(start, end);

        final SortedSet<String> unmappedPaths = changes.getFilePaths(BeChangesProvider.UNMAPPED_PATHS_NAME);
        if ((null != unmappedPaths) && !unmappedPaths.isEmpty()) {
            System.err.println("Found unmapped path(s):");
            for (final String unmappedPath : unmappedPaths) {
                System.err.println(" " + unmappedPath);
            }
        }

        System.out.println(new BeAssembleProvider().getAssembleText(changes));
    }


}
