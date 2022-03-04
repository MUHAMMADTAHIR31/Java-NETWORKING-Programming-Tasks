import java.io.*;
import java.net.*;
import java.util.*;
class  RegistryConnectionHandlerThread extends Thread{
	   private String registryHostName;
	   
	   private static String sendHostName;
	   
       RegistryConnectionHandlerThread(String registryHostName){
			this.registryHostName=registryHostName;
       }

       public void run(){
			try{
				Socket socket=new Socket(registryHostName,7770);
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

       while(tokens.hasMoreTokens()){
			String hostName=tokens.nextToken();
			setHostName(hostName);
			System.out.println("RegistryConnectionHandlerThread : "+hostName);       
        }
   }//end method
   
	public static String getHostName(){
		return sendHostName;
	}
	public static void setHostName(String sendHostNamee){
		sendHostName = sendHostNamee;
	}

	   
}//end class