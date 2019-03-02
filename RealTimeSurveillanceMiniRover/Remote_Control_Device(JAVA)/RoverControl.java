import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RoverControl extends JPanel
{
	
        static String kp = null; // The static string which will hold the command to be sent
        MyClickListener clickListener; // The listener object for listening to button  clicks
        
        
        // --------------- The Five Buttons for Up, Down, Left, Right, and Straight Directions -----------
        JButton up = new JButton("UP");
        JButton down = new JButton("DOWN");
        JButton left = new JButton("LEFT");
        JButton right = new JButton("RIGHT");
        JButton jb3 = new JButton("STRAIGHT");
        // ------------------------------------------------------------------------------------
        
        // -------------- These are just dummies to fill up the empty spaces in the layout -----
        JButton jb1 = new JButton();
        JButton jb2 = new JButton();        
        JButton jb4 = new JButton();
        JButton jb5 = new JButton();
        //------------------------------------------------------------------------------------
    

	public RoverControl()
    {
            // Declaring and initializing the click listener object
            clickListener = new MyClickListener();
            
            
            // --------- Registering Buttons to the listener ------------------
            up.addMouseListener(clickListener);
            down.addMouseListener(clickListener);
            left.addMouseListener(clickListener);
            right.addMouseListener(clickListener);
            jb3.addMouseListener(clickListener);
            jb5.addMouseListener(clickListener);
           
            
            // --------------- Adding The Buttons to the JPanel GUI ----------
            setLayout(new GridLayout(3,3));
            add(jb1);
            add(up);            
            add(jb2);
            
            add(left);
            add(jb3);
            add(right);
            
            add(jb4);
            add(down);
            add(jb5);
            
            setFocusable(true);
            
                
	}


	
	public static void main(String[] args)
    {
            
		JFrame frame = new JFrame("Rover Control Screen");
                
		RoverControl keyboardExample = new RoverControl();
                
		frame.add(keyboardExample);
		frame.setSize(400,400);
		frame.setVisible(true);
                
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                 
                try   
                {
                    Socket socket = new Socket("192.168.0.101",6000); // Connecting to the central server
            
                    System.out.println("Sending: ");
                    
                    
                    while(true)
                    {   
                            
                            DataOutputStream outs = new DataOutputStream(socket.getOutputStream());
                            outs.writeByte(2);// Identifying self with user name/id
                            outs.flush();
                                                                  
                            while(!socket.isClosed())
                            {                                      
                                  if((kp!=null)) // if there is a command to be sent, then send
                                  {                                     
                                      outs.writeUTF(kp);
                                      outs.flush();
                                      System.out.println("Key  =  "+kp+"  Sent To Pi");
                                      kp=null;// prepping kp for obtaining the next command                                                                       
                                  } 
                               
                            }                            
                            
                    }       
            
       
                }
       
                catch(Exception e)
                {
                    System.out.println("Error in sending command data. Exception Details: ");
                    e.printStackTrace();
                }     
                
                
	}

        
        
        /*------------------------The Mouse Click Listener Class -------------------------------*/

        public class MyClickListener implements MouseListener 
        {        

            @Override
            public void mouseClicked(MouseEvent e){}

            @Override
            public void mousePressed(MouseEvent e)
            {                
                kp = ((JButton)e.getSource()).getText();      
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {                
                kp = ((JButton)e.getSource()).getText()+" Released";              
            }

            @Override
            public void mouseEntered(MouseEvent e){}

            @Override
            public void mouseExited(MouseEvent e){}
                
		
	}
}