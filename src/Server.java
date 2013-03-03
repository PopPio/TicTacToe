import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server extends Thread{
	ObjectInputStream userInput[];
	ObjectOutputStream userOutput[];
	ServerSocket serverSocket = null;
	
	String name0, name1;

	TheGame game = null;
	
	int port;
	
	public Server(int port){
		this.port = port;
		userInput = new ObjectInputStream[2];
		userOutput = new ObjectOutputStream[2];
		start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int n = 0;

		try{
			serverSocket = new ServerSocket(port);
			System.out.println("Connection Socket Created");
			try{
				while(n < 2){
					System.out.println("Waiting for Connection");
					new GameRoomHost(serverSocket.accept(), n++);
					System.out.println("user " + n + " connected");
				}
			}catch(IOException e){
				System.err.println("Accept failed.");
			}
		}catch(IOException e){
			System.err.println("Create Socket failed.");
			e.printStackTrace();
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
					if(getObject.protocol == 'n'){
						System.out.println("get name object from id:" + id + " name :" + getObject.name);
						if(id == 0){
							name0 = getObject.name;
							continue;
						}else{
							name1 = getObject.name;
							PassingObject p = new PassingObject();
							p.setName(name0);
							userOutput[1].writeObject(p);
							p = new PassingObject();
							p.setName(name1);
							userOutput[0].writeObject(p);
							p = null;
							game = new TheGame();
							startGame();
							continue;
						}		
					}
					PassingObject passObject = readObject(getObject);
					passObject(id, passObject);
					if(passObject.protocol == 'e'){	
						try{
							System.out.println("initiate protocol 'e' in thread");
							userInput[id].close();
							userOutput[id].close();
							serverSocket.close();
							break;
						}catch(IOException err){
							System.out.println("IOError in closing the stream after receving end game protocol in thread");
						}
					}
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
					err.printStackTrace();
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
			if((i == id && theObject.protocol != 'e' && theObject.protocol != 'w' && theObject.protocol != 'd'))
				continue;
			try{
				System.out.println("writing to id: " + i + " with protocol: " + theObject.protocol);
				userOutput[i].writeObject(theObject);
			}catch(IOException e){
				System.out.println("error writing object from passObject method");
			}
		}
	}	
	
	public void startGame(){
		PassingObject p = new PassingObject();
		String first, second;
		if((new Random()).nextBoolean()){
			first = "x";
			second = "o";
		}else{
			first = "o";
			second = "x";
		}
		p.setStart(first);
		PassingObject o = new PassingObject();
		o.setStart(second);
		try{
			System.out.println("Trying to determine who start first then send info");
			userOutput[0].writeObject(p);
			userOutput[1].writeObject(o);
		}catch(IOException e){
			System.out.println("unable to write object on startGame()");
			e.printStackTrace();
		}
	}
}
