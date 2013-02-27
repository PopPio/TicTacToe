
public class User {
	String name;
	int score;
	int type;
	
	final static int TYPE_O = 1;
	final static int TYPE_X = 2;
	final static int TYPE_SPEC = 3;
	
	public User(){
		this("John Doe", TYPE_SPEC); // default
	}
	public User(String name, int type){
		this.name = name;
		this.type = type;
		this.score = 0;
	}
	public User(String name, int type, int score){
		this.name = name;
		this.type = type;
		this.score = score;
	}
	
	
}
