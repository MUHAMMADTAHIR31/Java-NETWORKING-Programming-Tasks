import java.io.*;
import java.net.*;
class HandleClientConnectionDemo extends Thread{
	private String hostName;
	private Socket socket;
	HandleClientConnectionDemo(String hostName , Socket socket ){
		this.hostName = hostName;
		this.socket = socket;
	}//End of constructor
	
	public void run(){
		try{
			DataInputStream in = new DataInputStream(socket.getInputStream());
			System.out.println("Client Says: "+in.readLine());
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of run
}//End of Main Class