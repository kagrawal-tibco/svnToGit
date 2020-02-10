package com.tibco.cep.vcs.svn;

import com.tibco.cep.vcs.VcsBranchContext;
import com.tibco.cep.vcs.VcsSourceFileProvider;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;

/**
 * User: nprade
 * Date: 7/14/11
 * Time: 12:15 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class SvnSourceFileProvider
        implements VcsSourceFileProvider {


    private static final String TMP_FILE_PREFIX = SvnSourceFileProvider.class.getSimpleName();
    private static final String TMP_FILE_SUFFIX = ".tmp";


    private final VcsBranchContext vcsBranchContext;


    public SvnSourceFileProvider(
            VcsBranchContext vcsBranchContext) {

        this.vcsBranchContext = vcsBranchContext;
    }


    @Override
    public byte[] getSourceFile(
            String url)
            throws Exception {

        return this.getSourceFile(url, -1);
    }


    public byte[] getSourceFile(
            String url,
            long revisionEnd)
            throws Exception {


        final SvnConnection svnConnection = (SvnConnection) this.vcsBranchContext.makeConnection();

        final File tmpFile = File.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SUFFIX);
        try {
            final SVNUpdateClient svnUpdateClient = svnConnection.getClientManager().getUpdateClient();
            svnUpdateClient.doExport(
                    SVNURL.parseURIEncoded(url),
                    tmpFile,
                    SVNRevision.UNDEFINED,
                    ((revisionEnd <= 0) ? SVNRevision.HEAD : SVNRevision.create(revisionEnd)),
                    null,
                    true,
                    SVNDepth.EMPTY);

            final FileInputStream fs = new FileInputStream(tmpFile);
            try {
                return readStreamToByteArray(fs);
            } finally {
                fs.close();
            }
        } finally {
            if (!tmpFile.delete()) {
                tmpFile.deleteOnExit();
            }
        }
    }


    public static void main(
            String[] args)
            throws Exception {

        if (args.length < 1) {
            System.err.println("Please provide the url, and optionally the max revision number.");
            System.exit(1);
        }

        final String url = args[0];
        final long maxRevision = (args.length < 2) ? -1 : Long.parseLong(args[1]);

        System.err.println("Getting content of <" + url + "> at " + maxRevision + ".");

        @SuppressWarnings({"unchecked"})
        final Class<? extends VcsBranchContext> vcsBranchContextClass = (Class<? extends VcsBranchContext>)
                Class.forName("com.tibco.cep.releases.be.BeVcsBranchContext");
        final Constructor<? extends VcsBranchContext> constructor = vcsBranchContextClass.getConstructor(String.class);
        final VcsBranchContext vcsBranchContext = constructor.newInstance(url);

        final SvnSourceFileProvider svnSourceFileProvider = new SvnSourceFileProvider(vcsBranchContext);
        System.out.println(new String(svnSourceFileProvider.getSourceFile(url)));
    }


    private byte[] readStreamToByteArray(
            FileInputStream fs)
            throws IOException {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] bytes = new byte[2048];
            for (int read = fs.read(bytes); read >= 0; read = fs.read(bytes)) {
                outputStream.write(bytes, 0, read);
            }
            return outputStream.toByteArray();
        } finally {
            outputStream.close();
        }
    }


}
