import java.io.*;
import java.net.*;
class MultiMessageSenderClient{
	public static void main(String arg[]){
		try{
			// Socket socket = new Socket("192.168.0.103",8080);
			Socket socket = new Socket("localhost",9090);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			PrintStream out = new PrintStream(socket.getOutputStream());
			new ClientMessageReciverThread(in).start();
			do{
				System.out.print("Enter message : ");
				out.println(new DataInputStream(System.in).readLine());
			}while(true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of main Method
}//End og main Class