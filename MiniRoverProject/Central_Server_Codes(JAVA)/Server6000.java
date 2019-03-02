import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server6000 extends Thread
{
	
	ServerSocket server;
	Socket socket;
	
	@Override
 	public void run() 
 	{
		
		super.run();
 
		System.out.println("Server 6000 is running");
		
		try
		{
 
			server = new ServerSocket(6000);
 
			while(true)
			{
				socket = server.accept(); // Accepting connection
				socket.setKeepAlive(true);
				new Socket6000(socket).start();// Starting new thread for the accepted connection
 		 	}
		} 
		catch (IOException e)
		{		
			e.printStackTrace();
		}

	}
}
