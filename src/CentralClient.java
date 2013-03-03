import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class CentralClient extends Thread{
	ObjectInputStream serverInput;
	ObjectOutputStream serverOutput;
	final String ip = "";
	final int port = 4;
	Client2 client;
	
	public CentralClient(Client2 client){
		this.client = client;
	}
	
	public boolean logIn(String uid, String password){
		PassingObjectCentral p = new PassingObjectCentral();
		p.logIn(uid, password);
		ObjectOutputStream serverOutput = null;
		ObjectInputStream serverInput = null;
		Socket serverSocket;
		try{
			serverSocket = new Socket(ip, port);
			OutputStream os = serverSocket.getOutputStream();
			serverOutput = new ObjectOutputStream(os);
			InputStream is = serverSocket.getInputStream();
			serverInput = new ObjectInputStream(is);
		}catch(IOException e){
			System.out.println("error in attempting to connect");
			e.printStackTrace();
			return false;
		}
		try{
			serverOutput.writeObject(p);
		}catch(IOException e){
			System.out.println("error in writing log in object to server");
			e.printStackTrace();
			try{
				serverSocket.close();
			}catch(IOException err){
				System.out.println("unable to close socket");
				err.printStackTrace();
			}
			return false;
		}
		try{
			PassingObjectCentral loginResult = (PassingObjectCentral)serverInput.readObject();
			if(loginResult.logIn){
				//Log in success
				this.serverInput = serverInput;
				this.serverOutput = serverOutput;
				initializeData();
				return true;
			}else{
				//Log in fail
				serverInput.close();
				serverOutput.close();
				serverSocket.close();
				return false;
			}
		}catch(IOException e){
			System.out.println("error in receiving log in object from server");
			e.printStackTrace();
			return false;
		}catch(ClassNotFoundException e){
			System.out.println("call not found error in login method");
			return false;
		}
	}
	
	//after log in success server will send list of friends and list of online users
	//store those data into UI before changing the page on UI
	public void initializeData(){
		try {
			PassingObjectCentral initializer = (PassingObjectCentral)serverInput.readObject();
			//set friendlist onlinelist name
			start();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		
	}
}
