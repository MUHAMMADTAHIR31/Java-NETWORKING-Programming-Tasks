import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


class ClientServerArchitactureServerJFrame extends JFrame{
			
	JLabel label = new JLabel("ONLINE CLIENTS");
	JList userList = new JList();
	
	JLabel ipJLabel = new JLabel("IP-Address :");
	static JTextField ipJTextField = new JTextField("192.168.0.108");
	JLabel portJLabel = new JLabel("Port :");
	JTextField portJTextField = new JTextField("9090");
	JLabel userJLabel = new JLabel("User Name :");
	JTextField userJTextField = new JTextField("PC-1");
	
	static JTextArea jTextArea = new JTextArea();
	JTextField msgJTextField = new JTextField();
	JButton jButton = new JButton("SEND");
	
	static Hashtable hashtable=new Hashtable();
	static String ipAddress;
	
	public ClientServerArchitactureServerJFrame()throws Exception{
				
		this.setTitle("SERVER FRAME");
		
		label.setBounds(500,10,100,30);
		userList.setBounds(470,50,180,540);
		
		ipJLabel.setBounds(10,10,100,30);
		ipJTextField.setBounds(110,10,150,30);
		portJLabel.setBounds(270,10,50,30);
		portJTextField.setBounds(310,10,150,30);
		
		userJLabel.setBounds(10,50,150,30);
		userJTextField.setBounds(110,50,350,30);
		
		jTextArea.setBounds(10,100,450,400);
		msgJTextField.setBounds(10,520,450,30);
		jButton.setBounds(390,560,70,30);
		
		JScrollPane scroll = new JScrollPane (jTextArea);
		add(scroll);
		
		add(label);
		add(userList);
		add(ipJLabel);
		add(ipJTextField);
		add(portJLabel);
		add(portJTextField);
		add(userJLabel);
		add(userJTextField);
		add(jTextArea);
		add(msgJTextField);
		add(jButton);
		
		setBounds(0,0,670,650);
		setLayout(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				sendMessage();
            }//End of actionPerformed
        });//End of addActionListener
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					ServerSocket server=new ServerSocket(9090);	
					System.out.println("Server Started...");
					String cmd = "";
					do{
						
						Socket socket=server.accept();
						
						InetAddress addr=socket.getInetAddress();
						String hostName=addr.getHostName();
						System.out.println(hostName+" Client Connected......");
						
						hashtable.put(hostName,socket);
						
						// new HandleClientConnection(hostName,socket).start();
						
						Enumeration keys=hashtable.keys();
						Enumeration e=hashtable.elements();


						DataInputStream in=new DataInputStream (socket.getInputStream());
						cmd=in.readLine();
						System.out.println("Cmd : "+cmd);
						java.util.StringTokenizer tokens=new java.util.StringTokenizer(cmd,":");
						String user=tokens.nextToken();
						String msg=tokens.nextToken();

								
						while(keys.hasMoreElements()){
							String hostname=(String)keys.nextElement();
							if(hostname.equals(user)){
								System.out.println("hostName : "+ hostname);
								Socket s =(Socket)e.nextElement();
								PrintStream out=new PrintStream(s.getOutputStream());
								out.println(user+": "+msg);  
							}
							
							jTextArea.append("\n"+user+": "+msg);
						}

						
						// in.close();
						// socket.close();
					}while(true);
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			}//End of run
		}); //End Thread
		t.start();
		
		
	}//End of costructor
	
	private void sendMessage(){
		try{
			String hostName = ipJTextField.getText();
			String msg = msgJTextField.getText();
			String user = userJTextField.getText();
			
			Enumeration keys=hashtable.keys();
			Enumeration e=hashtable.elements();
			
			while(keys.hasMoreElements()){
				String hostname=(String)keys.nextElement();
				if(hostname.equals(hostName)){
					Socket s =(Socket)e.nextElement();
					PrintStream out=new PrintStream(s.getOutputStream());
					out.println(user+": "+msg);  
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of sendMessage

	
	public static void main(String arg[])throws Exception{
		
		new ClientServerArchitactureServerJFrame();
		
	}//End of main Method
}//End of main Class

