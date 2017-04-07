

import java.io.IOException;

public class ServerGUI{
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		
		while(true){
		  SocketServer_v3 server = new SocketServer_v3();
		  server.run();
		}  
	}
	
}
