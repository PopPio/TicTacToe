import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class GameClient extends Thread{
	ObjectInputStream serverInput;
	ObjectOutputStream serverOutput;
	
	public void Connect(String ip, int port){
		try{
			Socket serverSocket = new Socket(ip, port);
			OutputStream os = serverSocket.getOutputStream();
			serverOutput = new ObjectOutputStream(os);
			InputStream is = serverSocket.getInputStream();
			serverInput = new ObjectInputStream(is);
			start();
		} catch(UnknownHostException e){
			System.err.println("Don't know about host: " + ip);
		} catch(IOException e){
			System.err.println("Couldn't get I/O for " + "the connection to work");
		}
	}
	
	public void run(){
		while(true){
			try{
				PassingObject theObject = (PassingObject)serverInput.readObject();
				processObject(theObject);
				if(theObject.protocol == 'e'){
					System.out.println("end game");
					serverInput.close();
					serverOutput.close();
					//INVOKE UI TO GO BACK
					break;
				}
			}catch(IOException e){
				System.out.println("IO Error");
				try{
					serverInput.close();
					serverOutput.close();
					System.out.println("Stream closed");
				}catch(IOException err){
					System.out.println("cannot close streams");
				}
				return;
			}catch(ClassNotFoundException e){
				System.out.println("class not found");
			}
		}
	}
	
	public synchronized void processObject(PassingObject theObject){
		if(theObject.protocol == 'g'){
			//game info
			System.out.println("player " + theObject.symbol + " places at + " + (theObject.position+1));
		}else if(theObject.protocol == 'e'){
			//leave room
			System.out.println("user left room closed");
		}else if(theObject.protocol == 'c'){
			//chat
			System.out.println(theObject.name + " says: " + theObject.text);
		}else if(theObject.protocol == 'r'){
			//reset score
			System.out.println("reset score");
		}else if(theObject.protocol == 'w'){
			//win
			System.out.println(theObject.symbol + " wins!");
		}else if(theObject.protocol == 'd'){
			//draw
			System.out.println("draws...");
		}
	}
	
	public void sendObject(PassingObject theObject){
		try{
			serverOutput.writeObject(theObject);
		}catch(IOException e){
			System.out.println("ioerror");
		}
	}
	
}
