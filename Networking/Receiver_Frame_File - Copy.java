import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Receiver_Frame_File extends JFrame implements ActionListener  {

	// Frame Setting
	JTextArea  textarea     =  new JTextArea();
	JLabel     port_label   =  new  JLabel("Port");
	JTextField port_text    =  new JTextField();
	JButton    connectButton  =  new JButton("Start");
	Cursor     cur          =  new Cursor(Cursor.HAND_CURSOR);
	Container  con;
	
	// Networking Class
    private  ServerSocket server;

	Receiver_Frame_File()throws Exception{
		
		con=this.getContentPane();
		con.setLayout(null);
		
		// Set Bounds 
		port_label.setBounds(10 , 20, 60,  30);
		port_text.setBounds(40 , 20, 60,  30);
		textarea.setBounds(20  , 60,  280, 200);
		connectButton.setBounds(220, 280, 70, 30);
		
		// Add In Containers
		con.add(textarea);
		con.add(port_label);
		con.add(port_text);
		con.add(connectButton);
		
		connectButton.setCursor(cur);	
		connectButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		
		try{	
			int port = Integer.parseInt(port_text.getText()); 
			server = new ServerSocket(port);
			
			if(e.getSource() ==  connectButton){
				Socket socket=server.accept();
				textarea.setText(textarea.getText().trim()+"\n"+"Connected......:");
				saveFile(socket);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	private void saveFile(Socket clientSocket)throws Exception{
	
		DataInputStream in=new DataInputStream(clientSocket.getInputStream());
		PrintStream out=new PrintStream(clientSocket.getOutputStream());
		
		String filename=in.readLine();
		int fileSize=Integer.parseInt(in.readLine());

		byte data[]=new byte[fileSize];

		in.read(data,0,fileSize);

		textarea.setText(textarea.getText().trim()+"\n"+"File Received Successfully....");
		
		File f=new File("d:\\download");
		if(!f.exists())f.mkdirs();
		
		String filePath="d:\\download\\"+filename;

		FileOutputStream file=new FileOutputStream(filePath);
		file.write(data,0,data.length);

		file.close();
		textarea.setText(textarea.getText().trim()+"\n"+"File Saved...\nFile Path: "+filePath);
		textarea.setText(textarea.getText().trim()+"\n"+"File Size is: "+fileSize);

		in.close();
		out.close();
		clientSocket.close();
	}
	
	public static void main(String arg[])throws Exception{
		
		Receiver_Frame_File frame = new Receiver_Frame_File();
		frame.setBounds(100,50,400,400);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle(" Receiver ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}