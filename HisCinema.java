import java.io.*;
import java.net.*;


public class HisCinema {
	
	public static void main(String argv[]) throws Exception
	{
		//String clientSentence;
		//String cappedSentence;
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    ServerSocket welcomeSocket = null;
	    Socket connectionSocket = null;
	    try{
	    	welcomeSocket = new ServerSocket(6789);
		
	    	while (true) {
	    		try{
	    			connectionSocket = welcomeSocket.accept();
				
	    			File myFile = new File("index.txt");
				
	    			byte [] mybytearray  = new byte [(int)myFile.length()];
				
	    			fis = new FileInputStream(myFile);
	    			bis = new BufferedInputStream(fis);
	    			bis.read(mybytearray,0,mybytearray.length);
				
	    			os = connectionSocket.getOutputStream();
				
	    			System.out.println("Sending " + myFile.toString() + "(" + mybytearray.length + " bytes)");
				
	    			os.write(mybytearray,0,mybytearray.length);
	    			os.flush();
	    			System.out.println("Done.");

	    		}
	    		finally {
	    			if (bis != null) bis.close();
	    			if (os != null) os.close();
	    			if (connectionSocket !=null) connectionSocket.close();
		        }
	    		
	    	}
	    	
	    }
	    finally {
		      if (welcomeSocket != null) welcomeSocket.close();
		}
	}
}
