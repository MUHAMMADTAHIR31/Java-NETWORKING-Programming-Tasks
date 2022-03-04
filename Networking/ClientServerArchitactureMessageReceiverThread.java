import java.io.*;

class ClientServerArchitactureMessageReceiverThread extends Thread{
	private static DataInputStream in;
	private static javax.swing.JTextArea jTextArea;
	ClientServerArchitactureMessageReceiverThread(DataInputStream in,javax.swing.JTextArea jTextArea){
		this.in=in;			
		this.jTextArea = jTextArea;
	}//End of constructor
	
	public void run() {
		try{
			do{
				String cmd=in.readLine();
				java.util.StringTokenizer tokens=new java.util.StringTokenizer(cmd,":");
				String user=tokens.nextToken();
				String msg=tokens.nextToken();
				System.out.println(user+" : " + msg);
				jTextArea.append("\n"+user+" Says:" + msg);

				
			}while(true);
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End of run
}//end inner class