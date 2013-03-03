import java.util.LinkedList;


public class PassingObjectCentral {
	LinkedList <UserProfile>userList;
	LinkedList <UserProfile>friendList;
	char protocol;
	Register registerInfo;
	String newName;
	boolean logIn;
	String addFriendUID;
	String clientUID;
	String uid;
	String password;
	
	public void logIn(String uid, String password){
		protocol = 'l';
		this.uid = uid;
		this.password = password;
	}
}
