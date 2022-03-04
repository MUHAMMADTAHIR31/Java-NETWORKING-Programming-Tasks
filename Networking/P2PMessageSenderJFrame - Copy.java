import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
class P2PMessageSenderJFrame extends JFrame{
	
	static Hashtable hashtable = new Hashtable();
	
	JTextArea jTextArea = new JTextArea();
	JTextField jTextField = new JTextField();
	JButton jButton = new JButton("SEND");

	
	public P2PMessageSenderJFrame()throws Exception{
		
		this.setTitle("CLIENT-SERVER FRAME");
		
		jTextArea.setBounds(0,0,500,400);
		jTextField.setBounds(0,420,500,30);
		jButton.setBounds(400,460,70,30);
		
		JScrollPane scroll = new JScrollPane (jTextArea);
		add(scroll);
		
		add(jTextArea);
		add(jTextField);
		add(jButton);
		
		setBounds(500,0,500,550);
		setLayout(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//sendMessage();
            }//End of actionPerformed
        });//End of addActionListener
		
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					
					ServerSocket server=new ServerSocket(9090);	
					System.out.println("Registry Service Started...");
					String cmd = "";
					
					do{
						
						Socket socket=server.accept();
						
						InetAddress addr=socket.getInetAddress();
						String hostName=addr.getHostName();
						System.out.println(hostName+" Client Connected......");
						
						hashtable.put(hostName,socket);
						Enumeration keys =hashtable.keys();
						Enumeration  e   =hashtable.elements(); 

						DataInputStream in=new DataInputStream (socket.getInputStream());
						cmd=in.readLine();
					
						java.util.StringTokenizer tokens=new java.util.StringTokenizer(cmd,":");
						String user=tokens.nextToken();
						String msg=tokens.nextToken();
						
						
						while(keys.hasMoreElements()){
							
							if(keys.nextElement().equals(user)){
								
								hostName=(String)keys.nextElement();
								Socket  s =(Socket)e.nextElement();
								PrintStream out =new PrintStream(s.getOutputStream());
								out.println(user+" "+msg);
							
							}
						}
						
						jTextArea.append("\n"+user+": "+msg);
						
						//in.close();
						//socket.close();
					}while(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}//End of run
		}); //End Thread
		t.start();
		
	}//End of costructor

	public static void main(String arg[])throws Exception{
		new P2PMessageSenderJFrame();
		
	}//End of main Method
}//End of main Class
