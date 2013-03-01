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
			serverSocket = new ServerSocket(26);
			System.out.println("Connection Socket Created");
			try{
				while(n < 2){
					System.out.println("Waiting for Connection");
					new GameRoomHost(serverSocket.accept(), n++);
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
			try{
				InputStream is = reader.getInputStream();
				userInput[id] = new ObjectInputStream(is);
			}catch(IOException e){
				System.out.println("Error initiaing inputstream");
			}
			try{
				OutputStream os = reader.getOutputStream();
				userOutput[id] = new ObjectOutputStream(os);
			}catch(IOException e){
				System.out.println("Error initiaing inputstream");
			}
			myThread = new Thread(this);
			myThread.start();
		}
		
		public void run(){
			try{
				while(true){
					PassingObject getObject = (PassingObject)userInput[id].readObject();
					readObject(getObject);
					passObject(id, getObject);
					if(getObject.protocol == 'e')
						break;
				}
			}catch(ClassNotFoundException e){
				System.out.println("cannot find class");
			}catch(IOException e){
				System.out.println("IO Error");
			}
		}
	}
	
	public synchronized void readObject(PassingObject theObject){
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
	}

	public void passObject(int id, PassingObject theObject){
		for(int i = 0; i < 2; i ++){
			if(i == id)
				continue;
			try{
				userOutput[i].writeObject(theObject);
			}catch(IOException e){
				System.out.println("error writing object");
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
