package com.tibco.cep.dashboard.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.List;

import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.XMLUtil;
import com.tibco.cep.dashboard.integration.standalone.TCPStreamingServer;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;

public class RemoteDashboardAgentClient implements DashboardAgentClient {

	private String pullRequestURL;
	private URL requestURL;
	private int streamingPort;

	private boolean keepReading;
	private Socket streamingSocket;
	private Thread thread;

	public RemoteDashboardAgentClient(String pullRequestURL,int streamingPort) throws MalformedURLException{
		this.pullRequestURL = pullRequestURL;
		this.streamingPort = streamingPort;
		this.requestURL = new URL(this.pullRequestURL);
	}

	@Override
	public void close() throws IOException {
		//do nothing
	}

	@Override
	public String execute(BizRequest request) throws IOException {
		//open a connection
		HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setInstanceFollowRedirects(true);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.connect();
		//post the request xml
        OutputStream out = connection.getOutputStream();
//        String encodedRequest = URLEncoder.encode(request.toXML(), "UTF-8");
		out.write(request.toXML().getBytes());
		//read the response xml
        InputStream is = connection.getInputStream();
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(responseCode+":"+connection.getResponseMessage());
        }
        String contentType = connection.getContentType();
//        if (contentType == null || contentType.startsWith("text/xml") == false){
//            throw new IOException("Unexpected content type [" + contentType+"]");
//        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        int ch = (int) br.read();
        while (ch != -1) {
            sb.append((char)ch);
            ch = br.read();
        }
        if (contentType != null && contentType.toLowerCase().startsWith("application") == true) {
        	return sb.toString();
        }
        return extractContent(sb.toString());
	}

	private String extractContent(String response) throws IOException{
		Node responseNode;
		try {
			responseNode = XMLUtil.parse(response);
		} catch (Exception e) {
			throw new IOException(e);
		}
		String status = XMLUtil.getString(responseNode, "state");
		if (status != null) {
			if (status.equals(BizResponse.SUCCESS_STATUS) == true){
				String content = null;
				Node contentNode = XMLUtil.getNode(responseNode, "content");
				List<Node> childNodes = XMLUtil.getChildNodes(contentNode, true);
				if (childNodes.isEmpty() == true){
					content = XMLUtil.getString(contentNode, "/");
				}
				else {
					Node node = childNodes.get(0);
					if (node.getNodeType() == Node.CDATA_SECTION_NODE){
						content = node.getNodeValue();
					}
					else {
						content = XMLUtil.toSimpleString(node);
					}
				}
				return content;
			}
			throw new IOException(XMLUtil.getString(responseNode, "message"));
		}
		return response;
	}

	@Override
	public void pause() throws IOException {
		//do nothing
	}

	@Override
	public void resume() throws IOException {
		//do nothing
	}

	@Override
	public void start() throws IOException {
		//do nothing
	}

	@Override
	public void startStreaming(BizRequest request) throws IOException {
		if (streamingSocket != null){
			throw new IllegalStateException("streaming has already been started");
		}
		streamingSocket = new Socket(requestURL.getHost(),streamingPort);
		String requestXML = request.toXML();
		byte[] bytes = requestXML.getBytes();
		streamingSocket.getOutputStream().write(bytes);
		streamingSocket.getOutputStream().write(TCPStreamingServer.DEFAULT_EOL);
		keepReading = true;
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					InputStreamReader reader = new InputStreamReader(streamingSocket.getInputStream());
					StringBuilder sb = new StringBuilder();
					while(keepReading == true){
						char character = (char) reader.read();
						if (character == -1){
							keepReading = false;
						}
						else if (character == TCPStreamingServer.DEFAULT_EOL){
							System.err.println(sb.toString());
							sb.setLength(0);
						}
						else {
							sb.append(character);
						}
					}
				} catch (IOException e) {
					if (keepReading == true) {
						e.printStackTrace();
					}
				} finally {
					try {
						streamingSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
		thread.start();
		//do nothing
	}

	@Override
	public void stop() throws IOException {
		keepReading = false;
		disconnectFromStreamingServer();
	}

	@Override
	public void stopStreaming(BizRequest request) throws IOException {
		execute(request);
		disconnectFromStreamingServer();
	}

	private void disconnectFromStreamingServer() {
		if (keepReading == false){
			return;
		}
		keepReading = false;
		try {
			streamingSocket.close();
		} catch (IOException ignore) {
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
