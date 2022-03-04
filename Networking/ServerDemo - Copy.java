import java.net.*;
import java.io.*;
class ServerDemo{
	public static void main(String arg[]){
		try{
			ServerSocket server = new ServerSocket(9090);
			System.out.println("Server started....");
			Socket socket = server.accept();
			
			InetAddress addr=socket.getInetAddress();
			String hostName=addr.getHostName();
			System.out.println(hostName+" Client Connected......");
			do{
				DataInputStream in = new DataInputStream(socket.getInputStream());
				PrintStream out = new PrintStream(socket.getOutputStream());
				System.out.println("Client Says : "+in.readLine());
				out.println(new java.util.Scanner(System.in).nextLine());
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}