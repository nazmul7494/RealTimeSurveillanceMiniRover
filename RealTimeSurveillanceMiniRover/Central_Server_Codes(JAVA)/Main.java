import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;	
import java.util.Map;
import javax.swing.JOptionPane;

public class Main
{
 	
 	// Socket6000 is for exchanging movement control data between the remote control device and the mini-rover
 	static Map<String,Socket6000> map6000=new HashMap<String,Socket6000>();

 	// Socket6001 is for exchanging webcam video stream data from the mini-rover's webcam
 	// to the remote control device via the central server
 	static Map<String,Socket6001> map6001=new HashMap<String,Socket6001>();

  	//static Socket socket;
	
 	public static void main(String[] args) 
 	{
        
 		Server6000 x = new Server6000();
 		x.start(); // Starting the thread
 		
 		Server6001 y = new Server6001();
 		y.start();// Starting the thread
 	}

}
