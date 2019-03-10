package client1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class test {
	public static void main(String[] args) throws UnknownHostException {
		InetAddress localAddr=InetAddress.getLocalHost(); 
		System.out.println(localAddr);
	}
}
