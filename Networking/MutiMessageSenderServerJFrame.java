import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MutiMessageSenderServerJFrame extends JFrame{
		
	JTextArea jTextArea = new JTextArea();
	JTextField jTextField = new JTextField();
	JButton jButton = new JButton("SEND");

    static DataInputStream in;
    static PrintStream out;
	
	public MutiMessageSenderServerJFrame(DataInputStream in , PrintStream out)throws Exception{
		this.in = in;
		this.out = out;
			
		this.setTitle("SERVER FRAME");
		
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
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try{
					if(jTextField.getText()!=("") && jTextField.getText().length()!=0){
					
						out.println(jTextField.getText());//send to server
						if(jTextArea.getText().length() <= 0 )
							jTextArea.setText(jTextArea.getText()+"\t\t\t\tServer says:\t" + jTextField.getText());
						else
							jTextArea.setText(jTextArea.getText()+"\n"+"\t\t\t\tServer says:\t" + jTextField.getText());

						jTextField.setText("");
					}
					else{
						javax.swing.JOptionPane.showMessageDialog(null,"fill msg Feild");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
					
            }//End of actionPerformed
        });//End of addActionListener
		
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					String msg = "";
					while(true){
						msg = in.readLine();
						if(jTextArea.getText().length() > 0 ){
							jTextArea.setText(jTextArea.getText()+"\n"+"Server Says:\t" + msg);
						}
						else{
							 jTextArea.setText(jTextArea.getText()+"Server Says:\t" + msg);
						}
					}
			
				}catch(Exception e){
					e.printStackTrace();
				}
			}//End of run
		}); //End Thread
		t.start();
		
		
	}//End of costructor
	
	
	public static void main(String arg[]){
		try{
			ServerSocket server = new ServerSocket(9090);
			System.out.println("Server started...");
		
			Socket socket = server.accept();
			System.out.println("Client Connected...");
			
			in = new DataInputStream(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());
			
			// new ServerMessageReciverThread(in).start();
			
			new MutiMessageSenderServerJFrame(in,out);
		
		}catch(Exception e){
            e.printStackTrace();
        }
	}//End of main Method
}//End of main Class
