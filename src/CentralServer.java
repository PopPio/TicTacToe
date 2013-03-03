import java.util.LinkedList;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class CentralServer extends Thread{
	LinkedList <User> userData;
	LinkedList <User> onlineUser;
	
	final int serverPort = 4;
	
	ObjectInputStream userInput[];
	ObjectOutputStream userOutput[];
	
	ServerSocket serverSocket = null;
	
	public static void main(String[] args){
		new CentralServer();
	}
	
	public CentralServer(){
		userData = new LinkedList<User>();
		onlineUser = new LinkedList<User>();
		userInput = new ObjectInputStream[0];
		userOutput = new ObjectOutputStream[0];
		start();
	}
	
	@Override
	public void run() {
		try{
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Connection Socket Created");
			try{
				while(true){
					System.out.println("Waiting for Connection");
					newConnection(serverSocket.accept());
					System.out.println("user connected");
				}
			}catch(IOException e){
				System.err.println("Accept failed.");
			}
		}catch(IOException e){
			System.err.println("Create Socket failed.");
			e.printStackTrace();
		}
	}
	
	//new connection check whether register or log in
	//if log in check whether valid log in or not if not reject then close connect;
	public void newConnection(Socket reader){
		System.out.println("is instantiated");
		ObjectOutputStream userOutput = null;
		ObjectInputStream userInput = null;
		String userIP = reader.getInetAddress().toString();
		System.out.println("connecting ip: " + userIP);
		
		try{
			System.out.println("getOutputStream");
			OutputStream os = reader.getOutputStream();
			userOutput = new ObjectOutputStream(os);
		}catch(IOException e){
			System.out.println("Error initiaing inputstream");
		}
		try{
			System.out.println("getInputStream");
			InputStream is = reader.getInputStream();
			System.out.println("getObjectInputStream");
			userInput = new ObjectInputStream(is);
			System.out.println("initialized object input stream");
		}catch(IOException e){
			System.out.println("Error initiaing inputstream");
		}
		PassingObjectCentral firstPacket = null;;
		try {
			firstPacket = (PassingObjectCentral)userInput.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(firstPacket.protocol == 'l'){
			//log in
			for(int i = 0; i < userData.size(); i ++){
				if(userData.get(i).uid.equals(firstPacket.uid) && userData.get(i).password.equals(firstPacket.password)){
					//correct Log in reply then start passing requried information
					userData.get(i).setOnline();
					userData.get(i).ip = userIP;

					onlineUser.add(userData.get(i));
					PassingObjectCentral firstReply = new PassingObjectCentral();
					firstReply.logInSuccess(getOnlineList(), getFriendList(onlineUser.size() - 1), userData.get(i).name, userData.get(i).uid);
					try {
						userOutput.writeObject(firstReply);
					} catch (IOException e) {
						System.out.println("fail to write firstReply object");
						e.printStackTrace();
					}
					this.userOutput = java.util.Arrays.copyOf(this.userOutput, this.userOutput.length + 1);
					this.userOutput[this.userOutput.length - 1] = userOutput;
					this.userInput = java.util.Arrays.copyOf(this.userInput, this.userInput.length + 1);
					this.userInput[this.userInput.length - 1] = userInput;
					userData.get(i).streamid = this.userInput.length - 1;
					//TODO start listening to this guy with id of this.userInput/Output.length - 1 and id = i and onlineid = onlineUser.size() - 1
					//TODO broadcast information that the person is online
					broadcast(i);
					new ServerListener(i, this.userInput.length - 1, onlineUser.size() - 1);
					return;
				}
			}
			//wrong user reply as fail then close stream
			PassingObjectCentral firstReply = new PassingObjectCentral();
			firstReply.logInFail();
			try{
				userOutput.writeObject(firstReply);
			}catch(IOException e){
				System.out.println("fail to write invalid log in firstReply object");
				e.printStackTrace();
			}
			//try to close stream
			try{
				System.out.println("closing Stream");
				userOutput.close();
				userInput.close();
			}catch(IOException e){
				System.out.println("unable to close stream on fail log in");
				e.printStackTrace();
			}
		}else if(firstPacket.protocol == 'r'){
			//register new user
			User newUser = new User(firstPacket.name, firstPacket.uid, firstPacket.password, userIP);
			newUser.setOnline();
			userData.add(newUser);
			onlineUser.add(newUser);
			int position = userData.size() - 1;
			PassingObjectCentral firstReply = new PassingObjectCentral();
			firstReply.logInSuccess(getOnlineList(), getFriendList(onlineUser.size() - 1), userData.get(position).name, userData.get(position).uid);
			try {
				userOutput.writeObject(firstReply);
			} catch (IOException e) {
				System.out.println("fail to write firstReply object");
				e.printStackTrace();
			}
			this.userOutput = java.util.Arrays.copyOf(this.userOutput, this.userOutput.length + 1);
			this.userOutput[this.userOutput.length - 1] = userOutput;
			this.userInput = java.util.Arrays.copyOf(this.userInput, this.userInput.length + 1);
			this.userInput[this.userInput.length - 1] = userInput;
			userData.get(userData.size()-1).streamid = this.userInput.length - 1;
			//TODO start listening to this guy with id of this.userInput/Output.lenght - 1 and id = userData.size() - 1 and onlienid = onlineUser.size() - 1
			//TODO broadcast information that the person is online
			broadcast(userData.size()-1);
			new ServerListener(userData.size() - 1, this.userInput.length - 1, onlineUser.size() - 1);
		}
	}
	
	public LinkedList<UserProfile> getOnlineList(){
		LinkedList<UserProfile> getOnlineUser = new LinkedList<UserProfile>();
		for(int i = 0; i < onlineUser.size(); i ++){
			UserProfile addUser = new UserProfile();
			addUser.name = onlineUser.get(i).name;
			addUser.gameStatus = onlineUser.get(i).gameStatus;
			addUser.inGameip = onlineUser.get(i).inGameip;
			addUser.ip = onlineUser.get(i).ip;
			addUser.status = onlineUser.get(i).status;
			addUser.uid = onlineUser.get(i).uid;
			getOnlineUser.add(addUser);
		}
		return getOnlineUser;
	}
	
	public LinkedList<UserProfile> getFriendList(int position){
		User targetUser = onlineUser.get(position);
		LinkedList<UserProfile> getFriend = new LinkedList<UserProfile>();
		for(int i = 0; i < targetUser.friend.size(); i ++){
			UserProfile addUser = new UserProfile();
			addUser.name = targetUser.friend.get(i).name;
			addUser.gameStatus = targetUser.friend.get(i).gameStatus;
			addUser.inGameip = targetUser.friend.get(i).inGameip;
			addUser.ip = targetUser.friend.get(i).ip;
			addUser.status = targetUser.friend.get(i).status;
			addUser.uid = targetUser.friend.get(i).uid;
			getFriend.add(addUser);
		}
		return getFriend;
	}
	
	//broadcast to all onlineUser
	public void broadcast(int id){
		for(int i = 0; i < onlineUser.size(); i ++){
			PassingObjectCentral sendingPacket = new PassingObjectCentral();
			sendingPacket.updateList(getOnlineList(), getFriendList(i));
			try {
				userOutput[onlineUser.get(i).streamid].writeObject(sendingPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ServerListener extends Thread{
		int id;
		int streamid;
		int onlineid;
		
		public ServerListener(int id, int streamid, int onlineid){
			this.id = id;
			this.streamid = streamid;
			this.onlineid = onlineid;
			start();
		}
		
		public void run(){
			while(true){
				try{
					while(true){
						System.out.println("Listening to id" + id);
						PassingObjectCentral getObject = (PassingObjectCentral)userInput[streamid].readObject();
						if(getObject.protocol == 'o'){	
							//extra protocol for log out, reply user of the same object to confirm log out then terminate connection
							System.out.println("reply 'o' object to client");
							try{
								PassingObjectCentral logOutReply = new PassingObjectCentral();
								logOutReply.logOut();
								userOutput[streamid].writeObject(logOutReply);
							}catch(IOException e){
								System.out.println("unable to reply logout object");
								e.printStackTrace();
							}
							try{
								System.out.println("initiate protocol 'o' in thread");
								userInput[streamid].close();
								userOutput[streamid].close();
								userData.get(id).setOffline();
								System.out.println("online size before log out: " + onlineUser.size());
								for(int i = 0; i < onlineUser.size(); i ++){
									if(userData.get(id).uid.equals(onlineUser.get(i).uid))
										onlineUser.remove(i);
								}
								System.out.println("position: " + onlineid + " removed");
								System.out.println("new online size: " + onlineUser.size());
								broadcast(onlineid);
								return;
							}catch(IOException err){
								System.out.println("IOError in closing the stream after receving log out protocol in thread");
							}
						}else if(getObject.protocol == 'p'){
							//extra protocol for requesting user profile return User Object to the client
							PassingObjectCentral profileReply = new PassingObjectCentral();
							User replyUser = new User(userData.get(id));
							profileReply.userInfo = replyUser;
							profileReply.protocol = 'p';
							userOutput[streamid].writeObject(profileReply);
							continue;
						}else if(getObject.protocol == 'a'){
							//extra protocol for following user
							//add target uid to friend list then reply with list of friend
							for(int i = 0; i < userData.size(); i ++){
								if(userData.get(i).uid.equals(getObject.uid)){
									userData.get(id).addFriend(userData.get(i));
									break;
								}
							}
							PassingObjectCentral replyAdd = new PassingObjectCentral();
							replyAdd.friendList = getFriendList(id);
							replyAdd.protocol = 'a';
							userOutput[streamid].writeObject(replyAdd);
							continue;
						}else if(getObject.protocol == 'w'){
							//update history and win lose draw counts
							if(getObject.winLoseDraw == 'w'){
								userData.get(id).win(getObject.uid);
							}else if(getObject.winLoseDraw == 'l'){
								userData.get(id).lose(getObject.uid);
							}else if(getObject.winLoseDraw == 'd'){
								userData.get(id).draw(getObject.uid);
							}
							continue;
						}
						readObject(getObject, id);
					}
				}catch(ClassNotFoundException e){
					System.out.println("cannot find class");
				}catch(IOException e){
					System.out.println("IO Error at id:" + id);
					System.out.println("change status of user to offline then broadcast");
					userData.get(id).setOffline();
					for(int i = 0; i < onlineUser.size(); i ++){
						if(userData.get(id).uid.equals(onlineUser.get(i).uid))
							onlineUser.remove(i);
					}
					System.out.println("try to close stream");
					try{
						userInput[id].close();
						userOutput[id].close();
					}catch(IOException err){
						System.out.println("unable to close stream on run()");
						err.printStackTrace();
					}
					return;
				}
			}
		}
		
		
		public void readObject(PassingObjectCentral theObject, int id){
			switch(theObject.protocol){
			case'c':
				//user create room
				userData.get(id).setHosting();
				broadcast(id);
				break;
			case'j':
				//join room
				userData.get(id).setInGame(theObject.ingameIP);
				broadcast(id);
				break;
			case'n':
				//change name
				userData.get(id).name = theObject.newName;
				broadcast(id);
				break;
			case'e':
				//exit room
				userData.get(id).setNotInRoom();
				broadcast(id);
				break;
			}
		}
	}
}
