import java.io.*;
import java.net.*;
class MultiFileReciverThread extends Thread{
		private Socket socket;
		MultiFileReciverThread(Socket socket){
			this.socket = socket;
		}//end of constructor
		
		public void run(){
			try{
				
				DataInputStream in = new DataInputStream(socket.getInputStream());
				PrintStream out = new PrintStream(socket.getOutputStream());
			
				String fileName = in.readLine();
				int size = Integer.parseInt(in.readLine());
				byte data[] = new byte[size];
				in.read(data,0,size);
				
				System.out.println("File "+fileName+" Received Successfully..");

				File file = new File("F:\\Download");
				if(!file.exists())
					file.mkdirs();
				
				String filePath = "F:\\Download\\"+fileName;
				FileOutputStream fileOut = new FileOutputStream(filePath);
				fileOut.write(data,0,size);
				
				fileOut.close();
				System.out.println("File Saved Successfully..");
				
				// in.close();
				// out.close();
				// socket.close();
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}//End of run
		
		
		
}//End of main Class
