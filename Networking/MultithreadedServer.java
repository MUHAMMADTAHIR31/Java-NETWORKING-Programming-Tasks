import java.io.*;

import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
public class MultithreadedServer{
	
	Vector<ClientHandler> clients = new Vector<ClientHandler>();
	Vector<String> users = new Vector<String>();
	private static ServerSocket servSocket;
	private static final int PORT = 1247;

	public MultithreadedServer() throws IOException{
		servSocket = new ServerSocket(PORT);
		while(true) {

					Socket client = servSocket.accept();
					System.out.println("\nNew client accepted.\n");




		ClientHandler handler;
			handler = new ClientHandler(client);
			clients.add(handler);

		}
	}
	
	public static void main(String[] args) throws IOException	{
		MultithreadedServer ms = new MultithreadedServer();

	}

	class ClientHandler extends Thread	{

		private Socket client;
		private BufferedReader in;
		private PrintWriter out;
		String name,message,response;

		public ClientHandler(Socket socket){
		//Set up reference to associated socket...
			client = socket;
			try{
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(),true);
			}catch(IOException e){
				e.printStackTrace();
			}
			start();
		}
		
		public void sendMessage(String  msg)  {
			System.out.println("is it even coming here?");
			out.println("Server:" + msg);
		}
	
		public void boradcast(String message)  {
		// send message to all connected users
			for (ClientHandler ch : clients){
				ch.sendMessage(message); //Or something	
			}
		}
		
		public String getUserName() {  
			return name; 
		}
		
		public void run(){
			
			try{
				String received;
				
				do{
					System.out.println("Enter Your Message: ");
					String msg = in.readLine();
					out.println(msg);
					boradcast("testing");
					received = in.readLine();
					out.println("ECHO: " + received);
					//Repeat above until 'QUIT' sent by client...

				}while (!received.equals("QUIT"));
			}catch(IOException e){
				e.printStackTrace();
			}finally{
			
			 try{
				if (client!=null){
					System.out.println("Closing down connection...");
					client.close();
				}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}