import java.io.*;
import java.net.*;
class MultiFileSenderThread extends Thread{
		private Socket socket;
		private String fileName;
		private String path;

		public MultiFileSenderThread(Socket socket,String path, String fileName){
			this.socket = socket;
			this.fileName = fileName;
			this.path = path;
		}//End of constructor
		
		public void run(){
			try{
				
				
				String completePath = path+fileName;
				FileInputStream file = new FileInputStream(completePath);
				int size = file.available();
				byte data[] = new byte[size];
				file.read(data,0,size);
				file.close();
				
				
				DataInputStream in = new DataInputStream(socket.getInputStream());
				PrintStream out = new PrintStream(socket.getOutputStream());
				
				out.println(fileName);
				out.println(size);
				out.write(data,0,size);
				
				System.out.println("File "+fileName+" Transfer Successfully..");
				
				// in.close();
				// out.close();
				// socket.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}//End of run
		
		
		
}//End of main Class