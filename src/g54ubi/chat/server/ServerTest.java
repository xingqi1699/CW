package g54ubi.chat.server;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest {
	private static Server myServer = null;
	private static Thread myThreadServer = null;
	
	@BeforeClass
	public static void initialize(){
		 System.out.println("@Before"); 
		
		 myThreadServer = new Thread() { 
	            public void run() { 
	                    System.out.println("@Before myThread run()"); 
	                             myServer = new Server(9000); 
	            } 
	    }; 
	    myThreadServer.start(); 
}

	@Test
	public void testConnection() {
    
		TestClient client1 = new TestClient("Client1");
		Thread clientThread1 = new Thread(client1);
		clientThread1.start();
		
      while(client1.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      String result = client1.getResult();
	    System.out.println("Client1: " + result);
	    client1.setCommand("QUIT");
		assertEquals("OK Welcome to the chat server, there are currelty 1 user(s) online", result);
		
	}
	
	
	@Test
	public void testInvalidMessage(){
		TestClient client2 = new TestClient("Client2");
		Thread clientThread2 = new Thread(client2);
		clientThread2.start();
		
      while(client2.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client2.setCommand("ABC");
      while(client2.getFlag() == 1){}
      
      String finalresult = client2.getCommandResult();
      System.out.println("Client2: " + finalresult);
	    client2.setCommand("QUIT");
      assertEquals("BAD invalid command to server", finalresult);
	    
	}
	
	
	@Test
	public void testBadCommand(){
		TestClient client3 = new TestClient("Client3");
		Thread clientThread3 = new Thread(client3);
		clientThread3.start();
		
      while(client3.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      
      client3.setCommand("BADCOMMAND");
      
      while(client3.getFlag() == 1){}
      String finalResult = client3.getCommandResult();
      System.out.println("Client3: " + finalResult);
	    client3.setCommand("QUIT");
      assertEquals("BAD command not recognised", finalResult);
	}
	
	@Test
	public void testStatUnReg(){
		TestClient client4 = new TestClient("Client4");
		Thread clientThread4 = new Thread(client4);
		clientThread4.start();
		
      while(client4.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      
      client4.setCommand("STAT");
      while(client4.getFlag() == 1){}
      String finalResult = client4.getCommandResult();
      System.out.println("Client4: " + finalResult);
	    client4.setCommand("QUIT");
      assertEquals("OK There are currently 1 user(s) on the server You have not logged in yet", finalResult);
	}
	
	@Test
	public void testStatReg(){
		TestClient client5 = new TestClient("Client5");
		Thread clientThread5 = new Thread(client5);
		clientThread5.start();
		
      while(client5.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client5.setCommand("IDEN Client5");
      client5.setCommand("STAT");
      while(client5.getFlag() == 1){}
      String finalResult = client5.getCommandResult();
      System.out.println("Client5: " + finalResult);
	    client5.setCommand("QUIT");
      assertEquals("OK There are currently 1 user(s) on the server You are logged im and have sent 0 message(s)", finalResult);
	}
	
	@Test
	public void testListUnReg(){
		TestClient client6 = new TestClient("Client6");
		Thread clientThread6 = new Thread(client6);
		clientThread6.start();
		
      while(client6.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client6.setCommand("LIST");
      while(client6.getFlag() == 1){}
      String finalResult = client6.getCommandResult();
      System.out.println("Client6: " + finalResult);
	    client6.setCommand("QUIT");
      assertEquals("BAD You have not logged in yet", finalResult);
	}
	
	@Test
	public void testListReg(){
		TestClient client7 = new TestClient("Client7");
		Thread clientThread7 = new Thread(client7);
		clientThread7.start();
		
		TestClient client8 = new TestClient("Client8");
		Thread clientThread8 = new Thread(client8);
		clientThread8.start();
		
      while(client7.getResult()== null || client8.getResult() == null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      
      client7.setCommand("IDEN Client7");
      client8.setCommand("IDEN Client8");
      while(client8.getFlag() == 1 ){}
      client7.setCommand("LIST");
      while(client7.getFlag() == 1){}
      String finalResult = client7.getCommandResult();
      System.out.println("Client7Final: " + finalResult);
	    client7.setCommand("QUIT");
	    client8.setCommand("QUIT");
	    boolean condition = finalResult.equals("OK Client7, Client8, ") ||  finalResult.equals("OK Client8, Client7, ") ;
	    System.out.println("Condition: "+condition);
      assertTrue("False", condition);
	}
	
	@Test
	public void testIdenUnReg(){
		TestClient client9 = new TestClient("Client9");
		Thread clientThread9 = new Thread(client9);
		clientThread9.start();
		
      while(client9.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client9.setCommand("IDEN Client9");
      while(client9.getFlag() == 1){}
      String finalResult = client9.getCommandResult();
      System.out.println("Client9: " + finalResult);
	    client9.setCommand("QUIT");
      assertEquals("OK Welcome to the chat server Client9", finalResult);
	}
	
	@Test
	public void testIdenBadUsername(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		TestClient client2 = new TestClient("Client2");
		Thread clientThread2 = new Thread(client2);
		clientThread2.start();
      while(client.getResult()== null || client2.getResult() == null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
		client2.setCommand("IDEN Client");
      client.setCommand("IDEN Client");
      while(client2.getFlag() == 1){}
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
	    client.setCommand("QUIT");
	    client2.setCommand("QUIT");
      assertEquals("BAD username is already taken", finalResult);
	}
	
	@Test
	public void testIdenReg(){
		TestClient client6 = new TestClient("Client6");
		Thread clientThread6 = new Thread(client6);
		clientThread6.start();
		
      while(client6.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client6.setCommand("IDEN Client");
      client6.setCommand("IDEN Client2");
      while(client6.getFlag() == 1){}
      String finalResult = client6.getCommandResult();
      System.out.println("Client6: " + finalResult);
	    client6.setCommand("QUIT");
      assertEquals("BAD you are already registerd with username Client", finalResult);
	}
	
	@Test
	public void testHailUnReg(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
      while(client.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("HAIL broadcastmessage");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
	    client.setCommand("QUIT");
      assertEquals("BAD You have not logged in yet", finalResult);
	}
	
	@Test
	public void testHailReg(){
		TestClient client = new TestClient("Client1");
		Thread clientThread = new Thread(client);
		clientThread.start();
		TestClient client2 = new TestClient("Client2");
		Thread clientThread2 = new Thread(client2);
		clientThread2.start();
		TestClient client3 = new TestClient("Client3");
		Thread clientThread3 = new Thread(client3);
		clientThread3.start();
		
      while(client.getResult()== null || client2.getResult() == null || client3.getResult() == null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
		
      client.setCommand("IDEN Client1");
      client2.setCommand("IDEN Client2");
      client3.setCommand("IDEN Client3");
      while(client.getFlag() == 1){}
      while(client2.getFlag() == 1){}
      while(client3.getFlag() == 1){}
      client.setCommand("HAIL BroadcastMessage");
      while(client.getFlag() == 1){}
      String finalResult1 = client.getCommandResult();
      String finalResult2 = client2.getCommandResult();
      String finalResult3 = client3.getCommandResult();
//      
//      while((client.getCommandResult()) == null || client.getCommandResult().equals("OK Welcome to the chat server Client1")){}
//      while((client2.getCommandResult()) == null || client2.getCommandResult().equals("OK Welcome to the chat server Client2")){}
//      while((client3.getCommandResult()) == null || client.getCommandResult().equals("OK Welcome to the chat server Client3")){}
      
      
      System.out.println("Client1: " + finalResult1);
      System.out.println("Client2: " + finalResult2);
      System.out.println("Client3: " + finalResult3);
      boolean condition = finalResult1.equals("Broadcast from Client1: broadcastmessage") 
	    		&& finalResult2.equals("Broadcast from Client1: broadcastmessage") 
	    		&&finalResult3.equals("Broadcast from Client1: broadcastmessage");
	    client.setCommand("QUIT");
	    client2.setCommand("QUIT");
	    client3.setCommand("QUIT");
	    
      assertTrue("False", condition);
	}
	
	@Test
	public void testMesgUnReg(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
      while(client.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("MESG message");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
	    client.setCommand("QUIT");
      assertEquals("BAD You have not logged in yet", finalResult);
	}
	
	@Test 
	public void testMesgBadForm(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
      while(client.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("IDEN Client1");
      client.setCommand("MESG BADFORMAT");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
	    client.setCommand("QUIT");
      assertEquals("BAD Your message is badly formatted", finalResult);
	}
	
	@Test 
	public void testMesgReg(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
		TestClient client2 = new TestClient("Client2");
		Thread clientThread2 = new Thread(client2);
		clientThread2.start();
		
      while(client.getResult()== null || client2.getResult() == null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("IDEN Client1");
      client2.setCommand("IDEN Client2");
      while(client2.getFlag() == 1){}
      client.setCommand("MESG Client2 Hello!");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      String finalResult2 = client2.getCommandResult();
      System.out.println("Client1: " + finalResult);
      System.out.println("Client2: " + finalResult2);
	    client.setCommand("QUIT");
	    client2.setCommand("QUIT");
	    boolean condition = finalResult.equals("OK your message has been sent") && finalResult2.equals("PM from Client1:Hello!");
      assertTrue("False", condition);
	}
	
	@Test 
	public void testMesgNoUser(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
      while(client.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("IDEN Client1");
      client.setCommand("MESG NoUser Hello!");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
	    client.setCommand("QUIT");
      assertEquals("BAD the user does not exist", finalResult);
	}
	
	@Test
	public void testQuitReg(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
      while(client.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("IDEN Client1");
      client.setCommand("QUIT");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
      assertEquals("OK thank you for sending 0 message(s) with the chat service, goodbye. ", finalResult);
	}
	
	@Test
	public void testQuitUnReg(){
		TestClient client = new TestClient("Client");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
      while(client.getResult()== null){
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      client.setCommand("QUIT");
      while(client.getFlag() == 1){}
      String finalResult = client.getCommandResult();
      System.out.println("Client: " + finalResult);
      assertEquals("OK goodbye", finalResult);
	}
	
}
