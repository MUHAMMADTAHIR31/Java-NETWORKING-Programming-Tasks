import java.io.*;
import java.net.*;
class ClientServerArchitactureFileReciverThread extends Thread{
		private Socket socket;
		ClientServerArchitactureFileReciverThread(Socket socket){
			this.socket = socket;
		}//end of constructor
		
		public void run(){
			try{
				
				DataInputStream in = new DataInputStream(socket.getInputStream());

				String header = in.readLine();
			    java.util.StringTokenizer tokens=new java.util.StringTokenizer(header,":");
	
				String fileName = tokens.nextToken();
				int packetSize = Integer.parseInt(tokens.nextToken());						
				int totalPackets = Integer.parseInt(tokens.nextToken());
				int lastPacketSize = Integer.parseInt(tokens.nextToken());
	
				System.out.println("File "+fileName+" Received Successfully..!!!");

				File file = new File("F:\\Download");
				if(!file.exists())
					file.mkdirs();
				
				String filePath = "F:\\Download\\"+fileName;
				FileOutputStream fileOut = new FileOutputStream(filePath);
				byte data[] = new byte[packetSize];
				int p;
				for( p = 0; p < totalPackets; p++){
					in.read(data,0,packetSize);
					fileOut.write(data,0,packetSize);
			
				}
				in.read(data,0,lastPacketSize);
				fileOut.write(data,0,lastPacketSize);
				
				fileOut.close();
				System.out.println("File Saved Successfully..");
				
				
				System.out.println("-----------------DATA RECIVE FROM SERVER ------------------");
				System.out.println("packetSize : "+packetSize);
				System.out.println("totalPackets : "+totalPackets);
				System.out.println("lastPacketSize : "+lastPacketSize);
				System.out.println("data.length : "+data.length);
				
				// in.close();
				// socket.close();
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}//End of run
		
		
		
}//End of main Class
