// test user object used in friend and online user list
public class ListObject {
	String name;
	String ip;
	
	public ListObject() {
		
	}
	
	public ListObject(String name) {
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
