import java.io.*;
import java.net.*;
class MultiFileReciver{
	public static void main(String arg[])throws Exception{
		
		ServerSocket server = new ServerSocket(9090);
		System.out.println("Server started..");
		do{
			
			Socket socket = server.accept();
			System.out.println("Client Connected..");
			
			MultiFileReciverThread t = new MultiFileReciverThread(socket);
			t.start();
		}while(true);
		
	}//End of main Class
}//End of main Method