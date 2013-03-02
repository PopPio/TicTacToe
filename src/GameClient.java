import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class GameClient extends Thread{
	ObjectInputStream serverInput;
	ObjectOutputStream serverOutput;
	
	Client client;
	
	public GameClient(Client client){
		this.client = client;
	}
	
	public void Connect(String ip, int port){
		try{
			Socket serverSocket = new Socket(ip, port);
			OutputStream os = serverSocket.getOutputStream();
			serverOutput = new ObjectOutputStream(os);
			InputStream is = serverSocket.getInputStream();
			serverInput = new ObjectInputStream(is);
			PassingObject p = new PassingObject();
			p.setName(client.playerName);
			serverOutput.writeObject(p);
			start();
		} catch(UnknownHostException e){
			System.err.println("Don't know about host: " + ip);
		} catch(IOException e){
			System.err.println("Couldn't get I/O for " + "the connection to work");
		}
	}
	
	//prototocl for leaving the room
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * when press disconnect send an object of end game to server
	 * server replies with the same packet
	 * close both stream on inputstream to prevent error
	 */
	
	public void run(){
		while(true){
			try{
				PassingObject theObject = (PassingObject)serverInput.readObject();
				processObject(theObject);
				if(theObject.protocol == 'e'){
					System.out.println("end game");
					//INVOKE UI TO GO BACK
					return;
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
			try{
				serverInput.close();
				serverOutput.close();
			}catch(IOException e){
				System.out.println("IOError on closing stream on method processObject");
			}
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
		}else if (theObject.protocol == 'b'){
			//notify ui will be what symbol
			System.out.println("you are " + theObject.symbol);
			client.setSide(theObject.symbol);
			if(!client.isHost)
				client.initializeScreen();
			else
				client.createGame();
		}else if (theObject.protocol == 'n'){
			//name
			client.opponentName = theObject.name;
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
