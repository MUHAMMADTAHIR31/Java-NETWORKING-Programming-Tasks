import java.io.*;
import java.net.*;
class FileSender{
	public static void main(String arg[])throws Exception{
		String fileName = "temp.txt";
		String path = "C:\\Users\\Maham Computers\\Desktop\\Center files\\"+fileName;
		FileInputStream file = new FileInputStream(path);
		int size = file.available();
		byte data[] = new byte[size];
		file.read(data,0,size);
		file.close();
		
		Socket socket = new Socket("localhost",9090);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		out.println(fileName);
		out.println(size);
		out.write(data,0,size);
		
		System.out.println("File Transfer Successfully..");
		
		in.close();
		out.close();
		socket.close();
		
	}//End of main Class
}//End of main Method