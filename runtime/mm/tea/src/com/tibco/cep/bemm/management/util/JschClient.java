package com.tibco.cep.bemm.management.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.h2.store.fs.FileUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;

/**
 * @author dijadhav
 *
 */
public class JschClient {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(JschClient.class);

	private String username;
	private String password;
	private int port;
	private String hostIp;
	private Session session;
	private ChannelSftp sftpChannel;
	private ChannelExec execChannel;

	public class HostUserInfo implements UserInfo {
		String password = null;

		@Override
		public String getPassphrase() {
			return null;
		}

		@Override
		public String getPassword() {
			return password;
		}

		public void setPassword(String passwd) {
			password = passwd;
		}

		@Override
		public boolean promptPassphrase(String message) {
			return false;
		}

		@Override
		public boolean promptPassword(String message) {
			return false;
		}

		@Override
		public boolean promptYesNo(String message) {
			return true;
		}

		@Override
		public void showMessage(String message) {
		}
	}

	public JschClient(String username, String password, String hostIp, int port) {

		this.username = username;
		this.password = password;
		this.hostIp = hostIp;
		this.port = port;
	}

	/**
	 * Connect the Jsch session
	 * 
	 * @return Session object
	 * @throws JSchException
	 */
	public Session establishJschSession() throws JSchException {
		if (session != null)
			return session;
		JSch jsch = new JSch();

		session = jsch.getSession(username, hostIp, port);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications", "password");
		session.setPassword(password);
		// Create UserInfo instance in order to support SFTP connection to any
		// machine
		// without a key username and password will be given via UserInfo
		// interface.
		UserInfo userInfo = new HostUserInfo();
		session.setUserInfo(userInfo);
		session.setConfig(config);
		session.setTimeout(60000);
		session.connect();

		return session;
	}

	/**
	 * If channel is connected then return connected channel else connect and
	 * return it.
	 * 
	 * @return SFTP channel
	 * @throws JSchException
	 */
	public ChannelSftp establishSftpChannel() throws JSchException {
		if (sftpChannel != null) {
			return sftpChannel;
		}

		establishJschSession();
		sftpChannel = (ChannelSftp) session.openChannel("sftp");
		sftpChannel.connect();

		return sftpChannel;
	}

	/**
	 * Create the Exec Channel
	 * 
	 * @return Exec channel
	 * @throws JSchException
	 */
	public void createExecChanel() throws JSchException {
		if (execChannel == null) {
			establishJschSession();
			execChannel = (ChannelExec) session.openChannel("exec");
			execChannel.setInputStream(null);
			execChannel.setErrStream(System.err);
		}
	}

	/**
	 * Reconnect the session.
	 * 
	 * @return Jsch Session.
	 * @throws JSchException
	 */
	public Session reConnectJschSession() throws JSchException {
		disconnectJschSession();
		return establishJschSession();
	}

	/**
	 * Does not re-establish the session (only the channel). Call
	 * reConnectJschSession to re-establish the session
	 */
	public ChannelSftp reEstablishSftpChannel() throws JSchException {
		disconnectSftpChannel();
		return establishSftpChannel();
	}

	/**
	 * @param retryCount
	 * @param waitInterval
	 * @throws Throwable
	 */
	public void waitForExecChannelClose(int retryCount, long waitInterval) {
		if (execChannel != null) {
			for (int loop = 0; loop < retryCount; loop++) {
				try {
					if (execChannel.isClosed())
						break;
					Thread.sleep(waitInterval);
				} catch (InterruptedException ex) {
					LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}
		}
	}

	/**
	 * Disconnect the session.
	 */
	public void disconnectJschSession() {
		disconnectSftpChannel();

		if (session != null)
			session.disconnect();

		session = null;
	}

	/**
	 * Disconnect the sftp channel
	 */
	public void disconnectSftpChannel() {
		if (sftpChannel != null)
			sftpChannel.disconnect();

		sftpChannel = null;
	}

	/**
	 * Disconnect the exec channel
	 */
	public void disconnectExceChannel() {
		if (execChannel != null)
			execChannel.disconnect();

		execChannel = null;
	}

	/**
	 * This method is used to execute the given command
	 * 
	 * @param command
	 *            - Command to be executed.
	 * @throws JschCommandFailException
	 */
	public void executeCommand(String command) throws JschCommandFailException {
		try {
			if (null == execChannel) {
				createExecChanel();
			}

			LOGGER.log(Level.DEBUG, "Executing " + command + " command");
			InputStream errStream = execChannel.getErrStream();
			execChannel.setCommand(command);
			execChannel.connect();

		} catch (JSchException e) {
			throw new JschCommandFailException(e);
		} catch (IOException e) {
			throw new JschCommandFailException(e);
		}
	}

	/**
	 * @param dir
	 * @throws JSchException
	 * @throws SftpException
	 */
	public void makeDir(String dir) throws JSchException, SftpException {
		establishSftpChannel();
		if (null != sftpChannel) {
			sftpChannel.mkdir(dir);
		}
	}

	
	/**
	 * This method is used to change the directory on remote machine
	 * 
	 * @param dir
	 *            - Path of the directory.
	 * @throws JSchException
	 * @throws SftpException
	 */
	public void cd(String dir) throws JSchException, SftpException {
		establishSftpChannel();
		if (null != sftpChannel) {
			sftpChannel.cd(dir);
		}
	}

	/**
	 * This method is used to upload the file on remote machine
	 * 
	 * @param fileName
	 *            - Name .
	 * @throws JSchException
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public void upload(String source, String destination) throws JSchException, SftpException, FileNotFoundException {
		establishSftpChannel();
		if (null != sftpChannel) {
			sftpChannel.put(source, destination);
		}
	}

	/**
	 * @param source
	 * @param destination
	 * @throws JSchException
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public void upload(InputStream source, String destination)
			throws JSchException, SftpException, FileNotFoundException {
		establishSftpChannel();
		if (null != sftpChannel) {
			sftpChannel.put(source, destination);
		}
	}

	/**
	 * @param source
	 * @return
	 * @throws JSchException
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public InputStream download(String source) {
		try {
			establishSftpChannel();
			if (null != sftpChannel) {
				return sftpChannel.get(source);
			}
		} catch (Exception e) {
		}
		return null;
	}

	public boolean isPathExits(String path) {
		boolean isExits = false;
		try {
			establishSftpChannel();
			if (null != sftpChannel) {
				SftpATTRS attrs = sftpChannel.stat(path);
				if (null != attrs) {
					isExits = true;
				}
			}
		} catch (JSchException | SftpException e) {
		}
		return isExits;

	}

	public String downloadLog(String path, boolean isWindows, String name)
			throws JSchException, SftpException, IOException {

		if (isPathExits(path)) {

			String tempFile = FileUtils.createTempFile(name, "_logs.zip", true, true);
			if (null != sftpChannel) {

				InputStream logFile = download(path);
				if (null != logFile) {
					FileOutputStream fos = new FileOutputStream(tempFile);
					ZipOutputStream zos = new ZipOutputStream(fos);

					if (isWindows) {
						path = path.replaceAll("\\\\", "/");

						ZipEntry zipEntry = new ZipEntry(name + ".log");
						zos.putNextEntry(zipEntry);
						byte[] bytes = new byte[1024];
						int length;
						while ((length = logFile.read(bytes)) >= 0) {
							zos.write(bytes, 0, length);
						}

						zos.closeEntry();

					}

					zos.close();
					fos.close();
				}
			}
			return tempFile;
		}
		return null;

	}
}
