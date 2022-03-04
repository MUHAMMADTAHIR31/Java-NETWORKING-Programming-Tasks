import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ClientServerArchitactureDataShiftingAgentJFrame extends JFrame{
	
	JLabel infoLabel = new JLabel("DATA SHIFTING AGENT");
	JLabel pathLabel = new JLabel("ENTER PATH");
	JLabel ipLabel = new JLabel("IP-ADDRESS ");
	
	JLabel totalSizeLabel = new JLabel("TOTAL SIZE : ");
	JLabel totalSizeValueLabel = new JLabel();
	JLabel packetSizeLabel = new JLabel("PACKET SIZE : ");
	JLabel packetSizeValueLabel = new JLabel();
	JLabel totalPacketsLabel = new JLabel("<html> TOTAL <br> PACKETS : </html>");
	JLabel totalPacketsValueLabel = new JLabel();
	JLabel lastPacketSizeLabel = new JLabel("LAST PACKETS : ");
	JLabel lastPacketSizeValueLabel = new JLabel();
	
	JTextField pathTextField  = new JTextField();
	static JTextField ipTextField  = new JTextField("192.168.43.33");
	JButton browserButton = new JButton("BROWSER");
	JButton sendButton = new JButton("TRANSFER");
	
	JProgressBar progressBar;
	
	String filename;
	String path;
	String directory;
	static Socket socket;
	
	public ClientServerArchitactureDataShiftingAgentJFrame( Socket socket ){
		this.socket = socket;
		
		progressBar = new JProgressBar(0,2000);
		
		infoLabel.setFont(new Font("Arial",Font.BOLD,25));
		
		infoLabel.setBounds(250,30,300,40);
		pathLabel.setBounds(30,100,100,40);
		pathTextField.setBounds(150,100,500,40);
		ipLabel.setBounds(30,150,100,40);
		ipTextField.setBounds(150,150,250,40);
		
		pathTextField.setFont(new Font("Arial",Font.BOLD,12));
		ipTextField.setFont(new Font("Arial",Font.BOLD,18));
		
		browserButton.setBounds(450,150,200,40);
		browserButton.setFont(new Font("Arial",Font.BOLD,18));
		
		totalSizeLabel.setBounds(30,200,100,40);
		totalSizeValueLabel.setBounds(150,200,250,40);
		
		packetSizeLabel.setBounds(30,250,100,40);
		packetSizeValueLabel.setBounds(150,250,250,40);
		
		totalPacketsLabel.setBounds(30,300,100,40);
		totalPacketsValueLabel.setBounds(150,300,250,40);
		
		lastPacketSizeLabel.setBounds(30,350,100,40);
		lastPacketSizeValueLabel.setBounds(150,350,250,40);
		
		progressBar.setBounds(30,400,630,40);         
		progressBar.setValue(0);    
		progressBar.setStringPainted(true); 
		
		sendButton.setBounds(30,450,630,40);
		sendButton.setFont(new Font("Arial",Font.BOLD,18));
		
		add(infoLabel);
		add(pathLabel);
		add(pathTextField);
		add(ipLabel);
		add(ipTextField);
		add(browserButton);
		add(totalSizeLabel);
		add(totalSizeValueLabel);
		add(packetSizeLabel);
		add(packetSizeValueLabel);
		add(totalPacketsLabel);
		add(totalPacketsValueLabel);
		add(lastPacketSizeLabel);
		add(lastPacketSizeValueLabel);
		add(sendButton);
		add(progressBar);
		progressBar.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,700,550);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		browserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				FileDialog dialog=new FileDialog(new JFrame(),"Upload",FileDialog.LOAD);
				dialog.setVisible(true);
				filename=dialog.getFile();
				directory=dialog.getDirectory();
				
				if(filename!=null){
					path=directory+filename;
				}
				pathTextField.setText(path);				
					
            }//End of actionPerformed
        });//End of actionListener
		
		sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				sendData();
				
            }//End of actionPerformed
        });//End of actionListener
		
				
	}//End of constructoructor
	
	private void sendData(){
		try{
			
			System.out.println("File Path : "+path);
			DataInputStream file = new DataInputStream( new FileInputStream(path));
			int fileSize = file.available();
			int packetSize = 1000;
			int totalPackets = fileSize/packetSize;
			int lastPacketSize = fileSize%packetSize;

			totalSizeValueLabel.setText(""+fileSize);
			packetSizeValueLabel.setText(""+packetSize);
		

			PrintStream out = new PrintStream(socket.getOutputStream());
			
			String user = "127.0.0.1";
			String header = user +":"+ filename +":"+ packetSize +":"+ totalPackets +":"+ lastPacketSize;
			System.out.println(header);
			
			out.println(header);

			int totalPacket = 0;
			int lastPacket = 0;
			
			byte data[] = new byte[packetSize];
			int p;
			for( p = 0; p < totalPackets; p++){
				file.readFully(data,0,packetSize);
				out.write(data,0,packetSize);
				
				totalPacketsValueLabel.setText(""+p);
				totalPacket = p;
			}
			file.readFully(data,0,lastPacketSize);
			out.write(data,0,lastPacketSize);
			
			lastPacket = (p-totalPacket);
			lastPacketSizeValueLabel.setText(""+lastPacket);
			
			//for progressBar...
			progressBar();
			
			JOptionPane.showMessageDialog(null,"File Transferd Successfully..");
			
			file.close();
			// out.close();
			// socket.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End sendData Method
	
	private void progressBar(){
		
		progressBar.setVisible(true);
		Thread t = new Thread(new Runnable(){
			int i=0;
			public void run(){
				try{
					while(i<=2000){    
						setCursor(new Cursor(Cursor.WAIT_CURSOR));
						sendButton.setEnabled(false);
						progressBar.setValue(i);    
						i=i+20;    
					  
						Thread.sleep(150);
					}//End of while
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					progressBar.setVisible(false);
					sendButton.setEnabled(true);
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});t.start();
	}//end of progressBar
	
	
	public static void main(String arg[])throws Exception{
	
		Socket socket =new Socket("localhost",9090);
			
		new ClientServerArchitactureDataShiftingAgentJFrame(socket);
		new ClientServerArchitactureFileReciverThread(socket).start();
		
	}//End of Main Method
}//End of Main Class