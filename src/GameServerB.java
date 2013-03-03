import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class GameServerB extends Thread{
	ObjectInputStream userInput[];
	ObjectOutputStream userOutput[];
	ServerSocket serverSocket = null;
	
	String name0, name1;
	
	boolean isOnGoing;

	TheGame game = null;
	
	final int port = 8;
	
	public GameServerB(){
		userInput = new ObjectInputStream[0];
		userOutput = new ObjectOutputStream[0];
		isOnGoing = true;
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
				while(isOnGoing){
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
		String symbol0;
		
		public GameRoomHost(Socket reader, int id){
			this.id = id;
			System.out.println("is instantiated");
			try{
				System.out.println("getOutputStream");
				OutputStream os = reader.getOutputStream();
				userOutput = java.util.Arrays.copyOf(userOutput, userOutput.length + 1);
				userOutput[id] = new ObjectOutputStream(os);
			}catch(IOException e){
				System.out.println("Error initiaing inputstream");
			}
			try{
				System.out.println("getInputStream");
				InputStream is = reader.getInputStream();
				System.out.println("getObjectInputStream");
				userInput = java.util.Arrays.copyOf(userInput, userInput.length + 1);
				userInput[id] = new ObjectInputStream(is);
				System.out.println("initialized object input stream");
				if(id > 1){
					//reply as a spec
					PassingObjectB p = new PassingObjectB();
					p.setStart("spec");
					userOutput[id].writeObject(p);
					p = new PassingObjectB();
					p.whatSymbol();
					userOutput[0].writeObject(p);
				}
			}catch(IOException e){
				System.out.println("Error initiaing inputstream");
			}
			myThread = new Thread(this);
			myThread.start();
		}
		
		public void start(){
			myThread.start();
		}
		
		// TODO passingobject for question b requires user status: player/spectator
		
		public void setsymbol0(){

			PassingObjectB p = new PassingObjectB();
			p.specName(symbol0.equals("o")?name0:name1, symbol0.equals("o")?name1:name0);
			for(int i = 2; i < userInput.length; i ++){	
				try {
					System.out.println("trying to write name :" + name0 + "for symbol : " + symbol0);
					userOutput[i].writeObject(p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void run(){
			try{
				while(true){
					System.out.println("Listening to id" + id);
					PassingObjectB getObject = (PassingObjectB)userInput[id].readObject();
					if(getObject.protocol == 'n'){
						System.out.println("get name object from id:" + id + " name :" + getObject.name);
						if(id == 0){
							name0 = getObject.name;
							continue;
						}else if(id == 1){
							name1 = getObject.name;
							PassingObjectB p = new PassingObjectB();
							p.setName(name0);
							userOutput[1].writeObject(p);
							p = new PassingObjectB();
							p.setName(name1);
							userOutput[0].writeObject(p);
							p = null;
							game = new TheGame();
							startGame();
							continue;
						}else
							continue;
					}else if(getObject.protocol == 'x'){
						System.out.println("get symbol: " + getObject.symbol);
						symbol0 = getObject.symbol;
						setsymbol0();
						continue;
					}
					PassingObjectB passObject = readObject(getObject, id);
					passObject(id, passObject);
					if(passObject.protocol == 'e'){	
						try{
							System.out.println("initiate protocol 'e' in thread");
							userInput[id].close();
							userOutput[id].close();
							if(id < 2)
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
				System.out.println("notify other player that the player left the room if id < 2");
				if(id < 2){
					PassingObjectB passObject = new PassingObjectB();
					passObject.leave();
					passObject(id, passObject);
				}
				System.out.println("Trying to close stream for");
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
	
	public synchronized PassingObjectB readObject(PassingObjectB theObject, int id){
		if(theObject.protocol == 'g'){
			String process = game.clicked(theObject.symbol, theObject.position);
			if(!process.equals("c")){
				passObject(id, theObject);
				if(process.equals("x")){
					theObject = new PassingObjectB();
					theObject.win("x");
				}else if(process.equals("o")){
					theObject = new PassingObjectB();
					theObject.win("o");
				}else if(process.equals("d")){
					theObject = new PassingObjectB();
					theObject.draw();
				}
				game.newGame();
			}
		}else if(theObject.protocol == 's'){
			game.newGame();
			String winningSymbol = theObject.symbol.equals("x")?"o":"x";
			theObject = new PassingObjectB();
			theObject.protocol = 'w';
			theObject.symbol = winningSymbol;
		}
		return theObject;
	}			

	public void passObject(int id, PassingObjectB theObject){
		for(int i = 0; i < userOutput.length; i ++){
			if((i == id && theObject.protocol != 'e' && theObject.protocol != 'w' && theObject.protocol != 'd'))
				continue;
			if(id > 1 && theObject.protocol == 'e'){
				continue;
			}
			try{
				System.out.println("writing to id: " + i + " with protocol: " + theObject.protocol);
				userOutput[i].writeObject(theObject);
			}catch(IOException e){
				System.out.println("error writing object from passObject method");
			}
		}
	}	
	
	public void startGame(){
		PassingObjectB p = new PassingObjectB();
		String first, second;
		if((new Random()).nextBoolean()){
			first = "x";
			second = "o";
		}else{
			first = "o";
			second = "x";
		}
		p.setStart(first);
		PassingObjectB o = new PassingObjectB();
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
