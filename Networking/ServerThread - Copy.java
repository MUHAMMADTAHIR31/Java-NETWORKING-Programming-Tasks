import java.io.*;
import java.net.*;
class ServerThread extends Thread{
	public void run(){
		try{
			ServerSocket server = new ServerSocket(9090);
			System.out.println("Server Started...");
			do{
				Socket socket = server.accept();
				System.out.println("Client Connected...");
				new P2PMultiFileReciverThread(socket).start();
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of run
}//End of main Class