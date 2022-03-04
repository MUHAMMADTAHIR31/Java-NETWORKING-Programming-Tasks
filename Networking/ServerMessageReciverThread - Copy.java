class ServerMessageReciverThread extends Thread{
	java.io.DataInputStream in;
	public ServerMessageReciverThread(java.io.DataInputStream in){
		this.in = in;
	}//End of contructor
	public void run(){
		do{
			try{
				System.out.println("\nClient says :"+in.readLine());
			}catch(Exception e){
				e.printStackTrace();
			}
		}while(true);
	}//End of run
}//End of of Class