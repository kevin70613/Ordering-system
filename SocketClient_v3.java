

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

	public class SocketClient_v3{

	   private Customer[] totalObject;
	   
	   public SocketClient_v3(Customer[] object){
		    totalObject = new Customer[object.length];
	        for(int i=0;i<object.length;i++)
	            totalObject[i]= new Customer(object[i]);
	   }

	   public String run() throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
	        //get the localhost IP address, if server is running on some other IP, you need to use that
	       InetAddress host = InetAddress.getLocalHost();
	       Socket socket = null;
	       ObjectOutputStream oos = null;
	       ObjectInputStream ois = null;
	        //establish socket connection to server
	       socket = new Socket(host.getHostName(), 9876);
	        //write to socket using ObjectOutputStream

	       
	       oos = new ObjectOutputStream(socket.getOutputStream());
	       //System.out.println("Sending request to Socket Server");
           
	       for(int i=0;i<totalObject.length;i++){
	          oos.writeObject(totalObject[i]);
	       }
	         //read the server response message
	       ois = new ObjectInputStream(socket.getInputStream());
	       String message = (String) ois.readObject();
	       System.out.println(message);
	         //close resources
	       ois.close();
	       oos.close();
		   
		   for(int i=0;i<totalObject.length;i++){
			   totalObject[i].setCustNo(Integer.parseInt(message));
		   }
		   insertToSQL(totalObject);
	       Thread.sleep(100);
	       return message;
	   }
	   
	public static void insertToSQL(Customer[] object){ 
			 insertToSQL connect = new insertToSQL(object);
		 }
	}