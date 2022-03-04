import java.io.*;
import java.util.*;
import java.net.*;

class ClientServerArchitacturDataShiftingHandleClientConnection extends Thread{
    private String hostName;
    private Socket socket;
	
	
    ClientServerArchitacturDataShiftingHandleClientConnection(String hostName,Socket socket){
		this.hostName=hostName;
		this.socket=socket;
    }

    public void run(){
       try{
			DataInputStream in=new DataInputStream (socket.getInputStream());
			
		   do{
				String header = in.readLine();
			    java.util.StringTokenizer tokens=new java.util.StringTokenizer(header,":");
				
				//Read Data..
				String user = tokens.nextToken();
				String fileName = tokens.nextToken();
				int packetSize = Integer.parseInt(tokens.nextToken());						
				int totalPackets = Integer.parseInt(tokens.nextToken());
				int lastPacketSize = Integer.parseInt(tokens.nextToken());
				
				System.out.println("File "+fileName+" Received Successfully..");
				
				//Write Data
				Socket destSocket=(Socket)ClientServerArchitactureDataShiftingServer.hashtable.get(user);
				PrintStream out=new PrintStream (destSocket.getOutputStream());
				String destHeader = fileName +":"+ packetSize +":"+ totalPackets +":"+ lastPacketSize;
				out.println(destHeader);
		
		
				//Read/Write Data...
				byte data[] = new byte[packetSize];
				int p;
				for( p = 0; p < totalPackets; p++){
					in.read(data,0,packetSize);
					out.write(data,0,packetSize);
				}
				in.read(data,0,lastPacketSize);
				out.write(data,0,lastPacketSize);
		
				System.out.println("-----------------DATA RECIVE FROM CLIENT ------------------");
				System.out.println("packetSize : "+packetSize);
				System.out.println("totalPackets : "+totalPackets);
				System.out.println("lastPacketSize : "+lastPacketSize);
				System.out.println("data.length : "+data.length);
				
		
				//Write Data..
				// sendDataToClient(user,fileName,packetSize,totalPackets,lastPacketSize,data);		
				
				
		   }while(true);
       }catch(Exception e){
            ClientServerArchitactureDataShiftingServer.hashtable.remove(hostName);   
            System.out.println(hostName+" Client Disconnected......");
       }
    }//end run

	// private void sendDataToClient(String user ,String fileName,int packetSize,int totalPackets,int lastPacketSize, byte data[])throws Exception{
		
			// Socket destSocket=(Socket)ClientServerArchitactureDataShiftingServer.hashtable.get(user);
			// PrintStream out=new PrintStream (destSocket.getOutputStream());
			// String header = fileName +":"+ packetSize +":"+ totalPackets +":"+ lastPacketSize;
			
			// out.println(header);
		
			// int p;
			// for( p = 0; p < totalPackets; p++){
				// out.write(data,0,packetSize);
			// }
			// out.write(data,0,lastPacketSize);
			
			// System.out.println("-----------------DATA SEND BY SERVER ------------------");
			// System.out.println("packetSize : "+packetSize);
			// System.out.println("totalPackets : "+totalPackets);
			// System.out.println("lastPacketSize : "+lastPacketSize);
			// System.out.println("data.length : "+data.length);
					
	// }//end method
	
}//end class