import java.io.*;
import java.net.*;
import java.util.*;
class  RegistryConnectionHandlerThread extends Thread{
	   private String registryHostName;
	   
	   private static String sendHostName;
	   static Vector v = new Vector();
       RegistryConnectionHandlerThread(String registryHostName){
			this.registryHostName=registryHostName;
       }

       public void run(){
			try{
				Socket socket=new Socket(registryHostName,7070);
				DataInputStream in=new DataInputStream(socket.getInputStream());

				do{
					String clientList=in.readLine();
					printClientList(clientList);
				}while(true);
			}catch(Exception e){
				e.printStackTrace();
			}
       }//end run

    private void printClientList(String clientList){
       StringTokenizer tokens=new StringTokenizer(clientList,":");
		int counter = 0;
		
       while(tokens.hasMoreTokens()){
			String hostName=tokens.nextToken();
			v.addElement(hostName);
		
			System.out.println("RegistryConnectionHandlerThread : "+counter++ +"  "+hostName);       
        }
		
   }//end method
   
   public static Vector getHostName(){
	   return v;
   }

	   
}//end class