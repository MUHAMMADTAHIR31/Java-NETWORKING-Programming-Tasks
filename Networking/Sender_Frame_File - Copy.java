import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Sender_Frame_File extends JFrame implements ActionListener {
	
	// Frame Setting 
	JLabel 	    port_label    =  new JLabel("Port");
	JTextField 	port_name    =  new JTextField();
	JLabel 	    ip_label    =  new JLabel("IP Address");
	JTextField 	ip_name    =  new JTextField();
	JMenuBar    menuBar      =  new JMenuBar();
	JMenu       file_item    =  new JMenu("File");;    
	JMenuItem   open         =  new JMenuItem("Open File");
	JTextArea   textarea     =  new JTextArea();
	JButton     startButton  =  new JButton("Connect");
	Cursor      cur          =  new Cursor(Cursor.HAND_CURSOR);
	JFileChooser jFileChooser = new JFileChooser();
	
	Container   con;
	private Socket clientSocket;
	
	Sender_Frame_File()throws Exception{
		
		//LayOut
		con=this.getContentPane();
		con.setLayout(null);	
		
		// Set Bounds 
		port_label.setBounds(10  , 20,  60, 30);
		port_name.setBounds(40  , 20,  60, 30);
		ip_label.setBounds(110  , 20,  80, 30);
		ip_name.setBounds(180  , 20,  120, 30);
		textarea.setBounds(15  , 60,  280, 200);
		startButton.setBounds(220, 280, 90, 30);
		menuBar.setBounds(0,0,800,20);  
		
		// Add In Containers
		con.add(port_label);
		con.add(port_name);
		con.add(ip_label);
		con.add(ip_name);
		con.add(textarea);
		con.add(menuBar);
		con.add(startButton);
		menuBar.add(file_item);
		file_item.add(open);
		
		// ActionListener
		startButton.setCursor(cur);
		file_item.setCursor(cur);
		open.setCursor(cur);
		startButton.addActionListener(this);
		open.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		
		try{
			
			if(e.getSource() ==  startButton){
				
				int port = Integer.parseInt(port_name.getText());
				String host_ip = ip_name.getText();
				clientSocket = new Socket(host_ip,port);
				
				textarea.setText(textarea.getText().trim()+"\n"+"Connected with Server....");
			}
			
			
			if(e.getSource() ==  open ){
				
				jFileChooser.setCurrentDirectory(new File("D:"));
				int result = jFileChooser.showOpenDialog(new JFrame());
				
				if (result == JFileChooser.APPROVE_OPTION) {
				
					File selectedFile = jFileChooser.getSelectedFile();
					textarea.setText(textarea.getText().trim()+"\n"+"Selected file: " + selectedFile.getName());
					textarea.setText(textarea.getText().trim()+"\n"+"Selected file: " + selectedFile.getAbsolutePath());
				
					saveFile(selectedFile.getName());
				}
			}
		}catch(Exception ex){
		  ex.printStackTrace();
		}
	}
	
	private void saveFile(String filename)throws Exception{
		
		FileInputStream file=new FileInputStream(filename);
		
		int fileSize=file.available();
		byte data[]=new byte[fileSize];

		int numOfReadBytes=file.read(data,0,data.length);
		file.close();

		textarea.setText(textarea.getText().trim()+"\n"+"File Size is: "+fileSize);
		textarea.setText(textarea.getText().trim()+"\n"+"Number of Reading Bytes: "+numOfReadBytes);

		DataInputStream in=new DataInputStream(clientSocket.getInputStream());
		PrintStream out=new PrintStream(clientSocket.getOutputStream());

		//Protocol to send file Data
		out.println(filename);
		out.println(fileSize);
		out.write(data,0,fileSize);
		
		textarea.setText(textarea.getText().trim()+"\n"+"File Transfer Successfully....");
		
		in.close();
		out.close();
		clientSocket.close();
	}
	
	public static void main(String arg[])throws Exception{
		
		Sender_Frame_File frame = new Sender_Frame_File();
		frame.setBounds(100,50,400,400);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle(" Sender ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}