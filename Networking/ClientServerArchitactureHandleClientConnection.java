import java.io.*;
import java.util.*;
import java.net.*;

class ClientServerArchitactureHandleClientConnection extends Thread{
    private String hostName;
    private Socket socket;
	
	
    ClientServerArchitactureHandleClientConnection(String hostName,Socket socket){
		this.hostName=hostName;
		this.socket=socket;
    }

    public void run(){
       try{
			DataInputStream in=new DataInputStream (socket.getInputStream());
			
		   do{
				String cmd=in.readLine();
				System.out.println("CMD : "+cmd);
				java.util.StringTokenizer tokens=new java.util.StringTokenizer(cmd,":");
				String user=tokens.nextToken();
				String msg=tokens.nextToken();// not used..

				sendMessage(user,cmd);
		   }while(true);
       }catch(Exception e){
            ClientServerArchitactureServer.hashtable.remove(hostName);   
            System.out.println(hostName+" Client Disconnected......");
       }
    }//end run

	private void sendMessage(String user,String cmd)throws Exception{
		
			Socket destSocket=(Socket)ClientServerArchitactureServer.hashtable.get(user);
			PrintStream out=new PrintStream (destSocket.getOutputStream());
			out.println(cmd);
					
	}//end method
	
}//end class