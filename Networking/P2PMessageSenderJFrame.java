import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class P2PMessageSenderJFrame extends JFrame{
		
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
				sendMessage();
            }//End of actionPerformed
        });//End of addActionListener
		
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					ServerSocket server=new ServerSocket(9090);
					System.out.println("Server Started..");
					
					do{
						Socket socket=server.accept();
						InetAddress addr=socket.getInetAddress();
						
						String hostname=addr.getHostName();
						String hostaddr=addr.getHostAddress();
						
						System.out.println("Client Connected.."+"\nIP : "+hostaddr);


						DataInputStream in=new DataInputStream (socket.getInputStream());
						String msg=in.readLine();
						jTextArea.setText(jTextArea.getText()+"\n"+hostname+": "+msg+"\n");

						in.close();
						socket.close();

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
			// do{
				Socket socket=new Socket("192.168.43.167",9090);
				PrintStream out=new PrintStream(socket.getOutputStream());
				if(jTextField.getText()!=("") && jTextField.getText().length()!=0){
					
					out.println(jTextField.getText());//send to server
					if(jTextArea.getText().length() <= 0 )
						jTextArea.setText(jTextArea.getText()+"\t\t\t\tYou:\t" + jTextField.getText());
					else
						jTextArea.setText(jTextArea.getText()+"\n"+"\t\t\t\tYou:\t" + jTextField.getText());

					jTextField.setText("");
					out.close();
					socket.close();
				}
				else{
					System.out.println("fill msg Feild...");
					javax.swing.JOptionPane.showMessageDialog(null,"fill msg Feild");
				}
				
				
			// }while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of sendMessage
	
	
	public static void main(String arg[])throws Exception{
		new P2PMessageSenderJFrame();
		
	}//End of main Method
}//End of main Class
