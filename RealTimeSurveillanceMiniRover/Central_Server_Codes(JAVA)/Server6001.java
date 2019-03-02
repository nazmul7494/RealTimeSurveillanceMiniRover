import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server6001 extends Thread
{
 
	ServerSocket server;
	Socket socket ;
  
	@Override
	public void run()
	{
	

		System.out.println("Server 6001 is running");

		try
		{

 			server=new ServerSocket(6001);

			while(true)
			{
 				socket = server.accept(); // Accepting new connection
 				socket.setKeepAlive(true);
				new Socket6001(socket).start(); // Starting the appropriate thread for the new connection
 
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
}