package com.tibco.be.bemm.functions;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 4/24/12
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class JschClient {
    public final static String MM_TOOLS_PRIVATE_KEY_FILE = "be.mm.ssh.privatekey.file";
    public final static String MM_TOOLS_PRIVATE_KEY_PASS_PHRASE = "be.mm.ssh.privatekey.passphrase";

    private String username;
    private String password;
    private int port;
    private String hostIp;
    private Logger logger;

    private Session session;
    private ChannelSftp sftpChannel;

    public JschClient(String username, String password,
                      String hostIp, int port, Logger logger) {

        this.username = username;
        this.password = password;
        this.hostIp = hostIp;
        this.port = port;
        this.logger = logger;
    }

    public Session establishJschSession() throws JSchException {
        if (session != null)
            return session;

        JSch jsch = new JSch();

        String privateKeyFileLoc = System.getProperty(MM_TOOLS_PRIVATE_KEY_FILE);
        final String passPhrase = System.getProperty(MM_TOOLS_PRIVATE_KEY_PASS_PHRASE);

        //Do authentication using private/public keys.
        if (privateKeyFileLoc != null) {
            try {
                logger.log(Level.INFO, "Attempting authentication to %s@%s:%s using PRIVATE KEY '%s'",
                        username,hostIp,port,privateKeyFileLoc);

                if (passPhrase == null)
                    jsch.addIdentity(privateKeyFileLoc);
                else
                    jsch.addIdentity(privateKeyFileLoc, passPhrase);

                session = jsch.getSession(username, hostIp, port);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
            } catch (JSchException e) {
                final String emsg = "Check if you provided the correct passphrase using the -pph CL option. ";

                logger.log(Level.WARN, "Authentication to %s@%s:%s " +
                        "using PRIVATE KEY '%s' FAILED. %s%s ",
                        username, hostIp, port,
                        privateKeyFileLoc, emsg, e.getMessage());

                jsch.removeAllIdentity();
                privateKeyFileLoc = null;
            }
        }

        //if private key authentication not set or failed proceed with password authentication
        if (privateKeyFileLoc == null) {
            logger.log(Level.INFO, "Attempting PASSWORD authentication to %s@%s:%s",
                                    username,hostIp,port);

            session = jsch.getSession(username, hostIp, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
        }

        logger.log(Level.INFO, "Authentication SUCCESSFUL and session connected");

        return session;
    }

    public ChannelSftp establishSftpChannel() throws JSchException {
        if (sftpChannel != null) {
            return sftpChannel;
        }

        establishJschSession();

        sftpChannel = (ChannelSftp)session.openChannel("sftp");
        sftpChannel.connect();

        logger.log(Level.DEBUG, "SFTP channel connected");
        return sftpChannel;
    }

    public Session reConnectJschSession() throws JSchException {
        disconnectJschSession();
        return establishJschSession();
    }

    /** Does not re-establish the session (only the channel).
     *  Call reConnectJschSession to re-establish the session */
    public ChannelSftp reEstablishSftpChannel() throws JSchException {
        disconnectSftpChannel();
        return establishSftpChannel();
    }

    public void disconnectJschSession(){
        disconnectSftpChannel();

        if (session != null)
            session.disconnect();

        session = null;
    }

    public void disconnectSftpChannel() {
        if (sftpChannel!= null)
            sftpChannel.disconnect();

        sftpChannel = null;
    }




}
