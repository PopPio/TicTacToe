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
				initializeData(loginResult);
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
	
	//after log in success or register server will send list of friends and list of online users
	//store those data into UI before changing the page on UI
	public void initializeData(PassingObjectCentral initializer){
			//set friendlist onlinelist name uid
			client.updateFriendList(initializer.friendList);
			client.updateOnlineList(initializer.userList);
			client.uid = initializer.uid;
			client.playerName = initializer.name; 
			client.profile.setText(client.playerName);
			client.redirectToHomePanel();
			start();
	}
	
	public void register(String name, String uid, String password){
		PassingObjectCentral p = new PassingObjectCentral();
		p.register(name, uid, password);
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
			return;
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
			return;
		}
		try{
			PassingObjectCentral loginResult = (PassingObjectCentral)serverInput.readObject();
			//Register Success
			this.serverInput = serverInput;
			this.serverOutput = serverOutput;
			initializeData(loginResult);
			return;
		}catch(IOException e){
			System.out.println("error in receiving log in object from server");
			e.printStackTrace();
			return;
		}catch(ClassNotFoundException e){
			System.out.println("call not found error in register method");
			return;
		}
	}
	
	public void createRoom(){
		PassingObjectCentral passObject = new PassingObjectCentral();
		passObject.createRoom();
		try{
			serverOutput.writeObject(passObject);
		}catch(IOException e){
			System.out.println("unable to pass creatroom object");
			e.printStackTrace();
		}
	}
	
	public void joinGame(String inGameIP){
		PassingObjectCentral passObject = new PassingObjectCentral();
		passObject.joinGame(inGameIP);
		try{
			serverOutput.writeObject(passObject);
		}catch(IOException e){
			System.out.println("unable to pass joinroom object");
			e.printStackTrace();
		}
	}
	
	public void addFriend(String targetuid){
		PassingObjectCentral passObject = new PassingObjectCentral();
		passObject.addFriend(targetuid);
		try{
			serverOutput.writeObject(passObject);
		}catch(IOException e){
			System.out.println("unable to pass addfriend object");
			e.printStackTrace();
		}
	}
	
	public void requestProfile(){
		PassingObjectCentral passObject = new PassingObjectCentral();
		passObject.requestProfile();
		try{
			serverOutput.writeObject(passObject);
		}catch(IOException e){
			System.out.println("unable to pass addfriend object");
			e.printStackTrace();
		}
	}
	
	public void changeName(String name){
		PassingObjectCentral passObject = new PassingObjectCentral();
		passObject.changeName(name, client.uid);
		try{
			serverOutput.writeObject(passObject);
		}catch(IOException e){
			System.out.println("unable to pass addfriend object");
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try{
				PassingObjectCentral getObject = (PassingObjectCentral)serverInput.readObject();
				if(getObject.protocol == 'o'){
					//log out close stream and end
					try{
						serverInput.close();
						serverOutput.close();
						//TODO ui invoke logout
						return;
					}catch(IOException e){
						System.out.println("unable to close stream on log out protocol");
						e.printStackTrace();
						return;
					}
				}else if(getObject.protocol == 'a'){
					//get a friendlist reply from server
					client.updateFriendList(getObject.friendList);
					continue;
				}else if(getObject.protocol == 'p'){
					//get a profile reply from server
					//TODO invoke UI Change
				}else if(getObject.protocol == 'u'){
					//get update online list and frist list from server
					client.updateFriendList(getObject.friendList);
					client.updateOnlineList(getObject.userList);
					continue;
				}
			}catch(IOException e){
				
			}catch(ClassNotFoundException e){
				
			}
		}
	}
}
