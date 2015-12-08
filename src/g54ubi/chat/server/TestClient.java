package g54ubi.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient implements Runnable {
	 private static String serverAddress = "localhost";
	  private static int serverPort = 9000;
	  private String command;
	  private String clientName;
	  private String result = null;
	  private String result2 = null;
	  private BufferedReader in;
	  private PrintWriter out;
	  private Socket clientSocket;
	  
	  TestClient(String clientName) {
		  this.clientName = clientName;
		 this.command = null;
		 
	  }

	  public void run() {
	    in = null;
	    out = null;
	    clientSocket = null;
	    try {
	      clientSocket = new Socket(serverAddress,serverPort);
	      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	      out = new PrintWriter(clientSocket.getOutputStream(), true);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	    try {
	    	 this.result =in.readLine();
	      System.out.println(this.clientName + "I am the result :" +result);
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	   
	    while (true){}
	  }
	  
	  public String getResult(){
		  return result;
	  }
	  
	  public String getCommandResult(){return result2;}
	  
	  
	  public void setCommand(String command){
		  this.command = command;
		  System.out.println("Command: " + this.command);
		  out.println(command);
		  try {
	   	    	result2 =in.readLine();
	   	    	
	   	        System.out.println(this.clientName + ": " + result2);
	   	        
	   	        
	   	      } catch (IOException e) {
	    		 e.printStackTrace();
	   	      }
	  }
	  
	  public void clientClose(){
		  try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  
	}


