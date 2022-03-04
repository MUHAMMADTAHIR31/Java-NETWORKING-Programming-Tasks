import java.io.*;
import java.net.*;
import java.util.*;
class ClientServerArchitactureServer{
	static Hashtable hashtable=new Hashtable();

	public static void main(String arg[]){
		try{
			ServerSocket server = new ServerSocket(9090);
			System.out.println("Server started...");
			do{
				Socket socket=server.accept();
				InetAddress addr=socket.getInetAddress();
				String hostName=addr.getHostName();
				System.out.println(hostName+" Client Connected......");
		
				hashtable.put(hostName,socket);
			
				ClientServerArchitactureHandleClientConnection t = new ClientServerArchitactureHandleClientConnection(hostName,socket);
				t.start();
			}while(true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of main Method
}//End of main Class