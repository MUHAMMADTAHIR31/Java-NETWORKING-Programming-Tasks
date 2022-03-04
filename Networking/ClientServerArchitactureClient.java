import java.io.*;
import java.net.*;
import java.util.*;
class ClientServerArchitactureClient{
	public static void main(String arg[]){
		try{
			
			Socket socket = new Socket("localhost",9090);
			InetAddress addr=socket.getInetAddress();
			String hostName=addr.getHostAddress();
			do{
				DataInputStream in = new DataInputStream(socket.getInputStream());
				PrintStream out = new PrintStream(socket.getOutputStream());
		
			
				System.out.print("\nEnter message : ");
				out.println(hostName+":"+new DataInputStream(System.in).readLine());
				System.out.println("Server Says  : "+in.readLine());
			}while(true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of main Method
}//End of main Class