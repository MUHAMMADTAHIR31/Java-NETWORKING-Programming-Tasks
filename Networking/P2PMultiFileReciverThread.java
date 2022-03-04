import java.io.*;
import java.net.*;
class P2PMultiFileReciverThread extends Thread{
		private Socket socket;
		P2PMultiFileReciverThread(Socket socket){
			this.socket = socket;
		}//end of constructor
		
		public void run(){
			try{
				do{
				DataInputStream in = new DataInputStream(socket.getInputStream());
			
				
				String fileName = in.readLine();
				int packetSize = Integer.parseInt(in.readLine());
				int totalPackets = Integer.parseInt(in.readLine());
				int lastPacketSize = Integer.parseInt(in.readLine());
				
				
				
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
				
				System.out.println("packetSize : "+packetSize);
				System.out.println("totalPackets : "+totalPackets);
				System.out.println("lastPacketSize : "+lastPacketSize);
				System.out.println("data.length : "+data.length);
				
				
				
								
				// System.out.println("File "+fileName+" Received Successfully..");

				// File file = new File("F:\\Download");
				// if(!file.exists())
					// file.mkdirs();
				
				// String filePath = "F:\\Download\\"+fileName;
				// FileOutputStream fileOut = new FileOutputStream(filePath);
				// fileOut.write(data,0,size);
				
				// fileOut.close();
				// System.out.println("File Saved Successfully..");
				
				in.close();
				socket.close();
				}while(true);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}//End of run
		
		
		
}//End of main Class
