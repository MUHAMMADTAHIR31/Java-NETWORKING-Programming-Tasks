class P2PMessageConversation{
	public static void main(String arg[])throws Exception{
		new MessageReceiverThread().start();
		new RegistryConnectionHandlerThread("localhost").start();
		new MessageSenderFrame();
   }
}