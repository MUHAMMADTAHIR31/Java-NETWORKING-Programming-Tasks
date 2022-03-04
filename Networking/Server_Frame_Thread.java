import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Server_Frame_Thread extends JFrame implements ActionListener ,Runnable {
	
	// Frame Setting 
	JTextArea  textarea     =  new JTextArea();;
	JTextField textfield    =  new JTextField();;
	JButton    enterButton  =  new JButton("Enter");;
	Cursor     cur          =  new Cursor(Cursor.HAND_CURSOR);
	Container con;
	
	Thread t;
	BufferedReader in;
	PrintStream  out;
  	
	Server_Frame_Thread(BufferedReader in,PrintStream out )throws Exception{
		
		this.in  = in;
		this.out = out;
		
		t=new Thread(this);
		t.start();
		
		//LayOut
		con=this.getContentPane();
		con.setLayout(null);
		
		// Set Bounds 
		textarea.setBounds(15  , 20,  270, 250);
		textfield.setBounds(15 , 280, 200,  30);
		enterButton.setBounds(220, 280, 70, 30);
		
		// Add In Containers
		con.add(textarea);
		con.add(textfield);
		con.add(enterButton);
		
		// ActionListener
		enterButton.setCursor(cur);
		enterButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource() ==  enterButton){
			    String st1=textfield.getText();
				textarea.setText(textarea.getText().trim()+"\n"+"You Says :"+st1);
				out.println(st1);
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void run(){
		try{
			String st1="";
			while(true){
				st1=in.readLine();
				textarea.setText(textarea.getText().trim()+"\n"+"Clients Says :"+st1);
			}
		}catch(IOException e){
			System.out.println(e);
		}	
	}
	
	public static void main(String arg[])throws Exception{
		
		ServerSocket  serversocket =new ServerSocket(2220);
		System.out.println("Server started...");
		Socket  client = serversocket.accept();
		
		BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintStream out  = new PrintStream( client.getOutputStream());
		
		Server_Frame_Thread frame = new Server_Frame_Thread(in,out);
		frame.setBounds(100,50,400,400);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle(" Server ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}