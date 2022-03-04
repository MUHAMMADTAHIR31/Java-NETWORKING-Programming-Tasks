import java.io.*;
import java.net.*;
class MutiMessageSenderServer{
	public static void main(String arg[]){
		try{
			ServerSocket server = new ServerSocket(8080);
			// ServerSocket server = new ServerSocket(9090);
			System.out.println("Server started...");
			Socket socket = server.accept();
			System.out.println("Client Connected...");
			DataInputStream in = new DataInputStream(socket.getInputStream());
			PrintStream out = new PrintStream(socket.getOutputStream());
			new ServerMessageReciverThread(in).start();
			do{
				System.out.print("Enter message : ");
				out.println(new DataInputStream(System.in).readLine());
			}while(true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of main Method
}//End og main Class