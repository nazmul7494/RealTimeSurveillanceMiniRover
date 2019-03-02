import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


// For displaying the mini rover video stream data on the remote control device screen
public class VideoFrame 
{

   
    public static void main(String[] args)
    {
        
       try
       {
           
           
        //----------- Size of the image ------------------------------------
        int screen = (320*240);
        int h = 240;
        int w = 320;
        //----------------------------------------------------------------------
        
        
        // Connecting to the Raspberry pi on the mini-rover via the Central Server program
        Socket pi = new Socket("192.168.0.101",6001);// put the (central_server_ip,port) here 
        
        DataInputStream dis = new DataInputStream(pi.getInputStream());
        
        DataOutputStream dos = new DataOutputStream(pi.getOutputStream());

        //Identifying self with user name/id        
        dos.writeByte(2);        
        System.out.println("Connected with mini-rover. Obtaining Video Feed: ");      
       
        
        // --------------------- The Raster object for constructing the image frame is created here -------------
        BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY); // will be displaying a gray-scale image
        WritableRaster raster = (WritableRaster) image.getData();
        
        
        // ------------------- Initializing GUI Components ---------------------
        JFrame jfm = new JFrame();
        JLabel jlb; 
        jlb = null;        
        jfm.setSize(w,h);   
        
        
        // --------------- Setting-up and Initializing The Arrays --------------
        
        byte[] pixel = new byte[screen];// will hold the recieved bytes
        int[] pixInt = new int[screen]; // will store the bytes after they have been wrapped around        
        int p; // a temporary int variable for holding an un-checked byte value
        
        //----------------------------------------------------------------------
       
        while(true)
        {
            
            System.out.println("Preparing to read Stream");
            
            //Reading the whole stream recieved from pi socket into the byte array named 'pixel'.
            dis.readFully(pixel,0,(screen)); 

            // Checking for negative byte values inside 'pixel' array and wrapping-up them if necessary 
            // and storing in a int type array named 'pixInt'

            for(int i=0;i<(screen);i++)
            {
                p = (int)pixel[i]; // initial byte value from array 'pixel'
                
                if(p<0)
                {
                    pixInt[i] = 256+p; // If negative, wrapped-up and stored in 'pixInt'
                }
                
                else
                {
                    pixInt[i] = p; // If positive, the byte value is stored into 'pixInt' as it was.
                }
            }              
            
         
            // ---------------- Setting-up Raster Data Here --------------------
            raster.setPixels(0,0,w,h,pixInt);    
            image.setData(raster); 
                      
            
            // --------------- Using the Raster object to Create and Display the image frame received from mini-rover --------
             
            if(jlb!=null)
            {
                // Removing already displayed image frame
                jfm.remove(jlb); 
            }
            
            ImageIcon icon = new ImageIcon(image);//Creating icon from raster object 'image'           
            jlb = new JLabel(icon);// Creating a new JLabel with icon passed as parameter
            jfm.add(jlb);// Adding the JLabel to the JFrame object          
            jfm.setVisible(true); // Setting the visibility of JFrame as true            
        
        }//while ends
        
      }//try ends
       
    catch(Exception e)
    {       
        e.printStackTrace();
    }      
        
    } // main() ends
    
}
