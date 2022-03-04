import java.io.*;
import java.net.*;
class P2PMessageSender{
	public P2PMessageSender(){
		new P2PMessageReceiverThread().start();
	}
	private void sendMessage(){
		try{
			do{
				Socket socket=new Socket("localhost",9090);
				PrintStream out=new PrintStream(socket.getOutputStream());
				System.out.print("Enter Message : ");
				out.println(new DataInputStream(System.in).readLine());
				out.close();
				socket.close();
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String arg[]){
		P2PMessageSender ob = new P2PMessageSender();
		ob.sendMessage();
	}
}