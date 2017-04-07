

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
 
public class SocketServer_v3{
     
       //static ServerSocket variable
    private static ServerSocket server;
       //socket server port on which it will listen
    private static int port = 9876;
    private static int totalPrice = 0;
	private static int orderNum = 1;
    
	public SocketServer_v3(){
	
	}
	
	public static void run() throws IOException, ClassNotFoundException{
	    int ordering = 0;
          //create the socket server object
        server = new ServerSocket(port);
          //keep listens indefinitely until receives 'exit' call or program terminates
 
            System.out.println("Waiting for client request");
              //creating socket and waiting for client connection
            Socket socket = server.accept();
              //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
              //convert ObjectInputStream object to String
        while(ordering == 0){  
            Customer object = new Customer((Customer) ois.readObject());
            System.out.println("�~�W :"+object.getName()+" �ƶq :"+object.getNum()+" ��� : "+object.getPrice()+" ���\�s�� : "+orderNum);
            totalPrice += (object.getNum()*object.getPrice());
            if(object.getFinish()==1) 
			   ordering = 1;
		}
        System.out.println("�������O�`���B : "+totalPrice);
        
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        //write object to Socket
        oos.writeObject(Integer.toString(orderNum));
        orderNum++;
        //close resources
        ois.close();
        oos.close();
		totalPrice = 0; // because totalPrice is static
        socket.close();
            //terminate the server if client sends exit request

        System.out.println("Ordering finish");
        //close the ServerSocket object
        server.close();
    }
   
}