import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class LocalDDNS {
	
	public static void main(String argv[]) throws Exception
	{
		class Record{
			String name;
			String value;
			String type;
			
			public Record(String name, String value, String type){
				this.name = name;
				this.value = value;
				this.type = type;
			}
		}
		
		Record DNSrecords[] = new Record[10];
		
		DNSrecords[0] = new Record("herCDN.com", "NSherCDN.com", "NS");
		DNSrecords[1] = new Record("NSherCDN.com", "192.168.0.16", "A");
		DNSrecords[2] = new Record("hiscinema.com", "NShiscinema.com", "NS");
		DNSrecords[3] = new Record("NShiscinema.com", "192.168.0.16", "A");
		
		
		DatagramSocket toClientSocket = new DatagramSocket(9876);
		//DatagramSocket toHisAuthDNS = new DatagramSocket(9875);
		
		byte[] receiveData = new byte[2048];
		byte[] sendData = new byte[2048];
		
		while (true) {
			
			DatagramPacket receiveClientPacket = 
			new DatagramPacket(receiveData, receiveData.length);
			
			toClientSocket.receive(receiveClientPacket);
			
			String sentence = new String(receiveClientPacket.getData());
			
			InetAddress iPAddress = receiveClientPacket.getAddress();
			
			int port = receiveClientPacket.getPort();
			
			System.out.println("FROM CLIENT:" + sentence);
			
			DNSrecords[4] = new Record(sentence, iPAddress.toString(), "V");
			
			//System.out.println(DNSrecords[4].name + DNSrecords[4].value + DNSrecords[4].type);
			
			String cappedSentence = sentence.toUpperCase();
			String RR = (DNSrecords[4].name +","+ DNSrecords[4].value + ","+DNSrecords[4].type);
			//System.out.println(RR);
			sendData = RR.getBytes();
			//System.out.println(sendData);
			DatagramPacket sendClientPacket = 
			new DatagramPacket(sendData, sendData.length, iPAddress, port);
						
			toClientSocket.send(sendClientPacket);
			
			//toClientSocket.close();
			localDNS_to_hisAuthUDP(sendData.toString());
			
		}
		
	}
	
	public static void localDNS_to_hisAuthUDP(String record) throws IOException {
		
		DatagramSocket toHisAuthSocket = new DatagramSocket();
		
		InetSocketAddress IPhisAuth = new InetSocketAddress("192.168.0.16", 9875);
		
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[2048];
		
		sendData = record.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPhisAuth);
		
		toHisAuthSocket.send(sendPacket);
		System.out.println("sending " + record + " to hisAuthDNS");
		
		//toHisAuthSocket.close();

		
	}

}
