import java.io.*;
import java.net.*;
class FileReciver{
	public static void main(String arg[])throws Exception{
		
		ServerSocket server = new ServerSocket(9090);
		System.out.println("Server started..");
		Socket socket = server.accept();
		System.out.println("Client Connected..");
		DataInputStream in = new DataInputStream(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
	
		String fileName = in.readLine();
		int size = Integer.parseInt(in.readLine());
		byte data[] = new byte[size];
		in.read(data,0,size);
		
		System.out.println("File Received Successfully..");

		File file = new File("F:\\Download");
		if(!file.exists())
			file.mkdirs();
		
		String filePath = "F:\\Download\\"+fileName;
		FileOutputStream fileOut = new FileOutputStream(filePath);
		fileOut.write(data,0,size);
		
		fileOut.close();
		System.out.println("File Saved Successfully..");
		
		in.close();
		out.close();
		socket.close();
		
		
	}//End of main Class
}//End of main Method