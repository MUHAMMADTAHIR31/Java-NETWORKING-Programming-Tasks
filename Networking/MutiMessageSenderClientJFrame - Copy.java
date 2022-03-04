import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MutiMessageSenderClientJFrame  extends JFrame{
	

	JTextArea jTextArea = new JTextArea();
	JTextField jTextField = new JTextField();
	JButton jButton = new JButton("SEND");
	
	static ServerSocket server;
    static Socket socket;
    static DataInputStream in;
    static PrintStream out;
	
	public MutiMessageSenderClientJFrame (DataInputStream in , PrintStream out)throws Exception{
		this.in = in;
		this.out = out;
		
		this.setTitle("CLIENT FRAME");
		
		jTextArea.setBounds(0,0,500,400);
		jTextField.setBounds(0,420,500,30);
		jButton.setBounds(400,460,70,30);
		
		JScrollPane scroll = new JScrollPane (jTextArea);
			
		add(scroll);
		add(jTextArea);
		add(jTextField);
		add(jButton);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,500,550);
		setLayout(null);
		setVisible(true);;
		
		
		
		jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
				try{
					if(jTextField.getText()!=("") && jTextField.getText().length()!=0){
						out.println(jTextField.getText());//send to server
						if(jTextArea.getText().length() <= 0 )
							jTextArea.setText(jTextArea.getText()+"\t\t\t\tClient says:\t" + jTextField.getText());
						else
							jTextArea.setText(jTextArea.getText()+"\n"+"\t\t\t\tClient says:\t" + jTextField.getText());

						jTextField.setText("");
					}
					else{
						javax.swing.JOptionPane.showMessageDialog(null,"fill msg Feild");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
					
            }//End of actionPerformed
        });//End of actionListener
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					String msg = "";
					while(true){
						msg = in.readLine();
						if(jTextArea.getText().length() > 0 ){
							jTextArea.setText(jTextArea.getText()+"\n"+"Client Says:\t" + msg);
						}
						else{
							 jTextArea.setText(jTextArea.getText()+"Client Says:\t" + msg);
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
			socket = new Socket("192.168.43.33",9090);
            in = new DataInputStream(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
			new MutiMessageSenderClientJFrame(in,out);
           
			
        }catch(Exception e){
            e.printStackTrace();
        }
	
	}//End of main Method
}//End of main Class
