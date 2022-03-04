import java.io.*;
import java.util.*;
import java.net.*;

class HandleClientConnection extends Thread{
    private String hostName;
    private Socket socket;
	
	static Hashtable hashtable=new Hashtable();

    HandleClientConnection(String hostName,Socket socket){
		this.hostName=hostName;
		this.socket=socket;
    }

    public void run(){
       try{
			DataInputStream in=new DataInputStream(socket.getInputStream());

             do{
				in.readLine();
              }while(true);
 
       }catch(Exception e){
            hashtable.remove(hostName);
            System.out.println(hostName+" Client Disconnected......");
			try{
				String clientList=RegistryService.printActiveClients();
				RegistryService.sendClientListToAllConnectedActiveClients(clientList);   
			}catch(Exception ee){
				ee.printStackTrace();
			}
       }
    }//end run
}//end class