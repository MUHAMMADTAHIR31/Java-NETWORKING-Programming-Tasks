import java.io.*;
import java.net.*;
class P2PMessageReceiverThread extends Thread{
	public void run(){
	try{
		ServerSocket server=new ServerSocket(9090);
		System.out.println("Server Started..");
		
		do{
			Socket socket=server.accept();
			InetAddress addr=socket.getInetAddress();
			
			String hostname=addr.getHostName();
			String hostaddr=addr.getHostAddress();
			
			System.out.println("Client Connected.."+"\nIP : "+hostaddr);


			DataInputStream in=new DataInputStream (socket.getInputStream());
			String msg=in.readLine();
			System.out.println("\n"+hostname+": "+msg+"\n");

			in.close();
			socket.close();


		}while(true);

		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of run
}//End of Main Class