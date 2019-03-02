import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Socket6001 extends Thread
{

	Socket socket;

	DataOutputStream outputStream; 
	DataInputStream inputStream;

	byte[] data,processData;

	int Size = (320*240);
	int userName;

	public Socket6001(Socket socket)
	{
		this.socket = socket;
	}



	@Override
	public void run()
	{

		data = new byte[Size];
		processData = new byte[Size];

		try
		{

			//get the inputstream  to receive text from client to server
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			
			userName = inputStream.readByte() ;
			byte x = 49; // ASCII 49 == Decimal 1

			System.out.println("In socket 6001, "+userName+" is connected"); 
			Main.map6001.put(""+userName,this);

			
			while(true)
			{

				if( (Main.map6001.size()==2) && (userName == x) ) // byte x = 49 so, x is indicating it's the mini-rover
				{
					
					data = new byte[Size];
					inputStream.readFully(data,0,Size);					
 
					processData = processingData(data);
 
					//Get the outputStream of the connected remote control device (username is 2)
					DataOutputStream outs = Main.map6001.get(""+2).outputStream;
					//int z = processData.length;

					if( (outs!=null) && (processData!=null) )
					{  
						//outs.writeInt(z);
						outs.write(processData);
					}	
				}

			}

		} 

		catch (Exception e)
		{			
			System.out.println("Socket6001, "+userName+" ,Exception- "+e.getMessage());
			e.printStackTrace();
		}

	}


	public byte[] processingData(byte[] pix)
	{

		byte[] result  = null;

		try 
		{

			//----------- the size of the image ------------------------------------
			int screen = (320*240);
			int height = 240;
			int width = 320;		

			// --------------- Setting-up and Initializing The Arrays --------------

			byte[] pixel = pix;// will hold the recieved bytes
			int[] pixInt = new int[screen]; // will store the bytes after they have been wrapped around        
			int p; // a temporary int variable for holding an un-checked byte value

			//-------------- Converting byte data into image and writing to the output stream -----------

			BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

			for(int i=0;i<(screen);i++)
			{
				p = (int) pixel[i]; // initial byte value from array 'pixel'


				// Because python uses unsigned byte and Java uses signed byte. So, we need to wrap around the values
				if(p<0)
				{
					pixInt[i] = 256+p; // If negative, wrapped-up and stored in 'pixInt'
				}

				else
				{
					pixInt[i] = p; // If positive, the byte value is stored into 'pixInt' as it was.
				}
			}       

			//---------------- Converting the wrapped pixInt values to RGB format ------

			int[] rgbArray = new int[320*240];

			for(int i=0;i<screen;i++)
			{
				// As for a grayscale image, all the RGB component values are same ------- 
				int x = (pixInt[i]*65536);// the R value in RGB format
				int y =(pixInt[i]*256); // the G value in RGB format
				int z = pixInt[i];// the B value in RGB format                
				rgbArray[i] = (x+y+z);              
			}			

			          
			// --------------------- setting the RGB pixel values of the BufferedImage -------
			int count=0; 
			for(int i=0;i<height;i++)
			{
				for(int j=0;j<width;j++)
				{                                                    
					int x = rgbArray[count];           
					image.setRGB(j,i,x);
					count++;                   
				}
			}      

			
			if(image==null)
			{
				System.out.println("Image is null");
			}


			// -------- Converting the prepared image into a bytearray and writing to the output stream---------
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image,"jpg",baos);
			result = baos.toByteArray();

		
		}// try ends

		catch (Exception e) 
		{
			System.out.println("Socket6001,processing,Exception- "+e.getMessage());
			e.printStackTrace();

		}	

		return result;

	}