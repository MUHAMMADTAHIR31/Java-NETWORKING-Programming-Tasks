import java.net.*;
class InetAddressDemo{
	public static void main(String arg[]){
		try{
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println("IP Address:- " + inetAddress.getHostAddress());
			System.out.println("Host Name:- " + inetAddress.getHostName());
			
			
			InetAddress ip=InetAddress.getByName("localhost");  
  
			
			//Methods
			String getCanonicalHostName = ip.getCanonicalHostName();
			String getHostAddress = ip.getHostAddress();
			String getHostName = ip.getHostName();
			int hashCode = ip.hashCode();
			boolean isAnyLocalAddress = ip.isAnyLocalAddress();
			boolean	isLinkLocalAddress = ip.isLinkLocalAddress();
			boolean	isLoopbackAddress = ip.isLoopbackAddress();
			boolean	isMCGlobal = ip.isMCGlobal();
			boolean	isMCLinkLocal = ip.isMCLinkLocal();
			boolean	isMCNodeLocal = ip.isMCNodeLocal();
			boolean	isMCOrgLocal = ip.isMCOrgLocal();
			boolean	isMCSiteLocal = ip.isMCSiteLocal();
			boolean	isMulticastAddress = ip.isMulticastAddress();
			boolean	isReachable = ip.isReachable(1000);//int timeout
			boolean	isSiteLocalAddress = ip.isSiteLocalAddress();
			
			System.out.println("getCanonicalHostName : "+getCanonicalHostName);
			System.out.println("getHostAddress : "+getHostAddress);
			System.out.println("getHostName : "+getHostName);
			System.out.println("hashCode : "+hashCode);
			System.out.println("isAnyLocalAddress : "+isAnyLocalAddress);
			System.out.println("isLinkLocalAddress : "+isLinkLocalAddress);
			System.out.println("isLoopbackAddress : "+isLoopbackAddress);
			System.out.println("isMCGlobal : "+isMCGlobal);
			System.out.println("isMCLinkLocal : "+isMCLinkLocal);
			System.out.println("isMCNodeLocal : "+isMCNodeLocal);
			System.out.println("isMCOrgLocal : "+isMCOrgLocal);
			System.out.println("isMCSiteLocal : "+isMCSiteLocal);
			System.out.println("isMulticastAddress : "+isMulticastAddress);
			System.out.println("isReachable : "+isReachable);
			System.out.println("isSiteLocalAddress : "+isSiteLocalAddress);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End main Method
}//End of main Class


