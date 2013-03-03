import java.io.Serializable;
import java.util.LinkedList;


public class PassingObjectCentral implements Serializable{
	private static final long serialVersionUID = 7526472295622776147L;
	LinkedList <UserProfile>userList;
	LinkedList <UserProfile>friendList;
	char protocol;
	Register registerInfo;
	String newName;
	boolean logIn;
	String addFriendUID;
	String clientUID;
	String name;
	String uid;
	String password;
	User userInfo;
	String ingameIP;
	char winLoseDraw;
	
	public void logIn(String uid, String password){
		protocol = 'l';
		this.uid = uid;
		this.password = password;
	}
	
	public void logInSuccess(LinkedList <UserProfile>userList, LinkedList <UserProfile>friendList, String name, String uid){
		this.userList = userList;
		this.friendList = friendList;
		this.name = name;
		this.uid = uid;
		logIn = true;
	}
	
	public void updateList(LinkedList <UserProfile>userList, LinkedList <UserProfile>friendList){
		protocol = 'u';
		this.userList = userList;
		this.friendList = friendList;
	}
	
	public void logInFail(){
		logIn = false;
	}
	
	public void register(String name, String uid, String password){
		protocol = 'r';
		this.name = name;
		this.uid = uid;
		this.password = password;
	}
	
	public void logOut(){
		protocol = 'o';
	}
	
	public void requestProfile(){
		protocol = 'p';
	}
	
	public void followFriend(String friendUID){
		protocol = 'a';
		uid = friendUID;
	}
	
	public void updateScore(char result, String frienduid){
		protocol = 'w';
		winLoseDraw = result;
		uid = frienduid;
	}
	
	public void createRoom(){
		protocol = 'c';
	}
	
	public void joinGame(String ingameIP){
		protocol = 'j';
		this.ingameIP = ingameIP;
	}
	
	public void addFriend(String targetuid){
		uid = targetuid;
		protocol = 'a';
	}
	
	public void changeName(String newName, String uid){
		protocol = 'n';
		this.uid = uid;
		this.newName = newName;
	}
	
	public void exitRoom(){
		protocol = 'e';
	}
	
}
