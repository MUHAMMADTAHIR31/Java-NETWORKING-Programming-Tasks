import java.io.*;
import java.net.*;
import java.util.*;
class MessageReceiverThread extends Thread{
	private javax.swing.JTextArea msgArea;
	MessageReceiverThread(javax.swing.JTextArea msgArea){
		this.msgArea = msgArea;
	}
	
	public void run(){
		 try{
			ServerSocket server=new ServerSocket(9090);	
			System.out.println("Registry Service Started...");
			String cmd = "";
			do{
				
				Socket socket=server.accept();
				
				InetAddress addr=socket.getInetAddress();
				String hostName=addr.getHostName();

				System.out.println(hostName+" Client Connected......");

				DataInputStream in=new DataInputStream (socket.getInputStream());
				cmd=in.readLine();
			
				java.util.StringTokenizer tokens=new java.util.StringTokenizer(cmd,":");
				String user=tokens.nextToken();
				String msg=tokens.nextToken();

				msgArea.append("\n"+user+": "+msg);
				
				in.close();
				socket.close();
			}while(true);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}//end run
}//end Thread class