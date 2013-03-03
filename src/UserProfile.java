import java.io.Serializable;


public class UserProfile implements Serializable{
	private static final long serialVersionUID = 7526472295622776147L;
	String name;
	String uid;
	String ip;
	String inGameip;
	String status;
	String gameStatus;
	
	public String friendStatus(){
		return name + status;
	}
	
	public String userStatus(){
		return name + gameStatus;
	}

	public String toString(){
		return name + status + gameStatus;
	}
}
