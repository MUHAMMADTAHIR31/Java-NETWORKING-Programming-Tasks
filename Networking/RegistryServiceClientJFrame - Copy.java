import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
class RegistryServiceClientJFrame extends JFrame{
		
	JLabel label = new JLabel("ONLINE CLIENTS");
	JList userList = new JList();
	
	JLabel ipJLabel = new JLabel("IP-Address :");
	static JTextField ipJTextField = new JTextField("192.168.43.167");
	JLabel portJLabel = new JLabel("Port :");
	JTextField portJTextField = new JTextField("9090");
	JLabel userJLabel = new JLabel("User Name :");
	JTextField userJTextField = new JTextField("PC-2");
	
	JTextArea jTextArea = new JTextArea();
	JTextField msgJTextField = new JTextField();
	JButton jButton = new JButton("SEND");
	
	static String ipAddress;
	
	public RegistryServiceClientJFrame()throws Exception{
	
		new MessageReceiverThread(jTextArea).start();
		
		String hostName = RegistryConnectionHandlerThread.getHostName();
		Vector v = new Vector();
		v.addElement(hostName);
		for(int i=0; i<v.size(); i++)
			userList.setListData(v);
				
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
			String ipAddress = ipJTextField.getText();
			String msg = msgJTextField.getText();
			String user = userJTextField.getText();
			int portNo = Integer.parseInt(portJTextField.getText());
			Socket socket=new Socket(ipAddress,portNo);
			PrintStream out=new PrintStream(socket.getOutputStream());

			if(user.trim().equals(""))
				user=System.getProperty("user.name");

			out.println(user+":"+msg);	
			jTextArea.append("\n"+user+": "+msg);

			out.close();
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of sendMessage
	

	
	public static void main(String arg[])throws Exception{
		new RegistryConnectionHandlerThread(ipJTextField.getText()).start();
		new RegistryServiceClientJFrame();
	}//End of main Method
}//End of main Class

