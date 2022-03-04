import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Client_Frame_Thread extends JFrame implements ActionListener , Runnable {

	// Frame Setting
	JTextArea  textarea     =  new JTextArea();
	JTextField textfield    =  new JTextField();
	JButton    enterButton  =  new JButton("Enter");
	Cursor cur=new Cursor(Cursor.HAND_CURSOR);
	Container con;
	
	// Networking Class
   	Thread t;
	BufferedReader  in; 
	PrintStream  out;   

	Client_Frame_Thread( BufferedReader in, PrintStream out )throws Exception{
		
		this.in  = in;
		this.out = out;
		
		t = new Thread(this);
		t.start();
		
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
				textarea.setText(textarea.getText().trim()+"\n"+"Server Says :"+st1);
			}	
		}catch(IOException e){
			System.out.println(e);
		}	
	} 
	
	public static void main(String arg[])throws Exception{
		
		Socket       socket  =  new Socket("localhost",9090);
		BufferedReader in    =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream    out   =  new PrintStream(socket.getOutputStream());
		
		Client_Frame_Thread frame = new Client_Frame_Thread(in,out);
		frame.setBounds(100,50,400,400);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle(" Client ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}