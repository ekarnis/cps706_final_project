import java.io.*;
import java.net.*;


public class HisCinemaAuthDNS {
	

	public static void main(String argv[]) throws Exception
	{
	
		DatagramSocket toLocalSocket = new DatagramSocket(9875);
	
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
	
		while (true) {
		
			DatagramPacket receivePacket = 
					new DatagramPacket(receiveData, receiveData.length);
		
			toLocalSocket.receive(receivePacket);
		
			String sentence = new String(receivePacket.getData());
		
			InetAddress iPAddress = receivePacket.getAddress();
		
			int port = receivePacket.getPort();
		
			System.out.println("FROM CLIENT:" + sentence);
		
			String cappedSentence = sentence.toUpperCase();
		
			sendData = cappedSentence.getBytes();
		
			DatagramPacket sendPacket = 
			new DatagramPacket(sendData, sendData.length, iPAddress, port);
					
			toLocalSocket.send(sendPacket);
		

		}
	}
}
