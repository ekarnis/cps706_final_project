import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ClientApp {
	
	public final static int FILE_SIZE = 1024;


	public static void main(String argv[]) throws Exception
	{
		//TCP to hisCinema
		InetAddress IPAddress = InetAddress.getLocalHost();

		String sentence;
		//String modifiedSentence;
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket clientSocket = null;
	    String line = null;
	    String option = null;
	    String URL = null;
	    
	    Scanner reader = new Scanner(System.in);
	    
	    while(reader.hasNextLine()){
	    	
	    	sentence = reader.nextLine();
	    
	    	if (sentence.equals("www.hiscinema.com") ){
	    
	    		//ip hiscinema.com
	    		clientSocket = new Socket(IPAddress, 6789);
			
	    		byte [] mybytearray  = new byte [FILE_SIZE];
			
	    		InputStream is = clientSocket.getInputStream();
	    		fos = new FileOutputStream("index.txt");
	    		bos = new BufferedOutputStream(fos);
	    		BufferedReader fr = new BufferedReader(new FileReader("index.txt"));
	    		bytesRead = is.read(mybytearray,0,mybytearray.length);
	    		current = bytesRead;
	    		do {
	    			bytesRead =
		            is.read(mybytearray, current, (mybytearray.length-current));
	    			if(bytesRead >= 0) current += bytesRead;
	    		} while(bytesRead > -1);

		      bos.write(mybytearray, 0 , current);
		      bos.flush();
		      System.out.println("File " + "index.txt"
		          + " downloaded (" + current + " bytes read)");
		      while ((line = fr.readLine()) != null){
		    	  System.out.println(line);
		      }
		      fr.close();
		      System.out.println("select file to download: ");
		      option = reader.nextLine();
		      
		      if (option.equals("1")){
		    	  URL = "http://video.hiscinema.com/F1";
		      }
		      else if (option.equals("2")){
		    	  URL = "http://video.hiscinema.com/F2";
		      }
		      else if (option.equals("3")){
		    	  URL = "http://video.hiscinema.com/F3";
		      }
		      else if (option.equals("4")){
		    	  URL = "http://video.hiscinema.com/F4";
		      }
		      else if (option.equals("5")){
		    	  URL = "http://video.hiscinema.com/F5";
		      }
		      
		      //System.out.println(URL);
		      clientSocket.close();
		      
		      client_to_localDNS(URL);
		      
		      
	    	}
	
	    }
	}
	
	public static void client_to_localDNS(String URL) throws IOException {
		
		DatagramSocket clientSocketUDP = new DatagramSocket();
		//InetAddress IPlocalDNS = InetAddress.getLocalHost();
		InetSocketAddress IPlocalDNS = new InetSocketAddress("192.168.0.16", 9876);
	      
	    byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[2048];
		  
		sendData = URL.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPlocalDNS);
		
		clientSocketUDP.send(sendPacket);
		System.out.println("sending " + URL + " to local DNS");
		
		DatagramPacket receivePacket = 
		new DatagramPacket(receiveData, receiveData.length);
		clientSocketUDP.receive(receivePacket);
		
		String confirm = new String(receivePacket.getData());
		System.out.println("client received from localDNS: " + confirm);
		
		clientSocketUDP.close();
		
	}
	
	
}
