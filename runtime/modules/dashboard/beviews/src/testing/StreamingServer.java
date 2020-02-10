import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.ConsoleHandler;

public class StreamingServer{

	private static ServerSocket _serverSocket;
	private static Socket _clientSocket;
	
	public static void main(String[] args){
		short port = -1;
		
		//Create the server
		try{
			port = Short.parseShort(args[0]);
			_serverSocket = new ServerSocket(port);
			System.out.println("Created server on port " + port);
		}
		catch(Exception ex){
			System.err.println(ex.toString());
		}
		
		//Accept a connection and listen for input
		try{
			System.out.println("\nWaiting for client connection...");
			_clientSocket = _serverSocket.accept();
			System.out.println("\nAccepted connection from client " + _clientSocket.getInetAddress().toString());
			System.out.println("\nPlease enter text to send to client:\n\n");
			//DataInputStream is = new DataInputStream(_clientSocket.getInputStream());
			PrintStream os = new PrintStream(_clientSocket.getOutputStream());
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			while(true){
				line = console.readLine();
				os.print("<text><![CDATA["+line+"]]></text>\0");
				if(line.equals("\\bye")) break;
			}
		}
		catch(Exception ex){
			System.err.println("Communication: " + ex.toString());
		}
		finally{
			try{
				if(_clientSocket != null && !_clientSocket.isClosed()){
					System.out.println("Closing Client...");
					_clientSocket.close();
				}
				if(_serverSocket != null && !_serverSocket.isClosed()){
					System.out.println("Closing Server...");
					_serverSocket.close();
				}
			}
			catch(Exception ex){
				System.err.println("Choked closing sockets!!!");
			}
		}
	}

}
