import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MultiFileSender extends JFrame{
	
	JTextField pathTextField  = new JTextField();
	JButton browserButton = new JButton("BROWSER");
	JButton sendButton = new JButton("SEND");
	
	String filename;
	String path;
	String directory;
	static Socket socket;
	public MultiFileSender(Socket socket){
		this.socket = socket;
		
		pathTextField.setBounds(100,100,400,40);
		browserButton.setBounds(300,150,100,40);
		sendButton.setBounds(400,150,100,40);
		
		add(pathTextField);
		add(browserButton);
		add(sendButton);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,700,300);
		setLayout(null);
		setVisible(true);;
		
		
		
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
				try{
					MultiFileSenderThread t = new MultiFileSenderThread(socket,directory,filename);
					t.start();
				}catch(Exception ex){
					ex.printStackTrace();
				}
					
            }//End of actionPerformed
        });//End of actionListener
		
				
	}//End of constructor

	
	
	public static void main(String arg[])throws Exception{
			
		socket = new Socket("localhost",9090);
		new MultiFileSender(socket);
		
	}//End of main Class
}//End of main Method