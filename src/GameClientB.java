import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class GameClientB extends Thread{
	ObjectInputStream serverInput;
	ObjectOutputStream serverOutput;
	
	Client2 client;
	
	final int port = 8;
	
	public GameClientB(Client2 client){
		this.client = client;
	}
	
	public void Connect(String ip){
		try{
			Socket serverSocket = new Socket(ip, port);
			OutputStream os = serverSocket.getOutputStream();
			serverOutput = new ObjectOutputStream(os);
			InputStream is = serverSocket.getInputStream();
			serverInput = new ObjectInputStream(is);
			PassingObjectB p = new PassingObjectB();
			System.out.println("Sending player name: " + client.playerName);
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
				PassingObjectB theObject = (PassingObjectB)serverInput.readObject();
				processObject(theObject);
				if(theObject.protocol == 'e'){
					System.out.println("end game");
					//INVOKE UI TO GO BACK
					return;
				}
			}catch(IOException e){
				System.out.println("IO Error");
				PassingObjectB p = new PassingObjectB();
				p.leave();
				processObject(p);
				return;
			}catch(ClassNotFoundException e){
				System.out.println("class not found");
			}
		}
	}
	
	public synchronized void processObject(PassingObjectB theObject){
		if(theObject.protocol == 'g'){
			//game info
			System.out.println("player " + theObject.symbol + " places at + " + (theObject.position+1));
			client.receiveTick(theObject.position, theObject.symbol);
		}else if(theObject.protocol == 'e'){
			//leave room
			try{
				serverInput.close();
				serverOutput.close();
			}catch(IOException e){
				System.out.println("IOError on closing stream on method processObject");
			}
			System.out.println("user left room closed");
			System.out.println("Disconnected");
			client.redirectToHomePanel();
		}else if(theObject.protocol == 'c'){
			//chat
			System.out.println(theObject.name + " says: " + theObject.text);
			client.receiveChat(1, theObject.text, theObject.name);
		}else if(theObject.protocol == 'r'){
			//reset score
			System.out.println("reset score");
			client.resetScoreRequest();
		}else if(theObject.protocol == 'w'){
			//win
			System.out.println(theObject.symbol + " wins!");
			try{
				client.win(theObject.symbol);
			}catch(IOException e){
				System.out.println("error in calling win method");
				e.printStackTrace();
			}
		}else if(theObject.protocol == 'd'){
			//draw
			System.out.println("draws...");
			try{
				client.draw();
			}catch(IOException e){
				System.out.println("error in calling draw method");
				e.printStackTrace();
			}
		}else if (theObject.protocol == 'b'){
			//notify ui will be what symbol
			System.out.println("you are " + theObject.symbol);
			client.setSide(theObject.symbol);
			client.ourSide = theObject.symbol;
			if(!client.isHost)
				client.initializeScreen();
			else
				client.createGame();
		}else if (theObject.protocol == 'n'){
			//name
			client.opponentName = theObject.name;
		}else if(theObject.protocol == 'x'){
			//request symbol
			System.out.println("Receive protocol x");
			System.out.println("Current side is :" + client.currentSide);
			PassingObjectB p = new PassingObjectB();
			p.replySymbol(client.currentSide);
			sendObject(p);
		}else if(theObject.protocol == 'z'){
			System.out.println("get name: " + theObject.name);
			System.out.println("get symbol " + theObject.symbol);
			client.specSeting(theObject.name, theObject.symbol);
		}
	}
	
	public void sendObject(PassingObjectB theObject){
		try{
			serverOutput.writeObject(theObject);
		}catch(IOException e){
			System.out.println("ioerror");
		}
	}
	
}
