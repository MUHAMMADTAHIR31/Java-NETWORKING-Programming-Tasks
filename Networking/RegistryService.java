import java.io.*;
import java.net.*;
import java.util.*;
class RegistryService{
    static Hashtable hashtable=new Hashtable();
    
    public static void main(String arg[])throws Exception{
         ServerSocket server = new ServerSocket(7070); 
         System.out.println("Registry server started.....");
         
         do{
				Socket socket=server.accept();
				InetAddress addr=socket.getInetAddress();
				String hostName=addr.getHostName();

				System.out.println(hostName+" Client Connected......");

				hashtable.put(hostName,socket);
				String clientList=printActiveClients();
				sendClientListToAllConnectedActiveClients(clientList);
				
				new HandleClientConnection(hostName,socket).start();
				
           }while(true);
    }//end main
    
    public static String printActiveClients()throws Exception{
       Enumeration keys=hashtable.keys();

       String clientList="";
       String symbol="";

       while(keys.hasMoreElements()){
			String hostName=(String)keys.nextElement();
			clientList+= symbol+hostName;       
			symbol=":";
        }
       
       return clientList;	
    }//end method

   public static void sendClientListToAllConnectedActiveClients(String clientList)throws Exception{
       Enumeration e=hashtable.elements();

       while(e.hasMoreElements()){
			Socket socket=(Socket)e.nextElement();
			PrintStream out=new PrintStream(socket.getOutputStream());
			out.println(clientList);
        }
   }//end method
}//end main class

