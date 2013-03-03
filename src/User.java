import java.io.Serializable;
import java.util.LinkedList;


public class User implements Serializable{
	private static final long serialVersionUID = 7526472295622776147L;
	String name;
	String uid;
	String password;
	String status;
	String gameStatus;
	String ip;
	String inGameip;
	int win;
	int lose;
	int draw;
	String history;
	LinkedList <User>friend;
	int streamid;
	
	public User(String name, String uid, String password, String ip){
		this.name = name;
		this.uid = uid;
		this.password = password;
		this.ip = ip;
		inGameip = "";
		win = 0;
		lose = 0;
		draw = 0;
		history = "";
		friend = new LinkedList<User>();
	}
	
	public User(User cloneUser){
		name = cloneUser.name;
		uid = cloneUser.uid;
		password = cloneUser.password;
		status = cloneUser.status;
		gameStatus = cloneUser.gameStatus;
		ip = cloneUser.ip;
		inGameip = cloneUser.inGameip;
		win = cloneUser.win;
		lose = cloneUser.lose;
		draw = cloneUser.draw;
		history = cloneUser.history;
		friend = cloneUser.friend;
	}
	
	public void setOnline(){
		status = "[Online]";
	}
	
	public void setOffline(){
		status = "[Offline]";
	}
	
	public void setHosting(){
		gameStatus = "[Hosting a Game]";
		inGameip = ip;
	}
	
	public void setInGame(String inGameIP){
		gameStatus = "[In Game]";
		inGameip = inGameIP;
	}
	
	public void setNotInRoom(){
		gameStatus = "";
		inGameip = "";
	}
	
	public void win(String uid){
		for(int i = 0; i < friend.size(); i ++){
			if(uid.equals(friend.get(i).uid)){
				history += "[win] " + friend.get(i).name + "\n";
				win++;
				System.out.println(win);
				System.out.println(history);
				return;
			}
		}
	}
	
	public void lose(String uid){
		for(int i = 0; i < friend.size(); i ++){
			if(uid.equals(friend.get(i).uid)){
				history += "[lose] " + friend.get(i).name + "\n";
				lose++;

				System.out.println(lose);
				System.out.println(history);
				return;
			}
		}
	}
	
	public void draw(String uid){
		for(int i = 0; i < friend.size(); i++){
			if(uid.equals(friend.get(i).uid)){
				history += "[draw] " + friend.get(i).name + "\n";
				draw++;

				System.out.println(draw);
				System.out.println(history);
				return;
			}
		}
	}
	
	public void addFriend(User newFriend){
		friend.add(newFriend);
	}
	
	
	
}
