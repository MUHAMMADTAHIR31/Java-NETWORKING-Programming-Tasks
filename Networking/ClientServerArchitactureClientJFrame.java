import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
class ClientServerArchitactureClientJFrame extends JFrame{
		
	JLabel label = new JLabel("ONLINE CLIENTS");
	JList userList = new JList();
	
	JLabel ipJLabel = new JLabel("IP-Address :");
	static JTextField ipJTextField = new JTextField("192.168.43.167");
	JLabel portJLabel = new JLabel("Port :");
	JTextField portJTextField = new JTextField("9090");
	JLabel userJLabel = new JLabel("User Name :");
	JTextField userJTextField = new JTextField("PC-2");
	
	static JTextArea jTextArea = new JTextArea();
	// JScrollPane scroll = new JScrollPane (jTextArea);
	
	JTextField msgJTextField = new JTextField();
	JButton jButton = new JButton("SEND");
	Vector v = new Vector();
	 
	static PrintStream out;
	
	public ClientServerArchitactureClientJFrame(PrintStream out)throws Exception{
	
		this.out = out;
	
				
		this.setTitle("CLIENT FRAME");
		
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
		// add(scroll);
		
		setBounds(690,0,670,650);
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
		
		
		
		
	
	}//End of costructor
	
	private void sendMessage(){
		try{
			
			String msg = msgJTextField.getText();
			String user = userJTextField.getText();

			if(user.trim().equals(""))
				user=System.getProperty("user.name");

			out.println(user+":"+msg);	
			jTextArea.append("\n"+user+": "+msg);

		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of sendMessage
	

	
	public static void main(String arg[])throws Exception{
		Socket socket = new Socket("192.168.43.26",9090);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		new ClientServerArchitactureClientJFrame(out);
		
		ClientServerArchitactureMessageReceiverThread t = new ClientServerArchitactureMessageReceiverThread(in , jTextArea);
		t.start();

		
		
	}//End of main Method
}//End of main Class

