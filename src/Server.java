import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread{
	ObjectInputStream userInput[];
	ObjectOutputStream userOutput[];

	TheGame game = null;
	
	public Server(){
		userInput = new ObjectInputStream[2];
		userOutput = new ObjectOutputStream[2];
		start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int n = 0;
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(4);
			System.out.println("Connection Socket Created");
			try{
				while(n < 2){
					System.out.println("Waiting for Connection");
					new GameRoomHost(serverSocket.accept(), n++);
					System.out.println("user " + n + " connected");
				}
				game = new TheGame();
			}catch(IOException e){
				System.err.println("Accept failed.");
			}
		}catch(IOException e){
			System.err.println("Create Socket failed.");
		}
	}
	
	class GameRoomHost implements Runnable{
		int id;
		Thread myThread;
		
		public GameRoomHost(Socket reader, int id){
			this.id = id;
			System.out.println("is instantiated");
			try{
				System.out.println("getOutputStream");
				OutputStream os = reader.getOutputStream();
				userOutput[id] = new ObjectOutputStream(os);
			}catch(IOException e){
				System.out.println("Error initiaing inputstream");
			}
			try{
				System.out.println("getInputStream");
				InputStream is = reader.getInputStream();
				System.out.println("getObjectInputStream");
				userInput[id] = new ObjectInputStream(is);
				System.out.println("initialized object input stream");
			}catch(IOException e){
				System.out.println("Error initiaing inputstream");
			}
			myThread = new Thread(this);
			myThread.start();
		}
		
		public void start(){
			myThread.start();
		}
		
		public void run(){
			try{
				while(true){
					System.out.println("Listening to id" + id);
					PassingObject getObject = (PassingObject)userInput[id].readObject();
					PassingObject passObject = readObject(getObject);
					passObject(id, passObject);
					if(passObject.protocol == 'e')
						break;
				}
			}catch(ClassNotFoundException e){
				System.out.println("cannot find class");
			}catch(IOException e){
				System.out.println("IO Error at id:" + id);
				System.out.println("possible sudden close in client stream from id:" + id);
				System.out.println("notify other player that the player left the room");
				PassingObject passObject = new PassingObject();
				passObject.leave();
				passObject(id, passObject);
				System.out.println("Trying to close stream");
				try{
					userInput[id].close();
					userOutput[id].close();
					System.out.println("stream closed");
				}catch(IOException err){
					System.out.println("unable to close stream");
				}
				return;
			}
		}
	}
	
	public synchronized PassingObject readObject(PassingObject theObject){
		if(theObject.protocol == 'g'){
			String process = game.clicked(theObject.symbol, theObject.position);
			if(!process.equals("c")){
				if(process.equals("x")){
					theObject = new PassingObject();
					theObject.win("x");
				}else if(process.equals("o")){
					theObject = new PassingObject();
					theObject.win("o");
				}else if(process.equals("d")){
					theObject = new PassingObject();
					theObject.draw();
				}
				game.newGame();
			}
		}else if(theObject.protocol == 's'){
			game.newGame();
			String winningSymbol = theObject.symbol.equals("x")?"o":"x";
			theObject = new PassingObject();
			theObject.protocol = 'w';
			theObject.symbol = winningSymbol;
		}
		return theObject;
	}

	public void passObject(int id, PassingObject theObject){
		for(int i = 0; i < 2; i ++){
			if(i == id)
				continue;
			try{
				System.out.println("writing to id: " + i);
				userOutput[i].writeObject(theObject);
			}catch(IOException e){
				System.out.println("error writing object from passObject method");
			}
		}
		if(theObject.protocol == 'l'){
			try{
				for(int i = 0; i < 2; i ++){
					userOutput[i].close();
					userInput[i].close();
				}
			}catch(IOException e){
				System.out.println("Problem closing stream");
			}
		}
	}	
}
