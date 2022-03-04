import java.io.*;
import java.net.*;
class RegistryServiceDemo{
	public static void main(String arg[]){
		try{
			ServerSocket server = new ServerSocket(9090);
			System.out.println("Server Started..");
			do{
				Socket socket = server.accept();
				System.out.println("Client Connected..");
				
				InetAddress address = socket.getInetAddress();
				
				String hostName  = address.getHostName();
				new HandleClientConnectionDemo(hostName,socket).start();
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of Main Method
}//End of Main Class