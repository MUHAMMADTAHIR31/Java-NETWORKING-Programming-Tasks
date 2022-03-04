import java.io.*;
import java.net.*;
class P2PDataShiftingAgent{
	public static void main(String arg[])throws Exception{
		//Start the server..
		new ServerThread().start();
		
		DataInputStream  key = new DataInputStream(System.in);
		do{
			System.out.print("Enter full Path : ");
			String filePath = key.readLine();
			System.out.print("Enter IP Address : ");
			String ipAdress = key.readLine();
			
			File f = new File(filePath);
			String filename = null;
			if(f.exists()){
				filename = f.getName();
			}else{
				System.out.println("File not found!!! "+filename);
				continue;
			}
			
			DataInputStream file = new DataInputStream( new FileInputStream(filePath));
			int fileSize = file.available();
			int packetSize = 1000;
			int totalPackets = fileSize/packetSize;
			int lastPacketSize = fileSize%packetSize;
			
			Socket socket = null;
			try{
				socket =new Socket(ipAdress,9090);
			}catch(Exception e){
				System.out.println("Invalid IP-Address "+e.getMessage());
				continue;
			}
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			PrintStream out = new PrintStream(socket.getOutputStream());
			out.println(filename);
			out.println(packetSize);
			out.println(totalPackets);
			out.println(lastPacketSize);
			
			byte data[] = new byte[packetSize];
			int p;
			for( p = 0; p < totalPackets; p++){
				file.readFully(data,0,packetSize);
				out.write(data,0,packetSize);
				
				System.out.println("File Transferd "+p);
				
			}
			file.readFully(data,0,lastPacketSize);
			out.write(data,0,lastPacketSize);
			System.out.println("File Transferd "+p);
			System.out.println("File Transferd Successfully..");
			
			file.close();
			in.close();
			out.close();
			socket.close();
			
		}while(true);
		
	}//End of Main Method
}//End of Main Class