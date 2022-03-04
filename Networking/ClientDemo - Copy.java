import java.net.*;
import java.io.*;
class ClientDemo{
	public static void main(String arg[]){
		try{
			
			Socket socket = new Socket("localhost",9090);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			do{
				PrintStream out = new PrintStream(socket.getOutputStream());
				out.println(new java.util.Scanner(System.in).nextLine());
				System.out.println("Server Says : "+in.readLine());
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}