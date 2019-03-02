import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Socket6000 extends Thread
{
	
	Socket socket;

	DataInputStream inputStream;
	DataOutputStream outputStream;

	int username; // username = 1 for mini-rover, username = 2 for the remote control device

	public Socket6000(Socket socket)
	{
		super();
		this.socket = socket;
	}
	
	@Override
	public void run() 
	{
		
		super.run();

		try {

			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());

			 
			// Reading the user name of the connected client (1 = the R-pi on the mini rover, 2 = The remote control device)
			username = inputStream.readByte();

			System.out.println("In socket 6000, "+username+" is connected");
			
			Main.map6000.put(""+username, this);
 			 
			while(true)
			{
				// If this is the mini-rover's socket and the remote control device is also connected
				// so that there are two objects in the Hashmap
				if( (Main.map6000.size() == 2) && (username == 1) ) // username ==1 means it's the mini-rover 
				{
					
					//receive the message from remote control station for the Raspberry-pi
					String message = inputStream.readUTF();					
					
					//send the control command to the pi(ASCII 49 == Decimal 1)
					Main.map6000.get(""+49).outputStream.writeUTF(message);
					  
				}
				 
			}
			 
		}
		catch (IOException e)
		{			
			 System.out.println("Socket6001, "+username+" Exception: "+e.getMessage());
		}
 
	}
}
