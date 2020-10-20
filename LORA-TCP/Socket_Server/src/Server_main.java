import java.net.*;  
import java.io.*;  

public class Server_main extends Thread {
	protected static Socket socket;
	Server_main(Socket s)  
    {  
        this.socket = s;  
    }  
	public static void main(String []args){  
            server();  
    }  
	public static void server(){  
        try{  
            ServerSocket server_socket = new ServerSocket(8087);
            while(true){  
                System.out.println("The Server is starting .......");  
                new Server_main(server_socket.accept()).start(); 
            }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
   
    }  
	public void run(){  
        try{
            InputStream inputstream=socket.getInputStream();  
            byte [] buf =new byte[100];
            int len=inputstream.read(buf);  
            System.out.println(new String(buf,0,len));  
            inputstream.close();  
            socket.close();  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
}
